/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SoftwareAutoRun;

import com.softpos.bean.S_TranT_SaleBean;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.util.ArrayList;
import util.DateConvert;
import util.MSG;

/**
 *
 * @author Administrator
 */
public class MathDiscountFromDb {

    public void mathDiscount(String branch, String dateFrom, String dateTo) {

        MySQLConnect mysql = new MySQLConnect();
        ArrayList<S_TranT_SaleBean> list;
        list = new ArrayList();
        DateConvert dc = new DateConvert();
        String sql = "";
        try {
            mysql.open();
            //ค้นหาจาก S_Tran ว่ามีส่วนลด
            if (dateFrom.equals(dc.GetCurrentDate())) {
                sql = "select * from t_sale;";
////                sql = "select * from t_sale where r_total<>r_nettotal;";
            } else {
                sql = "select * from s_tran where s_date between'" + dateFrom + "' and '" + dateTo + "';";
//                sql = "select * from s_tran where r_total<>r_nettotal and s_date between'" + dateFrom + "' and '" + dateTo + "';";
            }

            ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql);
            while (rs.next()) {
                S_TranT_SaleBean sbean = new S_TranT_SaleBean();
                sbean.setR_Refno(rs.getString("R_Refno"));
                sbean.setR_Date(rs.getString("R_Date"));
                sbean.setR_Time(rs.getString("R_Time"));
                sbean.setMacno(rs.getString("MacNo"));
                sbean.setCashier(rs.getString("Cashier"));
                sbean.setR_EMP(rs.getString("R_EMP"));
                sbean.setR_PluCode(rs.getString("R_PluCode"));
                sbean.setR_Total(rs.getDouble("R_Total"));
                sbean.setR_Nettotal(rs.getDouble("R_Nettotal"));
                sbean.setR_Refund(rs.getString("R_Refund"));
                sbean.setDiscount(sbean.getR_Total() - sbean.getR_Nettotal());
                list.add(sbean);
            }
            MSG.NOTICE("ListSize= " + list.size());
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    String sqlUpdate = "update stcard_tocolo set "
                            + "discount='" + list.get(i).getDiscount() + "' " + ",refno='" + list.get(i).getR_Refno() + "' ,emp='" + list.get(i).getR_EMP() + "',cashier='" + list.get(i).getCashier() + "' "
                            + "where s_date='" + list.get(i).getR_Date() + "' and s_no='" + list.get(i).getMacno() + "-" + list.get(i).getR_Time() + "' "
                            + "and s_pcode='" + list.get(i).getR_PluCode() + "' and s_user='" + list.get(i).getCashier() + "' and s_rem='SAL' ";
                    String sqlUpdateServer = "update stcard_tocolo set "
                            + "discount='" + list.get(i).getDiscount() + "' " + ",refno='" + list.get(i).getR_Refno() + "' ,emp='" + list.get(i).getR_EMP() + "',cashier='" + list.get(i).getCashier() + "' "
                            + "where s_date='" + list.get(i).getR_Date() + "' and s_no='" + list.get(i).getMacno() + "-" + list.get(i).getR_Time() + "' "
                            + "and s_pcode='" + list.get(i).getR_PluCode() + "' and s_user='" + list.get(i).getCashier() + "' and s_rem='SAL' "
                            + "and s_bran='" + branch + "' ";
                    mysql.getConnection().createStatement().executeUpdate(sqlUpdate);
                    System.out.println("round List =" + i);
                    System.out.println(list.get(i).getR_Refno() + " Discount = " + list.get(i).getDiscount() + " S_NO = " + list.get(i).getMacno() + "-" + list.get(i).getR_Time());
//                    System.out.println(sqlUpdate);
                }
            }
            rs.close();
        } catch (Exception e) {
            MSG.NOTICE(e.toString());
            System.out.println(e.toString());
        } finally {
            mysql.close();
        }

    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MathDiscountFromDb mathDiscount = new MathDiscountFromDb();
                mathDiscount.mathDiscount("U01", "2024-01-01", "2025-12-31");
            }
        });
    }
}
