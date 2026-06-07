package com.softpos.pos.core.controller;


import com.softpos.pos.core.model.ProductBean;
import com.softpos.pos.core.model.STCardBean;
import com.softpos.pos.core.model.StkFileBean;
import com.softpos.connection.database.MySQLConnect;
import com.softpos.constants.PublicVar;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.softpos.util.AppLogUtil;

public class StockControl {

    private int iSubCheck = 0;
    public static final int STOCK_MAIN = 0;
    public static final int STOCK_TABLE_POS = 1;
    public static final int STOCK_SUB = 2;
    private final MySQLConnect mysqlConnect = new MySQLConnect();
    private final ProductControl productControl = AppContext.getProductControl();

    public StkFileBean getDataStkFile(String sql) {
        StkFileBean bean = new StkFileBean();
        
        mysqlConnect.open(this.getClass());
        try {
            try (Statement stmt = mysqlConnect.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
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
            }
        } catch (SQLException e) {

            AppLogUtil.log(StockControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return bean;
    }

    public StkFileBean getDataStkFile(String PCode, String stockCode) {
        String sql = "select * from stkfile where BPCode='" + PCode + "' and BStk='" + stockCode + "'";

        return getDataStkFile(sql);
    }

    public void saveSubTCard(STCardBean bean, String ETD) {
        mysqlConnect.open(this.getClass());
        try {
            //หาว่าสินค้ามีส่วนประกอบหรือไม่ ?
            String sql = "select * from pset "
                    + "where PCode='" + bean.getS_PCode() + "' ";
            boolean isIntoSub;
            try (Statement stmt = mysqlConnect.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss");
                isIntoSub = false;
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
                    stcBean.setS_OutCost(R_Quan * PRODUCT_PRICE(PSubCode, ETD));
                    stcBean.setS_ACost(0);
                    
                    String addLink = stcBean.getS_SubNo() + "@" + stcBean.getS_PCode();
                    stcBean.setS_SubNo("@" + bean.getS_PCode());
                    stcBean.setS_Link(addLink);
                    
                    String sql1 = "insert into stcard (S_Date,S_No,S_SubNo,S_Que,S_PCode,S_Stk,S_In,S_Out,S_InCost,S_OutCost,S_ACost,S_Rem,S_User,S_EntryDate,S_EntryTime,S_Link) "
                            + "values(curdate(),'" + stcBean.getS_No() + "','" + stcBean.getS_SubNo() + "','" + stcBean.getS_Que() + "','" + stcBean.getS_PCode() + "','" + stcBean.getS_Stk() + "',"
                            + "'" + stcBean.getS_In() + "','" + stcBean.getS_Out() + "','" + stcBean.getS_InCost() + "','" + stcBean.getS_OutCost() + "','" + stcBean.getS_ACost() + "','',"
                            + "'" + stcBean.getS_User() + "',curdate(),curtime(),'" + stcBean.getS_Link() + "')";
                    try (Statement stmt1 = mysqlConnect.getConnection().createStatement()) {
                        int Update = stmt1.executeUpdate(sql1);
                        if (Update > 0) {
                            iSubCheck++;
                            saveSubTCard(stcBean, ETD);
                        }
                    }
                }              }

            if (!isIntoSub) {
                iSubCheck = 1;
            }
        } catch (SQLException e) {
            AppLogUtil.log(StockControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
    }

    public List<String[]> listAllStock() {
        List<String[]> result = new ArrayList<>();

        mysqlConnect.open(this.getClass());
        try {
            String sql = "select * from stockfile;";
            try (Statement stmt = mysqlConnect.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    result.add(new String[]{
                        rs.getString("stkcode"),
                        ThaiUtil.ASCII2Unicode(rs.getString("stkname")),
                        rs.getString("flage")
                    });
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(StockControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return result;
    }

    public String[] findProductByCode(String pcode) {
        String[] row = null;

        mysqlConnect.open(this.getClass());
        try {
            String sql = "select pcode, pdesc from product where pcode='" + pcode + "'";
            try (Statement stmt = mysqlConnect.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    row = new String[]{
                        rs.getString("pcode"),
                        ThaiUtil.ASCII2Unicode(rs.getString("pdesc"))
                    };
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(StockControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return row;
    }

    public void addOutStockItem(String pcode) {
        mysqlConnect.open(this.getClass());
        try {
            String sql = "select pcode,pdesc from product where pcode='" + pcode + "' limit 1";
            try (Statement stmt = mysqlConnect.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    String foundCode = rs.getString("pcode");
                    try (Statement stmt2 = mysqlConnect.getConnection().createStatement()) {
                        stmt2.executeUpdate("delete from outstocklist where pcode='" + foundCode + "'");
                        stmt2.executeUpdate("insert into outstocklist values('" + foundCode + "');");
                    }
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(StockControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
    }

    public List<String[]> listOutStockItems() {
        List<String[]> result = new ArrayList<>();

        mysqlConnect.open(this.getClass());
        try {
            String sql = "select product.pcode,pdesc,punit1 "
                    + "from outstocklist o, product "
                    + "where o.pcode=product.pcode";
            try (Statement stmt = mysqlConnect.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    result.add(new String[]{
                        rs.getString("pcode"),
                        rs.getString("pdesc"),
                        rs.getString("punit1")
                    });
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(StockControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return result;
    }

    public void removeOutStockItem(String pcode) {
        mysqlConnect.open(this.getClass());
        try {
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                int i = stmt.executeUpdate("delete from outstocklist where pcode='" + pcode + "'");
                if (i > 0) {
                    AppLogUtil.info("Delete success.");
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(StockControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
    }

    public double PRODUCT_PRICE(String PCode, String ETD) {
        double price = 0.00;
        try {
            ProductBean product = productControl.getProductByPCode(PCode);

            //กำหนดราคาเองตามป้าย
            if (product.getPStatus().equals("S")) {
                price = PublicVar.PRICE_BY_USER;
            } //กำหนดราคาตาม Plu E,T,D,P,W หรือราคา 1-5
            else if (product.getPStatus().equals("P") || product.getPStatus().equals("T")) {
                switch (ETD) {
                    case "E":
                        price = product.getPPrice11();
                        break;
                    case "T":
                        price = product.getPPrice12();
                        break;
                    case "D":
                        price = product.getPPrice13();
                        break;
                    case "P":
                        price = product.getPPrice14();
                        break;
                    case "W":
                        price = product.getPPrice15();
                        break;
                    default:
                        break;
                }
            }

        } catch (Exception e) {
            AppLogUtil.log(StockControl.class, "error", e);
            price = 0.00;
        }
        return price;
    }

}
