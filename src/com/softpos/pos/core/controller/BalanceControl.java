package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.BalanceBean;
import com.softpos.pos.core.model.ProductBean;
import com.softpos.pos.core.model.MemberBean;
import com.softpos.member.MemberControl;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import sun.natee.project.util.DateFormat;
import sun.natee.project.util.ThaiUtil;
import util.DateConvert;
import util.MSG;

public class BalanceControl {

    private BalanceBean balanceCurrent = new BalanceBean();

    public String getLastIndex(String tableNo) {
        String tempIndex = "";
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            String sql = "select max(R_Index) R_Index "
                    + "from balance "
                    + "where r_table='" + tableNo + "';";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                if (rs.next()) {
                    tempIndex = rs.getString("R_Index");
                } else {
                    tempIndex = "";
                }
            }
        } catch (SQLException e) {
            MSG.ERR("BalanceControl:" + e.getMessage());
        } finally {
            mysql.close();
        }

        return tempIndex;
    }

    
    public boolean saveBalance(BalanceBean bb) {
        try {
            ProductControl pc = new ProductControl();
            BalanceBean bean = new BalanceBean();

            //### Save to balance ###
            bean.setR_ETD(bb.getR_ETD());//set default E
            bean.setR_Table(bb.getR_Table());
            bean.setR_Emp(bb.getR_Emp());
            bean.setR_PluCode(bb.getR_PluCode());
            bean.setR_Quan(bb.getR_Quan());
            bean.setR_PrQuan(0);
            bean.setMacno(Value.MACNO);

            bean.setR_KicPrint(bb.getR_KicPrint());
            bean.setR_Pause(bb.getR_Pause());

            /*?????????????????????????????????????????????????????????*/
            bean.setR_PrType("");//set default ''
            bean.setR_PrCode("");//set default ''
            bean.setR_PrDisc(0);
            bean.setR_PrBath(0);
            bean.setR_PrAmt(0);
            bean.setR_PrCuType("");//set default ''
            bean.setR_PrCuCode("");
            bean.setR_PrChkType("");

            // ??????????????????????????????????????????????????????            
            bean.setR_PrSubType(bb.getR_PrSubType());
            bean.setR_PrSubCode(bb.getR_PrSubCode());
            bean.setR_PrSubQuan(bb.getR_PrSubQuan());
            bean.setR_PrSubDisc(bb.getR_PrSubDisc());
            bean.setR_PrSubBath(bb.getR_PrSubBath());
            bean.setR_PrSubAmt(bb.getR_PrSubAmt());
            bean.setR_QuanCanDisc(bb.getR_QuanCanDisc());

            bean.setR_Void("");
            bean.setR_Serve("");
            bean.setR_PrintOK("Y");
            bean.setPosStk("0");
            bean.setR_Order("1");
            bean.setR_MemSum("N");
            bean.setR_MoveItem("N");
            bean.setR_MovePrint("N");
            //bill.setR_CardPay("N");
            bean.setR_Pickup("P");
            bean.setR_KicOK("");

            bean.setR_CallWait("");
            bean.setR_VoidPause("");

            bean.setR_Opt1(bb.getR_Opt1());
            bean.setR_Opt2(bb.getR_Opt2());
            bean.setR_Opt3(bb.getR_Opt3());
            bean.setR_Opt4(bb.getR_Opt4());
            bean.setR_Opt5(bb.getR_Opt5());
            bean.setR_Opt6(bb.getR_Opt6());
            bean.setR_Opt7(bb.getR_Opt7());
            bean.setR_Opt8(bb.getR_Opt8());
            bean.setR_Opt9(bb.getR_Opt9());

            bean.setVoidMSG(bb.getVoidMSG());

            bean.setR_MoveFrom("");
            bean.setR_MoveUser("");

            bean.setCashier(bb.getCashier());

            bean.setR_Status("Y");
            bean.setR_Void("");
            bean.setR_VoidUser("");
            bean.setR_VoidTime("");
            bean.setR_MoveFlag("0");

            ProductBean product = pc.getData(bb.getR_PluCode());
            bean.setR_Index(bb.getR_Index());//?????????????????????????????????/?????????????????????????????????????????????????????????
            bean.setR_Table(bb.getR_Table());//?????????????????????????????????
            //bean.setR_Date(Calendar.getInstance().getTime());
            //bean.setR_Time("16:04:14");
//            bean.setMacno(bb.getMacno());//??????????????????????????????????????????
            bean.setCashier(bb.getCashier());//???????????? login
            bean.setR_Emp(bb.getR_Emp());//???????????????????????????
            bean.setR_PluCode(product.getPCode());//??????????????????????????????
            bean.setR_PName(product.getPDesc());//??????????????????????????????
            bean.setR_PEName(product.getPEDesc());//??????????????????????????????ENG
            bean.setR_Unit(ThaiUtil.Unicode2ASCII(product.getPUnit1()));//????????????????????????
            bean.setR_Group(product.getPGroup());//?????????????????????????????????
            bean.setR_Status(product.getPStatus());//??????????????????????????????????????? (P=???????????????????????????(Plu), D=?????????????????????????????????????????????(SDP), T=????????????????????????????????????(Time))
            bean.setR_Normal(product.getPNormal());//????????????????????????????????? (N=Normal, C=Consign, S=Special)
            bean.setR_Discount(product.getPDiscount());//??????????????????????????????????????????????????????
            bean.setR_Service(product.getPService());//????????????????????????????????????
            bean.setR_Stock(product.getPStock());//???????????????????????????????????????????????????
            bean.setR_Set(product.getPSet());//????????????????????????????????????????????????????????????
            bean.setR_Vat(product.getPVat());//??????????????????????????????????????????
            bean.setR_Type(product.getPType());//????????????????????????????????????
            bean.setR_Quan(bb.getR_Quan());//????????????????????????????????????
            bean.setR_Price(bb.getR_Price());//??????????????????????????????
            bean.setR_Total(bb.getR_Quan() * bb.getR_Price());//?????????????????????            
            bean.setR_Kic(product.getPKic());

            //Add new Field
//            bean.setStkCode(product.getMSStk());
            bean.setStkCode(bb.getStkCode());
            bean.setPosStk(product.getPOSStk());

            if (bb.getR_LinkIndex() == null) {
                bean.setR_LinkIndex("");
            } else {
                bean.setR_LinkIndex(bb.getR_LinkIndex());
            }

            bean.setR_Date(bb.getR_Date());
            bean.setR_Time(bb.getR_Time());

            //copy balance bean
            balanceCurrent = bean;

            return saveBillNoSQL(bean);

        } catch (Exception e) {
            MSG.ERR("BalanceControl:saveBalance:" + e.getMessage());
            return false;
        }
    }

    public boolean moveBalanceAll(String table, BalanceBean bean) {
        BalanceBean bill = bean;

        //### Save to balance ###
        bill.setR_Table(table);
        String tempRIndex = bill.getR_Index();
        bill.setR_Index(getIndexBalance(bill.getR_Table()));
        bill.setR_Time(bean.getR_Time());
        bill.setR_Date(bean.getR_Date());
        bill.setR_MoveFrom(tempRIndex);

        //copy balance bean
        this.balanceCurrent = bill;

        return saveBillNoSQL(bill);
    }

    public BalanceBean getCurrentBalance() {
        return balanceCurrent;
    }

    private boolean saveBillNoSQL(BalanceBean bean) {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            if (bean.getR_Date() == null) {
                bean.setR_Date(new Date());
            }
            if (bean.getR_Time() == null) {
                SimpleDateFormat sss = new SimpleDateFormat("HH:mm:ss");
                bean.setR_Time(sss.format(new Date()));
            }
            String kicprint = bean.getR_KicPrint();
            if (kicprint == null) {
                bean.setR_KicPrint("");
            }
            String sqlInsert = "insert into balance "
                    + "(R_Index,R_Table,R_Date,R_Time,Macno,"
                    + "Cashier,R_Emp,R_PluCode,R_PName,R_Unit,"
                    + "R_Group,R_Status,R_Normal,R_Discount,R_Service,"
                    + "R_Stock,R_Set,R_Vat,R_Type,R_ETD,R_Quan,R_Price,"
                    + "R_Total,R_PrType,R_PrCode,R_PrDisc,R_PrBath,"
                    + "R_PrAmt,R_DiscBath,R_PrCuType,R_PrCuQuan,R_PrCuAmt,"
                    + "R_Redule,R_Kic,R_KicPrint,R_Void,R_VoidUser,R_VoidTime,"
                    + "R_Opt1,R_Opt2,R_Opt3,R_Opt4,R_Opt5,R_Opt6,R_Opt7,R_Opt8,"
                    + "R_Opt9,R_PrCuCode,R_Serve,R_PrintOK,R_KicOK,StkCode,"
                    + "PosStk,R_PrChkType,R_PrQuan,R_PrSubType,R_PrSubCode,"
                    + "R_PrSubQuan,R_PrSubDisc,R_PrSubBath,R_PrSubAmt,R_PrSubAdj,"
                    + "R_PrCuDisc,R_PrCuBath,R_PrCuAdj,R_QuanCanDisc,R_Order,R_PItemNo,"
                    + "R_PKicQue,R_MemSum,R_MoveItem,R_MoveFrom,R_MoveUser,R_MoveFlag,"
                    + "R_MovePrint,R_Pause,R_PrVcType,R_PrVcCode,R_LinkIndex,R_VoidPause,R_SPIndex,VoidMSG,R_PEName) "
                    + "values('" + bean.getR_Index() + "','" + bean.getR_Table() + "',"
                    + "'" + DateFormat.getMySQL_Date(bean.getR_Date()) + "','" + bean.getR_Time() + "','"
                    + bean.getMacno() + "','" + bean.getCashier() + "','"
                    + bean.getR_Emp() + "','" + bean.getR_PluCode() + "','" + ThaiUtil.Unicode2ASCII(bean.getR_PName()) + "','"
                    + bean.getR_Unit() + "','" + bean.getR_Group() + "','" + bean.getR_Status() + "','"
                    + bean.getR_Normal() + "','" + bean.getR_Discount() + "','" + bean.getR_Service() + "','"
                    + bean.getR_Stock() + "','" + bean.getR_Set() + "','" + bean.getR_Vat() + "','"
                    + bean.getR_Type() + "','" + bean.getR_ETD() + "','" + bean.getR_Quan() + "','"
                    + bean.getR_Price() + "','" + bean.getR_Total() + "','" + bean.getR_PrType() + "','"
                    + bean.getR_PrCode() + "','" + bean.getR_PrDisc() + "','" + bean.getR_PrBath() + "','"
                    + bean.getR_PrAmt() + "','" + bean.getR_DiscBath() + "','" + bean.getR_PrCuType() + "','"
                    + bean.getR_PrCuQuan() + "','" + bean.getR_PrCuAmt() + "','" + bean.getR_Redule() + "','"
                    + bean.getR_Kic() + "','" + bean.getR_KicPrint() + "','" + bean.getR_Void() + "','"
                    + bean.getR_VoidUser() + "','" + bean.getR_VoidTime() + "','" + ThaiUtil.Unicode2ASCII(bean.getR_Opt1()) + "','"
                    + ThaiUtil.Unicode2ASCII(bean.getR_Opt2()) + "','" + ThaiUtil.Unicode2ASCII(bean.getR_Opt3()) + "','" + ThaiUtil.Unicode2ASCII(bean.getR_Opt4()) + "','"
                    + ThaiUtil.Unicode2ASCII(bean.getR_Opt5()) + "','" + ThaiUtil.Unicode2ASCII(bean.getR_Opt6()) + "','" + ThaiUtil.Unicode2ASCII(bean.getR_Opt7()) + "','"
                    + ThaiUtil.Unicode2ASCII(bean.getR_Opt8()) + "','" + ThaiUtil.Unicode2ASCII(bean.getR_Opt9()) + "','" + bean.getR_PrCuCode() + "','"
                    + bean.getR_Serve() + "','" + bean.getR_PrintOK() + "','" + bean.getR_KicOK() + "','"
                    + bean.getStkCode() + "','" + bean.getPosStk() + "','" + bean.getR_PrChkType() + "','"
                    + bean.getR_PrQuan() + "','" + bean.getR_PrSubType() + "','" + bean.getR_PrSubCode() + "','"
                    + bean.getR_PrSubQuan() + "','" + bean.getR_PrSubDisc() + "','" + bean.getR_PrSubBath() + "','"
                    + bean.getR_PrSubAmt() + "','" + bean.getR_PrSubAdj() + "','" + bean.getR_PrCuDisc() + "','"
                    + bean.getR_PrCuBath() + "','" + bean.getR_PrCuAdj() + "','" + bean.getR_QuanCanDisc() + "','"
                    + bean.getR_Order() + "','" + bean.getR_PItemNo() + "','" + bean.getR_PKicQue() + "','"
                    + bean.getR_MemSum() + "','" + bean.getR_MoveItem() + "','" + bean.getR_MoveFrom() + "','"
                    + bean.getR_MoveUser() + "','" + bean.getR_MoveFlag() + "','" + bean.getR_MovePrint() + "','"
                    + bean.getR_Pause() + "','','','" + bean.getR_LinkIndex() + "','','" + bean.getR_Index() + "','" + ThaiUtil.Unicode2ASCII(bean.getVoidMSG()) + "','" + bean.getR_PEName() + "')";
            int iUpdate = mysql.getConnection().createStatement().executeUpdate(sqlInsert);
            String sqlUpNull = "update balance set r_linkindex='' where r_linkindex=''";
            mysql.getConnection().createStatement().executeUpdate(sqlUpNull);
            //clear Option file...
            Value.ClearOPT();
            return iUpdate > 0;
        } catch (SQLException e) {
            MSG.ERR("BalanceControl:saveBillNoSQL:" + e.getMessage());
            return false;
        } finally {
            mysql.close();
        }
    }

    public List<BalanceBean> getAllBalance(String table) {
        List<BalanceBean> beanData = new ArrayList<>();
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            String sql = "select * from balance "
                    + "where R_Table='" + table + "' "
                    + "order by R_Index";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                while (rs.next()) {
                    BalanceBean balanceBean = new BalanceBean();
                    balanceBean.setR_Index(rs.getString("R_Index"));
                    balanceBean.setR_Table(rs.getString("R_Table"));
                    balanceBean.setR_Time(rs.getString("R_Time"));
                    balanceBean.setMacno(rs.getString("Macno"));
                    balanceBean.setCashier(rs.getString("Cashier"));
                    balanceBean.setR_Emp(rs.getString("R_Emp"));
                    balanceBean.setR_PluCode(rs.getString("R_PluCode"));
                    balanceBean.setR_PName(ThaiUtil.ASCII2Unicode(rs.getString("R_PName")));
                    balanceBean.setR_Unit(rs.getString("R_Unit"));
                    balanceBean.setR_Group(rs.getString("R_Group"));
                    balanceBean.setR_Status(rs.getString("R_Status"));
                    balanceBean.setR_Normal(rs.getString("R_Normal"));
                    balanceBean.setR_Discount(rs.getString("R_Discount"));
                    balanceBean.setR_Service(rs.getString("R_Service"));
                    balanceBean.setR_Stock(rs.getString("R_Stock"));
                    balanceBean.setR_Set(rs.getString("R_Set"));
                    balanceBean.setR_Vat(rs.getString("R_Vat"));
                    balanceBean.setR_Type(rs.getString("R_Type"));
                    balanceBean.setR_ETD(rs.getString("R_ETD"));
                    balanceBean.setR_Quan(rs.getFloat("R_Quan"));
                    balanceBean.setR_Price(rs.getFloat("R_Price"));
                    balanceBean.setR_Total(rs.getFloat("R_Total"));
                    String R_PrType = rs.getString("R_PrType");
                    if (R_PrType == null) {
                        R_PrType = "";
                    }
                    balanceBean.setR_PrType(R_PrType);

                    balanceBean.setR_PrCode(rs.getString("R_PrCode"));
                    balanceBean.setR_PrDisc(rs.getFloat("R_PrDisc"));
                    balanceBean.setR_PrBath(rs.getFloat("R_PrBath"));
                    balanceBean.setR_PrAmt(rs.getFloat("R_PrAmt"));
                    balanceBean.setR_DiscBath(rs.getFloat("R_DiscBath"));
                    balanceBean.setR_PrCuType(rs.getString("R_PrCuType"));
                    balanceBean.setR_PrCuQuan(rs.getFloat("R_PrCuQuan"));
                    balanceBean.setR_PrCuAmt(rs.getFloat("R_PrCuAmt"));
                    balanceBean.setR_Redule(rs.getFloat("R_Redule"));
                    balanceBean.setR_Kic(rs.getString("R_Kic"));
                    balanceBean.setR_KicPrint(rs.getString("R_KicPrint"));
                    balanceBean.setR_Void(rs.getString("R_Void"));
                    balanceBean.setR_VoidUser(rs.getString("R_VoidUser"));
                    balanceBean.setR_VoidTime(rs.getString("R_VoidTime"));
                    balanceBean.setR_Opt1(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt1")));
                    balanceBean.setR_Opt2(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt2")));
                    balanceBean.setR_Opt3(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt3")));
                    balanceBean.setR_Opt4(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt4")));
                    balanceBean.setR_Opt5(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt5")));
                    balanceBean.setR_Opt6(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt6")));
                    balanceBean.setR_Opt7(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt7")));
                    balanceBean.setR_Opt8(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt8")));
                    balanceBean.setR_Opt9(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt9")));
                    balanceBean.setVoidMSG(ThaiUtil.ASCII2Unicode(rs.getString("VoidMSG")));
                    balanceBean.setR_PrCuCode(rs.getString("R_PrCuCode"));
                    balanceBean.setR_Serve(rs.getString("R_Serve"));
                    balanceBean.setR_PrintOK(rs.getString("R_PrintOK"));
                    balanceBean.setR_KicOK(rs.getString("R_KicOK"));
                    balanceBean.setStkCode(rs.getString("StkCode"));
                    balanceBean.setPosStk(rs.getString("PosStk"));
                    balanceBean.setR_PrChkType(rs.getString("R_PrChkType"));
                    balanceBean.setR_PrQuan(rs.getFloat("R_PrQuan"));
                    balanceBean.setR_PrSubType(rs.getString("R_PrSubType"));
                    balanceBean.setR_PrSubCode(rs.getString("R_PrSubCode"));
                    balanceBean.setR_PrSubQuan(rs.getFloat("R_PrSubQuan"));
                    balanceBean.setR_PrSubDisc(rs.getFloat("R_PrSubDisc"));
                    balanceBean.setR_PrSubBath(rs.getFloat("R_PrSubBath"));
                    balanceBean.setR_PrSubAmt(rs.getFloat("R_PrSubAmt"));
                    balanceBean.setR_PrSubAdj(rs.getFloat("R_PrSubAdj"));
                    balanceBean.setR_PrCuDisc(rs.getFloat("R_PrCuDisc"));
                    balanceBean.setR_PrCuBath(rs.getFloat("R_PrCuBath"));
                    balanceBean.setR_PrCuAdj(rs.getFloat("R_PrCuAdj"));
                    balanceBean.setR_QuanCanDisc(rs.getFloat("R_QuanCanDisc"));
                    balanceBean.setR_Order(rs.getString("R_Order"));
                    balanceBean.setR_PItemNo(rs.getInt("R_PItemNo"));
                    balanceBean.setR_PKicQue(rs.getInt("R_PKicQue"));
                    balanceBean.setR_MemSum(rs.getString("R_MemSum"));
                    balanceBean.setR_MoveItem(rs.getString("R_MoveItem"));
                    balanceBean.setR_MoveFrom(rs.getString("R_MoveFrom"));
                    balanceBean.setR_MoveUser(rs.getString("R_MoveUser"));
                    balanceBean.setR_MoveFlag(rs.getString("R_MoveFlag"));
                    balanceBean.setR_MovePrint(rs.getString("R_MovePrint"));
                    balanceBean.setR_Pause(rs.getString("R_Pause"));
                    String r_linkIndex = rs.getString("R_LinkIndex");
                    if (r_linkIndex == null || r_linkIndex.equals("null")) {
                        r_linkIndex = "";
                    }
                    balanceBean.setR_LinkIndex(r_linkIndex);
                    try {
                        balanceBean.setR_Date(rs.getDate("R_Date"));
                    } catch (SQLException e) {
                        MSG.ERR("BalanceControl:" + e.getMessage());
                    }
                    beanData.add(balanceBean);
                }
                rs.close();
            }
            
        } catch (SQLException e) {
            MSG.ERR("BalanceControl:" + e.getMessage());
        } finally {
            mysql.close();
        }
        return beanData;
    }

    public List<BalanceBean> getAllBalanceSum(String table) {
        List<BalanceBean> beanData = new ArrayList<>();
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            String sql = "select "
                    + "sum(b.r_quan) sum_R_Quan, "
                    + "sum(b.r_total) sum_R_Total, "
                    + "sum(r_discbath) sum_R_DiscBath,"
                    + "sum(r_pramt) sum_R_PrAmt,"
                    + "sum(r_prsubAmt) sum_R_PrsubAmt,"
                    + "sum(r_prcuamt) sum_R_PrCuAmt, "
                    + "sum(R_ServiceAmt) sum_R_ServiceAmt, "
                    + "b.* "
                    + "from balance b "
                    + "where r_table='" + table + "' "
                    + "and r_plucode<>'8899' "
//                    + "and r_void<>'V' "
                    + "group by r_plucode,r_etd,r_Pname "
                    + "order by r_time, r_index";
//            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql);
            while (rs.next()) {
                BalanceBean balanceBean = new BalanceBean();
                balanceBean.setR_Index(rs.getString("R_Index"));
                balanceBean.setR_Table(rs.getString("R_Table"));
                balanceBean.setR_Time(rs.getString("R_Time"));
                balanceBean.setMacno(rs.getString("Macno"));
                balanceBean.setCashier(rs.getString("Cashier"));
                balanceBean.setR_Emp(rs.getString("R_Emp"));
                balanceBean.setR_PluCode(rs.getString("R_PluCode"));
                balanceBean.setR_PName(ThaiUtil.ASCII2Unicode(rs.getString("R_PName")));
                balanceBean.setR_Unit(rs.getString("R_Unit"));
                balanceBean.setR_Group(rs.getString("R_Group"));
                balanceBean.setR_Status(rs.getString("R_Status"));
                balanceBean.setR_Normal(rs.getString("R_Normal"));
                balanceBean.setR_Discount(rs.getString("R_Discount"));
                balanceBean.setR_Service(rs.getString("R_Service"));
                balanceBean.setR_Stock(rs.getString("R_Stock"));
                balanceBean.setR_Set(rs.getString("R_Set"));
                balanceBean.setR_Vat(rs.getString("R_Vat"));
                balanceBean.setR_Type(rs.getString("R_Type"));
                balanceBean.setR_ETD(rs.getString("R_ETD"));
//                balanceBean.setR_Quan(rs.getFloat("R_Quan"));
                balanceBean.setR_Quan(rs.getFloat("sum_R_Quan"));
                balanceBean.setR_Price(rs.getFloat("R_Price"));
                balanceBean.setR_Total(rs.getFloat("sum_R_Total"));
                String R_PrType = rs.getString("R_PrType");
                if (R_PrType == null) {
                    R_PrType = "";
                }
                balanceBean.setR_PrType(R_PrType);

                balanceBean.setR_PrCode(rs.getString("R_PrCode"));
                balanceBean.setR_PrDisc(rs.getFloat("R_PrDisc"));
                balanceBean.setR_PrBath(rs.getFloat("R_PrBath"));
                balanceBean.setR_PrAmt(rs.getFloat("sum_R_PrAmt"));
                balanceBean.setR_DiscBath(rs.getFloat("R_DiscBath"));
                balanceBean.setR_PrCuType(rs.getString("R_PrCuType"));
                balanceBean.setR_PrCuQuan(rs.getFloat("R_PrCuQuan"));
                balanceBean.setR_PrCuAmt(rs.getFloat("sum_R_PrCuAmt"));
                balanceBean.setR_Redule(rs.getFloat("R_Redule"));
                balanceBean.setR_Kic(rs.getString("R_Kic"));
                balanceBean.setR_KicPrint(rs.getString("R_KicPrint"));
                balanceBean.setR_Void(rs.getString("R_Void"));
                balanceBean.setR_VoidUser(rs.getString("R_VoidUser"));
                balanceBean.setR_VoidTime(rs.getString("R_VoidTime"));
                balanceBean.setR_Opt1(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt1")));
                balanceBean.setR_Opt2(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt2")));
                balanceBean.setR_Opt3(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt3")));
                balanceBean.setR_Opt4(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt4")));
                balanceBean.setR_Opt5(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt5")));
                balanceBean.setR_Opt6(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt6")));
                balanceBean.setR_Opt7(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt7")));
                balanceBean.setR_Opt8(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt8")));
                balanceBean.setR_Opt9(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt9")));
                balanceBean.setVoidMSG(ThaiUtil.ASCII2Unicode(rs.getString("VoidMSG")));
                balanceBean.setR_PrCuCode(rs.getString("R_PrCuCode"));
                balanceBean.setR_Serve(rs.getString("R_Serve"));
                balanceBean.setR_PrintOK(rs.getString("R_PrintOK"));
                balanceBean.setR_KicOK(rs.getString("R_KicOK"));
                balanceBean.setStkCode(rs.getString("StkCode"));
                balanceBean.setPosStk(rs.getString("PosStk"));
                balanceBean.setR_PrChkType(rs.getString("R_PrChkType"));
                balanceBean.setR_PrQuan(rs.getFloat("R_PrQuan"));
                balanceBean.setR_PrSubType(rs.getString("R_PrSubType"));
                balanceBean.setR_PrSubCode(rs.getString("R_PrSubCode"));
                balanceBean.setR_PrSubQuan(rs.getFloat("R_PrSubQuan"));
                balanceBean.setR_PrSubDisc(rs.getFloat("R_PrSubDisc"));
                balanceBean.setR_PrSubBath(rs.getFloat("R_PrSubBath"));
                balanceBean.setR_PrSubAmt(rs.getFloat("sum_R_PrsubAmt"));
                balanceBean.setR_PrSubAdj(rs.getFloat("R_PrSubAdj"));
                balanceBean.setR_PrCuDisc(rs.getFloat("R_PrCuDisc"));
                balanceBean.setR_PrCuBath(rs.getFloat("R_PrCuBath"));
                balanceBean.setR_PrCuAdj(rs.getFloat("R_PrCuAdj"));
                balanceBean.setR_QuanCanDisc(rs.getFloat("R_QuanCanDisc"));
                balanceBean.setR_Order(rs.getString("R_Order"));
                balanceBean.setR_PItemNo(rs.getInt("R_PItemNo"));
                balanceBean.setR_PKicQue(rs.getInt("R_PKicQue"));
                balanceBean.setR_MemSum(rs.getString("R_MemSum"));
                balanceBean.setR_MoveItem(rs.getString("R_MoveItem"));
                balanceBean.setR_MoveFrom(rs.getString("R_MoveFrom"));
                balanceBean.setR_MoveUser(rs.getString("R_MoveUser"));
                balanceBean.setR_MoveFlag(rs.getString("R_MoveFlag"));
                balanceBean.setR_MovePrint(rs.getString("R_MovePrint"));
                balanceBean.setR_Pause(rs.getString("R_Pause"));
                balanceBean.setR_LinkIndex(rs.getString("R_LinkIndex"));
                balanceBean.setR_DiscBath(rs.getFloat("sum_R_DiscBath"));
                balanceBean.setR_ServiceAmt(rs.getFloat("sum_R_ServiceAmt"));

                try {
                    balanceBean.setR_Date(rs.getDate("R_Date"));
                } catch (SQLException e) {
                    MSG.ERR(e.getMessage());
                }

                beanData.add(balanceBean);
            }
            String sql1 = "select * from balance "
                    + "where r_table='" + table + "' "
                    + "and r_void<>'V' "
                    + "and r_plucode='8899' "
                    + "order by r_index";
//            Statement stmt1 = mysql.getConnection().createStatement();
            ResultSet rs1 = mysql.getConnection().createStatement().executeQuery(sql1);
            while (rs1.next()) {
                BalanceBean balanceBean = new BalanceBean();
                balanceBean.setR_Index(rs1.getString("R_Index"));
                balanceBean.setR_Table(rs1.getString("R_Table"));
                balanceBean.setR_Time(rs1.getString("R_Time"));
                balanceBean.setMacno(rs1.getString("Macno"));
                balanceBean.setCashier(rs1.getString("Cashier"));
                balanceBean.setR_Emp(rs1.getString("R_Emp"));
                balanceBean.setR_PluCode(rs1.getString("R_PluCode"));
                balanceBean.setR_PName(ThaiUtil.ASCII2Unicode(rs1.getString("R_Opt1")) + " :OPEN-FOOD");
//                balanceBean.setR_PName(ThaiUtil.ASCII2Unicode(rs1.getString("R_PName")));
                balanceBean.setR_Unit(rs1.getString("R_Unit"));
                balanceBean.setR_Group(rs1.getString("R_Group"));
                balanceBean.setR_Status(rs1.getString("R_Status"));
                balanceBean.setR_Normal(rs1.getString("R_Normal"));
                balanceBean.setR_Discount(rs1.getString("R_Discount"));
                balanceBean.setR_Service(rs1.getString("R_Service"));
                balanceBean.setR_Stock(rs1.getString("R_Stock"));
                balanceBean.setR_Set(rs1.getString("R_Set"));
                balanceBean.setR_Vat(rs1.getString("R_Vat"));
                balanceBean.setR_Type(rs1.getString("R_Type"));
                balanceBean.setR_ETD(rs1.getString("R_ETD"));
                balanceBean.setR_Quan(rs1.getFloat("R_Quan"));
                balanceBean.setR_Price(rs1.getFloat("R_Price"));
                balanceBean.setR_Total(rs1.getFloat("R_Total"));
                String R_PrType = rs1.getString("R_PrType");
                if (R_PrType == null) {
                    R_PrType = "";
                }
                balanceBean.setR_PrType(R_PrType);

                balanceBean.setR_PrCode(rs1.getString("R_PrCode"));
                balanceBean.setR_PrDisc(rs1.getFloat("R_PrDisc"));
                balanceBean.setR_PrBath(rs1.getFloat("R_PrBath"));
                balanceBean.setR_PrAmt(rs1.getFloat("R_PrAmt"));
                balanceBean.setR_DiscBath(rs1.getFloat("R_DiscBath"));
                balanceBean.setR_PrCuType(rs1.getString("R_PrCuType"));
                balanceBean.setR_PrCuQuan(rs1.getFloat("R_PrCuQuan"));
                balanceBean.setR_PrCuAmt(rs1.getFloat("R_PrCuAmt"));
                balanceBean.setR_Redule(rs1.getFloat("R_Redule"));
                balanceBean.setR_Kic(rs1.getString("R_Kic"));
                balanceBean.setR_KicPrint(rs1.getString("R_KicPrint"));
                balanceBean.setR_Void(rs1.getString("R_Void"));
                balanceBean.setR_VoidUser(rs1.getString("R_VoidUser"));
                balanceBean.setR_VoidTime(rs1.getString("R_VoidTime"));
                balanceBean.setR_Opt1(ThaiUtil.ASCII2Unicode(rs1.getString("R_Opt1")));
                balanceBean.setR_Opt2(ThaiUtil.ASCII2Unicode(rs1.getString("R_Opt2")));
                balanceBean.setR_Opt3(ThaiUtil.ASCII2Unicode(rs1.getString("R_Opt3")));
                balanceBean.setR_Opt4(ThaiUtil.ASCII2Unicode(rs1.getString("R_Opt4")));
                balanceBean.setR_Opt5(ThaiUtil.ASCII2Unicode(rs1.getString("R_Opt5")));
                balanceBean.setR_Opt6(ThaiUtil.ASCII2Unicode(rs1.getString("R_Opt6")));
                balanceBean.setR_Opt7(ThaiUtil.ASCII2Unicode(rs1.getString("R_Opt7")));
                balanceBean.setR_Opt8(ThaiUtil.ASCII2Unicode(rs1.getString("R_Opt8")));
                balanceBean.setR_Opt9(ThaiUtil.ASCII2Unicode(rs1.getString("R_Opt9")));
                balanceBean.setVoidMSG(ThaiUtil.ASCII2Unicode(rs1.getString("VoidMSG")));
                balanceBean.setR_PrCuCode(rs1.getString("R_PrCuCode"));
                balanceBean.setR_Serve(rs1.getString("R_Serve"));
                balanceBean.setR_PrintOK(rs1.getString("R_PrintOK"));
                balanceBean.setR_KicOK(rs1.getString("R_KicOK"));
                balanceBean.setStkCode(rs1.getString("StkCode"));
                balanceBean.setPosStk(rs1.getString("PosStk"));
                balanceBean.setR_PrChkType(rs1.getString("R_PrChkType"));
                balanceBean.setR_PrQuan(rs1.getFloat("R_PrQuan"));
                balanceBean.setR_PrSubType(rs1.getString("R_PrSubType"));
                balanceBean.setR_PrSubCode(rs1.getString("R_PrSubCode"));
                balanceBean.setR_PrSubQuan(rs1.getFloat("R_PrSubQuan"));
                balanceBean.setR_PrSubDisc(rs1.getFloat("R_PrSubDisc"));
                balanceBean.setR_PrSubBath(rs1.getFloat("R_PrSubBath"));
                balanceBean.setR_PrSubAmt(rs1.getFloat("R_PrSubAmt"));
                balanceBean.setR_PrSubAdj(rs1.getFloat("R_PrSubAdj"));
                balanceBean.setR_PrCuDisc(rs1.getFloat("R_PrCuDisc"));
                balanceBean.setR_PrCuBath(rs1.getFloat("R_PrCuBath"));
                balanceBean.setR_PrCuAdj(rs1.getFloat("R_PrCuAdj"));
                balanceBean.setR_QuanCanDisc(rs1.getFloat("R_QuanCanDisc"));
                balanceBean.setR_Order(rs1.getString("R_Order"));
                balanceBean.setR_PItemNo(rs1.getInt("R_PItemNo"));
                balanceBean.setR_PKicQue(rs1.getInt("R_PKicQue"));
                balanceBean.setR_MemSum(rs1.getString("R_MemSum"));
                balanceBean.setR_MoveItem(rs1.getString("R_MoveItem"));
                balanceBean.setR_MoveFrom(rs1.getString("R_MoveFrom"));
                balanceBean.setR_MoveUser(rs1.getString("R_MoveUser"));
                balanceBean.setR_MoveFlag(rs1.getString("R_MoveFlag"));
                balanceBean.setR_MovePrint(rs1.getString("R_MovePrint"));
                balanceBean.setR_Pause(rs1.getString("R_Pause"));
                balanceBean.setR_LinkIndex(rs1.getString("R_LinkIndex"));
                balanceBean.setR_ServiceAmt(rs1.getDouble("R_ServiceAmt"));

                try {
                    balanceBean.setR_Date(rs1.getDate("R_Date"));
                } catch (SQLException e) {
                    MSG.ERR(e.getMessage());
                }

                beanData.add(balanceBean);
            }

            rs1.close();
//            stmt1.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());

        } finally {
            mysql.close();
        }

        return beanData;
    }

    public List<BalanceBean> getAllBalanceNoVoid(String table) {
        List<BalanceBean> beanData = new ArrayList<>();
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            String sql = "select * from balance "
                    + "where R_Table='" + table + "' and r_void<>'V'"
                    + "order by R_ETD,R_Index,r_time";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                while (rs.next()) {
                    BalanceBean balanceBean = new BalanceBean();
                    balanceBean.setR_Index(rs.getString("R_Index"));
                    balanceBean.setR_Table(rs.getString("R_Table"));
                    balanceBean.setR_Time(rs.getString("R_Time"));
                    balanceBean.setMacno(rs.getString("Macno"));
                    balanceBean.setCashier(rs.getString("Cashier"));
                    balanceBean.setR_Emp(rs.getString("R_Emp"));
                    balanceBean.setR_PluCode(rs.getString("R_PluCode"));
                    balanceBean.setR_PName(ThaiUtil.ASCII2Unicode(rs.getString("R_PName")));
                    balanceBean.setR_Unit(rs.getString("R_Unit"));
                    balanceBean.setR_Group(rs.getString("R_Group"));
                    balanceBean.setR_Status(rs.getString("R_Status"));
                    balanceBean.setR_Normal(rs.getString("R_Normal"));
                    balanceBean.setR_Discount(rs.getString("R_Discount"));
                    balanceBean.setR_Service(rs.getString("R_Service"));
                    balanceBean.setR_Stock(rs.getString("R_Stock"));
                    balanceBean.setR_Set(rs.getString("R_Set"));
                    balanceBean.setR_Vat(rs.getString("R_Vat"));
                    balanceBean.setR_Type(rs.getString("R_Type"));
                    balanceBean.setR_ETD(rs.getString("R_ETD"));
                    balanceBean.setR_Quan(rs.getFloat("R_Quan"));
                    balanceBean.setR_Price(rs.getFloat("R_Price"));
                    balanceBean.setR_Total(rs.getFloat("R_Total"));
                    String R_PrType = rs.getString("R_PrType");
                    if (R_PrType == null) {
                        R_PrType = "";
                    }
                    balanceBean.setR_PrType(R_PrType);

                    balanceBean.setR_PrCode(rs.getString("R_PrCode"));
                    balanceBean.setR_PrDisc(rs.getFloat("R_PrDisc"));
                    balanceBean.setR_PrBath(rs.getFloat("R_PrBath"));
                    balanceBean.setR_PrAmt(rs.getFloat("R_PrAmt"));
                    balanceBean.setR_DiscBath(rs.getFloat("R_DiscBath"));
                    balanceBean.setR_PrCuType(rs.getString("R_PrCuType"));
                    balanceBean.setR_PrCuQuan(rs.getFloat("R_PrCuQuan"));
                    balanceBean.setR_PrCuAmt(rs.getFloat("R_PrCuAmt"));
                    balanceBean.setR_Redule(rs.getFloat("R_Redule"));
                    balanceBean.setR_Kic(rs.getString("R_Kic"));
                    balanceBean.setR_KicPrint(rs.getString("R_KicPrint"));
                    balanceBean.setR_Void(rs.getString("R_Void"));
                    balanceBean.setR_VoidUser(rs.getString("R_VoidUser"));
                    balanceBean.setR_VoidTime(rs.getString("R_VoidTime"));
                    balanceBean.setR_Opt1(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt1")));
                    balanceBean.setR_Opt2(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt2")));
                    balanceBean.setR_Opt3(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt3")));
                    balanceBean.setR_Opt4(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt4")));
                    balanceBean.setR_Opt5(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt5")));
                    balanceBean.setR_Opt6(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt6")));
                    balanceBean.setR_Opt7(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt7")));
                    balanceBean.setR_Opt8(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt8")));
                    balanceBean.setR_Opt9(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt9")));
                    balanceBean.setVoidMSG(ThaiUtil.ASCII2Unicode(rs.getString("VoidMSG")));
                    balanceBean.setR_PrCuCode(rs.getString("R_PrCuCode"));
                    balanceBean.setR_Serve(rs.getString("R_Serve"));
                    balanceBean.setR_PrintOK(rs.getString("R_PrintOK"));
                    balanceBean.setR_KicOK(rs.getString("R_KicOK"));
                    balanceBean.setStkCode(rs.getString("StkCode"));
                    balanceBean.setPosStk(rs.getString("PosStk"));
                    balanceBean.setR_PrChkType(rs.getString("R_PrChkType"));
                    balanceBean.setR_PrQuan(rs.getFloat("R_PrQuan"));
                    balanceBean.setR_PrSubType(rs.getString("R_PrSubType"));
                    balanceBean.setR_PrSubCode(rs.getString("R_PrSubCode"));
                    balanceBean.setR_PrSubQuan(rs.getFloat("R_PrSubQuan"));
                    balanceBean.setR_PrSubDisc(rs.getFloat("R_PrSubDisc"));
                    balanceBean.setR_PrSubBath(rs.getFloat("R_PrSubBath"));
                    balanceBean.setR_PrSubAmt(rs.getFloat("R_PrSubAmt"));
                    balanceBean.setR_PrSubAdj(rs.getFloat("R_PrSubAdj"));
                    balanceBean.setR_PrCuDisc(rs.getFloat("R_PrCuDisc"));
                    balanceBean.setR_PrCuBath(rs.getFloat("R_PrCuBath"));
                    balanceBean.setR_PrCuAdj(rs.getFloat("R_PrCuAdj"));
                    balanceBean.setR_QuanCanDisc(rs.getFloat("R_QuanCanDisc"));
                    balanceBean.setR_Order(rs.getString("R_Order"));
                    balanceBean.setR_PItemNo(rs.getInt("R_PItemNo"));
                    balanceBean.setR_PKicQue(rs.getInt("R_PKicQue"));
                    balanceBean.setR_MemSum(rs.getString("R_MemSum"));
                    balanceBean.setR_MoveItem(rs.getString("R_MoveItem"));
                    balanceBean.setR_MoveFrom(rs.getString("R_MoveFrom"));
                    balanceBean.setR_MoveUser(rs.getString("R_MoveUser"));
                    balanceBean.setR_MoveFlag(rs.getString("R_MoveFlag"));
                    balanceBean.setR_MovePrint(rs.getString("R_MovePrint"));
                    balanceBean.setR_Pause(rs.getString("R_Pause"));
                    balanceBean.setR_LinkIndex(rs.getString("R_LinkIndex"));
                    if (!balanceBean.getR_Opt1().equals("")) {
                        balanceBean.setR_Opt1("    *** " + rs.getString("R_Opt1"));
                    }
                    try {
                        balanceBean.setR_Date(rs.getDate("R_Date"));
                    } catch (SQLException e) {
                        MSG.ERR(e.getMessage());
                    }

                    beanData.add(balanceBean);
                }
//            stmt.close();
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());

        } finally {
            mysql.close();
        }

        return beanData;
    }

    public List<BalanceBean> getAllBalanceNoVoidSum(String table) {
        List<BalanceBean> beanData = new ArrayList<>();
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            String sql = "";
            for (int i = 0; i <= 2; i++) {
                if (i == 0) {
                    sql = "select "
                            + "sum(b.r_quan) sumr_quan,"
                            + "sum(b.r_total) sumr_total,r_etd,"
                            + "b.* "
                            + "from balance b "
                            + "where r_table='" + table + "' "
                            + "and r_void<>'V' "
                            + "and r_plucode<>'8899' "
                            + "and r_normal='N' "
                            + "group by r_plucode,r_pname,r_etd "
                            + "order by r_etd,r_normal,r_type,r_index,"
                            + "r_time";
                }
                if (i == 1) {
                    sql = "select "
                            + "sum(b.r_quan) sumr_quan,"
                            + "sum(b.r_total) sumr_total,r_etd,"
                            + "b.* "
                            + "from balance b "
                            + "where r_table='" + table + "' "
                            + "and r_void<>'V' "
                            + "and r_plucode<>'8899' "
                            + "and r_normal='C' "
                            + "group by r_plucode,r_pname,r_etd "
                            + "order by r_etd,r_normal,r_type,r_index,"
                            + "r_time";
                }
                if (i == 2) {
                    sql = "select "
                            + "sum(b.r_quan) sumr_quan,"
                            + "sum(b.r_total) sumr_total,r_etd,"
                            + "b.* "
                            + "from balance b "
                            + "where r_table='" + table + "' "
                            + "and r_void<>'V' "
                            + "and r_plucode<>'8899' "
                            + "and r_normal='S' "
                            + "group by r_plucode,r_pname,r_etd "
                            + "order by r_etd,r_normal,r_type,r_index,"
                            + "r_time";
                }
                ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql);
                while (rs.next()) {
                    BalanceBean balanceBean = new BalanceBean();
                    balanceBean.setR_Index(rs.getString("R_Index"));
                    balanceBean.setR_Table(rs.getString("R_Table"));
                    balanceBean.setR_Time(rs.getString("R_Time"));
                    balanceBean.setMacno(rs.getString("Macno"));
                    balanceBean.setCashier(rs.getString("Cashier"));
                    balanceBean.setR_Emp(rs.getString("R_Emp"));
                    balanceBean.setR_PluCode(rs.getString("R_PluCode"));
                    if (PublicVar.languagePrint.equals("EN")) {
                        balanceBean.setR_PName(ThaiUtil.ASCII2Unicode(rs.getString("R_PEName")));
                    } else {
                        balanceBean.setR_PName(ThaiUtil.ASCII2Unicode(rs.getString("R_PName")));
                    }
                    balanceBean.setR_Unit(rs.getString("R_Unit"));
                    balanceBean.setR_Group(rs.getString("R_Group"));
                    balanceBean.setR_Status(rs.getString("R_Status"));
                    balanceBean.setR_Normal(rs.getString("R_Normal"));
                    balanceBean.setR_Discount(rs.getString("R_Discount"));
                    balanceBean.setR_Service(rs.getString("R_Service"));
                    balanceBean.setR_Stock(rs.getString("R_Stock"));
                    balanceBean.setR_Set(rs.getString("R_Set"));
                    balanceBean.setR_Vat(rs.getString("R_Vat"));
                    balanceBean.setR_Type(rs.getString("R_Type"));
                    balanceBean.setR_ETD(rs.getString("R_ETD"));
                    balanceBean.setR_Quan(rs.getFloat("sumr_quan"));
//                balanceBean.setR_Quan(rs.getFloat("R_Quan"));
                    balanceBean.setR_Price(rs.getFloat("R_Price"));
                    balanceBean.setR_Total(rs.getFloat("sumr_total"));
                    String R_PrType = rs.getString("R_PrType");
                    if (R_PrType == null) {
                        R_PrType = "";
                    }
                    balanceBean.setR_PrType(R_PrType);
                    balanceBean.setR_PrCode(rs.getString("R_PrCode"));
                    balanceBean.setR_PrDisc(rs.getFloat("R_PrDisc"));
                    balanceBean.setR_PrBath(rs.getFloat("R_PrBath"));
                    balanceBean.setR_PrAmt(rs.getFloat("R_PrAmt"));
                    balanceBean.setR_DiscBath(rs.getFloat("R_DiscBath"));
                    balanceBean.setR_PrCuType(rs.getString("R_PrCuType"));
                    balanceBean.setR_PrCuQuan(rs.getFloat("R_PrCuQuan"));
                    balanceBean.setR_PrCuAmt(rs.getFloat("R_PrCuAmt"));
                    balanceBean.setR_Redule(rs.getFloat("R_Redule"));
                    balanceBean.setR_Kic(rs.getString("R_Kic"));
                    balanceBean.setR_KicPrint(rs.getString("R_KicPrint"));
                    balanceBean.setR_Void(rs.getString("R_Void"));
                    balanceBean.setR_VoidUser(rs.getString("R_VoidUser"));
                    balanceBean.setR_VoidTime(rs.getString("R_VoidTime"));
                    balanceBean.setR_Opt1(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt1")));
                    balanceBean.setR_Opt2(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt2")));
                    balanceBean.setR_Opt3(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt3")));
                    balanceBean.setR_Opt4(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt4")));
                    balanceBean.setR_Opt5(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt5")));
                    balanceBean.setR_Opt6(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt6")));
                    balanceBean.setR_Opt7(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt7")));
                    balanceBean.setR_Opt8(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt8")));
                    balanceBean.setR_Opt9(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt9")));
                    balanceBean.setVoidMSG(ThaiUtil.ASCII2Unicode(rs.getString("VoidMSG")));
                    balanceBean.setR_PrCuCode(rs.getString("R_PrCuCode"));
                    balanceBean.setR_Serve(rs.getString("R_Serve"));
                    balanceBean.setR_PrintOK(rs.getString("R_PrintOK"));
                    balanceBean.setR_KicOK(rs.getString("R_KicOK"));
                    balanceBean.setStkCode(rs.getString("StkCode"));
                    balanceBean.setPosStk(rs.getString("PosStk"));
                    balanceBean.setR_PrChkType(rs.getString("R_PrChkType"));
                    balanceBean.setR_PrQuan(rs.getFloat("R_PrQuan"));
                    balanceBean.setR_PrSubType(rs.getString("R_PrSubType"));
                    balanceBean.setR_PrSubCode(rs.getString("R_PrSubCode"));
                    balanceBean.setR_PrSubQuan(rs.getFloat("R_PrSubQuan"));
                    balanceBean.setR_PrSubDisc(rs.getFloat("R_PrSubDisc"));
                    balanceBean.setR_PrSubBath(rs.getFloat("R_PrSubBath"));
                    balanceBean.setR_PrSubAmt(rs.getFloat("R_PrSubAmt"));
                    balanceBean.setR_PrSubAdj(rs.getFloat("R_PrSubAdj"));
                    balanceBean.setR_PrCuDisc(rs.getFloat("R_PrCuDisc"));
                    balanceBean.setR_PrCuBath(rs.getFloat("R_PrCuBath"));
                    balanceBean.setR_PrCuAdj(rs.getFloat("R_PrCuAdj"));
                    balanceBean.setR_QuanCanDisc(rs.getFloat("R_QuanCanDisc"));
                    balanceBean.setR_Order(rs.getString("R_Order"));
                    balanceBean.setR_PItemNo(rs.getInt("R_PItemNo"));
                    balanceBean.setR_PKicQue(rs.getInt("R_PKicQue"));
                    balanceBean.setR_MemSum(rs.getString("R_MemSum"));
                    balanceBean.setR_MoveItem(rs.getString("R_MoveItem"));
                    balanceBean.setR_MoveFrom(rs.getString("R_MoveFrom"));
                    balanceBean.setR_MoveUser(rs.getString("R_MoveUser"));
                    balanceBean.setR_MoveFlag(rs.getString("R_MoveFlag"));
                    balanceBean.setR_MovePrint(rs.getString("R_MovePrint"));
                    balanceBean.setR_Pause(rs.getString("R_Pause"));
                    balanceBean.setR_LinkIndex(rs.getString("R_LinkIndex"));
                    if (!balanceBean.getR_Opt1().equals("")) {
                        balanceBean.setR_Opt1("    *** " + rs.getString("R_Opt1"));
                    }
                    try {
                        balanceBean.setR_Date(rs.getDate("R_Date"));
                    } catch (SQLException e) {
                        MSG.ERR(e.getMessage());
                    }

                    beanData.add(balanceBean);
                }
                rs.close();
            }

            String sql1 = "select * from balance "
                    + "where r_table='" + table + "' "
                    + "and r_void<>'V' "
                    + "and r_plucode='8899' "
                    + "order by r_index,"
                    + "r_time";
            ResultSet rs1 = mysql.getConnection().createStatement().executeQuery(sql1);
            while (rs1.next()) {
                BalanceBean balanceBean = new BalanceBean();
                balanceBean.setR_Index(rs1.getString("R_Index"));
                balanceBean.setR_Table(rs1.getString("R_Table"));
                balanceBean.setR_Time(rs1.getString("R_Time"));
                balanceBean.setMacno(rs1.getString("Macno"));
                balanceBean.setCashier(rs1.getString("Cashier"));
                balanceBean.setR_Emp(rs1.getString("R_Emp"));
                balanceBean.setR_PluCode(rs1.getString("R_PluCode"));
                if (PublicVar.languagePrint.equals("EN")) {
                    balanceBean.setR_PName(ThaiUtil.ASCII2Unicode(rs1.getString("R_PEName")));
                } else {
                    balanceBean.setR_PName(ThaiUtil.ASCII2Unicode(rs1.getString("R_PName")));
                }
//                balanceBean.setR_PName(ThaiUtil.ASCII2Unicode(rs1.getString("R_PName")));
                balanceBean.setR_Unit(rs1.getString("R_Unit"));
                balanceBean.setR_Group(rs1.getString("R_Group"));
                balanceBean.setR_Status(rs1.getString("R_Status"));
                balanceBean.setR_Normal(rs1.getString("R_Normal"));
                balanceBean.setR_Discount(rs1.getString("R_Discount"));
                balanceBean.setR_Service(rs1.getString("R_Service"));
                balanceBean.setR_Stock(rs1.getString("R_Stock"));
                balanceBean.setR_Set(rs1.getString("R_Set"));
                balanceBean.setR_Vat(rs1.getString("R_Vat"));
                balanceBean.setR_Type(rs1.getString("R_Type"));
                balanceBean.setR_ETD(rs1.getString("R_ETD"));
                balanceBean.setR_Quan(rs1.getFloat("R_Quan"));
                balanceBean.setR_Price(rs1.getFloat("R_Price"));
                balanceBean.setR_Total(rs1.getFloat("r_total"));
                String R_PrType = rs1.getString("R_PrType");
                if (R_PrType == null) {
                    R_PrType = "";
                }
                balanceBean.setR_PrType(R_PrType);

                balanceBean.setR_PrCode(rs1.getString("R_PrCode"));
                balanceBean.setR_PrDisc(rs1.getFloat("R_PrDisc"));
                balanceBean.setR_PrBath(rs1.getFloat("R_PrBath"));
                balanceBean.setR_PrAmt(rs1.getFloat("R_PrAmt"));
                balanceBean.setR_DiscBath(rs1.getFloat("R_DiscBath"));
                balanceBean.setR_PrCuType(rs1.getString("R_PrCuType"));
                balanceBean.setR_PrCuQuan(rs1.getFloat("R_PrCuQuan"));
                balanceBean.setR_PrCuAmt(rs1.getFloat("R_PrCuAmt"));
                balanceBean.setR_Redule(rs1.getFloat("R_Redule"));
                balanceBean.setR_Kic(rs1.getString("R_Kic"));
                balanceBean.setR_KicPrint(rs1.getString("R_KicPrint"));
                balanceBean.setR_Void(rs1.getString("R_Void"));
                balanceBean.setR_VoidUser(rs1.getString("R_VoidUser"));
                balanceBean.setR_VoidTime(rs1.getString("R_VoidTime"));
                balanceBean.setR_Opt1(ThaiUtil.ASCII2Unicode(rs1.getString("R_Opt1")));
                balanceBean.setR_Opt2(ThaiUtil.ASCII2Unicode(rs1.getString("R_Opt2")));
                balanceBean.setR_Opt3(ThaiUtil.ASCII2Unicode(rs1.getString("R_Opt3")));
                balanceBean.setR_Opt4(ThaiUtil.ASCII2Unicode(rs1.getString("R_Opt4")));
                balanceBean.setR_Opt5(ThaiUtil.ASCII2Unicode(rs1.getString("R_Opt5")));
                balanceBean.setR_Opt6(ThaiUtil.ASCII2Unicode(rs1.getString("R_Opt6")));
                balanceBean.setR_Opt7(ThaiUtil.ASCII2Unicode(rs1.getString("R_Opt7")));
                balanceBean.setR_Opt8(ThaiUtil.ASCII2Unicode(rs1.getString("R_Opt8")));
                balanceBean.setR_Opt9(ThaiUtil.ASCII2Unicode(rs1.getString("R_Opt9")));
                balanceBean.setVoidMSG(ThaiUtil.ASCII2Unicode(rs1.getString("VoidMSG")));
                balanceBean.setR_PrCuCode(rs1.getString("R_PrCuCode"));
                balanceBean.setR_Serve(rs1.getString("R_Serve"));
                balanceBean.setR_PrintOK(rs1.getString("R_PrintOK"));
                balanceBean.setR_KicOK(rs1.getString("R_KicOK"));
                balanceBean.setStkCode(rs1.getString("StkCode"));
                balanceBean.setPosStk(rs1.getString("PosStk"));
                balanceBean.setR_PrChkType(rs1.getString("R_PrChkType"));
                balanceBean.setR_PrQuan(rs1.getFloat("R_PrQuan"));
                balanceBean.setR_PrSubType(rs1.getString("R_PrSubType"));
                balanceBean.setR_PrSubCode(rs1.getString("R_PrSubCode"));
                balanceBean.setR_PrSubQuan(rs1.getFloat("R_PrSubQuan"));
                balanceBean.setR_PrSubDisc(rs1.getFloat("R_PrSubDisc"));
                balanceBean.setR_PrSubBath(rs1.getFloat("R_PrSubBath"));
                balanceBean.setR_PrSubAmt(rs1.getFloat("R_PrSubAmt"));
                balanceBean.setR_PrSubAdj(rs1.getFloat("R_PrSubAdj"));
                balanceBean.setR_PrCuDisc(rs1.getFloat("R_PrCuDisc"));
                balanceBean.setR_PrCuBath(rs1.getFloat("R_PrCuBath"));
                balanceBean.setR_PrCuAdj(rs1.getFloat("R_PrCuAdj"));
                balanceBean.setR_QuanCanDisc(rs1.getFloat("R_QuanCanDisc"));
                balanceBean.setR_Order(rs1.getString("R_Order"));
                balanceBean.setR_PItemNo(rs1.getInt("R_PItemNo"));
                balanceBean.setR_PKicQue(rs1.getInt("R_PKicQue"));
                balanceBean.setR_MemSum(rs1.getString("R_MemSum"));
                balanceBean.setR_MoveItem(rs1.getString("R_MoveItem"));
                balanceBean.setR_MoveFrom(rs1.getString("R_MoveFrom"));
                balanceBean.setR_MoveUser(rs1.getString("R_MoveUser"));
                balanceBean.setR_MoveFlag(rs1.getString("R_MoveFlag"));
                balanceBean.setR_MovePrint(rs1.getString("R_MovePrint"));
                balanceBean.setR_Pause(rs1.getString("R_Pause"));
                balanceBean.setR_LinkIndex(rs1.getString("R_LinkIndex"));
                if (!balanceBean.getR_Opt1().equals("")) {
                    balanceBean.setR_Opt1("    *** " + rs1.getString("R_Opt1"));
                }
                try {
                    balanceBean.setR_Date(rs1.getDate("R_Date"));
                } catch (SQLException e) {
                    MSG.ERR(e.getMessage());
                }
                beanData.add(balanceBean);
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());

        } finally {
            mysql.close();
        }

        return beanData;
    }

    public List<BalanceBean> getBalanceIndex(String R_Index) {
        List<BalanceBean> beanData = new ArrayList<>();
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "select * from balance where R_Index='" + R_Index + "'";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                while (rs.next()) {
                    BalanceBean balanceBean = new BalanceBean();
                    balanceBean.setR_Index(rs.getString("R_Index"));
                    balanceBean.setR_Table(rs.getString("R_Table"));
                    balanceBean.setR_Time(rs.getString("R_Time"));
                    balanceBean.setMacno(rs.getString("Macno"));
                    balanceBean.setCashier(rs.getString("Cashier"));
                    balanceBean.setR_Emp(rs.getString("R_Emp"));
                    balanceBean.setR_PluCode(rs.getString("R_PluCode"));
                    balanceBean.setR_PName(ThaiUtil.ASCII2Unicode(rs.getString("R_PName")));
                    balanceBean.setR_Unit(rs.getString("R_Unit"));
                    balanceBean.setR_Group(rs.getString("R_Group"));
                    balanceBean.setR_Status(rs.getString("R_Status"));
                    balanceBean.setR_Normal(rs.getString("R_Normal"));
                    balanceBean.setR_Discount(rs.getString("R_Discount"));
                    balanceBean.setR_Service(rs.getString("R_Service"));
                    balanceBean.setR_Stock(rs.getString("R_Stock"));
                    balanceBean.setR_Set(rs.getString("R_Set"));
                    balanceBean.setR_Vat(rs.getString("R_Vat"));
                    balanceBean.setR_Type(rs.getString("R_Type"));
                    balanceBean.setR_ETD(rs.getString("R_ETD"));
                    balanceBean.setR_Quan(rs.getFloat("R_Quan"));
                    balanceBean.setR_Price(rs.getFloat("R_Price"));
                    balanceBean.setR_Total(rs.getFloat("R_Total"));
                    String R_PrType = rs.getString("R_PrType");
                    if (R_PrType == null) {
                        R_PrType = "";
                    }
                    balanceBean.setR_PrType(R_PrType);
                    balanceBean.setR_PrCode(rs.getString("R_PrCode"));
                    balanceBean.setR_PrDisc(rs.getFloat("R_PrDisc"));
                    balanceBean.setR_PrBath(rs.getFloat("R_PrBath"));
                    balanceBean.setR_PrAmt(rs.getFloat("R_PrAmt"));
                    balanceBean.setR_DiscBath(rs.getFloat("R_DiscBath"));
                    balanceBean.setR_PrCuType(rs.getString("R_PrCuType"));
                    balanceBean.setR_PrCuQuan(rs.getFloat("R_PrCuQuan"));
                    balanceBean.setR_PrCuAmt(rs.getFloat("R_PrCuAmt"));
                    balanceBean.setR_Redule(rs.getFloat("R_Redule"));
                    balanceBean.setR_Kic(rs.getString("R_Kic"));
                    balanceBean.setR_KicPrint(rs.getString("R_KicPrint"));
                    balanceBean.setR_Void(rs.getString("R_Void"));
                    balanceBean.setR_VoidUser(rs.getString("R_VoidUser"));
                    balanceBean.setR_VoidTime(rs.getString("R_VoidTime"));
                    balanceBean.setR_Opt1(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt1")));
                    balanceBean.setR_Opt2(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt2")));
                    balanceBean.setR_Opt3(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt3")));
                    balanceBean.setR_Opt4(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt4")));
                    balanceBean.setR_Opt5(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt5")));
                    balanceBean.setR_Opt6(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt6")));
                    balanceBean.setR_Opt7(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt7")));
                    balanceBean.setR_Opt8(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt8")));
                    balanceBean.setR_Opt9(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt9")));
                    balanceBean.setVoidMSG(ThaiUtil.ASCII2Unicode(rs.getString("VoidMSG")));
                    balanceBean.setR_PrCuCode(rs.getString("R_PrCuCode"));
                    balanceBean.setR_Serve(rs.getString("R_Serve"));
                    balanceBean.setR_PrintOK(rs.getString("R_PrintOK"));
                    balanceBean.setR_KicOK(rs.getString("R_KicOK"));
                    balanceBean.setStkCode(rs.getString("StkCode"));
                    balanceBean.setPosStk(rs.getString("PosStk"));
                    balanceBean.setR_PrChkType(rs.getString("R_PrChkType"));
                    balanceBean.setR_PrQuan(rs.getFloat("R_PrQuan"));
                    balanceBean.setR_PrSubType(rs.getString("R_PrSubType"));
                    balanceBean.setR_PrSubCode(rs.getString("R_PrSubCode"));
                    balanceBean.setR_PrSubQuan(rs.getFloat("R_PrSubQuan"));
                    balanceBean.setR_PrSubDisc(rs.getFloat("R_PrSubDisc"));
                    balanceBean.setR_PrSubBath(rs.getFloat("R_PrSubBath"));
                    balanceBean.setR_PrSubAmt(rs.getFloat("R_PrSubAmt"));
                    balanceBean.setR_PrSubAdj(rs.getFloat("R_PrSubAdj"));
                    balanceBean.setR_PrCuDisc(rs.getFloat("R_PrCuDisc"));
                    balanceBean.setR_PrCuBath(rs.getFloat("R_PrCuBath"));
                    balanceBean.setR_PrCuAdj(rs.getFloat("R_PrCuAdj"));
                    balanceBean.setR_QuanCanDisc(rs.getFloat("R_QuanCanDisc"));
                    balanceBean.setR_Order(rs.getString("R_Order"));
                    balanceBean.setR_PItemNo(rs.getInt("R_PItemNo"));
                    balanceBean.setR_PKicQue(rs.getInt("R_PKicQue"));
                    balanceBean.setR_MemSum(rs.getString("R_MemSum"));
                    balanceBean.setR_MoveItem(rs.getString("R_MoveItem"));
                    balanceBean.setR_MoveFrom(rs.getString("R_MoveFrom"));
                    balanceBean.setR_MoveUser(rs.getString("R_MoveUser"));
                    balanceBean.setR_MoveFlag(rs.getString("R_MoveFlag"));
                    balanceBean.setR_MovePrint(rs.getString("R_MovePrint"));
                    balanceBean.setR_Pause(rs.getString("R_Pause"));
                    balanceBean.setR_LinkIndex(rs.getString("R_LinkIndex"));
                    balanceBean.setR_PEName(rs.getString("R_PEName"));

                    try {
                        balanceBean.setR_Date(rs.getDate("R_Date"));
                    } catch (SQLException e) {
                        MSG.ERR(e.getMessage());
                    }

                    beanData.add(balanceBean);
                }
//            stmt.close();
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());

        } finally {
            mysql.close();
        }

        return beanData;
    }

    public List<BalanceBean> getAllBalancePromotion(String table) {
        List<BalanceBean> beanData = new ArrayList<>();
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "select * from balance "
                    + "where R_Table='" + table + "' "
                    + "and R_Discount='Y' "
                    + "and R_Void <> 'V' "
                    + "group by R_PRType "
                    + "order by R_PluCode, R_Index";
            ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql);
            while (rs.next()) {
                BalanceBean balanceBean = new BalanceBean();
                balanceBean.setR_Index(rs.getString("R_Index"));
                balanceBean.setR_Table(rs.getString("R_Table"));
                balanceBean.setR_Time(rs.getString("R_Time"));
                balanceBean.setMacno(rs.getString("Macno"));
                balanceBean.setCashier(rs.getString("Cashier"));
                balanceBean.setR_Emp(rs.getString("R_Emp"));
                balanceBean.setR_PluCode(rs.getString("R_PluCode"));
                balanceBean.setR_PName(ThaiUtil.ASCII2Unicode(rs.getString("R_PName")));
                balanceBean.setR_Unit(rs.getString("R_Unit"));
                balanceBean.setR_Group(rs.getString("R_Group"));
                balanceBean.setR_Status(rs.getString("R_Status"));
                balanceBean.setR_Normal(rs.getString("R_Normal"));
                balanceBean.setR_Discount(rs.getString("R_Discount"));
                balanceBean.setR_Service(rs.getString("R_Service"));
                balanceBean.setR_Stock(rs.getString("R_Stock"));
                balanceBean.setR_Set(rs.getString("R_Set"));
                balanceBean.setR_Vat(rs.getString("R_Vat"));
                balanceBean.setR_Type(rs.getString("R_Type"));
                balanceBean.setR_ETD(rs.getString("R_ETD"));
                balanceBean.setR_Quan(rs.getFloat("R_Quan"));
                balanceBean.setR_Price(rs.getFloat("R_Price"));
                balanceBean.setR_Total(rs.getFloat("R_Total"));
                String R_PrType = rs.getString("R_PrType");
                if (R_PrType == null) {
                    R_PrType = "";
                }
                balanceBean.setR_PrType(R_PrType);

                balanceBean.setR_PrCode(rs.getString("R_PrCode"));
                balanceBean.setR_PrDisc(rs.getFloat("R_PrDisc"));
                balanceBean.setR_PrBath(rs.getFloat("R_PrBath"));
                balanceBean.setR_PrAmt(rs.getFloat("R_PrAmt"));
                balanceBean.setR_DiscBath(rs.getFloat("R_DiscBath"));
                balanceBean.setR_PrCuType(rs.getString("R_PrCuType"));
                balanceBean.setR_PrCuQuan(rs.getFloat("R_PrCuQuan"));
                balanceBean.setR_PrCuAmt(rs.getFloat("R_PrCuAmt"));
                balanceBean.setR_Redule(rs.getFloat("R_Redule"));
                balanceBean.setR_Kic(rs.getString("R_Kic"));
                balanceBean.setR_KicPrint(rs.getString("R_KicPrint"));
                balanceBean.setR_Void(rs.getString("R_Void"));
                balanceBean.setR_VoidUser(rs.getString("R_VoidUser"));
                balanceBean.setR_VoidTime(rs.getString("R_VoidTime"));
                balanceBean.setR_Opt1(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt1")));
                balanceBean.setR_Opt2(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt2")));
                balanceBean.setR_Opt3(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt3")));
                balanceBean.setR_Opt4(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt4")));
                balanceBean.setR_Opt5(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt5")));
                balanceBean.setR_Opt6(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt6")));
                balanceBean.setR_Opt7(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt7")));
                balanceBean.setR_Opt8(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt8")));
                balanceBean.setR_Opt9(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt9")));
                balanceBean.setVoidMSG(ThaiUtil.ASCII2Unicode(rs.getString("VoidMSG")));
                balanceBean.setR_PrCuCode(rs.getString("R_PrCuCode"));
                balanceBean.setR_Serve(rs.getString("R_Serve"));
                balanceBean.setR_PrintOK(rs.getString("R_PrintOK"));
                balanceBean.setR_KicOK(rs.getString("R_KicOK"));
                balanceBean.setStkCode(rs.getString("StkCode"));
                balanceBean.setPosStk(rs.getString("PosStk"));
                balanceBean.setR_PrChkType(rs.getString("R_PrChkType"));
                balanceBean.setR_PrQuan(rs.getFloat("R_PrQuan"));
                balanceBean.setR_PrSubType(rs.getString("R_PrSubType"));
                balanceBean.setR_PrSubCode(rs.getString("R_PrSubCode"));
                balanceBean.setR_PrSubQuan(rs.getFloat("R_PrSubQuan"));
                balanceBean.setR_PrSubDisc(rs.getFloat("R_PrSubDisc"));
                balanceBean.setR_PrSubBath(rs.getFloat("R_PrSubBath"));
                balanceBean.setR_PrSubAmt(rs.getFloat("R_PrSubAmt"));
                balanceBean.setR_PrSubAdj(rs.getFloat("R_PrSubAdj"));
                balanceBean.setR_PrCuDisc(rs.getFloat("R_PrCuDisc"));
                balanceBean.setR_PrCuBath(rs.getFloat("R_PrCuBath"));
                balanceBean.setR_PrCuAdj(rs.getFloat("R_PrCuAdj"));
                balanceBean.setR_QuanCanDisc(rs.getFloat("R_QuanCanDisc"));
                balanceBean.setR_Order(rs.getString("R_Order"));
                balanceBean.setR_PItemNo(rs.getInt("R_PItemNo"));
                balanceBean.setR_PKicQue(rs.getInt("R_PKicQue"));
                balanceBean.setR_MemSum(rs.getString("R_MemSum"));
                balanceBean.setR_MoveItem(rs.getString("R_MoveItem"));
                balanceBean.setR_MoveFrom(rs.getString("R_MoveFrom"));
                balanceBean.setR_MoveUser(rs.getString("R_MoveUser"));
                balanceBean.setR_MoveFlag(rs.getString("R_MoveFlag"));
                balanceBean.setR_MovePrint(rs.getString("R_MovePrint"));
                balanceBean.setR_Pause(rs.getString("R_Pause"));

                try {
                    balanceBean.setR_Date(rs.getDate("R_Date"));
                } catch (SQLException e) {
                    MSG.ERR(null, e.getMessage());
                }

                //balanceBean.setR_CashCard(""+rs.getFloat("R_CashCard"));
                beanData.add(balanceBean);
            }

            rs.close();
//            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());

        }

        return beanData;
    }

    public void updateTableHold(String table, String emp) {
        String date_default = "1899-12-30";
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            String sql = "update tablefile set "
                    + "TOnAct='N',"
                    + "TCurTime=curtime(),"
                    + "Service='" + POSConfigSetup.Bean().getP_Service() + "',"
                    + "MemBegin='" + date_default + "',"
                    + "MemEnd='" + date_default + "' "
                    + "where tcode='" + table + "' "
                    + "and tOnAct='Y'";
//            Statement stmt = mysql.getConnection().createStatement();
            mysql.getConnection().createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());

        } finally {
            mysql.close();
        }
    }

    public String getIndexBalance(String R_Table) {
        String index = R_Table + "/001";
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            String sql = "select max(R_Index) R_Index "
                    + "from balance "
                    + "where R_Table = '" + R_Table + "' "
                    + "order by R_Index";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                String R_Index;
                boolean notfound = false;
                while (rs.next()) {
                    notfound = true;
                    R_Index = rs.getString("R_Index");
                    if (R_Index == null) {
                        break;
                    }
                    String[] data = R_Index.split("/");
                    int id;
                    try {
                        id = Integer.parseInt(data[1]);
                        id = id + 1;
                    } catch (NumberFormatException e) {
                        id = 1;
                    }
                    if (id < 10) {
                        index = R_Table + "/00" + id;
                    } else if (id < 100) {
                        index = R_Table + "/0" + id;
                    } else if (id < 1000) {
                        index = R_Table + "/" + id;
                    }
                }
                if (!notfound) {
                    index = R_Table + "/001";
                }
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());

            index = R_Table + "/001";
        } finally {
            mysql.close();
        }

        return index;
    }

    public void setDefaultBalance(String table) {
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            String sql = "delete from balance where R_Table='" + table + "'";
            mysql.getConnection().createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        } finally {
            mysql.close();
        }
    }

    public boolean checkQuantity(String table, String R_PluCode, double R_Quan) {
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            String sql = "select sum(R_Quan) R_Quan "
                    + "from balance "
                    + "where R_PluCode='" + R_PluCode + "' "
                    + "and R_Table='" + table + "' "
                    + "and R_Void<>'V' "
                    + "group by R_PluCode";
            double quan;
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                quan = R_Quan;
                if (rs.next()) {
                    quan = rs.getDouble("R_Quan");
                    quan += R_Quan;
                }
            }
            return quan >= 0;
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            return false;
        } finally {
            mysql.close();
        }
    }

    public BalanceBean getProduct(String PCode, String R_Index) {
        BalanceBean balanceBean = new BalanceBean();
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            String sql = "select * from balance "
                    + "where R_PluCode='" + PCode + "' "
                    + "and R_Index='" + R_Index + "'";
            try (Statement stmt = mysql.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    balanceBean.setR_Index(rs.getString("R_Index"));
                    balanceBean.setR_Table(rs.getString("R_Table"));
                    balanceBean.setR_Time(rs.getString("R_Time"));
                    balanceBean.setMacno(rs.getString("Macno"));
                    balanceBean.setCashier(rs.getString("Cashier"));
                    balanceBean.setR_Emp(rs.getString("R_Emp"));
                    balanceBean.setR_PluCode(rs.getString("R_PluCode"));
                    balanceBean.setR_PName(ThaiUtil.ASCII2Unicode(rs.getString("R_PName")));
                    balanceBean.setR_Unit(ThaiUtil.ASCII2Unicode(rs.getString("R_Unit")));
                    balanceBean.setR_Group(rs.getString("R_Group"));
                    balanceBean.setR_Status(rs.getString("R_Status"));
                    balanceBean.setR_Normal(rs.getString("R_Normal"));
                    balanceBean.setR_Discount(rs.getString("R_Discount"));
                    balanceBean.setR_Service(rs.getString("R_Service"));
                    balanceBean.setR_Stock(rs.getString("R_Stock"));
                    balanceBean.setR_Set(rs.getString("R_Set"));
                    balanceBean.setR_Vat(rs.getString("R_Vat"));
                    balanceBean.setR_Type(rs.getString("R_Type"));
                    balanceBean.setR_ETD(rs.getString("R_ETD"));
                    balanceBean.setR_Quan(rs.getFloat("R_Quan"));
                    balanceBean.setR_Price(rs.getFloat("R_Price"));
                    balanceBean.setR_Total(rs.getFloat("R_Total"));
                    String R_PrType = rs.getString("R_PrType");
                    if (R_PrType == null) {
                        R_PrType = "";
                    }
                    balanceBean.setR_PrType(R_PrType);
                    balanceBean.setR_PrCode(rs.getString("R_PrCode"));
                    balanceBean.setR_PrDisc(rs.getFloat("R_PrDisc"));
                    balanceBean.setR_PrBath(rs.getFloat("R_PrBath"));
                    balanceBean.setR_PrAmt(rs.getFloat("R_PrAmt"));
                    balanceBean.setR_DiscBath(rs.getFloat("R_DiscBath"));
                    balanceBean.setR_PrCuType(rs.getString("R_PrCuType"));
                    balanceBean.setR_PrCuQuan(rs.getFloat("R_PrCuQuan"));
                    balanceBean.setR_PrCuAmt(rs.getFloat("R_PrCuAmt"));
                    balanceBean.setR_Redule(rs.getFloat("R_Redule"));
                    balanceBean.setR_Kic(rs.getString("R_Kic"));
                    balanceBean.setR_KicPrint(rs.getString("R_KicPrint"));
                    balanceBean.setR_Void(rs.getString("R_Void"));
                    balanceBean.setR_VoidUser(rs.getString("R_VoidUser"));
                    balanceBean.setR_VoidTime(rs.getString("R_VoidTime"));
                    balanceBean.setR_Opt1(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt1")));
                    balanceBean.setR_Opt2(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt2")));
                    balanceBean.setR_Opt3(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt3")));
                    balanceBean.setR_Opt4(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt4")));
                    balanceBean.setR_Opt5(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt5")));
                    balanceBean.setR_Opt6(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt6")));
                    balanceBean.setR_Opt7(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt7")));
                    balanceBean.setR_Opt8(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt8")));
                    balanceBean.setR_Opt9(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt9")));
                    balanceBean.setVoidMSG(ThaiUtil.ASCII2Unicode(rs.getString("VoidMSG")));
                    balanceBean.setR_PrCuCode(rs.getString("R_PrCuCode"));
                    balanceBean.setR_Serve(rs.getString("R_Serve"));
                    balanceBean.setR_PrintOK(rs.getString("R_PrintOK"));
                    balanceBean.setR_KicOK(rs.getString("R_KicOK"));
                    balanceBean.setStkCode(rs.getString("StkCode"));
                    balanceBean.setPosStk(rs.getString("PosStk"));
                    balanceBean.setR_PrChkType(rs.getString("R_PrChkType"));
                    balanceBean.setR_PrQuan(rs.getFloat("R_PrQuan"));
                    balanceBean.setR_PrSubType(rs.getString("R_PrSubType"));
                    balanceBean.setR_PrSubCode(rs.getString("R_PrSubCode"));
                    balanceBean.setR_PrSubQuan(rs.getFloat("R_PrSubQuan"));
                    balanceBean.setR_PrSubDisc(rs.getFloat("R_PrSubDisc"));
                    balanceBean.setR_PrSubBath(rs.getFloat("R_PrSubBath"));
                    balanceBean.setR_PrSubAmt(rs.getFloat("R_PrSubAmt"));
                    balanceBean.setR_PrSubAdj(rs.getFloat("R_PrSubAdj"));
                    balanceBean.setR_PrCuDisc(rs.getFloat("R_PrCuDisc"));
                    balanceBean.setR_PrCuBath(rs.getFloat("R_PrCuBath"));
                    balanceBean.setR_PrCuAdj(rs.getFloat("R_PrCuAdj"));
                    balanceBean.setR_QuanCanDisc(rs.getFloat("R_QuanCanDisc"));
                    balanceBean.setR_Order(rs.getString("R_Order"));
                    balanceBean.setR_PItemNo(rs.getInt("R_PItemNo"));
                    balanceBean.setR_PKicQue(rs.getInt("R_PKicQue"));
                    balanceBean.setR_MemSum(rs.getString("R_MemSum"));
                    balanceBean.setR_MoveItem(rs.getString("R_MoveItem"));
                    balanceBean.setR_MoveFrom(rs.getString("R_MoveFrom"));
                    balanceBean.setR_MoveUser(rs.getString("R_MoveUser"));
                    balanceBean.setR_MoveFlag(rs.getString("R_MoveFlag"));
                    balanceBean.setR_MovePrint(rs.getString("R_MovePrint"));
                    balanceBean.setR_Pause(rs.getString("R_Pause"));
                }
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        } finally {
            mysql.close();
        }

        return balanceBean;
    }

    public BalanceBean getBalanceIndex(String Table, String R_Index) {
        BalanceBean balanceBean = null;
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            String sql = "select * from balance "
                    + "where R_Table='" + Table + "' "
                    + "and R_Index='" + R_Index + "'";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                if (rs.next()) {
                    balanceBean = new BalanceBean();
                    balanceBean.setR_Index(rs.getString("R_Index"));
                    balanceBean.setR_Table(rs.getString("R_Table"));
                    balanceBean.setR_Date(rs.getDate("R_Date"));
                    balanceBean.setR_Time(rs.getString("R_Time"));
                    balanceBean.setMacno(rs.getString("Macno"));
                    balanceBean.setCashier(rs.getString("Cashier"));
                    balanceBean.setR_Emp(rs.getString("R_Emp"));
                    balanceBean.setR_PluCode(rs.getString("R_PluCode"));
                    balanceBean.setR_PName(ThaiUtil.ASCII2Unicode(rs.getString("R_PName")));
                    balanceBean.setR_Unit(ThaiUtil.ASCII2Unicode(rs.getString("R_Unit")));
                    balanceBean.setR_Group(rs.getString("R_Group"));
                    balanceBean.setR_Status(rs.getString("R_Status"));
                    balanceBean.setR_Normal(rs.getString("R_Normal"));
                    balanceBean.setR_Discount(rs.getString("R_Discount"));
                    balanceBean.setR_Service(rs.getString("R_Service"));
                    balanceBean.setR_Stock(rs.getString("R_Stock"));
                    balanceBean.setR_Set(rs.getString("R_Set"));
                    balanceBean.setR_Vat(rs.getString("R_Vat"));
                    balanceBean.setR_Type(rs.getString("R_Type"));
                    balanceBean.setR_ETD(rs.getString("R_ETD"));
                    balanceBean.setR_Quan(rs.getFloat("R_Quan"));
                    balanceBean.setR_Price(rs.getFloat("R_Price"));
                    balanceBean.setR_Total(rs.getFloat("R_Total"));
                    String R_PrType = rs.getString("R_PrType");
                    if (R_PrType == null) {
                        R_PrType = "";
                    }
                    balanceBean.setR_PrType(R_PrType);
                    balanceBean.setR_PrCode(rs.getString("R_PrCode"));
                    balanceBean.setR_PrDisc(rs.getFloat("R_PrDisc"));
                    balanceBean.setR_PrBath(rs.getFloat("R_PrBath"));
                    balanceBean.setR_PrAmt(rs.getFloat("R_PrAmt"));
                    balanceBean.setR_DiscBath(rs.getFloat("R_DiscBath"));
                    balanceBean.setR_PrCuType(rs.getString("R_PrCuType"));
                    balanceBean.setR_PrCuQuan(rs.getFloat("R_PrCuQuan"));
                    balanceBean.setR_PrCuAmt(rs.getFloat("R_PrCuAmt"));
                    balanceBean.setR_Redule(rs.getFloat("R_Redule"));
                    balanceBean.setR_Kic(rs.getString("R_Kic"));
                    balanceBean.setR_KicPrint(rs.getString("R_KicPrint"));
                    balanceBean.setR_Void(rs.getString("R_Void"));
                    balanceBean.setR_VoidUser(rs.getString("R_VoidUser"));
                    balanceBean.setR_VoidTime(rs.getString("R_VoidTime"));
                    balanceBean.setR_Opt1(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt1")));
                    balanceBean.setR_Opt2(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt2")));
                    balanceBean.setR_Opt3(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt3")));
                    balanceBean.setR_Opt4(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt4")));
                    balanceBean.setR_Opt5(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt5")));
                    balanceBean.setR_Opt6(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt6")));
                    balanceBean.setR_Opt7(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt7")));
                    balanceBean.setR_Opt8(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt8")));
                    balanceBean.setR_Opt9(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt9")));
                    balanceBean.setVoidMSG(ThaiUtil.ASCII2Unicode(rs.getString("VoidMSG")));
                    balanceBean.setR_PrCuCode(rs.getString("R_PrCuCode"));
                    balanceBean.setR_Serve(rs.getString("R_Serve"));
                    balanceBean.setR_PrintOK(rs.getString("R_PrintOK"));
                    balanceBean.setR_KicOK(rs.getString("R_KicOK"));
                    balanceBean.setStkCode(rs.getString("StkCode"));
                    balanceBean.setPosStk(rs.getString("PosStk"));
                    balanceBean.setR_PrChkType(rs.getString("R_PrChkType"));
                    balanceBean.setR_PrQuan(rs.getFloat("R_PrQuan"));
                    balanceBean.setR_PrSubType(rs.getString("R_PrSubType"));
                    balanceBean.setR_PrSubCode(rs.getString("R_PrSubCode"));
                    balanceBean.setR_PrSubQuan(rs.getFloat("R_PrSubQuan"));
                    balanceBean.setR_PrSubDisc(rs.getFloat("R_PrSubDisc"));
                    balanceBean.setR_PrSubBath(rs.getFloat("R_PrSubBath"));
                    balanceBean.setR_PrSubAmt(rs.getFloat("R_PrSubAmt"));
                    balanceBean.setR_PrSubAdj(rs.getFloat("R_PrSubAdj"));
                    balanceBean.setR_PrCuDisc(rs.getFloat("R_PrCuDisc"));
                    balanceBean.setR_PrCuBath(rs.getFloat("R_PrCuBath"));
                    balanceBean.setR_PrCuAdj(rs.getFloat("R_PrCuAdj"));
                    balanceBean.setR_QuanCanDisc(rs.getFloat("R_QuanCanDisc"));
                    balanceBean.setR_Order(rs.getString("R_Order"));
                    balanceBean.setR_PItemNo(rs.getInt("R_PItemNo"));
                    balanceBean.setR_PKicQue(rs.getInt("R_PKicQue"));
                    balanceBean.setR_MemSum(rs.getString("R_MemSum"));
                    balanceBean.setR_MoveItem(rs.getString("R_MoveItem"));
                    balanceBean.setR_MoveFrom(rs.getString("R_MoveFrom"));
                    balanceBean.setR_MoveUser(rs.getString("R_MoveUser"));
                    balanceBean.setR_MoveFlag(rs.getString("R_MoveFlag"));
                    balanceBean.setR_MovePrint(rs.getString("R_MovePrint"));
                    balanceBean.setR_Pause(rs.getString("R_Pause"));

                    balanceBean.setR_LinkIndex(rs.getString("R_LinkIndex"));
                    balanceBean.setR_SPIndex(rs.getString("R_SPIndex"));
                }
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            return null;
        } finally {
            mysql.close();
        }

        return balanceBean;
    }

    public List<BalanceBean> getBalanceIndexVoid(String Table) {
        List<BalanceBean> list = new ArrayList<>();
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            String sql = "select * "
                    + "from balance "
                    + "where r_table='" + Table + "' "
                    + "and r_void='V' "
                    + "and r_pause='Y' "
                    + "and r_kicprint=''";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                while (rs.next()) {
                    BalanceBean balanceBean = new BalanceBean();
                    balanceBean.setR_Index(rs.getString("R_Index"));
                    balanceBean.setR_Table(rs.getString("R_Table"));
                    balanceBean.setR_Time(rs.getString("R_Time"));
                    balanceBean.setMacno(rs.getString("Macno"));
                    balanceBean.setCashier(rs.getString("Cashier"));
                    balanceBean.setR_Emp(rs.getString("R_Emp"));
                    balanceBean.setR_PluCode(rs.getString("R_PluCode"));
                    balanceBean.setR_PName(ThaiUtil.ASCII2Unicode(rs.getString("R_PName")));
                    balanceBean.setR_Unit(ThaiUtil.ASCII2Unicode(rs.getString("R_Unit")));
                    balanceBean.setR_Group(rs.getString("R_Group"));
                    balanceBean.setR_Status(rs.getString("R_Status"));
                    balanceBean.setR_Normal(rs.getString("R_Normal"));
                    balanceBean.setR_Discount(rs.getString("R_Discount"));
                    balanceBean.setR_Service(rs.getString("R_Service"));
                    balanceBean.setR_Stock(rs.getString("R_Stock"));
                    balanceBean.setR_Set(rs.getString("R_Set"));
                    balanceBean.setR_Vat(rs.getString("R_Vat"));
                    balanceBean.setR_Type(rs.getString("R_Type"));
                    balanceBean.setR_ETD(rs.getString("R_ETD"));
                    balanceBean.setR_Quan(rs.getFloat("R_Quan"));
                    balanceBean.setR_Price(rs.getFloat("R_Price"));
                    balanceBean.setR_Total(rs.getFloat("R_Total"));
                    String R_PrType = rs.getString("R_PrType");
                    if (R_PrType == null) {
                        R_PrType = "";
                    }
                    balanceBean.setR_PrType(R_PrType);
                    balanceBean.setR_PrCode(rs.getString("R_PrCode"));
                    balanceBean.setR_PrDisc(rs.getFloat("R_PrDisc"));
                    balanceBean.setR_PrBath(rs.getFloat("R_PrBath"));
                    balanceBean.setR_PrAmt(rs.getFloat("R_PrAmt"));
                    balanceBean.setR_DiscBath(rs.getFloat("R_DiscBath"));
                    balanceBean.setR_PrCuType(rs.getString("R_PrCuType"));
                    balanceBean.setR_PrCuQuan(rs.getFloat("R_PrCuQuan"));
                    balanceBean.setR_PrCuAmt(rs.getFloat("R_PrCuAmt"));
                    balanceBean.setR_Redule(rs.getFloat("R_Redule"));
                    balanceBean.setR_Kic(rs.getString("R_Kic"));
                    balanceBean.setR_KicPrint(rs.getString("R_KicPrint"));
                    balanceBean.setR_Void(rs.getString("R_Void"));
                    balanceBean.setR_VoidUser(rs.getString("R_VoidUser"));
                    balanceBean.setR_VoidTime(rs.getString("R_VoidTime"));
                    balanceBean.setR_Opt1(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt1")));
                    balanceBean.setR_Opt2(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt2")));
                    balanceBean.setR_Opt3(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt3")));
                    balanceBean.setR_Opt4(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt4")));
                    balanceBean.setR_Opt5(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt5")));
                    balanceBean.setR_Opt6(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt6")));
                    balanceBean.setR_Opt7(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt7")));
                    balanceBean.setR_Opt8(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt8")));
                    balanceBean.setR_Opt9(ThaiUtil.ASCII2Unicode(rs.getString("R_Opt9")));
                    balanceBean.setVoidMSG(ThaiUtil.ASCII2Unicode(rs.getString("VoidMSG")));
                    balanceBean.setR_PrCuCode(rs.getString("R_PrCuCode"));
                    balanceBean.setR_Serve(rs.getString("R_Serve"));
                    balanceBean.setR_PrintOK(rs.getString("R_PrintOK"));
                    balanceBean.setR_KicOK(rs.getString("R_KicOK"));
                    balanceBean.setStkCode(rs.getString("StkCode"));
                    balanceBean.setPosStk(rs.getString("PosStk"));
                    balanceBean.setR_PrChkType(rs.getString("R_PrChkType"));
                    balanceBean.setR_PrQuan(rs.getFloat("R_PrQuan"));
                    balanceBean.setR_PrSubType(rs.getString("R_PrSubType"));
                    balanceBean.setR_PrSubCode(rs.getString("R_PrSubCode"));
                    balanceBean.setR_PrSubQuan(rs.getFloat("R_PrSubQuan"));
                    balanceBean.setR_PrSubDisc(rs.getFloat("R_PrSubDisc"));
                    balanceBean.setR_PrSubBath(rs.getFloat("R_PrSubBath"));
                    balanceBean.setR_PrSubAmt(rs.getFloat("R_PrSubAmt"));
                    balanceBean.setR_PrSubAdj(rs.getFloat("R_PrSubAdj"));
                    balanceBean.setR_PrCuDisc(rs.getFloat("R_PrCuDisc"));
                    balanceBean.setR_PrCuBath(rs.getFloat("R_PrCuBath"));
                    balanceBean.setR_PrCuAdj(rs.getFloat("R_PrCuAdj"));
                    balanceBean.setR_QuanCanDisc(rs.getFloat("R_QuanCanDisc"));
                    balanceBean.setR_Order(rs.getString("R_Order"));
                    balanceBean.setR_PItemNo(rs.getInt("R_PItemNo"));
                    balanceBean.setR_PKicQue(rs.getInt("R_PKicQue"));
                    balanceBean.setR_MemSum(rs.getString("R_MemSum"));
                    balanceBean.setR_MoveItem(rs.getString("R_MoveItem"));
                    balanceBean.setR_MoveFrom(rs.getString("R_MoveFrom"));
                    balanceBean.setR_MoveUser(rs.getString("R_MoveUser"));
                    balanceBean.setR_MoveFlag(rs.getString("R_MoveFlag"));
                    balanceBean.setR_MovePrint(rs.getString("R_MovePrint"));
                    balanceBean.setR_Pause(rs.getString("R_Pause"));

                    list.add(balanceBean);
                }
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            return null;
        } finally {
            mysql.close();
        }

        return list;
    }

    public void deleteBalance(String r_Table, String r_PluCode, String r_Index) {
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            String sql = "delete from balance "
                    + "where R_Table = '" + r_Table + "' "
                    + "and R_PluCode = '" + r_PluCode + "' "
                    + "and R_Index = '" + r_Index + "'";
            try (Statement stmt = mysql.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        } finally {
            mysql.close();
        }
    }

    public void deleteProduct(String table, String PCode, String R_Index) {
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            String sql = "delete from balance "
                    + "where R_Index='" + R_Index + "' "
                    + "and R_Table='" + table + "' ";
            try (Statement stmt = mysql.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        } finally {
            mysql.close();
        }
    }

    public boolean backupBalance(String tableNo) {
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            String sql = "delete from temp_balance";
            try (Statement stmt = mysql.getConnection().createStatement()) {
                stmt.executeUpdate(sql);

                sql = "insert into temp_balance select * from balance where R_Table='" + tableNo + "';";
                stmt.executeUpdate(sql);
            }

            return true;
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());

            try {
                mysql.open();
                try (Statement stmt = mysql.getConnection().createStatement()) {
                    stmt.executeUpdate("drop table if exists temp_balance;");
                    stmt.executeUpdate("create table temp_balance select * from balance limit 0,0;");
                }
            } catch (SQLException e1) {
                MSG.ERR(e1.getMessage());
            } finally {
                mysql.close();
            }
        } finally {
            mysql.close();
        }

        return false;
    }

    public boolean restoreBalance(String tableNo, String table2) {
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            Statement stmt = mysql.getConnection().createStatement();

            // clear table 1
            String sql = "delete from balance where R_Table='" + tableNo + "'";
            stmt.executeUpdate(sql);

            // clear table 1-1
            sql = "delete from balance where R_Table='" + table2 + "'";
            stmt.executeUpdate(sql);

            sql = "insert into balance select * from temp_balance where R_Table='" + tableNo + "';";
            stmt.executeUpdate(sql);

            sql = "delete from temp_balance";
            stmt.executeUpdate(sql);

            return true;
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        } finally {
            mysql.close();
        }

        return false;
    }

    public boolean copyProductTo(String table1, String table2, String R_Index, String PCode) {
        BalanceControl balanceControl = new BalanceControl();
        BalanceBean bean = balanceControl.getBalanceIndex(table1, R_Index);
        if (bean == null) {
            return false;
        }
        bean.setR_Index(getIndexBalance(table2));
        bean.setR_Table(table2);
        if (bean.getR_LinkIndex().split("/").length > 1) {
            String getIndex = bean.getR_LinkIndex().split("/")[1];
            bean.setR_LinkIndex(table2 + "/" + getIndex);
        }
        return saveBalance(bean);
    }

    public static void updateProSerTable(String table, MemberBean memberBean) {
        //??????????????????????????????????????????
        DateConvert dc = new DateConvert();
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            String sql = "select procode,prodesc,pdate1,pdate2,ptype,psum1 from protab;";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                while (rs.next() && !rs.wasNull()) {
                    int dateEXP = Integer.parseInt(rs.getString("pdate2").replace("-", ""));
                    int nowDate = Integer.parseInt(dc.GetCurrentDate().replace("-", ""));
                    if (dateEXP >= nowDate) {
                        PromotionControl proControl = new PromotionControl();
                        proControl.updatePromotion(table);
                    }
                }
            }
        } catch (NumberFormatException | SQLException e) {
            MSG.ERR(e.getMessage());
        } finally {
            mysql.close();
        }

        if (memberBean != null) {
            //??????????????? VIPDiscount
            updateProSerTableMem(table, memberBean);

            // ?????????????????????????????????????????????
            MemberControl memControl = new MemberControl();
            memControl.updateMemberDiscount(table, memberBean);
        }

        //?????????????????????????????????
        DiscountControl discControl = new DiscountControl();
        discControl.updateDiscount(table);

        //??????????????? % ?????????????????????????????????
        //?????????????????????????????????????????? + ????????????????????????????????????????????????????????????
        ServiceControl serviceControl = new ServiceControl();
        serviceControl.updateService(table);
    }

    public static void updateProSerTableMem(String table, MemberBean memberBean) {
        if (memberBean != null) {
            MemberControl memControl = new MemberControl();
            memControl.updateMemberAllBalance(table, memberBean);
        }
    }

    public static void updateProSerTableMemVIP(String table, String discountRate) {
        MemberControl memControl = new MemberControl();
        memControl.updateMemVIPAllBalance(table, discountRate);
    }

    public static String GetDiscount(String table) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        double discount = 0.00;
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            String sql = "select sum(R_PrAmt) + sum(R_Discbath) discount from balance where r_table='" + table + "' and r_void<>'V'";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                if (rs.next()) {
                    discount = rs.getDouble("discount");
                } else {
                    return 0.00 + "";
                }
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        } finally {
            mysql.close();
        }
        return df.format(discount);
    }

}
