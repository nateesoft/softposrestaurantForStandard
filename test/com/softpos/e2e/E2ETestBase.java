package com.softpos.e2e;

import com.softpos.e2e.model.TestCase;
import com.softpos.e2e.model.TestStep;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.awt.AWTException;
import java.io.File;
import java.util.List;

/**
 * Base class for all E2E tests.
 * Sets up logging, popup detection, screenshot capture, and Excel/HTML output.
 */
public abstract class E2ETestBase {

    protected static final String EXCEL_PATH = "test-reports/e2e-testcases.xls";

    protected static TestStepLogger    logger;
    protected static SwingTestDriver   driver;
    protected static PopupDetector     popupDetector;
    protected static ScreenCaptureUtil screenCapture;

    @BeforeClass
    public static void setUpFramework() throws AWTException {
        logger        = TestStepLogger.getInstance();
        driver        = new SwingTestDriver();
        popupDetector = new PopupDetector();
        screenCapture = new ScreenCaptureUtil();

        new File("test-reports").mkdirs();
        new File("test-reports/screenshots").mkdirs();

        // Wire TestEventBus → logger so production-code events become test steps
        com.softpos.e2e.TestEventBus.register((event, detail) ->
            System.out.printf("[E2E EVENT] %-25s  %s%n", event, detail)
        );
    }

    @AfterClass
    public static void tearDownFramework() {
        com.softpos.e2e.TestEventBus.unregister();
        writeResults();
    }

    @Before
    public void startPopupDetection() {
        popupDetector.start();
    }

    @After
    public void stopPopupDetection() {
        popupDetector.stop();
    }

    // ── convenience helpers for subclasses ───────────────────────────────────

    protected TestStep step(String screen, String action, String input, String expected) {
        return logger.logStep(screen, action, input, expected);
    }

    protected void pass(String actual) {
        logger.passLastStep(actual);
    }

    protected void fail(String actual, String error) {
        logger.failLastStep(actual, error);
        String shot = screenCapture.capture("FAIL_" + System.currentTimeMillis());
        logger.attachScreenshot(shot);
    }

    protected void screenshot(String label) {
        String path = screenCapture.capture(label);
        logger.attachScreenshot(path);
    }

    /** Pause the test thread (not the EDT). */
    protected void sleep(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    // ── post-run output ───────────────────────────────────────────────────────

    private static void writeResults() {
        List<TestCase> all = logger.getAllCases();
        if (all.isEmpty()) return;

        // Write Excel
        try {
            ExcelTestCaseManager excel = new ExcelTestCaseManager();
            excel.writeTestCases(EXCEL_PATH, all);
            System.out.println("[E2E] Excel written: " + new File(EXCEL_PATH).getAbsolutePath());
        } catch (Exception e) {
            System.err.println("[E2E] Failed to write Excel: " + e.getMessage());
        }

        // Write HTML
        try {
            HtmlReportGenerator html = new HtmlReportGenerator();
            String path = html.generate(all);
            System.out.println("[E2E] HTML report: " + path);
        } catch (Exception e) {
            System.err.println("[E2E] Failed to write HTML: " + e.getMessage());
        }

        // Print summary to console
        long passed = all.stream().filter(tc -> "PASS".equals(tc.getOverallStatus())).count();
        long failed = all.stream().filter(tc -> "FAIL".equals(tc.getOverallStatus())).count();
        System.out.println("=================================================");
        System.out.printf(" E2E SUMMARY  Total: %d  PASS: %d  FAIL: %d%n",
                all.size(), passed, failed);
        System.out.println("=================================================");
    }
}
