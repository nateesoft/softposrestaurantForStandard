package database;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.softpos.pos.core.controller.Value;
import util.MSG;

public class MySQLConnectWebOnline {

    private Connection con = null;
    private String HostName = null;
    private String DbName = null;
    private String UserName = null;
    private String Password = null;
    private String PortNumber = null;
    private String msgError = "พบการเชื่อมต่อมีปัญหา ไม่สามารถดำเนินการต่อได้\nท่านต้องการปิดโปรแกรมอัตโนมัติหรือไม่ ?";

    public String getHostName() {
        return HostName;
    }

    public void setHostName(String HostName) {
        this.HostName = HostName;
    }

    public String getDbName() {
        return DbName;
    }

    public void setDbName(String DbName) {
        this.DbName = DbName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getPortNumber() {
        return PortNumber;
    }

    public void setPortNumber(String PortNumber) {
        this.PortNumber = PortNumber;
    }

    public String getMsgError() {
        return msgError;
    }

    public void setMsgError(String msgError) {
        this.msgError = msgError;
    }

    public void open() {
        try {
            getDbVar();
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + HostName + ":" + PortNumber + "/" + DbName + "?characterEncoding=utf-8", UserName, Password);
        } catch (ClassNotFoundException | SQLException e) {
            MSG.ERR(e.getMessage());
        }
    }

    public Connection getConnection() {
        return con;
    }

    public void close() {
        if (con != null) {
            try {
                con.close();
                //System.out.println("Database Closed.");
            } catch (SQLException ex) {
                Logger.getLogger(MySQLConnect.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void getDbVar() {
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
//                        UserName = "root";
                    } else if (data[0].equalsIgnoreCase("pass")) {
                        Password = data[1];
                    } else if (data[0].equalsIgnoreCase("port")) {
//                        PortNumber = data[1];
                        PortNumber = "3307";
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
                
            }
        }
    }
}
