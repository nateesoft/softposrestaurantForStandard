package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.TSaleBean;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nathee
 */
public class TSaleController {

    public List<TSaleBean> listTSaleByRefId(String b_refno) {
        List<TSaleBean> listTsale = new ArrayList<>();
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(TSaleController.class);
        try {
            String sql = "select * from t_sale "
                    + "where r_refno='" + b_refno + "' "
                    + "order by r_index limit 1;";
            ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql);
            while (rs.next()) {
                TSaleBean bean = new TSaleBean();
                String pcode = rs.getString("r_plucode");
                String r_etd = rs.getString("r_etd");
                double r_quan = rs.getDouble("r_quan");
                bean.setR_PluCode(pcode);
                bean.setR_ETD(r_etd);
                bean.setR_Quan(r_quan);

                listTsale.add(bean);
            }
        } catch (SQLException e) {

        } finally {
            mysql.close();
        }

        return listTsale;
    }
}
