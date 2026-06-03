/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softpos.pos.core.model;

////

import com.softpos.pos.core.controller.AppContext;
import com.softpos.pos.core.controller.BranchControl;
import com.softpos.pos.core.controller.ProductControl;
import com.softpos.util.AppLogUtil;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.softpos.util.DateConvert;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class PKicTran {
    
    private final MySQLConnect mysqlConnect = new MySQLConnect();
    private final BranchControl BranchControl = AppContext.getBranchControl();

    public void setPKicTran(List<BalanceBean> bill, int kicItemNo) {

        DateConvert dc = new DateConvert();
        String today = dc.GetCurrentDate();
        String time = dc.GetCurrentTime();
        try {
            
            mysqlConnect.open();
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
                    mysqlConnect.executeUpdate(sqlINSKictran);
                    BranchControl.updateKicItemNo();
                }
            }

            mysqlConnect.close();
        } catch (Exception e) {
            AppLogUtil.log(PKicTran.class, "error", e);
        }
    }

    public List<PKicTranBean> getKicTran(String tableNo) {
        DateConvert dc = new DateConvert();
        List<PKicTranBean> list = new ArrayList();
        
        try {
            mysqlConnect.open();
            String sql = "select pitemno, pcode, pindex, ptable, ptimein, pqty,"
                    + " pflage, petd,R_UrgentFoodItemName "
                    + "from kictran "
                    + "where ptable='" + tableNo + "' "
                    + "and pflage='N' "
                    + "order by pitemno,petd;";
            ResultSet rs = mysqlConnect.executeQuery(sql);

            ProductControl ProductControl = AppContext.getProductControl();
            while (rs.next()) {
                PKicTranBean kicTranBean = new PKicTranBean();
                ProductBean bean = ProductControl.getData(rs.getString("pcode"));
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
                if (null == pdesc || pdesc.equals("") || pdesc.equals("null")) {
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
            
        } catch (SQLException | ParseException e) {
            AppLogUtil.log(PKicTran.class, "error", e);
        } finally {
            mysqlConnect.close();
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

    public void updateKicTranDisplay(String status, String tableNo, String pcode, String pindex) {
        try {
            mysqlConnect.open();
            String sql = "";
            if (pindex.equals("")) {
                sql = "update kictran set R_ShowDisplayAlert='" + status + "' where ptable='" + tableNo + "';";
                if (status.equals("Y")) {
                    sql = "update kictran set R_ShowDisplayAlert='" + status + "',R_AlertKitchen='Y' where ptable='" + tableNo + "';";
                }
                mysqlConnect.executeUpdate(sql);
            } else {
                sql = "update kictran set R_ShowDisplayAlert='" + status + "' where ptable='" + tableNo + "'and pcode='" + pcode + "' and pindex='" + pindex + "';";
                if (status.equals("Y")) {
                    sql = "update kictran set R_ShowDisplayAlert='" + status + "',R_AlertKitchen='Y' where ptable='" + tableNo + "' and pcode='" + pcode + "' and pindex='" + pindex + "';";
                }
                mysqlConnect.executeUpdate(sql);
            }

            mysqlConnect.close();
        } catch (Exception e) {
            AppLogUtil.log(PKicTran.class, "error", e);
        }

    }
}
