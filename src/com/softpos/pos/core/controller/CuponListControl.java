package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.CuponlistBean;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.softpos.util.AppLogUtil;

public class CuponListControl {

    private final MySQLConnect mysqlConnect = new MySQLConnect();
    
    public List<CuponlistBean> listCuponlist() {
        /**
         * * OPEN CONNECTION **
         */
        
        mysqlConnect.open(this.getClass());

        List<CuponlistBean> listBean = new ArrayList<>();
        try {
            String sql = "select * from cuponlist";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                CuponlistBean bean = new CuponlistBean();

                bean.setCuCode(rs.getString("CuCode"));
                bean.setPCode(rs.getString("PCode"));

                listBean.add(bean);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {

            AppLogUtil.log(CuponListControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return listBean;
    }

    public List<CuponlistBean> listCuponlist(String CuCode) {
        /**
         * * OPEN CONNECTION **
         */
        mysqlConnect.open(this.getClass());

        List<CuponlistBean> listBean = new ArrayList<>();
        try {
            String sql = "select * from cuponlist "
                    + "where CuCode='" + CuCode + "'";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                CuponlistBean bean = new CuponlistBean();

                bean.setCuCode(rs.getString("CuCode"));
                bean.setPCode(rs.getString("PCode"));

                listBean.add(bean);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {

            AppLogUtil.log(CuponListControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return listBean;
    }

    public CuponlistBean getCuponlist(String CuCode) {
        /**
         * * OPEN CONNECTION **
         */
        mysqlConnect.open(this.getClass());

        CuponlistBean bean = new CuponlistBean();
        try {
            String sql = "select * from cuponlist where CuCode='" + CuCode + "' limit 1";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                bean.setCuCode(rs.getString("CuCode"));
                bean.setPCode(rs.getString("PCode"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {

            AppLogUtil.log(CuponListControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return bean;
    }

    public void saveCuponlist(CuponlistBean bean) {
        /**
         * * OPEN CONNECTION **
         */
        mysqlConnect.open(this.getClass());
        try {
            String sql = "insert into cuponlist (CuCode,PCode)  "
                    + "values('" + bean.getCuCode() + "','" + bean.getPCode() + "')";
            String sqlChk = "select cucode from cuponlist where CuCode='" + bean.getCuCode() + "' limit 1";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sqlChk);
            if (rs.next()) {
                updateCuponlist(bean);
            } else {
                stmt.executeUpdate(sql);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {

            AppLogUtil.log(CuponListControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
    }

    public void updateCuponlist(CuponlistBean bean) {
        /**
         * * OPEN CONNECTION **
         */
        mysqlConnect.open(this.getClass());

        try {
            String sql = "update cuponlist set "
                    + "CuCode='" + bean.getCuCode() + "', "
                    + "PCode='" + bean.getPCode() + "' "
                    + "where CuCode='" + bean.getCuCode() + "'";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {

            AppLogUtil.log(CuponListControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
    }
}
