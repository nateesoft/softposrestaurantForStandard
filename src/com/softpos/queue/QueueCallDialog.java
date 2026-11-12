package com.softpos.queue;

import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 * Cashier calls a queue: the queue is moved to Serve (Ready), the number is announced by voice, and a
 * full-screen card with a flashing border is shown (on monitor2/3 when open, else on this screen) until the
 * voice has finished.
 */
public final class QueueCallDialog {

    private QueueCallDialog() {
    }

    /** Blocks (nested event loop) until the announcement is over or the user presses Esc. */
    public static void callReady(Window owner, QueueService.Entry entry) {
        if (!QueueService.SERVE.equals(entry.status)) {
            QueueMonitor.getInstance().skipAnnounce(entry.id); // we announce here, not the monitor
            QueueService.setStatus(entry.id, QueueService.SERVE);
            QueueMonitor.getInstance().refreshNow();
        }

        // customer monitors (monitor2/3) are in use: show the call there, the cashier screen stays as it is
        if (com.softpos.main.floorplan.view.AdvertisingScreen.showCall(entry.no)) {
            QueueAnnouncer.announce(entry.no, () -> SwingUtilities.invokeLater(
                    com.softpos.main.floorplan.view.AdvertisingScreen::hideCall));
            return;
        }

        // no customer monitor: fall back to a full-screen dialog on this screen
        final JDialog dlg = new JDialog(owner, "เรียกคิว", java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        dlg.setUndecorated(true);
        dlg.setAlwaysOnTop(true);

        QueueCallPanel root = new QueueCallPanel();
        dlg.setContentPane(root);
        root.start(entry.no);

        Runnable close = () -> {
            root.stop();
            dlg.dispose();
        };
        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "close");
        root.getActionMap().put("close", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                close.run();
            }
        });

        GraphicsConfiguration gc = owner != null ? owner.getGraphicsConfiguration() : null;
        Rectangle b = gc != null ? gc.getBounds()
                : java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        dlg.setBounds(b);

        // queued behind setVisible(true), so it only runs once the dialog is up
        SwingUtilities.invokeLater(() -> QueueAnnouncer.announce(entry.no, () -> SwingUtilities.invokeLater(close)));
        dlg.setVisible(true);
    }
}
