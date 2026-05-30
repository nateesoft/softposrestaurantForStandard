package com.softpos.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AppLogUtil {

    private static final String LOG_DIR = "logs";
    private static final SimpleDateFormat DATE_FMT = new SimpleDateFormat("yyyy-MM-dd");

    // Single named logger — satisfies "static final" and "single logger" hints
    private static final Logger LOGGER = Logger.getLogger("softpos");

    private static FileHandler allHandler = null;
    private static FileHandler errorHandler = null;
    private static volatile boolean initialized = false;

    private static synchronized void init() {
        if (initialized) {
            return;
        }
        try {
            new File(LOG_DIR).mkdirs();
            String date = DATE_FMT.format(new Date());

            // logs/app-YYYY-MM-DD.log — INFO and above (all levels)
            allHandler = new FileHandler(LOG_DIR + "/app-" + date + ".log", true);
            allHandler.setFormatter(new SimpleFormatter());
            allHandler.setLevel(Level.INFO);

            // logs/error-YYYY-MM-DD.log — WARNING and SEVERE only
            errorHandler = new FileHandler(LOG_DIR + "/error-" + date + ".log", true);
            errorHandler.setFormatter(new SimpleFormatter());
            errorHandler.setLevel(Level.WARNING);
            errorHandler.setFilter(new Filter() {
                @Override
                public boolean isLoggable(LogRecord r) {
                    return r.getLevel().intValue() >= Level.WARNING.intValue();
                }
            });

            LOGGER.setUseParentHandlers(false);
            LOGGER.addHandler(allHandler);
            LOGGER.addHandler(errorHandler);
            LOGGER.setLevel(Level.INFO);

            initialized = true;
        } catch (IOException e) {
            System.err.println("AppLogUtil: failed to init log files: " + e.getMessage());
        }
    }

    private static String format(Class<?> clazz, Exception e) {
        return clazz.getSimpleName()
                + " | " + e.getClass().getSimpleName()
                + " | line:" + e.getStackTrace()[0].getLineNumber()
                + " | " + e.getMessage();
    }

    /**
     * Log an exception — backward-compatible with existing callers.
     */
    public static void log(Class<?> clazz, String type, Exception e) {
        init();
        String msg = format(clazz, e);
        switch (type.toLowerCase()) {
            case "error":
                LOGGER.log(Level.SEVERE, "{0}", msg);
                break;
            case "warning":
                LOGGER.log(Level.WARNING, "{0}", msg);
                break;
            default:
                LOGGER.log(Level.INFO, "{0}", msg);
        }
    }

    /**
     * Log an informational message (no exception).
     */
    public static void info(Class<?> clazz, String msg) {
        init();
        LOGGER.log(Level.INFO, "{0}", clazz.getSimpleName() + " | " + msg);
    }

    /**
     * Log a plain informational message.
     */
    public static void info(String msg) {
        init();
        LOGGER.log(Level.INFO, "{0}", msg);
    }

    /**
     * Log a warning message (no exception).
     */
    public static void warn(Class<?> clazz, String msg) {
        init();
        LOGGER.log(Level.WARNING, "{0}", clazz.getSimpleName() + " | " + msg);
    }

    /**
     * Log application startup — call once from main().
     */
    public static void startup(String appName, String version) {
        init();
        LOGGER.log(Level.INFO, "===== {0} v{1} started at {2} =====",
                new Object[]{appName, version, new Date()});
    }

    /**
     * Flush and close log files — call on application exit.
     */
    public static void shutdown() {
        if (!initialized) {
            return;
        }
        initialized = false;

        // Write "stopped" directly to the file to avoid LogManager's own shutdown hook
        // closing our FileHandlers before this method runs (JVM hook order is non-deterministic).
        String date = DATE_FMT.format(new Date());
        String msg = "===== Application stopped at " + new Date() + " =====" + System.lineSeparator();
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(LOG_DIR + "/app-" + date + ".log", true))) {
            bw.write(msg);
        } catch (IOException e) {
            System.err.println("AppLogUtil.shutdown: failed to write stop marker: " + e.getMessage());
        }

        if (allHandler != null) {
            try {
                allHandler.flush();
            } catch (Exception ignore) {
            }
            try {
                allHandler.close();
            } catch (Exception ignore) {
            }
            allHandler = null;
        }
        if (errorHandler != null) {
            try {
                errorHandler.close();
            } catch (Exception ignore) {
            }
            errorHandler = null;
        }
    }

    /**
     * Write raw HTML to logs/ folder for print debugging (kept for
     * PrintDriver).
     */
    public static void htmlFile(String htmlText) {
        try {
            new File(LOG_DIR).mkdirs();
            File f = new File(LOG_DIR, "print-debug-" + System.currentTimeMillis() + ".html");
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
                bw.write(htmlText);
            }
        } catch (IOException e) {
            System.err.println("AppLogUtil.htmlFile: " + e.getMessage());
        }
    }
}
