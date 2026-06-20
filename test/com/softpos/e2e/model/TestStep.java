package com.softpos.e2e.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/** One step inside a test case — maps directly to one Excel row. */
public class TestStep {

    private static final SimpleDateFormat FMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String testCaseId;
    private int stepNo;
    private String screenName;
    private String action;
    private String inputData;
    private String expectedResult;
    private String actualResult;
    private String status;        // PASS | FAIL | SKIP | PENDING
    private String errorMessage;
    private String screenshotPath;
    private String timestamp;

    public TestStep(String testCaseId, int stepNo, String screenName,
                    String action, String inputData, String expectedResult) {
        this.testCaseId = testCaseId;
        this.stepNo = stepNo;
        this.screenName = screenName;
        this.action = action;
        this.inputData = inputData;
        this.expectedResult = expectedResult;
        this.actualResult = "";
        this.status = "PENDING";
        this.errorMessage = "";
        this.screenshotPath = "";
        this.timestamp = FMT.format(new Date());
    }

    public void pass(String actualResult) {
        this.actualResult = actualResult;
        this.status = "PASS";
    }

    public void fail(String actualResult, String errorMessage) {
        this.actualResult = actualResult;
        this.errorMessage = errorMessage;
        this.status = "FAIL";
    }

    // --- getters / setters ---

    public String getTestCaseId()       { return testCaseId; }
    public int    getStepNo()           { return stepNo; }
    public String getScreenName()       { return screenName; }
    public String getAction()           { return action; }
    public String getInputData()        { return inputData; }
    public String getExpectedResult()   { return expectedResult; }
    public String getActualResult()     { return actualResult; }
    public String getStatus()           { return status; }
    public String getErrorMessage()     { return errorMessage; }
    public String getScreenshotPath()   { return screenshotPath; }
    public String getTimestamp()        { return timestamp; }

    public void setActualResult(String v)   { actualResult = v; }
    public void setStatus(String v)         { status = v; }
    public void setErrorMessage(String v)   { errorMessage = v; }
    public void setScreenshotPath(String v) { screenshotPath = v; }
    public void setTimestamp(String v)      { timestamp = v; }
}
