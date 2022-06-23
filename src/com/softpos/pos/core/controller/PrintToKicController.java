package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.BalanceBean;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.AppLogUtil;
import util.MSG;

/**
 *
 * @author nathee
 */
public class PrintToKicController extends DatabaseConnection {

    public BalanceBean getBalaneForPDA() {
        BalanceBean bean = null;

        MySQLConnect mysql = new MySQLConnect();
        mysql.open(PrintToKicController.class);
        try {
            String sql = "select r_table,"
                    + "sum(b.r_quan) qty,sum(b.r_total) total from balance b "
                    + "where trantype='PDA' "
                    + "and r_kicprint<>'P' and r_void<>'V' "
                    + "and r_kic<>'0' and r_printOK='Y' and r_pause='P' "
                    + "group by r_table order by r_etd,r_index;";
            System.out.println(sql);
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                if (rs.next()) {
                    bean = new BalanceBean();
                    bean.setR_Table(rs.getString("r_table"));
                    bean.setR_Total(rs.getDouble("total"));
                }
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(PrintToKicController.class, "error", e);
        } finally {
            mysql.close();
        }

        return bean;
    }

    public List<BalanceBean> getBalaneForPDAByTableNo(String tableNo) {
        List<BalanceBean> listBalance = new ArrayList<>();

        MySQLConnect mysql = new MySQLConnect();
        mysql.open(PrintToKicController.class);
        try {
            String sql = "select r_kic,r_etd from balance "
                    + "where r_table='" + tableNo + "' "
                    + "and R_PrintOK='Y' "
                    + "and R_KicPrint<>'P' "
                    + "and R_Kic<>'0' "
                    + "and R_Void<>'V' "
                    + "group by r_kic,r_etd "
                    + "order by r_kic, r_etd";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                if (rs.next()) {
                    BalanceBean bean = new BalanceBean();
                    bean.setR_Kic(rs.getString("r_kic"));
                    bean.setR_ETD(rs.getString("r_etd"));

                    listBalance.add(bean);
                }
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(PrintToKicController.class, "error", e);
        } finally {
            mysql.close();
        }

        return listBalance;
    }

    public List<BalanceBean> getBalancePrintForm1(String tableNo, String rKic) {
        List<BalanceBean> listBalance = new ArrayList<>();

        MySQLConnect mysql = new MySQLConnect();
        mysql.open(PrintToKicController.class);
        try {
            String sql = "select * from balance "
                    + "where r_table='" + tableNo + "' "
                    + "and R_PrintOK='Y' "
                    + "and R_KicPrint<>'P' "
                    + "and R_Kic<>'' "
                    + "and R_Void<>'V' "
                    + "and R_kic='" + rKic + "' "
                    + "group by r_plucode";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                if (rs.next()) {
                    BalanceBean bean = new BalanceBean();
                    bean.setR_PluCode(rs.getString("R_PluCode"));

                    listBalance.add(bean);
                }
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(PrintToKicController.class, "error", e);
        } finally {
            mysql.close();
        }

        return listBalance;
    }

    public List<BalanceBean> getBalancePrintForm6(String tableNo, String rKic) {
        List<BalanceBean> listBalance = new ArrayList<>();

        MySQLConnect mysql = new MySQLConnect();
        mysql.open(PrintToKicController.class);
        try {
            String sql = "select * from balance "
                    + "where r_table='" + tableNo + "' "
                    + "and R_PrintOK='Y' "
                    + "and R_KicPrint<>'P' "
                    + "and R_Kic<>'' "
                    + "and R_Void<>'V' "
                    + "and R_Kic='" + rKic + "'";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                if (rs.next()) {
                    BalanceBean bean = new BalanceBean();
                    bean.setR_PluCode(rs.getString("R_PluCode"));
                    bean.setR_Quan(rs.getDouble("R_Quan"));
                    bean.setR_Total(rs.getDouble("R_Total"));
                    bean.setR_Index(rs.getString("R_Index"));

                    listBalance.add(bean);
                }
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(PrintToKicController.class, "error", e);
        } finally {
            mysql.close();
        }

        return listBalance;
    }
}
