package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.CuponBean;
import database.MySQLConnect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.softpos.util.AppLogUtil;
import com.softpos.util.DateFormat;
import com.softpos.util.ThaiUtil;

public class CuponControl {

    private final MySQLConnect mysqlConnect = new MySQLConnect();
    
    public List<CuponBean> listCupon() {
        
        mysqlConnect.open(this.getClass());

        List<CuponBean> listBean = new ArrayList<>();
        try {
            String sql = "select * from cupon";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                CuponBean bean = new CuponBean();
                bean.setCuCode(rs.getString("CuCode"));
                bean.setCuName(rs.getString("CuName"));
                bean.setCuBegin(rs.getDate("CuBegin"));
                bean.setCuEnd(rs.getDate("CuEnd"));
                bean.setCuStrDay(rs.getString("CuStrDay"));
                bean.setCuType(rs.getString("CuType"));
                bean.setCuADisc(rs.getString("CuADisc"));
                bean.setCuADiscBath(rs.getFloat("CuADiscBath"));
                bean.setCuBDisc(rs.getString("CuBDisc"));
                bean.setCuBDiscBath(rs.getFloat("CuBDiscBath"));
                bean.setCuPLUList(rs.getString("CuPLUList"));
                bean.setCuPLU1(rs.getString("CuPLU1"));
                bean.setCuPLU2(rs.getString("CuPLU2"));
                bean.setCuPLU3(rs.getString("CuPLU3"));
                bean.setCuPLU4(rs.getString("CuPLU4"));
                bean.setCuPLU5(rs.getString("CuPLU5"));
                bean.setCuPLU6(rs.getString("CuPLU6"));
                bean.setCuPLU7(rs.getString("CuPLU7"));
                bean.setCuPLU8(rs.getString("CuPLU8"));
                bean.setCuPLU9(rs.getString("CuPLU9"));
                bean.setCuPLU10(rs.getString("CuPLU10"));
                bean.setCuDisc(rs.getFloat("CuDisc"));
                bean.setCuDiscBath(rs.getFloat("CuDiscBath"));
                bean.setChkMember(rs.getString("ChkMember"));
                bean.setCuDisc2(rs.getFloat("CuDisc2"));
                bean.setCuDiscBath2(rs.getFloat("CuDiscBath2"));
                bean.setCuDisc3(rs.getFloat("CuDisc3"));
                bean.setCuDiscBath3(rs.getFloat("CuDiscBath3"));
                bean.setCuDisc1(rs.getFloat("CuDisc1"));
                bean.setCuDiscBath1(rs.getFloat("CuDiscBath1"));
                bean.setCuSelectDisc(rs.getString("CuSelectDisc"));
                bean.setCuEDiscount(rs.getFloat("CuEDiscount"));
                bean.setCuEPayment(rs.getFloat("CuEPayment"));

                listBean.add(bean);
            }

            stmt.close();
            rs.close();
        } catch (SQLException e) {

            AppLogUtil.log(CuponControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return listBean;
    }

    public List<CuponBean> listCupon(String CuCode) {
        mysqlConnect.open(this.getClass());

        List<CuponBean> listBean = new ArrayList<>();
        try {
            String sql = "select * from cupon where CuCode='" + CuCode + "'";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                CuponBean bean = new CuponBean();
                bean.setCuCode(rs.getString("CuCode"));
                bean.setCuName(rs.getString("CuName"));
                bean.setCuBegin(rs.getDate("CuBegin"));
                bean.setCuEnd(rs.getDate("CuEnd"));
                bean.setCuStrDay(rs.getString("CuStrDay"));
                bean.setCuType(rs.getString("CuType"));
                bean.setCuADisc(rs.getString("CuADisc"));
                bean.setCuADiscBath(rs.getFloat("CuADiscBath"));
                bean.setCuBDisc(rs.getString("CuBDisc"));
                bean.setCuBDiscBath(rs.getFloat("CuBDiscBath"));
                bean.setCuPLUList(rs.getString("CuPLUList"));
                bean.setCuPLU1(rs.getString("CuPLU1"));
                bean.setCuPLU2(rs.getString("CuPLU2"));
                bean.setCuPLU3(rs.getString("CuPLU3"));
                bean.setCuPLU4(rs.getString("CuPLU4"));
                bean.setCuPLU5(rs.getString("CuPLU5"));
                bean.setCuPLU6(rs.getString("CuPLU6"));
                bean.setCuPLU7(rs.getString("CuPLU7"));
                bean.setCuPLU8(rs.getString("CuPLU8"));
                bean.setCuPLU9(rs.getString("CuPLU9"));
                bean.setCuPLU10(rs.getString("CuPLU10"));
                bean.setCuDisc(rs.getFloat("CuDisc"));
                bean.setCuDiscBath(rs.getFloat("CuDiscBath"));
                bean.setChkMember(rs.getString("ChkMember"));
                bean.setCuDisc2(rs.getFloat("CuDisc2"));
                bean.setCuDiscBath2(rs.getFloat("CuDiscBath2"));
                bean.setCuDisc3(rs.getFloat("CuDisc3"));
                bean.setCuDiscBath3(rs.getFloat("CuDiscBath3"));
                bean.setCuDisc1(rs.getFloat("CuDisc1"));
                bean.setCuDiscBath1(rs.getFloat("CuDiscBath1"));
                bean.setCuSelectDisc(rs.getString("CuSelectDisc"));
                bean.setCuEDiscount(rs.getFloat("CuEDiscount"));
                bean.setCuEPayment(rs.getFloat("CuEPayment"));

                listBean.add(bean);
            }

            stmt.close();
            rs.close();
        } catch (SQLException e) {

            AppLogUtil.log(CuponControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return listBean;
    }

    public CuponBean getCupon(String CuCode) {
        /**
         * * OPEN CONNECTION **
         */
        mysqlConnect.open(this.getClass());

        CuponBean bean = new CuponBean();
        try {
            String sql = "select * from cupon where CuCode='" + CuCode + "' limit 1";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                bean.setCuCode(rs.getString("CuCode"));
                bean.setCuName(rs.getString("CuName"));
                bean.setCuBegin(rs.getDate("CuBegin"));
                bean.setCuEnd(rs.getDate("CuEnd"));
                bean.setCuStrDay(rs.getString("CuStrDay"));
                bean.setCuType(rs.getString("CuType"));
                bean.setCuADisc(rs.getString("CuADisc"));
                bean.setCuADiscBath(rs.getFloat("CuADiscBath"));
                bean.setCuBDisc(rs.getString("CuBDisc"));
                bean.setCuBDiscBath(rs.getFloat("CuBDiscBath"));
                bean.setCuPLUList(rs.getString("CuPLUList"));
                bean.setCuPLU1(rs.getString("CuPLU1"));
                bean.setCuPLU2(rs.getString("CuPLU2"));
                bean.setCuPLU3(rs.getString("CuPLU3"));
                bean.setCuPLU4(rs.getString("CuPLU4"));
                bean.setCuPLU5(rs.getString("CuPLU5"));
                bean.setCuPLU6(rs.getString("CuPLU6"));
                bean.setCuPLU7(rs.getString("CuPLU7"));
                bean.setCuPLU8(rs.getString("CuPLU8"));
                bean.setCuPLU9(rs.getString("CuPLU9"));
                bean.setCuPLU10(rs.getString("CuPLU10"));
                bean.setCuDisc(rs.getFloat("CuDisc"));
                bean.setCuDiscBath(rs.getFloat("CuDiscBath"));
                bean.setChkMember(rs.getString("ChkMember"));
                bean.setCuDisc2(rs.getFloat("CuDisc2"));
                bean.setCuDiscBath2(rs.getFloat("CuDiscBath2"));
                bean.setCuDisc3(rs.getFloat("CuDisc3"));
                bean.setCuDiscBath3(rs.getFloat("CuDiscBath3"));
                bean.setCuDisc1(rs.getFloat("CuDisc1"));
                bean.setCuDiscBath1(rs.getFloat("CuDiscBath1"));
                bean.setCuSelectDisc(rs.getString("CuSelectDisc"));
                bean.setCuEDiscount(rs.getFloat("CuEDiscount"));
                bean.setCuEPayment(rs.getFloat("CuEPayment"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {

            AppLogUtil.log(CuponControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return bean;
    }

    public void saveCupon(CuponBean bean) {
        /**
         * * OPEN CONNECTION **
         */
        mysqlConnect.open(this.getClass());
        try {
            String sql = "insert into cupon (CuCode,CuName,CuBegin,CuEnd,CuStrDay,CuType,CuADisc,"
                    + "CuADiscBath,CuBDisc,CuBDiscBath,CuPLUList,CuPLU1,CuPLU2,CuPLU3,CuPLU4,CuPLU5,"
                    + "CuPLU6,CuPLU7,CuPLU8,CuPLU9,CuPLU10,CuDisc,CuDiscBath,ChkMember,CuDisc2,CuDiscBath2,"
                    + "CuDisc3,CuDiscBath3,CuDisc1,CuDiscBath1,CuSelectDisc,CuEDiscount,CuEPayment)  "
                    + "values('" + bean.getCuCode() + "','" + bean.getCuName() + "','" + bean.getCuBegin() + "',"
                    + "'" + bean.getCuEnd() + "','" + bean.getCuStrDay() + "','" + bean.getCuType() + "',"
                    + "'" + bean.getCuADisc() + "','" + bean.getCuADiscBath() + "','" + bean.getCuBDisc() + "',"
                    + "'" + bean.getCuBDiscBath() + "','" + bean.getCuPLUList() + "','" + bean.getCuPLU1() + "',"
                    + "'" + bean.getCuPLU2() + "','" + bean.getCuPLU3() + "','" + bean.getCuPLU4() + "',"
                    + "'" + bean.getCuPLU5() + "','" + bean.getCuPLU6() + "','" + bean.getCuPLU7() + "',"
                    + "'" + bean.getCuPLU8() + "','" + bean.getCuPLU9() + "','" + bean.getCuPLU10() + "',"
                    + "'" + bean.getCuDisc() + "','" + bean.getCuDiscBath() + "','" + bean.getChkMember() + "',"
                    + "'" + bean.getCuDisc2() + "','" + bean.getCuDiscBath2() + "','" + bean.getCuDisc3() + "',"
                    + "'" + bean.getCuDiscBath3() + "','" + bean.getCuDisc1() + "',"
                    + "'" + bean.getCuDiscBath1() + "','" + bean.getCuSelectDisc() + "',"
                    + "'" + bean.getCuEDiscount() + "','" + bean.getCuEPayment() + "')";
            String sqlChk = "select CuCode from cupon where CuCode='" + bean.getCuCode() + "' limit 1";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sqlChk);
            if (rs.next()) {
                updateCupon(bean);
            } else {
                stmt.executeUpdate(sql);
            }

            stmt.close();
            rs.close();
        } catch (SQLException e) {

            AppLogUtil.log(CuponControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
    }

    public void updateCupon(CuponBean bean) {
        /**
         * * OPEN CONNECTION **
         */
        mysqlConnect.open(this.getClass());

        try {
            String sql = "update cupon set CuCode='" + bean.getCuCode() + "', "
                    + "CuName='" + bean.getCuName() + "', "
                    + "CuBegin='" + DateFormat.getMySQL_Date(bean.getCuBegin()) + "', "
                    + "CuEnd='" + DateFormat.getMySQL_Date(bean.getCuEnd()) + "', "
                    + "CuStrDay='" + bean.getCuStrDay() + "', CuType='" + bean.getCuType() + "', "
                    + "CuADisc='" + bean.getCuADisc() + "', CuADiscBath='" + bean.getCuADiscBath() + "', "
                    + "CuBDisc='" + bean.getCuBDisc() + "', CuBDiscBath='" + bean.getCuBDiscBath() + "', "
                    + "CuPLUList='" + bean.getCuPLUList() + "', CuPLU1='" + bean.getCuPLU1() + "', "
                    + "CuPLU2='" + bean.getCuPLU2() + "', CuPLU3='" + bean.getCuPLU3() + "', "
                    + "CuPLU4='" + bean.getCuPLU4() + "', CuPLU5='" + bean.getCuPLU5() + "', "
                    + "CuPLU6='" + bean.getCuPLU6() + "', CuPLU7='" + bean.getCuPLU7() + "', "
                    + "CuPLU8='" + bean.getCuPLU8() + "', CuPLU9='" + bean.getCuPLU9() + "', "
                    + "CuPLU10='" + bean.getCuPLU10() + "', CuDisc='" + bean.getCuDisc() + "', "
                    + "CuDiscBath='" + bean.getCuDiscBath() + "', ChkMember='" + bean.getChkMember() + "', "
                    + "CuDisc2='" + bean.getCuDisc2() + "', CuDiscBath2='" + bean.getCuDiscBath2() + "', "
                    + "CuDisc3='" + bean.getCuDisc3() + "', CuDiscBath3='" + bean.getCuDiscBath3() + "', "
                    + "CuDisc1='" + bean.getCuDisc1() + "', CuDiscBath1='" + bean.getCuDiscBath1() + "', "
                    + "CuSelectDisc='" + bean.getCuSelectDisc() + "', "
                    + "CuEDiscount='" + bean.getCuEDiscount() + "', CuEPayment='" + bean.getCuEPayment() + "' "
                    + "where CuCode='" + bean.getCuCode() + "'";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {

            AppLogUtil.log(CuponControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
    }

    // -------------------------------------------------------------------------
    // Gift Voucher / Temp Gift methods
    // -------------------------------------------------------------------------

    /**
     * Loads all rows from tempgift. Returns a list of Object[] where each
     * element is {giftno (String), giftamt (Double)}.
     * Used by GiftVoucherDialog.loadData().
     */
    public List<Object[]> loadTempGift(String macno) {
        /**
         * * OPEN CONNECTION **
         */
        mysqlConnect.open(this.getClass());
        List<Object[]> rows = new ArrayList<>();
        try {
            String sql = "select * from tempgift where macno='" + macno + "'";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                rows.add(new Object[]{rs.getString("giftno"), rs.getDouble("giftamt")});
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            AppLogUtil.log(CuponControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
        return rows;
    }

    /**
     * Inserts a row into tempgift (giftno + giftamt) if it does not already
     * exist. Returns true if the row was inserted (or already existed).
     * Used by GiftVoucherDialog.btnExitActionPerformed().
     */
    public boolean saveTempGift(String giftNo, String voucherType, double amt, String macno) {
        /**
         * * OPEN CONNECTION **
         */
        mysqlConnect.open(this.getClass());
        try {
            String sqlCheckTempGift = "select giftno from tempgift where giftno='" + giftNo + "' limit 1";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sqlCheckTempGift);
            boolean alreadyExists = rs.next();
            rs.close();
            if (!alreadyExists) {
                String sqlAdd = "insert into tempgift"
                        + "(macno,gifttype,giftno,giftamt) "
                        + "values('" + macno + "','" + voucherType + "','" + giftNo + "','" + amt + "')";
                stmt.executeUpdate(sqlAdd);
            }
            stmt.close();
            return true;
        } catch (SQLException e) {
            AppLogUtil.log(CuponControl.class, "error", e);
            return false;
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
    }

    /**
     * Deletes all rows from tempgift (no macno filter).
     * Used by GiftVoucherDialog.clearModel().
     */
    public void clearTempGift() {
        /**
         * * OPEN CONNECTION **
         */
        mysqlConnect.open(this.getClass());
        try {
            String sql = "delete from tempgift";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            AppLogUtil.log(CuponControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
    }

    /**
     * Returns all rows from the gifttype table as a list of Object[] {code,
     * name}. Used by GiftDialogList.loadData().
     */
    public List<Object[]> getGiftTypeList() {
        /**
         * * OPEN CONNECTION **
         */
        mysqlConnect.open(this.getClass());
        List<Object[]> rows = new ArrayList<>();
        try {
            String sql = "select * from gifttype";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                rows.add(new Object[]{rs.getString(1), ThaiUtil.ASCII2Unicode(rs.getString(2))});
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            AppLogUtil.log(CuponControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
        return rows;
    }

    /**
     * Deletes all tempgift rows for a specific macno.
     * Used by Giftvoucher.bntClearAllClick().
     */
    public void clearTempGiftByMacno(String macno) {
        /**
         * * OPEN CONNECTION **
         */
        mysqlConnect.open(this.getClass());
        try {
            PreparedStatement prm = mysqlConnect.getConnection().prepareStatement(
                    "delete from tempgift where (macno=?) ");
            prm.setString(1, macno);
            prm.executeUpdate();
            prm.close();
        } catch (SQLException e) {
            AppLogUtil.log(CuponControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
    }

    /**
     * Inserts a fully-parsed gift barcode row into tempgift.
     * Used by Giftvoucher.getValidVoucher().
     */
    public void insertTempGift(String macno, String giftBarcode, String giftType,
            String giftPrice, String giftModel, String giftLot, String giftExp,
            String giftCode, String giftNo, double giftAmt) {
        /**
         * * OPEN CONNECTION **
         */
        mysqlConnect.open(this.getClass());
        try {
            String sql = "insert into tempgift (macno,giftbarcode,gifttype,giftprice,giftmodel,giftlot,giftexp,giftcode,giftno,giftamt) "
                    + "values (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement prm = mysqlConnect.getConnection().prepareStatement(sql);
            prm.setString(1, macno);
            prm.setString(2, giftBarcode);
            prm.setString(3, giftType);
            prm.setString(4, giftPrice);
            prm.setString(5, giftModel);
            prm.setString(6, giftLot);
            prm.setString(7, giftExp);
            prm.setString(8, giftCode);
            prm.setString(9, giftNo);
            prm.setDouble(10, giftAmt);
            prm.executeUpdate();
            prm.close();
        } catch (SQLException e) {
            AppLogUtil.log(CuponControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
    }

    /**
     * Checks whether a gift exists in giftstatus for the given gcode and gno.
     * Returns true if found. Used by Giftvoucher.findGiftStatus().
     */
    public boolean isGiftStatusValid(String giftCode, String giftNo) {
        /**
         * * OPEN CONNECTION **
         */
        mysqlConnect.open(this.getClass());
        boolean isValid = false;
        try {
            String sql = "select gcode from giftstatus where (gcode='" + giftCode + "') and (gno= '" + giftNo + "') limit 1";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            isValid = rs.next();
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            AppLogUtil.log(CuponControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
        return isValid;
    }

    /**
     * Looks up the priceamt for a given pricecode in giftprice.
     * Returns the price amount, or -1.0 if not found.
     * Used by Giftvoucher.findGiftPrice().
     */
    public double getGiftPrice(String priceCode) {
        /**
         * * OPEN CONNECTION **
         */
        mysqlConnect.open(this.getClass());
        double priceAmt = -1.0;
        try {
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(
                    "select priceamt from giftprice where (pricecode='" + priceCode + "') limit 1");
            if (rs.next()) {
                priceAmt = rs.getDouble("priceamt");
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            AppLogUtil.log(CuponControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
        return priceAmt;
    }

    /**
     * Fetches the first tempgift row for the given macno.
     * Returns an Object[] with all display columns, or null if none found.
     * Columns: gifttype, giftmodel, giftlot, giftexp, giftbarcode, giftno, giftamt.
     * Used by Giftvoucher.showDataToGrid().
     */
    public Object[] getTempGiftByMacno(String macno) {
        /**
         * * OPEN CONNECTION **
         */
        mysqlConnect.open(this.getClass());
        Object[] row = null;
        try {
            String sql = "select * from tempgift where macno='" + macno + "' limit 1";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                row = new Object[]{
                    rs.getString("gifttype"),
                    rs.getString("giftmodel"),
                    rs.getString("giftlot"),
                    rs.getString("giftexp"),
                    rs.getString("giftbarcode"),
                    rs.getString("giftno"),
                    rs.getDouble("giftamt")
                };
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            AppLogUtil.log(CuponControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
        return row;
    }
}
