package com.softpos.e2e;

import javax.swing.SwingUtilities;
import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Listens for AWT window events and captures dialog/popup text during tests.
 * Call start() before a test and stop() after.
 */
public class PopupDetector implements AWTEventListener {

    private final List<String> capturedPopups = Collections.synchronizedList(new ArrayList<>());
    private volatile boolean active = false;
    private final TestStepLogger logger = TestStepLogger.getInstance();

    public void start() {
        capturedPopups.clear();
        active = true;
        Toolkit.getDefaultToolkit().addAWTEventListener(this, AWTEvent.WINDOW_EVENT_MASK);
    }

    public void stop() {
        active = false;
        Toolkit.getDefaultToolkit().removeAWTEventListener(this);
    }

    @Override
    public void eventDispatched(AWTEvent event) {
        if (!active) return;
        if (!(event instanceof WindowEvent)) return;
        WindowEvent we = (WindowEvent) event;

        if (we.getID() == WindowEvent.WINDOW_OPENED) {
            Window w = we.getWindow();
            if (w instanceof Dialog) {
                SwingUtilities.invokeLater(() -> {
                    String text = extractText(w);
                    if (!text.isEmpty()) {
                        capturedPopups.add(text);
                        logger.recordPopup(text);
                    }
                });
            }
        }
    }

    /** All popup messages captured since last start(). */
    public List<String> getCapturedPopups() {
        return Collections.unmodifiableList(capturedPopups);
    }

    /** True if any popup has been captured since last start(). */
    public boolean hasPopup() {
        return !capturedPopups.isEmpty();
    }

    /** Most recent popup text, or empty string if none. */
    public String lastPopupText() {
        if (capturedPopups.isEmpty()) return "";
        return capturedPopups.get(capturedPopups.size() - 1);
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    /** Walk the dialog tree and collect visible text labels. */
    private String extractText(Container container) {
        StringBuilder sb = new StringBuilder();
        for (Component c : container.getComponents()) {
            if (c instanceof javax.swing.JLabel) {
                String t = ((javax.swing.JLabel) c).getText();
                if (t != null && !t.trim().isEmpty()) {
                    sb.append(t.trim()).append(" ");
                }
            } else if (c instanceof javax.swing.JTextArea) {
                String t = ((javax.swing.JTextArea) c).getText();
                if (t != null && !t.trim().isEmpty()) {
                    sb.append(t.trim()).append(" ");
                }
            } else if (c instanceof Container) {
                String child = extractText((Container) c);
                if (!child.isEmpty()) sb.append(child);
            }
        }
        return sb.toString().trim();
    }
}
