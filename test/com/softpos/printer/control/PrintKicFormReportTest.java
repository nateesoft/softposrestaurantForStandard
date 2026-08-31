package com.softpos.printer.control;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * ตรวจสอบว่า PrintKicFormReport สามารถ "connect" ไปยัง jasper report
 * (โหลด .jrxml จาก classpath แล้ว compile เป็น JasperReport ได้จริง)
 * โดยไม่ต้องพึ่ง DB หรือเครื่องพิมพ์จริง
 */
public class PrintKicFormReportTest {

    @Test
    public void kicForm7Jrxml_isReachableOnClasspath() {
        InputStream in = PrintKicFormReport.class.getResourceAsStream("/report/file/kicFrom_7Qrcode.jrxml");

        assertNotNull("ไม่พบไฟล์ kicFrom_7Qrcode.jrxml บน classpath (/report/file/kicFrom_7Qrcode.jrxml)", in);
    }

    @Test
    public void kicForm7Jrxml_compilesToValidJasperReport() throws Exception {
        JasperReport report = JasperCompileManager.compileReport(
                PrintKicFormReport.class.getResourceAsStream("/report/file/kicFrom_7Qrcode.jrxml"));

        assertNotNull(report);
        assertParametersInclude(report, "tableNo", "printerName", "etd", "etdThai", "R_PluCode", "custTomer");
    }

    @Test
    public void kicForm8Jrxml_isReachableOnClasspath() {
        InputStream in = PrintKicFormReport.class.getResourceAsStream("/report/file/kicFrom_8Qrcode.jrxml");

        assertNotNull("ไม่พบไฟล์ kicFrom_8Qrcode.jrxml บน classpath (/report/file/kicFrom_8Qrcode.jrxml)", in);
    }

    @Test
    public void kicForm8Jrxml_compilesToValidJasperReport() throws Exception {
        JasperReport report = JasperCompileManager.compileReport(
                PrintKicFormReport.class.getResourceAsStream("/report/file/kicFrom_8Qrcode.jrxml"));

        assertNotNull(report);
        assertParametersInclude(report, "tableNo", "printerName", "etd", "etdThai", "R_Index", "custTomer");
    }

    private void assertParametersInclude(JasperReport report, String... expectedNames) {
        Set<String> actualNames = new HashSet<>();
        for (JRParameter parameter : report.getParameters()) {
            actualNames.add(parameter.getName());
        }
        for (String expected : expectedNames) {
            assertTrue("report ควรมี parameter '" + expected + "'", actualNames.contains(expected));
        }
    }
}
