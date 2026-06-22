package com.softpos.pos.core.controller;

import com.softpos.util.ThaiUtil;
import com.softpos.pos.core.model.MgrButtonSetupBean;
import com.softpos.pos.core.model.OptionSetBean;
import com.softpos.pos.core.model.SoftMenuSetup;
import com.softpos.connection.database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.softpos.util.AppLogUtil;

/**
 *
 * @author nathee
 */
public class ModalPopupControl {
    
    private final MySQLConnect mysqlConnect = new MySQLConnect();

    public List<OptionSetBean> getListOptionSet(String pCode) {
        List<OptionSetBean> listOption = new ArrayList<>();

        
        mysqlConnect.open(this.getClass());
        try {
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select * from optionset where pcode='" + pCode + "';");
            while (rs.next()) {
                OptionSetBean bean = new OptionSetBean();
                bean.setPcode(rs.getString("pcode"));
                bean.setPdesc(ThaiUtil.ASCII2Unicode(rs.getString("pdesc")));
                bean.setOptionName(ThaiUtil.ASCII2Unicode(rs.getString("OptionName")));

                listOption.add(bean);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {

            AppLogUtil.log(ModalPopupControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return listOption;
    }

    public List<MgrButtonSetupBean> getListMgrButtonSetupByPCode(String pCode, String type) {
        List<MgrButtonSetupBean> listOption = new ArrayList<>();

        mysqlConnect.open(this.getClass());
        try {
            Statement stmt = mysqlConnect.getConnection().createStatement();
            String sql = "select * from mgrbuttonsetup "
                    + "where pcode='" + pCode + "' ";
            if (type.equals("sd")) {
                sql += " and sd_pcode<>'' ";
            } else if (type.equals("ex")) {
                sql += " and ex_pcode<>'' ";
            }
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                MgrButtonSetupBean bean = new MgrButtonSetupBean();
                bean.setSd_pcode(rs.getString("sd_pcode"));
                bean.setSd_pdesc(ThaiUtil.ASCII2Unicode(rs.getString("sd_pdesc")));
                bean.setEx_pcode(rs.getString("ex_pcode"));
                bean.setEx_pdesc(ThaiUtil.ASCII2Unicode(rs.getString("ex_pdesc")));

                listOption.add(bean);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {

            AppLogUtil.log(ModalPopupControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return listOption;
    }

    public List<SoftMenuSetup> loadSoftMenuSetupByMenuCode(String menuSub) {
        List<SoftMenuSetup> listOption = new ArrayList<>();

        mysqlConnect.open(this.getClass());
        try {
            Statement stmt = mysqlConnect.getConnection().createStatement();
            String sql = "select PCode, MenuShowText from soft_menusetup "
                    + "where menucode like '" + menuSub + "%' and menutype='1'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                SoftMenuSetup bean = new SoftMenuSetup();
                bean.setPCode(rs.getString("PCode"));
                bean.setMenuShowText(ThaiUtil.ASCII2Unicode(rs.getString("MenuShowText")));

                listOption.add(bean);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {

            AppLogUtil.log(ModalPopupControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return listOption;
    }
}
