package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.TempCuponBean;
import com.softpos.connection.database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.softpos.util.AppLogUtil;

public class TempCuponController {
    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    private final MySQLConnect mysqlConnect = new MySQLConnect();

    public TempCuponBean getTempcupon(String R_Index) {
        TempCuponBean bean = new TempCuponBean();
        /**
         * * OPEN CONNECTION **
         */
        mysqlConnect.open(this.getClass());
        try {
            String sql = "select * from tempcupon where R_Index='" + R_Index + "' limit 1";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                bean.setR_Index(rs.getString("R_Index"));
                bean.setR_Table(rs.getString("R_Table"));
                bean.setTerminal(rs.getString("Terminal"));
                bean.setCashier(rs.getString("Cashier"));
                bean.setTime(rs.getString("Time"));
                bean.setCuCode(rs.getString("CuCode"));
                bean.setCuQuan(rs.getInt("CuQuan"));
                bean.setCuAmt(rs.getFloat("CuAmt"));
                bean.setCuTotal(rs.getFloat("CuTotal"));
                bean.setCuDisc(rs.getFloat("CuDisc"));
                bean.setCuRedule(rs.getFloat("CuRedule"));
                bean.setCuPayment(rs.getFloat("CuPayment"));
                bean.setCuTextCode(rs.getString("CuTextCode"));
                bean.setCuTextComment(rs.getString("CuTextComment"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {

            AppLogUtil.log(TempCuponController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return bean;
    }

    public void clearTempOld(String index) {
        databaseConnection.execUpdate("delete from tempcupon where r_index='" + index + "'");
        databaseConnection.execUpdate("delete from tempgift");
    }

    public void saveTempCupon(TempCuponBean bean) {
        /**
         * * OPEN CONNECTION **
         */
        mysqlConnect.open(this.getClass());
        try {
            SimpleDateFormat simp = new SimpleDateFormat("HH:mm");
            String sql = "insert into tempcupon"
                    + "(R_Index,R_Table,Terminal,Cashier,Time,CuCode,CuQuan,CuAmt,"
                    + "CuTotal,CuDisc,CuRedule,CuPayment,CuTextCode,CuTextComment) "
                    + "values('" + bean.getR_Index() + "','" + bean.getR_Table() + "','" + bean.getTerminal() + "',"
                    + "'" + bean.getCashier() + "','" + simp.format(new Date()) + "','" + bean.getCuCode() + "','" + bean.getCuQuan() + "',"
                    + "'" + bean.getCuAmt() + "','" + bean.getCuTotal() + "','" + bean.getCuDisc() + "','" + bean.getCuRedule() + "',"
                    + "'" + bean.getCuPayment() + "','" + bean.getCuTextCode() + "','" + bean.getCuTextComment() + "')";

            String sql1 = "select r_index from tempcupon where r_index='" + bean.getR_Index() + "' limit 1";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql1);
            if (rs.next()) {
                String sqlDel = "delete from tempcupon where r_index='" + bean.getR_Index() + "' ";
                Statement stmt1 = mysqlConnect.getConnection().createStatement();
                stmt1.executeUpdate(sqlDel);
                stmt1.close();
            }

            rs.close();
            stmt.close();

            //insert data
            Statement stmt1 = mysqlConnect.getConnection().createStatement();
            stmt1.executeUpdate(sql);
            stmt1.close();
        } catch (SQLException e) {

            AppLogUtil.log(TempCuponController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
    }

}
