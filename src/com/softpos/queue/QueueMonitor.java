package com.softpos.queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.SwingUtilities;

/**
 * Polls queue_works on a background thread and notifies the monitor2 boards.
 * A queue that newly reaches "Serve" is announced by voice (3 times).
 */
public final class QueueMonitor {

    private static final QueueMonitor INSTANCE = new QueueMonitor();
    private static final int POLL_SECONDS = 2;

    private final List<Runnable> listeners = new CopyOnWriteArrayList<>();
    private volatile List<QueueService.Entry> snapshot = Collections.emptyList();
    private final Set<Integer> knownServe = new HashSet<>();
    private boolean seeded = false;
    private ScheduledExecutorService scheduler;

    private QueueMonitor() {
    }

    public static QueueMonitor getInstance() {
        return INSTANCE;
    }

    public List<QueueService.Entry> getSnapshot() {
        return snapshot;
    }

    public void addListener(Runnable r) {
        listeners.add(r);
    }

    public synchronized void start() {
        if (scheduler != null) {
            return;
        }
        seeded = false;
        scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "queue-monitor");
            t.setDaemon(true);
            return t;
        });
        scheduler.scheduleWithFixedDelay(this::poll, 0, POLL_SECONDS, TimeUnit.SECONDS);
    }

    public synchronized void stop() {
        if (scheduler != null) {
            scheduler.shutdownNow();
            scheduler = null;
        }
    }

    /** Refresh right now (e.g. after a new queue was created). */
    public void refreshNow() {
        ScheduledExecutorService s = scheduler;
        if (s != null) {
            s.execute(this::poll);
        }
    }

    /** The caller announces this queue itself and is about to set it to Serve: do not announce it again here. */
    public synchronized void skipAnnounce(int queueId) {
        knownServe.add(queueId);
    }

    private synchronized void poll() {
        try {
            List<QueueService.Entry> list = QueueService.listActive();
            Set<Integer> nowServe = new HashSet<>();
            List<Integer> fresh = new ArrayList<>();
            for (QueueService.Entry e : list) {
                if (QueueService.SERVE.equals(e.status)) {
                    nowServe.add(e.id);
                    if (seeded && !knownServe.contains(e.id)) {
                        fresh.add(e.no);
                    }
                }
            }
            knownServe.clear();
            knownServe.addAll(nowServe);
            seeded = true;
            snapshot = list;
            for (int no : fresh) {
                QueueAnnouncer.announce(no);
            }
            SwingUtilities.invokeLater(() -> {
                for (Runnable r : listeners) {
                    r.run();
                }
            });
        } catch (RuntimeException e) {
            com.softpos.util.AppLogUtil.log(QueueMonitor.class, "error", e);
        }
    }
}
