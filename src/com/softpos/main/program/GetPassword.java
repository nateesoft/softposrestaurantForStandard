package com.softpos.main.program;

import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import soft.virtual.KeyBoardDialog;
import util.MSG;

public class GetPassword extends javax.swing.JDialog {

    SimpleDateFormat DateTimeFmt = new SimpleDateFormat("dd/MM/yyyy (HH:mm)", Locale.ENGLISH);
    SimpleDateFormat SqlDateFmt = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    SimpleDateFormat DateFmt = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    DecimalFormat DecFmt = new DecimalFormat("##,###,##0.00");
    DecimalFormat IntFmt = new DecimalFormat("##,###,##0");
    Date date = new Date();

    public boolean ValidPassword = false;

    /**
     * Creates new form GetPassword
     */
    public GetPassword(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        initComponents();
        XPassword.setText("");
        XPassword.requestFocus();
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
        jLabel1 = new javax.swing.JLabel();
        XPassword = new javax.swing.JPasswordField();
        jToggleButton1 = new javax.swing.JToggleButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setTitle("Get Password");
        setFont(new java.awt.Font("Norasi", 0, 14)); // NOI18N
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");

        XPassword.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        XPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        XPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                XPasswordMouseClicked(evt);
            }
        });
        XPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                XPasswordKeyPressed(evt);
            }
        });

        jToggleButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jToggleButton1.setText("X");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setText("KEY BOARD");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(165, 165, 165)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(XPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jToggleButton1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(XPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

private void XPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_XPasswordKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
        System.exit(0);
    }
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        char[] pass = XPassword.getPassword();
        String password = "";
        for (int i = 0; i < pass.length; i++) {
            password = password + pass[i];
        }
        if (password.length() == 0) {
            MSG.ERR(this, "????????????????????????????????????????????????????????????...!!!");
        } else {
            if (PasswordOK(password)) {
                ValidPassword = true;
                this.dispose();
            } else {
                MSG.ERR(this, "??????????????????????????????????????????????????????????????????...!!!");
                ValidPassword = false;
                XPassword.setText("");
            }
        }
    }
    if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
        ValidPassword = false;
        this.dispose();
    }
}//GEN-LAST:event_XPasswordKeyPressed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void XPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_XPasswordMouseClicked
        if (evt.getClickCount() == 2) {
            new KeyBoardDialog(null, true, 4).get(XPassword, 4);
        }
    }//GEN-LAST:event_XPasswordMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        char[] pass = XPassword.getPassword();
        String password = "";
        for (int i = 0; i < pass.length; i++) {
            password = password + pass[i];
        }
        if (password.length() == 0) {
            MSG.ERR(this, "????????????????????????????????????????????????????????????...!!!");
        } else {
            if (PasswordOK(password)) {
                ValidPassword = true;
                this.dispose();
            } else {
                MSG.ERR(this, "???????????????????????????????????????????????????????????????????????????...!!!");
                ValidPassword = false;
                XPassword.setText("");
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new KeyBoardDialog(null, true, 4).get(XPassword, 4);
    }//GEN-LAST:event_jButton2ActionPerformed
    public boolean PasswordOK(String password) {
        boolean RetVal;
        SimpleDateFormat XDate = new SimpleDateFormat("dd", Locale.ENGLISH);
        SimpleDateFormat XMonth = new SimpleDateFormat("MM", Locale.ENGLISH);
        SimpleDateFormat XYear = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        SimpleDateFormat XDay = new SimpleDateFormat("E", Locale.ENGLISH);
        DecimalFormat PassFmt = new DecimalFormat("##########0");
        String TempDate = XDate.format(date);
        String TempMonth = XMonth.format(date);
        String TempYear = XYear.format(date);
        String TempDay = XDay.format(date);
        int TempPro = (Integer.parseInt(TempDate) + Integer.parseInt(TempMonth) + Integer.parseInt(TempYear)) * DayOfWeek(TempDay);
        String TempPro2 = PassFmt.format(TempPro).trim();
        RetVal = password.equals(TempPro2);
        System.out.println("TempPro:"+TempPro);

        return RetVal;
    }

    public int DayOfWeek(String StrDay) {
        int RetVal = 0;
        if (StrDay.equals("Sun")) {
            RetVal = 1;
        } else if (StrDay.equals("Mon")) {
            RetVal = 2;
        } else if (StrDay.equals("Tue")) {
            RetVal = 3;
        } else if (StrDay.equals("Wed")) {
            RetVal = 4;
        } else if (StrDay.equals("Thu")) {
            RetVal = 5;
        } else if (StrDay.equals("Fri")) {
            RetVal = 6;
        } else if (StrDay.equals("Sat")) {
            RetVal = 7;
        }

        return RetVal;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField XPassword;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables

}
