package com.softpos.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

/**
 *
 * @author nathee
 */
public class CheckApplication {

    private static final String LOCK_FILE = System.getProperty("java.io.tmpdir") + File.separator + "softpos_restaurant.lock";
    private static RandomAccessFile lockRaf;
    private static FileLock fileLock;

    public static boolean isRunning() {
        try {
            File file = new File(LOCK_FILE);
            lockRaf = new RandomAccessFile(file, "rw");
            fileLock = lockRaf.getChannel().tryLock();
            if (fileLock == null) {
                // ไม่สามารถ lock ได้ = มี instance อื่นรันอยู่แล้ว
                lockRaf.close();
                return true;
            }
            // lock สำเร็จ = เราเป็น instance แรก
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    if (fileLock != null && fileLock.isValid()) {
                        fileLock.release();
                    }
                    if (lockRaf != null) {
                        lockRaf.close();
                    }
                    file.delete();
                } catch (IOException ignored) {
                }
            }));
            return false;
        } catch (IOException ex) {
            return false;
        }
    }
}
