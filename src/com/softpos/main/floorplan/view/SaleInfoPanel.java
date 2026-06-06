package com.softpos.main.floorplan.view;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.Timer;
import javax.swing.JPanel;

public class SaleInfoPanel extends JPanel {

    public static class Item {
        public final String name;
        public final String qty;
        public final String amount;

        public Item(String name, String qty, String amount) {
            this.name = name;
            this.qty = qty;
            this.amount = amount;
        }
    }

    private String tableNo = "";
    private int customerCount = 0;
    private List<Item> items = new ArrayList<>();
    private double subTotal;
    private double serviceAmt;
    private double vatAmt;
    private double grandTotal;
    private boolean paymentMode = false;
    private double changeAmt = 0;

    private int scrollOffset = 0;
    private Timer clockTimer;
    private Timer scrollTimer;

    private static final int ROW_H = 42;
    private static final int HEADER_H = 90;
    private static final int TOTALS_H = 200;
    private static final Color BG_DARK = new Color(10, 15, 35);
    private static final Color ACCENT = new Color(190, 65, 0);
    private static final Color ACCENT_BRIGHT = new Color(255, 200, 50);
    private static final Font FONT_ITEM = new Font("Tahoma", Font.PLAIN, 20);
    private static final Font FONT_TOTAL_LABEL = new Font("Tahoma", Font.PLAIN, 20);
    private static final Font FONT_GRAND_LABEL = new Font("Tahoma", Font.BOLD, 24);
    private static final Font FONT_GRAND_VALUE = new Font("Tahoma", Font.BOLD, 52);
    private static final Font FONT_HEADER = new Font("Tahoma", Font.BOLD, 48);

    public SaleInfoPanel() {
        setBackground(BG_DARK);
        startTimers();
    }

    public void update(String tableNo, int customerCount, List<Item> items,
            double subTotal, double serviceAmt, double vatAmt, double grandTotal) {
        this.tableNo = tableNo != null ? tableNo : "";
        this.customerCount = customerCount;
        this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
        this.subTotal = subTotal;
        this.serviceAmt = serviceAmt;
        this.vatAmt = vatAmt;
        this.grandTotal = grandTotal;
        this.paymentMode = false;
        this.changeAmt = 0;
        this.scrollOffset = 0;
        repaint();
    }

    public void updatePayment(String tableNo, List<Item> payLines, double grandTotal, double change) {
        this.tableNo = tableNo != null ? tableNo : "";
        this.customerCount = 0;
        this.items = payLines != null ? new ArrayList<>(payLines) : new ArrayList<>();
        this.subTotal = 0;
        this.serviceAmt = 0;
        this.vatAmt = 0;
        this.grandTotal = grandTotal;
        this.paymentMode = true;
        this.changeAmt = change;
        this.scrollOffset = 0;
        repaint();
    }

    private int maxVisibleRows() {
        int availH = getHeight() - HEADER_H - 40 - TOTALS_H;
        return Math.max(1, availH / ROW_H);
    }

    private void startTimers() {
        clockTimer = new Timer(1000, e -> repaint());
        clockTimer.start();

        scrollTimer = new Timer(2500, e -> {
            int max = maxVisibleRows();
            if (items.size() > max) {
                scrollOffset++;
                if (scrollOffset >= items.size()) {
                    scrollOffset = 0;
                }
                repaint();
            }
        });
        scrollTimer.start();
    }

    public void stopTimers() {
        if (clockTimer != null) clockTimer.stop();
        if (scrollTimer != null) scrollTimer.stop();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        paintBackground(g2, w, h);
        paintHeader(g2, w);
        paintColumnHeader(g2, w);
        paintItems(g2, w, h);
        paintTotals(g2, w, h);
        paintClock(g2, w, h);

        g2.dispose();
    }

    private void paintBackground(Graphics2D g2, int w, int h) {
        g2.setColor(BG_DARK);
        g2.fillRect(0, 0, w, h);
        g2.setPaint(new GradientPaint(0, 0, new Color(30, 20, 55, 200), w, h, new Color(5, 10, 25, 200)));
        g2.fillRect(0, 0, w, h);
    }

    private void paintHeader(Graphics2D g2, int w) {
        // Header background
        g2.setColor(ACCENT);
        g2.fillRect(0, 0, w, HEADER_H);
        g2.setPaint(new GradientPaint(0, 0, new Color(255, 255, 255, 35), 0, HEADER_H, new Color(0, 0, 0, 0)));
        g2.fillRect(0, 0, w, HEADER_H);

        // Table number
        g2.setFont(FONT_HEADER);
        g2.setColor(Color.WHITE);
        String title = tableNo.isEmpty() ? "กรุณารอสักครู่..." : "โต๊ะที่  " + tableNo;
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(title, (w - fm.stringWidth(title)) / 2, 62);

        // Customer count (right aligned)
        if (customerCount > 0) {
            g2.setFont(new Font("Tahoma", Font.PLAIN, 18));
            g2.setColor(new Color(255, 220, 180));
            String custStr = "ลูกค้า " + customerCount + " ท่าน";
            fm = g2.getFontMetrics();
            g2.drawString(custStr, w - fm.stringWidth(custStr) - 20, 78);
        }
    }

    private void paintColumnHeader(Graphics2D g2, int w) {
        int y = HEADER_H + 30;
        g2.setColor(new Color(255, 255, 255, 15));
        g2.fillRect(0, y - 22, w, 28);

        g2.setFont(new Font("Tahoma", Font.BOLD, 15));
        g2.setColor(new Color(160, 160, 180));
        FontMetrics fm = g2.getFontMetrics();

        g2.drawString("รายการ", 30, y);

        String qtyHdr = "จำนวน";
        g2.drawString(qtyHdr, w * 3 / 4 - fm.stringWidth(qtyHdr) / 2, y);

        String priceHdr = "ราคา";
        g2.drawString(priceHdr, w - 30 - fm.stringWidth(priceHdr), y);

        g2.setColor(new Color(255, 255, 255, 35));
        g2.drawLine(20, y + 6, w - 20, y + 6);
    }

    private void paintItems(Graphics2D g2, int w, int h) {
        if (items.isEmpty()) {
            g2.setFont(new Font("Tahoma", Font.PLAIN, 22));
            g2.setColor(new Color(120, 120, 140));
            String empty = "ยังไม่มีรายการสั่ง";
            FontMetrics fm = g2.getFontMetrics();
            int areaTop = HEADER_H + 40;
            int areaH = h - areaTop - TOTALS_H;
            g2.drawString(empty, (w - fm.stringWidth(empty)) / 2, areaTop + areaH / 2);
            return;
        }

        int areaTop = HEADER_H + 44;
        int max = maxVisibleRows();
        int start = scrollOffset;
        int end = Math.min(start + max, items.size());

        g2.setFont(FONT_ITEM);
        FontMetrics fm = g2.getFontMetrics();

        for (int i = start; i < end; i++) {
            Item item = items.get(i);
            int rowY = areaTop + (i - start) * ROW_H + ROW_H;

            // Alternate row
            if ((i % 2) == 0) {
                g2.setColor(new Color(255, 255, 255, 8));
                g2.fillRect(0, rowY - ROW_H + 4, w, ROW_H);
            }

            // Name (truncate to fit half screen)
            g2.setColor(Color.WHITE);
            String name = truncate(g2, item.name, w * 2 / 3 - 50);
            g2.drawString(name, 30, rowY);

            // Qty
            g2.setColor(new Color(180, 210, 255));
            String qty = item.qty;
            g2.drawString(qty, w * 3 / 4 - fm.stringWidth(qty) / 2, rowY);

            // Amount
            g2.setColor(ACCENT_BRIGHT);
            String amt = item.amount;
            g2.drawString(amt, w - 30 - fm.stringWidth(amt), rowY);
        }

        // Scroll indicator
        if (items.size() > max) {
            g2.setFont(new Font("Tahoma", Font.PLAIN, 13));
            g2.setColor(new Color(255, 255, 255, 50));
            String indicator = (scrollOffset + max < items.size())
                    ? "▼  " + items.size() + " รายการ"
                    : "▲  กลับไปรายการแรก";
            g2.drawString(indicator, 30, areaTop + max * ROW_H + 18);
        }
    }

    private void paintTotals(Graphics2D g2, int w, int h) {
        int sepY = h - TOTALS_H;

        // Separator
        g2.setColor(new Color(255, 180, 50, 100));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawLine(20, sepY, w - 20, sepY);
        g2.setStroke(new BasicStroke(1f));

        int labelX = w / 2 + 20;
        int valueRightX = w - 30;
        int lineH = 34;
        int y = sepY + lineH;

        g2.setFont(FONT_TOTAL_LABEL);
        FontMetrics fm = g2.getFontMetrics();

        if (paymentMode) {
            // เงินทอน row (แสดงเฉพาะเมื่อมีเงินทอน)
            if (changeAmt > 0) {
                g2.setColor(new Color(100, 255, 150));
                g2.setFont(new Font("Tahoma", Font.BOLD, 22));
                fm = g2.getFontMetrics();
                g2.drawString("เงินทอน", labelX, y);
                String cs = String.format("%,.2f", changeAmt);
                g2.drawString(cs, valueRightX - fm.stringWidth(cs), y);
                g2.setFont(FONT_TOTAL_LABEL);
                fm = g2.getFontMetrics();
            }
        } else {
            // Sub total
            if (subTotal > 0 && (serviceAmt > 0 || vatAmt > 0)) {
                g2.setColor(new Color(200, 200, 215));
                g2.drawString("ยอดรวม", labelX, y);
                String s = String.format("%,.2f", subTotal);
                g2.drawString(s, valueRightX - fm.stringWidth(s), y);
                y += lineH;
            }

            // Service
            if (serviceAmt > 0) {
                g2.setColor(new Color(200, 200, 215));
                g2.drawString("ค่าบริการ", labelX, y);
                String s = String.format("%,.2f", serviceAmt);
                g2.drawString(s, valueRightX - fm.stringWidth(s), y);
                y += lineH;
            }

            // VAT
            if (vatAmt > 0) {
                g2.setColor(new Color(200, 200, 215));
                g2.drawString("ภาษีมูลค่าเพิ่ม 7%", labelX, y);
                String s = String.format("%,.2f", vatAmt);
                g2.drawString(s, valueRightX - fm.stringWidth(s), y);
            }
        }

        // Grand total bar
        int barH = 80;
        int barY = h - barH;
        if (paymentMode) {
            g2.setPaint(new GradientPaint(0, barY, new Color(0, 130, 60), 0, h, new Color(0, 80, 35)));
        } else {
            g2.setPaint(new GradientPaint(0, barY, new Color(200, 70, 0), 0, h, new Color(140, 40, 0)));
        }
        g2.fillRect(0, barY, w, barH);
        g2.setPaint(new GradientPaint(0, barY, new Color(255, 255, 255, 30), 0, h, new Color(0, 0, 0, 0)));
        g2.fillRect(0, barY, w, barH);

        g2.setFont(FONT_GRAND_LABEL);
        g2.setColor(ACCENT_BRIGHT);
        String barLabel = paymentMode ? "ยอดชำระทั้งหมด" : "ยอดที่ต้องชำระ";
        g2.drawString(barLabel, 30, barY + 50);

        g2.setFont(FONT_GRAND_VALUE);
        g2.setColor(Color.WHITE);
        fm = g2.getFontMetrics();
        String grandStr = String.format("฿ %,.2f", grandTotal);
        g2.drawString(grandStr, w - 30 - fm.stringWidth(grandStr), barY + 58);
    }

    private void paintClock(Graphics2D g2, int w, int h) {
        g2.setFont(new Font("Tahoma", Font.PLAIN, 13));
        g2.setColor(new Color(255, 255, 255, 60));
        g2.drawString(new SimpleDateFormat("HH:mm:ss").format(new Date()), 20, HEADER_H + 16);
    }

    private String truncate(Graphics2D g2, String text, int maxWidth) {
        FontMetrics fm = g2.getFontMetrics();
        if (fm.stringWidth(text) <= maxWidth) return text;
        while (text.length() > 3 && fm.stringWidth(text + "...") > maxWidth) {
            text = text.substring(0, text.length() - 1);
        }
        return text + "...";
    }
}
