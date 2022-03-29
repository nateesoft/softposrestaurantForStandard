package com.softpos.pos.core.controller;

import database.MySQLConnect;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import util.MSG;

/**
 *
 * @author nathee
 */
public class ButtonCustom {

    public JButton getButtonLayout(String menuCode, int menuIndex) {
        return new JButton();
    }

    public MenuMGR getDataButtonLayout(String menuCode, int menuIndex) {
        MenuMGR menuMGR = new MenuMGR();
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "select * from soft_menusetup "
                    + "where MenuCode='" + menuCode + "' "
                    + "and M_Index='" + menuIndex + "'";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                menuMGR.setMenuCode(rs.getString("MenuCode"));
                menuMGR.setMenuType(rs.getInt("MenuType"));
                menuMGR.setPCode(rs.getString("PCode"));
                menuMGR.setMenuShowText(sun.natee.project.util.ThaiUtil.ASCII2Unicode(rs.getString("MenuShowText")));
                menuMGR.setIMG(rs.getString("IMG"));
                menuMGR.setFontColor(rs.getString("FontColor"));
                menuMGR.setBGColor(rs.getString("BGColor"));
                menuMGR.setLayout(rs.getInt("Layout"));
                menuMGR.setFontSize(rs.getInt("FontSize"));
                menuMGR.setFontName(rs.getString("FontName"));
                menuMGR.setMIndex(rs.getInt("M_Index"));
                menuMGR.setFontAttr(rs.getString("FontAttr"));
                menuMGR.setImgSize(rs.getInt("IMG_SIZE"));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        } finally {
            mysql.close();
        }

        return menuMGR;
    }

    public List<MenuMGR> getDataButtonLayout(String menuCode) {
        List<MenuMGR> listMenu = new ArrayList<>();
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "select * from soft_menusetup "
                    + "where MenuCode like '" + menuCode + "__' order by menucode";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                MenuMGR bean = new MenuMGR();
                bean.setMenuCode(rs.getString("MenuCode"));
                bean.setMenuType(rs.getInt("MenuType"));
                bean.setPCode(rs.getString("PCode"));
                bean.setMenuShowText(sun.natee.project.util.ThaiUtil.ASCII2Unicode(rs.getString("MenuShowText")));
                bean.setIMG(rs.getString("IMG"));
                bean.setFontColor(rs.getString("FontColor"));
                bean.setBGColor(rs.getString("BGColor"));
                bean.setLayout(rs.getInt("Layout"));
                bean.setFontSize(rs.getInt("FontSize"));
                bean.setFontName(rs.getString("FontName"));
                bean.setMIndex(rs.getInt("M_Index"));
                bean.setFontAttr(rs.getString("FontAttr"));
                bean.setImgSize(rs.getInt("IMG_SIZE"));

                listMenu.add(bean);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        } finally {
            mysql.close();
        }

        return listMenu;
    }

    public JButton getButtonLayout(MenuMGR m, JButton button) {
        int fontType = Font.PLAIN;
        int layout = m.getLayout();
        if (m.getFontAttr() != null) {
            switch (m.getFontAttr()) {
                case "B":
                    fontType = Font.BOLD;
                    break;
                case "I":
                    fontType = Font.ITALIC;
                    break;
                case "BI":
                    fontType = Font.BOLD | Font.ITALIC;
                    break;
                default:
                    break;
            }
        }

        if (m.getMenuShowText() == null) {
            return button;
        }

        button.setName(m.getMenuCode());
        if (m.getIMG() == null) {
            m.setIMG("");
        }
        switch (layout) {
            case 0:
                button.setFont(new Font(m.getFontName(), fontType, m.getFontSize()));
                if (!m.getIMG().equals("")) {
                    updateIconFull(button, m.getIMG(), m.getImgSize());
                }
                button.setText("<html>" + m.getMenuShowText() + "</html>");
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                button.setVerticalAlignment(SwingConstants.TOP);
                button.setVerticalTextPosition(SwingConstants.BOTTOM);
                button.setForeground(getColorFormat(m.getFontColor()));
                break;
            case 1:
                button.setFont(new Font(m.getFontName(), fontType, m.getFontSize()));
                if (!m.getIMG().equals("")) {
                    updateIconFull(button, m.getIMG(), m.getImgSize());
                }
                button.setText("<html>" + m.getMenuShowText() + "</html>");
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                button.setVerticalAlignment(SwingConstants.BOTTOM);
                button.setVerticalTextPosition(SwingConstants.TOP);
                button.setForeground(getColorFormat(m.getFontColor()));
                break;
            case 2:
                button.setFont(new Font(m.getFontName(), fontType, m.getFontSize()));
                button.setForeground(getColorFormat(m.getFontColor()));
                if (!m.getIMG().equals("")) {
                    updateIconFull(button, m.getIMG(), m.getImgSize());
                }
                button.setText("<html>" + m.getMenuShowText() + "</html>");
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                break;
            case 3:
                button.setFont(new Font(m.getFontName(), fontType, m.getFontSize()));
                button.setForeground(getColorFormat(m.getFontColor()));
                button.setText("<html><center>" + m.getMenuShowText() + "</center></html>");
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                if (!m.getIMG().equals("")) {
                    updateIconFull(button, m.getIMG(), m.getImgSize());
                }
                break;
            case 4:
                button.setBackground(getColorFormat(m.getBGColor()));
                button.setFont(new Font(m.getFontName(), fontType, m.getFontSize()));
                button.setForeground(getColorFormat(m.getFontColor()));
                button.setText("<html>" + m.getMenuShowText() + "</html>");
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                break;
            case 5:
                button.setFont(new Font(m.getFontName(), fontType, m.getFontSize()));
                if (!m.getIMG().equals("")) {
                    updateIconFull(button, m.getIMG(), m.getImgSize());
                }
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                button.setVerticalTextPosition(SwingConstants.BOTTOM);
                break;
            case 6:
                button.setBackground(getColorFormat(m.getBGColor()));
                button.setFont(new Font(m.getFontName(), fontType, m.getFontSize()));
                if (!m.getIMG().equals("")) {
                    updateIconFull(button, m.getIMG(), m.getImgSize());
                }
                button.setText("<html>" + m.getMenuShowText() + "</html>");
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                button.setVerticalAlignment(SwingConstants.TOP);
                button.setVerticalTextPosition(SwingConstants.BOTTOM);
                button.setForeground(getColorFormat(m.getFontColor()));
                break;
            default:
                break;
        }

        //check empty text name
        if (button.getText().trim().equals("<html>null</html>")) {
            button.setOpaque(false);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        }

        return button;
    }

    void updateIconFull(JButton button, String img, int imgSize) {
        //full button image
        ImageIcon icon = new ImageIcon(img);
        int scale = 1;
        int width = imgSize;//icon.getIconWidth();
        if (width <= 0) {
            width = 125;
        }
        int newWidth = width / scale;
        button.setIcon(new ImageIcon(icon.getImage().getScaledInstance(newWidth, -1, Image.SCALE_SMOOTH)));
    }

    public JButton buttonDefault(JButton button) {
        button.setText("");
        button.setForeground(null);
        button.setIcon(null);
        button.setBackground(null);
        button.setHorizontalTextPosition(SwingConstants.LEFT);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setRequestFocusEnabled(false);

        for (ActionListener al : button.getActionListeners()) {
            button.removeActionListener(al);
        }

        return button;
    }

    public Color getColorFormat(String bgColor) {
        int red = 240, green = 240, blue = 240;
        if (bgColor != null) {
            String[] color = bgColor.split(",");
            if (color.length == 3) {
                try {
                    red = Integer.parseInt(color[0]);
                    green = Integer.parseInt(color[1]);
                    blue = Integer.parseInt(color[2]);
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return new Color(red, green, blue);
    }
}
