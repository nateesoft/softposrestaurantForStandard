package database;

import com.softpos.pos.core.controller.Value;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.AppLogUtil;
import util.MSG;

public class MySQLConnect {

    private Connection con = null;
    public static String HostName = null;
    public static String DbName = null;
    public static String UserName = null;
    public static String Password = null;
    public static String PortNumber = null;
    public static String CharSet = "utf-8";
    private String msgError = "พบการเชื่อมต่อมีปัญหา ไม่สามารถดำเนินการต่อได้\nท่านต้องการปิดโปรแกรมอัตโนมัติหรือไม่ ?";
    private Class clazz = null;

    static {
        try {
            FileInputStream fs = new FileInputStream(Value.FILE_CONFIG);
            DataInputStream ds = new DataInputStream(fs);
            BufferedReader br = new BufferedReader(new InputStreamReader(ds));
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
                        System.err.println(e.getMessage());
                        Value.useprint = false;
                    }
                } else if (data[0].equalsIgnoreCase("printkic")) {
                    try {
                        Value.printkic = Boolean.parseBoolean(data[1]);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                        Value.printkic = false;
                    }
                } else if (data[0].equalsIgnoreCase("autoqty")) {
                    try {
                        Value.autoqty = Boolean.parseBoolean(data[1]);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                        Value.autoqty = false;
                    }
                } else if (data[0].equalsIgnoreCase("printdriver")) {
                    try {
                        Value.printdriver = Boolean.parseBoolean(data[1]);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                        Value.printdriver = false;
                    }
                } else if (data[0].equalsIgnoreCase("printerName")) {
                    Value.printerDriverName = data[1];
                }
            }
            br.close();
            ds.close();
            fs.close();
        } catch (IOException e) {
            MSG.ERR(e.getMessage());
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
        this.clazz = clazz;
        if (MySQLConstants.MYSQL_CONNECT.size() > 0) {
            System.out.println(this.clazz + "(not close)");
        }
        
        if (con == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://" + HostName + ":" + PortNumber + "/" + DbName + "?characterEncoding=utf-8", UserName, Password);
//                System.out.println(this.clazz + "Connection:" + con.hashCode());
                MySQLConstants.MYSQL_CONNECT.put(this.clazz, con.hashCode());
            } catch (ClassNotFoundException | SQLException e) {
                MSG.ERR("Database Connection Error !!!\n" + e.getMessage());
                AppLogUtil.log(MySQLConnect.class, "error", e);
                System.exit(0);
            }
        }
    }

    public Connection getConnection() {
        return con;
    }

    public void close() {
        if (con != null) {
            try {
                con.close();
//                System.out.println(this.clazz + "Connection_Close:" + con.hashCode());
                MySQLConstants.MYSQL_CONNECT.remove(this.clazz);
            } catch (SQLException ex) {
                Logger.getLogger(MySQLConnect.class.getName()).log(Level.SEVERE, null, ex);
                AppLogUtil.log(MySQLConnect.class, "error", ex);
            }

        }
    }

    public static void startMysql() {
        try {
            Runtime.getRuntime().exec("cmd /c start d:\"\"startService.bat");
            System.out.println("MySQL server start successfully!");
        } catch (IOException e) {

        }
    }

    public static void stopMysql() {
        try {
            Runtime.getRuntime().exec("cmd /c start d:\"\"stopService.bat");
            System.out.println("MySQL server stopped successfully!");
        } catch (IOException e) {

        }
    }

}
