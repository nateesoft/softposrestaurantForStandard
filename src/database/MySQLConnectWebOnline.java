package database;

import com.softpos.pos.core.controller.Value;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.MSG;

public class MySQLConnectWebOnline {

    private Connection con = null;
    private Statement currentStatement = null;

    private static String HostName = null;
    private static String DbName = null;
    private static String UserName = null;
    private static String Password = null;
    private static String PortNumber = null;

    static {
        getDbVar();
    }

    public String getHostName() { return HostName; }
    public void setHostName(String HostName) { MySQLConnectWebOnline.HostName = HostName; }
    public String getDbName() { return DbName; }
    public void setDbName(String DbName) { MySQLConnectWebOnline.DbName = DbName; }
    public String getUserName() { return UserName; }
    public void setUserName(String UserName) { MySQLConnectWebOnline.UserName = UserName; }
    public String getPassword() { return Password; }
    public void setPassword(String Password) { MySQLConnectWebOnline.Password = Password; }
    public String getPortNumber() { return PortNumber; }
    public void setPortNumber(String PortNumber) { MySQLConnectWebOnline.PortNumber = PortNumber; }

    public void open() {
        try {
            con = DatabasePoolOnline.getConnection();
        } catch (SQLException e) {
        }
    }

    public Connection getConnection() {
        return con;
    }

    /**
     * Execute an UPDATE/INSERT/DELETE safely — Statement is closed automatically.
     */
    public int executeUpdate(String sql) {
        try (Statement stmt = con.createStatement()) {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            Logger.getLogger(MySQLConnectWebOnline.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    /**
     * Execute a SELECT query. Statement is stored and closed when close() is called.
     */
    public ResultSet executeQuery(String sql) {
        closeCurrentStatement();
        try {
            currentStatement = con.createStatement();
            return currentStatement.executeQuery(sql);
        } catch (SQLException e) {
            Logger.getLogger(MySQLConnectWebOnline.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public void close() {
        closeCurrentStatement();
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(MySQLConnectWebOnline.class.getName()).log(Level.SEVERE, null, ex);
            }
            con = null;
        }
    }

    private void closeCurrentStatement() {
        if (currentStatement != null) {
            try {
                currentStatement.close();
            } catch (SQLException ignore) {
            }
            currentStatement = null;
        }
    }

    public static void getDbVar() {
        if (HostName == null) {
            try {
                FileInputStream fs = new FileInputStream(Value.FILE_CONFIGOnline);
                DataInputStream ds = new DataInputStream(fs);
                BufferedReader br = new BufferedReader(new InputStreamReader(ds));
                String tmp;
                while ((tmp = br.readLine()) != null) {
                    String[] data = tmp.split(",", tmp.length());
                    if (data[0].equalsIgnoreCase("webServer")) {
                        HostName = data[1];
                    } else if (data[0].equalsIgnoreCase("webDatabase")) {
                        DbName = data[1];
                        Value.DATABASE = data[1];
                    } else if (data[0].equalsIgnoreCase("user")) {
                        UserName = data[1];
                    } else if (data[0].equalsIgnoreCase("pass")) {
                        Password = data[1];
                    } else if (data[0].equalsIgnoreCase("port")) {
                        PortNumber = data[1];
                    } else if (data[0].equalsIgnoreCase("macno")) {
                        Value.MACNO = data[1];
                    } else if (data[0].equalsIgnoreCase("language")) {
                        Value.LANG = data[1];
                    } else if (data[0].equalsIgnoreCase("db_member")) {
                        Value.db_member = data[1];
                    } else if (data[0].equalsIgnoreCase("useprint")) {
                        try {
                            Value.useprint = Boolean.parseBoolean(data[1]);
                        } catch (Exception e) {
                            Value.useprint = false;
                        }
                    } else if (data[0].equalsIgnoreCase("printkic")) {
                        try {
                            Value.printkic = Boolean.parseBoolean(data[1]);
                        } catch (Exception e) {
                            Value.printkic = false;
                        }
                    } else if (data[0].equalsIgnoreCase("autoqty")) {
                        try {
                            Value.autoqty = Boolean.parseBoolean(data[1]);
                        } catch (Exception e) {
                            Value.autoqty = false;
                        }
                    } else if (data[0].equalsIgnoreCase("printdriver")) {
                        try {
                            Value.printdriver = Boolean.parseBoolean(data[1]);
                        } catch (Exception e) {
                            Value.printdriver = false;
                        }
                    } else if (data[0].equalsIgnoreCase("printerName")) {
                        Value.printerDriverName = data[1];
                    }
                }
                br.close();
                ds.close();
                fs.close();
                DatabasePoolOnline.init(HostName, PortNumber, DbName, UserName, Password);
            } catch (IOException e) {
            }
        }
    }
}
