package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.PIngredientBean;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nathee
 */
public class IngedientController {

    public List<PIngredientBean> getIngredient(String pluCode) {
        List<PIngredientBean> listIng = new ArrayList<>();
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open(IngedientController.class);
            String sql2 = "select i.*,pdesc,PBPack,pstock,pactive "
                    + "from product p, pingredent i "
                    + "where p.pcode=i.pingcode "
                    + "and i.pcode='" + pluCode + "' "
                    + "and PFix='L' and PStock='Y'";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql2);
            while (rs.next()) {
                PIngredientBean bean = new PIngredientBean();
                bean.setPBPack(rs.getDouble("pbpack"));
                bean.setPactive(rs.getString("Pactive"));
                bean.setPingCode(rs.getString("PingCode"));
                bean.setPingQty(rs.getDouble("PingQty"));
                bean.setPstock(rs.getString("Pstock"));
                
                listIng.add(bean);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
        } finally {
            mysql.close();
        }
        
        return listIng;
    }
}
