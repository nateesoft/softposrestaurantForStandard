package com.softpos.main.program;

import com.softpos.pos.core.controller.Value;
import database.MySQLConnect;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import javax.swing.table.DefaultTableModel;
import sun.natee.project.util.ThaiUtil;
import util.MSG;

public class MemberDialog extends javax.swing.JDialog {

    private String MemCode;
    private String MemName;
    private String tableNo;
    DecimalFormat df = new DecimalFormat("#,###.00");

    public MemberDialog(java.awt.Dialog parent, boolean modal, String table) {
        super(parent, modal);
        initComponents();

        txtTel.requestFocus();
        this.tableNo = table;
    }

    public String getMemCode() {
        return MemCode;
    }

    public void setMemCode(String MemCode) {
        this.MemCode = MemCode;
    }

    public String getMemName() {
        return MemName;
    }

    public void setMemName(String MemName) {
        this.MemName = MemName;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtCode = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        txtTel = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbMember = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtRemark = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        btnCancelMember = new javax.swing.JButton();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("???????????????????????????????????????????????? (Member)");
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtCode.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodeKeyPressed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setText("???????????????");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txtTel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("??????????????????????????????");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("??????????????????????????????");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("???????????????????????????????????????");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtName)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtTel, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtCode))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton5.setText("(F5) ?????????????????????????????????");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton6.setText("????????????????????????????????? (OK)");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton7.setText("????????? (EXIT)");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        tbMember.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbMember.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "???????????? (Code)", "?????????????????????????????? (Member Name)", "???????????????????????????", "???????????????????????????????????????", "?????????????????????????????????", "??????????????????????????????????????????"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbMember.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMemberMouseClicked(evt);
            }
        });
        tbMember.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbMemberKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbMember);
        if (tbMember.getColumnModel().getColumnCount() > 0) {
            tbMember.getColumnModel().getColumn(0).setPreferredWidth(50);
            tbMember.getColumnModel().getColumn(1).setPreferredWidth(150);
            tbMember.getColumnModel().getColumn(2).setPreferredWidth(50);
            tbMember.getColumnModel().getColumn(3).setPreferredWidth(50);
            tbMember.getColumnModel().getColumn(4).setPreferredWidth(50);
            tbMember.getColumnModel().getColumn(5).setPreferredWidth(50);
        }

        txtRemark.setColumns(20);
        txtRemark.setRows(5);
        jScrollPane2.setViewportView(txtRemark);

        jLabel5.setText("????????????????????????");

        btnCancelMember.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCancelMember.setText("????????????????????????????????????");
        btnCancelMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelMemberActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 363, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCancelMember, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelMember, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        setMemCode("");
        setMemName("");
        dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        UpdateMember("Ins");
        selectMember();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        loadAllMember();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        loadSearchMember(txtCode.getText(), txtName.getText(), txtTel.getText());

    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_F5) {
            jButton5ActionPerformed(null);
        }
    }//GEN-LAST:event_txtCodeKeyPressed

    private void tbMemberMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMemberMouseClicked
        if (evt.getClickCount() == 2) {

            selectMember();
            UpdateMember("Ins");
        }
    }//GEN-LAST:event_tbMemberMouseClicked

    private void tbMemberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbMemberKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            selectMember();
            UpdateMember("Ins");
        }
    }//GEN-LAST:event_tbMemberKeyPressed

    private void btnCancelMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelMemberActionPerformed
//              setMemCode("");
//        setMemName("");  
//        selectMember();
        UpdateMember("Del");
        clearMemberDiscount();
        dispose();
    }//GEN-LAST:event_btnCancelMemberActionPerformed

    void selectMember() {
        int row = tbMember.getSelectedRow();
        if (row != -1) {
            String mCode = model.getValueAt(row, 0).toString();
            String mName = model.getValueAt(row, 1).toString();
            setMemCode(mCode);
            setMemName(mName);
            dispose();
        } else {
            setMemCode("");
            setMemName("");
            dispose();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelMember;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbMember;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextArea txtRemark;
    private javax.swing.JTextField txtTel;
    // End of variables declaration//GEN-END:variables

    private DefaultTableModel model;

    private void loadAllMember() {
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            model = (DefaultTableModel) tbMember.getModel();
            tbMember.setRowHeight(35);

            int size = model.getRowCount();
            for (int i = 0; i < size; i++) {
                model.removeRow(0);
            }

            String sql = "select * from " + Value.db_member + ".memmaster "
                    + "order by Member_Code; ";
//                    + "limit 0,100";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("Member_Code"),
                    ThaiUtil.ASCII2Unicode(rs.getString("Member_TitleNameThai") + rs.getString("Member_NameThai") + " " + rs.getString("Member_SurnameThai")),
                    rs.getString("Member_HomeTel"),
                    "",
                    rs.getString("Member_Mobile"),
                    rs.getString("Member_ExpiredDate")

                });
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(this, e.getMessage());
        } finally {
            mysql.close();
        }

        tbMember.requestFocus();
    }

    private void loadAllMember(String memCode, String memName, String memTel) {
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            model = (DefaultTableModel) tbMember.getModel();
            tbMember.setRowHeight(35);

            int size = model.getRowCount();
            for (int i = 0; i < size; i++) {
                model.removeRow(0);
            }

            String sql = "select * from " + Value.db_member + ".memmaster "
                    + "where Member_Code like '%" + memCode + "%' "
                    + "and Member_NameThai like '%" + ThaiUtil.Unicode2ASCII(memName) + "%' "
                    + "and Member_HomeTel like '%" + memTel + "%' "
                    + "order by Member_Code ";
//                    + "limit 0,10";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("Member_Code"),
                    ThaiUtil.ASCII2Unicode(rs.getString("Member_TitleNameThai") + rs.getString("Member_NameThai") + " " + rs.getString("Member_SurnameThai")),
                    rs.getString("Member_HomeTel"),
                    "",
                    rs.getString("Member_Mobile"),
                    rs.getString("Member_ExpiredDate")

                });
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(this, e.getMessage());
        } finally {
            mysql.close();
        }

        tbMember.requestFocus();
    }

    private void loadSearchMember(String memCode, String memName, String memTel) {
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            model = (DefaultTableModel) tbMember.getModel();
            tbMember.setRowHeight(35);

            int size = model.getRowCount();
            for (int i = 0; i < size; i++) {
                model.removeRow(0);
            }

            String sql = "";
            if (!memCode.equals("")) {
                sql = "select * from " + Value.db_member + ".memmaster "
                        + "where Member_Code like '%" + memCode + "%' "
                        + "order by Member_Code ";
//                    + "limit 0,10";
            } else if (!memName.equals("")) {
                sql = "select * from " + Value.db_member + ".memmaster "
                        + "where Member_NameThai like '%" + ThaiUtil.Unicode2ASCII(memName) + "%' "
                        + "order by Member_Code ";
            } else if (!memTel.equals("")) {
                sql = "select * from " + Value.db_member + ".memmaster "
                        + "where Member_Mobile like '%" + ThaiUtil.Unicode2ASCII(memTel) + "%' "
                        + "order by Member_Code ";
            }
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("Member_Code"),
                    ThaiUtil.ASCII2Unicode(rs.getString("Member_TitleNameThai") + rs.getString("Member_NameThai") + " " + rs.getString("Member_SurnameThai")),
                    rs.getString("Member_HomeTel"),
                    "",
                    rs.getString("Member_Mobile"),
                    rs.getString("Member_ExpiredDate")

                });
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(this, e.getMessage());
        } finally {
            mysql.close();
        }

        tbMember.requestFocus();
    }

    private void clearMemberDiscount() {
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        //clear temp cupon
        try {
            String sql = "update tablefile set memdisc='',nettotal= nettotal+memdiscamt,"
                    + " memdiscamt='0',memname='',memcode='' where tcode='" + tableNo + "'";
            Statement stmt = mysql.getConnection().createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        } finally {
            mysql.close();
        }
        mysql.open();
        try {
            String sqlUpdate = "update balance "
                    + "set "
                    + "r_prsubtype='',"
                    + "r_prsubcode='',"
                    + "r_prsubquan='0',"
                    + "r_prsubdisc='0',"
                    + "r_prsubamt='0'"
                    + " where r_table='" + tableNo + "'";
            mysql.getConnection().createStatement().executeUpdate(sqlUpdate);
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());

        } finally {
            mysql.close();
        }
    }

    public void UpdateMember(String choice) {
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            String sql;
            String memCode = MemCode + "";
            if (memCode.equals("null")) {
                MemCode = "";
            }
            if (choice.equals("Ins")) {
                sql = "Update tablefile set memcode='" + MemCode + "',memname='" + ThaiUtil.Unicode2ASCII(MemName) + "' where tcode='" + tableNo + "';";
            } else {
                sql = "Update tablefile set memcode='',memname='',memdisc='',memdiscamt='0',nettotal=tamount where tcode='" + tableNo + "'";
            }
            switch (choice) {
                case "Ins":
                    mysql.getConnection().createStatement().executeUpdate(sql);
                    break;
                case "Del":
                    mysql.getConnection().createStatement().executeUpdate(sql);
                    break;
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        } finally {
            mysql.close();
        }
    }

}
