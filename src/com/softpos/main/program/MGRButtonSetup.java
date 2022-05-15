package com.softpos.main.program;

import com.softpos.pos.core.controller.ThaiUtil;
import database.AppCheckDB;
import database.MySQLConnect;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import setupmenu.DlgBrowseProduct;
import util.AppLogUtil;
import util.MSG;

public class MGRButtonSetup extends javax.swing.JDialog {

    private String menuCode;
    private int menuIndex;
    private boolean editOK = false;

    private DefaultTableModel model1;
    private DefaultTableModel model2;
    private DefaultTableModel model3;

    public MGRButtonSetup(java.awt.Frame parent, boolean modal,
            String menuCode, int menuIndex) {
        super(parent, modal);
        initComponents();

        this.menuCode = menuCode;
        this.menuIndex = menuIndex;

        //ตรวจสอบในกรณีที่ยังไม่มี column check_extra ในระบบ
        AppCheckDB.checkExtra();
        //จบการตรวจสอบ

        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "select p.pcode, p.pdesc "
                    + "from soft_menusetup m, product p "
                    + "where m.pcode=p.pcode "
                    + "and menucode='" + menuCode + "' "
                    + "and m.pcode<>''";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                txtPCode.setText(rs.getString("pcode"));
                txtPDesc.setText(ThaiUtil.ASCII2Unicode(rs.getString("pdesc")));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(MGRButtonSetup.class, "error", e.getMessage());
        } finally {
            mysql.close();
        }

        model1 = (DefaultTableModel) tbSideDishFree.getModel();
        model2 = (DefaultTableModel) tbExtra.getModel();
        model3 = (DefaultTableModel) tbAutoAdd.getModel();

        tbButtonSetup.setRowHeight(30);
        JTableHeader tHeader = tbButtonSetup.getTableHeader();
        tHeader.setFont(new Font("Tahoma", Font.PLAIN, 12));

        loadData();
        txtPCode.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        chkMSet2 = new javax.swing.JCheckBox();
        txtQty = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        chkAutoSet = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        chkMSet1 = new javax.swing.JCheckBox();
        jPanel15 = new javax.swing.JPanel();
        chkExtra = new javax.swing.JCheckBox();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbButtonSetup = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        txtPDesc = new javax.swing.JTextField();
        txtPCode = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbAutoAdd = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbExtra = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbSideDishFree = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("กำหนดคุณสมบัติปุ่มเมนูใช้งานลัด");
        setAlwaysOnTop(true);
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        chkMSet2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        chkMSet2.setText("เลือกครบตามจำนวน");
        chkMSet2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkMSet2MouseClicked(evt);
            }
        });

        txtQty.setBackground(new java.awt.Color(255, 255, 153));
        txtQty.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtQty.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtQty.setText("0");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("รายการ");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(43, 43, 43))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkMSet2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(chkMSet2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        chkAutoSet.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        chkAutoSet.setText("Auto Add");
        chkAutoSet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkAutoSetMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkAutoSet, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(chkAutoSet)
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Try Menu Set");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        chkMSet1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        chkMSet1.setText("ต้องสั่งรายการอื่นก่อน");
        chkMSet1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkMSet1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(chkMSet1)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(chkMSet1))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        chkExtra.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        chkExtra.setText("เลือก Extra เพิ่มได้");
        chkExtra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkExtraMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkExtra, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(chkExtra)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        tbButtonSetup.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PCode", "PDesc", "SD_PCode", "SD_PDesc", "EX_PCode", "EX_PDesc", "Auto_PCode", "Auto_PDesc", "Check_Before", "Check_Qty", "Check_Amount", "Check_AutoAdd", "Check_Extra"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbButtonSetup.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane6.setViewportView(tbButtonSetup);

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        txtPDesc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPDesc.setEnabled(false);

        txtPCode.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPCode.setEnabled(false);
        txtPCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPCodeKeyPressed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("รหัสสินค้า");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtPCode, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPDesc, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txtPDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtPCode, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel11))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        btnSave.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSave.setText("บันทึก / Exit");
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSaveMouseClicked(evt);
            }
        });
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        btnSave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSaveKeyPressed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDelete.setText("ลบปุ่ม (Delete)");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCancel.setText("ยกเลิก (Cancel)");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jButton13.setText("+");
        jButton13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton13MouseClicked(evt);
            }
        });
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setText("-");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jLabel6.setText("Auto Add");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(67, 67, 67)
                .addComponent(jButton13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton14))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel6))
        );

        tbAutoAdd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "รหัสสินค้า", "ชื่อสินค้า"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tbAutoAdd);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        tbExtra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "รหัสสินค้า", "ชื่อสินค้า"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tbExtra);

        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jButton10.setText("-");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jLabel5.setText("Extra");

        jButton12.setText("+");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addComponent(jButton12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel5)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        tbSideDishFree.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "รหัสสินค้า", "ชื่อสินค้า"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tbSideDishFree);

        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jButton11.setText("-");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jLabel4.setText("Side Dish Free");

        jButton9.setText("+");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jButton9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel4)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtPCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPCodeKeyPressed

    }//GEN-LAST:event_txtPCodeKeyPressed

    private void chkMSet1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkMSet1MouseClicked
        if (chkMSet1.isSelected()) {
            chkAutoSet.setEnabled(false);
            jButton13.setEnabled(false);
            jButton14.setEnabled(false);
            jLabel6.setEnabled(false);
        } else if (!chkMSet1.isSelected()) {
            chkAutoSet.setEnabled(true);
            jButton13.setEnabled(true);
            jButton14.setEnabled(true);
            jLabel6.setEnabled(true);
        }
    }//GEN-LAST:event_chkMSet1MouseClicked

    private void chkAutoSetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkAutoSetMouseClicked
        if (chkAutoSet.isSelected()) {
            tbSideDishFree.setEnabled(false);
            jButton11.setEnabled(false);
            jButton9.setEnabled(false);
            jLabel4.setEnabled(false);
            tbExtra.setEnabled(false);
            jButton9.setEnabled(false);
            jLabel4.setEnabled(false);
            jButton10.setEnabled(false);
            jButton12.setEnabled(false);
            jLabel5.setEnabled(false);

        }
        if (!chkAutoSet.isSelected()) {
            tbSideDishFree.setEnabled(true);
            jButton11.setEnabled(true);
            jButton9.setEnabled(true);
            jLabel4.setEnabled(true);
            tbExtra.setEnabled(true);
            jButton10.setEnabled(true);
            jButton12.setEnabled(true);
            jLabel5.setEnabled(true);

        }
    }//GEN-LAST:event_chkAutoSetMouseClicked

    private void chkMSet2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkMSet2MouseClicked
        if (chkMSet2.isSelected()) {
            txtQty.requestFocus();
            chkAutoSet.setEnabled(false);
            jButton13.setEnabled(false);
            jButton14.setEnabled(false);
            jLabel6.setEnabled(false);
        } else if (!chkMSet2.isSelected()) {
            chkAutoSet.setEnabled(true);
            jButton13.setEnabled(true);
            jButton14.setEnabled(true);
            jLabel6.setEnabled(true);
        }
    }//GEN-LAST:event_chkMSet2MouseClicked

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteDataPCode();
        loadData();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSaveKeyPressed
        Save();
    }//GEN-LAST:event_btnSaveKeyPressed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        //save
        deleteDataPCode();
        SaveData();
        loadData();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseClicked

        Save();

    }//GEN-LAST:event_btnSaveMouseClicked

    private void jButton13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton13MouseClicked

    }//GEN-LAST:event_jButton13MouseClicked

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        SideDishFree();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        int row = tbSideDishFree.getSelectedRow();
        if (row != -1) {
            model1.removeRow(row);
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        Extra();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        int row = tbExtra.getSelectedRow();
        if (row != -1) {
            model2.removeRow(row);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        AutoAdd();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        int row = tbAutoAdd.getSelectedRow();
        if (row != -1) {
            model3.removeRow(row);
        }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void chkExtraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkExtraMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_chkExtraMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.JCheckBox chkAutoSet;
    private javax.swing.JCheckBox chkExtra;
    private javax.swing.JCheckBox chkMSet1;
    private javax.swing.JCheckBox chkMSet2;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable tbAutoAdd;
    private javax.swing.JTable tbButtonSetup;
    private javax.swing.JTable tbExtra;
    private javax.swing.JTable tbSideDishFree;
    private javax.swing.JTextField txtPCode;
    private javax.swing.JTextField txtPDesc;
    private javax.swing.JTextField txtQty;
    // End of variables declaration//GEN-END:variables

    private void Save() {
        String MenuSet;
        if (chkMSet1.isSelected()) {
            MenuSet = "Y";
        } else if (chkMSet2.isSelected()) {
            MenuSet = "Y";
        }
        String AutoSet;
        if (chkAutoSet.isSelected()) {
            AutoSet = "Y";
        }
        String pcode = txtPCode.getText();
        String pdesc = txtPDesc.getText();
        String ppice = txtQty.getText();

    }

    private void SideDishFree() {
        DefaultTableModel md = (DefaultTableModel) tbSideDishFree.getModel();
        tbSideDishFree.setRowHeight(35);
//        int size = md.getRowCount();
//        for (int i = 0; i < size; i++) {
//            md.removeRow(0);
//        }

        DlgBrowseProduct browse = new DlgBrowseProduct(null, true);
        browse.setVisible(true);
        if (browse.getSelectPlu() != null) {

            String PCodeSDF = browse.getSelectPlu().getCode();
            String PNameSDF = browse.getSelectPlu().getName();

            md.addRow(new Object[]{PCodeSDF, PNameSDF});

            tbSideDishFree.setToolTipText("");

        }

    }

    private void Extra() {
        DefaultTableModel md = (DefaultTableModel) tbExtra.getModel();
        tbExtra.setRowHeight(35);
//        int size = md.getRowCount();
//        for (int i = 0; i < size; i++) {
//            md.removeRow(0);
//        }
        DlgBrowseProduct browse = new DlgBrowseProduct(null, true);
        browse.setVisible(true);
        if (browse.getSelectPlu() != null) {

            String PCodeSDF = browse.getSelectPlu().getCode();
            String PNameSDF = browse.getSelectPlu().getName();

            md.addRow(new Object[]{PCodeSDF, PNameSDF});

            tbExtra.setToolTipText("");

        }

    }

    private void AutoAdd() {
        DefaultTableModel md = (DefaultTableModel) tbAutoAdd.getModel();
        tbAutoAdd.setRowHeight(35);
//        int size = md.getRowCount();
//        for (int i = 0; i < size; i++) {
//            md.removeRow(0);
//        }
        try {

            DlgBrowseProduct browse = new DlgBrowseProduct(null, true);
            browse.setVisible(true);

            if (browse.getSelectPlu() != null) {

                String PCodeSDF = browse.getSelectPlu().getCode();
                String PNameSDF = browse.getSelectPlu().getName();

                md.addRow(new Object[]{PCodeSDF, PNameSDF});

            }
            tbAutoAdd.setToolTipText("");
        } catch (Exception e) {
            MSG.ERR(this, e.getMessage());
        }

    }

    private void loadData() {
        boolean isBefore = false;
        boolean isQtyCheck = false;
        boolean isAutoAdd = false;
        boolean isExtraNoLimit = false;
        int qtyAmt = 0;

        DefaultTableModel model4 = (DefaultTableModel) tbButtonSetup.getModel();
        int size = model4.getRowCount();
        for (int i = 0; i < size; i++) {
            model4.removeRow(0);
        }

        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "select * from mgrbuttonsetup where pcode='" + txtPCode.getText() + "' order by pcode";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (rs.getString("Check_before").equals("Y")) {
                    isBefore = true;
                }
                if (rs.getString("Check_qty").equals("Y")) {
                    isQtyCheck = true;
                }
                if (rs.getString("check_autoadd").equals("Y")) {
                    isAutoAdd = true;
                }

                //เพิ่มใหม่
                if (rs.getString("check_extra").equals("Y")) {
                    isExtraNoLimit = true;
                }

                qtyAmt = rs.getInt("qty");

                model4.addRow(new Object[]{
                    rs.getString("pcode"),
                    ThaiUtil.ASCII2Unicode(rs.getString("pdesc")),
                    rs.getString("sd_pcode"),
                    ThaiUtil.ASCII2Unicode(rs.getString("sd_pdesc")),
                    rs.getString("ex_pcode"),
                    ThaiUtil.ASCII2Unicode(rs.getString("ex_pdesc")),
                    rs.getString("auto_pcode"),
                    ThaiUtil.ASCII2Unicode(rs.getString("auto_pdesc")),
                    rs.getString("Check_before"),
                    rs.getString("Check_qty"),
                    rs.getInt("qty"),
                    rs.getString("check_autoadd"),
                    rs.getString("check_extra")
                });
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(MGRButtonSetup.class, "error", e.getMessage());
        } finally {
            mysql.close();
        }

        loadSideDish();
        loadExtra();
        loadAutoAdd();

        //checkbox
        chkMSet1.setSelected(isBefore);
        chkMSet2.setSelected(isQtyCheck);
        chkAutoSet.setSelected(isAutoAdd);
        chkExtra.setSelected(isExtraNoLimit);

        txtQty.setText("" + qtyAmt);
    }

    private void deleteDataPCode() {
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            Statement stmt = mysql.getConnection().createStatement();
            stmt.executeUpdate("delete from mgrbuttonsetup where pcode='" + txtPCode.getText() + "'");
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(MGRButtonSetup.class, "error", e.getMessage());
        } finally {
            mysql.close();
        }
    }

    private void SaveData() {
        boolean isBeforeCheck = chkMSet1.isSelected();
        String beforeCheck = "N";
        if (isBeforeCheck) {
            beforeCheck = "Y";
        }

        boolean isQtyCheck = chkMSet2.isSelected();
        String qtyCheck = "N";
        if (isQtyCheck) {
            qtyCheck = "Y";
        }

        //เพิ่มใหม่
        boolean isExtraNoLimit = chkExtra.isSelected();
        String extraCheck = "N";
        if (isExtraNoLimit) {
            extraCheck = "Y";
        }

        int qtyAmt = 0;
        try {
            qtyAmt = Integer.parseInt(txtQty.getText());
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
        }

        boolean isAutoCheck = chkAutoSet.isSelected();
        String autocheck = "N";
        if (isAutoCheck) {
            autocheck = "Y";
        }

        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();

        int size1 = model1.getRowCount();
        for (int i = 0; i < size1; i++) {
            String sdCode = "" + tbSideDishFree.getValueAt(i, 0);
            String sdPDesc = "" + tbSideDishFree.getValueAt(i, 1);
            try {
                String sql = "insert into mgrbuttonsetup"
                        + "(pcode,pdesc,"
                        + "sd_pcode,sd_pdesc,"
                        + "ex_pcode,ex_pdesc,auto_pcode,auto_pdesc,check_before,check_qty,"
                        + "qty,check_autoadd,check_extra) "
                        + "values("
                        + "'" + txtPCode.getText() + "','" + ThaiUtil.Unicode2ASCII(txtPDesc.getText()) + "',"
                        + "'" + sdCode + "','" + ThaiUtil.Unicode2ASCII(sdPDesc) + "',"
                        + "'','','','','" + beforeCheck + "','" + qtyCheck + "',"
                        + "'" + qtyAmt + "','" + autocheck + "',"
                        + "'" + extraCheck + "')";
                Statement stmt = mysql.getConnection().createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
            } catch (SQLException e) {
                MSG.ERR(e.getMessage());
                AppLogUtil.log(MGRButtonSetup.class, "error", e.getMessage());
            }
        }

        int size2 = model2.getRowCount();
        for (int i = 0; i < size2; i++) {
            String exCode = "" + tbExtra.getValueAt(i, 0);
            String exPDesc = "" + tbExtra.getValueAt(i, 1);
            try {
                String sql = "insert into mgrbuttonsetup"
                        + "(pcode,pdesc,"
                        + "sd_pcode,sd_pdesc,"
                        + "ex_pcode,ex_pdesc,auto_pcode,auto_pdesc,check_before,check_qty,"
                        + "qty,check_autoadd,check_extra) "
                        + "values("
                        + "'" + txtPCode.getText() + "','" + ThaiUtil.Unicode2ASCII(txtPDesc.getText()) + "',"
                        + "'','',"
                        + "'" + exCode + "','" + ThaiUtil.Unicode2ASCII(exPDesc) + "',"
                        + "'','','" + beforeCheck + "','" + qtyCheck + "',"
                        + "'" + qtyAmt + "','" + autocheck + "',"
                        + "'" + extraCheck + "')";
                Statement stmt = mysql.getConnection().createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
            } catch (SQLException e) {
                MSG.ERR(e.getMessage());
                AppLogUtil.log(MGRButtonSetup.class, "error", e.getMessage());
            }
        }

        int size3 = model3.getRowCount();
        for (int i = 0; i < size3; i++) {
            String atCode = "" + tbAutoAdd.getValueAt(i, 0);
            String atPDesc = "" + tbAutoAdd.getValueAt(i, 1);
            try {
                String sql = "insert into mgrbuttonsetup"
                        + "(pcode,pdesc,"
                        + "sd_pcode,sd_pdesc,"
                        + "ex_pcode,ex_pdesc,auto_pcode,auto_pdesc,check_before,check_qty,"
                        + "qty,check_autoadd,check_extra) "
                        + "values("
                        + "'" + txtPCode.getText() + "','" + ThaiUtil.Unicode2ASCII(txtPDesc.getText()) + "',"
                        + "'','',"
                        + "'','',"
                        + "'" + atCode + "','" + ThaiUtil.Unicode2ASCII(atPDesc) + "',"
                        + "'" + beforeCheck + "','" + qtyCheck + "',"
                        + "'" + qtyAmt + "','" + autocheck + "',"
                        + "'" + extraCheck + "')";
                Statement stmt = mysql.getConnection().createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
            } catch (SQLException e) {
                MSG.ERR(e.getMessage());
                AppLogUtil.log(MGRButtonSetup.class, "error", e.getMessage());
            }
        }

        mysql.close();
    }

    private void loadSideDish() {
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            int size = model1.getRowCount();
            for (int i = 0; i < size; i++) {
                model1.removeRow(0);
            }

            String sql = "select sd_pcode, sd_pdesc from mgrbuttonsetup "
                    + "where pcode='" + txtPCode.getText() + "' and sd_pcode<>'' "
                    + "order by sd_pcode";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                model1.addRow(new Object[]{
                    rs.getString(1),
                    ThaiUtil.ASCII2Unicode(rs.getString(2))
                });
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(MGRButtonSetup.class, "error", e.getMessage());
        } finally {
            mysql.close();
        }
    }

    private void loadExtra() {
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            int size = model2.getRowCount();
            for (int i = 0; i < size; i++) {
                model2.removeRow(0);
            }

            String sql = "select ex_pcode, ex_pdesc from mgrbuttonsetup "
                    + "where pcode='" + txtPCode.getText() + "' and ex_pcode<>'' "
                    + "order by ex_pcode";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                model2.addRow(new Object[]{
                    rs.getString(1),
                    ThaiUtil.ASCII2Unicode(rs.getString(2))
                });
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(MGRButtonSetup.class, "error", e.getMessage());
        } finally {
            mysql.close();
        }
    }

    private void loadAutoAdd() {
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            int size = model3.getRowCount();
            for (int i = 0; i < size; i++) {
                model3.removeRow(0);
            }

            String sql = "select auto_pcode, auto_pdesc from mgrbuttonsetup "
                    + "where pcode='" + txtPCode.getText() + "' and auto_pcode<>'' "
                    + "order by auto_pcode";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                model3.addRow(new Object[]{
                    rs.getString(1),
                    ThaiUtil.ASCII2Unicode(rs.getString(2))
                });
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(MGRButtonSetup.class, "error", e.getMessage());
        } finally {
            mysql.close();
        }
    }
}
