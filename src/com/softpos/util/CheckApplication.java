package com.softpos.util;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author nathee
 */
public class CheckApplication {

    // Port เฉพาะของแอปนี้ — ถ้า bind ได้ = ไม่มี instance อื่น
    private static final int APP_PORT = 49152;
    private static ServerSocket lockSocket;

    public static boolean isRunning() {
        try {
            lockSocket = new ServerSocket(APP_PORT);
            // bind สำเร็จ = เราเป็น instance แรก
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    if (lockSocket != null && !lockSocket.isClosed()) {
                        lockSocket.close();
                    }
                } catch (IOException ignored) {
                }
            }));
            return false;
        } catch (IOException ex) {
            // port ถูกใช้งานอยู่ = มี instance อื่นรันอยู่แล้ว
            return true;
        }
    }
}
