package com.softpos.posreport;

import com.softpos.pos.core.model.POSHWSetup;
import com.softpos.pos.core.controller.PPrint;
import com.softpos.pos.core.controller.PUtility;
import com.softpos.crm.pos.core.modal.PublicVar;
import com.softpos.pos.core.controller.Value;
import database.MySQLConnect;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;
import soft.virtual.KeyBoardDialog;
import util.DateChooseDialog;
import util.MSG;

public class MTDArPayment extends javax.swing.JDialog {

    SimpleDateFormat Datefmt = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    SimpleDateFormat DatefmtShow = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    SimpleDateFormat Timefmt = new SimpleDateFormat("HH:mm:ss");
    Date date = new Date();
    Date TDate1 = new Date();
    Date TDate2 = new Date();
    PPrint prn = new PPrint();
    SimpleDateFormat DatefmtThai = new SimpleDateFormat("dd/MM/yyyy (HH:mm)", Locale.ENGLISH);
    SimpleDateFormat ShowDatefmt = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    DecimalFormat DecFmt = new DecimalFormat("##,###,##0.00");
    DecimalFormat IntFmt = new DecimalFormat("##,###,##0");
    private POSHWSetup POSHW;

    /**
     * Creates new form MTDArPayment
     */
    public MTDArPayment(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        txtDate1.setText(DatefmtShow.format(date));
        txtDate2.setText(DatefmtShow.format(date));
        InitScreen();

        POSHW = POSHWSetup.Bean(Value.MACNO);
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
        bntExit = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtDate1 = new javax.swing.JFormattedTextField();
        txtDate2 = new javax.swing.JFormattedTextField();
        cmdDateChoose1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        cmdDateChoose2 = new javax.swing.JButton();
        bntOK = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("รายงานการรับชำระลูกหนี้ภายนอก (MTD AR Payment Report)");
        setUndecorated(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        bntExit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        bntExit.setText("ESC- ออก");
        bntExit.setFocusable(false);
        bntExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntExitActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ช่วงวันที่ๆ ต้องการ (Date)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        txtDate1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtDate1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDate1MouseClicked(evt);
            }
        });
        txtDate1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate1KeyPressed(evt);
            }
        });

        txtDate2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtDate2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDate2MouseClicked(evt);
            }
        });
        txtDate2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate2KeyPressed(evt);
            }
        });

        cmdDateChoose1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Calendar.png"))); // NOI18N
        cmdDateChoose1.setFocusable(false);
        cmdDateChoose1.setRequestFocusEnabled(false);
        cmdDateChoose1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDateChoose1ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("ถึง");

        cmdDateChoose2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Calendar.png"))); // NOI18N
        cmdDateChoose2.setFocusable(false);
        cmdDateChoose2.setRequestFocusEnabled(false);
        cmdDateChoose2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDateChoose2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdDateChoose1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDate2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdDateChoose2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdDateChoose1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtDate2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdDateChoose2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bntOK.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        bntOK.setText("F5- พิมพ์");
        bntOK.setFocusable(false);
        bntOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntOKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(bntOK, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(bntExit, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bntExit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bntOK, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

private void txtDate1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate1KeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
        bntExitClick();
    }
    if (evt.getKeyCode() == KeyEvent.VK_F5) {
        bntOKClick();
    }
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        txtDate2.requestFocus();
    }
}//GEN-LAST:event_txtDate1KeyPressed

private void txtDate2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate2KeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
        bntExitClick();
    }
    if (evt.getKeyCode() == KeyEvent.VK_F5) {
        bntOKClick();
    }
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        txtDate1.requestFocus();
    }
}//GEN-LAST:event_txtDate2KeyPressed

private void cmdDateChoose1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDateChoose1ActionPerformed
    Point point = cmdDateChoose2.getLocation();
    point.setLocation(point.getX(), point.getY());
    DateChooseDialog dcd = new DateChooseDialog(new Frame(), true, cmdDateChoose1.getLocationOnScreen());
    dcd.setVisible(true);
    // dcd.showDialog(new LookAndFeelFrame(), true, point);
    txtDate1.setText(ShowDatefmt.format(dcd.getSelectDate().getTime()));
    txtDate1.requestFocus();
}//GEN-LAST:event_cmdDateChoose1ActionPerformed

private void cmdDateChoose2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDateChoose2ActionPerformed
    Point point = cmdDateChoose2.getLocation();
    point.setLocation(point.getX(), point.getY());
    DateChooseDialog dcd = new DateChooseDialog(new Frame(), true, cmdDateChoose1.getLocationOnScreen());
    dcd.setVisible(true);
    // dcd.showDialog(new LookAndFeelFrame(), true, point);
    txtDate2.setText(ShowDatefmt.format(dcd.getSelectDate().getTime()));
    txtDate2.requestFocus();
}//GEN-LAST:event_cmdDateChoose2ActionPerformed

    private void txtDate1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDate1MouseClicked
        if (evt.getClickCount() == 2) {
            KeyBoardDialog.get(txtDate1);
        }
    }//GEN-LAST:event_txtDate1MouseClicked

    private void txtDate2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDate2MouseClicked
        if (evt.getClickCount() == 2) {
            KeyBoardDialog.get(txtDate2);
        }
    }//GEN-LAST:event_txtDate2MouseClicked

    public void InitScreen() {

        txtDate1.requestFocus();
    }

    public void inputfrombnt(String str) {

        if (txtDate1.hasFocus()) {
            String tempstr = "";
            tempstr = txtDate1.getText();
            tempstr = tempstr + str;
            txtDate1.setText(tempstr);
        }
        if (txtDate2.hasFocus()) {
            String tempstr = "";
            tempstr = txtDate2.getText();
            tempstr = tempstr + str;
            txtDate2.setText(tempstr);
        }

    }

    public void bntExitClick() {
        this.dispose();
    }

    public void bntOKClick() {
        String TDate = txtDate1.getText();

        try {
            TDate1 = DatefmtShow.parse(txtDate1.getText());
            TDate2 = DatefmtShow.parse(txtDate2.getText());
            if (!PUtility.ChkValidDate(TDate1)) {
                txtDate1.requestFocus();
            }
            if (!PUtility.ChkValidDate(TDate2)) {
                txtDate2.requestFocus();
            }
            ProcessProc();
            InitScreen();
        } catch (ParseException ex) {
            MSG.ERR(this, "กรุณาป้อนวันที่ให้ถูกต้อง (Format=dd/MM/yyyy EXP 01/01/2009)");
        }
    }

    public void ProcessProc() {

        if (Value.printdriver) {
            JOptionPane.showMessageDialog(null, Value.driverNotSupport);
        } else if (!Value.getComPort().equals("NONE")) {
            if (prn.OpenPrint(Value.getComPort())) {
                Double SumAmt = 0.0;
                prn.print(POSHW.getHeading1());
                prn.print(POSHW.getHeading2());
                prn.print(POSHW.getHeading3());
                prn.print(POSHW.getHeading4());
                prn.print("REG ID :" + Value.MACNO);
                prn.print("    รายงานการรับชำระจากลูกหนี้ภายนอก ");
                prn.print("      (MTD AR Payment Report)");
                prn.print("ช่วงวันที่  : " + DatefmtShow.format(TDate1) + "..." + DatefmtShow.format(TDate2));
                prn.print(" ");
                Date dateP = new Date();
                prn.print(DatefmtThai.format(dateP) + " " + "Cashier:" + PublicVar._User + " Mac:" + Value.MACNO);
                prn.print("----------------------------------------");
                prn.print("AR Code    เลขที่ใบเสร็จรับเงิน/วันที่  จำนวนเงิน");
                prn.print("----------------------------------------");
                /**
                 * * OPEN CONNECTION **
                 */
                MySQLConnect mysql = new MySQLConnect();
                mysql.open();
                try {
                    Statement stmt = mysql.getConnection().createStatement();
                    String SqlQuery = "select * from s_tar where (fat<>'V') and (s_date>='" + Datefmt.format(TDate1) + "') and (s_date<='" + Datefmt.format(TDate2) + "')";
                    ResultSet rs = stmt.executeQuery(SqlQuery);
                    while (rs.next()) {
                        prn.print(PUtility.DataFull(rs.getString("arcode"), 4) + "  " + rs.getString("billno") + "  " + ShowDatefmt.format(rs.getDate("billdate")) + PUtility.DataFull(DecFmt.format(rs.getDouble("amount")), 9));
                        SumAmt = SumAmt + rs.getDouble("amount");
                    }
                    rs.close();
                    stmt.close();
                } catch (SQLException e) {
                    MSG.ERR(e.getMessage());
                }
                prn.print("----------------------------------------");
                prn.print(PUtility.DataFullR("Total Amount  ", 26) + PUtility.DataFull(DecFmt.format(SumAmt), 13));
                prn.print("----------------------------------------");
                prn.print("");
                Double SumCash = 0.0;
                Double SumCupon = 0.0;
                int CntBill = 0;
                try {
                    Statement stmt = mysql.getConnection().createStatement();
                    String SqlQuery = "select * from s_billar where (fat<>'V') and (s_date>='" + Datefmt.format(TDate1) + "') and (s_date<='" + Datefmt.format(TDate2) + "')";
                    ResultSet rs = stmt.executeQuery(SqlQuery);
                    while (rs.next()) {
                        CntBill++;
                        SumCash = SumCash + rs.getDouble("cash");
                        SumCupon = SumCupon + rs.getDouble("cupon");
                    }
                    rs.close();
                    stmt.close();
                } catch (SQLException e) {
                    MSG.ERR(e.getMessage());
                }
                prn.print(PUtility.DataFullR("     เงินสด Cash              ", 26) + PUtility.DataFull(DecFmt.format(SumCash), 13));
                prn.print(PUtility.DataFullR("     บัตรกำนัล Coupon          ", 26) + PUtility.DataFull(DecFmt.format(SumCupon), 13));
                try {
                    Statement stmt = mysql.getConnection().createStatement();
                    String SqlQuery = "select * from s_tcrar where (fat<>'V') and (s_date>='" + Datefmt.format(TDate1) + "') and (s_date<='" + Datefmt.format(TDate2) + "') group by crcode";
                    ResultSet rs = stmt.executeQuery(SqlQuery);
                    while (rs.next()) {
                        prn.print(PUtility.DataFullR(PUtility.SeekCreditName(rs.getString("crcode") + "                "), 20) + PUtility.DataFull(IntFmt.format(rs.getInt("crcnt")), 6) + PUtility.DataFull(DecFmt.format(rs.getDouble("cramt")), 13));
                    }
                    rs.close();
                    stmt.close();
                } catch (SQLException e) {
                    MSG.ERR(e.getMessage());
                }
                prn.print("ยอดรับชำระ AR    : " + PUtility.DataFull(IntFmt.format(CntBill), 6));
                prn.print("----------------------------------------");
                prn.print(" ");
                prn.print("ยอดยกเลิกรายการ การรับชำระจากลูกหนี้ภายนอก");
                prn.print("AR Pay-No    Amount  Mac  User User Void ");
                prn.print("----------------------------------------");
                try {
                    Statement stmt = mysql.getConnection().createStatement();
                    String SqlQuery = "select * from s_billar where (fat='V') and (s_date>='" + Datefmt.format(TDate1) + "') and (s_date<='" + Datefmt.format(TDate2) + "')";
                    ResultSet rs = stmt.executeQuery(SqlQuery);
                    while (rs.next()) {
                        prn.print(rs.getString("ref_no") + "  " + PUtility.DataFull(DecFmt.format(rs.getDouble("stotal")), 9) + "  " + rs.getString("terminal") + "  " + PUtility.DataFull(rs.getString("cashier"), 6) + "  " + PUtility.DataFull(rs.getString("uservoid"), 6));
                    }
                    rs.close();
                    stmt.close();
                } catch (SQLException e) {
                    MSG.ERR(e.getMessage());
                }

                mysql.close();
                prn.print("----------------------------------------");
                prn.print(" ");
                prn.print(" ");
                prn.print(" ");
                prn.print(" ");
                prn.print(" ");
                prn.print(" ");
                prn.CutPaper();
                prn.closePrint();
            } else {
//                MSG.ERR("เครื่องพิมพ์ใบกำกับภาษีไม่สามารถพิมพ์ได้ ...");
            }
        }

        InitScreen();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntExit;
    private javax.swing.JButton bntOK;
    private javax.swing.JButton cmdDateChoose1;
    private javax.swing.JButton cmdDateChoose2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JFormattedTextField txtDate1;
    private javax.swing.JFormattedTextField txtDate2;
    // End of variables declaration//GEN-END:variables

}
