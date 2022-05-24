package com.softpos.floorplan;

import com.softpos.login.FileSettingDialog;
import com.softpos.main.program.CheckProductNotEnough;
import com.softpos.main.program.CheckStockNow;
import com.softpos.main.program.CopyBill;
import com.softpos.main.program.DisplayEJ;
import com.softpos.main.program.EmployLogin;
import com.softpos.main.program.GetQty;
import com.softpos.main.program.GetUserAction;
import com.softpos.main.program.MainSale;
import com.softpos.main.program.PrintKicControl;
import com.softpos.main.program.SetupButtonTable;
import com.softpos.main.program.UpdateData;
import com.softpos.pos.core.controller.BalanceControl;
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
import com.softpos.pos.core.model.CompanyBean;
import com.softpos.pos.core.model.MemberBean;
import com.softpos.pos.core.model.ProductBean;
import database.MySQLConnect;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import util.AppLogUtil;
import util.DateConvert;
import util.MSG;
import util.Option;

public class FloorPlanDialog extends javax.swing.JFrame {

    private POSHWSetup POSHW;
    private POSConfigSetup CONFIG;
    private final SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
    private final SimpleDateFormat dy = new SimpleDateFormat("dd/MM/yyyy ", Locale.ENGLISH);
    private ImageIcon image;
    private int refresh = 1;
    private Font fontA = new Font("Tahoma", Font.PLAIN, 14);
    private Font fontB = new Font("Tahoma", Font.BOLD, 11);
    private Font fontC = new Font("Tahoma", Font.BOLD, 16);
    private SimpleDateFormat Timefmt = new SimpleDateFormat("HH:mm:ss");
    private MemberBean memberBean;

    private int floorplanTabSelected = 0;
    private int buttonStyle = 0;

    private final ProductControl productControl = new ProductControl();

    public FloorPlanDialog() {
        setUndecorated(true);
        initComponents();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Already there
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        POSHW = POSHWSetup.Bean(Value.getMacno());
        CONFIG = POSConfigSetup.Bean();

        refresh = PosControl.getRefreshTime();

        Value.TableSelected = "";

        new Thread(() -> {
            for (int a = 0; a < 10; a++) {
                if (a == 9) {
                    a = 0;
                }
                showTime();
            }
        }).start();

        //load header for tab
        loadHeaderTab();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                addButton();
                if (PublicVar.PrintCheckBillFromPDA.equals("true")) {
                    PrintCheckBillFromPDA();
                }
                if (i == 9) {
                    i = 0;
                }
                try {
                    Thread.sleep(refresh * 1000);
                } catch (InterruptedException ex) {
                }
            }
        }).start();

        jMenu1.setVisible(true);
        jMenu2.setVisible(false);
        jMenu3.setVisible(true);
        jMenu4.setVisible(false);
        jMenu7.setVisible(false);
        MShowDailyEJ1.setVisible(false);
        jMenuItem38.setVisible(false);

        // init product list data
        productControl.initLoadProductActive();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnZone1 = new javax.swing.JPanel();
        pnZone2 = new javax.swing.JPanel();
        pnZone3 = new javax.swing.JPanel();
        pnZone4 = new javax.swing.JPanel();
        pnZone5 = new javax.swing.JPanel();
        pnZone6 = new javax.swing.JPanel();
        pnZone7 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem33 = new javax.swing.JMenuItem();
        jMenuItem38 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem30 = new javax.swing.JMenuItem();
        jMenuItem31 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem19 = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        MShowDailyEJ1 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem35 = new javax.swing.JMenuItem();
        jMenuItem36 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem24 = new javax.swing.JMenuItem();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenuItem26 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem27 = new javax.swing.JMenuItem();
        jMenuItem28 = new javax.swing.JMenuItem();
        jMenuItem29 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem34 = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem32 = new javax.swing.JMenuItem();
        jMenu9 = new javax.swing.JMenu();

        jPopupMenu1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jMenuItem1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem1.setText("กำหนดโต๊ะ (Setup Table)");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Restaurant By SoftPOS V7.0");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jTabbedPane1.setForeground(new java.awt.Color(0, 0, 204));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });
        jTabbedPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabbedPane1KeyPressed(evt);
            }
        });

        pnZone1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0), 2));
        pnZone1.setOpaque(false);
        pnZone1.setLayout(new java.awt.GridLayout(10, 10, 2, 2));
        jTabbedPane1.addTab("Zone1", pnZone1);

        pnZone2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0), 2));
        pnZone2.setOpaque(false);
        pnZone2.setLayout(new java.awt.GridLayout(10, 10, 2, 2));
        jTabbedPane1.addTab("Zone2", pnZone2);

        pnZone3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0), 2));
        pnZone3.setOpaque(false);
        pnZone3.setLayout(new java.awt.GridLayout(10, 10, 2, 2));
        jTabbedPane1.addTab("Zone3", pnZone3);

        pnZone4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0), 2));
        pnZone4.setOpaque(false);
        pnZone4.setLayout(new java.awt.GridLayout(10, 10, 2, 2));
        jTabbedPane1.addTab("Zone4", pnZone4);

        pnZone5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0), 2));
        pnZone5.setOpaque(false);
        pnZone5.setLayout(new java.awt.GridLayout(10, 10, 2, 2));
        jTabbedPane1.addTab("Zone5", pnZone5);

        pnZone6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0), 2));
        pnZone6.setOpaque(false);
        pnZone6.setLayout(new java.awt.GridLayout(10, 10, 2, 2));
        jTabbedPane1.addTab("Zone6", pnZone6);

        pnZone7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0), 2));
        pnZone7.setOpaque(false);
        pnZone7.setLayout(new java.awt.GridLayout(10, 10, 2, 2));
        jTabbedPane1.addTab("Zone7", pnZone7);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 0, 51));
        jButton1.setMnemonic('\u0e22');
        jButton1.setText("-");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jMenuBar1.setBackground(new java.awt.Color(255, 102, 102));
        jMenuBar1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenuBar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuBar1MouseClicked(evt);
            }
        });

        jMenu1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jMenu1.setText("โปรแกรม (Program)");
        jMenu1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jMenuItem4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem4.setText("กำหนดชื่อแต่ละ Tab");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);
        jMenu1.add(jSeparator2);

        jMenuItem7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem7.setText("นำเงินเข้าระบบ (Float In)");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        jMenuItem8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem8.setText("นำเงินออกจากระบบ (Float Out)");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem8);

        jMenuItem11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem11.setText("ตรวจสอบโต๊ะ (Check Table List)");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem11);

        jMenuItem12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem12.setText("ย้ายโต๊ะ (Move Table)");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem12);

        jMenuItem10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem10.setText("ยกเลิกบิล (Refund Bill)");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem10);

        jMenuItem33.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem33.setText("พิมพ์สำเนาบิล (Copy Bill)");
        jMenuItem33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem33ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem33);

        jMenuItem38.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem38.setText("ดึงบิลยกเลิกล่าสุดมาทำรายการใหม่");
        jMenuItem38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem38ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem38);

        jMenuBar1.add(jMenu1);

        jMenu4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jMenu4.setText("การขาย (Sale System)");
        jMenu4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jMenuItem6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem6.setText("ดึงรายการสินค้าที่ยกเลิกบิลก่อนหน้า");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem6);

        jMenuItem30.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem30.setText("รายการสินค้าที่หมดในวันนี้");
        jMenuItem30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem30ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem30);

        jMenuItem31.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem31.setText("ตรวจสอบสถานะคลังสินค้า");
        jMenuItem31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem31ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem31);

        jMenuBar1.add(jMenu4);

        jMenu2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jMenu2.setText("รายงาน (Report)");
        jMenu2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jMenuItem19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem19.setText("Diary Sale (ตรวจสอบยอดขาย)");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem19);
        jMenu2.add(jSeparator7);

        jMenuItem2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem2.setText("Dialy Report (รายงานประจำวัน)");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem5.setText("MTD Report (รายงานประจำเดือน)");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);
        jMenu2.add(jSeparator3);

        MShowDailyEJ1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        MShowDailyEJ1.setText("ตรวจสอบข้อมูลม้วนสำเนา(ของวันนี้)");
        MShowDailyEJ1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MShowDailyEJ1ActionPerformed(evt);
            }
        });
        jMenu2.add(MShowDailyEJ1);

        jMenuBar1.add(jMenu2);

        jMenu7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jMenu7.setText("กำหนดข้อมูลระบบ (System Config)");
        jMenu7.setToolTipText("");
        jMenu7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jMenuItem13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem13.setText("แก้ไขข้อมูลเริ่มต้นระบบ");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem13);

        jMenuItem35.setText("กำหนดรายการออกครัว");
        jMenuItem35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem35ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem35);

        jMenuItem36.setText("UpdateNew Menu");
        jMenuItem36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem36ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem36);

        jMenuBar1.add(jMenu7);

        jMenu3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jMenu3.setText("เกี่ยวกับโปรแกรม (About)");
        jMenu3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jMenuItem14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem14.setText("คู่มือการใช้งาน (user_manual.pdf)");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem14);

        jMenuItem9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem9.setText("คีย์ลัดต่าง ๆ  ในระบบ");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem9);

        jMenuItem15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem15.setText("ส่งข้อความคิดเห็น / Bug / ติดต่อ Support");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem15);

        jMenuItem17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem17.setText("เว็บไซต์ http://www.softpos.co.th");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem17);
        jMenu3.add(jSeparator6);

        jMenuItem21.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem21.setText("ติดตั้งปลั๊กอิน (Plug-In)");
        jMenu3.add(jMenuItem21);

        jMenuItem16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem16.setText("เปลี่ยนรูปภาพ Welcome");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem16);

        jMenuItem18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem18.setText("การอัพเดตโปรแกรมอัตโนมัติ");
        jMenu3.add(jMenuItem18);

        jMenuItem20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem20.setText("Serial Number สำหรับใช้งานเต็มรูปแบบ");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem20);

        jMenu5.setText("เลือกภาษาโปรแกรม");
        jMenu5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jMenuItem22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/th.png"))); // NOI18N
        jMenuItem22.setText("ภาษาไทย");
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem22);

        jMenuItem23.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/us.png"))); // NOI18N
        jMenuItem23.setText("English / America");
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem23);
        jMenu5.add(jSeparator5);

        jMenuItem24.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem24.setText("ติดตั้งภาษาอื่น ๆ");
        jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem24ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem24);

        jMenu3.add(jMenu5);

        jMenuItem25.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem25.setText("Sync ข้อมูลวันและเวลาจาก Time Server");
        jMenu3.add(jMenuItem25);

        jMenuItem26.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem26.setText("Sync Database อัตโนมัติ");
        jMenu3.add(jMenuItem26);

        jMenu6.setText("เลือกการพิมพ์ออก Printer");
        jMenu6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jMenuItem27.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem27.setText("Printer Port");
        jMenuItem27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem27ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem27);

        jMenuItem28.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem28.setText("Printer Driver");
        jMenuItem28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem28ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem28);

        jMenu3.add(jMenu6);

        jMenuItem29.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem29.setText("เปลี่ยน Theme Program");
        jMenu3.add(jMenuItem29);
        jMenu3.add(jSeparator4);

        jMenuItem34.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem34.setText("เกี่ยวกับโปรแกรม (About)");
        jMenuItem34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem34ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem34);

        jMenuBar1.add(jMenu3);

        jMenu8.setBackground(new java.awt.Color(51, 255, 204));
        jMenu8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jMenu8.setText("ออกจากระบบ (Logout)");
        jMenu8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jMenuItem32.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem32.setText("ออกจากระบบ (Exit)");
        jMenuItem32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem32ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem32);

        jMenuBar1.add(jMenu8);

        jMenu9.setBackground(new java.awt.Color(255, 153, 51));
        jMenu9.setForeground(new java.awt.Color(0, 0, 255));
        jMenu9.setText("Show Time");
        jMenu9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuBar1.add(jMenu9);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 951, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 746, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        SetupFloorPlanHeader setup = new SetupFloorPlanHeader(null, true);
        setup.setVisible(true);

        loadHeaderTab();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        if (ChkEJPath()) {
            DailyRep frm = new DailyRep(null, true);
            frm.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        if (ChkEJPath()) {
            MTDRep frm = new MTDRep(null, true);
            frm.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        try {
            openWebpage(new URL("http://www.softpos.co.th"));
        } catch (MalformedURLException | URISyntaxException ex) {
            MSG.ERR(this, ex.getMessage());
        }
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        ReportBug report = new ReportBug(null, true);
        report.setVisible(true);
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        ShortKeyDialog sk = new ShortKeyDialog(null, true);
        sk.setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        openDoc();
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        PaidinFrm frm = new PaidinFrm(null, true);
        frm.setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        ResonPaidoutFrm frm = new ResonPaidoutFrm(null, true);
        frm.setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        refund();
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        ShowTable s = new ShowTable(null, true);
        setVisible(false);
        s.setVisible(true);
        if (!Value.TableSelected.equals("")) {
            setVisible(false);
            MainSale mainSale = new MainSale(null, true, Value.TableSelected);
            mainSale.setVisible(true);

            dispose();
        }
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        MoveGroupTable move = new MoveGroupTable(null, true);
        move.setVisible(true);
        addButton();
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        HomeImageDialog home = new HomeImageDialog(null, true);
        home.setVisible(true);
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        if (!Value.LANG.equals("TH")) {
            Value.LANG = "TH";
        }
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
        if (!Value.LANG.equals("EN")) {
            Value.LANG = "EN";
        }
    }//GEN-LAST:event_jMenuItem23ActionPerformed

    private void jMenuItem24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem24ActionPerformed
        MSG.ERR(this, "Comming soon...");
    }//GEN-LAST:event_jMenuItem24ActionPerformed

    private void jMenuItem27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem27ActionPerformed
        Value.printdriver = false;
    }//GEN-LAST:event_jMenuItem27ActionPerformed

    private void jMenuItem28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem28ActionPerformed
        Value.printdriver = true;
    }//GEN-LAST:event_jMenuItem28ActionPerformed

    private void MShowDailyEJ1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MShowDailyEJ1ActionPerformed
        DisplayEJ frm2 = new DisplayEJ(null, true);
        frm2.setVisible(true);
    }//GEN-LAST:event_MShowDailyEJ1ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        returnBill();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem30ActionPerformed
        CheckProductNotEnough cp = new CheckProductNotEnough(null, true);
        cp.setVisible(true);
    }//GEN-LAST:event_jMenuItem30ActionPerformed

    private void jMenuItem31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem31ActionPerformed
        CheckStockNow check = new CheckStockNow(null, true);
        check.setVisible(true);
    }//GEN-LAST:event_jMenuItem31ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        FileSettingDialog fd = new FileSettingDialog(null, true);
        fd.setVisible(true);
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        jMenuItem6.setVisible(false);
        MShowDailyEJ1.setVisible(false);
        jMenu3.setVisible(false);

    }//GEN-LAST:event_formWindowOpened

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        SetupButtonTable setup = new SetupButtonTable(null, true, Value.BTN_FLOORPLAN);
        setup.setVisible(true);

        addButton();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem32ActionPerformed
        logout();
    }//GEN-LAST:event_jMenuItem32ActionPerformed

    private void jTabbedPane1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabbedPane1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_F5) {
            addButton();
        } else if (evt.getKeyCode() == KeyEvent.VK_F10) {
            jMenuBar1.setVisible(true);
        }
    }//GEN-LAST:event_jTabbedPane1KeyPressed

    private void jMenuBar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuBar1MouseClicked
        String TS = POSHW.getTakeOrderChk();
        if (TS.equals("N")) {
            if (jMenu3.isVisible() == false) {
                jMenu1.setVisible(true);
                jMenu2.setVisible(true);
                jMenu3.setVisible(true);
                jMenu4.setVisible(true);
            } else {
                jMenu1.setVisible(true);
                jMenu2.setVisible(false);
                jMenu3.setVisible(false);
                jMenu4.setVisible(false);
                jMenu7.setVisible(false);
            }
        } else {
            jMenu3.setVisible(true);
        }
    }//GEN-LAST:event_jMenuBar1MouseClicked

    private void jMenuItem33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem33ActionPerformed
        CopyBill c = new CopyBill(this, true);
        c.setVisible(true);
    }//GEN-LAST:event_jMenuItem33ActionPerformed

    private void jMenuItem34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem34ActionPerformed
        MSG.NOTICE(this, "SoftPOS Update:V8.2 22/04/2022 12:42");
    }//GEN-LAST:event_jMenuItem34ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        DiarySale d = new DiarySale(null, true);
        d.setVisible(true);
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItem35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem35ActionPerformed
        PrintKicControl Kic = new PrintKicControl(null, true);
        Kic.setVisible(true);
    }//GEN-LAST:event_jMenuItem35ActionPerformed

    private void jMenuItem36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem36ActionPerformed
        UpdateData UP = new UpdateData(null, true);
        UP.setVisible(true);
    }//GEN-LAST:event_jMenuItem36ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void jMenuItem38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem38ActionPerformed
        int dialogResult = JOptionPane.showConfirmDialog(null, "ต้องการดึงรายการยกเลิกบิลล่าสุดใช่หรือไม่");
        if (dialogResult == JOptionPane.YES_OPTION) {
            if (dialogResult == 0) {
                MySQLConnect mysql = new MySQLConnect();
                try {
                    String sql = "SELECT * FROM billno where b_void='V' ORDER BY b_refno DESC LIMIT 1";
                    mysql.open();
                    ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql);
                    if (rs.next()) {
                        String tableNo = ThaiUtil.Unicode2ASCII(rs.getString("b_table"));
                        String b_refno = rs.getString("b_refno");
                        String b_cust = rs.getString("b_cust");
                        String checkTablefile = "select titem from tablefile "
                                + "where tcode = '" + tableNo + "' and titem>'0' limit 1 ";
                        ResultSet rs1 = mysql.getConnection().createStatement().executeQuery(checkTablefile);
                        if (rs1.next()) {
                            if (rs1.getInt("titem") > 0) {
                                MSG.WAR(this, "ไม่สามารถทำรายการได้ เนื่องจากโต๊ะนี้ยังมีรายการขายอยู่");
                            } else {
                                String sqlGetFromT_sale = "select * from t_sale "
                                        + "where r_refno='" + b_refno + "' order by r_index limit 1;";
                                ResultSet rs2 = mysql.getConnection().createStatement().executeQuery(sqlGetFromT_sale);
                                if (rs2.next()) {
                                    String updateBCust = "update tablefile set tcustomer='" + b_cust + "' where tcode='" + tableNo + "'";
                                    mysql.getConnection().createStatement().executeUpdate(updateBCust);
                                    while (rs2.next()) {
                                        String pcode = rs2.getString("r_plucode");
                                        String r_etd = rs2.getString("r_etd");
                                        double r_quan = rs2.getDouble("r_quan");
                                        saveToBalance(tableNo, pcode, r_etd, r_quan);
                                    }
                                    MSG.NOTICE("ดึงรายการยกเลิกบิลล่าสุดเรียบร้อย โต๊ะ : " + tableNo);
                                    addButton();
                                }
                                rs2.close();
                            }
                        }
                        rs1.close();
                    }
                    rs.close();
                } catch (SQLException e) {
                    MSG.ERR(e.getMessage());
                    AppLogUtil.log(FloorPlanDialog.class, "error", e);
                } finally {
                    mysql.close();
                }
            }
            // Saving code here
        }
    }//GEN-LAST:event_jMenuItem38ActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        this.floorplanTabSelected = jTabbedPane1.getSelectedIndex();
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        addButton();
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void saveToBalance(String tableNo, String pcode, String r_etd, double r_quan) {
        String PCode = pcode;

        String StkCode = PUtility.GetStkCode();
        String emp = Value.EMP_CODE;
        String etd = r_etd;
        String[] data = Option.splitPrice(PCode);
        double R_Quan = r_quan;
        PCode = data[1];

        ProductControl pCon = new ProductControl();
        ProductBean productBean = pCon.getData(PCode);
        BalanceBean balance = new BalanceBean();
        balance.setStkCode(StkCode);

        double Price = 0.00;

        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            Statement stmt = mysql.getConnection().createStatement();
            String sql = "select pcode from product where pcode='" + PCode + "' and pactive='Y' limit 1";
            ResultSet rs = stmt.executeQuery(sql);
            boolean checkFoundProduct = rs.next();
            if (!checkFoundProduct) {
                MSG.WAR("ไม่พบรหัสสินค้า " + PCode + " ในฐานข้อมูล หรือ รหัสสินค้านี้อาจยกเลิกการขายแล้ว...");
            } else {
                if (!PUtility.CheckStockOK(PCode, R_Quan)) {
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
                    balance.setR_PrintOK("Y");
                    balance.setMacno(Value.MACNO);
                    balance.setCashier(Value.USERCODE);
                    balance.setR_ETD(etd);
                    balance.setR_Quan(R_Quan);
                    balance.setR_Table(tableNo);
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
                        balance.setR_QuanCanDisc(0);// if member default 0
                    } else {
                        balance.setR_PrSubType("");
                        balance.setR_PrSubCode("");
                        balance.setR_PrSubQuan(0);// not member default 0
                        balance.setR_PrSubDisc(0);
                        balance.setR_PrSubBath(0);
                        balance.setR_PrSubAmt(0);
                        balance.setR_QuanCanDisc(balance.getR_Quan());
                    }

                    balance.setR_Pause("P");
                    balanceControl.saveBalance(balance, productBean);

                    //update temptset
                    updateTempTset(balance, r_etd, tableNo, productBean);

                    stmt.close();

                    //Process Stock Out
                    String StkRemark = "SAL";
                    String DocNo = tableNo + "/" + Timefmt.format(new Date());
                    if (productBean.getPStock().equals("Y") && productBean.getPActive().equals("Y")) {
                        PUtility.ProcessStockOut(DocNo, StkCode, balance.getR_PluCode(), new Date(), StkRemark, balance.getR_Quan(), balance.getR_Total(),
                                balance.getCashier(), balance.getR_Stock(), balance.getR_Set(), R_Index, "1");

                    }

                    //ตัดสต็อกสินค้าที่มี Ingredent
                    try {
                        String sql2 = "select i.*,pdesc,PBPack,pstock,pactive "
                                + "from product p, pingredent i "
                                + "where p.pcode=i.pingcode "
                                + "and i.pcode='" + balance.getR_PluCode() + "' "
                                + "and PFix='L' and PStock='Y'";
                        Statement stmt1 = mysql.getConnection().createStatement();
                        ResultSet rs1 = stmt1.executeQuery(sql2);
                        while (rs1.next()) {
                            if (rs1.getString("pstock").equals("Y") && rs1.getString("pactive").equals("Y")) {
                                String R_PluCode = rs1.getString("PingCode");
                                double PBPack = rs1.getDouble("PBPack");
                                if (PBPack <= 0) {
                                    PBPack = 1;
                                }
                                double R_QuanIng = (rs1.getDouble("PingQty") * balance.getR_Quan()) / PBPack;
                                double R_Total = 0;
                                PUtility.ProcessStockOut(DocNo, StkCode, R_PluCode, new Date(), StkRemark, R_QuanIng, R_Total,
                                        balance.getCashier(), "Y", "", "", "");
                            }

                        }

                        rs1.close();
                        stmt1.close();
                    } catch (SQLException e) {
                        MSG.ERR(e.getMessage());
                        AppLogUtil.log(FloorPlanDialog.class, "error", e);
                    }

                    //update promotion
                    memberBean = null;
                    BalanceControl.updateProSerTable(tableNo, memberBean);
                    BalanceControl.GetDiscount(tableNo);
                    PublicVar.ErrorColect = true;
                } //end of Load Data
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(this, e.getMessage());
            AppLogUtil.log(FloorPlanDialog.class, "error", e);
        } finally {
            mysql.close();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem MShowDailyEJ1;
    private javax.swing.JButton jButton1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem27;
    private javax.swing.JMenuItem jMenuItem28;
    private javax.swing.JMenuItem jMenuItem29;
    private javax.swing.JMenuItem jMenuItem30;
    private javax.swing.JMenuItem jMenuItem31;
    private javax.swing.JMenuItem jMenuItem32;
    private javax.swing.JMenuItem jMenuItem33;
    private javax.swing.JMenuItem jMenuItem34;
    private javax.swing.JMenuItem jMenuItem35;
    private javax.swing.JMenuItem jMenuItem36;
    private javax.swing.JMenuItem jMenuItem38;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel pnZone1;
    private javax.swing.JPanel pnZone2;
    private javax.swing.JPanel pnZone3;
    private javax.swing.JPanel pnZone4;
    private javax.swing.JPanel pnZone5;
    private javax.swing.JPanel pnZone6;
    private javax.swing.JPanel pnZone7;
    // End of variables declaration//GEN-END:variables

    private void addButton() {
        int count;
        String strCount;
        int c = 0;

        JPanel[] panelMain = new JPanel[]{pnZone1, pnZone2, pnZone3, pnZone4, pnZone5, pnZone6, pnZone7};
        for (int i = 0; i < panelMain.length; i++) {
            if (i == jTabbedPane1.getSelectedIndex()) {
                panelMain[i].removeAll();
            }
        }

        String[] listTableHeader = new String[]{"T", "A", "B", "C", "D", "E", "F"};
        for (int i = 1; i <= 10; i++) {
            count = i;
            for (int j = 0; j < 10; j++) {//โต๊ะแนวนอน
                if (count < 10) {
                    strCount = "00" + count;
                } else if (count < 100) {
                    strCount = "0" + count;
                } else {
                    strCount = "" + count;
                }
                for (int x = 0; x < listTableHeader.length; x++) {
                    String listTableHeader1 = listTableHeader[x];
                    JButton button = initButtonTable(listTableHeader1 + strCount, c);
                    if (x == jTabbedPane1.getSelectedIndex()) {
                        panelMain[x].add(button);
                    }
                }
                count += 10;
                c++;
            }
        }

        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "select code_id, t1.tcode, tcustomer, tonact,tlogintime,titem,"
                    + "TAmount,PrintChkBill "
                    + "from tablesetup t1, tablefile t2 "
                    + "where t1.tcode=t2.tcode "
                    + "order by code_id";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                TableSetup bean = new TableSetup();
                bean.setTableNo(rs.getString("TCode"));
                bean.setLoginTime(rs.getString("tlogintime"));
                bean.setCustomer(rs.getInt("TCustomer"));
                bean.setIsActive(rs.getString("TOnAct").equals("Y"));
                bean.setTAmount(rs.getDouble("TAmount"));
                bean.setPrintChkBill(rs.getString("PrintChkBill"));
                int item = 0;
                item = rs.getInt("titem");
                String codeId = rs.getString("code_id");

                //find zone, index
                bean.setZone(codeId.substring(0, 1));
                try {
                    JButton btn = null;
                    bean.setIndex(Integer.parseInt(codeId.substring(1, codeId.length())));
                    if (bean.getZone().equals("T")) {
                        btn = findButton(pnZone1, codeId);
                    } else if (bean.getZone().equals("A")) {
                        btn = findButton(pnZone2, codeId);
                    } else if (bean.getZone().equals("B")) {
                        btn = findButton(pnZone3, codeId);
                    } else if (bean.getZone().equals("C")) {
                        btn = findButton(pnZone4, codeId);
                    } else if (bean.getZone().equals("D")) {
                        btn = findButton(pnZone5, codeId);
                    } else if (bean.getZone().equals("E")) {
                        btn = findButton(pnZone6, codeId);
                    } else if (bean.getZone().equals("F")) {
                        btn = findButton(pnZone7, codeId);
                    }

                    if (btn == null) {
                        continue;
                    }

                    //set value of Table No
                    if (bean.getLoginTime() != null) {
                        String r_time = "";
                        try {
                            String sqlGettime = "select r_time from balance where r_table ='" + bean.getTableNo() + "' "
                                    + "order by r_date,r_time limit 1";
                            Statement stmt1 = mysql.getConnection().createStatement();
                            ResultSet rsTime1 = stmt1.executeQuery(sqlGettime);
                            if (rsTime1.next()) {
                                r_time = rsTime1.getString("r_time").substring(0, 5);
                            }
                            rsTime1.close();
                            stmt1.close();
                        } catch (SQLException e) {
                            MSG.ERR(e.getMessage());
                            AppLogUtil.log(FloorPlanDialog.class, "error", e);
                        }
                        btn.setText(bean.getTableNo() + "(" + r_time + ")");
                        if (bean.getCustomer() == 0 && item > 0) {
                            btn.setText(bean.getTableNo() + "(" + r_time + ")");
                        }
                        if (bean.isIsActive()) {
                            btn.setOpaque(false);
                            setButtonShowTableFloorPlan(btn, Color.RED, bean.getTableNo());
                        } else {
                            if (bean.getPrintChkBill().equals("N") && bean.getCustomer() > 0) {
                                btn.setFont(fontB);
                                btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Teble2.png")));
                                btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                                btn.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
                                btn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                            } else if (bean.getPrintChkBill().equals("Y")) {
                                btn.setOpaque(true);
                                btn.setFont(fontB);
                                btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/checkbill.png")));
                                btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                                btn.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
                                btn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                                btn.setBackground(Color.PINK);
                            } else {
                                btn.setOpaque(true);
                                btn.setFont(fontB);
                                btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/table_1.png")));
                                btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                                btn.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
                                btn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                            }
                        }
                    } else {
                        setButtonShowTableFloorPlan(btn, new Color(153, 255, 153), bean.getTableNo());
                    }
                } catch (NumberFormatException e) {
                    MSG.ERR(this, e.getMessage());
                    AppLogUtil.log(FloorPlanDialog.class, "error", e);
                }
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(FloorPlanDialog.class, "error", e);
        } finally {
            mysql.close();
        }
    }

    private JButton findButton(JPanel pnZone, String codeId) {
        for (int i = 0; i < pnZone.getComponentCount(); i++) {
            JButton btn = (JButton) pnZone.getComponent(i);
            if (btn.getName().equals(codeId)) {
                return btn;
            }
        }
        return null;
    }

    private void loadHeaderTab() {
        CompanyBean companyBean = PosControl.getDataCompany();
        String[] floorTab = new String[]{
            ThaiUtil.ASCII2Unicode(companyBean.getFloorTab1()),
            ThaiUtil.ASCII2Unicode(companyBean.getFloorTab2()),
            ThaiUtil.ASCII2Unicode(companyBean.getFloorTab3()),
            ThaiUtil.ASCII2Unicode(companyBean.getFloorTab4()),
            ThaiUtil.ASCII2Unicode(companyBean.getFloorTab5()),
            ThaiUtil.ASCII2Unicode(companyBean.getFloorTab6()),
            ThaiUtil.ASCII2Unicode(companyBean.getFloorTab7())
        };
        FPlanPanel[] fPlanPanel = new FPlanPanel[7];
        for (int i = 0; i < floorTab.length; i++) {
//            fPlanPanel[i] = new FPlanPanel();
//            jTabbedPane1.add(fPlanPanel[i]);

            JLabel lab = new JLabel(ThaiUtil.ASCII2Unicode(floorTab[i]));
            add(lab);
            lab.setFont(fontC);
            if (lab.getText().trim().equals("")) {
                lab.setHorizontalAlignment(SwingConstants.CENTER);
            }
            jTabbedPane1.setTabComponentAt(i, lab);
//            jTabbedPane1.setComponentAt(i, fPlanPanel[i].loadData(i));
            jTabbedPane1.setIconAt(i, null);
        }
    }

    public void PrintCheckBillFromPDA() {
        //ฟังก์ชั่นสั่งเช็คบิลสำหรับ PDA
        System.out.println("PrintCheckBillFromPDA= loop");
        if (!POSHW.getTakeOrderChk().equals("Y")) {
            MySQLConnect mysql = new MySQLConnect();
            try {
                mysql.open();
                String sql = "select * from balance where PDAPrintCheck='Y' "
                        + "group by r_table order by r_time;";
                // อัพเดท = N หลังจากพิมพ์แล้วเพื่อให้ PDA สามารถสั่งพิมพ์ซ้ำๆ ได้
                ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql);
                while (rs.next()) {
                    String table = rs.getString("r_table");
                    String emp = ThaiUtil.ASCII2Unicode(rs.getString("PDAEMP"));
                    if (Value.useprint) {
                        PPrint print = new PPrint();
                        print.printCheckBillDriverPDA(table, emp);
                    }
                    String sqlUpdate = "update balance set PDAPrintCheck='N' where r_table='" + table + "';";
                    mysql.getConnection().createStatement().executeUpdate(sqlUpdate);
                }
                rs.close();
            } catch (SQLException e) {
                MSG.ERR(e.getMessage());
                AppLogUtil.log(FloorPlanDialog.class, "error", e);
            } finally {
                mysql.close();
            }
        }
    }

    private void logout() {
        if (MSG.CONF(this, "ยืนยันการออกจากระบบการขาย (Logoff User) ? ")) {
            PublicVar.P_LineCount = 1;
            PublicVar.P_LogoffOK = false;

            if (UpdateLogout(PublicVar._RealUser)) {
                clearTemp();

                MySQLConnect mysql = new MySQLConnect();
                mysql.open();
                try {
                    Statement stmt = mysql.getConnection().createStatement();
                    String sql1 = "update posuser set onact='N',macno=''where (username='" + PublicVar._User + "')";
                    stmt.executeUpdate(sql1);

                    String sql2 = "update poshwsetup set onact='N' where(terminal='" + Value.MACNO + "')";
                    if (stmt.executeUpdate(sql2) > 0) {
                        // reset load poshwsetup
                        PosControl.resetPosHwSetup();
                    }
                    stmt.close();
                } catch (SQLException e) {
                    MSG.ERR(e.getMessage());
                    AppLogUtil.log(FloorPlanDialog.class, "error", e);
                    System.exit(0);
                } finally {
                    mysql.close();
                }
                System.exit(0);
            }
        } else {
            MySQLConnect mysql = new MySQLConnect();
            try {
                mysql.open();
                String sql1 = "update poshwsetup set onact='N' where terminal='" + Value.MACNO + "'";
                Statement stmt = mysql.getConnection().createStatement();
                if (stmt.executeUpdate(sql1) > 0) {
                    // reset load poshwsetup
                    PosControl.resetPosHwSetup();
                }
                String sql2 = "update posuser set onact='N',macno=''where (username='" + PublicVar._User + "')";
                stmt.executeUpdate(sql2);
                stmt.close();
            } catch (SQLException e) {
                MSG.ERR(e.getMessage());
                AppLogUtil.log(FloorPlanDialog.class, "error", e);
            } finally {
                mysql.close();
            }
        }
    }

    private void clearTemp() {
        new File("softrestaurant.running").delete();
    }

    boolean UpdateLogout(String UserCode) {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            try (Statement stmt = mysql.getConnection().createStatement()) {
                String SQLQuery = "update posuser set onact='N',macno='' where username='" + UserCode + "'";
                stmt.executeUpdate(SQLQuery);
            }
            Value.CASHIER = "";

            return true;
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(FloorPlanDialog.class, "error", e);
            return false;
        } finally {
            mysql.close();
        }

    }

    private void returnBill() {
        String tableTemp = Value.TEMP_TABLE_REFUND;
        boolean checkExistTempRefund = false;

        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "select r_plucode from sp_temp_refund limit 1;";
            try (Statement stmt = mysql.getConnection().createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    //create temp table
                    TableFileControl tfControl = new TableFileControl();
                    tfControl.createNewTable(tableTemp);
                }

                BalanceControl bControl = new BalanceControl();
                int count = 0;
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    count++;
                    checkExistTempRefund = true;
                    BalanceBean bBean = new BalanceBean();
                    bBean.setR_PluCode(rs.getString("R_PluCode"));
                    bBean.setR_Quan(rs.getDouble("R_Quan"));
                    bBean.setR_Price(rs.getDouble("R_Price"));
                    bBean.setR_ETD(rs.getString("R_ETD"));
                    bBean.setR_Opt1("");
                    bBean.setR_Opt2("");
                    bBean.setR_Opt3("");
                    bBean.setR_Opt4("");
                    bBean.setR_Opt5("");
                    bBean.setR_Opt6("");
                    bBean.setR_Opt7("");
                    bBean.setR_Opt8("");
                    bBean.setR_Opt9("");

                    bBean.setR_Table(tableTemp);
                    bBean.setR_Emp("");
                    bBean.setCashier("");

                    String runningIndex;
                    if (count < 10) {
                        runningIndex = "00" + count;
                    } else if (count < 100) {
                        runningIndex = "0" + count;
                    } else {
                        runningIndex = "" + count;
                    }

                    bBean.setR_Index(tableTemp + "/" + runningIndex);

                    ProductBean productBean = productControl.getProductCodeArray(bBean.getR_PluCode());
                    bControl.saveBalance(bBean, productBean);
                }

                rs.close();
                stmt.close();
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(FloorPlanDialog.class, "error", e);
        } finally {
            mysql.close();
        }

        if (!checkExistTempRefund) {
            JOptionPane.showMessageDialog(this, "ไม่พบบิลรายการขายสินค้า ที่ยกเลิกก่อนหน้านี้ !");
        } else {
            mysql.open();
            try {
                Statement stmt = mysql.getConnection().createStatement();
                //clear temp table
                stmt.executeUpdate("delete from sp_temp_refund");

                BalanceControl.updateProSerTable(tableTemp, null);
                Value.TableSelected = tableTemp;
                MainSale mainSale = new MainSale(null, true, tableTemp);
                mainSale.setVisible(true);

                stmt.close();
            } catch (SQLException e) {
                MSG.ERR(e.getMessage());
            } finally {
                mysql.close();
            }

            // close this window
            dispose();
        }

    }

    private void setButtonShowTableFloorPlan(JButton btn, Color color, String tableNo) {
        if (!tableNo.equals("")) {
            btn.setText(tableNo);
            btn.setText("<html><center><h3>" + tableNo + "(0)</h3></center>");
            btn.setText(tableNo);
            btn.setBackground(color);
            btn.setFont(fontB);
            btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/table_1.png")));
            btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            btn.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
            btn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        }

    }

    private void refund() {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        boolean isPermit = false;
        try {
            String sql = "select Username, Sale3 from posuser where username='" + Value.USERCODE + "' and Sale2='Y' limit 1";
            try (Statement stmt = mysql.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    isPermit = true;
                }
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(FloorPlanDialog.class, "error", e);
        } finally {
            mysql.close();
        }

        if (isPermit) {
            RefundBill refund = new RefundBill(null, true);
            refund.setVisible(true);
        } else {
            GetUserAction getuser = new GetUserAction(null, true);
            getuser.setVisible(true);

            if (!PublicVar.ReturnString.equals("")) {
                String loginname = PublicVar.ReturnString;
                UserRecord supUser = new UserRecord();
                if (supUser.GetUserAction(loginname)) {
                    if (supUser.Sale2.equals("Y")) {
                        RefundBill refund = new RefundBill(null, true);
                        refund.setVisible(true);
                    } else {
                        MSG.WAR(this, "รหัสพนักงานนี้ไม่สามารถเข้าใช้งาน...รายการนี้ได้...!!!");
                    }
                } else {
                    MSG.WAR(this, "ไม่สามารถ Load สิทธิ์การใช้งานของผู้ใช้งานคนนี้ได้ ...");
                }
            }
        }
    }

    private JButton initButtonTable(String buttonName, int c) {
        JButton button = new JButton("");
        if (buttonStyle == 1) {
            button.setName(buttonName);
            button.setFont(fontA);
            button.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            button.addMouseListener(new MouseClickAction(button, c));
            button.addActionListener(new MouseFocusAction(button, c));
        } else {
            button.setPreferredSize(new Dimension(50, 50));
            button.setFocusPainted(false);
            button.setMargin(new Insets(0, 0, 0, 0));
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setOpaque(false);
            button.setName(buttonName);
            button.setFont(fontA);
            button.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            button.addMouseListener(new MouseClickAction(button, c));
            button.addActionListener(new MouseFocusAction(button, c));
        }

        return button;
    }

    class TableSetup {

        private int index;
        private String tableNo;
        private String loginTime;
        private int customer;
        private String status;
        private boolean isActive;
        private String zone;
        private double TAmount;
        private String PrintChkBill;

        public String getPrintChkBill() {
            return PrintChkBill;
        }

        public void setPrintChkBill(String PrintChkBill) {
            this.PrintChkBill = PrintChkBill;
        }

        public double getTAmount() {
            return TAmount;
        }

        public void setTAmount(double TAmount) {
            this.TAmount = TAmount;
        }

        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }

        public boolean isIsActive() {
            return isActive;
        }

        public void setIsActive(boolean isActive) {
            this.isActive = isActive;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getTableNo() {
            return tableNo;
        }

        public void setTableNo(String tableNo) {
            this.tableNo = tableNo;
        }

        public String getLoginTime() {
            return loginTime;
        }

        public void setLoginTime(String loginTime) {
            this.loginTime = loginTime;
        }

        public int getCustomer() {
            return customer;
        }

        public void setCustomer(int customer) {
            this.customer = customer;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    private class MouseFocusAction implements ActionListener {

        private JButton button;
        private int index;

        public MouseFocusAction(JButton button, int index) {
            this.button = button;
            this.index = index;
        }

        private void showPOS(String tableNo) {
            dispose();

            String sql = "delete from tempset where ptableno='" + tableNo + "';";
            MySQLConnect mysql = new MySQLConnect();
            try {
                mysql.open();
                mysql.getConnection().createStatement().executeUpdate(sql);
            } catch (SQLException e) {
                MSG.ERR(e.getMessage());
                AppLogUtil.log(FloorPlanDialog.class, "error", e);
            } finally {
                mysql.close();
            }

            MainSale mainSale = new MainSale(null, true, tableNo);
            mainSale.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String tableNo = button.getText().trim();
            if (!tableNo.equals("")) {
                // tableNo = button.getText().substring(6,tableNo.length());
                Value.TableSelected = tableNo;
                //check table is available
                TableFileControl tfCont = new TableFileControl();
                if (!tfCont.checkTableOpened(tableNo)) {

                    String P_EmpUse = CONFIG.getP_EmpUse();
                    if (P_EmpUse.equals("Y")) {
                        // get employee password
                        EmployLogin login = new EmployLogin(null, true);
                        login.setVisible(true);

                        if (!login.getLoginPWD().equals("")) {
                            String sql2 = "select code,name from employ where Code='" + login.getLoginPWD() + "' limit 1;";
                            MySQLConnect mysql = new MySQLConnect();
                            mysql.open();
                            try {
                                Statement stmt2 = mysql.getConnection().createStatement();
                                ResultSet rs2 = stmt2.executeQuery(sql2);
                                if (rs2.next()) {
                                    login.setVisible(false);
                                    try {
                                        String sql9 = "update tablefile "
                                                + "set TUser='" + login.getLoginPWD() + "',"
                                                + "Macno='" + Value.MACNO + "' "
                                                + "where TCode = '" + tableNo + "'";
                                        //String sqlEmp_balance = "";
                                        Statement stmt3 = mysql.getConnection().createStatement();
                                        stmt3.executeUpdate(sql9);
                                        stmt3.close();
                                    } catch (SQLException e9) {
                                        MSG.ERR(e9.getMessage());
                                    }

                                    Value.EMP_CODE = login.getLoginPWD();

                                    showPOS(tableNo);
                                } else {
                                    if (!login.getLoginPWD().equals("")) {
                                        JOptionPane.showMessageDialog(null, "ท่านระบุรหัสบริกรไม่ถูกต้อง !");
                                    }
                                    login.setVisible(false);
                                }

                                rs2.close();
                                stmt2.close();
                            } catch (HeadlessException | SQLException e1) {
                                login.setVisible(false);
                                MSG.ERR(e1.getMessage());
                                AppLogUtil.log(FloorPlanDialog.class, "error", e1);
                            } finally {
                                mysql.close();
                            }
                        }
                    } else {
                        showPOS(tableNo);
                    }
                } else {
                    MSG.WAR("มีพนักงานกำลังใช้งานโต๊ะนี้อยู่ !!!");
                }
            }
        }
    }

    private class MouseClickAction extends MouseAdapter {

        private JButton button;
        private int index;

        public MouseClickAction(JButton button, int index) {
            this.button = button;
            this.index = index;
        }

        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            if (evt.getButton() == 3) {
                Value.BTN_FLOORPLAN = button.getName();
                JPopupMenu pop = jPopupMenu1;
                pop.show(button, evt.getX(), evt.getY());

            }
        }
    }

    public JPanel getPanelImage(JLabel lbCust, final JLabel lbTable, JButton btnIcon) {
        //config layout
        JPanel pnButton = new JPanel();
        pnButton.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbCust.setFont(new java.awt.Font("Tahoma", 1, 10));
        lbCust.setForeground(new java.awt.Color(0, 0, 204));
        lbCust.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbCust.setText("C(0) T(1,500)");
        pnButton.add(lbCust, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 80, 30));

        lbTable.setFont(new java.awt.Font("Tahoma", 1, 12));
        lbTable.setForeground(new java.awt.Color(0, 0, 204));
        lbTable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTable.setText("TABLE: 101");
        pnButton.add(lbTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 80, 30));

        btnIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/table_void.png")));
        btnIcon.setSize(70, 70);
        btnIcon.addActionListener((ActionEvent e) -> {
            MSG.ERR(null, lbTable.getText());
        });

        pnButton.add(btnIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 80, 80));

        return pnButton;
    }

    boolean ChkEJPath() {
        if (POSHW.getEJounal().equals("Y")) {
            try {
                String TempFile = POSHW.getEJDailyPath();
                File fObject = new File(TempFile);
                if (!fObject.exists()) {
                    MSG.ERR(this, "ไม่สามารถสร้าง Log File/EJFile ตามตำแหน่งที่เก็บข้อมูล Log File/EJ ได้ กรุณาติดต่อเจ้าหน้าที่ Support...");
                    return false;
                }
                return true;
            } catch (HeadlessException e) {
                MSG.ERR(this, "ไม่สามารถสร้าง Log File/EJFile ตามตำแหน่งที่เก็บข้อมูล Log File/EJ ได้ กรุณาติดต่อเจ้าหน้าที่ Support...");
                return false;
            }
        } else {
            return true;
        }
    }

    private void openDoc() {
        Desktop desktop = Desktop.getDesktop();
        String file = "user_manaual.pdf";
        try {
            File f = new File(file);
            if (f.exists()) {
                desktop.open(f);
            } else {
                MSG.ERR(this, "หาไฟล์ " + file + " ไม่เจอในระบบ กรุณาตรวจสอบ");
            }
        } catch (IOException ex) {
            MSG.ERR(this, ex.getMessage());
        }
    }

    private void openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (IOException e) {
                MSG.ERR(this, e.getMessage());
            }
        }
    }

    private void openWebpage(URL url) throws URISyntaxException {
        openWebpage(url.toURI());
    }

    private void updateTempTset(BalanceBean bBean, String r_etd, String tableNo, ProductBean productBean) {
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();

        try {
            String sqlUpd = "update tempset set "
                    + "PIndex='" + bBean.getR_Index() + "' "
                    + "where PTableNo='" + bBean.getR_Table() + "' ";
            mysql.getConnection().createStatement().executeUpdate(sqlUpd);

            String sql = "select * from tempset where PIndex='" + bBean.getR_Index() + "' ";
            ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql);
            while (rs.next()) {
                if (rs.getString("PCode").equals(bBean.getR_PluCode())) {
                    updateBalanceOptionFromTemp(bBean.getR_Index(), bBean.getR_Table(), bBean.getR_PluCode());
                } else {
                    //อย่าลืมเพิ่มข้อมูลใน balance ด้วย
                    String PCode = rs.getString("PCode");
                    if (!PCode.equals("")) {
                        String StkCode = PUtility.GetStkCode();
                        String emp = Value.EMP_CODE;
                        String etd = r_etd;
                        String[] data = Option.splitPrice(PCode);
                        double R_Quan = Double.parseDouble(data[0]);
                        PCode = data[1];

                        BalanceBean balance = new BalanceBean();
                        balance.setStkCode(StkCode);
                        balance.setR_PrintOK(PublicVar.PrintOK);
                        balance.setMacno(Value.MACNO);
                        balance.setCashier(Value.USERCODE);
                        balance.setR_ETD(etd);
                        balance.setR_Quan(R_Quan);
                        balance.setR_Table(tableNo);
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

                        balanceControl.saveBalance(balance, productBean);
                        updateBalanceOptionFromTemp(bBean.getR_Index(), balance.getR_Table(), PCode);

                        //Process stock out
                        String StkRemark = "SAL";
                        String DocNo = tableNo + "/" + Timefmt.format(new Date());

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
                            Statement stmt2 = mysql.getConnection().createStatement();
                            ResultSet rs1 = stmt2.executeQuery(sql1);
                            while (rs1.next()) {
                                String R_PluCode = rs1.getString("PingCode");
                                double PBPack = rs1.getDouble("PBPack");
                                if (PBPack <= 0) {
                                    PBPack = 1;
                                }
                                double R_QuanIng = (rs1.getDouble("PingQty") * balance.getR_Quan()) / PBPack;
                                double R_Total = 0;
                                if (rs1.getString("pstock").equals("Y") && rs1.getString("pactive").equals("Y")) {
                                    PUtility.ProcessStockOut(DocNo, StkCode, R_PluCode, new Date(), StkRemark, R_QuanIng, R_Total,
                                            balance.getCashier(), "Y", "", "", "");
                                }
                            }
                            rs1.close();
                            stmt2.close();
                        } catch (SQLException e) {
                            MSG.ERR(e.getMessage());
                            AppLogUtil.log(FloorPlanDialog.class, "error", e);
                        }
                    }
                }
            }

            //clear tempset
            try {
                String sqlClear = "delete from tempset where PTableNo='" + bBean.getR_Table() + "'";
                Statement stmt2 = mysql.getConnection().createStatement();
                stmt2.executeUpdate(sqlClear);
                stmt2.close();
            } catch (SQLException e) {
                MSG.ERR(e.getMessage());
                AppLogUtil.log(FloorPlanDialog.class, "error", e);
            }

            rs.close();
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(FloorPlanDialog.class, "error", e);
        } finally {
            mysql.close();
        }
    }

    private void updateBalanceOptionFromTemp(String R_Index, String TableNo, String PCode) {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "select POption from tempset where PIndex='" + R_Index + "' and PCode='" + PCode + "' limit 1";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String opt = ThaiUtil.Unicode2ASCII(rs.getString("POption"));
                String sql1 = "update balance "
                        + "set R_Opt1='" + opt + "',"
                        + "R_LinkIndex='" + R_Index + "' "
                        + "where R_Table='" + TableNo + "' "
                        + "and R_PluCode='" + PCode + "' "
                        + "and R_LinkIndex=''";
                try (Statement stmt1 = mysql.getConnection().createStatement()) {
                    stmt1.executeUpdate(sql1);
                }
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(this, e.getMessage());
            AppLogUtil.log(FloorPlanDialog.class, "error", e);
        } finally {
            mysql.close();
        }

    }

    DateConvert dateConvertTimeShow = new DateConvert();

    public void showTime() {
        jMenu9.setText(dateConvertTimeShow.dateGetToShow(dateConvertTimeShow.GetCurrentDate()).replace(" ", "") + " " + dateConvertTimeShow.GetCurrentTime() + " USER " + Value.CASHIER);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
    }

}
