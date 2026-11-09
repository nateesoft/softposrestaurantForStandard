package com.softpos.queue;

import com.softpos.connection.database.ConfigFile;
import com.softpos.util.AppLogUtil;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

/**
 * Calls a queue number out loud, 3 times. Language comes from connect.ini
 * "queueVoice" (default th). To add a language: add a phrase in {@link #phrase}
 * and optionally drop recorded clips in sounds/&lt;lang&gt;/ (see below).
 *
 * Order of preference:
 * 1. recorded clips: sounds/&lt;lang&gt;/call.wav + n0..n9.wav (+ end.wav optional)
 * 2. OS text-to-speech (Windows SAPI via PowerShell, macOS "say")
 * 3. system beep
 */
public final class QueueAnnouncer {

    public static final int REPEAT = 3;

    private static final ExecutorService EXEC = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r, "queue-announcer");
        t.setDaemon(true);
        return t;
    });

    private QueueAnnouncer() {
    }

    public static String language() {
        String l = ConfigFile.getProperties("queueVoice");
        return l.isEmpty() ? "th" : l.toLowerCase();
    }

    public static void announce(int queueNo) {
        final String lang = language();
        EXEC.submit(() -> {
            for (int i = 0; i < REPEAT; i++) {
                try {
                    if (!playClips(lang, queueNo) && !speak(lang, queueNo)) {
                        java.awt.Toolkit.getDefaultToolkit().beep();
                        Thread.sleep(700);
                    }
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                } catch (Exception e) {
                    AppLogUtil.log(QueueAnnouncer.class, "error", e);
                }
            }
        });
    }

    static String phrase(String lang, int no) {
        switch (lang) {
            case "lo":
                return "ເຊີນ ຄິວ ເລກ " + no;
            case "en":
                return "Queue number " + no + ", please";
            case "th":
            default:
                return "ขอเชิญคิวหมายเลข " + no + " ค่ะ";
        }
    }

    private static String locale(String lang) {
        switch (lang) {
            case "lo":
                return "lo-LA";
            case "en":
                return "en-US";
            default:
                return "th-TH";
        }
    }

    private static boolean playClips(String lang, int no) throws Exception {
        File dir = new File("sounds", lang);
        File call = new File(dir, "call.wav");
        if (!call.isFile()) {
            return false;
        }
        java.util.List<File> files = new java.util.ArrayList<>();
        files.add(call);
        for (char c : String.valueOf(no).toCharArray()) {
            File f = new File(dir, "n" + c + ".wav");
            if (!f.isFile()) {
                return false;
            }
            files.add(f);
        }
        File end = new File(dir, "end.wav");
        if (end.isFile()) {
            files.add(end);
        }
        for (File f : files) {
            playAndWait(f);
        }
        return true;
    }

    private static void playAndWait(File f) throws Exception {
        try (AudioInputStream in = AudioSystem.getAudioInputStream(f)) {
            Clip clip = AudioSystem.getClip();
            java.util.concurrent.CountDownLatch done = new java.util.concurrent.CountDownLatch(1);
            clip.addLineListener(ev -> {
                if (ev.getType() == LineEvent.Type.STOP) {
                    done.countDown();
                }
            });
            clip.open(in);
            clip.start();
            done.await(15, java.util.concurrent.TimeUnit.SECONDS);
            clip.close();
        }
    }

    private static boolean speak(String lang, int no) throws Exception {
        String os = System.getProperty("os.name", "").toLowerCase();
        String text = phrase(lang, no);
        ProcessBuilder pb;
        if (os.contains("win")) {
            // text is passed via environment so it is never parsed as script
            String script = "Add-Type -AssemblyName System.Speech;"
                    + "$s=New-Object System.Speech.Synthesis.SpeechSynthesizer;"
                    + "$c=[System.Globalization.CultureInfo]::GetCultureInfo($env:QUEUE_LOCALE);"
                    + "try{$s.SelectVoiceByHints([System.Speech.Synthesis.VoiceGender]::NotSet,"
                    + "[System.Speech.Synthesis.VoiceAge]::NotSet,0,$c)}catch{};"
                    + "$s.Speak($env:QUEUE_TEXT)";
            pb = new ProcessBuilder("powershell", "-NoProfile", "-Command", script);
            pb.environment().put("QUEUE_LOCALE", locale(lang));
            pb.environment().put("QUEUE_TEXT", text);
        } else if (os.contains("mac")) {
            pb = new ProcessBuilder("say", "-v", "th".equals(lang) ? "Kanya" : "en".equals(lang) ? "Samantha" : "Alex", text);
        } else {
            return false;
        }
        pb.redirectErrorStream(true);
        Process p = pb.start();
        byte[] buf = new byte[256];
        while (p.getInputStream().read(buf) >= 0) {
            // drain output so the process cannot block
        }
        return p.waitFor() == 0;
    }
}
