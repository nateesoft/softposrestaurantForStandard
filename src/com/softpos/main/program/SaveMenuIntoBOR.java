/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softpos.main.program;

import com.softpos.crm.pos.core.controller.sendMenuButttonToBorController;
import com.softpos.crm.pos.core.modal.sendMgrButtonToBorBean;
import com.softpos.crm.pos.core.modal.sendSoft_MenustupBean;
import com.softpos.floorplan.FloorPlanDialog;
import database.SQLServerConnect;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import util.AppLogUtil;
import util.MSG;

/**
 *
 * @author Dell
 */
public class SaveMenuIntoBOR extends javax.swing.JDialog {

    /**
     * Creates new form NewJDialog
     */
    public SaveMenuIntoBOR(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                jTextField1.setText("");
                processSendToBor();
            } catch (Exception e) {
                MSG.NOTICE(e.toString());
            }
            }
        }).start();
    }

    public void processSendToBor() throws ClassNotFoundException, SQLException, Exception {
        sendMenuButttonToBorController sendBor = new sendMenuButttonToBorController();
        ArrayList<sendMgrButtonToBorBean> list = sendBor.sendMGRButtonSetupToBor();
        ArrayList<sendSoft_MenustupBean> list1 = sendBor.sendDataSoft_menusetupToBor();
        int sizeMGRButton = list.size();
        int sizeSoftMenusetup = list1.size();
        SQLServerConnect SV = new SQLServerConnect();
        try {

            if (sizeMGRButton > 0) {
                String SVDel = "delete from mgrbuttonsetup;";
                SV.getUpdate(SVDel);
                Thread.sleep(900);
                for (int i = 0; i < list.size(); i++) {
                    jTextField1.setText("ส่งข้อมูล MGRButtonSetup : " + (i + 1));
                    checkUpdate();
                    String SqlINSMgrButton = "INSERT INTO mgrbuttonsetup "
                            + "(pcode, pdesc, sd_pcode, sd_pdesc, ex_pcode,"
                            + " ex_pdesc, ex_uncode, ex_undesc, auto_pcode, auto_pdesc,"
                            + " Check_before, Check_qty, qty, check_autoadd, Check_Extra) "
                            + "VALUES ("
                            + "'" + list.get(i).getPcode() + "', '" + list.get(i).getPdesc() + "', '" + list.get(i).getSd_pcode() + "', '" + list.get(i).getSd_pdesc() + "', '" + list.get(i).getEx_pcode() + "',"
                            + " '" + list.get(i).getPdesc() + "', '" + list.get(i).getEx_uncode() + "', '" + list.get(i).getEx_undesc() + "', '" + list.get(i).getAuto_pcode() + "', '" + list.get(i).getAuto_pdesc() + "',"
                            + " '" + list.get(i).getCheck_before() + "', '" + list.get(i).getCheck_qty() + "', '" + list.get(i).getQty() + "', '" + list.get(i).getCheck_autoadd() + "', '" + list.get(i).getCheck_Extra() + "')";
                    SV.getUpdate(SqlINSMgrButton);
                }
            }
            if (sizeSoftMenusetup > 0) {
                String SVDel = "delete from soft_menusetup;";
                SV.getUpdate(SVDel);
                Thread.sleep(900);
                for (int i = 0; i < list1.size(); i++) {
                    jTextField1.setText("ส่งข้อมูล Soft_Menusetup : " + (i + 1));
                    checkUpdate();
                    String str[] = list1.get(i).getImg().split("/");
                    String IMG = "";
                    for (String img : str) {
                        IMG = img;
                    }
                    String INSSoft_Menusetup = "INSERT INTO soft_menusetup ("
                            + "MenuCode, MenuType, OptSet, PSet, PCode,"
                            + " MenuShowText, IMG, FontColor, BGColor, Layout,"
                            + " FontSize, FontName, FontAttr, M_Index, IMG_SIZE) "
                            + "VALUES ("
                            + "'" + list1.get(i).getMenuCode() + "', '" + list1.get(i).getMenuType() + "', '" + list1.get(i).getOptSet() + "', '" + list1.get(i).getpSet() + "', '" + list1.get(i).getpCode() + "',"
                            + " '" + list1.get(i).getMenuShowText() + "', '" + IMG + "', '" + list1.get(i).getFontColor() + "', '" + list1.get(i).getBgColor() + "', '" + list1.get(i).getLayout() + "',"
                            + " '" + list1.get(i).getFontSize() + "', '" + list1.get(i).getFontName() + "', '" + list1.get(i).getFontAttr() + "', '" + list1.get(i).getM_Index() + "', '" + list1.get(i).getiMG_Size() + "')";
                    SV.getUpdate(INSSoft_Menusetup);
                }
            }

            pbCheckUpdate.setForeground(Color.green);
            MSG.NOTICE("รับส่งข้อมูลเมนูไปยัง BOR เรียบร้อย");
            this.setVisible(false);
        } catch (Exception e) {
            MSG.NOTICE(e.toString());
            AppLogUtil.log(FloorPlanDialog.class, "error", e);
        } finally {
            
        }

    }

    private void checkUpdate() {
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
                    pbCheckUpdate.setString("Check Update: (" + i + " %)");
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        MSG.NOTICE(e.toString());
                    }
                }
                pbCheckUpdate.setString("Update Menu To BOR Complete!");
            } catch (Exception e) {
                MSG.NOTICE(e.toString());
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

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        pbCheckUpdate = new javax.swing.JProgressBar();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tranfer.jpg"))); // NOI18N

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField1.setText("jTextField1");

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Exit (X)");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pbCheckUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pbCheckUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
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
            java.util.logging.Logger.getLogger(SaveMenuIntoBOR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SaveMenuIntoBOR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SaveMenuIntoBOR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SaveMenuIntoBOR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SaveMenuIntoBOR dialog = new SaveMenuIntoBOR(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JProgressBar pbCheckUpdate;
    // End of variables declaration//GEN-END:variables
}
