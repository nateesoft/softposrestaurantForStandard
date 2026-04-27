/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softpos.bean;
////

import com.softpos.pos.core.controller.ProductControl;
import com.softpos.pos.core.model.BalanceBean;
import com.softpos.pos.core.model.ProductBean;
import static com.softpos.pos.core.controller.BranchControl.updateKicItemNo;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import util.DateConvert;
import util.MSG;

/**
 *
 * @author Administrator
 */
public class PKicTran {

    public static void setPKicTran(ArrayList<BalanceBean> bill, int kicItemNo) {

        DateConvert dc = new DateConvert();
        String today = dc.GetCurrentDate();
        String time = dc.GetCurrentTime();
        try {
            MySQLConnect mysql = new MySQLConnect();
            mysql.open();
            if (bill.size() > 0) {
                for (int i = 0; i < bill.size(); i++) {
                    kicItemNo++;
                    String sqlINSKictran = "INSERT INTO kictran ("
                            + "PItemNo, PDate, PCode, PIndex, MacNo,"
                            + " Cashier, Emp, PTable, PKic, PTimeIn,"
                            + " PTimeOut, PVoid, PETD, PQty, PFlage,"
                            + " PServe, PServeTime, PWaitTime, PPayment, PInvNo,"
                            + " PWaitServe, PWaitTotal, R_PEName) "
                            + "VALUES ("
                            + "'" + kicItemNo + "', '" + today + "', '" + bill.get(i).getR_PluCode() + "', '" + bill.get(i).getR_Index() + "', '" + bill.get(i).getMacno() + "',"
                            + " '" + bill.get(i).getCashier() + "', '" + bill.get(i).getR_Emp() + "', '" + bill.get(i).getR_Table() + "', '" + bill.get(i).getR_Kic() + "', '" + time + "',"
                            + " '00:00:00', '', '" + bill.get(i).getR_ETD() + "', '" + bill.get(i).getR_Quan() + "', 'N',"
                            + " 'N', '00:00:00', '00:00:00', 'N', '',"
                            + " '00:00:00', '00:00:00', ''); ";
                    mysql.getConnection().createStatement().executeUpdate(sqlINSKictran);
                    updateKicItemNo();
                }
            }

            mysql.close();
        } catch (Exception e) {
            MSG.NOTICE(e.toString());
        }
    }

    public static ArrayList<PKicTranBean> getKicTran(String tableNo) {
        DateConvert dc = new DateConvert();
        ArrayList<PKicTranBean> list = new ArrayList();
        try {
            MySQLConnect mysql = new MySQLConnect();
            mysql.open();
            String sql = "select pitemno, pcode, pindex, ptable, ptimein, pqty,"
                    + " pflage, petd,R_UrgentFoodItemName "
                    + "from kictran "
                    + "where ptable='" + tableNo + "' "
                    + "and pflage='N' "
                    + "order by pitemno,petd;";
            ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql);

            ProductControl ProductControl = new ProductControl();
            while (rs.next()) {
                PKicTranBean kicTranBean = new PKicTranBean();
                ProductBean bean = new ProductBean();
                bean = ProductControl.getData(rs.getString("pcode"));
                kicTranBean.setpItemNo(rs.getString("pitemno"));
                kicTranBean.setpCode(rs.getString("pcode"));
                kicTranBean.setpDesc(bean.getPDesc());
                kicTranBean.setpIndex(rs.getString("pindex"));
                kicTranBean.setpTable(rs.getString("ptable"));
                kicTranBean.setpTimeIn(rs.getString("ptimeIn"));
                kicTranBean.setpQty(rs.getInt("pqty"));
                kicTranBean.setpFlage(rs.getString("pflage"));
                String etd = rs.getString("petd");
                String pdesc = rs.getString("R_UrgentFoodItemName");
                if (pdesc.isEmpty() || pdesc == null || pdesc.equals("") || pdesc.equals("null")) {
                    pdesc = "ตามทั้งโต๊ะ";
                }
                if (etd.equals("E")) {
                    etd = "นั่งทาน";
                }
                if (etd.equals("T")) {
                    etd = "ห่อกลับ";
                }
                if (etd.equals("D")) {
                    etd = "Delivery";
                }
                if (etd.equals("P")) {
                    etd = "Pinto";
                }

                if (etd.equals("W")) {
                    etd = "WholeSale";
                }
                kicTranBean.setpEtd(etd);
                String timeWait;
                timeWait = getDefferentTime(kicTranBean.getpTimeIn(), dc.GetCurrentTime());
                kicTranBean.setpWaitTime(timeWait);
                list.add(kicTranBean);
                rs.close();
            }
            mysql.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return list;
    }

    public static String getDefferentTime(String time1, String time2) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date1 = format.parse(time1);
        Date date2 = format.parse(time2);
        long difference = date2.getTime() - date1.getTime();
        long diffSeconds = difference / 1000 % 60;
        long diffMinutes = difference / (60 * 1000) % 60;
        long diffHours = difference / (60 * 60 * 1000) % 24;
        long diffDays = difference / (24 * 60 * 60 * 1000);
        String time = "";
        time = diffHours + "." + diffMinutes;
        return time;
    }

    public static void updateKicTranDisplay(String status, String tableNo, String pcode, String pindex) {
        try {
            MySQLConnect mysql = new MySQLConnect();
            mysql.open();
            String sql = "";
            if (pindex.equals("")) {
                sql = "update kictran set R_ShowDisplayAlert='" + status + "' where ptable='" + tableNo + "';";
                if (status.equals("Y")) {
                    sql = "update kictran set R_ShowDisplayAlert='" + status + "',R_AlertKitchen='Y' where ptable='" + tableNo + "';";
                }
                mysql.getConnection().createStatement().executeUpdate(sql);
            } else {
                sql = "update kictran set R_ShowDisplayAlert='" + status + "' where ptable='" + tableNo + "'and pcode='" + pcode + "' and pindex='" + pindex + "';";
                if (status.equals("Y")) {
                    sql = "update kictran set R_ShowDisplayAlert='" + status + "',R_AlertKitchen='Y' where ptable='" + tableNo + "' and pcode='" + pcode + "' and pindex='" + pindex + "';";
                }
                mysql.getConnection().createStatement().executeUpdate(sql);
            }

            mysql.close();
        } catch (Exception e) {
            MSG.NOTICE(e.toString());
        }

    }

//    public static void main(String[] args) throws ParseException {
//
//        getDefferentTime("10:00:01", "10:32:03");
//    }
}
