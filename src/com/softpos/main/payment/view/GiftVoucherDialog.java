package com.softpos.main.payment.view;

import com.softpos.constants.PublicVar;
import com.softpos.main.program.GiftDialogList;
import com.softpos.pos.core.controller.AppContext;
import com.softpos.pos.core.controller.GiftTypeControl;
import com.softpos.pos.core.model.GiftTypeBean;
import com.softpos.util.AppLogUtil;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import com.softpos.util.NumberUtil;
import javax.swing.JFrame;

public class GiftVoucherDialog extends javax.swing.JDialog {

    private DefaultTableModel model;
    private double totalAmount = 0.00;
    private double netTotalAmount = 0.00;
    private String billno;

    public GiftVoucherDialog(java.awt.Frame parent, boolean modal, String billno, String totalAmountText) {
        super(parent, modal);
        initComponents();

        model = (DefaultTableModel) tbGift.getModel();
        tbGift.setRowHeight(30);
        tbGift.setFont(new Font("Tahoma", Font.PLAIN, 14));
        this.billno = billno;
        netTotalAmount = Double.parseDouble(totalAmountText.replace(",", ""));

        loadData();
        txtGiftTypeCode.requestFocus();
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txtGiftMoney = new javax.swing.JTextField();
        txtGiftTypeCode = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbGift = new javax.swing.JTable();
        txtTotalAmount = new javax.swing.JTextField();
        btnClear = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("รายการบัตรกำนัล (Gift Voucher)");
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("เพิ่ม");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtGiftMoney.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtGiftMoney.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtGiftMoney.setText("0.00");
        txtGiftMoney.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtGiftMoneyKeyPressed(evt);
            }
        });

        txtGiftTypeCode.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtGiftTypeCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtGiftTypeCodeKeyPressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("จำนวนเงิน");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("เลขที่บัตรกำนัล");

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton4.setText("ค้นหา");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtGiftTypeCode)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtGiftMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGiftTypeCode, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGiftMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setText("ลบ");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        tbGift.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "เลขที่บัตรกำนัล", "ชื่อบัตรกำนัล", "จำนวนเงิน"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbGift);
        if (tbGift.getColumnModel().getColumnCount() > 0) {
            tbGift.getColumnModel().getColumn(0).setPreferredWidth(150);
            tbGift.getColumnModel().getColumn(1).setPreferredWidth(250);
            tbGift.getColumnModel().getColumn(2).setPreferredWidth(100);
        }

        txtTotalAmount.setEditable(false);
        txtTotalAmount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTotalAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalAmount.setText("0.00");

        btnClear.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/buttons/close.png"))); // NOI18N
        btnClear.setText("ยกเลิกทั้งหมด");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnExit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/buttons/save.png"))); // NOI18N
        btnExit.setText("บันทึก");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/buttons/close.png"))); // NOI18N
        jButton3.setText("ยกเลิก");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("ยอดรวมค่าบัตรกำนัล");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(btnClear)
                    .addComponent(btnExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        int rows = model.getRowCount();
        for (int i = 0; i < rows; i++) {
            String GNo = tbGift.getValueAt(i, 0).toString();
            String GAmt = tbGift.getValueAt(i, 2).toString().replace(",", "");
            if (netTotalAmount > 0) {
                boolean saved = AppContext.getCuponControl().saveTempGift(
                        GNo, PublicVar.VoucherType, Double.parseDouble(GAmt), PublicVar.MACNO);
                if (saved) {
                    setTotalAmount(Double.parseDouble(txtTotalAmount.getText().replace(",", "")));
                }
            }
        }

        this.dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearModel();
    }//GEN-LAST:event_btnClearActionPerformed

    private void txtGiftTypeCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiftTypeCodeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            btnExitActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            GiftTypeBean giftTypeBean = loadGiftTypeCode(txtGiftTypeCode.getText());
            if(giftTypeBean != null){
                txtGiftTypeCode.setText(giftTypeBean.getGiftTypeCode());
                this.giftTypeCode = giftTypeBean.getGiftTypeCode();
                this.giftTypeName = giftTypeBean.getGiftTypeName();
                
                txtGiftMoney.selectAll();
                txtGiftMoney.requestFocus();
            }
        }
    }//GEN-LAST:event_txtGiftTypeCodeKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int sel = tbGift.getSelectedRow();
        if (sel != -1) {
            model.removeRow(sel);
            clearText();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        saveGift();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtGiftMoneyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiftMoneyKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jButton1.requestFocus();
        }
    }//GEN-LAST:event_txtGiftMoneyKeyPressed

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jButton1ActionPerformed(null);
        }
    }//GEN-LAST:event_jButton1KeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        PublicVar.VoucherType = "";
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private String giftTypeCode = null;
    private String giftTypeName = null;
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        GiftDialogList list = new GiftDialogList(new JFrame(), true);
        list.setVisible(true);

        if (list.getGiftTypeCode() != null) {
            this.giftTypeCode = list.getGiftTypeCode();
            this.giftTypeName = list.getGiftTypeName();
            
            txtGiftTypeCode.setText(list.getGiftTypeCode());
            txtGiftTypeCode.selectAll();
            txtGiftTypeCode.requestFocus();
        } else {
            this.giftTypeCode = null;
            this.giftTypeName = null;
            
            txtGiftTypeCode.setText("");
            txtGiftMoney.setText("0.00");
            txtGiftTypeCode.requestFocus();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbGift;
    private javax.swing.JTextField txtGiftMoney;
    private javax.swing.JTextField txtGiftTypeCode;
    private javax.swing.JTextField txtTotalAmount;
    // End of variables declaration//GEN-END:variables

    private void clearText() {
        txtGiftTypeCode.setText("");
        txtGiftMoney.setText("0.00");
        txtGiftTypeCode.requestFocus();

        int size = model.getRowCount();
        double total = 0.00;
        for (int i = 0; i < size; i++) {
            double money = Double.parseDouble(model.getValueAt(i, 2).toString());
            total += money;
        }

        txtTotalAmount.setText("" + total);
    }

    private void clearModel() {
        AppContext.getCuponControl().clearTempGift();
        PublicVar.VoucherType = "";

        int size = model.getRowCount();
        for (int i = 0; i < size; i++) {
            model.removeRow(0);
        }

        txtTotalAmount.setText("0.00");
    }

    private void saveGift() {
        if (this.giftTypeCode == null || this.giftTypeName == null) {
            txtGiftTypeCode.requestFocus();
            return;
        }
        String GAmt = txtGiftMoney.getText().replace(",", "");
        try {
            double money = Double.parseDouble(GAmt);
            if (money <= 0) {
                clearText();
                return;
            }
        } catch (NumberFormatException e) {
            AppLogUtil.log(GiftVoucherDialog.class, "error", e);
            this.giftTypeCode = null;
            this.giftTypeName = null;
            
            txtGiftMoney.setText("");
            txtGiftMoney.requestFocus();
            return;
        }

        removeAtRow(this.giftTypeCode);
        model.addRow(new Object[]{
            this.giftTypeCode, 
            this.giftTypeName, 
            txtGiftMoney.getText()
        });
        clearText();
    }

    private void removeAtRow(String GNoInput) {
        int rows = model.getRowCount();
        for (int i = 0; i < rows; i++) {
            String GNo = tbGift.getValueAt(i, 0).toString();
            if (GNo.equals(GNoInput)) {
                model.removeRow(i);
            }
        }
    }

    private void loadData() {
        List<Object[]> rows = AppContext.getCuponControl().loadTempGift(PublicVar.MACNO);
        for (Object[] row : rows) {
            model.addRow(new Object[]{
                row[0],
                row[1],
                NumberUtil.showDouble2((Double) row[2])
            });
        }
    }

    private GiftTypeBean loadGiftTypeCode(String giftTypeCode) {
        GiftTypeControl giftTypeControl = new GiftTypeControl();
        return giftTypeControl.findByCode(giftTypeCode);
    }
}
