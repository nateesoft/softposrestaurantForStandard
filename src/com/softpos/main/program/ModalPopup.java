package com.softpos.main.program;

import com.softpos.pos.core.controller.ModalPopupController;
import com.softpos.pos.core.controller.PUtility;
import com.softpos.pos.core.controller.ThaiUtil;
import com.softpos.pos.core.model.MgrButtonSetupBean;
import com.softpos.pos.core.model.OptionSetBean;
import database.MySQLConnect;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import util.AppLogUtil;
import util.MSG;

// Panel Option ข้อความพิเศษ สำหรับสินค้าแต่ละรายการ
public class ModalPopup extends javax.swing.JDialog {

    private String PCode = "";
    private String MenuCode = "";
    private String PCodeSet = "";
    private String PName = "";
    private String PNameSet = "";
    private String Main = "";
    private String TableNo = "";
    private ModalPopupController modalControl = new ModalPopupController();

    public ModalPopup(java.awt.Dialog parent, boolean modal,
            String PCode, String PName, String TableNo, String Main, String MenuCode) {
        super(parent, modal);
        initComponents();

        this.PCode = PCode;
        this.PName = PName;
        this.Main = Main;
        this.TableNo = TableNo;
        this.MenuCode = MenuCode;

        if (!loadMenu2Pcs()) {
            loadOptionProduct();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel1.setLayout(new java.awt.GridLayout(5, 5));

        jButton1.setBackground(new java.awt.Color(255, 255, 153));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(jButton1);

        jButton2.setBackground(new java.awt.Color(255, 255, 153));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(jButton2);

        jButton3.setBackground(new java.awt.Color(255, 255, 153));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(jButton3);

        jButton4.setBackground(new java.awt.Color(255, 255, 153));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(jButton4);

        jButton5.setBackground(new java.awt.Color(255, 255, 153));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(jButton5);

        jButton6.setBackground(new java.awt.Color(255, 255, 153));
        jButton6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(jButton6);

        jButton7.setBackground(new java.awt.Color(255, 255, 153));
        jButton7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(jButton7);

        jButton8.setBackground(new java.awt.Color(255, 255, 153));
        jButton8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(jButton8);

        jButton9.setBackground(new java.awt.Color(255, 255, 153));
        jButton9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(jButton9);

        jButton10.setBackground(new java.awt.Color(255, 255, 153));
        jButton10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(jButton10);

        jButton11.setBackground(new java.awt.Color(255, 255, 153));
        jButton11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(jButton11);

        jButton12.setBackground(new java.awt.Color(255, 255, 153));
        jButton12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(jButton12);

        jButton13.setBackground(new java.awt.Color(255, 255, 153));
        jButton13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(jButton13);

        jButton14.setBackground(new java.awt.Color(255, 255, 153));
        jButton14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(jButton14);

        jButton15.setBackground(new java.awt.Color(255, 255, 153));
        jButton15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(jButton15);

        jButton16.setBackground(new java.awt.Color(255, 255, 153));
        jButton16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(jButton16);

        jButton17.setBackground(new java.awt.Color(255, 255, 153));
        jButton17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(jButton17);

        jButton18.setBackground(new java.awt.Color(255, 255, 153));
        jButton18.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(jButton18);

        jButton19.setBackground(new java.awt.Color(255, 255, 153));
        jButton19.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(jButton19);

        jButton20.setBackground(new java.awt.Color(255, 255, 153));
        jButton20.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(jButton20);

        jButton21.setBackground(new java.awt.Color(255, 255, 153));
        jButton21.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(jButton21);

        jButton22.setBackground(new java.awt.Color(255, 255, 153));
        jButton22.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(jButton22);

        jButton23.setBackground(new java.awt.Color(255, 255, 153));
        jButton23.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(jButton23);

        jButton24.setBackground(new java.awt.Color(255, 255, 153));
        jButton24.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(jButton24);

        jButton25.setBackground(new java.awt.Color(255, 255, 153));
        jButton25.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(jButton25);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 813, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

    private void loadOptionProduct() {
        JButton[] button = new JButton[25];
        button[0] = jButton1;
        button[1] = jButton2;
        button[2] = jButton3;
        button[3] = jButton4;
        button[4] = jButton5;
        button[5] = jButton6;
        button[6] = jButton7;
        button[7] = jButton8;
        button[8] = jButton9;
        button[9] = jButton10;
        button[10] = jButton11;
        button[11] = jButton12;
        button[12] = jButton13;
        button[13] = jButton14;
        button[14] = jButton15;
        button[15] = jButton16;
        button[16] = jButton17;
        button[17] = jButton18;
        button[18] = jButton19;
        button[19] = jButton20;
        button[20] = jButton21;
        button[21] = jButton22;
        button[22] = jButton23;
        button[23] = jButton24;
        button[24] = jButton25;

        List<OptionSetBean> listOptions = modalControl.getListOptionSet(PCode);
        int count = 0;
        for (OptionSetBean optionBean : listOptions) {
            button[count].setText("<html><center>" + optionBean.getOptionName() + "</center></html>");
            button[count].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton btn = (JButton) e.getSource();
                    String ProMain = btn.getText().replace("<html>", "").replace("<center>", "").replace("</center>", "").replace("</html>", "");

                    updateTempmenusetProduct("", PCode, PName, ProMain, Main);
                    loadProductSideDish();
                }

                private void updateTempmenusetProduct(String Index, String PCode, String PName, String ProMain, String Main) {
                    MySQLConnect mysql = new MySQLConnect();
                    mysql.open(this.getClass());
                    try {
                        String sqll = "select pcode, pdesc from mgrbuttonsetup where pcode = '" + PCode + "'";
                        String pstock = PUtility.GetStkCode();
                        try (Statement stmt1 = mysql.getConnection().createStatement(); ResultSet rss = stmt1.executeQuery(sqll)) {
                            if (rss.next()) {
                                String pcode = rss.getString("pcode");
                                String tempset = "INSERT INTO tempset (PTableNo, PIndex, PCode, PDesc, "
                                        + "PPostStock,PProTry, POption, PTime) "
                                        + "VALUES ('" + TableNo + "', '" + Index + "', '" + pcode + "', "
                                        + "'" + ThaiUtil.Unicode2ASCII(PName) + "', '" + pstock + "','" + Main + "', "
                                        + "'" + ThaiUtil.Unicode2ASCII(ProMain) + "', CURTIME())";
                                mysql.getConnection().createStatement().executeUpdate(tempset);
                            } else {
                                String pcode = PCode;
                                String tempset = "INSERT INTO tempset (PTableNo, PIndex, PCode, PDesc, "
                                        + "PPostStock,PProTry, POption, PTime) "
                                        + "VALUES ('" + TableNo + "', '" + Index + "', '" + pcode + "', "
                                        + "'" + ThaiUtil.Unicode2ASCII(PName) + "', '" + pstock + "','', "
                                        + "'" + ThaiUtil.Unicode2ASCII(ProMain) + "', CURTIME())";
                                mysql.getConnection().createStatement().executeUpdate(tempset);
                            }
                        }
                    } catch (SQLException e) {
                        MSG.ERR(null, e.getMessage());
                        AppLogUtil.log(ModalPopup.class, "error", e);
                    } finally {
                        mysql.close();
                    }
                }
            });
            count++;
        }

        if (count == 0) {
            loadProductSideDish();
        }
    }

    private void loadProductSideDish() {
        JButton[] button = new JButton[25];
        button[0] = jButton1;
        button[1] = jButton2;
        button[2] = jButton3;
        button[3] = jButton4;
        button[4] = jButton5;
        button[5] = jButton6;
        button[6] = jButton7;
        button[7] = jButton8;
        button[8] = jButton9;
        button[9] = jButton10;
        button[10] = jButton11;
        button[11] = jButton12;
        button[12] = jButton13;
        button[13] = jButton14;
        button[14] = jButton15;
        button[15] = jButton16;
        button[16] = jButton17;
        button[17] = jButton18;
        button[18] = jButton19;
        button[19] = jButton20;
        button[20] = jButton21;
        button[21] = jButton22;
        button[22] = jButton23;
        button[23] = jButton24;
        button[24] = jButton25;

        button[24].setText("SKIP");
        button[24].setBackground(Color.GREEN);
        button[24].addActionListener((ActionEvent e) -> {
            loadProductExtra();
        });

        for (JButton currentButton : button) {
            for (ActionListener al : currentButton.getActionListeners()) {
                currentButton.removeActionListener(al);
            }
        }

        for (JButton button1 : button) {
            button1.setText("");
            button1.setBackground(Color.PINK);
        }

        List<MgrButtonSetupBean> listMgrSetup = modalControl.getListMgrButtonSetupByPCode(PCode, "sd");
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());
        int count = 0;
        for (MgrButtonSetupBean mgrBean : listMgrSetup) {
            String PNameSide = mgrBean.getSd_pdesc();
            this.PNameSet = PNameSide;
            String PCodeSide = mgrBean.getSd_pcode();
            this.PCodeSet = PCodeSide;
            button[count].setName(mgrBean.getSd_pcode());
            button[count].setText("<html><center>" + PNameSet + "</center></html>");
            button[count].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton btn = (JButton) e.getSource();
                    String ProFree = btn.getText().replace("<html>", "").replace("<center>", "").replace("</center>", "").replace("</html>", "");
                    updateTempmenusetSideDish("", btn.getName(), ProFree, "", "free");
                    loadProductExtra();
                }

                private void updateTempmenusetSideDish(String Index, String PCodeSet, String PNameSet, String ProFree, String free) {
                    String pstock = PUtility.GetStkCode();

                    MySQLConnect mysql = new MySQLConnect();
                    mysql.open(this.getClass());
                    try {
                        String sql = "select sd_pcode, sd_pdesc from mgrbuttonsetup where pcode = '" + PCode + "' limit 1";
                        try (Statement stmt1 = mysql.getConnection().createStatement(); ResultSet rs = stmt1.executeQuery(sql)) {
                            if (rs.next()) {
                                String tempset = "INSERT INTO tempset "
                                        + "(PTableNo, PIndex, PCode, PDesc, "
                                        + "PPostStock,PProTry, POption, PTime) "
                                        + "VALUES ('" + TableNo + "', '" + Index + "', '" + PCodeSet + "', "
                                        + "'" + ThaiUtil.Unicode2ASCII(PNameSet) + "', '" + pstock + "','" + free + "', "
                                        + "'" + ThaiUtil.Unicode2ASCII(ProFree) + "', CURTIME())";
                                mysql.getConnection().createStatement().executeUpdate(tempset);
                            }
                        }
                    } catch (SQLException e) {
                        MSG.ERR(null, e.getMessage());
                        AppLogUtil.log(ModalPopup.class, "error", e);
                    } finally {
                        mysql.close();
                    }
                }
            });
            count++;
        }

        if (count == 0) {
            loadProductExtra();
        }
    }

    private void loadProductExtra() {
        JButton[] button = new JButton[25];
        button[0] = jButton1;
        button[1] = jButton2;
        button[2] = jButton3;
        button[3] = jButton4;
        button[4] = jButton5;
        button[5] = jButton6;
        button[6] = jButton7;
        button[7] = jButton8;
        button[8] = jButton9;
        button[9] = jButton10;
        button[10] = jButton11;
        button[11] = jButton12;
        button[12] = jButton13;
        button[13] = jButton14;
        button[14] = jButton15;
        button[15] = jButton16;
        button[16] = jButton17;
        button[17] = jButton18;
        button[18] = jButton19;
        button[19] = jButton20;
        button[20] = jButton21;
        button[21] = jButton22;
        button[22] = jButton23;
        button[23] = jButton24;
        button[24] = jButton25;

        for (JButton currentButton : button) {
            for (ActionListener al : currentButton.getActionListeners()) {
                currentButton.removeActionListener(al);
            }
        }

        for (JButton button1 : button) {
            button1.setText("");
            button1.setBackground(new Color(153, 255, 204));
        }

        button[24].setText("SKIP");
        button[24].setBackground(Color.GREEN);
        button[24].addActionListener((ActionEvent e) -> {
            dispose();
        });

        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());
        List<MgrButtonSetupBean> listMgrSetup = modalControl.getListMgrButtonSetupByPCode(PCode, "ex");

        int count = 0;
        for (MgrButtonSetupBean mgrBean : listMgrSetup) {
            String pcode = mgrBean.getEx_pcode();
            this.PCode = pcode;
            String pname = mgrBean.getEx_pdesc();
            this.PName = pname;
            button[count].setText("<html><center>" + pname + "</center></html>");
            button[count].setName(mgrBean.getEx_pcode());
            button[count].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton btn = (JButton) e.getSource();
                    String btnPCode = btn.getName();
                    String PDesc = btn.getText().replace("<html>", "").replace("<center>", "").replace("</center>", "").replace("</html>", "");
                    if (showPopupOption(btnPCode)) {
                        updateTempmenusetExtra("", btnPCode, PDesc, "", "extra");
                        loadProductExtraOption(btnPCode);
                    } else {
                        updateTempmenusetExtra("", btnPCode, PDesc, "", "extra");
                        if (!checkLimiExtra(TableNo)) {
                            dispose();
                        } else {
                            btn.setIcon(new ImageIcon(getClass().getResource("/images/button_ok.png")));
                            jButton25.setText("ยืนยัน (OK)");
                        }
                    }
                }

                private boolean checkLimiExtra(String TableNo) {
                    boolean checkExtra = false;
                    MySQLConnect mysql = new MySQLConnect();
                    mysql.open(this.getClass());
                    try {
                        String sql = "select PCode from tempset where PTableNo='" + TableNo + "' and PProTry='main' limit 1";
                        try (Statement stmt = mysql.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                            if (rs.next()) {
                                String PCode = rs.getString("PCode");
                                String sql2 = "select check_extra "
                                        + "from mgrbuttonsetup "
                                        + "where pcode='" + PCode + "' "
                                        + "and check_extra='Y'";
                                try (Statement stmt1 = mysql.getConnection().createStatement(); ResultSet rs2 = stmt1.executeQuery(sql2)) {
                                    if (rs2.next()) {
                                        checkExtra = true;
                                    }
                                }
                            }
                        }
                    } catch (SQLException e) {
                        MSG.ERR(e.getMessage());
                        AppLogUtil.log(ModalPopup.class, "error", e);
                    } finally {
                        mysql.close();
                    }
                    return checkExtra;
                }
            });
            count++;
        }

        if (count == 0) {
            dispose();
        }

    }

    private void loadProductExtraOption(String PCodeItem) {
        JButton[] button = new JButton[25];
        button[0] = jButton1;
        button[1] = jButton2;
        button[2] = jButton3;
        button[3] = jButton4;
        button[4] = jButton5;
        button[5] = jButton6;
        button[6] = jButton7;
        button[7] = jButton8;
        button[8] = jButton9;
        button[9] = jButton10;
        button[10] = jButton11;
        button[11] = jButton12;
        button[12] = jButton13;
        button[13] = jButton14;
        button[14] = jButton15;
        button[15] = jButton16;
        button[16] = jButton17;
        button[17] = jButton18;
        button[18] = jButton19;
        button[19] = jButton20;
        button[20] = jButton21;
        button[21] = jButton22;
        button[22] = jButton23;
        button[23] = jButton24;
        button[24] = jButton25;

        for (JButton currentButton : button) {
            for (ActionListener al : currentButton.getActionListeners()) {
                currentButton.removeActionListener(al);
            }
        }

        for (JButton button1 : button) {
            button1.setText("");
            button1.setBackground(new Color(255, 255, 153));
            button1.setIcon(null);
        }

        List<OptionSetBean> listOptions = modalControl.getListOptionSet(PCodeItem);
        int count = 0;
        for (OptionSetBean optionBean : listOptions) {
            String POpName = optionBean.getOptionName();
            String PNameEx = optionBean.getPdesc();
            this.PNameSet = PNameEx;
            String PCodeEx = optionBean.getPcode();
            this.PCodeSet = PCodeEx;
            button[count].setText("<html><center>" + POpName + "</center></html>");
            button[count].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton btn1 = (JButton) e.getSource();
                    String Extra = btn1.getText().replace("<html>", "").replace("<center>", "").replace("</center>", "").replace("</html>", "");
                    UpdateTempmenusetExtraOption(PCodeSet, Extra);
                    dispose();
                }

                private void UpdateTempmenusetExtraOption(String PCode, String POption) {
                    MySQLConnect mysql = new MySQLConnect();
                    mysql.open(this.getClass());
                    try {
                        String sql = "update tempset set "
                                + "POption='" + ThaiUtil.Unicode2ASCII(POption) + "' "
                                + "where PCode='" + PCode + "'";
                        try (Statement stmt = mysql.getConnection().createStatement()) {
                            stmt.executeUpdate(sql);
                        }
                    } catch (SQLException e) {
                        MSG.ERR(e.getMessage());
                        AppLogUtil.log(ModalPopup.class, "error", e);
                    } finally {
                        mysql.close();
                    }
                }
            });
            count++;
        }
    }

    private boolean showPopupOption(String pCodeItem) {
        boolean isCheck = false;
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());
        try {
            String sql = "select pcode from optionset where pcode ='" + pCodeItem + "' limit 1";
            try (Statement stmt = mysql.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    isCheck = true;
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(ModalPopup.class, "error", e);
        } finally {
            mysql.close();
        }

        return isCheck;
    }

    private void updateTempmenusetExtra(String Index, String PCode, String PName, String Option, String TryName) {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());
        try {
            String sql = "select ex_pcode , ex_pdesc from mgrbuttonsetup "
                    + "where ex_pdesc = '" + ThaiUtil.Unicode2ASCII(PName) + "' limit 1";
            try (Statement stmt = mysql.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    String pstock = PUtility.GetStkCode();
                    String tempset = "INSERT INTO tempset "
                            + "(PTableNo, PIndex, PCode, PDesc, "
                            + "PPostStock,PProTry, POption, PTime) "
                            + "VALUES ('" + TableNo + "', '" + Index + "', '" + PCode + "', "
                            + "'" + ThaiUtil.Unicode2ASCII(PName) + "', '" + pstock + "','" + TryName + "', "
                            + "'" + ThaiUtil.Unicode2ASCII(Option) + "', CURTIME())";
                    try (Statement stmt1 = mysql.getConnection().createStatement()) {
                        stmt1.executeUpdate(tempset);
                    }
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(ModalPopup.class, "error", e);
        } finally {
            mysql.close();
        }

    }

    private boolean loadMenu2Pcs() {
        boolean show = false;
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());
        try {
            String sql = "select qty from mgrbuttonsetup "
                    + "where pcode='" + PCode + "' and check_qty='Y' limit 1";
            try (Statement stmt = mysql.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    show = true;
                }
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(ModalPopup.class, "error", e);
        } finally {
            mysql.close();
        }

        if (!show) {
            return false;
        }

        JButton[] button = new JButton[25];
        button[0] = jButton1;
        button[1] = jButton2;
        button[2] = jButton3;
        button[3] = jButton4;
        button[4] = jButton5;
        button[5] = jButton6;
        button[6] = jButton7;
        button[7] = jButton8;
        button[8] = jButton9;
        button[9] = jButton10;
        button[10] = jButton11;
        button[11] = jButton12;
        button[12] = jButton13;
        button[13] = jButton14;
        button[14] = jButton15;
        button[15] = jButton16;
        button[16] = jButton17;
        button[17] = jButton18;
        button[18] = jButton19;
        button[19] = jButton20;
        button[20] = jButton21;
        button[21] = jButton22;
        button[22] = jButton23;
        button[23] = jButton24;
        button[24] = jButton25;

        MySQLConnect mysql2 = new MySQLConnect();
        mysql2.open(this.getClass());
        try {
            String menuSub = "";
            if (MenuCode.length() > 5) {
                menuSub = MenuCode.substring(0, 5);
            }
            String sql = "select PCode, MenuShowText from soft_menusetup "
                    + "where menucode like '" + menuSub + "%' and menutype='1'";
            Statement stmt = mysql2.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int count = 0;
            while (rs.next()) {
                if(count == button.length) {
                    break;
                }
                button[count].setName(rs.getString("PCode"));
                button[count].setText("<html><center>" + ThaiUtil.ASCII2Unicode(rs.getString("MenuShowText")) + "</center></html>");
                button[count].addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton btn = (JButton) e.getSource();
                        String ProMain = btn.getText().replace("<html>", "").replace("<center>", "").replace("</center>", "").replace("</html>", "");
                        String PCode = btn.getName();

                        UpdateTempmenusetProduct("", PCode, PName, ProMain, "qty");
                        loadProductSideDish();
                    }

                    private void UpdateTempmenusetProduct(String Index, String PCode, String PName, String ProMain, String Main) {
                        MySQLConnect mysql = new MySQLConnect();
                        mysql.open(this.getClass());
                        try {
                            String sql = "select pcode, pdesc from mgrbuttonsetup where pcode = '" + PCode + "' limit 1";
                            try (Statement stmt = mysql.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                                if (rs.next()) {
                                    String pcode = rs.getString("pcode");
                                    String pstock = PUtility.GetStkCode();
                                    String tempset = "INSERT INTO tempset "
                                            + "(PTableNo, PIndex, PCode, PDesc, "
                                            + "PPostStock,PProTry, POption, PTime) "
                                            + "VALUES ('" + TableNo + "', '" + Index + "', '" + pcode + "', "
                                            + "'" + ThaiUtil.Unicode2ASCII(ProMain) + "', '" + pstock + "','" + Main + "', "
                                            + "'', CURTIME())";
                                    try (Statement stmt1 = mysql.getConnection().createStatement()) {
                                        stmt1.executeUpdate(tempset);
                                    }
                                }
                                
                            }
                        } catch (SQLException e) {
                            MSG.ERR(null, e.getMessage());
                            AppLogUtil.log(ModalPopup.class, "error", e);
                        } finally {
                            mysql.close();
                        }
                    }
                });
                count++;
            }

            if (count == 0) {
                loadProductSideDish();
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(ModalPopup.class, "error", e);
        } finally {
            mysql2.close();
        }

        return show;
    }
}
