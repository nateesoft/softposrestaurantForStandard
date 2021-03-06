package com.softpos.login;

import database.MySQLConnect;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import com.softpos.floorplan.FloorPlanDialog;
import com.softpos.pos.core.controller.BranchControl;
import com.softpos.main.program.GetPassword;
import com.softpos.pos.core.controller.POSHWSetup;
import com.softpos.pos.core.controller.PosControl;
import com.softpos.pos.core.controller.PublicVar;
import com.softpos.pos.core.controller.UserRecord;
import com.softpos.pos.core.controller.Value;
import database.ConfigFile;
import soft.virtual.KeyBoardDialog;
import sun.natee.project.util.ThaiUtil;
import util.MSG;

public class Login extends javax.swing.JDialog {

    Timer timer;
    SimpleDateFormat DatefmtShow = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    DecimalFormat IntFmt = new DecimalFormat("#,##0");

    public Login(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        txtUser.setText("");
        txtPass.setText("");
        txtMacNo.setText("MAC NO. " + Value.MACNO);
        txtUser.requestFocus();
        TimeOfDay time = new TimeOfDay();
        timer = new Timer(1000, time);
        timer.start();
        checkUpdate();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        pbCheckUpdate = new javax.swing.JProgressBar();
        jPanel3 = new javax.swing.JPanel();
        btnCancel = new javax.swing.JButton();
        lbPass = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        btnLogin = new javax.swing.JButton();
        txtPass = new javax.swing.JPasswordField();
        lbUser = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtMacNo = new javax.swing.JTextField();
        txtShowDate = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbVersion = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Login Sale System www.softpos.co.th tel.02-116-6615 Hotline: 086-320-3877");
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pbCheckUpdate.setBackground(new java.awt.Color(255, 153, 153));
        pbCheckUpdate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pbCheckUpdate.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(pbCheckUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 530, 430, 20));

        jPanel3.setBackground(new java.awt.Color(255, 204, 51));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        btnCancel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        lbPass.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbPass.setText("???????????????????????? (Password)  :");

        txtUser.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        txtUser.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtUserMouseClicked(evt);
            }
        });
        txtUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUserKeyPressed(evt);
            }
        });

        btnLogin.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        btnLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnLoginKeyPressed(evt);
            }
        });

        txtPass.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        txtPass.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPass.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtPass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPassMouseClicked(evt);
            }
        });
        txtPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPassKeyPressed(evt);
            }
        });

        lbUser.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbUser.setText("??????????????????????????????????????? (User name):");

        jButton1.setBackground(new java.awt.Color(255, 102, 0));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/mrtworld-unlock1.png"))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(202, 202, 202)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbPass, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbUser, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtUser)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbUser))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbPass, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 1020, 110));

        txtMacNo.setBackground(new java.awt.Color(153, 255, 153));
        txtMacNo.setFont(new java.awt.Font("Norasi", 1, 14)); // NOI18N
        txtMacNo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMacNo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtMacNo.setFocusable(false);
        txtMacNo.setRequestFocusEnabled(false);
        txtMacNo.setVerifyInputWhenFocusTarget(false);
        txtMacNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMacNoActionPerformed(evt);
            }
        });
        jPanel1.add(txtMacNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 490, 223, 30));

        txtShowDate.setBackground(new java.awt.Color(153, 255, 153));
        txtShowDate.setFont(new java.awt.Font("Norasi", 1, 14)); // NOI18N
        txtShowDate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtShowDate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtShowDate.setFocusable(false);
        txtShowDate.setRequestFocusEnabled(false);
        txtShowDate.setVerifyInputWhenFocusTarget(false);
        txtShowDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtShowDateActionPerformed(evt);
            }
        });
        jPanel1.add(txtShowDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 490, 181, 30));

        jPanel2.setBackground(new java.awt.Color(255, 102, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel2.setForeground(java.awt.Color.white);

        jLabel6.setFont(new java.awt.Font("Norasi", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Software Restaurant SOFTPOS??2016 V.8.2");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1006, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 560, 1010, 40));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/BackGround.jpg"))); // NOI18N
        jLabel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 770));

        lbVersion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbVersion.setText("Update: 07/04/2022");
        jPanel1.add(lbVersion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 280, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1014, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 743, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtShowDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtShowDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtShowDateActionPerformed

    private void txtMacNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMacNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMacNoActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        clearTemp();
        System.exit(0);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void txtUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUserKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            clearTemp();
            System.exit(0);
        } else {
            if (!txtUser.getText().equals("")) {
                keyboardcheck(evt, "txtUser");
            } else {
                txtUser.requestFocus();
            }
        }
    }//GEN-LAST:event_txtUserKeyPressed

    private void txtPassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPassKeyPressed
        if (!txtPass.getText().equals("")) {
            keyboardcheck(evt, "txtPass");
        } else {
            txtPass.requestFocus();
        }
    }//GEN-LAST:event_txtPassKeyPressed

    private void btnLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLoginKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            checkUserLogin();
        }
    }//GEN-LAST:event_btnLoginKeyPressed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        checkUserLogin();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void txtUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUserMouseClicked
        //if (evt.getClickCount() == 2) {
        new KeyBoardDialog(null, true, 4).get(txtUser, 4);
        //}
    }//GEN-LAST:event_txtUserMouseClicked

    private void txtPassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPassMouseClicked
        //if (evt.getClickCount() == 2) {
        new KeyBoardDialog(null, true, 4).get(txtPass, 4);
        //}
    }//GEN-LAST:event_txtPassMouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        System.out.println("Close Login Form");
    }//GEN-LAST:event_formWindowClosed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        PosHwSetupOnAct("N");
        JOptionPane.showMessageDialog(this, "???????????????????????????????????????????????????????????????????????????????????????????????? Username : Password");
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {
        MySQLConnect.getDbVar();
        
        //create file to check program is exist
        File f = new File("softrestaurant.running");
        if (f.exists()) {
            int confirm = JOptionPane.showConfirmDialog(null, "?????????????????????????????????????????????????????????????????????????????? ???????????????????????????????????????????????????????????????????????????????????? ?",
                    "??????????????????????????????????????????????????????????????????", JOptionPane.OK_CANCEL_OPTION);
            if (confirm == JOptionPane.OK_OPTION) {
                //running program continue;
                new File("softrestaurant.running").delete();
            } else {
                System.exit(0);
            }
        } else {
            try {
                f.createNewFile();
            } catch (IOException ex) {
                MSG.ERR(null, ex.getMessage());
            }
        }

        Font myfont = new Font("Norasi", Font.PLAIN, 14);
        UIManager.put("Label.font", myfont);
        UIManager.put("Button.font", myfont);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("OptionPane.messageFont", new javax.swing.plaf.FontUIResource(new java.awt.Font(
                    "Norasi", java.awt.Font.PLAIN, 14)));
        } catch (ClassNotFoundException e) {
            MSG.ERR(null, e.getMessage());
        } catch (InstantiationException e) {
            MSG.ERR(null, e.getMessage());
        } catch (IllegalAccessException e) {
            MSG.ERR(null, e.getMessage());
        } catch (UnsupportedLookAndFeelException e) {
            MSG.ERR(null, e.getMessage());
        }

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Login dialog = new Login(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lbPass;
    private javax.swing.JLabel lbUser;
    private javax.swing.JLabel lbVersion;
    private javax.swing.JProgressBar pbCheckUpdate;
    private javax.swing.JTextField txtMacNo;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtShowDate;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables

    private void checkUserLogin() {
        String OnAct;
        String MacNoOnAct;
        String loginname = txtUser.getText();
        String password = txtPass.getText();
        
        PublicVar.printerStation = ConfigFile.getProperties("printerStation");
        PublicVar.defaultCustomer = ConfigFile.getProperties("defaultCustomer");
        PublicVar.defaultCustomerQty = ConfigFile.getProperties("defaultCustomerQty");
        PublicVar.PrintCheckBillFromPDA = ConfigFile.getProperties("PrintCheckBillFromPDA");
        PublicVar.PrintCopyAuto = ConfigFile.getProperties("PrintCopyAuto");
        
        if ((loginname.length() == 0) || (password.length() == 0)) {
            MSG.ERR(this, "??????????????????????????????????????????????????????????????????(Username)/????????????????????????(Password)");
            clearlogin();
        } else {
            MySQLConnect mysql = new MySQLConnect();
            mysql.open();

            try {
                String sql = "select * from posuser "
                        + "where username= '" + ThaiUtil.Unicode2ASCII(loginname) + "' "
                        + "and password='" + ThaiUtil.Unicode2ASCII(password) + "'";
                Statement stmt = mysql.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                rs.first();
                if (rs.getRow() == 0) {
                    MSG.ERR(this, "??????????????????????????????????????? (Username) ????????????????????????????????? (Password) ?????????????????????????????? !!! ");
                    clearlogin();
                } else {
                    PublicVar._RealUser = rs.getString("username");
                    PublicVar._Password = rs.getString("password");
                    PublicVar._UserName = ThaiUtil.ASCII2Unicode(rs.getString("name"));
                    OnAct = rs.getString("onact");
                    MacNoOnAct = rs.getString("macno");
                    rs.close();
                    SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    String St = tf.format(new Date());
                    txtShowDate.setText(St);

                    String sqlCheckBillno = "select b_ondate b_ondate from billno where b_ondate<>'" + St + "'";
                    Statement stmt2 = mysql.getConnection().createStatement();
                    ResultSet rs2 = stmt2.executeQuery(sqlCheckBillno);
                    if (!rs2.next()) {
                        if (OnAct.equals("Y") && (!MacNoOnAct.equals(Value.MACNO))) {
                            MSG.ERR(this, "????????????????????????????????? " + loginname + " ????????????????????????????????????????????????????????????????????????????????????????????????????????? " + MacNoOnAct);
                            clearlogin();
                        } else {
                            UserRecord TUserRec = new UserRecord();
                            if (TUserRec.GetUserAction(loginname)) {
                                if (TUserRec.Sale1.equals("Y")) {
                                    PublicVar.TUserRec = TUserRec;
                                    UpdateLogin(loginname);
                                    PosHwSetupOnAct("Y");
                                    Value.USERCODE = txtUser.getText();
                                    PublicVar.Branch_Code = BranchControl.getData().getCode();
                                    dispose();
                                    FloorPlanDialog floorPlan = new FloorPlanDialog();
                                    floorPlan.setVisible(true);
                                } else {
                                    MSG.ERR(this, "???????????????????????????????????????????????????????????????????????????????????????????????????...???????????????????????????????????????...!!!");
                                    clearlogin();
                                }
                            } else {
                                MSG.ERR(this, "??????????????????????????? Load ????????????????????????????????????????????????????????????????????????????????????????????????????????? ...");
                                clearlogin();
                            }
                        }
                    } else {
                        POSHWSetup poshwSetup = PosControl.getData(Value.MACNO);
                        String POSOnActCheck = poshwSetup.getOnAct();
                        if (POSOnActCheck.equals("Y")) {
                            JOptionPane.showMessageDialog(this, "?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????? Task bar", "Applications are opened", JOptionPane.WARNING_MESSAGE);
                            System.exit(0);
                        }
                        MSG.ERR(this, "?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????? ??????????????? End Of Day ???????????? ");
                        GetPassword frm = new GetPassword(null, true);
                        frm.setVisible(true);
                        if (frm.ValidPassword) {
                            if (OnAct.equals("Y") && (!MacNoOnAct.equals(Value.MACNO))) {
                                MSG.ERR(this, "????????????????????????????????? " + loginname + " ????????????????????????????????????????????????????????????????????????????????????????????????????????? " + MacNoOnAct);
                                clearlogin();
                            } else {
                                UserRecord TUserRec = new UserRecord();
                                if (TUserRec.GetUserAction(loginname)) {
                                    if (TUserRec.Sale1.equals("Y")) {
                                        PublicVar.TUserRec = TUserRec;
                                        UpdateLogin(loginname);
                                        PosHwSetupOnAct("Y");
                                        Value.USERCODE = txtUser.getText();
                                        PublicVar.Branch_Code = BranchControl.getData().getCode();
                                        dispose();
                                        FloorPlanDialog floorPlan = new FloorPlanDialog();
                                        floorPlan.setVisible(true);
                                    } else {
                                        MSG.ERR(this, "???????????????????????????????????????????????????????????????????????????????????????????????????...???????????????????????????????????????...!!!");
                                        clearlogin();
                                    }
                                } else {
                                    MSG.ERR(this, "??????????????????????????? Load ????????????????????????????????????????????????????????????????????????????????????????????????????????? ...");
                                    clearlogin();
                                }
                            }
                        } else {
                            System.exit(WIDTH);
                        }
                    }

                    rs2.close();
                    stmt2.close();
                }

                rs.close();
                stmt.close();
            } catch (SQLException e) {
                MSG.ERR(this, e.getMessage());
                clearlogin();
            } finally {
                mysql.close();
            }
        }
    }

    private void clearlogin() {
        txtUser.setText("");
        txtPass.setText("");
        txtUser.requestFocus();
    }

    private void UpdateLogin(String UserCode) {
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String SQLQuery = "update posuser set "
                    + "onact='Y',"
                    + "macno='" + Value.MACNO + "' "
                    + "where username='" + UserCode + "'";
            Statement stmt = mysql.getConnection().createStatement();
            stmt.executeUpdate(SQLQuery);
            Value.CASHIER = UserCode;
            String sql = "update posuser set "
                    + "onact='N' "
                    + "where username<>'" + UserCode + "' "
                    + "and macno='" + Value.MACNO + "';";
            mysql.getConnection().createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            MSG.ERR(this, e.getMessage());
            clearlogin();
        } finally {
            mysql.close();
        }
    }

    private void keyboardcheck(KeyEvent evt, String c_loginname) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (c_loginname.equals("txtUser")) {
                txtPass.requestFocus();
            } else if (c_loginname.equals("txtPass")) {
                btnLogin.requestFocus();
            }
        } else {
            if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
                clearTemp();
                System.exit(0);
            }
        }
    }

    private void clearTemp() {
        new File("softrestaurant.running").delete();
    }

    private void checkUpdate() {

        new Thread(() -> {
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
                    }
                }
                pbCheckUpdate.setString("SoftPOS Update:V8.2 12/08/2020 00:35");
            } catch (Exception e) {
                MSG.ERR(e.getMessage());
            }
        }).start();
    }

    public void PosHwSetupOnAct(String Onact) {
        try {
            MySQLConnect mysql = new MySQLConnect();
            mysql.open();
            String sql = "update poshwsetup set onact='" + Onact + "' where terminal='" + Value.MACNO + "';";
            Statement stmt = mysql.getConnection().createStatement();
            if(stmt.executeUpdate(sql)>0){
                // reset load poshwsetup
                PosControl.resetPosHwSetup();
            }
            mysql.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        }
    }

    class TimeOfDay implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            SimpleDateFormat tf = new SimpleDateFormat("dd/MM/yyyy (HH:mm:ss)", Locale.ENGLISH);
            String St = tf.format(new Date());
            txtShowDate.setText(St);
        }
    }

}
