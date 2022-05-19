package com.softpos.pos.core.controller;

import database.MySQLConnect;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import printReport.convertToChar;
import util.AppLogUtil;
import util.MSG;

public final class ViewReport {

    private DecimalFormat doubleFmt = new DecimalFormat("##,###,##0.00");
    private SimpleDateFormat outFmt = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    private SimpleDateFormat inFmt = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public void printReportPVat(String vatNo) {
        ResultSet rs1;
        String comName = null, address = null, comTel = null, comFax = null, no = null, tax = null;
        String sqlCompany = "SELECT c.Name, c.Address, c.Subprovince,"
                + " c.Province, c.City, c.POST, c.Tel, c.Fax, c.Tax"
                + " FROM company c limit 1";
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            try {
                rs1 = mysql.getConnection().createStatement().executeQuery(sqlCompany);
                if (rs1.next()) {
                    comName = rs1.getString("c.Name");
                    address = rs1.getString("c.Address");
                    comTel = rs1.getString("c.Tel");
                    comFax = rs1.getString("c.Fax");
                    tax = rs1.getString("c.Tax");
                }
                rs1.close();
            } catch (SQLException e) {
                MSG.ERR(e.getMessage());
                AppLogUtil.log(ViewReport.class, "error", e);
            }

            String[] hrecive = new String[4];
            ResultSet rs;
            int check = 0;
            String amoutlist = "";
            String amount = "";
            String cashPay = "";
            String crPay = "";
            String crNo = "";
            String cupon = "";
            String onDate = "";
            try {
                String sql = "SELECT * FROM invcashdoc WHERE invNo = '" + vatNo + "' limit 1;";
                rs = mysql.getConnection().createStatement().executeQuery(sql);
                if (rs.next()) {
                    onDate = rs.getString("InvDate");
                    amount = rs.getString("Amount");
                    cashPay = rs.getString("CashPay");
                    crPay = rs.getString("CrPay");
                    crNo = rs.getString("CrNo");
                    cupon = rs.getString("Cupon");
                    check = 1;
                }

                rs.close();
            } catch (SQLException e) {
                MSG.ERR(e.getMessage());
                AppLogUtil.log(ViewReport.class, "error", e);
            }

            if (check > 0) {
                String sqlBranch = "SELECT * FROM branch limit 1";
                String branchName = "";
                try {
                    rs = mysql.getConnection().createStatement().executeQuery(sqlBranch);
                    if (rs.next()) {
                        branchName = rs.getString("Name");
                    }
                } catch (SQLException e) {
                    MSG.ERR(e.getMessage());
                    AppLogUtil.log(ViewReport.class, "error", e);
                }
                try {
                    Float cp = Float.parseFloat(cashPay);
                    Float crp = Float.parseFloat(crPay);
                    Float cup = Float.parseFloat(cupon);

                    Map parameters = new HashMap();

                    parameters.put("companyName", comName);
                    parameters.put("address", address);
                    parameters.put("companyTel", comTel);
                    parameters.put("companyFax", comFax);
                    parameters.put("no", no);
                    parameters.put("vatNo", tax);
                    parameters.put("branVat", branchName);

                    convertToChar convert = new convertToChar();
                    parameters.put("priceString", "(" + convert.convertNumberToChar(amount) + ")");

                    if (cp > 0 && crp > 0 && cup > 0) {
                        parameters.put("cash", "X");
                        parameters.put("credit", "X");
                        parameters.put("gift", "X");
                        parameters.put("creditNo", crNo);
                    } else if (cp > 0 && crp <= 0 && cup <= 0) {
                        parameters.put("cash", "X");
                        parameters.put("credit", "");
                        parameters.put("gift", "");
                        parameters.put("creditNo", "");
                    } else if (cp > 0 && crp > 0 && cup <= 0) {
                        parameters.put("cash", "X");
                        parameters.put("credit", "X");
                        parameters.put("gift", "");
                        parameters.put("creditNo", crNo);
                    } else if (cp > 0 && crp <= 0 && cup > 0) {
                        parameters.put("cash", "X");
                        parameters.put("credit", "");
                        parameters.put("gift", "X");
                        parameters.put("creditNo", "");
                    } else if (cp <= 0 && crp > 0 && cup > 0) {
                        parameters.put("cash", "");
                        parameters.put("credit", "X");
                        parameters.put("gift", "X");
                        parameters.put("creditNo", crNo);
                    } else if (cp <= 0 && crp <= 0 && cup > 0) {
                        parameters.put("cash", "");
                        parameters.put("credit", "");
                        parameters.put("gift", "X");
                        parameters.put("creditNo", "");
                    } else if (cp <= 0 && crp > 0 && cup <= 0) {
                        parameters.put("cash", "");
                        parameters.put("credit", "X");
                        parameters.put("gift", "");
                        parameters.put("creditNo", crNo);
                    }

                    parameters.put("docNo", vatNo);
                    parameters.put("branchName", branchName);
                    parameters.put("vatNo", vatNo);
                    parameters.put("amoutlist", amoutlist);
                    parameters.put("docDate", convertDate(onDate));
                    parameters.put("remark", hrecive[1]);
                    JasperReport jasperReport = null;
                    try {
                        jasperReport = (JasperReport) JRLoader.loadObject(getClass().getResource("/report/file/inVat.jasper"));
                    } catch (JRException e) {
                        MSG.ERR(null, e.getMessage());
                    }

                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, mysql.getConnection());
                    JasperViewer v = new JasperViewer(jasperPrint, false);
                    JDialog j = new JDialog(new JFrame(), true);
                    j.setTitle("Print");
                    j.setSize(1024, 768);
                    j.getContentPane().add(v.getContentPane());
                    j.setLocationRelativeTo(null);
                    j.setVisible(true);
                    v.setTitle("Report...");
                    mysql.close();
                } catch (HeadlessException | NumberFormatException | JRException e) {
                    MSG.ERR(e.getMessage());
                }
            } else {
                MSG.ERR(null, "ไมพบข้อมูลที่ต้องการพิมพ์");
            }
            mysql.close();
        } catch (Exception e) {
            MSG.NOTICE(e.toString());
        }

    }

    public void printReportIVat(String vatNo) {
        ResultSet rs1;
        String comName = null, address = null, comTel = null, comFax = null, no = null, tax = null;
        String sqlCompany = "SELECT c.Name, c.Address, c.Subprovince,"
                + " c.Province, c.City, c.POST, c.Tel, c.Fax, c.Tax"
                + " FROM company c";
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            rs1 = mysql.getConnection().createStatement().executeQuery(sqlCompany);
            if (rs1.next()) {
                comName = rs1.getString("c.Name");
                address = rs1.getString("c.Address");
                comTel = rs1.getString("c.Tel");
                comFax = rs1.getString("c.Fax");
                tax = rs1.getString("c.Tax");
            }
            rs1.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        }

        ResultSet rs;
        int check = 0;
        String onDate = "";
        String CustCode = "";
        String CustName = "";
        String CustAddr = "";
        String CustTel = "";
        String CustFax = "";
        String dueDate = "";
        String crTerm = "";
        String OnTime = "";
        String MacNo = "";
        String RegNo = "";
        String RefNo = "";
        String Cashier = "";
        String discount = "";
        String earnest = "";
        String service = "";
        String subtotal = "";
        String vat = "";
        String amount = "";
        String ramark = "";
        try {
            String sql = "SELECT * FROM invcashdoc WHERE invNo = '" + vatNo + "' limit 1;";
            rs = mysql.getConnection().createStatement().executeQuery(sql);
            if (rs.next()) {
                onDate = rs.getString("InvDate");
                CustCode = rs.getString("CustCode");
                CustName = rs.getString("CustName");
                CustAddr = rs.getString("CustAddr1");
                CustTel = rs.getString("CustTel");
                CustFax = rs.getString("CustFax");
                dueDate = rs.getString("DueDate");
                crTerm = rs.getString("CrTerm");
                OnTime = rs.getString("OnTime");
                MacNo = rs.getString("MacNo");
                RegNo = rs.getString("RegNo");
                RefNo = rs.getString("RefNo");
                Cashier = rs.getString("Cashier");
                discount = rs.getString("Discount");
                earnest = rs.getString("Earnest");
                service = rs.getString("Service");
                subtotal = rs.getString("Subtotal");
                vat = rs.getString("Vat");
                amount = rs.getString("Amount");
                ramark = rs.getString("Remark");
                check = 1;
            }
            rs.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        }

        if (check > 0) {
            String sqlBranch = "SELECT Name FROM branch limit 1 ";
            String branchName = "";
            try {
                rs = mysql.getConnection().createStatement().executeQuery(sqlBranch);
                if (rs.next()) {
                    branchName = rs.getString("Name");
                }
            } catch (SQLException e) {
                MSG.ERR(e.getMessage());
                AppLogUtil.log(ViewReport.class, "error", e);
            }

            try {
                Map parameters = new HashMap();

                parameters.put("companyName", comName);
                parameters.put("address", address);
                parameters.put("companyTel", comTel);
                parameters.put("companyFax", comFax);
                parameters.put("branVat", branchName);
                convertToChar convert = new convertToChar();
                parameters.put("priceString", "(" + convert.convertNumberToChar(amount) + ")");

                parameters.put("docNo", vatNo);
                parameters.put("branchName", branchName);
                parameters.put("vatNo", vatNo);
                parameters.put("docDate", convertDate(onDate));
                parameters.put("remark", ramark);
                dueDate = convertDate(dueDate);

                parameters.put("onDate", onDate);
                parameters.put("CustCode", CustCode);
                parameters.put("CustName", CustName);
                parameters.put("CustAddr", CustAddr);
                parameters.put("CustTel", CustTel);
                parameters.put("CustFax", CustFax);
                parameters.put("dueDate", dueDate);
                parameters.put("crTerm", crTerm);
                parameters.put("OnTime", OnTime);
                parameters.put("MacNo", MacNo);
                parameters.put("RegNo", RegNo);
                parameters.put("RefNo", RefNo);
                parameters.put("Cashier", Cashier);

                Float amt = Float.parseFloat(amount);
                Float dis = Float.parseFloat(discount);
                Float ear = Float.parseFloat(earnest);
                Float ser = Float.parseFloat(service);
                Float subt = Float.parseFloat(subtotal);
                Float vt = Float.parseFloat(vat);

                parameters.put("discount", doubleFmt.format(dis));
                parameters.put("earnest", doubleFmt.format(ear));
                parameters.put("service", doubleFmt.format(ser));
                parameters.put("subtotal", doubleFmt.format(subt));
                parameters.put("vat", doubleFmt.format(vt));
                parameters.put("amount", doubleFmt.format(amt));
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(getClass().getResource("/report/file/debtVat.jasper"));

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, mysql.getConnection());
                JasperViewer v = new JasperViewer(jasperPrint, false);
                JDialog j = new JDialog(new JFrame(), true);
                j.setTitle("Print");
                j.setSize(1024, 768);
                j.getContentPane().add(v.getContentPane());
                j.setLocationRelativeTo(null);
                j.setVisible(true);
                v.setTitle("Report...");
            } catch (HeadlessException | NumberFormatException | JRException e) {
                MSG.ERR(e.getMessage());
                AppLogUtil.log(ViewReport.class, "error", e);
            }
        } else {
            MSG.ERR(null, "ไมพบข้อมูลที่ต้องการพิมพ์");
        }

        mysql.close();
    }

    public void printReportPVatDaily(String str, String end) {
        String date1 = "";
        String date2 = "";
        String header = "รายงานการพิมพ์ใบกำกับภาษี / ใบเสร็จรับเงิน ประจำวันที่ " + str + " - " + end;

        String branchName = "";
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            try {
                String sqlBranch = "SELECT * FROM branch limit 1";
                ResultSet rs = mysql.getConnection().createStatement().executeQuery(sqlBranch);
                if (rs.next()) {
                    branchName = rs.getString("Name");
                }
            } catch (SQLException e) {
                MSG.ERR(e.getMessage());
                AppLogUtil.log(ViewReport.class, "error", e);
            }

            try {
                Date dates = outFmt.parse(str);
                date1 = inFmt.format(dates);

                dates = outFmt.parse(end);
                date2 = inFmt.format(dates);
            } catch (ParseException e) {
            }
            try {
                Map parameters = new HashMap();
                parameters.put("header", header);
                parameters.put("branchName", branchName);
                parameters.put("date1", date1);
                parameters.put("date2", date2);

                JasperReport jasperReport = null;
                try {
                    jasperReport = (JasperReport) JRLoader.loadObject(getClass().getResource("/report/file/PVatDaily.jasper"));
                } catch (JRException e) {
                    MSG.ERR(null, e.getMessage());
                }

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, mysql.getConnection());
                int pageSize = jasperPrint.getPages().size();
                if (pageSize > 0) {
                    JasperViewer v = new JasperViewer(jasperPrint, false);
                    JDialog j = new JDialog(new JFrame(), true);
                    j.setTitle("Print");
                    j.setSize(1024, 768);
                    j.getContentPane().add(v.getContentPane());
                    j.setLocationRelativeTo(null);
                    j.setVisible(true);
                    v.setTitle("Report...");

                } else {
                    MSG.ERR(null, "ไม่พบข้อมูลที่ต้องการพิมพ์");
                }

                mysql.close();
            } catch (HeadlessException | JRException e) {
                MSG.ERR(e.getMessage());
            }
        } catch (Exception e) {
            MSG.ERR(e.toString());
        } finally {
            mysql.close();
        }

    }

    public void printReportIVatDaily(String str, String end) {
        ResultSet rs;
        String date1 = "";
        String date2 = "";
        String header = "รายงานการพิมพ์ใบกำกับภาษี / ใบแจ้งหนี้ ประจำวันที่ " + str + " - " + end;
        String sqlBranch = "SELECT * FROM branch limit 1 ";
        String branchName = "";

        MySQLConnect mysql = new MySQLConnect();
        try {
            rs = mysql.getConnection().createStatement().executeQuery(sqlBranch);
            if (rs.next()) {
                branchName = rs.getString("Name");
            }
            rs.close();
        } catch (SQLException e) {
        }

        Date dates;
        try {
            dates = outFmt.parse(str);
            date1 = inFmt.format(dates);

            dates = outFmt.parse(end);
            date2 = inFmt.format(dates);
        } catch (ParseException e) {
        }

        try {
            Map parameters = new HashMap();
            parameters.put("header", header);
            parameters.put("branchName", branchName);
            parameters.put("date1", date1);
            parameters.put("date2", date2);

            JasperReport jasperReport = null;
            try {
                jasperReport = (JasperReport) JRLoader.loadObject(getClass().getResource("/report/file/IVatDaily.jasper"));
            } catch (JRException e) {
                MSG.ERR(null, e.getMessage());
            }

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, mysql.getConnection());
            int pageSize = jasperPrint.getPages().size();
            if (pageSize > 0) {
                JasperViewer v = new JasperViewer(jasperPrint, false);
                JDialog j = new JDialog(new JFrame(), true);
                j.setTitle("Print");
                j.setSize(1024, 768);
                j.getContentPane().add(v.getContentPane());
                j.setLocationRelativeTo(null);
                j.setVisible(true);
                v.setTitle("Report...");

            } else {
                MSG.WAR(null, "ไม่พบข้อมูลที่ต้องการพิมพ์");
            }
        } catch (HeadlessException | JRException e) {
            MSG.ERR(e.getMessage());
        } finally {
            mysql.close();
        }

    }

    public String convertDate(String convert) {
        String output = "";
        try {
            Date date = new Date();
            date = inFmt.parse(convert);
            output = outFmt.format(date);

        } catch (Exception e) {
        }
        return output;
    }

    public void printCompile() {
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            Map parameters = new HashMap();
            parameters.put("branchName", "");
            parameters.put("month", "");
            parameters.put("sql", "");
            parameters.put("tax", "");
            parameters.put("companyName", "");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(getClass().getResource("/report/file/stockInhand_balanceVender.jasper"));

            JasperFillManager.fillReport(jasperReport, parameters, mysql.getConnection());
        } catch (JRException e) {
            AppLogUtil.log(ViewReport.class, "error", e);
        } finally {
            mysql.close();
        }
    }

}
