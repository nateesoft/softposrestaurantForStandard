package com.softpos.main.floorplan.view;

import com.softpos.connection.database.ConfigFile;
import com.softpos.queue.QueueBoardPanel;
import com.softpos.queue.QueueMonitor;
import com.softpos.queue.QueueService;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Secondary customer displays. connect.ini decides what each monitor shows:
 * <pre>
 * monitor1 = main POS screen (staff) - fixed, not managed here
 * monitor2,sale          (default)   order/payment info while selling, ads when idle
 * monitor3,queue         (default when businessType=FoodCenter)
 * </pre>
 * Values for monitor2/monitor3:
 * <ul>
 * <li>none - monitor not used</li>
 * <li>ads - ads slideshow only</li>
 * <li>sale - order/payment info while selling, ads when idle</li>
 * <li>queue - queue board full screen (FoodCenter only)</li>
 * <li>sale+queue - order/payment info on the left + queue board on the right while selling,
 * queue board full screen when idle (FoodCenter only)</li>
 * </ul>
 * Monitor N is the N-th screen reported by Java; a monitor that is not connected is skipped.
 */
public class AdvertisingScreen extends JFrame {

    private static final String CARD_ADS = "ads";
    private static final String CARD_SALE = "sale";
    private static final String CARD_QUEUE = "queue";

    private static final List<AdvertisingScreen> screensOpen = new ArrayList<>();

    private final AdvertisingPanel adsPanel;
    private final SaleInfoPanel salePanel;
    private final CardLayout cards = new CardLayout();
    private final boolean showsSale;
    private final boolean showsQueue;
    private final boolean queueFullWhenIdle;

    private AdvertisingScreen(GraphicsConfiguration gc, String mode) {
        super(gc);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        showsSale = mode.startsWith("sale");
        showsQueue = mode.contains("queue");
        queueFullWhenIdle = mode.equals("queue") || mode.equals("sale+queue");

        adsPanel = new AdvertisingPanel();
        salePanel = new SaleInfoPanel();

        JPanel container = new JPanel(cards);
        container.add(adsPanel, CARD_ADS);
        if (showsSale && showsQueue) {
            JPanel split = new JPanel(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.BOTH;
            c.weighty = 1;
            c.weightx = 0.62;
            split.add(salePanel, c);
            c.gridx = 1;
            c.weightx = 0.38;
            split.add(new QueueBoardPanel(true), c);
            container.add(split, CARD_SALE);
        } else if (showsSale) {
            container.add(salePanel, CARD_SALE);
        }
        if (queueFullWhenIdle) {
            container.add(new QueueBoardPanel(false), CARD_QUEUE);
        }
        setContentPane(container);

        setBounds(gc.getBounds());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void showIdle() {
        cards.show(getContentPane(), queueFullWhenIdle ? CARD_QUEUE : CARD_ADS);
    }

    /** What monitor N should show, from connect.ini (monitorN) with defaults. */
    private static String modeOf(int monitor) {
        boolean food = QueueService.isFoodCenter();
        String def = monitor == 2 ? "sale" : (food ? "queue" : "none");
        String v = ConfigFile.getProperties("monitor" + monitor).toLowerCase().replace(" ", "");
        if (v.isEmpty()) {
            v = def;
        }
        switch (v) {
            case "none":
            case "ads":
            case "sale":
                return v;
            case "queue":
            case "sale+queue":
                return food ? v : (v.equals("queue") ? "ads" : "sale");
            default:
                return def;
        }
    }

    /** Opens monitor2 / monitor3 as configured. Monitors that are not connected are skipped. */
    public static void open() {
        if (!screensOpen.isEmpty()) return;
        GraphicsDevice[] screens = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getScreenDevices();
        boolean queue = false;
        for (int monitor = 2; monitor <= 3; monitor++) {
            if (screens.length < monitor) continue;
            String mode = modeOf(monitor);
            if (mode.equals("none")) continue;
            AdvertisingScreen s = new AdvertisingScreen(screens[monitor - 1].getDefaultConfiguration(), mode);
            screensOpen.add(s);
            s.setVisible(true);
            s.showIdle();
            queue |= s.showsQueue;
        }
        if (queue) {
            QueueMonitor.getInstance().start();
        }
    }

    public static void close() {
        if (screensOpen.isEmpty()) return;
        QueueMonitor.getInstance().stop();
        for (AdvertisingScreen s : screensOpen) {
            s.adsPanel.stopTimers();
            s.salePanel.stopTimers();
            s.dispose();
        }
        screensOpen.clear();
    }

    /** Idle view: ads slideshow, or the full-screen queue board on queue monitors. */
    public static void showAds() {
        for (AdvertisingScreen s : screensOpen) {
            s.showIdle();
        }
    }

    /**
     * แสดงข้อมูลการขายบนจอลูกค้า
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
        for (AdvertisingScreen s : screensOpen) {
            if (!s.showsSale) continue;
            s.salePanel.update(tableNo, customerCount, items,
                    subTotal, serviceAmt, vatAmt, grandTotal);
            s.cards.show(s.getContentPane(), CARD_SALE);
        }
    }

    /**
     * แสดงข้อมูลการชำระเงินบนจอลูกค้า
     *
     * @param tableNo    เลขโต๊ะ
     * @param orderItems รายการอาหารที่สั่ง (ใช้ SaleInfoPanel.Item)
     * @param payLines   รายการวิธีชำระเงิน (ใช้ SaleInfoPanel.Item)
     * @param grandTotal ยอดชำระทั้งหมด
     * @param changeAmt  เงินทอน (0 = ยังไม่ได้ชำระ)
     */
    public static void showPaymentInfo(String tableNo,
            List<SaleInfoPanel.Item> orderItems,
            List<SaleInfoPanel.Item> payLines,
            double grandTotal, double changeAmt) {
        for (AdvertisingScreen s : screensOpen) {
            if (!s.showsSale) continue;
            s.salePanel.updatePayment(tableNo, orderItems, payLines, grandTotal, changeAmt);
            s.cards.show(s.getContentPane(), CARD_SALE);
        }
    }
}
