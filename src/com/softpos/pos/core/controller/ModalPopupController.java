package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.MgrButtonSetupBean;
import com.softpos.pos.core.model.OptionSetBean;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.AppLogUtil;
import util.MSG;

/**
 *
 * @author nathee
 */
public class ModalPopupController extends DatabaseConnection {

    public List<OptionSetBean> getListOptionSet(String pCode) {
        List<OptionSetBean> listOption = new ArrayList<>();

        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());
        try {
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select * from optionset where pcode='" + pCode + "'");
            while (rs.next()) {
                OptionSetBean bean = new OptionSetBean();
                bean.setPcode(rs.getString("pcode"));
                bean.setPdesc(ThaiUtil.ASCII2Unicode(rs.getString("pdesc")));
                bean.setOptionName(ThaiUtil.ASCII2Unicode(rs.getString("OptionName")));

                listOption.add(bean);
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(ModalPopupController.class, "error", e);
        } finally {
            mysql.close();
        }

        return listOption;
    }

    public List<MgrButtonSetupBean> getListMgrButtonSetupByPCode(String pCode, String type) {
        List<MgrButtonSetupBean> listOption = new ArrayList<>();

        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());
        try {
            Statement stmt = mysql.getConnection().createStatement();
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
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(ModalPopupController.class, "error", e);
        } finally {
            mysql.close();
        }

        return listOption;
    }
}
