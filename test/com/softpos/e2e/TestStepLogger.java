package com.softpos.e2e;

import com.softpos.e2e.model.TestCase;
import com.softpos.e2e.model.TestStep;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread-safe singleton that collects test steps during execution.
 * Each test starts with beginTestCase() and ends with endTestCase().
 */
public class TestStepLogger {

    private static final TestStepLogger INSTANCE = new TestStepLogger();

    private final List<TestCase> allCases = Collections.synchronizedList(new ArrayList<>());
    private volatile TestCase current;
    private final AtomicInteger stepCounter = new AtomicInteger(0);

    private TestStepLogger() {}

    public static TestStepLogger getInstance() {
        return INSTANCE;
    }

    // -------------------------------------------------------------------------

    public void beginTestCase(String id, String description) {
        current = new TestCase(id, description);
        stepCounter.set(0);
    }

    public TestStep logStep(String screenName, String action,
                            String inputData, String expectedResult) {
        if (current == null) {
            beginTestCase("AUTO-" + System.currentTimeMillis(), "Auto-generated case");
        }
        int no = stepCounter.incrementAndGet();
        TestStep step = new TestStep(current.getId(), no, screenName, action, inputData, expectedResult);
        current.addStep(step);
        return step;
    }

    /** Mark the last logged step as PASS with an actual result message. */
    public void passLastStep(String actualResult) {
        TestStep last = lastStep();
        if (last != null) last.pass(actualResult);
    }

    /** Mark the last logged step as FAIL with details. */
    public void failLastStep(String actualResult, String error) {
        TestStep last = lastStep();
        if (last != null) last.fail(actualResult, error);
    }

    /** Attach a screenshot path to the last step. */
    public void attachScreenshot(String path) {
        TestStep last = lastStep();
        if (last != null) last.setScreenshotPath(path);
    }

    public void endTestCase() {
        if (current != null) {
            current.finish();
            allCases.add(current);
            current = null;
        }
    }

    /** Called after a popup is detected — records it on the last step. */
    public void recordPopup(String popupText) {
        TestStep last = lastStep();
        if (last != null) {
            String existing = last.getErrorMessage();
            if (existing == null || existing.isEmpty()) {
                last.setErrorMessage("[POPUP] " + popupText);
            } else {
                last.setErrorMessage(existing + " | [POPUP] " + popupText);
            }
        }
    }

    // -------------------------------------------------------------------------

    public TestCase getCurrentCase() { return current; }

    public List<TestCase> getAllCases() { return Collections.unmodifiableList(allCases); }

    public void clearAll() { allCases.clear(); }

    // -------------------------------------------------------------------------

    private TestStep lastStep() {
        if (current == null || current.getSteps().isEmpty()) return null;
        List<TestStep> steps = current.getSteps();
        return steps.get(steps.size() - 1);
    }
}
