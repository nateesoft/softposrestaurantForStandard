package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.ProductBean;
import com.softpos.pos.core.model.StkFileBean;
import com.softpos.pos.core.model.STCardBean;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import util.MSG;

public class StockControl {

    private int iSubCheck = 0;
    public static final int STOCK_MAIN = 0;
    public static final int STOCK_TABLE_POS = 1;
    public static final int STOCK_SUB = 2;

    public StockControl() {
    }

    public boolean Active(String stock) {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        String sql = "select * from stockfile where StkCode='" + stock + "' and flage='Y'";
        try {
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                rs.close();
                stmt.close();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            return false;
        } finally {
            mysql.close();
        }
    }

    public double GET_PRODUCT_QTY(String PCode, String stockCode) {
        double qty = 0;
        SimpleDateFormat sp = new SimpleDateFormat("MM");
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            int month = Integer.parseInt(sp.format(new Date())) + 12;
            String sql = "select BQty" + month + " from stkfile where BPCode='" + PCode + "' and BStk='" + stockCode + "'";
            try (Statement stmt = mysql.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    qty = rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        } finally {
            mysql.close();
        }

        return qty;
    }

    public StkFileBean getDataStkFile(String sql) {
        StkFileBean bean = new StkFileBean();
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                bean.setBPCode(rs.getString("BPCode"));
                bean.setBStk(rs.getString("BStk"));
                bean.setBQty(rs.getFloat("BQty"));
                bean.setBAmt(rs.getFloat("BAmt"));
                bean.setBTotalAmt(rs.getFloat("BTotalAmt"));
                bean.setBQty0(rs.getFloat("BQty0"));
                bean.setBQty1(rs.getFloat("BQty1"));
                bean.setBQty2(rs.getFloat("BQty2"));
                bean.setBQty3(rs.getFloat("BQty3"));
                bean.setBQty4(rs.getFloat("BQty4"));
                bean.setBQty5(rs.getFloat("BQty5"));
                bean.setBQty6(rs.getFloat("BQty6"));
                bean.setBQty7(rs.getFloat("BQty7"));
                bean.setBQty8(rs.getFloat("BQty8"));
                bean.setBQty9(rs.getFloat("BQty9"));
                bean.setBQty10(rs.getFloat("BQty10"));
                bean.setBQty11(rs.getFloat("BQty11"));
                bean.setBQty12(rs.getFloat("BQty12"));
                bean.setBQty13(rs.getFloat("BQty13"));
                bean.setBQty14(rs.getFloat("BQty14"));
                bean.setBQty15(rs.getFloat("BQty15"));
                bean.setBQty16(rs.getFloat("BQty16"));
                bean.setBQty17(rs.getFloat("BQty17"));
                bean.setBQty18(rs.getFloat("BQty18"));
                bean.setBQty19(rs.getFloat("BQty19"));
                bean.setBQty20(rs.getFloat("BQty20"));
                bean.setBQty21(rs.getFloat("BQty21"));
                bean.setBQty22(rs.getFloat("BQty22"));
                bean.setBQty23(rs.getFloat("BQty23"));
                bean.setBQty24(rs.getFloat("BQty24"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        } finally {
            mysql.close();
        }

        return bean;
    }

    public StkFileBean getDataStkFile(String PCode, String stockCode) {
        String sql = "select * from stkfile where BPCode='" + PCode + "' and BStk='" + stockCode + "'";

        return getDataStkFile(sql);
    }

    public void updateSTKFileAdd(String BPCode, String StockCode) {
        SimpleDateFormat sim = new SimpleDateFormat("MM");
        int month;
        try {
            month = 12 + Integer.parseInt(sim.format(new Date()));
        } catch (NumberFormatException e) {
            month = 13;
        }
        String sql = "UPDATE stkfile "
                + "set BQty" + month + "=BQty" + month + "-1 "
                + "where BPCode='" + BPCode + "' and BStk='" + StockCode + "'";
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            Statement stmt = mysql.getConnection().createStatement();
            int Update = stmt.executeUpdate(sql);
            if (Update > 0) {

            }
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        } finally {
            mysql.close();
        }
    }

    public void updateSTKFileVoid(String BPCode, String StockCode) {
        SimpleDateFormat sim = new SimpleDateFormat("MM");
        int month;
        try {
            month = 12 + Integer.parseInt(sim.format(new Date()));
        } catch (NumberFormatException e) {
            month = 13;
        }
        String sql = "UPDATE stkfile "
                + "set BQty" + month + "=BQty" + month + "+1 "
                + "where BPCode='" + BPCode + "' and BStk='" + StockCode + "'";
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            Statement stmt = mysql.getConnection().createStatement();
            int Update = stmt.executeUpdate(sql);
            if (Update > 0) {

            }
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        } finally {
            mysql.close();
        }
    }

    public void saveSTCard(STCardBean bean, String ETD) {
        /*
         ????????????????????????????????????????????????????????????????????????????????????
         19/11/2013	1-09:35:45	NULL	0	1501	A1	0	1	0	16	0	SAL	1001	19/11/2013	9:35	NULL
        
         ????????????????????????????????? void ??????????????????
         19/11/2013	E1-09:44:59	NULL	0	1501	A1	0	-1	0	-16	0	SAL	9999	19/11/2013	9:44	NULL 
         */
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "insert into stcard (S_Date,S_No,S_SubNo,S_Que,S_PCode,S_Stk,S_In,S_Out,S_InCost,S_OutCost,S_ACost,S_Rem,S_User,S_EntryDate,S_EntryTime,S_Link) "
                    + "values(curdate(),'" + bean.getS_No() + "','" + bean.getS_SubNo() + "','" + bean.getS_Que() + "','" + bean.getS_PCode() + "','" + bean.getS_Stk() + "',"
                    + "'" + bean.getS_In() + "','" + bean.getS_Out() + "','" + bean.getS_InCost() + "','" + bean.getS_OutCost() + "','" + bean.getS_ACost() + "','" + bean.getS_Rem() + "',"
                    + "'" + bean.getS_User() + "',curdate(),curtime(),'" + bean.getS_Link() + "')";
            Statement stmt = mysql.getConnection().createStatement();
            int Update = stmt.executeUpdate(sql);

            if (Update > 0) {
                //save item sub
                /*                
                 ????????????????????????????????????????????????????????????????????????????????? comment ???????????????????????????????????????
                 ????????????????????????????????????????????????????????????????????????????????? comment ???????????????????????????????????????
                 ????????????????????????????????????????????????????????????????????????????????? comment ???????????????????????????????????????
                 ????????????????????????????????????????????????????????????????????????????????? comment ???????????????????????????????????????
                 ????????????????????????????????????????????????????????????????????????????????? comment ???????????????????????????????????????
                 ????????????????????????????????????????????????????????????????????????????????? comment ???????????????????????????????????????
                 ????????????????????????????????????????????????????????????????????????????????? comment ???????????????????????????????????????
                 ????????????????????????????????????????????????????????????????????????????????? comment ???????????????????????????????????????
                 ????????????????????????????????????????????????????????????????????????????????? comment ???????????????????????????????????????
                 ????????????????????????????????????????????????????????????????????????????????? comment ???????????????????????????????????????
                 ????????????????????????????????????????????????????????????????????????????????? comment ???????????????????????????????????????
                 ????????????????????????????????????????????????????????????????????????????????? comment ???????????????????????????????????????
                 ????????????????????????????????????????????????????????????????????????????????? comment ???????????????????????????????????????
                 ????????????????????????????????????????????????????????????????????????????????? comment ???????????????????????????????????????
                 ????????????????????????????????????????????????????????????????????????????????? comment ???????????????????????????????????????
                 ????????????????????????????????????????????????????????????????????????????????? comment ???????????????????????????????????????
                 ????????????????????????????????????????????????????????????????????????????????? comment ???????????????????????????????????????
                 ????????????????????????????????????????????????????????????????????????????????? comment ???????????????????????????????????????
                
                 */
                //saveSubTCard(bean, ETD);
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        } finally {
            mysql.close();
        }

    }

    public void saveSubTCard(STCardBean bean, String ETD) {
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            //?????????????????????????????????????????????????????????????????????????????????????????? ?
            String sql = "select * from pset "
                    + "where PCode='" + bean.getS_PCode() + "' ";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss");
            boolean isIntoSub = false;
            while (rs.next()) {
                isIntoSub = true;
                String PSubCode = rs.getString("PSubCode");
                double R_Quan = rs.getDouble("PSubQty");

                STCardBean stcBean = new STCardBean();
                stcBean.setS_Rem("SALE");
                stcBean.setS_User(bean.getS_User());
                stcBean.setS_Stk(bean.getS_Stk());
                stcBean.setS_PCode(PSubCode);
                stcBean.setS_No("1" + s.format(new Date()));
                stcBean.setS_Que(0);
                stcBean.setS_Out(R_Quan);
                stcBean.setS_InCost(0);
                stcBean.setS_OutCost(R_Quan * StockControl.PRODUCT_PRICE(PSubCode, ETD));
                stcBean.setS_ACost(0);

                String addLink = stcBean.getS_SubNo() + "@" + stcBean.getS_PCode();
                stcBean.setS_SubNo("@" + bean.getS_PCode());
                stcBean.setS_Link(addLink);

                String sql1 = "insert into stcard (S_Date,S_No,S_SubNo,S_Que,S_PCode,S_Stk,S_In,S_Out,S_InCost,S_OutCost,S_ACost,S_Rem,S_User,S_EntryDate,S_EntryTime,S_Link) "
                        + "values(curdate(),'" + stcBean.getS_No() + "','" + stcBean.getS_SubNo() + "','" + stcBean.getS_Que() + "','" + stcBean.getS_PCode() + "','" + stcBean.getS_Stk() + "',"
                        + "'" + stcBean.getS_In() + "','" + stcBean.getS_Out() + "','" + stcBean.getS_InCost() + "','" + stcBean.getS_OutCost() + "','" + stcBean.getS_ACost() + "','',"
                        + "'" + stcBean.getS_User() + "',curdate(),curtime(),'" + stcBean.getS_Link() + "')";
                Statement stmt1 = mysql.getConnection().createStatement();
                int Update = stmt1.executeUpdate(sql1);
                if (Update > 0) {
                    iSubCheck++;
                    saveSubTCard(stcBean, ETD);
                }
                stmt1.close();
            }

            if (!isIntoSub) {
                iSubCheck = 1;
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        }finally{
            mysql.close();
        }
    }

    public static double PRODUCT_PRICE(String PCode, String ETD) {
        double price = 0.00;
        try {
            ProductControl proCont = new ProductControl();
            ProductBean product = proCont.getData(PCode);

            //?????????????????????????????????????????????????????????
            if (product.getPStatus().equals("S")) {
                price = Value.PRICE_BY_USER;
            } //???????????????????????????????????? Plu E,T,D,P,W ???????????????????????? 1-5
            else if (product.getPStatus().equals("P") || product.getPStatus().equals("T")) {
                if (ETD.equals("E")) {
                    price = product.getPPrice11();
                } else if (ETD.equals("T")) {
                    price = product.getPPrice12();
                } else if (ETD.equals("D")) {
                    price = product.getPPrice13();
                } else if (ETD.equals("P")) {
                    price = product.getPPrice14();
                } else if (ETD.equals("W")) {
                    price = product.getPPrice15();
                }
            }

        } catch (Exception e) {
            MSG.ERR(e.getMessage());
            price = 0.00;
        }

        return price;
    }

}
