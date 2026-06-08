package com.softpos.printer.control;

import com.softpos.constants.PublicVar;

import java.awt.Font;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.softpos.util.AppLogUtil;
import com.softpos.util.DateConvert;
import com.softpos.util.OSValidator;

public class PrinterDriverControl {

    private String textAll = "";
    private String textNormal = "";
    private final String header = "<html><head></head><body><table border=0 cellpadding=0 cellspaceing=0 width=100% height=50px>";
    private final String footer = "</table></body></html>";
    private final String fontName = "Angsana New";
    private float width = 75;
    private float height = 72;

    public PrinterDriverControl() {
    }

    public void setResolution(float w, float h) {
        this.width = w;
        this.height = h;
    }

    public void addTextIFont(String str) {
        textAll += "<tr><td " + str + "</td></tr>";
        textNormal += str + "\n";
    }

    public void addTextLn(String str) {
        textAll += "<tr><td><font face=" + fontName + " size=-1>" + str + "</font><br></td></tr>";
        textNormal += str + "\n";
    }

    public void printHTML() {
        //Print Cashier
        String text = header + textAll + footer;
        AppLogUtil.info("printHTML: ");
        AppLogUtil.htmlFile(text);

        try {
            JEditorPane editor = new JEditorPane();
            editor.setContentType("text/html");
            editor.setText(text);

            HashPrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
            attr.add(new MediaPrintableArea(0f, 0f, width, height, MediaPrintableArea.INCH));

            PrintService printService = getPrinter();
            if (printService != null) {
                editor.print(null, null, false, printService, attr, false);
            } else {
                AppLogUtil.info("Cannot print printHTML:...>>>  ");
            }
        } catch (PrinterException e) {
            AppLogUtil.log(PrinterDriverControl.class, "error", e);
        }

        close();
    }

    public void printHTMLIntoFile() throws FileNotFoundException, UnsupportedEncodingException {
        //Print Cashier
        String header1 = "<html><head><meta charset=\"UTF-8\"></head><body><table align='center' border=0 cellpadding=0 cellspaceing=0 width=180 height=50>";
        String footer1 = "</table></body></html>";

        String barcode = " <div style='padding-top:8px; text-align:center; font-size:15px; font-family: Source Sans Pro, Arial, sans-serif;'>\n"
                + "            <!-- back-linking to www.tec-it.com is required -->\n"
                + "            <a href='https://www.tec-it.com' title='Barcode Software by TEC-IT' target='_blank'>\n"
                + "                TEC-IT Barcode Generator<br/>\n"
                + "                <!-- logos are optional -->\n"
                + "                <img alt='TEC-IT Barcode Software' border='0'\n"
                + "                     src='http://www.tec-it.com/pics/banner/web/TEC-IT_Logo_75x75.gif'>\n"
                + "            </a>\n"
                + "        </div>";

        String text = header1 + textAll + barcode + footer1;
        BufferedWriter output = null;
        DateConvert dc = new DateConvert();
        try {
            String filename = dc.dateGetToShow(dc.GetCurrentDate()).replace("/", "").replace(" ", "");
            String path = "D:/DailySales/" + filename + "MTD.html";
            PublicVar.filePath = path;
            File file = new File(path);
            output = new BufferedWriter(new FileWriter(file));
            output.write(text);

        } catch (IOException e) {
            AppLogUtil.log(PrinterDriverControl.class, "error", e);
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    public void printHTMLKitChen(String printerName) {
        //Print Cashier
        String text = header + textAll + footer;
        AppLogUtil.info("printHTMLKitChen: " + printerName);
        AppLogUtil.htmlFile(text);
        try {
            JEditorPane editor = new JEditorPane();
            editor.setContentType("text/html");
            editor.setText(text);

            HashPrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
            attr.add(new MediaPrintableArea(0f, 0f, width, height, MediaPrintableArea.INCH));

            PrintService printService = getPrinterKitchen();
            if (printService != null) {
                AppLogUtil.info("Process Print kic No.:...>>>  " + printerName);
                editor.print(null, null, false, printService, attr, false);
            } else {
                AppLogUtil.info("Cannot print printHTMLKitChen:...>>>  ");
            }
        } catch (PrinterException ex) {
            AppLogUtil.log(PrinterDriverControl.class, "error", ex);
        }

        close();
    }

    public void printHTMLKitChenByKictran(String printerName) {
        //Print Cashier
        String text = header + textAll + footer;
        AppLogUtil.info("printHTMLKitChenByKictran: ");
        AppLogUtil.htmlFile(text);
        try {
            JEditorPane editor = new JEditorPane();
            editor.setContentType("text/html");
            editor.setText(text);

            HashPrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
            attr.add(new MediaPrintableArea(0f, 0f, width, height, MediaPrintableArea.INCH));

            PrintService printService = getPrinterKitchenKictran(printerName);
            if (printService != null) {
                editor.print(null, null, false, printService, attr, false);
            } else {
                AppLogUtil.info("Cannot print printHTMLKitChenByKictran:...>>>  " + printerName);
            }
        } catch (PrinterException e) {
            AppLogUtil.log(PrinterDriverControl.class, "error", e);
        }

        close();
    }

    public void printNormal() {
        AppLogUtil.info("printNormal: ");
        AppLogUtil.htmlFile(textNormal);
        try {
            JTextArea textArea = new JTextArea(textNormal);
            textArea.setFont(new Font("Tahoma", Font.PLAIN, 10));

            HashPrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
            attr.add(new MediaPrintableArea(0f, 0f, width, height, MediaPrintableArea.INCH));

            PrintService printService = getPrinter();
            if (printService != null) {
                textArea.print(null, null, false, printService, attr, false);
            } else {
                AppLogUtil.info("Cannot print printNormal:...>>>  ");
            }
        } catch (PrinterException e) {
            AppLogUtil.log(PrinterDriverControl.class, "error", e);
        }

        close();
    }

    public void printKichen() {
        AppLogUtil.info("printKichen: ");
        AppLogUtil.htmlFile(textNormal);

        try {
            JTextArea textArea = new JTextArea(textNormal);
            textArea.setFont(new Font("Tahoma", Font.PLAIN, 16));

            HashPrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
            attr.add(new MediaPrintableArea(0f, 0f, width, height, MediaPrintableArea.INCH));
            PrintService printService = getPrinter();
            if (printService != null) {
                textArea.print(null, null, false, printService, attr, false);
            } else {
                AppLogUtil.info("Cannot print printKichen:...>>>  ");
            }
        } catch (PrinterException e) {
            AppLogUtil.log(PrinterDriverControl.class, "error", e);
        }

        close();
    }

    private PrintService getPrinter() {
        PrintService[] printService = PrinterJob.lookupPrintServices();

        for (PrintService printService1 : printService) {
            if (PublicVar.printerCheckBill == true) {
                if (printService1.getName().equals(PublicVar.printerCheckBillName)) {
                    PublicVar.printerCheckBill = false;
                    return printService1;
                }
            } else {
                if (printService1.getName().equals(PublicVar.printerDriverName)) {
                    return printService1;
                }
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
            if (printService1.getName().equals(PublicVar.printerDriverKitChenName)) {
                return printService1;
            }
        }

        if (printService.length > 0) {
            return printService[0];
        }

        return null;
    }

    private PrintService getPrinterKitchenKictran(String printer) {
        PrintService[] printService = PrinterJob.lookupPrintServices();
        for (PrintService printService1 : printService) {
            if (printService1.getName().equals(printer)) {
                return printService1;
            }
        }

        if (printService.length > 0) {
            return printService[0];
        }

        return null;
    }

    public void close() {
        SwingUtilities.invokeLater(() -> {
            if (OSValidator.isWindows()) {
                UIManager.put("OptionPane.messageFont", new javax.swing.plaf.FontUIResource(new java.awt.Font(
                        "Tahoma", java.awt.Font.PLAIN, 14)));
            }
        });
    }
}
