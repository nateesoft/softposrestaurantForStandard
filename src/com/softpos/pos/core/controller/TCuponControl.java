package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.TCuponBean;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.AppLogUtil;
import util.MSG;

public class TCuponControl extends DatabaseConnection {

    public List<TCuponBean> listTCupon() {
        List<TCuponBean> listBean = new ArrayList<>();
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());
        try {
            String sql = "select * from t_cupon";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                TCuponBean bean = new TCuponBean();
                bean.setR_Index(rs.getString("R_Index"));
                bean.setR_Refno(rs.getString("R_Refno"));
                bean.setTerminal(rs.getString("Terminal"));
                bean.setCashier(rs.getString("Cashier"));
                bean.setTime(rs.getString("Time"));
                bean.setCuCode(rs.getString("CuCode"));
                bean.setCuQuan(rs.getInt("CuQuan"));
                bean.setCuAmt(rs.getFloat("CuAmt"));
                bean.setRefund(rs.getString("Refund"));
                bean.setCuTextCode(rs.getString("CuTextCode"));
                bean.setCuTextComment(rs.getString("CuTextComment"));

                listBean.add(bean);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(TCuponControl.class, "error", e);
        } finally {
            mysql.close();
        }

        return listBean;
    }

    public List<TCuponBean> listTCupon(String R_Index) {
        List<TCuponBean> listBean = new ArrayList<>();
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());
        try {
            String sql = "select * from t_cupon "
                    + "where R_Index='" + R_Index + "'";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                TCuponBean bean = new TCuponBean();

                bean.setR_Index(rs.getString("R_Index"));
                bean.setR_Refno(rs.getString("R_Refno"));
                bean.setTerminal(rs.getString("Terminal"));
                bean.setCashier(rs.getString("Cashier"));
                bean.setTime(rs.getString("Time"));
                bean.setCuCode(rs.getString("CuCode"));
                bean.setCuQuan(rs.getInt("CuQuan"));
                bean.setCuAmt(rs.getFloat("CuAmt"));
                bean.setRefund(rs.getString("Refund"));
                bean.setCuTextCode(rs.getString("CuTextCode"));
                bean.setCuTextComment(rs.getString("CuTextComment"));

                listBean.add(bean);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(TCuponControl.class, "error", e);
        } finally {
            mysql.close();
        }

        return listBean;
    }

    public TCuponBean getTCupon(String R_Index) {
        TCuponBean bean = null;
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());
        try {
            String sql = "select * from t_cupon where R_Index='" + R_Index + "' limit 1";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                bean = new TCuponBean();
                bean.setR_Index(rs.getString("R_Index"));
                bean.setR_Refno(rs.getString("R_Refno"));
                bean.setTerminal(rs.getString("Terminal"));
                bean.setCashier(rs.getString("Cashier"));
                bean.setTime(rs.getString("Time"));
                bean.setCuCode(rs.getString("CuCode"));
                bean.setCuQuan(rs.getInt("CuQuan"));
                bean.setCuAmt(rs.getFloat("CuAmt"));
                bean.setRefund(rs.getString("Refund"));
                bean.setCuTextCode(rs.getString("CuTextCode"));
                bean.setCuTextComment(rs.getString("CuTextComment"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(TCuponControl.class, "error", e);
        } finally {
            mysql.close();
        }

        return bean;
    }

    public boolean saveTCupon(TCuponBean bean) {
        boolean isResult = false;
        if (bean.getTerminal() == null || bean.getCuCode() == null) {
            bean.setTerminal("");
            bean.setCuCode("");
        } else {
            if (getTCupon(bean.getR_Index()) != null) {
                isResult = updateTCupon(bean);
            } else {
                String sql = "insert into t_cupon "
                        + "(R_Index,R_Refno,Terminal,Cashier,Time,CuCode,CuQuan,"
                        + "CuAmt,Refund,CuTextCode,CuTextComment)  "
                        + "values('" + bean.getR_Index() + "','" + bean.getR_Refno() + "',"
                        + "'" + bean.getTerminal() + "','" + bean.getCashier() + "','" + bean.getTime() + "',"
                        + "'" + bean.getCuCode() + "','" + bean.getCuQuan() + "','" + bean.getCuAmt() + "',"
                        + "'" + bean.getRefund() + "','" + bean.getCuTextCode() + "','" + bean.getCuTextComment() + "')";
                this.execUpdate(sql);
                isResult = true;
            }
        }

        return isResult;
    }

    public boolean updateTCupon(TCuponBean bean) {
        String sql = "update t_cupon "
                + "set R_Index='" + bean.getR_Index() + "', "
                + "R_Refno='" + bean.getR_Refno() + "', "
                + "Terminal='" + bean.getTerminal() + "', "
                + "Cashier='" + bean.getCashier() + "', "
                + "Time='" + bean.getTime() + "', "
                + "CuCode='" + bean.getCuCode() + "', "
                + "CuQuan='" + bean.getCuQuan() + "', "
                + "CuAmt='" + bean.getCuAmt() + "', "
                + "Refund='" + bean.getRefund() + "', "
                + "CuTextCode='" + bean.getCuTextCode() + "', "
                + "CuTextComment='" + bean.getCuTextComment() + "' "
                + "where R_Index='" + bean.getR_Index() + "'";
        return this.execUpdate(sql);
    }
}
