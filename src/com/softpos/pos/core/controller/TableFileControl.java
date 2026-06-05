package com.softpos.pos.core.controller;

import com.softpos.util.ThaiUtil;
import com.softpos.pos.core.model.TableSetup;
import com.softpos.pos.core.model.TableFileBean;
import com.softpos.connection.database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.softpos.util.AppLogUtil;

public class TableFileControl {

    public static final int TABLE_READY = 1;
    public static final int TABLE_NOT_ACTIVE = 2;
    public static final int TABLE_NOT_SETUP = 0;
    public static final int TABLE_EXIST_DATA = 3;
    public static final int TABLE_EXIST_DATA_IS_ACTIVE = 4;
    private final MySQLConnect mysqlConnect = new MySQLConnect();

    public TableFileControl() {
        try {
            
            mysqlConnect.closeConnection(TableFileControl.class);
        } catch (Exception e) {
            AppLogUtil.log(TableFileControl.class, "error", e);
        }
    }

    public List<TableFileBean> getALlHoldTable() {
        List<TableFileBean> allTable = new ArrayList<>();
        
        mysqlConnect.closeConnection(TableFileControl.class);
        try {
            mysqlConnect.open(TableFileControl.class);
            try (Statement stmt = mysqlConnect.getConnection().createStatement(); ResultSet rs = stmt.executeQuery("select * from tablefile where TAmount>0")) {
                while (rs.next()) {
                    TableFileBean bean = new TableFileBean();
                    bean.setTcode(rs.getString("Tcode"));
                    bean.setSoneCode(rs.getString("SoneCode"));
                    bean.setMacNo(rs.getString("MacNo"));
                    bean.setCashier(rs.getString("Cashier"));
                    bean.setTLoginTime(rs.getString("TLoginTime"));
                    bean.setTCurTime(rs.getString("TCurTime"));
                    bean.setTCustomer(rs.getInt("TCustomer"));
                    bean.setTItem(rs.getInt("TItem"));
                    bean.setTAmount(rs.getFloat("TAmount"));
                    bean.setTOnAct(rs.getString("TOnAct"));
                    bean.setService(rs.getFloat("Service"));
                    bean.setServiceAmt(rs.getFloat("ServiceAmt"));
                    bean.setEmpDisc(rs.getString("EmpDisc"));
                    bean.setEmpDiscAmt(rs.getFloat("EmpDiscAmt"));
                    bean.setFastDisc(rs.getString("FastDisc"));
                    bean.setFastDiscAmt(rs.getFloat("FastDiscAmt"));
                    bean.setTrainDisc(rs.getString("TrainDisc"));
                    bean.setTrainDiscAmt(rs.getFloat("TrainDiscAmt"));
                    bean.setMemDisc(rs.getString("MemDisc"));
                    bean.setMemDiscAmt(rs.getFloat("MemDiscAmt"));
                    bean.setSubDisc(rs.getString("SubDisc"));
                    bean.setSubDiscAmt(rs.getFloat("SubDiscAmt"));
                    bean.setDiscBath(rs.getFloat("DiscBath"));
                    bean.setProDiscAmt(rs.getFloat("ProDiscAmt"));
                    bean.setSpaDiscAmt(rs.getFloat("SpaDiscAmt"));
                    bean.setCuponDiscAmt(rs.getFloat("CuponDiscAmt"));
                    bean.setItemDiscAmt(rs.getFloat("ItemDiscAmt"));
                    bean.setMemCode(rs.getString("MemCode"));
                    bean.setMemCurAmt(rs.getFloat("MemCurAmt"));
                    bean.setMemName(rs.getString("MemName"));
                    bean.setFood(rs.getFloat("Food"));
                    bean.setDrink(rs.getFloat("Drink"));
                    bean.setProduct(rs.getFloat("Product"));
                    bean.setNetTotal(rs.getFloat("NetTotal"));
                    bean.setPrintTotal(rs.getFloat("PrintTotal"));
                    bean.setPrintChkBill(rs.getString("PrintChkBill"));
                    bean.setPrintCnt(rs.getInt("PrintCnt"));
                    bean.setPrintTime1(rs.getString("PrintTime1"));
                    bean.setPrintTime2(rs.getString("PrintTime2"));
                    bean.setChkBill(rs.getString("ChkBill"));
                    bean.setChkBillTime(rs.getString("ChkBillTime"));
                    bean.setStkCode1(rs.getString("StkCode1"));
                    bean.setStkCode2(rs.getString("StkCode2"));
                    bean.setTDesk(rs.getInt("TDesk"));
                    bean.setTUser(rs.getString("TUser"));
                    bean.setTPause(rs.getString("TPause"));

                    try {
                        bean.setTLoginDate(rs.getDate("TLoginDate"));
                    } catch (SQLException e) {
                        AppLogUtil.log(TableFileControl.class, "error", e);
                    }

                    allTable.add(bean);
                }
                rs.close();
                stmt.close();
            }
        } catch (SQLException e) {

            AppLogUtil.log(TableFileControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(TableFileControl.class);
        }

        return allTable;
    }

    public List<TableFileBean> getALlTable() {
        List<TableFileBean> allTable = new ArrayList<>();
        
        mysqlConnect.closeConnection(TableFileControl.class);
        try {
            mysqlConnect.open(TableFileControl.class);
            try (Statement stmt = mysqlConnect.getConnection().createStatement(); ResultSet rs = stmt.executeQuery("select * from tablefile")) {
                while (rs.next()) {
                    TableFileBean bean = new TableFileBean();
                    bean.setTcode(rs.getString("Tcode"));
                    bean.setSoneCode(rs.getString("SoneCode"));
                    bean.setMacNo(rs.getString("MacNo"));
                    bean.setCashier(rs.getString("Cashier"));
                    bean.setTLoginTime(rs.getString("TLoginTime"));
                    bean.setTCurTime(rs.getString("TCurTime"));
                    bean.setTCustomer(rs.getInt("TCustomer"));
                    bean.setTItem(rs.getInt("TItem"));
                    bean.setTAmount(rs.getFloat("TAmount"));
                    bean.setTOnAct(rs.getString("TOnAct"));
                    bean.setService(rs.getFloat("Service"));
                    bean.setServiceAmt(rs.getFloat("ServiceAmt"));
                    bean.setEmpDisc(rs.getString("EmpDisc"));
                    bean.setEmpDiscAmt(rs.getFloat("EmpDiscAmt"));
                    bean.setFastDisc(rs.getString("FastDisc"));
                    bean.setFastDiscAmt(rs.getFloat("FastDiscAmt"));
                    bean.setTrainDisc(rs.getString("TrainDisc"));
                    bean.setTrainDiscAmt(rs.getFloat("TrainDiscAmt"));
                    bean.setMemDisc(rs.getString("MemDisc"));
                    bean.setMemDiscAmt(rs.getFloat("MemDiscAmt"));
                    bean.setSubDisc(rs.getString("SubDisc"));
                    bean.setSubDiscAmt(rs.getFloat("SubDiscAmt"));
                    bean.setDiscBath(rs.getFloat("DiscBath"));
                    bean.setProDiscAmt(rs.getFloat("ProDiscAmt"));
                    bean.setSpaDiscAmt(rs.getFloat("SpaDiscAmt"));
                    bean.setCuponDiscAmt(rs.getFloat("CuponDiscAmt"));
                    bean.setItemDiscAmt(rs.getFloat("ItemDiscAmt"));
                    bean.setMemCode(rs.getString("MemCode"));
                    bean.setMemCurAmt(rs.getFloat("MemCurAmt"));
                    bean.setMemName(rs.getString("MemName"));
                    bean.setFood(rs.getFloat("Food"));
                    bean.setDrink(rs.getFloat("Drink"));
                    bean.setProduct(rs.getFloat("Product"));
                    bean.setNetTotal(rs.getFloat("NetTotal"));
                    bean.setPrintTotal(rs.getFloat("PrintTotal"));
                    bean.setPrintChkBill(rs.getString("PrintChkBill"));
                    bean.setPrintCnt(rs.getInt("PrintCnt"));
                    bean.setPrintTime1(rs.getString("PrintTime1"));
                    bean.setPrintTime2(rs.getString("PrintTime2"));
                    bean.setChkBill(rs.getString("ChkBill"));
                    bean.setChkBillTime(rs.getString("ChkBillTime"));
                    bean.setStkCode1(rs.getString("StkCode1"));
                    bean.setStkCode2(rs.getString("StkCode2"));
                    bean.setTDesk(rs.getInt("TDesk"));
                    bean.setTUser(rs.getString("TUser"));
                    bean.setTPause(rs.getString("TPause"));
                    try {
                        bean.setTLoginDate(rs.getDate("TLoginDate"));
                        bean.setMemBegin(rs.getDate("MemBegin"));
                        bean.setMemEnd(rs.getDate("MemEnd"));
                    } catch (SQLException e) {
                        AppLogUtil.log(TableFileControl.class, "error", e);
                    }

                    allTable.add(bean);
                }
                rs.close();
                stmt.close();
            }
        } catch (SQLException e) {

            AppLogUtil.log(TableFileControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(TableFileControl.class);
        }

        return allTable;
    }

    public List<TableSetup> getTable(String TCode) {
        List<TableSetup> allTable = new ArrayList<>();
        
        mysqlConnect.closeConnection(TableFileControl.class);
        try {
            mysqlConnect.open(TableFileControl.class);
            String sql = "select t2.Code_ID, t1.TCode from tablefile t1 , tablesetup t2 "
                    + "where t1.TCode=t2.TCode "
                    + "and Code_ID like '" + TCode + "%' "
                    + "order by Code_ID";
            try (Statement stmt = mysqlConnect.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    TableSetup bean = new TableSetup();
                    bean.setCode_ID(rs.getString("Code_ID"));
                    bean.setTCode(rs.getString("TCode"));

                    allTable.add(bean);
                }
                rs.close();
                stmt.close();
            }
        } catch (SQLException e) {

            AppLogUtil.log(TableFileControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(TableFileControl.class);
        }

        return allTable;
    }

    public TableFileBean getData(String table) {
        TableFileBean bean = new TableFileBean();
        
        mysqlConnect.closeConnection(TableFileControl.class);
        try {
            mysqlConnect.open(TableFileControl.class);
            String sql = "select * from tablefile where Tcode='" + table + "' limit 1";
            try (Statement stmt = mysqlConnect.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    bean.setTcode(rs.getString("Tcode"));
                    bean.setSoneCode(rs.getString("SoneCode"));
                    bean.setMacNo(rs.getString("MacNo"));
                    bean.setCashier(rs.getString("Cashier"));
                    bean.setTLoginTime(rs.getString("TLoginTime"));
                    bean.setTCurTime(rs.getString("TCurTime"));
                    bean.setTCustomer(rs.getInt("TCustomer"));
                    bean.setTItem(rs.getInt("TItem"));
                    bean.setTAmount(rs.getFloat("TAmount"));
                    bean.setTOnAct(rs.getString("TOnAct"));
                    bean.setService(rs.getFloat("Service"));
                    bean.setServiceAmt(rs.getFloat("ServiceAmt"));
                    bean.setEmpDisc(rs.getString("EmpDisc"));
                    bean.setEmpDiscAmt(rs.getFloat("EmpDiscAmt"));
                    bean.setFastDisc(rs.getString("FastDisc"));
                    bean.setFastDiscAmt(rs.getFloat("FastDiscAmt"));
                    bean.setTrainDisc(rs.getString("TrainDisc"));
                    bean.setTrainDiscAmt(rs.getFloat("TrainDiscAmt"));

                    bean.setMemDisc(rs.getString("MemDisc"));
                    bean.setMemDiscAmt(rs.getFloat("MemDiscAmt"));
                    bean.setSubDisc(rs.getString("SubDisc"));
                    bean.setSubDiscAmt(rs.getFloat("SubDiscAmt"));
                    bean.setDiscBath(rs.getFloat("DiscBath"));
                    bean.setProDiscAmt(rs.getFloat("ProDiscAmt"));
                    bean.setSpaDiscAmt(rs.getFloat("SpaDiscAmt"));
                    bean.setCuponDiscAmt(rs.getFloat("CuponDiscAmt"));
                    bean.setItemDiscAmt(rs.getFloat("ItemDiscAmt"));
                    bean.setMemCode(rs.getString("MemCode"));
                    if (bean.getMemCode() == null) {
                        bean.setMemCode("");
                    }
                    bean.setMemCurAmt(rs.getFloat("MemCurAmt"));
                    bean.setMemName(ThaiUtil.ASCII2Unicode(rs.getString("MemName")));
                    if (bean.getMemName() == null) {
                        bean.setMemName("");
                    }
                    bean.setFood(rs.getFloat("Food"));
                    bean.setDrink(rs.getFloat("Drink"));
                    bean.setProduct(rs.getFloat("Product"));

                    bean.setNetTotal(rs.getFloat("NetTotal"));
                    bean.setPrintTotal(rs.getFloat("PrintTotal"));
                    bean.setPrintChkBill(rs.getString("PrintChkBill"));
                    bean.setPrintCnt(rs.getInt("PrintCnt"));
                    bean.setPrintTime1(rs.getString("PrintTime1"));
                    bean.setPrintTime2(rs.getString("PrintTime2"));
                    bean.setChkBill(rs.getString("ChkBill"));
                    bean.setChkBillTime(rs.getString("ChkBillTime"));
                    bean.setStkCode1(rs.getString("StkCode1"));
                    bean.setStkCode2(rs.getString("StkCode2"));
                    bean.setTDesk(rs.getInt("TDesk"));
                    bean.setTUser(rs.getString("TUser"));
                    bean.setTPause(rs.getString("TPause"));
                } else {
                    bean.setTcode("null");
                }
                stmt.close();
                rs.close();
            }

        } catch (SQLException e) {

            AppLogUtil.log(TableFileControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(TableFileControl.class);
        }

        return bean;
    }

    public void setDefaultTableFile(String table) {
        
        mysqlConnect.closeConnection(TableFileControl.class);
        try {
            mysqlConnect.open(TableFileControl.class);
            String sql = "update tablefile set "
                    + "MacNo='', Cashier='', TLoginTime='00:00:00', TCurTime='00:00', TCustomer='0', TItem='0', TAmount='0.00', TOnAct='N', "
                    + "Service='0.00', ServiceAmt='0.00', EmpDisc='', EmpDiscAmt='0.00', FastDisc='', FastDiscAmt='0.00', TrainDisc='', "
                    + "TrainDiscAmt='0.00', MemDisc='', MemDiscAmt='0.00', SubDisc='', SubDiscAmt='0.00', DiscBath='0.00', ProDiscAmt='0.00', "
                    + "SpaDiscAmt='0.00', CuponDiscAmt='0.00', ItemDiscAmt='0.00', MemCode='', MemCurAmt='0.00', MemName='', "
                    + "Food='0.00', Drink='0.00', Product='0.00', NetTotal='0.00', PrintTotal='0.00', PrintChkBill='N', "
                    + "PrintCnt='0', PrintTime1='', PrintTime2='', ChkBill='N', ChkBillTime='00:00:00', StkCode2='', TDesk='0', "
                    + "TUser='', TPause='N' "
                    + "where Tcode='" + table + "'";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
                stmt.close();
            }
            if (table.contains("-")) {
                sql = "delete from tablefile where Tcode='" + table + "'";
                try (Statement stmt1 = mysqlConnect.getConnection().createStatement()) {
                    stmt1.executeUpdate(sql);
                    stmt1.close();
                }
            }

        } catch (SQLException e) {

            AppLogUtil.log(TableFileControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(TableFileControl.class);
        }
    }

    public void createNewTableSplit(TableFileBean table, String newTable) {
        
        mysqlConnect.closeConnection(TableFileControl.class);
        try {
            mysqlConnect.open(TableFileControl.class);
            String chk = "select tcode from tablefile where tcode='" + newTable + "' limit 1";
            try (Statement stmt = mysqlConnect.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(chk)) {
                if (rs.next()) {
                    // มีตารางในระบบแล้ว
                    AppLogUtil.info("มีตาราง " + newTable + " ในระบบแล้ว");
                    String[] subString = newTable.split("-");
                    int tbSplit = 0;
                    String text = "";
                    for (String a : subString) {
                        text = subString[1];
                    }
                    tbSplit = Integer.parseInt(text);
                    if (tbSplit > 9) {
                        System.err.println("ไม่สามารถแยกชำระได้เกิน 9 ครั้ง");
                    }
                } else {
                    String sql = "insert into tablefile (Tcode,SoneCode,StkCode1,StkCode2,TLoginTime, TCurTime, TLoginDate, MemBegin, MemEnd) "
                            + "value('" + newTable + "','" + table.getSoneCode() + "','" + table.getStkCode1() + "',' ',curtime(), curtime(), curdate(),'1899-12-30','1899-12-30');";
                    try (Statement stmt1 = mysqlConnect.getConnection().createStatement()) {
                        stmt1.executeUpdate(sql);
                    }
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(TableFileControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(TableFileControl.class);
        }
    }

    public String getSplitTable(String tableNo) {
        
        mysqlConnect.closeConnection(TableFileControl.class);
        try {
            mysqlConnect.open(TableFileControl.class);
            String sql = "select Tcode from tablefile where Tcode='" + tableNo + "' limit 1";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String[] data = rs.getString("Tcode").split("-");
                if (data.length <= 1) {
                    return tableNo;
                }
                int index;
                try {
                    index = Integer.parseInt(data[1]) + 1;
                } catch (NumberFormatException e) {
                    index = 1;
                }
                rs.close();
                stmt.close();
                return getSplitTable(data[0] + "-" + index);
            } else {
                rs.close();
                stmt.close();

            }
        } catch (SQLException e) {

            AppLogUtil.log(TableFileControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(TableFileControl.class);
        }

        return tableNo;
    }

    public boolean checkTableOpened(String tableNo) {
        
        mysqlConnect.closeConnection(TableFileControl.class);
        try {
            mysqlConnect.open(TableFileControl.class);
            String sql = "select Cashier from tablefile where TOnact='Y' and tcode='" + tableNo + "';";
            try (Statement stmt = mysqlConnect.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    return true;
                }
                rs.close();
                stmt.close();
            }
        } catch (SQLException e) {

            AppLogUtil.log(TableFileControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(TableFileControl.class);
        }

        return false;
    }

    public void createNewTable(String tableTemp) {
        
        mysqlConnect.closeConnection(TableFileControl.class);
        try {
            mysqlConnect.open(TableFileControl.class);
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate("delete from tablefile where tcode='" + tableTemp + "';");
                stmt.executeUpdate("insert into tablefile(Tcode) values('" + tableTemp + "');");
                stmt.close();
            }

            setDefaultTableFile(tableTemp);
        } catch (SQLException e) {

            AppLogUtil.log(TableFileControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(TableFileControl.class);
        }
    }

    public void updateTableNotActive(String TABLE_NO) {
        
        mysqlConnect.closeConnection(TableFileControl.class);
        try {
            mysqlConnect.open(TableFileControl.class);
            String sql = "update tablefile set tonact='N' where tcode='" + TABLE_NO + "';";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
                stmt.close();
            }
        } catch (SQLException e) {

            AppLogUtil.log(TableFileControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(TableFileControl.class);
        }
    }

    public int getItemCount(String tableNo) {
        
        mysqlConnect.closeConnection(TableFileControl.class);
        int countItem = 0;
        try {
            mysqlConnect.open(TableFileControl.class);
            String sql = "select r_linkindex from balance "
                    + "where r_table='" + tableNo + "' "
                    + "and r_linkindex<>'' "
                    + "group by R_LinkIndex";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    countItem++;
                }

                rs.close();

                String sql1 = "select r_index from balance "
                        + "where r_table='" + tableNo + "' "
                        + "and r_linkindex=''";
                rs = stmt.executeQuery(sql1);
                while (rs.next()) {
                    countItem++;
                }

                rs.close();
                String sql2 = "select r_index from balance "
                        + "where r_table='" + tableNo + "' "
                        + "and r_linkindex is null";
                rs = stmt.executeQuery(sql2);
                while (rs.next()) {
                    countItem++;
                }

                rs.close();
                stmt.close();
            }
        } catch (SQLException e) {

            AppLogUtil.log(TableFileControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(TableFileControl.class);
        }

        return countItem;
    }

    public boolean checkTableMoreItem(String tableNo) {
        boolean isValid = false;
        
        mysqlConnect.closeConnection(TableFileControl.class);
        try {
            mysqlConnect.open(TableFileControl.class);
            String checkTablefile = "select titem from tablefile "
                    + "where tcode = '" + tableNo + "' and titem>'0' limit 1 ";
            try (ResultSet rs = mysqlConnect.executeQuery(checkTablefile)) {
                if (rs.next()) {
                    isValid = true;
                }
            }
        } catch (SQLException e) {

            AppLogUtil.log(TableFileControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(TableFileControl.class);
        }

        return isValid;
    }

    public void updateBalanceType(String rIndex, String rEtd, String tableNo) {
        
        mysqlConnect.open(TableFileControl.class);
        try {
            String sql = "update balance set R_ETD='" + rEtd + "' "
                    + "where R_Index='" + rIndex + "' and R_Table='" + tableNo + "'";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            AppLogUtil.log(TableFileControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(TableFileControl.class);
        }
    }

    public void saveCustomerName(String tableNo, String memName) {

        mysqlConnect.open(TableFileControl.class);
        try {
            String sql = "UPDATE tablefile SET "
                    + "MemName='" + ThaiUtil.Unicode2ASCII(memName) + "' "
                    + "WHERE Tcode = '" + tableNo + "'";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            AppLogUtil.log(TableFileControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(TableFileControl.class);
        }
    }

    public void saveCustomerCount(String tableNo, String cus, String memName) {
        
        mysqlConnect.open(TableFileControl.class);
        try {
            String sql = "UPDATE tablefile SET "
                    + "TCustomer = " + cus + ","
                    + "MemName='" + ThaiUtil.Unicode2ASCII(memName) + "' "
                    + "WHERE Tcode = '" + tableNo + "'";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            AppLogUtil.log(TableFileControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(TableFileControl.class);
        }
    }

    public String getCustomerName(String tableNo) {
        
        mysqlConnect.open(TableFileControl.class);
        try {
            String sql = "select MemName from tablefile where Tcode = '" + tableNo + "' limit 1";
            try (Statement stmt = mysqlConnect.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    String name = rs.getString("MemName");
                    return name != null ? ThaiUtil.ASCII2Unicode(name) : "";
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(TableFileControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(TableFileControl.class);
        }
        return "";
    }

    public void setTableNotActive(String tableNo) {
        
        mysqlConnect.open(TableFileControl.class);
        try {
            String sql = "update tablefile set TonAct='N' where (TCode='" + tableNo + "')";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            AppLogUtil.log(TableFileControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(TableFileControl.class);
        }
    }

    public List<Object[]> getActiveTables() {
        List<Object[]> list = new ArrayList<>();
        
        mysqlConnect.open(TableFileControl.class);
        try {
            String sql = "select Tcode, Tlogindate, TCurTime, TCustomer, TItem, TAmount,"
                    + "TOnAct, ChkBill, PrintChkBill"
                    + " from tablefile "
                    + "where tonact='Y' or TAmount>0 or TItem > 0 or Tcustomer > 0 "
                    + "order by tcode";
            try (Statement stmt = mysqlConnect.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    list.add(new Object[]{
                        rs.getString("Tcode"),
                        rs.getDate("Tlogindate"),
                        rs.getString("TCurTime"),
                        rs.getFloat("TCustomer"),
                        rs.getFloat("TItem"),
                        rs.getFloat("TAmount"),
                        rs.getString("TOnAct"),
                        rs.getString("ChkBill"),
                        rs.getString("PrintChkBill")
                    });
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(TableFileControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(TableFileControl.class);
        }
        return list;
    }

}
