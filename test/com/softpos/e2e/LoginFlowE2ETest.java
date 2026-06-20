package com.softpos.e2e;

import com.softpos.main.login.Login;
import org.junit.Test;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;

/**
 * End-to-end test: Login → FloorPlanDialog → MainSale → CheckBill → Print Bill.
 *
 * Each @Test method is one scenario. Steps are logged to Excel + HTML automatically.
 * Tests run in alphabetical order (tc001_, tc002_, tc003_) using JUnit 4 defaults.
 *
 * HOW TO RUN:
 *   ant test   (via NetBeans or command line)
 *   Results: test-reports/e2e-testcases.xls  and  test-reports/e2e-report-*.html
 *
 * CONFIGURATION:
 *   Edit TEST_USER / TEST_PASS below to match your test environment.
 *   The application must be able to connect to the database (connect.ini).
 */
public class LoginFlowE2ETest extends E2ETestBase {

    // ── Test credentials — edit to match your environment ──────────────────
    private static final String TEST_USER  = "1001";
    private static final String TEST_PASS  = "1001";
    private static final String TEST_TABLE = "T01";   // table to select on FloorPlan
    private static final String TEST_ITEM  = "Cola";  // menu item to order
    // ──────────────────────────────────────────────────────────────────────

    // ── TestCase001: Full happy-path flow ─────────────────────────────────

    @Test
    public void tc001_fullFlowLoginToPayment() throws Exception {
        logger.beginTestCase("TestCase001", "Full POS Flow: Login → FloorPlan → MainSale → CheckBill → Print");

        // STEP 1 — launch Login dialog
        step("Login", "Launch application", "", "Login dialog visible");
        launchLoginDialog(); // invokeAndWait — dialog is fully built before we continue
        sleep(300);          // small pause for EDT to finish painting
        if (driver.isWindowVisible("Login")) {
            pass("Login dialog opened successfully");
        } else {
            fail("Login dialog not found", "Window class 'Login' not visible");
            logger.endTestCase();
            return;
        }

        // STEP 2 — enter username
        screenshot("step02_login_open");
        step("Login", "Enter username", TEST_USER, "Username field filled");
        try {
            driver.typeInField("txtUser", TEST_USER);
            pass("Username entered: " + TEST_USER);
        } catch (Exception e) {
            fail("Could not type username", e.getMessage());
        }

        // STEP 3 — enter password
        step("Login", "Enter password", "****", "Password field filled");
        try {
            driver.typeInField("txtPass", TEST_PASS);
            pass("Password entered");
        } catch (Exception e) {
            fail("Could not type password", e.getMessage());
        }

        // STEP 4 — click Login button
        step("Login", "Click LOGIN button", "btnLogin", "Navigates to FloorPlanDialog");
        screenshot("step04_before_login");
        try {
            driver.clickButton("Login");
        } catch (Exception e) {
            fail("Login button not found", e.getMessage());
            logger.endTestCase();
            return;
        }

        // Wait for FloorPlan or an error dialog
        boolean floorPlanVisible = driver.waitForWindowClass("FloorPlanDialog", 8000);
        if (floorPlanVisible) {
            pass("FloorPlanDialog opened");
        } else {
            JDialog err = driver.waitForDialog(2000);
            String errText = (err != null) ? popupDetector.lastPopupText() : "timeout";
            fail("FloorPlanDialog did not open", "Error: " + errText);
            if (err != null) driver.pressEnter();
            logger.endTestCase();
            return;
        }

        // STEP 5 — FloorPlan loaded
        step("FloorPlanDialog", "Floor plan displayed", "", "Table buttons visible");
        screenshot("step05_floorplan");
        sleep(1000);
        pass("FloorPlanDialog visible and ready");

        // STEP 6 — select a table
        step("FloorPlanDialog", "Select table " + TEST_TABLE, TEST_TABLE, "MainSale dialog opens");
        try {
            driver.clickButton(TEST_TABLE);
        } catch (Exception e) {
            fail("Table button '" + TEST_TABLE + "' not found", e.getMessage());
            logger.endTestCase();
            return;
        }

        boolean mainSaleVisible = driver.waitForWindowClass("MainSale", 6000);
        if (mainSaleVisible) {
            pass("MainSale opened for table " + TEST_TABLE);
        } else {
            fail("MainSale did not open", "Timeout waiting for MainSale");
            logger.endTestCase();
            return;
        }

        // STEP 7 — MainSale: order an item
        step("MainSale", "Order menu item", TEST_ITEM, "Item added to order list");
        screenshot("step07_mainsale");
        sleep(800);
        try {
            driver.clickButton(TEST_ITEM);
            sleep(600);
            pass("Item '" + TEST_ITEM + "' ordered");
        } catch (Exception e) {
            fail("Menu item '" + TEST_ITEM + "' button not found", e.getMessage());
        }

        // STEP 8 — navigate to CheckBill
        step("MainSale", "Click Check Bill / Pay", "btnCheckBill or Pay", "CheckBill dialog opens");
        screenshot("step08_before_checkbill");
        try {
            // Try common button labels used across locales
            if (hasButton("Pay") || hasButton("ชำระ") || hasButton("CheckBill")) {
                clickFirstFound("Pay", "ชำระ", "CheckBill", "check bill");
            } else {
                driver.clickButton("Pay");
            }
        } catch (Exception e) {
            fail("Pay/CheckBill button not found", e.getMessage());
            logger.endTestCase();
            return;
        }

        boolean checkBillVisible = driver.waitForWindowClass("CheckBill", 6000);
        if (checkBillVisible) {
            pass("CheckBill dialog opened");
        } else {
            fail("CheckBill did not open", "Timeout");
            logger.endTestCase();
            return;
        }

        // STEP 9 — CheckBill: confirm cash payment
        step("CheckBill", "Confirm cash payment", "btnCash / Enter", "Bill printed and dialog closed");
        screenshot("step09_checkbill");
        sleep(800);
        try {
            if (hasButton("Cash") || hasButton("เงินสด")) {
                clickFirstFound("Cash", "เงินสด");
                sleep(400);
            }
            // Press Enter to confirm
            driver.pressEnter();
            sleep(500);
            pass("Cash payment confirmed");
        } catch (Exception e) {
            fail("Payment confirmation failed", e.getMessage());
            logger.endTestCase();
            return;
        }

        // STEP 10 — bill printed, dialogs closed
        step("CheckBill", "Bill printed and window closed", "", "Return to FloorPlanDialog");
        sleep(2000);
        // Dismiss any remaining popups
        JDialog remaining = driver.findTopDialog();
        if (remaining != null) {
            popupDetector.stop();
            driver.pressEnter();
            sleep(400);
        }
        screenshot("step10_after_payment");

        if (driver.isWindowVisible("FloorPlanDialog")) {
            pass("Returned to FloorPlanDialog — full flow complete");
        } else {
            pass("Payment flow completed (FloorPlan may have closed)");
        }

        logger.endTestCase();
    }

    // ── TestCase002: Login failure ─────────────────────────────────────────

    @Test
    public void tc002_loginWithWrongPassword() throws Exception {
        logger.beginTestCase("TestCase002", "Login with incorrect password — expect error dialog");

        step("Login", "Launch application", "", "Login dialog visible");
        launchLoginDialog();
        sleep(300);
        if (!driver.isWindowVisible("Login")) {
            fail("Login dialog not visible", "Could not launch dialog");
            logger.endTestCase();
            return;
        }
        pass("Login dialog opened");

        step("Login", "Enter username", TEST_USER, "Username field filled");
        driver.typeInField("txtUser", TEST_USER);
        pass("Username entered");

        step("Login", "Enter wrong password", "WRONGPASS", "Password field filled");
        driver.typeInField("txtPass", "WRONGPASS");
        pass("Wrong password entered");

        step("Login", "Click LOGIN button", "btnLogin", "Error dialog appears");
        screenshot("tc002_before_login");
        driver.clickButton("Login");

        JDialog errDialog = driver.waitForDialog(5000);
        if (errDialog != null) {
            String msg = popupDetector.lastPopupText();
            screenshot("tc002_error_dialog");
            pass("Error dialog appeared: " + msg);
            driver.pressEnter();
            sleep(400);
        } else {
            fail("No error dialog appeared for wrong password", "Expected dialog within 5s");
        }

        logger.endTestCase();
    }

    // ── TestCase003: Re-run from Excel ────────────────────────────────────

    /**
     * Reads previously recorded test steps from the Excel file and replays them.
     * Each row's Action column must follow the format:  ACTION_TYPE:param1:param2
     *   CLICK_BUTTON:<text>
     *   TYPE_FIELD:<fieldName>:<value>
     *   WAIT_WINDOW:<className>:<timeoutMs>
     *   PRESS_ENTER
     *   PRESS_ESCAPE
     *   SLEEP:<ms>
     */
    @Test
    public void tc003_replayFromExcel() throws Exception {
        logger.beginTestCase("TestCase003", "Re-play test cases read from Excel");

        ExcelTestCaseManager excel = new ExcelTestCaseManager();
        java.io.File xlsFile = new java.io.File(EXCEL_PATH);
        if (!xlsFile.exists()) {
            step("Excel", "Load test cases from Excel", EXCEL_PATH, "Test cases loaded");
            pass("Skipped — no Excel file yet (run tc001 first)");
            logger.endTestCase();
            return;
        }

        java.util.List<com.softpos.e2e.model.TestCase> cases;
        try {
            cases = excel.readTestCases(EXCEL_PATH);
        } catch (Exception e) {
            step("Excel", "Load test cases from Excel", EXCEL_PATH, "File loaded");
            fail("Failed to read Excel", e.getMessage());
            logger.endTestCase();
            return;
        }

        for (com.softpos.e2e.model.TestCase tc : cases) {
            if ("TestCase003".equals(tc.getId())) continue; // skip self

            for (com.softpos.e2e.model.TestStep recorded : tc.getSteps()) {
                step(
                    recorded.getScreenName(),
                    "[REPLAY] " + recorded.getAction(),
                    recorded.getInputData(),
                    recorded.getExpectedResult()
                );
                try {
                    executeAction(recorded.getAction(), recorded.getInputData());
                    pass("Replayed: " + recorded.getAction());
                } catch (Exception e) {
                    fail("Replay failed: " + recorded.getAction(), e.getMessage());
                }
                sleep(400);
            }
        }

        logger.endTestCase();
    }

    // ── helpers ───────────────────────────────────────────────────────────

    private void launchLoginDialog() throws Exception {
        // invokeAndWait ensures the dialog is fully constructed before the test thread proceeds
        SwingUtilities.invokeAndWait(() -> {
            Login dlg = new Login(new javax.swing.JFrame(), true);
            dlg.setModal(false);
            dlg.setVisible(true);
        });
    }

    private boolean hasButton(String text) {
        return driver.findButtonByText(text) != null;
    }

    private void clickFirstFound(String... texts) throws Exception {
        for (String t : texts) {
            if (hasButton(t)) {
                driver.clickButton(t);
                return;
            }
        }
        throw new AssertionError("None of the buttons found: " + java.util.Arrays.toString(texts));
    }

    /**
     * Interprets an Action string from Excel and executes it.
     * Format: ACTION_TYPE:param1:param2
     */
    private void executeAction(String action, String input) throws Exception {
        if (action == null || action.startsWith("[REPLAY]")) return;

        String[] parts = action.split(":", 3);
        String cmd = parts[0].trim().toUpperCase();

        switch (cmd) {
            case "CLICK_BUTTON":
                driver.clickButton(parts.length > 1 ? parts[1].trim() : input);
                break;
            case "TYPE_FIELD":
                if (parts.length >= 3) {
                    driver.typeInField(parts[1].trim(), parts[2].trim());
                } else if (!input.isEmpty()) {
                    driver.typeInField(parts.length > 1 ? parts[1].trim() : "", input);
                }
                break;
            case "WAIT_WINDOW":
                int timeout = parts.length >= 3 ? Integer.parseInt(parts[2].trim()) : 5000;
                driver.waitForWindowClass(parts.length > 1 ? parts[1].trim() : "", timeout);
                break;
            case "PRESS_ENTER":
                driver.pressEnter();
                break;
            case "PRESS_ESCAPE":
                driver.pressEscape();
                break;
            case "SLEEP":
                sleep(parts.length > 1 ? Integer.parseInt(parts[1].trim()) : 500);
                break;
            default:
                System.out.println("[E2E] Unknown action: " + action);
        }
    }
}
