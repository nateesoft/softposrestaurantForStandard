package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.BalanceBean;
import com.softpos.pos.core.model.CompanyBean;
import com.softpos.pos.core.model.MenuListBean;
import com.softpos.pos.core.model.MgrButtonSetupBean;
import com.softpos.pos.core.model.PSetBean;
import com.softpos.pos.core.model.SoftMenuSetup;
import com.softpos.pos.core.model.TableFileBean;
import com.softpos.pos.core.model.TempsetBean;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.AppLogUtil;
import util.MSG;

/**
 *
 * @author nathee
 */
public class MainSaleController extends DatabaseConnection {

    public MenuListBean getMenuListByMenuItem(String PluCode) {
        MenuListBean bean = null;
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(MainSaleController.class);
        try {
            String SqlQuery = "select plucode from menulist where menuitem=('" + PluCode + "') "
                    + "and (menuactive='Y') limit 1";
            Statement stmt = mysql.getConnection().createStatement();
            try (ResultSet rs = stmt.executeQuery(SqlQuery)) {
                if (rs.next()) {
                    bean = new MenuListBean();
                    bean.setPlucode(rs.getString("plucode"));
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSaleController.class, "error", e);
        } finally {
            mysql.close();
        }

        return bean;
    }

    public boolean checkOutstockList(String TempCode) {
        boolean valid = false;
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());
        try {
            String sql = "select pcode from outstocklist where pcode='" + TempCode + "' limit 1";
            try (Statement stmt = mysql.getConnection().createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    valid = true;
                }
                rs.close();
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSaleController.class, "error", e);
        } finally {
            mysql.close();
        }

        return valid;
    }

    public List<PSetBean> getPSetByPCode(String pluCode) {
        List<PSetBean> listPset = new ArrayList<>();

        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());
        try {
            String sqlPSET = "select * from pset where pcode='" + pluCode + "';";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sqlPSET);
            while (rs.next()) {
                PSetBean bean = new PSetBean();
                bean.setPsubcode(rs.getString("psubcode"));
                bean.setPsubQTY(rs.getDouble("psudbQTY"));

                listPset.add(bean);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSaleController.class, "error", e);
        } finally {
            mysql.close();
        }

        return listPset;
    }

    public String getQueryShowKic(String tableNo) {
        return "select r_kic,r_etd from balance "
                + "where r_table='" + tableNo + "' "
                + "and R_PrintOK='Y' "
                + "and R_KicPrint<>'P' "
                + "and R_Kic<>'' "
                + "group by r_kic,r_etd "
                + "order by r_kic,r_etd";
    }

    public boolean checkCountPrinterTo(String tableNo) {
        boolean isValid = false;
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());
        try {
            String sqlShowKic = getQueryShowKic(tableNo);
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sqlShowKic)) {
                if (rs.next()) {
                    isValid = true;
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSaleController.class, "error", e);
        } finally {
            mysql.close();
        }

        return isValid;
    }

    public List<BalanceBean> getListAllPrintToKic(String tableNo) {
        List<BalanceBean> listBalance = new ArrayList<>();

        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());
        try {
            String sqlShowKic = getQueryShowKic(tableNo);
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sqlShowKic)) {
                while (rs.next()) {
                    BalanceBean bean = new BalanceBean();
                    bean.setR_Kic(rs.getString("r_kic"));
                    bean.setR_ETD(rs.getString("r_etd"));

                    listBalance.add(bean);
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSaleController.class, "error", e);
        } finally {
            mysql.close();
        }

        return listBalance;
    }

    public List<BalanceBean> printOnlyForm1(String tableNo, String rKic) {
        List<BalanceBean> listBalance = new ArrayList<>();

        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());
        try {
            String sql = "select R_PluCode from balance "
                    + "where r_table='" + tableNo + "' "
                    + "and R_PrintOK='Y' "
                    + "and R_KicPrint<>'P' "
                    + "and R_kic='" + rKic + "' "
                    + "group by r_plucode";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                while (rs.next()) {
                    BalanceBean bean = new BalanceBean();
                    bean.setR_PluCode(rs.getString("R_PluCode"));

                    listBalance.add(bean);
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSaleController.class, "error", e);
        } finally {
            mysql.close();
        }

        return listBalance;
    }

    public List<BalanceBean> printOnlyForm6(String tableNo, String rKic) {
        List<BalanceBean> listBalance = new ArrayList<>();

        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());
        try {
            String sql = "select sum(b.r_quan) R_Quan,sum(b.r_quan)*b.r_price Total, b.* from balance b "
                    + "where r_table='" + tableNo + "' "
                    + "and R_PrintOK='Y' "
                    + "and R_KicPrint<>'P' "
                    + "and R_Kic<>'' "
                    + "and R_KIC='" + rKic + "' "
                    + "group by r_plucode,r_void order by r_opt1";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                while (rs.next()) {
                    BalanceBean bean = new BalanceBean();
                    bean.setR_Index(rs.getString("R_Index"));
                    bean.setR_PluCode(rs.getString("R_PluCode"));
                    bean.setR_Quan(rs.getDouble("R_Quan"));
                    bean.setR_Total(rs.getDouble("Total"));

                    listBalance.add(bean);
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSaleController.class, "error", e);
        } finally {
            mysql.close();
        }

        return listBalance;
    }

    public TableFileBean getTableFileByCode(String tableNo) {
        TableFileBean bean = null;
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(MainSaleController.class);
        try {
            String sql = "select TAmount, TCustomer, tonact from tablefile where tcode='" + tableNo + "' limit 1";
            Statement stmt = mysql.getConnection().createStatement();
            try (ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    bean = new TableFileBean();
                    bean.setTOnAct(rs.getString("tonact"));
                    bean.setTAmount(rs.getDouble("TAmount"));
                    bean.setTCustomer(rs.getInt("TCustomer"));
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSaleController.class, "error", e);
        } finally {
            mysql.close();
        }

        return bean;
    }

    public BalanceBean getBalanceByTableNo(String tableNo) {
        BalanceBean bean = null;
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(MainSaleController.class);
        try {
            String sql = "select r_time,r_date from balance where r_table ='" + tableNo + "' limit 1";
            Statement stmt = mysql.getConnection().createStatement();
            try (ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    bean = new BalanceBean();
                    bean.setR_Time(rs.getString("r_time"));
                    bean.setR_Date(rs.getDate("r_date"));
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSaleController.class, "error", e);
        } finally {
            mysql.close();
        }

        return bean;
    }

    public boolean getBalanceByIndex(String RIndex) {
        boolean isValid = false;

        MySQLConnect mysql = new MySQLConnect();
        mysql.open(MainSaleController.class);
        try {
            String sql = "select R_QuanCanDisc from balance where R_Index='" + RIndex + "' "
                    + "and R_QuanCanDisc>0 limit 1";
            Statement stmt = mysql.getConnection().createStatement();
            try (ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    isValid = true;
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSaleController.class, "error", e);
        } finally {
            mysql.close();
        }

        return isValid;
    }

    public BalanceBean getBalanceByRTable(String tableNo) {
        BalanceBean bean = null;
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(MainSaleController.class);
        try {
            String sql = "SELECT COUNT(R_PName) items FROM balance where r_table = '" + tableNo + "'";
            Statement stmt = mysql.getConnection().createStatement();
            try (ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    bean = new BalanceBean();
                    bean.setR_Total(rs.getInt("items"));
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSaleController.class, "error", e);
        } finally {
            mysql.close();
        }

        return bean;
    }

    public List<TempsetBean> getTempsetByPIndex(String PIndex) {
        List<TempsetBean> listPset = new ArrayList<>();

        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());
        try {
            String sql = "select * from tempset where PIndex='" + PIndex + "' ";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                TempsetBean bean = new TempsetBean();
                bean.setPCode(rs.getString("PCode"));

                listPset.add(bean);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSaleController.class, "error", e);
        } finally {
            mysql.close();
        }

        return listPset;
    }

    public TempsetBean getTempsetByPIndexPCode(String PIndex, String pCode) {
        TempsetBean bean = null;

        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());
        try {
            String sql = "select POption from tempset where PIndex='" + PIndex + "' "
                    + "and PCode='" + pCode + "' limit 1";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                bean = new TempsetBean();
                bean.setPOption(ThaiUtil.ASCII2Unicode(rs.getString("POption")));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSaleController.class, "error", e);
        } finally {
            mysql.close();
        }

        return bean;
    }

    public BalanceBean getBalanceByRIndex(String rIndex) {
        BalanceBean bean = null;
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(MainSaleController.class);
        try {
            String sql = "select r_linkindex from balance "
                    + "where r_index='" + rIndex + "' "
                    + "and r_linkindex<>'' limit 1";
            Statement stmt = mysql.getConnection().createStatement();
            try (ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    bean = new BalanceBean();
                    bean.setR_LinkIndex(rs.getString("r_linkindex"));
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSaleController.class, "error", e);
        } finally {
            mysql.close();
        }

        return bean;
    }

    public boolean checkKicPrint(String tableNo) {
        boolean isValid = false;

        MySQLConnect mysql = new MySQLConnect();
        mysql.open(MainSaleController.class);
        try {
            String sql = "select r_kicprint "
                    + "from balance where r_table='" + tableNo + "' "
                    + "and r_kicprint <> 'P' and R_PName <> '' limit 1";
            Statement stmt = mysql.getConnection().createStatement();
            try (ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    isValid = true;
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSaleController.class, "error", e);
        } finally {
            mysql.close();
        }

        return isValid;
    }

    public CompanyBean getHeaderCompany() {
        CompanyBean bean = null;
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(MainSaleController.class);
        try {
            String sql = "SELECT head1, head2, head3, head4 FROM company limit 1";
            Statement stmt = mysql.getConnection().createStatement();
            try (ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    bean = new CompanyBean();
                    bean.setHead1(ThaiUtil.ASCII2Unicode(rs.getString("head1")));
                    bean.setHead2(ThaiUtil.ASCII2Unicode(rs.getString("head2")));
                    bean.setHead3(ThaiUtil.ASCII2Unicode(rs.getString("head3")));
                    bean.setHead4(ThaiUtil.ASCII2Unicode(rs.getString("head4")));
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSaleController.class, "error", e);
        } finally {
            mysql.close();
        }

        return bean;
    }

    public boolean printBillVoidCheck(String tableNo) {
        boolean isValid = false;

        MySQLConnect mysql = new MySQLConnect();
        mysql.open(MainSaleController.class);
        try {
            String sql = "select r_index from balance where r_table='" + tableNo + "' and r_void='V' limit 1";
            Statement stmt = mysql.getConnection().createStatement();
            try (ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    isValid = true;
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSaleController.class, "error", e);
        } finally {
            mysql.close();
        }

        return isValid;
    }

    public List<BalanceBean> getBalanceByRLinkIndex(String rLinkIndex) {
        List<BalanceBean> listBalance = new ArrayList<>();

        MySQLConnect mysql = new MySQLConnect();
        mysql.open(MainSaleController.class);
        try {
            String sql = "select R_Index,R_Stock from balance "
                    + "where R_LinkIndex='" + rLinkIndex + "'";
            Statement stmt = mysql.getConnection().createStatement();
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    BalanceBean bean = new BalanceBean();
                    bean.setR_Index(rs.getString("r_index"));

                    listBalance.add(bean);
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSaleController.class, "error", e);
        } finally {
            mysql.close();
        }

        return listBalance;
    }

    public SoftMenuSetup getMenuShowText(String menuCode) {
        SoftMenuSetup bean = null;

        MySQLConnect mysql = new MySQLConnect();
        mysql.open(MainSaleController.class);
        try {
            String sql = "select n.PCode,n.MenuShowText from optionset o,soft_menusetup n "
                    + "where o.pcode = n.pcode and n.menucode='" + menuCode + "' limit 1";
            Statement stmt = mysql.getConnection().createStatement();
            try (ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    bean = new SoftMenuSetup();
                    bean.setPCode(rs.getString("PCode"));
                    bean.setMenuShowText(ThaiUtil.ASCII2Unicode(rs.getString("MenuShowText")));
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSaleController.class, "error", e);
        } finally {
            mysql.close();
        }

        return bean;
    }

    public List<MgrButtonSetupBean> getAllMgrButtonSetup(String pCode) {
        List<MgrButtonSetupBean> listMgrButton = new ArrayList<>();

        MySQLConnect mysql = new MySQLConnect();
        mysql.open(MainSaleController.class);
        try {
            String sql = "select auto_pcode, auto_pdesc from mgrbuttonsetup "
                    + "where pcode='" + pCode + "' and auto_pcode<>''";
            Statement stmt = mysql.getConnection().createStatement();
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    MgrButtonSetupBean bean = new MgrButtonSetupBean();
                    bean.setAutu_pcode(rs.getString("auto_pcode"));
                    bean.setAuto_pdesc(rs.getString("auto_pdesc"));

                    listMgrButton.add(bean);
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSaleController.class, "error", e);
        } finally {
            mysql.close();
        }

        return listMgrButton;
    }

    public MgrButtonSetupBean getMgrButtonAndMenuSetup(String menuCode) {
        MgrButtonSetupBean bean = null;

        MySQLConnect mysql = new MySQLConnect();
        mysql.open(MainSaleController.class);
        try {
            String sql = "select o.PCode,o.PDesc, o.check_before "
                    + "from mgrbuttonsetup o,soft_menusetup n "
                    + "where o.pcode = n.pcode "
                    + "and n.menucode='" + menuCode + "' limit 1";;
            Statement stmt = mysql.getConnection().createStatement();
            try (ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    bean = new MgrButtonSetupBean();
                    bean.setPCode(rs.getString("PCode"));
                    bean.setCheck_before(rs.getString("Check_before"));
                    bean.setPDesc(ThaiUtil.ASCII2Unicode(rs.getString("PDesc")));
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSaleController.class, "error", e);
        } finally {
            mysql.close();
        }

        return bean;
    }

    public boolean checkPassBeforeOrder(String tableNo) {
        boolean isFound = false;
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(MainSaleController.class);
        try {
            String sql = "SELECT 1 FROM balance where r_table = '" + tableNo + "' limit 1";
            Statement stmt = mysql.getConnection().createStatement();
            try (ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    isFound = true;
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(MainSaleController.class, "error", e);
        } finally {
            mysql.close();
        }

        return isFound;
    }
}
