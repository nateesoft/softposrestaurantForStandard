package com.softpos.pos.core.controller;

import com.softpos.pos.core.controller.ThaiUtil;
import com.softpos.pos.core.model.PluButtonBean;
import database.MySQLConnect;
import java.sql.SQLException;
import java.sql.Statement;
import util.AppLogUtil;
import util.MSG;

public class StorePlu {

    public boolean store(PluButtonBean bean) {
        String sql = "";
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            sql = "INSERT INTO menusetup (code_id,code_type,pcode,shortname,ppathname,pcolor)"
                    + " VALUES ('" + bean.getButtonName() + "','" + bean.getButtonType().toCharArray()[0] + "',"
                    + "'" + bean.getPcode() + "','" + ThaiUtil.Unicode2ASCII(bean.getShortDesc()) + "','" + bean.getPicture() + "','" + bean.getPcolor() + "')";
            int i;
            try (Statement stmt = mysql.getConnection().createStatement()) {
                i = stmt.executeUpdate(sql);
            }
            return i > 0;
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(StorePlu.class, "error", e);
            
            return false;
        } finally {
            mysql.close();
        }
    }

    public boolean storeUpdate(PluButtonBean bean) {
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "UPDATE menusetup SET "
                    + "code_type = '" + bean.getButtonType().toCharArray()[0] + "',"
                    + "pcode = '" + bean.getPcode() + "',"
                    + "shortname = '" + ThaiUtil.Unicode2ASCII(bean.getShortDesc()) + "',"
                    + "ppathname = '" + bean.getPicture() + "',"
                    + "pcolor='" + bean.getPcolor() + "' "
                    + "WHERE code_id = '" + bean.getButtonName() + "'";
            Statement stmt = mysql.getConnection().createStatement();
            int i = stmt.executeUpdate(sql);
            stmt.close();
            return i > 0;
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(StorePlu.class, "error", e);
            
            return false;
        } finally {
            mysql.close();
        }
    }
}
