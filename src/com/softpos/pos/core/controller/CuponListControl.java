package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.CuponlistBean;
import com.softpos.connection.database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.softpos.util.AppLogUtil;

public class CuponListControl {

    private final MySQLConnect mysqlConnect = new MySQLConnect();
    
    public List<CuponlistBean> listCuponlist() {
        mysqlConnect.open(this.getClass());

        List<CuponlistBean> listBean = new ArrayList<>();
        try {
            String sql = "select * from cuponlist";
            try (Statement stmt = mysqlConnect.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    CuponlistBean bean = new CuponlistBean();
                    
                    bean.setCuCode(rs.getString("CuCode"));
                    bean.setPCode(rs.getString("PCode"));
                    
                    listBean.add(bean);
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(CuponListControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return listBean;
    }

    public List<CuponlistBean> listCuponlist(String CuCode) {
        mysqlConnect.open(this.getClass());

        List<CuponlistBean> listBean = new ArrayList<>();
        try {
            String sql = "select * from cuponlist "
                    + "where CuCode='" + CuCode + "'";
            try (Statement stmt = mysqlConnect.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    CuponlistBean bean = new CuponlistBean();
                    
                    bean.setCuCode(rs.getString("CuCode"));
                    bean.setPCode(rs.getString("PCode"));
                    
                    listBean.add(bean);
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(CuponListControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return listBean;
    }

    public CuponlistBean getCuponlist(String CuCode) {
        mysqlConnect.open(this.getClass());

        CuponlistBean bean = new CuponlistBean();
        try {
            String sql = "select * from cuponlist where CuCode='" + CuCode + "' limit 1";
            try (Statement stmt = mysqlConnect.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    bean.setCuCode(rs.getString("CuCode"));
                    bean.setPCode(rs.getString("PCode"));
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(CuponListControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return bean;
    }

}
