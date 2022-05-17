package com.softpos.posreport;

import com.softpos.main.program.Jdi_depReport;
import com.softpos.pos.core.controller.PPrint;
import com.softpos.pos.core.controller.PUtility;
import com.softpos.pos.core.controller.PluRec;
import com.softpos.pos.core.controller.Value;
import database.MySQLConnect;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import util.AppLogUtil;
import util.MSG;

public class DeptRep extends javax.swing.JDialog {

    PPrint prn = new PPrint();

    public DeptRep(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        try {
            javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            }
        }
        initComponents();
        txtMacNo1.setText("001");
        txtMacNo2.setText("999");
        txtCashNo1.setText("0000");
        txtCashNo2.setText("9999");
        txtGroup1.setText("0000");
        txtGroup2.setText("9999");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        bntOK = new javax.swing.JButton();
        bntF1 = new javax.swing.JButton();
        bntExit = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMacNo1 = new javax.swing.JTextField();
        txtMacNo2 = new javax.swing.JTextField();
        txtCashNo1 = new javax.swing.JTextField();
        txtCashNo2 = new javax.swing.JTextField();
        txtGroup1 = new javax.swing.JTextField();
        txtGroup2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("รายงานการขายตามกลุ่มสินค้า (Department/Group Report)");
        setUndecorated(true);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        bntOK.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        bntOK.setText("F5- พิมพ์");
        bntOK.setFocusable(false);
        bntOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntOKActionPerformed(evt);
            }
        });

        bntF1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        bntF1.setText("F1- จอภาพ");
        bntF1.setFocusable(false);
        bntF1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntF1ActionPerformed(evt);
            }
        });

        bntExit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        bntExit.setText("ESC- ออก");
        bntExit.setFocusable(false);
        bntExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntExitActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray, 3));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("หมายเลขเครื่อง");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("รหัสพนักงาน");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("รหัสกลุ่มสินค้า");

        txtMacNo1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtMacNo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMacNo1KeyPressed(evt);
            }
        });

        txtMacNo2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtMacNo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMacNo2KeyPressed(evt);
            }
        });

        txtCashNo1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtCashNo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCashNo1KeyPressed(evt);
            }
        });

        txtCashNo2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtCashNo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCashNo2KeyPressed(evt);
            }
        });

        txtGroup1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtGroup1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtGroup1KeyPressed(evt);
            }
        });

        txtGroup2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtGroup2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtGroup2KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtGroup1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCashNo1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMacNo1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtGroup2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCashNo2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMacNo2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(82, 82, 82))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtMacNo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(txtMacNo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(txtCashNo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCashNo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(txtGroup1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtGroup2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(bntF1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bntOK, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bntExit, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bntExit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bntOK, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bntF1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

private void bntOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntOKActionPerformed
    bntOKClick();
}//GEN-LAST:event_bntOKActionPerformed

private void bntExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntExitActionPerformed
    bntExitClick();
}//GEN-LAST:event_bntExitActionPerformed

private void txtMacNo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMacNo1KeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
        bntExitClick();
    }
    if (evt.getKeyCode() == KeyEvent.VK_F5) {
        bntOKClick();
    }
    if (evt.getKeyCode() == KeyEvent.VK_F1) {
        bntViewClick();
    }
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

        txtMacNo2.requestFocus();

    }
}//GEN-LAST:event_txtMacNo1KeyPressed

private void txtMacNo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMacNo2KeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
        bntExitClick();
    }
    if (evt.getKeyCode() == KeyEvent.VK_F5) {
        bntOKClick();
    }
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        txtCashNo1.requestFocus();
    }
    if (evt.getKeyCode() == KeyEvent.VK_F1) {
        bntViewClick();
    }
}//GEN-LAST:event_txtMacNo2KeyPressed

private void txtCashNo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCashNo1KeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
        bntExitClick();
    }
    if (evt.getKeyCode() == KeyEvent.VK_F5) {
        bntOKClick();
    }
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        txtCashNo2.requestFocus();
    }
    if (evt.getKeyCode() == KeyEvent.VK_F1) {
        bntViewClick();
    }
}//GEN-LAST:event_txtCashNo1KeyPressed

private void txtCashNo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCashNo2KeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
        bntExitClick();
    }
    if (evt.getKeyCode() == KeyEvent.VK_F5) {
        bntOKClick();
    }
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        txtGroup1.requestFocus();
    }
    if (evt.getKeyCode() == KeyEvent.VK_F1) {
        bntViewClick();
    }
}//GEN-LAST:event_txtCashNo2KeyPressed

private void txtGroup1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGroup1KeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
        bntExitClick();
    }
    if (evt.getKeyCode() == KeyEvent.VK_F5) {
        bntOKClick();
    }
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        txtGroup2.requestFocus();
    }
    if (evt.getKeyCode() == KeyEvent.VK_F1) {
        bntViewClick();
    }
}//GEN-LAST:event_txtGroup1KeyPressed

private void txtGroup2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGroup2KeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
        bntExitClick();
    }
    if (evt.getKeyCode() == KeyEvent.VK_F5) {
        bntOKClick();
    }
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        txtMacNo1.requestFocus();
    }
    if (evt.getKeyCode() == KeyEvent.VK_F1) {
        bntViewClick();
    }
}//GEN-LAST:event_txtGroup2KeyPressed

private void bntF1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntF1ActionPerformed
    bntViewClick();
}//GEN-LAST:event_bntF1ActionPerformed
    public void bntViewClick() {
        String MacNo1 = txtMacNo1.getText();
        String MacNo2 = txtMacNo2.getText();
        String CashNo1 = txtCashNo1.getText();
        String CashNo2 = txtCashNo2.getText();
        String Group1 = txtGroup1.getText();
        String Group2 = txtGroup2.getText();
        String TempGroup;
        int ArraySize = 0;
        PluRec[] GArray;
        GArray = new PluRec[1];
        ArraySize = 0;

        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            Statement stmt = mysql.getConnection().createStatement();
            String SqlQuery = "select * from t_sale where (macno>='" + MacNo1 + "') and (macno<='" + MacNo2 + "') "
                    + "and (cashier>='" + CashNo1 + "') and (cashier<='" + CashNo2 + "') "
                    + "and (r_group>='" + Group1 + "') and (r_group<='" + Group2 + "') "
                    + "and (r_void<>'V') and (r_refund<>'V') Order by r_group";
            ResultSet rs = stmt.executeQuery(SqlQuery);
            TempGroup = "";
            Double SumEQty = 0.0;
            Double SumEAmt = 0.0;
            Double SumTQty = 0.0;
            Double SumTAmt = 0.0;
            Double SumDQty = 0.0;
            Double SumDAmt = 0.0;
            Double SumPQty = 0.0;
            Double SumPAmt = 0.0;
            Double SumWQty = 0.0;
            Double SumWAmt = 0.0;
            Double SumSQty = 0.0;
            Double SumSAmt = 0.0;
            PluRec GroupRec = new PluRec();
            GroupRec.MacNo1 = MacNo1;
            GroupRec.MacNo2 = MacNo2;
            GroupRec.Cashier1 = CashNo1;
            GroupRec.Cashier2 = CashNo1;
            GroupRec.Group1 = Group1;
            GroupRec.Group2 = Group1;
            GroupRec.Plu1 = "";
            GroupRec.Plu2 = "";
            GroupRec.GroupCode = TempGroup;
            GroupRec.GroupName = PUtility.SeekGroupName(TempGroup);
            GroupRec.PCode = "";
            GroupRec.PName = "";

            GroupRec.E_Qty = GroupRec.E_Qty + SumEQty;
            GroupRec.E_Amt = GroupRec.E_Amt + SumEAmt;

            GroupRec.T_Qty = GroupRec.T_Qty + SumTQty;
            GroupRec.T_Amt = GroupRec.T_Amt + SumTAmt;

            GroupRec.D_Qty = GroupRec.D_Qty + SumDQty;
            GroupRec.D_Amt = GroupRec.D_Amt + SumDAmt;

            GroupRec.P_Qty = GroupRec.P_Qty + SumPQty;
            GroupRec.P_Amt = GroupRec.P_Amt + SumPAmt;

            GroupRec.W_Qty = GroupRec.W_Qty + SumWQty;
            GroupRec.W_Amt = GroupRec.W_Amt + SumWAmt;

            GroupRec.S_Qty = GroupRec.S_Qty + SumSQty;
            GroupRec.S_Amt = GroupRec.S_Amt + SumSAmt;
            GArray[ArraySize] = GroupRec;

            while (rs.next()) {
                TempGroup = rs.getString("r_group");

                if (!TempGroup.equals(rs.getString("r_group"))) {
                    GroupRec = new PluRec();
                    GroupRec.MacNo1 = MacNo1;
                    GroupRec.MacNo2 = MacNo2;
                    GroupRec.Cashier1 = CashNo1;
                    GroupRec.Cashier2 = CashNo1;
                    GroupRec.Group1 = Group1;
                    GroupRec.Group2 = Group1;
                    GroupRec.Plu1 = "";
                    GroupRec.Plu2 = "";
                    GroupRec.GroupCode = TempGroup;
                    GroupRec.GroupName = PUtility.SeekGroupName(TempGroup);
                    GroupRec.PCode = "";
                    GroupRec.PName = "";

                    GroupRec.E_Qty = GroupRec.E_Qty + SumEQty;
                    GroupRec.E_Amt = GroupRec.E_Amt + SumEAmt;

                    GroupRec.T_Qty = GroupRec.T_Qty + SumTQty;
                    GroupRec.T_Amt = GroupRec.T_Amt + SumTAmt;

                    GroupRec.D_Qty = GroupRec.D_Qty + SumDQty;
                    GroupRec.D_Amt = GroupRec.D_Amt + SumDAmt;

                    GroupRec.P_Qty = GroupRec.P_Qty + SumPQty;
                    GroupRec.P_Amt = GroupRec.P_Amt + SumPAmt;

                    GroupRec.W_Qty = GroupRec.W_Qty + SumWQty;
                    GroupRec.W_Amt = GroupRec.W_Amt + SumWAmt;

                    GroupRec.S_Qty = GroupRec.S_Qty + SumSQty;
                    GroupRec.S_Amt = GroupRec.S_Amt + SumSAmt;
                    if (ArraySize == 0) {
                        GArray[ArraySize] = GroupRec;
                        ArraySize = GArray.length;
                    } else {
                        GArray = PUtility.addPluArray(GArray);
                        ArraySize = GArray.length;
                        GArray[ArraySize - 1] = GroupRec;
                    }
                    TempGroup = rs.getString("r_group");
                    SumEQty = 0.0;
                    SumEAmt = 0.0;
                    SumTQty = 0.0;

                    SumTAmt = 0.0;
                    SumDQty = 0.0;
                    SumDAmt = 0.0;
                    SumPQty = 0.0;
                    SumPAmt = 0.0;
                    SumWQty = 0.0;
                    SumWAmt = 0.0;
                    SumSQty = 0.0;
                    SumSAmt = 0.0;
                }
                if (rs.getString("r_etd").equals("E")) {
                    SumEQty = SumEQty + rs.getDouble("r_quan");
                    SumEAmt = SumEAmt + rs.getDouble("r_total");
                }
                if (rs.getString("r_etd").equals("T")) {
                    SumTQty = SumTQty + rs.getDouble("r_quan");
                    SumTAmt = SumTAmt + rs.getDouble("r_total");
                }
                if (rs.getString("r_etd").equals("D")) {
                    SumDQty = SumDQty + rs.getDouble("r_quan");
                    SumDAmt = SumDAmt + rs.getDouble("r_total");
                }
                if (rs.getString("r_etd").equals("P")) {
                    SumPQty = SumPQty + rs.getDouble("r_quan");
                    SumPAmt = SumPAmt + rs.getDouble("r_total");
                }
                if (rs.getString("r_etd").equals("W")) {
                    SumWQty = SumWQty + rs.getDouble("r_quan");
                    SumWAmt = SumWAmt + rs.getDouble("r_total");
                }
                SumSQty = SumSQty + rs.getDouble("r_quan");
                SumSAmt = SumSAmt + rs.getDouble("r_total");
            }
            if (SumSQty > 0) {
                GroupRec = new PluRec();
                GroupRec.MacNo1 = MacNo1;
                GroupRec.MacNo2 = MacNo2;
                GroupRec.Cashier1 = CashNo1;
                GroupRec.Cashier2 = CashNo1;
                GroupRec.Group1 = Group1;
                GroupRec.Group2 = Group1;
                GroupRec.Plu1 = "";
                GroupRec.Plu2 = "";
                GroupRec.GroupCode = TempGroup;
                GroupRec.GroupName = PUtility.SeekGroupName(TempGroup);
                GroupRec.PCode = "";
                GroupRec.PName = "";

                GroupRec.E_Qty = GroupRec.E_Qty + SumEQty;
                GroupRec.E_Amt = GroupRec.E_Amt + SumEAmt;

                GroupRec.T_Qty = GroupRec.T_Qty + SumTQty;
                GroupRec.T_Amt = GroupRec.T_Amt + SumTAmt;

                GroupRec.D_Qty = GroupRec.D_Qty + SumDQty;
                GroupRec.D_Amt = GroupRec.D_Amt + SumDAmt;

                GroupRec.P_Qty = GroupRec.P_Qty + SumPQty;
                GroupRec.P_Amt = GroupRec.P_Amt + SumPAmt;

                GroupRec.W_Qty = GroupRec.W_Qty + SumWQty;
                GroupRec.W_Amt = GroupRec.W_Amt + SumWAmt;

                GroupRec.S_Qty = GroupRec.S_Qty + SumSQty;
                GroupRec.S_Amt = GroupRec.S_Amt + SumSAmt;
                if (ArraySize == 0) {
                    GArray[ArraySize] = GroupRec;
                } else {
                    GArray = PUtility.addPluArray(GArray);
                    ArraySize = GArray.length;
                    GArray[ArraySize - 1] = GroupRec;
                }
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(DeptRep.class, "error", e.getMessage());
        } finally {
            mysql.close();
        }

        Jdi_depReport dept = new Jdi_depReport(new Frame(), true);
        dept.setData(GArray);
        dept.setHeaderPage(MacNo1, MacNo2, CashNo1, CashNo2, Group1, Group2);
        dept.setVisible(true);
    }

    public void bntOKClick() {
        String MacNo1 = txtMacNo1.getText();
        String MacNo2 = txtMacNo2.getText();
        String CashNo1 = txtCashNo1.getText();
        String CashNo2 = txtCashNo2.getText();
        String Group1 = txtGroup1.getText();
        String Group2 = txtGroup2.getText();
        String TempGroup = "";
        int ArraySize = 0;
        Boolean Found = false;
        PluRec[] GArray;
        GArray = new PluRec[1];
        ArraySize = 0;

        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            Statement stmt = mysql.getConnection().createStatement();
            String SqlQuery = "select *from t_sale "
                    + "where "
                    + "(macno>='" + MacNo1 + "') and (macno<='" + MacNo2 + "') "
                    + "and (cashier>='" + CashNo1 + "') and (cashier<='" + CashNo2 + "') "
                    + "and (r_group>='" + Group1 + "') and (r_group<='" + Group2 + "') "
                    + "and (r_void<>'V') and (r_refund<>'V') "
                    //                    + "and r_date=curdate() "
                    + "Order by r_group";

            ResultSet rs = stmt.executeQuery(SqlQuery);
            TempGroup = "";
            Double SumEQty = 0.0;
            Double SumEAmt = 0.0;
            Double SumTQty = 0.0;
            Double SumTAmt = 0.0;
            Double SumDQty = 0.0;
            Double SumDAmt = 0.0;
            Double SumPQty = 0.0;
            Double SumPAmt = 0.0;
            Double SumWQty = 0.0;
            Double SumWAmt = 0.0;
            Double SumSQty = 0.0;
            Double SumSAmt = 0.0;
            PluRec GroupRec = new PluRec();
            GroupRec.MacNo1 = MacNo1;
            GroupRec.MacNo2 = MacNo2;
            GroupRec.Cashier1 = CashNo1;
            GroupRec.Cashier2 = CashNo2;
            GroupRec.Group1 = Group1;
            GroupRec.Group2 = Group2;
            GroupRec.Plu1 = "";
            GroupRec.Plu2 = "";
            GroupRec.GroupCode = TempGroup;
            GroupRec.GroupName = PUtility.SeekGroupName(TempGroup);
            GroupRec.PCode = "";
            GroupRec.PName = "";

            GroupRec.E_Qty = GroupRec.E_Qty + SumEQty;
            GroupRec.E_Amt = GroupRec.E_Amt + SumEAmt;

            GroupRec.T_Qty = GroupRec.T_Qty + SumTQty;
            GroupRec.T_Amt = GroupRec.T_Amt + SumTAmt;

            GroupRec.D_Qty = GroupRec.D_Qty + SumDQty;
            GroupRec.D_Amt = GroupRec.D_Amt + SumDAmt;

            GroupRec.P_Qty = GroupRec.P_Qty + SumPQty;
            GroupRec.P_Amt = GroupRec.P_Amt + SumPAmt;

            GroupRec.W_Qty = GroupRec.W_Qty + SumWQty;
            GroupRec.W_Amt = GroupRec.W_Amt + SumWAmt;

            GroupRec.S_Qty = GroupRec.S_Qty + SumSQty;
            GroupRec.S_Amt = GroupRec.S_Amt + SumSAmt;
            GArray[ArraySize] = GroupRec;
            
            while(rs.next()){
                TempGroup = rs.getString("r_group");
                
                if (!TempGroup.equals(rs.getString("r_group"))) {
                        GroupRec = new PluRec();
                        GroupRec.MacNo1 = MacNo1;
                        GroupRec.MacNo2 = MacNo2;
                        GroupRec.Cashier1 = CashNo1;
                        GroupRec.Cashier2 = CashNo2;
                        GroupRec.Group1 = Group1;
                        GroupRec.Group2 = Group2;
                        GroupRec.Plu1 = "";
                        GroupRec.Plu2 = "";
                        GroupRec.GroupCode = TempGroup;
                        GroupRec.GroupName = PUtility.SeekGroupName(TempGroup);
                        GroupRec.PCode = "";
                        GroupRec.PName = "";

                        GroupRec.E_Qty = GroupRec.E_Qty + SumEQty;
                        GroupRec.E_Amt = GroupRec.E_Amt + SumEAmt;

                        GroupRec.T_Qty = GroupRec.T_Qty + SumTQty;
                        GroupRec.T_Amt = GroupRec.T_Amt + SumTAmt;

                        GroupRec.D_Qty = GroupRec.D_Qty + SumDQty;
                        GroupRec.D_Amt = GroupRec.D_Amt + SumDAmt;

                        GroupRec.P_Qty = GroupRec.P_Qty + SumPQty;
                        GroupRec.P_Amt = GroupRec.P_Amt + SumPAmt;

                        GroupRec.W_Qty = GroupRec.W_Qty + SumWQty;
                        GroupRec.W_Amt = GroupRec.W_Amt + SumWAmt;

                        GroupRec.S_Qty = GroupRec.S_Qty + SumSQty;
                        GroupRec.S_Amt = GroupRec.S_Amt + SumSAmt;
                        if (ArraySize == 0) {
                            GArray[ArraySize] = GroupRec;
                            ArraySize = GArray.length;
                        } else {
                            GArray = PUtility.addPluArray(GArray);
                            ArraySize = GArray.length;
                            GArray[ArraySize - 1] = GroupRec;
                        }
                        TempGroup = rs.getString("r_group");
                        SumEQty = 0.0;
                        SumEAmt = 0.0;
                        SumTQty = 0.0;

                        SumTAmt = 0.0;
                        SumDQty = 0.0;
                        SumDAmt = 0.0;
                        SumPQty = 0.0;
                        SumPAmt = 0.0;
                        SumWQty = 0.0;
                        SumWAmt = 0.0;
                        SumSQty = 0.0;
                        SumSAmt = 0.0;
                    }
                    if (rs.getString("r_etd").equals("E")) {
                        SumEQty = SumEQty + rs.getDouble("r_quan");
                        SumEAmt = SumEAmt + rs.getDouble("r_total");
                    }
                    if (rs.getString("r_etd").equals("T")) {
                        SumTQty = SumTQty + rs.getDouble("r_quan");
                        SumTAmt = SumTAmt + rs.getDouble("r_total");
                    }
                    if (rs.getString("r_etd").equals("D")) {
                        SumDQty = SumDQty + rs.getDouble("r_quan");
                        SumDAmt = SumDAmt + rs.getDouble("r_total");
                    }
                    if (rs.getString("r_etd").equals("P")) {
                        SumPQty = SumPQty + rs.getDouble("r_quan");
                        SumPAmt = SumPAmt + rs.getDouble("r_total");
                    }
                    if (rs.getString("r_etd").equals("W")) {
                        SumWQty = SumWQty + rs.getDouble("r_quan");
                        SumWAmt = SumWAmt + rs.getDouble("r_total");
                    }
                    SumSQty = SumSQty + rs.getDouble("r_quan");
                    SumSAmt = SumSAmt + rs.getDouble("r_total");
            }
            if (SumSQty > 0) {
                    GroupRec = new PluRec();
                    GroupRec.MacNo1 = MacNo1;
                    GroupRec.MacNo2 = MacNo2;
                    GroupRec.Cashier1 = CashNo1;
                    GroupRec.Cashier2 = CashNo2;
                    GroupRec.Group1 = Group1;
                    GroupRec.Group2 = Group2;
                    GroupRec.Plu1 = "";
                    GroupRec.Plu2 = "";
                    GroupRec.GroupCode = TempGroup;
                    GroupRec.GroupName = PUtility.SeekGroupName(TempGroup);
                    GroupRec.PCode = "";
                    GroupRec.PName = "";

                    GroupRec.E_Qty = GroupRec.E_Qty + SumEQty;
                    GroupRec.E_Amt = GroupRec.E_Amt + SumEAmt;

                    GroupRec.T_Qty = GroupRec.T_Qty + SumTQty;
                    GroupRec.T_Amt = GroupRec.T_Amt + SumTAmt;

                    GroupRec.D_Qty = GroupRec.D_Qty + SumDQty;
                    GroupRec.D_Amt = GroupRec.D_Amt + SumDAmt;

                    GroupRec.P_Qty = GroupRec.P_Qty + SumPQty;
                    GroupRec.P_Amt = GroupRec.P_Amt + SumPAmt;

                    GroupRec.W_Qty = GroupRec.W_Qty + SumWQty;
                    GroupRec.W_Amt = GroupRec.W_Amt + SumWAmt;

                    GroupRec.S_Qty = GroupRec.S_Qty + SumSQty;
                    GroupRec.S_Amt = GroupRec.S_Amt + SumSAmt;
                    if (ArraySize == 0) {
                        GArray[ArraySize] = GroupRec;
                        ArraySize = GArray.length;
                    } else {
                        GArray = PUtility.addPluArray(GArray);
                        ArraySize = GArray.length;
                        GArray[ArraySize - 1] = GroupRec;
                    }
                }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(DeptRep.class, "error", e.getMessage());
        } finally {
            mysql.close();
        }

        if (Value.useprint) {
            prn.PrintGroup(GArray);
        }
        txtMacNo1.requestFocus();
    }

    public void bntExitClick() {
        this.dispose();
    }

    public void inputfrombnt(String str) {
        if (txtMacNo1.hasFocus()) {
            String tempstr = "";
            tempstr = txtMacNo1.getText();
            tempstr = tempstr + str;
            txtMacNo1.setText(tempstr);
        }
        if (txtMacNo2.hasFocus()) {
            String tempstr = "";
            tempstr = txtMacNo2.getText();
            tempstr = tempstr + str;
            txtMacNo2.setText(tempstr);
        }
        if (txtCashNo1.hasFocus()) {
            String tempstr = "";
            tempstr = txtCashNo1.getText();
            tempstr = tempstr + str;
            txtCashNo1.setText(tempstr);
        }
        if (txtCashNo2.hasFocus()) {
            String tempstr = "";
            tempstr = txtCashNo2.getText();
            tempstr = tempstr + str;
            txtCashNo2.setText(tempstr);
        }
        if (txtGroup1.hasFocus()) {
            String tempstr = "";
            tempstr = txtGroup1.getText();
            tempstr = tempstr + str;
            txtGroup1.setText(tempstr);
        }
        if (txtGroup2.hasFocus()) {
            String tempstr = "";
            tempstr = txtGroup2.getText();
            tempstr = tempstr + str;
            txtGroup2.setText(tempstr);
        }
    }

    public void ProcessChkKey(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            bntExitClick();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F5) {
            bntOKClick();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtMacNo1.hasFocus()) {
                txtMacNo2.requestFocus();
            }
            if (txtMacNo2.hasFocus()) {
                txtCashNo1.requestFocus();
            }
            if (txtCashNo1.hasFocus()) {
                txtCashNo2.requestFocus();
            }
            if (txtCashNo2.hasFocus()) {
                txtGroup1.requestFocus();
            }
            if (txtGroup1.hasFocus()) {
                txtGroup2.requestFocus();
            }
            if (txtGroup2.hasFocus()) {
                txtMacNo1.requestFocus();
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntExit;
    private javax.swing.JButton bntF1;
    private javax.swing.JButton bntOK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtCashNo1;
    private javax.swing.JTextField txtCashNo2;
    private javax.swing.JTextField txtGroup1;
    private javax.swing.JTextField txtGroup2;
    private javax.swing.JTextField txtMacNo1;
    private javax.swing.JTextField txtMacNo2;
    // End of variables declaration//GEN-END:variables
}
