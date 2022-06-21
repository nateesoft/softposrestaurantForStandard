package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.FloorPlanBean;
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
public class TableSetupControl {

    public List<FloorPlanBean> getTableSetup(String zone) {
        List<FloorPlanBean> listBean = new ArrayList<>();

        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "select code_id, t1.tcode, tcustomer, tonact, "
                    + "tlogintime, titem, TAmount, PrintChkBill, b.r_time "
                    + "from tablesetup t1 "
                    + "inner join tablefile t2 on t1.tcode=t2.tcode "
                    + "left join balance b on t2.tcode=b.r_table "
                    + "where code_id like '" + zone + "%' "
                    + "order by code_id;";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                FloorPlanBean bean = new FloorPlanBean();
                bean.setCodeId(rs.getString("code_id"));
                bean.setTableNo(rs.getString("TCode"));
                bean.setLoginTime(rs.getString("tlogintime"));
                bean.setCustomer(rs.getInt("TCustomer"));
                bean.setIsActive(rs.getString("TOnAct").equals("Y"));
                bean.setTAmount(rs.getDouble("TAmount"));
                bean.setPrintChkBill(rs.getString("PrintChkBill"));
                bean.setRTime(rs.getString("r_time"));
                bean.setItem(rs.getInt("titem"));

                String codeId = rs.getString("code_id");
                bean.setZone(codeId.substring(0, 1));
                bean.setIndex(Integer.parseInt(codeId.substring(1, codeId.length())));

                listBean.add(bean);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(TableSetupControl.class, "error", e);
        } finally {
            mysql.close();
        }

        return listBean;
    }

}
