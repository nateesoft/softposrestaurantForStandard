/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softpos.main.program;

import com.softpos.pos.core.controller.BalanceControl;
import com.softpos.pos.core.controller.ThaiUtil;
import com.softpos.pos.core.model.BalanceBean;
import database.ConfigFile;
import database.MySQLConnect;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.sql.ResultSet;
import javax.swing.JFrame;
import printReport.PrintDriver;
import util.AppLogUtil;
import util.DateConvert;
import util.MSG;

/**
 *
 * @author Administrator
 */
public class UrgentFoodLoopCheck extends javax.swing.JFrame {

    /**
     * Creates new form UrgentFoodLoopCheck
     */
    public static boolean trickSound = false;
    public static String printerName = "";
    public static String printerConfigDriver = ConfigFile.getProperties("printdriver");
    public static String stationKicNo = ConfigFile.getProperties("stationKicNo");
    public static DateConvert dc = new DateConvert();
//    public static MySQLConnect mysql = new MySQLConnect();
    double countTime = 1;
    double countTime1 = 1;

    public UrgentFoodLoopCheck() {
        try {
            Thread.sleep(3600 * 3);
        } catch (Exception e) {
        }
        initComponents();
        new Thread(new Runnable() {
            @Override
            public void run() {
                setState(JFrame.ICONIFIED);
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                jLabel1.setText("Version 2.1: 20/05/2568.18:36");

                setState(JFrame.ICONIFIED);

                printerName = ConfigFile.getProperties("printerName");
                jLabel4.setText("Printer Active = " + printerName);
//                for (int i = 0; i < 10; i++) {
                getDataFromKicTran();
                System.out.println("Into Method UrgentFoodLoopCheck() " + countTime + this.getClass());
                if (trickSound == true) {
                    for (int a = 0; a < 10; a++) {
                        playSound();
                        try {
                            Thread.sleep(6300 * 4);
                        } catch (Exception e) {
                        }
                    }
                    trickSound = false;
                }
//                    try {
//                        Thread.sleep(900 * 5);
//                    } catch (Exception e) {
//                    }
//                    if (i == 9) {
//                        i = 0;
//                    }
            }

//            }
        }).start();
        if (printerConfigDriver.equals("true")) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        countTime1++;
                        printCheckItOut();
                        jLabel3.setText("UrgentFoodLoopCheck : Loop LEVEL " + countTime1);
                        try {
                            Thread.sleep(900 * 5);
                        } catch (Exception e) {
                        }
                        if (i == 9) {
                            i = 0;
                        }
                    }

                }
            }).start();
        }
    }

    public void getDataFromKicTran() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {

        try {

            for (int i = 0; i < 10; i++) {
                countTime++;
                jLabel2.setText("getDataFromKictran : Loop LEVEL " + countTime);
                System.out.println("Into Method getDataFromKictran() " + countTime + this.getClass());
                String sql = "select * from kictran "
                        + "where "
                        + "PFlage='N' "
                        + "and R_AlertKitchen='N' "
                        + "and R_FoodUrgent='Y' "
                        + "and pkic='" + stationKicNo + "' "
                        + "limit 1;";
                MySQLConnect mysql = new MySQLConnect();
                try {
                    mysql.open(this.getClass());
                    ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql);
                    if (rs.next() && !rs.wasNull()) {
                        trickSound = true;
                        String tableNo = rs.getString(ThaiUtil.ASCII2Unicode("PTable"));
                        String pdesc = rs.getString(ThaiUtil.ASCII2Unicode("R_UrgentFoodItemName"));
                        String pcode = rs.getString("pcode");
                        String pindex = rs.getString(ThaiUtil.ASCII2Unicode("pindex"));

                        if (pdesc.equals("") || pdesc == null || pdesc.equals("null")) {
                            UrgentFoodDisplay display = new UrgentFoodDisplay(tableNo, "", "ตามทั้งโต๊ะ", "");
                            display.setVisible(true);
                        } else {
                            UrgentFoodDisplay display = new UrgentFoodDisplay(tableNo, pcode, pdesc, pindex);
                            display.setVisible(true);
                        }
                    } else {
                        trickSound = false;
                    }

                    rs.close();

                } catch (Exception e) {
                    AppLogUtil.log(UrgentFoodLoopCheck.class, "error", e);
                    MSG.NOTICE(e.toString());
                } finally {
                    mysql.closeConnection(this.getClass());
                }
                try {

                    Thread.sleep(1200);
                } catch (Exception e) {
                }
                if (i > 7) {
                    i = 0;
                }
            }
        } catch (Exception e) {
            AppLogUtil.log(UrgentFoodLoopCheck.class, "error", e);
            System.out.println(e.toString());
        }

    }
//        }).start();
//    }

    public static void playSound() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.default");
                if (runnable != null) {
                    System.out.println("Into Method playSound() " + this.getClass());
                    runnable.run();
                }
            }
        }).start();
    }

    public void printCheckItOut() {
        MySQLConnect mysql = new MySQLConnect();
        try {
//            Thread.sleep(200);

            System.out.println("Into Method printCheckItOut() ");
            String sql = "select * from kictran "
                    + "where "
                    + "PFlage='Y' "
                    + "and PServe='Y' "
                    + "and R_PrintCheckOut='N' "
                    + "and pkic='" + stationKicNo + "' "
                    + "limit 1;";
            mysql.open(this.getClass());
            countTime1++;
            jLabel2.setText("getDataFromKictran : Loop LEVEL " + countTime1);
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                String textToPrint = "";
                String pTable = "";
                String pIndex = "";
                String pcode = "";

                if (rs.next()) {
                    System.out.println("Printing PrictCheckItOut");
                    System.out.println(rs.getString("PCode"));
                    pcode = rs.getString("PCode");
                    pIndex = rs.getString("PIndex");
                    pTable = rs.getString("PTable");
                    BalanceBean bean = new BalanceBean();
                    BalanceControl bl = new BalanceControl();
                    bean = bl.getBalanceIndex(pTable, pIndex);
                    if (bean != null) {
                        int pQty = rs.getInt("PQty");
                        String pEtd = rs.getString("PEtd");
                        textToPrint += "โตีะ : " + pTable + "_";
                        if (pEtd.equals("E")) {
                            pEtd = "ทานในร้าน";
                        } else if (pEtd.equals("T")) {
                            pEtd = "ห่อกลับ";
                        } else if (pEtd.equals("D")) {
                            pEtd = "Delivery";
                        } else if (pEtd.equals("P")) {
                            pEtd = "Pinto";
                        } else if (pEtd.equals("W")) {
                            pEtd = "Whole Sale";
                        }
                        textToPrint += "colspan=3 align=center><font face=Angsana New size=5> " + "โต๊ะ : " + pTable + "_";
                        textToPrint += "colspan=3 align=center><font face=Angsana New size=3> " + "*** : " + pEtd + " ***_";
                        textToPrint += "colspan=3 align=left><font face=Angsana New size=3> " + "จำนวน : " + pQty + "_";
                        textToPrint += "colspan=3 align=left><font face=Angsana New size=3> " + pcode + " # " + bean.getR_PName() + "_";
                        textToPrint += "colspan=3 align=left><font face=Angsana New size=3> " + dc.dateGetToShow(dc.GetCurrentDate()) + " เวลา " + dc.GetCurrentTime() + "_";
                        PrintDriver printDriver = new PrintDriver();
                        String[] strs = textToPrint.split("_");
                        for (String data1 : strs) {
                            printDriver.addTextIFont(data1);
                            try {
                                Thread.sleep(100);
                            } catch (Exception e) {
                            }
                        }
                        try {
                            if (printerConfigDriver.equals("true")) {
                                printDriver.printHTMLKitChenByKictran(printerName);
                                jLabel4.setText("Printing : " + printerName + " / printCheckItOut " + bean.getR_Table() + " index =  " + bean.getR_Index());
                                Thread.sleep(900);
                            }

                        } catch (Exception e) {
                            AppLogUtil.log(UrgentFoodLoopCheck.class, "error", e);
                            MSG.ERR(e.getMessage());
                        }
                        try {
                            String sqlUpdate = "update kictran set "
                                    + "R_PrintCheckOut='Y' "
                                    + "where ptable='" + pTable + "' "
                                    + "and pindex='" + pIndex + "' "
                                    + "and pcode='" + pcode + "' "
                                    + "and R_PrintCheckOut='N' ;";
                            mysql.getConnection().createStatement().executeUpdate(sqlUpdate);
                            Thread.sleep(100);
                        } catch (Exception e) {
                            AppLogUtil.log(UrgentFoodLoopCheck.class, "error", e);
                            MSG.NOTICE(e.toString());
                        }

                    } else {
                        try {
                            String sqlBalanceisNull = "update kictran "
                                    + "set "
                                    + "R_PrintCheckOut='Y' "
                                    + "where pindex='" + pIndex + "' "
                                    + "and ptable='" + pTable + "' "
                                    + "and pcode='" + pcode + "';";
                            System.out.println(sqlBalanceisNull);
                            mysql.getConnection().createStatement().executeUpdate(sqlBalanceisNull);
                        } catch (Exception e) {
                            System.out.println(e.toString());
                            MSG.NOTICE(e.toString());
                            AppLogUtil.log(UrgentFoodLoopCheck.class, "error", e);
                        }

                    }

                }
                rs.close();
//                Thread.sleep(900);
            }
        } catch (Exception e) {
            MSG.NOTICE(e.toString());
        } finally {
            mysql.closeConnection(this.getClass());
        }

    }

    public void printUrgentLog() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MySQLConnect mysql = new MySQLConnect();
                DateConvert dc = new DateConvert();
                try {
                    String textToPrint = "";
                    String sql = "select * from kictran_urgentClick where pdate='" + dc.GetCurrentDate() + "';";
                    mysql.open(this.getClass());
                    ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql);
                    int i = 0;
                    while (rs.next()) {
                        textToPrint += "colspan=3 align=center><font face=Angsana New size=3> " + "โต๊ะ : " + rs.getString("pTable") + " # " + dc.dateGetToShow(rs.getString("pDate")) + " เวลา " + rs.getString("pTime") + "_";
                        i++;
                    }
                    textToPrint += "colspan=3 align=left><font face=Angsana New size=3> " + "รวมทั้งสิ้นวันนี้ :  " + i + " ครั้ง " + "_";
                    PrintDriver printDriver = new PrintDriver();
                    String[] strs = textToPrint.split("_");
                    for (String data1 : strs) {
                        printDriver.addTextIFont(data1);
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                        }
                    }
                    try {
                        Thread.sleep(100);
                        if (printerConfigDriver.equals("true")) {
                            printDriver.printHTMLKitChenByKictran(ConfigFile.getProperties("printerName"));
                        }

                    } catch (Exception e) {
                        System.err.println(e.toString());
                        AppLogUtil.log(UrgentFoodLoopCheck.class, "error", e);
                        MSG.ERR(e.toString());
                    }
                    rs.close();
                    mysql.close();
                } catch (Exception e) {
                    AppLogUtil.log(UrgentFoodLoopCheck.class, "error", e);
                    MSG.NOTICE(e.toString());
                } finally {
                    mysql.closeConnection(this.getClass());
                }

            }
        }).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/urgent.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setText("Exit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton3.setText("พิมพ์ รายงานการตามอาหาร");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setText("jLabel1");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("jLabel1");

        jLabel3.setForeground(new java.awt.Color(51, 51, 255));
        jLabel3.setText("jLabel1");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel4.setForeground(new java.awt.Color(255, 102, 0));
        jLabel4.setText("jLabel4");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 637, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(133, 133, 133)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 728, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (MSG.CONF(this, "ยืนยันการออกจากระบบการขาย (Logoff User) ? ")) {
            System.exit(0);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        printUrgentLog();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UrgentFoodLoopCheck.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UrgentFoodLoopCheck.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UrgentFoodLoopCheck.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UrgentFoodLoopCheck.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UrgentFoodLoopCheck urgen = new UrgentFoodLoopCheck();
                urgen.setVisible(true);
//                urgen.dispose();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
