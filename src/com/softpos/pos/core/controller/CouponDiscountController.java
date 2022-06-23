package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.BalanceBean;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.AppLogUtil;
import util.MSG;

/**
 *
 * @author nathee
 */
public class CouponDiscountController extends DatabaseConnection {

    public List<BalanceBean> getBalanceByQuanCanDisc(String tableNo, String pCode) {
        List<BalanceBean> listBalance = new ArrayList<>();
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open(CouponDiscountController.class);
            String sql = "select r_quancandisc, R_Index, R_Price, R_Normal "
                    + "from balance "
                    + "where r_quancandisc>0 "
                    + "and r_table='" + tableNo + "' "
                    + "and r_plucode='" + pCode + "'";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                BalanceBean bean = new BalanceBean();
                bean.setR_QuanCanDisc(rs.getDouble("r_quancandisc"));
                bean.setR_Index(rs.getString("R_Index"));
                bean.setR_Price(rs.getDouble("R_Price"));
                bean.setR_Normal(rs.getString("R_Normal"));

                listBalance.add(bean);
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(CouponDiscountController.class, "error", e);
        } finally {
            mysql.close();
        }

        return listBalance;
    }

    public List<BalanceBean> getBalanceByPrAmt(String tableNo) {
        List<BalanceBean> listBalance = new ArrayList<>();
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open(CouponDiscountController.class);
            String sql = "select r_quancandisc, R_Index, R_Price, R_Normal "
                    + "from balance "
                    + "where R_PRAmt='0' "
                    + "and R_Discount ='Y' "
                    + "and r_table='" + tableNo + "' "
                    + "and r_void<>'V' ";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                BalanceBean bean = new BalanceBean();
                bean.setR_QuanCanDisc(rs.getDouble("r_quancandisc"));
                bean.setR_Index(rs.getString("R_Index"));
                bean.setR_Price(rs.getDouble("R_Price"));
                bean.setR_Normal(rs.getString("R_Normal"));

                listBalance.add(bean);
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(CouponDiscountController.class, "error", e);
        } finally {
            mysql.close();
        }

        return listBalance;
    }
}
