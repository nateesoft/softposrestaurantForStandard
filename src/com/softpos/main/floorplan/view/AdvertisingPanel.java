package com.softpos.main.floorplan.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class AdvertisingPanel extends JPanel {

    private final List<BufferedImage> images = new ArrayList<>();
    private int currentIndex = 0;
    private Timer slideTimer;
    private Timer clockTimer;
    private final SimpleDateFormat timeFmt = new SimpleDateFormat("HH:mm:ss");
    private final SimpleDateFormat dateFmt = new SimpleDateFormat("dd/MM/yyyy");

    private static final String ADS_FOLDER = "ads";
    private static final int SLIDE_INTERVAL_MS = 5000;

    public AdvertisingPanel() {
        setBackground(Color.BLACK);
        loadImages();
        startTimers();
    }

    private void loadImages() {
        File folder = new File(ADS_FOLDER);
        if (!folder.exists() || !folder.isDirectory()) return;
        File[] files = folder.listFiles();
        if (files == null) return;
        for (File f : files) {
            String name = f.getName().toLowerCase();
            if (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png")) {
                try {
                    images.add(ImageIO.read(f));
                } catch (IOException ignored) {
                }
            }
        }
    }

    private void startTimers() {
        slideTimer = new Timer(SLIDE_INTERVAL_MS, e -> {
            if (!images.isEmpty()) {
                currentIndex = (currentIndex + 1) % images.size();
                repaint();
            }
        });
        slideTimer.start();

        clockTimer = new Timer(1000, e -> repaint());
        clockTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        if (!images.isEmpty()) {
            drawSlide(g2, w, h);
        } else {
            drawDefault(g2, w, h);
        }

        drawClock(g2, w, h);
        g2.dispose();
    }

    private void drawSlide(Graphics2D g2, int w, int h) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, w, h);
        BufferedImage img = images.get(currentIndex);
        double scale = Math.min((double) w / img.getWidth(), (double) h / img.getHeight());
        int iw = (int) (img.getWidth() * scale);
        int ih = (int) (img.getHeight() * scale);
        g2.drawImage(img, (w - iw) / 2, (h - ih) / 2, iw, ih, null);
    }

    private void drawDefault(Graphics2D g2, int w, int h) {
        // Same warm gradient theme as panelMain in FloorPlanDialog
        float[] fractions = {0.0f, 0.40f, 0.70f, 1.0f};
        Color[] colors = {
            new Color(20, 8, 2),
            new Color(65, 32, 9),
            new Color(45, 20, 5),
            new Color(28, 11, 3)
        };
        g2.setPaint(new LinearGradientPaint(0, 0, w, h, fractions, colors));
        g2.fillRect(0, 0, w, h);

        g2.setColor(new Color(255, 200, 50));
        g2.setFont(new Font("Tahoma", Font.BOLD, 72));
        String welcome = "ยินดีต้อนรับ";
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(welcome, (w - fm.stringWidth(welcome)) / 2, h / 2 - 30);

        g2.setColor(new Color(255, 160, 40));
        g2.setFont(new Font("Tahoma", Font.PLAIN, 30));
        String sub = "Restaurant By SoftPOS";
        fm = g2.getFontMetrics();
        g2.drawString(sub, (w - fm.stringWidth(sub)) / 2, h / 2 + 30);

        g2.setColor(new Color(120, 80, 40));
        g2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        String hint = "วางรูปภาพโฆษณาไว้ในโฟลเดอร์  \"ads\"  เพื่อแสดงสไลด์โชว์";
        fm = g2.getFontMetrics();
        g2.drawString(hint, (w - fm.stringWidth(hint)) / 2, h - 40);
    }

    private void drawClock(Graphics2D g2, int w, int h) {
        Date now = new Date();
        String time = timeFmt.format(now);
        String date = dateFmt.format(now);

        int boxW = 220, boxH = 70;
        int bx = w - boxW - 20;
        int by = h - boxH - 20;

        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRoundRect(bx, by, boxW, boxH, 16, 16);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Tahoma", Font.BOLD, 32));
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(time, bx + (boxW - fm.stringWidth(time)) / 2, by + 44);

        g2.setColor(new Color(200, 200, 200));
        g2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        fm = g2.getFontMetrics();
        g2.drawString(date, bx + (boxW - fm.stringWidth(date)) / 2, by + 63);
    }

    public void stopTimers() {
        if (slideTimer != null) slideTimer.stop();
        if (clockTimer != null) clockTimer.stop();
    }
}
