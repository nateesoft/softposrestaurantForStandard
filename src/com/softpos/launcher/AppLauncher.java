package com.softpos.launcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.update4j.Configuration;

/**
 * Entry point for launcher.jar — checks for updates then starts the main app.
 *
 * Deployment layout (all in same directory): launcher.jar ← users run this
 * SoftPOSRestaurantForStandart.jar ← main app (gets auto-updated)
 * lib/update4j-1.5.9.jar connect.ini ← add:
 * updateUrl,http://YOUR_SERVER/softpos
 *
 * Server must serve: http://YOUR_SERVER/softpos/update4j-config.xml
 * http://YOUR_SERVER/softpos/SoftPOSRestaurantForStandart.jar
 *
 * REQUIRES: Java 9+ runtime (update4j uses JPMS). Source compiles to Java 8
 * bytecode but must run on JRE 9+.
 */
public class AppLauncher {

    private static final String MAIN_JAR = "SoftPOSRestaurantForStandart.jar";

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ignored) {
        }

        UpdateSplashFrame splash = new UpdateSplashFrame();
        SwingUtilities.invokeLater(splash::show);

        try {
            Thread.sleep(300);
        } catch (InterruptedException ignored) {
        }

        String updateConfigUrl = readUpdateConfigUrl();

        if (updateConfigUrl != null) {
            performUpdate(updateConfigUrl, splash);
        } else {
            splash.setStatus("ไม่พบ updateUrl ใน connect.ini — เปิดโปรแกรมโดยตรง", 90);
            try {
                Thread.sleep(800);
            } catch (InterruptedException ignored) {
            }
        }

        launchMainApp(splash);
    }

    private static void performUpdate(String configUrl, UpdateSplashFrame splash) {
        try {
            splash.setStatus("กำลังตรวจสอบอัพเดทจาก server...", 15);

            try (InputStreamReader reader = new InputStreamReader(
                    URI.create(configUrl).toURL().openStream(), "UTF-8")) {
                Configuration config = Configuration.read(reader);

                splash.setStatus("กำลังตรวจสอบเวอร์ชัน...", 25);

                if (config.requiresUpdate()) {
                    splash.setStatus("พบอัพเดทใหม่ กำลังดาวน์โหลด...", 30);
                    SoftPOSUpdateHandler handler = new SoftPOSUpdateHandler(splash);
                    @SuppressWarnings("deprecation")
                    boolean success = config.update(handler);
                    if (!success) {
                        splash.setError("อัพเดทไม่สำเร็จ กำลังใช้เวอร์ชันเดิม...");
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException ignored) {
                        }
                    }
                } else {
                    splash.setStatus("โปรแกรมเป็นเวอร์ชันล่าสุดแล้ว", 92);
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException ignored) {
                    }
                }
            }

        } catch (java.net.ConnectException | java.net.UnknownHostException e) {
            splash.setStatus("ไม่สามารถเชื่อมต่อ server ได้ — เปิดโปรแกรมเวอร์ชันเดิม", 90);
            try {
                Thread.sleep(1200);
            } catch (InterruptedException ignored) {
            }
        } catch (IOException e) {
            splash.setStatus("ตรวจสอบอัพเดทไม่สำเร็จ: " + e.getMessage(), 90);
            try {
                Thread.sleep(1200);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private static void launchMainApp(UpdateSplashFrame splash) {
        File appJar = new File(MAIN_JAR);
        if (!appJar.exists()) {
            splash.close();
            JOptionPane.showMessageDialog(null,
                    "ไม่พบไฟล์ " + MAIN_JAR + "\nกรุณาตรวจสอบว่า launcher.jar และ " + MAIN_JAR + " อยู่ใน folder เดียวกัน",
                    "ไม่พบโปรแกรมหลัก", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        try {
            splash.setStatus("กำลังเปิดโปรแกรม...", 100);
            Thread.sleep(400);

            String javaHome = System.getProperty("java.home");
            String javaBin = javaHome + File.separator + "bin" + File.separator + "java";

            ProcessBuilder pb = new ProcessBuilder(
                    javaBin,
                    "-Dfile.encoding=utf8",
                    "-jar", MAIN_JAR
            );
            pb.directory(new File(".").getAbsoluteFile());
            pb.inheritIO();
            pb.start();

            splash.close();
            System.exit(0);

        } catch (IOException | InterruptedException e) {
            splash.close();
            JOptionPane.showMessageDialog(null,
                    "ไม่สามารถเปิดโปรแกรมได้:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * Reads updateUrl from connect.ini. Add this line to connect.ini:
     * updateUrl,http://YOUR_SERVER/softpos/update4j-config.xml
     */
    private static String readUpdateConfigUrl() {
        try (BufferedReader br = new BufferedReader(new FileReader("connect.ini"))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("updateUrl,")) {
                    String url = line.substring("updateUrl,".length()).trim();
                    return url.isEmpty() ? null : url;
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
