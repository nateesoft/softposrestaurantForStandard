package com.softpos.posreport;

import com.softpos.pos.core.controller.POSHWSetup;
import com.softpos.pos.core.controller.PPrint;
import com.softpos.pos.core.controller.PUtility;
import com.softpos.crm.pos.core.modal.PublicVar;
import com.softpos.pos.core.controller.Value;
import database.MySQLConnect;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import printReport.PrintDriver;
import soft.virtual.KeyBoardDialog;
import util.DateChooseDialog;
import util.DateConvert;
import util.MSG;

public class MTDCredit extends javax.swing.JDialog {

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
    private String Space = " &nbsp; ";
    private String TAB = Space + Space + Space;

    public MTDCredit(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        txtDate1.setText(DatefmtShow.format(date));
        txtDate2.setText(DatefmtShow.format(date));
        InitScreen();

        POSHW = POSHWSetup.Bean(Value.getMacno());
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
        bntOK = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtDate1 = new javax.swing.JFormattedTextField();
        txtDate2 = new javax.swing.JFormattedTextField();
        cmdDateChoose1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cmdDateChoose2 = new javax.swing.JButton();
        bntExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("รายงานการรับชำระด้วยบัตรเครดิต (MTD Credit Report)");
        setUndecorated(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        bntOK.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        bntOK.setText("F5- พิมพ์");
        bntOK.setFocusable(false);
        bntOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntOKActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ช่วงวันที่ๆ ต้องการ (Date)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        txtDate1.setEditable(false);
        txtDate1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDate1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
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

        txtDate2.setEditable(false);
        txtDate2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDate2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
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

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("ถึง");

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
                .addComponent(txtDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdDateChoose1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDate2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdDateChoose2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdDateChoose1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtDate2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdDateChoose2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bntExit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        bntExit.setText("ESC- ออก");
        bntExit.setFocusable(false);
        bntExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntExitActionPerformed(evt);
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
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addComponent(bntOK, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(bntExit, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

private void bntExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntExitActionPerformed
    bntExitClick();
}//GEN-LAST:event_bntExitActionPerformed

private void bntOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntOKActionPerformed
    bntOKClick();
}//GEN-LAST:event_bntOKActionPerformed

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
                return;
            }
            if (!PUtility.ChkValidDate(TDate2)) {
                txtDate2.requestFocus();
                return;
            }
            ProcessProc();
            InitScreen();
        } catch (ParseException ex) {
            MSG.ERR(this, "กรุณาป้อนวันที่ให้ถูกต้อง (Format=dd/MM/yyyy EXP 01/01/2009)");
        }
    }

    public void ProcessProc() {
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            Statement stmt = mysql.getConnection().createStatement();
            String SqlQuery = "delete from tempcredit where terminal='" + Value.MACNO + "'";
            stmt.executeUpdate(SqlQuery);
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        }

        try {
            Statement stmt = mysql.getConnection().createStatement();
            DateConvert dc = new DateConvert();
            String SqlQuery = "select B_Refno,B_CrCode1 crcode, B_CardNo1 crid, B_AppCode1 crapp, B_CrAmt1 cramt "
                    + "from s_invoice "
                    + "where s_date between'" + dc.dateDatabase(txtDate1.getText()) + "' and '" + dc.dateDatabase(txtDate2.getText())
                    + "' and (B_CrAmt1<>'0' and (B_Void<>'V')) ";
            ResultSet rs = stmt.executeQuery(SqlQuery);
            while(rs.next()){
                String TCrCode = rs.getString("crcode");
                    String TCrId = rs.getString("crid");
                    String TCrApp = rs.getString("crapp");
                    Double TCrAmt = rs.getDouble("cramt");
                    InsertTemp(TCrCode, TCrId, TCrApp, TCrAmt);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        }

        if (Value.printdriver) {
            MTDPrintCreditDriver();
        } else {
            if (!Value.getComPort().equals("NONE")) {
                if (prn.OpenPrint(Value.getComPort())) {
                    prn.InitPrinter();
                    prn.print(POSHW.getHeading1());
                    prn.print(POSHW.getHeading2());
                    prn.print(POSHW.getHeading3());
                    prn.print(POSHW.getHeading4());
                    prn.print("REG ID :" + Value.MACNO);
                    prn.print("       รายงานการรับชำระด้วยบัตรเครดิต");
                    prn.print("          (MTD Credit Report)");
                    prn.print("ช่วงวันที่  :" + DatefmtShow.format(TDate1) + " ..." + DatefmtShow.format(TDate2));
                    prn.print(" ");
                    Date dateP = new Date();
                    prn.print(DatefmtThai.format(dateP) + " " + "Cashier:" + PublicVar._User + " Mac:" + Value.MACNO);
                    prn.print("----------------------------------------");
                    prn.print("ประเภทบัตร    ชื่อบัตรเครดิต");
                    prn.print("ลำดับ  หมายเลขบัตร     รหัสอนุมัติ    จำนวนเงิน");
                    prn.print("----------------------------------------");
                    String TempCr;
                    int SumCard = 0;
                    Double SumCardAmt = 0.0;
                    int SumTotal = 0;
                    Double SumTotalAmt = 0.0;
                    try {
                        Statement stmt = mysql.getConnection().createStatement();
                        String SqlQuery = "select * from tempcredit where (terminal='" + Value.MACNO + "') order by crcode";
                        ResultSet rs = stmt.executeQuery(SqlQuery);
                        while(rs.next()){
                            prn.print(rs.getString("crcode") + "   " + PUtility.SeekCreditName(rs.getString("crcode")));
                            TempCr = rs.getString("crcode");
                            
                            if (!rs.getString("crcode").equals(TempCr)) {
                                    prn.print("       Total Slip " + PUtility.DataFull(IntFmt.format(SumCard), 6) + "    " + PUtility.DataFull(DecFmt.format(SumCardAmt), 11));
                                    prn.print("                 " + "-----------------------");
                                    SumCard = 0;
                                    SumCardAmt = 0.0;
                                    prn.print(rs.getString("crcode") + "   " + PUtility.SeekCreditName(rs.getString("crcode")));
                                    TempCr = rs.getString("crcode");
                                }
                                SumCard++;
                                SumCardAmt = SumCardAmt + rs.getDouble("cramt");
                                SumTotal++;
                                SumTotalAmt = SumTotalAmt + rs.getDouble("cramt");
                                String TempCrid = PUtility.Addzero(rs.getString("crid"), 16);
                                prn.print(PUtility.DataFull(IntFmt.format(SumCard), 5) + "  " + "XXXXXXXXXXXX" + TempCrid.substring(12, 16) + " " + PUtility.DataFullR(rs.getString("crapp"), 6) + PUtility.DataFull(DecFmt.format(rs.getDouble("cramt")), 10));
                        }
                        rs.close();
                        stmt.close();
                    } catch (SQLException e) {
                        MSG.ERR(e.getMessage());
                    }
                    if (SumCard > 0) {
                        prn.print("       Total Slip " + PUtility.DataFull(IntFmt.format(SumCard), 6) + "    " + PUtility.DataFull(DecFmt.format(SumCardAmt), 12));
                        prn.print("                 " + "-----------------------");
                    }
                    prn.print("----------------------------------------");
                    prn.print("Sum-Total Slip  " + PUtility.DataFull(IntFmt.format(SumTotal), 6) + "    " + PUtility.DataFull(DecFmt.format(SumTotalAmt), 14));
                    prn.print("----------------------------------------");
                    prn.print(" ");
                    prn.print(" ");
                    prn.print(" ");

                    prn.CutPaper();
                    prn.closePrint();
                }
            }
            InitScreen();
        }
    }

    public void MTDPrintCreditDriver() {
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        String t = "";
        if (POSHW.getHeading1().length() >= 18) {
            String[] strs = POSHW.getHeading1().trim().replace(" ", Space).split("_");
            for (String data : strs) {
                t += "colspan=3 align=center><font face=Angsana New size=1>" + data + "_";
            }
        } else {
            t += "colspan=3 align=center><font face=Angsana New size=1>" + POSHW.getHeading1().trim().replace(" ", "&nbsp; ") + "_";
        }
        if (POSHW.getHeading2().trim().length() >= 18) {
            String[] strs = POSHW.getHeading2().trim().replace(" ", Space).split("_");
            for (String data : strs) {
                t += "colspan=3 align=center><font face=Angsana New size=1>" + data + "_";
            }
        } else {
            t += "colspan=3 align=center><font face=Angsana New size=1>" + POSHW.getHeading2().trim().replace(" ", "&nbsp; ") + "_";
        }
        t += "colspan=3 align=center><font face=Angsana New size=1>" + (POSHW.getHeading3()).trim() + "_";
        t += "colspan=3 align=center><font face=Angsana New size=1>" + (POSHW.getHeading4()).trim() + "_";
        t += ("colspan=3 align=center><font face=Angsana New size=1>_");
        t += "colspan=3 align=center><font face=Angsana New size=1>" + ("รายงานการรับชำระด้วยบัตรเครดิต" + "_");
        t += "colspan=3 align=center><font face=Angsana New size=1>" + ("(MTD Credit Report)") + "_";
        t += "colspan=3 align=left><font face=Angsana New size=1>" + ("ช่วงวันที่  :" + DatefmtShow.format(TDate1) + " ..." + DatefmtShow.format(TDate2)) + "_";
        Date dateP = new Date();
        t += "colspan=3 align=center><font face=Angsana New size=1>" + "Print Date:" + Space + (DatefmtThai.format(dateP) + Space + "Cashier:" + PublicVar._User + Space + "Mac:" + Value.MACNO) + "_";
        t += "colspan=3 align=center><font face=Angsana New size=1>" + ("-----------------------------------------------------") + "_";
        t += "colspan=3 align=left><font face=Angsana New size=1>" + ("ประเภทบัตร" + Space + "ชื่อบัตรเครดิต") + "_";
        t += "colspan=2 align=left><font face=Angsana New size=1>" + ("ลำดับ" + TAB + "หมายเลขบัตร" + "</td><td align=right><font face=Angsana New size=1>" + "รหัสอนุมัติ" + TAB + "จำนวนเงิน") + "_";
        t += "colspan=3 align=center><font face=Angsana New size=1>" + ("-----------------------------------------------------") + "_";
        int SumVoid = 0;
        String TempCr = "";
        int SumCard = 0;
        Double SumCardAmt = 0.0;
        int SumTotal = 0;
        Double SumTotalAmt = 0.0;
        try {
            Statement stmt = mysql.getConnection().createStatement();
            String SqlQuery = "select * from tempcredit order by crcode";
            ResultSet rs = stmt.executeQuery(SqlQuery);
            while(rs.next()){
                t += "colspan=3 align=left><font face=Angsana New size=1>" + (rs.getString("crcode") + Space + PUtility.SeekCreditName(rs.getString("crcode"))) + "_";
                TempCr = rs.getString("crcode");
                
                if (!rs.getString("crcode").equals(TempCr)) {
                        t += "colspan=3 align=left><font face=Angsana New size=1>" + TAB + ("Total Slip " + PUtility.DataFull(IntFmt.format(SumCard), 6) + TAB + PUtility.DataFull(DecFmt.format(SumCardAmt), 11)) + "_";
                        t += "colspan=3 align=center><font face=Angsana New size=1>" + ("-----------------------------------------------------") + "_";
                        SumCard = 0;
                        SumCardAmt = 0.0;
                        t += "colspan=2 align=left><font face=Angsana New size=1>" + (rs.getString("crcode") + Space + PUtility.SeekCreditName(rs.getString("crcode"))) + "_";
                    }
                    SumCard++;
                    SumCardAmt = SumCardAmt + rs.getDouble("cramt");
                    SumTotal++;
                    SumTotalAmt = SumTotalAmt + rs.getDouble("cramt");
                    String TempCrid = PUtility.Addzero(rs.getString("crid"), 16);
                    t += "colspan=2 align=left><font face=Angsana New size=1>" + (PUtility.DataFull(IntFmt.format(SumCard), 5) + TAB + TempCrid.substring(12, 16) + "</td><td align=right><font face=Angsana New size=1>" + PUtility.DataFullR(rs.getString("crapp"), 6) + TAB + PUtility.DataFull(DecFmt.format(rs.getDouble("cramt")), 9)) + "_";
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        }
        if (SumCard > 0) {
            t += "colspan=3 align=left><font face=Angsana New size=1>" + TAB + ("Total Slip " + Space + PUtility.DataFull(IntFmt.format(SumCard), 6) + TAB + PUtility.DataFull(DecFmt.format(SumCardAmt), 11)) + "_";
        }
        t += "colspan=3 align=center><font face=Angsana New size=1>" + ("-----------------------------------------------------") + "_";
        t += "colspan=2 align=left><font face=Angsana New size=1>" + ("Sum-Total Slip  " + Space + PUtility.DataFull(IntFmt.format(SumTotal), 6) + "</td><td align=right><font face=Angsana New size=1>" + PUtility.DataFull(DecFmt.format(SumTotalAmt), 13)) + "_";
        t += "colspan=3 align=center><font face=Angsana New size=1>" + ("-----------------------------------------------------" + "_");

        PrintDriver pd = new PrintDriver();
        String[] strs = t.split("_");

        for (String data1 : strs) {
            pd.addTextIFont(data1);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
        }
        pd.printHTML();
        InitScreen();
    }

    public void InsertTemp(String TCrCode, String TCrId, String TCrApp, Double TCrAmt) {
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            Statement stmt = mysql.getConnection().createStatement();
            String SqlQuery = "insert into tempcredit (terminal,crcode,crid,crapp,cramt) "
                    + "values (?,?,?,?,?)";
            PreparedStatement prm = mysql.getConnection().prepareStatement(SqlQuery);
            prm.setString(1, Value.MACNO);
            prm.setString(2, TCrCode);
            prm.setString(3, TCrId);
            prm.setString(4, TCrApp);
            prm.setDouble(5, TCrAmt);
            prm.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        } finally {
            mysql.close();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntExit;
    private javax.swing.JButton bntOK;
    private javax.swing.JButton cmdDateChoose1;
    private javax.swing.JButton cmdDateChoose2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JFormattedTextField txtDate1;
    private javax.swing.JFormattedTextField txtDate2;
    // End of variables declaration//GEN-END:variables

}
