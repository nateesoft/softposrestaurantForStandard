package com.softpos.launcher;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class UpdateSplashFrame {

    private JFrame frame;
    private JProgressBar progressBar;
    private JLabel statusLabel;
    private JLabel versionLabel;

    public void show() {
        frame = new JFrame("SoftPOS Restaurant");
        frame.setUndecorated(true);
        frame.setSize(480, 220);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(30, 40, 55));
        root.setBorder(BorderFactory.createLineBorder(new Color(60, 120, 200), 2));
        frame.setContentPane(root);

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.setBorder(new EmptyBorder(24, 28, 10, 28));

        JLabel title = new JLabel("SoftPOS Restaurant");
        title.setFont(new Font("Tahoma", Font.BOLD, 22));
        title.setForeground(new Color(255, 255, 255));
        top.add(title, BorderLayout.WEST);

        versionLabel = new JLabel("Launcher");
        versionLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
        versionLabel.setForeground(new Color(150, 170, 200));
        top.add(versionLabel, BorderLayout.EAST);

        root.add(top, BorderLayout.NORTH);

        JPanel mid = new JPanel(new BorderLayout(0, 8));
        mid.setOpaque(false);
        mid.setBorder(new EmptyBorder(0, 28, 6, 28));

        statusLabel = new JLabel("กำลังเริ่มต้นระบบ...");
        statusLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        statusLabel.setForeground(new Color(200, 215, 235));
        mid.add(statusLabel, BorderLayout.NORTH);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setString("");
        progressBar.setPreferredSize(new Dimension(0, 22));
        progressBar.setForeground(new Color(60, 140, 255));
        progressBar.setBackground(new Color(50, 60, 80));
        progressBar.setBorderPainted(false);
        progressBar.setOpaque(true);
        mid.add(progressBar, BorderLayout.CENTER);

        root.add(mid, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setOpaque(false);
        bottom.setBorder(new EmptyBorder(0, 20, 14, 20));
        JLabel copy = new JLabel("© SoftPOS Co., Ltd.");
        copy.setFont(new Font("Tahoma", Font.PLAIN, 10));
        copy.setForeground(new Color(100, 120, 150));
        bottom.add(copy);
        root.add(bottom, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void setStatus(final String message, final int progress) {
        SwingUtilities.invokeLater(() -> {
            if (statusLabel != null) statusLabel.setText(message);
            if (progressBar != null) {
                progressBar.setValue(progress);
                progressBar.setString(progress + "%");
            }
        });
    }

    public void setDownloadProgress(final String filename, final float frac) {
        SwingUtilities.invokeLater(() -> {
            int pct = (int) (frac * 100);
            if (statusLabel != null)
                statusLabel.setText("กำลังดาวน์โหลด: " + filename + " (" + pct + "%)");
            if (progressBar != null) {
                progressBar.setValue(30 + (int) (frac * 60));
                progressBar.setString((30 + (int) (frac * 60)) + "%");
            }
        });
    }

    public void setError(final String message) {
        SwingUtilities.invokeLater(() -> {
            if (statusLabel != null) statusLabel.setText(message);
            if (progressBar != null) progressBar.setForeground(new Color(220, 80, 60));
        });
    }

    public void close() {
        SwingUtilities.invokeLater(() -> {
            if (frame != null) frame.dispose();
        });
    }
}
