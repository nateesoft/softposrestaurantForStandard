package com.softpos.main.program;

import com.softpos.pos.core.controller.AppContext;
import com.softpos.pos.core.controller.BalanceControl;
import com.softpos.pos.core.controller.PrintToKicController;
import com.softpos.pos.core.model.BalanceBean;
import com.softpos.pos.core.model.PKicTranBean;
import database.ConfigFile;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.JFrame;
import printReport.PrintDriver;
import com.softpos.util.AppLogUtil;
import com.softpos.util.DateConvert;
import com.softpos.util.MSG;

/**
 *
 * @author Administrator
 */
public class UrgentFoodLoopCheck extends javax.swing.JFrame {

    /**
     * Creates new form UrgentFoodLoopCheck
     */
    public static boolean trickSound = false;
    public static String printerName = "";
    public static String printerConfigDriver = ConfigFile.getProperties("printdriver");
    public static String stationKicNo = ConfigFile.getProperties("stationKicNo");
    public static DateConvert dc = new DateConvert();
    double countTime = 1;
    double countTime1 = 1;

    public UrgentFoodLoopCheck() {
        initComponents();
        new Thread(new Runnable() {
            @Override
            public void run() {
                setState(JFrame.ICONIFIED);
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                jLabel1.setText("Version 2.1: 20/05/2568.18:36");

                setState(JFrame.ICONIFIED);

                printerName = ConfigFile.getProperties("printerName");
                jLabel4.setText("Printer Active = " + printerName);
                getDataFromKicTran();
                System.out.println("Into Method UrgentFoodLoopCheck() " + countTime + this.getClass());
                if (trickSound == true) {
                    for (int a = 0; a < 10; a++) {
                        playSound();
                    }
                    trickSound = false;
                }
            }
        }).start();
        
        if (printerConfigDriver.equals("true")) {
            new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    countTime1++;
                    printCheckItOut();
                    jLabel3.setText("UrgentFoodLoopCheck : Loop LEVEL " + countTime1);
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }
                    if (i == 9) {
                        i = 0;
                    }
                }
            }).start();
        }
    }

    public void getDataFromKicTran() {
        try {
            for (int i = 0; i < 10; i++) {
                countTime++;
                jLabel2.setText("getDataFromKictran : Loop LEVEL " + countTime);
                System.out.println("Into Method getDataFromKictran() " + countTime + this.getClass());

                try {
                    PrintToKicController controller = AppContext.getPrintToKicController();
                    PKicTranBean bean = controller.getUrgentFoodItem(stationKicNo);
                    if (bean != null) {
                        trickSound = true;
                        String tableNo = bean.getpTable();
                        String pdesc = bean.getR_UrgentFoodItemName();
                        String pcode = bean.getpCode();
                        String pindex = bean.getpIndex();

                        if (pdesc == null || pdesc.equals("") || pdesc.equals("null")) {
                            UrgentFoodDisplay display = new UrgentFoodDisplay(tableNo, "", "ตามทั้งโต๊ะ", "");
                            display.setVisible(true);
                        } else {
                            UrgentFoodDisplay display = new UrgentFoodDisplay(tableNo, pcode, pdesc, pindex);
                            display.setVisible(true);
                        }
                    } else {
                        trickSound = false;
                    }
                } catch (Exception e) {
                    AppLogUtil.log(UrgentFoodLoopCheck.class, "error", e);
                    MSG.NOTICE(this, e.getMessage());
                }

                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
                if (i > 7) {
                    i = 0;
                }
            }
        } catch (Exception e) {
            AppLogUtil.log(UrgentFoodLoopCheck.class, "error", e);
        }
    }

    public static void playSound() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.default");
                if (runnable != null) {
                    System.out.println("Into Method playSound() " + this.getClass());
                    runnable.run();
                }
            }
        }).start();
    }

    public void printCheckItOut() {
        try {
            System.out.println("Into Method printCheckItOut() ");
            PrintToKicController controller = AppContext.getPrintToKicController();
            countTime1++;
            jLabel2.setText("getDataFromKictran : Loop LEVEL " + countTime1);

            PKicTranBean kicBean = controller.getPendingCheckout(stationKicNo);
            if (kicBean != null) {
                System.out.println("Printing PrictCheckItOut");
                System.out.println(kicBean.getpCode());
                String pcode = kicBean.getpCode();
                String pIndex = kicBean.getpIndex();
                String pTable = kicBean.getpTable();
                BalanceControl bl = AppContext.getBalanceControl();
                BalanceBean bean = bl.getBalanceIndex(pTable, pIndex);
                if (bean != null) {
                    int pQty = kicBean.getpQty();
                    String pEtd = kicBean.getpEtd();
                    String textToPrint = "";
                    textToPrint += "โตีะ : " + pTable + "_";
                    if (pEtd.equals("E")) {
                        pEtd = "ทานในร้าน";
                    } else if (pEtd.equals("T")) {
                        pEtd = "ห่อกลับ";
                    } else if (pEtd.equals("D")) {
                        pEtd = "Delivery";
                    } else if (pEtd.equals("P")) {
                        pEtd = "Pinto";
                    } else if (pEtd.equals("W")) {
                        pEtd = "Whole Sale";
                    }
                    textToPrint += "colspan=3 align=center><font face=Angsana New size=5> " + "โต๊ะ : " + pTable + "_";
                    textToPrint += "colspan=3 align=center><font face=Angsana New size=3> " + "*** : " + pEtd + " ***_";
                    textToPrint += "colspan=3 align=left><font face=Angsana New size=3> " + "จำนวน : " + pQty + "_";
                    textToPrint += "colspan=3 align=left><font face=Angsana New size=3> " + pcode + " # " + bean.getR_PName() + "_";
                    textToPrint += "colspan=3 align=left><font face=Angsana New size=3> " + dc.dateGetToShow(dc.GetCurrentDate()) + " เวลา " + dc.GetCurrentTime() + "_";
                    PrintDriver printDriver = new PrintDriver();
                    String[] strs = textToPrint.split("_");
                    for (String data1 : strs) {
                        printDriver.addTextIFont(data1);
                        try {
                            Thread.sleep(100);
                        } catch (Exception e) {
                        }
                    }
                    try {
                        if (printerConfigDriver.equals("true")) {
                            printDriver.printHTMLKitChenByKictran(printerName);
                            jLabel4.setText("Printing : " + printerName + " / printCheckItOut " + bean.getR_Table() + " index =  " + bean.getR_Index());
                        }
                    } catch (Exception e) {
                        AppLogUtil.log(UrgentFoodLoopCheck.class, "error", e);
                        MSG.ERR(this, e.getMessage());
                    }
                    try {
                        controller.markCheckoutPrinted(pTable, pIndex, pcode);
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                } else {
                    try {
                        controller.markCheckoutPrintedNoBalance(pTable, pIndex, pcode);
                    } catch (Exception e) {
                        MSG.ERR(this, e.getMessage());
                        AppLogUtil.log(UrgentFoodLoopCheck.class, "error", e);
                    }
                }
            }
        } catch (Exception e) {
            MSG.ERR(this, e.getMessage());
        }
    }

    public void printUrgentLog() {
        new Thread(() -> {
            DateConvert dcLocal = new DateConvert();
            try {
                PrintToKicController controller = AppContext.getPrintToKicController();
                List<PKicTranBean> logList = controller.getUrgentClickLog(dcLocal.GetCurrentDate());
                String textToPrint = "";
                for (PKicTranBean entry : logList) {
                    textToPrint += "colspan=3 align=center><font face=Angsana New size=3> " + "โต๊ะ : " + entry.getpTable() + " # " + dcLocal.dateGetToShow(entry.getpDate()) + " เวลา " + entry.getpTime() + "_";
                }
                textToPrint += "colspan=3 align=left><font face=Angsana New size=3> " + "รวมทั้งสิ้นวันนี้ :  " + logList.size() + " ครั้ง " + "_";
                PrintDriver printDriver = new PrintDriver();
                String[] strs = textToPrint.split("_");
                for (String data1 : strs) {
                    printDriver.addTextIFont(data1);
                }
                try {
                    if (printerConfigDriver.equals("true")) {
                        printDriver.printHTMLKitChenByKictran(ConfigFile.getProperties("printerName"));
                    }
                } catch (Exception e) {
                    AppLogUtil.log(UrgentFoodLoopCheck.class, "error", e);
                }
            } catch (Exception e) {
                AppLogUtil.log(UrgentFoodLoopCheck.class, "error", e);
                MSG.ERR(new JFrame(), e.getMessage());
            }
        }).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/urgent.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setText("Exit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton3.setText("พิมพ์ รายงานการตามอาหาร");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setText("jLabel1");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("jLabel1");

        jLabel3.setForeground(new java.awt.Color(51, 51, 255));
        jLabel3.setText("jLabel1");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel4.setForeground(new java.awt.Color(255, 102, 0));
        jLabel4.setText("jLabel4");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 637, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(133, 133, 133)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 728, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (MSG.CONF(this, "ยืนยันการออกจากระบบการขาย (Logoff User) ? ")) {
            System.exit(0);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        printUrgentLog();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
