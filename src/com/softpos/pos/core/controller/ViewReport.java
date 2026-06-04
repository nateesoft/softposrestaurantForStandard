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
import com.softpos.util.AppLogUtil;
import com.softpos.util.MSG;

public final class ViewReport {

    private DecimalFormat doubleFmt = new DecimalFormat("##,###,##0.00");
    private SimpleDateFormat outFmt = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    private SimpleDateFormat inFmt = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    private final MySQLConnect mysqlConnect = new MySQLConnect();

    public void printReportPVatDaily(String str, String end) {
        String date1 = "";
        String date2 = "";
        String header = "รายงานการพิมพ์ใบกำกับภาษี / ใบเสร็จรับเงิน ประจำวันที่ " + str + " - " + end;

        String branchName = "";
        try {
            mysqlConnect.open(this.getClass());
            try {
                String sqlBranch = "SELECT * FROM branch limit 1";
                ResultSet rs = mysqlConnect.executeQuery(sqlBranch);
                if (rs.next()) {
                    branchName = rs.getString("Name");
                }
            } catch (SQLException e) {

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

                }

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, mysqlConnect.getConnection());
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

                mysqlConnect.closeConnection(this.getClass());
            } catch (HeadlessException | JRException e) {

            }
        } catch (Exception e) {
            System.err.println(e.toString());
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

    }

}
