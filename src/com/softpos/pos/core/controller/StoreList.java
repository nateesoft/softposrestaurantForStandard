package com.softpos.pos.core.controller;

import com.softpos.pos.core.controller.ThaiUtil;
import com.softpos.pos.core.model.ListButtonBean;
import database.MySQLConnect;
import java.sql.SQLException;
import java.sql.Statement;
import util.AppLogUtil;
import util.MSG;

public class StoreList {

    public boolean store(ListButtonBean bean) {
        String sql = "";
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());
        try {
            sql = "INSERT INTO menusetup (code_id,code_type,pcode,shortname,ppathname,pcolor)"
                    + " VALUES ('" + bean.getButtonName() + "','" + bean.getButtonType().toCharArray()[0] + "',"
                    + "'','" + ThaiUtil.Unicode2ASCII(bean.getShortDesc()) + "','" + bean.getPicture() + "','" + bean.getPcolor() + "')";
            int i;
            try (Statement stmt = mysql.getConnection().createStatement()) {
                i = stmt.executeUpdate(sql);
            }
            return i > 0;
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(StoreList.class, "error", e);

            return false;
        } finally {
            mysql.close();
        }
    }

    public boolean storeUpdate(ListButtonBean bean) {
        String sql = "";
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());
        try {
            sql = "UPDATE menusetup SET "
                    + "code_type = '" + bean.getButtonType().toCharArray()[0] + "',"
                    + "pcode = '',"
                    + "shortname = '" + ThaiUtil.Unicode2ASCII(bean.getShortDesc()) + "',"
                    + "ppathname = '" + bean.getPicture() + "',"
                    + "pcolor='" + bean.getPcolor() + "' "
                    + "WHERE code_id = '" + bean.getButtonName() + "'";
            int i;
            try (Statement stmt = mysql.getConnection().createStatement()) {
                i = stmt.executeUpdate(sql);
            }

            return i > 0;
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(StoreList.class, "error", e);

            return false;
        } finally {
            mysql.close();
        }
    }
}
