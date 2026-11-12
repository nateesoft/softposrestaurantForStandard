package com.softpos.queue;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import javax.swing.Timer;

/** Full-screen "queue N is ready" card with a flashing border. Call {@link #start} / {@link #stop} when shown / hidden. */
public class QueueCallPanel extends JPanel {

    private static final Color[] FLASH = {new Color(255, 40, 40), new Color(255, 220, 0)};

    private int queueNo;
    private int tick;
    private final Timer flash = new Timer(400, e -> {
        tick++;
        repaint();
    });

    public QueueCallPanel() {
        setBackground(Color.BLACK);
        flash.setInitialDelay(0);
    }

    public void start(int no) {
        queueNo = no;
        tick = 0;
        flash.start();
        repaint();
    }

    public void stop() {
        flash.stop();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        int border = Math.max(12, Math.min(w, h) / 24);
        g2.setColor(FLASH[tick % 2]);
        g2.fillRect(0, 0, w, border);
        g2.fillRect(0, h - border, w, border);
        g2.fillRect(0, 0, border, h);
        g2.fillRect(w - border, 0, border, h);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Tahoma", Font.BOLD, h / 10));
        center(g2, "เชิญรับอาหาร / Ready", w, h * 22 / 100);

        g2.setColor(new Color(120, 255, 120));
        g2.setFont(new Font("Tahoma", Font.BOLD, h * 38 / 100));
        center(g2, "คิว " + queueNo, w, h * 68 / 100);
        g2.dispose();
    }

    private static void center(Graphics2D g2, String s, int w, int baseline) {
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(s, (w - fm.stringWidth(s)) / 2, baseline);
    }
}
