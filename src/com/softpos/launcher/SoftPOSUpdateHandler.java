package com.softpos.launcher;

import java.nio.file.Path;
import org.update4j.FileMetadata;
import org.update4j.service.UpdateHandler;

// NOTE: update4j 1.5.9 requires Java 9+ runtime (module system).
// This project compiles to Java 8 bytecode but must be run on JRE 9+.
// IDE may show "cannot resolve" for update4j imports — compilation via Ant will succeed.
public class SoftPOSUpdateHandler implements UpdateHandler {

    private final UpdateSplashFrame splash;
    private int totalFiles = 0;
    private int doneFiles = 0;

    public SoftPOSUpdateHandler(UpdateSplashFrame splash) {
        this.splash = splash;
    }

    @Override
    public void startDownloads() throws Throwable {
        splash.setStatus("กำลังเตรียมดาวน์โหลดอัพเดท...", 30);
    }

    @Override
    public void startDownloadFile(FileMetadata file) throws Throwable {
        totalFiles++;
        String name = file.getPath().getFileName().toString();
        splash.setStatus("กำลังดาวน์โหลด: " + name, 35);
    }

    @Override
    public void updateDownloadFileProgress(FileMetadata file, float frac) throws Throwable {
        String name = file.getPath().getFileName().toString();
        splash.setDownloadProgress(name, frac);
    }

    @Override
    public void updateDownloadProgress(float frac) throws Throwable {
        int pct = 30 + (int) (frac * 60);
        splash.setStatus("กำลังดาวน์โหลดอัพเดท... " + (int) (frac * 100) + "%", pct);
    }

    @Override
    public void doneDownloadFile(FileMetadata file, Path tempFile) throws Throwable {
        doneFiles++;
        String name = file.getPath().getFileName().toString();
        int pct = 35 + (int) ((float) doneFiles / Math.max(totalFiles, 1) * 55);
        splash.setStatus("ดาวน์โหลดสำเร็จ: " + name, pct);
    }

    @Override
    public void doneDownloads() throws Throwable {
        splash.setStatus("ดาวน์โหลดอัพเดทสำเร็จ!", 92);
    }

    @Override
    public void failed(Throwable t) {
        splash.setError("อัพเดทล้มเหลว: " + t.getMessage() + " — กำลังเปิดโปรแกรมเวอร์ชันเดิม");
    }

    @Override
    public void succeeded() {
        splash.setStatus("อัพเดทเรียบร้อย!", 95);
    }
}
