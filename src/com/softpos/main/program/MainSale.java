package com.softpos.main.program;

import com.softpos.discount.DiscountDialog;
import com.softpos.floorplan.PaidinFrm;
import com.softpos.floorplan.RefundBill;
import com.softpos.floorplan.ShowTable;
import com.softpos.member.MemberControl;
import com.softpos.pos.core.controller.BalanceControl;
import static com.softpos.pos.core.controller.BalanceControl.updateProSerTable;
import static com.softpos.pos.core.controller.BalanceControl.updateProSerTableMemVIP;
import com.softpos.pos.core.controller.BranchControl;
import com.softpos.pos.core.controller.ButtonCustom;
import com.softpos.pos.core.controller.MenuMGR;
import com.softpos.pos.core.controller.NumberControl;
import com.softpos.pos.core.controller.POSConfigSetup;
import com.softpos.pos.core.controller.POSHWSetup;
import com.softpos.pos.core.controller.PPrint;
import com.softpos.pos.core.controller.PUtility;
import com.softpos.pos.core.controller.PosControl;
import com.softpos.pos.core.controller.ProductControl;
import com.softpos.pos.core.controller.PublicVar;
import com.softpos.pos.core.controller.TableFileControl;
import com.softpos.pos.core.controller.ThaiUtil;
import com.softpos.pos.core.controller.UserRecord;
import com.softpos.pos.core.controller.Value;
import com.softpos.pos.core.model.BalanceBean;
import com.softpos.pos.core.model.BranchBean;
import com.softpos.pos.core.model.MemberBean;
import com.softpos.pos.core.model.ProductBean;
import com.softpos.pos.core.model.TableFileBean;
import database.MySQLConnect;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import printReport.PrintSimpleForm;
import setupmenu.SetHeaderMenu;
import static soft.virtual.JTableControl.alignColumn;
import soft.virtual.KeyBoardDialog;
import util.AppLogUtil;
import util.MSG;
import util.Option;
import util.ValidateValue;

public class MainSale extends javax.swing.JDialog {

    private PPrint Prn = new PPrint();
    private DefaultTableModel model;
    private SimpleDateFormat Timefmt = new SimpleDateFormat("HH:mm:ss");
    private boolean TableOpenStatus;
    private DecimalFormat QtyIntFmt = new DecimalFormat("###########0");
    private String TempStatus = "";
    private POSHWSetup POSHW;
    private POSConfigSetup CONFIG;
    public static String INDEX_NAME = "";
    private List<Integer> historyBack;
    private DecimalFormat dc1 = new DecimalFormat("#,##0.00");
    private MemberBean memberBean;
    private String tableNo;
    private String SALE_DINE_IN = "นั่งทาน";
    private String SALE_TAKE_AWAY = "ห่อกลับ";
    private String SALE_Delivery = "เดลิเวอรี่";
    private String SALE_Pinto = "ส่งประจำ";
    private String SALE_WholeSale = "ขายส่ง";
    private boolean btnClickPrintKic = false;

    public MainSale(java.awt.Frame parent, boolean modal, String tableNo) {

        super(parent, modal);
        initComponents();

//        jMenuBar11.setVisible(false);
        MMainMenu1.setVisible(true);
        jMenu2.setVisible(true);
        txtDisplayDiscount.setVisible(true);
        txtDiscount.setVisible(true);
//        jPanelMember.setVisible(false);
        jPanel5.setVisible(false);
        txtDisplayDiscount.setVisible(false);
        txtDiscount.setVisible(false);

        if (btnLangTH.isSelected()) {
            PublicVar.languagePrint = "TH";
        }
        if (btnLangEN.isSelected()) {
            PublicVar.languagePrint = "EN";
        }
        if (tableNo.contains("(")) {
            String T1 = tableNo.substring(0, tableNo.indexOf("("));
            tableNo = T1;
        }
        TableFileControl tbControl = new TableFileControl();
        TableFileBean tbBean = tbControl.getData(tableNo);
        this.memberBean = MemberBean.getMember(tbBean.getMemCode());
        if (memberBean == null) {
            BalanceControl.updateProSerTable(tableNo, memberBean);
        } else {
            if (ValidateValue.isNotEmpty(memberBean.getMember_Code())) {
                BalanceControl.updateProSerTable(tableNo, memberBean);
                txtMember1.setText(memberBean.getMember_NameThai());
                txtMember2.setText("แต้มสะสม " + memberBean.getMember_TotalScore());
            } else if (tbBean.getMemDiscAmt() != 0) {
                updateProSerTableMemVIP(tableNo, tbBean.getMemDisc());
            }
        }
        txtDiscount.setText("- " + BalanceControl.GetDiscount(tableNo));
        loadButtonProductMenu("A");
        Value.MemberAlready = false;

        POSHW = POSHWSetup.Bean(Value.getMacno());
        CONFIG = POSConfigSetup.Bean();

        initScreen();
        super.setTitle(super.getTitle() + " (" + Value.USERCODE + ") " + "พนักงาน: " + PublicVar._UserName);
        BranchBean branchBean = BranchControl.getData();
        if (branchBean.getLocation_Area().equals("02")) {
            txtShowETD.setText("T");
        }

        this.tableNo = tableNo;
        txtTable.setText(tableNo);
        tblShowPluShow(txtTable.getText());

        historyBack = new ArrayList<>();

        setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
        sumSplit();
        txtPluCode.setEditable(true);
        txtPluCode.setFocusable(true);
        txtPluCode.requestFocus();
        MAddNewMember1.setVisible(true);
        upDateTableFile();
        showCustomerInput();
    }

    @SuppressWarnings("static-access")
    private boolean chkEJPath() {
        if (POSHW.getEJounal().equals("Y")) {
            try {
                String TempFile = POSHW.getEJDailyPath();
                File fObject = new File(TempFile);
                return fObject.exists();
            } catch (HeadlessException e) {
                MSG.ERR(this, "ไม่สามารถสร้าง Log File/EJFile ตามตำแหน่งที่เก็บข้อมูล Log File/EJ ได้ กรุณาติดต่อเจ้าหน้าที่ Support...");
                return false;
            }
        } else {
            return true;
        }
    }

    private void loadLoginForm() {
        this.setVisible(false);
        showTableOpen();
    }

    void showTableOpen() {
        this.setVisible(false);
    }

    private void showSaleType(String SaleType) {
        txtShowETD.setVisible(false);
        txtShowETD.setText(SaleType);
    }

    private void initScreen() {
        model = (DefaultTableModel) tblShowBalance.getModel();

        tblShowBalance.setShowGrid(true);
        tblShowBalance.setGridColor(Color.gray);
        tblShowBalance.setRowHeight(35);

        JTableHeader header = tblShowBalance.getTableHeader();
        header.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 14));

        alignColumn(tblShowBalance, 2, "right");
        alignColumn(tblShowBalance, 3, "right");
        alignColumn(tblShowBalance, 4, "right");

        PublicVar.CheckStockOnLine = PUtility.GetStockOnLine();

        TableOpenStatus = false;
        PublicVar.ErrorColect = false;

        showSaleType(POSHW.getSaleType());
        PublicVar.P_LogoffOK = false;

        PublicVar.CouponCnt = 0;
        PublicVar.P_ItemCount = 0;
        PublicVar.P_TotalAmt = 0.00;
        PublicVar.P_TotService = 0.00;
        PublicVar.P_TotDiscount = 0.00;
        PublicVar.P_NetAmt = 0.00;

        txtTable.setText("");
        txtCust.setText("");
        txtPluCode.setText("");
        txtTable.setEditable(true);
        txtCust.setEditable(false);
        txtPluCode.setEditable(false);

        txtTable.requestFocus();

        lbTotalAmount.setText("0.00");
        jLabel1.setText("จำนวนรายการสินค้า: 0 รายการ");

        changeSaleType("E");
        getTableAction();
    }

    private void getTableAction() {
        txtTable.setEditable(true);
        txtTable.requestFocus();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnCancel = new javax.swing.JButton();
        btnHold = new javax.swing.JButton();
        bntPrintCheckBill = new javax.swing.JButton();
        btnSplit = new javax.swing.JButton();
        btnPayment = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lbTotalAmount = new javax.swing.JLabel();
        tbpMain = new javax.swing.JTabbedPane();
        pMenu1 = new javax.swing.JPanel();
        btnP1 = new javax.swing.JButton();
        btnP2 = new javax.swing.JButton();
        btnP3 = new javax.swing.JButton();
        btnP4 = new javax.swing.JButton();
        btnP5 = new javax.swing.JButton();
        btnP6 = new javax.swing.JButton();
        btnP7 = new javax.swing.JButton();
        btnP8 = new javax.swing.JButton();
        btnP9 = new javax.swing.JButton();
        btnP10 = new javax.swing.JButton();
        btnP11 = new javax.swing.JButton();
        btnP12 = new javax.swing.JButton();
        btnP13 = new javax.swing.JButton();
        btnP14 = new javax.swing.JButton();
        btnP15 = new javax.swing.JButton();
        btnP16 = new javax.swing.JButton();
        pMenu2 = new javax.swing.JPanel();
        btnP17 = new javax.swing.JButton();
        btnP18 = new javax.swing.JButton();
        btnP19 = new javax.swing.JButton();
        btnP20 = new javax.swing.JButton();
        btnP21 = new javax.swing.JButton();
        btnP22 = new javax.swing.JButton();
        btnP23 = new javax.swing.JButton();
        btnP24 = new javax.swing.JButton();
        btnP25 = new javax.swing.JButton();
        btnP26 = new javax.swing.JButton();
        btnP27 = new javax.swing.JButton();
        btnP28 = new javax.swing.JButton();
        btnP29 = new javax.swing.JButton();
        btnP30 = new javax.swing.JButton();
        btnP31 = new javax.swing.JButton();
        btnP32 = new javax.swing.JButton();
        pMenu3 = new javax.swing.JPanel();
        pMenu4 = new javax.swing.JPanel();
        pMenu5 = new javax.swing.JPanel();
        pMenu6 = new javax.swing.JPanel();
        pMenu7 = new javax.swing.JPanel();
        pMenu8 = new javax.swing.JPanel();
        pMenu9 = new javax.swing.JPanel();
        pSubMenu1 = new javax.swing.JPanel();
        pSubMenu2 = new javax.swing.JPanel();
        pSubMenu3 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txtTable = new javax.swing.JTextField();
        txtShowETD = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtCust = new javax.swing.JTextField();
        txtTypeDesc = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        txtDisplayDiscount = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        txtPluCode = new javax.swing.JTextField();
        txtDiscount = new javax.swing.JTextField();
        btnPrintKic = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbCredit = new javax.swing.JLabel();
        lbCreditMoney = new javax.swing.JLabel();
        lbCreditAmt = new javax.swing.JLabel();
        txtMember1 = new javax.swing.JTextField();
        txtMember2 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        btnLangTH = new javax.swing.JRadioButton();
        btnLangEN = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblShowBalance = new javax.swing.JTable();
        jMenuBar11 = new javax.swing.JMenuBar();
        MMainMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        MCancelArPayment2 = new javax.swing.JMenuItem();
        MCancelArPayment1 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        MAddNewAccr1 = new javax.swing.JMenuItem();
        MRepArNotPayment1 = new javax.swing.JMenuItem();
        MRepArHistory1 = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JSeparator();
        MAddNewMember1 = new javax.swing.JMenuItem();
        MRepMemberHistory1 = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JSeparator();
        MRepInvCash1 = new javax.swing.JMenuItem();
        MRepInvAr1 = new javax.swing.JMenuItem();
        MHeaderBill1 = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JSeparator();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jCheckBoxMenuItem3 = new javax.swing.JCheckBoxMenuItem();

        jPopupMenu1.setBackground(new java.awt.Color(102, 153, 0));
        jPopupMenu1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPopupMenu1.setForeground(new java.awt.Color(255, 255, 255));

        jMenuItem1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem1.setText("แก้ไขรายการ");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        jMenuItem2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem2.setText("กำหนดประเภทปุ่ม");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem2);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("โปรแกรมร้านอาหาร");
        setBackground(new java.awt.Color(255, 204, 204));
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        btnCancel.setBackground(new java.awt.Color(204, 51, 0));
        btnCancel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("<html>ยกเลิกราย<br />การก่อนส่งครัว</html>");
        btnCancel.setFocusable(false);
        btnCancel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancel.setRequestFocusEnabled(false);
        btnCancel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        jPanel2.add(btnCancel);

        btnHold.setBackground(new java.awt.Color(204, 51, 0));
        btnHold.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnHold.setForeground(new java.awt.Color(255, 255, 255));
        btnHold.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hold.png"))); // NOI18N
        btnHold.setText("ส่งครัว/พักบิล");
        btnHold.setActionCommand("พักบิล/พักโต๊ะ (F3)");
        btnHold.setFocusable(false);
        btnHold.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHold.setPreferredSize(new java.awt.Dimension(70, 61));
        btnHold.setRequestFocusEnabled(false);
        btnHold.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnHold.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoldActionPerformed(evt);
            }
        });
        jPanel2.add(btnHold);

        bntPrintCheckBill.setBackground(new java.awt.Color(204, 51, 0));
        bntPrintCheckBill.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bntPrintCheckBill.setForeground(new java.awt.Color(255, 255, 255));
        bntPrintCheckBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/print.png"))); // NOI18N
        bntPrintCheckBill.setText("พิมพ์ตรวจสอบ");
        bntPrintCheckBill.setFocusable(false);
        bntPrintCheckBill.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bntPrintCheckBill.setRequestFocusEnabled(false);
        bntPrintCheckBill.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bntPrintCheckBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntPrintCheckBillActionPerformed(evt);
            }
        });
        jPanel2.add(bntPrintCheckBill);

        btnSplit.setBackground(new java.awt.Color(204, 51, 0));
        btnSplit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSplit.setForeground(new java.awt.Color(255, 255, 255));
        btnSplit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/split.png"))); // NOI18N
        btnSplit.setText("แยกชำระ");
        btnSplit.setFocusable(false);
        btnSplit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSplit.setPreferredSize(new java.awt.Dimension(70, 61));
        btnSplit.setRequestFocusEnabled(false);
        btnSplit.setRolloverEnabled(false);
        btnSplit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSplit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSplitActionPerformed(evt);
            }
        });
        jPanel2.add(btnSplit);

        btnPayment.setBackground(new java.awt.Color(204, 51, 0));
        btnPayment.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnPayment.setForeground(new java.awt.Color(255, 255, 255));
        btnPayment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/เช็คบิล.png"))); // NOI18N
        btnPayment.setText("ชำระเงิน");
        btnPayment.setFocusable(false);
        btnPayment.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPayment.setRequestFocusEnabled(false);
        btnPayment.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaymentActionPerformed(evt);
            }
        });
        jPanel2.add(btnPayment);

        jPanel4.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 629, 580, 100));

        jPanel3.setBackground(new java.awt.Color(204, 51, 0));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTotalAmount.setBackground(new java.awt.Color(153, 255, 153));
        lbTotalAmount.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        lbTotalAmount.setForeground(new java.awt.Color(255, 255, 255));
        lbTotalAmount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbTotalAmount.setText("0.00");
        jPanel3.add(lbTotalAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 570, 80));

        jPanel4.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 2, 580, 90));

        tbpMain.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tbpMain.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        tbpMain.setFocusable(false);
        tbpMain.setFont(new java.awt.Font("Tahoma", 0, 1)); // NOI18N
        tbpMain.setPreferredSize(new java.awt.Dimension(0, 0));
        tbpMain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbpMainMouseClicked(evt);
            }
        });

        pMenu1.setBackground(new java.awt.Color(255, 255, 255));
        pMenu1.setLayout(new java.awt.GridLayout(4, 4));
        pMenu1.add(btnP1);
        pMenu1.add(btnP2);
        pMenu1.add(btnP3);
        pMenu1.add(btnP4);
        pMenu1.add(btnP5);
        pMenu1.add(btnP6);
        pMenu1.add(btnP7);
        pMenu1.add(btnP8);
        pMenu1.add(btnP9);
        pMenu1.add(btnP10);
        pMenu1.add(btnP11);
        pMenu1.add(btnP12);
        pMenu1.add(btnP13);
        pMenu1.add(btnP14);
        pMenu1.add(btnP15);
        pMenu1.add(btnP16);

        tbpMain.addTab("", pMenu1);

        pMenu2.setLayout(new java.awt.GridLayout(4, 4));
        pMenu2.add(btnP17);
        pMenu2.add(btnP18);
        pMenu2.add(btnP19);
        pMenu2.add(btnP20);
        pMenu2.add(btnP21);
        pMenu2.add(btnP22);
        pMenu2.add(btnP23);
        pMenu2.add(btnP24);
        pMenu2.add(btnP25);
        pMenu2.add(btnP26);
        pMenu2.add(btnP27);
        pMenu2.add(btnP28);
        pMenu2.add(btnP29);
        pMenu2.add(btnP30);
        pMenu2.add(btnP31);
        pMenu2.add(btnP32);

        tbpMain.addTab("", pMenu2);

        pMenu3.setLayout(new java.awt.GridLayout(4, 4));
        tbpMain.addTab("", pMenu3);

        pMenu4.setLayout(new java.awt.GridLayout(4, 4));
        tbpMain.addTab("", pMenu4);

        pMenu5.setLayout(new java.awt.GridLayout(4, 4));
        tbpMain.addTab("", pMenu5);

        pMenu6.setLayout(new java.awt.GridLayout(4, 4));
        tbpMain.addTab("", pMenu6);

        pMenu7.setLayout(new java.awt.GridLayout(4, 4));
        tbpMain.addTab("", pMenu7);

        pMenu8.setLayout(new java.awt.GridLayout(4, 4));
        tbpMain.addTab("", pMenu8);

        pMenu9.setLayout(new java.awt.GridLayout(4, 4));
        tbpMain.addTab("", pMenu9);

        pSubMenu1.setLayout(new java.awt.GridLayout(4, 4));
        tbpMain.addTab("", pSubMenu1);

        pSubMenu2.setLayout(new java.awt.GridLayout(4, 4));
        tbpMain.addTab("", pSubMenu2);

        pSubMenu3.setLayout(new java.awt.GridLayout(4, 4));
        tbpMain.addTab("", pSubMenu3);

        jPanel4.add(tbpMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 103, 580, 520));

        jPanel1.setBackground(new java.awt.Color(204, 51, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtTable.setFont(new java.awt.Font("Norasi", 1, 16)); // NOI18N
        txtTable.setForeground(new java.awt.Color(255, 0, 0));
        txtTable.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtTable.setDisabledTextColor(java.awt.Color.red);
        txtTable.setFocusable(false);
        txtTable.setMargin(new java.awt.Insets(2, 10, 2, 2));
        txtTable.setRequestFocusEnabled(false);
        txtTable.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTableFocusGained(evt);
            }
        });
        txtTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTableKeyPressed(evt);
            }
        });

        txtShowETD.setEditable(false);
        txtShowETD.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        txtShowETD.setForeground(new java.awt.Color(255, 255, 255));
        txtShowETD.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtShowETD.setText("E");
        txtShowETD.setDisabledTextColor(java.awt.Color.black);
        txtShowETD.setEnabled(false);
        txtShowETD.setFocusable(false);
        txtShowETD.setRequestFocusEnabled(false);
        txtShowETD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtShowETDMouseClicked(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("ลูกค้า");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("โต๊ะ");

        txtCust.setFont(new java.awt.Font("Norasi", 1, 16)); // NOI18N
        txtCust.setForeground(new java.awt.Color(255, 0, 0));
        txtCust.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCust.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtCust.setDisabledTextColor(java.awt.Color.black);
        txtCust.setFocusable(false);
        txtCust.setRequestFocusEnabled(false);
        txtCust.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCustFocusGained(evt);
            }
        });
        txtCust.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCustMouseClicked(evt);
            }
        });
        txtCust.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCustKeyPressed(evt);
            }
        });

        txtTypeDesc.setEditable(false);
        txtTypeDesc.setBackground(new java.awt.Color(204, 204, 255));
        txtTypeDesc.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtTypeDesc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTypeDesc.setText("DINE IN");
        txtTypeDesc.setBorder(null);
        txtTypeDesc.setCaretColor(new java.awt.Color(255, 255, 255));
        txtTypeDesc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTypeDescMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTable, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCust, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTypeDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtShowETD, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtCust, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtTable, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtTypeDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtShowETD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtDisplayDiscount.setEditable(false);
        txtDisplayDiscount.setBackground(new java.awt.Color(255, 153, 153));
        txtDisplayDiscount.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtDisplayDiscount.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDisplayDiscount.setText("Discount");
        txtDisplayDiscount.setBorder(null);
        txtDisplayDiscount.setCaretColor(new java.awt.Color(255, 255, 255));
        jPanel6.add(txtDisplayDiscount, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 153, 170, 40));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("แป้นพิมพ์");
        jButton1.setRequestFocusEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 35, 97, 30));

        txtPluCode.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtPluCode.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtPluCode.setMargin(new java.awt.Insets(2, 0, 2, 2));
        txtPluCode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPluCodeFocusGained(evt);
            }
        });
        txtPluCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPluCodeKeyPressed(evt);
            }
        });
        jPanel6.add(txtPluCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(248, 35, 170, 30));

        txtDiscount.setEditable(false);
        txtDiscount.setBackground(new java.awt.Color(255, 153, 153));
        txtDiscount.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtDiscount.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDiscount.setText("0.00");
        txtDiscount.setBorder(null);
        txtDiscount.setCaretColor(new java.awt.Color(255, 255, 255));
        jPanel6.add(txtDiscount, new org.netbeans.lib.awtextra.AbsoluteConstraints(175, 153, 240, 40));

        btnPrintKic.setBackground(new java.awt.Color(0, 0, 204));
        btnPrintKic.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnPrintKic.setForeground(new java.awt.Color(255, 255, 255));
        btnPrintKic.setText("Print - ON");
        btnPrintKic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintKicActionPerformed(evt);
            }
        });
        jPanel6.add(btnPrintKic, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 35, 139, 30));

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));
        jPanel5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("ค้างชำระ");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("เครดิต");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("วงเงิน");

        lbCredit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbCredit.setForeground(new java.awt.Color(255, 255, 255));
        lbCredit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbCredit.setText("0");

        lbCreditMoney.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbCreditMoney.setForeground(new java.awt.Color(255, 255, 255));
        lbCreditMoney.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbCreditMoney.setText("0.00");

        lbCreditAmt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbCreditAmt.setForeground(new java.awt.Color(255, 255, 255));
        lbCreditAmt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbCreditAmt.setText("0.00");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbCredit, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(lbCreditMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbCreditAmt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel13)
                    .addComponent(jLabel11)
                    .addComponent(lbCredit)
                    .addComponent(lbCreditMoney)
                    .addComponent(lbCreditAmt))
                .addContainerGap())
        );

        jPanel6.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 108, 420, 40));

        txtMember1.setEditable(false);
        txtMember1.setBackground(new java.awt.Color(0, 102, 204));
        txtMember1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtMember1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMember1.setText(" <ค้นหาสมาชิก> ");
        txtMember1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMember1.setFocusable(false);
        txtMember1.setRequestFocusEnabled(false);
        txtMember1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMember1MouseClicked(evt);
            }
        });
        txtMember1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMember1ActionPerformed(evt);
            }
        });
        jPanel6.add(txtMember1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 280, 36));

        txtMember2.setEditable(false);
        txtMember2.setBackground(new java.awt.Color(0, 102, 204));
        txtMember2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtMember2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMember2.setText(": แต้มสะสม");
        txtMember2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.add(txtMember2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, 140, 36));

        jPanel7.setBackground(new java.awt.Color(204, 255, 204));

        btnLangTH.setBackground(new java.awt.Color(204, 255, 204));
        buttonGroup1.add(btnLangTH);
        btnLangTH.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnLangTH.setSelected(true);
        btnLangTH.setText("TH");
        btnLangTH.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                btnLangTHItemStateChanged(evt);
            }
        });

        btnLangEN.setBackground(new java.awt.Color(204, 255, 204));
        buttonGroup1.add(btnLangEN);
        btnLangEN.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnLangEN.setText("EN");
        btnLangEN.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                btnLangENItemStateChanged(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText(" 0  รายการ");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnLangTH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLangEN)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(btnLangTH)
                    .addComponent(btnLangEN))
                .addContainerGap())
        );

        jPanel6.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 420, -1));

        tblShowBalance.setFont(new java.awt.Font("Angsana New", 0, 20)); // NOI18N
        tblShowBalance.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Product Name", "QTY.", "Price", "Total", "Void Flag", "Type Sale", "Print to Kic", "Order Send", "Promotion", "RIndex", "Pause", "Employ", "Time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblShowBalance.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblShowBalance.setFocusable(false);
        tblShowBalance.setRequestFocusEnabled(false);
        tblShowBalance.setRowHeight(25);
        tblShowBalance.setSelectionBackground(new java.awt.Color(102, 153, 255));
        tblShowBalance.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblShowBalance.setShowVerticalLines(false);
        tblShowBalance.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblShowBalanceMouseClicked(evt);
            }
        });
        tblShowBalance.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblShowBalanceKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblShowBalance);
        if (tblShowBalance.getColumnModel().getColumnCount() > 0) {
            tblShowBalance.getColumnModel().getColumn(0).setMinWidth(0);
            tblShowBalance.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblShowBalance.getColumnModel().getColumn(0).setMaxWidth(0);
            tblShowBalance.getColumnModel().getColumn(1).setPreferredWidth(390);
            tblShowBalance.getColumnModel().getColumn(2).setPreferredWidth(45);
        }

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jMenuBar11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuBar11MouseClicked(evt);
            }
        });

        MMainMenu1.setText("เมนูหลักระบบ (Main Menu)         ");
        MMainMenu1.setDelay(100);
        MMainMenu1.setDoubleBuffered(true);
        MMainMenu1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        MMainMenu1.setRequestFocusEnabled(false);

        jMenuItem3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem3.setText("ย้ายรายการ");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        MMainMenu1.add(jMenuItem3);

        jMenuItem7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem7.setText("คืนเงินมัดจำเป็นเงินสด");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        MMainMenu1.add(jMenuItem7);

        jMenuItem8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem8.setText("ยกเลิกการคืนเงินมัดจำ");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        MMainMenu1.add(jMenuItem8);
        MMainMenu1.add(jSeparator1);

        MCancelArPayment2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        MCancelArPayment2.setText("รับชำระจากลูกหนี้ภายนอก");
        MCancelArPayment2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MCancelArPayment2ActionPerformed(evt);
            }
        });
        MMainMenu1.add(MCancelArPayment2);

        MCancelArPayment1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        MCancelArPayment1.setText("ยกเลิกการรับชำระจากลูกหนี้ภายนอก");
        MCancelArPayment1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MCancelArPayment1ActionPerformed(evt);
            }
        });
        MMainMenu1.add(MCancelArPayment1);
        MMainMenu1.add(jSeparator2);

        MAddNewAccr1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        MAddNewAccr1.setText("ปรับปรุงรายละเอียดลูกหนี้ภายนอก");
        MAddNewAccr1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MAddNewAccr1ActionPerformed(evt);
            }
        });
        MMainMenu1.add(MAddNewAccr1);

        MRepArNotPayment1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        MRepArNotPayment1.setText("รายงานลูกหนี้ภายนอกค้างชำระ");
        MRepArNotPayment1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MRepArNotPayment1ActionPerformed(evt);
            }
        });
        MMainMenu1.add(MRepArNotPayment1);

        MRepArHistory1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        MRepArHistory1.setText("ประวัติการซื้อของลูกหนี้ภายนอก");
        MRepArHistory1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MRepArHistory1ActionPerformed(evt);
            }
        });
        MMainMenu1.add(MRepArHistory1);
        MMainMenu1.add(jSeparator10);

        MAddNewMember1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        MAddNewMember1.setForeground(new java.awt.Color(255, 51, 51));
        MAddNewMember1.setText("ปรับปรุงข้อมูลสมาชิก (Member)ส่วนนี้อาจจะเอาออกเพราะใน CRM มีอยู่แล้ว");
        MAddNewMember1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MAddNewMember1ActionPerformed(evt);
            }
        });
        MMainMenu1.add(MAddNewMember1);

        MRepMemberHistory1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        MRepMemberHistory1.setText("ประวัติการซื้อของสมาชิก");
        MRepMemberHistory1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MRepMemberHistory1ActionPerformed(evt);
            }
        });
        MMainMenu1.add(MRepMemberHistory1);
        MMainMenu1.add(jSeparator11);

        MRepInvCash1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        MRepInvCash1.setText("พิมพ์ใบกำกับภาษี/ใบเสร็จรับเงิน");
        MRepInvCash1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MRepInvCash1ActionPerformed(evt);
            }
        });
        MMainMenu1.add(MRepInvCash1);

        MRepInvAr1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        MRepInvAr1.setForeground(new java.awt.Color(255, 51, 51));
        MRepInvAr1.setText("พิมพ์ใบกำกับภาษี/ใบแจ้งหนี้(ยกเลิกหัวข้อนี้)");
        MRepInvAr1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MRepInvAr1ActionPerformed(evt);
            }
        });
        MMainMenu1.add(MRepInvAr1);

        MHeaderBill1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        MHeaderBill1.setText("พิมพ์หัวกระดาษ/ท้ายกระดาษ");
        MHeaderBill1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MHeaderBill1ActionPerformed(evt);
            }
        });
        MMainMenu1.add(MHeaderBill1);
        MMainMenu1.add(jSeparator12);

        jMenuItem4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem4.setText("กำหนด Header TAB");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        MMainMenu1.add(jMenuItem4);

        jMenuBar11.add(MMainMenu1);

        jMenu2.setText("กำหนดรายละเอียดสินค้า     ");
        jMenu2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jCheckBoxMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PAGE_DOWN, 0));
        jCheckBoxMenuItem3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jCheckBoxMenuItem3.setSelected(true);
        jCheckBoxMenuItem3.setText("การกำหนดรายละเอียด Option");
        jCheckBoxMenuItem3.setActionCommand("การกำหนดรายละเอียด Option\nปลดล็อกการวอย Void Unlock");
        jCheckBoxMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jCheckBoxMenuItem3);

        jMenuBar11.add(jMenu2);

        setJMenuBar(jMenuBar11);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    private void tableOpened() {
        if (!txtTable.getText().trim().equals("")) {
            txtTableOnExit();
            showSum();
            if (!CONFIG.getP_EmpUse().equals("Y")) {
                if (PublicVar.TableRec_TCustomer == 0) {
                    txtCust.setEditable(true);
                    txtCust.requestFocus();
                    txtCust.selectAll();
                } else {
                    txtPluCode.setEditable(true);
                    txtPluCode.setBackground(Color.WHITE);
                    txtPluCode.requestFocus();
                }
            }
            return;
        }
        if (txtTable.getText().trim().length() > 5) {
            MSG.ERR(this, "หมายเลขโต๊ะต้องกำหนดเป็นตัวเลข 0-9 เท่านั้น และกำหนดได้ไม่เกิน 5 ตัวอักษร...");
            txtTable.selectAll();
            txtTable.requestFocus();
        }
    }

private void txtTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTableKeyPressed
    switch (evt.getKeyCode()) {
        case KeyEvent.VK_ENTER:
            tableOpened();
            break;
        case KeyEvent.VK_ESCAPE:
            txtTable.setText("");
            bntlogoffuserClick();
            break;
        case KeyEvent.VK_F5:
            showTableAvialble();
            break;
        case KeyEvent.VK_F8:
            showPaidIn();
            break;
        case KeyEvent.VK_F9:
            showPaidOut();
            break;
        case KeyEvent.VK_F6:
            showBillDuplicate();
            break;
        case KeyEvent.VK_F7:
            showRefundBill();
            break;
        case KeyEvent.VK_F12:
            showPayAR();
            break;
        default:
            break;
    }

}//GEN-LAST:event_txtTableKeyPressed

    private void tblShowPluShow(String P_Table) {
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();

        try {
            mysql.open();
            String sql = "select * from balance where r_table='" + P_Table + "' order by r_index,r_etd";
            ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql);
            int rowCount = model.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                model.removeRow(0);
            }

            while (rs.next()) {
                String r_linkindex = rs.getString("r_linkindex");
                String r_index = rs.getString("r_index");
                String voidStr = rs.getString("r_void");
                String rEtd = rs.getString("R_ETD");

                String PName = rEtd + " " + ThaiUtil.ASCII2Unicode(rs.getString("r_pname"));

                if (voidStr.equals("V")) {
                    PName = "<html><div align= 'left'><strike><font color=red>" + PName + "</font></strike></div></html>";

                }
                if (r_index.equals(r_linkindex)) {
                    PName = "<html><b><font color=blue>" + PName + "</font></b></html>";
                }
                String empName = "";
                String employ = rs.getString("r_emp");
                String sqlGetEmployName = "select name from employ where code='" + employ + "'";
                ResultSet rsGetEmpName = mysql.getConnection().createStatement().executeQuery(sqlGetEmployName);
                if (rsGetEmpName.next()) {
                    empName = ThaiUtil.ASCII2Unicode(rsGetEmpName.getString("name"));
                }
                Object[] input = {
                    rs.getString("r_plucode"),
                    PName,
                    dc1.format(rs.getDouble("r_quan")),
                    dc1.format(rs.getDouble("r_price")),
                    dc1.format(rs.getDouble("r_total")),
                    rs.getString("r_void"),
                    rs.getString("r_etd"),
                    rs.getString("r_kicprint"),
                    rs.getString("r_kicok"),
                    rs.getString("r_prtype"),
                    rs.getString("r_index"),
                    rs.getString("r_pause"),
                    empName,
                    rs.getString("r_time"),};
                model.addRow(input);
                //วนหาข้อความพิเศษ
                String str = "";
                for (int i = 1; i <= 9; i++) {
                    String R_Opt = rs.getString("R_Opt" + i);
                    if (R_Opt == null) {
                        R_Opt = "";
                    }
                    if (!R_Opt.equals("")) {
                        if (str.equals("")) {
                            if (voidStr.equals("V")) {
                                str += "  - ";
                            } else {
                                str += "  + ";
                            }
                        }
                        str += ThaiUtil.ASCII2Unicode(rs.getString("R_Opt" + i)) + ",";
                    }
                }
                if (!str.equals("")) {
                    if (str.contains("-")) {
                        str = "<html><font color=red>" + str + "</font></html>";
                    }
                    model.addRow(new Object[]{"", str});
                }
            }

            rowCount = model.getRowCount();
            showCell(rowCount - 1, 0);
            rs.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(MainSale.class, "error", e.getMessage());
        } finally {
            mysql.close();
        }

        showSum();
    }

    private void showSum() { //คำสั่งกำหนดให้ไปโชว์ค่ายอดเงินในส่วนต่างๆ
        //show sum
        TableFileControl tCon = new TableFileControl();
        TableFileBean tfBean = tCon.getData(tableNo);
        double totalDiscount;
        totalDiscount = tfBean.getProDiscAmt() + tfBean.getSpaDiscAmt() + tfBean.getCuponDiscAmt()
                + tfBean.getFastDiscAmt() + tfBean.getEmpDiscAmt() + tfBean.getTrainDiscAmt()
                + tfBean.getSubDiscAmt() + tfBean.getDiscBath() + tfBean.getItemDiscAmt();
        if (CONFIG.getP_VatType().equals("I")) {
            if (!CONFIG.getP_PayBahtRound().equals("O")) {
                lbTotalAmount.setText("" + dc1.format(NumberControl.UP_DOWN_NATURAL_BAHT((tfBean.getNetTotal()))));
            } else {
                lbTotalAmount.setText("" + dc1.format((tfBean.getNetTotal())));
            }
        }
        if (CONFIG.getP_VatType().equals("E")) {
            double vat = (tfBean.getTAmount() - totalDiscount + tfBean.getServiceAmt()) * 7 / 100;
            lbTotalAmount.setText(
                    dc1.format(((tfBean.getTAmount() - totalDiscount + tfBean.getServiceAmt()) + vat))
            );
        }

        jLabel1.setText("จำนวนรายการสินค้า: " + tCon.getItemCount(tableNo) + " รายการ");
        txtCust.setText("" + tfBean.getTCustomer());

        // for member
        String MemberCode = tfBean.getMemCode();
        if (ValidateValue.isNotEmpty(MemberCode)) {
            Value.MemberAlready = true;
            memberBean = MemberBean.getMember(MemberCode);
            txtMember1.setText(memberBean.getMember_NameThai());
            txtMember2.setText("แต้มสะสม : " + memberBean.getMember_TotalScore());
        }
    }

private void txtCustKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCustKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        txtPluCode.setEditable(true);
        txtPluCode.requestFocus();

        txtCustOnExit();
    }
}//GEN-LAST:event_txtCustKeyPressed
    //คำสั่งกำหนดจำนวนลูกค้า
    private void txtCustOnExit() {
        try {
            int customerInTable = Integer.parseInt(txtCust.getText());
            if (customerInTable > 999) {
                MSG.ERR("จำนวนลูกค้าป้อนได้ไม่เกิน 999 คน...");
                txtCust.requestFocus();
                return;
            }
            if (txtCust.getText().equals("0")) {
                updateCustomerCount(customerInTable = 1);
                txtCust.setText("1");
            }
            updateCustomerCount(customerInTable);
            txtCust.setEditable(false);
            txtPluCode.setEditable(true);
            txtPluCode.requestFocus();
        } catch (NumberFormatException e) {
            MSG.ERR("กรุณาป้อนจำนวนลูกค้า เป็นตัวเลขเท่านั้น...");
            PublicVar.TableRec_TCustomer = 1;
            txtCust.requestFocus();
        }
    }

private void txtPluCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPluCodeKeyPressed
    //คำสั่ง Enter,ESCAPE
    if (!isTakeOrder()) {
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                pCodeEnter();
                break;
            case KeyEvent.VK_ESCAPE:
                break;
            case KeyEvent.VK_F1:
                FindProduct find = new FindProduct(null, true);
                find.setVisible(true);
                if (!find.getPCode().equals("")) {
                    txtPluCode.setText(txtPluCode.getText() + find.getPCode());

                }
                break;
            case KeyEvent.VK_F3:
                showHoldTable();
                break;
            case KeyEvent.VK_F4:
                showCheckBill();
                break;
            case KeyEvent.VK_F6:
                showBillCheck();
                break;
            case KeyEvent.VK_F9:
                showCustomerInput();
                break;
            case KeyEvent.VK_UP:
                selectedTableBalance();
                break;
            case KeyEvent.VK_F11:
                showMember();
                break;
            default:
                break;
        }
    }

}//GEN-LAST:event_txtPluCodeKeyPressed

private void tblShowBalanceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblShowBalanceKeyPressed
    //คำสั่ง Enter,ESCAPE
    if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
        txtPluCode.requestFocus();
    } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        int row = tblShowBalance.getSelectedRow();
        if (row != -1) {
            Object r_index = model.getValueAt(row, 10);
            Object voidMsg = model.getValueAt(row, 5);
            String strVoid;
            if (voidMsg != null) {
                strVoid = voidMsg.toString();
            } else {
                strVoid = "";
            }

            if (r_index != null && !strVoid.equalsIgnoreCase("V")) {
                selectedOptionBill();
                txtPluCode.requestFocus();
            }
        }
    }

}//GEN-LAST:event_tblShowBalanceKeyPressed

private void MAddNewAccr1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MAddNewAccr1ActionPerformed
    PublicVar.TempUserRec = PublicVar.TUserRec;
    if (PublicVar.TUserRec.Sale7.equals("Y")) {
        AddNewArCustomer fmt = new AddNewArCustomer(null, true);
        fmt.setVisible(true);
    } else {
        GetUserAction getuser = new GetUserAction(null, true);
        getuser.setVisible(true);

        if (!PublicVar.ReturnString.equals("")) {
            String loginname = PublicVar.ReturnString;
            UserRecord supUser = new UserRecord();
            if (supUser.GetUserAction(loginname)) {
                if (supUser.Sale7.equals("Y")) {
                    PublicVar.TUserRec = supUser;
                    AddNewArCustomer fmt = new AddNewArCustomer(null, true);
                    fmt.setVisible(true);
                } else {
                    MSG.ERR(this, "รหัสพนักงานนี้ไม่สามารถเข้าใช้งาน...รายการนี้ได้...!!!");
                }
            } else {
                MSG.ERR(this, "ไม่สามารถ Load สิทธิ์การใช้งานของผู้ใช้งานคนนี้ได้ ...");
            }
        }
    }
    PublicVar.TUserRec = PublicVar.TempUserRec;
}//GEN-LAST:event_MAddNewAccr1ActionPerformed

private void MRepArNotPayment1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MRepArNotPayment1ActionPerformed
    PublicVar.TempUserRec = PublicVar.TUserRec;
    if (PublicVar.TUserRec.Sale8.equals("Y")) {
        ArNotPay frm = new ArNotPay(null, true);
        frm.setVisible(true);
    } else {
        GetUserAction getuser = new GetUserAction(null, true);
        getuser.setVisible(true);

        if (!PublicVar.ReturnString.equals("")) {
            String loginname = PublicVar.ReturnString;
            UserRecord supUser = new UserRecord();
            if (supUser.GetUserAction(loginname)) {
                if (supUser.Sale8.equals("Y")) {
                    PublicVar.TUserRec = supUser;
                    ArNotPay frm = new ArNotPay(null, true);
                    frm.setVisible(true);
                } else {
                    MSG.ERR(this, "รหัสพนักงานนี้ไม่สามารถเข้าใช้งาน...รายการนี้ได้...!!!");
                }
            } else {
                MSG.ERR(this, "ไม่สามารถ Load สิทธิ์การใช้งานของผู้ใช้งานคนนี้ได้ ...");
            }
        }
    }

    PublicVar.TUserRec = PublicVar.TempUserRec;

}//GEN-LAST:event_MRepArNotPayment1ActionPerformed

private void MRepArHistory1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MRepArHistory1ActionPerformed
    PublicVar.TempUserRec = PublicVar.TUserRec;
    if (PublicVar.TUserRec.Sale9.equals("Y")) {
        ArHistory frm = new ArHistory(null, true);
        frm.setVisible(true);
    } else {
        GetUserAction getuser = new GetUserAction(null, true);
        getuser.setVisible(true);

        if (!PublicVar.ReturnString.equals("")) {
            String loginname = PublicVar.ReturnString;
            UserRecord supUser = new UserRecord();
            if (supUser.GetUserAction(loginname)) {
                if (supUser.Sale9.equals("Y")) {
                    PublicVar.TUserRec = supUser;
                    ArHistory frm = new ArHistory(null, true);
                    frm.setVisible(true);
                } else {
                    MSG.ERR(this, "รหัสพนักงานนี้ไม่สามารถเข้าใช้งาน...รายการนี้ได้...!!!");
                }
            } else {
                MSG.ERR(this, "ไม่สามารถ Load สิทธิ์การใช้งานของผู้ใช้งานคนนี้ได้ ...");
            }
        }
    }
    PublicVar.TUserRec = PublicVar.TempUserRec;
}//GEN-LAST:event_MRepArHistory1ActionPerformed

private void MAddNewMember1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MAddNewMember1ActionPerformed
    AddMemberMaster master = new AddMemberMaster(null, true);
    master.setVisible(true);
}//GEN-LAST:event_MAddNewMember1ActionPerformed

private void MRepMemberHistory1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MRepMemberHistory1ActionPerformed
    RepMember frm = new RepMember(null, true);
    frm.setVisible(true);
}//GEN-LAST:event_MRepMemberHistory1ActionPerformed

private void MHeaderBill1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MHeaderBill1ActionPerformed
    if (Value.useprint) {
        PPrint prn = new PPrint();
        prn.printHeaderBill();
    }
}//GEN-LAST:event_MHeaderBill1ActionPerformed

private void MRepInvCash1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MRepInvCash1ActionPerformed
    PrintInv1 frm = new PrintInv1(null, true);
    frm.setVisible(true);
}//GEN-LAST:event_MRepInvCash1ActionPerformed

private void MRepInvAr1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MRepInvAr1ActionPerformed
    PrintInv2 frm = new PrintInv2(null, true);
    frm.setVisible(true);
}//GEN-LAST:event_MRepInvAr1ActionPerformed

private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
    SetHeaderMenu s = new SetHeaderMenu(null, true);
    s.setVisible(true);

    loadHeaderMenu();
}//GEN-LAST:event_jMenuItem4ActionPerformed

    private void clearTable() {
        tblShowBalance.setBackground(null);
        txtTableOnEnter();
        changeSaleType("E");
        txtTable.setText("");
        txtTable.requestFocus();
    }

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        CancelCashBack c = new CancelCashBack(null, true);
        c.setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        CashBackDialog c = new CashBackDialog(null, true);
        c.setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void MCancelArPayment2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MCancelArPayment2ActionPerformed
        ARPayment Ar = new ARPayment(null, true);
        Ar.setVisible(true);

    }//GEN-LAST:event_MCancelArPayment2ActionPerformed

    private void MCancelArPayment1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MCancelArPayment1ActionPerformed
        if (PublicVar.TUserRec.Sale7.equals("Y")) {
            if (PUtility.CheckSaleDateOK()) {
                cancelArPaymentClick();
            }
        } else {
            GetUserAction getuser = new GetUserAction(null, true);
            getuser.setVisible(true);

            if (!PublicVar.ReturnString.equals("")) {
                String loginname = PublicVar.ReturnString;
                UserRecord supUser = new UserRecord();
                if (supUser.GetUserAction(loginname)) {
                    if (supUser.Sale7.equals("Y")) {
                        PublicVar.TUserRec = supUser;
                        if (PUtility.CheckSaleDateOK()) {
                            cancelArPaymentClick();
                        }
                    } else {
                        MSG.ERR(this, "รหัสพนักงานนี้ไม่สามารถเข้าใช้งาน...รายการนี้ได้...!!!");
                    }
                } else {
                    MSG.ERR(this, "ไม่สามารถ Load สิทธิ์การใช้งานของผู้ใช้งานคนนี้ได้ ...");
                }
            }
        }
    }//GEN-LAST:event_MCancelArPayment1ActionPerformed

    private void txtShowETDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtShowETDMouseClicked
        switch (txtShowETD.getText()) {
            case "E":
                txtShowETD.setText("T");
                changeSaleType("T");
                break;
            case "T":
                txtShowETD.setText("D");
                changeSaleType("D");
                break;
            case "D":
                txtShowETD.setText("E");
                changeSaleType("E");
                break;
            default:
                break;
        }
    }//GEN-LAST:event_txtShowETDMouseClicked

    private void tblShowBalanceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblShowBalanceMouseClicked
        if (evt.getClickCount() == 2) {
            selectedOptionBill();
        }
    }//GEN-LAST:event_tblShowBalanceMouseClicked

    private void btnHoldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoldActionPerformed
        showHoldTable();
    }//GEN-LAST:event_btnHoldActionPerformed

    private void btnPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaymentActionPerformed
        if (btnClickPrintKic == true) {
            String sqlTurnPrintKicOff = "update balance set r_kic='0' where r_kicprint<>'P' and r_table='" + tableNo + "';";
            MySQLConnect mysql = new MySQLConnect();
            try {
                mysql.open();
                mysql.getConnection().createStatement().executeUpdate(sqlTurnPrintKicOff);
                mysql.close();
            } catch (SQLException e) {
                MSG.ERR(e.getMessage());
                AppLogUtil.log(MainSale.class, "error", e.getMessage());
            } finally {
                mysql.close();
            }
        }

        if (lbTotalAmount.getText().equals("0.00")) {
            JOptionPane.showMessageDialog(this, "ไม่สามารถชำระเงินที่มูลค่าเป็น 0 ได้");
        } else {
            MySQLConnect mysql = new MySQLConnect();
            try {
                String sql = "update tablefile set tpause='Y' where tcode='" + tableNo + "';";
                mysql.open();
                mysql.getConnection().createStatement().executeUpdate(sql);
                kichenPrint();
                sql = "update tablefile set tpause='N' where tcode='" + tableNo + "';";
                mysql.getConnection().createStatement().executeUpdate(sql);
                mysql.close();
            } catch (SQLException e) {
                MSG.ERR(e.getMessage());
                AppLogUtil.log(MainSale.class, "error", e.getMessage());
            } finally {
                mysql.close();
            }

            showCheckBill();
        }

    }//GEN-LAST:event_btnPaymentActionPerformed

    private void btnSplitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSplitActionPerformed
        showSplitBill();
    }//GEN-LAST:event_btnSplitActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        cancelItemBeforeHold();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void txtTableFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTableFocusGained
        if (!Value.TableSelected.equals("")) {
            txtTable.setText(tableNo);
            Value.TableSelected = "";
        }
    }//GEN-LAST:event_txtTableFocusGained

    private void txtPluCodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPluCodeFocusGained
        txtPluCode.setEditable(true);
        txtPluCode.requestFocus();
    }//GEN-LAST:event_txtPluCodeFocusGained

    boolean isSelected = false;

    private void txtCustFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCustFocusGained
        isSelected = true;
    }//GEN-LAST:event_txtCustFocusGained

    private void txtCustMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCustMouseClicked
        CustomerCountDialog ccd = new CustomerCountDialog(null, true, txtTable.getText(), txtShowETD.getText());
        ccd.setVisible(true);

        showSum();
    }//GEN-LAST:event_txtCustMouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        MGRButtonMenu mgr = new MGRButtonMenu(null, true, buttonName, buttonIndex);
        mgr.setVisible(true);

        loadButtonProductMenu(refreshMenuButtonGroup(buttonName));
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if (tableNo.contains("T")) {
            txtShowETD.setText("T");
            changeSaleType("T");
            txtTypeDesc.setText(SALE_TAKE_AWAY);
        } else if (tableNo.contains("DE")) {
            txtShowETD.setText("D");
            changeSaleType("D");
            txtTypeDesc.setText(SALE_Delivery);
        } else if (tableNo.contains("E")) {
            txtShowETD.setText("E");
            changeSaleType("E");
            txtTypeDesc.setText(SALE_DINE_IN);
        }
        pMenu2.setVisible(false);
        pMenu3.setVisible(false);
        pMenu4.setVisible(false);
        pMenu5.setVisible(false);
        pMenu6.setVisible(false);

        isTakeOrder();

        if (!txtTypeDesc.getText().equals(SALE_DINE_IN)) {
            CustomerName ccd = new CustomerName(null, true, txtTable.getText(), txtShowETD.getText());
            ccd.setVisible(true);
        }
    }//GEN-LAST:event_formWindowOpened

    private void jMenuBar11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuBar11MouseClicked
        if (MMainMenu1.isVisible() == false && jMenu2.isVisible() == false) {
            MMainMenu1.setVisible(true);
            jMenu2.setVisible(true);
        } else {
            MMainMenu1.setVisible(false);
            jMenu2.setVisible(false);
        }
    }//GEN-LAST:event_jMenuBar11MouseClicked

    private void bntPrintCheckBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntPrintCheckBillActionPerformed
        double totalCheck = Double.parseDouble(lbTotalAmount.getText().replace(",", ""));
        if (totalCheck > 0) {
            MySQLConnect mysql = new MySQLConnect();
            mysql.open();

            try {
                Statement stmt = mysql.getConnection().createStatement();
                if (btnClickPrintKic == true) {
                    String sql = "update balance set r_kic='0' where r_kicprint<>'P' and mcno='" + PublicVar.MacNo + "';";
                    stmt.executeUpdate(sql);
                }

                stmt.executeUpdate("update tablefile set tpause='Y' where tcode='" + tableNo + "';");
                kichenPrint();
                stmt.executeUpdate("update tablefile set tpause='N' where tcode='" + tableNo + "';");

                kichenPrintAfterPrintCheck();
                printBillCheck();
            } catch (SQLException e) {
                MSG.ERR(e.getMessage());
                AppLogUtil.log(MainSale.class, "error", e.getMessage());
            } finally {
                mysql.close();
            }
        } else {
            MSG.WAR("มูลค่า 0 บาทไม่สามารถพิมพ์รายการได้");
        }
    }//GEN-LAST:event_bntPrintCheckBillActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        MGRButtonSetup mgr = new MGRButtonSetup(null, true, buttonName, buttonIndex);
        mgr.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jCheckBoxMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem3ActionPerformed
        OptionMenuSet browse = new OptionMenuSet(null, true);
        browse.setVisible(true);
    }//GEN-LAST:event_jCheckBoxMenuItem3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new KeyBoardDialog(null, true, 4).get(txtPluCode, 4);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtMember1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMember1MouseClicked
        MemberDialog MBD = new MemberDialog(this, true, tableNo);
        MBD.setVisible(true);

        this.memberBean = MemberBean.getMember(MBD.getMemCode());

        updateProSerTable(tableNo, memberBean);
        try {
            if (ValidateValue.isNotEmpty(memberBean.getMember_Code()) && memberBean != null) {
                txtMember1.setText(memberBean.getMember_NameThai());
                txtMember2.setText(QtyIntFmt.format(memberBean.getMember_TotalScore()));
                tblShowPluShow(txtTable.getText());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        if (memberBean == null) {
            memberBean = null;
            txtMember1.setText(" <ค้นหาสมาชิก> ");
            txtMember2.setText(": แต้มสะสม");
        }

    }//GEN-LAST:event_txtMember1MouseClicked

    private void tbpMainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbpMainMouseClicked
        clearHistory();
        addHistory(tbpMain.getSelectedIndex());
    }//GEN-LAST:event_tbpMainMouseClicked

    private void txtTypeDescMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTypeDescMouseClicked
        if (txtTypeDesc.getText().equals(SALE_DINE_IN)) {
            txtShowETD.setText("T");
            changeSaleType("T");
            txtTypeDesc.setText(SALE_TAKE_AWAY);
        } else if (txtTypeDesc.getText().equals(SALE_TAKE_AWAY)) {
            txtShowETD.setText("D");
            changeSaleType("D");
            txtTypeDesc.setText(SALE_Delivery);
        } else if (txtTypeDesc.getText().equals(SALE_Delivery)) {
            txtShowETD.setText("P");
            changeSaleType("P");
            txtTypeDesc.setText(SALE_Pinto);
        } else if (txtTypeDesc.getText().equals(SALE_Pinto)) {
            txtShowETD.setText("W");
            changeSaleType("W");
            txtTypeDesc.setText(SALE_WholeSale);
        } else if (txtTypeDesc.getText().equals(SALE_WholeSale)) {
            txtShowETD.setText("E");
            changeSaleType("E");
            txtTypeDesc.setText(SALE_DINE_IN);
        }
    }//GEN-LAST:event_txtTypeDescMouseClicked

    private void btnPrintKicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintKicActionPerformed
        String Check = btnPrintKic.getText();
        if (Check.equals("Print - OFF")) {
            btnClickPrintKic = false;
            btnPrintKic.setText("Print - ON");
            btnPrintKic.setBackground(Color.blue);
        }
        if (Check.equals("Print - ON")) {
            btnClickPrintKic = true;
            btnPrintKic.setText("Print - OFF");
            btnPrintKic.setBackground(Color.red);
        }
    }//GEN-LAST:event_btnPrintKicActionPerformed

    private void txtMember1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMember1ActionPerformed
        MemberDialog MBD = new MemberDialog(this, true, tableNo);
        MBD.setVisible(true);
        String memberCode = MBD.getMemCode();
        if (memberCode.equals("")) {
            txtMember1.setText(" <ค้นหาสมาชิก> ");
            txtMember2.setText(": แต้มสะสม");
        }
    }//GEN-LAST:event_txtMember1ActionPerformed

    private void btnLangTHItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_btnLangTHItemStateChanged
        PublicVar.languagePrint = "TH";
    }//GEN-LAST:event_btnLangTHItemStateChanged

    private void btnLangENItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_btnLangENItemStateChanged
        PublicVar.languagePrint = "EN";
    }//GEN-LAST:event_btnLangENItemStateChanged

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        MoveItemDialog move = new MoveItemDialog(null, true, txtTable.getText());
        move.setVisible(true);
        tblShowPluShow(txtTable.getText());

    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void cancelArPaymentClick() {
        PublicVar.TempUserRec = PublicVar.TUserRec;

        if (PublicVar.TUserRec.Sale6.equals("Y")) {
            CancelArPayment frm = new CancelArPayment(null, true);
            frm.setVisible(true);
        } else {
            GetUserAction getuser = new GetUserAction(null, true);
            getuser.setVisible(true);

            if (!PublicVar.ReturnString.equals("")) {
                String loginname = PublicVar.ReturnString;
                UserRecord supUser = new UserRecord();
                if (supUser.GetUserAction(loginname)) {
                    if (supUser.Sale6.equals("Y")) {
                        PublicVar.TUserRec = supUser;
                        CancelArPayment frm = new CancelArPayment(null, true);
                        frm.setVisible(true);
                    } else {
                        MSG.ERR(this, "รหัสพนักงานนี้ไม่สามารถเข้าใช้งาน...รายการนี้ได้...!!!");
                    }
                } else {
                    MSG.ERR(this, "ไม่สามารถ Load สิทธิ์การใช้งานของผู้ใช้งานคนนี้ได้ ...");
                }
            }
        }
        PublicVar.TUserRec = PublicVar.TempUserRec;

    }

    private void bntcheckbillClick() {
        if (!chkEJPath()) {
            return;
        }
        PublicVar.ErrorColect = false;
        PublicVar.ProcessType = "1"; //For Check Bill

        CheckBill frm = new CheckBill(null, true, txtTable.getText(), memberBean, "", "");
        frm.setVisible(true);

        if (PublicVar.SubTotalOK) {
            bntHoldTableClick();
            clearTable();
            tbpMain.setSelectedIndex(0);

            showTableOpen();
        }
    }

    private void bntPaymentClick() {
        if (!chkEJPath()) {
            return;
        }

        PublicVar.SubTotalOK = false;

        //visible MainSale
        setVisible(false);
        CheckBill frm = new CheckBill(null, true, txtTable.getText(), memberBean, "", "");
        frm.setVisible(true);
        if (PublicVar.SubTotalOK) {
            initScreen();
            clearTable();
            txtTable.setText("");
            tbpMain.setSelectedIndex(0);
            Value.TableSelected = "";
        } else {
            tblShowPluShow(txtTable.getText());
            showSum();
            setVisible(true);
        }
    }

    private void bntVoidClick() {
        int row = getSelectedRowIndex();
        if (row == -1) {
            return;
        }
        String R_Index = model.getValueAt(row, 10).toString();
        boolean isPermit = false;
        //check permission
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "select Username, Sale3,Name "
                    + "from posuser "
                    + "where username='" + Value.USERCODE + "' "
                    + "and Sale3='Y'";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                isPermit = true;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(MainSale.class, "error", e.getMessage());
        }

        if (isPermit) {//มีสิทธิ์ Void
            VoidPopupDialog voidD = new VoidPopupDialog(null, true, txtTable.getText(), memberBean);
            voidD.setVisible(true);
            if (!VoidPopupDialog.VOID_MSG[0].equals("")) {

                //check link r (26/02/2016 15:12)
                boolean hasValue = false;
                try {
                    String sql = "select R_Index, R_LinkIndex from balance "
                            + "where R_LinkIndex='" + R_Index + "'";
                    Statement stmt = mysql.getConnection().createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        hasValue = true;
                        procVoid(rs.getString("R_Index"), VoidPopupDialog.VOID_MSG[1], Value.USERCODE);
                    }

                    rs.close();
                    stmt.close();
                } catch (SQLException e) {
                    MSG.ERR(e.getMessage());
                    AppLogUtil.log(MainSale.class, "error", e.getMessage());
                }

                if (!hasValue) {
                    procVoid(R_Index, VoidPopupDialog.VOID_MSG[1], Value.USERCODE);
                }
                showCell(row, 0);
            }
        } else {
            if (!PublicVar.TableRec_PrintChkBill.equals("Y")) {
                GetUserAction getuser = new GetUserAction(null, true);
                getuser.setVisible(true);

                if (!PublicVar.ReturnString.equals("")) {
                    String loginname = PublicVar.ReturnString;
                    UserRecord supUser = new UserRecord();
                    if (supUser.GetUserAction(loginname)) {
                        if (supUser.Sale3.equals("Y")) {
                            PublicVar.TUserRec = supUser;
                            VoidPopupDialog voidD = new VoidPopupDialog(null, true, txtTable.getText(), memberBean);
                            voidD.setVisible(true);
                            if (!VoidPopupDialog.VOID_MSG[0].equals("")) {
                                //check link r (28/02/2016 15:12)
                                boolean hasValue = false;
                                try {
                                    String sql = "select R_Index, R_LinkIndex from balance "
                                            + "where R_LinkIndex='" + R_Index + "'";
                                    Statement stmt = mysql.getConnection().createStatement();
                                    ResultSet rs = stmt.executeQuery(sql);
                                    while (rs.next()) {
                                        hasValue = true;
                                        procVoid(rs.getString("R_Index"), VoidPopupDialog.VOID_MSG[1], loginname);
                                        txtDiscount.setText("- " + BalanceControl.GetDiscount(txtTable.getText()));
                                    }

                                    rs.close();
                                    stmt.close();
                                } catch (SQLException e) {
                                    MSG.ERR(e.getMessage());
                                    AppLogUtil.log(MainSale.class, "error", e.getMessage());
                                }

                                if (!hasValue) {
                                    procVoid(R_Index, VoidPopupDialog.VOID_MSG[1], loginname);
                                    txtDiscount.setText("- " + BalanceControl.GetDiscount(txtTable.getText()));
                                }
                                showCell(row, 0);
                            }
                        } else {
                            MSG.ERR(this, "รหัสพนักงานนี้ไม่สามารถเข้าใช้งาน...รายการนี้ได้...!!!");
                        }
                    } else {
                        MSG.ERR(this, "ไม่สามารถ Load สิทธิ์การใช้งานของผู้ใช้งานคนนี้ได้ ...");
                    }
                }
            } else {
                GetUserAction getuser = new GetUserAction(null, true);
                getuser.setVisible(true);

                if (!PublicVar.ReturnString.equals("")) {
                    String loginname = PublicVar.ReturnString;
                    UserRecord supUser = new UserRecord();
                    if (supUser.GetUserAction(loginname)) {
                        if (supUser.Sale4.equals("Y")) {
                            PublicVar.TUserRec = supUser;
                            VoidPopupDialog voidD = new VoidPopupDialog(null, true, txtTable.getText(), memberBean);
                            voidD.setVisible(true);
                            if (!VoidPopupDialog.VOID_MSG[0].equals("")) {

                                //check link r (28/02/2016 15:12)
                                boolean hasValue = false;
                                try {
                                    String sql = "select R_Index, R_LinkIndex from balance "
                                            + "where R_LinkIndex='" + R_Index + "'";
                                    Statement stmt = mysql.getConnection().createStatement();
                                    ResultSet rs = stmt.executeQuery(sql);
                                    while (rs.next()) {
                                        hasValue = true;
                                        procVoid(rs.getString("R_Index"), VoidPopupDialog.VOID_MSG[1], loginname);
                                    }

                                    rs.close();
                                    stmt.close();
                                } catch (SQLException e) {
                                    MSG.ERR(e.getMessage());
                                    AppLogUtil.log(MainSale.class, "error", e.getMessage());
                                }

                                if (!hasValue) {
                                    procVoid(R_Index, VoidPopupDialog.VOID_MSG[1], loginname);
                                }
                                showCell(row, 0);
                            }
                        } else {
                            MSG.ERR(this, "รหัสพนักงานนี้ไม่สามารถเข้าใช้งาน...รายการนี้ได้...!!!");
                        }
                    } else {
                        MSG.ERR(this, "ไม่สามารถ Load สิทธิ์การใช้งานของผู้ใช้งานคนนี้ได้ ...");
                    }
                }
            }
        }

        mysql.close();
    }

    private void procVoid(String RIndex, String voidMsg, String LoginName) {
        BalanceControl bc = new BalanceControl();
        BalanceBean bean = bc.getBalanceIndex(txtTable.getText(), RIndex);

        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();

        if (bean.getR_Void().equals("V")) {
            bean.setR_Void("");
            bean.setR_VoidUser("");
            bean.setR_VoidTime("");
            bean.setR_DiscBath(0.00);

            String StkRemark;
            String DocNo;
            String StkCode = PUtility.GetStkCode();
            if (PublicVar.ChargeCode.equals("")) {
                StkRemark = "SAL";
                DocNo = "EV" + txtTable.getText() + "/" + Timefmt.format(new Date());
            } else {
                StkRemark = "FRE";
                if (PublicVar.ChargeDocNo.equals("")) {
                    DocNo = txtTable.getText() + "/" + Timefmt.format(new Date());
                    PublicVar.ChargeDocNo = DocNo;
                } else {
                    DocNo = PublicVar.ChargeDocNo;
                }
            }

            Date TDate = new Date();
            PUtility.ProcessStockOut(DocNo, StkCode, bean.getR_PluCode(), TDate,
                    StkRemark, bean.getR_Quan(), bean.getR_Total(), bean.getCashier(),
                    bean.getR_Stock(), bean.getR_Set(), bean.getR_Index(), "1");

            //ตัดสต็อกสินค้าที่มี Ingredent
            try {
                String sql1 = "select i.*,pdesc,PBPack "
                        + "from product p, pingredent i "
                        + "where p.pcode=i.pingcode "
                        + "and i.pcode='" + bean.getR_PluCode() + "' "
                        + "and PFix='L' and PStock='Y'";
                Statement stmt1 = mysql.getConnection().createStatement();
                ResultSet rs = stmt1.executeQuery(sql1);
                while (rs.next()) {
                    String R_PluCode = rs.getString("PingCode");
                    double PBPack = rs.getDouble("PBPack");
                    if (PBPack <= 0) {
                        PBPack = 1;
                    }
                    double R_QuanIng = (rs.getDouble("PingQty") * bean.getR_Quan());
                    double R_Total = 0;
                    PUtility.ProcessStockOut(DocNo, StkCode, R_PluCode, TDate,
                            StkRemark, R_QuanIng, R_Total, bean.getCashier(),
                            "Y", "", "", "");//edit by  nathee 30/10/2016
                }

                rs.close();
                stmt1.close();
            } catch (SQLException e) {
                MSG.ERR(e.getMessage());
                AppLogUtil.log(MainSale.class, "error", e.getMessage());
            }
        } else {
            bean.setR_Void("V");
            bean.setR_VoidUser(LoginName);
            bean.setR_VoidTime(Timefmt.format(new Date()));
            bean.setR_DiscBath(0.00);

            String StkCode = PUtility.GetStkCode();
            String StkRemark;
            String DocNo;
            if (PublicVar.ChargeCode.equals("")) {
                StkRemark = "SAL";
                DocNo = "V" + txtTable.getText() + "/" + Timefmt.format(new Date());
            } else {
                StkRemark = "FRE";
                if (PublicVar.ChargeDocNo.equals("")) {
                    DocNo = txtTable.getText() + "/" + Timefmt.format(new Date());
                    PublicVar.ChargeDocNo = DocNo;
                } else {
                    DocNo = PublicVar.ChargeDocNo;
                }
            }

            Date TDate = new Date();
            PUtility.ProcessStockOut(DocNo, StkCode, bean.getR_PluCode(), TDate, StkRemark, -1 * bean.getR_Quan(), -1 * bean.getR_Total(),
                    PublicVar.TUserRec.UserCode, bean.getR_Stock(), bean.getR_Set(), bean.getR_Index(), "1");

            //ตัดสต็อกสินค้าที่มี Ingredent
            try {
                String sql1 = "select i.*,pdesc,PBPack "
                        + "from product p, pingredent i "
                        + "where p.pcode=i.pingcode "
                        + "and i.pcode='" + bean.getR_PluCode() + "' "
                        + "and PFix='L' and PStock='Y'";
                Statement stmt = mysql.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(sql1);
                while (rs.next()) {
                    String R_PluCode = rs.getString("PingCode");
                    double PBPack = rs.getDouble("PBPack");
                    if (PBPack <= 0) {
                        PBPack = 1;
                    }
                    String pname = ThaiUtil.ASCII2Unicode(rs.getString("pdesc"));
                    double R_QuanIng = (rs.getDouble("PingQty") * bean.getR_Quan());
                    double R_Total = 0;
                    PUtility.ProcessStockOut(DocNo, StkCode, R_PluCode, TDate, StkRemark, -1 * R_QuanIng, R_Total,
                            PublicVar.TUserRec.UserCode, "Y", "", "", "");
                }

                rs.close();
                stmt.close();
            } catch (SQLException e) {
                MSG.ERR(e.getMessage());
                AppLogUtil.log(MainSale.class, "error", e.getMessage());
            }
        }

        //Update  Balance File For Void
        try {
            String updBalance = "update balance "
                    + "set r_void='" + bean.getR_Void() + "',"
                    + "cashier='" + Value.USERCODE + "',"
                    + "r_emp='" + bean.getR_Emp() + "',"
                    + "r_voiduser='" + bean.getR_VoidUser() + "',"
                    + "r_voidtime='" + bean.getR_VoidTime() + "',"
                    + "r_discbath='" + bean.getR_DiscBath() + "',"
                    + "r_kicprint='',"
                    + "r_opt9='" + ThaiUtil.Unicode2ASCII(voidMsg) + "',"
                    + "voidmsg='" + ThaiUtil.Unicode2ASCII(voidMsg) + "' "
                    + "where r_index='" + bean.getR_Index() + "' "
                    + "and r_table='" + bean.getR_Table() + "'";
            Statement stmt = mysql.getConnection().createStatement();
            stmt.executeUpdate(updBalance);
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(this, e.getMessage());
            AppLogUtil.log(MainSale.class, "error", e.getMessage());
        }
        if ((bean.getR_Set().equals("Y")) && checkPSetSelect(bean.getR_PluCode())) {
            //Update  Balance File For Void
            try {
                String updateBalance = "update balance "
                        + "set r_void='" + bean.getR_Void() + "',"
                        + "cashier='" + Value.USERCODE + "',"
                        + "r_emp='" + bean.getR_Emp() + "',"
                        + "r_opt9='" + ThaiUtil.Unicode2ASCII(voidMsg) + "',"
                        + "voidmsg='" + ThaiUtil.Unicode2ASCII(voidMsg) + "' "
                        + "r_kicprint='' "
                        + "where r_index='" + bean.getR_Index() + "' "
                        + "and r_table='" + bean.getR_Table() + "'";
                Statement stmt = mysql.getConnection().createStatement();
                stmt.executeUpdate(updateBalance);
                stmt.close();
            } catch (SQLException e) {
                MSG.ERR(this, e.getMessage());
                AppLogUtil.log(MainSale.class, "error", e.getMessage());
            }
        }

        mysql.close();

        //update promotion, discount
        BalanceControl.updateProSerTable(txtTable.getText(), memberBean);
        PublicVar.ErrorColect = false;
        PublicVar.TableRec_DiscBath = 0.0;

        tblShowPluShow(txtTable.getText());
    }

    private void showCell(int row, int column) {
        int sizeTable = tblShowBalance.getRowCount();
        if (row > 0) {
            if (row > sizeTable - 1) {
                row = sizeTable - 1;
            }
            Rectangle rect = tblShowBalance.getCellRect(row, column, true);
            tblShowBalance.scrollRectToVisible(rect);
            tblShowBalance.clearSelection();
            tblShowBalance.setRowSelectionInterval(row, row);
        }
    }

    private void bntoptionClick() {
        int row = tblShowBalance.getSelectedRow();
        if (row != -1) {
            String RKicPrint = model.getValueAt(row, 8).toString();
            String RVoid = model.getValueAt(row, 5).toString();
            String RIndex = model.getValueAt(row, 10).toString();
            if (!RKicPrint.equals("P")) {
                if (!RVoid.equals("V")) {
                    OptionMsg frm = new OptionMsg(null, true, txtTable.getText(), RIndex);
                    frm.setVisible(true);
                } else {
                    MSG.WAR(this, "รายการนี้ได้ยกเลิกออกจากบิลแล้ว ไม่สามารถใส่ข้อความพิเศษได้ !");
                }

            } else {
                PUtility.ShowMsg("รายการนี้ได้มีการพิมพ์ออกครัวไปแล้ว...ไม่สามารถกำหนด Option เพิ่มเติมได้...");
                txtPluCode.requestFocus();
            }
        }
    }

    private int getSelectedRowIndex() {
        int row = tblShowBalance.getSelectedRow();
        if (row != -1) {
            return row;
        } else {
            return -1;
        }
    }

    boolean seekPluCode() {
        String PluCode;
        String StrQty;
        String TempCode = txtPluCode.getText();
        PublicVar.P_Code = "";
        PublicVar.P_Status = "";
        PublicVar.P_Qty = 0.0;
        boolean found = false;
        double Qty;
        if (TempCode.length() > 0) {
            int index = TempCode.indexOf("*");
            if (index != -1) {
                StrQty = TempCode.substring(0, index);
                PluCode = TempCode.substring(index + 1);
                if (!PUtility.ChkNumValue(StrQty)) {
                    MSG.ERR("ป้อนจำนวนไม่ถูกต้อง..กรุณาป้อนใหม่...");
                    txtPluCode.setText("");
                    txtPluCode.requestFocus();
                }
                Qty = Double.parseDouble(StrQty);
            } else {
                Qty = 1;
                PluCode = TempCode;
            }
            /**
             * * OPEN CONNECTION **
             */
            MySQLConnect mysql = new MySQLConnect();
            mysql.open();
            if (Qty > 0) {
                if (POSHW.getMenuItemList().equals("Y")) {
                    if (PluCode.length() <= 3) {
                        try {
                            Statement stmt = mysql.getConnection().createStatement();
                            String SqlQuery = "select *from menulist "
                                    + "where menuitem=('" + PluCode + "') "
                                    + "and (menuactive='Y')";
                            ResultSet rec = stmt.executeQuery(SqlQuery);
                            rec.first();
                            if (rec.getRow() == 0) {
                                MSG.ERR("ไม่พบรหัส Menu Items " + PluCode + " ในฐานข้อมูล !!!");
                                txtPluCode.setText("");
                                rec.close();
                                stmt.close();
                                txtPluCode.selectAll();
                                txtPluCode.requestFocus();
                                return false;
                            } else {
                                PluCode = rec.getString("plucode");
                            }
                            rec.close();
                            stmt.close();

                        } catch (SQLException e) {
                            MSG.ERR(e.getMessage());
                            AppLogUtil.log(MainSale.class, "error", e.getMessage());
                        }
                    }
                }
                try {
                    Statement stmt = mysql.getConnection().createStatement();
                    String sql = "select * "
                            + "from product "
                            + "where pcode='" + PluCode + "' "
                            + "and pactive='Y'";
                    ResultSet rec = stmt.executeQuery(sql);
                    rec.first();
                    if (rec.getRow() == 0) {
                        TempStatus = "";
                        String TempCode2 = seekBarCode(PluCode);
                        if (TempCode2.equals("")) {
                            MSG.ERR("ไม่พบรหัสสินค้า " + PluCode + " ในฐานข้อมูล หรือรหัสสินค้านี้อาจถูกยกเลิกการขายแล้ว...");
                            txtPluCode.setText("");
                        } else {
                            PluCode = TempCode2;
                            found = true;
                            PublicVar.P_Code = PluCode;
                            PublicVar.P_Status = TempStatus;
                            PublicVar.P_Qty = Qty;
                        }
                        rec.close();
                        stmt.close();

                        txtPluCode.selectAll();
                        txtPluCode.requestFocus();
                    } else {
                        found = true;
                        PublicVar.P_Code = PluCode;
                        PublicVar.P_Status = rec.getString("pstatus");
                        PublicVar.P_Qty = Qty;
                    }
                    rec.close();
                    stmt.close();

                } catch (SQLException e) {
                    MSG.ERR(e.getMessage());
                    AppLogUtil.log(MainSale.class, "error", e.getMessage());
                }
            } else {
                PUtility.ShowMsg("จำนวนขายต้องมากกว่า 0...");
                txtPluCode.requestFocus();
            }

            mysql.close();
        }

        return found;
    }

    private void changTypeClick() {
        boolean ChangOk = false;
        int row = tblShowBalance.getSelectedRow();
        if (row != -1) {
            if (!txtTable.getText().trim().equals("")) {
                PublicVar.ChangTypeOK = false;
                ChangTypeDialog frm = new ChangTypeDialog(null, true, txtTable.getText(), txtShowETD.getText());
                frm.setVisible(true);
                if (PublicVar.ChangTypeOK) {
                    if (ChangOk) {
                        clearDataMem();
                        tblShowPluShow(txtTable.getText());
                        txtPluCode.requestFocus();
                    }
                }
            }
        }
    }

    private void clearDataMem() {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DAY_OF_MONTH, -30);
    }

    private boolean findPluCode() {
        String PluCode;
        String StrQty;
        String TempCode = txtPluCode.getText();
        if (TempCode != null) {
            if (TempCode.substring(0, 1).equals("*")) {
                TempCode = TempCode.replace("*", "");
            }
        }
        PublicVar.P_Code = "";
        PublicVar.P_Status = "";
        PublicVar.P_Qty = 0.0;
        boolean found = false;
        double Qty;

        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        //check outofstock
        try {
            String sql = "select * from outstocklist "
                    + "where pcode='" + TempCode + "'";
            try ( Statement stmt = mysql.getConnection().createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    MSG.WAR("สินค้ามีไม่ในสต๊อก หรือถูกยกเลิกการขายไปแล้ว กรุณาตรวจสอบ !!!");
                    txtPluCode.setText("");
                    txtPluCode.requestFocus();
                    return false;
                }
                rs.close();
            }
        } catch (SQLException e) {
            MSG.ERR(this, e.getMessage());
            AppLogUtil.log(MainSale.class, "error", e.getMessage());
            return false;
        }

        if (TempCode != null && TempCode.length() > 0) {
            int index = TempCode.indexOf("*");
            if (index != -1) {
                StrQty = TempCode.substring(0, index);
                PluCode = TempCode.substring(index + 1);
                if (!PUtility.ChkNumValue(StrQty)) {
                    MSG.ERR("ป้อนจำนวนไม่ถูกต้อง..กรุณาป้อนใหม่...");
                    txtPluCode.setText("");
                    txtPluCode.requestFocus();
                }
                Qty = Double.parseDouble(StrQty);
            } else {
                Qty = 1;
                PluCode = TempCode;
            }
            if (Qty > 0) {

                //for menuitem
                if (PluCode.length() <= 3) {
                    try {
                        if (POSHW.getMenuItemList().equals("Y")) {
                            Statement stmt = mysql.getConnection().createStatement();
                            String SqlQuery = "select *from menulist "
                                    + "where menuitem=('" + PluCode + "') and (menuactive='Y')";
                            ResultSet rec = stmt.executeQuery(SqlQuery);
                            rec.first();
                            if (rec.getRow() == 0) {
                                MSG.WAR("ไม่พบรหัส Menu Items " + PluCode + " ในฐานข้อมูล !!!");
                                txtPluCode.setText("");
                                rec.close();
                                stmt.close();
                                txtPluCode.selectAll();
                                txtPluCode.requestFocus();
                                return false;
                            } else {
                                PluCode = rec.getString("plucode");
                            }
                            rec.close();
                            stmt.close();
                        }
                    } catch (SQLException e) {
                        MSG.ERR(e.getMessage());
                        AppLogUtil.log(MainSale.class, "error", e.getMessage());
                    }
                }
                try {
                    Statement stmt = mysql.getConnection().createStatement();
                    String SqlQuery = "select *from product "
                            + "where pcode='" + PluCode + "' and pactive='Y'";
                    ResultSet rec = stmt.executeQuery(SqlQuery);
                    rec.first();
                    if (rec.getRow() == 0) {
                        TempStatus = "";
                        String TempCode2 = seekBarCode(PluCode);
                        if (TempCode2.equals("")) {
                            MSG.ERR("ไม่พบรหัสสินค้า " + PluCode + " ในฐานข้อมูล หรือรหัสสินค้านี้อาจถูกยกเลิกการขายแล้ว...");
                            txtPluCode.setText("");
                        } else {
                            PluCode = TempCode2;
                            found = true;
                            PublicVar.P_Code = PluCode;
                            PublicVar.P_Status = TempStatus;
                            PublicVar.P_Qty = Qty;
                        }
                        rec.close();
                        stmt.close();

                        txtPluCode.selectAll();
                        txtPluCode.requestFocus();
                    } else {
                        found = true;
                        PublicVar.P_Code = PluCode;
                        PublicVar.P_Status = rec.getString("pstatus");
                        PublicVar.P_Qty = Qty;
                    }
                    rec.close();
                    stmt.close();

                } catch (SQLException e) {
                    MSG.ERR(e.getMessage());
                    AppLogUtil.log(MainSale.class, "error", e.getMessage());
                }
            } else {
                PUtility.ShowMsg("จำนวนขายต้องมากกว่า 0...");
                txtPluCode.requestFocus();
            }
        }

        mysql.close();

        return found;
    }

    private boolean checkPSetSelect(String PCode) {
        boolean isValid = false;
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            Statement stmt = mysql.getConnection().createStatement();
            String SqlQuery = "select * from product where pcode='" + PCode + "' and pactive='Y'";
            ResultSet rec = stmt.executeQuery(SqlQuery);
            rec.first();
            if (rec.getRow() == 0) {
                isValid = false;
            }
            rec.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(this, e.getMessage());
            AppLogUtil.log(MainSale.class, "error", e.getMessage());
        } finally {
            mysql.close();
        }

        return isValid;
    }

    private String seekBarCode(String BarCode) {
        String RetVal = "";
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            Statement stmt = mysql.getConnection().createStatement();
            String SqlQuery = "select * from product where pbarcode='" + BarCode + "' and pactive='Y'";
            ResultSet rec = stmt.executeQuery(SqlQuery);
            rec.first();
            if (rec.getRow() == 0) {
                RetVal = "";
                TempStatus = "";
            } else {
                RetVal = rec.getString("pcode");
                TempStatus = rec.getString("pstatus");
            }
            rec.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(MainSale.class, "error", e.getMessage());
        } finally {
            mysql.close();
        }

        return RetVal;
    }

    private void saveToBalance() {
        String PCode = txtPluCode.getText();

        String StkCode = PUtility.GetStkCode();
        String emp = Value.EMP_CODE;
        String etd = txtShowETD.getText();
        String[] data = Option.splitPrice(PCode);
        double R_Quan = Double.parseDouble(data[0]);
        PCode = data[1];

        ProductControl pCon = new ProductControl();
        ProductBean productBean = pCon.getData(PCode);
        BalanceBean balance = new BalanceBean();
        balance.setStkCode(StkCode);

        double Price = 0.00;
        if (productBean.getPStatus().equals("S")) {
            txtPluCode.setEditable(false);
        }

        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            Statement stmt = mysql.getConnection().createStatement();
            String SqlQuery = "select * from product where pcode='" + PCode + "' and pactive='Y'";
            ResultSet rs = stmt.executeQuery(SqlQuery);
            rs.first();
            if (rs.getRow() == 0) {
                MSG.ERR("ไม่พบรหัสสินค้า " + PCode + " ในฐานข้อมูล หรือ รหัสสินค้านี้อาจยกเลิกการขายแล้ว...");
                txtPluCode.setText("");
                rs.close();
                stmt.close();
                txtPluCode.selectAll();
                txtPluCode.requestFocus();
            } else {
                if (!PUtility.CheckStockOK(PCode, R_Quan)) {
                    txtPluCode.setText("");
                    txtPluCode.selectAll();
                    txtPluCode.requestFocus();
                } else {
                    balance.setR_Opt1(GetQty.OPTION_TEXT[0]);
                    balance.setR_Opt2(GetQty.OPTION_TEXT[1]);
                    balance.setR_Opt3(GetQty.OPTION_TEXT[2]);
                    balance.setR_Opt4(GetQty.OPTION_TEXT[3]);
                    balance.setR_Opt5(GetQty.OPTION_TEXT[4]);
                    balance.setR_Opt6(GetQty.OPTION_TEXT[5]);
                    balance.setR_Opt7(GetQty.OPTION_TEXT[6]);
                    balance.setR_Opt8(GetQty.OPTION_TEXT[7]);
                    balance.setR_Opt9(GetQty.OPTION_TEXT[8]);

                    GetQty.clear();//clear temp option
                    balance.setR_PrintOK(PublicVar.PrintOK);
                    balance.setMacno(Value.MACNO);
                    balance.setCashier(Value.USERCODE);
                    balance.setR_ETD(etd);
                    balance.setR_Quan(R_Quan);
                    balance.setR_Table(txtTable.getText());
                    balance.setR_Emp(emp);

                    balance.setR_PrCuType("");
                    balance.setR_PrCuQuan(0.00);
                    balance.setR_PrCuAmt(0.00);

                    balance.setR_PluCode(productBean.getPCode());
                    balance.setR_Group(productBean.getPGroup());
                    balance.setR_Status(productBean.getPStatus());
                    balance.setR_Normal(productBean.getPNormal());
                    balance.setR_Discount(productBean.getPDiscount());
                    balance.setR_Service(productBean.getPService());
                    balance.setR_Vat(productBean.getPVat());
                    balance.setR_Type(productBean.getPType());
                    balance.setR_Stock(productBean.getPStock());
                    balance.setR_PName(productBean.getPDesc());
                    balance.setR_PEName(productBean.getPEDesc());
                    balance.setR_Unit(productBean.getPUnit1());
                    balance.setR_Set(productBean.getPSet());

                    if (balance.getR_Status().equals("P")) {
                        switch (etd) {
                            case "E":
                                balance.setR_Price(productBean.getPPrice11());
                                break;
                            case "T":
                                balance.setR_Price(productBean.getPPrice12());
                                break;
                            case "D":
                                balance.setR_Price(productBean.getPPrice13());
                                break;
                            case "P":
                                balance.setR_Price(productBean.getPPrice14());
                                break;
                            case "W":
                                balance.setR_Price(productBean.getPPrice15());
                                break;
                            default:
                                txtShowETD.setText("E");
                                break;
                        }
                    } else {
                        balance.setR_Price(Price);
                    }

                    balance.setR_Total(balance.getR_Quan() * balance.getR_Price());
                    balance.setR_PrChkType("");

                    BalanceControl balanceControl = new BalanceControl();
                    String R_Index = balanceControl.getIndexBalance(balance.getR_Table());
                    balance.setR_Index(R_Index);

                    // for member discount
                    if (Value.MemberAlready && balance.getR_Discount().equals("Y")) {
                        balance.setR_PrSubType("-M");
                        balance.setR_PrSubCode("MEM");
                        balance.setR_PrSubQuan(balance.getR_Quan());

                        // คำนวณหาว่าลดเท่าไหร่
                        String[] subPercent = memberBean.getMember_DiscountRate().split("/");
                        int Percent = 0;
                        if (subPercent.length == 3) {
                            switch (balance.getR_Normal()) {
                                case "N":
                                    Percent = Integer.parseInt(subPercent[0].trim());
                                    break;
                                case "C":
                                    Percent = Integer.parseInt(subPercent[1].trim());
                                    break;
                                case "S":
                                    Percent = Integer.parseInt(subPercent[2].trim());
                                    break;
                                default:
                                    break;
                            }
                        }
                        balance.setR_PrSubDisc(Percent);
                        balance.setR_PrSubBath(0);
                        balance.setR_PrSubAmt((balance.getR_Total() * Percent) / 100);
                        balance.setR_QuanCanDisc(0);// if member default 0
                    } else {
                        memberBean = null;
                        // for not member
                        balance.setR_PrSubType("");
                        balance.setR_PrSubCode("");
                        balance.setR_PrSubQuan(0);// not member default 0
                        balance.setR_PrSubDisc(0);
                        balance.setR_PrSubBath(0);
                        balance.setR_PrSubAmt(0);
                        balance.setR_QuanCanDisc(balance.getR_Quan());
                    }

                    balance.setR_Pause("P");

                    balanceControl.saveBalance(balance);

                    //update temptset
                    updateTempTset(balance);

                    stmt.close();

                    //Process Stock Out
                    String StkRemark = "SAL";
                    String DocNo = txtTable.getText() + "/" + Timefmt.format(new Date());
                    if (productBean.getPStock().equals("Y") && productBean.getPActive().equals("Y")) {
                        PUtility.ProcessStockOut(DocNo, StkCode, balance.getR_PluCode(), new Date(), StkRemark, balance.getR_Quan(), balance.getR_Total(),
                                balance.getCashier(), balance.getR_Stock(), balance.getR_Set(), R_Index, "1");
                    }

                    //ตัดสต็อกสินค้าที่มี Ingredent
                    try {
                        String sql = "select i.*,pdesc,PBPack,pstock,pactive "
                                + "from product p, pingredent i "
                                + "where p.pcode=i.pingcode "
                                + "and i.pcode='" + balance.getR_PluCode() + "' "
                                + "and PFix='L' and PStock='Y'";
                        Statement stmt1 = mysql.getConnection().createStatement();
                        ResultSet rsIn = stmt1.executeQuery(sql);
                        while (rsIn.next()) {
                            if (rsIn.getString("pstock").equals("Y") && rsIn.getString("pactive").equals("Y")) {
                                String R_PluCode = rsIn.getString("PingCode");
                                double PBPack = rsIn.getDouble("PBPack");
                                if (PBPack <= 0) {
                                    PBPack = 1;
                                }
                                double R_QuanIng = (rsIn.getDouble("PingQty") * balance.getR_Quan());
                                double R_Total = 0;
                                PUtility.ProcessStockOut(DocNo, StkCode, R_PluCode, new Date(), StkRemark, R_QuanIng, R_Total,
                                        balance.getCashier(), "Y", "", "", "");
                            }

                        }

                        rsIn.close();
                        stmt1.close();
                    } catch (SQLException e) {
                        MSG.ERR(e.getMessage());
                        AppLogUtil.log(MainSale.class, "error", e.getMessage());
                    }

                    //ตัดสต็อกสินค้าที่เป็นชุด SET (PSET)
                    try {
                        String sqlPSET = "select * from pset where pcode='" + balance.getR_PluCode() + "';";
                        Statement stmt1 = mysql.getConnection().createStatement();
                        ResultSet rsPSET = stmt1.executeQuery(sqlPSET);
                        while (rsPSET.next()) {
                            String pSubCode = rsPSET.getString("psubcode");
                            double pSubQTY = rsPSET.getDouble("psubQTY");
                            PUtility.ProcessStockOut(DocNo, StkCode, pSubCode, new Date(), "A1", pSubQTY * balance.getR_Quan(), 0.00,
                                    balance.getCashier(), "Y", "", "", "");
                        }
                        stmt1.close();
                        rsPSET.close();
                    } catch (SQLException e) {
                        MSG.NOTICE(e.toString());
                    }
                    //update promotion
                    BalanceControl.updateProSerTable(txtTable.getText(), memberBean);
                    String Discount = BalanceControl.GetDiscount(txtTable.getText());
                    txtDiscount.setText("- " + Discount);
                    PublicVar.ErrorColect = true;
                } //end of Load Data
            }
            rs.close();
        } catch (SQLException e) {
            MSG.ERR(this, e.getMessage());
            AppLogUtil.log(MainSale.class, "error", e.getMessage());
            
            txtPluCode.requestFocus();
        } finally {
            mysql.close();
        }
    }

    private void kichenPrint() {
        PrintSimpleForm printSimpleForm = new PrintSimpleForm();
        try {
            String printerName;
            String[] kicMaster = BranchControl.getKicData20();
            // หาจำนวนปริ้นเตอร์ว่าต้องออกกี่เครื่อง
            String sqlShowKic = "select r_kic,r_etd from balance "
                    + "where r_table='" + tableNo + "' "
                    + "and R_PrintOK='Y' "
                    + "and R_KicPrint<>'P' "
                    + "and R_Kic<>'' "
                    + "group by r_kic,r_etd "
                    + "order by r_kic,r_etd";
            /**
             * * OPEN CONNECTION **
             */
            MySQLConnect mysql = new MySQLConnect();
            mysql.open();
            String sqlGetSaveOrder = "select SaveOrder from branch";

            try {
                ResultSet rsGetSaveOrderConfig = mysql.getConnection().createStatement().executeQuery(sqlGetSaveOrder);
                if (rsGetSaveOrderConfig.next() && !rsGetSaveOrderConfig.wasNull()) {
                    String config = rsGetSaveOrderConfig.getString("SaveOrder");
                    if (!config.equals("N")) {
                        PublicVar.Branch_Saveorder = config;
                    }
                }

                Statement stmt1 = mysql.getConnection().createStatement();
                ResultSet rsKic = stmt1.executeQuery(sqlShowKic);

                ResultSet rsKicSaveOrder = mysql.getConnection().createStatement().executeQuery(sqlShowKic);
                if (rsKicSaveOrder.next() && !rsKicSaveOrder.wasNull()) {
                    if (!PublicVar.Branch_Saveorder.equals("N")) {
                        printSimpleForm.KIC_FORM_SaveOrder("", "SaveOrder", tableNo, 0);
                    }
                }
                rsKicSaveOrder.close();

                //วนคำสั่งเพื่อพิมพ์ให้ครบทุกครัว
                while (rsKic.next()) {
                    String rKic = rsKic.getString("r_kic");
                    if (!rKic.equals("")) {
                        try {
                            int iKic = Integer.parseInt(rKic);
                            if (iKic - 1 < 0) {
                                //ถ้าเป็น iKic=0 จะเป็นการไม่กำหนดให้ปริ้นออกครัว
                            } else {
                                if (kicMaster[iKic - 1].equals("N")) {
                                    //NOT PRINT or Print already
                                } else {
                                    printerName = "kic" + rKic;
                                    String printerForm = BranchControl.getForm(rKic);
                                    if (printerForm.equals("1")) {
                                        String sql1 = "select * from balance "
                                                + "where r_table='" + txtTable.getText() + "' "
                                                + "and R_PrintOK='Y' "
                                                + "and R_KicPrint<>'P' "
                                                + "and R_Kic<>'' "
                                                + "and R_kic='" + rKic + "' "
                                                + "group by r_plucode";
                                        printerName = "kic" + rKic;
                                        Statement stmt2 = mysql.getConnection().createStatement();
                                        ResultSet rs1 = stmt2.executeQuery(sql1);
                                        while (rs1.next()) {
                                            String PCode = rs1.getString("R_PluCode");
                                            if (printerForm.equals("1")) {
                                                if (Value.printkic) {
                                                    printSimpleForm.KIC_FORM_1(printerName, txtTable.getText(), new String[]{PCode});
                                                }
                                            }
                                        }
                                        rs1.close();
                                        stmt2.close();

                                    } else if (printerForm.equals("6")) {
                                        String sql2 = "select sum(b.r_quan) R_Quan,sum(b.r_quan)*b.r_price Total, b.* from balance b "
                                                + "where r_table='" + txtTable.getText() + "' "
                                                + "and R_PrintOK='Y' "
                                                + "and R_KicPrint<>'P' "
                                                + "and R_Kic<>'' "
                                                + "and R_KIC='" + rKic + "' "
                                                + "group by r_plucode,r_void order by r_opt1";
                                        Statement stmt2 = mysql.getConnection().createStatement();
                                        ResultSet rs2 = stmt2.executeQuery(sql2);
                                        while (rs2.next()) {
                                            if (Value.printkic) {
                                                String R_Index = rs2.getString("R_Index");
                                                String R_PLUCODE = rs2.getString("R_Plucode");
                                                double qty = rs2.getDouble("R_Quan");
                                                double priceTotal = rs2.getDouble("Total");
                                                printSimpleForm.KIC_FORM_6(printerName, txtTable.getText(), R_Index, R_PLUCODE, qty, priceTotal);
                                            }
                                        }
                                        rs2.close();
                                        stmt2.close();
                                    } else if (printerForm.equals("3") || printerForm.equals("4") || printerForm.equals("5")) {

                                        if (printerForm.equals("3")) {

                                            if (Value.printkic) {
//                                                printSimpleForm.KIC_FORM_3("", printerName, txtTable.getText(), iKic);
                                                String retd = rsKic.getString("r_etd");
                                                printSimpleForm.KIC_FORM_3New(printerName, tableNo, iKic, retd, "");
//                                                String CheckBillBeforeCash = CONFIG.getP_CheckBillBeforCash();
                                                String CheckBillBeforeCash = CONFIG.getP_CheckBillBeforCash();
                                                if (CheckBillBeforeCash.equals("Y")) {
                                                    printBillVoidCheck();
                                                }
                                            }
                                        } else if (printerForm.equals("4")) {
                                            if (Value.printkic) {
                                                printSimpleForm.KIC_FORM_4(printerName, txtTable.getText());
                                                printBillVoidCheck();
                                            }
                                        } else if (printerForm.equals("5")) {
                                            if (Value.printkic) {
                                                printSimpleForm.KIC_FORM_5(printerName, txtTable.getText());
                                                printBillVoidCheck();
                                            }
                                        }

                                    } else if (printerForm.equals("7") || printerForm.equals("2")) {
                                        if (Value.printkic) {
                                            printSimpleForm.KIC_FORM_7(printerName, txtTable.getText());
                                            printBillVoidCheck();
                                        }
                                    } else {
                                        printBillVoidCheck();
                                        MSG.ERR(this, "ไม่พบฟอร์มปริ้นเตอร์ครัวในระบบที่สามารใช้งานได้ !!!");
                                    }
                                }
                            }
                        } catch (SQLException e) {
                            MSG.ERR(this, e.getMessage());
                            AppLogUtil.log(MainSale.class, "error", e.getMessage());
                        }
                    }
                }

                CheckKicPrint();

                //update r_kicprint
                try {
                    String sql = "update balance "
                            + "set r_kicprint='P',"
                            + "r_pause='Y' "
                            + "where r_table='" + txtTable.getText() + "' "
                            + "and r_kicprint<>'P' "
                            + "and r_printOk='Y' "
                            + "and r_kic<>'';";
                    mysql.open();
                    Statement stmt = mysql.getConnection().createStatement();
                    stmt.executeUpdate(sql);
                    mysql.close();
                } catch (SQLException e) {
                    MSG.ERR(this, e.getMessage());
                    AppLogUtil.log(MainSale.class, "error", e.getMessage());
                }
                rsKic.close();
                stmt1.close();

            } catch (SQLException e) {
                MSG.ERR(null, e.getMessage());
                AppLogUtil.log(MainSale.class, "error", e.getMessage());
            } finally {
                mysql.close();
            }

        } catch (HeadlessException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSale.class, "error", e.getMessage());
        }
    }

    private void kichenPrintAfterPrintCheck() {
        PrintSimpleForm printSimpleForm = new PrintSimpleForm();

        try {
            String printerName;
            String[] kicMaster = BranchControl.getKicData20();
            // หาจำนวนปริ้นเตอร์ว่าต้องออกกี่เครื่อง
            String sqlShowKic = "select r_kic from balance "
                    + "where r_table='" + txtTable.getText() + "' "
                    + "and R_PrintOK='Y' "
                    + "and R_KicPrint<>'P' "
                    + "and R_Kic<>'' "
                    + "group by r_kic "
                    + "order by r_kic";
            /**
             * * OPEN CONNECTION **
             */
            MySQLConnect mysql = new MySQLConnect();
            mysql.open();
            try {
                Statement stmt1 = mysql.getConnection().createStatement();
                ResultSet rsKic = stmt1.executeQuery(sqlShowKic);
                while (rsKic.next()) {
                    String rKic = rsKic.getString("r_kic");
                    if (!rKic.equals("")) {
                        try {
                            int iKic = Integer.parseInt(rKic);
                            if (iKic - 1 < 0) {
                                //ถ้าเป็น iKic=0 จะเป็นการไม่กำหนดให้ปริ้นออกครัว
                            } else {
                                if (kicMaster[iKic - 1].equals("N")) {
                                    //NOT PRINT or Print already
                                } else {
                                    printerName = "KIC" + rKic;
                                    String printerForm = BranchControl.getForm(rKic);
                                    if (printerForm.equals("1") || printerForm.equals("2")) {
                                        String sql1 = "select * from balance "
                                                + "where r_table='" + txtTable.getText() + "' "
                                                + "and R_PrintOK='Y' "
                                                + "and R_KicPrint<>'P' "
                                                + "and R_Kic<>'' "
                                                + "and R_Void <> 'V' "
                                                + "group by r_plucode";
                                        Statement stmt2 = mysql.getConnection().createStatement();
                                        ResultSet rs1 = stmt2.executeQuery(sql1);
                                        while (rs1.next()) {
                                            String PCode = rs1.getString("R_PluCode");
                                            if (printerForm.equals("1")) {
                                                if (Value.printkic) {
                                                    printSimpleForm.KIC_FORM_1(printerName, txtTable.getText(), new String[]{PCode});
                                                }
                                            } else if (printerForm.equals("2")) {
                                                if (Value.printkic) {
                                                    printSimpleForm.KIC_FORM_2(printerName, txtTable.getText(), new String[]{PCode});
                                                }
                                            }
                                        }

                                        rs1.close();
                                        stmt2.close();
                                    } else if (printerForm.equals("6")) {
                                        String sql2 = "select sum(b.r_quan) R_Quan,sum(b.r_quan)*b.r_price Total, b.* from balance b "
                                                + "where r_table='" + txtTable.getText() + "' "
                                                + "and R_PrintOK='Y' "
                                                + "and R_KicPrint<>'P' "
                                                + "and R_Kic<>'' "
                                                + "and R_Void<>'V' and R_KIC='" + rKic + "' "
                                                + "group by r_plucode order by r_opt1";
                                        Statement stmt2 = mysql.getConnection().createStatement();
                                        ResultSet rs2 = stmt2.executeQuery(sql2);
                                        while (rs2.next()) {
                                            if (Value.printkic) {
                                                String R_Index = rs2.getString("R_Index");
                                                String R_PLUCODE = rs2.getString("R_Plucode");
                                                double qty = rs2.getDouble("R_Quan");
                                                double priceTotal = rs2.getDouble("Total");
                                                printSimpleForm.KIC_FORM_6(printerName, txtTable.getText(), R_Index, R_PLUCODE, qty, priceTotal);
                                            }
                                        }

                                        rs2.close();
                                        stmt2.close();
                                    } else if (printerForm.equals("3") || printerForm.equals("4") || printerForm.equals("5")) {

                                        if (printerForm.equals("3")) {
                                            if (Value.printkic) {
                                                printSimpleForm.KIC_FORM_3("", printerName, txtTable.getText(), iKic);
                                            }
                                        } else if (printerForm.equals("4")) {
                                            if (Value.printkic) {
                                                printSimpleForm.KIC_FORM_4(printerName, txtTable.getText());
                                            }
                                        } else if (printerForm.equals("5")) {
                                            if (Value.printkic) {
                                                printSimpleForm.KIC_FORM_5(printerName, txtTable.getText());
                                            }
                                        }
                                    } else {
                                        MSG.ERR(this, "ไม่พบฟอร์มปริ้นเตอร์ในระบบที่สามารใช้งานได้ !!!");
                                    }
                                }
                            }
                        } catch (SQLException e) {
                            MSG.ERR(this, e.getMessage());
                            AppLogUtil.log(MainSale.class, "error", e.getMessage());
                        }
                    }
                }

                rsKic.close();
                stmt1.close();

                //update r_kicprint
                String sql = "update balance "
                        + "set r_kicprint='P',"
                        + "r_pause='Y' "
                        + "where r_table='" + txtTable.getText() + "' "
                        + "and r_kicprint<>'P' "
                        + "and r_printOk='Y' "
                        + "and r_kic<>'';";
                Statement stmt = mysql.getConnection().createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
            } catch (SQLException e) {
                MSG.ERR(null, e.getMessage());
                AppLogUtil.log(MainSale.class, "error", e.getMessage());
            } finally {
                mysql.close();
            }
        } catch (HeadlessException e) {
            MSG.ERR(null, "HeadlessException:" + e.getMessage());
        }
    }

    private void changeSaleType(String SaleType) {
        // description กำหนดประเภทการขาย
        switch (SaleType) {
            case "E":
                txtTypeDesc.setText(SALE_DINE_IN);
                break;
            case "T":
                txtTypeDesc.setText(SALE_TAKE_AWAY);
                break;
            case "D":
                txtTypeDesc.setText(SALE_Delivery);
                break;
            default:
                break;
        }

        String oldType = txtShowETD.getText();
        if (SaleType.equals("E")) {
            if (oldType.equals("E") || oldType.equals("T") || model.getRowCount() == 0) {
                txtShowETD.setText("E");
            } else {
                MSG.ERR(this, "คุณกำหนดประเภทการขาย (Sale Type)ไม่ถูกต้อง !!!");
            }
        } else if (SaleType.equals("T")) {
            if (((oldType.equals("E")) | (oldType.equals("T")) | (model.getRowCount() == 0))
                    & !PublicVar.ChargeGroup.equals("2")) {
                txtShowETD.setText("T");
            } else {
                MSG.ERR(this, "คุณกำหนดประเภทการขาย (Sale Type)ไม่ถูกต้อง !!!");
            }
        }
    }

    private void txtTableOnEnter() {
        txtCust.setEditable(false);

        int RowCount = model.getRowCount();
        for (int i = 0; i <= RowCount - 1; i++) {
            model.removeRow(0);
        }
    }

    private void txtTableOnExit() {
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            Statement stmt = mysql.getConnection().createStatement();
            String sql = "select * from tablefile "
                    + "where tcode='" + txtTable.getText().trim() + "'";
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                if (rs.getString("tonact").equals("Y") && rs.getDouble("TAmount") > 0) {
                    MSG.WAR("มีพนักงานกำลังป้อนรายการโต๊ะนี้อยู่...");
                    TableOpenStatus = false;
                    txtTable.setText("");
                    txtTable.setEditable(true);
                    txtTable.requestFocus();
                } else {
                    //load data from tablefile
                    txtTable.setEditable(false);
                    txtCust.setText(rs.getString("TCustomer"));
                    txtCust.setEditable(false);
                    try {
                        String UpdateTable = "update tablefile set "
                                + "tonact='Y',"
                                + "macno='" + Value.MACNO + "',"
                                + "cashier='" + Value.USERCODE + "',"
                                + "EmpDisc='0',"
                                + "FastDisc='0',"
                                + "TrainDisc='0',"
                                + "PrintTime1='',"
                                + "TUser='',"
                                + "tlogindate=curdate(),"
                                + "tlogintime=curtime(),"
                                + "TCurTime=curtime()"
                                + "where tcode='" + txtTable.getText().trim() + "'";
                        try ( Statement stmt1 = mysql.getConnection().createStatement()) {
                            stmt1.executeUpdate(UpdateTable);
                        }
                        tbpMain.setSelectedIndex(0);

                        //load data to table
                        tblShowPluShow(txtTable.getText());
                        TableOpenStatus = true;
                    } catch (SQLException ex) {
                        MSG.ERR(this, ex.getMessage());
                        TableOpenStatus = false;
                        txtTable.setText("");
                        txtTable.setEditable(true);
                        txtTable.requestFocus();
                        txtTable.setText("");
                    }
                }
            } else {
                MSG.ERR(this, "หมายเลขนี้ไม่ได้มีการกำหนดไว้ในการทำงานโต๊ะหลัก !!!");
                TableOpenStatus = false;
                txtTable.setText("");
                txtTable.setEditable(true);
                txtTable.requestFocus();
                txtTable.setText("");
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(MainSale.class, "error", e.getMessage());

            TableOpenStatus = false;
            txtTable.setText("");
            txtTable.setEditable(true);
            txtTable.requestFocus();
            txtTable.setText("");
        } finally {
            mysql.close();
        }

    }

    private void txtPluCodeOnExit() {
        saveToBalance();
        tblShowPluShow(txtTable.getText());
        txtPluCode.setText("");
        txtPluCode.requestFocus();
    }

    //ปุ่มพักโต๊ะ
    private void bntHoldTableClick() {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
                    
        if (txtTable.getText().length() > 0 && tblShowBalance.getRowCount() > 0) {
            if (btnClickPrintKic == true) {
                String sqlTurnPrintKicOff = "update balance set r_kic='0' "
                        + "where r_kicprint<>'P' and r_table='" + tableNo + "';";
                try {
                    mysql.getConnection().createStatement().executeUpdate(sqlTurnPrintKicOff);
                } catch (SQLException e) {
                    MSG.ERR(e.getMessage());
                    AppLogUtil.log(MainSale.class, "error", e.getMessage());
                }
            }
            try {
                String sql = "update tablefile set tpause='Y' where tcode='" + tableNo + "';";
                mysql.getConnection().createStatement().executeUpdate(sql);
            } catch (SQLException e) {
                MSG.ERR(e.getMessage());
                AppLogUtil.log(MainSale.class, "error", e.getMessage());
            }
            
            kichenPrint();
            holdTableAndSave();
            PublicVar.ErrorColect = false;
            initScreen();
            return;
        }
        try {
            String sql = "update tablefile set tonact ='N',tcurtime='00:00:00',tcustomer='0' "
                    + "where tcode='" + txtTable.getText() + "';";
            mysql.getConnection().createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(MainSale.class, "error", e.getMessage());
        } finally {
            mysql.close();
        }
    }

    private void updateCustomerCount(int custCount) {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "update tablefile "
                    + "set tcustomer='" + custCount + "',"
                    + "macno='" + Value.MACNO + "' "
                    + "where tcode='" + txtTable.getText() + "'";
            try ( Statement stmt = mysql.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            MSG.ERR(this, e.getMessage());
            AppLogUtil.log(MainSale.class, "error", e.getMessage());
        } finally {
            mysql.close();
        }
        txtPluCode.requestFocus();
    }

    private void holdTableAndSave() {
        MySQLConnect mysql = new MySQLConnect();
        BalanceBean balanceBean = new BalanceBean();
        mysql.open();
        try {
            String getLogIntimeBalance = "select r_time,r_date from balance where r_table ='" + txtTable.getText() + "'";
            ResultSet rs = mysql.getConnection().createStatement().executeQuery(getLogIntimeBalance);
            if (rs.next()) {
                balanceBean.setLoginTime(rs.getString("r_time"));
                balanceBean.setR_LoginDate(rs.getString("r_date"));
            }
            String UpdateTableFile = "update tablefile "
                    + "set tonact='N', tlogintime ='" + balanceBean.getLoginTime() + "',"
                    + "macno='" + Value.MACNO + "',"
                    + "tlogindate='" + balanceBean.getR_LoginDate() + "' ,"
                    + "tpause='Y' "
                    + "where tcode='" + txtTable.getText() + "'";
            try ( Statement stmt = mysql.getConnection().createStatement()) {
                stmt.executeUpdate(UpdateTableFile);
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(MainSale.class, "error", e.getMessage());
        } finally {
            mysql.close();
        }

        showSum();
    }

    private void bntlogoffuserClick() {
        if (MSG.CONF(this, "ยืนยันการออกจากระบบการขาย (Logoff User) ? ")) {
            if (model.getRowCount() == 0) {
                PublicVar.P_LineCount = 1;
                PublicVar.P_LogoffOK = false;
                MySQLConnect mysql = new MySQLConnect();
                mysql.open();
                try {
                    Statement stmt = mysql.getConnection().createStatement();
                    String sql1 = "update posuser set onact='N',macno='' where (username='" + PublicVar._User + "')";
                    stmt.executeUpdate(sql1);

                    String sql2 = "update poshwsetup set onact='N' where(terminal='" + Value.MACNO + "')";
                    if (stmt.executeUpdate(sql2) > 0) {
                        // reset load poshwsetup
                        PosControl.resetPosHwSetup();
                    }
                    stmt.close();
                } catch (SQLException e) {
                    MSG.ERR(e.getMessage());
                    AppLogUtil.log(MainSale.class, "error", e.getMessage());
                } finally {
                    mysql.close();
                }

                if (this.chkEJPath()) {
                    Prn.printLogout();
                }
                if (updateLogout(PublicVar._RealUser)) {
                    loadLoginForm();
                }
            } else {
                MSG.WAR(this, "มีรายการขายค้างอยู่ไม่สามารถ Logoff ออกจากระบบได้...");
                if (TableOpenStatus) {
                    txtPluCode.requestFocus();
                } else {
                    txtTable.requestFocus();
                }
            }
        } else {
            if (TableOpenStatus) {
                txtPluCode.requestFocus();
            } else {
                txtTable.requestFocus();
            }
        }

    }

    private boolean updateLogout(String UserCode) {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            Statement stmt = mysql.getConnection().createStatement();
            stmt.executeUpdate("update posuser set onact='N',macno='' where username='" + UserCode + "'");

            Value.CASHIER = "";
            stmt.close();
            return true;
        } catch (SQLException e) {
            MSG.ERR(this, e.getMessage());
            AppLogUtil.log(MainSale.class, "error", e.getMessage());
            return false;
        } finally {
            mysql.close();
        }

    }

    private void bntPaidinClick() {
        if (!chkEJPath()) {
            return;
        }
        if (model.getRowCount() == 0) {
            PaidinFrm frm = new PaidinFrm(null, true);
            frm.setVisible(true);
        }
    }

    private void bntPaidoutClick() {
        chkEJPath();
    }

    private void showTableAvialble() {
        if (!PUtility.CheckCashierClose(PublicVar._User)) {
            if (txtTable.getText().trim().equals("")) {
                ShowTable frm = new ShowTable(null, true);
                frm.setVisible(true);
                if (!PublicVar.ReturnString.equals("")) {
                    txtTable.setText(PublicVar.ReturnString);
                    if (txtTable.getText().trim().length() > 0) {
                        txtTable.setEditable(false);
                        txtTableOnExit();
                        if (PublicVar.TableRec_TCustomer == 0) {
                            txtCust.setEditable(true);
                            txtCust.requestFocus();
                            txtCust.selectAll();
                        } else {
                            txtPluCode.setEditable(true);
                            txtPluCode.requestFocus();
                            txtCust.setSelectionEnd(0);
                            txtCust.setEditable(false);
                        }
                    }
                }
            } else {
                PUtility.ShowWaring("มีการกำหนดหมายเลขโต๊ะไว้แล้ว...");
            }
        } else {
            txtTable.setText("");
            txtTable.requestFocus();
        }
    }

    private void showBillDuplicate() {
        if (!chkEJPath()) {
            return;
        }
        if (model.getRowCount() == 0) {
            CopyBill frm = new CopyBill(null, true);
            frm.setVisible(true);
            initScreen();
        }
    }

    private void showRefundBill() {
        if (!chkEJPath()) {
            return;
        }
        PublicVar.TempUserRec = PublicVar.TUserRec;
        if (model.getRowCount() == 0) {
            if (PublicVar.TUserRec.Sale2.equals("Y")) {
                RefundBill frm = new RefundBill(null, true);
                frm.setVisible(true);
                PublicVar.P_ItemCount = 0;
                initScreen();
            } else {
                GetUserAction getuser = new GetUserAction(null, true);
                getuser.setVisible(true);

                if (!PublicVar.ReturnString.equals("")) {
                    String loginname = PublicVar.ReturnString;
                    UserRecord supUser = new UserRecord();
                    if (supUser.GetUserAction(loginname)) {
                        if (supUser.Sale2.equals("Y")) {
                            PublicVar.TUserRec = supUser;
                            RefundBill frm = new RefundBill(null, true);
                            frm.setVisible(true);
                            PublicVar.P_ItemCount = 0;
                            initScreen();
                        } else {
                            MSG.ERR(this, "รหัสพนักงานนี้ไม่สามารถเข้าใช้งาน...รายการนี้ได้...!!!");
                        }
                    } else {
                        MSG.ERR(this, "ไม่สามารถ Load สิทธิ์การใช้งานของผู้ใช้งานคนนี้ได้ ...");
                    }
                }
            }
        }
        PublicVar.TUserRec = PublicVar.TempUserRec;
    }

    private void bntArPaymentClick() {
        if (!chkEJPath()) {
            return;
        }
        if (!PUtility.CheckSaleDateOK()) {
            return;
        }

        PublicVar.TempUserRec = PublicVar.TUserRec;
        if (model.getRowCount() == 0) {
            if (PublicVar.TUserRec.Sale5.equals("Y")) {
                ARPayment frm = new ARPayment(null, true);
                frm.setVisible(true);
            } else {
                GetUserAction getuser = new GetUserAction(null, true);
                getuser.setVisible(true);

                if (!PublicVar.ReturnString.equals("")) {
                    String loginname = PublicVar.ReturnString;
                    UserRecord supUser = new UserRecord();
                    if (supUser.GetUserAction(loginname)) {
                        if (supUser.Sale5.equals("Y")) {
                            PublicVar.TUserRec = supUser;
                            ARPayment frm = new ARPayment(null, true);
                            frm.setVisible(true);
                        } else {
                            MSG.ERR(this, "รหัสพนักงานนี้ไม่สามารถเข้าใช้งาน...รายการนี้ได้...!!!");
                        }
                    } else {
                        MSG.ERR(this, "ไม่สามารถ Load สิทธิ์การใช้งานของผู้ใช้งานคนนี้ได้ ...");
                    }
                }
            }
        }
        PublicVar.TUserRec = PublicVar.TempUserRec;
    }

    public static void main(String args[]) {
        new MySQLConnect();
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

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainSale(null, false, "1").setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem MAddNewAccr1;
    private javax.swing.JMenuItem MAddNewMember1;
    private javax.swing.JMenuItem MCancelArPayment1;
    private javax.swing.JMenuItem MCancelArPayment2;
    private javax.swing.JMenuItem MHeaderBill1;
    private javax.swing.JMenu MMainMenu1;
    private javax.swing.JMenuItem MRepArHistory1;
    private javax.swing.JMenuItem MRepArNotPayment1;
    private javax.swing.JMenuItem MRepInvAr1;
    private javax.swing.JMenuItem MRepInvCash1;
    private javax.swing.JMenuItem MRepMemberHistory1;
    private javax.swing.JButton bntPrintCheckBill;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnHold;
    private javax.swing.JRadioButton btnLangEN;
    private javax.swing.JRadioButton btnLangTH;
    private javax.swing.JButton btnP1;
    private javax.swing.JButton btnP10;
    private javax.swing.JButton btnP11;
    private javax.swing.JButton btnP12;
    private javax.swing.JButton btnP13;
    private javax.swing.JButton btnP14;
    private javax.swing.JButton btnP15;
    private javax.swing.JButton btnP16;
    private javax.swing.JButton btnP17;
    private javax.swing.JButton btnP18;
    private javax.swing.JButton btnP19;
    private javax.swing.JButton btnP2;
    private javax.swing.JButton btnP20;
    private javax.swing.JButton btnP21;
    private javax.swing.JButton btnP22;
    private javax.swing.JButton btnP23;
    private javax.swing.JButton btnP24;
    private javax.swing.JButton btnP25;
    private javax.swing.JButton btnP26;
    private javax.swing.JButton btnP27;
    private javax.swing.JButton btnP28;
    private javax.swing.JButton btnP29;
    private javax.swing.JButton btnP3;
    private javax.swing.JButton btnP30;
    private javax.swing.JButton btnP31;
    private javax.swing.JButton btnP32;
    private javax.swing.JButton btnP4;
    private javax.swing.JButton btnP5;
    private javax.swing.JButton btnP6;
    private javax.swing.JButton btnP7;
    private javax.swing.JButton btnP8;
    private javax.swing.JButton btnP9;
    private javax.swing.JButton btnPayment;
    private javax.swing.JButton btnPrintKic;
    private javax.swing.JButton btnSplit;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar11;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JLabel lbCredit;
    private javax.swing.JLabel lbCreditAmt;
    private javax.swing.JLabel lbCreditMoney;
    private javax.swing.JLabel lbTotalAmount;
    private javax.swing.JPanel pMenu1;
    private javax.swing.JPanel pMenu2;
    private javax.swing.JPanel pMenu3;
    private javax.swing.JPanel pMenu4;
    private javax.swing.JPanel pMenu5;
    private javax.swing.JPanel pMenu6;
    private javax.swing.JPanel pMenu7;
    private javax.swing.JPanel pMenu8;
    private javax.swing.JPanel pMenu9;
    private javax.swing.JPanel pSubMenu1;
    private javax.swing.JPanel pSubMenu2;
    private javax.swing.JPanel pSubMenu3;
    private javax.swing.JTable tblShowBalance;
    private javax.swing.JTabbedPane tbpMain;
    private javax.swing.JTextField txtCust;
    private javax.swing.JTextField txtDiscount;
    private javax.swing.JTextField txtDisplayDiscount;
    private javax.swing.JTextField txtMember1;
    private javax.swing.JTextField txtMember2;
    private javax.swing.JTextField txtPluCode;
    private javax.swing.JTextField txtShowETD;
    private javax.swing.JTextField txtTable;
    private javax.swing.JTextField txtTypeDesc;
    // End of variables declaration//GEN-END:variables

    private void showSplitBill() {
        SplitBillPayment sp = new SplitBillPayment(null, true, txtTable.getText());
        sp.setVisible(true);

        if (!sp.getTable2().equals("")) {
            this.setVisible(true);
            txtTable.setText(sp.getTable2());
            tableOpened();
            txtCustOnExit();
            txtDiscount.setText(BalanceControl.GetDiscount(txtTable.getText()));
        }
        sumSplit();
    }

    private void showMember() {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        if (Value.MemberAlready == true) {
            int confirm = JOptionPane.showConfirmDialog(this, "มีการป้อนรหัสสมาชิกไว้แล้วต้องการเปลี่ยนใหม่หรือไม่...?");
            if (confirm == JOptionPane.YES_OPTION) {
                Value.MemberAlready = false;
                try {
                    String sql = "update tablefile set "
                            + "MemDisc='', "
                            + "MemDiscAmt='0.00', "
                            + "MemCode='', "
                            + "MemCurAmt='0.00', "
                            + "MemName='' "
                            + "where TCode='" + txtTable.getText() + "'";
                    try ( Statement stmt = mysql.getConnection().createStatement()) {
                        stmt.executeUpdate(sql);
                    }
                    showMember();
                } catch (SQLException e) {
                    MSG.ERR(e.getMessage());
                    AppLogUtil.log(MainSale.class, "error", e.getMessage());
                }
            }
        } else {
            MemberDialog frm = new MemberDialog(null, true, tableNo);
            frm.setVisible(true);

            if (frm.getMemCode() != null) {
                Value.MemberAlready = true;
                memberBean = MemberBean.getMember(frm.getMemCode());
                // update member in tablefile
                SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                try {
                    String sql = "update tablefile set "
                            + "MemDisc='" + memberBean.getMember_DiscountRate() + "', "
                            + "MemDiscAmt='0.00', "
                            + "MemCode='" + memberBean.getMember_Code() + "', "
                            + "MemCurAmt='" + memberBean.getMember_TotalScore() + "', "
                            + "MemName='" + ThaiUtil.Unicode2ASCII(memberBean.getMember_NameThai()) + "', "
                            + "MemBegin='" + simp.format(memberBean.getMember_Brithday()) + "', "
                            + "MemEnd='" + simp.format(memberBean.getMember_ExpiredDate()) + "' "
                            + "where TCode='" + txtTable.getText() + "'";
                    try ( Statement stmt = mysql.getConnection().createStatement()) {
                        stmt.executeUpdate(sql);
                    }
                } catch (SQLException e) {
                    MSG.ERR(this, e.getMessage());
                }

                // update old order
                MemberControl mc = new MemberControl();
                mc.updateMemberAllBalance(txtTable.getText(), memberBean);

                // update all discount and promotion
                BalanceControl.updateProSerTable(txtTable.getText(), memberBean);
            }
        }
        
        mysql.close();
        
        showSum();
    }

    private void showHoldTable() {
        tbpMain.setSelectedIndex(0);

        bntHoldTableClick();
        txtTable.setText("");
        txtCust.setText("");

        clearTable();
        showTableOpen();
        clearHistory();
        Value.TableSelected = "";
    }

    private void showPaidIn() {
        if (PUtility.CheckSaleDateOK()) {
            bntPaidinClick();
        }
    }

    private void showPaidOut() {
        if (PUtility.CheckSaleDateOK()) {
            bntPaidoutClick();
        }
    }

    private void showBillCheck() {
        if (model.getRowCount() > 0) {
            kichenPrint();
            bntcheckbillClick();
        }
    }

    private void showPayAR() {
        if (PUtility.CheckSaleDateOK()) {
            bntArPaymentClick();
        }
    }

    private void showCheckBill() {
        if (model.getRowCount() > 0) {
            kichenPrint();
            bntPaymentClick();
            showSum();
        }
    }

    private void selectedTableBalance() {
        if (tblShowBalance.getRowCount() > 0) {
            tblShowBalance.requestFocus();
            Rectangle rect = tblShowBalance.getCellRect(0, 0, true);
            tblShowBalance.scrollRectToVisible(rect);
            tblShowBalance.clearSelection();
            tblShowBalance.setRowSelectionInterval(0, 0);
        }
    }

    private void selectedOptionBill() {
        int row = tblShowBalance.getSelectedRow();
        String chkPCode = "" + tblShowBalance.getValueAt(row, 0);
        if (chkPCode.equals("")) {
            return;
        }

        String chkRIndex = "" + tblShowBalance.getValueAt(row, 10);
        //find data set
        if (!checkRIndex(chkRIndex) && chkRIndex != null) {
            return;
        }

        if (row != -1) {
            PopupItemJDialog popup = new PopupItemJDialog(null, true);
            popup.setVisible(true);

            String typeIndex = popup.getTypeIndex();

            if (!typeIndex.equals("none")) {
                String PCode = model.getValueAt(row, 0).toString();
                String PVoid = model.getValueAt(row, 5).toString();
                String RIndex = model.getValueAt(row, 10).toString();
                String RPause = model.getValueAt(row, 11).toString();
                String RKicPrint = model.getValueAt(row, 7).toString();

                if (typeIndex.equals("ItemOption")) {
                    bntoptionClick();
                    tblShowPluShow(txtTable.getText());
                } else if (typeIndex.equals("TypeSale")) {
                    if (model.getRowCount() > 0) {
                        if (!PCode.equals("") && !PVoid.equals("V")) {
                            changTypeClick();
                        } else {
                            PUtility.ShowMsg("รายการนี้ได้มีการพิมพ์ออกครัวไปแล้ว...\nไม่สามารถเปลี่ยนประเภทการขายได้...");
                        }
                    }
                } else if (typeIndex.equals("ItemDiscount")) {
                    // ตรวจสอบว่าสามารถให้ส่วนลดได้อีกหรือไม่ ?
                    if (checkCanDisc(RIndex)) {
                        if (model.getRowCount() > 0) {
                            if (!PCode.equals("") && !PVoid.equals("V")) {
                                ItemDiscount i = new ItemDiscount(null, true, txtTable.getText(), RIndex, memberBean);
                                i.setVisible(true);

                                tblShowPluShow(txtTable.getText());
                                txtPluCode.setText("");
                                txtPluCode.requestFocus();
                            }
                        }
                    } else {
                        MSG.WAR(this, "รายการสินค้านี้มีการให้ส่วนลดอื่นไปแล้วไม่สามารถให้ส่วนลดได้อีก");
                    }
                } else if (typeIndex.equals("ItemVoid")) {
                    if (RPause.equalsIgnoreCase("P") && !RKicPrint.equals("P")) {
                        cancelItemBeforeHold();
                    } else {
                        if (!PCode.equals("") && !PVoid.equals("V")) {
                            bntVoidClick();
                            double totalAmount = Double.parseDouble(lbTotalAmount.getText().replace(",", ""));
                            DiscountDialog dd = new DiscountDialog(null, true, tableNo, totalAmount, memberBean,
                                    txtMember1.getText(), txtMember2.getText());
                            dd.clearMemberDiscount();
                        }
                    }
                } else if (typeIndex.equals("ItemMove")) {
                    MoveItemDialog m = new MoveItemDialog(null, true, txtTable.getText());
                    m.setVisible(true);
                } else if (typeIndex.equals("EditQty")) {
                    ItemEditQty itemEditQty = new ItemEditQty(null, true, txtTable.getText(), RIndex, memberBean);
                    itemEditQty.setVisible(true);
                }
            }
            BalanceControl.updateProSerTable(txtTable.getText(), memberBean);
            //load data from balance
            tblShowPluShow(txtTable.getText());
        }
    }

    private void pCodeEnter() {
        String pluCode = txtPluCode.getText().trim();
        String chkOpt = "";
        if (!pluCode.equals("")) {
            chkOpt = pluCode.substring(pluCode.length() - 1, pluCode.length());
        }

        if (chkOpt.equals("+")) {
            //สามารถเลือกจำนวนได้เลย

            pluCode = pluCode.substring(0, pluCode.length() - 1);

            int qtySet;
            if (Value.autoqty) {
                GetQty frm = new GetQty(null, true, pluCode);
                frm.setVisible(true);

                qtySet = frm.ReturnQty;
            } else {
                qtySet = 1;
            }
            if (!pluCode.equals("")) {
                if (qtySet > 0) {
                    txtPluCode.setText(QtyIntFmt.format(qtySet).trim() + "*" + pluCode);
                    if (seekPluCode()) {
                        if (PublicVar.P_Status.equals("S")) {
                            txtPluCode.setEditable(false);
                        } else {
                            txtPluCodeOnExit();
                        }
                    }
                } else {
                    txtPluCode.setText("");
                    txtPluCode.requestFocus();
                }
            }
        } else {
            if (findPluCode()) {
                if (PublicVar.P_Status.equals("S")) {
                    txtPluCode.setEditable(false);
                } else {
                    txtPluCodeOnExit();
                }
            }
        }
    }

    private void clearHistory() {
        int size = historyBack.size();
        for (int i = 0; i < size; i++) {
            historyBack.remove(0);
        }
    }

    private String buttonName;
    private int buttonIndex;

    private void addMouseEvent(final JButton btn, final int index) {
        btn.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 3) {//click ขวา
                    buttonName = btn.getName();
                    buttonIndex = index;
                    if (!ProductControl.checkProductItem(buttonName)) {
                        jMenuItem2.setVisible(false);
                    } else {
                        jMenuItem2.setVisible(true);
                    }
                    jPopupMenu1.show(btn, e.getX(), e.getY());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    private boolean checkCanDisc(String RIndex) {
        boolean result = false;
        
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "select R_QuanCanDisc from balance "
                    + "where R_Index='" + RIndex + "' "
                    + "and R_QuanCanDisc>0";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            result = rs.next();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(MainSale.class, "error", e.getMessage());
            
            result = false;
        } finally {
            mysql.close();
        }
        
        return result;
    }

    private void sumSplit() {
        updatetable();
    }

    private void updatetable() {
        String table = txtTable.getText();
        String cus = txtCust.getText();
        
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "SELECT COUNT(R_PName) FROM balance where r_table = '" + table + "'";
            try ( ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                while (rs.next()) {
                    String R_PName = rs.getString("COUNT(R_PName)");
                    String UpdateTableFile = "update tablefile "
                            + "set tonact='N',"
                            + "macno='" + Value.MACNO + "',"
                            + "TCurTime = CurTime(),"
                            + "TCustomer = '" + cus + "',"
                            + "TItem = '" + R_PName + "',"
                            + "Service = '" + POSConfigSetup.Bean().getP_Service() + "' "
                            + "where tcode='" + txtTable.getText() + "'";
                    try ( Statement stmt1 = mysql.getConnection().createStatement()) {
                        stmt1.executeUpdate(UpdateTableFile);
                    }
                }
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(MainSale.class, "error", e.getMessage());
        } finally {
            mysql.close();
        }
    }

    private boolean isTakeOrder() {
        boolean isTakeOrder = false;
        POSHWSetup poshwSetup = PosControl.getData(Value.MACNO);
        if (poshwSetup.getTakeOrderChk().equals("Y")) {
            btnPayment.setVisible(false);
            btnSplit.setVisible(false);
            bntPrintCheckBill.setVisible(false);
            isTakeOrder = true;
        }

        return isTakeOrder;
    }

    private void updateTempTset(BalanceBean bBean) {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sqlUpd = "update tempset set "
                    + "PIndex='" + bBean.getR_Index() + "' "
                    + "where PTableNo='" + bBean.getR_Table() + "' ";
            mysql.getConnection().createStatement().executeUpdate(sqlUpd);

            String sql = "select * from tempset "
                    + "where PIndex='" + bBean.getR_Index() + "' ";
            ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql);
            while (rs.next()) {
                if (rs.getString("PCode").equals(bBean.getR_PluCode())) {
                    updateBalanceOptionFromTemp(bBean.getR_Index(), bBean.getR_Table(), bBean.getR_PluCode());
                } else {
                    String PCode = rs.getString("PCode");
                    if (!PCode.equals("")) {
                        String StkCode = PUtility.GetStkCode();
                        String emp = Value.EMP_CODE;
                        String etd = txtShowETD.getText();
                        String[] data = Option.splitPrice(PCode);
                        double R_Quan = Double.parseDouble(data[0]);
                        PCode = data[1];

                        ProductControl pCon = new ProductControl();
                        ProductBean productBean = pCon.getData(PCode);

                        BalanceBean balance = new BalanceBean();
                        balance.setStkCode(StkCode);
                        balance.setR_PrintOK(PublicVar.PrintOK);
                        balance.setMacno(Value.MACNO);
                        balance.setCashier(Value.USERCODE);
                        balance.setR_ETD(etd);
                        balance.setR_Quan(R_Quan);
                        balance.setR_Table(txtTable.getText());
                        balance.setR_Emp(emp);

                        balance.setR_PrCuType("");
                        balance.setR_PrCuQuan(0.00);
                        balance.setR_PrCuAmt(0.00);

                        balance.setR_PluCode(productBean.getPCode());
                        balance.setR_Group(productBean.getPGroup());
                        balance.setR_Status(productBean.getPStatus());
                        balance.setR_Normal(productBean.getPNormal());
                        balance.setR_Discount(productBean.getPDiscount());
                        balance.setR_Service(productBean.getPService());
                        balance.setR_Vat(productBean.getPVat());
                        balance.setR_Type(productBean.getPType());
                        balance.setR_Stock(productBean.getPStock());
                        balance.setR_PName(productBean.getPDesc());
                        balance.setR_Unit(productBean.getPUnit1());
                        balance.setR_Set(productBean.getPSet());

                        if (balance.getR_Status().equals("P")) {
                            switch (etd) {
                                case "E":
                                    balance.setR_Price(productBean.getPPrice11());
                                    break;
                                case "T":
                                    balance.setR_Price(productBean.getPPrice12());
                                    break;
                                case "D":
                                    balance.setR_Price(productBean.getPPrice13());
                                    break;
                                case "P":
                                    balance.setR_Price(productBean.getPPrice14());
                                    break;
                                case "W":
                                    balance.setR_Price(productBean.getPPrice15());
                                    break;
                                default:
                                    txtShowETD.setText("E");
                                    break;
                            }
                        }

                        balance.setR_Total(balance.getR_Quan() * balance.getR_Price());
                        balance.setR_PrChkType("");

                        BalanceControl balanceControl = new BalanceControl();
                        String R_Index = balanceControl.getIndexBalance(balance.getR_Table());
                        balance.setR_Index(R_Index);
                        memberBean = null;

                        // for not member
                        balance.setR_PrSubType("");
                        balance.setR_PrSubCode("");
                        balance.setR_PrSubQuan(0);// not member default 0
                        balance.setR_PrSubDisc(0);
                        balance.setR_PrSubBath(0);
                        balance.setR_PrSubAmt(0);
                        balance.setR_QuanCanDisc(balance.getR_Quan());
                        balance.setR_KicPrint("");
                        balance.setR_Pause("P");

                        balanceControl.saveBalance(balance);
                        updateBalanceOptionFromTemp(bBean.getR_Index(), balance.getR_Table(), PCode);

                        //Process stock out
                        String StkRemark = "SAL";
                        String DocNo = txtTable.getText() + "/" + Timefmt.format(new Date());
                        if (productBean.getPStock().equals("Y") && productBean.getPActive().equals("Y")) {
                            PUtility.ProcessStockOut(DocNo, StkCode, balance.getR_PluCode(), new Date(), StkRemark, balance.getR_Quan(), balance.getR_Total(),
                                    balance.getCashier(), balance.getR_Stock(), balance.getR_Set(), R_Index, "1");
                        }

                        //ตัดสต็อกสินค้าที่มี Ingredent
                        try {
                            String sql1 = "select i.*,pdesc,PBPack,pstock,pactive "
                                    + "from product p, pingredent i "
                                    + "where p.pcode=i.pingcode "
                                    + "and i.pcode='" + balance.getR_PluCode() + "' "
                                    + "and PFix='L' and PStock='Y'";
                            try ( Statement stmt2 = mysql.getConnection().createStatement();  ResultSet rs1 = stmt2.executeQuery(sql1)) {
                                while (rs1.next()) {
                                    String R_PluCode = rs1.getString("PingCode");
                                    double R_QuanIng = (rs1.getDouble("PingQty") * balance.getR_Quan());
                                    double R_Total = 0;
                                    if (rs1.getString("pstock").equals("Y") && rs1.getString("pactive").equals("Y")) {
                                        PUtility.ProcessStockOut(DocNo, StkCode, R_PluCode, new Date(), StkRemark, R_QuanIng, R_Total,
                                                balance.getCashier(), "Y", "", "", "");
                                    }
                                }
                            }
                        } catch (SQLException e) {
                            MSG.ERR(e.getMessage());
                            AppLogUtil.log(MainSale.class, "error", e.getMessage());
                        }
                    }
                }
            }

            //clear tempset
            try {
                String sqlClear = "delete from tempset where PTableNo='" + bBean.getR_Table() + "'";
                try ( Statement stmt2 = mysql.getConnection().createStatement()) {
                    stmt2.executeUpdate(sqlClear);
                }
            } catch (SQLException e) {
                MSG.ERR(e.getMessage());
                AppLogUtil.log(MainSale.class, "error", e.getMessage());
            }

            rs.close();
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSale.class, "error", e.getMessage());
        } finally {
            mysql.close();
        }
    }

    private void updateBalanceOptionFromTemp(String R_Index, String TableNo, String PCode) {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "select * from tempset "
                    + "where PIndex='" + R_Index + "' "
                    + "and PCode='" + PCode + "'";
            try ( Statement stmt = mysql.getConnection().createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    String opt = ThaiUtil.Unicode2ASCII(rs.getString("POption"));
                    String sql1 = "update balance "
                            + "set R_Opt1='" + opt + "',"
                            + "R_LinkIndex='" + R_Index + "' "
                            + "where R_Table='" + TableNo + "' "
                            + "and R_PluCode='" + PCode + "' "
                            + "and R_LinkIndex=''";
                    try ( Statement stmt1 = mysql.getConnection().createStatement()) {
                        stmt1.executeUpdate(sql1);
                    }
                }
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(MainSale.class, "error", e.getMessage());
        } finally {
            mysql.close();
        }
    }

    private boolean checkRIndex(String chkRIndex) {
        boolean isCheck = true;
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "select r_linkindex from balance "
                    + "where r_index='" + chkRIndex + "' "
                    + "and r_linkindex<>''";
            try ( Statement stmt = mysql.getConnection().createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    isCheck = false;
                    String r_linkindex = rs.getString("r_linkindex");
                    isCheck = r_linkindex.equals(chkRIndex);
                } else {
                    isCheck = true;
                }
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(MainSale.class, "error", e.getMessage());
        } finally {
            mysql.close();
        }
        
        return isCheck;
    }

    private void cancelItemBeforeHold() {
        BalanceControl bc = new BalanceControl();
        int[] rows = tblShowBalance.getSelectedRows();
        String StkRemark = "SAL";
        Date TDate = new Date();
        String DocNo = txtTable.getText() + "/" + Timefmt.format(new Date());

        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        for (int i = 0; i < rows.length; i++) {
            String r_index = "" + tblShowBalance.getValueAt(rows[i], 10);
            try {
                BalanceBean bean = bc.getBalanceIndex(txtTable.getText(), r_index);
                if (bean == null) {
                    continue;
                }

                boolean isMenuSet = false;
                String r_linkIndex = bean.getR_LinkIndex();
                if (r_linkIndex == null || r_linkIndex.equals("null")) {
                    r_linkIndex = "";
                }
                if (!r_linkIndex.equals("")) {
                    if (!bean.getR_Index().equals(bean.getR_LinkIndex())) {
                        isMenuSet = true;
                        r_index = r_linkIndex;
                    } else {
                        isMenuSet = true;
                    }
                }

                String sql = "delete from balance "
                        + "where r_index='" + r_index + "' "
                        + "and r_pause='P' and r_kicprint<>'P'";
                Statement stmt = mysql.getConnection().createStatement();
                String sqldelTempSet = "delete from tempset where PTableNo='" + txtTable.getText() + "';";
                mysql.getConnection().createStatement().executeUpdate(sqldelTempSet);

                int result = stmt.executeUpdate(sql);
                stmt.close();
                if (result <= 0) {
                    MSG.WAR("รายการอาหาร " + bean.getR_PName() + " ถูกส่งครัวไปแล้ว ไม่สามารถลบออกได้ จะต้อง Void เท่านั้น");
                } else {
                    // ################ โค้ดสำหรับคืน Stock
                    PUtility.ProcessStockOut(DocNo, bean.getStkCode(), bean.getR_PluCode(), TDate, StkRemark, -1 * bean.getR_Quan(),
                            -1 * bean.getR_Total(), bean.getCashier(), bean.getR_Stock(), bean.getR_Set(), bean.getR_Index(), "1");

                    //ตัดสต็อกสินค้าที่มี Ingredent
                    try {
                        String sql1 = "select i.*,pdesc,PBPack "
                                + "from product p, pingredent i "
                                + "where p.pcode=i.pingcode "
                                + "and i.pcode='" + bean.getR_PluCode() + "' "
                                + "and PFix='L' and PStock='Y'";
                        Statement stmt1 = mysql.getConnection().createStatement();
                        ResultSet rs = stmt1.executeQuery(sql1);
                        while (rs.next()) {
                            String R_PluCode = rs.getString("PingCode");
                            double R_QuanIng = (rs.getDouble("PingQty") * bean.getR_Quan());
                            double R_Total = 0;
                            PUtility.ProcessStockOut(DocNo, bean.getStkCode(), R_PluCode, TDate, StkRemark, -1 * R_QuanIng,
                                    R_Total, bean.getCashier(), "Y", "", "", "");
                        }

                        rs.close();
                        stmt1.close();
                    } catch (SQLException e) {
                        MSG.ERR(e.getMessage());
                        AppLogUtil.log(MainSale.class, "error", e.getMessage());
                    }

                    //ตรวจสอบถ้าเป็น menu set ให้ลบข้อมูลภายใน Set ด้วย
                    if (isMenuSet) {
                        try {
                            String sql3 = "select R_Index,R_Stock from balance "
                                    + "where R_LinkIndex='" + r_index + "'";
                            Statement stmt2 = mysql.getConnection().createStatement();
                            ResultSet rs = stmt2.executeQuery(sql3);
                            BalanceControl balanceControl = new BalanceControl();
                            while (rs.next()) {
                                BalanceBean bean2 = balanceControl.getBalanceIndex(txtTable.getText(), rs.getString("R_Index"));
                                String sqlDel = "delete from balance "
                                        + "where r_index='" + rs.getString("R_Index") + "' "
                                        + "and r_pause='P'";
                                Statement stmt3 = mysql.getConnection().createStatement();
                                stmt3.executeUpdate(sqlDel);
                                stmt3.close();
                                PUtility.ProcessStockOut(DocNo, bean2.getStkCode(), bean2.getR_PluCode(), TDate, StkRemark, -1 * bean2.getR_Quan(),
                                        -1 * bean2.getR_Total(), bean2.getCashier(), bean2.getR_Stock(), bean2.getR_Set(), bean2.getR_Index(), "1");

                                //ตัดสต็อกสินค้าที่มี Ingredent
                                try {
                                    String sql1 = "select i.*,pdesc,PBPack "
                                            + "from product p, pingredent i "
                                            + "where p.pcode=i.pingcode "
                                            + "and i.pcode='" + bean2.getR_PluCode() + "' "
                                            + "and PFix='L' and PStock='Y'";
                                    Statement stmt4 = mysql.getConnection().createStatement();
                                    ResultSet rs2 = stmt4.executeQuery(sql1);
                                    while (rs2.next()) {
                                        String R_PluCode = rs2.getString("PingCode");
                                        double PBPack = rs2.getDouble("PBPack");
                                        if (PBPack <= 0) {
                                            PBPack = 1;
                                        }
                                        double R_QuanIng = (rs2.getDouble("PingQty") * bean2.getR_Quan());
                                        double R_Total = 0;
                                        PUtility.ProcessStockOut(DocNo, bean2.getStkCode(), R_PluCode, TDate, StkRemark, -1 * R_QuanIng,
                                                R_Total, bean2.getCashier(), "Y", "", "", "");
                                    }

                                    rs2.close();
                                    stmt4.close();
                                } catch (SQLException e) {
                                    MSG.ERR(e.getMessage());
                                    AppLogUtil.log(MainSale.class, "error", e.getMessage());
                                }
                            }

                            rs.close();
                            stmt2.close();
                        } catch (SQLException e) {
                            MSG.ERR(e.getMessage());
                            AppLogUtil.log(MainSale.class, "error", e.getMessage());
                        }
                    }
                    // ################ END คืน Stock 
                }

            } catch (SQLException e) {
                MSG.ERR(e.getMessage());
                AppLogUtil.log(MainSale.class, "error", e.getMessage());
            }
        }

        mysql.close();
        
        //update tablefile
        BalanceControl.updateProSerTable(txtTable.getText(), memberBean);
        txtDiscount.setText("- " + BalanceControl.GetDiscount(txtTable.getText()));
        showSum();
        tblShowPluShow(txtTable.getText());
    }

    private void upDateTableFile() {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "UPDATE tablefile SET "
                    + "TOnAct= 'Y',"
                    + "macno='" + Value.MACNO + "' ,"
                    + "tpause='N' "
                    + "WHERE Tcode='" + txtTable.getText() + "'";
            try ( Statement stmt = mysql.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSale.class, "error", e.getMessage());
        } finally {
            mysql.close();
        }
    }

    private void CheckKicPrint() {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "select r_kicprint "
                    + "from balance where r_table='" + tableNo + "'"
                    + " and r_kicprint <> 'P' "
                    + " and R_PName <> ''";
            try ( Statement stmt = mysql.getConnection().createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    String CheckBillBeforeCash = CONFIG.getP_CheckBillBeforCash();
                    if (CheckBillBeforeCash.equals("Y")) {
                        printBillVoidCheck();
                    }
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSale.class, "error", e.getMessage());
        } finally {
            mysql.close();
        }
    }

    private String getButtonIndex(int i) {
        if ((i + 1) < 10) {
            return "0" + (i + 1);
        }
        return "" + (i + 1);
    }

    private String refreshMenuButtonGroup(String buttonName) {
        String panelGroup = buttonName.substring(0, buttonName.length() - 2);
        if (panelGroup.length() <= 0) {
            panelGroup = buttonName;
        }

        return panelGroup;
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

    private void loadButtonProductMenu(String menuCode) {
        ButtonCustom buttonCustom = new ButtonCustom();
        List<MenuMGR> listMenu = buttonCustom.getDataButtonLayout(menuCode);
        JButton[] btnGrid = new JButton[]{
            btnP1, btnP2, btnP3, btnP4, btnP5, btnP6, btnP7, btnP8,
            btnP9, btnP10, btnP11, btnP12, btnP13, btnP14, btnP15, btnP16
        };
        for (int i = 0; i < btnGrid.length; i++) {
            JButton btn = btnGrid[i];
            btn = buttonCustom.buttonDefault(btn);
            String btnIndex = getButtonIndex(i);
            btn.setName(menuCode + btnIndex);
            addMouseEvent(btn, i);
        }
        for (int i = 0; i < listMenu.size(); i++) {
            MenuMGR menu = listMenu.get(i);
            btnGrid[menu.getMIndex()] = buttonCustom.getButtonLayout(menu, btnGrid[menu.getMIndex()]);
            btnGrid[menu.getMIndex()].addActionListener((ActionEvent e) -> {
                JButton btnMenu = (JButton) e.getSource();
                if (menu.getPCode().equals("")) {
                    loadButtonProductMenu(menu.getMenuCode());
                } else if (!txtCust.getText().trim().equals("")) {
                    addProductFromButtonMenu(menu.getPCode(), btnMenu.getName());
                }
            });
        }

        // add back button
        btnGrid[15].setText("กลับ");
        btnGrid[15].setName(menuCode);
        btnGrid[15].setFocusable(false);
        btnGrid[15].setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
        btnGrid[15].setBackground(Color.RED);
        btnGrid[15].addActionListener((ActionEvent e) -> {
            JButton btn = (JButton) e.getSource();
            if (btn.getName() != null) {
                String btnName = btn.getName();
                if (btnName.length() >= 3) {
                    String backMenu = btnName.substring(0, btnName.length() - 2);
                    loadButtonProductMenu(backMenu);
                }
            }
        });
    }

    private void addProductFromButtonMenu(String PCode, String btnName) {
        if (!showPopupOption(btnName)) {
            return;
        }
        txtPluCode.setText(txtPluCode.getText().trim() + "*" + PCode);
        if (findPluCode()) {
            if (PublicVar.P_Status.equals("S")) {
                txtPluCode.setEditable(false);
                return;
            }

            //สามารถเลือกจำนวนได้เลย
            double qtySet;
            if (Value.autoqty) {
                GetQty frm = new GetQty(null, true, txtPluCode.getText());
                frm.setVisible(true);
                qtySet = frm.ReturnQty;
            } else {
                qtySet = PublicVar.P_Qty;
            }

            if (!txtPluCode.getText().trim().equals("")) {
                if (qtySet > 0) {
                    txtPluCode.setText("" + qtySet + "*" + PCode);
                    if (seekPluCode()) {
                        if (PublicVar.P_Status.equals("S")) {
                            txtPluCode.setEditable(false);
                        } else {
                            txtPluCodeOnExit();
                        }
                    }
                } else {
                    txtPluCode.setText("");
                    txtPluCode.requestFocus();
                }
            }
        }
    }

    private void addHistory(int index) {
        int size = historyBack.size();
        boolean isExists = false;
        for (int i = 0; i < size; i++) {
            int a = historyBack.get(i);
            if (a == index) {
                isExists = true;
                break;
            }
        }
        if (!isExists) {
            historyBack.add(index);
        }
    }

    private void loadHeaderMenu() {
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM company limit 1");
            if (rs.next()) {
                for (int i = 0; i < 9; i++) {
                    String h = ThaiUtil.ASCII2Unicode(rs.getString("head" + (i + 1)));
                    tbpMain.setTitleAt(i, h);
                }
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(this, e.getMessage());
            AppLogUtil.log(MainSale.class, "error", e.getMessage());
        } finally {
            mysql.close();
        }

    }

    private void printBillCheck() {
        if (Value.useprint) {
            PPrint print = new PPrint();
            print.PrintCheckBill(txtTable.getText());
        } else {
            JOptionPane.showMessageDialog(this, "ระบบไม่ได้กำหนดให้ใช้งานเครื่องพิมพ์ !!!" + Value.useprint);
        }
    }

    private void printBillVoidCheck() {
        if (Value.useprint) {
            MySQLConnect mysql = new MySQLConnect();
            mysql.open();
            try {
                String sql = "select * from balance where r_table='" + tableNo + "' and r_void='V'";
                try ( Statement stmt = mysql.getConnection().createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
                    if (rs.next()) {
                        PPrint print = new PPrint();
                        print.PrintVoidBill(tableNo);
                    }
                }
            } catch (SQLException e) {
                MSG.ERR(e.getMessage());
                AppLogUtil.log(MainSale.class, "error", e.getMessage());
            } finally {
                mysql.close();
            }
        }
    }

    private boolean showPopupOption(String MenuCode) {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            boolean showModalPopup = false;
            String pcode = "", pname = "", main = "";
            String sql = "select n.PCode,n.MenuShowText from optionset o,soft_menusetup n "
                    + "where o.pcode = n.pcode and n.menucode='" + MenuCode + "'";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                pcode = rs.getString("PCode");
                pname = ThaiUtil.Unicode2ASCII(rs.getString("MenuShowText"));
                main = "main";
                
                showModalPopup = true;
            }
            rs.close();
            stmt.close();
            
            if (showModalPopup) {
                ModalPopup popup = new ModalPopup(null, true, pcode, pname, tableNo, main, MenuCode);
                popup.setVisible(true);
            } else {
                try {
                    String sqll = "select o.PCode,o.PDesc, o.check_before from mgrbuttonsetup o,soft_menusetup n "
                            + "where o.pcode = n.pcode and n.menucode='" + MenuCode + "'";
                    Statement stmt1 = mysql.getConnection().createStatement();
                    ResultSet rss = stmt1.executeQuery(sqll);
                    if (rss.next()) {
                        String PCode = rss.getString("PCode");

                        //check before order foods
                        boolean checkBefore = rss.getString("Check_before").equals("Y");
                        boolean passBefore = false;
                        if (checkBefore) {
                            try {
                                String sqlChkTable = "select * from balance "
                                        + "where r_table='" + txtTable.getText() + "'";
                                Statement stmt2 = mysql.getConnection().createStatement();
                                ResultSet rsTb = stmt2.executeQuery(sqlChkTable);
                                if(rsTb.next()){
                                    passBefore = true;
                                }
                                rsTb.close();
                                stmt2.close();
                            } catch (SQLException e) {
                                MSG.ERR(e.getMessage());
                                AppLogUtil.log(MainSale.class, "error", e.getMessage());
                                passBefore = false;
                            }

                            if (!passBefore) {
                                MSG.WAR("ไม่มีรายการอาหาร กรุณาเลือกเมนูอาหารหลักก่อน");
                                return false;
                            }
                        }
                        //end before order foods

                        //check auto add before
                        boolean isAutoAdd = false;
                        String pstock = PUtility.GetStkCode();
                        try {
                            String sqlA1 = "select * from mgrbuttonsetup "
                                    + "where pcode='" + PCode + "' and auto_pcode<>''";
                            Statement stmt3 = mysql.getConnection().createStatement();
                            ResultSet rsA1 = stmt3.executeQuery(sqlA1);
                            while (rsA1.next()) {
                                isAutoAdd = true;
                                String autoPCode = rsA1.getString("auto_pcode");
                                String autoPDesc = rsA1.getString("auto_pdesc");
                                String tempset = "INSERT INTO tempset "
                                        + "(PTableNo, PIndex, PCode, PDesc, "
                                        + "PPostStock,PProTry, POption, PTime) "
                                        + "VALUES ('" + txtTable.getText() + "', '', '" + autoPCode + "', "
                                        + "'" + ThaiUtil.Unicode2ASCII(autoPDesc) + "', '" + pstock + "',"
                                        + "'auto', '', "
                                        + "CURTIME())";
                                try ( Statement stmt4 = mysql.getConnection().createStatement()) {
                                    stmt4.execute(tempset);
                                }
                            }

                            rsA1.close();
                            stmt3.close();
                        } catch (SQLException e) {
                            MSG.ERR(e.getMessage());
                            AppLogUtil.log(MainSale.class, "error", e.getMessage());
                        }

                        //end loop autoadd
                        pname = ThaiUtil.Unicode2ASCII(rss.getString("PDesc"));
                        main = "main";
                        updateTempmenuset("", PCode, pname, "", main);

                        if (!isAutoAdd) {
                            ModalPopup popup = new ModalPopup(null, true, PCode, pname, tableNo, main, MenuCode);
                            popup.setVisible(true);
                        }
                    }
                    rss.close();
                    stmt1.close();
                } catch (SQLException e) {
                    MSG.ERR(e.getMessage());
                    AppLogUtil.log(MainSale.class, "error", e.getMessage());
                }
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(MainSale.class, "error", e.getMessage());
        } finally {
            mysql.close();
        }

        return true;
    }

    private void updateTempmenuset(String Index, String PCode, String PName, String Option, String TryName) {
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            String pstock = PUtility.GetStkCode();
            String sql = "INSERT INTO tempset "
                    + "(PTableNo, PIndex, PCode, PDesc, "
                    + "PPostStock,PProTry, POption, PTime) "
                    + "VALUES ('" + tableNo + "', '" + Index + "', '" + PCode + "', "
                    + "'" + ThaiUtil.Unicode2ASCII(PName) + "', '" + pstock + "','" + TryName + "', "
                    + "'" + ThaiUtil.Unicode2ASCII(Option) + "', CURTIME())";
            try ( Statement stmt = mysql.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSale.class, "error", e.getMessage());
        } finally {
            mysql.close();
        }
    }

    private void showCustomerInput() {
        String r_etd = txtShowETD.getText();
        String customerCount = txtCust.getText();
        int cc = 0;
        try {
            cc = Integer.parseInt(customerCount);
        } catch (NumberFormatException e) {
        }

        if (PublicVar.defaultCustomer.equals("true")) {
            int cuscount = Integer.parseInt(PublicVar.defaultCustomerQty);
            cc = cuscount;
            txtCust.setText("" + cc);
            updateCustomerCount(cc);
        }
        if (cc == 0 && r_etd.equalsIgnoreCase("E")) {
            CustomerCountDialog ccd = new CustomerCountDialog(null, true, txtTable.getText(), txtShowETD.getText());
            ccd.setVisible(true);

            txtCust.setText("" + ccd.getCountCustomer());
            int custCount = ccd.getCountCustomer();
            if (r_etd.equalsIgnoreCase("T")) {
                txtCustOnExit();
                return;
            }

            if (custCount > 0) {
                txtCustOnExit();
            }
        }
    }

}
