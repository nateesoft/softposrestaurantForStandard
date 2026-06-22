package com.softpos.e2e;

import com.softpos.e2e.model.TestCase;
import com.softpos.e2e.model.TestStep;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Generates a self-contained HTML test report.
 * Double-click the output file to open in any browser.
 */
public class HtmlReportGenerator {

    private static final String REPORT_DIR = "test-reports";

    public String generate(List<TestCase> cases) throws IOException {
        new File(REPORT_DIR).mkdirs();
        String filename = "e2e-report-" +
                new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".html";
        File out = new File(REPORT_DIR, filename);

        long totalSteps = cases.stream().mapToLong(tc -> tc.getSteps().size()).sum();
        long passSteps  = cases.stream().mapToLong(TestCase::passCount).sum();
        long failSteps  = cases.stream().mapToLong(TestCase::failCount).sum();
        long passCases  = cases.stream().filter(tc -> "PASS".equals(tc.getOverallStatus())).count();
        long failCases  = cases.stream().filter(tc -> "FAIL".equals(tc.getOverallStatus())).count();

        try (BufferedWriter w = new BufferedWriter(new FileWriter(out))) {
            w.write(buildHtml(cases, totalSteps, passSteps, failSteps, passCases, failCases));
        }
        return out.getAbsolutePath();
    }

    // ── HTML builder ──────────────────────────────────────────────────────────

    private String buildHtml(List<TestCase> cases,
                             long totalSteps, long passSteps, long failSteps,
                             long passCases, long failCases) {
        StringBuilder sb = new StringBuilder();
        String now = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

        sb.append("<!DOCTYPE html><html lang='th'><head>")
          .append("<meta charset='UTF-8'>")
          .append("<title>E2E Test Report - SoftPOS Restaurant</title>")
          .append("<style>").append(css()).append("</style>")
          .append("</head><body>");

        // header
        sb.append("<div class='header'>")
          .append("<h1>SoftPOS Restaurant — E2E Test Report</h1>")
          .append("<p class='subtitle'>Generated: ").append(now).append("</p>")
          .append("</div>");

        // summary cards
        sb.append("<div class='summary'>")
          .append(card("Total Test Cases", String.valueOf(cases.size()), "blue"))
          .append(card("Passed Cases", String.valueOf(passCases), "green"))
          .append(card("Failed Cases", String.valueOf(failCases), failCases > 0 ? "red" : "green"))
          .append(card("Total Steps", String.valueOf(totalSteps), "blue"))
          .append(card("Steps Passed", String.valueOf(passSteps), "green"))
          .append(card("Steps Failed", String.valueOf(failSteps), failSteps > 0 ? "red" : "green"))
          .append("</div>");

        // progress bar
        int pct = totalSteps == 0 ? 100 : (int) (passSteps * 100 / totalSteps);
        sb.append("<div class='progress-wrap'><div class='progress-bar' style='width:")
          .append(pct).append("%'>").append(pct).append("% Passed</div></div>");

        // test case detail tables
        for (TestCase tc : cases) {
            sb.append("<div class='tc-block'>")
              .append("<div class='tc-header ").append(statusClass(tc.getOverallStatus())).append("'>")
              .append("<span class='tc-id'>").append(esc(tc.getId())).append("</span>")
              .append(" &mdash; ").append(esc(tc.getDescription()))
              .append("<span class='badge ").append(statusClass(tc.getOverallStatus())).append("'>")
              .append(tc.getOverallStatus()).append("</span>")
              .append("<span class='tc-meta'>Steps: ").append(tc.getSteps().size())
              .append(" | Pass: ").append(tc.passCount())
              .append(" | Fail: ").append(tc.failCount())
              .append(" | Start: ").append(esc(tc.getStartTime() != null ? tc.getStartTime() : "-"))
              .append("</span></div>");

            sb.append("<table><thead><tr>")
              .append("<th>#</th><th>Screen</th><th>Action</th><th>Input</th>")
              .append("<th>Expected</th><th>Actual</th><th>Status</th>")
              .append("<th>Error</th><th>Screenshot</th><th>Timestamp</th>")
              .append("</tr></thead><tbody>");

            for (TestStep step : tc.getSteps()) {
                sb.append("<tr class='").append(statusClass(step.getStatus())).append("'>")
                  .append(td(String.valueOf(step.getStepNo())))
                  .append(td(step.getScreenName()))
                  .append(td(step.getAction()))
                  .append(td(step.getInputData()))
                  .append(td(step.getExpectedResult()))
                  .append(td(step.getActualResult()))
                  .append("<td><span class='badge ").append(statusClass(step.getStatus()))
                  .append("'>").append(esc(step.getStatus())).append("</span></td>")
                  .append(td(step.getErrorMessage()))
                  .append(screenshotTd(step.getScreenshotPath()))
                  .append(td(step.getTimestamp()))
                  .append("</tr>");
            }
            sb.append("</tbody></table></div>");
        }

        sb.append("<footer><p>SoftPOS Restaurant ICS &copy;2026 — E2E Test Framework</p></footer>")
          .append("</body></html>");
        return sb.toString();
    }

    private String card(String label, String value, String color) {
        return "<div class='card " + color + "'><div class='card-value'>" + value +
               "</div><div class='card-label'>" + label + "</div></div>";
    }

    private String td(String text) {
        return "<td>" + esc(text != null ? text : "") + "</td>";
    }

    private String screenshotTd(String path) {
        if (path == null || path.isEmpty()) return "<td>-</td>";
        String name = new File(path).getName();
        return "<td><a href='file:///" + esc(path.replace('\\', '/')) +
               "' target='_blank'>" + esc(name) + "</a></td>";
    }

    private String statusClass(String status) {
        if ("PASS".equals(status))    return "pass";
        if ("FAIL".equals(status))    return "fail";
        if ("SKIP".equals(status))    return "skip";
        return "pending";
    }

    private String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;");
    }

    private String css() {
        return "body{font-family:Tahoma,Arial,sans-serif;margin:0;background:#f4f6f9;color:#333}"
             + ".header{background:#2c3e50;color:#fff;padding:20px 30px}"
             + ".header h1{margin:0;font-size:22px}"
             + ".subtitle{margin:4px 0 0;font-size:13px;opacity:.8}"
             + ".summary{display:flex;flex-wrap:wrap;gap:14px;padding:20px 30px}"
             + ".card{border-radius:8px;padding:16px 22px;min-width:130px;color:#fff;text-align:center}"
             + ".card.blue{background:#2980b9}.card.green{background:#27ae60}.card.red{background:#c0392b}"
             + ".card-value{font-size:28px;font-weight:bold}"
             + ".card-label{font-size:12px;margin-top:4px;opacity:.9}"
             + ".progress-wrap{margin:0 30px 20px;background:#ddd;border-radius:6px;overflow:hidden;height:28px}"
             + ".progress-bar{height:100%;background:#27ae60;color:#fff;font-size:13px;font-weight:bold;"
             + "  display:flex;align-items:center;justify-content:center;min-width:40px;transition:width .4s}"
             + ".tc-block{margin:0 30px 26px;border:1px solid #ddd;border-radius:6px;overflow:hidden}"
             + ".tc-header{padding:10px 16px;background:#ecf0f1;font-weight:bold;font-size:14px;"
             + "  display:flex;align-items:center;gap:10px;flex-wrap:wrap}"
             + ".tc-header.pass{border-left:5px solid #27ae60}"
             + ".tc-header.fail{border-left:5px solid #c0392b}"
             + ".tc-id{font-size:15px}"
             + ".tc-meta{font-size:12px;color:#666;margin-left:auto}"
             + ".badge{padding:2px 8px;border-radius:4px;font-size:11px;font-weight:bold;color:#fff}"
             + ".badge.pass{background:#27ae60}.badge.fail{background:#c0392b}"
             + ".badge.skip{background:#f39c12}.badge.pending{background:#95a5a6}"
             + "table{width:100%;border-collapse:collapse;font-size:12px}"
             + "th{background:#2c3e50;color:#fff;padding:7px 10px;text-align:left}"
             + "td{padding:6px 10px;border-bottom:1px solid #eee;vertical-align:top}"
             + "tr.pass td{background:#f0fff4}tr.fail td{background:#fff0f0}"
             + "tr.skip td{background:#fffbf0}tr:hover td{filter:brightness(0.97)}"
             + "footer{text-align:center;padding:16px;font-size:12px;color:#999}"
             + "a{color:#2980b9}";
    }
}
