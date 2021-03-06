/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softpos.main.program;

import com.softpos.pos.core.controller.BranchControl;
import com.softpos.pos.core.controller.PublicVar;
import com.softpos.pos.core.controller.Value;
import com.softpos.pos.core.model.BranchBean;
import database.ConfigFile;
import database.MySQLConnect;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import printReport.PrintSimpleForm;
import sun.natee.project.util.ThaiUtil;
import util.MSG;

/**
 *
 * @author Dell-Softpos
 */
public class PrintToKic extends javax.swing.JFrame {

    private String tableNo = "";
    private int refresh = 30;
    private boolean kicPrintting = false;
    boolean printkic = false;

    /**
     * Creates new form PrintToKic
     */
    public PrintToKic(java.awt.Frame parent, boolean modal) {
////        super(parent, modal);
        initComponents();
        MySQLConnect.getDbVar();
        MySQLConnect mysql = new MySQLConnect();
        BranchBean BranchBean = new BranchBean();
        BranchBean = BranchControl.getData();
        try {
            String sqlGetSaveOrder = "select SaveOrder from branch";
            mysql.open();
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rsGetSaveOrderConfig =stmt.executeQuery(sqlGetSaveOrder);
            if (rsGetSaveOrderConfig.next() && !rsGetSaveOrderConfig.wasNull()) {
                String config = rsGetSaveOrderConfig.getString("SaveOrder");
                if (!config.equals("N")) {
                    PublicVar.Branch_Saveorder = config;
                }
            }
            
            rsGetSaveOrderConfig.close();
            stmt.close();
            mysql.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        printkic = Boolean.parseBoolean(ConfigFile.getProperties("printkic"));
        lblProcessShow.setText("???????????????????????????????????????");
        new Thread(new Runnable() {
            @Override
            public void run() {
                setState(JFrame.ICONIFIED);
                for (int i = 0; i < 10; i++) {
                    //setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    if (i == 9) {
                        i = 0;
                    }
                    if (printkic == true) {
                        System.out.println("PROCESS " + refresh + "sec/" + i);
                        kicPrintFromPDA();
                        try {
                            Thread.sleep(refresh * 700);
//                        Thread.sleep(refresh);
                        } catch (InterruptedException ex) {
                            MSG.ERR(ex.toString());
                        }
                    }
//                    if (ConfigFile.getProperties("printerStation").equals("true")) {
//
//                    }
                }
            }
        }).start();
    }

    private void loadStatus() {

        new Thread(new Runnable() {

            @Override
            public void run() {
                //check ftp file date
                try {
                    pbCheckUpdate.setStringPainted(true);
                    pbCheckUpdate.setMinimum(0);
                    pbCheckUpdate.setMaximum(100);
                    for (int i = 1; i <= 100; i++) {
                        pbCheckUpdate.setValue(i);
                        pbCheckUpdate.setString("LOADDING Data: (" + i + " %)");
                        try {
                            Thread.sleep(25);
                        } catch (InterruptedException e) {
                            MSG.ERR(e.toString());
                        }
                    }

                    pbCheckUpdate.setString("Load data Complete ");
                    Thread.sleep(25);
                    for (int i = 100; i >= 0; i--) {
                        pbCheckUpdate.setValue(i);
                        pbCheckUpdate.setString("LOADDING Data: (" + i + " %)");
                        try {
                            Thread.sleep(25);
                        } catch (InterruptedException e) {
                            MSG.ERR(e.toString());
                        }
                    }
                } catch (Exception e) {
                    MSG.ERR(e.toString());
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

        jPanel1 = new javax.swing.JPanel();
        pbCheckUpdate = new javax.swing.JProgressBar();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lblProcessShow = new javax.swing.JLabel();

        setTitle("Print To Kic @Softpos");
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        pbCheckUpdate.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Print_Button.jpg"))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 153, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("PROCESSING: PDA-Document @ Softpos Java 10:14 20220331");

        jButton1.setBackground(new java.awt.Color(204, 0, 0));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("-");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(204, 0, 0));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("X");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        lblProcessShow.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblProcessShow.setText("jLabel3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(pbCheckUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 699, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblProcessShow, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pbCheckUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblProcessShow, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 714, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (MSG.CONF(this, "???????????????????????????????????????????????????????????? Print PDA ?????????????????????")) {
            System.exit(0);
        } else {
            this.dispose();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public void kicPrintFromPDA() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (kicPrintting == false) {
MySQLConnect mysql = new MySQLConnect();
                    try {
                        String sql = "select "
                                //                                                                                + "b.*,"
                                + "r_table,"
                                + "sum(b.r_quan) qty,"
                                + "sum(b.r_total) total"
                                + " from balance b "
                                + "where trantype='PDA' "
                                + "and r_kicprint<>'P' "
                                + "and r_void<>'V' "
                                + "and r_kic<>'0' "
                                + "and r_printOK='Y' "
                                + "and r_pause='P' "
                                + "group by r_table "
                                + "order by r_etd,r_index;";
                        mysql.open();
                        ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql);
                        if (rs.next() && !rs.wasNull() && kicPrintting == false) {
                            kicPrintting = true;
                            Thread.sleep(700);
                            tableNo = ThaiUtil.Unicode2ASCII(rs.getString("r_table"));
                            kichenPrint(ThaiUtil.ASCII2Unicode(tableNo));
                            lblProcessShow.setText("???????????????????????????????????????????????? ???????????? : " + rs.getString("r_table"));
                        }
                        kicPrintting = false;
                        rs.close();
                    } catch (SQLException e) {
                        MSG.ERR(e.toString());
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PrintToKic.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        mysql.close();
                    }

//                    try {
//                        String sqlDel = "delete from balance where r_table='';";
//                        mysql.open();
//                        mysql.getConnection().createStatement().executeUpdate(sqlDel);
//                    } catch (SQLException e) {
//                        MSG.ERR(e.getMessage());
//                    } finally {
//                        mysql.close();
//                    }
                }
            }
        }).start();

    }

    private void kichenPrint(String tableNo) {
        PrintSimpleForm printSimpleForm = new PrintSimpleForm();
        try {
            String printerName;
            String[] kicMaster = BranchControl.getKicData20();
            // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????
            String sqlShowKic = "select r_kic,r_etd from balance "
                    + "where r_table='" + tableNo + "' "
                    + "and R_PrintOK='Y' "
                    + "and R_KicPrint<>'P' "
                    + "and R_Kic<>'0' "
                    + "and R_Void<>'V' "
                    + "group by r_kic,r_etd "
                    + "order by r_kic, r_etd";
            /**
             * * OPEN CONNECTION **
             */
            MySQLConnect mysql = new MySQLConnect();
            mysql.open();

            try {

                Statement stmt1 = mysql.getConnection().createStatement();
                ResultSet rsKic = stmt1.executeQuery(sqlShowKic);

                if (!PublicVar.Branch_Saveorder.equals("N")) {
                    printSimpleForm.KIC_FORM_SaveOrder("", "SaveOrder", tableNo, 0);
                }
                //?????????????????????????????????????????????????????????????????????????????????????????????
                while (rsKic.next()) {
                    kicPrintting = true;
                    loadStatus();
                    String rKic = rsKic.getString("r_kic");
                    if (!rKic.equals("")) {
                        try {
                            int iKic = Integer.parseInt(rKic);
                            if (iKic - 1 < 0) {
                                //????????????????????? iKic=0 ????????????????????????????????????????????????????????????????????????????????????????????????
                            } else {
                                if (kicMaster[iKic - 1].equals("N")) {
                                    //NOT PRINT or Print already
                                } else {
//                                    printerName = rKic;
                                    printerName = "kic" + rKic;
                                    String printerForm = BranchControl.getForm(rKic);
                                    if (printerForm.equals("1")) {
                                        String sql1 = "select * from balance "
                                                + "where r_table='" + tableNo + "' "
                                                + "and R_PrintOK='Y' "
                                                + "and R_KicPrint<>'P' "
                                                + "and R_Kic<>'' "
                                                + "and R_Void<>'V' "
                                                + "and R_kic='" + rKic + "' "
                                                + "group by r_plucode";
                                        printerName = "kic" + rKic;
                                        Statement stmt2 = mysql.getConnection().createStatement();
                                        ResultSet rs1 = stmt2.executeQuery(sql1);
                                        while (rs1.next()) {
                                            String PCode = rs1.getString("R_PluCode");
                                            if (printerForm.equals("1")) {
                                                if (Value.printkic) {
                                                    printSimpleForm.KIC_FORM_1(printerName, tableNo, new String[]{PCode});
                                                }
                                            }
                                        }
                                        rs1.close();
                                        stmt2.close();
//                                    } 

                                    } else if (printerForm.equals("6")) {
                                        String sql2 = "select * from balance "
                                                + "where r_table='" + tableNo + "' "
                                                + "and R_PrintOK='Y' "
                                                + "and R_KicPrint<>'P' "
                                                + "and R_Kic<>'' "
                                                + "and R_Void<>'V' "
                                                + "and R_Kic='" + rKic + "'";
                                        Statement stmt2 = mysql.getConnection().createStatement();
                                        ResultSet rs2 = stmt2.executeQuery(sql2);
                                        while (rs2.next()) {
                                            kicPrintting = true;
                                            if (Value.printkic) {
                                                double qty = rs2.getDouble("R_Quan");
                                                double total = rs2.getDouble("R_Total");
                                                String r_plucode = rs2.getString("R_Plucode");
                                                String R_Index = rs2.getString("R_Index");
                                                printSimpleForm.KIC_FORM_6(printerName, tableNo, R_Index, r_plucode, qty, total);
//                                                printBillCheck();
                                            }
                                        }
                                        rs2.close();
                                        stmt2.close();
                                    } else if (printerForm.equals("3") || printerForm.equals("4") || printerForm.equals("5")) {

                                        if (printerForm.equals("3")) {
                                            if (Value.printkic) {
                                                String retd = rsKic.getString("r_etd");
                                                printSimpleForm.KIC_FORM_3New(printerName, tableNo, iKic, retd, "PDA");
//                                                String CheckBillBeforeCash = CONFIG.getP_CheckBillBeforCash();
//                                                if (CheckBillBeforeCash.equals("Y")) {
//                                                    printBillVoidCheck();
//                                                }
                                            }
                                        } else if (printerForm.equals("4")) {
                                            if (Value.printkic) {
                                                printSimpleForm.KIC_FORM_4(printerName, tableNo);
//                                                printBillVoidCheck();
                                            }
                                        } else if (printerForm.equals("5")) {
                                            if (Value.printkic) {
                                                printSimpleForm.KIC_FORM_5(printerName, tableNo);
//                                                printBillVoidCheck();
                                            }
                                        }

                                    } else if (printerForm.equals("7") || printerForm.equals("2")) {
                                        if (Value.printkic) {
                                            printSimpleForm.KIC_FORM_7(printerName, tableNo);
                                        }
                                    } else {
//                                        printBillVoidCheck();
                                        MSG.ERR(this, "????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????? !!!");
                                    }
                                }
                            }
                        } catch (SQLException e) {
                            MSG.ERR(this, e.getMessage());
                        }
                    }
                }
                rsKic.close();
                stmt1.close();
                //update r_kicprint
                try {
                    String sql = "update balance "
                            + "set r_kicprint='P',"
                            + "r_pause='Y' "
                            + "where r_table='" + tableNo + "' "
                            + "and r_kicprint<>'P' "
                            + "and r_printOk='Y' "
                            + "and r_kic<>'';";
                    Statement stmt = mysql.getConnection().createStatement();
                    stmt.executeUpdate(sql);
                } catch (SQLException e) {
                    MSG.ERR(this, e.getMessage());
                }
                kicPrintting = false;
            } catch (SQLException e) {
                MSG.ERR(null, e.getMessage());
            } finally {
                mysql.close();
            }
        } catch (HeadlessException e) {
            MSG.ERR(null, e.getMessage());
        }
    }

    public void printCheckBillStation() {
        MySQLConnect mysql = new MySQLConnect();
        try {
            String sql = "select * form balance "
                    + "wehre r_table='" + tableNo + "' "
                    + "and PDAPrintCheckStation <>'N' ";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                if (rs.next()) {
                    MySQLConnect mysql2 = new MySQLConnect();
                    try {
                        String sqlUpdate = "update balance set PDAPrintCheckStation='N' where r_table='" + tableNo + "';";
                        mysql2.getConnection().createStatement().executeUpdate(sqlUpdate);
                    } catch (SQLException e) {
                        MSG.ERR(e.getMessage());
                    } finally {
                        mysql2.close();
                    }
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
        } finally {
            mysql.close();
        }
    }

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
            java.util.logging.Logger.getLogger(PrintToKic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrintToKic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrintToKic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrintToKic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PrintToKic dialog = new PrintToKic(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblProcessShow;
    private javax.swing.JProgressBar pbCheckUpdate;
    // End of variables declaration//GEN-END:variables
}
