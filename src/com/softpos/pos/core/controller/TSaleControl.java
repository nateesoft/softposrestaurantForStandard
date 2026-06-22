package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.TSaleBean;
import com.softpos.connection.database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nathee
 */
public class TSaleControl {

    private final MySQLConnect mysqlConnect = new MySQLConnect();

    /**
     * Returns the daily sale summary as a double array:
     *   [0] = hold (balance unpaid, r_total)
     *   [1] = paid (billno paid, b_nettotal)
     *   [2] = total (hold + paid)
     */
    public double[] getDailySaleSummary() {
        double hold = 0.00;
        double paid = 0.00;

        mysqlConnect.open(TSaleControl.class);
        try {
            Statement stmt = mysqlConnect.getConnection().createStatement();

            String sql = "SELECT sum(r_total) r_total FROM balance where R_VOID <> 'V';";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                hold = rs.getDouble("r_total");
            }
            rs.close();

            String sql1 = "SELECT sum(b_nettotal) b_nettotal FROM billno where b_void <> 'V';";
            ResultSet rs1 = stmt.executeQuery(sql1);
            if (rs1.next()) {
                paid = rs1.getDouble("b_nettotal");
            }
            rs1.close();

            stmt.close();
        } catch (SQLException e) {
            // caller handles display of errors
        } finally {
            mysqlConnect.closeConnection(TSaleControl.class);
        }

        return new double[]{hold, paid, hold + paid};
    }

    public List<TSaleBean> listTSaleByRefId(String b_refno) {
        List<TSaleBean> listTsale = new ArrayList<>();
        
        mysqlConnect.open(TSaleControl.class);
        try {
            String sql = "select * from t_sale "
                    + "where r_refno='" + b_refno + "' "
                    + "order by r_index limit 1;";
            ResultSet rs = mysqlConnect.executeQuery(sql);
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
            rs.close();
        } catch (SQLException e) {

        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return listTsale;
    }
}
