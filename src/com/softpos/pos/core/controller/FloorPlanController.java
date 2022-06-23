package com.softpos.pos.core.controller;

import com.softpos.floorplan.FloorPlanDialog;
import com.softpos.pos.core.model.PIngredientBean;
import com.softpos.pos.core.model.SPTempRefundBean;
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
public class FloorPlanController extends DatabaseConnection {

    public List<SPTempRefundBean> getSpTempRefund() {
        List<SPTempRefundBean> listSpTempRefund = new ArrayList<>();
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());
        try {
            String sql = "select * from sp_temp_refund limit 1;";
            try (Statement stmt = mysql.getConnection().createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    SPTempRefundBean bean = new SPTempRefundBean();
                    bean.setR_PluCode(rs.getString("R_PluCode"));
                    bean.setR_Quan(rs.getDouble("R_Quan"));
                    bean.setR_Price(rs.getDouble("R_Price"));
                    bean.setR_ETD(rs.getString("R_ETD"));

                    listSpTempRefund.add(bean);
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

        return listSpTempRefund;
    }

    public void deleteSpTempRefund() {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());
        try {
            String sql = "delete from sp_temp_refund";
            mysql.getConnection().createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(FloorPlanDialog.class, "error", e);
        } finally {
            mysql.close();
        }
    }

    public TempsetBean getPOptionFromTempSet(String rIndex, String pCode) {
        TempsetBean bean = null;
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(FloorPlanController.class);
        try {
            String sql = "select POption from tempset "
                    + "where PIndex='" + rIndex + "' "
                    + "and PCode='" + pCode + "' limit 1";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                bean = new TempsetBean();
                bean.setPOption(ThaiUtil.Unicode2ASCII(rs.getString("POption")));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(FloorPlanDialog.class, "error", e);
        } finally {
            mysql.close();
        }

        return bean;
    }

    public List<TempsetBean> getTempsetByPIndex(String rIndex) {
        List<TempsetBean> listTempset = new ArrayList<>();

        MySQLConnect mysql = new MySQLConnect();
        mysql.open(FloorPlanController.class);
        try {
            String sql = "select * from tempset where PIndex='" + rIndex + "' ";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                while (rs.next()) {
                    TempsetBean bean = new TempsetBean();
                    bean.setPCode(rs.getString("PCode"));
                    listTempset.add(bean);
                }
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(FloorPlanDialog.class, "error", e);
        } finally {
            mysql.close();
        }

        return listTempset;
    }

    public List<PIngredientBean> listIngredeint(String pluCode) {
        List<PIngredientBean> listPing = new ArrayList<>();

        MySQLConnect mysql = new MySQLConnect();
        mysql.open(FloorPlanController.class);
        try {
            String sql1 = "select i.*,pdesc,PBPack,pstock,pactive "
                    + "from product p, pingredent i "
                    + "where p.pcode=i.pingcode "
                    + "and i.pcode='" + pluCode + "' "
                    + "and PFix='L' and PStock='Y'";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql1);
            while (rs.next()) {
                PIngredientBean bean = new PIngredientBean();
                bean.setPingCode(rs.getString("PingCode"));
                bean.setPBPack(rs.getDouble("PBPack"));
                bean.setPingQty(rs.getDouble("PingQty"));
                bean.setPstock(rs.getString("pstock"));
                bean.setPactive(rs.getString("pstock"));
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(FloorPlanDialog.class, "error", e);
        } finally {
            mysql.close();
        }
        
        return listPing;
    }
}
