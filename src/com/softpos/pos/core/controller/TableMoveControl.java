package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.BalanceBean;
import com.softpos.pos.core.model.MemberBean;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import com.softpos.util.AppLogUtil;

public class TableMoveControl {

    private static MemberBean memberBean;
    private final MySQLConnect mysqlConnect = new MySQLConnect();
    private final BalanceControl BalanceControl = AppContext.getBalanceControl();

    private void updateRLinkIndex(String tableDest) {
        
        mysqlConnect.open(TableMoveControl.class);
        try {
            String sql1 = "select R_SPIndex,R_LinkIndex,R_MoveFrom "
                    + "from balance where r_table='" + tableDest + "' "
                    + "and r_movefrom=r_linkindex;";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql1);
            while (rs.next()) {
                String R_SPIndex = rs.getString("R_SPIndex");
                String R_MoveFrom = rs.getString("R_MoveFrom");

                Statement stmt1 = mysqlConnect.getConnection().createStatement();
                String sql2 = "update balance set "
                        + "r_movefrom='',"
                        + "r_linkindex='" + R_SPIndex + "' "
                        + "where r_linkindex='" + R_MoveFrom + "' "
                        + "and r_movefrom<>'' "
                        + "and r_table='" + tableDest + "'";
                stmt1.executeUpdate(sql2);
                stmt1.close();
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {

            AppLogUtil.log(TableMoveControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(TableMoveControl.class);
        }
    }

    public TableMoveControl() {}
    
    public TableMoveControl(MemberBean memberBean) {
        this.memberBean = memberBean;
    }

    public void moveTable(String tableFrom, String tableDest) {

        // Check table are same
        if (tableFrom.equals(tableDest)) {
            //keyMoveTable.put("Table invalid move !", false);
        } else {

            // Check table form exist data
            BalanceControl bControl = AppContext.getBalanceControl();
            List<BalanceBean> dataBalanceFrom = bControl.getAllBalance(tableFrom);
            List<BalanceBean> dataBalanceDest = bControl.getAllBalance(tableDest);

            // Table from no data
            if (dataBalanceFrom.isEmpty()) {
                //keyMoveTable.put("Table "+tableFrom+" no data to move", false);
            } else {
                // Table destination no data
                if (dataBalanceDest.isEmpty()) {
                    //keyMoveTable.put("Move success", true);
                    for (int i = 0; i < dataBalanceFrom.size(); i++) {
                        BalanceBean bean = (BalanceBean) dataBalanceFrom.get(i);
                        String R_Index = bean.getR_Index();
                        bControl.moveBalanceAll(tableDest, bean);
                        bControl.deleteBalance(tableFrom, bean.getR_PluCode(), R_Index);
                    }

                    updateRLinkIndex(tableDest);

                    // Clear table destination
                    TableFileControl tbFile = AppContext.getTableFileControl();
                    tbFile.setDefaultTableFile(tableFrom);
                } else {
                    //keyMoveTable.put("Table "+tableDest+" exist data already , Confirm to Move table ?", true);
                    for (int i = 0; i < dataBalanceFrom.size(); i++) {
                        BalanceBean bean = (BalanceBean) dataBalanceFrom.get(i);
                        String R_Index = bean.getR_Index();
                        bControl.moveBalanceAll(tableDest, bean);
                        bControl.deleteBalance(tableFrom, bean.getR_PluCode(), R_Index);
                    }

                    updateRLinkIndex(tableDest);

                    // Clear table destination
                    TableFileControl tbFile = AppContext.getTableFileControl();
                    tbFile.setDefaultTableFile(tableFrom);
                }
            }
        }
    }

    public void moveProduct(String table1, String table2, String R_Index) {
        BalanceControl bControl = AppContext.getBalanceControl();
        List<BalanceBean> dataBalanceFrom = bControl.getBalanceIndex(R_Index);

        for (int i = 0; i < dataBalanceFrom.size(); i++) {
            BalanceBean bean = (BalanceBean) dataBalanceFrom.get(i);
            bControl.moveBalanceAll(table2, bean);
            bControl.deleteBalance(table1, bean.getR_PluCode(), R_Index);
        }

        BalanceControl.updateProSerTable(table1, memberBean);
        BalanceControl.updateProSerTable(table2, memberBean);
    }

    public void backupTableData(String table1, String table2) {
        mysqlConnect.open(TableMoveControl.class);
        try {
            Statement stmt = mysqlConnect.getConnection().createStatement();
            stmt.executeUpdate("drop table IF EXISTS temp_tablefile;");
            stmt.executeUpdate("create table IF NOT EXISTS temp_tablefile select * from tablefile "
                    + "where tcode in('" + table1 + "','" + table2 + "');");
            stmt.executeUpdate("drop table IF EXISTS temp_balance;");
            stmt.executeUpdate("create table IF NOT EXISTS temp_balance select * from balance "
                    + "where R_Table in('" + table1 + "','" + table2 + "');");
            stmt.close();
        } catch (SQLException e) {
            AppLogUtil.log(TableMoveControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(TableMoveControl.class);
        }
    }

    public void restoreTableData(String table1, String table2) {
        mysqlConnect.open(TableMoveControl.class);
        try {
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate("delete from tablefile where tcode in('" + table1 + "','" + table2 + "');");
                stmt.executeUpdate("delete from balance where r_table in('" + table1 + "','" + table2 + "');");
                stmt.executeUpdate("insert into tablefile select * from temp_tablefile "
                        + "where tcode in('" + table1 + "','" + table2 + "');");
                stmt.executeUpdate("insert into balance select * from temp_balance "
                        + "where r_table in('" + table1 + "','" + table2 + "');");
            }
        } catch (SQLException e) {
            AppLogUtil.log(TableMoveControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(TableMoveControl.class);
        }
    }

    public void updateCustomerCount(String tableFrom, String tableTo) {
        mysqlConnect.open(TableMoveControl.class);
        try {
            int ct1 = 0, ct2 = 0;
            Statement stmt1 = mysqlConnect.getConnection().createStatement();
            ResultSet rs1 = stmt1.executeQuery("select tcustomer from tablefile where tcode='" + tableFrom + "' limit 1");
            if (rs1.next()) ct1 = rs1.getInt("tcustomer");
            rs1.close();
            stmt1.close();

            Statement stmt2 = mysqlConnect.getConnection().createStatement();
            ResultSet rs2 = stmt2.executeQuery("select tcustomer from tablefile where tcode='" + tableTo + "' limit 1");
            if (rs2.next()) ct2 = rs2.getInt("tcustomer");
            rs2.close();
            stmt2.close();

            Statement stmt3 = mysqlConnect.getConnection().createStatement();
            stmt3.executeUpdate("update tablefile set tcustomer='" + (ct1 + ct2) + "' where tcode='" + tableTo.toUpperCase() + "'");
            stmt3.close();
        } catch (SQLException e) {
            AppLogUtil.log(TableMoveControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(TableMoveControl.class);
        }
    }

    public String[] getPrinterList(String table) {
        mysqlConnect.open(TableMoveControl.class);
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select r_kic from balance where r_table='" + table + "' group by r_kic;");
            while (rs.next()) {
                sb.append("kic").append(rs.getString("r_kic")).append(",");
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            AppLogUtil.log(TableMoveControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(TableMoveControl.class);
        }
        return sb.toString().split(",");
    }

    public boolean verifyEmployeeCode(String code) {
        mysqlConnect.open(TableMoveControl.class);
        try {
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select code from employ where code='" + code + "' limit 1");
            boolean found = rs.next();
            rs.close();
            stmt.close();
            return found;
        } catch (SQLException e) {
            AppLogUtil.log(TableMoveControl.class, "error", e);
            return false;
        } finally {
            mysqlConnect.closeConnection(TableMoveControl.class);
        }
    }

    public void updateForVoidAfterMoveTable(String table) {
        mysqlConnect.open(TableMoveControl.class);
        try {
            mysqlConnect.executeUpdate("update balance set r_spindex=r_index ,r_linkIndex=r_index where r_table='" + table + "'");
        } catch (Exception e) {
            AppLogUtil.log(TableMoveControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(TableMoveControl.class);
        }
    }

    public void tmpTableBeforeMove(String table) {
        mysqlConnect.open(TableMoveControl.class);
        try {
            String[] sqls = {
                "drop table if exists tmp_tablefile;",
                "drop table if exists tmp_balance;",
                "create table if not exists tmp_tablefile select * from tablefile where tcode='" + table + "';",
                "create table if not exists tmp_balance select * from balance where r_table = '" + table + "';"
            };
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                for (String sql : sqls) {
                    stmt.executeUpdate(sql);
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(TableMoveControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(TableMoveControl.class);
        }
    }
}
