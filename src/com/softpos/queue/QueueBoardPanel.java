package com.softpos.queue;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * Customer-facing queue board for monitor2: three rows (Pending / Prepare / Serve),
 * each listing the queue numbers in that status.
 */
public class QueueBoardPanel extends JPanel {

    private static final Color BG = new Color(10, 15, 35);
    private static final String[] TITLES = {"รอคิว / Pending", "กำลังเตรียม / Preparing", "เชิญรับอาหาร / Ready"};
    private static final String[] STATUS = {QueueService.PENDING, QueueService.PREPARE, QueueService.SERVE};
    private static final Color[] COLORS = {
        new Color(255, 190, 40), new Color(80, 160, 255), new Color(60, 200, 110)
    };

    private final boolean compact;

    public QueueBoardPanel(boolean compact) {
        this.compact = compact;
        setBackground(BG);
        QueueMonitor.getInstance().addListener(this::repaint);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int pad = compact ? 12 : 30;
        int headH = compact ? 56 : 100;

        g2.setColor(new Color(255, 200, 50));
        g2.setFont(new Font("Tahoma", Font.BOLD, compact ? 30 : 56));
        String title = "คิวอาหาร (QUEUE)";
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(title, (w - fm.stringWidth(title)) / 2, headH - (compact ? 16 : 30));

        List<QueueService.Entry> all = QueueMonitor.getInstance().getSnapshot();
        int rowH = (h - headH - pad) / 3;
        for (int r = 0; r < 3; r++) {
            List<Integer> nos = new ArrayList<>();
            for (QueueService.Entry e : all) {
                if (STATUS[r].equals(e.status)) {
                    nos.add(e.no);
                }
            }
            paintRow(g2, pad, headH + r * rowH, w - 2 * pad, rowH - pad / 2, r, nos);
        }
        g2.dispose();
    }

    private void paintRow(Graphics2D g2, int x, int y, int w, int h, int r, List<Integer> nos) {
        Color c = COLORS[r];
        g2.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 40));
        g2.fillRoundRect(x, y, w, h, 24, 24);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(r == 2 ? 4f : 2f));
        g2.drawRoundRect(x, y, w, h, 24, 24);

        g2.setFont(new Font("Tahoma", Font.BOLD, compact ? 18 : 30));
        g2.drawString(TITLES[r], x + 16, y + (compact ? 28 : 46));

        int top = y + (compact ? 38 : 62);
        int availH = h - (top - y) - 8;
        int availW = w - 24;
        int chipH = Math.max(24, Math.min(compact ? 64 : 110, availH));
        int fontSize = Math.max(14, (int) (chipH * 0.62));
        g2.setFont(new Font("Tahoma", Font.BOLD, fontSize));
        FontMetrics fm = g2.getFontMetrics();
        int gap = 10;
        int chipW = Math.max(fm.stringWidth("0000") + 24, chipH);

        // shrink to fit in as many lines as the row allows; beyond that show "+n"
        int perLine = Math.max(1, (availW + gap) / (chipW + gap));
        int maxLines = Math.max(1, (availH + gap) / (chipH + gap));
        int capacity = perLine * maxLines;
        List<Integer> shown = nos;
        int hidden = 0;
        if (nos.size() > capacity) {
            // keep the oldest numbers visible (oldest first), summarise the rest
            shown = nos.subList(0, Math.max(0, capacity - 1));
            hidden = nos.size() - shown.size();
        }
        int cx = x + 12;
        int cy = top;
        Color fill = r == 2 ? c : new Color(c.getRed(), c.getGreen(), c.getBlue(), 200);
        for (int i = 0; i <= shown.size(); i++) {
            String text;
            if (i < shown.size()) {
                text = String.valueOf(shown.get(i));
            } else if (hidden > 0) {
                text = "+" + hidden;
            } else {
                break;
            }
            if (cx + chipW > x + w) {
                cx = x + 12;
                cy += chipH + gap;
            }
            g2.setColor(fill);
            g2.fillRoundRect(cx, cy, chipW, chipH, 18, 18);
            g2.setColor(r == 2 ? new Color(10, 40, 20) : BG);
            g2.drawString(text, cx + (chipW - fm.stringWidth(text)) / 2, cy + (chipH + fm.getAscent() - fm.getDescent()) / 2);
            cx += chipW + gap;
        }
    }
}
