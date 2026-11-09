package com.softpos.printer.template;

import com.softpos.connection.database.MySQLConnect;
import com.softpos.constants.PublicVar;
import com.softpos.pos.core.controller.AppContext;
import com.softpos.pos.core.model.TableFileBean;
import com.softpos.printer.control.PrinterDriverControl;
import com.softpos.queue.QueueService;
import com.softpos.util.AppLogUtil;
import com.softpos.util.ThaiUtil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/** Prints a kitchen ticket from a designer template (see {@link KitchenTemplateRenderer}). */
public final class KitchenTemplatePrinter {

    /** Variables offered by the designer: {name, description}. */
    public static final String[][] VARIABLES = {
        {"shop_name", "ชื่อร้าน"},
        {"table", "เลขโต๊ะ"},
        {"queue_no", "เลขคิว (FoodCenter, ว่างถ้าไม่มี)"},
        {"customer", "ชื่อลูกค้า"},
        {"cust_count", "จำนวนลูกค้า"},
        {"order_type", "ประเภทการขาย (ทานในร้าน/ห่อกลับ ...)"},
        {"date", "วันที่"},
        {"time", "เวลา"},
        {"macno", "เลขเครื่อง"},
        {"cashier", "พนักงาน"},
        {"kitchen", "ชื่อเครื่องพิมพ์ครัว (kic1 ...)"},
        {"hr", "เส้นคั่น ----"},
        {"#items", "เริ่มวนรายการอาหาร (บรรทัดเดี่ยว)"},
        {"qty", "จำนวน (ใน items)"},
        {"name", "ชื่อเมนู (ใน items)"},
        {"no", "ลำดับรายการ (ใน items)"},
        {"etd", "ประเภทของรายการ (ใน items)"},
        {"price", "ราคาต่อหน่วย (ใน items)"},
        {"total", "ราคารวม (ใน items)"},
        {"#options", "เริ่มวนข้อความพิเศษ (ใน items)"},
        {"opt", "ข้อความพิเศษ (ใน options)"},
        {"/options", "จบ options"},
        {"/items", "จบ items"},};

    private KitchenTemplatePrinter() {
    }

    private static String etdLabel(String etd) {
        if (etd == null) {
            return "";
        }
        switch (etd) {
            case "E":
                return "ทานในร้าน";
            case "T":
                return "ห่อกลับ";
            case "D":
                return "ส่งถึงบ้าน";
            case "P":
                return "ปิ่นโต";
            case "W":
                return "ขายส่ง";
            default:
                return etd;
        }
    }

    /** Prints all not-yet-printed items of this kitchen on one ticket. */
    public static void print(String tableNo, String rKic, String printerName, String template) {
        try {
            Map<String, String> vars = headerVars(tableNo, printerName);
            List<KitchenTemplateRenderer.Item> items = loadItems(tableNo, rKic);
            if (items.isEmpty()) {
                return;
            }
            vars.put("order_type", items.get(0).vars.get("etd"));
            output(printerName, KitchenTemplateRenderer.render(template, vars, items));
        } catch (RuntimeException e) {
            AppLogUtil.log(KitchenTemplatePrinter.class, "error", e);
            throw e;
        }
    }

    /** Test print with sample data (designer button). */
    public static void printSample(String printerName, String template) {
        output(printerName, renderSample(template, printerName));
    }

    public static List<String> renderSample(String template, String printerName) {
        Map<String, String> vars = new HashMap<>();
        fillCommon(vars, printerName);
        vars.put("shop_name", "ร้านตัวอย่าง");
        vars.put("table", "A12");
        vars.put("queue_no", "15");
        vars.put("customer", "คุณสมชาย");
        vars.put("cust_count", "2");
        vars.put("order_type", "ทานในร้าน");
        List<KitchenTemplateRenderer.Item> items = new ArrayList<>();
        items.add(sampleItem(1, "2", "ข้าวผัดกุ้ง", "ทานในร้าน", "60.00", "120.00", "ไม่ใส่ผัก", "เผ็ดน้อย"));
        items.add(sampleItem(2, "1", "ต้มยำกุ้ง", "ทานในร้าน", "150.00", "150.00"));
        return KitchenTemplateRenderer.render(template, vars, items);
    }

    private static KitchenTemplateRenderer.Item sampleItem(int no, String qty, String name, String etd,
            String price, String total, String... options) {
        KitchenTemplateRenderer.Item it = new KitchenTemplateRenderer.Item();
        it.vars.put("no", String.valueOf(no));
        it.vars.put("qty", qty);
        it.vars.put("name", name);
        it.vars.put("etd", etd);
        it.vars.put("price", price);
        it.vars.put("total", total);
        for (String o : options) {
            it.options.add(o);
        }
        return it;
    }

    private static void output(String printerName, List<String> rows) {
        PrinterDriverControl pd = new PrinterDriverControl();
        PublicVar.printerDriverKitChenName = printerName;
        for (String row : rows) {
            pd.addTextIFont(row);
        }
        pd.printHTMLKitChen(printerName);
    }

    private static void fillCommon(Map<String, String> vars, String printerName) {
        Date now = new Date();
        vars.put("date", new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(now));
        vars.put("time", new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(now));
        vars.put("macno", PublicVar.MACNO == null ? "" : PublicVar.MACNO);
        vars.put("cashier", PublicVar.CASHIER == null ? "" : PublicVar.CASHIER);
        vars.put("kitchen", printerName);
    }

    private static Map<String, String> headerVars(String tableNo, String printerName) {
        Map<String, String> vars = new HashMap<>();
        fillCommon(vars, printerName);
        vars.put("table", ThaiUtil.ASCII2Unicode(tableNo));
        int q = QueueService.getKitchenQueueNo();
        vars.put("queue_no", q > 0 ? String.valueOf(q) : "");
        String customer = "";
        String count = "";
        TableFileBean tb = AppContext.getTableFileControl().getDataByTCode(tableNo);
        if (tb != null) {
            customer = ThaiUtil.ASCII2Unicode(tb.getMemName());
            count = String.valueOf(tb.getTCustomer());
        }
        vars.put("customer", customer);
        vars.put("cust_count", count);
        String shop = "";
        try {
            shop = ThaiUtil.ASCII2Unicode(AppContext.getPosControl().getDataCompany().getName());
        } catch (RuntimeException e) {
            AppLogUtil.log(KitchenTemplatePrinter.class, "error", e);
        }
        vars.put("shop_name", shop);
        return vars;
    }

    private static List<KitchenTemplateRenderer.Item> loadItems(String tableNo, String rKic) {
        List<KitchenTemplateRenderer.Item> items = new ArrayList<>();
        DecimalFormat qtyFmt = new DecimalFormat("#,##0.##", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        DecimalFormat moneyFmt = new DecimalFormat("#,##0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(KitchenTemplatePrinter.class);
        try (PreparedStatement ps = mysql.getConnection().prepareStatement(
                "SELECT R_Index, R_PName, R_Quan, R_Price, R_Total, R_ETD, R_Void, "
                + "R_Opt1, R_Opt2, R_Opt3, R_Opt4, R_Opt5, R_Opt6, R_Opt7, R_Opt8, R_Opt9 "
                + "FROM balance WHERE r_table = ? AND R_PrintOK = 'Y' AND R_KicPrint <> 'P' AND R_Kic = ? "
                + "ORDER BY R_ETD, R_Index")) {
            ps.setString(1, tableNo);
            ps.setString(2, rKic);
            try (ResultSet rs = ps.executeQuery()) {
                int no = 0;
                while (rs.next()) {
                    KitchenTemplateRenderer.Item it = new KitchenTemplateRenderer.Item();
                    String name = ThaiUtil.ASCII2Unicode(rs.getString("R_PName"));
                    if ("V".equals(rs.getString("R_Void"))) {
                        name = "ยกเลิก " + name;
                    }
                    it.vars.put("no", String.valueOf(++no));
                    it.vars.put("name", name);
                    it.vars.put("qty", qtyFmt.format(rs.getDouble("R_Quan")));
                    it.vars.put("price", moneyFmt.format(rs.getDouble("R_Price")));
                    it.vars.put("total", moneyFmt.format(rs.getDouble("R_Total")));
                    it.vars.put("etd", etdLabel(rs.getString("R_ETD")));
                    for (int i = 1; i <= 9; i++) {
                        String o = ThaiUtil.ASCII2Unicode(rs.getString("R_Opt" + i));
                        if (o != null && !o.trim().isEmpty()) {
                            it.options.add(o.trim());
                        }
                    }
                    items.add(it);
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(KitchenTemplatePrinter.class, "error", e);
        } finally {
            mysql.closeConnection(KitchenTemplatePrinter.class);
        }
        return items;
    }
}
