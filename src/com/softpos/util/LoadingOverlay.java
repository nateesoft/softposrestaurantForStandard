package com.softpos.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Arc2D;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.Timer;

/**
 * Reusable glass-pane loading overlay. Works with any JFrame or JDialog.
 *
 * Usage:
 *   LoadingOverlay.show(frame, "กำลังโหลด...");
 *   LoadingOverlay.hide(frame);
 */
public class LoadingOverlay extends JPanel {

    private static final int DOT_COUNT = 12;
    private static final int SPINNER_RADIUS = 32;
    private static final int DOT_SIZE = 10;
    private static final Color OVERLAY_BG = new Color(0, 0, 0, 170);
    private static final Color CARD_BG = new Color(30, 30, 30, 220);

    private int tick = 0;
    private final String message;
    private final Timer timer;

    private LoadingOverlay(String message) {
        this.message = (message == null) ? "" : message;
        setOpaque(false);
        // Block all mouse/key events while overlay is active
        addMouseListener(new MouseAdapter() {});
        addMouseMotionListener(new MouseMotionAdapter() {});
        timer = new Timer(80, e -> {
            tick = (tick + 1) % DOT_COUNT;
            repaint();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Dim background
        g2.setColor(OVERLAY_BG);
        g2.fillRect(0, 0, getWidth(), getHeight());

        int cx = getWidth() / 2;
        int cy = getHeight() / 2;

        // Card behind spinner
        int cardW = 180;
        int cardH = message.isEmpty() ? 120 : 150;
        int cardX = cx - cardW / 2;
        int cardY = cy - cardH / 2;
        g2.setColor(CARD_BG);
        g2.fillRoundRect(cardX, cardY, cardW, cardH, 20, 20);

        // Spinner — 12 arcs in a circle
        g2.setStroke(new BasicStroke(DOT_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (int i = 0; i < DOT_COUNT; i++) {
            int diff = (i - tick + DOT_COUNT) % DOT_COUNT;
            float alpha = 0.1f + 0.9f * (1f - diff / (float) DOT_COUNT);
            g2.setColor(new Color(1f, 1f, 1f, alpha));
            double startAngle = 90 - (360.0 / DOT_COUNT) * i;
            Arc2D arc = new Arc2D.Double(
                    cx - SPINNER_RADIUS, cy - SPINNER_RADIUS - (message.isEmpty() ? 0 : 12),
                    SPINNER_RADIUS * 2, SPINNER_RADIUS * 2,
                    startAngle, 360.0 / DOT_COUNT - 4,
                    Arc2D.OPEN);
            g2.draw(arc);
        }

        // Message text
        if (!message.isEmpty()) {
            g2.setFont(new Font("Tahoma", Font.BOLD, 14));
            g2.setColor(Color.WHITE);
            FontMetrics fm = g2.getFontMetrics();
            int textW = fm.stringWidth(message);
            g2.drawString(message, cx - textW / 2, cy + SPINNER_RADIUS + 30);
        }

        g2.dispose();
    }

    private void start() {
        timer.start();
    }

    private void stop() {
        timer.stop();
    }

    /**
     * Attach and show the overlay on any JFrame or JDialog.
     * Safe to call before setVisible(true).
     */
    public static LoadingOverlay show(RootPaneContainer container, String message) {
        LoadingOverlay overlay = new LoadingOverlay(message);
        container.setGlassPane(overlay);
        overlay.setVisible(true);
        overlay.start();
        return overlay;
    }

    /**
     * Stop animation and remove the overlay.
     */
    public static void hide(RootPaneContainer container) {
        java.awt.Component glass = container.getGlassPane();
        if (glass instanceof LoadingOverlay) {
            ((LoadingOverlay) glass).stop();
        }
        glass.setVisible(false);
        // Force repaint of content pane — required on Windows where hiding the
        // glass pane does not automatically repaint the underlying components.
        java.awt.Container content = container.getContentPane();
        content.revalidate();
        content.repaint();
    }
}
