package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.POSConfigSetup;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import com.softpos.util.AppLogUtil;
import com.softpos.util.NumberUtil;

public class DiscountControl {

    private final MySQLConnect mysqlConnect = new MySQLConnect();
    private final POSConfigSetup POSConfigSetup = new POSConfigSetup();

    public double getDouble(double db) {
        if (POSConfigSetup.Bean().getP_DiscRound().equals("F")) {
            return NumberUtil.UP_DOWN_25(db);
        } else {
            return db;
        }
    }

    public void updateDiscount(String tableNo) {
        //หามูลค่าส่วนลดรายการ

        mysqlConnect.open(this.getClass());
        try {
            String sql = "select sum(R_PrAmt) SUM_R_PrAmt "
                    + "from balance "
                    + "where R_table = '" + tableNo + "' "
                    + "and R_Void<>'V' "
                    + "and R_Discount='Y' "
                    + "and R_PrType = '-I'";
            ResultSet rs = mysqlConnect.executeQuery(sql);
            if (rs.next()) {
                String sqlUpd = "update tablefile set "
                        + "ItemDiscAmt='" + rs.getDouble("SUM_R_PrAmt") + "' "
                        + "where Tcode = '" + tableNo + "'";
                Statement stmt1 = mysqlConnect.getConnection().createStatement();
                stmt1.executeUpdate(sqlUpd);
                stmt1.close();
            }

            rs.close();
        } catch (SQLException e) {

            AppLogUtil.log(DiscountControl.class, "error", e);
        }

        //หามูลค่าบัตรลดคูปอง
        try {
            String sql = "select sum(R_PrCuAmt) SUM_R_PrCuAmt "
                    + "from balance "
                    + "where R_table = '" + tableNo + "' "
                    + "and R_Void<>'V' "
                    + "and R_Discount='Y' "
                    + "and R_PrCuType = '-C'";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String sqlUpd;
                if (!POSConfigSetup.Bean().getP_DiscRound().equals("F")) {
                    sqlUpd = "update tablefile set "
                            + "CuponDiscAmt='" + NumberUtil.UP_DOWN_NATURAL_BAHT(rs.getDouble("SUM_R_PrCuAmt")) + "' "
                            + "where Tcode = '" + tableNo + "'";
                } else {
                    sqlUpd = "update tablefile set "
                            + "CuponDiscAmt='" + (rs.getDouble("SUM_R_PrCuAmt")) + "' "
                            + "where Tcode = '" + tableNo + "'";
                }
                mysqlConnect.executeUpdate(sqlUpd);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {

        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
    }

    /**
     * Returns total R_Total grouped by R_Normal for a table.
     * Result: double[0] = total for R_Normal='N', double[1] = total for all other R_Normal values.
     */
    public double[] getBalanceTotalByNormal(String tableNo, boolean excludeVoid) {
        double[] result = {0.0, 0.0};
        mysqlConnect.open(DiscountControl.class);
        try {
            String sql = "select R_Normal, sum(R_Total) as R_Total "
                    + "from balance where R_Table='" + tableNo + "' "
                    + (excludeVoid ? "and r_void<>'V' " : "")
                    + "group by R_Normal;";
            try (Statement stmt = mysqlConnect.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String type = rs.getString("R_Normal");
                    double total = rs.getDouble("R_Total");
                    if ("N".equals(type)) {
                        result[0] = total;
                    } else {
                        result[1] = total;
                    }
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(DiscountControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(DiscountControl.class);
        }
        return result;
    }

    /** Updates balance with employee-type discount percentages by ETD type. */
    public void updateDiscountBalance(String type, String tableNo, String discForN, String discForC, String discForS) {
        if (!"EmpDiscount".equals(type)) return;

        String[] typeDisc = POSConfigSetup.Bean().getP_EmpDisc().split("/");
        double typeNormalN = 0, typeNormalC = 0, typeNormalS = 0;
        if (typeDisc.length >= 3) {
            typeNormalN = Double.parseDouble(typeDisc[0]);
            typeNormalC = Double.parseDouble(typeDisc[1]);
            typeNormalS = Double.parseDouble(typeDisc[2]);
        }

        String[] saleTypeDiscCheck = POSConfigSetup.Bean().getP_DiscChkBySaleType().split("/");
        if (saleTypeDiscCheck.length < 5) return;

        String saleTypeE = saleTypeDiscCheck[0];
        String saleTypeT = saleTypeDiscCheck[1];
        String saleTypeD = saleTypeDiscCheck[2];
        String saleTypeP = saleTypeDiscCheck[3];
        String saleTypeW = saleTypeDiscCheck[4];

        String[][] etdUpdates = {
            {"Y".equals(saleTypeE) ? "E" : null, discForN, discForC, discForS},
            {"E".equals(saleTypeT) && "Y".equals(saleTypeE) ? "T" : null, discForN, discForC, discForS},
            {"Y".equals(saleTypeD) && "Y".equals(saleTypeE) ? "D" : null, discForN, discForC, discForS},
            {"Y".equals(saleTypeP) && "Y".equals(saleTypeE) ? "P" : null, discForN, discForC, discForS},
            {"Y".equals(saleTypeW) && "Y".equals(saleTypeE) ? "W" : null, discForN, discForC, discForS}
        };

        for (String[] upd : etdUpdates) {
            if (upd[0] == null) continue;
            String etd = upd[0];
            String sql = null;
            if (typeNormalN > 0) {
                sql = "update balance set R_PRSubType='-E', R_PRSubCode='EMP', R_PRSubQuan='1', "
                        + "R_PRSubDisc='" + upd[1] + "', R_PRSubAmt=(R_Total * " + upd[1] + ")/100 "
                        + "where r_table='" + tableNo + "' and R_ETD='" + etd + "' and R_Normal='N' and R_Discount='Y';";
            } else if (typeNormalC > 0) {
                sql = "update balance set R_PRSubType='-E', R_PRSubCode='EMP', R_PRSubQuan='1', "
                        + "R_PRSubDisc='" + upd[2] + "', R_PRSubAmt=(R_Total * " + upd[2] + ")/100 "
                        + "where r_table='" + tableNo + "' and R_ETD='" + etd + "' and R_Normal='C' and R_Discount='Y';";
            } else if (typeNormalS > 0) {
                sql = "update balance set R_PRSubType='-E', R_PRSubCode='EMP', R_PRSubQuan='1', "
                        + "R_PRSubDisc='" + upd[3] + "', R_PRSubAmt=(R_Total * " + upd[3] + ")/100 "
                        + "where r_table='" + tableNo + "' and R_ETD='" + etd + "' and R_Normal='S' and R_Discount='Y';";
            }
            if (sql == null) continue;
            mysqlConnect.open(DiscountControl.class);
            try {
                mysqlConnect.executeUpdate(sql);
            } catch (Exception e) {
                AppLogUtil.log(DiscountControl.class, "error", e);
            } finally {
                mysqlConnect.closeConnection(DiscountControl.class);
            }
        }
    }

    /** Resets all R_PRSub discount fields on balance for a table. */
    public void cancelDiscountBalance(String tableNo) {
        mysqlConnect.open(DiscountControl.class);
        try {
            String sql = "update balance set "
                    + "R_PRSubType='', R_PRSubCode='', R_PRSubQuan='0', "
                    + "R_PRSubDisc='0', R_PRSubAmt='0' "
                    + "where r_table='" + tableNo + "' and R_Discount='Y';";
            mysqlConnect.executeUpdate(sql);
        } catch (Exception e) {
            AppLogUtil.log(DiscountControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(DiscountControl.class);
        }
    }

    /** Resets all discount fields on tablefile for a table. */
    public void cancelTablefileDiscount(String tableNo) {
        mysqlConnect.open(DiscountControl.class);
        try {
            String sql = "update tablefile set nettotal=(TAmount+ServiceAmt),"
                    + "EMPDisc='', EMPDiscAmt='0',"
                    + "FastDisc='', FASTDiscAmt='0',"
                    + "TrainDisc='', TrainDiscAmt='0',"
                    + "MemDisc='', MemDiscAmt='0',"
                    + "SubDisc='', SubDiscAmt='0',"
                    + "CuponDiscAmt='0' where tcode='" + tableNo + "'; ";
            mysqlConnect.executeUpdate(sql);
        } catch (Exception e) {
            AppLogUtil.log(DiscountControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(DiscountControl.class);
        }
    }

    /**
     * Returns discount permissions from posconfigsetup.
     * boolean[0]=MemDiscGet, [1]=FastDiscGet, [2]=EmpDiscGet,
     * [3]=TrainDiscGet, [4]=SubDiscGet, [5]=DiscBathChk  (true = 'Y')
     */
    public boolean[] loadDiscountPermissions() {
        boolean[] result = new boolean[6];
        mysqlConnect.open(DiscountControl.class);
        try {
            String sql = "select P_MemDiscGet,P_FastDiscGet,P_EmpDiscGet,P_TrainDiscGet,P_SubDiscGet,P_DiscBathChk "
                    + "from posconfigsetup limit 1;";
            ResultSet rs = mysqlConnect.executeQuery(sql);
            if (rs.next()) {
                result[0] = "Y".equals(rs.getString("P_MemDiscGet"));
                result[1] = "Y".equals(rs.getString("P_FastDiscGet"));
                result[2] = "Y".equals(rs.getString("P_EmpDiscGet"));
                result[3] = "Y".equals(rs.getString("P_TrainDiscGet"));
                result[4] = "Y".equals(rs.getString("P_SubDiscGet"));
                result[5] = "Y".equals(rs.getString("P_DiscBathChk"));
                rs.close();
            }
        } catch (SQLException e) {
            AppLogUtil.log(DiscountControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(DiscountControl.class);
        }
        return result;
    }

    /**
     * Returns discount fields from tablefile for a table.
     * Keys: FastDisc, FastDiscAmt, EmpDisc, EmpDiscAmt, MemDisc, MemDiscAmt,
     *       TrainDisc, TrainDiscAmt, SubDisc, SubDiscAmt, DiscBath
     */
    public Map<String, String> loadTablefileDiscounts(String tableNo) {
        Map<String, String> map = new HashMap<>();
        mysqlConnect.open(DiscountControl.class);
        try {
            String sql = "select * from tablefile where Tcode = '" + tableNo + "' limit 1";
            try (Statement stmt = mysqlConnect.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    map.put("FastDisc", rs.getString("FastDisc"));
                    map.put("FastDiscAmt", rs.getString("FastDiscAmt"));
                    map.put("EmpDisc", rs.getString("EmpDisc"));
                    map.put("EmpDiscAmt", rs.getString("EmpDiscAmt"));
                    map.put("MemDisc", rs.getString("MemDisc"));
                    map.put("MemDiscAmt", rs.getString("MemDiscAmt"));
                    map.put("TrainDisc", rs.getString("TrainDisc"));
                    map.put("TrainDiscAmt", rs.getString("TrainDiscAmt"));
                    map.put("SubDisc", rs.getString("SubDisc"));
                    map.put("SubDiscAmt", rs.getString("SubDiscAmt"));
                    map.put("DiscBath", rs.getString("DiscBath"));
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(DiscountControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(DiscountControl.class);
        }
        return map;
    }

    /** Distributes baht discount evenly across all non-void balance rows for a table. */
    public void distributeBahtDiscount(String tableNo, double bahtDiscount) {
        mysqlConnect.open(DiscountControl.class);
        try {
            String sqlCount = "select count(r_index) r_index from balance where r_table='" + tableNo + "' and r_void<>'V'";
            try (java.sql.Statement stmt = mysqlConnect.getConnection().createStatement();
                 java.sql.ResultSet rs = stmt.executeQuery(sqlCount)) {
                if (rs.next()) {
                    int count = rs.getInt("r_index");
                    if (count > 0) {
                        String sqlUpd = "update balance set r_discbath ='" + (bahtDiscount / count) + "' where r_table='" + tableNo + "'";
                        try (java.sql.Statement stmt2 = mysqlConnect.getConnection().createStatement()) {
                            stmt2.executeUpdate(sqlUpd);
                        }
                    }
                }
            }
        } catch (java.sql.SQLException e) {
            AppLogUtil.log(DiscountControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(DiscountControl.class);
        }
    }

    /**
     * Clears coupon special data: deletes tempcupon, restores balance from temp_balance if exists,
     * and resets r_prcuamt/r_discbath on balance.
     * Returns true if temp_balance was restored (caller should call BalanceControl.updateProSerTable).
     */
    public boolean clearCouponSpecial(String tableNo) {
        boolean restored = false;
        mysqlConnect.open(DiscountControl.class);
        try {
            String sqlDelTempCupon = "delete from tempcupon where r_table='" + tableNo + "'";
            mysqlConnect.getConnection().createStatement().executeUpdate(sqlDelTempCupon);

            String sqlCheck = "select * from temp_balance where r_table='" + tableNo + "' limit 1";
            try (Statement stmt = mysqlConnect.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery(sqlCheck)) {
                if (rs.next()) {
                    stmt.executeUpdate("delete from balance where r_table='" + tableNo + "'");
                    stmt.executeUpdate("insert into balance select * from temp_balance "
                            + "where r_table='" + tableNo + "' order by r_index");
                    stmt.executeUpdate("delete from temp_balance where r_table='" + tableNo + "'");
                    restored = true;
                }
            }

            mysqlConnect.executeUpdate(
                    "update balance set r_prcuamt='0',r_discbath='0' where r_table='" + tableNo + "'");
        } catch (Exception e) {
            AppLogUtil.log(DiscountControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(DiscountControl.class);
        }
        return restored;
    }
}
