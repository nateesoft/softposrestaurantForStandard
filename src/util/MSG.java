package util;

import java.awt.Component;
import java.awt.HeadlessException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class MSG {

    public static void ERR(Component com, String error) {
        try {
            URL imgURL = MSG.class.getResource("/util/error64.png");
            JOptionPane.showMessageDialog(com, error, "Error Message", JOptionPane.OK_OPTION, new ImageIcon(imgURL));
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(com, error);
        }
    }

    public static void WAR(Component com, String warning) {
        try {
            URL imgURL = MSG.class.getResource("/util/warning64.png");
            JOptionPane.showMessageDialog(com, warning, "Warning Message", JOptionPane.OK_OPTION, new ImageIcon(imgURL));
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(com, warning);
        }
    }

    public static void NOTICE(Component com, String notice) {
        try {
            URL imgURL = MSG.class.getResource("/util/notice64.png");
            JOptionPane.showMessageDialog(com, notice, "Notice Message", JOptionPane.OK_OPTION, new ImageIcon(imgURL));
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(com, notice);
        }
    }

    public static boolean CONF(Component com, String confirm) {
        boolean success = false;
        try {
            URL imgURL = MSG.class.getResource("/util/warning64.png");
            int rs = JOptionPane.showConfirmDialog(com, confirm, "Confirm Dialog", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(imgURL));
            if (rs == JOptionPane.YES_OPTION) {
                success = true;
            }
        } catch (HeadlessException ex) {
            int rs = JOptionPane.showConfirmDialog(com, confirm);
            if (rs == JOptionPane.YES_OPTION) {
                success = true;
            }
        }
        return success;
    }

}
