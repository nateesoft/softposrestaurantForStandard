package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.BalanceBean;
import com.softpos.pos.core.model.MemberBean;
import com.softpos.util.ThaiUtil;
import com.softpos.connection.database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.softpos.util.AppLogUtil;

public class MemberControl {

    MemberBean MemberBean = new MemberBean();
    private final MySQLConnect mysqlConnect = new MySQLConnect();

    public void updateMemberDiscount(String table, MemberBean memberBean) {
        String strDisc = "";
        if (memberBean != null) {
            if (!memberBean.getMember_DiscountRate().equals("")) {
                strDisc = memberBean.getMember_DiscountRate();
            }
        }

        
        mysqlConnect.open(this.getClass());
        try {
            String sql = "select sum(R_PrSubAmt) as MemDiscount "
                    + "from balance where r_table='" + table + "' "
                    + "order by R_Index;";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String upd = "update tablefile set "
                        + "MemDiscAmt='" + rs.getDouble("MemDiscount") + "',"
                        + "MemDisc='" + strDisc + "' "
                        + "where tcode='" + table + "'";
                Statement stmt1 = mysqlConnect.getConnection().createStatement();
                stmt1.executeUpdate(upd);
                stmt1.close();
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            AppLogUtil.log(MemberControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
    }

    public void updateMemberAllBalance(String table, MemberBean memberBean) {

        mysqlConnect.open(this.getClass());
        try {
            /*
             R_PrSubType = -M
             R_PrSubCode = MEM
             R_PrSubQuan = 1
             R_PrSubDisc = 5 (เปอร์เซ็นต์การลด)
             R_PrSubBath = 0
             R_PrSubAmt = 4.75 (5% ของราคาสินค้า)
             R_QuanCanDisc = 0
             */
            String sql = "select * from balance "
                    + "where R_Table='" + table + "' "
                    + "and R_Discount='Y' "
                    + "and R_Void<>'V' "
                    + "order by R_Index;";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                BalanceBean balance = new BalanceBean();

                // คำนวณหาว่าลดเท่าไหร่
                if (memberBean != null) {
                    String[] subPercent = memberBean.getMember_DiscountRate().split("/");
                    int Percent = 0;

                    if (subPercent.length == 3) {
                        String R_Normal = rs.getString("R_Normal");
                        if (R_Normal == null) {
                            R_Normal = "";
                        }
                        if (R_Normal.equals("N")) {
                            Percent = Integer.parseInt(subPercent[0].replace("  ", "00").trim());
                        } else if (R_Normal.equals("C")) {
                            Percent = Integer.parseInt(subPercent[1].replace("  ", "00").trim());
                        } else if (R_Normal.equals("S")) {
                            Percent = Integer.parseInt(subPercent[2].replace("  ", "00").trim());
                        }
                    }

                    balance.setR_PrSubAmt((rs.getDouble("R_Total") * Percent) / 100);
                    balance.setR_QuanCanDisc(0);// if member default 0

                    String sqlUpd = "update balance set "
                            + "R_PrSubType='-M',"
                            + "R_PrSubCode='MEM',"
                            + "R_PrSubQuan='" + rs.getDouble("R_Quan") + "',"
                            + "R_PrSubDisc='" + Percent + "',"
                            + "R_PrSubBath='0',"
                            + "R_PrSubAmt='" + ((rs.getDouble("R_Total") * Percent) / 100) + "',"
                            + "R_QuanCanDisc='0' "
                            + "where R_Index='" + rs.getString("R_Index") + "' "
                            + "and R_Table='" + rs.getString("R_Table") + "'";
                    Statement stmt1 = mysqlConnect.getConnection().createStatement();
                    stmt1.executeUpdate(sqlUpd);
                    stmt1.close();
                }
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            AppLogUtil.log(MemberControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
    }

    /** Returns all rows from memmaster. Each element: {code, fullName, homeTel, "", mobile, expiredDate, fax} */
    public List<Object[]> findAllMembers(String dbMember) {
        List<Object[]> list = new ArrayList<>();
        mysqlConnect.open(MemberControl.class);
        try {
            String sql = "select * from " + dbMember + ".memmaster order by Member_Code";
            try (Statement stmt = mysqlConnect.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    list.add(new Object[]{
                        rs.getString("Member_Code"),
                        ThaiUtil.ASCII2Unicode(rs.getString("Member_TitleNameThai") + rs.getString("Member_NameThai") + " " + rs.getString("Member_SurnameThai")),
                        rs.getString("Member_HomeTel"),
                        "",
                        rs.getString("Member_Mobile"),
                        rs.getString("Member_ExpiredDate"),
                        rs.getString("Member_Fax")
                    });
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(MemberControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(MemberControl.class);
        }
        return list;
    }

    public List<Object[]> searchMembers(String dbMember, String memCode, String memName, String memTel) {
        List<Object[]> list = new ArrayList<>();
        String sql = "";
        if (!memCode.equals("")) {
            sql = "select * from " + dbMember + ".memmaster where Member_Code like '%" + memCode + "%' order by Member_Code";
        } else if (!memName.equals("")) {
            sql = "select * from " + dbMember + ".memmaster where Member_NameThai like '%" + ThaiUtil.Unicode2ASCII(memName) + "%' order by Member_Code";
        } else if (!memTel.equals("")) {
            sql = "select * from " + dbMember + ".memmaster where Member_Mobile like '%" + ThaiUtil.Unicode2ASCII(memTel) + "%' order by Member_Code";
        }
        if (sql.isEmpty()) return list;
        mysqlConnect.open(MemberControl.class);
        try {
            try (Statement stmt = mysqlConnect.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    list.add(new Object[]{
                        rs.getString("Member_Code"),
                        ThaiUtil.ASCII2Unicode(rs.getString("Member_TitleNameThai") + rs.getString("Member_NameThai") + " " + rs.getString("Member_SurnameThai")),
                        rs.getString("Member_HomeTel"),
                        "",
                        rs.getString("Member_Mobile"),
                        rs.getString("Member_ExpiredDate"),
                        rs.getString("member_fax")
                    });
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(MemberControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(MemberControl.class);
        }
        return list;
    }

    public void clearMemberDiscount(String tableNo) {
        mysqlConnect.open(MemberControl.class);
        try {
            String sql = "update tablefile set memdisc='',nettotal= nettotal+memdiscamt,"
                    + " memdiscamt='0',memname='',memcode='' where tcode='" + tableNo + "'";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
            }
            String sqlUpdate = "update balance "
                    + "set r_prsubtype='',r_prsubcode='',"
                    + "r_prsubquan='0',r_prsubdisc='0',r_prsubamt='0'"
                    + " where r_table='" + tableNo + "'";
            mysqlConnect.executeUpdate(sqlUpdate);
        } catch (SQLException e) {
            AppLogUtil.log(MemberControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(MemberControl.class);
        }
    }

    /**
     * Returns all members from {db_member}.memmaster ordered by m_name.
     * Each element: {m_code, m_name, m_tel, m_mobile, m_office, m_end (java.sql.Date), m_brid (java.sql.Date)}
     */
    public List<Object[]> getAllMembers(String dbMember) {
        List<Object[]> list = new ArrayList<>();

        mysqlConnect.open(this.getClass());
        try {
            String sql = "select * from " + dbMember + ".memmaster order by m_name";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getString("m_code"),
                    rs.getString("m_name"),
                    rs.getString("m_tel"),
                    rs.getString("m_mobile"),
                    rs.getString("m_office"),
                    rs.getDate("m_end"),
                    rs.getDate("m_brid")
                });
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            AppLogUtil.log(MemberControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
        return list;
    }

    /**
     * Finds members from {db_member}.memmaster where m_code = code, ordered by m_name.
     * Each element: {m_code, m_name, m_tel, m_mobile, m_office, m_end (java.sql.Date), m_brid (java.sql.Date)}
     */
    public List<Object[]> findMemberByCode(String dbMember, String code) {
        List<Object[]> list = new ArrayList<>();

        mysqlConnect.open(this.getClass());
        try {
            String sql = "select * from " + dbMember + ".memmaster where m_code = '" + code + "' order by m_name";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getString("m_code"),
                    rs.getString("m_name"),
                    rs.getString("m_tel"),
                    rs.getString("m_mobile"),
                    rs.getString("m_office"),
                    rs.getDate("m_end"),
                    rs.getDate("m_brid")
                });
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            AppLogUtil.log(MemberControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
        return list;
    }

    /**
     * Finds members from {db_member}.memmaster where m_name LIKE %name%, ordered by m_name.
     * Each element: {m_code, m_name, m_tel, m_mobile, m_office, m_end (java.sql.Date), m_brid (java.sql.Date)}
     */
    public List<Object[]> findMemberByName(String dbMember, String name) {
        List<Object[]> list = new ArrayList<>();
        String pattern = "%" + name + "%";

        mysqlConnect.open(this.getClass());
        try {
            String sql = "select * from " + dbMember + ".memmaster where m_name like '" + pattern + "' order by m_name";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getString("m_code"),
                    rs.getString("m_name"),
                    rs.getString("m_tel"),
                    rs.getString("m_mobile"),
                    rs.getString("m_office"),
                    rs.getDate("m_end"),
                    rs.getDate("m_brid")
                });
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            AppLogUtil.log(MemberControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
        return list;
    }

    /**
     * Finds members from {db_member}.memmaster where m_tel/m_mobile/m_office LIKE %tel%, ordered by m_name.
     * Each element: {m_code, m_name, m_tel, m_mobile, m_office, m_end (java.sql.Date), m_brid (java.sql.Date)}
     */
    public List<Object[]> findMemberByTel(String dbMember, String tel) {
        List<Object[]> list = new ArrayList<>();
        String pattern = "%" + tel + "%";

        mysqlConnect.open(this.getClass());
        try {
            String sql = "select * from " + dbMember + ".memmaster "
                    + "where (m_tel like '" + pattern + "') or (m_mobile like '" + pattern + "') "
                    + "or (m_office like '" + pattern + "') order by m_name";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getString("m_code"),
                    rs.getString("m_name"),
                    rs.getString("m_tel"),
                    rs.getString("m_mobile"),
                    rs.getString("m_office"),
                    rs.getDate("m_end"),
                    rs.getDate("m_brid")
                });
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            AppLogUtil.log(MemberControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
        return list;
    }

    /**
     * Returns member transaction report: mtran LEFT JOIN {db_member}.memmaster
     * filtered by m_code range and m_date range, ordered by m_code, m_date, m_billno.
     * Each element: {m_code, m_name, m_date (java.sql.Date), m_billno, m_netamt, m_disc, m_score, m_sum}
     */
    public List<Object[]> getMemberTransactionReport(String dbMember, String code1, String code2, String date1, String date2) {
        List<Object[]> list = new ArrayList<>();

        mysqlConnect.open(this.getClass());
        try {
            String sql = "select * from mtran left join " + dbMember + ".memmaster on mtran.m_code=memmaster.m_code "
                    + "where (mtran.m_code>='" + code1 + "') and (mtran.m_code<='" + code2 + "') "
                    + "and (m_date>='" + date1 + "') and (m_date<='" + date2 + "') "
                    + "order by mtran.m_code,m_date,m_billno";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getString("m_code"),
                    rs.getString("m_name"),
                    rs.getDate("m_date"),
                    rs.getString("m_billno"),
                    rs.getDouble("m_netamt"),
                    rs.getDouble("m_disc"),
                    rs.getDouble("m_score"),
                    rs.getDouble("m_sum")
                });
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            AppLogUtil.log(MemberControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
        return list;
    }

    public void updateTableFileMember(String tableNo, String memCode, String memName, boolean add) {
        mysqlConnect.open(MemberControl.class);
        try {
            String sql;
            if (add) {
                sql = "Update tablefile set memcode='" + memCode + "',memname='" + ThaiUtil.Unicode2ASCII(memName) + "' where tcode='" + tableNo + "'";
            } else {
                sql = "Update tablefile set memcode='',memname='',memdisc='',memdiscamt='0',nettotal=tamount where tcode='" + tableNo + "'";
            }
            mysqlConnect.executeUpdate(sql);
        } catch (Exception e) {
            AppLogUtil.log(MemberControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(MemberControl.class);
        }
    }

    public void updateMemVIPAllBalance(String table, String discountRate) {

        mysqlConnect.open(this.getClass());
        try {
            /*
             R_PrSubType = -M
             R_PrSubCode = MEM
             R_PrSubQuan = 1
             R_PrSubDisc = 5 (เปอร์เซ็นต์การลด)
             R_PrSubBath = 0
             R_PrSubAmt = 4.75 (5% ของราคาสินค้า)
             R_QuanCanDisc = 0
             */
            String sql = "select * from balance "
                    + "where R_Table='" + table + "' "
                    + "and R_Discount='Y' "
                    + "order by R_Index;";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                BalanceBean balance = new BalanceBean();

                // คำนวณหาว่าลดเท่าไหร่
                String[] subPercent = discountRate.split("/");
                int Percent = 0;

                if (subPercent.length == 3) {
                    String R_Normal = rs.getString("R_Normal");
                    if (R_Normal == null) {
                        R_Normal = "";
                    }
                    if (R_Normal.equals("N")) {
                        Percent = Integer.parseInt(subPercent[0].trim());
                    } else if (R_Normal.equals("C")) {
                        Percent = Integer.parseInt(subPercent[1].trim());
                    } else if (R_Normal.equals("S")) {
                        Percent = Integer.parseInt(subPercent[2].trim());
                    }
                }
                if (subPercent.length == 3) {
                    String R_Normal = rs.getString("R_Normal");
                    if (R_Normal == null) {
                        R_Normal = "";
                    }
                    if (R_Normal.equals("N")) {
                        Percent = Integer.parseInt(subPercent[0].trim());
                    } else if (R_Normal.equals("C")) {
                        Percent = Integer.parseInt(subPercent[1].trim());
                    } else if (R_Normal.equals("S")) {
                        Percent = Integer.parseInt(subPercent[2].trim());
                    }
                }

                balance.setR_PrSubAmt((rs.getDouble("R_Total") * Percent) / 100);
                balance.setR_QuanCanDisc(0);// if member default 0

                String sqlUpd = "update balance set "
                        + "R_PrSubType='-M',"
                        + "R_PrSubCode='MEM',"
                        + "R_PrSubQuan='" + rs.getDouble("R_Quan") + "',"
                        + "R_PrSubDisc='" + Percent + "',"
                        + "R_PrSubBath='0',"
                        + "R_PrSubAmt='" + ((rs.getDouble("R_Total") * Percent) / 100) + "',"
                        + "R_QuanCanDisc='0' "
                        + "where R_Index='" + rs.getString("R_Index") + "' "
                        + "and R_Table='" + rs.getString("R_Table") + "'";
                Statement stmt1 = mysqlConnect.getConnection().createStatement();
                stmt1.executeUpdate(sqlUpd);
                stmt1.close();
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            AppLogUtil.log(MemberControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
    }

}
