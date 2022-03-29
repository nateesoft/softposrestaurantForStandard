package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import util.MSG;

public class AppCheckDB {

    public static void checkExtra() {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "select check_extra from mgrbuttonsetup limit 0,1";
            try (Statement stmt = mysql.getConnection().createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                }
                rs.close();
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            try {
                String sqlUpd = "alter table mgrbuttonsetup add check_extra char(1) default 'N'";
                try (Statement stmt = mysql.getConnection().createStatement()) {
                    stmt.executeUpdate(sqlUpd);
                }
            } catch (SQLException ex) {
                MSG.ERR(e.getMessage());
                
            }
        } finally{
            mysql.close();
        }
    }
}
