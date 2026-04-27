/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softpos.main.program;

import printReport.PrintKicFormReport;
import database.ConfigFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.MSG;

/**
 *
 * @author Dell
 */
public class printKicReport {

    private String tableNo = "";
    private String PrinterName = "";
    private String Macno = "";

    public void printKicReport(String tableNo, String PrinterName, String Macno, final String r_etd, final String plucode) {
        this.tableNo = tableNo;
        this.PrinterName = PrinterName;
        this.Macno = Macno;
        if (ConfigFile.getProperties("printerStation").equals("true")) {
//            new Thread(new Runnable() {
//
//                @Override
//                public void run() {
                    printProcess(r_etd, plucode);
//                }
//            }).start();
        } else {
            MSG.NOTICE("ไม่ได้กำหนดให้ printerStation ใช้งาน");
            System.out.print("ไม่ได้กำหนดให้ printerStation ใช้งาน \nกรุณาชำระเงินที่แคชเชียร์");
        }

    }

    private void printProcess(String r_etd, String plucode) {
//        BalanceControl bl = new BalanceControl();
//        ArrayList<BalanceBean> beanData = bl.getAllBalance(tableNo);
//        TableFileControl tbControl = new TableFileControl();
//        TableFileBean tb = tbControl.getData(tableNo);

        try {
            PrintKicFormReport print = new PrintKicFormReport();
            print.PrintKicForm7_Report(tableNo, PrinterName, Macno, r_etd, plucode);
        } catch (Exception ex) {
            Logger.getLogger(printKicReport.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//    public static void main(String[] args) {
//        printCheckBillStation printCheckBill = new printCheckBillStation();
//        printCheckBill.printCheckBillStation("901","Station2");
//    }
}
