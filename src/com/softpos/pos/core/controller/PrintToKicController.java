package com.softpos.pos.core.controller;

import com.softpos.main.program.PrintToKic;
import com.softpos.pos.core.model.BalanceBean;
import com.softpos.pos.core.model.PKicTranBean;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.softpos.util.AppLogUtil;

/**
 *
 * @author nathee
 */
public class PrintToKicController {
    
    private final MySQLConnect mysqlConnect = new MySQLConnect();

    public BalanceBean getBalaneForPDA() {
        BalanceBean bean = null;

        
        mysqlConnect.open(PrintToKicController.class);
        try {
            String sql = "select r_table,macno,"
                    + "sum(b.r_quan) qty,sum(b.r_total) total from balance b "
                    + "where trantype='PDA' "
                    + "and r_kicprint<>'P' and r_void<>'V' "
                    + "and r_kic<>'0' and r_printOK='Y' and r_pause='P' "
                    + "group by r_table order by r_etd,r_index;";
            try (ResultSet rs = mysqlConnect.executeQuery(sql)) {
                if (rs.next()) {
                    PrintToKic.kicPrintting = true;
                    bean = new BalanceBean();
                    bean.setR_Table(rs.getString("r_table"));
                    bean.setR_Total(rs.getDouble("total"));
                    bean.setMacno(rs.getString("macno"));
                }
                rs.close();
            }
        } catch (SQLException e) {
            AppLogUtil.log(PrintToKicController.class, "error" + " : getBalaneForPDA()", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return bean;
    }

    public List<BalanceBean> getBalaneForPDAByTableNo(String tableNo, String macno) {
        List<BalanceBean> listBalance = new ArrayList<>();
        mysqlConnect.open(PrintToKicController.class);
        try {
            String sql = "select r_kic,r_etd from balance "
                    + "where r_table='" + tableNo + "' "
                    + "and R_PrintOK='Y' "
                    + "and TranType='PDA' "
                    + "and R_KicPrint<>'P' "
                    + "and R_Kic<>'0' "
                    + "and R_Void<>'V' "
                    + "and R_Pause='P' "
                    + "and macno='" + macno + "' "
                    + "group by r_kic,r_etd "
                    + "order by r_kic, r_etd";
            try (ResultSet rs = mysqlConnect.executeQuery(sql)) {
                while (rs.next()) {
                    BalanceBean bean = new BalanceBean();
                    bean.setR_Kic(rs.getString("r_kic"));
                    bean.setR_ETD(rs.getString("r_etd"));

                    listBalance.add(bean);
                }
                rs.close();
            }
        } catch (SQLException e) {
            AppLogUtil.log(PrintToKicController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return listBalance;
    }

    public List<BalanceBean> getBalancePrintForm1(String tableNo, String rKic) {
        List<BalanceBean> listBalance = new ArrayList<>();

        mysqlConnect.open(PrintToKicController.class);
        try {
            String sql = "select * from balance "
                    + "where trantype='PDA' "
                    + "and r_table='" + tableNo + "' "
                    + "and R_PrintOK='Y' "
                    + "and R_KicPrint<>'P' "
                    + "and R_Kic<>'' "
                    + "and R_Void<>'V' "
                    + "and R_kic='" + rKic + "' "
                    + "group by r_plucode";
            try (ResultSet rs = mysqlConnect.executeQuery(sql)) {
                if (rs.next()) {
                    BalanceBean bean = new BalanceBean();
                    bean.setR_PluCode(rs.getString("R_PluCode"));

                    listBalance.add(bean);
                }
                rs.close();
            }
        } catch (SQLException e) {
            AppLogUtil.log(PrintToKicController.class, "error" + " getBalaneForPDAByTableNo()", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return listBalance;
    }

    public List<BalanceBean> getBalancePrintForm6(String tableNo, String rKic) {
        List<BalanceBean> listBalance = new ArrayList<>();

        mysqlConnect.open(PrintToKicController.class);
        try {
            String sql = "select * from balance "
                    + "where r_table='" + tableNo + "' "
                    + "and R_PrintOK='Y' "
                    + "and R_KicPrint<>'P' "
                    + "and R_Kic<>'' "
                    + "and R_Void<>'V' "
                    + "and R_Kic='" + rKic + "'";
            try (ResultSet rs = mysqlConnect.executeQuery(sql)) {
                if (rs.next()) {
                    BalanceBean bean = new BalanceBean();
                    bean.setR_PluCode(rs.getString("R_PluCode"));
                    bean.setR_Quan(rs.getDouble("R_Quan"));
                    bean.setR_Total(rs.getDouble("R_Total"));
                    bean.setR_Index(rs.getString("R_Index"));

                    listBalance.add(bean);
                }
                rs.close();
            }
        } catch (SQLException e) {
            AppLogUtil.log(PrintToKicController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return listBalance;
    }

    /**
     * Returns at most one kictran row that is urgent and not yet alerted,
     * for the given kitchen station.
     * Replaces the inline SQL that was in UrgentFoodLoopCheck.getDataFromKicTran().
     */
    public PKicTranBean getUrgentFoodItem(String stationKicNo) {
        PKicTranBean bean = null;
        mysqlConnect.open(PrintToKicController.class);
        try {
            String sql = "select * from kictran "
                    + "where "
                    + "PFlage='N' "
                    + "and R_AlertKitchen='N' "
                    + "and R_FoodUrgent='Y' "
                    + "and pkic='" + stationKicNo + "' "
                    + "limit 1;";
            try (ResultSet rs = mysqlConnect.executeQuery(sql)) {
                if (rs.next() && !rs.wasNull()) {
                    bean = new PKicTranBean();
                    bean.setpTable(rs.getString("PTable"));
                    bean.setR_UrgentFoodItemName(rs.getString("R_UrgentFoodItemName"));
                    bean.setpCode(rs.getString("pcode"));
                    bean.setpIndex(rs.getString("pindex"));
                }
                rs.close();
            }
        } catch (SQLException e) {
            AppLogUtil.log(PrintToKicController.class, "error getUrgentFoodItem()", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
        return bean;
    }

    /**
     * Returns at most one kictran row that is ready for checkout printing,
     * for the given kitchen station.
     * Replaces the SELECT in UrgentFoodLoopCheck.printCheckItOut().
     */
    public PKicTranBean getPendingCheckout(String stationKicNo) {
        PKicTranBean bean = null;
        mysqlConnect.open(PrintToKicController.class);
        try {
            String sql = "select * from kictran "
                    + "where "
                    + "PFlage='Y' "
                    + "and PServe='Y' "
                    + "and R_PrintCheckOut='N' "
                    + "and pkic='" + stationKicNo + "' "
                    + "limit 1;";
            try (ResultSet rs = mysqlConnect.executeQuery(sql)) {
                if (rs.next()) {
                    bean = new PKicTranBean();
                    bean.setpCode(rs.getString("PCode"));
                    bean.setpIndex(rs.getString("PIndex"));
                    bean.setpTable(rs.getString("PTable"));
                    bean.setpQty(rs.getInt("PQty"));
                    bean.setpEtd(rs.getString("PEtd"));
                }
                rs.close();
            }
        } catch (SQLException e) {
            AppLogUtil.log(PrintToKicController.class, "error getPendingCheckout()", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
        return bean;
    }

    /**
     * Marks a kictran row as checkout-printed (R_PrintCheckOut='Y').
     * Replaces the UPDATE in UrgentFoodLoopCheck.printCheckItOut().
     */
    public void markCheckoutPrinted(String pTable, String pIndex, String pCode) {
        mysqlConnect.open(PrintToKicController.class);
        try {
            String sql = "update kictran set "
                    + "R_PrintCheckOut='Y' "
                    + "where ptable='" + pTable + "' "
                    + "and pindex='" + pIndex + "' "
                    + "and pcode='" + pCode + "' "
                    + "and R_PrintCheckOut='N' ;";
            mysqlConnect.executeUpdate(sql);
        } catch (Exception e) {
            AppLogUtil.log(PrintToKicController.class, "error markCheckoutPrinted()", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
    }

    /**
     * Marks a kictran row as checkout-printed even when the balance record is
     * missing (bean was null in UrgentFoodLoopCheck.printCheckItOut()).
     */
    public void markCheckoutPrintedNoBalance(String pTable, String pIndex, String pCode) {
        mysqlConnect.open(PrintToKicController.class);
        try {
            String sql = "update kictran "
                    + "set "
                    + "R_PrintCheckOut='Y' "
                    + "where pindex='" + pIndex + "' "
                    + "and ptable='" + pTable + "' "
                    + "and pcode='" + pCode + "';";
            mysqlConnect.executeUpdate(sql);
        } catch (Exception e) {
            AppLogUtil.log(PrintToKicController.class, "error markCheckoutPrintedNoBalance()", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
    }

    /**
     * Returns all rows from kictran_urgentClick for the given date.
     * Replaces the SELECT in UrgentFoodLoopCheck.printUrgentLog().
     */
    public List<PKicTranBean> getUrgentClickLog(String date) {
        List<PKicTranBean> list = new ArrayList<>();
        mysqlConnect.open(PrintToKicController.class);
        try {
            String sql = "select * from kictran_urgentClick where pdate='" + date + "';";
            try (ResultSet rs = mysqlConnect.executeQuery(sql)) {
                while (rs.next()) {
                    PKicTranBean bean = new PKicTranBean();
                    bean.setpTable(rs.getString("pTable"));
                    bean.setpDate(rs.getString("pDate"));
                    bean.setpTime(rs.getString("pTime"));
                    list.add(bean);
                }
                rs.close();
            }
        } catch (SQLException e) {
            AppLogUtil.log(PrintToKicController.class, "error getUrgentClickLog()", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
        return list;
    }
}
