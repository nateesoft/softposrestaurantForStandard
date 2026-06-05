package com.softpos.report.driver;

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

public class PrintDriver {

    private String textAll = "";
    private String textNormal = "";
    private final String header = "<html><head></head><body><table border=0 cellpadding=0 cellspaceing=0 width=100% height=50px>";
    private final String footer = "</table></body></html>";
    private final String fontName = "Angsana New";
    private float width = 75;
    private float height = 72;

    // Cache result of lookupPrintServices() — the call is expensive (OS-level scan).
    // Refreshed every 30 s; stale enough to be cheap, fresh enough to catch printer changes.
    private static volatile PrintService[] serviceCache = null;
    private static volatile long serviceCacheTime = 0;
    private static final long SERVICE_CACHE_TTL = 30_000L;

    public PrintDriver() {
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

    // -------------------------------------------------------------------------
    // Public print methods — all return immediately; actual printing is queued.
    // PrintService is resolved here (on the calling thread) so that side-effects
    // in getPrinter() (e.g. clearing the printerCheckBill flag) happen synchronously.
    // -------------------------------------------------------------------------

    public void printHTML() {
        String capturedText = header + textAll + footer;
        AppLogUtil.info("printHTML: ");
        AppLogUtil.htmlFile(capturedText);

        PrintService service = getPrinter();
        String queueKey = (service != null) ? service.getName() : "cashier";
        float w = this.width, h = this.height;

        PrintQueueManager.getInstance().submitPrint(queueKey, () -> {
            try {
                JEditorPane editor = new JEditorPane();
                editor.setContentType("text/html");
                editor.setText(capturedText);
                HashPrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
                attr.add(new MediaPrintableArea(0f, 0f, w, h, MediaPrintableArea.INCH));
                if (service != null) {
                    editor.print(null, null, false, service, attr, false);
                } else {
                    AppLogUtil.info("Cannot print printHTML:...>>>  ");
                }
            } catch (PrinterException e) {
                AppLogUtil.log(PrintDriver.class, "error", e);
            } finally {
                close();
            }
        });
    }

    public void printHTMLIntoFile() throws FileNotFoundException, UnsupportedEncodingException {
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
            AppLogUtil.log(PrintDriver.class, "error", e);
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
        String capturedText = header + textAll + footer;
        AppLogUtil.info("printHTMLKitChen: " + printerName);
        AppLogUtil.htmlFile(capturedText);

        PrintService service = getPrinterKitchen();
        float w = this.width, h = this.height;

        PrintQueueManager.getInstance().submitPrint(printerName, () -> {
            try {
                JEditorPane editor = new JEditorPane();
                editor.setContentType("text/html");
                editor.setText(capturedText);
                HashPrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
                attr.add(new MediaPrintableArea(0f, 0f, w, h, MediaPrintableArea.INCH));
                if (service != null) {
                    AppLogUtil.info("Process Print kic No.:...>>>  " + printerName);
                    editor.print(null, null, false, service, attr, false);
                } else {
                    AppLogUtil.info("Cannot print printHTMLKitChen:...>>>  ");
                }
            } catch (PrinterException ex) {
                AppLogUtil.log(PrintDriver.class, "error", ex);
            } finally {
                close();
            }
        });
    }

    public void printHTMLKitChenByKictran(String printerName) {
        String capturedText = header + textAll + footer;
        AppLogUtil.info("printHTMLKitChenByKictran: ");
        AppLogUtil.htmlFile(capturedText);

        PrintService service = getPrinterKitchenKictran(printerName);
        float w = this.width, h = this.height;

        PrintQueueManager.getInstance().submitPrint(printerName, () -> {
            try {
                JEditorPane editor = new JEditorPane();
                editor.setContentType("text/html");
                editor.setText(capturedText);
                HashPrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
                attr.add(new MediaPrintableArea(0f, 0f, w, h, MediaPrintableArea.INCH));
                if (service != null) {
                    editor.print(null, null, false, service, attr, false);
                } else {
                    AppLogUtil.info("Cannot print printHTMLKitChenByKictran:...>>>  " + printerName);
                }
            } catch (PrinterException e) {
                AppLogUtil.log(PrintDriver.class, "error", e);
            } finally {
                close();
            }
        });
    }

    public void printNormal() {
        String capturedText = textNormal;
        AppLogUtil.info("printNormal: ");
        AppLogUtil.htmlFile(capturedText);

        PrintService service = getPrinter();
        String queueKey = (service != null) ? service.getName() : "cashier";
        float w = this.width, h = this.height;

        PrintQueueManager.getInstance().submitPrint(queueKey, () -> {
            try {
                JTextArea textArea = new JTextArea(capturedText);
                textArea.setFont(new Font("Tahoma", Font.PLAIN, 10));
                HashPrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
                attr.add(new MediaPrintableArea(0f, 0f, w, h, MediaPrintableArea.INCH));
                if (service != null) {
                    textArea.print(null, null, false, service, attr, false);
                } else {
                    AppLogUtil.info("Cannot print printNormal:...>>>  ");
                }
            } catch (PrinterException e) {
                AppLogUtil.log(PrintDriver.class, "error", e);
            } finally {
                close();
            }
        });
    }

    public void printKichen() {
        String capturedText = textNormal;
        AppLogUtil.info("printKichen: ");
        AppLogUtil.htmlFile(capturedText);

        PrintService service = getPrinter();
        String queueKey = (service != null) ? service.getName() : "cashier";
        float w = this.width, h = this.height;

        PrintQueueManager.getInstance().submitPrint(queueKey, () -> {
            try {
                JTextArea textArea = new JTextArea(capturedText);
                textArea.setFont(new Font("Tahoma", Font.PLAIN, 16));
                HashPrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
                attr.add(new MediaPrintableArea(0f, 0f, w, h, MediaPrintableArea.INCH));
                if (service != null) {
                    textArea.print(null, null, false, service, attr, false);
                } else {
                    AppLogUtil.info("Cannot print printKichen:...>>>  ");
                }
            } catch (PrinterException e) {
                AppLogUtil.log(PrintDriver.class, "error", e);
            } finally {
                close();
            }
        });
    }

    // -------------------------------------------------------------------------
    // PrintService lookup — uses a 30-second cache to avoid repeated OS scans.
    // getPrinter() is intentionally NOT moved to async: it reads+clears the
    // printerCheckBill flag and must run on the caller's thread.
    // -------------------------------------------------------------------------

    private PrintService getPrinter() {
        PrintService[] services = lookupServices();
        for (PrintService ps : services) {
            if (PublicVar.printerCheckBill) {
                if (ps.getName().equals(PublicVar.printerCheckBillName)) {
                    PublicVar.printerCheckBill = false;
                    return ps;
                }
            } else {
                if (ps.getName().equals(PublicVar.printerDriverName)) {
                    return ps;
                }
            }
        }
        return services.length > 0 ? services[0] : null;
    }

    private PrintService getPrinterKitchen() {
        PrintService[] services = lookupServices();
        for (PrintService ps : services) {
            if (ps.getName().equals(PublicVar.printerDriverKitChenName)) {
                return ps;
            }
        }
        return services.length > 0 ? services[0] : null;
    }

    private PrintService getPrinterKitchenKictran(String printer) {
        PrintService[] services = lookupServices();
        for (PrintService ps : services) {
            if (ps.getName().equals(printer)) {
                return ps;
            }
        }
        return services.length > 0 ? services[0] : null;
    }

    private static PrintService[] lookupServices() {
        long now = System.currentTimeMillis();
        if (serviceCache == null || (now - serviceCacheTime) > SERVICE_CACHE_TTL) {
            synchronized (PrintDriver.class) {
                if (serviceCache == null || (now - serviceCacheTime) > SERVICE_CACHE_TTL) {
                    serviceCache = PrinterJob.lookupPrintServices();
                    serviceCacheTime = now;
                }
            }
        }
        return serviceCache;
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
