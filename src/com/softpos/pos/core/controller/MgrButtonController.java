package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.MenuMGR;
import com.softpos.util.ThaiUtil;
import com.softpos.connection.database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.softpos.util.AppLogUtil;

public class MgrButtonController {

    private final MySQLConnect mysqlConnect = new MySQLConnect();
    
    // --- MGRButtonSetup ---

    /** Returns {pcode, pdesc} from soft_menusetup join product for given menuCode, or null if not found */
    public String[] getMenuProductByCode(String menuCode) {
        
        mysqlConnect.open(MgrButtonController.class);
        try {
            String sql = "select p.pcode, p.pdesc "
                    + "from soft_menusetup m, product p "
                    + "where m.pcode=p.pcode "
                    + "and menucode='" + menuCode + "' "
                    + "and m.pcode<>'' limit 1";
            try (Statement stmt = mysqlConnect.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    return new String[]{
                        rs.getString("pcode"),
                        ThaiUtil.ASCII2Unicode(rs.getString("pdesc"))
                    };
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(MgrButtonController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(MgrButtonController.class);
        }
        return null;
    }

    /** Returns rows from mgrbuttonsetup for given pCode. Each row: {pcode,pdesc,sd_pcode,sd_pdesc,ex_pcode,ex_pdesc,auto_pcode,auto_pdesc,Check_before,Check_qty,qty,check_autoadd,check_extra} */
    public List<Object[]> getButtonSetupRows(String pCode) {
        List<Object[]> list = new ArrayList<>();
        mysqlConnect.open(MgrButtonController.class);
        try {
            String sql = "select * from mgrbuttonsetup where pcode='" + pCode + "' order by pcode";
            try (Statement stmt = mysqlConnect.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    list.add(new Object[]{
                        rs.getString("pcode"),
                        ThaiUtil.ASCII2Unicode(rs.getString("pdesc")),
                        rs.getString("sd_pcode"),
                        ThaiUtil.ASCII2Unicode(rs.getString("sd_pdesc")),
                        rs.getString("ex_pcode"),
                        ThaiUtil.ASCII2Unicode(rs.getString("ex_pdesc")),
                        rs.getString("auto_pcode"),
                        ThaiUtil.ASCII2Unicode(rs.getString("auto_pdesc")),
                        rs.getString("Check_before"),
                        rs.getString("Check_qty"),
                        rs.getInt("qty"),
                        rs.getString("check_autoadd"),
                        rs.getString("check_extra")
                    });
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(MgrButtonController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(MgrButtonController.class);
        }
        return list;
    }

    public void deleteByPCode(String pCode) {
        mysqlConnect.open(MgrButtonController.class);
        try {
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate("delete from mgrbuttonsetup where pcode='" + pCode + "'");
            }
        } catch (SQLException e) {
            AppLogUtil.log(MgrButtonController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(MgrButtonController.class);
        }
    }

    public void saveButtonSetupRows(String pCode, String pDesc, String beforeCheck, String qtyCheck,
            String extraCheck, int qtyAmt, String autoCheck,
            List<String[]> sideDishList, List<String[]> extraList, List<String[]> autoAddList) {
        mysqlConnect.open(MgrButtonController.class);
        try {
            for (String[] sd : sideDishList) {
                String sql = "insert into mgrbuttonsetup"
                        + "(pcode,pdesc,sd_pcode,sd_pdesc,ex_pcode,ex_pdesc,auto_pcode,auto_pdesc,check_before,check_qty,qty,check_autoadd,check_extra) "
                        + "values('" + pCode + "','" + ThaiUtil.Unicode2ASCII(pDesc) + "',"
                        + "'" + sd[0] + "','" + ThaiUtil.Unicode2ASCII(sd[1]) + "',"
                        + "'','','','','" + beforeCheck + "','" + qtyCheck + "',"
                        + "'" + qtyAmt + "','" + autoCheck + "','" + extraCheck + "')";
                try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                    stmt.executeUpdate(sql);
                }
            }
            for (String[] ex : extraList) {
                String sql = "insert into mgrbuttonsetup"
                        + "(pcode,pdesc,sd_pcode,sd_pdesc,ex_pcode,ex_pdesc,auto_pcode,auto_pdesc,check_before,check_qty,qty,check_autoadd,check_extra) "
                        + "values('" + pCode + "','" + ThaiUtil.Unicode2ASCII(pDesc) + "',"
                        + "'','',"
                        + "'" + ex[0] + "','" + ThaiUtil.Unicode2ASCII(ex[1]) + "',"
                        + "'','','" + beforeCheck + "','" + qtyCheck + "',"
                        + "'" + qtyAmt + "','" + autoCheck + "','" + extraCheck + "')";
                try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                    stmt.executeUpdate(sql);
                }
            }
            for (String[] at : autoAddList) {
                String sql = "insert into mgrbuttonsetup"
                        + "(pcode,pdesc,sd_pcode,sd_pdesc,ex_pcode,ex_pdesc,auto_pcode,auto_pdesc,check_before,check_qty,qty,check_autoadd,check_extra) "
                        + "values('" + pCode + "','" + ThaiUtil.Unicode2ASCII(pDesc) + "',"
                        + "'','','','',"
                        + "'" + at[0] + "','" + ThaiUtil.Unicode2ASCII(at[1]) + "',"
                        + "'" + beforeCheck + "','" + qtyCheck + "',"
                        + "'" + qtyAmt + "','" + autoCheck + "','" + extraCheck + "')";
                try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                    stmt.executeUpdate(sql);
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(MgrButtonController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(MgrButtonController.class);
        }
    }

    public List<String[]> loadSideDish(String pCode) {
        return loadSubList(pCode, "sd_pcode", "sd_pdesc", "sd_pcode<>''");
    }

    public List<String[]> loadExtra(String pCode) {
        return loadSubList(pCode, "ex_pcode", "ex_pdesc", "ex_pcode<>''");
    }

    public List<String[]> loadAutoAdd(String pCode) {
        return loadSubList(pCode, "auto_pcode", "auto_pdesc", "auto_pcode<>''");
    }

    private List<String[]> loadSubList(String pCode, String codeCol, String descCol, String filter) {
        List<String[]> list = new ArrayList<>();
        mysqlConnect.open(MgrButtonController.class);
        try {
            String sql = "select " + codeCol + ", " + descCol + " from mgrbuttonsetup "
                    + "where pcode='" + pCode + "' and " + filter + " order by " + codeCol;
            try (Statement stmt = mysqlConnect.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    list.add(new String[]{rs.getString(1), ThaiUtil.ASCII2Unicode(rs.getString(2))});
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(MgrButtonController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(MgrButtonController.class);
        }
        return list;
    }

    // --- MGRButtonMenu ---

    /** Returns MenuMGR from soft_menusetup by menuCode and menuIndex, null if not found */
    public MenuMGR getMenuSetup(String menuCode, int menuIndex) {
        mysqlConnect.open(MgrButtonController.class);
        try {
            String sql = "select * from soft_menusetup "
                    + "where MenuCode='" + menuCode + "' "
                    + "and M_Index='" + menuIndex + "'";
            try (Statement stmt = mysqlConnect.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    MenuMGR m = new MenuMGR();
                    m.setMenuCode(rs.getString("MenuCode"));
                    m.setMenuType(rs.getInt("MenuType"));
                    m.setOptSet(rs.getString("OptSet"));
                    m.setPSet(rs.getString("PSet"));
                    m.setPCode(rs.getString("PCode"));
                    m.setMenuShowText(ThaiUtil.ASCII2Unicode(rs.getString("MenuShowText")));
                    m.setIMG(rs.getString("IMG"));
                    m.setFontColor(rs.getString("FontColor"));
                    m.setBGColor(rs.getString("BGColor"));
                    m.setLayout(rs.getInt("Layout"));
                    m.setFontSize(rs.getInt("FontSize"));
                    m.setFontName(rs.getString("FontName"));
                    m.setMIndex(rs.getInt("M_Index"));
                    m.setFontAttr(rs.getString("FontAttr"));
                    return m;
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(MgrButtonController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(MgrButtonController.class);
        }
        return null;
    }

    /** Returns PDesc of active product, or empty string if not found */
    public String getProductDesc(String pCode) {
        mysqlConnect.open(MgrButtonController.class);
        try {
            String sql = "select PDesc from product where pcode='" + pCode + "' and PActive='Y' limit 1";
            try (Statement stmt = mysqlConnect.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    return ThaiUtil.ASCII2Unicode(rs.getString("PDesc"));
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(MgrButtonController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(MgrButtonController.class);
        }
        return "";
    }

    public void deleteMenuSetup(String menuCode, String shortNameAscii) {
        mysqlConnect.open(MgrButtonController.class);
        try {
            String sql = "delete from soft_menusetup "
                    + "where MenuCode ='" + menuCode + "'"
                    + "AND MenuShowText='" + ThaiUtil.Unicode2ASCII(shortNameAscii) + "'";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            AppLogUtil.log(MgrButtonController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(MgrButtonController.class);
        }
    }

    /** Saves (replaces) menu setup; returns true on success */
    public boolean saveMenuSetup(MenuMGR mgr) {
        mysqlConnect.open(MgrButtonController.class);
        try {
            String sql = "insert into soft_menusetup"
                    + "(MenuCode,MenuType,OptSet,PSet,PCode,MenuShowText,"
                    + "IMG,FontColor,BGColor,Layout,"
                    + "FontSize,FontName,FontAttr,M_Index,IMG_SIZE) "
                    + "values("
                    + "'" + mgr.getMenuCode() + "','" + mgr.getMenuType() + "','" + mgr.getOptSet() + "','" + mgr.getPSet() + "',"
                    + "'" + mgr.getPCode() + "','" + ThaiUtil.Unicode2ASCII(mgr.getMenuShowText()) + "',"
                    + "'" + mgr.getIMG() + "','" + mgr.getFontColor() + "','" + mgr.getBGColor() + "','" + mgr.getLayout() + "',"
                    + "'" + mgr.getFontSize() + "','" + mgr.getFontName() + "','" + mgr.getFontAttr() + "'"
                    + ",'" + mgr.getMIndex() + "','" + mgr.getImgSize() + "');";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate("delete from soft_menusetup where MenuCode='" + mgr.getMenuCode() + "'");
                return stmt.executeUpdate(sql) > 0;
            }
        } catch (SQLException e) {
            AppLogUtil.log(MgrButtonController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(MgrButtonController.class);
        }
        return false;
    }

    /** Updates font/color/layout for all items of a menu type; returns true on success */
    public boolean updateMenuSetupAll(MenuMGR mgr) {
        mysqlConnect.open(MgrButtonController.class);
        try {
            String sql = "update soft_menusetup "
                    + "set FontColor='" + mgr.getFontColor() + "',"
                    + "BGColor='" + mgr.getBGColor() + "',"
                    + "Layout='" + mgr.getLayout() + "',"
                    + "FontSize='" + mgr.getFontSize() + "',"
                    + "FontName='" + mgr.getFontName() + "',"
                    + "FontAttr='" + mgr.getFontAttr() + "' "
                    + "where MenuType='" + mgr.getMenuType() + "'";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                return stmt.executeUpdate(sql) > 0;
            }
        } catch (SQLException e) {
            AppLogUtil.log(MgrButtonController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(MgrButtonController.class);
        }
        return false;
    }
}
