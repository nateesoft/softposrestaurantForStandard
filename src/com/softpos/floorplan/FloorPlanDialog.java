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
import com.softpos.pos.core.model.POSConfigSetup;
import com.softpos.pos.core.model.POSHWSetup;
import com.softpos.pos.core.controller.PPrint;
import com.softpos.pos.core.controller.PUtility;
import com.softpos.pos.core.controller.PosControl;
import com.softpos.pos.core.controller.ProductControl;
import com.softpos.crm.pos.core.modal.PublicVar;
import com.softpos.pos.core.controller.TableFileControl;
import com.softpos.pos.core.controller.TableSetupControl;
import com.softpos.pos.core.controller.ThaiUtil;
import com.softpos.pos.core.controller.Value;
import com.softpos.pos.core.model.BalanceBean;
import com.softpos.pos.core.model.CompanyBean;
import com.softpos.pos.core.model.FloorPlanBean;
import com.softpos.pos.core.model.MemberBean;
import com.softpos.pos.core.model.PosUserBean;
import com.softpos.pos.core.model.ProductBean;
import database.MySQLConnect;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.util.List;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
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
    private int refresh = 10;
    private Font fontA = new Font("Tahoma", Font.PLAIN, 14);
    private Font fontB = new Font("Tahoma", Font.BOLD, 11);
    private Font fontC = new Font("Tahoma", Font.BOLD, 16);
    private SimpleDateFormat Timefmt = new SimpleDateFormat("HH:mm:ss");
    private MemberBean memberBean;
    
    private String zoneSelected = "T";
    private int buttonStyle = 0;
    
    private final ProductControl productControl = new ProductControl();
    private JButton[] buttons = new JButton[100];
    private PosUserBean posUser = null;
    
    public FloorPlanDialog() {
        setUndecorated(true);
        initComponents();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Already there
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        POSHW = POSHWSetup.Bean(Value.MACNO);
        CONFIG = POSConfigSetup.Bean();
        posUser = PosControl.getPosUser(PublicVar.ReturnString);
        
        refresh = PosControl.getRefreshTime();
        if (refresh < 1) {
            refresh = 1;
        }
        
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
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnZone1 = new javax.swing.JButton();
        btnZone2 = new javax.swing.JButton();
        btnZone3 = new javax.swing.JButton();
        btnZone4 = new javax.swing.JButton();
        btnZone5 = new javax.swing.JButton();
        btnZone6 = new javax.swing.JButton();
        btnZone7 = new javax.swing.JButton();
        panelMain = new javax.swing.JPanel();
        btn1 = new javax.swing.JButton();
        btn2 = new javax.swing.JButton();
        btn3 = new javax.swing.JButton();
        btn4 = new javax.swing.JButton();
        btn5 = new javax.swing.JButton();
        btn6 = new javax.swing.JButton();
        btn7 = new javax.swing.JButton();
        btn8 = new javax.swing.JButton();
        btn9 = new javax.swing.JButton();
        btn10 = new javax.swing.JButton();
        btn11 = new javax.swing.JButton();
        btn12 = new javax.swing.JButton();
        btn13 = new javax.swing.JButton();
        btn14 = new javax.swing.JButton();
        btn15 = new javax.swing.JButton();
        btn16 = new javax.swing.JButton();
        btn17 = new javax.swing.JButton();
        btn18 = new javax.swing.JButton();
        btn19 = new javax.swing.JButton();
        btn20 = new javax.swing.JButton();
        btn21 = new javax.swing.JButton();
        btn22 = new javax.swing.JButton();
        btn23 = new javax.swing.JButton();
        btn24 = new javax.swing.JButton();
        btn25 = new javax.swing.JButton();
        btn26 = new javax.swing.JButton();
        btn27 = new javax.swing.JButton();
        btn28 = new javax.swing.JButton();
        btn29 = new javax.swing.JButton();
        btn30 = new javax.swing.JButton();
        btn31 = new javax.swing.JButton();
        btn32 = new javax.swing.JButton();
        btn33 = new javax.swing.JButton();
        btn34 = new javax.swing.JButton();
        btn35 = new javax.swing.JButton();
        btn36 = new javax.swing.JButton();
        btn37 = new javax.swing.JButton();
        btn38 = new javax.swing.JButton();
        btn39 = new javax.swing.JButton();
        btn40 = new javax.swing.JButton();
        btn41 = new javax.swing.JButton();
        btn42 = new javax.swing.JButton();
        btn43 = new javax.swing.JButton();
        btn44 = new javax.swing.JButton();
        btn45 = new javax.swing.JButton();
        btn46 = new javax.swing.JButton();
        btn47 = new javax.swing.JButton();
        btn48 = new javax.swing.JButton();
        btn49 = new javax.swing.JButton();
        btn50 = new javax.swing.JButton();
        btn51 = new javax.swing.JButton();
        btn52 = new javax.swing.JButton();
        btn53 = new javax.swing.JButton();
        btn54 = new javax.swing.JButton();
        btn55 = new javax.swing.JButton();
        btn56 = new javax.swing.JButton();
        btn57 = new javax.swing.JButton();
        btn58 = new javax.swing.JButton();
        btn59 = new javax.swing.JButton();
        btn60 = new javax.swing.JButton();
        btn61 = new javax.swing.JButton();
        btn62 = new javax.swing.JButton();
        btn63 = new javax.swing.JButton();
        btn64 = new javax.swing.JButton();
        btn65 = new javax.swing.JButton();
        btn66 = new javax.swing.JButton();
        btn67 = new javax.swing.JButton();
        btn68 = new javax.swing.JButton();
        btn69 = new javax.swing.JButton();
        btn70 = new javax.swing.JButton();
        btn71 = new javax.swing.JButton();
        btn72 = new javax.swing.JButton();
        btn73 = new javax.swing.JButton();
        btn74 = new javax.swing.JButton();
        btn75 = new javax.swing.JButton();
        btn76 = new javax.swing.JButton();
        btn77 = new javax.swing.JButton();
        btn78 = new javax.swing.JButton();
        btn79 = new javax.swing.JButton();
        btn80 = new javax.swing.JButton();
        btn81 = new javax.swing.JButton();
        btn82 = new javax.swing.JButton();
        btn83 = new javax.swing.JButton();
        btn84 = new javax.swing.JButton();
        btn85 = new javax.swing.JButton();
        btn86 = new javax.swing.JButton();
        btn87 = new javax.swing.JButton();
        btn88 = new javax.swing.JButton();
        btn89 = new javax.swing.JButton();
        btn90 = new javax.swing.JButton();
        btn91 = new javax.swing.JButton();
        btn92 = new javax.swing.JButton();
        btn93 = new javax.swing.JButton();
        btn94 = new javax.swing.JButton();
        btn95 = new javax.swing.JButton();
        btn96 = new javax.swing.JButton();
        btn97 = new javax.swing.JButton();
        btn98 = new javax.swing.JButton();
        btn99 = new javax.swing.JButton();
        btn100 = new javax.swing.JButton();
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
        jMenuItem35 = new javax.swing.JMenuItem();
        jMenuItem36 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
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

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 0, 51));
        jButton1.setMnemonic('\u0e22');
        jButton1.setText("-");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        btnZone1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnZone1.setText("Zone1");
        btnZone1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZone1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnZone1);

        btnZone2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnZone2.setText("Zone2");
        btnZone2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZone2ActionPerformed(evt);
            }
        });
        jPanel1.add(btnZone2);

        btnZone3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnZone3.setText("Zone3");
        btnZone3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZone3ActionPerformed(evt);
            }
        });
        jPanel1.add(btnZone3);

        btnZone4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnZone4.setText("Zone4");
        btnZone4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZone4ActionPerformed(evt);
            }
        });
        jPanel1.add(btnZone4);

        btnZone5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnZone5.setText("Zone5");
        btnZone5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZone5ActionPerformed(evt);
            }
        });
        jPanel1.add(btnZone5);

        btnZone6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnZone6.setText("Zone6");
        btnZone6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZone6ActionPerformed(evt);
            }
        });
        jPanel1.add(btnZone6);

        btnZone7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnZone7.setText("Zone7");
        btnZone7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZone7ActionPerformed(evt);
            }
        });
        jPanel1.add(btnZone7);

        panelMain.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 51), 3));
        panelMain.setLayout(new java.awt.GridLayout(10, 10, 2, 2));

        btn1.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        panelMain.add(btn1);

        btn2.setBorder(null);
        panelMain.add(btn2);

        btn3.setBorder(null);
        panelMain.add(btn3);

        btn4.setBorder(null);
        panelMain.add(btn4);

        btn5.setBorder(null);
        panelMain.add(btn5);

        btn6.setBorder(null);
        panelMain.add(btn6);

        btn7.setBorder(null);
        panelMain.add(btn7);

        btn8.setBorder(null);
        panelMain.add(btn8);

        btn9.setBorder(null);
        panelMain.add(btn9);

        btn10.setBorder(null);
        panelMain.add(btn10);

        btn11.setBorder(null);
        panelMain.add(btn11);

        btn12.setBorder(null);
        panelMain.add(btn12);

        btn13.setBorder(null);
        panelMain.add(btn13);

        btn14.setBorder(null);
        panelMain.add(btn14);

        btn15.setBorder(null);
        panelMain.add(btn15);

        btn16.setBorder(null);
        panelMain.add(btn16);

        btn17.setBorder(null);
        panelMain.add(btn17);

        btn18.setBorder(null);
        panelMain.add(btn18);

        btn19.setBorder(null);
        panelMain.add(btn19);

        btn20.setBorder(null);
        panelMain.add(btn20);

        btn21.setBorder(null);
        panelMain.add(btn21);

        btn22.setBorder(null);
        panelMain.add(btn22);

        btn23.setBorder(null);
        panelMain.add(btn23);

        btn24.setBorder(null);
        panelMain.add(btn24);

        btn25.setBorder(null);
        panelMain.add(btn25);

        btn26.setBorder(null);
        panelMain.add(btn26);

        btn27.setBorder(null);
        panelMain.add(btn27);

        btn28.setBorder(null);
        panelMain.add(btn28);

        btn29.setBorder(null);
        panelMain.add(btn29);

        btn30.setBorder(null);
        panelMain.add(btn30);

        btn31.setBorder(null);
        panelMain.add(btn31);

        btn32.setBorder(null);
        panelMain.add(btn32);

        btn33.setBorder(null);
        panelMain.add(btn33);

        btn34.setBorder(null);
        panelMain.add(btn34);

        btn35.setBorder(null);
        panelMain.add(btn35);

        btn36.setBorder(null);
        panelMain.add(btn36);

        btn37.setBorder(null);
        panelMain.add(btn37);

        btn38.setBorder(null);
        panelMain.add(btn38);

        btn39.setBorder(null);
        panelMain.add(btn39);

        btn40.setBorder(null);
        panelMain.add(btn40);

        btn41.setBorder(null);
        panelMain.add(btn41);

        btn42.setBorder(null);
        panelMain.add(btn42);

        btn43.setBorder(null);
        panelMain.add(btn43);

        btn44.setBorder(null);
        panelMain.add(btn44);

        btn45.setBorder(null);
        panelMain.add(btn45);

        btn46.setBorder(null);
        panelMain.add(btn46);

        btn47.setBorder(null);
        panelMain.add(btn47);

        btn48.setBorder(null);
        panelMain.add(btn48);

        btn49.setBorder(null);
        panelMain.add(btn49);

        btn50.setBorder(null);
        panelMain.add(btn50);

        btn51.setBorder(null);
        panelMain.add(btn51);

        btn52.setBorder(null);
        panelMain.add(btn52);

        btn53.setBorder(null);
        panelMain.add(btn53);

        btn54.setBorder(null);
        panelMain.add(btn54);

        btn55.setBorder(null);
        panelMain.add(btn55);

        btn56.setBorder(null);
        panelMain.add(btn56);

        btn57.setBorder(null);
        panelMain.add(btn57);

        btn58.setBorder(null);
        panelMain.add(btn58);

        btn59.setBorder(null);
        panelMain.add(btn59);

        btn60.setBorder(null);
        panelMain.add(btn60);

        btn61.setBorder(null);
        panelMain.add(btn61);

        btn62.setBorder(null);
        panelMain.add(btn62);

        btn63.setBorder(null);
        panelMain.add(btn63);

        btn64.setBorder(null);
        panelMain.add(btn64);

        btn65.setBorder(null);
        panelMain.add(btn65);

        btn66.setBorder(null);
        panelMain.add(btn66);

        btn67.setBorder(null);
        panelMain.add(btn67);

        btn68.setBorder(null);
        panelMain.add(btn68);

        btn69.setBorder(null);
        panelMain.add(btn69);

        btn70.setBorder(null);
        panelMain.add(btn70);

        btn71.setBorder(null);
        panelMain.add(btn71);

        btn72.setBorder(null);
        panelMain.add(btn72);

        btn73.setBorder(null);
        panelMain.add(btn73);

        btn74.setBorder(null);
        panelMain.add(btn74);

        btn75.setBorder(null);
        panelMain.add(btn75);

        btn76.setBorder(null);
        panelMain.add(btn76);

        btn77.setBorder(null);
        panelMain.add(btn77);

        btn78.setBorder(null);
        panelMain.add(btn78);

        btn79.setBorder(null);
        panelMain.add(btn79);

        btn80.setBorder(null);
        panelMain.add(btn80);

        btn81.setBorder(null);
        panelMain.add(btn81);

        btn82.setBorder(null);
        panelMain.add(btn82);

        btn83.setBorder(null);
        panelMain.add(btn83);

        btn84.setBorder(null);
        panelMain.add(btn84);

        btn85.setBorder(null);
        panelMain.add(btn85);

        btn86.setBorder(null);
        panelMain.add(btn86);

        btn87.setBorder(null);
        panelMain.add(btn87);

        btn88.setBorder(null);
        panelMain.add(btn88);

        btn89.setBorder(null);
        panelMain.add(btn89);

        btn90.setBorder(null);
        panelMain.add(btn90);

        btn91.setBorder(null);
        panelMain.add(btn91);

        btn92.setBorder(null);
        panelMain.add(btn92);

        btn93.setBorder(null);
        panelMain.add(btn93);

        btn94.setBorder(null);
        panelMain.add(btn94);

        btn95.setBorder(null);
        panelMain.add(btn95);

        btn96.setBorder(null);
        panelMain.add(btn96);

        btn97.setBorder(null);
        panelMain.add(btn97);

        btn98.setBorder(null);
        panelMain.add(btn98);

        btn99.setBorder(null);
        panelMain.add(btn99);

        btn100.setBorder(null);
        panelMain.add(btn100);

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

        jMenuItem35.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem35.setText("กำหนดรายการออกครัว");
        jMenuItem35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem35ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem35);

        jMenuItem36.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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

        jMenuItem13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem13.setText("แก้ไขข้อมูลเริ่มต้นระบบ");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem13);
        jMenu3.add(jSeparator1);

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(panelMain, javax.swing.GroupLayout.PREFERRED_SIZE, 1024, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelMain, javax.swing.GroupLayout.DEFAULT_SIZE, 694, Short.MAX_VALUE)
                .addContainerGap())
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
                    mysql.open(this.getClass());
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnZone1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZone1ActionPerformed
        loadZone("T");
    }//GEN-LAST:event_btnZone1ActionPerformed

    private void btnZone2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZone2ActionPerformed
        loadZone("A");
    }//GEN-LAST:event_btnZone2ActionPerformed

    private void btnZone3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZone3ActionPerformed
        loadZone("B");
    }//GEN-LAST:event_btnZone3ActionPerformed

    private void btnZone4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZone4ActionPerformed
        loadZone("C");
    }//GEN-LAST:event_btnZone4ActionPerformed

    private void btnZone5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZone5ActionPerformed
        loadZone("D");
    }//GEN-LAST:event_btnZone5ActionPerformed

    private void btnZone6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZone6ActionPerformed
        loadZone("E");
    }//GEN-LAST:event_btnZone6ActionPerformed

    private void btnZone7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZone7ActionPerformed
        loadZone("F");
    }//GEN-LAST:event_btnZone7ActionPerformed
    
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
        mysql.open(this.getClass());
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
    private javax.swing.JButton btn1;
    private javax.swing.JButton btn10;
    private javax.swing.JButton btn100;
    private javax.swing.JButton btn11;
    private javax.swing.JButton btn12;
    private javax.swing.JButton btn13;
    private javax.swing.JButton btn14;
    private javax.swing.JButton btn15;
    private javax.swing.JButton btn16;
    private javax.swing.JButton btn17;
    private javax.swing.JButton btn18;
    private javax.swing.JButton btn19;
    private javax.swing.JButton btn2;
    private javax.swing.JButton btn20;
    private javax.swing.JButton btn21;
    private javax.swing.JButton btn22;
    private javax.swing.JButton btn23;
    private javax.swing.JButton btn24;
    private javax.swing.JButton btn25;
    private javax.swing.JButton btn26;
    private javax.swing.JButton btn27;
    private javax.swing.JButton btn28;
    private javax.swing.JButton btn29;
    private javax.swing.JButton btn3;
    private javax.swing.JButton btn30;
    private javax.swing.JButton btn31;
    private javax.swing.JButton btn32;
    private javax.swing.JButton btn33;
    private javax.swing.JButton btn34;
    private javax.swing.JButton btn35;
    private javax.swing.JButton btn36;
    private javax.swing.JButton btn37;
    private javax.swing.JButton btn38;
    private javax.swing.JButton btn39;
    private javax.swing.JButton btn4;
    private javax.swing.JButton btn40;
    private javax.swing.JButton btn41;
    private javax.swing.JButton btn42;
    private javax.swing.JButton btn43;
    private javax.swing.JButton btn44;
    private javax.swing.JButton btn45;
    private javax.swing.JButton btn46;
    private javax.swing.JButton btn47;
    private javax.swing.JButton btn48;
    private javax.swing.JButton btn49;
    private javax.swing.JButton btn5;
    private javax.swing.JButton btn50;
    private javax.swing.JButton btn51;
    private javax.swing.JButton btn52;
    private javax.swing.JButton btn53;
    private javax.swing.JButton btn54;
    private javax.swing.JButton btn55;
    private javax.swing.JButton btn56;
    private javax.swing.JButton btn57;
    private javax.swing.JButton btn58;
    private javax.swing.JButton btn59;
    private javax.swing.JButton btn6;
    private javax.swing.JButton btn60;
    private javax.swing.JButton btn61;
    private javax.swing.JButton btn62;
    private javax.swing.JButton btn63;
    private javax.swing.JButton btn64;
    private javax.swing.JButton btn65;
    private javax.swing.JButton btn66;
    private javax.swing.JButton btn67;
    private javax.swing.JButton btn68;
    private javax.swing.JButton btn69;
    private javax.swing.JButton btn7;
    private javax.swing.JButton btn70;
    private javax.swing.JButton btn71;
    private javax.swing.JButton btn72;
    private javax.swing.JButton btn73;
    private javax.swing.JButton btn74;
    private javax.swing.JButton btn75;
    private javax.swing.JButton btn76;
    private javax.swing.JButton btn77;
    private javax.swing.JButton btn78;
    private javax.swing.JButton btn79;
    private javax.swing.JButton btn8;
    private javax.swing.JButton btn80;
    private javax.swing.JButton btn81;
    private javax.swing.JButton btn82;
    private javax.swing.JButton btn83;
    private javax.swing.JButton btn84;
    private javax.swing.JButton btn85;
    private javax.swing.JButton btn86;
    private javax.swing.JButton btn87;
    private javax.swing.JButton btn88;
    private javax.swing.JButton btn89;
    private javax.swing.JButton btn9;
    private javax.swing.JButton btn90;
    private javax.swing.JButton btn91;
    private javax.swing.JButton btn92;
    private javax.swing.JButton btn93;
    private javax.swing.JButton btn94;
    private javax.swing.JButton btn95;
    private javax.swing.JButton btn96;
    private javax.swing.JButton btn97;
    private javax.swing.JButton btn98;
    private javax.swing.JButton btn99;
    private javax.swing.JButton btnZone1;
    private javax.swing.JButton btnZone2;
    private javax.swing.JButton btnZone3;
    private javax.swing.JButton btnZone4;
    private javax.swing.JButton btnZone5;
    private javax.swing.JButton btnZone6;
    private javax.swing.JButton btnZone7;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPanel panelMain;
    // End of variables declaration//GEN-END:variables

    private final TableSetupControl tableSetupControl = new TableSetupControl();
    
    private void addButton() {
        try {
            resetButton();
            
            List<FloorPlanBean> listFloorPlan = tableSetupControl.getTableSetup(zoneSelected);
            for (FloorPlanBean bean : listFloorPlan) {
                String codeId = bean.getCodeId();
                JButton btn = findButton(panelMain, codeId);
                if (btn == null) {
                    continue;
                }
                if (bean.getLoginTime() != null) {
                    String r_time = bean.getRTime();
                    if (r_time == null) {
                        r_time = "";
                    } else {
                        r_time = "(" + r_time + ")";
                    }
                    
                    btn.setText(bean.getTableNo() + r_time);
                    if (bean.getCustomer() == 0 && bean.getItem() > 0) {
                        btn.setText(bean.getTableNo() + r_time);
                    }
                    if (bean.isIsActive()) {
                        btn.setOpaque(false);
                        btn.setText(bean.getTableNo() + "(" + bean.getCustomer() + ")");
                        btn.setForeground(Color.RED);
                        btn.setFont(fontB);
                        btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/table_1.png")));
                        btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                        btn.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
                        btn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
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
                    btn.setOpaque(false);
                    btn.setText(bean.getTableNo() + "(" + bean.getCustomer() + ")");
                    btn.setBackground(null);
                    btn.setFont(fontB);
                    btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/table_1.png")));
                    btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                    btn.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
                    btn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                }
                
            }
        } catch (Exception e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(FloorPlanDialog.class, "error", e);
        }
        
    }
    
    private void loadHeaderTab() {
        btnZone1.setBackground(Color.green);
        
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
        
        btnZone1.setText(floorTab[0]);
        btnZone1.setVisible(!floorTab[0].trim().equals(""));
        
        btnZone2.setText(floorTab[1]);
        btnZone2.setVisible(!floorTab[1].trim().equals(""));
        
        btnZone3.setText(floorTab[2]);
        btnZone3.setVisible(!floorTab[2].trim().equals(""));
        
        btnZone4.setText(floorTab[3]);
        btnZone4.setVisible(!floorTab[3].trim().equals(""));
        
        btnZone5.setText(floorTab[4]);
        btnZone5.setVisible(!floorTab[4].trim().equals(""));
        
        btnZone6.setText(floorTab[5]);
        btnZone6.setVisible(!floorTab[5].trim().equals(""));
        
        btnZone7.setText(floorTab[6]);
        btnZone7.setVisible(!floorTab[6].trim().equals(""));

        // load in first time
        initLoadButtons();
        loadZone(zoneSelected);
    }
    
    private JButton findButton(JPanel pnZone, String codeId) {
        for (int i = 0; i < pnZone.getComponentCount(); i++) {
            Component comp = pnZone.getComponent(i);
            if (comp instanceof JButton) {
                JButton btn = (JButton) pnZone.getComponent(i);
                if (btn.getName().equals(codeId)) {
                    return btn;
                }
            }
        }
        return null;
    }
    
    public void PrintCheckBillFromPDA() {
        try {
            //ฟังก์ชั่นสั่งเช็คบิลสำหรับ PDA
            System.out.println("PrintCheckBillFromPDA= loop");
            if (!POSHW.getTakeOrderChk().equals("Y")) {
                MySQLConnect mysql = new MySQLConnect();
                try {
                    mysql.open(this.getClass());
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
        } catch (Exception e) {
            AppLogUtil.log(FloorPlanDialog.class, "error", e);
        }
        
    }
    
    private void logout() {
        if (MSG.CONF(this, "ยืนยันการออกจากระบบการขาย (Logoff User) ? ")) {
            PublicVar.P_LineCount = 1;
            PublicVar.P_LogoffOK = false;
            
            if (UpdateLogout(PublicVar._RealUser)) {
                clearTemp();
                
                MySQLConnect mysql = new MySQLConnect();
                mysql.open(this.getClass());
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
                mysql.open(this.getClass());
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
        mysql.open(this.getClass());
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
        mysql.open(this.getClass());
        try {
            String sql = "select r_plucode from sp_temp_refund limit 1;";
            try (Statement stmt = mysql.getConnection().createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    //create temp table
                    TableFileControl tableFileControl = new TableFileControl();
                    tableFileControl.createNewTable(tableTemp);
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
            mysql.open(this.getClass());
            try {
                Statement stmt = mysql.getConnection().createStatement();
                //clear temp table
                stmt.executeUpdate("delete from sp_temp_refund");
                
                BalanceControl.updateProSerTable(tableTemp, null);
                Value.TableSelected = tableTemp;
                try {
                    MainSale mainSale = new MainSale(null, true, tableTemp);
                    mainSale.setVisible(true);
                } catch (Exception e) {
                    MSG.NOTICE(e.toString());
                    AppLogUtil.log(FloorPlanDialog.class, "error", e);
                }
                
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
    
    private void refund() {
        try {
            MySQLConnect mysql = new MySQLConnect();
            mysql.open(this.getClass());

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
                    if (posUser.getUserName()!=null) {
                        if (posUser.getSale2().equals("Y")) {
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
            
        } catch (Exception e) {
            MSG.NOTICE(e.toString());
            AppLogUtil.log(FloorPlanDialog.class, "error", e);
        }
       
    }
    
    private void initLoadButtons() {
        buttons[0] = btn1;
        buttons[1] = btn11;
        buttons[2] = btn21;
        buttons[3] = btn31;
        buttons[4] = btn41;
        buttons[5] = btn51;
        buttons[6] = btn61;
        buttons[7] = btn71;
        buttons[8] = btn81;
        buttons[9] = btn91;
        
        buttons[10] = btn2;
        buttons[11] = btn12;
        buttons[12] = btn22;
        buttons[13] = btn32;
        buttons[14] = btn42;
        buttons[15] = btn52;
        buttons[16] = btn62;
        buttons[17] = btn72;
        buttons[18] = btn82;
        buttons[19] = btn92;
        
        buttons[20] = btn3;
        buttons[21] = btn13;
        buttons[22] = btn23;
        buttons[23] = btn33;
        buttons[24] = btn43;
        buttons[25] = btn53;
        buttons[26] = btn63;
        buttons[27] = btn73;
        buttons[28] = btn83;
        buttons[29] = btn93;
        
        buttons[30] = btn4;
        buttons[31] = btn14;
        buttons[32] = btn24;
        buttons[33] = btn34;
        buttons[34] = btn44;
        buttons[35] = btn54;
        buttons[36] = btn64;
        buttons[37] = btn74;
        buttons[38] = btn84;
        buttons[39] = btn94;
        
        buttons[40] = btn5;
        buttons[41] = btn15;
        buttons[42] = btn25;
        buttons[43] = btn35;
        buttons[44] = btn45;
        buttons[45] = btn55;
        buttons[46] = btn65;
        buttons[47] = btn75;
        buttons[48] = btn85;
        buttons[49] = btn95;
        
        buttons[50] = btn6;
        buttons[51] = btn16;
        buttons[52] = btn26;
        buttons[53] = btn36;
        buttons[54] = btn46;
        buttons[55] = btn56;
        buttons[56] = btn66;
        buttons[57] = btn76;
        buttons[58] = btn86;
        buttons[59] = btn96;
        
        buttons[60] = btn7;
        buttons[61] = btn17;
        buttons[62] = btn27;
        buttons[63] = btn37;
        buttons[64] = btn47;
        buttons[65] = btn57;
        buttons[66] = btn67;
        buttons[67] = btn77;
        buttons[68] = btn87;
        buttons[69] = btn97;
        
        buttons[70] = btn8;
        buttons[71] = btn18;
        buttons[72] = btn28;
        buttons[73] = btn38;
        buttons[74] = btn48;
        buttons[75] = btn58;
        buttons[76] = btn68;
        buttons[77] = btn78;
        buttons[78] = btn88;
        buttons[79] = btn98;
        
        buttons[80] = btn9;
        buttons[81] = btn19;
        buttons[82] = btn29;
        buttons[83] = btn39;
        buttons[84] = btn49;
        buttons[85] = btn59;
        buttons[86] = btn69;
        buttons[87] = btn79;
        buttons[88] = btn89;
        buttons[89] = btn99;
        
        buttons[90] = btn10;
        buttons[91] = btn20;
        buttons[92] = btn30;
        buttons[93] = btn40;
        buttons[94] = btn50;
        buttons[95] = btn60;
        buttons[96] = btn70;
        buttons[97] = btn80;
        buttons[98] = btn90;
        buttons[99] = btn100;
        
        resetButton();
    }
    
    private void resetButton() {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = initButtonTable(buttons[i], i);

//            for(ActionListener listener: buttons[i].getActionListeners()){
//                buttons[i].removeActionListener(listener);
//            }
        }
    }
    
    private JButton initButtonTable(JButton button, int c) {
        button.setText("");
        button.setIcon(null);
        button.setBackground(null);
        button.setForeground(Color.BLACK);
        
        for (ActionListener listener : button.getActionListeners()) {
            button.removeActionListener(listener);
        }
        
        if (buttonStyle == 1) {
            button.setFont(fontA);
            button.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            button.addMouseListener(new FloorPlanDialog.MouseClickAction(button, c));
            button.addActionListener(new FloorPlanDialog.MouseFocusAction(button, c));
        } else {
            button.setPreferredSize(new Dimension(50, 50));
            button.setFocusPainted(false);
            button.setMargin(new Insets(0, 0, 0, 0));
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setOpaque(false);
            button.setFont(fontA);
            button.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            button.addMouseListener(new FloorPlanDialog.MouseClickAction(button, c));
            button.addActionListener(new FloorPlanDialog.MouseFocusAction(button, c));
        }
        
        return button;
    }
    
    private void loadZone(String zone) {
        this.zoneSelected = zone;
        
        btnZone1.setBackground("T".equals(zone) ? Color.green : null);
        btnZone2.setBackground("A".equals(zone) ? Color.green : null);
        btnZone3.setBackground("B".equals(zone) ? Color.green : null);
        btnZone4.setBackground("C".equals(zone) ? Color.green : null);
        btnZone5.setBackground("D".equals(zone) ? Color.green : null);
        btnZone6.setBackground("E".equals(zone) ? Color.green : null);
        btnZone7.setBackground("F".equals(zone) ? Color.green : null);

        // set button name
        for (int i = 0; i < buttons.length; i++) {
            String strCount;
            if ((i + 1) < 10) {
                strCount = "00" + (i + 1);
            } else if ((i + 1) < 100) {
                strCount = "0" + (i + 1);
            } else {
                strCount = "" + (i + 1);
            }
            buttons[i].setName(zone + strCount);
            buttons[i].setText("");
            buttons[i].setIcon(null);
            buttons[i].setBackground(null);
            buttons[i].setForeground(Color.BLACK);
        }
        // load data
        addButton();
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
                mysql.open(this.getClass());
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
                if (tableNo.contains("(")) {
                    tableNo = button.getText().substring(0, tableNo.indexOf("("));
                }
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
                            MySQLConnect mysql = new MySQLConnect();
                            mysql.open(this.getClass());
                            
                            String sql2 = "select code,name from employ where Code='" + login.getLoginPWD() + "' limit 1;";
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
                                    
                                    mysql.close();
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
        mysql.open(this.getClass());
        
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
        mysql.open(this.getClass());
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
