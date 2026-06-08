package com.softpos.main.login;

import com.softpos.connection.database.MySQLConnect;
import com.softpos.constants.PublicVar;
import com.softpos.printer.control.PrinterDriverControl;
import com.softpos.util.FileManager;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.print.PrintService;
import javax.swing.table.DefaultTableModel;
import com.softpos.util.AppLogUtil;
import com.softpos.util.MSG;

public class FileSettingDialog extends javax.swing.JDialog {

    public FileSettingDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        loadConfig();
        loadPrinters();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtServer = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPort = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtPass = new javax.swing.JTextField();
        txtCharSet = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtDatabase = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtMember = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        txtMacno = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtLang = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cbPrint = new javax.swing.JComboBox();
        cbKicPrint = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        cbDriver = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        txtPrinterDriverName = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        cbPopup = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbData = new javax.swing.JTable();
        lblItem1 = new javax.swing.JLabel();
        txtItem1 = new javax.swing.JTextField();
        btnPrintTest = new javax.swing.JButton();
        cbListPrinterTest = new javax.swing.JComboBox<>();
        lblItem2 = new javax.swing.JLabel();
        txtItem2 = new javax.swing.JTextField();
        txtItem3 = new javax.swing.JTextField();
        lblItem3 = new javax.swing.JLabel();
        lblItem4 = new javax.swing.JLabel();
        txtItem4 = new javax.swing.JTextField();
        btnAddItem = new javax.swing.JButton();
        btnClearTable = new javax.swing.JButton();
        btnExample = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("File Configuration");

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("แสดง popup ก่อนสั่งอาหารหรือไม่ ?");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Database Setting"));

        jLabel6.setText("Port");

        txtServer.setText("localhost");
        txtServer.setToolTipText("localhost");
        txtServer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtServerKeyPressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("รหัสผ่าน");

        txtUser.setText("root");
        txtUser.setToolTipText("root");

        jLabel2.setText("Server");

        txtPort.setText("3307");
        txtPort.setToolTipText("3306");

        jLabel7.setText("CharSet");

        txtCharSet.setText("tis-620");
        txtCharSet.setToolTipText("tis-620");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("ชื่อฐานข้อมูล");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("ชื่อผู้ใช้งาน");

        txtDatabase.setText("MyRestaurant_POS");
        txtDatabase.setToolTipText("MyRestaurant_POS");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("ฐานข้อมูลสมาชิก");

        txtMember.setText("MyCRMbranch");
        txtMember.setToolTipText("MyCRMbranch");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel2)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCharSet)
                    .addComponent(txtPort)
                    .addComponent(txtPass)
                    .addComponent(txtUser)
                    .addComponent(txtDatabase)
                    .addComponent(txtServer, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMember)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtDatabase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtCharSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtMember, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Terminal Setting"));

        txtMacno.setText("001");
        txtMacno.setToolTipText("001");

        jLabel9.setText("Macno");

        txtLang.setText("th");
        txtLang.setToolTipText("th");

        jLabel8.setText("Language");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMacno, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .addComponent(txtLang))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtLang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtMacno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Printer Setting"));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("ให้มีการพิมพ์ หรือไม่ ?");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("ให้ออกปริ้นเตอร์ครัวหรือไม่ ?");

        cbPrint.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "false", "true" }));

        cbKicPrint.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "false", "true" }));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Printer Dirver");

        cbDriver.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "false", "true" }));

        jLabel15.setText("Printer Name");

        txtPrinterDriverName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPrinterDriverName.setText("SoftPrint");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbPrint, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbKicPrint, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbDriver, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrinterDriverName)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cbPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cbKicPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(cbDriver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtPrinterDriverName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton1.setText("บันทึก");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton2.setText("ยกเลิก");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        cbPopup.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "false", "true" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbPopup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(cbPopup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("กำหนดค่าการใช้งานระบบ", jPanel4);

        tbData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Column 1", "Column 2", "Column 3", "Column 4"
            }
        ));
        jScrollPane1.setViewportView(tbData);

        lblItem1.setText("Column 1");

        btnPrintTest.setBackground(new java.awt.Color(51, 102, 255));
        btnPrintTest.setText("Print Test");
        btnPrintTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintTestActionPerformed(evt);
            }
        });

        cbListPrinterTest.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblItem2.setText("Column 2");

        lblItem3.setText("Column 3");

        lblItem4.setText("Column 4");

        btnAddItem.setBackground(new java.awt.Color(0, 255, 204));
        btnAddItem.setText("Add Item");
        btnAddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddItemActionPerformed(evt);
            }
        });

        btnClearTable.setText("Clear Table");
        btnClearTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearTableActionPerformed(evt);
            }
        });

        btnExample.setText("Example");
        btnExample.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExampleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(cbListPrinterTest, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrintTest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(lblItem2)
                                .addGap(18, 18, 18)
                                .addComponent(txtItem2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(lblItem3)
                                .addGap(18, 18, 18)
                                .addComponent(txtItem3, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(lblItem4)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtItem4, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(lblItem1)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtItem1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnClearTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnExample, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddItem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblItem1)
                    .addComponent(txtItem1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExample))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblItem2)
                    .addComponent(txtItem2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblItem3)
                    .addComponent(txtItem3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblItem4)
                    .addComponent(txtItem4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddItem)
                    .addComponent(btnClearTable))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrintTest)
                    .addComponent(cbListPrinterTest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("ทดสอบปริ้นเตอร์", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        save();
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtServerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtServerKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            dispose();
        }
    }//GEN-LAST:event_txtServerKeyPressed

    private void btnPrintTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintTestActionPerformed
        btnPrintTestActionPerformed();
    }//GEN-LAST:event_btnPrintTestActionPerformed

    private void btnClearTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearTableActionPerformed
        btnClearTableActionPerformed();
    }//GEN-LAST:event_btnClearTableActionPerformed

    private void btnAddItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddItemActionPerformed
        btnAddItemActionPerformed();
    }//GEN-LAST:event_btnAddItemActionPerformed

    private void btnExampleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExampleActionPerformed
        btnExampleActionPerformed();
    }//GEN-LAST:event_btnExampleActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddItem;
    private javax.swing.JButton btnClearTable;
    private javax.swing.JButton btnExample;
    private javax.swing.JButton btnPrintTest;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbDriver;
    private javax.swing.JComboBox cbKicPrint;
    private javax.swing.JComboBox<String> cbListPrinterTest;
    private javax.swing.JComboBox cbPopup;
    private javax.swing.JComboBox cbPrint;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblItem1;
    private javax.swing.JLabel lblItem2;
    private javax.swing.JLabel lblItem3;
    private javax.swing.JLabel lblItem4;
    private javax.swing.JTable tbData;
    private javax.swing.JTextField txtCharSet;
    private javax.swing.JTextField txtDatabase;
    private javax.swing.JTextField txtItem1;
    private javax.swing.JTextField txtItem2;
    private javax.swing.JTextField txtItem3;
    private javax.swing.JTextField txtItem4;
    private javax.swing.JTextField txtLang;
    private javax.swing.JTextField txtMacno;
    private javax.swing.JTextField txtMember;
    private javax.swing.JTextField txtPass;
    private javax.swing.JTextField txtPort;
    private javax.swing.JTextField txtPrinterDriverName;
    private javax.swing.JTextField txtServer;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables

    private void loadPrinters() {
        cbListPrinterTest.removeAllItems();
        for (PrintService ps : PrinterJob.lookupPrintServices()) {
            cbListPrinterTest.addItem(ps.getName());
        }
    }

    private void btnAddItemActionPerformed() {
        DefaultTableModel model = (DefaultTableModel) tbData.getModel();
        model.addRow(new Object[]{
            txtItem1.getText(),
            txtItem2.getText(),
            txtItem3.getText(),
            txtItem4.getText()
        });
        txtItem1.setText("");
        txtItem2.setText("");
        txtItem3.setText("");
        txtItem4.setText("");
        txtItem1.requestFocus();
    }

    private void btnClearTableActionPerformed() {
        ((DefaultTableModel) tbData.getModel()).setRowCount(0);
        exampleCounter = 1;
    }

    private static final String[] SAMPLE_ITEMS = {
        "ข้าวผัดกุ้ง", "ต้มยำกุ้ง", "ผัดไทย", "แกงเขียวหวาน", "ยำวุ้นเส้น",
        "ข้าวมันไก่", "ผัดกะเพรา", "ต้มข่าไก่", "ผัดซีอิ้ว", "ข้าวหมูแดง"
    };
    private int exampleCounter = 1;

    private void btnExampleActionPerformed() {
        String name = SAMPLE_ITEMS[(exampleCounter - 1) % SAMPLE_ITEMS.length];
        txtItem1.setText(name + " " + exampleCounter);
        txtItem2.setText("หมวด " + (char) ('A' + (exampleCounter - 1) % 5));
        txtItem3.setText("" + exampleCounter);
        txtItem4.setText(String.format("%.2f", exampleCounter * 50.0));
        exampleCounter++;
    }

    private void btnPrintTestActionPerformed() {
        String printerName = (String) cbListPrinterTest.getSelectedItem();
        AppLogUtil.info("PrintDriver PrintTest: printer=[" + printerName + "]");
        if (printerName == null) {
            MSG.NOTICE(this, "กรุณาเลือก Printer ก่อน");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tbData.getModel();
        if (model.getRowCount() == 0) {
            MSG.NOTICE(this, "กรุณาเพิ่มข้อมูลใน Table ก่อน");
            return;
        }

        AppLogUtil.info("PrintDriver PrintTest: rows=" + model.getRowCount());
        PrinterDriverControl pd = new PrinterDriverControl();
        pd.addTextLn("<b>=== Print Test ===</b>");
        pd.addTextLn("----------------------------------");

        for (int row = 0; row < model.getRowCount(); row++) {
            StringBuilder sb = new StringBuilder();
            for (int col = 0; col < model.getColumnCount(); col++) {
                Object val = model.getValueAt(row, col);
                sb.append(val != null ? val.toString() : "").append("  ");
            }
            pd.addTextLn(sb.toString().trim());
        }

        pd.addTextLn("----------------------------------");
        pd.printHTMLKitChenByKictran(printerName);
        MSG.NOTICE(this, "ส่งงานพิมพ์ไปที่ [" + printerName + "] แล้ว\nดูผลใน log หากไม่มีงานออกมา");
    }

    private void save() {
        String fileConfig = PublicVar.FILE_CONFIG;
        String fileBackup = PublicVar.FILE_CONFIG + ".bak";

        // copy file
        FileManager.copyFileUsingStream(new File(fileConfig), new File(fileBackup));
        try {
            File file = new File(fileConfig);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write("server," + txtServer.getText());
            writer.newLine();
            writer.write("database," + txtDatabase.getText());
            writer.newLine();
            writer.write("db_member," + txtMember.getText());
            writer.newLine();
            writer.write("user," + txtUser.getText());
            writer.newLine();
            writer.write("pass," + txtPass.getText());
            writer.newLine();
            writer.write("port," + txtPort.getText());
            writer.newLine();
            writer.write("charset," + txtCharSet.getText());
            writer.newLine();
            writer.write("language," + txtLang.getText());
            writer.newLine();
            writer.write("macno," + txtMacno.getText());
            writer.newLine();
            writer.write("useprint," + cbPrint.getSelectedItem());
            writer.newLine();
            writer.write("printkic," + cbKicPrint.getSelectedItem());
            writer.newLine();
            writer.write("autoqty," + cbPopup.getSelectedItem());
            writer.newLine();
            writer.write("printdriver," + cbDriver.getSelectedItem());
            writer.newLine();
            writer.write("printerName," + txtPrinterDriverName.getText());
            writer.newLine();
            writer.flush();
            writer.close();

            MSG.NOTICE(this, "กรุณาออกจากระบบ และเริ่มต้นใหม่อีกครั้ง !");
        } catch (IOException e) {
            AppLogUtil.log(FileSettingDialog.class, "error", e);
        }
    }

    private void loadConfig() {
        txtServer.setText(MySQLConnect.HostName);
        txtDatabase.setText(MySQLConnect.DbName);
        txtUser.setText(MySQLConnect.UserName);
        txtPass.setText(MySQLConnect.Password);
        txtPort.setText(MySQLConnect.PortNumber);
        txtCharSet.setText(MySQLConnect.CharSet);
        txtLang.setText(PublicVar.LANG);
        txtMacno.setText(PublicVar.MACNO);
        cbPrint.setSelectedItem("" + PublicVar.useprint);
        cbKicPrint.setSelectedItem("" + PublicVar.printkic);
        cbPopup.setSelectedItem("" + PublicVar.autoqty);
        cbDriver.setSelectedItem("" + PublicVar.printdriver);
        txtMember.setText(PublicVar.db_member);
        txtPrinterDriverName.setText(PublicVar.printerDriverName);
    }
}
