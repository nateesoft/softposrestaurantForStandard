package com.softpos.pos.core.controller;

import com.softpos.printer.control.PPrint;
import com.softpos.util.ThaiUtil;
import com.softpos.pos.core.model.POSHWSetup;
import com.softpos.constants.CreditRec;
import com.softpos.constants.HourlyRec;
import com.softpos.constants.PublicVar;
import com.softpos.constants.CreditPaymentRec;
import com.softpos.constants.PluRec;
import com.softpos.pos.core.model.BalanceSetBean;
import com.softpos.pos.core.model.CompanyBean;
import com.softpos.pos.core.model.PSetBean;
import com.softpos.pos.core.model.ProductBean;
import com.softpos.pos.core.model.TranRecord;
import com.softpos.connection.database.MySQLConnect;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import com.softpos.util.AppLogUtil;

public class PUtility {

    static DecimalFormat DecFmt = new DecimalFormat("#######0.00");
    private final MySQLConnect mysqlConnect = new MySQLConnect();
    private final PosControl PosControl = AppContext.getPosControl();

    private List<PSetBean> getPSetByPCode(String TempCode) {
        List<PSetBean> listBean = new ArrayList<>();

        mysqlConnect.open(PUtility.class);
        try {
            String sql = "select * from pset where pcode='" + TempCode + "'";
            try (ResultSet rs = mysqlConnect.executeQuery(sql)) {
                while (rs.next()) {
                    PSetBean bean = new PSetBean();
                    bean.setPcode(rs.getString("PCode"));
                    bean.setPsubcode(rs.getString("PSubCode"));
                    bean.setPsubQTY(rs.getDouble("PSubQty"));

                    listBean.add(bean);
                }
                rs.close();
            }
        } catch (SQLException e) {            
            AppLogUtil.log(PUtility.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(PUtility.class);
        }

        return listBean;
    }

    private List<BalanceSetBean> getBalanceSetByPCodeRIndex(String XCode, String r_index) {
        List<BalanceSetBean> listBean = new ArrayList<>();

        
        mysqlConnect.open(PUtility.class);
        try {
            String sql = "select * from balanceset "
                    + "where r_plucode='" + XCode + "' "
                    + "and r_index='" + r_index + "' ";
            try (Statement stmt = mysqlConnect.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    BalanceSetBean bean = new BalanceSetBean();
                    bean.setR_psubcode(rs.getString("r_psubcode"));
                    bean.setR_setqty(rs.getDouble("r_setqty"));

                    listBean.add(bean);
                }
                rs.close();
                stmt.close();
            }
        } catch (SQLException e) {
            AppLogUtil.log(PUtility.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(PUtility.class);
        }

        return listBean;
    }
    Font myfont = new Font("Tahoma", Font.PLAIN, 14);
    static SimpleDateFormat Dateft = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    SimpleDateFormat SqlDateFmt = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    SimpleDateFormat DateFmt = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    SimpleDateFormat TimeFmt = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
    Date date = new Date();

    public int GetActionMon(Date EndofdayDate) {
        CompanyBean companyBean = PosControl.getDataCompany();
        int RetVal;
        SimpleDateFormat XYear = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        SimpleDateFormat XMonth = new SimpleDateFormat("MM", Locale.ENGLISH);
        String TempYear = XYear.format(companyBean.getAccterm());
        String CurYear = XYear.format(EndofdayDate);
        String CurMonth = XMonth.format(EndofdayDate);
        if (TempYear.equals(CurYear)) {
            RetVal = Integer.parseInt(CurMonth) + 12;
        } else {
            if (Integer.parseInt(CurYear) == Integer.parseInt(TempYear) - 1) {
                RetVal = Integer.parseInt(CurMonth);
            } else {
                RetVal = 0;
            }
        }
        return RetVal;
    }

    public static boolean CheckSaleDateOK() {
        return true;
    }

    public String GetStkCode() {
        CompanyBean companyBean = PosControl.getDataCompany();
        return companyBean.getPosStock();
    }

    public static String GetStockOnLine() {
        return "N";
    }

    public Boolean CheckStockOK(String TempCode, double TempQty) {
        String T_Stk = GetStkCode();
        Boolean RetVal = true;
        if (PublicVar.CheckStockOnLine.equals("Y")) {
            //Date date = new Date();
            Boolean StkProc = false;
            Boolean SetProc = false;
            String PName = "";
            int TempAct = GetActionMon(new Date());
            String T_Mon = "bqty" + String.valueOf(TempAct);

            
            mysqlConnect.open(PUtility.class);
            try {
                try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                    String SqlQuery = "select pdesc,pstock,pset from product where pcode='" + TempCode + "' limit 1";
                    try (ResultSet rs = stmt.executeQuery(SqlQuery)) {
                        if (rs.next()) {
                            PName = rs.getString("pdesc");
                            StkProc = rs.getString("pstock").equals("Y");
                            SetProc = rs.getString("pset").equals("Y");
                        }
                        rs.close();
                        stmt.close();
                    }
                }
            } catch (SQLException e) {
                AppLogUtil.log(PUtility.class, "error", e);
            } finally {
                mysqlConnect.closeConnection(PUtility.class);
            }

            if (StkProc) {
                mysqlConnect.open(PUtility.class);
                try {
                    try (Statement stmt2 = mysqlConnect.getConnection().createStatement()) {
                        String LoadTableFile = "select bpcode from stkfile where (bpcode='" + TempCode + "') and (bstk='" + T_Stk + "') limit 1 ";
                        try (ResultSet rec2 = stmt2.executeQuery(LoadTableFile)) {
                            if (rec2.next()) {
                                double OnHand = rec2.getDouble(T_Mon);
                                if (OnHand >= TempQty) {
                                    RetVal = true;
                                } else {
                                    System.err.println("(" + PName + ") ปริมาณสินค้าคงเหลือในคลังสินค้าน้อยกว่าจำนวนที่ทำการขาย !!!" + "\n...ปริมาณคงเหลือ = " + OnHand);
                                    RetVal = false;
                                }
                            } else {
                                System.err.println("(" + PName + ") ปริมาณสินค้าคงเหลือในคลังสินค้าน้อยกว่าจำนวนที่ทำการขาย !!!" + "\n...ปริมาณคงเหลือ = 0");
                                RetVal = false;
                            }
                            rec2.close();
                            stmt2.close();
                        }
                    }
                } catch (SQLException e) {
                    AppLogUtil.log(PUtility.class, "error", e);
                } finally {
                    mysqlConnect.closeConnection(PUtility.class);
                }
            }
            if (SetProc) {
                ProductControl productControl = AppContext.getProductControl();
                List<PSetBean> listPsetBean = getPSetByPCode(TempCode);
                for (PSetBean psetBean : listPsetBean) {
                    RetVal = true;
                    ProductBean proBean = productControl.getProductByPCode(psetBean.getPsubcode());
                    StkProc = "Y".equals(proBean.getPStock());

                    if (StkProc) {
                        mysqlConnect.open(PUtility.class);
                        try {
                            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                                String sql = "select 1 from stkfile where (bpcode='" + psetBean.getPsubcode() + "') and (bstk='" + T_Stk + "') limit 1";
                                try (ResultSet rs = stmt.executeQuery(sql)) {
                                    if (rs.next()) {
                                        double OnHand = rs.getDouble(T_Mon);
                                        if (OnHand >= TempQty) {
                                            RetVal = true;
                                        } else {
                                            System.err.println("(" + proBean.getPDesc() + ") ปริมาณสินค้าคงเหลือในคลังสินค้าน้อยกว่าจำนวนที่ทำการขาย !!!" + "\n...ปริมาณคงเหลือ = " + OnHand);
                                            RetVal = false;
                                            break;
                                        }
                                    } else {
                                        System.err.println("(" + proBean.getPDesc() + ") ปริมาณสินค้าคงเหลือในคลังสินค้าน้อยกว่าจำนวนที่ทำการขาย !!!" + "\n...ปริมาณคงเหลือ = 0");
                                        RetVal = false;
                                        break;
                                    }
                                    rs.close();
                                    stmt.close();
                                }
                            }
                        } catch (SQLException e) {
                            AppLogUtil.log(PUtility.class, "error", e);
                        } finally {
                            mysqlConnect.closeConnection(PUtility.class);
                        }
                    }
                }
            }
        }
        return RetVal;
    }

    public Boolean SeekStkFile(String TempCode, String T_Stk) {
        Boolean RetVal = false;

        
        mysqlConnect.open(PUtility.class);
        try {
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                String LoadTableFile = "select bpcode from stkfile where (bpcode='" + TempCode + "') and (bstk='" + T_Stk + "') limit 1 ";
                try (ResultSet rs = stmt.executeQuery(LoadTableFile)) {
                    RetVal = rs.next();
                    rs.close();
                }
                stmt.close();
            }
        } catch (SQLException e) {
            AppLogUtil.log(PUtility.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(PUtility.class);
        }

        return RetVal;
    }

    public void ProcessStockOut(String DocNo, String StkCode, String PCode, Date TDate,
            String Stk_Remark, Double Qty, Double Amount, String UserPost, String PStock,
            String PSet, String r_index, String SaleOrRefund) {
        SimpleDateFormat SqlDateFmt = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        SimpleDateFormat TimeFmt = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        Date date = new Date();
        String TempCode = PCode;
        Double TempQty = Qty;
        Double TempAmt = Amount;
        String T_Rem = Stk_Remark;
        Boolean StkProc;
        Boolean SetProc;
        Boolean SelectSet = false;
        StkProc = PStock.equals("Y");
        if (PSet.equals("Y")) {
            SetProc = true;
            SelectSet = CheckPSetSelect(PCode);
        } else {
            SetProc = false;
        }

        if (StkProc) {
            
            mysqlConnect.open(PUtility.class);
            try {
                String sql = "insert into stcard (s_date,s_no,s_stk,s_pcode,s_que,s_in,s_incost,"
                        + "s_out,s_outcost,s_rem,s_user,s_entrydate,s_entrytime) "
                        + "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement prm = mysqlConnect.getConnection().prepareStatement(sql);
                prm.setString(1, SqlDateFmt.format(date));
                prm.setString(2, DocNo);
                prm.setString(3, StkCode);
                prm.setString(4, TempCode);
                prm.setInt(5, 1);
                prm.setDouble(6, 0);
                prm.setDouble(7, 0);
                prm.setDouble(8, TempQty);
                prm.setDouble(9, TempAmt);
                prm.setString(10, T_Rem);
                prm.setString(11, UserPost); //User

                prm.setString(12, SqlDateFmt.format(date));
                prm.setString(13, TimeFmt.format(date));
                prm.executeUpdate();
                prm.close();
            } catch (SQLException e) {
                AppLogUtil.log(PUtility.class, "error", e);
            } finally {
                mysqlConnect.closeConnection(PUtility.class);
            }

            int TempAct = GetActionMon(TDate);
            if (!SeekStkFile(TempCode, StkCode)) {
                mysqlConnect.open(PUtility.class);
                try {
                    PreparedStatement prm4 = mysqlConnect.getConnection().prepareStatement("insert into stkfile (bpcode,bstk) values (?,?)");
                    prm4.setString(1, TempCode);
                    prm4.setString(2, StkCode);
                    prm4.executeUpdate();
                    prm4.close();
                } catch (SQLException e) {
                    
                    AppLogUtil.log(PUtility.class, "error", e);
                } finally {
                    mysqlConnect.closeConnection(PUtility.class);
                }

            }
            for (int i = TempAct; i <= 24; i++) {
                String T_Mon = "bqty" + String.valueOf(i);
                String InsertQuery4 = "update stkfile set " + T_Mon + "=" + T_Mon + "-? where (bpcode=?) and (bstk=?)";
                mysqlConnect.open(PUtility.class);
                try {
                    PreparedStatement prm4 = mysqlConnect.getConnection().prepareStatement(InsertQuery4);
                    prm4.setDouble(1, TempQty);
                    prm4.setString(2, TempCode);
                    prm4.setString(3, StkCode);
                    prm4.executeUpdate();
                    prm4.close();
                } catch (SQLException e) {
                    AppLogUtil.log(PUtility.class, "error", e);
                } finally {
                    mysqlConnect.closeConnection(PUtility.class);
                }
            }
        }
        if (SetProc) {
            if (!SelectSet) {
                ProcessSetUpdateStockOut(DocNo, StkCode, PCode, TDate, Stk_Remark, Qty, UserPost);
            } else {
                if (SaleOrRefund.equals("1")) {
                    ProcessSelectSetUpdateStockOut(DocNo, StkCode, PCode, TDate, Stk_Remark, Qty, UserPost, r_index);
                } else if (SaleOrRefund.equals("2")) {
                    ProcessSelectSetUpdateStockOutRefund(DocNo, StkCode, PCode, TDate, Stk_Remark, Qty, UserPost, r_index);
                }

            }
        }
    }

    public boolean CheckPSetSelect(String PCode) {
        boolean RetValue = false;
        
        mysqlConnect.open(PUtility.class);
        try {
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                String sql = "select pcode from product where pcode='" + PublicVar.P_Code + "' and pactive='Y' limit 1";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    RetValue = rs.next();
                    rs.close();
                }
                stmt.close();
            }
        } catch (SQLException e) {
            AppLogUtil.log(PUtility.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(PUtility.class);
        }

        return RetValue;
    }

    public void ProcessSetUpdateStockOut(String DocNo, String StkCode, String XCode, Date TDate, String StkRemark, Double XQty, String UserPost) {
        SimpleDateFormat SqlDateFmt = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        SimpleDateFormat TimeFmt = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        Date date = new Date();

        List<PSetBean> psetList = getPSetByPCode(XCode);
        ProductControl productControl = AppContext.getProductControl();
        for (PSetBean psetBean : psetList) {
            String TempCode = psetBean.getPsubcode();
            Double TempQty = psetBean.getPsubQTY() * XQty;
            String T_Rem = StkRemark;

            ProductBean productBean = productControl.getProductByPCode(TempCode);
            Boolean StkProc = productBean.getPStock().equals("Y");
            Double TempAmt = productBean.getPPrice11() * XQty;

            if (StkProc) {
                
                mysqlConnect.open(PUtility.class);
                try {
                    String InsertQuery = "insert into stcard (s_date,s_no,s_stk,s_pcode,s_que,s_in,s_incost,"
                            + "s_out,s_outcost,s_rem,s_user,s_entrydate,s_entrytime) "
                            + "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement prm = mysqlConnect.getConnection().prepareStatement(InsertQuery);
                    prm.setString(1, SqlDateFmt.format(TDate));
                    prm.setString(2, DocNo + "@" + XCode);
                    prm.setString(3, StkCode);
                    prm.setString(4, TempCode);
                    prm.setInt(5, 1);
                    prm.setDouble(6, 0);
                    prm.setDouble(7, 0);
                    prm.setDouble(8, TempQty);
                    prm.setDouble(9, TempAmt);
                    prm.setString(10, T_Rem);
                    prm.setString(11, UserPost); //User
                    prm.setString(12, SqlDateFmt.format(date));
                    prm.setString(13, TimeFmt.format(date));
                    prm.executeUpdate();
                    prm.close();
                } catch (SQLException e) {
                    AppLogUtil.log(PUtility.class, "error", e);
                } finally {
                    mysqlConnect.closeConnection(PUtility.class);
                }

                int TempAct = GetActionMon(TDate);
                if (!SeekStkFile(TempCode, StkCode)) {
                    mysqlConnect.open(PUtility.class);
                    try {
                        String InsertQuery4 = "insert into stkfile (bpcode,bstk) values (?,?)";
                        PreparedStatement prm4 = mysqlConnect.getConnection().prepareStatement(InsertQuery4);
                        prm4.setString(1, TempCode);
                        prm4.setString(2, StkCode);
                        prm4.executeUpdate();
                        prm4.close();
                    } catch (SQLException e) {
                        AppLogUtil.log(PUtility.class, "error", e);
                    } finally {
                        mysqlConnect.closeConnection(PUtility.class);
                    }
                }

                mysqlConnect.open(PUtility.class);
                try {
                    String sql = "update stkfile set bqty?=bqty?-? where (bpcode=?) and (bstk=?)";
                    PreparedStatement prmt = mysqlConnect.getConnection().prepareStatement(sql);
                    for (int i = TempAct; i <= 24; i++) {
                        prmt.setInt(1, i);
                        prmt.setInt(2, i);
                        prmt.setDouble(3, TempQty);
                        prmt.setString(4, TempCode);
                        prmt.setString(5, StkCode);

                        prmt.addBatch();
                    }
                    prmt.executeUpdate();
                    prmt.close();
                } catch (SQLException e) {
                    AppLogUtil.log(PUtility.class, "error", e);
                } finally {
                    mysqlConnect.closeConnection(PUtility.class);
                }
            }
        }
    }

    public void ProcessSelectSetUpdateStockOut(String DocNo, String StkCode, String XCode, Date TDate, String StkRemark, Double XQty, String UserPost, String r_index) {
        SimpleDateFormat SqlDateFmt = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        SimpleDateFormat TimeFmt = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        Date date = new Date();

        ProductControl productControl = AppContext.getProductControl();
        List<BalanceSetBean> listBalanceSet = getBalanceSetByPCodeRIndex(XCode, r_index);
        for (BalanceSetBean bean : listBalanceSet) {
            String TempCode = bean.getR_psubcode();
            Double TempQty = bean.getR_setqty() * XQty;
            String T_Rem = StkRemark;

            ProductBean proBean = productControl.getProductByPCode(TempCode);
            Boolean StkProc = proBean.getPStock().equals("Y");
            Double TempAmt = proBean.getPPrice11() * XQty;

            if (StkProc) {
                
                mysqlConnect.open(ProductControl.class);
                try {
                    String InsertQuery = "insert into stcard (s_date,s_no,s_stk,s_pcode,s_que,s_in,s_incost,"
                            + "s_out,s_outcost,s_rem,s_user,s_entrydate,s_entrytime) "
                            + "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement prm = mysqlConnect.getConnection().prepareStatement(InsertQuery);
                    prm.setString(1, SqlDateFmt.format(TDate));
                    prm.setString(2, DocNo + "@" + XCode);
                    prm.setString(3, StkCode);
                    prm.setString(4, TempCode);
                    prm.setInt(5, 1);
                    prm.setDouble(6, 0);
                    prm.setDouble(7, 0);
                    prm.setDouble(8, TempQty);
                    prm.setDouble(9, TempAmt);
                    prm.setString(10, T_Rem);
                    prm.setString(11, UserPost); //User
                    prm.setString(12, SqlDateFmt.format(date));
                    prm.setString(13, TimeFmt.format(date));
                    prm.executeUpdate();
                    prm.close();
                } catch (SQLException e) {
                    AppLogUtil.log(PUtility.class, "error", e);
                } finally {
                    mysqlConnect.closeConnection(PUtility.class);
                }

                int TempAct = GetActionMon(TDate);
                if (!SeekStkFile(TempCode, StkCode)) {
                    mysqlConnect.open(ProductControl.class);
                    try {
                        String sql = "insert into stkfile (bpcode,bstk) values (?,?)";
                        PreparedStatement prmt = mysqlConnect.getConnection().prepareStatement(sql);
                        prmt.setString(1, TempCode);
                        prmt.setString(2, StkCode);
                        prmt.executeUpdate();
                        prmt.close();
                    } catch (SQLException e) {
                        AppLogUtil.log(PUtility.class, "error", e);
                    } finally {
                        mysqlConnect.closeConnection(PUtility.class);
                    }

                    mysqlConnect.open(ProductControl.class);
                    try {
                        String sql = "update stkfile set bqty?=bqty?-? where (bpcode=?) and (bstk=?)";
                        PreparedStatement prmt = mysqlConnect.getConnection().prepareStatement(sql);
                        for (int i = TempAct; i <= 24; i++) {
                            prmt.setInt(1, i);
                            prmt.setInt(2, i);
                            prmt.setDouble(3, TempQty);
                            prmt.setString(4, TempCode);
                            prmt.setString(5, StkCode);

                            prmt.addBatch();
                        }
                        prmt.executeUpdate();
                        prmt.close();
                    } catch (SQLException e) {
                        AppLogUtil.log(PUtility.class, "error", e);
                    } finally {
                        mysqlConnect.closeConnection(PUtility.class);
                    }
                }
            }
        }
    }

    public void ProcessSelectSetUpdateStockOutRefund(String DocNo, String StkCode, String XCode, Date TDate, String StkRemark, Double XQty, String UserPost, String r_index) {
        SimpleDateFormat SqlDateFmt = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        SimpleDateFormat TimeFmt = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        String XDocNo = DocNo.substring(1, 8);
        Date date = new Date();

        
        mysqlConnect.open(PUtility.class);
        try {
            Statement stmt = mysqlConnect.getConnection().createStatement();
            String SQLQuery = "select * from t_saleset where r_plucode='" + XCode + "' and r_index='" + r_index + "'  and r_refno='" + XDocNo + "' ";
            ResultSet rs = stmt.executeQuery(SQLQuery);
            while (rs.next()) {
                String TempCode = rs.getString("r_psubcode");
                Double TempQty = rs.getDouble("r_setqty") * XQty;
                Double TempAmt = 0.0;
                String T_Rem = StkRemark;
                Boolean StkProc = false;
                Statement stmt3 = mysqlConnect.getConnection().createStatement();
                String LoadTableFile = "select pstock,pprice11 from product where pcode='" + TempCode + "' limit 1";
                ResultSet rec3 = stmt3.executeQuery(LoadTableFile);
                if (rec3.next()) {
                    StkProc = rec3.getString("pstock").equals("Y");
                    TempAmt = rec3.getDouble("pprice11") * XQty;
                }
                rec3.close();
                stmt3.close();
                if (StkProc) {
                    Statement stmt2 = mysqlConnect.getConnection().createStatement();
                    String InsertQuery = "insert into stcard (s_date,s_no,s_stk,s_pcode,s_que,s_in,s_incost,"
                            + "s_out,s_outcost,s_rem,s_user,s_entrydate,s_entrytime) "
                            + "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement prm = mysqlConnect.getConnection().prepareStatement(InsertQuery);
                    prm.setString(1, SqlDateFmt.format(TDate));
                    prm.setString(2, DocNo + "@" + XCode);
                    prm.setString(3, StkCode);
                    prm.setString(4, TempCode);
                    prm.setInt(5, 1);
                    prm.setDouble(6, 0);
                    prm.setDouble(7, 0);
                    prm.setDouble(8, TempQty);
                    prm.setDouble(9, TempAmt);
                    prm.setString(10, T_Rem);
                    prm.setString(11, UserPost); //User

                    prm.setString(12, SqlDateFmt.format(date));
                    prm.setString(13, TimeFmt.format(date));
                    prm.executeUpdate();
                    prm.close();
                    stmt2.close();
                    int TempAct = GetActionMon(TDate);
                    if (!SeekStkFile(TempCode, StkCode)) {
                        String InsertQuery4 = "insert into stkfile (bpcode,bstk) values (?,?)";
                        PreparedStatement prm4 = mysqlConnect.getConnection().prepareStatement(InsertQuery4);
                        prm4.setString(1, TempCode);
                        prm4.setString(2, StkCode);
                        prm4.executeUpdate();
                        prm4.close();
                    }
                    for (int i = TempAct; i <= 24; i++) {
                        String T_Mon = "bqty" + String.valueOf(i);
                        String InsertQuery4 = "update stkfile set " + T_Mon + "=" + T_Mon + "-? where (bpcode=?) and (bstk=?)";
                        PreparedStatement prm4 = mysqlConnect.getConnection().prepareStatement(InsertQuery4);
                        prm4.setDouble(1, TempQty);
                        prm4.setString(2, TempCode);
                        prm4.setString(3, StkCode);
                        prm4.executeUpdate();
                        prm4.close();
                    }
                }
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            AppLogUtil.log(PUtility.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(PUtility.class);
        }
    }

    public boolean CheckCashierClose(String XUser) {
        boolean ReturnVal = false;
        
        mysqlConnect.open(PUtility.class);
        try {
            Statement stmt = mysqlConnect.getConnection().createStatement();
            String SQLQuery = "select b_roundclose from billno where b_cashier=" + "'" + XUser + "' limit 1";
            ResultSet rs = stmt.executeQuery(SQLQuery);
            if (rs.next()) {
                ReturnVal = rs.getString("b_roundclose").equals("Y");
            } else {
                ReturnVal = false;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            AppLogUtil.log(PUtility.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(PUtility.class);
        }

        return ReturnVal;
    }

    public String ConvertReal(String TStr) {
        String TempStr = "";
        if (TStr.equals("")) {
            TStr = "0";
        }
        if (TStr.indexOf(",") > 0) {
            for (int i = 0; i < TStr.length(); i++) {
                char ch = TStr.charAt(i);
                String StrCh = "";
                StrCh = StrCh + ch;
                if (!StrCh.equals(",")) {
                    TempStr = TempStr + StrCh;
                }
            }
        } else {
            TempStr = TStr;
        }
        return TempStr;
    }

    public Boolean ChkValidDate(Date tdate) {
        Boolean ReturnValue = false;
        try {
            Dateft.format(tdate);
            ReturnValue = true;
        } catch (Exception e) {
            AppLogUtil.log(PUtility.class, "error", e);
        }
        return ReturnValue;
    }

    public Boolean ChkDate(String tdate) {
        SimpleDateFormat DateFmt = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Boolean ReturnValue;
        Date TempDate;
        try {
            TempDate = DateFmt.parse(tdate);
            if (TempDate.getYear() > 2200) {
                System.err.println("กรุณาป้อนวันที่ให้ถูกต้อง...EXP(dd/MM/yyyy) โดยป้อนปีเป็นปี คศ.เท่านั้น...");
                ReturnValue = false;
            } else {
                ReturnValue = true;
            }
        } catch (ParseException ex) {
            System.err.println("กรุณาป้อนวันที่ให้ถูกต้อง...EXP(dd/MM/yyyy)");
            AppLogUtil.log(PUtility.class, "error", ex);
            ReturnValue = false;
        }

        return ReturnValue;
    }

    public String Addzero(String Str, int Len) {
        String TempStr = "";
        String ReturnStr;
        int StrLen = Str.trim().length();
        if (StrLen < Len) {
            for (int i = 1; i <= Len - StrLen; i++) {
                TempStr = TempStr + "0";
            }
            ReturnStr = TempStr + Str.trim();
        } else {
            ReturnStr = Str;
        }
        return ReturnStr;
    }

    public boolean ChkNumValue(String Str) {
        String NumList[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "-", ","};
        char ch;
        String Tmp;
        int Num = 0;
        int Chk;
        for (int j = 0; j < Str.length(); j++) {
            ch = Str.charAt(j);
            Tmp = "";
            Tmp = Tmp + ch;

            Chk = 0;
            for (String NumList1 : NumList) {
                if (NumList1.equals(Tmp)) {
                    Chk++;
                }
            }
            if (Chk > 0) {
                Num++;
            }
        }
        return Num == Str.length() && Str.length() != 0;
    }

    public String DataFull2(String Str, int Len) {
        return TAB + Str;
    }

    public String DataFull(String Str, int Len) {
        if (Str == null) {
            return "";
        }
        String ReturnStr;
        String AddStr = "";
        if (Len < Str.length()) {
            ReturnStr = Str.substring(0, Len - 1);
        } else {
            for (int i = 1; i <= (Len - Str.length()); i++) {
                AddStr = AddStr + " ";
            }
            ReturnStr = AddStr + Str.trim();
        }

        return ReturnStr;
    }

    public String DataFullSpace(String Str, int Len) {
        if (Str == null) {
            return "";
        }
        String ReturnStr;
        String AddStr = "";
        if (Len < Str.length()) {
            ReturnStr = Str.substring(0, Len - 1);
        } else {
            for (int i = 1; i <= (Len - Str.length()); i++) {
                AddStr = AddStr + "&nbsp; ";
            }
            ReturnStr = AddStr + Str.trim();
        }

        return ReturnStr;
    }

    public String TAB = "&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;";

    public String DataFullR(String Str, int Len) {
        String ReturnStr;
        String AddStr = "";
        int List1[] = {209, 212, 213, 214, 215, 216, 217, 218, 219, 231, 232, 233, 234, 235, 236, 237};
        if (Len < Str.length()) {
            ReturnStr = Str.substring(0, Len - 1);
        } else {
            for (int i = 1; i <= (Len - Str.length()); i++) {
                AddStr = AddStr + " ";
            }

            ReturnStr = Str.trim() + AddStr;
        }
        int I = 0;
        int ICnt = 0;
        char ch;
        String TempStr = ThaiUtil.Unicode2ASCII(ReturnStr);
        while (I <= TempStr.length() - 1) {   // Check TIS Upper }

            ch = TempStr.charAt(I);
            if (PPrint.searchArray((int) ch, List1) != -1) {
                ICnt++;
            }
            I = I + 1;
        }

        if (ICnt > 0) {
            for (int i = 1; i <= ICnt; i++) {
                ReturnStr = ReturnStr + " ";
            }
        }

        return ReturnStr;

    }

    public String CurDate() {
        return Dateft.format(new Date());
    }

    public TranRecord[] addArray(TranRecord[] MyArray) {
        int aSize = MyArray.length;
        TranRecord[] NewArray;
        NewArray = new TranRecord[aSize + 1];
        for (int i = 0; i <= aSize - 1; i++) {
            NewArray[i] = MyArray[i];
        }
        MyArray = NewArray;
        return MyArray;

    }

    public CreditRec[] addCrArray(CreditRec[] MyArray) {
        int aSize = MyArray.length;
        CreditRec[] NewArray;
        NewArray = new CreditRec[aSize + 1];
        for (int i = 0; i <= aSize - 1; i++) {
            NewArray[i] = MyArray[i];
        }
        MyArray = NewArray;
        return MyArray;

    }

    public CreditPaymentRec[] addCreditArray(CreditPaymentRec[] CuArray) {
        int aSize = CuArray.length;
        CreditPaymentRec[] NewArray;
        NewArray = new CreditPaymentRec[aSize + 1];
        for (int i = 0; i <= aSize - 1; i++) {
            NewArray[i] = CuArray[i];
        }
        CuArray = NewArray;
        return CuArray;

    }

    public PluRec[] addPluArray(PluRec[] MyArray) {
        int aSize = MyArray.length;
        PluRec[] NewArray;
        NewArray = new PluRec[aSize + 1];
        for (int i = 0; i <= aSize - 1; i++) {
            NewArray[i] = MyArray[i];
        }
        MyArray = NewArray;
        return MyArray;

    }

    public HourlyRec[] addHourlyArray(HourlyRec[] MyArray) {
        int aSize = MyArray.length;
        HourlyRec[] NewArray;
        NewArray = new HourlyRec[aSize + 1];
        for (int i = 0; i <= aSize - 1; i++) {
            NewArray[i] = MyArray[i];
        }
        MyArray = NewArray;
        return MyArray;

    }

    public double RoundDecimal(double TempAmount, String RoundType) {
        double ReturnVal;
        double decimal = DecimalChk(TempAmount);
        double number = NumberChk(TempAmount);
        switch (RoundType) {
            case "U":
                if (decimal > 0) {
                    ReturnVal = number + 1;
                } else {
                    ReturnVal = number;
                }
                break;
            case "D":
                ReturnVal = number;
                break;
            case "N":
                if (decimal >= 0.50) {
                    ReturnVal = number + 1;
                } else {
                    ReturnVal = number;
                }
                break;
            case "F":
                if (decimal >= 0.75) {
                    if (decimal >= 0.87) {
                        ReturnVal = number + 1;
                    } else {
                        ReturnVal = number + 0.75;
                    }
                } else {
                    if (decimal >= 0.50) {
                        if (decimal >= 0.63) {
                            ReturnVal = number + 0.75;
                        } else {
                            ReturnVal = number + 0.50;
                        }
                    } else {
                        if (decimal >= 0.25) {
                            if (decimal >= 0.38) {
                                ReturnVal = number + 0.50;
                            } else {
                                ReturnVal = number + 0.25;
                            }
                        } else {
                            if (decimal >= 0.13) {
                                ReturnVal = number + 0.25;
                            } else {
                                ReturnVal = number;
                            }
                        }
                    }
                }
                break;
            default:
                return TempAmount;
        }

        return ReturnVal;
    }

    public double NumberChk(Double TempAmount) {
        Double ReturnVal;
        String TempNum;
        String TempDec;
        TempNum = TempAmount.toString();
        int index = TempNum.indexOf(".");
        if (index != -1) {
            TempDec = TempNum.substring(0, index);
            ReturnVal = Double.parseDouble(ConvertReal(TempDec));
        } else {
            ReturnVal = 0.0;
        }
        return ReturnVal;
    }

    public double DecimalChk(Double TempAmount) {
        Double ReturnVal;
        String TempNum;
        String TempDec;
        TempNum = TempAmount.toString();
        int index = TempNum.indexOf(".");
        if (index != -1) {
            TempDec = TempNum.substring(index + 1);
            ReturnVal = Double.parseDouble(ConvertReal(TempDec));
        } else {
            ReturnVal = 0.0;
        }
        return ReturnVal;
    }

    public String SeekGroupName(String TCode) {
        String ReturnValues = "";

        
        mysqlConnect.open(PUtility.class);
        try {
            Statement stmt = mysqlConnect.getConnection().createStatement();
            String UserGroupFile = "select groupname from groupfile where groupcode='" + TCode + "' limit 1";
            ResultSet rs = stmt.executeQuery(UserGroupFile);
            if (rs.next()) {
                ReturnValues = ThaiUtil.ASCII2Unicode(rs.getString("groupname"));
            } else {
                ReturnValues = "*****";
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            
            AppLogUtil.log(PUtility.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(PUtility.class);
        }

        return ReturnValues;
    }

    public String SeekProductName(String TCode) {
        String ReturnValues = "";
        
        mysqlConnect.open(PUtility.class);
        try {
            Statement stmt = mysqlConnect.getConnection().createStatement();
            String UserGroupFile = "select pdesc from product where pcode='" + TCode + "' limit 1";
            ResultSet rs = stmt.executeQuery(UserGroupFile);
            if (rs.next()) {
                ReturnValues = ThaiUtil.ASCII2Unicode(rs.getString("pdesc"));
            } else {
                ReturnValues = "*****";
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            
            AppLogUtil.log(PUtility.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(PUtility.class);
        }

        return ReturnValues;
    }

    public String SeekCreditName(String TCode) {
        String ReturnValues = "";
        
        mysqlConnect.open(PUtility.class);
        try {
            Statement stmt = mysqlConnect.getConnection().createStatement();
            String UserGroupFile = "select crname from creditfile where crcode='" + TCode + "' limit 1";
            ResultSet rs = stmt.executeQuery(UserGroupFile);
            if (rs.next()) {
                ReturnValues = ThaiUtil.ASCII2Unicode(rs.getString("crname"));
            } else {
                ReturnValues = "*****";
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            
            AppLogUtil.log(PUtility.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(PUtility.class);
        }

        return ReturnValues;
    }

    public String SeekCuponName(String TCode) {
        String ReturnValues = "";
        
        mysqlConnect.open(PUtility.class);
        try {
            Statement stmt = mysqlConnect.getConnection().createStatement();
            String UserGroupFile = "select cuname from cupon where cucode='" + TCode + "' limit 1";
            ResultSet rs = stmt.executeQuery(UserGroupFile);
            if (rs.next()) {
                ReturnValues = ThaiUtil.ASCII2Unicode(rs.getString("cuname"));
            } else {
                ReturnValues = "*****";
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            
            AppLogUtil.log(PUtility.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(PUtility.class);
        }

        return ReturnValues;
    }

    public String SeekArName(String TCode) {
        String ReturnValues = "";
        
        mysqlConnect.open(PUtility.class);
        try {
            Statement stmt = mysqlConnect.getConnection().createStatement();
            String UserGroupFile = "select sp_desc from custfile where sp_code='" + TCode + "' limit 1";
            ResultSet rs = stmt.executeQuery(UserGroupFile);
            if (rs.next()) {
                ReturnValues = ThaiUtil.ASCII2Unicode(rs.getString("sp_desc"));
            } else {
                ReturnValues = "*****";
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            
            AppLogUtil.log(PUtility.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(PUtility.class);
        }

        return ReturnValues;
    }

    public String SeekRegNo(String macNo) {
        POSHWSetup poshwSetup = PosControl.getData(macNo);
        return poshwSetup.getMacNo();
    }

    public String SeekPromotionName(String TCode) {
        String ReturnValues = "";
        
        mysqlConnect.open(PUtility.class);
        try {
            Statement stmt = mysqlConnect.getConnection().createStatement();
            String UserGroupFile = "select prodesc from protab where procode='" + TCode + "' limit 1";
            ResultSet rs = stmt.executeQuery(UserGroupFile);
            if (rs.next()) {
                ReturnValues = ThaiUtil.ASCII2Unicode(rs.getString("prodesc"));
            } else {
                ReturnValues = "*****";
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            
            AppLogUtil.log(PUtility.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(PUtility.class);
        }

        return ReturnValues;
    }

}
