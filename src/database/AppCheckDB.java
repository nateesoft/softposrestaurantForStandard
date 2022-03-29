package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import util.MSG;

public class AppCheckDB {

    public static void checkExtra() {
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "select check_extra from mgrbuttonsetup limit 0,1";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            
            
            try {
                String sqlUpd = "alter table mgrbuttonsetup add check_extra char(1) default 'N'";
                Statement stmt = mysql.getConnection().createStatement();
                stmt.executeUpdate(sqlUpd);
                stmt.close();
            } catch (SQLException ex) {
                MSG.ERR(e.getMessage());
                
            }
        } finally{
            mysql.close();
        }
    }
}
