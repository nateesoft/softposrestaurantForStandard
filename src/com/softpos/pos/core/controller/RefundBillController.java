package com.softpos.pos.core.controller;


import com.softpos.pos.core.model.BillNoBean;
import com.softpos.pos.core.model.PIngredientBean;
import com.softpos.util.AppLogUtil;
import com.softpos.util.ThaiUtil;
import com.softpos.connection.database.MySQLConnect;
import com.softpos.constants.PublicVar;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author nathee
 */
public class RefundBillController {

    private final MySQLConnect mysqlConnect = new MySQLConnect();

    public BillNoBean checkBillByRefno(String macno, String billNo) {
        BillNoBean bean = null;

        mysqlConnect.open(this.getClass());
        try {
            String sql = "select * from billno "
                    + "where (b_macno='" + macno + "') and (b_refno='" + billNo + "')";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                bean = new BillNoBean();
                bean.setB_Void(rs.getString("b_void"));
                bean.setB_InvNo(rs.getString("b_invno"));
                bean.setB_MacNo(rs.getString("b_macno"));
                bean.setB_MemCode(rs.getString("b_memcode"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            AppLogUtil.log(RefundBillController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return bean;
    }

    /**
     * Voids all related records in the database for a bill refund.
     * Updates billno, t_sale, t_saleset, t_cupon, t_gift;
     * deletes t_promotion, accr, mtran, mtranplu;
     * updates memmaster if a member is linked.
     * Also returns stock via ProcessStockOut for each sale line.
     */
    public void voidBillForRefund(String macno, String billNo, String user, String memcode,
                                   String branchCode, double netTotal, PUtility pUtility) {
        SimpleDateFormat timefmt = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();

        mysqlConnect.open(this.getClass());
        try {
            String sql;

            sql = "update billno "
                    + "set b_voiduser='" + user + "',"
                    + "b_voidtime='" + timefmt.format(date) + "',"
                    + "b_void='V' "
                    + "where b_macno='" + macno + "' "
                    + "and b_refno='" + billNo + "'";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
            }

            sql = "update t_sale set r_refund='V' "
                    + "where (macno='" + macno + "') "
                    + "and (r_refno='" + billNo + "')";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
            }

            sql = "update t_saleset set r_refund='V' "
                    + "where (macno='" + macno + "') "
                    + "and (r_refno='" + billNo + "')";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
            }

            sql = "update t_cupon set refund='V' "
                    + "where (terminal='" + macno + "') "
                    + "and (r_refno='" + billNo + "')";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
            }

            sql = "delete from t_promotion "
                    + "where (terminal='" + macno + "') "
                    + "and (r_refno='" + billNo + "')";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
            }

            sql = "update t_gift set fat='V' where (macno='" + macno + "') and (refno='" + billNo + "')";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
            }

            sql = "delete from accr where (arno='" + branchCode + "/" + macno + "/" + billNo + "')";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
            }

            if (memcode != null && !memcode.equals("")) {
                String sqlMem = "update " + PublicVar.db_member + ".memmaster set "
                        + "m_sum=m_sum-" + netTotal + " "
                        + "where (m_code='" + memcode + "')";
                try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                    stmt.executeUpdate(sqlMem);
                }

                sql = "delete from mtran where m_billno='" + macno + "/" + billNo + "'";
                try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                    stmt.executeUpdate(sql);
                }

                sql = "delete from mtranplu where m_billno='" + macno + "/" + billNo + "'";
                try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                    stmt.executeUpdate(sql);
                }
            }

            // Return Stock
            sql = "select * from t_sale where (macno='" + macno + "') and (r_refno='" + billNo + "') and (r_void<>'V')";
            try (Statement stmt = mysqlConnect.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String stkCode = pUtility.GetStkCode();
                    String stkRemark = "SAL";
                    String docNo = "R" + rs.getString("r_refno");
                    Date tDate = rs.getDate("r_date");
                    pUtility.ProcessStockOut(docNo, stkCode, rs.getString("r_plucode"), tDate, stkRemark,
                            -1 * rs.getDouble("r_quan"), -1 * rs.getDouble("r_total"),
                            user, rs.getString("r_stock"), rs.getString("r_set"), rs.getString("r_index"), "2");
                    double quantity = rs.getDouble("r_quan");
                    String rPluCode = rs.getString("r_plucode");
                    FloorPlanController floorPlanControl = AppContext.getFloorPlanController();
                    List<PIngredientBean> listING = floorPlanControl.listIngredeint(rPluCode);
                    for (PIngredientBean ingBean : listING) {
                        if (ingBean.getPstock().equals("Y") && ingBean.getPactive().equals("Y")) {
                            String ingCode = ingBean.getPingCode();
                            double pbPack = ingBean.getPBPack();
                            if (pbPack <= 0) {
                                pbPack = 1;
                            }
                            double rQuanIng = (ingBean.getPingQty() * quantity);
                            pUtility.ProcessStockOut(docNo, stkCode, ingCode, new Date(), stkRemark,
                                    -rQuanIng, 0.0, PublicVar.USERCODE, "Y", "", "", "");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(RefundBillController.class, "error", e);
            throw new RuntimeException(e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
    }

    /**
     * Loads all sale items from t_sale for a given macno and bill number.
     * Each Object[] row contains (in order):
     *   r_index, r_date, r_table, r_time, macno, cashier, r_emp, r_set, r_stock,
     *   r_plucode, r_pname(Unicode), r_unit, r_group, r_status, r_normal, r_discount,
     *   r_service, r_vat, r_type, r_etd, r_quan, r_price, r_total, r_prtype, r_prcode,
     *   r_prdisc, r_prbath, r_pramt, r_prquan, r_redule, r_kic, r_kicprint,
     *   r_void, r_voidtime, r_discbath
     */
    public List<Object[]> loadSaleItemsForRefund(String macno, String billNo) {
        List<Object[]> list = new ArrayList<>();

        mysqlConnect.open(this.getClass());
        try {
            String sql = "select * from t_sale "
                    + "where (macno='" + macno + "') and (r_refno='" + billNo + "')";
            try (Statement stmt = mysqlConnect.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    list.add(new Object[]{
                        rs.getString("r_index"),
                        rs.getDate("r_date"),
                        rs.getString("r_table"),
                        rs.getString("r_time"),
                        rs.getString("macno"),
                        rs.getString("cashier"),
                        rs.getString("r_emp"),
                        rs.getString("r_set"),
                        rs.getString("r_stock"),
                        rs.getString("r_plucode"),
                        ThaiUtil.ASCII2Unicode(rs.getString("r_pname")),
                        rs.getString("r_unit"),
                        rs.getString("r_group"),
                        rs.getString("r_status"),
                        rs.getString("r_normal"),
                        rs.getString("r_discount"),
                        rs.getString("r_service"),
                        rs.getString("r_vat"),
                        rs.getString("r_type"),
                        rs.getString("r_etd"),
                        rs.getDouble("r_quan"),
                        rs.getDouble("r_price"),
                        rs.getDouble("r_total"),
                        rs.getString("r_prtype"),
                        rs.getString("r_prcode"),
                        rs.getDouble("r_prdisc"),
                        rs.getDouble("r_prbath"),
                        rs.getDouble("r_pramt"),
                        rs.getDouble("r_prquan"),
                        rs.getDouble("r_redule"),
                        rs.getString("r_kic"),
                        rs.getString("r_kicprint"),
                        rs.getString("r_void"),
                        rs.getString("r_voidtime"),
                        rs.getDouble("r_discbath")
                    });
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(RefundBillController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return list;
    }

    /**
     * Checks whether the given userCode has refund (Sale2) permission in posuser.
     * Returns true if a matching row is found with Sale2='Y'.
     */
    public boolean hasRefundPermission(String userCode) {
        boolean isPermit = false;

        mysqlConnect.open(this.getClass());
        try {
            String sql = "select Username, Sale3 from posuser "
                    + "where username='" + userCode + "' and Sale2='Y' limit 1";
            try (Statement stmt = mysqlConnect.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    isPermit = true;
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(RefundBillController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return isPermit;
    }

    /**
     * Returns all pending cash-back records from billret where fat='N'.
     * Each Object[] row contains: Ref_No, Terminal, Cashier, STotal, Fat, UserVoid.
     */
    public List<Object[]> listPendingCashBacks() {
        List<Object[]> list = new ArrayList<>();

        mysqlConnect.open(this.getClass());
        try {
            String sql = "select * from billret where fat = 'N'";
            try (Statement stmt = mysqlConnect.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    list.add(new Object[]{
                        rs.getString("Ref_No"),
                        rs.getString("Terminal"),
                        rs.getString("Cashier"),
                        rs.getString("STotal"),
                        rs.getString("Fat"),
                        rs.getString("UserVoid")
                    });
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(RefundBillController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return list;
    }

    /**
     * Voids a cash-back record by setting Fat='V' for the given ref_no.
     * Returns true if at least one row was updated.
     */
    public boolean voidCashBack(String billNo) {
        boolean updated = false;

        mysqlConnect.open(this.getClass());
        try {
            String sql = "update billret set Fat='V' where ref_no='" + billNo + "'";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                int rows = stmt.executeUpdate(sql);
                updated = rows > 0;
            }
        } catch (SQLException e) {
            AppLogUtil.log(RefundBillController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return updated;
    }

    /**
     * Saves a cash-back record: reads returnbillno from branch, inserts into billret,
     * increments branch.returnbillno.
     * Returns the formatted reference string, or null on failure.
     */
    public String saveCashBack(double cash, String macno, String cashier) {
        mysqlConnect.open(this.getClass());
        String refStr = null;
        try {
            String sql = "select returnbillno from branch limit 1";
            int refNo = 0;
            try (Statement stmt = mysqlConnect.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    String ref = rs.getString(1);
                    if (ref != null) {
                        refNo = Integer.parseInt(ref);
                    }
                }
            }

            if (refNo < 10) {
                refStr = "000000" + refNo;
            } else if (refNo < 100) {
                refStr = "00000" + refNo;
            } else if (refNo < 1000) {
                refStr = "0000" + refNo;
            } else if (refNo < 10000) {
                refStr = "000" + refNo;
            } else if (refNo < 100000) {
                refStr = "00" + refNo;
            } else if (refNo < 1000000) {
                refStr = "0" + refNo;
            } else {
                refStr = "" + refNo;
            }

            String sql1 = "insert into billret(Ref_No,OnDate,Stotal,Cash,Cupon,Credit,Terminal,Cashier,Fat,UserVoid) values "
                    + "('" + refStr + "',curdate(),'" + cash + "','" + cash + "','0','0','" + macno + "','" + cashier + "','N','')";
            try (Statement stmt1 = mysqlConnect.getConnection().createStatement()) {
                int i = stmt1.executeUpdate(sql1);
                if (i > 0) {
                    stmt1.executeUpdate("update branch set returnbillno=returnbillno+1");
                } else {
                    refStr = null;
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(RefundBillController.class, "error", e);
            refStr = null;
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return refStr;
    }
}
