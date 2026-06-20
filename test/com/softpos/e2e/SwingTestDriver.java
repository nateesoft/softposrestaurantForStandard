package com.softpos.e2e;

import javax.swing.AbstractButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.AWTException;
import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Window;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Lightweight Swing UI driver using java.awt.Robot and component traversal.
 * Replaces assertj-swing for projects that cannot add external test jars.
 */
public class SwingTestDriver {

    private final Robot robot;

    public SwingTestDriver() throws AWTException {
        robot = new Robot();
        robot.setAutoDelay(80);
        robot.setAutoWaitForIdle(true);
    }

    // ── component finders ────────────────────────────────────────────────────

    /** Find a component by exact class and name attribute. */
    public <T extends Component> T findByName(Class<T> type, String name) {
        for (Window w : Window.getWindows()) {
            if (!w.isVisible()) continue;
            T found = findInContainer(w, type, name);
            if (found != null) return found;
        }
        return null;
    }

    /** Find a button (AbstractButton) whose text contains the given label. */
    public AbstractButton findButtonByText(String text) {
        for (Window w : Window.getWindows()) {
            if (!w.isVisible()) continue;
            AbstractButton b = findButtonInContainer(w, text);
            if (b != null) return b;
        }
        return null;
    }

    /** Find the topmost visible JDialog. */
    public JDialog findTopDialog() {
        for (Window w : Window.getWindows()) {
            if (w instanceof JDialog && w.isVisible()) return (JDialog) w;
        }
        return null;
    }

    /** Find the first visible JFrame. */
    public JFrame findTopFrame() {
        for (Window w : Window.getWindows()) {
            if (w instanceof JFrame && w.isVisible()) return (JFrame) w;
        }
        return null;
    }

    // ── actions ──────────────────────────────────────────────────────────────

    /**
     * Type text into a named JTextField or JPasswordField.
     * Clears the field first.
     */
    public void typeInField(String fieldName, String text) throws Exception {
        Component field = findByName(JTextField.class, fieldName);
        if (field == null) field = findByName(JPasswordField.class, fieldName);
        if (field == null) {
            throw new AssertionError("Field not found: '" + fieldName
                    + "'. Named text fields visible: " + listNamedTextFields());
        }
        clickComponent(field);
        sleep(100);
        // select-all + delete existing content
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_DELETE);
        robot.keyRelease(KeyEvent.VK_DELETE);
        sleep(80);
        typeString(text);
    }

    /** Click the first button whose text contains the given label. */
    public void clickButton(String text) throws Exception {
        AbstractButton btn = findButtonByText(text);
        if (btn == null) throw new AssertionError("Button not found: " + text);
        clickComponent(btn);
    }

    /** Click any AWT/Swing component by centre point. */
    public void clickComponent(Component c) throws Exception {
        if (!c.isVisible()) throw new AssertionError("Component not visible: " + c);
        final Point[] loc = new Point[1];
        SwingUtilities.invokeAndWait(() -> loc[0] = c.getLocationOnScreen());
        int cx = loc[0].x + c.getWidth() / 2;
        int cy = loc[0].y + c.getHeight() / 2;
        robot.mouseMove(cx, cy);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        sleep(60);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.waitForIdle();
    }

    /** Press Enter key. */
    public void pressEnter() {
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.waitForIdle();
    }

    /** Press Escape key (dismiss dialogs). */
    public void pressEscape() {
        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyRelease(KeyEvent.VK_ESCAPE);
        robot.waitForIdle();
    }

    // ── waits ─────────────────────────────────────────────────────────────────

    /**
     * Wait up to timeoutMs for a window whose class simple name contains className.
     * Returns true if found, false on timeout.
     */
    public boolean waitForWindowClass(String className, int timeoutMs) throws InterruptedException {
        long deadline = System.currentTimeMillis() + timeoutMs;
        while (System.currentTimeMillis() < deadline) {
            for (Window w : Window.getWindows()) {
                if (w.isVisible() && w.getClass().getSimpleName().contains(className)) return true;
            }
            Thread.sleep(200);
        }
        return false;
    }

    /**
     * Wait for a visible JDialog to appear (any dialog).
     * Returns the dialog or null on timeout.
     */
    public JDialog waitForDialog(int timeoutMs) throws InterruptedException {
        long deadline = System.currentTimeMillis() + timeoutMs;
        while (System.currentTimeMillis() < deadline) {
            JDialog d = findTopDialog();
            if (d != null) return d;
            Thread.sleep(150);
        }
        return null;
    }

    /**
     * Wait until all visible JDialogs are gone (i.e., confirmation dialogs closed).
     */
    public void waitForDialogClose(int timeoutMs) throws InterruptedException {
        long deadline = System.currentTimeMillis() + timeoutMs;
        while (System.currentTimeMillis() < deadline) {
            if (findTopDialog() == null) return;
            Thread.sleep(150);
        }
    }

    /** Pause execution on the test thread (non-EDT). */
    public void sleep(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    // ── assertions ───────────────────────────────────────────────────────────

    public boolean isWindowVisible(String className) {
        for (Window w : Window.getWindows()) {
            if (w.isVisible() && w.getClass().getSimpleName().contains(className)) return true;
        }
        return false;
    }

    // ── private helpers ───────────────────────────────────────────────────────

    private <T extends Component> T findInContainer(Container container, Class<T> type, String name) {
        for (Component c : container.getComponents()) {
            if (c == null) continue;
            if (type.isInstance(c) && name.equals(c.getName())) return type.cast(c);
            if (c instanceof Container) {
                T found = findInContainer((Container) c, type, name);
                if (found != null) return found;
            }
        }
        return null;
    }

    private AbstractButton findButtonInContainer(Container container, String text) {
        for (Component c : container.getComponents()) {
            if (c instanceof AbstractButton) {
                String label = ((AbstractButton) c).getText();
                if (label != null && label.contains(text)) return (AbstractButton) c;
            }
            if (c instanceof Container) {
                AbstractButton found = findButtonInContainer((Container) c, text);
                if (found != null) return found;
            }
        }
        return null;
    }

    /** Lists all named JTextField/JPasswordField components in visible windows — for diagnostics. */
    private String listNamedTextFields() {
        StringBuilder sb = new StringBuilder("[");
        for (Window w : Window.getWindows()) {
            if (!w.isVisible()) continue;
            collectNamedFields(w, sb);
        }
        sb.append("]");
        return sb.toString();
    }

    private void collectNamedFields(Container container, StringBuilder sb) {
        for (Component c : container.getComponents()) {
            if (c == null) continue;
            if (c instanceof JTextField) {
                String name = c.getName();
                if (name != null) sb.append(name).append(", ");
            }
            if (c instanceof Container) collectNamedFields((Container) c, sb);
        }
    }

    /** Type a plain ASCII string via Robot key events. */
    private void typeString(String text) {
        for (char ch : text.toCharArray()) {
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(ch);
            if (keyCode == KeyEvent.VK_UNDEFINED) continue;
            boolean needsShift = Character.isUpperCase(ch)
                    || "!@#$%^&*()_+{}|:\"<>?".indexOf(ch) >= 0;
            if (needsShift) robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);
            if (needsShift) robot.keyRelease(KeyEvent.VK_SHIFT);
        }
        robot.waitForIdle();
    }
}
