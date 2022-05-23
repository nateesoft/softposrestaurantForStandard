package printReport;

import com.softpos.pos.core.controller.BalanceControl;
import com.softpos.pos.core.controller.PublicVar;
import com.softpos.pos.core.controller.SendTerminalReportAuto;
import com.softpos.pos.core.controller.Value;
import com.softpos.pos.core.model.BalanceBean;
import database.MySQLConnect;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import util.AppLogUtil;
import util.DateConvert;
import util.MSG;
import util.OSValidator;

public class PrintDriver {

    private String textAll = "";
    private String textNormal = "";
    private final String header = "<html><head></head><body><table border=0 cellpadding=0 cellspaceing=0 width=100% height=50px>";
    private final String footer = "</table></body></html>";
    private final String fontName = "Angsana New";
    private float width = 75;
    private float height = 72;

    public PrintDriver() {
//        if (OSValidator.isWindows()) {
//            try {
//                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
//            }
//        }
    }

    public void setResolution(float w, float h) {
        this.width = w;
        this.height = h;
    }

    public String getPrinterName() {
        return Value.printerDriverName;
    }

    public void setPrinterName(String printerName) {
        Value.printerDriverName = printerName;
    }

    public void addImage(String path) {
        textAll += "<img src=\"" + path + "\" width=200 height=200></img>";
        textNormal += "...";
    }

    public void addText(String str, String size) {
        textAll += "<font face=" + fontName + " size=" + size + ">" + str + "</font>";
        textNormal += str;
    }

    public void addTextIFont(String str) {
        textAll += "<tr><td " + str + "</td></tr>";
        textNormal += str + "\n";
    }

    public void addTextLn(String str, String size) {
        textAll += "<font face=" + fontName + " size=" + size + ">" + str + "</font><br>";
        textNormal += str + "\n";
    }

    public void addText(String str) {
        String[] datas = str.split("=", str.length());
        for (int i = 0; i <= str.length(); i++) {

        }
        String[] temps = new String[]{"", "", ""};
        System.arraycopy(datas, 0, temps, 0, datas.length);
        textAll += "<tr><td><font face=" + fontName + " size=0>" + str + "</font></td></tr>";
        textNormal += str;
    }

    public void addTextLn(String str) {
        textAll += "<tr><td><font face=" + fontName + " size=-1>" + str + "</font><br></td></tr>";
        textNormal += str + "\n";
    }

    public void printVoid(String R_Table) {
        BalanceControl bControl = new BalanceControl();
        List<BalanceBean> list = bControl.getBalanceIndexVoid(R_Table);
        if (list == null) {
            return;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            BalanceBean bean = (BalanceBean) list.get(i);
            addTextLn("โต๊ะ " + R_Table + "");
            addTextLn("*** ยกเลิกรายการ ***");
            addTextLn("(" + bean.getR_Opt9() + ")");
            addTextLn("UserVoid : " + bean.getCashier());
            addTextLn("");
            String ETD = bean.getR_ETD();
            if (ETD.equals("E")) {
                addTextLn("*** EAT-IN ***");
            } else if (ETD.equals("T")) {
                addTextLn("*** Takeaway ***");
            } else if (ETD.equals("D")) {
                addTextLn("*** Delivery ***");
            } else if (ETD.equals("P")) {
                addTextLn("*** Pinto ***");
            } else if (ETD.equals("W")) {
                addTextLn("*** Wholesale ***");
            }
            addTextLn(bean.getR_PName());
            addTextLn("จำนวน  " + (bean.getR_Quan() * -1) + "  " + "ราคา  " + bean.getR_Price());
            addTextLn("----------------------");

            SimpleDateFormat simp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
            addTextLn(simp.format(new Date()) + " " + bean.getMacno() + "/");

            setPrinterName("KIC" + bean.getR_Kic());//set printer sample KIC1
            printKichen();

            //update r_kicprint
            /**
             * * OPEN CONNECTION **
             */
            MySQLConnect mysql = new MySQLConnect();
            mysql.open();
            try {
                String sql = "update balance "
                        + "set r_kicprint='P' "
                        + "where r_index='" + bean.getR_Index() + "' "
                        + "and r_table='" + bean.getR_Table() + "'";
                try ( Statement stmt = mysql.getConnection().createStatement()) {
                    stmt.executeUpdate(sql);
                }
            } catch (SQLException e) {
                MSG.ERR(null, e.getMessage());
                AppLogUtil.log(PrintDriver.class, "error", e);
            } finally {
                mysql.close();
            }
        }

        close();
    }

    public void printHTML() {
        //Print Cashier
        String text = header + textAll + footer;

        try {
            JEditorPane editor = new JEditorPane();
            editor.setContentType("text/html");
            editor.setText(text);

            HashPrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
            attr.add(new MediaPrintableArea(0f, 0f, width, height, MediaPrintableArea.INCH));

            PrintService printService = getPrinter();
            if (printService != null) {
                editor.print(null, null, false, printService, attr, false);
            }
        } catch (PrinterException ex) {
            MSG.ERR("printHTML=>PrinterException: " + ex.getMessage());
        }
        close();
    }

    public void printHTMLIntoFile() throws FileNotFoundException, UnsupportedEncodingException {
        //Print Cashier
        String header1 = "<html><head><meta charset=\"UTF-8\"></head><body><table align='center' border=0 cellpadding=0 cellspaceing=0 width=180 height=50>";
        String footer1 = "</table></body></html>";
        String text = header1 + textAll + footer1;
        BufferedWriter output = null;
        DateConvert dc = new DateConvert();
        try {
            SendTerminalReportAuto tm = new SendTerminalReportAuto();
            String filename = dc.dateGetToShow(dc.GetCurrentDate()).replace("/", "").replace(" ", "");
            String path = "D:/DailySales/" + filename + "MTD.html";
            PublicVar.filePath = path;
            File file = new File(path);
            output = new BufferedWriter(new FileWriter(file));
            output.write(text);

        } catch (IOException e) {

        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    public void printHTMLKitChen() {
        //Print Cashier
        String text = header + textAll + footer;
        try {
            JEditorPane editor = new JEditorPane();
            editor.setContentType("text/html");
            editor.setText(text);

            HashPrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
            attr.add(new MediaPrintableArea(0f, 0f, width, height, MediaPrintableArea.INCH));

            PrintService printService = getPrinterKitchen();
            if (printService != null) {
                editor.print(null, null, false, printService, attr, false);
            } else {
                AppLogUtil.htmlFile(text);
            }
        } catch (PrinterException ex) {
            MSG.ERR("printHTMLKitChen=>PrinterException:" + ex.getMessage());
        }
        close();
    }

    public void printHTMLOrder() {
        //Print Cashier
        String text = header + textAll + footer;

        try {
            JEditorPane editor = new JEditorPane();
            editor.setContentType("text/html");
            editor.setText(text);
            HashPrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
            attr.add(new MediaPrintableArea(0f, 0f, width, height, MediaPrintableArea.INCH));

            PrintService printService = getPrinterKitchen();
            if (printService != null) {
                editor.print(null, null, false, printService, attr, false);
            } else {
                AppLogUtil.htmlFile(text);
            }
        } catch (PrinterException ex) {
             MSG.ERR("printHTMLOrder=>PrinterException:" + ex.getMessage());
        }
        close();
    }

    public void printNormal() {
        try {
            JTextArea textArea = new JTextArea(textNormal);
            textArea.setFont(new Font("Tahoma", Font.PLAIN, 10));

            HashPrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
            attr.add(new MediaPrintableArea(0f, 0f, width, height, MediaPrintableArea.INCH));

            PrintService printService = getPrinter();
            if (printService != null) {
                textArea.print(null, null, false, printService, attr, false);
            }
        } catch (PrinterException ex) {
            MSG.ERR("printNormal=>PrinterException:" + ex.getMessage());
        }

        close();
    }

    public void printKichen() {
        try {
            JTextArea textArea = new JTextArea(textNormal);
            textArea.setFont(new Font("Tahoma", Font.PLAIN, 16));

            HashPrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
            attr.add(new MediaPrintableArea(0f, 0f, width, height, MediaPrintableArea.INCH));
            PrintService printService = getPrinter();
            if (printService != null) {
                textArea.print(null, null, false, printService, attr, false);
            }
        } catch (PrinterException ex) {
            MSG.ERR("printKichen=>PrinterException:" + ex.getMessage());
        }

        close();
    }

    private PrintService getPrinter() {
        PrintService[] printService = PrinterJob.lookupPrintServices();

        for (PrintService printService1 : printService) {
            if (printService1.getName().equals(Value.printerDriverName)) {
                return printService1;
            }
        }

        if (printService.length > 0) {
            return printService[0];
        } else {
            return null;
        }
    }

    private PrintService getPrinterKitchen() {
        PrintService[] printService = PrinterJob.lookupPrintServices();

        for (PrintService printService1 : printService) {
            if (printService1.getName().equals(Value.printerDriverKitChenName)) {
                return printService1;
            }
        }

        if (printService.length > 0) {
            return printService[0];
        }

        return null;
    }

    public void close() {
//        if (OSValidator.isWindows()) {
//            try {
//                UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
//                UIManager.put("OptionPane.messageFont", new javax.swing.plaf.FontUIResource(new java.awt.Font(
//                        "Tahoma", java.awt.Font.PLAIN, 14)));
//            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
//                MSG.ERR(null, e.getMessage());
//            }
//        }
    }

    public Image addTextToImage(BufferedImage bufferImage, String[] text) {
        final int VERTICLE_PADDING_PIXELS = 5;
        final int LEFT_MARGIN_PIXELS = 5;
        FontMetrics fm = bufferImage.createGraphics().getFontMetrics();
        int ww = bufferImage.getWidth();
        int hh = bufferImage.getHeight() + (text.length * (fm.getHeight() + VERTICLE_PADDING_PIXELS));

        for (String s : text) {
            ww = Math.max(ww, fm.stringWidth(s) + LEFT_MARGIN_PIXELS);
        }

        BufferedImage result = new BufferedImage(bufferImage.getHeight(), ww, hh);
        Graphics2D g = result.createGraphics();
        g.drawImage(bufferImage, 0, 0, null);

        for (int x = 0; x < text.length; x++) {
            g.drawString(text[x], LEFT_MARGIN_PIXELS, bufferImage.getHeight() + (x + 1) * VERTICLE_PADDING_PIXELS + x * fm.getHeight());
        }

        return result;
    }

}
