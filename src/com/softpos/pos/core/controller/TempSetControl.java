package com.softpos.pos.core.controller;

import com.softpos.connection.database.MySQLConnect;
import com.softpos.pos.core.model.BalanceBean;
import com.softpos.util.AppLogUtil;

/**
 *
 * @author nateelive
 */
public class TempSetControl {
    private final MySQLConnect mysqlConnect = new MySQLConnect();

    public void updatePIndexByPTableNo(BalanceBean bBean) {
        String sqlUpd = "update tempset set "
                + "PIndex='" + bBean.getR_Index() + "' "
                + "where PTableNo='" + bBean.getR_Table() + "' ";
        try {
            mysqlConnect.open();
            mysqlConnect.executeUpdate(sqlUpd);
        } catch (Exception e) {
            AppLogUtil.log(TempSetControl.class, "error", e);
        } finally {
            mysqlConnect.close();
        }
    }

    public void deleteByPTableNo(String tableNo) {
        mysqlConnect.open();
        try {
            String sql = "delete from tempset "
                + "where PTableNo='" + tableNo + "'";
            mysqlConnect.executeUpdate(sql);
        } catch (Exception e) {
            AppLogUtil.log(TableFileControl.class, "error", e);
        } finally {
            mysqlConnect.close();
        }
    }

    public void deleteTempByPTableNo(String tableNo) {
        mysqlConnect.open();
        try {
            String sql = "delete from tempset where PTableNo='" + tableNo + "';";
            mysqlConnect.executeUpdate(sql);
        } catch (Exception e) {
            AppLogUtil.log(TempSetControl.class, "error", e);
        } finally {
            mysqlConnect.close();
        }
    }

    public void addNewData(String tableNo, String index, String pCode, String pName, 
            String pstock, String tryName, String option, String pTime) {
        mysqlConnect.open();
        try {
            String sql = "INSERT INTO tempset "
                + "(PTableNo, PIndex, PCode, PDesc, "
                + "PPostStock,PProTry, POption, PTime) "
                + "VALUES ('" + tableNo + "', '" + index + "', '" + pCode + "', "
                + "'" + ThaiUtil.Unicode2ASCII(pName) + "', '" + pstock + "','" + tryName + "', "
                + "'" + ThaiUtil.Unicode2ASCII(option) + "', '"+pTime+"')";
            mysqlConnect.executeUpdate(sql);
        } catch (Exception e) {
            AppLogUtil.log(TempSetControl.class, "error", e);
        } finally {
            mysqlConnect.close();
        }
        
    }
    
}
