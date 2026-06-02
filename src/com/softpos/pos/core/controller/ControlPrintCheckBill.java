/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softpos.pos.core.controller;

import com.softpos.util.AppLogUtil;
import com.softpos.util.ThaiUtil;
import database.MySQLConnect;

/**
 *
 * @author Dell-Softpos
 */
public class ControlPrintCheckBill {

    private final MySQLConnect mysqlConnect = new MySQLConnect();
    
    public void PrintCheckBill(String tableNO, boolean CheckBill, String emp, String PrinterName, String Macno) {

        EmployControl empc = AppContext.getEmployControl();
        if (CheckBill == true) {
            emp = ThaiUtil.Unicode2ASCII(empc.empName(emp));
            
            try {
                mysqlConnect.open();
                String sql = "update balance set PDAPrintCheck='Y',pdaemp='" + ThaiUtil.Unicode2ASCII(emp) + "',PDAPrintCheckStation='" + PrinterName + "' "
                        + "where r_table='" + tableNO.toUpperCase() + "' "
                        + "and trantype ='PDA';";
                mysqlConnect.executeUpdate(sql);
            } catch (Exception e) {
                System.out.println(e.toString());
                AppLogUtil.log(ControlPrintCheckBill.class, "error", e);
            } finally {
                mysqlConnect.close();
            }
        }
    }

    public void setPrintCheckBillItemAfterSendKic(String tableNO) {
        try {
            String sql = "update balance set PDAPrintChekItemStation='Y' "
                    + "where PDAPrintChekItemStation='N' and r_table='" + tableNO + "'";
            mysqlConnect.open();
            mysqlConnect.executeUpdate(sql);
        } catch (Exception e) {
            System.err.println(e.toString());
        } finally {
            mysqlConnect.close();
        }
    }

    public void PrintUrgentFood(String tableNO) {
        try {
            String sql = "update kictran set R_FoodUrgent='Y',R_AlertKitChen='Y' where PTable='" + tableNO + "' and PFlage='N';";
            mysqlConnect.open();
            mysqlConnect.executeUpdate(sql);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            mysqlConnect.close();
        }

    }
}
