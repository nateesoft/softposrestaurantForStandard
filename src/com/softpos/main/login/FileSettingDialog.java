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
import com.softpos.util.DateChooseDialog;
import com.softpos.util.MSG;
import java.awt.Point;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.JFrame;

public class FileSettingDialog extends javax.swing.JDialog {

    private final SimpleDateFormat ShowDatefmt = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

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
        cbListPrinterTest = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        lblItem1 = new javax.swing.JLabel();
        lblItem3 = new javax.swing.JLabel();
        txtItem4 = new javax.swing.JTextField();
        lblItem4 = new javax.swing.JLabel();
        txtItem3 = new javax.swing.JTextField();
        txtItem1 = new javax.swing.JTextField();
        lblItem2 = new javax.swing.JLabel();
        txtItem2 = new javax.swing.JTextField();
        lblItem5 = new javax.swing.JLabel();
        txtItem5 = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        btnExample = new javax.swing.JButton();
        btnAddItem = new javax.swing.JButton();
        btnClearTable = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTableNo = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtCustomerCount = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtDateBill = new javax.swing.JTextField();
        btnCalendar = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        txtMacNo = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtEmpCode = new javax.swing.JTextField();
        btnRandomBillInfo = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        btnPrintTest = new javax.swing.JButton();

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
                    .addComponent(txtMacno)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(38, Short.MAX_VALUE))
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
                "เมนูอาหาร", "ข้อความพิเศษ", "จำนวน", "ราคา", "รวมราคา"
            }
        ));
        jScrollPane1.setViewportView(tbData);

        cbListPrinterTest.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "เมนูอาหารที่สั่ง", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        lblItem1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblItem1.setText("ชื่อเมนูอาหาร");

        lblItem3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblItem3.setText("จำนวน");

        txtItem4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblItem4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblItem4.setText("ราคา");

        txtItem3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtItem1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblItem2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblItem2.setText("ข้อความพิเศษ");

        txtItem2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblItem5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblItem5.setText("ยอดรวม");

        txtItem5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btnExample.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnExample.setText("สุ่มข้อมูล");
        btnExample.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExampleActionPerformed(evt);
            }
        });

        btnAddItem.setBackground(new java.awt.Color(0, 255, 204));
        btnAddItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnAddItem.setText("เพิ่มในบิล");
        btnAddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddItemActionPerformed(evt);
            }
        });

        btnClearTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnClearTable.setText("ล้างข้อมูล");
        btnClearTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearTableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnClearTable, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExample, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddItem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnClearTable, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExample, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(btnAddItem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblItem1)
                    .addComponent(lblItem3)
                    .addComponent(lblItem2)
                    .addComponent(lblItem5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtItem2)
                        .addComponent(txtItem1)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(txtItem3, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                            .addGap(18, 18, 18)
                            .addComponent(lblItem4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtItem4, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtItem5, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblItem1)
                            .addComponent(txtItem1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblItem2)
                            .addComponent(txtItem2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblItem3)
                            .addComponent(txtItem3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblItem4)
                            .addComponent(txtItem4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblItem5)
                            .addComponent(txtItem5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("หมายเลขโต๊ะ");

        txtTableNo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("จำนวนลูกค้า");

        txtCustomerCount.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("วันที่ใบเสร็จ");

        txtDateBill.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btnCalendar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnCalendar.setText("...");
        btnCalendar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalendarActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("MacNo");

        txtMacNo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel19.setText("พนักงาน");

        txtEmpCode.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btnRandomBillInfo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnRandomBillInfo.setText("สุ่มข้อมูล");
        btnRandomBillInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRandomBillInfoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel17)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtTableNo, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(jLabel16))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtMacNo, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                            .addComponent(txtDateBill))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCalendar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel19)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtEmpCode, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                        .addComponent(txtCustomerCount))
                    .addComponent(btnRandomBillInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTableNo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(txtCustomerCount, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtDateBill, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCalendar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(txtEmpCode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtMacNo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRandomBillInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel20.setText("ปริ้นเตอร์ในระบบ");

        btnPrintTest.setBackground(new java.awt.Color(0, 204, 204));
        btnPrintTest.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnPrintTest.setText("ทดสอบพิมพ์");
        btnPrintTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintTestActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbListPrinterTest, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrintTest, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrintTest, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(cbListPrinterTest, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("ทดสอบปริ้นเตอร์ครัว", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
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

    private void btnRandomBillInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRandomBillInfoActionPerformed
        btnRandomInfoBill();
    }//GEN-LAST:event_btnRandomBillInfoActionPerformed

    private void btnCalendarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalendarActionPerformed
        showCalendarPicker();
    }//GEN-LAST:event_btnCalendarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddItem;
    private javax.swing.JButton btnCalendar;
    private javax.swing.JButton btnClearTable;
    private javax.swing.JButton btnExample;
    private javax.swing.JButton btnPrintTest;
    private javax.swing.JButton btnRandomBillInfo;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbDriver;
    private javax.swing.JComboBox cbKicPrint;
    private javax.swing.JComboBox<String> cbListPrinterTest;
    private javax.swing.JComboBox cbPopup;
    private javax.swing.JComboBox cbPrint;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblItem1;
    private javax.swing.JLabel lblItem2;
    private javax.swing.JLabel lblItem3;
    private javax.swing.JLabel lblItem4;
    private javax.swing.JLabel lblItem5;
    private javax.swing.JTable tbData;
    private javax.swing.JTextField txtCharSet;
    private javax.swing.JTextField txtCustomerCount;
    private javax.swing.JTextField txtDatabase;
    private javax.swing.JTextField txtDateBill;
    private javax.swing.JTextField txtEmpCode;
    private javax.swing.JTextField txtItem1;
    private javax.swing.JTextField txtItem2;
    private javax.swing.JTextField txtItem3;
    private javax.swing.JTextField txtItem4;
    private javax.swing.JTextField txtItem5;
    private javax.swing.JTextField txtLang;
    private javax.swing.JTextField txtMacNo;
    private javax.swing.JTextField txtMacno;
    private javax.swing.JTextField txtMember;
    private javax.swing.JTextField txtPass;
    private javax.swing.JTextField txtPort;
    private javax.swing.JTextField txtPrinterDriverName;
    private javax.swing.JTextField txtServer;
    private javax.swing.JTextField txtTableNo;
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
        txtItem2.setText("" + (char) ('A' + (exampleCounter - 1) % 5));
        txtItem3.setText("" + exampleCounter);
        txtItem4.setText(String.format("%.2f", exampleCounter * 50.0));
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

    private void btnRandomInfoBill() {
        txtTableNo.setText("T01");
        txtCustomerCount.setText("2");
        txtDateBill.setText("01/06/2026 12:30");
        txtEmpCode.setText("1001");
        txtMacNo.setText("001");
    }

    private void showCalendarPicker() {
        Point point = btnCalendar.getLocation();
        point.setLocation(point.getX(), point.getY());
        DateChooseDialog dcd = new DateChooseDialog(new JFrame(), true, point);
        dcd.setVisible(true);

        if (dcd.getSelectDate() != null) {
            txtDateBill.setText(ShowDatefmt.format(dcd.getSelectDate().getTime()));
            txtDateBill.requestFocus();
        }
    }
}
