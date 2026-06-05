package com.softpos.report.driver;

import com.softpos.util.AppLogUtil;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Manages one FIFO print queue per printer.
 * All print jobs are non-blocking — callers return immediately,
 * and jobs execute in submission order on a dedicated daemon thread.
 */
public class PrintQueueManager {

    private static final PrintQueueManager INSTANCE = new PrintQueueManager();
    private final Map<String, ExecutorService> executors = new ConcurrentHashMap<>();

    private PrintQueueManager() {
    }

    public static PrintQueueManager getInstance() {
        return INSTANCE;
    }

    /**
     * Enqueues a print job for the given printer key.
     * Returns immediately — job runs on a background thread.
     *
     * @param printerKey unique key per printer (printer name or logical role)
     * @param job        the blocking print operation to run
     */
    public void submitPrint(String printerKey, Runnable job) {
        ExecutorService executor = executors.computeIfAbsent(printerKey, key ->
                Executors.newSingleThreadExecutor(r -> {
                    Thread t = new Thread(r, "print-" + key);
                    t.setDaemon(true);
                    return t;
                })
        );
        executor.submit(() -> {
            try {
                job.run();
            } catch (Exception e) {
                AppLogUtil.log(PrintQueueManager.class, "error", e);
            }
        });
    }

    /** Call on app shutdown to drain queues cleanly. */
    public void shutdown() {
        executors.values().forEach(ExecutorService::shutdown);
        executors.clear();
    }
}
