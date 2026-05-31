package com.softpos.util;

import java.awt.Component;
import java.awt.HeadlessException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class MSG {

    public static void ERR(Component com, String error) {
        AppLogUtil.info("[MODAL_ERROR]: " + error);
        try {
            URL imgURL = MSG.class.getResource("/images/error64.png");
            JOptionPane.showMessageDialog(com, error, "Error Message", JOptionPane.OK_OPTION, new ImageIcon(imgURL));
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(com, error);
        }
    }

    public static void WAR(Component com, String warning) {
        AppLogUtil.info("[MODAL_WARNING]: " + warning);
        try {
            URL imgURL = MSG.class.getResource("/images/warning64.png");
            JOptionPane.showMessageDialog(com, warning, "Warning Message", JOptionPane.OK_OPTION, new ImageIcon(imgURL));
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(com, warning);
        }
    }

    public static void NOTICE(Component com, String notice) {
        AppLogUtil.info("[MODAL_INFO]: " + notice);
        try {
            URL imgURL = MSG.class.getResource("/images/notice64.png");
            JOptionPane.showMessageDialog(com, notice, "Notice Message", JOptionPane.OK_OPTION, new ImageIcon(imgURL));
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(com, notice);
        }
    }

    public static boolean CONF(Component com, String confirm) {
        AppLogUtil.info("[MODAL_CONFIRM]: " + confirm);
        boolean success = false;
        try {
            URL imgURL = MSG.class.getResource("/images/warning64.png");
            int rs = JOptionPane.showConfirmDialog(com, confirm, "Confirm Dialog", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(imgURL));
            if (rs == JOptionPane.YES_OPTION) {
                AppLogUtil.info("[MODAL_CONFIRM]: ACCEPT");
                success = true;
            } else {
                AppLogUtil.info("[MODAL_CONFIRM]: NOT ACCEPT");
            }
        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(com, ex.getMessage());
        }

        return success;
    }

}
