package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.BalanceBean;
import com.softpos.pos.core.model.TableFileBean;
import database.MySQLConnect;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import util.AppLogUtil;
import util.DateConvert;
import util.MSG;

public class ServiceControl {

    private POSConfigSetup CONFIG;
    private final PosControl posControl;
    private SimpleDateFormat Timefmt = new SimpleDateFormat("HH:mm:ss");
    DecimalFormat df = new DecimalFormat("##.00");

    public ServiceControl() {
        posControl = new PosControl();
        CONFIG = POSConfigSetup.Bean();
    }

    public void updateService(String table) {
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        DateConvert dc = new DateConvert();
        try {
            BalanceControl balanceControl = new BalanceControl();
//            ArrayList<BalanceBean> dataBalance = balanceControl.getAllBalance(table);
            List<BalanceBean> dataBalance = balanceControl.getAllBalanceSum(table);
            double ServiceTotal = 0.00;
            double VatTotal = 0;
            double ServicePercent = POSConfigSetup.Bean().getP_Service();
            double ServiceAmt = 0.00;
            double vatEXCTypeServiceN = 0.00;
            String serviceYorN = "";
            double tAmount = 0;
            double totalDiscount = 0.00;
            double NetTotal = 0;
            for (int i = 0; i < dataBalance.size(); i++) {
                double ServiceLine = 0.00;
                BalanceBean balance = (BalanceBean) dataBalance.get(i);

                //ตรวจสอบสินค้าที่ Void ไปแล้ว
                if ("V".equals(balance.getR_Void())) {
                    continue;
                }
//                if (POSConfigSetup.Bean().getP_Service().equals("0")) {
//                            continue;
//                        }

                totalDiscount += balance.getR_PrAmt()
                        + balance.getR_PrSubAmt()
                        + balance.getR_PrSubBath()
                        + balance.getR_PrCuAmt()
                        + balance.getR_DiscBath();
//                
                totalDiscount = (totalDiscount);
                if (posControl.getETDPW_Active(balance.getR_ETD(), POSConfigSetup.Bean().getP_SerChkBySaleType())) {

                    //ตรวจสอบการให้ส่วนลด
                    if (balance.getR_Service().equals("Y")) {
                        String a = POSConfigSetup.Bean().getP_SerChkBySaleType();
//                        System.out.println("Processing Cal Discount type = " + a);
                        //คิดค่าบริการแบบยอด Net
                        if (POSConfigSetup.Bean().getP_ServiceType().equals("N")) {
                            String r_index = balance.getR_Index();
//                        System.out.println("r_index= " + r_index + "R_ETD = " + balance.getR_ETD());
                            String[] strs = POSConfigSetup.Bean().getP_SerChkBySaleType().split("/");
                            String serviceSaleTypeE = "";
                            String serviceSaleTypeT = "";
                            String serviceSaleTypeD = "";
                            String serviceSaleTypeP = "";
                            String serviceSaleTypeW = "";
                            for (String data : strs) {
                                serviceSaleTypeE = strs[0];
                                serviceSaleTypeT = strs[1];
                                serviceSaleTypeD = strs[2];
                                serviceSaleTypeP = strs[3];
                                serviceSaleTypeW = strs[4];
                            }
                            if (balance.getR_ETD().equals("E") && serviceSaleTypeE.equals("Y")) {
                                ServiceLine = balance.getR_Total();
                                serviceYorN = "Y";
                            } else if (balance.getR_ETD().equals("T") && serviceSaleTypeT.equals("Y")) {
                                serviceYorN = "Y";
                                ServiceLine = balance.getR_Total();
                            } else if (balance.getR_ETD().equals("D") && serviceSaleTypeD.equals("Y")) {
                                serviceYorN = "Y";
                                ServiceLine = balance.getR_Total();
                            } else if (balance.getR_ETD().equals("P") && serviceSaleTypeP.equals("Y")) {
                                serviceYorN = "Y";
                                ServiceLine = balance.getR_Total();
                            } else if (balance.getR_ETD().equals("W") && serviceSaleTypeW.equals("Y")) {
                                serviceYorN = "Y";
                                ServiceLine = balance.getR_Total();
                            } else if (balance.getR_ETD().equals("E") && serviceSaleTypeE.equals("N")) {
                                serviceYorN = "N";
                                ServiceLine = 0;
                            } else if (balance.getR_ETD().equals("T") && serviceSaleTypeT.equals("N")) {
                                serviceYorN = "N";
                                ServiceLine = 0;
                            } else if (balance.getR_ETD().equals("D") && serviceSaleTypeD.equals("N")) {
                                serviceYorN = "N";
                                ServiceLine = 0;
                            } else if (balance.getR_ETD().equals("P") && serviceSaleTypeP.equals("N")) {
                                serviceYorN = "N";
                                ServiceLine = 0;
                            } else if (balance.getR_ETD().equals("W") && serviceSaleTypeW.equals("N")) {
                                serviceYorN = "N";
                                ServiceLine = 0;
                            }
                            ServiceTotal += ServiceLine;
                        }
//                        คิดค่าบริการแบบยอด Gross
                        if (POSConfigSetup.Bean().getP_ServiceType().equals("G")) {
                            ServiceLine = balance.getR_Total();
                        }
                    }
                    if (ServiceTotal > 0 && POSConfigSetup.Bean().getP_ServiceType().equals("N")) {
                        ServiceAmt = (ServiceTotal - totalDiscount) * ServicePercent / 100;
                        ServiceAmt = ServiceAmt;
                    }
//                    คิดภาษีหรือไม่ ?
                    if (balance.getR_Vat().equals("V")) {
                        if (POSConfigSetup.Bean().getP_VatType().equals("I")) {
                            //คิดภาษีแบบ Include Vat
                            VatTotal += balance.getR_Total();
                        }
                        if (POSConfigSetup.Bean().getP_VatType().equals("E")) {
                            //คิดภาษีแบบ Exclude Vat
                            VatTotal += balance.getR_Total();
                        }

                    }

                }
                if (balance.getR_ETD().equals("T")) {
                    VatTotal += balance.getR_Total();
                }
                tAmount += balance.getR_Total();
            }
            TableFileControl tfc = new TableFileControl();
            TableFileBean tBean = tfc.getData(table);
            String sqlUpd;

            ServiceAmt = getDouble(ServiceAmt, "SERVICE");
            totalDiscount = getDouble(totalDiscount, "DISCOUNT");
            NetTotal = tAmount - totalDiscount + ServiceAmt;
            sqlUpd = "update tablefile "
                    + "set ServiceAmt = '" + ServiceAmt + "',"
                    + "TAmount='" + tAmount + "',"
                    + "NetTotal = " + NetTotal + " "
                    + "where Tcode = '" + table + "'";

            Statement stmt3 = mysql.getConnection().createStatement();
            stmt3.executeUpdate(sqlUpd);
            stmt3.close();
            // update all discount

                if (tAmount > 0 && ("00:00:00".equals(tBean.getTLoginTime()) || tBean.getTLoginTime() == null)) {
                    String sqlUpdateDate = "update tablefile set "
                            + "tlogindate='" + dc.GetCurrentDate() + "', "
                            + "tlogintime='" + Timefmt.format(new Date()) + "' "
                            + "where tcode='" + table + "'";
                    Statement stmt5 = mysql.getConnection().createStatement();
                    stmt5.executeUpdate(sqlUpdateDate);

                }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(ServiceControl.class, "error", e);
        } finally {
            mysql.close();
        }
    }

    public static double getDouble(double db, String type) {
        if (db > 0) {
            switch (type) {
                case "SERVICE":
                    if (POSConfigSetup.Bean().getP_ServiceRound().equalsIgnoreCase("U")) {
                        db = NumberControl.UP_BAHT(db);
                    }
                    if (POSConfigSetup.Bean().getP_ServiceRound().equalsIgnoreCase("D")) {
                        db = NumberControl.DOWN_BAHT(db);
                    }
                    if (POSConfigSetup.Bean().getP_ServiceRound().equalsIgnoreCase("O")) {
                        return db;
                    }
                    if (POSConfigSetup.Bean().getP_ServiceRound().equalsIgnoreCase("N")) {
                        db = NumberControl.UP_DOWN_NATURAL_BAHT(db);
                    }
                    if (POSConfigSetup.Bean().getP_ServiceRound().equalsIgnoreCase("F")) {
                        db = NumberControl.UP_DOWN_25NewTotal(db);
                    } else {
                        return db;
                    }
                    break;
                case "DISCOUNT":
                    if (POSConfigSetup.Bean().getP_DiscRound().equalsIgnoreCase("U")) {
                        db = NumberControl.UP_BAHT(db);
                    }
                    if (POSConfigSetup.Bean().getP_DiscRound().equalsIgnoreCase("D")) {
                        db = NumberControl.DOWN_BAHT(db);
                    }
                    if (POSConfigSetup.Bean().getP_DiscRound().equalsIgnoreCase("O")) {
                        return db;
                    }
                    if (POSConfigSetup.Bean().getP_DiscRound().equalsIgnoreCase("N")) {
                        db = NumberControl.UP_DOWN_NATURAL_BAHT(db);
                    }
                    if (POSConfigSetup.Bean().getP_DiscRound().equalsIgnoreCase("F")) {
                        db = NumberControl.UP_DOWN_25(db);
                    } else {
                        return db;
                    }
                    break;
                case "PAYMENT":
                    if (POSConfigSetup.Bean().getP_PayBahtRound().equalsIgnoreCase("U")) {
                        db = NumberControl.UP_BAHT(db);
                    }
                    if (POSConfigSetup.Bean().getP_PayBahtRound().equalsIgnoreCase("D")) {
                        db = NumberControl.DOWN_BAHT(db);
                    }
                    if (POSConfigSetup.Bean().getP_PayBahtRound().equalsIgnoreCase("O")) {
                        return db;
                    }
                    if (POSConfigSetup.Bean().getP_PayBahtRound().equalsIgnoreCase("N")) {
                        db = NumberControl.UP_DOWN_NATURAL_BAHT(db);
                    }
                    if (POSConfigSetup.Bean().getP_PayBahtRound().equalsIgnoreCase("F")) {
                        db = NumberControl.UP_DOWN_25(db);
                    } else {
                        return db;
                    }
                    break;
            }
        }

        return db;
    }

}
