package com.softpos.main.program;

import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.MySQLConnect;
import java.sql.Statement;
import soft.virtual.KeyBoardDialog;
import util.MSG;

public class GetUserAction extends javax.swing.JDialog {

    public GetUserAction(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        c_loginname.setText("");
        c_loginpassword.setText("");
        c_loginname.requestFocus();
        PublicVar.ReturnString = "";
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
        jPanel1 = new javax.swing.JPanel();
        c_loginpassword = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        c_bntlogincancel = new javax.swing.JButton();
        c_bntloginok = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        c_loginname = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("กรุณาป้อนรหัสผู้ใช้งาน/รหัสผ่าน");
        setFont(new java.awt.Font("Norasi", 1, 14)); // NOI18N
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        c_loginpassword.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        c_loginpassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        c_loginpassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                c_loginpasswordMouseClicked(evt);
            }
        });
        c_loginpassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                c_loginpasswordKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Username");

        c_bntlogincancel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        c_bntlogincancel.setText("Cancel");
        c_bntlogincancel.setFocusable(false);
        c_bntlogincancel.setRequestFocusEnabled(false);
        c_bntlogincancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_bntlogincancelActionPerformed(evt);
            }
        });

        c_bntloginok.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        c_bntloginok.setText("OK");
        c_bntloginok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_bntloginokActionPerformed(evt);
            }
        });
        c_bntloginok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                c_bntloginokKeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Password");

        c_loginname.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        c_loginname.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        c_loginname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                c_loginnameMouseClicked(evt);
            }
        });
        c_loginname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                c_loginnameKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(c_loginname, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(c_loginpassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(c_bntlogincancel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(c_bntloginok, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(c_loginname, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(c_bntloginok, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(c_loginpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(c_bntlogincancel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("กรุณาป้อนรหัสผู้ใช้งาน (User Name) และ รหัสผ่าน (Password) ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setSize(new java.awt.Dimension(432, 174));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void c_bntloginokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_bntloginokActionPerformed
        checkuserlogin();
    }//GEN-LAST:event_c_bntloginokActionPerformed

    private void c_bntloginokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_c_bntloginokKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            checkuserlogin();
        }
    }//GEN-LAST:event_c_bntloginokKeyPressed

    private void c_bntlogincancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_bntlogincancelActionPerformed
        PublicVar.ReturnString = "";
        this.dispose();
    }//GEN-LAST:event_c_bntlogincancelActionPerformed

    private void c_loginnameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_c_loginnameKeyPressed
        keyboardcheck(evt, "c_loginname");
    }//GEN-LAST:event_c_loginnameKeyPressed

    private void c_loginpasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_c_loginpasswordKeyPressed
        keyboardcheck(evt, "c_loginpassword");
    }//GEN-LAST:event_c_loginpasswordKeyPressed

    private void c_loginnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_c_loginnameMouseClicked
        if(evt.getClickCount()==2){
            KeyBoardDialog.get(c_loginname);
        }
    }//GEN-LAST:event_c_loginnameMouseClicked

    private void c_loginpasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_c_loginpasswordMouseClicked
        if(evt.getClickCount()==2){
            KeyBoardDialog.get(c_loginpassword);
        }
    }//GEN-LAST:event_c_loginpasswordMouseClicked

    public void keyboardcheck(java.awt.event.KeyEvent evt, String cpname) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (cpname.equals("c_loginname")) {
                c_loginpassword.requestFocus();
            } else if (cpname.equals("c_loginpassword")) {
                if (!c_loginname.getText().equals("")) {
                    c_bntloginok.requestFocus();
                }
            }
        } else {
            if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
                PublicVar.ReturnString = "";
                this.dispose();
            }
        }
    }

    public void inputfrombnt(String str) {

        if (c_loginname.hasFocus()) {
            String tempstr = "";
            tempstr = c_loginname.getText();
            tempstr = tempstr + str;
            c_loginname.setText(tempstr);
        }
        if (c_loginpassword.hasFocus()) {
            char[] pass = c_loginpassword.getPassword();
            String password = "";
            for (int i = 0; i < pass.length; i++) {
                password = password + pass[i];
            }
            password = password + str;
            c_loginpassword.setText(password);
        }

    }

    public void checkuserlogin() {
        //db.getDbVar();
        //db.dbconnect();
        String OnAct = "";
        String MacNoOnAct = "";
        String loginname = c_loginname.getText();
        char[] pass = c_loginpassword.getPassword();
        String password = "";
        for (int i = 0; i < pass.length; i++) {
            password = password + pass[i];
        }
        if ((loginname.length() == 0) || (password.length() == 0)) {
            MSG.ERR(this, "กรุณาป้อนรหัสผู้ใช้งาน(Username)/รหัสผ่าน(Password)");
            clearlogin();
        }
/**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            Statement stmt = mysql.getConnection().createStatement();
            String SQLQuery = "select * from posuser Where(username= '" + loginname + "') and (password='" + password + "')";

            ResultSet rec = stmt.executeQuery(SQLQuery);
            rec.first();
            if (rec.getRow() == 0) {
                MSG.ERR(this, "รหัสผู้ใช้งาน (Username) และรหัสผ่าน (Password) ไม่ถูกต้อง !!! ");
                clearlogin();
            } else {
                PublicVar.ReturnString = loginname;
                this.dispose();
            }
        } catch (SQLException e) {
            MSG.ERR(this, e.getMessage());
            clearlogin();
        }finally{
            mysql.close();
        }

    }

    public void clearlogin() {
        c_loginname.setText("");
        c_loginpassword.setText("");
        c_loginname.setFocusable(true);
        c_loginname.requestFocus();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GetUserAction dialog = new GetUserAction(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton c_bntlogincancel;
    private javax.swing.JButton c_bntloginok;
    private javax.swing.JTextField c_loginname;
    private javax.swing.JPasswordField c_loginpassword;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables

}
