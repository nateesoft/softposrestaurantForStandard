package com.softpos.main.floorplan.view;

import java.awt.*;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AdvertisingScreen extends JFrame {

    private static final String CARD_ADS = "ads";
    private static final String CARD_SALE = "sale";

    private static AdvertisingScreen instance;

    private final AdvertisingPanel adsPanel;
    private final SaleInfoPanel salePanel;
    private final CardLayout cards = new CardLayout();

    private AdvertisingScreen(GraphicsConfiguration gc) {
        super(gc);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        adsPanel = new AdvertisingPanel();
        salePanel = new SaleInfoPanel();

        JPanel container = new JPanel(cards);
        container.add(adsPanel, CARD_ADS);
        container.add(salePanel, CARD_SALE);
        setContentPane(container);

        setBounds(gc.getBounds());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    /** เปิดจอที่ 2 — ถ้าไม่มีจอที่ 2 จะไม่ทำอะไร */
    public static void open() {
        if (instance != null) return;
        GraphicsDevice[] screens = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getScreenDevices();
        if (screens.length < 2) return;
        instance = new AdvertisingScreen(screens[1].getDefaultConfiguration());
        instance.setVisible(true);
    }

    /** ปิดจอที่ 2 */
    public static void close() {
        if (instance == null) return;
        instance.adsPanel.stopTimers();
        instance.salePanel.stopTimers();
        instance.dispose();
        instance = null;
    }

    /** แสดงหน้าโฆษณา / slideshow */
    public static void showAds() {
        if (instance == null) return;
        instance.cards.show(instance.getContentPane(), CARD_ADS);
    }

    /**
     * แสดงข้อมูลการขายบนจอที่ 2
     *
     * @param tableNo      เลขโต๊ะ
     * @param customerCount จำนวนลูกค้า
     * @param items        รายการสั่ง (ใช้ SaleInfoPanel.Item)
     * @param subTotal     ยอดก่อน service/vat
     * @param serviceAmt   ค่าบริการ
     * @param vatAmt       ภาษีมูลค่าเพิ่ม
     * @param grandTotal   ยอดรวมที่ต้องชำระ
     */
    public static void showSaleInfo(String tableNo, int customerCount,
            List<SaleInfoPanel.Item> items,
            double subTotal, double serviceAmt,
            double vatAmt, double grandTotal) {
        if (instance == null) return;
        instance.salePanel.update(tableNo, customerCount, items,
                subTotal, serviceAmt, vatAmt, grandTotal);
        instance.cards.show(instance.getContentPane(), CARD_SALE);
    }

    /**
     * แสดงข้อมูลการชำระเงินบนจอที่ 2
     *
     * @param tableNo    เลขโต๊ะ
     * @param payLines   รายการวิธีชำระเงิน (ใช้ SaleInfoPanel.Item)
     * @param grandTotal ยอดชำระทั้งหมด
     * @param changeAmt  เงินทอน (0 = ยังไม่ได้ชำระ)
     */
    public static void showPaymentInfo(String tableNo,
            List<SaleInfoPanel.Item> payLines,
            double grandTotal, double changeAmt) {
        if (instance == null) return;
        instance.salePanel.updatePayment(tableNo, payLines, grandTotal, changeAmt);
        instance.cards.show(instance.getContentPane(), CARD_SALE);
    }
}
