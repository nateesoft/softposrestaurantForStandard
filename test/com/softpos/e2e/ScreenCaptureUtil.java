package com.softpos.e2e;

import javax.imageio.ImageIO;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** Captures full-screen screenshots and saves them as PNG files. */
public class ScreenCaptureUtil {

    private static final String SCREENSHOT_DIR = "test-reports/screenshots";
    private static final SimpleDateFormat FMT = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");

    private final Robot robot;

    public ScreenCaptureUtil() throws AWTException {
        robot = new Robot();
    }

    /**
     * Capture the full screen and save to test-reports/screenshots/.
     * Returns the saved file path, or empty string on failure.
     */
    public String capture(String label) {
        try {
            new File(SCREENSHOT_DIR).mkdirs();
            String filename = label.replaceAll("[^a-zA-Z0-9_-]", "_")
                    + "_" + FMT.format(new Date()) + ".png";
            File out = new File(SCREENSHOT_DIR, filename);

            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            BufferedImage img = robot.createScreenCapture(
                    new Rectangle(0, 0, screen.width, screen.height));
            ImageIO.write(img, "png", out);
            return out.getAbsolutePath();
        } catch (IOException e) {
            System.err.println("ScreenCaptureUtil: failed to save screenshot: " + e.getMessage());
            return "";
        }
    }
}
