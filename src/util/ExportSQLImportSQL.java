/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import database.ConfigFileServer;
import database.MySQLConnect;
import database.MySQLConnectWebOnline;
import java.io.File;
import java.sql.Statement;
import util.DateConvert;
import util.MSG;

/**
 *
 * @author Dell
 */
public class ExportSQLImportSQL {

    MySQLConnect c = new MySQLConnect();
    DateConvert dc = new DateConvert();
    private MySQLConnectWebOnline myOnline = new MySQLConnectWebOnline();

    public void exportSQL() {
        //exportFile.sql
        try {
            String product = "select * from product into outfile'" + "D:/Backup/Data/product.sql" + "'";
            String costfile = "select * from costfile into outfile'" + "D:/Backup/Data/costfile.sql" + "'";
            String posUser = "select * from posUser into outfile'" + "D:/Backup/Data/posUser.sql" + "'";
            String userGroup = "select * from userGroup into outfile'" + "D:/Backup/Data/userGroup.sql" + "'";
            String stkfile = "select * from stkfile into outfile'" + "D:/Backup/Data/stkfile.sql" + "'";
            String pset = "select * from pset into outfile'" + "D:/Backup/Data/pset.sql" + "'";
            String branch = "select * from branch into outfile'" + "D:/Backup/Data/branch.sql" + "'";
            String company = "select * from company into outfile'" + "D:/Backup/Data/company.sql" + "'";
            String poshwsetup = "select * from poshwsetup into outfile'" + "D:/Backup/Data/poshwsetup.sql" + "'";
            String menusetup = "select * from menusetup into outfile'" + "D:/Backup/Data/menusetup.sql" + "'";
            String posconfigsetup = "select * from posconfigsetup into outfile'" + "D:/Backup/Data/posconfigsetup.sql" + "'";
            String creditfile = "select * from posconfigsetup into outfile'" + "D:/Backup/Data/creditfile.sql" + "'";
            String groupfile = "select * from posconfigsetup into outfile'" + "D:/Backup/Data/groupfile.sql" + "'";
            String protab = "select * from posconfigsetup into outfile'" + "D:/Backup/Data/protab.sql" + "'";
            String soft_menusetup = "select * from posconfigsetup into outfile'" + "D:/Backup/Data/soft_menusetup.sql" + "'";
            String mgrbuttonsetup = "select * from posconfigsetup into outfile'" + "D:/Backup/Data/mgrbuttonsetup.sql" + "'";

            c.open();
            Statement stmtLocal = c.getConnection().createStatement();
            try {
                stmtLocal.executeQuery(product);
                Thread.sleep(1000 * 3);
            } catch (Exception e) {
                MSG.NOTICE(e.toString());
            }
            try {
                stmtLocal.executeQuery(costfile);
                Thread.sleep(1000 * 3);
            } catch (Exception e) {
                MSG.NOTICE(e.toString());
            }
            try {
                stmtLocal.executeQuery(posUser);
                Thread.sleep(1000 * 3);

            } catch (Exception e) {
                MSG.NOTICE(e.toString());
            }
            try {
                stmtLocal.executeQuery(userGroup);
                Thread.sleep(1000 * 3);
            } catch (Exception e) {
                MSG.NOTICE(e.toString());
            }
            try {
                stmtLocal.executeQuery(stkfile);
                Thread.sleep(1000 * 3);
            } catch (Exception e) {
                MSG.NOTICE(e.toString());
            }
            try {
                stmtLocal.executeQuery(pset);
                Thread.sleep(1000 * 3);
            } catch (Exception e) {
                MSG.NOTICE(e.toString());
            }
            try {
                stmtLocal.executeQuery(branch);
                Thread.sleep(1000 * 3);
            } catch (Exception e) {
            }
            try {
                stmtLocal.executeQuery(company);
                Thread.sleep(1000 * 3);
            } catch (Exception e) {
                MSG.NOTICE(e.toString());
            }
            try {
                stmtLocal.executeQuery(poshwsetup);
                Thread.sleep(1000 * 3);
            } catch (Exception e) {
                MSG.NOTICE(e.toString());
            }
            try {
                stmtLocal.executeQuery(menusetup);
                Thread.sleep(1000 * 3);
            } catch (Exception e) {
            }
            try {
                stmtLocal.executeQuery(posconfigsetup);
                Thread.sleep(1000 * 3);
            } catch (Exception e) {
                MSG.NOTICE(e.toString());
            }
            try {
                stmtLocal.executeQuery(creditfile);
                Thread.sleep(1000 * 3);
            } catch (Exception e) {
                MSG.NOTICE(e.toString());
            }
            try {
                stmtLocal.executeQuery(groupfile);
                Thread.sleep(1000 * 3);
            } catch (Exception e) {
                MSG.NOTICE(e.toString());
            }
            try {
                stmtLocal.executeQuery(protab);
                Thread.sleep(1000 * 3);

            } catch (Exception e) {
                MSG.NOTICE(e.toString());
            }
            try {
                stmtLocal.executeQuery(soft_menusetup);
                Thread.sleep(1000 * 3);
            } catch (Exception e) {
                MSG.NOTICE(e.toString());
            }
            try {
                stmtLocal.executeQuery(mgrbuttonsetup);
                Thread.sleep(1000 * 3);
            } catch (Exception e) {
                MSG.NOTICE(e.toString());
            }

            stmtLocal.close();
            c.close();
        } catch (Exception e) {
            MSG.NOTICE(e.toString());
        }
    }

    public void deleteFile() {
        //delete file all in folder
        File index = new File("D:/Backup/Data");
        String[] entries = index.list();
        for (String s : entries) {
            File currentFile = new File(index.getPath(), s);
            currentFile.delete();
        }
    }

    public void uploadSQL() {
        try {
            myOnline.open();
            String sqlDelData1 = "delete from product;";
            String sqlDelData2 = "delete from costfile;";
            String sqlDelData3 = "delete from posuser;";
            String sqlDelData4 = "delete from usergroup;";
            String sqlDelData5 = "delete from stkfile;";
            String sqlDelData6 = "delete from pset;";
            String sqlDelData7 = "delete from branch;";
            String sqlDelData8 = "delete from company;";
            String sqlDelData9 = "delete from poshwsetup;";
            String sqlDelData10 = "delete from menusetup;";
            String sqlDelData11 = "delete from posconfigsetup;";
            String sqlDelData12 = "delete from creditfile;";
            String sqlDelData13 = "delete from groupfile;";
            String sqlDelData14 = "delete from protab;";
            String sqlDelData15 = "delete from soft_menusetup;";
            String sqlDelData16 = "delete from mgrbuttonsetup;";
            Statement stmt = myOnline.getConnection().createStatement();

            stmt.executeUpdate(sqlDelData1);
            stmt.executeUpdate(sqlDelData2);
            stmt.executeUpdate(sqlDelData3);
            stmt.executeUpdate(sqlDelData4);
            stmt.executeUpdate(sqlDelData5);
            stmt.executeUpdate(sqlDelData6);
            stmt.executeUpdate(sqlDelData7);
            stmt.executeUpdate(sqlDelData8);
            stmt.executeUpdate(sqlDelData9);
            stmt.executeUpdate(sqlDelData10);
            stmt.executeUpdate(sqlDelData11);
            stmt.executeUpdate(sqlDelData12);
            stmt.executeUpdate(sqlDelData13);
            stmt.executeUpdate(sqlDelData14);
            stmt.executeUpdate(sqlDelData15);
            stmt.executeUpdate(sqlDelData16);
            String pathServer = ConfigFileServer.getProperties("ftp_server_sqlPath").replace("/", "//");
            String product = "load data infile'" + "D://Backup//Data//" + pathServer + "product.sql" + "' into table product;";
            String costfile = "load data infile'" + "D://Backup//Data//" + pathServer + "costfile.sql" + "' into table costfile;";
            String posUser = "load data infile'" + "D://Backup//Data//" + pathServer + "posUser.sql" + "' into table posUser;";
            String userGroup = "load data infile'" + "D://Backup//Data//" + pathServer + "userGroup.sql" + "' into table userGroup;";
            String stkfile = "load data infile'" + "D://Backup//Data//" + pathServer + "stkfile.sql" + "' into table stkfile;";
            String pset = "load data infile'" + "D://Backup//Data//" + pathServer + "pset.sql" + "' into table pset;";
            String branch = "load data infile'" + "D://Backup//Data//" + pathServer + "branch.sql" + "' into table branch;";
            String company = "load data infile'" + "D://Backup//Data//" + pathServer + "company.sql" + "' into table company;";
            String poshwsetup = "load data infile'" + "D://Backup//Data//" + pathServer + "poshwsetup.sql" + "' into table poshwsetup;";
            String menusetup = "load data infile'" + "D://Backup//Data//" + pathServer + "menusetup.sql" + "' into table menusetup;";
            String posconfigsetup = "load data infile'" + "D://Backup//Data//" + pathServer + "posconfigsetup.sql" + "' into table posconfigsetup;";
            String creditfile = "load data infile'" + "D://Backup//Data//" + pathServer + "creditfile.sql" + "' into table creditfile;";
            String groupfile = "load data infile'" + "D://Backup//Data//" + pathServer + "groupfile.sql" + "' into table groupfile;";
            String protab = "load data infile'" + "D://Backup//Data//" + pathServer + "protab.sql" + "' into table protab;";
            String soft_menusetup = "load data infile'" + "D://Backup//Data//" + pathServer + "soft_menusetup.sql" + "' into table protab;";
            String mgrbuttonsetup = "load data infile'" + "D://Backup//Data//" + pathServer + "mgrbuttonsetup.sql" + "' into table protab;";

            try {
                stmt.executeUpdate(product);
                Thread.sleep(1000 * 5);
            } catch (Exception e) {
                MSG.NOTICE(e.toString());
            }
            try {
                stmt.executeUpdate(costfile);
                Thread.sleep(1000 * 5);
            } catch (Exception e) {
            }
            try {
                stmt.executeUpdate(posUser);
                Thread.sleep(1000 * 5);
            } catch (Exception e) {
            }
            try {
                stmt.executeUpdate(userGroup);
                Thread.sleep(1000 * 5);
            } catch (Exception e) {
            }
            try {
                stmt.executeUpdate(stkfile);
                Thread.sleep(1000 * 5);
            } catch (Exception e) {
            }
            try {
                stmt.executeUpdate(pset);
                Thread.sleep(1000 * 5);
            } catch (Exception e) {
            }
            try {
                stmt.executeUpdate(branch);
                Thread.sleep(1000 * 5);
            } catch (Exception e) {
            }
            try {
                stmt.executeUpdate(company);
                Thread.sleep(1000 * 5);
            } catch (Exception e) {
            }
            try {
                stmt.executeUpdate(poshwsetup);
                Thread.sleep(1000 * 5);
            } catch (Exception e) {
            }
            try {
                stmt.executeUpdate(menusetup);
                Thread.sleep(1000 * 5);
            } catch (Exception e) {
            }
            try {
                stmt.executeUpdate(posconfigsetup);
                Thread.sleep(1000 * 5);
            } catch (Exception e) {
            }
            try {
                stmt.executeUpdate(creditfile);
                Thread.sleep(1000 * 5);
            } catch (Exception e) {
            }
            try {
                stmt.executeUpdate(groupfile);
                Thread.sleep(1000 * 5);
            } catch (Exception e) {
            }
            try {
                stmt.executeUpdate(protab);
                Thread.sleep(1000 * 5);
            } catch (Exception e) {
            }
            try {
                stmt.executeUpdate(soft_menusetup);
                Thread.sleep(1000 * 5);
            } catch (Exception e) {
            }
            try {
                stmt.executeUpdate(mgrbuttonsetup);
                Thread.sleep(1000 * 5);
            } catch (Exception e) {
            }
            stmt.close();
            myOnline.close();
        } catch (Exception e) {
            MSG.NOTICE(e.toString());
        }
    }

//    public static void main(String[] args) {
//        ExportSQLImportSQL test = new ExportSQLImportSQL();
//        test.deleteFile();
//        test.exportSQL();
//        test.uploadSQL();
//    }
}
