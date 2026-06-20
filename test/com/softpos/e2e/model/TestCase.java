package com.softpos.e2e.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** Container for all steps belonging to one test scenario. */
public class TestCase {

    private static final SimpleDateFormat FMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final String id;
    private final String description;
    private final List<TestStep> steps = new ArrayList<>();
    private String startTime;
    private String endTime;
    private String overallStatus; // PASS | FAIL | SKIP

    public TestCase(String id, String description) {
        this.id = id;
        this.description = description;
        this.startTime = FMT.format(new Date());
        this.overallStatus = "PENDING";
    }

    public void addStep(TestStep step) {
        steps.add(step);
    }

    public void finish() {
        endTime = FMT.format(new Date());
        boolean anyFail = steps.stream().anyMatch(s -> "FAIL".equals(s.getStatus()));
        overallStatus = anyFail ? "FAIL" : "PASS";
    }

    public long passCount() {
        return steps.stream().filter(s -> "PASS".equals(s.getStatus())).count();
    }

    public long failCount() {
        return steps.stream().filter(s -> "FAIL".equals(s.getStatus())).count();
    }

    // --- getters ---
    public String getId()            { return id; }
    public String getDescription()   { return description; }
    public List<TestStep> getSteps() { return steps; }
    public String getStartTime()     { return startTime; }
    public String getEndTime()       { return endTime; }
    public String getOverallStatus() { return overallStatus; }
}
