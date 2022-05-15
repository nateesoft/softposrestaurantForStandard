package com.softpos.floorplan;

import com.softpos.crm.pos.core.controller.MPluController;
import com.softpos.crm.pos.core.controller.MTranController;
import com.softpos.main.program.GetUserAction;
import com.softpos.pos.core.controller.BillControl;
import com.softpos.pos.core.controller.CreditPaymentRec;
import com.softpos.pos.core.controller.POSHWSetup;
import com.softpos.pos.core.controller.PPrint;
import com.softpos.pos.core.controller.PUtility;
import com.softpos.pos.core.controller.PublicVar;
import com.softpos.pos.core.controller.ThaiUtil;
import com.softpos.pos.core.controller.UserRecord;
import com.softpos.pos.core.controller.Value;
import com.softpos.pos.core.model.BillNoBean;
import com.softpos.pos.core.model.MemmaterController;
import com.softpos.pos.core.model.TranRecord;
import convert_utility.text_to_image.TextToImage;
import database.MySQLConnect;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import soft.virtual.KeyBoardDialog;
import util.AppLogUtil;
import util.MSG;

public class RefundBill extends javax.swing.JDialog {

    Date date = new Date();
    TranRecord[] MyArray;
    int ArraySize;
    PPrint prn = new PPrint();
    String BillNo = "";
    Boolean PurseOK = false;
    String PurseTranCode = "";
    Double PurseAmt = 0.0;
    DecimalFormat DecFmt = new DecimalFormat("##,###,##0.00");
    SimpleDateFormat Datefmt = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    SimpleDateFormat Timefmt = new SimpleDateFormat("HH:mm:ss");
    private DefaultTableModel model;
    String macno = "";
    String memcode = "";

    CreditPaymentRec[] CreditArray;
    private BillControl bCon = new BillControl();

    /**
     * Creates new form RefundBill
     */
    public RefundBill(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
        PublicVar.SmpCouponArray = null;
        model = (DefaultTableModel) tblshowplu.getModel();


        /*
         * Column List off Model
         * 1 {Void,Type,Print(Kic),PluCode,Description,Qty,Price,Amount,promotion,Subdisc,
         *   cudisc,time,emp,macno,user 
         */
        tblshowplu.setShowGrid(true);
        tblshowplu.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblshowplu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblshowplu.setRowSelectionAllowed(true);
        tblshowplu.setGridColor(Color.gray);

        JTableHeader header = tblshowplu.getTableHeader();
        //header.setBackground(Color.yellow);
        header.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 14));
        //txtTable.setBorder(javax.swing.border.LineBorder.createBlackLineBorder());

        /* Set Column Size      */
        int[] ColSize = {150, 150, 150, 330, 120, 120, 170};
        for (int i = 0; i < 7; i++) {
            //int vColIndex = 0;
            TableColumn col = tblshowplu.getColumnModel().getColumn(i);
            col.setPreferredWidth(ColSize[i]);
        }
        DecimalFormat DoubleFmt = new DecimalFormat("##,###,##0.00");
        DecimalFormat IntegerFmt = new DecimalFormat("##,###,##0");
        DecimalFormat PersentFmt = new DecimalFormat("#,##0.00%");

        TableColumnModel tcm = tblshowplu.getColumnModel();
        TableTestFormatRenderer r = new TableTestFormatRenderer(IntegerFmt);
        r.setHorizontalAlignment(SwingConstants.RIGHT);
        tcm.getColumn(4).setCellRenderer(r);

        r = new TableTestFormatRenderer(DoubleFmt);
        r.setHorizontalAlignment(SwingConstants.RIGHT);
        tcm.getColumn(5).setCellRenderer(r);

        r = new TableTestFormatRenderer(DoubleFmt);
        r.setHorizontalAlignment(SwingConstants.RIGHT);
        tcm.getColumn(6).setCellRenderer(r);

        InitRefund();
        //txtPurseMsg.setVisible(false);
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
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtBillNo = new javax.swing.JTextField();
        txtMacNo = new javax.swing.JTextField();
        bntOK = new javax.swing.JButton();
        bntNew = new javax.swing.JButton();
        bntExit = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblshowplu = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtTableNo = new javax.swing.JTextField();
        txtNetTotal = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("รายการยกเลิกบิลการขาย");
        setFocusable(false);
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("เลขที่ใบเสร็จรับเงิน");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Mac No/Cashier");

        txtBillNo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtBillNo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBillNoMouseClicked(evt);
            }
        });
        txtBillNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBillNoKeyPressed(evt);
            }
        });

        txtMacNo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtMacNo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMacNo.setFocusable(false);

        bntOK.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bntOK.setText("F5-ตกลง (OK)");
        bntOK.setFocusable(false);
        bntOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntOKActionPerformed(evt);
            }
        });

        bntNew.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bntNew.setText("F4-เลขที่ใหม่ (New)");
        bntNew.setFocusable(false);
        bntNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntNewActionPerformed(evt);
            }
        });

        bntExit.setBackground(new java.awt.Color(255, 153, 153));
        bntExit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bntExit.setText("ESC-ออก (Exit)");
        bntExit.setFocusable(false);
        bntExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntExitActionPerformed(evt);
            }
        });

        tblshowplu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblshowplu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "V", "ETD", "PLU Code", "Description", "Qty", "Price", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblshowplu.setFocusable(false);
        tblshowplu.setOpaque(false);
        tblshowplu.setRowHeight(25);
        jScrollPane2.setViewportView(tblshowplu);

        jPanel1.setBackground(new java.awt.Color(255, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setFocusable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("หมายเลขโต๊ะ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ยอดขายสุทธิ (Net Total Amount)");

        txtTableNo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTableNo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTableNo.setFocusable(false);
        txtTableNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTableNoActionPerformed(evt);
            }
        });
        txtTableNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTableNoKeyPressed(evt);
            }
        });

        txtNetTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtNetTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNetTotal.setFocusable(false);
        txtNetTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtNetTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNetTotalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTableNo, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNetTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNetTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtTableNo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(88, 88, 88)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBillNo, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(jLabel6)
                                .addGap(27, 27, 27)
                                .addComponent(txtMacNo, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 302, Short.MAX_VALUE)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(bntOK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bntExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bntNew, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(9, 9, 9)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6)
                            .addComponent(txtMacNo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBillNo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(bntOK, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bntNew, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bntExit, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 205, Short.MAX_VALUE)))
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

        setSize(new java.awt.Dimension(1040, 457));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

private void txtTableNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTableNoActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_txtTableNoActionPerformed

private void txtNetTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNetTotalActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_txtNetTotalActionPerformed

private void bntNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntNewActionPerformed
    InitRefund();
}//GEN-LAST:event_bntNewActionPerformed

private void txtTableNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTableNoKeyPressed
}//GEN-LAST:event_txtTableNoKeyPressed

private void txtBillNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBillNoKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        LoadDataFromBillNo();
    }
    if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
        bntExitClick();
    }
    if (evt.getKeyCode() == KeyEvent.VK_F5) {
        bntOKClick();
    }
    if (evt.getKeyCode() == KeyEvent.VK_F4) {
        InitRefund();
    }


}//GEN-LAST:event_txtBillNoKeyPressed

    private void bntOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntOKActionPerformed
        bntOKClick();
    }//GEN-LAST:event_bntOKActionPerformed

    private void bntExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntExitActionPerformed
        bntExitClick();
    }//GEN-LAST:event_bntExitActionPerformed

    private void txtBillNoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBillNoMouseClicked
        if (evt.getClickCount() == 2) {
            KeyBoardDialog.get(txtBillNo);
        }
    }//GEN-LAST:event_txtBillNoMouseClicked

    public void bntOKClick() {
        if (LoadDataFromBillNo()) {
            if(checkPermit()){
                if (PUtility.ShowConfirmMsg("ยืนยันการยกเลิกใบเสร็จรับเงินเลขที่ " + BillNo + " Yes/No ?")) {
                    UpdateDatabaseForRefund();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(RefundBill.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    POSHWSetup bean = POSHWSetup.Bean(Value.MACNO);
                    prn.Print_Head_EJ();
                    prn.PrintBillRefund(BillNo);
                    String TempBill = bean.getEJDailyPath() + "/tempbill.txt";
                    String ImageFile = bean.getEJDailyPath() + "/" + PublicVar.Branch_Code + "_" + Value.MACNO + "_RFN" + BillNo + ".gif";
                    TextToImage toImage = new TextToImage();
                    if (!toImage.textToImage(TempBill, ImageFile)) {
                        //MSG.ERR(this, "EJ File Write Error.......");
                    }
                    File fObject2 = new File(TempBill);
                    fObject2.delete();
                    
                    // check member point and remove both table mycrmbranch.mplu and mycrmbranch.mtran
                    BillControl billControl = new BillControl();
                    BillNoBean billBean = billControl.getData(BillNo);
                    
                    String receiptNo = billBean.getB_MacNo()+"/"+billBean.getB_Refno();
                    MPluController mpluControl = new MPluController();
                    mpluControl.refundBill(receiptNo);
                    MTranController mtranControl = new MTranController();
                    mtranControl.refundBill(receiptNo);
                    
                    MemmaterController memControl = new MemmaterController();
                    memControl.updateScoreRefund(billBean.getB_MemCode(), billBean.getB_MemCurSum());
                    
                    PUtility.ShowMsg("การยกเลิกใบเสร็จรับเงินเลขที่ " + BillNo + " เรียบร้อยแล้ว...");
                }
                InitRefund();
            }
        }
    }

    public void UpdateDatabaseForRefund() {
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();

        try {
            String sql = "update billno "
                    + "set b_voiduser='" + PublicVar.TUserRec.UserCode + "',"
                    + "b_voidtime='" + Timefmt.format(date) + "',"
                    + "b_void='V' "
                    + "where b_macno='" + macno + "' "
                    + "and b_refno='" + BillNo + "'";
            Statement stmt = mysql.getConnection().createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(RefundBill.class, "error", e.getMessage());
        }
        
        try {
            String sql = "update t_sale set r_refund='V' "
                    + "where (macno='" + macno + "') "
                    + "and (r_refno='" + BillNo + "')";
            Statement stmt = mysql.getConnection().createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(RefundBill.class, "error", e.getMessage());
        }

        try {
            String sql = "update t_saleset set r_refund='V' "
                    + "where (macno='" + macno + "') "
                    + "and (r_refno='" + BillNo + "')";
            Statement stmt = mysql.getConnection().createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(RefundBill.class, "error", e.getMessage());
        }
        
        try {
            String sql = "update t_cupon set refund='V' "
                    + "where (terminal='" + macno + "') "
                    + "and (r_refno='" + BillNo + "')";
            Statement stmt = mysql.getConnection().createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(RefundBill.class, "error", e.getMessage());
        }

        try {
            String sql = "delete from t_promotion "
                    + "where (terminal='" + macno + "') "
                    + "and (r_refno='" + BillNo + "')";
            Statement stmt = mysql.getConnection().createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(RefundBill.class, "error", e.getMessage());
        }
        
        try {
            String sql = "update t_gift set fat='V'  where (macno='" + macno + "') and (refno='" + BillNo + "')";
            Statement stmt = mysql.getConnection().createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(RefundBill.class, "error", e.getMessage());
        }
        
        try {
            String sql = "delete from accr where (arno='" + PublicVar.Branch_Code + "/" + macno + "/" + BillNo + "')";
            Statement stmt = mysql.getConnection().createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(RefundBill.class, "error", e.getMessage());
        }

        if (!memcode.equals("")) {
            try {
                Statement stmt = mysql.getConnection().createStatement();
                BillNoBean bBean = bCon.getData(BillNo);

                String SqlQuery = "update " + Value.db_member + ".memmaster set "
                        + "m_sum=m_sum-" + bBean.getB_NetTotal() + " "
                        + "where (m_code='" + memcode + "')";
                stmt.executeUpdate(SqlQuery);
                stmt.close();
            } catch (SQLException e) {
                MSG.ERR(e.getMessage());
                AppLogUtil.log(RefundBill.class, "error", e.getMessage());
            }
            
            try {
                String SqlQuery = "delete from mtran where m_billno='" + macno + "/" + BillNo + "'";
                Statement stmt = mysql.getConnection().createStatement();
                stmt.executeUpdate(SqlQuery);
                stmt.close();
            } catch (SQLException e) {
                MSG.ERR(e.getMessage());
                AppLogUtil.log(RefundBill.class, "error", e.getMessage());
            }
            
            try {
                String SqlQuery = "delete from mtranplu where m_billno='" + macno + "/" + BillNo + "'";
                Statement stmt = mysql.getConnection().createStatement();
                stmt.executeUpdate(SqlQuery);
                stmt.close();
            } catch (SQLException e) {
                MSG.ERR(e.getMessage());
                AppLogUtil.log(RefundBill.class, "error", e.getMessage());
            }
        }
        // Return Stock
        try {
            String SqlQuery = "select *from t_sale where (macno='" + macno + "') and (r_refno='" + BillNo + "') and (r_void<>'V')";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rec = stmt.executeQuery(SqlQuery);
            rec.first();
            if (rec.getRow() == 0) {
            } else {
                do {
                    String StkCode = PUtility.GetStkCode();
                    String StkRemark = "SAL";
                    String DocNo = "R" + rec.getString("r_refno");
                    Date TDate = rec.getDate("r_date");
                    String r_index = rec.getString("r_index");
                    PUtility.ProcessStockOut(DocNo, StkCode, rec.getString("r_plucode"), TDate, StkRemark, -1 * rec.getDouble("r_quan"), -1 * rec.getDouble("r_total"), PublicVar.TUserRec.UserCode, rec.getString("r_stock"), rec.getString("r_set"), rec.getString("r_index"), "2");
                } while (rec.next());
            }
            rec.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(RefundBill.class, "error", e.getMessage());
        } finally {
            mysql.close();
        }
    }

    public void Cleartblshowplu() {
        int RowCount = model.getRowCount();
        for (int i = 0; i <= RowCount - 1; i++) {
            model.removeRow(0);
        }
    }

    public void inputfrombnt(String str) {
        String tempstr = "";
        tempstr = txtBillNo.getText();
        tempstr = tempstr + str;
        txtBillNo.setText(tempstr);
    }

    public void bntExitClick() {
        this.dispose();
    }

    public Boolean LoadDataFromBillNo() {
        Boolean RetValue = false;
        BillNo = txtBillNo.getText();
        //Load Data From BillNo
        
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            Statement stmt = mysql.getConnection().createStatement();
            String SqlQuery = "select *from billno where (b_macno='" + Value.MACNO + "') and (b_refno='" + BillNo + "')";
            ResultSet rec = stmt.executeQuery(SqlQuery);
            rec.first();
            if (rec.getRow() == 0) {
                PUtility.ShowMsg("ไม่พบเลขที่ใบเสร็จรับเงินที่ต้องการยกเลิก (Refund) กรุณาตรวจสอบเลขที่ใบเสร็จใหม่...");
                InitRefund();
            } else {
                if (rec.getString("b_void").equals("V")) {
                    PUtility.ShowMsg("ใบเสร็จรับเงินเลขที่ " + BillNo + " ไม่มีการทำรายการยกเลิก (Refund) ไปแล้ว...");
                    InitRefund();

                } else {
                    if (rec.getString("b_invno") != null && !rec.getString("b_invno").equals("")) {
                        PUtility.ShowMsg("ใบเสร็จรับเงินนี้มีการพิมพ์ใบกำกับภาษีเต็มรูปเลขที่ " + rec.getString("b_invno") + " กรุณายกเลิกใบกำกับภาษีเต็มรูปก่อนทำรายการ Refund ");
                        InitRefund();
                    } else {
                        RetValue = true;

                        BillNoBean bBean = bCon.getData(BillNo);

                        txtTableNo.setText(bBean.getB_Table());
                        txtNetTotal.setValue(bBean.getB_NetTotal());
                        txtMacNo.setText(bBean.getB_MacNo() + "/" + bBean.getB_Cashier());
                        macno = rec.getString("b_macno");
                        memcode = rec.getString("b_memcode");
                        LoadDataFromT_Sale();

                        txtBillNo.setFocusable(false);
                        txtMacNo.setFocusable(false);
                        bntOK.requestFocus();
                    }
                }
            }
            rec.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(RefundBill.class, "error", e.getMessage());
            InitRefund();
        }finally{
            mysql.close();
        }

        return RetValue;
    }

    public void LoadDataFromT_Sale() {
        BillNo = txtBillNo.getText();
        
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            Statement stmt = mysql.getConnection().createStatement();
            String LoadBalance = "select *from t_sale where (macno='" + Value.MACNO + "') and (r_refno='" + BillNo + "')";
            ResultSet rec = stmt.executeQuery(LoadBalance);
            Date dt = new Date();
            PublicVar.P_ItemCount = 0;
            MyArray = null;
            MyArray = new TranRecord[1];
            Cleartblshowplu();
            rec.first();
            if (rec.getRow() == 0) {
            } else {
                do {
                    PublicVar.P_ItemCount++;
                    TranRecord TranRec = new TranRecord();
                    TranRec.R_Index = rec.getString("r_index");
                    TranRec.R_Date = rec.getDate("r_date");
                    TranRec.R_Table = rec.getString("r_table");
                    TranRec.R_Time = rec.getString("r_time");
                    TranRec.Macno = rec.getString("macno");
                    TranRec.Cashier = rec.getString("cashier");
                    TranRec.R_Emp = rec.getString("r_emp");
                    TranRec.R_Set = rec.getString("r_set");
                    TranRec.R_Stock = rec.getString("r_stock");
                    TranRec.R_PluCode = rec.getString("r_plucode");
                    TranRec.R_PName = ThaiUtil.ASCII2Unicode(rec.getString("r_pname"));
                    TranRec.R_Unit = rec.getString("r_unit");
                    TranRec.R_Group = rec.getString("r_group");
                    TranRec.R_Status = rec.getString("r_status");
                    TranRec.R_Normal = rec.getString("r_normal");
                    TranRec.R_Discount = rec.getString("r_discount");
                    TranRec.R_Service = rec.getString("r_service");
                    TranRec.R_Vat = rec.getString("r_vat");
                    TranRec.R_Type = rec.getString("r_type");
                    TranRec.R_ETD = rec.getString("r_etd");
                    TranRec.R_Quan = rec.getDouble("r_quan");
                    TranRec.R_Price = rec.getDouble("r_price");
                    TranRec.R_Total = rec.getDouble("r_total");
                    TranRec.R_PrType = rec.getString("r_prtype");
                    TranRec.R_PrCode = rec.getString("r_prcode");
                    TranRec.R_PrDisc = rec.getDouble("r_prdisc");
                    TranRec.R_PrBath = rec.getDouble("r_prbath");
                    TranRec.R_PrAmt = rec.getDouble("r_pramt");
                    TranRec.R_PrQuan = rec.getDouble("r_prquan");
                    TranRec.R_Redule = rec.getDouble("r_redule");
                    TranRec.R_KIC = rec.getString("r_kic");
                    TranRec.R_KicPrint = rec.getString("r_kicprint");
                    TranRec.R_Void = rec.getString("r_void");
                    TranRec.R_VoidTime = rec.getString("r_voidtime");
                    TranRec.R_DiscBath = rec.getDouble("r_discbath");
                    if (PublicVar.P_ItemCount > 1) {
                        MyArray = PUtility.addArray(MyArray);
                    }
                    ArraySize = MyArray.length;
                    MyArray[ArraySize - 1] = TranRec;
                    Object[] input = {rec.getString("r_void"),
                        rec.getString("r_etd"),
                        rec.getString("r_plucode"),
                        ThaiUtil.ASCII2Unicode(rec.getString("r_pname")),
                        rec.getDouble("r_quan"),
                        rec.getDouble("r_price"),
                        rec.getDouble("r_total")
                    };
                    model.addRow(input);
                } while (rec.next());
                int RowCount = model.getRowCount();
                showCell(RowCount - 1, 0);
            }
            rec.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(RefundBill.class, "error", e.getMessage());
        } finally {
            mysql.close();
        }
    }

    public void showCell(int row, int column) {
        if (row > 0) {
            Rectangle rect = tblshowplu.getCellRect(row, column, true);
            tblshowplu.scrollRectToVisible(rect);
            tblshowplu.clearSelection();
            tblshowplu.setRowSelectionInterval(row, row);
            //tblshowplu.getModel().fireTableDataChanged(); // notify the model
        }
    }

    public void InitRefund() {
        txtTableNo.setText("");
        txtBillNo.setText("");
        txtNetTotal.setValue(0);
        txtMacNo.setText("");
//        x_trancode.setText("");
//        x_purseamt.setValue(0);
        txtBillNo.setFocusable(true);
        txtBillNo.requestFocus();
        PurseOK = false;
        PurseTranCode = "";
        PurseAmt = 0.0;
        //txtPurseMsg.setVisible(false);
        Cleartblshowplu();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntExit;
    private javax.swing.JButton bntNew;
    private javax.swing.JButton bntOK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblshowplu;
    private javax.swing.JTextField txtBillNo;
    private javax.swing.JTextField txtMacNo;
    private javax.swing.JFormattedTextField txtNetTotal;
    private javax.swing.JTextField txtTableNo;
    // End of variables declaration//GEN-END:variables

    private boolean checkPermit() {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        boolean isPermit = false;
        try {
            String sql = "select Username, Sale3 "
                    + "from posuser "
                    + "where username='" + Value.USERCODE + "' "
                    + "and Sale2='Y'";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                isPermit = true;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(RefundBill.class, "error", e.getMessage());
        } finally {
            mysql.close();
        }
        
        if (isPermit) {
            return true;
        } else {
            GetUserAction getuser = new GetUserAction(null, true);
            getuser.setVisible(true);

            if (!PublicVar.ReturnString.equals("")) {
                String loginname = PublicVar.ReturnString;
                UserRecord supUser = new UserRecord();
                if (supUser.GetUserAction(loginname)) {
                    if (supUser.Sale2.equals("Y")) {
                        return true;
                    }else{
                        MSG.ERR(this, "รหัสพนักงานนี้ไม่สามารถเข้าใช้งาน...รายการนี้ได้...!!!");
                    }
                }else{
                    MSG.ERR(this, "ไม่สามารถ Load สิทธิ์การใช้งานของผู้ใช้งานคนนี้ได้ ...");
                }
            }
        }
        
        return false;
    }

    public class TableTestFormatRenderer extends DefaultTableCellRenderer {

        private Format formatter;

        public TableTestFormatRenderer(Format formatter) {
            if (formatter == null) {
                throw new NullPointerException();
            }
            this.formatter = formatter;
        }

        @Override
        protected void setValue(Object obj) {
            setText(obj == null ? "" : formatter.format(obj));
        }
    }
}
