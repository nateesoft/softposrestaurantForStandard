package com.softpos.e2e;

import com.softpos.e2e.model.TestCase;
import com.softpos.e2e.model.TestStep;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads and writes E2E test cases to/from an Excel file (.xls) using JXL.
 *
 * Column layout (0-based):
 *  0  TestCase ID   1  Step No    2  Screen       3  Action
 *  4  Input Data    5  Expected   6  Actual        7  Status
 *  8  Error Msg     9  Screenshot 10 Timestamp
 */
public class ExcelTestCaseManager {

    // ── column indexes ──────────────────────────────────────────────────────
    private static final int COL_TC_ID    = 0;
    private static final int COL_STEP     = 1;
    private static final int COL_SCREEN   = 2;
    private static final int COL_ACTION   = 3;
    private static final int COL_INPUT    = 4;
    private static final int COL_EXPECTED = 5;
    private static final int COL_ACTUAL   = 6;
    private static final int COL_STATUS   = 7;
    private static final int COL_ERROR    = 8;
    private static final int COL_SHOT     = 9;
    private static final int COL_TS       = 10;

    private static final String SHEET_STEPS   = "TestSteps";
    private static final String SHEET_SUMMARY = "Summary";

    // ── write ────────────────────────────────────────────────────────────────

    /**
     * Insert or update test cases in the Excel file.
     * Creates the file if it does not exist; appends new rows otherwise.
     */
    public void writeTestCases(String filePath, List<TestCase> cases)
            throws IOException, WriteException, BiffException {

        File file = new File(filePath);
        WritableWorkbook wb;

        if (file.exists()) {
            WorkbookSettings ws = new WorkbookSettings();
            ws.setEncoding("UTF-8");
            Workbook existing = Workbook.getWorkbook(file, ws);
            wb = Workbook.createWorkbook(file, existing);
        } else {
            file.getParentFile().mkdirs();
            wb = Workbook.createWorkbook(file);
        }

        WritableSheet stepSheet  = getOrCreateSheet(wb, SHEET_STEPS,  0);
        WritableSheet sumSheet   = getOrCreateSheet(wb, SHEET_SUMMARY, 1);

        ensureHeaders(stepSheet);
        ensureSummaryHeaders(sumSheet);

        int nextRow = stepSheet.getRows();
        if (nextRow == 0) nextRow = 1; // skip header row

        int sumRow = sumSheet.getRows();
        if (sumRow == 0) sumRow = 1;

        for (TestCase tc : cases) {
            // Write summary row
            addSummaryRow(sumSheet, sumRow++, tc);

            // Write each step row
            for (TestStep step : tc.getSteps()) {
                addStepRow(stepSheet, nextRow++, step);
            }
        }

        wb.write();
        wb.close();
    }

    // ── read ─────────────────────────────────────────────────────────────────

    /** Read test cases from an existing Excel file (for re-running). */
    public List<TestCase> readTestCases(String filePath)
            throws IOException, BiffException {

        File file = new File(filePath);
        if (!file.exists()) throw new IOException("Excel file not found: " + filePath);

        WorkbookSettings ws = new WorkbookSettings();
        ws.setEncoding("UTF-8");
        Workbook wb = Workbook.getWorkbook(file, ws);
        Sheet sheet;
        try {
            sheet = wb.getSheet(SHEET_STEPS);
        } catch (Exception e) {
            wb.close();
            throw new IOException("Sheet '" + SHEET_STEPS + "' not found in " + filePath);
        }

        List<TestCase> result = new ArrayList<>();
        TestCase current = null;

        for (int row = 1; row < sheet.getRows(); row++) {
            String tcId    = cell(sheet, COL_TC_ID, row);
            String stepStr = cell(sheet, COL_STEP, row);
            if (tcId.isEmpty()) continue;

            if (current == null || !current.getId().equals(tcId)) {
                current = new TestCase(tcId, tcId);
                result.add(current);
            }

            int stepNo = 0;
            try { stepNo = Integer.parseInt(stepStr); } catch (NumberFormatException ignored) {}

            TestStep step = new TestStep(
                tcId, stepNo,
                cell(sheet, COL_SCREEN, row),
                cell(sheet, COL_ACTION, row),
                cell(sheet, COL_INPUT, row),
                cell(sheet, COL_EXPECTED, row)
            );
            step.setActualResult(cell(sheet, COL_ACTUAL, row));
            step.setStatus(cell(sheet, COL_STATUS, row));
            step.setErrorMessage(cell(sheet, COL_ERROR, row));
            step.setScreenshotPath(cell(sheet, COL_SHOT, row));
            step.setTimestamp(cell(sheet, COL_TS, row));
            current.addStep(step);
        }

        wb.close();
        return result;
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private WritableSheet getOrCreateSheet(WritableWorkbook wb, String name, int index) {
        WritableSheet sheet = wb.getSheet(name);
        if (sheet == null) {
            sheet = wb.createSheet(name, index);
        }
        return sheet;
    }

    private void ensureHeaders(WritableSheet sheet) throws WriteException {
        if (sheet.getRows() > 0) return;
        WritableCellFormat bold = boldFormat();
        String[] headers = {
            "TestCase ID", "Step No", "Screen", "Action",
            "Input Data", "Expected Result", "Actual Result",
            "Status", "Error Message", "Screenshot", "Timestamp"
        };
        for (int c = 0; c < headers.length; c++) {
            sheet.addCell(new Label(c, 0, headers[c], bold));
            sheet.setColumnView(c, 22);
        }
    }

    private void ensureSummaryHeaders(WritableSheet sheet) throws WriteException {
        if (sheet.getRows() > 0) return;
        WritableCellFormat bold = boldFormat();
        String[] headers = {
            "TestCase ID", "Description",
            "Total Steps", "Pass", "Fail",
            "Overall Status", "Start Time", "End Time"
        };
        for (int c = 0; c < headers.length; c++) {
            sheet.addCell(new Label(c, 0, headers[c], bold));
            sheet.setColumnView(c, 22);
        }
    }

    private void addStepRow(WritableSheet sheet, int row, TestStep step)
            throws WriteException {
        WritableCellFormat passFormat = statusFormat("PASS".equals(step.getStatus()));
        WritableCellFormat normal = new WritableCellFormat();

        sheet.addCell(new Label(COL_TC_ID,    row, step.getTestCaseId(), normal));
        sheet.addCell(new Label(COL_STEP,     row, String.valueOf(step.getStepNo()), normal));
        sheet.addCell(new Label(COL_SCREEN,   row, step.getScreenName(), normal));
        sheet.addCell(new Label(COL_ACTION,   row, step.getAction(), normal));
        sheet.addCell(new Label(COL_INPUT,    row, step.getInputData(), normal));
        sheet.addCell(new Label(COL_EXPECTED, row, step.getExpectedResult(), normal));
        sheet.addCell(new Label(COL_ACTUAL,   row, step.getActualResult(), normal));
        sheet.addCell(new Label(COL_STATUS,   row, step.getStatus(), passFormat));
        sheet.addCell(new Label(COL_ERROR,    row, step.getErrorMessage(), normal));
        sheet.addCell(new Label(COL_SHOT,     row, step.getScreenshotPath(), normal));
        sheet.addCell(new Label(COL_TS,       row, step.getTimestamp(), normal));
    }

    private void addSummaryRow(WritableSheet sheet, int row, TestCase tc)
            throws WriteException {
        WritableCellFormat normal = new WritableCellFormat();
        WritableCellFormat statusFmt = statusFormat("PASS".equals(tc.getOverallStatus()));

        sheet.addCell(new Label(0, row, tc.getId(), normal));
        sheet.addCell(new Label(1, row, tc.getDescription(), normal));
        sheet.addCell(new Label(2, row, String.valueOf(tc.getSteps().size()), normal));
        sheet.addCell(new Label(3, row, String.valueOf(tc.passCount()), normal));
        sheet.addCell(new Label(4, row, String.valueOf(tc.failCount()), normal));
        sheet.addCell(new Label(5, row, tc.getOverallStatus(), statusFmt));
        sheet.addCell(new Label(6, row, tc.getStartTime() != null ? tc.getStartTime() : "", normal));
        sheet.addCell(new Label(7, row, tc.getEndTime()   != null ? tc.getEndTime()   : "", normal));
    }

    private WritableCellFormat boldFormat() throws WriteException {
        WritableFont bold = new WritableFont(WritableFont.ARIAL, 11, WritableFont.BOLD);
        return new WritableCellFormat(bold);
    }

    private WritableCellFormat statusFormat(boolean pass) throws WriteException {
        WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        font.setColour(pass ? jxl.format.Colour.GREEN : jxl.format.Colour.RED);
        return new WritableCellFormat(font);
    }

    private String cell(Sheet sheet, int col, int row) {
        try {
            Cell c = sheet.getCell(col, row);
            return c == null ? "" : c.getContents().trim();
        } catch (Exception e) {
            return "";
        }
    }
}
