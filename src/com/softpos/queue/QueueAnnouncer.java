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

    private static boolean warned = false;

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
        java.util.List<String> names = clipNames(lang, no);
        if (names == null || !allExist(dir, names)) {
            names = digitClipNames(no); // number-style clips missing: read digit by digit
            if (!allExist(dir, names)) {
                return false;
            }
        }
        for (String name : names) {
            files.add(new File(dir, name + ".wav"));
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

    private static boolean allExist(File dir, java.util.List<String> names) {
        for (String n : names) {
            if (!new File(dir, n + ".wav").isFile()) {
                return false;
            }
        }
        return true;
    }

    private static java.util.List<String> digitClipNames(int no) {
        java.util.List<String> names = new java.util.ArrayList<>();
        for (char c : String.valueOf(no).toCharArray()) {
            names.add("n" + c);
        }
        return names;
    }

    /**
     * Clips to play for the number. connect.ini "queueSpeak,words" reads Thai numbers as a quantity
     * (125 = 1 hundred 2 ten 5) using n0..n9 + ten, yi, et, hundred, thousand; anything else reads digit by digit.
     */
    static java.util.List<String> clipNames(String lang, int no) {
        if (!"words".equalsIgnoreCase(ConfigFile.getProperties("queueSpeak")) || !"th".equals(lang)
                || no < 1 || no > 9999) {
            return digitClipNames(no);
        }
        java.util.List<String> n = new java.util.ArrayList<>();
        int th = no / 1000;
        int h = (no / 100) % 10;
        int t = (no / 10) % 10;
        int u = no % 10;
        if (th > 0) {
            n.add("n" + th);
            n.add("thousand");
        }
        if (h > 0) {
            n.add("n" + h);
            n.add("hundred");
        }
        if (t == 1) {
            n.add("ten");
        } else if (t == 2) {
            n.add("yi");
            n.add("ten");
        } else if (t >= 3) {
            n.add("n" + t);
            n.add("ten");
        }
        if (u == 1 && no > 10) {
            n.add("et");
        } else if (u > 0) {
            n.add("n" + u);
        }
        return n;
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
            // exit 3 when no installed voice speaks this language: the caller then falls back to a beep
            String script = "Add-Type -AssemblyName System.Speech;"
                    + "$s=New-Object System.Speech.Synthesis.SpeechSynthesizer;"
                    + "$p=$env:QUEUE_LOCALE.Substring(0,2);"
                    + "$v=$s.GetInstalledVoices()|Where-Object{$_.Enabled -and $_.VoiceInfo.Culture.Name.StartsWith($p)}|Select-Object -First 1;"
                    + "if(-not $v){exit 3};"
                    + "$s.SelectVoice($v.VoiceInfo.Name);"
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
        int code = p.waitFor();
        if (code != 0 && !warned) {
            warned = true;
            AppLogUtil.warn(QueueAnnouncer.class, "no text-to-speech voice for '" + lang
                    + "' (exit " + code + "); using beep. Put recorded clips in sounds/" + lang + "/ (see sounds/README.txt)");
        }
        return code == 0;
    }
}
