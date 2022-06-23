package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.AccrBean;
import com.softpos.pos.core.model.BalanceBean;
import com.softpos.pos.core.model.CustFileBean;
import com.softpos.pos.core.model.TableFileBean;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import util.AppLogUtil;
import util.MSG;

/**
 *
 * @author nathee
 */
public class CheckBillController extends DatabaseConnection {

    public BalanceBean getBalanceByTableNo(String tableNo) {
        BalanceBean bean = null;
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open(CheckBillController.class);
            String sql = "select r_index from balance "
                    + "where r_table='" + tableNo + "' and r_type='1' limit 1";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                if (rs.next()) {
                    bean = new BalanceBean();
                    bean.setR_Index(rs.getString("r_index"));
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(CheckBillController.class, "error", e);
        } finally {
            mysql.close();
        }

        return bean;
    }

    public CustFileBean getCustFileBySpCode(String arCode) {
        CustFileBean bean = null;
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open(CheckBillController.class);
            String sql = "select sp_desc,sp_cr,sp_cramt from custfile "
                    + "where sp_code='" + arCode + "' limit 1";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                if (rs.next()) {
                    bean = new CustFileBean();
                    bean.setSp_desc(ThaiUtil.ASCII2Unicode(rs.getString("sp_desc")));
                    bean.setSp_cr(rs.getInt("sp_cr"));
                    bean.setSp_cramt(rs.getDouble("sp_cramt"));
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(CheckBillController.class, "error", e);
        } finally {
            mysql.close();
        }

        return bean;
    }

    public AccrBean getTotalAccrByArCode(String arCode) {
        AccrBean bean = null;
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open(CheckBillController.class);
            String sql = "select sum(aramount) total "
                    + "from accr "
                    + "where arcode='" + arCode + "' "
                    + "group by arcode";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                if (rs.next()) {
                    bean = new AccrBean();
                    bean.setTotal(rs.getDouble("total"));
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(CheckBillController.class, "error", e);
        } finally {
            mysql.close();
        }

        return bean;
    }

    public boolean readyToPrintVoid(String tableNo) {
        boolean isValid = false;
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open(CheckBillController.class);
            String sql = "select r_index from balance "
                    + "where r_table='" + tableNo + "' "
                    + "and r_void='V' limit 1";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                if (rs.next()) {
                    isValid = true;
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(CheckBillController.class, "error", e);
        } finally {
            mysql.close();
        }

        return isValid;
    }

    public boolean readyToCheckKic(String tableNo) {
        boolean isValid = false;
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open(CheckBillController.class);
            String sql = "select r_kicprint "
                    + "from balance where r_table='" + tableNo + "' "
                    + "and r_kicprint <> 'P' and R_PName <> '' limit 1";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                if (rs.next()) {
                    isValid = true;
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(CheckBillController.class, "error", e);
        } finally {
            mysql.close();
        }

        return isValid;
    }
    
    public TableFileBean loadDiscByTableNo(String tableNo) {
        TableFileBean bean = null;
        
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());
        try {
            String sql = "select sum(FastDiscAmt+EmpDiscAmt+MemDiscAmt+TrainDiscAmt+SubDiscAmt+DiscBath+CuponDiscAmt) AAA "
                    + "from tablefile where Tcode = '" + tableNo + "'";
            try (Statement stmt = mysql.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    bean = new TableFileBean();
                    bean.setTAmount(rs.getDouble("AAA"));
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(CheckBillController.class, "error", e);
        } finally {
            mysql.close();
        }
        
        return bean;
    }
    
    public boolean restoreTempBalance(String tableNo) {
        boolean isValid = false;
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open(CheckBillController.class);
            String sql = "select r_table from temp_balance "
                    + "where r_table ='" + tableNo + "' limit 1";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                if (rs.next()) {
                    isValid = true;
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(CheckBillController.class, "error", e);
        } finally {
            mysql.close();
        }

        return isValid;
    }

}
