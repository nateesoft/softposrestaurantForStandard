package com.softpos.printer.control;

/**
 *
 * @author User
 */
import com.softpos.pos.core.controller.AppContext;
import com.softpos.pos.core.controller.ControlPrintCheckBill;
import com.softpos.pos.core.controller.TableFileControl;
import com.softpos.util.ThaiUtil;
import com.softpos.pos.core.model.TableFileBean;
import com.softpos.util.AppLogUtil;
import com.softpos.connection.database.MySQLConnect;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.text.DecimalFormat;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterName;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import java.awt.print.PrinterException;
import net.sf.jasperreports.engine.JRException;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrintKicFormReport {
    private final MySQLConnect mysqlConnect;
    private final DecimalFormat intFM = new DecimalFormat("#,##0");

    public PrintKicFormReport() {
        this.mysqlConnect = new MySQLConnect();
    }

    public void PrintKicForm7_Report(
            final String tableNo, final String printerName, final String Macno, final String R_ETD, final String R_PluCode) throws Exception {

        TableFileControl tbControl = AppContext.getTableFileControl();
        TableFileBean tbBean = tbControl.getDataByTCode(tableNo);

        ControlPrintCheckBill ctPrint = new ControlPrintCheckBill();
        
        mysqlConnect.open(PrintKicFormReport.class);
        
        try {

            // source file
            JasperReport reportSource = JasperCompileManager.compileReport(
                    getClass().getResourceAsStream("/report/file/kicFrom_7Qrcode.jrxml"));

            // set parameters
            Map param = new HashMap();
            //Header
            param.put("tableNo", tbBean.getTcode());
            param.put("printerName", printerName);
            param.put("etd", R_ETD);
            String etdThai = R_ETD;
            switch (etdThai) {
                case "E": {
                    etdThai = "ทานในร้าน";
                    param.put("etdThai", etdThai);
                    break;
                }
                case "T": {
                    etdThai = "ใส่ห่อ";
                    param.put("etdThai", etdThai);
                    break;
                }
                case "D": {
                    etdThai = "เดลิเวอรี่";
                    param.put("etdThai", etdThai);
                    break;
                }
                case "P": {
                    etdThai = "ปิ่นโต";
                    param.put("etdThai", etdThai);
                    break;
                }
                case "W": {
                    etdThai = "ค้าส่ง";
                    param.put("etdThai", etdThai);
                    break;
                }

            }

            //Footer
            param.put("R_PluCode", ThaiUtil.ASCII2Unicode(R_PluCode));
            param.put("custTomer", ThaiUtil.ASCII2Unicode(intFM.format(tbBean.getTCustomer())));

            JasperPrint jasperPrint = JasperFillManager.fillReport(reportSource, param, mysqlConnect.getConnection());
            PrinterJob printerJob = PrinterJob.getPrinterJob();

            PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();
            printerJob.defaultPage(pageFormat);

            PrintService selectedPrintService = find2DPrintService(printerName);
            if (selectedPrintService == null) {
                AppLogUtil.error(PrintKicFormReport.class, "Printer '" + printerName
                        + "' is not available as a 2D print service; skipping KIC form 7 print.");
                return;
            }

            try {
                printerJob.setPrintService(selectedPrintService);
            } catch (PrinterException e) {
                AppLogUtil.log(PrintKicFormReport.class, "error", e);
            }
            JRPrintServiceExporter exporter;
            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
            printRequestAttributeSet.add(MediaSizeName.NA_LETTER);
            printRequestAttributeSet.add(new Copies(1));

            // these are deprecated
            exporter = new JRPrintServiceExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, selectedPrintService);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, selectedPrintService.getAttributes());
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
            exporter.exportReport();

            param.clear();
            ctPrint.setPrintCheckBillItemAfterSendKic(tableNo);
        } catch (JRException ex) {
            Logger.getLogger(PrintKicFormReport.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mysqlConnect.close();
        }
    }

    public void PrintKicForm8_Report(
            final String tableNo, final String printerName, final String Macno, final String R_ETD, final String R_Index) throws Exception {

        TableFileControl tbControl = AppContext.getTableFileControl();
        TableFileBean tbBean = tbControl.getDataByTCode(tableNo);

        ControlPrintCheckBill ctPrint = new ControlPrintCheckBill();
        mysqlConnect.open(PrintKicFormReport.class);
        
        try {
            JasperReport reportSource = JasperCompileManager.compileReport(
                    getClass().getResourceAsStream("/report/file/kicFrom_8Qrcode.jrxml"));

            // set parameters
            Map param = new HashMap();
            //Header
            param.put("tableNo", tbBean.getTcode());
            param.put("printerName", printerName);
            param.put("etd", R_ETD);
            String etdThai = R_ETD;
            switch (etdThai) {
                case "E": {
                    etdThai = "ทานในร้าน";
                    param.put("etdThai", etdThai);
                    break;
                }
                case "T": {
                    etdThai = "ใส่ห่อ";
                    param.put("etdThai", etdThai);
                    break;
                }
                case "D": {
                    etdThai = "เดลิเวอรี่";
                    param.put("etdThai", etdThai);
                    break;
                }
                case "P": {
                    etdThai = "ปิ่นโต";
                    param.put("etdThai", etdThai);
                    break;
                }
                case "W": {
                    etdThai = "ค้าส่ง";
                    param.put("etdThai", etdThai);
                    break;
                }

            }

            //Footer
            param.put("R_Index", ThaiUtil.ASCII2Unicode(R_Index));
            param.put("custTomer", ThaiUtil.ASCII2Unicode(intFM.format(tbBean.getTCustomer())));
            // QR code แทน barcode เดิม (Code128) — ส่งเป็นรูปภาพให้ report
            param.put("qrImage", qrImage(R_Index, 240));

            JasperPrint jasperPrint = JasperFillManager.fillReport(reportSource, param, mysqlConnect.getConnection());

            // save เป็น PDF ไว้ตรวจสอบ output กรณียังทดสอบผ่านเครื่องพิมพ์จริงไม่ได้
            File pdfDir = new File("kic_pdf");
            if (!pdfDir.exists()) {
                pdfDir.mkdirs();
            }
            String pdfFileName = new File(pdfDir, "kicForm8_" + tableNo + "_"
                    + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".pdf").getPath();
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFileName);

            //Class Printer Job
            PrinterJob printerJob = PrinterJob.getPrinterJob();

            PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();
            printerJob.defaultPage(pageFormat);

            PrintService selectedPrintService = find2DPrintService(printerName);
            if (selectedPrintService == null) {
                AppLogUtil.error(PrintKicFormReport.class, "Printer '" + printerName
                        + "' is not available as a 2D print service; skipping KIC form 8 print.");
                return;
            }

            try {
                printerJob.setPrintService(selectedPrintService);
            } catch (PrinterException e) {
                AppLogUtil.log(PrintKicFormReport.class, "error", e);
            }
            JRPrintServiceExporter exporter;
            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
            printRequestAttributeSet.add(MediaSizeName.NA_LETTER);
            printRequestAttributeSet.add(new Copies(1));

            // these are deprecated
            exporter = new JRPrintServiceExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, selectedPrintService);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, selectedPrintService.getAttributes());
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
            exporter.exportReport();

            param.clear();

            ctPrint.setPrintCheckBillItemAfterSendKic(tableNo);

        } catch (JRException e) {
            AppLogUtil.log(PrintKicFormReport.class, "error", e);
        } finally {
            mysqlConnect.close();
        }
    }

    /**
     * Find a print service by name that actually supports Java 2D printing.
     *
     * <p>A CUPS/IPP queue added as a "raw" printer only advertises byte-stream
     * doc flavors, so {@code PrinterJob.setPrintService(...)} rejects it with
     * "Not a 2D print service: IPP Printer : ...". We therefore look up only
     * services that support {@code DocFlavor.SERVICE_FORMATTED.PAGEABLE}, and
     * fall back to {@code PrinterJob.lookupPrintServices()} (which only ever
     * returns 2D-capable services) matching by name.
     *
     * @return a 2D-capable {@link PrintService}, or {@code null} if none matches.
     */
    private static PrintService find2DPrintService(final String printerName) {
        AttributeSet nameAttr = new HashPrintServiceAttributeSet(new PrinterName(printerName, null));

        PrintService[] matches = PrintServiceLookup.lookupPrintServices(
                DocFlavor.SERVICE_FORMATTED.PAGEABLE, nameAttr);
        if (matches != null && matches.length > 0) {
            return matches[0];
        }

        for (PrintService ps : PrinterJob.lookupPrintServices()) {
            if (ps.getName() != null && ps.getName().equalsIgnoreCase(printerName)) {
                return ps;
            }
        }
        return null;
    }

    /**
     * Render {@code text} as a square QR-code image (black modules on white),
     * used in place of the old Code128 barcode on KIC form 8.
     *
     * @param sizePx requested pixel size of the (square) image.
     * @return the QR image, or {@code null} if encoding failed (the report shows
     *         a blank in that case instead of crashing).
     */
    private static BufferedImage qrImage(final String text, final int sizePx) {
        try {
            Map<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 1);

            BitMatrix matrix = new MultiFormatWriter().encode(
                    text == null ? "" : text, BarcodeFormat.QR_CODE, sizePx, sizePx, hints);

            int w = matrix.getWidth();
            int h = matrix.getHeight();
            BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            int black = Color.BLACK.getRGB();
            int white = Color.WHITE.getRGB();
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    img.setRGB(x, y, matrix.get(x, y) ? black : white);
                }
            }
            return img;
        } catch (Exception e) {
            AppLogUtil.log(PrintKicFormReport.class, "error", e);
            return null;
        }
    }

}
