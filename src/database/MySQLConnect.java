package database;

import com.softpos.pos.core.controller.Value;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.softpos.util.AppLogUtil;

public class MySQLConnect {

    private Connection con = null;
    private Statement currentStatement = null;

    public static String HostName = null;
    public static String DbName = null;
    public static String UserName = null;
    public static String Password = null;
    public static String PortNumber = null;
    public static String CharSet = "utf-8";

    private String msgError = "พบการเชื่อมต่อมีปัญหา ไม่สามารถดำเนินการต่อได้\nท่านต้องการปิดโปรแกรมอัตโนมัติหรือไม่ ?";
    private Class clazz = null;

    static {
        try (FileInputStream fs = new FileInputStream(Value.FILE_CONFIG);
             BufferedReader br = new BufferedReader(new InputStreamReader(fs))) {
            String tmp;
            while ((tmp = br.readLine()) != null) {
                String[] data = tmp.split(",", tmp.length());
                if (data[0].equalsIgnoreCase("server")) {
                    HostName = data[1];
                } else if (data[0].equalsIgnoreCase("database")) {
                    DbName = data[1];
                    Value.DATABASE = data[1];
                } else if (data[0].equalsIgnoreCase("user")) {
                    UserName = data[1];
                } else if (data[0].equalsIgnoreCase("pass")) {
                    Password = data[1];
                } else if (data[0].equalsIgnoreCase("port")) {
                    PortNumber = data[1];
                } else if (data[0].equalsIgnoreCase("charset")) {
                    CharSet = data[1];
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
            DatabasePool.init(HostName, PortNumber, DbName, UserName, Password, CharSet);
        } catch (IOException e) {
            AppLogUtil.log(MySQLConnect.class, "error", e);
        }
    }

    public String getMsgError() {
        return msgError;
    }

    public void setMsgError(String msgError) {
        this.msgError = msgError;
    }

    public void open(Class clazz) {
        if (con != null) {
            close();
        }
        this.clazz = clazz;
        try {
            con = DatabasePool.getConnection();
            MySQLConstants.MYSQL_CONNECT.put(System.identityHashCode(con), clazz);
        } catch (SQLException e) {
            AppLogUtil.log(MySQLConnect.class, "error", e);
            throw new RuntimeException(msgError, e);
        }
    }

    public void open() {
        open(null);
    }

    public Connection getConnection() {
        return con;
    }

    /**
     * Execute an UPDATE/INSERT/DELETE safely — Statement is closed
     * automatically.
     */
    public int executeUpdate(String sql) {
        try (Statement stmt = con.createStatement()) {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            AppLogUtil.log(MySQLConnect.class, "error", e);
            return 0;
        }
    }

    /**
     * Execute a SELECT query. Statement is stored and closed when close() is
     * called. Caller must still close the returned ResultSet when done.
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        closeCurrentStatement();
        currentStatement = con.createStatement();
        return currentStatement.executeQuery(sql);
    }

    /**
     * Prepare a statement safely. Caller is responsible for closing the
     * PreparedStatement. Use try-with-resources: try (PreparedStatement p =
     * mysql.prepare(sql)) { ... }
     */
    public PreparedStatement prepare(String sql) throws SQLException {
        return con.prepareStatement(sql);
    }

    public void close() {
        closeCurrentStatement();
        if (con != null) {
            try {
                MySQLConstants.MYSQL_CONNECT.remove(System.identityHashCode(con));
                con.close();
            } catch (SQLException ex) {
                AppLogUtil.log(MySQLConnect.class, "error", ex);
            }
            con = null;
        }
    }

    public void closeConnection(Class clazz) {
        close();
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
}
