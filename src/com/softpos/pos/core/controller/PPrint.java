package com.softpos.pos.core.controller;


import com.softpos.util.ThaiUtil;
import com.softpos.pos.core.model.POSConfigSetup;
import com.softpos.pos.core.model.POSHWSetup;
import com.softpos.constants.CreditRec;
import com.softpos.constants.PublicVar;
import com.softpos.pos.core.model.FinalcialRec;
import com.softpos.constants.CreditPaymentRec;
import com.softpos.constants.PluRec;
import com.softpos.pos.core.model.BalanceBean;
import com.softpos.pos.core.model.BillNoBean;
import com.softpos.pos.core.model.MemberBean;
import com.softpos.pos.core.model.TSaleBean;
import com.softpos.pos.core.model.TableFileBean;
import com.softpos.pos.core.model.TranRecord;
import com.softpos.connection.database.ConfigFile;
import com.softpos.connection.database.MySQLConnect;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.PrinterName;
import com.softpos.report.driver.PrintDriver;
import com.softpos.util.AppLogUtil;
import com.softpos.util.DateUtil;
import com.softpos.util.NumberUtil;

public class PPrint {

    private SerialPort serialPort;
    private OutputStream outputStream;
    private Enumeration portList;
    private CommPortIdentifier portId;
    private boolean OpenStatus = false;
    private String XLine1 = "";
    private String XLine2 = "";
    private String XLine3 = "";
    private String XLine4 = "";
    private SimpleDateFormat PPrint_DatefmtThai = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
    private SimpleDateFormat Datefmt = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    private SimpleDateFormat ShowDatefmt = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    private DecimalFormat DecFmt = new DecimalFormat("##,###,##0.00");
    private DecimalFormat IntFmt = new DecimalFormat("##,###,##0");
    private DecimalFormat IntFmt1 = new DecimalFormat("#,##0");
    private DecimalFormat df = new DecimalFormat("#,##0.00");
    private int LineCount = 0;
    private boolean EJPrint = false;
    private POSHWSetup POSHW;
    private POSConfigSetup CONFIG;
    private String Space = " &nbsp; ";
    private String TAB = Space + Space + Space;
    private String TAB2 = TAB + TAB;
    private String PrinterName = "";
    boolean isPrintOutTest = true;
    private final TableFileControl tableFileControl = AppContext.getTableFileControl();
    private final BalanceControl balanceControl = AppContext.getBalanceControl();
    private final BillControl billControl = AppContext.getBillControl();
    private final MySQLConnect mysqlConnect = new MySQLConnect();
    private final PUtility pUtility = new PUtility();
    private final MemmaterController MemmaterController = AppContext.getMemmaterController();
    
    private final POSHWSetup POSHWSetup = new POSHWSetup();
    private final POSConfigSetup POSConfigSetup = new POSConfigSetup();
    private final ServiceControl ServiceControl = AppContext.getServiceControl();
    
    

    public PPrint() {
        POSHW = POSHWSetup.Bean(PublicVar.MACNO);
        CONFIG = POSConfigSetup.Bean();
    }

    public boolean openPrint(String PortName) {
        AppLogUtil.info("OpenPrint");
        if (OpenStatus) {
            return OpenStatus;
        }

        if (POSHW.getPRNTYPE().equals("6")) {
            OpenStatus = false;
            portList = CommPortIdentifier.getPortIdentifiers();
            while (portList.hasMoreElements()) {
                portId = (CommPortIdentifier) portList.nextElement();
                if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                    if (portId.getName().equals(PortName)) {
                        try {
                            serialPort = (SerialPort) portId.open("SimpleWriteApp", 1000);
                        } catch (PortInUseException | RuntimeException e) {
                            AppLogUtil.log(PPrint.class, "error", e);
                        }
                        try {
                            outputStream = serialPort.getOutputStream();
                        } catch (IOException e) {
                            AppLogUtil.log(PPrint.class, "error", e);
                        }
                        try {
                            serialPort.setSerialPortParams(9600, //boardrate
                                    SerialPort.DATABITS_8,
                                    SerialPort.STOPBITS_1,
                                    SerialPort.PARITY_NONE);
                            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_XONXOFF_IN | SerialPort.FLOWCONTROL_XONXOFF_OUT);
                            OpenStatus = true;
                        } catch (UnsupportedCommOperationException e) {
                            AppLogUtil.log(PPrint.class, "error", e);
                        }
                    }
                }
            }
        } else {
            OpenStatus = false;
            portList = CommPortIdentifier.getPortIdentifiers();

            while (portList.hasMoreElements()) {
                portId = (CommPortIdentifier) portList.nextElement();
                if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                    String nameLocal = portId.getName();
                    if (portId.getName().equals(PortName)) {
                        //if (portId.getName().equals("/dev/term/a")) 
                        try {
                            serialPort = (SerialPort) portId.open("SimpleWriteApp", 1000);
                        } catch (PortInUseException e) {
                            System.err.println("Can not Open Port...1\n" + e.getMessage());
                            AppLogUtil.log(PPrint.class, "error", e);

                        } catch (RuntimeException re) {
                            System.err.println("Com Port ไม่สามารถใช้งานได้ " + portId.getName() + "\n" + re.getMessage());
                            AppLogUtil.log(PPrint.class, "error", re);
                        }
                        try {
                            outputStream = serialPort.getOutputStream();
                        } catch (IOException e) {
                            System.err.println("Can not Open Port...2\n" + e.getMessage());
                            AppLogUtil.log(PPrint.class, "error", e);
                        }
                        try {
                            serialPort.setSerialPortParams(9600, //boardrate
                                    SerialPort.DATABITS_8,
                                    SerialPort.STOPBITS_1,
                                    SerialPort.PARITY_NONE);
                            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_XONXOFF_IN | SerialPort.FLOWCONTROL_XONXOFF_OUT);
                            OpenStatus = true;
                        } catch (UnsupportedCommOperationException e) {
                            System.err.println("Can not Open Port...3\n" + e.getMessage());
                            AppLogUtil.log(PPrint.class, "error", e);
                        }
                    }
                }
            }
        }

        return OpenStatus;
    }

    public void openDrawer() {
        if (!POSHW.getDRPort().equals("NONE") && POSHW.getDRType().equals("1")) {
            try {
                byte Str[] = {27, 112, 0, 25, 127}; //init Printer
                outputStream.write(Str);
                LineCount = 0;
            } catch (IOException ex) {
                AppLogUtil.log(PPrint.class, "error", ex);
            }
        }
    }

    public void openDrawerDriver() {
        PrinterName = PublicVar.printerDriverName;

        byte[] open = {27, 112, 48, 55, 121};
        PrintServiceAttributeSet printserviceattributeset = new HashPrintServiceAttributeSet();
        printserviceattributeset.add(new PrinterName(PrinterName, null));
        PrintService[] printservice = PrintServiceLookup.lookupPrintServices(null, printserviceattributeset);
        if (printservice.length == 0) {
            AppLogUtil.info("openDrawerDriver: Printer not found !!!");
            return;
        }

        PrintService pservice = printservice[0];
        DocPrintJob job = pservice.createPrintJob();
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        Doc doc = new SimpleDoc(open, flavor, null);
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        try {
            job.print(doc, aset);
        } catch (PrintException ex) {
            AppLogUtil.log(PPrint.class, "error", ex);
        }
    }

    public void initPrinter() {
        try {
            byte Str[] = {27, 64, 1}; //init Printer

            outputStream.write(Str);
            byte Str2[] = {27, 33, 1}; //Set to Nmormal Fornt

            outputStream.write(Str2);
            outputStream.flush();
            LineCount = 0;
        } catch (IOException ex) {
            AppLogUtil.log(PPrint.class, "error", ex);
        }
    }

    public void selectStye(int Stye) {

        if (Stye == 1) {
            byte Str[] = {27, 33, 1};
            try {
                outputStream.write(Str);
                outputStream.flush();
            } catch (IOException ex) {
                AppLogUtil.log(PPrint.class, "error", ex);
            }
        }
        if (Stye == 2) {
            byte Str[] = {27, 33, 2};
            try {
                outputStream.write(Str);
                outputStream.flush();
            } catch (IOException ex) {
                AppLogUtil.log(PPrint.class, "error", ex);
            }
        }
        if (Stye == 3) {
            byte Str[] = {27, 33, 97};
            try {
                outputStream.write(Str);
                outputStream.flush();
            } catch (IOException ex) {
                AppLogUtil.log(PPrint.class, "error", ex);
            }
        }
        if (Stye == 4) {
            byte Str[] = {27, 33, 98};
            try {
                outputStream.write(Str);
                outputStream.flush();
            } catch (IOException ex) {
                AppLogUtil.log(PPrint.class, "error", ex);
            }
        }
        if (Stye == 5) {
            byte Str[] = {27, 33, 16};
            try {
                outputStream.write(Str);
                outputStream.flush();
            } catch (IOException ex) {
                AppLogUtil.log(PPrint.class, "error", ex);
            }
        }
        if (Stye == 6) {
            byte Str[] = {27, 33, 17};
            try {
                outputStream.write(Str);
                outputStream.flush();
            } catch (IOException ex) {
                AppLogUtil.log(PPrint.class, "error", ex);
            }
        }
        if (Stye == 7) {
            byte Str[] = {27, 33, 49};
            try {
                outputStream.write(Str);
                outputStream.flush();
            } catch (IOException ex) {
                AppLogUtil.log(PPrint.class, "error", ex);
            }
        }
        if (Stye == 8) {
            byte Str[] = {27, 33, 48};
            try {
                outputStream.write(Str);
                outputStream.flush();
            } catch (IOException ex) {
                AppLogUtil.log(PPrint.class, "error", ex);
            }
        }
        if (Stye == 10) {
            byte Str[] = {27, 45, 1};
            try {
                outputStream.write(Str);
                outputStream.flush();
            } catch (IOException ex) {
                AppLogUtil.log(PPrint.class, "error", ex);
            }
        }
        if (Stye == 11) {
            byte Str[] = {27, 45, 0};
            try {
                outputStream.write(Str);
                outputStream.flush();
            } catch (IOException ex) {
                AppLogUtil.log(PPrint.class, "error", ex);
            }
        }
        if (Stye == 14) {
            byte Str[] = {27, 33, 30};
            try {
                outputStream.write(Str);
                outputStream.flush();
            } catch (IOException ex) {
                AppLogUtil.log(PPrint.class, "error", ex);
            }
        }
        if (Stye == 12) {
            byte Str[] = {27, 114, 1};
        }
        if (Stye == 13) {
            byte Str[] = {27, 114, 0};

            try {
                outputStream.write(Str);
                outputStream.flush();
            } catch (IOException ex) {
                AppLogUtil.log(PPrint.class, "error", ex);
            }
        }
    }

    public void get_Line(String St) {

        int SetMode[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        int list1[] = {232, 233, 234, 235, 236};
        int list2[] = {128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138,
            139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149,
            150, 151, 152, 153, 154, 155, 156, 157, 158,
            209, 212, 213, 214, 215, 219, 220, 221, 222, 231, 232,
            233, 234, 235, 236, 237, 251, 252, 253, 254
        };

        int list4[] = {216, 217};
        char ch;

        String Level1 = "";
        String Level2 = "";
        String Level3 = "";
        String Level4 = "";

        int I = 0;
        while (I <= St.length() - 1) {   // Check TIS Upper }

            ch = St.charAt(I);
            if (searchArray((int) ch, SetMode) != -1) {
                //Level2=Level2+ch ;
            } else {
                if (searchArray((int) ch, list1) != -1) {
                    Level1 = Level1.substring(0, Level1.length() - 1) + ch;
                } else {
                    if (searchArray((int) ch, list2) != -1) {
                        Level2 = Level2.substring(0, Level2.length() - 1) + ch;
                    } else {
                        if (searchArray((int) ch, list4) != -1) {
                            Level4 = Level4.substring(0, Level4.length() - 1) + ch;

                        } else {
                            Level1 = Level1 + " ";
                            Level2 = Level2 + " ";
                            Level3 = Level3 + ch;
                            Level4 = Level4 + " ";
                        }
                    }
                }
            }
            I++;
        }
        XLine1 = Level1;
        XLine2 = Level2;
        XLine3 = Level3;
        XLine4 = Level4;

    }

    public void print_Head_EJ() {
        String TempFile = POSHW.getEJDailyPath() + "/tempbill.txt";
        File fObject = new File(TempFile);
        if (fObject.canRead()) {
            fObject.delete();
        }
        int cLoop = 0;
        while ((fObject.canRead()) | (cLoop > 10)) {
            cLoop++;
            TextWriter TextWrite = new TextWriter();
            TextWrite.writeToText(TempFile, "");
        }
        TextWriter TextWrite = new TextWriter();
        TextWrite.writeToText(TempFile, POSHW.getHeading1());
        TextWrite.writeToText(TempFile, POSHW.getHeading2());
        TextWrite.writeToText(TempFile, POSHW.getHeading3());
        TextWrite.writeToText(TempFile, POSHW.getHeading4());
        TextWrite.writeToText(TempFile, "REG ID :" + POSHW.getMacNo());
        EJPrint = true;
    }

    public void print(String PrintMsg) {
        if (isPrintOutTest) {
            AppLogUtil.info("----------PRINT OUT TEST-----------");
            isPrintOutTest = false;
        }

        AppLogUtil.info(PrintMsg);
        if (POSHW.getPRNTYPE().equals("6")) {
            try {
                XLine1 = PrintMsg + "\n";
                outputStream.write(XLine1.getBytes("tis-620"));
            } catch (IOException e) {
                AppLogUtil.log(PPrint.class, "error", e);
            }
        } else {
            get_Line(ThaiUtil.Unicode2ASCII(PrintMsg));
            try {
                if (XLine1.trim().length() > 0) {
                    XLine1 = ThaiUtil.ASCII2Unicode(XLine1 + "\n");
                    byte LineSpace[] = {27, 51, 15, 1};
                    byte LineSpace2[] = {27, 51, 5, 1};
                    String TempPrint = "\n";
                    if (XLine2.trim().length() > 0) {
                        outputStream.write(LineSpace2);
                    } else {
                        outputStream.write(LineSpace);
                    }
                    outputStream.write(XLine1.getBytes("tis-620"));
                    LineCount = LineCount + 1;
                }
                if (XLine2.trim().length() > 0) {
                    XLine2 = ThaiUtil.ASCII2Unicode(XLine2 + "\n");
                    byte LineSpace[] = {27, 51, 17, 1};
                    outputStream.write(LineSpace);
                    outputStream.write(XLine2.getBytes("tis-620"));
                    LineCount = LineCount + 1;
                }
                if (XLine3.trim().length() >= 0) {
                    XLine3 = ThaiUtil.ASCII2Unicode(XLine3 + "\n");
                    byte LineSpace[] = {27, 51, 15, 1};
                    byte LineSpace2[] = {27, 51, 18, 1};
                    String TempPrint = "\n";
                    if (XLine4.trim().length() > 0) {
                        outputStream.write(LineSpace);
                    } else {
                        outputStream.write(LineSpace2);
                    }
                    outputStream.write(XLine3.getBytes("tis-620"));
                    LineCount = LineCount + 1;
                }
                if (XLine4.trim().length() > 0) {
                    XLine4 = ThaiUtil.ASCII2Unicode(XLine4 + "\n");
                    byte LineSpace4[] = {27, 51, 0};
                    outputStream.write(LineSpace4);
                    outputStream.write(XLine4.getBytes("tis-620"));
                    LineCount = LineCount + 1;
                }
            } catch (IOException ex) {
                AppLogUtil.log(PPrint.class, "error", ex);
            }
        }

        POSHWSetup bean = POSHWSetup.Bean(PublicVar.MACNO);
        String TempFile = bean.getEJDailyPath() + "/log" + PublicVar.MACNO + ".gif";
        TextWriter TextWrite = new TextWriter();
        File fObject = new File(TempFile);
        if (!fObject.canRead()) {
            TextWrite.writeToText(TempFile, "");
        }

        TextWrite.writeToText(TempFile, PrintMsg);
        if (EJPrint) {
            String TempBill = POSHW.getEJDailyPath() + "/tempbill.txt";
            if (!fObject.canRead()) {
                TextWrite.writeToText(TempBill, "");
            }
            TextWrite.writeToText(TempBill, PrintMsg);
        }

    }

    public void closePrint() {
        AppLogUtil.info("Close Printer");
        if (!OpenStatus) {
            return;
        }
        if (serialPort != null) {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                AppLogUtil.log(PPrint.class, "error", e);
            }
            serialPort.close();
        }
        OpenStatus = false;
    }

    public void printLogout() {
        if (PublicVar.useprint) {
            Date dateP = new Date();

            if (PublicVar.printdriver == true) {
                PrintDriver pd = new PrintDriver();
                pd.addTextLn("Log Out User : " + PublicVar.USERCODE);
                pd.addTextLn("Log Out Time : " + PPrint_DatefmtThai.format(dateP).replace("/", " / "));

                pd.printNormal();
            } else {
                if (!POSHW.getPRNPort().equals("NONE")) {
                    if (openPrint(POSHW.getPRNPort())) {
                        initPrinter();
                        print("Log Out User : " + PublicVar.USERCODE);
                        print("Log Out Time : " + PPrint_DatefmtThai.format(dateP).replace("/", " / "));
                        print("");
                        print("");
                        print("");
                        print("");
                        print("");
                        print("");
                        print("");
                        print("");
                        print("");
                        cutPaper();
                        closePrint();
                    }
                }
            }

        }

    }

    public void printSubTotalBillDriver(String _RefNo, String tableNo) {
        String t = "";
        String t1 = "";
        double totalDiscount = 0.00;
        BillNoBean bBean = billControl.getData(_RefNo);
        if (!CONFIG.getP_PrintSum().equals("Y")) {
            List<TSaleBean> listTSale = billControl.getAllTSale(_RefNo);

            int AmtLength = 10;
            int ItemCnt = 0;
            int CustCnt = 0;
            double discountBath = 0.00;
            String VatStr;
            PublicVar.P_LineCount = 0;
            for (int i = 0; i < listTSale.size(); i++) {
                TSaleBean bean = (TSaleBean) listTSale.get(i);
                if (!bean.getR_Void().equals("V")) {
                    ItemCnt = (int) (ItemCnt + bean.getR_Quan());
                    discountBath += (int) (bean.getR_DiscBath());
                }
            }
            if (CONFIG.getP_PrintDetailOnRecp().equals("Y")) {
                if (ConfigFile.getProperties("PrintQueue").equals("true")) {
                    
                    try {
                        mysqlConnect.open(this.getClass());
                        String sqlGetCountBillno = "select count(b_refno) cbillno from billno";
                        ResultSet rs = mysqlConnect.executeQuery(sqlGetCountBillno);
                        int queue = 0;
                        int Q = 0;
                        if (rs.next()) {
                            queue = rs.getInt("cbillno");
                            queue++;
                        }
                        t += "colspan=3 align=center><font face=Angsana New size=3>" + "คิว(Queue): " + queue + "_";
                        rs.close();

                    } catch (SQLException e) {
                        AppLogUtil.log(PPrint.class, "error", e);
                    } finally {
                        mysqlConnect.closeConnection(this.getClass());
                    }
                }
                Date dateP = new Date();
                if (POSHW.getHeading1().trim().length() >= 18) {
                    String[] strs = POSHW.getHeading1().trim().replace(" ", Space).split("_");
                    for (String data : strs) {
                        t += "colspan=3 align=center><font face=Angsana New size=2>" + data + "_";
                    }
                } else {
                    if (!POSHW.getHeading1().equals("")) {
                        t += "colspan=3 align=center><font face=Angsana New size=2>" + POSHW.getHeading1().trim().replace(" ", "&nbsp; ") + "_";
                    }
                }
                if (POSHW.getHeading2().trim().length() >= 18) {
                    String[] strs = POSHW.getHeading2().trim().replace(" ", Space).split("_");
                    for (String data : strs) {
                        t += "colspan=3 align=center><font face=Angsana New size=2>" + data + "_";
                    }
                } else {
                    if (!POSHW.getHeading2().equals("")) {
                        t += "colspan=3 align=center><font face=Angsana New size=2>" + POSHW.getHeading2().trim().replace(" ", "&nbsp; ") + "_";
                    }
                }
                if (!POSHW.getHeading3().equals("")) {
                    t += "colspan=3 align=center><font face=Angsana New size=2>" + (POSHW.getHeading3().trim()) + "_";
                }
                if (!POSHW.getHeading4().equals("")) {
                    t += "colspan=3 align=center><font face=Angsana New size=2>" + (POSHW.getHeading4().trim()) + "_";
                }

                t += "colspan=3 align=center><font face=Angsana New size=2>" + "REG ID :" + POSHW.getMacNo() + "_";
                t += "colspan=3 align=center><font face=Angsana New size=2> " + "-----------------------------------------_";
                t += "colspan=2 align=left><font face=Angsana New size=2> "
                        + PPrint_DatefmtThai.format(dateP).replace("/", " / " + "_");
                t += "colspan=2 align=left><font face=Angsana New size=2> "
                        + "TABLE :" + Space + tableNo + "_";
                t += "colspan=4 align=left><font face=Angsana New size=2> " + "CC : " + IntFmt.format(bBean.getB_Cust())
                        + " Seat :"
                        + "_";
                t += "colspan=4 align=left><font face=Angsana New size=2> "
                        + "=ชื่อ: " + Space
                        + getLastEmployee(tableNo) + "_";
                t += "colspan=3 align=center><font face=Angsana New size=2> " + "-----------------------------------------_";

                for (int i = 0; i < listTSale.size(); i++) {
                    TSaleBean bean = (TSaleBean) listTSale.get(i);
                    if (bean.getR_Vat().equals("N")) {
                        VatStr = "*";
                    } else {
                        VatStr = "-";
                    }
                    if (bean.getR_Void().equals("V")) {
                    } else {
                        if (bean.getR_PrAmt() == 0) {
                            if (CONFIG.getP_CodePrn().equals("Y")) {
                                t += ("colspan=3 align=left><font face=Angsana New size=2> " + subStringText(bean.getR_PName(), 20)) + "_";
                                t += "align=left width=-90%><font face=Angsana New size=2>"
                                        + DecFmt.format(bean.getR_Quan())
                                        + "</td><td align=left width=-30%><font face=Angsana New size=2>"
                                        + TAB + subStringText(bean.getR_PName(), 20)
                                        + "</td><td align=right width=50><font face=Angsana New size=2>"
                                        + DecFmt.format(bean.getR_Total()) + bean.getR_ETD() + "_";
                                if (!bean.getR_Opt1().equals("")) {
                                    t += "></td><td align=left colspan=2>"
                                            + "<font face=Angsana New size=2>"
                                            + (ThaiUtil.ASCII2Unicode(bean.getR_Opt1())) + "_";
                                }
                            } else {
                                t += "align=left width=100%><font face=Angsana New size=2>"
                                        + DecFmt.format(bean.getR_Quan())
                                        + "</td><td align=left width=-30%><font face=Angsana New size=2>"
                                        + TAB + subStringText(bean.getR_PName(), 26)
                                        + "</td><td align=right width=50><font face=Angsana New size=2>"
                                        + DecFmt.format(bean.getR_Total()) + bean.getR_ETD() + "_";

                            }
                        } else {
                            if (CONFIG.getP_CodePrn().equals("Y")) {
                                t += ("colspan=3 align=left><font face=Angsana New size=2> " + bean.getR_PName()) + "_";
                                t += "align=left width=-90%><font face=Angsana New size=2>"
                                        + DecFmt.format(bean.getR_Quan())
                                        + "</td><td align=left width=-30%><font face=Angsana New size=2>"
                                        + subStringText(bean.getR_Normal() + VatStr + "" + bean.getR_PName(), 26)
                                        + "</td><td align=right width=50><font face=Angsana New size=2>"
                                        + DecFmt.format(bean.getR_Total()) + "_";
                            } else {
                                t += "align=left width=-90%><font face=Angsana New size=2>"
                                        + DecFmt.format(bean.getR_Quan())
                                        + "</td><td align=left width=-30%><font face=Angsana New size=2>"
                                        + TAB + subStringText(bean.getR_PName(), 26)
                                        + "</td><td align=right width=50><font face=Angsana New size=2>"
                                        + DecFmt.format(bean.getR_Total()) + "_";

                            }
                            if (bean.getR_PrType().equals("-P")) {
                                if (bean.getR_PrAmt() > 0) {
                                    t += ("colspan=3 align=left><font face=Angsana New size=2> " + "   **Promotion  " + bean.getR_PrCode() + " " + pUtility.SeekPromotionName(bean.getR_PrCode())) + "_";
                                }
                            }
                            if (bean.getR_PrType().equals("-I")) {
                                if (bean.getR_PrDisc() != 0) {
                                    t += ("colspan=3 align=left><font face=Angsana New size=2> " + "   **Item-Discount " + bean.getR_PrCode() + " " + DecFmt.format(bean.getR_PrDisc() - bean.getR_Redule()) + "%") + "_";
                                }
                            }
                        }
                    }
                }
            } else {
                t += ("colspan=3 align=center><font face=Angsana New size=2> " + "-----------------------------------------") + "_";
                t += ("colspan=3 align=left><font face=Angsana New size=2> " + "     อาหารและเครื่องดื่ม " + DecFmt.format(bBean.getB_Total())) + "_";
            }
            t += ("colspan=3 align=center><font face=Angsana New size=2> " + "-----------------------------------------") + "_";

            if (bBean.getB_ProDiscAmt() > 0) {
                t += ("colspan=3 align=right><font face=Angsana New size=2> " + "ลด Promotion" + "</td><td align=right ><font face=Angsana New size=2>- " + DecFmt.format(bBean.getB_ProDiscAmt())) + "_";
            }
            if (bBean.getB_SpaDiscAmt() > 0) {
                t += ("colspan=2 align=left><font face=Angsana New size=2> " + "Special Disc" + "</td><td align=right ><font face=Angsana New size=2>- " + DecFmt.format(bBean.getB_SpaDiscAmt())) + "_";
            }
            if (bBean.getB_MemDiscAmt() > 0) {
                t += ("colspan=2 align=left><font face=Angsana New size=2> " + "ลดสมาชิก.." + bBean.getB_MemDisc() + "</td><td align=right ><font face=Angsana New size=2>- " + DecFmt.format(bBean.getB_MemDiscAmt())) + "_";
            }
            if (bBean.getB_FastDiscAmt() > 0) {
                t += ("colspan=2 align=left><font face=Angsana New size=-2> " + "ลดเทศกาล.." + bBean.getB_FastDisc() + "</td><td align=right ><font face=Angsana New size=2>- " + DecFmt.format(bBean.getB_FastDiscAmt())) + "_";
            }
            if (bBean.getB_EmpDiscAmt() > 0) {
                t += ("colspan=2 align=left><font face=Angsana New size=2> " + "ลดพนักงาน.." + bBean.getB_EmpDisc() + "</td><td align=right ><font face=Angsana New size=2>- " + DecFmt.format(bBean.getB_EmpDiscAmt())) + "_";
            }
            if (bBean.getB_TrainDiscAmt() > 0) {
                t += ("colspan=2 align=left><font face=Angsana New size=2> " + "ลด Trainnee.." + bBean.getB_TrainDisc() + "</td><td align=right ><font face=Angsana New size=2>- " + pUtility.DataFull2(DecFmt.format(bBean.getB_TrainDiscAmt()), AmtLength)) + "_";
            }
            if (bBean.getB_ItemDiscAmt() > 0) {
                t += ("colspan=2 align=left><font face=Angsana New size=2> " + "ลดตามรายการ(Item)" + "</td></font><td align=right><font face=Angsana New size=2> " + DecFmt.format(bBean.getB_ItemDiscAmt())) + "-_";
            }

            if (bBean.getB_Earnest() > 0) {
                t += ("colspan=2 align=left><font face=Angsana New size=2> " + "หักคืนเงินมัดจำ" + "</td><td align=right ><font face=Angsana New size=2>- " + DecFmt.format(bBean.getB_Earnest())) + "_";
            }
            if (bBean.getB_SubDiscBath() > 0) {
                t += ("colspan=2 align=left><font face=Angsana New size=2> " + "Disc(Bath)..  " + "</td><td align=right><font face=Angsana New size=2>- " + DecFmt.format(bBean.getB_SubDiscBath())) + "_";
            }
            if (bBean.getB_SubDiscAmt() > 0) {
                t += ("colspan=2 align=left><font face=Angsana New size=2> " + "ส่วนลดคูปอง  " + "</td><td align=right ><font face=Angsana New size=2>- " + DecFmt.format(bBean.getB_SubDiscAmt())) + "_";
            }
            if (bBean.getB_CuponDiscAmt() > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2>" + "**ส่วนลดยกเว้นไวน์ และรายการโปรโมชั่นปกติ**" + "_");
                t += ("colspan=2 align=left><font face=Angsana New size=2> " + Space + bBean.getB_CuponName() + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_CuponDiscAmt())) + "-" + "_";
                if (bBean.getB_Total() != bBean.getB_NetTotal()) {
                    t += "align=right colspan=3><font face=Angsana New size=3>" + "TOTAL : " + DecFmt.format(bBean.getB_Total() - discountBath) + "_";
                }
            }
            if (CONFIG.getP_VatType().equals("I")) {
                if (bBean.getB_MemDiscAmt() > 0) {
                    t += "colspan=3 align=left><font face=Angsana New size=2>" + "ลดสมาชิก.." + bBean.getB_MemDisc() + TAB + "- " + DecFmt.format(bBean.getB_MemDiscAmt()) + "_";
                }
                if (bBean.getB_ServiceAmt() > 0) {
                    t += ("colspan=2 align=left><font face=Angsana New size=2>" + Space + "Service :" + IntFmt.format(CONFIG.getP_Service()) + " %</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_ServiceAmt())) + "+_";
                }
                t += ("colspan=2 align=right><font face=Angsana New size=2> " + "Net-TOTAL.." + "</td><td align=right><font face=Angsana New size=3>" + DecFmt.format(Math.round(bBean.getB_NetTotal() - discountBath) - bBean.getB_Vat())) + "</font>_";
                if (CONFIG.getP_VatPrn().equals("Y")) {
                    t += ("colspan=2 align=right><font face=Angsana New size=2> " + "Vat..." + IntFmt.format(CONFIG.getP_Vat()) + "%" + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_Vat())) + "_";
                    t += ("colspan=2 align=right><font face=Angsana New size=3> " + "Net-TOTAL + VAT" + "</td><td align=right><font face=Angsana New size=3>" + DecFmt.format(Math.round(bBean.getB_NetTotal() - discountBath))) + "</font>_";
                    if (bBean.getB_NetDiff() != 0) {
                        t += ("colspan=2 align=left><font face=Angsana New size=2> " + "Round" + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_NetDiff())) + "_";
                    }
                }
            } else {
                if (CONFIG.getP_VatType().equals("I")) {
                    if (bBean.getB_MemDiscAmt() > 0) {
                        t += "colspan=3 align=left><font face=Angsana New size=2>" + "ลดสมาชิก.." + bBean.getB_MemDisc() + TAB + "- " + DecFmt.format(bBean.getB_MemDiscAmt()) + "_";
                    }
                    t += ("colspan =2 align=left><font face=Angsana New size=2> " + Space + "Net-Amount " + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_NetVat() + bBean.getB_NetNonVat())) + "_";

                }
                if (CONFIG.getP_VatType().equals("E")) {
                    if (bBean.getB_MemDiscAmt() > 0) {
                        t += "colspan=3 align=left><font face=Angsana New size=2>" + "ลดสมาชิก.." + bBean.getB_MemDisc() + TAB + "- " + DecFmt.format(bBean.getB_MemDiscAmt()) + "_";
                    }
                    t += ("colspan =2 align=left><font face=Angsana New size=2> " + Space + "Net-Amount " + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_NetTotal() - discountBath - bBean.getB_ServiceAmt() - bBean.getB_Vat())) + "_";
                }
                if (!CONFIG.getP_PayBahtRound().equals("O")) {
                    t += ("colspan =2 align=left><font face=Angsana New size=3> " + Space + "Net-Total " + "</td><td align=right><font face=Angsana New size=3>" + DecFmt.format(NumberUtil.UP_DOWN_NATURAL_BAHT(Math.round(bBean.getB_NetVat() + bBean.getB_NetNonVat() + bBean.getB_Vat())))) + "_";
                } else {
                    t += ("colspan =2 align=left><font face=Angsana New size=3> " + Space + "Net-Total " + "</td><td align=right><font face=Angsana New size=3>" + DecFmt.format(Math.round(bBean.getB_NetVat() + bBean.getB_NetNonVat() + bBean.getB_Vat()))) + "_";
                }

                t += ("colspan =2 align=left><font face=Angsana New size=2> " + Space + "Vat..." + IntFmt.format(CONFIG.getP_Vat())) + "%" + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_Vat()) + "_";
                if (bBean.getB_NetDiff() != 0) {
                    t += ("colspan=2 align=left><font face=Angsana New size=2> " + Space + "Round" + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_NetDiff())) + "_";
                }
                t += ("colspan=3 align=Center><font face=Angsana New size=2> " + "VAT INCLUDED") + "_";
            }

            if (bBean.getB_GiftVoucher() > 0) {
                t += ("colspan=3 align=center><font face=Angsana New size=3>" + "-----------------------------------------" + "_");
                t += ("colspan=2 align=left><font face=Angsana New size=2> " + "บัตรกำนัล.." + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_GiftVoucher())) + "_";

                mysqlConnect.open(this.getClass());
                try {
                    Statement stmt = mysqlConnect.getConnection().createStatement();
                    String CheckGift = "select *from t_gift where (refno='" + _RefNo + "')";
                    ResultSet rs = stmt.executeQuery(CheckGift);
                    while (rs.next()) {
                        t += ("colspan=3 align=left><font face=Angsana New size=2> " + TAB + rs.getString("giftbarcode") + rs.getString("giftno") + "@" + DecFmt.format(rs.getDouble("giftamt"))) + "_";
                    }
                    rs.close();
                    stmt.close();
                } catch (SQLException e) {
                    AppLogUtil.log(PPrint.class, "error", e);
                } finally {
                    mysqlConnect.closeConnection(this.getClass());
                }
            }
            t += ("colspan=3 align=center><font face=Angsana New size=3>" + "-----------------------------------------" + "_");
            if (bBean.getB_PayAmt() > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2> " + "CASH  : " + DecFmt.format(bBean.getB_PayAmt()) + Space + "Change : " + DecFmt.format(bBean.getB_Ton())) + "_";
            }

            if (bBean.getB_CrAmt1() > 0) {
                //get credit name
                String crName = "";
                mysqlConnect.open(this.getClass());
                try {
                    String sql = "select CrName from creditfile where crcode='" + bBean.getB_CrCode1() + "' limit 1";
                    Statement stmt = mysqlConnect.getConnection().createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        crName = ThaiUtil.ASCII2Unicode(rs.getString("CrName"));
                    }

                    rs.close();
                    stmt.close();
                } catch (SQLException e) {
                    AppLogUtil.log(PPrint.class, "error", e);
                } finally {
                    mysqlConnect.closeConnection(this.getClass());
                }

                t += ("colspan=3 align=left><font face=Angsana New size=-> " + crName) + "_";
                t += ("colspan=3 align=left><font face=Angsana New size=2> " + "XXXXXXXXXXX" + pUtility.Addzero(bBean.getB_CardNo1(), 16).substring(12, 16) + TAB + bBean.getB_AppCode1()) + "_";
                t += ("colspan=2 align=left><font face=Angsana New size=2> " + "Credit Payment" + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_CrAmt1())) + "_";
            }

            if (bBean.getB_AccrAmt() > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=-2> " + "AR-" + bBean.getB_AccrCode() + " ลูกหนี้การค้า........" + DecFmt.format(bBean.getB_AccrAmt())) + "_";
                t += ("colspan=3 align=left><font face=Angsana New size=2> " + pUtility.SeekArName(bBean.getB_AccrCode())) + "_";
            }
            if (bBean.getB_Entertain1() > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2> " + "Entertain  : " + DecFmt.format(bBean.getB_Entertain1())) + "_";
                t += ("colspan=3 align=left><font face=Angsana New size=3>" + "Signature..." + "_");
                t += ("colspan=3 align=center><font face=Angsana New size=3>" + "" + "_");
                t += ("colspan=3 align=center><font face=Angsana New size=3>" + "-----------------------------------------" + "_");
                t += ("colspan=3 align=center><font face=Angsana New size=3>" + "" + "_");
                t += ("colspan=3 align=center><font face=Angsana New size=3>" + "-----------------------------------------" + "_");
            }
            if (!bBean.getB_MemCode().equals("")) {
                t += ("colspan=3 align=center><font face=Angsana New size=3> " + "-----------------------------------------") + "_";
                MemberBean MemBean = MemmaterController.getMember(bBean.getB_MemCode());
                t += "align=left colspan=3><font face=Angsana New size=2>สมาชิก - " + MemBean.getMember_Code() + "_";
                t += "align=left colspan=3><font face=Angsana New size=2>" + ThaiUtil.ASCII2Unicode(MemBean.getMember_NameThai()) + "_";
                t += "align=left colspan=3><font face=Angsana New size=2>" + ThaiUtil.ASCII2Unicode(MemBean.getMember_Remark1()) + "_";
                t += "align=lefts colspan=3><font face=Angsana New size=2>" + ThaiUtil.ASCII2Unicode(MemBean.getMember_Remark2()) + "_";
                t += ("colspan=3 align=center><font face=Angsana New size=3> " + "-----------------------------------------") + "_";

                t += ("colspan=3 align=left><font face=Angsana New size=2> " + "   แต้มครั้งนี้ :           " + NumberUtil.showNumber(bBean.getB_MemCurSum()) + " แต้ม") + "_";
                t += ("colspan=3 align=left><font face=Angsana New size=2> " + "   แต้มสะสมถึง  --- -- ----   " + NumberUtil.showNumber(bBean.getB_SumScore()) + " แต้ม") + "_";
                t += ("colspan=3 align=left><font face=Angsana New size=2> " + "   บัตรหมดอายุวันที่ : " + DateUtil.toDateLocal(bBean.getB_MemEnd())) + "_";

            }
            t += ("colspan=3 align=center><font face=Angsana New size=3> " + "-----------------------------------------") + "_";
            t += ("colspan=3 align=left><font face=Angsana New size=2> " + "Receipt No: " + _RefNo + Space + "COM:" + Space + bBean.getB_MacNo()) + "_";
            t += ("colspan=3 align=left><font face=Angsana New size=-2> " + " ") + "_";
            if (!CONFIG.getP_PrintRecpMessage().equals("")) {
                t += ("colspan=3 align=center><font face=Angsana New size=-2> " + CONFIG.getP_PrintRecpMessage()) + "_";
            }
            if (!POSHW.getFootting3().equals("")) {
                t += ("colspan=3 align=center><font face=Angsana New size=2> " + POSHW.getFootting3()) + "_";
            }
            if (!POSHW.getFootting2().equals("")) {
                t += ("colspan=3 align=center><font face=Angsana New size=2> " + POSHW.getFootting2()) + "_";
            }
            if (!POSHW.getFootting1().equals("")) {
                t += ("colspan=3 align=center><font face=Angsana New size=2> " + POSHW.getFootting1()) + "_";
            }

            t += ("colspan=3 align=left><font face=Angsana New size=-2> " + " ") + "_";
            t = changeLanguage(t);
            EJPrint = false;

            //print
            PrintDriver pd = new PrintDriver();
            String[] strs = t.split("_");

            for (String data1 : strs) {
                pd.addTextIFont(data1);
            }
            openDrawerDriver();
            pd.printHTML();
        } else {
            List<TSaleBean> listTSale = billControl.getAllTSaleNovoidSum(_RefNo);

            int AmtLength = 10;
            int ItemCnt = 0;
            int CustCnt = 0;
            String VatStr;
            PublicVar.P_LineCount = 0;
            double discountBath = 0.00;
            for (int i = 0; i < listTSale.size(); i++) {
                TSaleBean bean = (TSaleBean) listTSale.get(i);
                if (!bean.getR_Void().equals("V")) {
                    ItemCnt = (int) (ItemCnt + bean.getR_Quan());
                    discountBath += (int) bean.getR_DiscBath();
                }
            }

            if (CONFIG.getP_PrintDetailOnRecp().equals("Y")) {
                if (ConfigFile.getProperties("PrintQueue").equals("true")) {
                    try {
                        mysqlConnect.open(this.getClass());
                        String sqlGetCountBillno = "select count(b_refno) cbillno from billno";
                        ResultSet rs = mysqlConnect.executeQuery(sqlGetCountBillno);
                        int queue = 0;
                        if (rs.next()) {
                            queue = rs.getInt("cbillno");
                            queue++;
                        }
                        t += "colspan=3 align=center><font face=Angsana New size=4>" + "คิว(Queue): " + queue + "_";
                        t1 += "colspan=3 align=center><font face=Angsana New size=3>" + "คิว(Queue): " + queue + "_";

                        rs.close();
                    } catch (SQLException e) {
                        AppLogUtil.log(PPrint.class, "error", e);
                    } finally {
                        mysqlConnect.closeConnection(this.getClass());
                    }
                }
                Date dateP = new Date();

                if (POSHW.getHeading1().trim().length() >= 18) {
                    String[] strs = POSHW.getHeading1().trim().replace(" ", Space).split("_");
                    for (String data : strs) {
                        t += "colspan=3 align=center><font face=Angsana New size=3>" + data + "_";
                    }
                } else {
                    if (!POSHW.getHeading1().equals("")) {
                        t += "colspan=3 align=center><font face=Angsana New size=4>" + POSHW.getHeading1().trim().replace(" ", "&nbsp; ") + "_";

                    }
                }
                if (POSHW.getHeading2().trim().length() >= 18) {
                    String[] strs = POSHW.getHeading2().trim().replace(" ", Space).split("_");
                    for (String data : strs) {
                        t += "colspan=3 align=center><font face=Angsana New size=3>" + data + "_";
                    }
                } else {
                    if (!POSHW.getHeading2().equals("")) {
                        t += "colspan=3 align=center><font face=Angsana New size=3>" + POSHW.getHeading2().trim().replace(" ", "&nbsp; ") + "_";
                    }
                }

                if (POSHW.getHeading3().trim().length() >= 18) {
                    String[] strs = POSHW.getHeading3().trim().replace(" ", Space).split("_");
                    for (String data : strs) {
                        t += "colspan=3 align=center><font face=Angsana New size=3>" + data + "_";
                    }
                } else {
                    if (!POSHW.getHeading3().equals("")) {
                        t += "colspan=3 align=center><font face=Angsana New size=3>" + POSHW.getHeading3().trim().replace(" ", "&nbsp; ") + "_";
                    }
                }
                if (POSHW.getHeading4().trim().length() >= 18) {
                    String[] strs = POSHW.getHeading4().trim().replace(" ", Space).split("_");
                    for (String data : strs) {
                        t += "colspan=3 align=center><font face=Angsana New size=3>" + data + "_";
                    }
                } else {
                    if (!POSHW.getHeading4().equals("")) {
                        t += "colspan=3 align=center><font face=Angsana New size=3>" + POSHW.getHeading4().trim().replace(" ", "&nbsp; ") + "_";
                    }
                }
                t += "colspan=3 align=center><font face=Angsana New size=2>" + "REG ID :" + POSHW.getMacNo() + "_";
                t += "colspan=3 align=center><font face=Angsana New size=3> " + "-----------------------------------------_";
                t += "colspan=2 align=left><font face=Angsana New size=2> "
                        + PPrint_DatefmtThai.format(dateP).replace("/", " / ") + "_";
                t += "colspan=2 align=left><font face=Angsana New size=2> "
                        + "TABLE :" + Space + tableNo + "_";
                t += "colspan=3 align=left><font face=Angsana New size=2> " + "CC : " + IntFmt.format(bBean.getB_Cust())
                        + " Seat :"
                        + TAB + TAB
                        + "ชื่อ:"
                        + getLastEmployeeCheckBill(tableNo, _RefNo) + "_";
                t += "colspan=3 align=center><font face=Angsana New size=3> " + "-----------------------------------------_";

                for (int i = 0; i < listTSale.size(); i++) {
                    TSaleBean bean = (TSaleBean) listTSale.get(i);
                    if (bean.getR_Vat().equals("N")) {
                        VatStr = "*";
                    } else {
                        VatStr = "-";
                    }
                    if (bean.getR_Void().equals("V")) {
                    } else {
                        if (bean.getR_PrAmt() == 0) {
                            if (CONFIG.getP_CodePrn().equals("Y")) {
                                t += ("colspan=3 align=left><font face=Angsana New size=-2> " + bean.getR_PName()) + "_";
                                t += "align=left width=-90%><font face=Angsana New size=2>"
                                        + DecFmt.format(bean.getR_Quan())
                                        + "</td><td align=left width=-30%><font face=Angsana New size=2>"
                                        + Space + subStringText(bean.getR_PName(), 20)
                                        + "</td><td align=right width=50><font face=Angsana New size=2>"
                                        + DecFmt.format(bean.getR_Total()) + bean.getR_ETD() + "_";
                                if (!bean.getR_Opt1().equals("")) {
                                    t += "></td><td align=left colspan=2>"
                                            + "<font face=Angsana New size=2>"
                                            + (ThaiUtil.ASCII2Unicode(bean.getR_Opt1())) + "_";
                                }
                            } else {
                                t += "align=left width=-90%><font face=Angsana New size=2>"
                                        + IntFmt.format(bean.getR_Quan())
                                        + "</td><td style= text-align:left; align=left; width=-30%><font face=Angsana New size=2>"
                                        + Space + subStringText(bean.getR_PName(), 26)
                                        + "</td><td align=right width=50><font face=Angsana New size=2>"
                                        + IntFmt.format(bean.getR_Total()) + bean.getR_ETD() + "_";
                                t1 += "align=left><font face=Angsana New size=2>"
                                        + IntFmt.format(bean.getR_Quan())
                                        + "</td><td td style= text-align:left; align=left ;width=80><font face=Angsana New size=2>"
                                        + Space + subStringText(bean.getR_PName(), 26)
                                        + "</td><td align=right width=50><font face=Angsana New size=2>"
                                        + IntFmt.format(bean.getR_Total()) + "_";
                            }
                        } else {
                            if (CONFIG.getP_CodePrn().equals("Y")) {
                                t += ("colspan=3 align=left><font face=Angsana New size=-2> " + bean.getR_PName()) + "_";
                                t += "align=left width=-90%><font face=Angsana New size=2>"
                                        + DecFmt.format(bean.getR_Quan())
                                        + "</td><td align=left width=-30%><font face=Angsana New size=2>"
                                        + subStringText(bean.getR_Normal() + VatStr + "" + bean.getR_PName(), 20)
                                        + "</td><td align=right width=50><font face=Angsana New size=2>"
                                        + DecFmt.format(bean.getR_Total()) + "_";
                            } else {
                                t += "align=left width=-90%><font face=Angsana New size=2>"
                                        + DecFmt.format(bean.getR_Quan())
                                        + "</td><td align=left width=-30%><font face=Angsana New size=2>"
                                        + subStringText(bean.getR_PName(), 26)
                                        + "</td><td align=right width=50><font face=Angsana New size=2>"
                                        + DecFmt.format(bean.getR_Total()) + "_";
                                t1 += "colspan=3 align=left><font face=Angsana New size=2>"
                                        + IntFmt.format(bean.getR_Quan())
                                        + "</td><td align=left width=80><font face=Angsana New size=2>"
                                        + subStringText(bean.getR_PName(), 26)
                                        + "</td><td align=right width=50><font face=Angsana New size=2>"
                                        + IntFmt.format(bean.getR_Total()) + "_";

                            }
                            if (bean.getR_PrType().equals("-P")) {
                                if (bean.getR_PrAmt() > 0) {
                                    t += ("colspan=3 align=left><font face=Angsana New size=2> " + Space + "**Promotion  " + bean.getR_PrCode()) + "_";
                                }
                            }
                            if (bean.getR_PrType().equals("-I")) {
                                if (bean.getR_PrDisc() != 0) {
                                    t += ("colspan=3 align=left><font face=Angsana New size=2> " + "   **Item-Discount " + bean.getR_PrCode() + " " + DecFmt.format(bean.getR_PrDisc() - bean.getR_Redule()) + "%") + "_";
                                }
                            }
                        }
                    }
                }
            } else {
                if (POSHW.getHeading1().trim().length() >= 18) {
                    String[] strs = POSHW.getHeading1().trim().replace(" ", Space).split("_");
                    for (String data : strs) {
                        t += "colspan=3 align=center><font face=Angsana New size=3>" + data + "_";
                    }
                } else {
                    if (POSHW.getHeading1().equals("")) {
                        t += "colspan=3 align=center><font face=Angsana New size=3>" + POSHW.getHeading1().trim().replace(" ", "&nbsp; ") + "_";
                    }
                }
                if (POSHW.getHeading2().trim().length() >= 18) {
                    String[] strs = POSHW.getHeading2().trim().replace(" ", Space).split("_");
                    for (String data : strs) {
                        t += "colspan=3 align=center><font face=Angsana New size=3>" + data + "_";
                    }
                } else {
                    if (POSHW.getHeading2().equals("")) {
                        t += "colspan=3 align=center><font face=Angsana New size=3>" + POSHW.getHeading2().trim().replace(" ", "&nbsp; ") + "_";
                    }
                }
                if (POSHW.getHeading3().trim().length() >= 18) {
                    String[] strs = POSHW.getHeading3().trim().replace(" ", Space).split("_");
                    for (String data : strs) {
                        t += "colspan=3 align=center><font face=Angsana New size=3>" + data + "_";
                    }
                } else {
                    if (POSHW.getHeading3().equals("")) {
                        t += "colspan=3 align=center><font face=Angsana New size=3>" + POSHW.getHeading3().trim().replace(" ", "&nbsp; ") + "_";
                    }
                }
                if (POSHW.getHeading4().trim().length() >= 18) {
                    String[] strs = POSHW.getHeading4().trim().replace(" ", Space).split("_");
                    for (String data : strs) {
                        t += "colspan=3 align=center><font face=Angsana New size=3>" + data + "_";
                    }
                } else {
                    if (POSHW.getHeading4().equals("")) {
                        t += "colspan=3 align=center><font face=Angsana New size=3>" + POSHW.getHeading4().trim().replace(" ", "&nbsp; ") + "_";
                    }
                }

                Date dateP = new Date();
                t += "colspan=3 align=center><font face=Angsana New size=2>" + "REG ID :" + POSHW.getMacNo() + "_";
                t += "colspan=3 align=center><font face=Angsana New size=3> " + "-----------------------------------------_";
                t += "colspan=2 align=left><font face=Angsana New size=2> "
                        + PPrint_DatefmtThai.format(dateP).replace("/", " / ")
                        + "</td><td align=right><font face=Angsana New size=2>"
                        + "TABLE :" + Space + tableNo + "_";
                t += "colspan=2 align=left><font face=Angsana New size=-2> " + "CC : " + IntFmt.format(bBean.getB_Cust())
                        + " Seat :"
                        + "</td><td align=right><font face=Angsana New size=2>"
                        + "NAME: " + Space
                        + getLastEmployeeCheckBill(tableNo, _RefNo) + "_";

                t += ("colspan=3 align=center><font face=Angsana New size=3> " + "-----------------------------------------") + "_";
                t += ("colspan=3 align=left><font face=Angsana New size=2> " + "     อาหารและเครื่องดื่ม " + DecFmt.format(bBean.getB_Total())) + "_";
            }
            t += ("colspan=3 align=center><font face=Angsana New size=3> " + "-----------------------------------------") + "_";
            t += ("colspan=2 align=left><font face=Angsana New size=2>" + "Sub-TOTAL : " + ItemCnt + " Item" + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_Total())) + "_";
            t += ("colspan=3 align=center><font face=Angsana New size=3> " + "-----------------------------------------") + "_";

            if (bBean.getB_ProDiscAmt() > 0) {
                t += ("colspan=2 align=right><font face=Angsana New size=2> " + "ลด Promotion" + "</td></font><td align=right><font face=Angsana New size=2>-" + Space + DecFmt.format(bBean.getB_ProDiscAmt())) + "_";
            }
            if (bBean.getB_SpaDiscAmt() > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=-2> " + "Special Disc     " + TAB + DecFmt.format(bBean.getB_SpaDiscAmt())) + "_";
            }

            if (bBean.getB_FastDiscAmt() > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=-2> " + "ลดเทศกาล.." + bBean.getB_FastDisc() + TAB + DecFmt.format(bBean.getB_FastDiscAmt())) + "_";
            }
            if (bBean.getB_EmpDiscAmt() > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=-2> " + "ลดพนักงาน.." + bBean.getB_EmpDisc() + TAB + DecFmt.format(bBean.getB_EmpDiscAmt())) + "_";
            }
            if (bBean.getB_TrainDiscAmt() > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=-2> " + "ลด Trainnee.." + bBean.getB_TrainDisc() + TAB + pUtility.DataFull2(DecFmt.format(bBean.getB_TrainDiscAmt()), AmtLength)) + "_";
            }
            if (bBean.getB_ItemDiscAmt() > 0) {
                t += ("colspan=2 align=left><font face=Angsana New size=2> " + "ลดตามรายการ(Item)" + "</td></font><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_ItemDiscAmt())) + "-_";
            }
            if (bBean.getB_Earnest() > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=-2> " + "หักคืนเงินมัดจำ  " + TAB + DecFmt.format(bBean.getB_Earnest())) + "_";
            }
            if (bBean.getB_SubDiscBath() > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2> " + "Disc(Bath)..  " + TAB + DecFmt.format(bBean.getB_SubDiscBath())) + "_";
            }
            if (bBean.getB_SubDiscAmt() > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=-2> " + "ส่วนลดคูปอง  " + TAB + DecFmt.format(bBean.getB_SubDiscAmt())) + "_";
            }

            if (bBean.getB_CuponDiscAmt() > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2>" + "**ส่วนลดยกเว้นไวน์ และรายการโปรโมชั่นปกติ**" + "_");
                t += ("colspan=2 align=left><font face=Angsana New size=2>" + Space + bBean.getB_CuponName() + " : " + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_CuponDiscAmt())) + " -" + "_";
            }
            totalDiscount = bBean.getB_ProDiscAmt() + bBean.getB_SpaDiscAmt()
                    + bBean.getB_FastDiscAmt() + bBean.getB_EmpDiscAmt() + bBean.getB_TrainDiscAmt()
                    + bBean.getB_SubDiscAmt() + bBean.getB_SubDiscBath() + bBean.getB_ItemDiscAmt() + bBean.getB_CuponDiscAmt() + bBean.getB_MemDiscAmt();
            if (CONFIG.getP_VatType().equals("I")) {
                if (bBean.getB_MemDiscAmt() > 0) {
                    t += ("colspan=2 align=left><font face=Angsana New size=2> " + "ลดสมาชิก.." + bBean.getB_MemDisc() + "</td><td align=right><font face=Angsana New size=2>- " + DecFmt.format(bBean.getB_MemDiscAmt())) + "_";

                }
                if (bBean.getB_ServiceAmt() > 0) {
                    t += ("colspan=2 align=left><font face=Angsana New size=2> " + Space + "ค่าบริการ : " + IntFmt.format(CONFIG.getP_Service()) + "% : " + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_ServiceAmt())) + " +_";
                }
                if (!CONFIG.getP_PayBahtRound().equals("O")) {
                    t += ("colspan =2 align=left><font face=Angsana New size=3> " + Space + "Net-Total " + "</td><td align=right><font face=Angsana New size=3>" + DecFmt.format(NumberUtil.UP_DOWN_NATURAL_BAHT(Math.round(bBean.getB_NetTotal())))) + "_";
                    if (CONFIG.getP_VatPrn().equals("Y")) {
                        t += ("colspan=2 align=left><font face=Angsana New size=2> " + Space + "Vat..." + IntFmt.format(CONFIG.getP_Vat()) + "%" + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_Vat())) + "_";
                    }
                    if (bBean.getB_NetDiff() > 0) {
                        t += ("colspan=2 align=left><font face=Angsana New size=2> " + Space + "Round" + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_NetDiff())) + "_";
                    }
                } else {
                    t += ("colspan =2 align=left><font face=Angsana New size=3> " + Space + "Net-Total " + "</td><td align=right><font face=Angsana New size=3>" + DecFmt.format((bBean.getB_NetTotal()))) + "_";
                }
            } else {
                if (bBean.getB_MemDiscAmt() > 0) {
                    t += ("colspan=2 align=left><font face=Angsana New size=2> " + "ลดสมาชิก.." + bBean.getB_MemDisc() + "</td><td align=right><font face=Angsana New size=2>- " + DecFmt.format(bBean.getB_MemDiscAmt())) + "_";
                }
                if (bBean.getB_ServiceAmt() > 0) {
                    t += ("colspan=2 align=left><font face=Angsana New size=2>" + Space + "Service :" + IntFmt.format(CONFIG.getP_Service()) + " %</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_ServiceAmt())) + "+_";
                }
                if (CONFIG.getP_VatType().contains("I")) {
                    t += ("colspan =2 align=left><font face=Angsana New size=2> " + Space + "Net-Amount.. " + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format((bBean.getB_NetTotal()))) + "_";
                }
                if (CONFIG.getP_VatType().contains("E")) {
                    t += ("colspan =2 align=left><font face=Angsana New size=2> " + Space + "Net-Amount.. " + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format((bBean.getB_Total() - totalDiscount + bBean.getB_ServiceAmt()))) + "_";
                }
                t += ("colspan =2 align=left><font face=Angsana New size=2> " + Space + "Vat..." + IntFmt.format(CONFIG.getP_Vat())) + "%" + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_Vat()) + "_";
                if (CONFIG.getP_VatType().equals("I")) {
                    t += ("colspan =2 align=left><font face=Angsana New size=3> " + "Net-Total.... " + "</td><td align=right><font face=Angsana New size=3>" + DecFmt.format((Math.round(bBean.getB_NetVat() + bBean.getB_NetNonVat())))) + "_";
                }
                if (CONFIG.getP_VatType().equals("E")) {
                    t += ("colspan =2 align=left><font face=Angsana New size32> " + "Net-Total.... " + "</td><td align=right><font face=Angsana New size=3>" + DecFmt.format((bBean.getB_NetVat() + bBean.getB_NetNonVat() + bBean.getB_Vat()))) + "_";
                }
                t += ("colspan=3 align=Center><font face=Angsana New size=2> " + "VAT INCLUDED") + "_";
            }
            if (bBean.getB_GiftVoucher() > 0) {
                t += ("colspan=3 align=center><font face=Angsana New size=3>" + "-----------------------------------------" + "_");
                t += ("colspan=2 align=left><font face=Angsana New size=2> " + "บัตรกำนัล.." + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_GiftVoucher())) + "_";

                mysqlConnect.open(this.getClass());
                try {
                    Statement stmt = mysqlConnect.getConnection().createStatement();
                    String CheckGift = "select * from t_gift where (refno='" + _RefNo + "')";
                    ResultSet rs = stmt.executeQuery(CheckGift);
                    while (rs.next()) {
                        t += ("colspan=3 align=left><font face=Angsana New size=2> " + TAB + rs.getString("giftbarcode") + rs.getString("giftno") + "@" + DecFmt.format(rs.getDouble("giftamt"))) + "_";
                    }
                    rs.close();
                    stmt.close();
                } catch (SQLException e) {
                    AppLogUtil.log(PPrint.class, "error", e);
                } finally {
                    mysqlConnect.closeConnection(this.getClass());
                }
            }
            t += ("colspan=3 align=center><font face=Angsana New size=3>" + "-----------------------------------------" + "_");
            if (bBean.getB_PayAmt() > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2> " + "CASH  : " + DecFmt.format(bBean.getB_PayAmt()) + Space + "Change : " + DecFmt.format(bBean.getB_Ton())) + "_";
            }
            if (bBean.getB_CrAmt1() > 0) {
                //get credit name
                String crName = "";
                mysqlConnect.open(this.getClass());
                try {
                    String sql = "select CrName from creditfile where crcode='" + bBean.getB_CrCode1() + "' limit 1";
                    Statement stmt = mysqlConnect.getConnection().createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        crName = ThaiUtil.ASCII2Unicode(rs.getString("CrName"));
                    }
                    rs.close();
                    stmt.close();
                } catch (SQLException e) {
                    AppLogUtil.log(PPrint.class, "error", e);
                } finally {
                    mysqlConnect.closeConnection(this.getClass());
                }
                t += ("colspan=3 align=left><font face=Angsana New size=2> " + crName) + "_";
                t += ("colspan=3 align=left><font face=Angsana New size=2> " + "XXXXXXXXXXX" + pUtility.Addzero(bBean.getB_CardNo1(), 16).substring(12, 16) + TAB + bBean.getB_AppCode1()) + "_";
                if (bBean.getB_CrCharge1() > 0) {
                    t += ("colspan=3 align=left><font face=Angsana New size=2> ") + "Credit Charge" + Space + DecFmt.format(bBean.getB_CrCharge1()) + Space + "%" + Space + DecFmt.format(bBean.getB_CrChargeAmt1()) + "_";
                }
                t += ("colspan=2 align=left><font face=Angsana New size=2> " + "Credit Payment" + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_CrAmt1())) + "_";
            }
            if (bBean.getB_Entertain1() > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2> " + "Entertain  : " + DecFmt.format(bBean.getB_Entertain1())) + "_";
                t += ("colspan=3 align=left><font face=Angsana New size=3>" + "Signature..." + "_");
                t += ("colspan=3 align=center><font face=Angsana New size=3>" + "-----------------------------------------" + "_");
            }
            if (bBean.getB_AccrAmt() > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=-2> " + "AR-" + bBean.getB_AccrCode() + " ลูกหนี้การค้า........" + DecFmt.format(bBean.getB_AccrAmt())) + "_";
                t += ("colspan=3 align=left><font face=Angsana New size=-2> " + pUtility.SeekArName(bBean.getB_AccrCode())) + "_";
            }

            if (!bBean.getB_MemCode().equals("")) {
                t += ("colspan=3 align=center><font face=Angsana New size=3> " + "-----------------------------------------") + "_";
                t += ("colspan=3 align=left><font face=Angsana New size=-2> " + "สมาชิก - " + bBean.getB_MemCode()) + "_";
                t += ("colspan=3 align=left><font face=Angsana New size=-2> " + ThaiUtil.ASCII2Unicode(bBean.getB_MemName())) + "_";
                t += ("colspan=3 align=left><font face=Angsana New size=-2> " + "   แต้มครั้งนี้ :           " + NumberUtil.showNumber(bBean.getB_MemCurSum()) + " แต้ม") + "_";
                t += ("colspan=3 align=left><font face=Angsana New size=-2> " + "   แต้มสะสมถึง  --- -- ----   " + NumberUtil.showNumber(bBean.getB_SumScore()) + " แต้ม") + "_";
                t += ("colspan=3 align=left><font face=Angsana New size=-2> " + "   บัตรหมดอายุวันที่ : " + DateUtil.toDateLocal(bBean.getB_MemEnd())) + "_";
            }
            t += ("colspan=3 align=center><font face=Angsana New size=3> " + "-----------------------------------------") + "_";
            t += ("colspan=3 align=left><font face=Angsana New size=2> " + "Receipt No: " + _RefNo) + "_";
            t += ("colspan=3 align=left><font face=Angsana New size=2> " + "COM:" + Space + bBean.getB_MacNo()) + "_";
            t += ("colspan=3 align=left><font face=Angsana New size=-2> " + " ") + "_";
            if (!CONFIG.getP_PrintRecpMessage().equals("")) {
                t += ("colspan=3 align=center><font face=Angsana New size=-2> " + CONFIG.getP_PrintRecpMessage()) + "_";
            }
            if (!POSHW.getFootting3().equals("")) {
                t += ("colspan=3 align=center><font face=Angsana New size=2> " + POSHW.getFootting3()) + "_";
            }
            if (!POSHW.getFootting2().equals("")) {
                t += ("colspan=3 align=center><font face=Angsana New size=2> " + POSHW.getFootting2()) + "_";
            }
            if (!POSHW.getFootting1().equals("")) {
                t += ("colspan=3 align=center><font face=Angsana New size=2> " + POSHW.getFootting1()) + "_";
            }

            t += ("colspan=3 align=left><font face=Angsana New size=-2> " + " ") + "_";
            EJPrint = false;
            t = changeLanguage(t);
            t1 = changeLanguage(t1);
            //print
            PrintDriver pd = new PrintDriver();
            String[] strs = t.split("_");
            for (String data1 : strs) {
                pd.addTextIFont(data1);
            }
            openDrawerDriver();
            pd.printHTML();
            if (ConfigFile.getProperties("printerStation").equals("true")) {
                PrintDriver pd1 = new PrintDriver();
                String[] strs1 = t1.split("_");

                for (String data2 : strs1) {
                    pd1.addTextIFont(data2);
                }
                pd1.printHTML();
            }
        }
    }

    public void printSubTotalBill(String _RefNo, String tableNo) {
        if (PublicVar.printdriver == true) {
            printSubTotalBillDriver(_RefNo, tableNo);
        } else {
            BillNoBean bBean = billControl.getData(_RefNo);

            TableFileBean tBean = tableFileControl.getData(tableNo);
            List<TSaleBean> listTSale = billControl.getAllTSale(_RefNo);

            int QtyLength = 3;
            int AmtLength = 10;
            int SubLength = 20;
            int SubLength2 = 13;
            int ItemCnt = 0;
            String VatStr;
            if (!POSHW.getPRNPort().equals("NONE")) {
                if (openPrint(POSHW.getPRNPort())) {
                    initPrinter();
                    openDrawer();
                    initPrinter();

                    PublicVar.P_LineCount = 0;
                    for (int i = 0; i < listTSale.size(); i++) {
                        TSaleBean bean = (TSaleBean) listTSale.get(i);
                        if (!bean.getR_Void().equals("V")) {
                            ItemCnt = (int) (ItemCnt + bean.getR_Quan());
                        }
                    }
                    double Vat = CONFIG.getP_Vat();
                    selectStye(14);
                    print(POSHW.getHeading1());
                    print(POSHW.getHeading2());
                    selectStye(1);
                    print(POSHW.getHeading3());
                    print(POSHW.getHeading4());
                    print("REG ID :" + POSHW.getMacNo());
                    if (CONFIG.getP_PrintDetailOnRecp().equals("Y")) {
                        Date dateP = new Date();
                        print(" ");
                        print(pUtility.DataFullR(PPrint_DatefmtThai.format(dateP).replace("/", " / "), 25) + pUtility.DataFullR(" TABLE : " + tableNo, 15));
                        print(pUtility.DataFullR("CC : " + IntFmt.format(bBean.getB_Cust()) + " Seat", 15) + pUtility.DataFullR(" ", 11) + pUtility.DataFullR("NAME: " + getLastEmployeeCheckBill(tableNo, _RefNo), 15));
                        print(pUtility.DataFullR("COM: " + PublicVar.MACNO, 15));
                        if (!tBean.getMemName().trim().equals("")) {
                            print(" ");
                            print(pUtility.DataFullR(" ", 26) + pUtility.DataFullR("NAME CC: " + tBean.getMemName(), 15));
                        }
                        print(" ");
                        print("----------------------------------------");
                        for (int i = 0; i < listTSale.size(); i++) {
                            TSaleBean bean = (TSaleBean) listTSale.get(i);
                            if (bean.getR_Vat().equals("N")) {
                                VatStr = "*";
                            } else {
                                VatStr = "-";
                            }
                            if (bean.getR_Void().equals("V")) {
                                selectStye(12);
                                if (CONFIG.getP_CodePrn().equals("Y")) {
                                } else {
                                    String R_PName = bean.getR_PName();
                                    if (bean.getR_PName().length() > 20) {
                                        R_PName = R_PName.substring(0, 21);
                                    }
                                }
                                selectStye(13);
                            } else {
                                if (bean.getR_PrAmt() == 0) {
                                    if (CONFIG.getP_CodePrn().equals("Y")) {
                                        print(bean.getR_PName());
                                        print(bean.getR_Normal() + VatStr + pUtility.DataFullR(bean.getR_PluCode(), 20) + "  " + pUtility.DataFull(IntFmt.format(bean.getR_Quan()), QtyLength) + pUtility.DataFull(DecFmt.format(bean.getR_Total()), AmtLength) + bean.getR_ETD());
                                    } else {
                                        String R_PName = bean.getR_PName();
                                        String space = "  ";
                                        int sizeNew = 20;
                                        if (bean.getR_PName().length() > 20) {
                                            sizeNew = 21;
                                            space = " ";
                                            R_PName = R_PName.substring(0, 21);
                                        }
                                        print(bean.getR_Normal() + VatStr + pUtility.DataFullR(R_PName, sizeNew) + space + pUtility.DataFull(IntFmt.format(bean.getR_Quan()), QtyLength) + pUtility.DataFull(DecFmt.format(bean.getR_Total()), AmtLength) + bean.getR_ETD());
                                    }
                                } else {
                                    if (CONFIG.getP_CodePrn().equals("Y")) {
                                        print(bean.getR_PName());
                                        print(bean.getR_Normal() + VatStr + pUtility.DataFullR(bean.getR_PluCode(), 20) + "  " + pUtility.DataFull(IntFmt.format(bean.getR_Quan()), QtyLength) + pUtility.DataFull(DecFmt.format(bean.getR_Total()), AmtLength) + bean.getR_ETD());
                                    } else {
                                        String R_PName = bean.getR_PName();
                                        int sizeNew = 20;
                                        String space = "  ";
                                        if (bean.getR_PName().length() > 20) {
                                            sizeNew = 21;
                                            space = " ";
                                            R_PName = R_PName.substring(0, 21);
                                        }
                                        print(bean.getR_Normal() + VatStr + pUtility.DataFullR(R_PName, sizeNew) + space + pUtility.DataFull(IntFmt.format(bean.getR_Quan()), QtyLength) + pUtility.DataFull(DecFmt.format(bean.getR_Total()), AmtLength) + bean.getR_ETD());
                                    }
                                    if (bean.getR_PrType().equals("-P")) {
                                        if (bean.getR_PrAmt() > 0) {
                                            print("   **Promotion  " + bean.getR_PrCode() + " " + pUtility.DataFull(pUtility.SeekPromotionName(bean.getR_PrCode()), 20));
                                        }
                                    }
                                    if (bean.getR_PrType().equals("-I")) {
                                        if (bean.getR_PrDisc() != 0) {
                                            print("   **Item-Discount " + bean.getR_PrCode() + " " + pUtility.DataFull(DecFmt.format(bean.getR_PrDisc() - bean.getR_Redule()), QtyLength) + "%");
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Date dateP = new Date();
                        print(PPrint_DatefmtThai.format(dateP).replace("/", " / ") + " " + "Cashier:" + PublicVar._User + " Mac:" + PublicVar.MACNO);
                        print("----------------------------------------");
                        print("     อาหารและเครื่องดื่ม " + pUtility.DataFull(DecFmt.format(bBean.getB_Total()), AmtLength));
                    }
                    printEntertain(bBean.getB_Table());
                    print("----------------------------------------");
                    if (bBean.getB_ProDiscAmt() > 0) {
                        print("    " + pUtility.DataFullR("ลด Promotion     ", SubLength) + pUtility.DataFull(DecFmt.format(bBean.getB_ProDiscAmt()), AmtLength));
                    }
                    if (bBean.getB_SpaDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("Special Disc     ", SubLength) + pUtility.DataFull(DecFmt.format(bBean.getB_SpaDiscAmt()), AmtLength));
                    }
                    if (bBean.getB_MemDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("ลดสมาชิก..........", SubLength2) + pUtility.DataFull(bBean.getB_MemDisc(), 8) + pUtility.DataFull(DecFmt.format(bBean.getB_MemDiscAmt()), AmtLength));
                    }
                    if (bBean.getB_FastDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("ลดเทศกาล.........", SubLength2) + pUtility.DataFull(bBean.getB_FastDisc(), 8) + pUtility.DataFull(DecFmt.format(bBean.getB_FastDiscAmt()), AmtLength));
                    }
                    if (bBean.getB_EmpDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("ลดพนักงาน.........", SubLength2) + pUtility.DataFull(bBean.getB_EmpDisc(), 8) + pUtility.DataFull(DecFmt.format(bBean.getB_EmpDiscAmt()), AmtLength));
                    }
                    if (bBean.getB_TrainDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("ลด Trainnee......", SubLength2) + pUtility.DataFull(bBean.getB_TrainDisc(), 8) + pUtility.DataFull(DecFmt.format(bBean.getB_TrainDiscAmt()), AmtLength));
                    }
                    if (bBean.getB_ItemDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("ลดตามรายการ(Item)", SubLength) + pUtility.DataFull(DecFmt.format(bBean.getB_ItemDiscAmt()), AmtLength));
                    }
                    if (bBean.getB_ServiceAmt() > 0) {
                        print("     " + pUtility.DataFullR("ค่าบริการ (Service)     ", 23) + pUtility.DataFull(DecFmt.format(bBean.getB_ServiceAmt()), 9));
                    }
                    if (bBean.getB_Earnest() > 0) {
                        print("     " + pUtility.DataFullR("หักคืนเงินมัดจำ           ", 23) + pUtility.DataFull(DecFmt.format(bBean.getB_Earnest()), 9));
                    }
                    if (bBean.getB_SubDiscBath() > 0) {
                        print("     " + pUtility.DataFullR("ส่วนลดบาท                                 ", 23) + pUtility.DataFull(DecFmt.format(bBean.getB_SubDiscBath()), 9));
                    }
                    if (bBean.getB_GiftVoucher() > 0) {
                        print("     " + pUtility.DataFullR("บัตรกำนัล               ", SubLength) + pUtility.DataFull(DecFmt.format(bBean.getB_GiftVoucher()), AmtLength));

                        try {
                            mysqlConnect.open(this.getClass());
                            String sqlGetGiffNo = "select giftno from t_gift where refno='" + bBean.getB_Refno() + "' limit 1;";
                            String giffno;
                            ResultSet rsGetGiftno = mysqlConnect.executeQuery(sqlGetGiffNo);
                            if (rsGetGiftno.next()) {
                                giffno = rsGetGiftno.getString("giftno");
                                print("Gift-No.    " + pUtility.DataFullR(giffno, 30));
                                rsGetGiftno.close();
                            }
                        } catch (SQLException e) {
                            AppLogUtil.log(PPrint.class, "error", e);
                        } finally {
                            mysqlConnect.closeConnection(this.getClass());
                        }
                    }
                    if (bBean.getB_CuponDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("ลดบัตรคูปอง      ", SubLength));
                        print("     " + pUtility.DataFullR(bBean.getB_CuponName(), SubLength) + pUtility.DataFull(DecFmt.format(bBean.getB_CuponDiscAmt()), AmtLength));
                        mysqlConnect.open(this.getClass());
                        try {
                            Statement stmt = mysqlConnect.getConnection().createStatement();
                            String CheckGift = "select * from t_gift where (refno='" + _RefNo + "')";
                            ResultSet rs = stmt.executeQuery(CheckGift);
                            while (rs.next()) {
                                print("   " + pUtility.DataFull(rs.getString("giftbarcode"), 25) + "@" + pUtility.DataFull(DecFmt.format(rs.getDouble("giftamt")), AmtLength));
                            }
                            rs.close();
                            stmt.close();
                        } catch (SQLException e) {
                            AppLogUtil.log(PPrint.class, "error", e);
                        } finally {
                            mysqlConnect.closeConnection(this.getClass());
                        }
                    } else {
                        List<Object[]> list = printCuponName(_RefNo);
                        String CuName;
                        double CuDisc;
                        if (list != null && !list.isEmpty()) {
                            CuName = list.get(0)[0].toString();
                            CuDisc = Double.parseDouble(list.get(0)[1].toString());
                            print(" " + pUtility.DataFullR("ลดบัตรคูปอง      ", SubLength));
                            print("    " + pUtility.DataFullR(CuName, 30) + " " + pUtility.DataFullR(DecFmt.format(CuDisc) + "", 7));

                        }
                    }
                    if (CONFIG.getP_VatType().equals("I")) {
                        selectStye(14);//แก้ไขใหม่
                        print("");
                        print("     Sub-TOTAL         " + pUtility.DataFull(DecFmt.format(bBean.getB_NetTotal()), AmtLength));
                        selectStye(1);
                        if (CONFIG.getP_VatPrn().equals("Y")) {
                            print(pUtility.DataFull("      Vat..." + IntFmt.format(Vat) + "%", 30) + pUtility.DataFull(DecFmt.format(bBean.getB_Vat()), AmtLength));
                            print("----------------------------------------");

                        }
                        if (PublicVar.b_entertain > 0) {
                            print("     " + pUtility.DataFullR("Entertain.....", SubLength) + pUtility.DataFull(DecFmt.format(PublicVar.b_entertain), AmtLength));
                            printEntertain(bBean.getB_Table());
                        }
                    } else {
                        print(pUtility.DataFull("      Net-Amount ", SubLength) + pUtility.DataFull(DecFmt.format(bBean.getB_NetVat() + bBean.getB_NetNonVat()), AmtLength));
                        print(pUtility.DataFull("      Vat....... ", SubLength) + pUtility.DataFull(DecFmt.format(CONFIG.getP_Vat()), QtyLength) + "%" + pUtility.DataFull(DecFmt.format(bBean.getB_Vat()), AmtLength));
                        print("VAT INCLUDED");
                    }

                    if (bBean.getB_PayAmt() > 0) {
                        print("CASH  : " + pUtility.DataFull(DecFmt.format(bBean.getB_PayAmt()), AmtLength) + "  CHANGE : " + pUtility.DataFull(DecFmt.format(bBean.getB_Ton()), AmtLength));
                    }
                    if (bBean.getB_CrAmt1() > 0) {
                        //get credit name
                        String crName = "";
                        mysqlConnect.open(this.getClass());
                        try {
                            String sql = "select CrName from creditfile where crcode='" + bBean.getB_CrCode1() + "' limit 1";
                            Statement stmt = mysqlConnect.getConnection().createStatement();
                            ResultSet rs = stmt.executeQuery(sql);
                            if (rs.next()) {
                                crName = ThaiUtil.ASCII2Unicode(rs.getString("CrName"));
                            }
                            rs.close();
                            stmt.close();
                        } catch (SQLException e) {
                            AppLogUtil.log(PPrint.class, "error", e);
                        } finally {
                            mysqlConnect.closeConnection(this.getClass());
                        }

                        print(bBean.getB_CrCode1() + "  " + crName);
                        print("XXXXXXXXXXX" + pUtility.Addzero(bBean.getB_CardNo1(), 16).substring(12, 16) + "  " + bBean.getB_AppCode1());
                        print("Credit Payment               " + pUtility.DataFull(DecFmt.format(bBean.getB_CrAmt1()), AmtLength));
                    }

                    if (bBean.getB_AccrAmt() > 0) {
                        print("AR-" + bBean.getB_AccrCode() + " ลูกหนี้การค้า........" + pUtility.DataFull(DecFmt.format(bBean.getB_AccrAmt()), AmtLength));
                        print(pUtility.DataFullR(pUtility.SeekArName(bBean.getB_AccrCode()), 38));
                    }

                    if (!bBean.getB_MemCode().equals("")) {
                        print("----------------------------------------");
                        print("สมาชิก - " + bBean.getB_MemCode());
                        print(ThaiUtil.ASCII2Unicode(bBean.getB_MemName()));
                        print("   แต้มครั้งนี้ :           " + NumberUtil.showNumber(bBean.getB_MemCurSum()) + " แต้ม");
                        print("   แต้มสะสมถึง  --- -- ----   " + NumberUtil.showNumber(bBean.getB_SumScore()) + " แต้ม");
                        print("   บัตรหมดอายุวันที่ : " + DateUtil.toDateLocal(bBean.getB_MemEnd()));
                    }

                    print("----------------------------------------");
                    selectStye(1);
                    print(" ");
                    selectStye(5);
                    if (!tBean.getMemName().equals("")) {
                        print("Name CC : " + pUtility.DataFullR(tBean.getMemName(), 15));
                    }
                    print(pUtility.DataFullR(" ", 12) + " " + "Receipt No: " + _RefNo);
                    selectStye(1);
                    print(" ");
                    if (!CONFIG.getP_PrintRecpMessage().equals("")) {
                        print(CONFIG.getP_PrintRecpMessage());
                    }
                    selectStye(14);
                    if (!POSHW.getFootting3().equals("")) {
                        print(POSHW.getFootting3());
                    }
                    if (!POSHW.getFootting1().equals("")) {
                        print(POSHW.getFootting1());
                    }
                    if (!POSHW.getFootting2().equals("")) {
                        print(POSHW.getFootting2());
                    }

                    print(" ");
                    print(" ");
                    print("");
                    print(" ");
                    print(" ");
                    print(" ");
                    EJPrint = false;
                    print(" ");
                    print(" ");
                    print(" ");
                    cutPaper();
                    closePrint();
                }
            }
        }
    }

    public void printCheckBillDriver(String tableNo) {
        AppLogUtil.info("printCheckBillDriver table = " + tableNo);

        PublicVar.printerCheckBill = true;
        PrintDriver pd = new PrintDriver();
        String t = "";
        String t1 = "";//Header1
        double totalDiscount;
        String cuponCode = "";

        if (CONFIG.getP_PrintSum().equals("Y")) {
            List<BalanceBean> listBeanNoVoid = balanceControl.getAllBalanceNoVoidSum(tableNo);
            TableFileBean tBean = tableFileControl.getData(tableNo);
            int ItemCnt = 0;
            String VatStr;
            double totalNonVat = 0;
            totalDiscount = tBean.getProDiscAmt() + tBean.getSpaDiscAmt()
                    + tBean.getFastDiscAmt() + tBean.getEmpDiscAmt() + tBean.getTrainDiscAmt()
                    + tBean.getSubDiscAmt() + tBean.getDiscBath() + tBean.getItemDiscAmt() + tBean.getCuponDiscAmt() + tBean.getMemDiscAmt();

            for (int i = 0; i < listBeanNoVoid.size(); i++) {
                BalanceBean bean = (BalanceBean) listBeanNoVoid.get(i);
                if (!bean.getR_Void().equals("V")) {
                    ItemCnt = (int) (ItemCnt + bean.getR_Quan());
                    if (bean.getR_Vat().equals("N")) {
                        totalNonVat += bean.getR_Total();
                    }

                }
            }
            double vatPrint = ServiceControl.getDouble(tBean.getTAmount() - totalNonVat, "PAYMENT") - (totalDiscount) + tBean.getServiceAmt();

            if (CONFIG.getP_VatType().equals("I")) {
                vatPrint = vatPrint * CONFIG.getP_Vat() / (100 + CONFIG.getP_Vat());
            }
            if (CONFIG.getP_VatType().equals("E")) {
                vatPrint = vatPrint * CONFIG.getP_Vat() / 100;
            }
            if (POSHW.getHeading1().length() >= 18) {
                String[] strs = POSHW.getHeading1().trim().replace(" ", Space).split("_");
                for (String data : strs) {
                    t1 += "colspan=3 align=center><font face=Angsana New size=3>" + data + "_";
                }
            } else {
                if (!POSHW.getHeading1().equals("") || POSHW.getHeading1() != null) {
                    t1 += "colspan=3 align=center><font face=Angsana New size=3>" + POSHW.getHeading1().trim().replace(" ", "&nbsp; ") + "_";
                }
            }
            if (POSHW.getHeading2().length() >= 18) {
                String[] strs = POSHW.getHeading2().trim().replace(" ", Space).split("_");
                for (String data : strs) {
                    t1 += "colspan=3 align=center><font face=Angsana New size=3>" + data + "_";
                }
            } else {
                if (!POSHW.getHeading2().equals("")) {
                    t1 += "colspan=3 align=center><font face=Angsana New size=3>" + POSHW.getHeading2().trim().replace(" ", "&nbsp; ") + "_";
                }
            }
            if (POSHW.getHeading3().length() >= 18) {
                String[] strs = POSHW.getHeading3().trim().replace(" ", Space).split("_");
                for (String data : strs) {
                    t1 += "colspan=3 align=center><font face=Angsana New size=3>" + data + "_";
                }
            } else {
                if (!POSHW.getHeading3().equals("")) {
                    t1 += "colspan=3 align=center><font face=Angsana New size=3>" + POSHW.getHeading3().trim().replace(" ", "&nbsp; ") + "_";
                }
            }
            if (POSHW.getHeading4().length() >= 18) {
                String[] strs = POSHW.getHeading4().trim().replace(" ", Space).split("_");
                for (String data : strs) {
                    t1 += "colspan=3 align=center><font face=Angsana New size=3>" + data + "_";
                }
            } else {
                if (!POSHW.getHeading4().equals("")) {
                    t1 += "colspan=3 align=center><font face=Angsana New size=3>" + POSHW.getHeading4().trim().replace(" ", "&nbsp; ") + "_";
                }
            }
            t1 += "colspan=3 align=center><font face=Angsana New size=3>" + "-----------------------------------------_";

            Date dateP = new Date();
            t1 += "colspan=3 align=left><font face=Angsana New size=2> "
                    + PPrint_DatefmtThai.format(dateP).replace("/", " / ") + "_";
            t1 += "colspan=3 align=left><font face=Angsana New size=2> "
                    + "TABLE :" + Space + tableNo + "_";
            t1 += "colspan=2 align=left><font face=Angsana New size=2> " + "CC " + IntFmt.format(tBean.getTCustomer())
                    + " Seat :" + "</td><td align=right><font face=Angsana New size=2>"
                    + "NAME: " + Space
                    + getLastEmployee(tableNo) + "_";
            t1 += "colspan=3 align=center><font face=Angsana New size=3>" + "-----------------------------------------_";
            for (int i = 0; i < listBeanNoVoid.size(); i++) {
                BalanceBean bean = (BalanceBean) listBeanNoVoid.get(i);
                if (bean.getR_Void().equals("V")) {
                } else {
                    if (bean.getR_Vat().equals("V")) {
                        VatStr = "-";
                    } else {
                        VatStr = "*";
                    }
                    if (bean.getR_PrAmt() == 0) {
                        //ให้พิมพ์รหัสสินค้าบนใบเสร็จ
                        if (CONFIG.getP_CodePrn().equals("Y")) {
                            t1 += "colspan=3 align=left><font face=Angsana New size=2>" + subStringText(bean.getR_PName(), 16) + "_";
                            t1 += "colspan=3 align=left><font face=Angsana New size=2>" + bean.getR_Normal() + VatStr + bean.getR_PluCode() + TAB + df.format(bean.getR_Quan()) + TAB2 + DecFmt.format(bean.getR_Total()) + bean.getR_ETD() + "_";
                        } else {
                            t1 += "style=vertical-align:topcolspan=1; width=-90%; align=left;><font face=Angsana New size=2>"
                                    + IntFmt.format(bean.getR_Quan()) + " "
                                    + "</font></td><td align=left width=70><font face=Angsana New size=2>"
                                    + subStringText(bean.getR_PName(), 30)
                                    + "</font></td><td align=right width=70><font face=Angsana New size=2>"
                                    + IntFmt.format(bean.getR_Total()) + bean.getR_ETD() + "_";
                            if (!bean.getR_Opt1().equals("")) {
                                t1 += "colspan=3 align=left><font face=Angsana New size=2>"
                                        + subStringText(ThaiUtil.ASCII2Unicode(TAB + bean.getR_Opt1()), 30) + "_";
                            }
                        }
                    } else {
                        if (CONFIG.getP_CodePrn().equals("Y")) {
                            t1 += "colspan=3 align=left><font face=Angsana New size=2>" + subStringText(bean.getR_PName(), 16) + "_";
                            t1 += "colspan=3 align=left><font face=Angsana New size=2>" + bean.getR_PluCode() + TAB + df.format(bean.getR_Quan()) + TAB2 + DecFmt.format(bean.getR_Total()) + bean.getR_ETD() + "_";
                        } else {
                            t1 += "colspan=2 align=left><font face=Angsana New size=2>" + IntFmt.format(bean.getR_Quan()) + VatStr + bean.getR_PName() + "</td></font><td align=right><font face=Angsana New size=2> " + DecFmt.format(bean.getR_Total()) + bean.getR_ETD() + "_";
                        }
                        if (bean.getR_PrType().equals("-P")) {
                            if (bean.getR_PrAmt() > 0) {
                                t1 += "colspan=3 align=left><font face=Angsana New size=2>" + "**Promotion  " + bean.getR_PrCode() + "_";
                            }
                        }
                        if (bean.getR_PrType().equals("-I")) {
                            if (bean.getR_PrDisc() != 0) {
                                t1 += "colspan=3 align=left><font face=Angsana New size=2>" + TAB + "**Item Discount " + bean.getR_PrCode() + DecFmt.format(bean.getR_PrAmt()) + " -_";
                            }
                        }
                    }
                }
                if (!bean.getR_PrCuCode().equals("") || !bean.getR_PrCuCode().equals("null")) {
                    cuponCode = bean.getR_PrCuCode();
                }
            }
            t1 += "align=center colspan=3><font face=Angsana New size=2>" + "-----------------------------------------_";
            t1 += "align=left colspan=2><font face=Angsana New size=2>" + "Sub-TOTAL : " + ItemCnt + " Item" + "</td><td align=right ><font face=Angsana New size=2>" + DecFmt.format(tBean.getTAmount()) + "_";
            t1 += "align=center colspan=3><font face=Angsana New size=2>" + "-----------------------------------------_";

            if (tBean.getProDiscAmt() > 0) {
                t1 += "colspan=2 align=right><font face=Angsana New size=2>" + TAB + "ลด Promotion" + "</td><td align=right ><font face=Angsana New size=2>- " + DecFmt.format(tBean.getProDiscAmt()) + "_";
            }
            if (tBean.getSpaDiscAmt() > 0) {
                t1 += "colspan=2 align=right><font face=Angsana New size=2>" + "Special Disc.." + "</td><td align=right ><font face=Angsana New size=2>- " + DecFmt.format(tBean.getSpaDiscAmt()) + "_";
            }
            if (tBean.getMemDiscAmt() > 0) {
                t1 += "colspan=2 align=left><font face=Angsana New size=2>" + "ลดสมาชิก.." + tBean.getMemDisc() + "</td><td align=right ><font face=Angsana New size=2> " + DecFmt.format(tBean.getMemDiscAmt()) + "_";
            }
            if (tBean.getFastDiscAmt() > 0) {
                t1 += "colspan=2 align=right><font face=Angsana New size=2>" + "ลดเทศกาล.." + "</td><td align=right ><font face=Angsana New size=2>- " + tBean.getFastDisc() + DecFmt.format(tBean.getFastDiscAmt()) + "_";
            }
            if (tBean.getEmpDiscAmt() > 0) {
                t1 += "colspan=2 align=right><font face=Angsana New size=2>" + "ลดพนักงาน.." + "</td><td align=right ><font face=Angsana New size=2>- " + tBean.getEmpDisc() + DecFmt.format(tBean.getEmpDiscAmt()) + "_";
            }
            if (tBean.getTrainDiscAmt() > 0) {
                t1 += "colspan=2 align=right><font face=Angsana New size=2>" + "ลด Trainnee.." + "</td><td align=right ><font face=Angsana New size=2>- " + tBean.getTrainDisc() + DecFmt.format(tBean.getTrainDiscAmt()) + "_";
            }
            if (tBean.getSubDiscAmt() > 0) {
                t1 += "colspan=2 align=right><font face=Angsana New size=2>" + "ลดคูปอง.." + "</td><td align=right ><font face=Angsana New size=2> " + tBean.getSubDisc() + DecFmt.format(tBean.getSubDiscAmt()) + "-_";
            }
            if (tBean.getDiscBath() > 0) {
                t1 += "colspan=2 align=left><font face=Angsana New size=2>" + "Disc(Bath).." + "</td><td align=right ><font face=Angsana New size=2>- " + DecFmt.format(tBean.getDiscBath()) + "_";
            }
            if (tBean.getItemDiscAmt() > 0) {
                t1 += "colspan=2 align=left><font face=Angsana New size=2>" + "ลดรายการ(Item)" + "</td><td align=right ><font face=Angsana New size=2>- " + DecFmt.format(tBean.getItemDiscAmt()) + "_";
            }

            if (tBean.getCuponDiscAmt() > 0) {
                t1 += "colspan=2 align=right><font face=Angsana New size=2>" + getCuponName(cuponCode) + "</td><td align=right ><font face=Angsana New size=2> " + DecFmt.format(tBean.getCuponDiscAmt()) + "-_";
            }
            if (tBean.getServiceAmt() > 0) {
                t1 += "colspan=2 align=left><font face=Angsana New size=2>" + Space + "Service :" + TAB + DecFmt.format(CONFIG.getP_Service()) + " %" + "</td><td align=right ><font face=Angsana New size=2>" + DecFmt.format(tBean.getServiceAmt()) + " +_";
            }
            t1 += "colspan=2 align=left><font face=Angsana New size=2>" + Space + "Net-Amount.." + "</td><td align=right ><font face=Angsana New size=2>" + DecFmt.format((tBean.getTAmount() - totalDiscount + tBean.getServiceAmt())) + "_";
            t1 += "colspan=2 align=left><font face=Angsana New size=2>" + Space + "VAT.." + "</td><td align=right ><font face=Angsana New size=2>" + DecFmt.format(vatPrint) + "_";

            if (CONFIG.getP_VatType().equals("I")) {
                if (!CONFIG.getP_PayBahtRound().equals("O")) {
                    t1 += "colspan=2 align=left><font face=Angsana New size=3>" + "Net-Total...." + "</td><td align=right ><font face=Angsana New size=3>" + DecFmt.format(NumberUtil.UP_DOWN_NATURAL_BAHT((tBean.getNetTotal()))) + "_";
                    double round;
                    round = tBean.getNetTotal() - NumberUtil.UP_DOWN_NATURAL_BAHT((tBean.getNetTotal()));
                    if (round != 0.00) {
                        t1 += "colspan=2 align=left><font face=Angsana New size=2>" + "Round...." + "</td><td align=right ><font face=Angsana New size=2>" + DecFmt.format(round) + "_";
                    }
                } else {
                    t1 += "colspan=2 align=left><font face=Angsana New size=3>" + "Net-Total...." + "</td><td align=right ><font face=Angsana New size=3>" + DecFmt.format((tBean.getNetTotal())) + "_";
                }

            }
            if (CONFIG.getP_VatType().equals("E")) {
                t1 += "colspan=2 align=left><font face=Angsana New size=3>" + "Net-Total...." + "</td><td align=right ><font face=Angsana New size=3>" + DecFmt.format((tBean.getTAmount() - totalDiscount + tBean.getServiceAmt() + vatPrint)) + "_";
            }
            t1 += "align=center colspan=3><font face=Angsana New size=3>" + "-----------------------------------------_";
            t1 += "align=center colspan=3><font face=Angsana New size=2>" + "COM: " + PublicVar.MACNO + " **No Receipt**" + "_";
            t1 += "colspan=3 align=center>_";

            if (!POSHW.getFootting1().equals("")) {
                t1 += "align=center colspan=3><font face=Angsana New size=2>" + POSHW.getFootting1().trim().replace(" ", Space) + "_";
            }
            if (!POSHW.getFootting2().equals("")) {
                t1 += "align=center colspan=3><font face=Angsana New size=2>" + POSHW.getFootting2().trim().replace(" ", Space) + "_";
            }
            if (!POSHW.getFootting3().equals("")) {
                t1 += "align=center colspan=3><font face=Angsana New size=2>" + POSHW.getFootting3().trim().replace(" ", Space) + "_";
            }
            t += "align=center colspan=3>_";
            String t2 = "";
            if (!tBean.getMemCode().equals("")) {
                MemberBean MemBean = MemmaterController.getMember(tBean.getMemCode());
                t2 += "align=left colspan=3><font face=Angsana New size=2>สมาชิก - " + MemBean.getMember_Code() + "_";
                t2 += "align=left colspan=3><font face=Angsana New size=2>" + ThaiUtil.ASCII2Unicode(MemBean.getMember_NameThai()) + "_";
                t2 += "align=left colspan=3><font face=Angsana New size=2>" + ThaiUtil.ASCII2Unicode(MemBean.getMember_Remark1()) + "_";
                t2 += "align=lefts colspan=3><font face=Angsana New size=2>" + ThaiUtil.ASCII2Unicode(MemBean.getMember_Remark2()) + "_";
            }

            //Check Language เปลี่ยนภาษาไทย
            if (PublicVar.languagePrint.equals("TH")) {
                t1 = t1.replace("CC", "ลูกค้า : ").replace("Seat: ", "ที่").replace("NAME", "ชื่อ").replace("Service", "ค่าบริการ").replace("VAT", "ภาษีมูลค่าเพิ่ม").replace("Sub-TOTAL", "มูลค่ารวม").replace("No Receipt", "ไม่ใช่ใบเสร็จรับเงิน");
                t1 = t1.replace("Net-Amount", "รวม").replace("Net-Total", "รวมที่ต้องชำระ").replace("TABLE", "โต๊ะ").replace("Seat", "ที่").replace("Round", "ปัดเศษ");
                t1 = t1.replace("Disc(Bath)..", "ส่วนลด(บาท)..");

            }
            //print
            String[] strs = t.split("_");
            String[] strsHead1 = t1.split("_");
            String[] strsMember = t2.split("_");

            for (String data1 : strsHead1) {
                pd.addTextIFont(data1);
            }

            for (String data : strs) {
                pd.addTextIFont(data);
            }
            for (String dataMem : strsMember) {
                pd.addTextIFont(dataMem);
            }

            pd.printHTML();

        } else {
            List<BalanceBean> listBeanNoVoid = balanceControl.getAllBalanceNoVoid(tableNo);
            TableFileBean tBean = tableFileControl.getData(tableNo);
            int ItemCnt = 0;
            String VatStr;
            double vatPrint = tBean.getNetTotal();
            for (int i = 0; i < listBeanNoVoid.size(); i++) {
                BalanceBean bean = (BalanceBean) listBeanNoVoid.get(i);
                if (!bean.getR_Void().equals("V")) {
                    ItemCnt = (int) (ItemCnt + bean.getR_Quan());
                }
            }
            if (CONFIG.getP_VatType().equals("I")) {
                vatPrint = vatPrint * 7 / 107;
            }
            if (CONFIG.getP_VatType().equals("E")) {
                vatPrint = vatPrint * 7 / 100;
            }
            if (POSHW.getHeading1().length() >= 18) {
                String[] strs = POSHW.getHeading1().trim().replace(" ", Space).split("_");
                for (String data : strs) {
                    t1 += "colspan=3 align=center><font face=Angsana New size=3>" + data + "_";
                }
            } else {
                t1 += "colspan=3 align=center><font face=Angsana New size=3>" + POSHW.getHeading1().trim().replace(" ", "&nbsp; ") + "_";
            }
            if (POSHW.getHeading2().length() >= 18) {
                String[] strs = POSHW.getHeading2().trim().replace(" ", Space).split("_");
                for (String data : strs) {
                    t1 += "colspan=3 align=center><font face=Angsana New size=3>" + data + "_";
                }
            } else {
                t1 += "colspan=3 align=center><font face=Angsana New size=3>" + POSHW.getHeading2().trim().replace(" ", "&nbsp; ") + "_";
            }
            t1 += "colspan=3 align=center><font face=Angsana New size=3>" + "-----------------------------------------_";

            Date dateP = new Date();
            t1 += "colspan=2 align=left><font face=Angsana New size=-2> "
                    + PPrint_DatefmtThai.format(dateP).replace("/", " / ")
                    + "</td><td align=right><font face=Angsana New size=-2>"
                    + "TABLE :" + Space + tableNo + "_";
            t1 += "colspan=2 align=left><font face=Angsana New size=-2> " + "CC : " + IntFmt.format(tBean.getTCustomer())
                    + " Seat :" + "</td><td align=right><font face=Angsana New size=-2>"
                    + "NAME: " + Space
                    + getLastEmployee(tableNo) + "_";
            t1 += "colspan=3 align=center><font face=Angsana New size=3>" + "-----------------------------------------_";
            for (int i = 0; i < listBeanNoVoid.size(); i++) {
                BalanceBean bean = (BalanceBean) listBeanNoVoid.get(i);
                if (bean.getR_Void().equals("V")) {
                } else {
                    if (bean.getR_Vat().equals("V")) {
                        VatStr = "-";
                    } else {
                        VatStr = "*";
                    }
                    if (bean.getR_PrAmt() == 0) {
                        //ให้พิมพ์รหัสสินค้าบนใบเสร็จ
                        if (CONFIG.getP_CodePrn().equals("Y")) {
                            t1 += "colspan=3 align=left><font face=Angsana New size=3>" + subStringText(bean.getR_PName(), 16) + "_";
                            t1 += "colspan=3 align=left><font face=Angsana New size=3>" + bean.getR_Normal() + VatStr + bean.getR_PluCode() + TAB + df.format(bean.getR_Quan()) + TAB2 + DecFmt.format(bean.getR_Total()) + bean.getR_ETD() + "_";
                        } else {
                            t1 += "align=left width=-90%><font face=Angsana New size=2>"
                                    + df.format(bean.getR_Quan())
                                    + "</td></font><td align=left width=-30%><font face=Angsana New size=2>"
                                    + subStringText(bean.getR_PName(), 20)
                                    + "</td></font><td align=right width=50><font face=Angsana New size=2>"
                                    + DecFmt.format(bean.getR_Total()) + bean.getR_ETD() + "_";
                            if (!bean.getR_Opt1().equals("")) {
                                t1 += "></td><td align=left colspan=2>"
                                        + "<font face=Angsana New size=2>"
                                        + subStringText(ThaiUtil.ASCII2Unicode(bean.getR_Opt1()), 20) + "_";
                            }
                        }
                    } else {
                        if (CONFIG.getP_CodePrn().equals("Y")) {
                            t1 += "colspan=3 align=left><font face=Angsana New size=3" + subStringText(bean.getR_PName(), 16) + "_";
                            t1 += "colspan=3 align=left><font face=Angsana New size=3>" + bean.getR_Normal() + VatStr + bean.getR_PluCode() + TAB + df.format(bean.getR_Quan()) + TAB2 + DecFmt.format(bean.getR_Total()) + bean.getR_ETD() + "_";
                        } else {
                            t1 += bean.getR_Normal() + VatStr + bean.getR_PName() + TAB + IntFmt.format(bean.getR_Quan()) + TAB2 + DecFmt.format(bean.getR_Total()) + bean.getR_ETD() + "_";
                        }
                        if (bean.getR_PrType().equals("-P")) {
                            if (bean.getR_PrAmt() > 0) {
                                t1 += "colspan=3 align=left><font face=Angsana New size=2>" + "**Pro...  " + bean.getR_PrCode() + "_";
                            }
                        }
                        if (bean.getR_PrType().equals("-I")) {
                            if (bean.getR_PrDisc() != 0) {
                                t1 += "colspan=3 align=left><font face=Angsana New size=2>" + "**Item-Discount " + bean.getR_PrCode() + TAB + DecFmt.format(bean.getR_PrDisc() - bean.getR_Redule()) + "%_";
                            }
                        }
                    }
                }
            }
            totalDiscount = tBean.getProDiscAmt() + tBean.getSpaDiscAmt() + tBean.getCuponDiscAmt()
                    + tBean.getFastDiscAmt() + tBean.getEmpDiscAmt() + tBean.getTrainDiscAmt()
                    + tBean.getSubDiscAmt() + tBean.getDiscBath() + tBean.getItemDiscAmt();

            if (tBean.getProDiscAmt() > 0) {
                t1 += "colspan=3 align=left><font face=Angsana New size=2>" + TAB + "ลด Promotion" + DecFmt.format(tBean.getProDiscAmt()) + "_";
            }
            if (tBean.getSpaDiscAmt() > 0) {
                t1 += "colspan=3 align=left><font face=Angsana New size=3>" + "Special Disc     " + DecFmt.format(tBean.getSpaDiscAmt()) + "_";
            }
            if (tBean.getMemDiscAmt() > 0) {
                t1 += "colspan=3 align=left><font face=Angsana New size=2>" + "ลดสมาชิก.." + tBean.getMemDisc() + TAB + "-" + DecFmt.format(tBean.getMemDiscAmt()) + "_";
            }
            if (tBean.getFastDiscAmt() > 0) {
                t1 += "colspan=3 align=left><font face=Angsana New size=2>" + "ลดเทศกาล.." + tBean.getFastDisc() + DecFmt.format(tBean.getFastDiscAmt()) + "_";
            }
            if (tBean.getEmpDiscAmt() > 0) {
                t1 += "colspan=3 align=left><font face=Angsana New size=2>" + "ลดพนักงาน.." + tBean.getEmpDisc() + DecFmt.format(tBean.getEmpDiscAmt()) + "_";
            }
            if (tBean.getTrainDiscAmt() > 0) {
                t1 += "colspan=3 align=left><font face=Angsana New size=2>" + "ลด Trainnee.." + tBean.getTrainDisc() + DecFmt.format(tBean.getTrainDiscAmt()) + "_";
            }
            if (tBean.getSubDiscAmt() > 0) {
                t1 += "colspan=3 align=left><font face=Angsana New size=2>" + "ลดคูปอง.." + tBean.getSubDisc() + DecFmt.format(tBean.getSubDiscAmt()) + "_";
            }
            if (tBean.getDiscBath() > 0) {
                t1 += "colspan=3 align=left><font face=Angsana New size=2>" + "ลด(บาท).." + DecFmt.format(tBean.getDiscBath()) + "_";
            }
            if (tBean.getItemDiscAmt() > 0) {
                t1 += "colspan=2 align=left><font face=Angsana New size=2>" + "ลดตามรายการ(Item)" + "</td></font><td align=right><font face=Angsana New size=2>" + DecFmt.format(tBean.getItemDiscAmt()) + "_";
            }

            if (tBean.getCuponDiscAmt() > 0) {
                t1 += "align=center colspan=3>_";
                t1 += "align=center colspan=3><font face=Angsana New size=3>" + "-----------------------------------------_";
                t1 += "colspan=3 align=right><font face=Angsana New size=3>" + "ส่วนลดคูปอง.." + Space + DecFmt.format(tBean.getCuponDiscAmt()) + "_";
            }
            t1 += "align=center colspan=3><font face=Angsana New size=3>" + "-----------------------------------------_";
            if (tBean.getTAmount() != tBean.getNetTotal()) {
                if (tBean.getServiceAmt() > 0) {
                    t1 += "align=left colspan=2><font face=Angsana New size=2>" + "Sub-TOTAL : " + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(Math.round(tBean.getTAmount())) + "_";
                    t1 += "colspan=2 align=left><font face=Angsana New size=2>" + Space + "Service :" + DecFmt.format(CONFIG.getP_Service()) + " %" + "</td><td align=right ><font face=Angsana New size=2>" + DecFmt.format(tBean.getServiceAmt()) + " +_";
                }
                t1 += "align=left colspan=2><font face=Angsana New size=2>" + "Sub-TOTAL : " + "</td><td align=right ><font face=Angsana New size=2>" + DecFmt.format(Math.round(tBean.getTAmount())) + "_";
                t1 += "colspan=2 align=left><font face=Angsana New size=2>" + Space + "Net-Amount.." + "</td><td align=right ><font face=Angsana New size=2>" + DecFmt.format(tBean.getTAmount() - totalDiscount + tBean.getServiceAmt()) + "_";
                t1 += "colspan=2 align=left><font face=Angsana New size=2>" + Space + "VAT.." + "</td><td align=right ><font face=Angsana New size=2>" + DecFmt.format(vatPrint) + "_";
                if (!CONFIG.getP_PayBahtRound().equals("O")) {
                    t1 += "colspan=2 align=left><font face=Angsana New size=3>" + "Net-Total...." + "</td><td align=right ><font face=Angsana New size=3>" + DecFmt.format(NumberUtil.UP_DOWN_NATURAL_BAHT(Math.round(tBean.getTAmount() + tBean.getServiceAmt() + vatPrint))) + "_";
                    t1 += "colspan=2 align=left><font face=Angsana New size=2>" + "Round...." + "</td><td align=right ><font face=Angsana New size=2>" + DecFmt.format(Math.round(tBean.getTAmount() + tBean.getServiceAmt() + vatPrint) - (tBean.getTAmount() + tBean.getServiceAmt() + vatPrint)) + "_";
                } else {
                    t1 += "colspan=2 align=left><font face=Angsana New size=3>" + "Net-Total...." + "</td><td align=right ><font face=Angsana New size=3>" + DecFmt.format(Math.round(tBean.getTAmount() + tBean.getServiceAmt() + vatPrint)) + "_";
                    t1 += "colspan=2 align=left><font face=Angsana New size=2>" + Space + "VAT.." + "</td><td align=right ><font face=Angsana New size=2>" + DecFmt.format(vatPrint) + "_";
                }
            } else {
                t1 += "colspan=2 align=left><font face=Angsana New size=3>" + "Net-Total...." + "</td><td align=right ><font face=Angsana New size=3>" + DecFmt.format(NumberUtil.UP_DOWN_NATURAL_BAHT(Math.round(tBean.getTAmount() + tBean.getServiceAmt() + vatPrint))) + "_";
            }
            t1 += "align=center colspan=3><font face=Angsana New size=3>" + "-----------------------------------------_";
            t1 += "align=center colspan=3><font face=Angsana New size=2>" + "COM: " + PublicVar.MACNO + " **No Recipt**" + "_";
            t1 += "colspan=3 align=center>_";

            if (!POSHW.getFootting3().equals("")) {
                t1 += "align=center colspan=3><font face=Angsana New size=2>" + POSHW.getFootting3().trim().replace(" ", Space) + "_";
            }
            if (!POSHW.getFootting2().equals("")) {
                t1 += "align=center colspan=3><font face=Angsana New size=2>" + POSHW.getFootting1().trim().replace(" ", Space) + "_";
            }
            if (!POSHW.getFootting1().equals("")) {
                t1 += "align=center colspan=3><font face=Angsana New size=2>" + POSHW.getFootting2().trim().replace(" ", Space) + "_";
            }
            t += "align=center colspan=3>_";

            //Check Language เปลี่ยนภาษาไทย
            if (PublicVar.languagePrint.equals("TH")) {
                t1 = t1.replace("CC", "ลูกค้า : ").replace("Seat: ", "ที่").replace("NAME", "ชื่อ").replace("Service", "ค่าบริการ").replace("VAT", "ภาษีมูลค่าเพิ่ม").replace("Sub-TOTAL", "มูลค่ารวม").replace("No Receipt", "ไม่ใช่ใบเสร็จรับเงิน");
                t1 = t1.replace("Net-Amount", "รวม").replace("Net-Total", "รวมที่ต้องชำระ").replace("Round", "ปัดเศษ").replace("Terminal", "หมายเลขเครื่อง");
            }
            String t2 = "";
            if (!tBean.getMemCode().equals("")) {
                MemberBean MemBean = MemmaterController.getMember(tBean.getMemCode());
                t2 += "align=left colspan=3><font face=Angsana New size=2>สมาชิก - " + MemBean.getMember_Code() + "_";
                t2 += "align=left colspan=3><font face=Angsana New size=2>" + ThaiUtil.ASCII2Unicode(MemBean.getMember_NameThai()) + "_";
                t2 += "align=left colspan=3><font face=Angsana New size=2>" + ThaiUtil.ASCII2Unicode(MemBean.getMember_Remark1()) + "_";
                t2 += "align=lefts colspan=3><font face=Angsana New size=2>" + ThaiUtil.ASCII2Unicode(MemBean.getMember_Remark2()) + "_";
            }
            //print
            String[] strs = t.split("_");
            String[] strsHead1 = t1.split("_");
            String[] strsMember = t2.split("_");

            for (String data1 : strsHead1) {
                pd.addTextIFont(data1);
            }

            for (String data : strs) {
                pd.addTextIFont(data);
            }
            for (String data : strsMember) {
                pd.addTextIFont(data);
            }
            pd.printHTML();
        }
        updatePrintCheckBill(tableNo);
    }

    public void printCheckBillDriverPDA(String tableNo, String emp) {
        PrintDriver pd = new PrintDriver();
        String t = "";
        t += "colspan=3 align=center><font face=Angsana New size=5>" + "-----------------------------------------_";
        t += "colspan=3 align=left><font face=Angsana New size=5>" + "***โต๊ะ " + tableNo + " สั่งเช็คบิล***_";
        t += "colspan=3 align=left><font face=Angsana New size=5>" + "พนักงาน..." + emp.replace("/", "") + "_";
        t += "colspan=3 align=center><font face=Angsana New size=5>" + "-----------------------------------------_";

        //print
        String[] strs = t.split("_");
        for (String data : strs) {
            pd.addTextIFont(data);
        }

        pd.printHTML();
    }

    public void printCheckBill(String tableNo) {
        AppLogUtil.info("printBillCheck printdriver = " + PublicVar.printdriver);

        if (PublicVar.printdriver == true) {
            printCheckBillDriver(tableNo);
        } else {
            List<BalanceBean> listBeanNoVoid = balanceControl.getAllBalanceNoVoid(tableNo);

            int QtyLength = 5;
            int AmtLength = 10;
            int SubLength = 20;
            int SubLength2 = 13;
            int ItemCnt = 0;
            String VatStr;
            String t1 = "";
            String t2 = "";
            if (!POSHW.getPRNPort().equals("NONE")) {
                if (openPrint(POSHW.getPRNPort())) {
                    TableFileBean tBean = tableFileControl.getData(tableNo);
                    initPrinter();
                    openDrawer();
                    PublicVar.P_LineCount = 0;
                    for (int i = 0; i < listBeanNoVoid.size(); i++) {
                        BalanceBean bean = (BalanceBean) listBeanNoVoid.get(i);
                        if (!bean.getR_Void().equals("V")) {
                            ItemCnt = (int) (ItemCnt + bean.getR_Quan());
                        }
                    }
                    selectStye(14);

                    if (POSHW.getHeading1().length() >= 18) {
                        String[] strs = POSHW.getHeading1().trim().split("_");
                        for (String data : strs) {
                            t1 += data.replace("_", "\n") + "\n";
                        }
                        print(t1);
                    } else {
                        if (!POSHW.getHeading1().equals("") || POSHW.getHeading1() != null) {
                            t1 += POSHW.getHeading1().trim().replace("_", "\n");
                        }
                        print(t1);
                    }
                    if (POSHW.getHeading2().length() >= 18) {
                        String[] strs = POSHW.getHeading2().trim().split("_");
                        for (String data : strs) {
                            t2 += data.replace("_", "\n");
                        }
                        print(t2);
                    } else {
                        if (!POSHW.getHeading2().equals("") || POSHW.getHeading2() != null) {
                            t2 += POSHW.getHeading2().trim().replace("_", "\n") + "\n";
                        }
                        print(t2);
                    }

                    selectStye(1);
                    print(" ");
                    Date dateP = new Date();
                    print(pUtility.DataFullR(PPrint_DatefmtThai.format(dateP).replace("/", " / "), 25) + pUtility.DataFullR(" TABLE : " + tableNo, 15));
                    print("CC : " + pUtility.DataFullR(IntFmt.format(tBean.getTCustomer()), 2) + " Seat" + pUtility.DataFullR(" ", 11) + pUtility.DataFullR("NAME: " + getLastEmployee(tableNo), 15));
                    if (!tBean.getMemName().equals("")) {
                        print(" ");
                        print(pUtility.DataFullR(" ", 26) + pUtility.DataFullR("NAME CC: " + tBean.getMemName(), 15));
                    }
                    print(" ");
                    print("----------------------------------------");

                    for (int i = 0; i < listBeanNoVoid.size(); i++) {
                        BalanceBean bean = (BalanceBean) listBeanNoVoid.get(i);
                        if (bean.getR_Void().equals("V")) {
                            selectStye(12);
                            print("  VOID by :" + bean.getR_VoidUser());

                            if (bean.getR_Vat().equals("V")) {
                                VatStr = "-";
                            } else {
                                VatStr = "*";
                            }
                            if (CONFIG.getP_CodePrn().equals("Y")) {
                                print(bean.getR_PName());
                                print(bean.getR_Normal() + VatStr + pUtility.DataFullR(bean.getR_PluCode(), 20) + "  " + pUtility.DataFull(IntFmt.format(-1 * bean.getR_Quan()), QtyLength) + pUtility.DataFull(DecFmt.format(-1 * bean.getR_Total()), AmtLength) + bean.getR_ETD());
                                if (!bean.getR_Opt1().equals("")) {
                                    print(ThaiUtil.ASCII2Unicode(bean.getR_Opt1()));
                                }
                            } else {
                                String R_PName = bean.getR_PName();
                                String space = "  ";
                                int sizeNew = 20;
                                if (bean.getR_PName().length() > 20) {
                                    sizeNew = 21;
                                    space = " ";
                                    R_PName = R_PName.substring(0, 21);
                                }
                                print(bean.getR_Normal() + VatStr + pUtility.DataFullR(R_PName, sizeNew) + space + pUtility.DataFull(IntFmt.format(-1 * bean.getR_Quan()), QtyLength) + pUtility.DataFull(DecFmt.format(-1 * bean.getR_Total()), AmtLength) + bean.getR_ETD());
                                if (!bean.getR_Opt1().equals("")) {
                                    print(ThaiUtil.ASCII2Unicode(bean.getR_Opt1()));
                                }
                            }
                            selectStye(13);
                        } else {
                            if (bean.getR_Vat().equals("V")) {
                                VatStr = "-";
                            } else {
                                VatStr = "*";
                            }
                            if (bean.getR_PrAmt() == 0) {
                                if (CONFIG.getP_CodePrn().equals("Y")) {
                                    print(bean.getR_PName());
                                    print(bean.getR_Normal() + VatStr + pUtility.DataFullR(bean.getR_PluCode(), 20) + "  " + pUtility.DataFull(IntFmt.format(bean.getR_Quan()), QtyLength) + pUtility.DataFull(DecFmt.format(bean.getR_Total()), AmtLength) + bean.getR_ETD());
                                    if (!bean.getR_Opt1().equals("")) {
                                        print(ThaiUtil.ASCII2Unicode(bean.getR_Opt1()));
                                    }
                                } else {
                                    String R_PName = bean.getR_PName();
                                    String space = "  ";
                                    int sizeNew = 20;
                                    if (bean.getR_PName().length() > 20) {
                                        sizeNew = 21;
                                        space = " ";
                                        R_PName = R_PName.substring(0, 21);
                                    }
                                    print(bean.getR_Normal() + VatStr + pUtility.DataFullR(R_PName, sizeNew) + space + pUtility.DataFull(IntFmt.format(bean.getR_Quan()), QtyLength) + pUtility.DataFull(DecFmt.format(bean.getR_Total()), AmtLength) + bean.getR_ETD());
                                    if (!bean.getR_Opt1().equals("")) {
                                        print(ThaiUtil.ASCII2Unicode(bean.getR_Opt1()));
                                    }
                                }
                            } else {
                                if (CONFIG.getP_CodePrn().equals("Y")) {
                                    print(bean.getR_PName());
                                    print(bean.getR_Normal() + VatStr + pUtility.DataFullR(bean.getR_PluCode(), 20) + "  " + pUtility.DataFull(IntFmt.format(bean.getR_Quan()), QtyLength) + pUtility.DataFull(DecFmt.format(bean.getR_Total()), AmtLength) + bean.getR_ETD());
                                    if (!bean.getR_Opt1().equals("")) {
                                        print(ThaiUtil.ASCII2Unicode(bean.getR_Opt1()));
                                    }
                                } else {
                                    String R_PName = bean.getR_PName();
                                    String space = "  ";
                                    int sizeNew = 20;
                                    if (bean.getR_PName().length() > 20) {
                                        sizeNew = 21;
                                        space = " ";
                                        R_PName = R_PName.substring(0, 21);
                                    }
                                    print(bean.getR_Normal() + VatStr + pUtility.DataFullR(R_PName, sizeNew) + space + pUtility.DataFull(IntFmt.format(bean.getR_Quan()), QtyLength) + pUtility.DataFull(DecFmt.format(bean.getR_Total()), AmtLength) + bean.getR_ETD());
                                    if (!bean.getR_Opt1().equals("")) {
                                        print(bean.getR_Opt1());
                                    }
                                }
                                if (bean.getR_PrType().equals("-P")) {
                                    if (bean.getR_PrAmt() > 0) {
                                        print("   **Promotion  " + bean.getR_PrCode() + " " + pUtility.DataFull(pUtility.SeekPromotionName(bean.getR_PrCode()), 20));
                                    }
                                    if (!bean.getR_Opt1().equals("")) {
                                        print(ThaiUtil.ASCII2Unicode(bean.getR_Opt1()));
                                    }
                                }
                                if (bean.getR_PrType().equals("-I")) {
                                    if (bean.getR_PrDisc() != 0) {
                                        print("   **Item-Discount " + bean.getR_PrCode() + " " + pUtility.DataFull(DecFmt.format(bean.getR_PrDisc() - bean.getR_Redule()), QtyLength) + "%");
                                        print(ThaiUtil.ASCII2Unicode(bean.getR_Opt1()));
                                    }
                                }
                            }
                        }
                    }

                    print("----------------------------------------");
                    if (tBean.getProDiscAmt() > 0) {
                        print("    " + pUtility.DataFullR("ลด Promotion     ", SubLength) + pUtility.DataFull(DecFmt.format(tBean.getProDiscAmt()), AmtLength));
                    }
                    if (tBean.getSpaDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("Special Disc     ", SubLength) + pUtility.DataFull(DecFmt.format(tBean.getSpaDiscAmt()), AmtLength));
                    }
                    if (tBean.getMemDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("ลดสมาชิก..", SubLength2) + pUtility.DataFull(tBean.getMemDisc(), 8) + pUtility.DataFull(DecFmt.format(tBean.getMemDiscAmt()), AmtLength));
                    }
                    if (tBean.getFastDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("ลดเทศกาล..", SubLength2) + pUtility.DataFull(tBean.getFastDisc(), 8) + pUtility.DataFull(DecFmt.format(tBean.getFastDiscAmt()), AmtLength));
                    }
                    if (tBean.getEmpDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("ลดพนักงาน..", SubLength2) + pUtility.DataFull(tBean.getEmpDisc(), 8) + pUtility.DataFull(DecFmt.format(tBean.getEmpDiscAmt()), AmtLength));
                    }
                    if (tBean.getTrainDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("ลด Trainnee..", SubLength2) + pUtility.DataFull(tBean.getTrainDisc(), 8) + pUtility.DataFull(DecFmt.format(tBean.getTrainDiscAmt()), AmtLength));
                    }
                    if (tBean.getSubDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("ลดคูปอง..", SubLength2) + pUtility.DataFull(tBean.getSubDisc(), 8) + pUtility.DataFull(DecFmt.format(tBean.getSubDiscAmt()), AmtLength));
                    }
                    if (tBean.getDiscBath() > 0) {
                        print("     " + pUtility.DataFullR("ลด(บาท)..", SubLength) + pUtility.DataFull(DecFmt.format(tBean.getDiscBath()), AmtLength));
                    }
                    if (tBean.getItemDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("ลดตามรายการ(Item)", SubLength) + pUtility.DataFull(DecFmt.format(tBean.getItemDiscAmt()), AmtLength));
                    }
                    if (tBean.getServiceAmt() > 0) {
                        print("     " + pUtility.DataFullR("ค่าบริการ (Service)", 23) + pUtility.DataFull(DecFmt.format(tBean.getServiceAmt()), 9));
                    }
                    if (tBean.getCuponDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("ส่วนลดคูปอง(Cupon)", SubLength) + pUtility.DataFull(DecFmt.format(tBean.getCuponDiscAmt()), AmtLength));
                    }
                    print(pUtility.DataFull(" ", 25) + "TOTAL " + DecFmt.format(tBean.getNetTotal()));
                    print("----------------------------------------");
                    print(" ");
                    print(pUtility.DataFullR("COM: " + PublicVar.MACNO, 15) + " **No Recipt**");
                    //print("----------------------------------------");
                    print(" ");
                    selectStye(1);
                    selectStye(14);
                    if (!POSHW.getFootting1().equals("")) {
                        print(POSHW.getFootting3());
                        print(" ");
                    }
                    if (!POSHW.getFootting2().equals("")) {
                        print(POSHW.getFootting1());
                        print(" ");
                    }
                    if (!POSHW.getFootting3().equals("")) {
                        print(POSHW.getFootting2());
                        print(" ");
                    }
                    print(" ");
                    selectStye(1);
                    print("");
                    print(" ");
                    print(" ");
                    print(" ");
                    print(" ");
                    print(" ");
                    print(" ");
                    print(" ");
                    print(" ");

                    cutPaper();
                    updatePrintCheckBill(pUtility.DataFullR(tableNo, 5));
                    closePrint();
                }
            }
        }

    }

    public void printVoidBill(final String tableNo) {
        ExecutorService service = Executors.newFixedThreadPool(1);
        service.submit(new Runnable() {
            @Override
            public void run() {
                AppLogUtil.info("PrintVoidBill : Start Printing....");
                if (PublicVar.printdriver == true) {
                    printVoidBillDriver(tableNo);
                } else {
                    List<BalanceBean> listBean = balanceControl.getAllBalance(tableNo);

                    int QtyLength = 5;
                    int AmtLength = 10;
                    int SubLength = 20;
                    int SubLength2 = 13;
                    String VatStr;
                    if (!POSHW.getPRNPort().equals("NONE")) {
                        if (openPrint(POSHW.getPRNPort())) {
                            initPrinter();
                            PublicVar.P_LineCount = 0;
                            for (int i = 0; i < listBean.size(); i++) {
                                BalanceBean bean = (BalanceBean) listBean.get(i);
                                if (!bean.getR_Void().equals("V")) {
                                    break;
                                }
                            }
                            print(POSHW.getHeading1());
                            print(POSHW.getHeading2());

                            print(" *** ใบยกเลิกรายการอาหาร *** ");

                            print(" ");
                            Date dateP = new Date();
                            print(PPrint_DatefmtThai.format(dateP).replace("/", " / ") + pUtility.DataFullR(" ", 11) + "NAME:" + getLastEmployee(tableNo));
                            print("COM: " + PublicVar.MACNO);
                            print(" ");
                            print("----------------------------------------");
                            for (int i = 0; i < listBean.size(); i++) {
                                BalanceBean bean = (BalanceBean) listBean.get(i);
                                if (bean.getR_Void().equals("V")) {
                                    if (bean.getR_Vat().equals("V")) {
                                        VatStr = "-";
                                    } else {
                                        VatStr = "*";
                                    }
                                    if (CONFIG.getP_CodePrn().equals("Y")) {
                                        print(bean.getR_PName());
                                        print(bean.getR_Normal() + VatStr + pUtility.DataFullR(bean.getR_PluCode(), 20) + "  " + pUtility.DataFull(IntFmt.format(-1 * bean.getR_Quan()), QtyLength) + pUtility.DataFull(DecFmt.format(-1 * bean.getR_Total()), AmtLength) + bean.getR_ETD());
                                    } else {
                                        print(bean.getR_Normal() + VatStr + pUtility.DataFullR(bean.getR_PName(), 20) + "  " + pUtility.DataFull(IntFmt.format(-1 * bean.getR_Quan()), QtyLength) + pUtility.DataFull(DecFmt.format(-1 * bean.getR_Total()), AmtLength) + bean.getR_ETD());
                                    }
                                    String sqlNameVoid = "select name from posuser where username='" + bean.getR_VoidUser() + "' limit 1;";
                                    String NameVoid = "";

                                    try {
                                        mysqlConnect.open(this.getClass());
                                        ResultSet rsNameVoid = mysqlConnect.executeQuery(sqlNameVoid);
                                        if (rsNameVoid.next()) {
                                            NameVoid = (rsNameVoid.getString("name"));
                                        }
                                        rsNameVoid.close();
                                    } catch (SQLException e) {
                                        AppLogUtil.log(PPrint.class, "error", e);
                                    } finally {
                                        mysqlConnect.closeConnection(this.getClass());
                                    }
                                    selectStye(12);
                                    print("  VOID...Item by : " + ThaiUtil.ASCII2Unicode(NameVoid));
                                    if (!bean.getR_Opt1().equals("")) {
                                        print(bean.getR_Opt1());
                                    }
                                    print("    #" + bean.getR_Opt9());
                                    print("");
                                }
                            }

                            TableFileBean tBean = tableFileControl.getData(tableNo);

                            if (tBean.getProDiscAmt() > 0) {
                                print("    " + pUtility.DataFullR("ลด Promotion     ", SubLength) + pUtility.DataFull(DecFmt.format(tBean.getProDiscAmt()), AmtLength));
                            }
                            if (tBean.getSpaDiscAmt() > 0) {
                                print("     " + pUtility.DataFullR("Special Disc     ", SubLength) + pUtility.DataFull(DecFmt.format(tBean.getSpaDiscAmt()), AmtLength));
                            }
                            if (tBean.getMemDiscAmt() > 0) {
                                print("     " + pUtility.DataFullR("ลดสมาชิก..........", SubLength2) + pUtility.DataFull(tBean.getMemDisc(), 8) + pUtility.DataFull(DecFmt.format(tBean.getMemDiscAmt()), AmtLength));
                            }
                            if (tBean.getFastDiscAmt() > 0) {
                                print("     " + pUtility.DataFullR("ลดเทศกาล.........", SubLength2) + pUtility.DataFull(tBean.getFastDisc(), 8) + pUtility.DataFull(DecFmt.format(tBean.getFastDiscAmt()), AmtLength));
                            }
                            if (tBean.getEmpDiscAmt() > 0) {
                                print("     " + pUtility.DataFullR("ลดพนักงาน.........", SubLength2) + pUtility.DataFull(tBean.getEmpDisc(), 8) + pUtility.DataFull(DecFmt.format(tBean.getEmpDiscAmt()), AmtLength));
                            }
                            if (tBean.getTrainDiscAmt() > 0) {
                                print("     " + pUtility.DataFullR("ลด Trainnee......", SubLength2) + pUtility.DataFull(tBean.getTrainDisc(), 8) + pUtility.DataFull(DecFmt.format(tBean.getTrainDiscAmt()), AmtLength));
                            }
                            if (tBean.getSubDiscAmt() > 0) {
                                print("     " + pUtility.DataFullR("ลดคูปอง...........", SubLength2) + pUtility.DataFull(tBean.getSubDisc(), 8) + pUtility.DataFull(DecFmt.format(tBean.getSubDiscAmt()), AmtLength));
                            }
                            if (tBean.getDiscBath() > 0) {
                                print("     " + pUtility.DataFullR("ลด(บาท)..........", SubLength) + pUtility.DataFull(DecFmt.format(tBean.getDiscBath()), AmtLength));
                            }
                            if (tBean.getItemDiscAmt() > 0) {
                                print("     " + pUtility.DataFullR("ลดตามรายการ(Item)", SubLength) + pUtility.DataFull(DecFmt.format(tBean.getItemDiscAmt()), AmtLength));
                            }
                            if (tBean.getServiceAmt() > 0) {
                                print("     " + pUtility.DataFullR("ค่าบริการ (Service)     ", 23) + pUtility.DataFull(DecFmt.format(tBean.getServiceAmt()), 9));
                            }

                            print("----------------------------------------");
                            selectStye(1);
                            print("TABLE  " + pUtility.DataFullR(tableNo, 5) + "   " + "จำนวนลูกค้า : " + IntFmt.format(tBean.getTCustomer()) + " คน");

                            print(" ");
                            print("");
                            print(" ");
                            print(" ");
                            print(" ");
                            print(" ");
                            print(" ");
                            print(" ");
                            print(" ");
                            print(" ");

                            cutPaper();
                            updatePrintCheckBill(pUtility.DataFullR(tableNo, 5));
                            closePrint();
                        }
                    }
                }

            }
        });

    }

    public void printVoidBillDriver(String tableNo) {
        List<BalanceBean> listBean = balanceControl.getAllBalance(tableNo);
        PrintDriver pd = new PrintDriver();
        String t = "";
        int QtyLength = 5;
        int AmtLength = 10;
        int SubLength = 20;
        int SubLength2 = 13;
        if (!POSHW.getPRNPort().equals("NONE")) {
            PublicVar.P_LineCount = 0;
            for (int i = 0; i < listBean.size(); i++) {
                BalanceBean bean = (BalanceBean) listBean.get(i);
                if (!bean.getR_Void().equals("V")) {
                    break;
                }
            }
            if (POSHW.getHeading1().length() >= 18) {
                String[] strs = POSHW.getHeading1().trim().replace(" ", Space).split("_");
                for (String data : strs) {
                    t += "colspan=3 align=center><font face=Angsana New size=2>" + data + "_";
                }
            } else {
                t += "colspan=3 align=center><font face=Angsana New size=2>" + POSHW.getHeading1().trim().replace(" ", "&nbsp; ") + "_";
            }
            if (POSHW.getHeading2().length() >= 18) {
                String[] strs = POSHW.getHeading2().trim().replace(" ", Space).split("_");
                for (String data : strs) {
                    t += "colspan=3 align=center><font face=Angsana New size=2>" + data + "_";
                }
            } else {
                t += "colspan=3 align=center><font face=Angsana New size=2>" + POSHW.getHeading2().trim().replace(" ", "&nbsp; ") + "_";
            }
            t += "colspan=3 align=center>_";
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + " *** ใบยกเลิกรายการอาหาร *** " + "_");

            t += "colspan=3 align=center>_";
            Date dateP = new Date();
            t += ("colspan=2 align=left><font face=Angsana New size=2>" + PPrint_DatefmtThai.format(dateP).replace("/", " / ") + "</td><td align=right><font face=Angsana New size=2>" + "NAME:" + Space + getLastEmployee(tableNo) + "_");
            t += ("colspan=3 align=left><font face=Angsana New size=2>" + "COM: " + PublicVar.MACNO + "_");
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + "------------------------------------------------------------" + "_");
            for (int i = 0; i < listBean.size(); i++) {
                BalanceBean bean = (BalanceBean) listBean.get(i);
                if (bean.getR_Void().equals("V")) {
                    if (CONFIG.getP_CodePrn().equals("Y")) {
                        t += ("colspan=3 align=left><font face=Angsana New size=2>" + bean.getR_PName() + "_");
                        t += ("align=left><font face=Angsana New size=2>" + pUtility.DataFullR(bean.getR_PluCode(), 20) + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(IntFmt.format(-1 * bean.getR_Quan()), QtyLength) + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(DecFmt.format(-1 * bean.getR_Total()), AmtLength) + "_");
                    } else {
                        t += ("align=left><font face=Angsana New size=2>" + pUtility.DataFullR(bean.getR_PName(), 20) + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(IntFmt.format(-1 * bean.getR_Quan()), QtyLength) + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(DecFmt.format(-1 * bean.getR_Total()), AmtLength) + "_");
                    }
                    String sqlNameVoid = "select name from posuser where username='" + bean.getR_VoidUser() + "' limit 1;";
                    String NameVoid = "";

                    try {
                        mysqlConnect.open(this.getClass());
                        ResultSet rsNameVoid = mysqlConnect.executeQuery(sqlNameVoid);
                        if (rsNameVoid.next()) {
                            NameVoid = (rsNameVoid.getString("name"));
                        }
                        rsNameVoid.close();
                    } catch (SQLException e) {
                        AppLogUtil.log(PPrint.class, "error", e);
                    } finally {
                        mysqlConnect.closeConnection(this.getClass());
                    }

                    selectStye(12);
                    t += (Space + "VOID...Item by : " + ThaiUtil.ASCII2Unicode(NameVoid));
                    if (!bean.getR_Opt1().equals("")) {
                        t += ("colspan=3 align=left><font face=Angsana New size=2>" + bean.getR_Opt1() + "_");
                    }
                    t += ("colspan=3 align=left><font face=Angsana New size=2>" + TAB + "#" + bean.getR_Opt9() + "_");
                }
            }

            TableFileBean tBean = tableFileControl.getData(tableNo);
            if (tBean.getProDiscAmt() > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2>" + Space + pUtility.DataFullR("ลด Promotion     ", SubLength) + pUtility.DataFull(DecFmt.format(tBean.getProDiscAmt()), AmtLength) + "_");
            }
            if (tBean.getSpaDiscAmt() > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2>" + Space + pUtility.DataFullR("Special Disc     ", SubLength) + pUtility.DataFull(DecFmt.format(tBean.getSpaDiscAmt()), AmtLength) + "_");
            }
            if (tBean.getMemDiscAmt() > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2>" + Space + pUtility.DataFullR("ลดสมาชิก..........", SubLength2) + pUtility.DataFull(tBean.getMemDisc(), 8) + "-" + pUtility.DataFull(DecFmt.format(tBean.getMemDiscAmt()), AmtLength) + "_");
            }
            if (tBean.getFastDiscAmt() > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2>" + Space + pUtility.DataFullR("ลดเทศกาล.........", SubLength2) + pUtility.DataFull(tBean.getFastDisc(), 8) + pUtility.DataFull(DecFmt.format(tBean.getFastDiscAmt()), AmtLength) + "_");
            }
            if (tBean.getEmpDiscAmt() > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2>" + Space + pUtility.DataFullR("ลดพนักงาน.........", SubLength2) + pUtility.DataFull(tBean.getEmpDisc(), 8) + pUtility.DataFull(DecFmt.format(tBean.getEmpDiscAmt()), AmtLength) + "_");
            }
            if (tBean.getTrainDiscAmt() > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2>" + Space + pUtility.DataFullR("ลด Trainnee......", SubLength2) + pUtility.DataFull(tBean.getTrainDisc(), 8) + pUtility.DataFull(DecFmt.format(tBean.getTrainDiscAmt()), AmtLength) + "_");
            }
            if (tBean.getSubDiscAmt() > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2>" + Space + pUtility.DataFullR("ลดคูปอง...........", SubLength2) + pUtility.DataFull(tBean.getSubDisc(), 8) + pUtility.DataFull(DecFmt.format(tBean.getSubDiscAmt()), AmtLength) + "_");
            }
            if (tBean.getDiscBath() > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2>" + Space + pUtility.DataFullR("ลด(บาท)..........", SubLength) + pUtility.DataFull(DecFmt.format(tBean.getDiscBath()), AmtLength) + "_");
            }
            if (tBean.getItemDiscAmt() > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2>" + Space + pUtility.DataFullR("ลดตามรายการ(Item)", SubLength) + pUtility.DataFull(DecFmt.format(tBean.getItemDiscAmt()), AmtLength) + "_");
            }
            if (tBean.getServiceAmt() > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2>" + Space + pUtility.DataFullR("ค่าบริการ (Service)     ", 23) + pUtility.DataFull(DecFmt.format(tBean.getServiceAmt()), 9) + "_");
            }

            t += ("colspan=3 align=center><font face=Angsana New size=2>" + "------------------------------------------------------------" + "_");
            t += ("colspan=3 align=left><font face=Angsana New size=2>" + "TABLE  " + TAB + pUtility.DataFullR(tableNo, 5) + Space + "จำนวนลูกค้า : " + IntFmt.format(tBean.getTCustomer()) + " คน" + "_");
            t += (">_");
            t = changeLanguage(t);
            updatePrintCheckBill(pUtility.DataFullR(tableNo, 5));
            String[] strs = t.split("_");

            for (String data : strs) {
                pd.addTextIFont(data);
            }
            pd.printHTML();
        }

    }

    public String getChargeType(int ChargeType) {
        String TempStr = "";
        if (ChargeType == 1) {
            TempStr = "1) เลี้ยงรับรอง ";
        }
        if (ChargeType == 2) {
            TempStr = "2) สินค้าตัวอย่าง ";
        }
        if (ChargeType == 3) {
            TempStr = "3) กิจกรรมเพื่อสังคม ";
        }
        if (ChargeType == 4) {
            TempStr = "4) อบรมภายใน ";
        }
        if (ChargeType == 5) {
            TempStr = "5) อาหารพนักงาน ";
        }
        
        return TempStr;
    }

    public static int searchArray(int key, int[] list) {
        int ans = -1;
        for (int i = 0; i < list.length; i++) {
            if (key == list[i]) {
                ans = i;
            }
        }
        return ans;
    }

    public void cutPaper() {
        try {
            byte Str[] = {27, 105, 0}; //init Printer
            outputStream.write(Str);
        } catch (IOException ex) {
            AppLogUtil.log(PPrint.class, "error", ex);
        }
    }

    public void printTableAction() {
        if (PublicVar.printdriver == true) {
            printTableActionDriver();
        } else if (!POSHW.getPRNPort().equals("NONE")) {
            if (openPrint(POSHW.getPRNPort())) {
                initPrinter();
                print(POSHW.getHeading1());
                print(POSHW.getHeading2());
                print(POSHW.getHeading3());
                print(POSHW.getHeading4());
                print("");

                print("REG ID :" + POSHW.getMacNo());
                print("      รายงานโต๊ะค้าง (ยังไม่ได้ชำระเงิน) ");
                print("               Table Check        ");
                Date dateP = new Date();
                print(PPrint_DatefmtThai.format(dateP).replace("/", " / ") + " " + "Cashier:" + PublicVar._User + " Mac:" + PublicVar.MACNO);

                print("----------------------------------------");
                print("Table     Amount    Open-Time  Customer");
                print("----------------------------------------");
                Double SumTotal = 0.0;

                mysqlConnect.open(this.getClass());
                try {
                    Statement stmt = mysqlConnect.getConnection().createStatement();
                    String ChkTable = "select r_table,sum(r_total),MIN(r_void) r_void,MIN(TCurTime) TCurTime,MAX(tcustomer) tcustomer from balance"
                            + " left join tablefile on balance.r_table=tablefile.tcode "
                            + "where (r_void<>'V') or (r_void is null) "
                            + "group by r_table";
                    ResultSet rs = stmt.executeQuery(ChkTable);
                    while (rs.next()) {
                        print(pUtility.DataFull(rs.getString("r_table"), 6)
                                + pUtility.DataFull(DecFmt.format(rs.getDouble("sum(r_total)")), 10) + "     "
                                + pUtility.DataFull(rs.getString("TCurTime"), 8) + "  "
                                + pUtility.DataFull(IntFmt.format(rs.getInt("tcustomer")), 5));
                        SumTotal = SumTotal + rs.getDouble("sum(r_total)");
                    }
                    rs.close();
                    stmt.close();
                } catch (SQLException e) {
                    AppLogUtil.log(PPrint.class, "error", e);
                } finally {
                    mysqlConnect.closeConnection(this.getClass());
                }

                print("-----------------------------------------");
                print("Total " + pUtility.DataFull(DecFmt.format(SumTotal), 10));
                print("-----------------------------------------");
                print("");
                print("");
                print("");
                print("");
                print("");
                print("");
                print("");
                print("");
                print("");
                print("");
                print("");
                print("");
                cutPaper();
                closePrint();
            }
        }
    }

    public void printTerminalEngForm(FinalcialRec frec, CreditRec[] CrArray, String macNo) {
        Date dateP = new Date();
        if (PublicVar.printdriver == true) {
            printTerminalEngFormDriver(frec, CrArray, macNo);
        } else {
            if (!POSHW.getPRNPort().equals("NONE")) {
                List<Object[]> list1 = docAnalyse(Datefmt.format(dateP) + "", Datefmt.format(dateP) + "");
                double totalE = 0.00, totalT = 0.00, totalD = 0.00, nettotalE = 0.00, nettotalT = 0.00, nettotalD = 0.00;
                double countCCE = 0.00, countCCT = 0.00, countCCD = 0.00, countBillE = 0.00, countBillT = 0.00, countBillD = 0.00;
                double AVG_DockE = 0.00;
                double AVG_DockT = 0.00;
                double AVG_DockD = 0.00;
                double AVG_CCE = 0.00;
                double AVG_CCT = 0.00;
                double AVG_CCD = 0.00;

                if (list1 != null && list1.size() > 0) {
                    countCCE = Double.parseDouble(list1.get(0)[2].toString());
                    totalE = Double.parseDouble(list1.get(0)[4].toString());
                    nettotalE = Double.parseDouble(list1.get(0)[5].toString());

                    countCCT = Double.parseDouble(list1.get(1)[2].toString());
                    nettotalT = Double.parseDouble(list1.get(1)[4].toString());
                    totalT = Double.parseDouble(list1.get(1)[5].toString());

                    countCCD = Double.parseDouble(list1.get(2)[2].toString());
                    nettotalD = Double.parseDouble(list1.get(2)[4].toString());
                    totalD = Double.parseDouble(list1.get(2)[5].toString());

                    countBillE = Double.parseDouble(list1.get(0)[0].toString());
                    countBillT = Double.parseDouble(list1.get(1)[0].toString());
                    countBillD = Double.parseDouble(list1.get(2)[0].toString());

                    AVG_DockE = nettotalE / countBillE;
                    AVG_DockT = nettotalT / countBillT;
                    AVG_DockD = nettotalD / countBillD;
                    AVG_CCE = nettotalE / countCCE;
                    AVG_CCT = nettotalT / countCCT;
                    AVG_CCD = nettotalD / countCCD;

                    if (nettotalE == 0.00 && countBillE == 0.00) {
                        AVG_DockE = 0.00;
                    }
                    if (nettotalT == 0.00 && countBillT == 0.00) {
                        AVG_DockT = 0.00;
                    }
                    if (nettotalD == 0.00 && countBillD == 0.00) {
                        AVG_DockD = 0.00;
                    }
                    if (nettotalE == 0.00 && countCCE == 0) {
                        AVG_CCE = 0.00;
                    }
                    if (nettotalT == 0.00 & countCCT == 0) {
                        AVG_CCT = 0.00;
                    }
                    if (nettotalD == 0.00 & countCCD == 0) {
                        AVG_CCD = 0.00;
                    }
                } else {

                }
                if (openPrint(POSHW.getPRNPort())) {
                    initPrinter();
                    print("   Daily Sale (Terminal Report)");

                    print("Printed On" + PPrint_DatefmtThai.format(dateP).replace("/", " / "));
                    print("Cashier:" + PublicVar._User + " Mac:" + macNo);
                    print("");
                    print(POSHW.getHeading1());
                    print(POSHW.getHeading2());
                    print(POSHW.getHeading3());
                    print(POSHW.getHeading4());
                    print("");

                    print("COM: " + macNo);
                    print("");

                    double NetSale_VatExclude = frec.Net_Sale * CONFIG.getP_Vat() / (100 + CONFIG.getP_Vat());

                    print("----------------------------------------");
                    print(pUtility.DataFullR("FOOD                   ", 20) + pUtility.DataFull(DecFmt.format(frec.Food), 19));
                    print(pUtility.DataFullR("BEVERAGE               ", 20) + pUtility.DataFull(DecFmt.format(frec.Drink), 19));
                    if (frec.Product > 0) {
                        print(pUtility.DataFullR("PRODUCT                ", 20) + pUtility.DataFull(DecFmt.format(frec.Product), 19));
                    }
                    print(pUtility.DataFullR("TOTAL-SALES            ", 20) + pUtility.DataFull(DecFmt.format(frec.Dept_Sum), 19));
                    print("========================================");
                    if (frec.Charge > 0) {
                        print(pUtility.DataFullR("Charge   Credit        ", 20) + pUtility.DataFull(IntFmt.format(frec.ChargeCnt), 6) + pUtility.DataFull(DecFmt.format(frec.Charge), 13));
                    }
                    if (frec.Vip_Disc > 0) {
                        print(pUtility.DataFullR("Discount Member           ", 20) + pUtility.DataFull(IntFmt.format(frec.Vip_DiscCnt), 6) + pUtility.DataFull(DecFmt.format(frec.Vip_Disc), 13));
                    }
                    if (frec.Fast_Disc > 0) {
                        print(pUtility.DataFullR("Discount Festival      ", 20) + pUtility.DataFull(IntFmt.format(frec.Fast_DiscCnt), 6) + pUtility.DataFull(DecFmt.format(frec.Fast_Disc), 13));
                    }
                    if (frec.Emp_Disc > 0) {
                        print(pUtility.DataFullR("Discount Employ        ", 20) + pUtility.DataFull(IntFmt.format(frec.Emp_DiscCnt), 6) + pUtility.DataFull(DecFmt.format(frec.Emp_Disc), 13));
                    }
                    if (frec.Train_Disc > 0) {
                        print(pUtility.DataFullR("Discount Staff Discound", 20) + pUtility.DataFull(IntFmt.format(frec.Train_DiscCnt), 6) + pUtility.DataFull(DecFmt.format(frec.Train_Disc), 13));
                    }
                    if (frec.Sub_Disc > 0) {
                        print(pUtility.DataFullR("Discount Cupon         ", 20) + pUtility.DataFull(IntFmt.format(frec.Sub_DiscCnt), 6) + pUtility.DataFull(DecFmt.format(frec.Sub_Disc), 13));
                    }
                    if (frec.Gen_Refund > 0) {
                        print(pUtility.DataFullR("Discount Bath.         ", 20) + pUtility.DataFull(IntFmt.format(frec.Gen_RefundCnt), 6) + pUtility.DataFull(DecFmt.format(frec.Gen_Refund), 13));
                    }
                    if (frec.Promotion > 0) {
                        print(pUtility.DataFullR("Discount Promotion     ", 20) + pUtility.DataFull(IntFmt.format(frec.PromotionCnt), 6) + pUtility.DataFull(DecFmt.format(frec.Promotion), 13));
                    }
                    if (frec.Spacial > 0) {
                        print(pUtility.DataFullR("Discount Special       ", 20) + pUtility.DataFull(IntFmt.format(frec.SpacialCnt), 6) + pUtility.DataFull(DecFmt.format(frec.Spacial), 13));
                    }
                    if (frec.Item_Disc > 0) {
                        print(pUtility.DataFullR("Discount Item          ", 20) + pUtility.DataFull(IntFmt.format(frec.Item_DiscCnt), 6) + pUtility.DataFull(DecFmt.format(frec.Item_Disc), 13));
                    }
                    if (frec.Cupon_Disc > 0) {
                        mysqlConnect.open(this.getClass());
                        try {
                            String sql = "select sum(cuamt) amt,sum(cuquan) quan,"
                                    + " t_cupon.cucode code,cupon.cuname name "
                                    + "from t_cupon "
                                    + "inner join cupon "
                                    + "on t_cupon.cucode = cupon.cucode "
                                    + "where t_cupon.cuquan<>'0' "
                                    + "and t_cupon.refund<>'V' "
                                    + "group by t_cupon.cucode, cupon.cuname";
                            ResultSet rs = mysqlConnect.executeQuery(sql);
                            while (rs.next()) {
                                double amt = rs.getDouble("amt");
                                String quan = rs.getString("quan");
                                String name = rs.getString("name");
                                print(pUtility.DataFullR(name, 20) + pUtility.DataFull(quan, 6) + pUtility.DataFull(DecFmt.format(amt), 13));
                            }
                            rs.close();
                        } catch (SQLException e) {
                            AppLogUtil.log(PPrint.class, "error", e);
                        } finally {
                            mysqlConnect.closeConnection(this.getClass());
                        }

                    }

                    print("----------------------------------------");
                    if (CONFIG.getP_VatType().equals("I")) {
                        print(pUtility.DataFullR("Gross-Sales              ", 20) + pUtility.DataFull(DecFmt.format(frec.Net_Sale), 19));
                    }
                    if (CONFIG.getP_VatType().equals("E")) {
                        print(pUtility.DataFullR("Gross-Sales              ", 20) + pUtility.DataFull(DecFmt.format(frec.Net_Sale - frec.Service), 19));
                    }

                    print("========================================");
                    if (frec.Gift > 0) {
                        print(pUtility.DataFullR("Gift Voucher           ", 20) + pUtility.DataFull(IntFmt.format(frec.GiftCnt), 6) + pUtility.DataFull(DecFmt.format(frec.Gift), 13));
                    }
                    if (frec.Entertain > 0) {
                        print(pUtility.DataFullR("Entertain                       ", 20) + pUtility.DataFull(IntFmt.format(frec.BillEntertain), 6) + pUtility.DataFull(DecFmt.format(frec.Entertain), 13));
                    }
                    print(pUtility.DataFullR("CASH                   ", 20) + pUtility.DataFull(IntFmt.format(frec.CashCnt), 6) + pUtility.DataFull(DecFmt.format(frec.Cash), 13));
                    String[] credit = credit(macNo);
                    if (!credit.equals("")) {
                        String cd = credit[0];
                        String am = credit[1];
                        print(pUtility.DataFullR("CRADIT                 ", 20) + pUtility.DataFull(cd, 6) + pUtility.DataFull(am, 13));
                        List<String[]> list = creName(macNo);
                        for (int i = 0; i < list.size(); i++) {
                            String[] CreName = (String[]) list.get(i);

                            String name = CreName[0];
                            String num = CreName[1];
                            String amt = CreName[2];
                            print(" " + pUtility.DataFull(name, 6) + pUtility.DataFull(("" + num), 18) + pUtility.DataFull(amt, 13));
                        }
                    }
                    if (frec.ArPaymentCnt > 0) {
                        print(pUtility.DataFullR("AR.                    ", 20) + pUtility.DataFull(IntFmt.format(frec.ArPaymentCnt), 6) + pUtility.DataFull(DecFmt.format(frec.ArPayment), 13));
                    }
                    if (CrArray != null) {
                        int ArraySize = CrArray.length;
                        for (int i = 0; i < ArraySize; i++) {
                            print(pUtility.DataFullR(CrArray[i].CrName + "                     ", 20) + pUtility.DataFull(IntFmt.format(CrArray[i].CrCnt), 6) + pUtility.DataFull(DecFmt.format(CrArray[i].CrAmt), 13));
                        }
                    }
                    if (frec.Paid_In > 0) {
                        print(pUtility.DataFullR("FLOAT IN              ", 20) + pUtility.DataFull(IntFmt.format(frec.Paid_InCnt), 6) + pUtility.DataFull(DecFmt.format(frec.Paid_In), 13));
                    }
                    if (frec.Paid_Out > 0) {
                        print(pUtility.DataFullR("FLOAT OUT             ", 20) + pUtility.DataFull(IntFmt.format(frec.Paid_OutCnt), 6) + pUtility.DataFull(DecFmt.format(frec.Paid_Out), 13));
                    }
                    double in = frec.Paid_In;
                    double out = frec.Paid_Out;
                    print("");
                    print(pUtility.DataFullR("CASH IN DRAWER        ", 20) + pUtility.DataFull(DecFmt.format(frec.Cash + in - out), 19));
                    print("========================================");
                    print(pUtility.DataFullR("Bank In              ", 20) + pUtility.DataFull(DecFmt.format(frec.Cash), 19));
                    print("========================================");
                    print(pUtility.DataFullR("Vat                  ", 20) + pUtility.DataFull(DecFmt.format(frec.VatAmt), 19));
                    print(pUtility.DataFullR("Net-Sales              ", 20) + pUtility.DataFull(DecFmt.format(frec.Net_Sale - NetSale_VatExclude), 19));
                    print("----------------------------------------");
                    print(pUtility.DataFullR("Customer             ", 20) + pUtility.DataFull(IntFmt.format(frec.Customer), 6));
                    print(pUtility.DataFullR("MGR Refund           ", 20) + pUtility.DataFull(IntFmt.format(frec.CntBillVoid), 6) + pUtility.DataFull(DecFmt.format(frec.AmtBillVoid), 13));
                    print(pUtility.DataFullR("MGR Void             ", 20) + pUtility.DataFull(IntFmt.format(frec.CntVoid), 6) + pUtility.DataFull(DecFmt.format(frec.VoidValue), 13));
                    print("----------------------------------------");
                    print(pUtility.DataFullR("Docket               ", 20) + pUtility.DataFull(IntFmt.format(frec.CntBill), 6));
                    print("  Start Docket   " + frec.StBill + "   To    " + frec.SpBill);
                    print("========================================");
                    print("SaleType     Docket    CC         Amount");
                    print("========================================");
                    print("Dine-In    " + pUtility.DataFull(IntFmt.format(frec.Eat_In_Cnt), 6) + pUtility.DataFull(IntFmt.format(frec.Eat_In_Cust), 8) + pUtility.DataFull(DecFmt.format(frec.Eat_In_Amt), 13));
                    print("Take Away  " + pUtility.DataFull(IntFmt.format(frec.Take_AwayCnt), 6) + pUtility.DataFull(IntFmt.format(frec.Take_AwayCust), 8) + pUtility.DataFull(DecFmt.format(frec.Take_AwayAmt), 13));
                    print("Delivery   " + pUtility.DataFull(IntFmt.format(frec.DeliveryCnt), 6) + pUtility.DataFull(IntFmt.format(frec.DeliveryCust), 8) + pUtility.DataFull(DecFmt.format(frec.DeliveryAmt), 13));
                    print("========================================");
                    print("");
                    print("");
                    print("");
                    print("                Analysts");
                    print("              ***Format***");
                    print("");
                    print("");
                    print("     " + pUtility.DataFullR(" DineIn", 10) + pUtility.DataFullR("   TakeAway", 16) + pUtility.DataFullR("Delivery", 18));
                    print("");
                    print("Gross Sales");
                    print("Net Sales");
                    print("Docket");
                    print("Customer");
                    print("AVG/Dock");
                    print("AVG/Head");
                    print("----------------------------------------");
                    print("" + pUtility.DataFull(DecFmt.format(totalE), 10) + " " + pUtility.DataFull(DecFmt.format(totalT), 14) + "" + pUtility.DataFull(DecFmt.format(totalD), 15));
                    print("" + pUtility.DataFull(DecFmt.format(nettotalE), 10) + " " + pUtility.DataFull(DecFmt.format(nettotalT), 14) + "" + pUtility.DataFull(DecFmt.format(nettotalD), 15));
                    print("" + pUtility.DataFull(IntFmt.format(countBillE), 10) + " " + pUtility.DataFull(IntFmt.format(countBillT), 14) + "" + pUtility.DataFull(IntFmt.format(countBillD), 15));
                    print("" + pUtility.DataFull(DecFmt.format(countCCE), 10) + " " + pUtility.DataFull(DecFmt.format(countCCT), 14) + "" + pUtility.DataFull(DecFmt.format(countCCD), 15));
                    print("" + pUtility.DataFull(DecFmt.format(AVG_DockE), 10) + " " + pUtility.DataFull(DecFmt.format(AVG_DockT), 14) + "" + pUtility.DataFull(DecFmt.format(AVG_DockD), 15));
                    print("" + pUtility.DataFull(DecFmt.format(AVG_CCE), 10) + " " + pUtility.DataFull(DecFmt.format(AVG_CCT), 14) + "" + pUtility.DataFull(DecFmt.format(AVG_CCD), 15));
                    print("");
                    print("----------------------------------------");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    cutPaper();
                    closePrint();
                }
            }
        }
    }

    private void printTerminalEngFormDriver(FinalcialRec frec, CreditRec[] CrArray, String macNo) {
        String t = "";
        Date dateP = new Date();
        if (!POSHW.getPRNPort().equals("NONE")) {
            List<Object[]> list1 = docAnalyse(Datefmt.format(dateP) + "", Datefmt.format(dateP) + "");
            double totalE = 0.00, totalT = 0.00, totalD = 0.00, nettotalE = 0.00, nettotalT = 0.00, nettotalD = 0.00;
            double countCCE = 0.00, countCCT = 0.00, countCCD = 0.00, countBillE = 0.00, countBillT = 0.00, countBillD = 0.00;
            double AVG_DockE = 0.00;
            double AVG_DockT = 0.00;
            double AVG_DockD = 0.00;
            double AVG_CCE = 0.00;
            double AVG_CCT = 0.00;
            double AVG_CCD = 0.00;

            if (list1 != null && list1.size() > 0) {
                countCCE = Double.parseDouble(list1.get(0)[2].toString());
                totalE = Double.parseDouble(list1.get(0)[4].toString());
                nettotalE = Double.parseDouble(list1.get(0)[5].toString());

                countCCT = Double.parseDouble(list1.get(1)[2].toString());
                nettotalT = Double.parseDouble(list1.get(1)[4].toString());
                totalT = Double.parseDouble(list1.get(1)[5].toString());

                countCCD = Double.parseDouble(list1.get(2)[2].toString());
                nettotalD = Double.parseDouble(list1.get(2)[4].toString());
                totalD = Double.parseDouble(list1.get(2)[5].toString());

                countBillE = Double.parseDouble(list1.get(0)[0].toString());
                countBillT = Double.parseDouble(list1.get(1)[0].toString());
                countBillD = Double.parseDouble(list1.get(2)[0].toString());

                AVG_DockE = nettotalE / countBillE;
                AVG_DockT = nettotalT / countBillT;
                AVG_DockD = nettotalD / countBillD;
                AVG_CCE = nettotalE / countCCE;
                AVG_CCT = nettotalT / countCCT;
                AVG_CCD = nettotalD / countCCD;

                if (nettotalE == 0.00 && countBillE == 0.00) {
                    AVG_DockE = 0.00;
                }
                if (nettotalT == 0.00 && countBillT == 0.00) {
                    AVG_DockT = 0.00;
                }
                if (nettotalD == 0.00 && countBillD == 0.00) {
                    AVG_DockD = 0.00;
                }
                if (nettotalE == 0.00 && countCCE == 0) {
                    AVG_CCE = 0.00;
                }
                if (nettotalT == 0.00 & countCCT == 0) {
                    AVG_CCT = 0.00;
                }
                if (nettotalD == 0.00 & countCCD == 0) {
                    AVG_CCD = 0.00;
                }
            }
            double totalDiscount;
            totalDiscount = frec.Vip_Disc + frec.Fast_Disc + frec.Emp_Disc
                    + frec.Train_Disc + frec.Sub_Disc + frec.Gen_Refund + frec.Promotion
                    + frec.Spacial + frec.Item_Disc + frec.Cupon_Disc;
            if (POSHW.getHeading1().length() >= 18) {
                String[] strs = POSHW.getHeading1().trim().replace(" ", Space).split("_");
                for (String data : strs) {
                    t += "colspan=3 align=center><font face=Angsana New size=2>" + data + "_";
                }
            } else {
                t += "colspan=3 align=center><font face=Angsana New size=2>" + POSHW.getHeading1().trim().replace(" ", "&nbsp; ") + "_";
            }
            if (POSHW.getHeading2().length() >= 18) {
                String[] strs = POSHW.getHeading2().replace(" ", Space).split("_");
                for (String data : strs) {
                    t += "colspan=3 align=center><font face=Angsana New size=2>" + data + "_";
                }
            } else {
                t += "colspan=3 align=center><font face=Angsana New size=2>" + POSHW.getHeading2().trim().replace(" ", "&nbsp; ") + "_";
            }
            t += "colspan=3 align=center><font face=Angsana New size=2>" + (POSHW.getHeading3()) + "_";
            t += "colspan=3 align=center><font face=Angsana New size=2>" + (POSHW.getHeading4()) + "_";
            t += "colspan=3 align=center><font face=Angsana New size=2>" + "REG.ID :" + Space + (POSHW.getTerminal()) + "_";
            t += ("colspan=3 align=center><font face=Angsana New size=2>_");
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + "รายงานการขายยอดเงินของเครื่อง" + "_");
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + "(Daily Sale..Terminal Report)" + "_");
            t += ("colspan=3 align=center><font face=Angsana New size=2>_");

            t += ("colspan=3 align=left><font face=Angsana New size=2>" + "Print Date" + Space + PPrint_DatefmtThai.format(dateP).replace("/", " / ") + "_");
            t += ("colspan=3 align=left><font face=Angsana New size=2>" + "Cashier:" + PublicVar._UserName + " Mac:" + macNo + "_");

            double NetSale_VatExclude = frec.Net_Sale * CONFIG.getP_Vat() / (100 + CONFIG.getP_Vat());
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + "-------------------------------------------------" + "_");
            t += ("colspan=2 align=left><font face=Angsana New size=2>" + "FOOD" + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(frec.Food) + TAB + "_");
            t += ("colspan=2 align=left><font face=Angsana New size=2>" + "BEVERAGE" + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(frec.Drink) + TAB + "_");
            if (frec.Product > 0) {
                t += ("colspan=2 align=left><font face=Angsana New size=2>" + "PRODUCT" + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(frec.Product) + TAB + "_");
            }
            t += ("colspan=2 align=left><font face=Angsana New size=2>" + "TOTAL-SALES" + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(frec.Dept_Sum) + TAB + "_");
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + "=====================================" + "_");
            if (frec.Charge > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2>" + pUtility.DataFullR("Charge   Credit", 20) + pUtility.DataFull(IntFmt.format(frec.ChargeCnt), 6) + pUtility.DataFull(DecFmt.format(frec.Charge), 13) + "_");
            }
            if (frec.Vip_Disc > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2>" + pUtility.DataFullR("Discount Member", 20) + pUtility.DataFull(IntFmt.format(frec.Vip_DiscCnt), 6) + pUtility.DataFull(DecFmt.format(frec.Vip_Disc), 13) + "_");
            }
            if (frec.Fast_Disc > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2>" + pUtility.DataFullR("Discount Festival", 20) + pUtility.DataFull(IntFmt.format(frec.Fast_DiscCnt), 6) + pUtility.DataFull(DecFmt.format(frec.Fast_Disc), 13) + "_");
            }
            if (frec.Emp_Disc > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2>" + pUtility.DataFullR("Discount Employ", 20) + pUtility.DataFull(IntFmt.format(frec.Emp_DiscCnt), 6) + pUtility.DataFull(DecFmt.format(frec.Emp_Disc), 13) + "_");
            }
            if (frec.Train_Disc > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2>" + pUtility.DataFullR("Discount Staff Discound", 20) + pUtility.DataFull(IntFmt.format(frec.Train_DiscCnt), 6) + pUtility.DataFull(DecFmt.format(frec.Train_Disc), 13) + "_");
            }
            if (frec.Sub_Disc > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2>" + pUtility.DataFullR("Discount Cupon", 20) + pUtility.DataFull(IntFmt.format(frec.Sub_DiscCnt), 6) + pUtility.DataFull(DecFmt.format(frec.Sub_Disc), 13) + "_");
            }
            if (frec.Gen_Refund > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2>" + pUtility.DataFullR("Discount Bath.", 20) + pUtility.DataFull(IntFmt.format(frec.Gen_RefundCnt), 6) + pUtility.DataFull(DecFmt.format(frec.Gen_Refund), 13) + "_");
            }
            if (frec.Promotion > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2>" + pUtility.DataFullR("Discount Promotion", 20) + pUtility.DataFull(IntFmt.format(frec.PromotionCnt), 6) + pUtility.DataFull(DecFmt.format(frec.Promotion), 13) + "_");
            }
            if (frec.Spacial > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2>" + pUtility.DataFullR("Discount Special", 20) + pUtility.DataFull(IntFmt.format(frec.SpacialCnt), 6) + pUtility.DataFull(DecFmt.format(frec.Spacial), 13) + "_");
            }
            if (frec.Item_Disc > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2>" + pUtility.DataFullR("Discount Item", 20) + pUtility.DataFull(IntFmt.format(frec.Item_DiscCnt), 6) + pUtility.DataFull(DecFmt.format(frec.Item_Disc), 13) + "_");
            }
            if (frec.Cupon_Disc > 0) {
                mysqlConnect.open(this.getClass());
                try {
                    String sql = "select sum(cuamt) amt,sum(cuquan) quan,"
                            + " t_cupon.cucode code,cupon.cuname name "
                            + "from t_cupon "
                            + "inner join cupon "
                            + "on t_cupon.cucode = cupon.cucode "
                            + "where t_cupon.cuquan<>'0' "
                            + "and t_cupon.refund<>'V' "
                            + "group by t_cupon.cucode, cupon.cuname";
                    ResultSet rs = mysqlConnect.executeQuery(sql);
                    while (rs.next()) {
                        double amt = rs.getDouble("amt");
                        String quan = rs.getString("quan");
                        String name = rs.getString("name");
                        t += (Space + "align=left><font face=Angsana New size=2>" + pUtility.DataFullR(name, 20) + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(quan, 6) + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(DecFmt.format(amt), 13) + TAB + "_");
                    }
                } catch (SQLException e) {
                    AppLogUtil.log(PPrint.class, "error", e);
                } finally {
                    mysqlConnect.closeConnection(this.getClass());
                }
            }

            t += ("colspan=3 align=center><font face=Angsana New size=2>" + "-------------------------------------------------" + "_");
            t += ("colspan=2 align=left><font face=Angsana New size=2>" + "Gross-Sales" + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(frec.Dept_Sum - totalDiscount) + TAB + "_");
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + "=================================" + "_");
            t += ("colspan=2 align=left><font face=Angsana New size=2>" + "Service Charge" + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(DecFmt.format(frec.Service), 19) + TAB + "_");
            if (frec.Gift > 0) {
                t += ("align=left><font face=Angsana New size=2>" + "Gift Voucher" + "</td><td align=right><font face=Angsana New size=2>" + IntFmt.format(frec.GiftCnt) + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(frec.Gift) + TAB + "_");
            }
            if (frec.Entertain > 0) {
                t += ("colspan=3 align=center><font face=Angsana New size=2>" + "Entertain" + IntFmt.format(frec.BillEntertain) + DecFmt.format(frec.Entertain) + TAB + "_");
            }
            if (CONFIG.getP_VatType().contains("I")) {
                t += ("colspan=2 align=left><font face=Angsana New size=2>" + "Net-Sales" + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(DecFmt.format(frec.Net_Sale), 19) + TAB + "_");
            }
            if (CONFIG.getP_VatType().contains("E")) {
                t += ("colspan=2 align=left><font face=Angsana New size=2>" + "Net-Sales" + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(DecFmt.format(frec.Net_Sale), 19) + TAB + "_");
                t += ("colspan=2 align=left><font face=Angsana New size=2>" + "Net (มูลค่า)" + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(DecFmt.format(frec.Net_Sale - NetSale_VatExclude), 19) + TAB + "_");
            }
            t += ("colspan=2 align=left><font face=Angsana New size=2>" + "Round Total" + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(frec.B_NetDiff) + TAB + "_");
            t += ("align=left><font face=Angsana New size=2>" + "CASH" + "</td><td align=right><font face=Angsana New size=2>" + IntFmt.format(frec.CashCnt) + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(frec.Cash) + TAB + "_");

//            พิมพ์ Credit แบบ Detail 
//            ตัวอย่าง VISA   xxx1234  1000
//            MASTER xxx3346  1500
            String[] credit = credit(macNo);
            if (!credit.equals("")) {
                String cd = credit[0];
                String am = credit[1];
                if (am == null) {
                    am = "0";
                }
                t += ("align=left><font face=Angsana New size=2>" + "CRADIT" + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(cd, 6) + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(Double.parseDouble(am)) + TAB + "_");
                List<String[]> list = creName(macNo);
                t += ("colspan=3 align=center><font face=Angsana New size=2>" + "รายการรับชำระเครดิต" + "_");
                for (int i = 0; i < list.size(); i++) {
                    String[] CreName = (String[]) list.get(i);

                    String name = CreName[0];
                    String num = CreName[1];
                    String amt = CreName[2];
                    double amt1 = Double.parseDouble(amt);
                    t += ("align=left><font face=Angsana New size=2>" + TAB + pUtility.DataFull(name, 16) + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(("" + num), 8) + "</td><td  align=right><font face=Angsana New size=2>" + pUtility.DataFull(DecFmt.format(amt1), 13)) + TAB + "_";
                }
            }
            if (frec.ArPaymentCnt > 0) {
                t += ("colspan=1 left=center><font face=Angsana New size=2>" + TAB + "AR." + "</td><td align=right><font face=Angsana New size=2>" + IntFmt.format(frec.ArPaymentCnt) + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(frec.ArPayment) + TAB + "_");
            }
            if (CrArray != null) {
                int ArraySize = CrArray.length;
                for (int i = 0; i < ArraySize; i++) {
                    t += ("colspan=2 align=center><font face=Angsana New size=2>" + Space + pUtility.DataFull(IntFmt.format(CrArray[i].CrCnt), 6) + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(DecFmt.format(CrArray[i].CrAmt), 13) + TAB + "_");
                }
            }

            t += ("colspan=3 align=center><font face=Angsana New size=2>_");
            if (frec.Paid_In > 0) {
                t += ("colspan=2 align=left><font face=Angsana New size=2>" + "FLOAT IN" + Space + pUtility.DataFull(IntFmt.format(frec.Paid_InCnt), 6) + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(DecFmt.format(frec.Paid_In), 13) + TAB + "_");
            }
            if (frec.Paid_Out > 0) {
                t += ("colspan=2 align=left><font face=Angsana New size=2>" + "FLOAT OUT" + Space + pUtility.DataFull(IntFmt.format(frec.Paid_OutCnt), 6) + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(DecFmt.format(frec.Paid_Out), 13) + TAB + "_");
            }
            double in = frec.Paid_In;
            double out = frec.Paid_Out;
            t += ("colspan=2 align=left><font face=Angsana New size=2>" + "CASH IN DRAWER" + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(DecFmt.format(frec.Cash + in - out), 19) + TAB + "_");
            t += ("colspan=2 align=left><font face=Angsana New size=2>" + "Bank In" + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(DecFmt.format(frec.Cash), 19) + TAB + "_");
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + "=================================" + "_");
            t += ("colspan=2 align=left><font face=Angsana New size=2>" + "Net Total : " + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(DecFmt.format(frec.Net_Sale + (frec.B_NetDiff * -1)), 19) + TAB + "_");
            t += ("colspan=2 align=left><font face=Angsana New size=2>" + "Vat" + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(DecFmt.format(frec.VatAmt), 19) + TAB + "_");
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + "-------------------------------------------------" + "_");
            t += ("colspan=2 align=left><font face=Angsana New size=2>" + "Customer" + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(IntFmt.format(frec.Customer), 6) + TAB + "_");
            t += ("colspan=2 align=left><font face=Angsana New size=2>" + "MGR Refund" + Space + pUtility.DataFull(IntFmt.format(frec.CntBillVoid), 6) + Space + "Doc." + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(DecFmt.format(frec.AmtBillVoid), 13) + TAB + "_");
            t += ("colspan=2 align=left><font face=Angsana New size=2>" + "MGR Void" + Space + TAB + pUtility.DataFull(IntFmt.format(frec.CntVoid), 6) + Space + "Items." + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(DecFmt.format(frec.VoidValue), 13) + TAB + "_");
            t += ("colspan=2 align=left><font face=Angsana New size=2>" + "Total Docket" + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(IntFmt.format(frec.CntBill), 6) + TAB + "_");
            t += ("colspan=3 align=left><font face=Angsana New size=2>" + "Start Docket" + Space + frec.StBill + Space + "To.." + Space + frec.SpBill + "_");
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + "=================================" + "_");
            t += ("colspan=3 align=left><font face=Angsana New size=2>" + "SaleType" + TAB + TAB + "Docket" + TAB + TAB + "CC" + TAB + "Amount" + "_");
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + "=================================" + "_");
            t += ("align=left><font face=Angsana New size=2>" + "Dine - In" + pUtility.DataFullSpace(IntFmt.format(frec.Eat_In_Cnt), 11) + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFullSpace(IntFmt.format(frec.Eat_In_Cust), 8) + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(DecFmt.format(frec.Eat_In_Amt), 13) + TAB + "_");
            t += ("align=left><font face=Angsana New size=2>" + "Take Away" + pUtility.DataFullSpace(IntFmt.format(frec.Take_AwayCnt), 11) + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFullSpace(IntFmt.format(frec.Take_AwayCust), 8) + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(DecFmt.format(frec.Take_AwayAmt), 13) + TAB + "_");
            t += ("align=left><font face=Angsana New size=2>" + "Delivery" + pUtility.DataFullSpace(IntFmt.format(frec.DeliveryCnt), 11) + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFullSpace(IntFmt.format(frec.DeliveryCust), 8) + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(DecFmt.format(frec.DeliveryAmt), 13) + TAB + "_");
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + "=================================" + "_");
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + "Analysts" + "_");
            t += ("colspan=3 align=center><font face=Angsana New size=2>_");
            t += ("colspan=3 align=right><font face=Angsana New size=2>" + "DineIn" + TAB + TAB + "TakeAway" + TAB + TAB + "Delivery" + "_");
            t += ("colspan=3 align=left><font face=Angsana New size=2>" + "Gross Sales" + "_");
            t += ("align=right><font face=Angsana New size=2>" + DecFmt.format(totalE) + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(totalT) + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(totalD) + TAB + "_");
            t += ("colspan=3 align=left><font face=Angsana New size=2>" + "Net Sales" + "_");
            t += ("align=right><font face=Angsana New size=2>" + DecFmt.format(nettotalE) + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(nettotalT) + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(nettotalD) + TAB + "_");
            t += ("colspan=3 align=left><font face=Angsana New size=2>" + "Docket" + "_");
            t += ("align=right><font face=Angsana New size=2>" + IntFmt.format(countBillE) + "</td><td align=right><font face=Angsana New size=2>" + IntFmt.format(countBillT) + "</td><td align=right><font face=Angsana New size=2>" + IntFmt.format(countBillD) + TAB + "_");
            t += ("colspan=3 align=left><font face=Angsana New size=2>" + "Customer" + "_");
            t += ("align=right><font face=Angsana New size=2>" + DecFmt.format(countCCE) + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(countCCT) + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(countCCD) + TAB + "_");
            t += ("colspan=3 align=left><font face=Angsana New size=2>" + "AVG/Dock" + "_");
            t += ("align=right><font face=Angsana New size=2>" + DecFmt.format(AVG_DockE) + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(AVG_DockT) + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(AVG_DockD) + TAB + "_");
            t += ("colspan=3 align=left><font face=Angsana New size=2>" + "AVG/Head" + "_");
            t += ("align=right><font face=Angsana New size=2>" + DecFmt.format(AVG_CCE) + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(AVG_CCT) + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(AVG_CCD) + TAB + "_");
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + "-------------------------------------------------" + "_");
            t += ("colspan=3 align=center><font face=Angsana New size=2>_");

        }
        t = changeReportLanguage(t);
        PrintDriver pd = new PrintDriver();
        String[] strs = t.split("_");

        for (String data1 : strs) {
            pd.addTextIFont(data1);
        }
        pd.printHTML();
    }

    public void printGroup(PluRec[] GArray) {
        Double SumEQty = 0.0;
        Double SumEAmt = 0.0;
        Double SumTQty = 0.0;
        Double SumTAmt = 0.0;
        Double SumDQty = 0.0;
        Double SumDAmt = 0.0;
        Double SumPQty = 0.0;
        Double SumPAmt = 0.0;
        Double SumWQty = 0.0;
        Double SumWAmt = 0.0;
        Double SumSQty = 0.0;
        Double SumSAmt = 0.0;
        int ArraySize = GArray.length;
        if (PublicVar.printdriver == true) {
            printGroupDriver(GArray);
        } else {
            if (!POSHW.getPRNPort().equals("NONE")) {
                if (openPrint(POSHW.getPRNPort())) {
                    initPrinter();
                    print(POSHW.getHeading1());
                    print(POSHW.getHeading2());
                    print(POSHW.getHeading3());
                    print(POSHW.getHeading4());

                    print("REG ID :" + POSHW.getMacNo());
                    print("         รายงานการขายตามกลุ่มสินค้า");
                    print("           (Department Report)");
                    print("หมายเลขเครื่อง :" + GArray[0].MacNo1 + " ..." + GArray[0].MacNo2);
                    print("พนักงานขาย    :" + GArray[0].Cashier1 + "..." + GArray[0].Cashier2);
                    print("รหัสกลุ่มสินค้า (Dept/Group) " + GArray[0].Group1 + "..." + GArray[0].Group2);
                    print(" ");
                    Date dateP = new Date();
                    print(PPrint_DatefmtThai.format(dateP).replace("/", " / ") + " " + "Cashier:" + PublicVar._User + " Mac:" + PublicVar.MACNO);
                    print("----------------------------------------");
                    print("รายละเอียด");
                    print("    .....EAT IN.....   ...TAKE AWAY.....");
                    print("    ....DELIVERY....   .....PINTO.......");
                    print("    ...WHOLE SALE...   .....TOTAL.......");
                    print("----------------------------------------");
                    if (GArray[0].S_Qty > 0) {
                        for (int i = 0; i < ArraySize; i++) {

                            print(pUtility.DataFullR(GArray[i].GroupCode, 4) + "  " + GArray[i].GroupName);
                            print(pUtility.DataFull(IntFmt.format(GArray[i].E_Qty), 6) + pUtility.DataFull(DecFmt.format(GArray[i].E_Amt), 12) + pUtility.DataFull(IntFmt.format(GArray[i].T_Qty), 6) + pUtility.DataFull(DecFmt.format(GArray[i].T_Amt), 12));
                            print(pUtility.DataFull(IntFmt.format(GArray[i].D_Qty), 6) + pUtility.DataFull(DecFmt.format(GArray[i].D_Amt), 12) + pUtility.DataFull(IntFmt.format(GArray[i].P_Qty), 6) + pUtility.DataFull(DecFmt.format(GArray[i].P_Amt), 12));
                            print(pUtility.DataFull(IntFmt.format(GArray[i].W_Qty), 6) + pUtility.DataFull(DecFmt.format(GArray[i].W_Amt), 12) + pUtility.DataFull(IntFmt.format(GArray[i].S_Qty), 6) + pUtility.DataFull(DecFmt.format(GArray[i].S_Amt), 12));

                            SumEQty = SumEQty + GArray[i].E_Qty;
                            SumEAmt = SumEAmt + GArray[i].E_Amt;
                            SumTQty = SumTQty + GArray[i].T_Qty;
                            SumTAmt = SumTAmt + GArray[i].T_Amt;
                            SumDQty = SumDQty + GArray[i].D_Qty;
                            SumDAmt = SumDAmt + GArray[i].D_Amt;
                            SumPQty = SumPQty + GArray[i].P_Qty;
                            SumPAmt = SumPAmt + GArray[i].P_Amt;
                            SumWQty = SumWQty + GArray[i].W_Qty;
                            SumWAmt = SumWAmt + GArray[i].W_Amt;
                            SumSQty = SumSQty + GArray[i].S_Qty;
                            SumSAmt = SumSAmt + GArray[i].S_Amt;
                        }
                    }
                    print("----------------------------------------");
                    print("***Subtotal***");
                    print(pUtility.DataFull(IntFmt.format(SumEQty), 6) + pUtility.DataFull(DecFmt.format(SumEAmt), 12) + pUtility.DataFull(IntFmt.format(SumTQty), 6) + pUtility.DataFull(DecFmt.format(SumTAmt), 12));
                    print(pUtility.DataFull(IntFmt.format(SumDQty), 6) + pUtility.DataFull(DecFmt.format(SumDAmt), 12) + pUtility.DataFull(IntFmt.format(SumPQty), 6) + pUtility.DataFull(DecFmt.format(SumPAmt), 12));
                    print(pUtility.DataFull(IntFmt.format(SumWQty), 6) + pUtility.DataFull(DecFmt.format(SumWAmt), 12) + pUtility.DataFull(IntFmt.format(SumSQty), 6) + pUtility.DataFull(DecFmt.format(SumSAmt), 12));

                    print("----------------------------------------");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    cutPaper();
                    closePrint();
                }
            }
        }
    }

    public void printGroupDriver(PluRec[] GArray) {
        String t = "";
        Double SumEQty = 0.0;
        Double SumEAmt = 0.0;
        Double SumTQty = 0.0;
        Double SumTAmt = 0.0;
        Double SumDQty = 0.0;
        Double SumDAmt = 0.0;
        Double SumPQty = 0.0;
        Double SumPAmt = 0.0;
        Double SumWQty = 0.0;
        Double SumWAmt = 0.0;
        Double SumSQty = 0.0;
        Double SumSAmt = 0.0;
        int ArraySize = GArray.length;

        if (POSHW.getHeading1().length() >= 18) {
            String[] strs = POSHW.getHeading1().replace(" ", Space).split("_");
            for (String data : strs) {
                t += "colspan=3 align=center><font face=Angsana New size=2>" + data + "_";
            }
        } else {
            t += "colspan=3 align=center><font face=Angsana New size=2>" + POSHW.getHeading1().trim().replace(" ", "&nbsp; ") + "_";
        }
        if (POSHW.getHeading2().length() >= 18) {
            String[] strs = POSHW.getHeading2().replace(" ", Space).split("_");
            for (String data : strs) {
                t += "colspan=3 align=center><font face=Angsana New size=2>" + data + "_";
            }
        } else {
            t += "colspan=3 align=center><font face=Angsana New size=2>" + POSHW.getHeading2().trim().replace(" ", "&nbsp; ") + "_";
        }
        t += "colspan=3 align=center><font face=Angsana New size=2>" + (POSHW.getHeading3()) + "_";
        t += "colspan=3 align=center><font face=Angsana New size=2>" + (POSHW.getHeading4()) + "_";

        t += "colspan=3 align=center><font face=Angsana New size=2>" + "REG ID :" + POSHW.getTerminal() + "_";
        t += "colspan=3 align=center><font face=Angsana New size=2>" + ("_");
        t += "colspan=3 align=center><font face=Angsana New size=2>" + ("รายงานการขายตามกลุ่มสินค้า" + "_");
        t += "colspan=3 align=center><font face=Angsana New size=2>" + ("(Daily..Department Report)" + "_");
        t += "colspan=3 align=center><font face=Angsana New size=2>" + ("_");
        Date dateP = new Date();
        t += "colspan=3 align=left><font face=Angsana New size=2>" + "Print Date :" + Space + (PPrint_DatefmtThai.format(dateP).replace("/", " / ") + "Cashier:" + PublicVar._User + " Mac:" + PublicVar.MACNO + "_");
        t += "align=left><font face=Angsana New size=2>" + ("หมายเลขเครื่อง :" + "</td><td colspan=2 align=left><font face=Angsana New size=2>" + GArray[0].MacNo1 + " ..." + GArray[0].MacNo2 + "_");
        t += "align=left><font face=Angsana New size=2>" + ("พนักงานขาย :" + "</td><td colspan=2 align=left><font face=Angsana New size=2>" + GArray[0].Cashier1 + "..." + GArray[0].Cashier2 + "_");
        t += "align=left><font face=Angsana New size=2>" + ("รหัสกลุ่มสินค้า :" + "</td><td colspan=2 align=left><font face=Angsana New size=2>" + GArray[0].Group1 + "..." + GArray[0].Group2 + "_");
        t += "colspan=3 align=center><font face=Angsana New size=2>" + ("_");
        t += "colspan=3 align=center><font face=Angsana New size=2>" + ("----------------------------------------" + "_");
        t += "colspan=3 align=center><font face=Angsana New size=2>" + "รายละเอียด" + "_";
        t += "align=left><font face=Angsana New size=2>" + "EAT IN " + "</td><td colspan=2 align=left><font face=Angsana New size=2>" + "TAKE AWAY " + "_";
        t += "align=left><font face=Angsana New size=2>" + "DELIVERY" + "</td><td colspan=2 align=left><font face=Angsana New size=2>" + "PINTO " + "_";
        t += "align=left><font face=Angsana New size=2>" + "WHOLE SALE" + "</td><td colspan=2 align=left><font face=Angsana New size=2>" + "TOTAL " + "_";
        t += "colspan=3 align=Center><font face=Angsana New size=2>" + ("----------------------------------------" + "_");
        if (GArray[0].S_Qty > 0) {
            for (int i = 0; i < ArraySize; i++) {
                t += "align=left><font face=Angsana New size=2>" + (pUtility.DataFullR(GArray[i].GroupCode, 4) + Space + Space + GArray[i].GroupName + "_");
                t += "align=left><font face=Angsana New size=2>" + TAB + (pUtility.DataFullSpace(IntFmt.format(GArray[i].E_Qty), 3) + pUtility.DataFullSpace(DecFmt.format(GArray[i].E_Amt), 10) + "</td><td colspan=2 align=left><font face=Angsana New size=2>" + pUtility.DataFullSpace(IntFmt.format(GArray[i].T_Qty), 3) + pUtility.DataFullSpace(DecFmt.format(GArray[i].T_Amt), 10) + "_");
                t += "align=left><font face=Angsana New size=2>" + TAB + (pUtility.DataFullSpace(IntFmt.format(GArray[i].D_Qty), 3) + pUtility.DataFullSpace(DecFmt.format(GArray[i].D_Amt), 10) + "</td><td colspan=2 align=left><font face=Angsana New size=2>" + pUtility.DataFullSpace(IntFmt.format(GArray[i].P_Qty), 3) + pUtility.DataFullSpace(DecFmt.format(GArray[i].P_Amt), 10) + "_");
                t += "align=left><font face=Angsana New size=2>" + TAB + (pUtility.DataFullSpace(IntFmt.format(GArray[i].W_Qty), 3) + pUtility.DataFullSpace(DecFmt.format(GArray[i].W_Amt), 10) + "</td><td colspan=2 align=left><font face=Angsana New size=2>" + pUtility.DataFullSpace(IntFmt.format(GArray[i].S_Qty), 3) + pUtility.DataFullSpace(DecFmt.format(GArray[i].S_Amt), 10) + "_");

                SumEQty = SumEQty + GArray[i].E_Qty;
                SumEAmt = SumEAmt + GArray[i].E_Amt;
                SumTQty = SumTQty + GArray[i].T_Qty;
                SumTAmt = SumTAmt + GArray[i].T_Amt;
                SumDQty = SumDQty + GArray[i].D_Qty;
                SumDAmt = SumDAmt + GArray[i].D_Amt;
                SumPQty = SumPQty + GArray[i].P_Qty;
                SumPAmt = SumPAmt + GArray[i].P_Amt;
                SumWQty = SumWQty + GArray[i].W_Qty;
                SumWAmt = SumWAmt + GArray[i].W_Amt;
                SumSQty = SumSQty + GArray[i].S_Qty;
                SumSAmt = SumSAmt + GArray[i].S_Amt;
            }
        }
        t += "colspan=3 align=center><font face=Angsana New size=2>" + ("----------------------------------------" + "_");
        t += "align=center><font face=Angsana New size=2>" + ("***Subtotal***" + "_");
        t += "align=left><font face=Angsana New size=2>" + (pUtility.DataFullSpace(IntFmt.format(SumEQty), 3) + pUtility.DataFullSpace(DecFmt.format(SumEAmt), 12) + "</td><td colspan=2 align=left><font face=Angsana New size=2>" + pUtility.DataFullSpace(IntFmt.format(SumTQty), 3) + pUtility.DataFullSpace(DecFmt.format(SumTAmt), 12) + "_");
        t += "align=left><font face=Angsana New size=2>" + (pUtility.DataFullSpace(IntFmt.format(SumDQty), 3) + pUtility.DataFullSpace(DecFmt.format(SumDAmt), 12) + "</td><td colspan=2 align=left><font face=Angsana New size=2>" + pUtility.DataFullSpace(IntFmt.format(SumPQty), 3) + pUtility.DataFullSpace(DecFmt.format(SumPAmt), 12) + "_");
        t += "align=left><font face=Angsana New size=2>" + (pUtility.DataFullSpace(IntFmt.format(SumWQty), 3) + pUtility.DataFullSpace(DecFmt.format(SumWAmt), 12) + "</td><td colspan=2 align=left><font face=Angsana New size=2>" + pUtility.DataFullSpace(IntFmt.format(SumSQty), 3) + pUtility.DataFullSpace(DecFmt.format(SumSAmt), 12) + "_");
        t += "colspan=3 align=center><font face=Angsana New size=2>" + ("----------------------------------------") + "_";
        t += "align=center><font face=Angsana New size=2>" + "_";

        PrintDriver pd = new PrintDriver();
        String[] strs = t.split("_");

        for (String data1 : strs) {
            pd.addTextIFont(data1);
        }

        pd.printHTML();
    }

    public void printPlu(PluRec[] GArray) {
        Double SumEQty = 0.0;
        Double SumEAmt = 0.0;
        Double SumTQty = 0.0;
        Double SumTAmt = 0.0;
        Double SumDQty = 0.0;
        Double SumDAmt = 0.0;
        Double SumPQty = 0.0;
        Double SumPAmt = 0.0;
        Double SumWQty = 0.0;
        Double SumWAmt = 0.0;
        Double SumSQty = 0.0;
        Double SumSAmt = 0.0;
        int ArraySize = GArray.length;
        if (!POSHW.getPRNPort().equals("NONE")) {
            if (PublicVar.printdriver == true) {
                printPluDriver(GArray);
            } else {
                if (openPrint(POSHW.getPRNPort())) {
                    initPrinter();
                    print(POSHW.getHeading1());
                    print(POSHW.getHeading2());
                    print(POSHW.getHeading3());
                    print(POSHW.getHeading4());

                    print("REG ID :" + POSHW.getMacNo());
                    print("         รายงานการขายตามรหัสสินค้า");
                    print("              (PLU Report)");
                    print("หมายเลขเครื่อง :" + GArray[0].MacNo1 + " ..." + GArray[0].MacNo2);
                    print("พนักงานขาย    :" + GArray[0].Cashier1 + "..." + GArray[0].Cashier2);
                    print("รหัสกลุ่มสินค้า (Dept/Group) " + GArray[0].Group1 + "..." + GArray[0].Group2);
                    print("รหัสสินค้า (PLU) " + GArray[0].Plu1 + "..." + GArray[0].Plu2);
                    print(" ");
                    Date dateP = new Date();
                    print(PPrint_DatefmtThai.format(dateP).replace("/", " / ") + " " + "Cashier:" + PublicVar._User + " Mac:" + PublicVar.MACNO);
                    print("----------------------------------------");
                    print("รายละเอียด");
                    print("    .....EAT IN.....   ...TAKE AWAY.....");
                    print("    ....DELIVERY....   .....PINTO.......");
                    print("    ...WHOLE SALE...   .....TOTAL.......");
                    print("----------------------------------------");
                    String TempDept = "";
                    if (ArraySize > 0) {
                        TempDept = "";
                    }
                    if (GArray[0].S_Qty > 0) {
                        for (int i = 0; i < ArraySize; i++) {
                            if (!GArray[i].GroupCode.equals(TempDept)) {
                                print(pUtility.DataFullR(GArray[i].GroupCode, 4) + "  " + GArray[i].GroupName);
                                TempDept = GArray[i].GroupCode;
                            }

                            print(pUtility.DataFullR(GArray[i].PCode + "  " + GArray[i].PName, 38));
                            print(pUtility.DataFull(IntFmt.format(GArray[i].E_Qty), 6) + pUtility.DataFull(DecFmt.format(GArray[i].E_Amt), 12) + pUtility.DataFull(IntFmt.format(GArray[i].T_Qty), 6) + pUtility.DataFull(DecFmt.format(GArray[i].T_Amt), 12));
                            print(pUtility.DataFull(IntFmt.format(GArray[i].D_Qty), 6) + pUtility.DataFull(DecFmt.format(GArray[i].D_Amt), 12) + pUtility.DataFull(IntFmt.format(GArray[i].P_Qty), 6) + pUtility.DataFull(DecFmt.format(GArray[i].P_Amt), 12));
                            print(pUtility.DataFull(IntFmt.format(GArray[i].W_Qty), 6) + pUtility.DataFull(DecFmt.format(GArray[i].W_Amt), 12) + pUtility.DataFull(IntFmt.format(GArray[i].S_Qty), 6) + pUtility.DataFull(DecFmt.format(GArray[i].S_Amt), 12));

                            SumEQty = SumEQty + GArray[i].E_Qty;
                            SumEAmt = SumEAmt + GArray[i].E_Amt;
                            SumTQty = SumTQty + GArray[i].T_Qty;
                            SumTAmt = SumTAmt + GArray[i].T_Amt;
                            SumDQty = SumDQty + GArray[i].D_Qty;
                            SumDAmt = SumDAmt + GArray[i].D_Amt;
                            SumPQty = SumPQty + GArray[i].P_Qty;
                            SumPAmt = SumPAmt + GArray[i].P_Amt;
                            SumWQty = SumWQty + GArray[i].W_Qty;
                            SumWAmt = SumWAmt + GArray[i].W_Amt;
                            SumSQty = SumSQty + GArray[i].S_Qty;
                            SumSAmt = SumSAmt + GArray[i].S_Amt;
                        }
                    }
                    print("----------------------------------------");
                    print("***Subtotal***");
                    print(pUtility.DataFull(IntFmt.format(SumEQty), 6) + pUtility.DataFull(DecFmt.format(SumEAmt), 12) + pUtility.DataFull(IntFmt.format(SumTQty), 6) + pUtility.DataFull(DecFmt.format(SumTAmt), 12));
                    print(pUtility.DataFull(IntFmt.format(SumDQty), 6) + pUtility.DataFull(DecFmt.format(SumDAmt), 12) + pUtility.DataFull(IntFmt.format(SumPQty), 6) + pUtility.DataFull(DecFmt.format(SumPAmt), 12));
                    print(pUtility.DataFull(IntFmt.format(SumWQty), 6) + pUtility.DataFull(DecFmt.format(SumWAmt), 12) + pUtility.DataFull(IntFmt.format(SumSQty), 6) + pUtility.DataFull(DecFmt.format(SumSAmt), 12));

                    print("----------------------------------------");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    cutPaper();
                    closePrint();
                }
            }
        }
    }

    public void printPluDriver(PluRec[] GArray) {
        String t = "";
        Double SumEQty = 0.0;
        Double SumEAmt = 0.0;
        Double SumTQty = 0.0;
        Double SumTAmt = 0.0;
        Double SumDQty = 0.0;
        Double SumDAmt = 0.0;
        Double SumPQty = 0.0;
        Double SumPAmt = 0.0;
        Double SumWQty = 0.0;
        Double SumWAmt = 0.0;
        Double SumSQty = 0.0;
        Double SumSAmt = 0.0;
        int ArraySize = GArray.length;
        if (POSHW.getHeading1().trim().length() >= 18) {
            String[] strs = POSHW.getHeading1().trim().replace(" ", Space).split("_");
            for (String data : strs) {
                t += "colspan=3 align=center><font face=Angsana New size=2>" + data + "_";
            }
        } else {
            t += "colspan=3 align=center><font face=Angsana New size=2>" + POSHW.getHeading1().trim().replace(" ", "&nbsp; ") + "_";
        }
        if (POSHW.getHeading2().trim().length() >= 18) {
            String[] strs = POSHW.getHeading2().trim().replace(" ", Space).split("_");
            for (String data : strs) {
                t += "colspan=3 align=center><font face=Angsana New size=2>" + data + "_";
            }
        } else {
            t += "colspan=3 align=center><font face=Angsana New size=2>" + POSHW.getHeading2().trim().replace(" ", "&nbsp; ") + "_";
        }
        t += "colspan=3 align=center><font face=Angsana New size=2>" + (POSHW.getHeading3().trim()) + "_";
        t += "colspan=3 align=center><font face=Angsana New size=2>" + (POSHW.getHeading4().trim()) + "_";

        t += "colspan=3 align=center><font face=Angsana New size=2>" + "REG.ID :" + POSHW.getTerminal() + "_";
        t += "colspan=3 align=center><font face=Angsana New size=2>_";
        t += "colspan=3 align=center><font face=Angsana New size=2>" + "รายงานการขายตามรหัสสินค้า" + "_";
        t += "colspan=3 align=center><font face=Angsana New size=2>" + "(PLU Report)" + "_";
        t += "colspan=3 align=center><font face=Angsana New size=2>_";
        Date dateP = new Date();
        t += "colspan=3 align=left><font face=Angsana New size=2>" + "Print Date" + Space + PPrint_DatefmtThai.format(dateP).replace("/", " / ") + Space + PublicVar._User + " Mac:" + Space + PublicVar.MACNO + "_";
        t += "align=left><font face=Angsana New size=2>" + "หมายเลขเครื่อง :" + "</td><td colspan=2 align=left><font face=Angsana New size=2>" + GArray[0].MacNo1 + " ..." + GArray[0].MacNo2 + "_";
        t += "align=left><font face=Angsana New size=2>" + "พนักงานขาย :" + "</td><td colspan=2 align=left><font face=Angsana New size=2>" + GArray[0].Cashier1 + "..." + GArray[0].Cashier2 + "_";
        t += "align=left><font face=Angsana New size=2>" + "รหัสกลุ่มสินค้า : " + "</td><td colspan=2 align=left><font face=Angsana New size=2>" + GArray[0].Group1 + "..." + GArray[0].Group2 + "_";
        t += "align=left><font face=Angsana New size=2>" + "รหัสสินค้า :" + "</td><td colspan=2 align=left><font face=Angsana New size=2>" + GArray[0].Plu1 + "..." + GArray[0].Plu2 + "_";
        t += "colspan=3 align=left><font face=Angsana New size=2>" + "_";
        t += "colspan=3 align=center><font face=Angsana New size=2>" + ("----------------------------------------" + "_");
        t += "colspan=3 align=center><font face=Angsana New size=2>" + ("รายละเอียด" + "_");
        t += "align=left><font face=Angsana New size=2>" + "EAT IN " + "</td><td colspan=2 align=left><font face=Angsana New size=2>" + "TAKE AWAY " + "_";
        t += "align=left><font face=Angsana New size=2>" + "DELIVERY" + "</td><td colspan=2 align=left><font face=Angsana New size=2>" + "PINTO " + "_";
        t += "align=left><font face=Angsana New size=2>" + "WHOLE SALE" + "</td><td colspan=2 align=left><font face=Angsana New size=2>" + "TOTAL " + "_";
        t += "colspan=3 align=center><font face=Angsana New size=2>" + ("----------------------------------------" + "_");
        String TempDept = "";
        if (ArraySize > 0) {
            TempDept = "";
        }
        if (GArray[0].S_Qty > 0) {
            for (int i = 0; i < ArraySize; i++) {
                if (!GArray[i].GroupCode.equals(TempDept)) {
                    t += "colspan=3 align=left><font face=Angsana New size=2>" + (pUtility.DataFullSpace(GArray[i].GroupCode, 4) + Space + GArray[i].GroupName) + "_";
                    TempDept = GArray[i].GroupCode;
                }

                t += "align=left><font face=Angsana New size=2>" + subStringText(GArray[i].PCode, 33) + "_";
                t += "align=left><font face=Angsana New size=2>" + subStringText(Space + GArray[i].PName, 33) + "_";
                t += "align=left><font face=Angsana New size=2>" + TAB + (pUtility.DataFullSpace(IntFmt.format(GArray[i].E_Qty), 3) + pUtility.DataFullSpace(DecFmt.format(GArray[i].E_Amt), 10) + "</td><td colspan=2 align=left><font face=Angsana New size=2>" + pUtility.DataFullSpace(IntFmt.format(GArray[i].T_Qty), 3) + pUtility.DataFullSpace(DecFmt.format(GArray[i].T_Amt), 10) + "_");
                t += "align=left><font face=Angsana New size=2>" + TAB + (pUtility.DataFullSpace(IntFmt.format(GArray[i].D_Qty), 3) + pUtility.DataFullSpace(DecFmt.format(GArray[i].D_Amt), 10) + "</td><td colspan=2 align=left><font face=Angsana New size=2>" + pUtility.DataFullSpace(IntFmt.format(GArray[i].P_Qty), 3) + pUtility.DataFullSpace(DecFmt.format(GArray[i].P_Amt), 10) + "_");
                t += "align=left><font face=Angsana New size=2>" + TAB + (pUtility.DataFullSpace(IntFmt.format(GArray[i].W_Qty), 3) + pUtility.DataFullSpace(DecFmt.format(GArray[i].W_Amt), 10) + "</td><td colspan=2 align=left><font face=Angsana New size=2>" + pUtility.DataFullSpace(IntFmt.format(GArray[i].S_Qty), 3) + pUtility.DataFullSpace(DecFmt.format(GArray[i].S_Amt), 10) + "_");

                SumEQty = SumEQty + GArray[i].E_Qty;
                SumEAmt = SumEAmt + GArray[i].E_Amt;
                SumTQty = SumTQty + GArray[i].T_Qty;
                SumTAmt = SumTAmt + GArray[i].T_Amt;
                SumDQty = SumDQty + GArray[i].D_Qty;
                SumDAmt = SumDAmt + GArray[i].D_Amt;
                SumPQty = SumPQty + GArray[i].P_Qty;
                SumPAmt = SumPAmt + GArray[i].P_Amt;
                SumWQty = SumWQty + GArray[i].W_Qty;
                SumWAmt = SumWAmt + GArray[i].W_Amt;
                SumSQty = SumSQty + GArray[i].S_Qty;
                SumSAmt = SumSAmt + GArray[i].S_Amt;
            }
        }
        t += "colspan=3 align=center><font face=Angsana New size=2>" + ("----------------------------------------") + "_";
        t += "colspan=3 align=center><font face=Angsana New size=2>" + ("***Subtotal***") + "_";
        t += "align=left><font face=Angsana New size=2>" + TAB + (pUtility.DataFullSpace(IntFmt.format(SumEQty), 3) + pUtility.DataFullSpace(DecFmt.format(SumEAmt), 10) + "</td><td colspan=2 align=left><font face=Angsana New size=2>" + pUtility.DataFull(IntFmt.format(SumTQty), 3) + pUtility.DataFullSpace(DecFmt.format(SumTAmt), 10)) + "_";
        t += "align=left><font face=Angsana New size=2>" + TAB + (pUtility.DataFullSpace(IntFmt.format(SumDQty), 3) + pUtility.DataFullSpace(DecFmt.format(SumDAmt), 10) + "</td><td colspan=2 align=left><font face=Angsana New size=2>" + pUtility.DataFull(IntFmt.format(SumPQty), 3) + pUtility.DataFullSpace(DecFmt.format(SumPAmt), 10)) + "_";
        t += "align=left><font face=Angsana New size=2>" + TAB + (pUtility.DataFullSpace(IntFmt.format(SumWQty), 3) + pUtility.DataFullSpace(DecFmt.format(SumWAmt), 10) + "</td><td colspan=2 align=left><font face=Angsana New size=2>" + pUtility.DataFull(IntFmt.format(SumSQty), 3) + pUtility.DataFullSpace(DecFmt.format(SumSAmt), 10)) + "_";
        t += "colspan=3 align=center><font face=Angsana New size=2>" + ("----------------------------------------") + "_";
        t += "colspan=3 align=center><font face=Angsana New size=2>" + "_";
        PrintDriver pd = new PrintDriver();
        String[] strs = t.split("_");

        for (String data1 : strs) {
            pd.addTextIFont(data1);
        }

        pd.printHTML();
    }

    public void printBillCopy(TranRecord[] myArray1, String _RefNo, int ICopy, CreditPaymentRec[] CreditArray1) {
        if (PublicVar.printdriver == true) {
            printBillCopyDriver(_RefNo, ICopy);
        } else {
            List<TSaleBean> listBean = billControl.getAllTSaleNovoid(_RefNo);

            int QtyLength = 5;
            int AmtLength = 10;
            int SubLength = 20;
            int SubLength2 = 13;
            int ItemCnt = 0;
            String VatStr;

            BillNoBean tBean = billControl.getData(_RefNo);
            if (!POSHW.getPRNPort().equals("NONE")) {
                if (openPrint(POSHW.getPRNPort())) {
                    initPrinter();
                    print(POSHW.getHeading1());
                    print(POSHW.getHeading2());
                    print(POSHW.getHeading3());
                    print(POSHW.getHeading4());

                    print("REG ID :" + POSHW.getMacNo());
                    print("      ***สำเนาใบเสร็จรับเงิน " + Integer.toString(ICopy) + " ***");
                    PublicVar.P_LineCount = 0;
                    for (int i = 0; i < listBean.size(); i++) {
                        TSaleBean bean = (TSaleBean) listBean.get(i);
                        if (!bean.getR_Void().equals("V")) {
                            ItemCnt = (int) (ItemCnt + bean.getR_Quan());
                        }
                    }
                    if (CONFIG.getP_PrintDetailOnRecp().equals("Y")) {
                        Date dateP = new Date();
                        print(PPrint_DatefmtThai.format(dateP).replace("/", " / ") + " " + "Cashier" + PublicVar._User + " Mac" + PublicVar.MACNO);
                        print("----------------------------------------");
                        for (int i = 0; i < listBean.size(); i++) {
                            TSaleBean bean = (TSaleBean) listBean.get(i);
                            if (bean.getR_Void().equals("V")) {
                                selectStye(12);
                                print("VOID..." + "User :" + bean.getR_VoidUser());
                                if (bean.getR_Vat().equals("V")) {
                                    VatStr = "-";
                                } else {
                                    VatStr = "*";
                                }
                                if (CONFIG.getP_CodePrn().equals("Y")) {
                                    print(bean.getR_PName());
                                    print(bean.getR_Normal() + VatStr + pUtility.DataFullR(bean.getR_PluCode(), 20) + "  " + pUtility.DataFull(IntFmt.format(-1 * bean.getR_Quan()), QtyLength) + pUtility.DataFull(DecFmt.format(-1 * bean.getR_Total()), AmtLength) + bean.getR_ETD());
                                } else {
                                    int sizeNew = 20;
                                    if (bean.getR_PName().length() > 20) {
                                        sizeNew = 21;
                                    }
                                    print(bean.getR_Normal() + VatStr + pUtility.DataFullR(bean.getR_PName(), sizeNew) + "  " + pUtility.DataFull(IntFmt.format(-1 * bean.getR_Quan()), QtyLength) + pUtility.DataFull(DecFmt.format(-1 * bean.getR_Total()), AmtLength) + bean.getR_ETD());
                                }
                                selectStye(13);
                            } else {
                                if (bean.getR_Vat().equals("V")) {
                                    VatStr = "-";
                                } else {
                                    VatStr = "*";
                                }
                                if (bean.getR_PrAmt() == 0) {
                                    if (CONFIG.getP_CodePrn().equals("Y")) {
                                        print(bean.getR_PName());
                                        print(bean.getR_Normal() + VatStr + pUtility.DataFullR(bean.getR_PluCode(), 20) + "  " + pUtility.DataFull(IntFmt.format(bean.getR_Quan()), QtyLength) + pUtility.DataFull(DecFmt.format(bean.getR_Total()), AmtLength) + bean.getR_ETD());
                                    } else {
                                        int sizeNew = 20;
                                        if (bean.getR_PName().length() > 20) {
                                            sizeNew = 21;
                                        }
                                        print(bean.getR_Normal() + VatStr + pUtility.DataFullR(bean.getR_PName(), sizeNew) + "  " + pUtility.DataFull(IntFmt.format(bean.getR_Quan()), QtyLength) + pUtility.DataFull(DecFmt.format(bean.getR_Total()), AmtLength) + bean.getR_ETD());
                                    }
                                } else {
                                    if (CONFIG.getP_CodePrn().equals("Y")) {
                                        print(bean.getR_PName());
                                        print(bean.getR_Normal() + VatStr + pUtility.DataFullR(bean.getR_PluCode(), 20) + "  " + pUtility.DataFull(IntFmt.format(bean.getR_Quan()), QtyLength) + pUtility.DataFull(DecFmt.format(bean.getR_Total()), AmtLength) + bean.getR_ETD());
                                    } else {
                                        int sizeNew = 20;
                                        if (bean.getR_PName().length() > 20) {
                                            sizeNew = 21;
                                        }
                                        print(bean.getR_Normal() + VatStr + pUtility.DataFullR(bean.getR_PName(), sizeNew) + "  " + pUtility.DataFull(IntFmt.format(bean.getR_Quan()), QtyLength) + pUtility.DataFull(DecFmt.format(bean.getR_Total()), AmtLength) + bean.getR_ETD());
                                    }
                                    if (bean.getR_PrType().equals("-P")) {
                                        if (bean.getR_PrAmt() > 0) {
                                            print("   **Promotion  " + bean.getR_PrCode() + " " + pUtility.DataFull(pUtility.SeekPromotionName(bean.getR_PrCode()), 20));
                                        }
                                        if (bean.getR_PrAmt2() > 0) {
                                            print("   **Promotion  " + bean.getR_PrCode2() + " " + pUtility.DataFull(pUtility.SeekPromotionName(bean.getR_PrCode2()), 20));
                                        }
                                    }
                                    if (bean.getR_PrType().equals("-I")) {
                                        if (bean.getR_PrDisc() != 0) {
                                            print("   **Item-Discount " + bean.getR_PrCode() + " " + pUtility.DataFull(DecFmt.format(bean.getR_PrDisc() - bean.getR_Redule()), QtyLength) + "%");
                                        }
                                        if (bean.getR_PrAmt2() > 0) {
                                            print("   **Promotion  " + bean.getR_PrCode2() + " " + pUtility.DataFull(DecFmt.format(bean.getR_PrAmt()), AmtLength));
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Date dateP = new Date();
                        print(PPrint_DatefmtThai.format(dateP).replace("/", " / ") + "TABLE  " + pUtility.DataFullR(tBean.getB_Table(), 5));
                        print("----------------------------------------");
                        print("     อาหารและเครื่องดื่ม " + pUtility.DataFull(DecFmt.format(tBean.getB_Total()), AmtLength));
                    }

                    print("----------------------------------------");
                    print("Sub-TOTAL         " + pUtility.DataFull(DecFmt.format(tBean.getB_Total()), AmtLength));
                    print("COMP NO:" + PublicVar._User + " Mac:" + PublicVar.MACNO);
                    if (tBean.getB_ProDiscAmt() > 0) {
                        print("    " + pUtility.DataFullR("ลด Promotion     ", SubLength) + pUtility.DataFull(DecFmt.format(tBean.getB_ProDiscAmt()), AmtLength));
                    }
                    if (tBean.getB_SpaDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("Special Disc     ", SubLength) + pUtility.DataFull(DecFmt.format(tBean.getB_SpaDiscAmt()), AmtLength));
                    }
                    if (tBean.getB_MemDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("ลดสมาชิก..........", SubLength2) + pUtility.DataFull(tBean.getB_MemDisc(), 8) + pUtility.DataFull(DecFmt.format(tBean.getB_MemDiscAmt()), AmtLength));
                    }
                    if (tBean.getB_FastDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("ลดเทศกาล.........", SubLength2) + pUtility.DataFull(tBean.getB_FastDisc(), 8) + pUtility.DataFull(DecFmt.format(tBean.getB_FastDiscAmt()), AmtLength));
                    }
                    if (tBean.getB_EmpDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("ลดพนักงาน.........", SubLength2) + pUtility.DataFull(tBean.getB_EmpDisc(), 8) + pUtility.DataFull(DecFmt.format(tBean.getB_EmpDiscAmt()), AmtLength));
                    }
                    if (tBean.getB_TrainDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("ลด Trainnee......", SubLength2) + pUtility.DataFull(tBean.getB_TrainDisc(), 8) + pUtility.DataFull(DecFmt.format(tBean.getB_TrainDiscAmt()), AmtLength));
                    }
                    if (tBean.getB_SubDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("ลดคูปอง(Cupon)    ", SubLength2) + pUtility.DataFull(tBean.getB_SubDisc(), 8) + pUtility.DataFull(DecFmt.format(tBean.getB_SubDiscAmt()), AmtLength));
                    }
                    if (tBean.getB_SubDiscBath() > 0) {
                        print("     " + pUtility.DataFullR("ลด(บาท)..........", SubLength) + pUtility.DataFull(DecFmt.format(tBean.getB_SubDiscBath()), AmtLength));
                    }
                    if (tBean.getB_ItemDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("ลดตามรายการ(Item)", SubLength) + pUtility.DataFull(DecFmt.format(tBean.getB_ItemDiscAmt()), AmtLength));
                    }
                    if (tBean.getB_CuponDiscAmt() > 0) {
                        mysqlConnect.open(this.getClass());
                        try {
                            Statement stmt = mysqlConnect.getConnection().createStatement();
                            String CheckCoupon = "select * from t_cupon where (r_refno='" + _RefNo + "') and (terminal='" + PublicVar.MACNO + "') ";
                            ResultSet rs = stmt.executeQuery(CheckCoupon);
                            while (rs.next()) {
                                print("     " + pUtility.DataFullR(pUtility.SeekCuponName(rs.getString("cucode")), 20) + pUtility.DataFull(DecFmt.format(rs.getDouble("cuamt")), AmtLength));
                                String SMS_Code = rs.getString("sms_code");
                                if ((SMS_Code != null) & (!SMS_Code.equals(""))) {
                                    print("     " + "SMS CODE " + SMS_Code);
                                }
                            }
                            rs.close();
                            stmt.close();
                        } catch (SQLException e) {
                            AppLogUtil.log(PPrint.class, "error", e);
                        } finally {
                            mysqlConnect.closeConnection(this.getClass());
                        }
                    }
                    if (tBean.getB_ServiceAmt() > 0) {
                        print("     " + pUtility.DataFullR("ค่าบริการ (Service)     ", 23) + pUtility.DataFull(DecFmt.format(tBean.getB_ServiceAmt()), 9));
                    }

                    if (tBean.getB_Earnest() > 0) {
                        print("     " + pUtility.DataFullR("หักคืนเงินมัดจำ           ", 23) + pUtility.DataFull(DecFmt.format(tBean.getB_Earnest()), 9));
                    }
                    if (CONFIG.getP_VatType().equals("I")) {
                        //Print_Str(" ");
                        selectStye(3);
                        print("TOTAL " + pUtility.DataFull(DecFmt.format(tBean.getB_NetTotal()), AmtLength));
                        selectStye(1);
                        if (CONFIG.getP_VatPrn().equals("Y")) {
                            print(pUtility.DataFull("              Vat...", 43) + pUtility.DataFull(DecFmt.format(tBean.getB_Vat()), AmtLength));
                        }
                    } else {
                        print(pUtility.DataFull("      Net-Amount ", SubLength) + pUtility.DataFull(DecFmt.format(tBean.getB_NetVat() + tBean.getB_NetNonVat()), AmtLength));
                        print(pUtility.DataFull("      Vat....... ", SubLength) + pUtility.DataFull(DecFmt.format(CONFIG.getP_Vat()), QtyLength) + "%" + pUtility.DataFull(DecFmt.format(tBean.getB_Vat()), AmtLength));
                        print("VAT INCLUDED");
                    }
                    if (tBean.getB_GiftVoucher() > 0) {
                        print("     " + pUtility.DataFullR("บัตรกำนัล..............", SubLength) + pUtility.DataFull(DecFmt.format(tBean.getB_GiftVoucher()), AmtLength));

                        mysqlConnect.open(this.getClass());
                        try {
                            Statement stmt = mysqlConnect.getConnection().createStatement();
                            String CheckGift = "select * from t_gift where (refno='" + _RefNo + "')";
                            ResultSet rs = stmt.executeQuery(CheckGift);
                            while (rs.next()) {
                                print("   " + pUtility.DataFull(rs.getString("giftbarcode"), 25) + "@" + pUtility.DataFull(DecFmt.format(rs.getDouble("giftamt")), AmtLength));
                            }
                            rs.close();
                            stmt.close();
                        } catch (SQLException e) {
                            AppLogUtil.log(PPrint.class, "error", e);
                        } finally {
                            mysqlConnect.closeConnection(this.getClass());
                        }
                    }
                    if (tBean.getB_PayAmt() > 0) {
                        print("เงินสด  : " + pUtility.DataFull(DecFmt.format(tBean.getB_PayAmt()), AmtLength) + "  ทอน : " + pUtility.DataFull(DecFmt.format(tBean.getB_Ton()), AmtLength));
                    }
                    if (tBean.getB_CrAmt1() > 0) {
                        //get credit name
                        String crName = "";
                        mysqlConnect.open(this.getClass());
                        try {
                            String sql = "select CrName from creditfile where crcode='" + tBean.getB_CrCode1() + "' limit 1";
                            Statement stmt = mysqlConnect.getConnection().createStatement();
                            ResultSet rs = stmt.executeQuery(sql);
                            if (rs.next()) {
                                crName = ThaiUtil.ASCII2Unicode(rs.getString("CrName"));
                            }

                            rs.close();
                            stmt.close();
                        } catch (SQLException e) {
                            AppLogUtil.log(PPrint.class, "error", e);
                        } finally {
                            mysqlConnect.closeConnection(this.getClass());
                        }

                        print(tBean.getB_CrCode1() + "  " + crName);
                        print("XXXXXXXXXXX" + pUtility.Addzero(tBean.getB_CardNo1(), 16).substring(12, 16) + "  " + tBean.getB_AppCode1());
                        print("Credit Payment               " + pUtility.DataFull(DecFmt.format(tBean.getB_CrAmt1()), AmtLength));
                    }
                    print("----------------------------------------");
                    selectStye(5);
                    print("Receipt No: " + _RefNo);
                    selectStye(1);
                    print("CC : " + IntFmt.format(tBean.getB_Cust()));
                    print(" ");
                    if (!CONFIG.getP_PrintRecpMessage().equals("")) {
                        print(CONFIG.getP_PrintRecpMessage());
                    }

                    if (!POSHW.getFootting1().equals("")) {
                        print(POSHW.getFootting1());
                    }
                    if (!POSHW.getFootting2().equals("")) {
                        print(POSHW.getFootting2());
                    }

                    print(" ");
                    selectStye(1);
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    cutPaper();

                    closePrint();
                }
            }
        }

    }

    public void printBillCopyDriver(String RefNo, int ICopy) {
        List<TSaleBean> listBean = billControl.getAllTSaleNovoid(RefNo);

        int QtyLength = 5;
        int AmtLength = 10;
        int ItemCnt = 0;
        String t = "";

        BillNoBean tBean = billControl.getData(RefNo);
        PPrint p = new PPrint();
        if (!POSHW.getPRNPort().equals("NONE")) {
            if (POSHW.getHeading1().length() >= 18) {
                String[] strs = POSHW.getHeading1().trim().replace(" ", Space).split("_");
                for (String data : strs) {
                    t += "colspan=3 align=center><font face=Angsana New size=3>" + data + "_";
                }
            } else {
                t += "colspan=3 align=center><font face=Angsana New size=3>" + POSHW.getHeading1().trim().replace(" ", "&nbsp; ") + "_";
            }
            if (POSHW.getHeading2().length() >= 18) {
                String[] strs = POSHW.getHeading2().trim().replace(" ", Space).split("_");
                for (String data : strs) {
                    t += "colspan=3 align=center><font face=Angsana New size=2>" + data + "_";
                }
            } else {
                t += "colspan=3 align=center><font face=Angsana New size=2>" + POSHW.getHeading2().trim().replace(" ", "&nbsp; ") + "_";
            }
            t += "_";
            t += "colspan=3 align=center><font face=Angsana New size=2>" + (POSHW.getHeading3().trim()) + "_";
            t += "colspan=3 align=center><font face=Angsana New size=2>" + (POSHW.getHeading4().trim()) + "_";

            t += ("colspan=3 align=center><font face=Angsana New size=2>" + "REG ID :" + POSHW.getMacNo() + "_");
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + "***สำเนาใบเสร็จรับเงิน " + Integer.toString(ICopy) + " ***" + "_");
            t += ("colspan=3 align=center><font face=Angsana New size=2>_");
            PublicVar.P_LineCount = 0;
            for (int i = 0; i < listBean.size(); i++) {
                TSaleBean bean = (TSaleBean) listBean.get(i);
                if (!bean.getR_Void().equals("V")) {
                    ItemCnt = (int) (ItemCnt + bean.getR_Quan());
                }
            }
            if (CONFIG.getP_PrintDetailOnRecp().equals("Y")) {
                Date dateP = new Date();
                t += "colspan=2 align=left><font face=Angsana New size=2> "
                        + PPrint_DatefmtThai.format(dateP).replace("/", " / ")
                        + "</td><td align=right><font face=Angsana New size=2>"
                        + "TABLE :" + Space + tBean.getB_Table() + "_";
                t += "colspan=4 align=left><font face=Angsana New size=-2> " + "CC : " + IntFmt.format(tBean.getB_Cust())
                        + " Seat :"
                        + "_";
                t += "colspan=4 align=right><font face=Angsana New size=2> "
                        + "NAME: " + Space
                        + getLastEmployeeCheckBill(tBean.getB_Table(), RefNo) + "_";
                t += ("colspan=3 align=center><font face=Angsana New size=2>" + "------------------------------------------------------------" + "_");
                for (int i = 0; i < listBean.size(); i++) {
                    TSaleBean bean = (TSaleBean) listBean.get(i);
                    if (bean.getR_Void().equals("V")) {
                        t += ("colspan=3 align=left><font face=Angsana New size=2>" + "VOID..." + "User :" + bean.getR_VoidUser() + "_");
                        if (CONFIG.getP_CodePrn().equals("Y")) {
                            t += ("colspan=3 align=left><font face=Angsana New size=2>" + bean.getR_PName() + "_");
                            t += "align=left width=-90%><font face=Angsana New size=2>"
                                    + IntFmt.format(bean.getR_Quan())
                                    + "</td><td align=left width=-30%><font face=Angsana New size=2>"
                                    + subStringText(bean.getR_PName(), 20)
                                    + "</td><td align=right width=50><font face=Angsana New size=2>"
                                    + IntFmt.format(bean.getR_Total()) + "_";
                        } else {
                            t += "align=left width=-90%><font face=Angsana New size=2>"
                                    + IntFmt.format(bean.getR_Quan())
                                    + "</td><td align=left width=-30%><font face=Angsana New size=2>"
                                    + subStringText(bean.getR_PName(), 20)
                                    + "</td><td align=right width=50><font face=Angsana New size=2>"
                                    + IntFmt.format(bean.getR_Total()) + "_";
                        }
                    } else {
                        if (bean.getR_PrAmt() == 0) {
                            if (CONFIG.getP_CodePrn().equals("Y")) {
                                t += ("colspan=3 align=left><font face=Angsana New size=2>" + bean.getR_PName() + "_");
                                t += "align=left width=-90%><font face=Angsana New size=2>"
                                        + IntFmt.format(bean.getR_Quan())
                                        + "</td><td align=left width=-30%><font face=Angsana New size=2>"
                                        + subStringText(bean.getR_PName(), 20)
                                        + "</td><td align=right width=50><font face=Angsana New size=2>"
                                        + IntFmt.format(bean.getR_Total()) + "_";
                            } else {
                                t += "align=left width=-90%><font face=Angsana New size=2>"
                                        + IntFmt.format(bean.getR_Quan())
                                        + "</td><td align=left width=-30%><font face=Angsana New size=2>"
                                        + subStringText(bean.getR_PName(), 20)
                                        + "</td><td align=right width=50><font face=Angsana New size=2>"
                                        + IntFmt.format(bean.getR_Total()) + "_";
                            }
                        } else {
                            if (CONFIG.getP_CodePrn().equals("Y")) {
                                t += ("colspan=3 align=left><font face=Angsana New size=2>" + bean.getR_PName() + "_");
                                t += "align=left width=-90%><font face=Angsana New size=2>"
                                        + IntFmt.format(bean.getR_Quan())
                                        + "</td><td align=left width=-30%><font face=Angsana New size=2>"
                                        + subStringText(bean.getR_PName(), 20)
                                        + "</td><td align=right width=50><font face=Angsana New size=2>"
                                        + IntFmt.format(bean.getR_Total()) + "_";
                            } else {
                                if (bean.getR_PName().length() > 20) {
                                }
                                t += "align=left width=-90%><font face=Angsana New size=2>"
                                        + IntFmt.format(bean.getR_Quan())
                                        + "</td><td align=left width=-30%><font face=Angsana New size=3>"
                                        + subStringText(bean.getR_PName(), 20)
                                        + "</td><td align=right width=50><font face=Angsana New size=2>"
                                        + IntFmt.format(bean.getR_Total()) + "_";
                            }
                            if (bean.getR_PrType().equals("-P")) {
                                if (bean.getR_PrAmt() > 0) {
                                    t += ("colspan=3 align=left><font face=Angsana New size=2>" + "**Promotion  " + bean.getR_PrCode() + "_");
                                }
                                if (bean.getR_PrAmt2() > 0) {
                                    t += ("colspan=3 align=left><font face=Angsana New size=2>" + "   **Promotion  " + bean.getR_PrCode2() + " " + pUtility.DataFull(pUtility.SeekPromotionName(bean.getR_PrCode2()), 20) + "_");
                                }
                            }
                            if (bean.getR_PrType().equals("-I")) {
                                if (bean.getR_PrDisc() != 0) {
                                    t += ("colspan=3 alin=left><font face=Angsana New size=2>" + "   **Item-Discount " + bean.getR_PrCode() + " " + pUtility.DataFull(DecFmt.format(bean.getR_PrDisc() - bean.getR_Redule()), QtyLength) + "%" + "_");
                                }
                                if (bean.getR_PrAmt2() > 0) {
                                    t += ("colspan=3 align=left><font face=Angsana New size=2>" + "   **Promotion  " + bean.getR_PrCode2() + " " + pUtility.DataFull(DecFmt.format(bean.getR_PrAmt()), AmtLength) + "_");
                                }
                            }
                        }
                    }
                }
            } else {
                if (POSHW.getHeading1().length() >= 18) {
                    String[] strs = POSHW.getHeading1().trim().replace(" ", Space).split("_");
                    for (String data : strs) {
                        t += "colspan=3 align=center><font face=Angsana New size=3>" + data + "_";
                    }
                } else {
                    t += "colspan=3 align=center><font face=Angsana New size=3>" + POSHW.getHeading1().trim().replace(" ", "&nbsp; ") + "_";
                }
                if (POSHW.getHeading2().length() >= 18) {
                    String[] strs = POSHW.getHeading2().trim().replace(" ", Space).split("_");
                    for (String data : strs) {
                        t += "colspan=3 align=center><font face=Angsana New size=2>" + data + "_";
                    }
                } else {
                    t += "colspan=3 align=center><font face=Angsana New size=2>" + POSHW.getHeading2().trim().replace(" ", "&nbsp; ") + "_";
                }
                t += "_";
                t += "colspan=3 align=center><font face=Angsana New size=2>" + (POSHW.getHeading3().trim()) + "_";
                t += "colspan=3 align=center><font face=Angsana New size=2>" + (POSHW.getHeading4().trim()) + "_";
                Date dateP = new Date();
                t += ("colspan=3 align=left><font face=Angsana New size=2>" + PPrint_DatefmtThai.format(dateP).replace("/", " / ") + "TABLE  " + pUtility.DataFullR(tBean.getB_Table(), 5) + "_");
                t += ("colspan=3 align=center><font face=Angsana New size=2>" + "------------------------------------------------------------");
                t += ("colspan=2 align=left><font face=Angsana New size=2>" + "     อาหารและเครื่องดื่ม " + "</td></font><td align=right><font face=Angsana New size=2>-" + pUtility.DataFull(DecFmt.format(tBean.getB_Total()), AmtLength) + "_");
            }

            t += ("colspan=3 align=center><font face=Angsana New size=2>" + "------------------------------------------------------------" + "_");
            t += ("colspan=2 align=left><font face=Angsana New size=2>" + "Sub-TOTAL :" + ItemCnt + " Item" + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(tBean.getB_Total()) + "_");
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + "------------------------------------------------------------" + "_");
            if (tBean.getB_ProDiscAmt() > 0) {
                t += ("colspan=2 align=right><font face=Angsana New size=2>" + "ลด Promotion" + "</td></font><td align=right><font face=Angsana New size=2>-" + Space + DecFmt.format(tBean.getB_ProDiscAmt()) + "_");
            }
            if (tBean.getB_SpaDiscAmt() > 0) {
                t += ("colspan=2 align=left><font face=Angsana New size=2>" + "Special Disc" + DecFmt.format(tBean.getB_SpaDiscAmt()) + "-_");
            }
            if (tBean.getB_MemDiscAmt() > 0) {
                t += ("colspan=2 align=left><font face=Angsana New size=2>" + "ลดสมาชิก..." + tBean.getB_MemDisc() + "</td><td align=right><font face=Angsana New size=2>- " + DecFmt.format(tBean.getB_MemDiscAmt()) + "-_");
            }
            if (tBean.getB_FastDiscAmt() > 0) {
                t += ("colspan=2 align=left><font face=Angsana New size=2>" + "{\n"
                        + "ลดเทศกาล..." + tBean.getB_FastDisc() + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(tBean.getB_FastDiscAmt()) + "-_");
            }
            if (tBean.getB_EmpDiscAmt() > 0) {
                t += ("colspan=2 align=left><font face=Angsana New size=2>" + "ลดพนักงาน..." + tBean.getB_EmpDisc() + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(tBean.getB_EmpDiscAmt()) + "-_");
            }
            if (tBean.getB_TrainDiscAmt() > 0) {
                t += ("colspan=2 align=left><font face=Angsana New size=2>" + "ลด Trainnee..." + tBean.getB_TrainDisc() + DecFmt.format(tBean.getB_TrainDiscAmt()) + "-_");
            }
            if (tBean.getB_SubDiscAmt() > 0) {
                t += ("colspan=2 align=left><font face=Angsana New size=2>" + "ลดคูปอง(Cupon)" + tBean.getB_SubDisc() + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(tBean.getB_SubDiscAmt()) + "-_");
            }
            if (tBean.getB_SubDiscBath() > 0) {
                t += ("colspan=2 align=left><font face=Angsana New size=2>" + "ลด(บาท)..." + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(tBean.getB_SubDiscBath()) + "-_");
            }
            if (tBean.getB_ItemDiscAmt() > 0) {
                t += ("colspan=2 align=left><font face=Angsana New size=2>" + "ลดตามรายการ(Item)" + "</td></font><td align=right><font face=Angsana New size=2>" + DecFmt.format(tBean.getB_ItemDiscAmt()) + "-_");
            }
            if (tBean.getB_CuponDiscAmt() > 0) {
                mysqlConnect.open(this.getClass());
                try {
                    Statement stmt = mysqlConnect.getConnection().createStatement();
                    String CheckCoupon = "select * from t_cupon where (r_refno='" + RefNo + "') and (terminal='" + PublicVar.MACNO + "') ";
                    ResultSet rs = stmt.executeQuery(CheckCoupon);
                    while (rs.next()) {
                        t += ("colspan=3 align=left><font face=Angsana New size=2>" + Space + pUtility.DataFullR(pUtility.SeekCuponName(rs.getString("cucode")), 20) + pUtility.DataFull(DecFmt.format(rs.getDouble("cuamt")), AmtLength) + "_");
                    }
                    rs.close();
                    stmt.close();
                } catch (SQLException e) {
                    AppLogUtil.log(PPrint.class, "error", e);
                } finally {
                    mysqlConnect.closeConnection(this.getClass());
                }
            }
            if (tBean.getB_ServiceAmt() > 0) {
                t += ("colspan=2 align=left><font face=Angsana New size=2>" + Space + "Service :" + IntFmt.format(CONFIG.getP_Service()) + " %" + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(tBean.getB_ServiceAmt()) + "+_");
            }
            if (tBean.getB_Earnest() > 0) {
                t += ("colspan=2 align=left><font face=Angsana New size=2>" + "หักคืนเงินมัดจำ" + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(tBean.getB_Earnest()) + "_");
            }
            if (CONFIG.getP_VatType().equals("I")) {
                //Print_Str(" ");
                t += ("colspan=2 align=left><font face=Angsana New size=3>" + "Net-TOTAL " + "</td><td align=right><font face=Angsana New size=3>" + DecFmt.format(tBean.getB_NetTotal()) + "_");
            } else {
                t += ("colspan=2 align=left><font face=Angsana New size=2>" + Space + "Net-Amount " + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format((tBean.getB_NetVat() + tBean.getB_NetNonVat())) + "_");
                t += ("colspan=2 align=left><font face=Angsana New size=3>" + "Net-Total " + "</td><td align=right><font face=Angsana New size=3>" + DecFmt.format(NumberUtil.UP_DOWN_NATURAL_BAHT(tBean.getB_NetTotal())) + "_");
                t += ("colspan=2 align=left><font face=Angsana New size=2>" + "Round..." + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(tBean.getB_NetDiff()) + "_");

                t += ("colspan=3 align=center><font face=Angsana New size=2>" + "VAT INCLUDED" + "_");
            }
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + "------------------------------------------------------------" + "_");
            if (tBean.getB_GiftVoucher() > 0) {
                t += ("colspan=2 align=left><font face=Angsana New size=2>" + Space + "บัตรกำนัล...." + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(tBean.getB_GiftVoucher()) + "_");
                mysqlConnect.open(this.getClass());
                try {
                    Statement stmt = mysqlConnect.getConnection().createStatement();
                    String CheckGift = "select * from t_gift where (refno='" + RefNo + "')";
                    ResultSet rs = stmt.executeQuery(CheckGift);
                    while (rs.next()) {
                        t += ("colspan=3 align=left><font face=Angsana New size=2>" + TAB + pUtility.DataFull(rs.getString("giftbarcode") + rs.getString("giftno"), 25) + "@" + pUtility.DataFull(DecFmt.format(rs.getDouble("giftamt")), AmtLength) + "_");
                    }
                    rs.close();
                    stmt.close();
                } catch (SQLException e) {
                    AppLogUtil.log(PPrint.class, "error", e);
                } finally {
                    mysqlConnect.closeConnection(this.getClass());
                }
            }
            if (tBean.getB_PayAmt() > 0) {
                t += ("colspan=3 align=left><font face=Angsana New size=2>" + "Cash : " + pUtility.DataFull(DecFmt.format(tBean.getB_PayAmt()), AmtLength) + Space + "Change : " + pUtility.DataFull(DecFmt.format(tBean.getB_Ton()), AmtLength) + "_");
            }
            if (tBean.getB_CrAmt1() > 0) {
                //get credit name
                String crName = "";
                mysqlConnect.open(this.getClass());
                try {
                    String sql = "select CrName from creditfile where crcode='" + tBean.getB_CrCode1() + "' limit 1";
                    Statement stmt = mysqlConnect.getConnection().createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        crName = ThaiUtil.ASCII2Unicode(rs.getString("CrName"));
                    }

                    rs.close();
                    stmt.close();
                } catch (SQLException e) {
                    AppLogUtil.log(PPrint.class, "error", e);
                } finally {
                    mysqlConnect.closeConnection(this.getClass());
                }

                t += ("colspan=3 align=left><font face=Angsana New size=2>" + tBean.getB_CrCode1() + Space + crName + "_");
                t += ("colspan=3 align=left><font face=Angsana New size=2>" + "XXXXXXXXXXX" + pUtility.Addzero(tBean.getB_CardNo1(), 16).substring(12, 16) + TAB + tBean.getB_AppCode1() + "_");
                t += ("colspan=2 align=left><font face=Angsana New size=2>" + "Credit Payment" + "</td><td align=right><font face=Angsana New size=2>" + pUtility.DataFull(DecFmt.format(tBean.getB_CrAmt1()), AmtLength) + "_");
            }
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + "------------------------------------------------------------" + "_");
            t += ("colspan=3 align=left><font face=Angsana New size=2>" + "Receipt No: " + RefNo + "_");
            t += ("colspan=3 align=left><font face=Angsana New size=2>" + "คอม: " + Space + POSHW.getMacNo() + "_");
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + "_");

            if (!CONFIG.getP_PrintRecpMessage().equals("")) {
                t += ("colspan=3 align=center><font face=Angsana New size=2>" + CONFIG.getP_PrintRecpMessage() + "_");
            }

            if (!POSHW.getFootting3().equals("")) {
                t += ("colspan=3 align=center><font face=Angsana New size=2>" + POSHW.getFootting3() + "_");
            }
            if (!POSHW.getFootting2().equals("")) {
                t += ("colspan=3 align=center><font face=Angsana New size=2>" + POSHW.getFootting2() + "_");
            }
            if (!POSHW.getFootting1().equals("")) {
                t += ("colspan=3 align=center><font face=Angsana New size=2>" + POSHW.getFootting1() + "_");
            }
            PublicVar.languagePrint = "TH";
            t = changeLanguage(t);
            PrintDriver pd = new PrintDriver();
            String[] strs = t.split("_");
            for (String data1 : strs) {
                pd.addTextIFont(data1);
            }
            pd.printHTML();
        }

    }

    public void printBillRefund(String _RefNo) {
        if (PublicVar.printdriver == true) {
            printBillRefundDriver(_RefNo);
        } else {
            List<TSaleBean> listBean = billControl.getAllTSale(_RefNo);
            int QtyLength = 5;
            int AmtLength = 10;
            int SubLength = 20;
            int SubLength2 = 13;
            int ItemCnt = 0;
            String VatStr;

            BillNoBean bBean = billControl.getData(_RefNo);
            if (!POSHW.getPRNPort().equals("NONE")) {
                if (openPrint(POSHW.getPRNPort())) {
                    initPrinter();
                    print(POSHW.getHeading1());
                    print(POSHW.getHeading2());
                    print(POSHW.getHeading3());
                    print(POSHW.getHeading4());

                    print("REG ID :" + POSHW.getMacNo());
                    print("      ***บิลยกเลิกรายการขาย (Refund) ***");
                    print("Void User : " + PublicVar._User);
                    print("Void Date/Time : " + pUtility.CurDate());
                    selectStye(3);
                    print("อ้างถึงใบเสร็จรับเงินเลขที่");
                    print(_RefNo);
                    selectStye(1);
                    print("");
                    PublicVar.P_LineCount = 0;
                    for (int i = 0; i < listBean.size(); i++) {
                        TSaleBean bean = (TSaleBean) listBean.get(i);
                        if (!bean.getR_Void().equals("V")) {
                            ItemCnt = (int) (ItemCnt + bean.getR_Quan());
                        }
                    }
                    if (CONFIG.getP_PrintDetailOnRecp().equals("Y")) {
                        Date dateP = new Date();
                        print(PPrint_DatefmtThai.format(dateP).replace("/", " / ") + " " + "Cashier:" + PublicVar._User + " Mac:" + PublicVar.MACNO);
                        print("----------------------------------------");
                        for (int i = 0; i < listBean.size(); i++) {
                            TSaleBean bean = (TSaleBean) listBean.get(i);
                            if (bean.getR_Void().equals("V")) {
                                selectStye(12);
                                print("VOID..." + "User :" + bean.getR_VoidUser());
                                if (bean.getR_Vat().equals("V")) {
                                    VatStr = "-";
                                } else {
                                    VatStr = "*";
                                }
                                if (CONFIG.getP_CodePrn().equals("Y")) {
                                    print(bean.getR_PName());
                                    print(bean.getR_Normal() + VatStr + pUtility.DataFullR(bean.getR_PluCode(), 20) + "  " + pUtility.DataFull(IntFmt.format(-1 * bean.getR_Quan()), QtyLength) + pUtility.DataFull(DecFmt.format(-1 * bean.getR_Total()), AmtLength) + bean.getR_ETD());
                                } else {
                                    print(bean.getR_Normal() + VatStr + pUtility.DataFullR(bean.getR_PName(), 20) + "  " + pUtility.DataFull(IntFmt.format(-1 * bean.getR_Quan()), QtyLength) + pUtility.DataFull(DecFmt.format(-1 * bean.getR_Total()), AmtLength) + bean.getR_ETD());
                                }
                                selectStye(13);
                            } else {
                                if (bean.getR_Vat().equals("V")) {
                                    VatStr = "-";
                                } else {
                                    VatStr = "*";
                                }
                                if (bean.getR_PrAmt() == 0) {
                                    if (CONFIG.getP_CodePrn().equals("Y")) {
                                        print(bean.getR_PName());
                                        print(bean.getR_Normal() + VatStr + pUtility.DataFullR(bean.getR_PluCode(), 20) + "  " + pUtility.DataFull(IntFmt.format(bean.getR_Quan()), QtyLength) + pUtility.DataFull(DecFmt.format(bean.getR_Total()), AmtLength) + bean.getR_ETD());
                                    } else {
                                        print(bean.getR_Normal() + VatStr + pUtility.DataFullR(bean.getR_PName(), 20) + "  " + pUtility.DataFull(IntFmt.format(bean.getR_Quan()), QtyLength) + pUtility.DataFull(DecFmt.format(bean.getR_Total()), AmtLength) + bean.getR_ETD());
                                    }
                                } else {
                                    if (CONFIG.getP_CodePrn().equals("Y")) {
                                        print(bean.getR_PName());
                                        print(bean.getR_Normal() + VatStr + pUtility.DataFullR(bean.getR_PluCode(), 20) + "  " + pUtility.DataFull(IntFmt.format(bean.getR_Quan()), QtyLength) + pUtility.DataFull(DecFmt.format(bean.getR_Total()), AmtLength) + bean.getR_ETD());
                                    } else {
                                        print(bean.getR_Normal() + VatStr + pUtility.DataFullR(bean.getR_PName(), 20) + "  " + pUtility.DataFull(IntFmt.format(bean.getR_Quan()), QtyLength) + pUtility.DataFull(DecFmt.format(bean.getR_Total()), AmtLength) + bean.getR_ETD());
                                    }
                                    if (bean.getR_PrType().equals("-P")) {
                                        if (bean.getR_PrAmt() > 0) {
                                            print("   **Promotion  " + bean.getR_PrCode() + " " + pUtility.DataFull(pUtility.SeekPromotionName(bean.getR_PrCode()), 20));
                                        }
                                        if (bean.getR_PrAmt2() > 0) {
                                            print("   **Promotion  " + bean.getR_PrCode2() + " " + pUtility.DataFull(pUtility.SeekPromotionName(bean.getR_PrCode2()), 20));
                                        }
                                    }
                                    if (bean.getR_PrType().equals("-I")) {
                                        if (bean.getR_PrDisc() != 0) {
                                            print("   **Item-Discount " + bean.getR_PrCode() + " " + pUtility.DataFull(DecFmt.format(bean.getR_PrDisc() - bean.getR_Redule()), QtyLength) + "%");
                                        }
                                        if (bean.getR_PrAmt2() > 0) {
                                            print("   **Promotion  " + bean.getR_PrCode2() + " " + pUtility.DataFull(DecFmt.format(bean.getR_PrAmt()), AmtLength));
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Date dateP = new Date();
                        print(PPrint_DatefmtThai.format(dateP).replace("/", " / ") + " " + "Cashier:" + PublicVar._User + " Mac:" + PublicVar.MACNO);
                        print("----------------------------------------");
                        print("     อาหารและเครื่องดื่ม " + pUtility.DataFull(DecFmt.format(bBean.getB_Total()), AmtLength));
                    }
                    print("----------------------------------------");
                    print("Sub-TOTAL   " + "(Item" + pUtility.DataFull(IntFmt.format(ItemCnt), QtyLength) + " )     " + pUtility.DataFull(DecFmt.format(bBean.getB_Total()), AmtLength));
                    if (bBean.getB_ProDiscAmt() > 0) {
                        print("    " + pUtility.DataFullR("ลด Promotion     ", SubLength) + pUtility.DataFull(DecFmt.format(bBean.getB_ProDiscAmt()), AmtLength));
                    }
                    if (bBean.getB_SpaDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("Special Disc     ", SubLength) + pUtility.DataFull(DecFmt.format(bBean.getB_SpaDiscAmt()), AmtLength));
                    }
                    if (bBean.getB_MemDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("ลดสมาชิก..........", SubLength2) + pUtility.DataFull(bBean.getB_MemDisc(), 8) + pUtility.DataFull(DecFmt.format(bBean.getB_MemDiscAmt()), AmtLength));
                    }
                    if (bBean.getB_FastDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("ลดเทศกาล.........", SubLength2) + pUtility.DataFull(bBean.getB_FastDisc(), 8) + pUtility.DataFull(DecFmt.format(bBean.getB_FastDiscAmt()), AmtLength));
                    }
                    if (bBean.getB_EmpDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("ลดพนักงาน.........", SubLength2) + pUtility.DataFull(bBean.getB_EmpDisc(), 8) + pUtility.DataFull(DecFmt.format(bBean.getB_EmpDiscAmt()), AmtLength));
                    }
                    if (bBean.getB_TrainDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("ลด Trainnee......", SubLength2) + pUtility.DataFull(bBean.getB_TrainDisc(), 8) + pUtility.DataFull(DecFmt.format(bBean.getB_TrainDiscAmt()), AmtLength));
                    }
                    if (bBean.getB_SubDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("ลดคูปอง...........", SubLength2) + pUtility.DataFull(bBean.getB_SubDisc(), 8) + pUtility.DataFull(DecFmt.format(bBean.getB_SubDiscAmt()), AmtLength));
                    }
                    if (bBean.getB_SubDiscBath() > 0) {
                        print("     " + pUtility.DataFullR("ลด(บาท)..........", SubLength) + pUtility.DataFull(DecFmt.format(bBean.getB_SubDiscBath()), AmtLength));
                    }
                    if (bBean.getB_ItemDiscAmt() > 0) {
                        print("     " + pUtility.DataFullR("ลดตามรายการ(Item)", SubLength) + pUtility.DataFull(DecFmt.format(bBean.getB_ItemDiscAmt()), AmtLength));
                    }
                    if (bBean.getB_CuponDiscAmt() > 0) {
                        
                        mysqlConnect.open(this.getClass());
                        try {
                            Statement stmt = mysqlConnect.getConnection().createStatement();
                            String CheckCoupon = "select * from t_cupon where (r_refno='" + _RefNo + "') and (terminal='" + PublicVar.MACNO + "') ";
                            ResultSet rs = stmt.executeQuery(CheckCoupon);
                            while (rs.next()) {
                                print("     " + pUtility.DataFullR(pUtility.SeekCuponName(rs.getString("cucode")), 20) + pUtility.DataFull(DecFmt.format(rs.getDouble("cuamt")), AmtLength));
                                String SMS_Code = rs.getString("sms_code");
                                if ((SMS_Code != null) & (!SMS_Code.equals(""))) {
                                    print("     " + "SMS CODE " + SMS_Code);
                                }
                            }
                            rs.close();
                            stmt.close();
                        } catch (SQLException e) {
                            AppLogUtil.log(PPrint.class, "error", e);
                        } finally {
                            mysqlConnect.closeConnection(this.getClass());
                        }
                    }
                    if (bBean.getB_ServiceAmt() > 0) {
                        print("     " + pUtility.DataFullR("ค่าบริการ (Service)     ", 23) + pUtility.DataFull(DecFmt.format(bBean.getB_ServiceAmt()), 9));
                    }

                    if (bBean.getB_Earnest() > 0) {
                        print("     " + pUtility.DataFullR("หักคืนเงินมัดจำ           ", 23) + pUtility.DataFull(DecFmt.format(bBean.getB_Earnest()), 9));
                    }
                    if (CONFIG.getP_VatType().equals("I")) {
                        //Print_Str(" ");
                        selectStye(3);
                        print("TOTAL " + pUtility.DataFull(DecFmt.format(bBean.getB_NetTotal()), AmtLength));
                        selectStye(1);
                        if (CONFIG.getP_VatPrn().equals("Y")) {
                            print(pUtility.DataFull("              Vat...", 43) + pUtility.DataFull(DecFmt.format(bBean.getB_Vat()), AmtLength));
                        }
                    } else {
                        print(pUtility.DataFull("      Net-Amount ", SubLength) + pUtility.DataFull(DecFmt.format(bBean.getB_NetVat() + bBean.getB_NetNonVat()), AmtLength));
                        print(pUtility.DataFull("      Vat....... ", SubLength) + pUtility.DataFull(DecFmt.format(CONFIG.getP_Vat()), QtyLength) + "%" + pUtility.DataFull(DecFmt.format(bBean.getB_Vat()), AmtLength));
                        print("VAT INCLUDED");
                    }
                    if (bBean.getB_GiftVoucher() > 0) {
                        print("     " + pUtility.DataFullR("บัตรกำนัล..............", SubLength) + pUtility.DataFull(DecFmt.format(bBean.getB_GiftVoucher()), AmtLength));

                        
                        mysqlConnect.open(this.getClass());
                        try {
                            Statement stmt = mysqlConnect.getConnection().createStatement();
                            String CheckGift = "select * from t_gift where (refno='" + _RefNo + "')";
                            ResultSet rs = stmt.executeQuery(CheckGift);
                            while (rs.next()) {
                                print("   " + pUtility.DataFull(rs.getString("giftbarcode"), 25) + "@" + pUtility.DataFull(DecFmt.format(rs.getDouble("giftamt")), AmtLength));
                            }
                            rs.close();
                            stmt.close();
                        } catch (SQLException e) {
                            AppLogUtil.log(PPrint.class, "error", e);
                        } finally {
                            mysqlConnect.closeConnection(this.getClass());
                        }
                    }
                    if (bBean.getB_PayAmt() > 0) {
                        print("เงินสด  : " + pUtility.DataFull(DecFmt.format(bBean.getB_PayAmt()), AmtLength) + "  ทอน : " + pUtility.DataFull(DecFmt.format(bBean.getB_Ton()), AmtLength));
                    }

                    print("----------------------------------------");
                    selectStye(5);
                    print("TABLE  " + pUtility.DataFull(bBean.getB_Table(), 5) + "   จำนวนลูกค้า : " + IntFmt.format(bBean.getB_Cust()) + " คน");
                    selectStye(1);

                    print(" ");
                    if (!CONFIG.getP_PrintRecpMessage().equals("")) {
                        print(CONFIG.getP_PrintRecpMessage());
                    }

                    if (!POSHW.getFootting1().equals("")) {
                        print(POSHW.getFootting1());
                    }
                    if (!POSHW.getFootting2().equals("")) {
                        print(POSHW.getFootting2());
                    }
                    print(" ");
                    selectStye(1);
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    print("");
                    EJPrint = false;
                    cutPaper();
                    closePrint();
                }
            }
        }
    }

    private void printBillRefundDriver(String _RefNo) {
        List<TSaleBean> listBean = billControl.getAllTSale(_RefNo);

        int QtyLength = 5;
        int AmtLength = 10;
        int SubLength = 20;
        int SubLength2 = 13;
        int ItemCnt = 0;
        double totalDiscount;
        String t = "";

        BillNoBean bBean = billControl.getData(_RefNo);
        if (POSHW.getHeading1().trim().length() >= 18) {
            String[] strs = POSHW.getHeading1().trim().replace(" ", Space).split("_");
            for (String data : strs) {
                t += ("colspan=3 align=center><font face=Angsana New size=3>" + data + "_");
            }
        } else {
            t += ("colspan=3 align=center><font face=Angsana New size=3>" + POSHW.getHeading1().trim().replace(" ", "&nbsp; ") + "_");
        }
        if (POSHW.getHeading2().trim().length() >= 18) {
            String[] strs = POSHW.getHeading2().trim().replace(" ", Space).split("_");
            for (String data : strs) {
                t += ("colspan=3 align=center><font face=Angsana New size=3>" + data + "_");
            }
        } else {
            t += ("colspan=3 align=center><font face=Angsana New size=3>" + POSHW.getHeading2().trim().replace(" ", "&nbsp; ") + "_");
        }

        t += ("colspan=3 align=center><font face=Angsana New size=2>" + POSHW.getHeading3().trim() + "_");
        t += ("colspan=3 align=center><font face=Angsana New size=2>" + POSHW.getHeading4().trim() + "_");

        t += ("colspan=3 align=center><font face=Angsana New size=2>" + "REG ID :" + POSHW.getMacNo() + "_");
        t += ("colspan=3 align=center><font face=Angsana New size=2>" + "------------------------------------------------------------" + "_");
        t += ("colspan=3 align=center><font face=Angsana New size=2>" + "***" + Space + "บิลยกเลิกรายการขาย" + "***" + "_");
        t += ("colspan=3 align=center><font face=Angsana New size=2>" + "***" + Space + "(Refund)***" + "_");
        t += ("colspan=3 align=left><font face=Angsana New size=2>" + "Void User : " + Space + ThaiUtil.ASCII2Unicode(bBean.getB_VoidUser()) + "_");
        t += ("colspan=3 align=left><font face=Angsana New size=2>" + "Void Date/Time : " + pUtility.CurDate() + "_");
        t += ("colspan=3 align=left><font face=Angsana New size=2>" + "อ้างถึงใบเสร็จรับเงินเลขที่_" + Space + _RefNo + "_");
        t += (">_");
        PublicVar.P_LineCount = 0;

        for (int i = 0; i < listBean.size(); i++) {
            TSaleBean bean = (TSaleBean) listBean.get(i);
            if (!bean.getR_Void().equals("V")) {
                ItemCnt = (int) (ItemCnt + bean.getR_Quan());
            }
        }
        if (CONFIG.getP_PrintDetailOnRecp().equals("Y")) {
            Date dateP = new Date();
            t += ("colspan=3 align=left><font face=Angsana New size=2>" + PPrint_DatefmtThai.format(dateP).replace("/", " / ") + " " + "Cashier:" + PublicVar._User + " Mac:" + PublicVar.MACNO + "_");
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + "------------------------------------------------------------" + "_");
            for (int i = 0; i < listBean.size(); i++) {
                TSaleBean bean = (TSaleBean) listBean.get(i);
                if (bean.getR_Void().equals("V")) {
                    t += ("VOID..." + "User :" + bean.getR_VoidUser() + "_");
                    if (CONFIG.getP_CodePrn().equals("Y")) {
                        t += (bean.getR_PName() + "_");
                        t += ("align=left width=-90%><font face=Angsana New size=2>"
                                + DecFmt.format(bean.getR_Quan())
                                + "</td><td align=left width=-30%><font face=Angsana New size=2>"
                                + subStringText(bean.getR_PName(), 20)
                                + "</td><td align=right width=50><font face=Angsana New size=2>"
                                + DecFmt.format(bean.getR_Total()) + "_");
                    } else {
                        t += ("align=left width=-90%><font face=Angsana New size=2>"
                                + DecFmt.format(bean.getR_Quan())
                                + "</td><td align=left width=-30%><font face=Angsana New size=2>"
                                + subStringText(bean.getR_PName(), 20)
                                + "</td><td align=right width=50><font face=Angsana New size=2>"
                                + DecFmt.format(bean.getR_Total()) + "_");
                    }
                } else {
                    if (bean.getR_PrAmt() == 0) {
                        if (CONFIG.getP_CodePrn().equals("Y")) {
                            t += (bean.getR_PName() + "_");
                            t += ("align=left width=-90%><font face=Angsana New size=2>"
                                    + DecFmt.format(bean.getR_Quan())
                                    + "</td><td align=left width=-30%><font face=Angsana New size=2>"
                                    + subStringText(bean.getR_PName(), 20)
                                    + "</td><td align=right width=50><font face=Angsana New size=2>"
                                    + DecFmt.format(bean.getR_Total()) + "_");
                        } else {
                            t += ("align=left width=-90%><font face=Angsana New size=2>"
                                    + DecFmt.format(bean.getR_Quan())
                                    + "</td><td align=left width=-30%><font face=Angsana New size=2>"
                                    + subStringText(bean.getR_PName(), 20)
                                    + "</td><td align=right width=50><font face=Angsana New size=2>"
                                    + DecFmt.format(bean.getR_Total()) + "_");
                        }
                    } else {
                        if (CONFIG.getP_CodePrn().equals("Y")) {
                            t += (bean.getR_PName() + "_");
                            t += ("align=left width=-90%><font face=Angsana New size=2>"
                                    + DecFmt.format(bean.getR_Quan())
                                    + "</td><td align=left width=-30%><font face=Angsana New size=2>"
                                    + subStringText(bean.getR_PName(), 20)
                                    + "</td><td align=right width=50><font face=Angsana New size=2>"
                                    + DecFmt.format(bean.getR_Total()) + "_");
                        } else {
                            t += ("align=left width=-90%><font face=Angsana New size=2>"
                                    + DecFmt.format(bean.getR_Quan())
                                    + "</td><td align=left width=-30%><font face=Angsana New size=2>"
                                    + subStringText(bean.getR_PName(), 20)
                                    + "</td><td align=right width=50><font face=Angsana New size=2>"
                                    + DecFmt.format(bean.getR_Total()) + "_");
                        }
                        if (bean.getR_PrType().equals("-P")) {
                            if (bean.getR_PrAmt() > 0) {
                                t += ("colspan=3 align=center><font face=Angsana New size=2>" + "   **Promotion  " + bean.getR_PrCode() + " " + pUtility.DataFull(pUtility.SeekPromotionName(bean.getR_PrCode()), 20) + "_");
                            }
                            if (bean.getR_PrAmt2() > 0) {
                                t += ("colspan=3 align=center><font face=Angsana New size=2>" + "   **Promotion  " + bean.getR_PrCode2() + " " + pUtility.DataFull(pUtility.SeekPromotionName(bean.getR_PrCode2()), 20) + "_");
                            }
                        }
                        if (bean.getR_PrType().equals("-I")) {
                            if (bean.getR_PrDisc() != 0) {
                                t += ("colspan=3 align=center><font face=Angsana New size=2>" + "   **Item-Discount " + bean.getR_PrCode() + " " + pUtility.DataFull(DecFmt.format(bean.getR_PrDisc() - bean.getR_Redule()), QtyLength) + "%" + "_");
                            }
                            if (bean.getR_PrAmt2() > 0) {
                                t += ("colspan=3 align=center><font face=Angsana New size=2>" + "   **Promotion  " + bean.getR_PrCode2() + " " + pUtility.DataFull(DecFmt.format(bean.getR_PrAmt()), AmtLength) + "_");
                            }
                        }
                    }
                }
            }
        } else {
            Date dateP = new Date();
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + PPrint_DatefmtThai.format(dateP).replace("/", " / ") + " " + "Cashier:" + PublicVar._User + " Mac:" + PublicVar.MACNO + "_");
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + "------------------------------------------------------------" + "_");
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + "     อาหารและเครื่องดื่ม " + pUtility.DataFull(DecFmt.format(bBean.getB_Total()), AmtLength) + "_");
        }
        if (bBean.getB_ProDiscAmt() > 0) {
            t += ("colspan=2 align=center><font face=Angsana New size=2>" + pUtility.DataFullR("ลด Promotion     ", SubLength) + pUtility.DataFull(DecFmt.format(bBean.getB_ProDiscAmt()), AmtLength) + "_");
        }
        if (bBean.getB_SpaDiscAmt() > 0) {
            t += ("colspan=2 align=center><font face=Angsana New size=2>" + pUtility.DataFullR("Special Disc     ", SubLength) + pUtility.DataFull(DecFmt.format(bBean.getB_SpaDiscAmt()), AmtLength) + "_");
        }
        if (bBean.getB_MemDiscAmt() > 0) {
            t += ("colspan=2 align=center><font face=Angsana New size=2>" + pUtility.DataFullR("ลดสมาชิก..........", SubLength2) + pUtility.DataFull(bBean.getB_MemDisc(), 8) + pUtility.DataFull(DecFmt.format(bBean.getB_MemDiscAmt()), AmtLength) + "_");
        }
        if (bBean.getB_FastDiscAmt() > 0) {
            t += ("colspan=2 align=center><font face=Angsana New size=2>" + pUtility.DataFullR("ลดเทศกาล.........", SubLength2) + pUtility.DataFull(bBean.getB_FastDisc(), 8) + pUtility.DataFull(DecFmt.format(bBean.getB_FastDiscAmt()), AmtLength) + "_");
        }
        if (bBean.getB_EmpDiscAmt() > 0) {
            t += ("colspan=2 align=center><font face=Angsana New size=2>" + pUtility.DataFullR("ลดพนักงาน.........", SubLength2) + pUtility.DataFull(bBean.getB_EmpDisc(), 8) + pUtility.DataFull(DecFmt.format(bBean.getB_EmpDiscAmt()), AmtLength) + "_");
        }
        if (bBean.getB_TrainDiscAmt() > 0) {
            t += ("colspan=2 align=center><font face=Angsana New size=2>" + pUtility.DataFullR("ลด Trainnee......", SubLength2) + pUtility.DataFull(bBean.getB_TrainDisc(), 8) + pUtility.DataFull(DecFmt.format(bBean.getB_TrainDiscAmt()), AmtLength) + "_");
        }
        if (bBean.getB_SubDiscAmt() > 0) {
            t += ("colspan=2 align=center><font face=Angsana New size=2>" + pUtility.DataFullR("ลดคูปอง...........", SubLength2) + pUtility.DataFull(bBean.getB_SubDisc(), 8) + pUtility.DataFull(DecFmt.format(bBean.getB_SubDiscAmt()), AmtLength) + "_");
        }
        if (bBean.getB_SubDiscBath() > 0) {
            t += ("colspan=2 align=center><font face=Angsana New size=2>" + pUtility.DataFullR("ลด(บาท)..........", SubLength) + pUtility.DataFull(DecFmt.format(bBean.getB_SubDiscBath()), AmtLength) + "_");
        }
        if (bBean.getB_ItemDiscAmt() > 0) {
            t += ("colspan=2 align=center><font face=Angsana New size=2>" + "ลดตามรายการ(Item)" + "</td></font><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_ItemDiscAmt()) + "_");
        }
        if (bBean.getB_SubDiscAmt() > 0) {
            t += ("colspan=2 align=left><font face=Angsana New size=-2> " + "ส่วนลดคูปอง  " + DecFmt.format(bBean.getB_SubDiscAmt())) + "_";
        }
        if (bBean.getB_CuponDiscAmt() > 0) {
            mysqlConnect.open(this.getClass());
            try {
                Statement stmt = mysqlConnect.getConnection().createStatement();
                String CheckCoupon = "select *from t_cupon where (r_refno='" + _RefNo + "') and (terminal='" + PublicVar.MACNO + "')";
                ResultSet rs = stmt.executeQuery(CheckCoupon);
                while (rs.next()) {
                    t += ("colspan=3 align=left><font face=Angsana New size=2>" + TAB + pUtility.DataFullR(pUtility.SeekCuponName(rs.getString("cucode")), 20) + pUtility.DataFull(DecFmt.format(rs.getDouble("cuamt")), AmtLength) + "_");
                }
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                AppLogUtil.log(PPrint.class, "error", e);
            } finally {
                mysqlConnect.closeConnection(this.getClass());
            }
        }
        totalDiscount = bBean.getB_ProDiscAmt() + bBean.getB_SpaDiscAmt()
                + bBean.getB_FastDiscAmt() + bBean.getB_EmpDiscAmt() + bBean.getB_TrainDiscAmt()
                + bBean.getB_SubDiscAmt() + bBean.getB_SubDiscBath() + bBean.getB_ItemDiscAmt() + bBean.getB_CuponDiscAmt();
        t += ("colspan=3 align=center><font face=Angsana New size=2>" + "------------------------------------------------------------" + "_");
        t += "colspan=2 align=left><font face=Angsana New size=2>" + ("Sub-TOTAL :" + ItemCnt + " Item" + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_Total() - totalDiscount) + "_");
        t += ("colspan=3 align=center><font face=Angsana New size=2>" + "------------------------------------------------------------" + "_");

        if (bBean.getB_ServiceAmt() > 0) {
            t += ("colspan=2 align=left><font face=Angsana New size=2>" + Space + "Service :" + DecFmt.format(CONFIG.getP_Service()) + " %" + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_ServiceAmt()) + "_");
        }

        if (bBean.getB_Earnest() > 0) {
            t += ("colspan=2 align=left><font face=Angsana New size=2>" + Space + "หักคืนเงินมัดจำ" + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_Earnest()) + "_");
        }
        if (CONFIG.getP_VatType().equals("I")) {
            t += ("colspan=2 align=left><font face=Angsana New size=3>" + "Net-TOTAL " + "</td><td align=right><font face=Angsana New size=3>" + DecFmt.format((bBean.getB_NetTotal() - bBean.getB_Vat())) + "_");
            if (CONFIG.getP_VatPrn().equals("Y")) {
                t += ("colspan=2 align=left><font face=Angsana New size=2>" + "Vat..." + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_Vat()) + "_");
            }
        } else {
            t += ("colspan=2 align=center><font face=Angsana New size=2>" + "Net-Amount " + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format((bBean.getB_NetVat() + bBean.getB_NetNonVat())) + "_");
            t += ("colspan=2 align=center><font face=Angsana New size=2>" + "Vat..." + DecFmt.format(CONFIG.getP_Vat()) + "%" + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_Vat()) + "_");
            t += ("colspan=2 align=center><font face=Angsana New size=3>" + "Net-TOTAl" + "</td><td align=right><font face=Angsana New size=3>" + DecFmt.format((bBean.getB_NetTotal())) + "_");
            t += ("colspan=2 align=center><font face=Angsana New size=2>" + "Round " + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_NetDiff()) + "_");
            t += ("colspan=2 align=center><font face=Angsana New size=2>" + "VAT INCLUDED" + "_");
        }
        if (bBean.getB_GiftVoucher() > 0) {
            t += ("colspan=2 align=center><font face=Angsana New size=2>" + Space + pUtility.DataFullR("บัตรกำนัล...", SubLength) + "</td><td align=right><font face=Angsana New size=2>" + DecFmt.format(bBean.getB_GiftVoucher()) + "_");

            mysqlConnect.open(this.getClass());
            try {
                Statement stmt = mysqlConnect.getConnection().createStatement();
                String CheckGift = "select * from t_gift where (refno='" + _RefNo + "')";
                ResultSet rs = stmt.executeQuery(CheckGift);
                while (rs.next()) {
                    t += ("colspan=3 align=center><font face=Angsana New size=2>" + TAB + rs.getString("giftbarcode") + "@" + DecFmt.format(rs.getDouble("giftamt")) + "_");
                }
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                AppLogUtil.log(PPrint.class, "error", e);
            } finally {
                mysqlConnect.closeConnection(this.getClass());
            }
        }
        t += ("colspan=3 align=center><font face=Angsana New size=2>" + "------------------------------------------------------------" + "_");
        if (bBean.getB_PayAmt() > 0) {
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + Space + "Cash  : " + Space + pUtility.DataFull(DecFmt.format(bBean.getB_PayAmt()), AmtLength) + Space + "Change : " + Space + pUtility.DataFull(DecFmt.format(bBean.getB_Ton()), AmtLength) + "_");
        }
        if (bBean.getB_CrAmt1() > 0) {
            t += ("colspan=3 align=left><font face=Angsana New size=2> " + "_");
            t += ("colspan=3 align=left><font face=Angsana New size=2> " + "XXXXXXXXXXX" + pUtility.Addzero(bBean.getB_CardNo1(), 16).substring(12, 16) + "  " + bBean.getB_AppCode1() + "_");
            t += ("colspan=3 align=left><font face=Angsana New size=2> " + "Credit Payment" + TAB + DecFmt.format(bBean.getB_CrAmt1())) + "_";
        }
        t += ("colspan=3 align=center><font face=Angsana New size=2>" + "------------------------------------------------------------" + "_");
        t += ("colspan=3 align=center><font face=Angsana New size=2>" + "_");
        if (!CONFIG.getP_PrintRecpMessage().equals("")) {
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + CONFIG.getP_PrintRecpMessage() + "_");
        }

        if (!POSHW.getFootting3().equals("")) {
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + POSHW.getFootting3() + "_");
        }
        if (!POSHW.getFootting2().equals("")) {
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + POSHW.getFootting2() + "_");
        }
        if (!POSHW.getFootting1().equals("")) {
            t += ("colspan=3 align=center><font face=Angsana New size=2>" + POSHW.getFootting1() + "_");
        }
        t += (">_");
        EJPrint = false;
        PrintDriver pd = new PrintDriver();
        String[] strs = t.split("_");

        for (String data1 : strs) {
            pd.addTextIFont(data1);
        }
        openDrawerDriver();
        pd.printHTML();
    }

    private void printTableActionDriver() {
        String t = "";
        if (POSHW.getHeading1().length() >= 18) {
            String[] strs = POSHW.getHeading1().replace(" ", Space).split("_");
            for (String data : strs) {
                t += "colspan=3 align=center><font face=Angsana New size=2>" + data + "_";
            }
        } else {
            t += "colspan=3 align=center><font face=Angsana New size=2>" + POSHW.getHeading1().trim().replace(" ", "&nbsp; ") + "_";
        }
        if (POSHW.getHeading2().length() >= 18) {
            String[] strs = POSHW.getHeading2().replace(" ", Space).split("_");
            for (String data : strs) {
                t += "colspan=3 align=center><font face=sAngsana New size=2>" + data + "_";
            }
        } else {
            t += "colspan=3 align=center><font face=Angsana New size=2>" + POSHW.getHeading2().trim().replace(" ", "&nbsp; ") + "_";
        }
        t += "colspan=3 align=center><font face=Angsana New size=2>" + (POSHW.getHeading3()) + "_";
        t += "colspan=3 align=center><font face=Angsana New size=2>" + (POSHW.getHeading4()) + "_";
        t += "colspan=3 align=center><font face=Angsana New size=2>" + "REG.ID :" + Space + (POSHW.getTerminal()) + "_";
        t += ("colspan=3 align=center><font face=Angsana New size=2>_");

        t += "colspan=3 align=center><font face=Angsana New size=2>" + ("รายงานโต๊ะค้าง (ยังไม่ได้ชำระเงิน)") + "_";
        t += "colspan=3 align=center><font face=Angsana New size=2>" + ("Daily...Table Check" + "_");
        t += ("colspan=3 align=center><font face=Angsana New size=2>_");
        Date dateP = new Date();
        t += "colspan=3 align=left><font face=Angsana New size=2>" + "Print Date" + Space + (PPrint_DatefmtThai.format(dateP).replace("/", " / ")) + Space + "Cashier:" + PublicVar._User + " Mac:" + PublicVar.MACNO + "_";
        t += "colspan=3 align=center><font face=Angsana New size=2>" + ("------------------------------------------------------------") + "_";
        t += "colspan=3 align=center><font face=Angsana New size=2>" + ("Table" + Space + "Amount" + Space + "Open-Time" + Space + "Customer") + "_";
        t += "colspan=3 align=center><font face=Angsana New size=2>" + ("------------------------------------------------------------") + "_";
        Double SumTotal = 0.0;

        mysqlConnect.open(this.getClass());
        try {
            Statement stmt = mysqlConnect.getConnection().createStatement();
            String ChkTable = "select r_table,sum(r_total),MIN(r_void) r_void,MIN(TCurTime) TCurTime,MAX(tcustomer) tcustomer from balance"
                    + " left join tablefile on balance.r_table=tablefile.tcode "
                    + "where (r_void<>'V') or (r_void is null) "
                    + "group by r_table";
            ResultSet rs = stmt.executeQuery(ChkTable);
            while (rs.next()) {
                t += "align=left><font face=Angsana New size=2>" + (pUtility.DataFullSpace(rs.getString("r_table"), 6)
                        + pUtility.DataFullSpace(DecFmt.format(rs.getDouble("sum(r_total)")), 10) + Space
                        + "</td><td align=right><fonjt face=Angsana New size=2>"
                        + pUtility.DataFullSpace(rs.getString("TCurTime"), 8) + Space
                        + pUtility.DataFullSpace(IntFmt.format(rs.getInt("tcustomer")), 5)) + "_";
                SumTotal = SumTotal + rs.getDouble("sum(r_total)");
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            AppLogUtil.log(PPrint.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
        t += "colspan=3 align=center><font face=Angsana New size=2>" + ("------------------------------------------------------------") + "_";
        t += "align=left><font face=Angsana New size=2>" + ("Total" + "</td><td colspan=2 align=right><font face=Angsana New size=2" + pUtility.DataFull(DecFmt.format(SumTotal), 10)) + "_";
        t += "colspan=3 align=center><font face=Angsana New size=2>" + ("------------------------------------------------------------") + "_";

        PrintDriver pd = new PrintDriver();
        String[] strs = t.split("_");
        for (String data1 : strs) {
            pd.addTextIFont(data1);
        }
        pd.printHTML();
    }

    public void printHeaderBill() {
        if (PublicVar.printdriver == true) {
            printHeaderBillDriver();
        } else if (!POSHW.getPRNPort().equals("NONE")) {
            if (openPrint(POSHW.getPRNPort())) {
                initPrinter();
                print(POSHW.getHeading1());
                print(POSHW.getHeading2());
                print(POSHW.getHeading3());
                print(POSHW.getHeading4());

                print("REG ID :" + POSHW.getMacNo());
                cutPaper();
                closePrint();
            }
        }
    }

    private void printHeaderBillDriver() {
        System.err.println(PublicVar.driverNotSupport);
    }

    private void updatePrintCheckBill(String table) {
        mysqlConnect.open(this.getClass());
        try {
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                String sql = "UPDATE tablefile SET PrintChkBill= 'Y' WHERE Tcode='" + table + "'";
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            AppLogUtil.log(PPrint.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
    }

    private List<String[]> creName(String macNo) {
        List<String[]> list = new ArrayList<>();
        mysqlConnect.open(this.getClass());
        try {
            Statement stmt = mysqlConnect.getConnection().createStatement();
            String sql = "select B_CrCode1, sum(B_CrAmt1) B_CrAmt1 "
                    + "from billno "
                    + "where b_crcode1<>'' "
                    + "and b_cramt1 <>'0' "
                    + "and b_macno='" + macNo + "' "
                    + "and b_void<>'V' "
                    + "group by B_CrCode1 order by B_CrCode1 ";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String[] CreName = new String[]{"", "", ""};
                String name = rs.getString("B_CrCode1");
                String sqlGetCreditName = "select crname from creditfile where crcode='" + name + "' limit 1";
                ResultSet rs2 = mysqlConnect.executeQuery(sqlGetCreditName);
                String creditName;
                if (rs2.next()) {
                    creditName = ThaiUtil.ASCII2Unicode(rs2.getString("crname"));
                    name = name + " : " + creditName;
                }
                rs2.close();
                String code = rs2.getString("B_CardNo1");
                String amount = rs2.getString("B_CrAmt1");
                switch (code.length()) {
                    case 5:
                        code = code.substring(1, 5);
                        break;
                    case 6:
                        code = code.substring(2, 6);
                        break;
                    case 7:
                        code = code.substring(3, 7);
                        break;
                    case 8:
                        code = code.substring(4, 8);
                        break;
                    case 9:
                        code = code.substring(5, 9);
                        break;
                    case 10:
                        code = code.substring(6, 10);
                        break;
                    case 11:
                        code = code.substring(7, 11);
                        break;
                    case 12:
                        code = code.substring(8, 12);
                        break;
                    case 13:
                        code = code.substring(9, 13);
                        break;
                    case 14:
                        code = code.substring(10, 14);
                        break;
                    case 15:
                        code = code.substring(11, 15);
                        break;
                    case 16:
                        code = code.substring(12, 16);
                        break;
                    default:
                        break;
                }
                CreName[0] = name;
                CreName[1] = "";
                CreName[2] = amount;
                list.add(CreName);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            AppLogUtil.log(PPrint.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return list;
    }

    private String[] credit(String Macno) {
        String[] credit = new String[]{"", ""};

        mysqlConnect.open(this.getClass());
        try {
            Statement stmt = mysqlConnect.getConnection().createStatement();
            String SqlQuery = "select  count(b_crcode1) b_crcode1,b_crcode1,sum(b_cramt1) "
                    + "from billno"
                    + " where b_void<>'V' "
                    + "and b_cramt1>'0' "
                    + "and B_MacNo='" + Macno + "'"
                    + "group by b_crcode1 "
                    + "order by b_crcode1 ";
            ResultSet rs = stmt.executeQuery(SqlQuery);
            int dCardNo = 0;
            double dAmt = 0.00;
            String CardNo;
            String Amt;
            while (rs.next()) {
                CardNo = rs.getString("b_crcode1");
                Amt = rs.getString("sum(b_cramt1)");

                dAmt += Double.parseDouble(Amt);
                dCardNo += Integer.parseInt(CardNo);
            }
            credit[0] = dCardNo + "";
            credit[1] = dAmt + "";
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            AppLogUtil.log(PPrint.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return credit;
    }

    private List<Object[]> docAnalyse(String date1, String date2) {
        List<Object[]> listObj = new ArrayList<>();
        String sqlSelectDocTypeE = "select count(b_refno)b_refno,"
                + "b_etd,sum(b_cust) b_cust, "
                + "sum(b_nettotal)-sum(b_serviceamt) b_nettotal,"
                + " sum(b_vat) b_vat "
                + "from billno "
                + "where b_void<>'V'"
                + "and b_etd='E' "
                + "group by b_etd";
        String sqlSelectDocTypeT = "select count(b_refno)b_refno,"
                + "b_etd,sum(b_cust) b_cust, "
                + "sum(b_nettotal)-sum(b_serviceamt) b_nettotal,"
                + " sum(b_vat) b_vat "
                + "from billno "
                + "where b_void<>'V'"
                + "and b_etd='T' "
                + "group by b_etd";
        String sqlSelectDocTypeD = "select count(b_refno)b_refno,"
                + "b_etd,sum(b_cust) b_cust, "
                + "sum(b_nettotal)-sum(b_serviceamt) b_nettotal,"
                + " sum(b_vat) b_vat "
                + "from s_invoice "
                + "where s_date between '" + date1 + "' and '" + date2 + "' "
                + "and b_void<>'V'"
                + "and b_etd='D' "
                + "group by b_etd";

        mysqlConnect.open(this.getClass());
        try {
            int countb_refnoE = 0;
            int countb_refnoT;
            int countb_refnoD;
            String b_etd;
            String b_cust;

            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sqlSelectDocTypeE);
            if (rs.next()) {
                int countb_refno = rs.getInt("b_refno");
                b_etd = rs.getString("b_etd");
                b_cust = rs.getString("b_cust");
                double b_nettotal = rs.getDouble("b_nettotal");
                double b_vat = rs.getDouble("b_vat");
                double nettotal = 0.00;
                if (CONFIG.getP_VatType().equals("I")) {
                    nettotal = b_nettotal;
                }
                if (CONFIG.getP_VatType().equals("E")) {
                    nettotal = b_nettotal - b_vat;
                }

                AppLogUtil.info(b_etd + " " + b_cust + " " + b_nettotal + " " + b_vat);
                listObj.add(new Object[]{countb_refno, b_etd, b_cust, b_vat, nettotal, b_nettotal,});
            } else {
                listObj.add(new Object[]{"0", "E", "0", 0.00, 0.00, 0.00});
            }
            rs.close();
            stmt.close();

            stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs4 = stmt.executeQuery(sqlSelectDocTypeT);
            if (rs4.next()) {
                int countb_refno = rs4.getInt("b_refno");
                b_etd = rs4.getString("b_etd");
                b_cust = rs4.getString("b_cust");
                double b_nettotal = rs4.getDouble("b_nettotal");
                double b_vat = rs4.getDouble("b_vat");
                double nettotal = 0.00;
                if (CONFIG.getP_VatType().equals("I")) {
                    nettotal = b_nettotal;
                }
                if (CONFIG.getP_VatType().equals("E")) {
                    nettotal = b_nettotal - b_vat;
                }
                AppLogUtil.info(b_etd + " " + b_cust + " " + b_nettotal + " " + b_vat);
                listObj.add(new Object[]{countb_refno, b_etd, b_cust, b_vat, nettotal, b_nettotal,});
            } else {
                listObj.add(new Object[]{"0", "T", "0", 0.00, 0.00, 0.00});
            }
            rs4.close();
            stmt.close();

            stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs5 = stmt.executeQuery(sqlSelectDocTypeD);
            if (rs5.next()) {
                int countb_refno = rs5.getInt("b_refno");
                b_etd = rs5.getString("b_etd");
                b_cust = rs5.getString("b_cust");
                double b_nettotal = rs5.getDouble("b_nettotal");
                double b_vat = rs5.getDouble("b_vat");
                double nettotal = 0.00;
                if (CONFIG.getP_VatType().equals("I")) {
                    nettotal = b_nettotal;
                }
                if (CONFIG.getP_VatType().equals("E")) {
                    nettotal = b_nettotal - b_vat;
                }
                AppLogUtil.info(b_etd + " " + b_cust + " " + b_nettotal + " " + b_vat);
                listObj.add(new Object[]{countb_refno, b_etd, b_cust, b_vat, nettotal, b_nettotal,});
            } else {
                listObj.add(new Object[]{"0", "D", "0", 0.00, 0.00, 0.00});
            }
            rs5.close();
            stmt.close();

            String sqlCountBillnoE = "select count(b_refno) cb_refnoE "
                    + "from billno "
                    + "where b_ondate between '" + date1 + "' and '" + date2 + "' "
                    + "and b_etd='E' and b_void<>'V'";
            stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs1 = stmt.executeQuery(sqlCountBillnoE);
            if (rs1.next()) {
                countb_refnoE = rs1.getInt("cb_refnoE");
            } else {
                countb_refnoE = 0;
            }
            rs1.close();
            stmt.close();
            listObj.add(new Object[]{countb_refnoE});

            String sqlCountBillnoT = "select count(b_refno) cb_refnoT "
                    + "from billno "
                    + "where b_ondate between '" + date1 + "' and '" + date2 + "' "
                    + "and b_etd='T' and b_void<>'V'";
            stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs2 = stmt.executeQuery(sqlCountBillnoT);
            if (rs2.next()) {
                countb_refnoT = rs2.getInt("cb_refnoT");
            } else {
                countb_refnoT = 0;
            }
            rs2.close();
            stmt.close();
            listObj.add(new Object[]{countb_refnoT});

            String sqlCountBillnoD = "select count(b_refno) cb_refnoD "
                    + "from billno "
                    + "where b_ondate between '" + date1 + "' and '" + date2 + "' "
                    + "and b_etd='T' and b_void<>'V'";
            stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs6 = stmt.executeQuery(sqlCountBillnoD);
            if (rs6.next()) {
                countb_refnoD = rs6.getInt("cb_refnoD");
            } else {
                countb_refnoD = 0;
            }
            rs6.close();
            stmt.close();
            listObj.add(new Object[]{countb_refnoD});
            rs.close();

        } catch (SQLException e) {
            AppLogUtil.log(PPrint.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return listObj;
    }

    private void printEntertain(String b_Table) {
        mysqlConnect.open(this.getClass());
        try {
            String sql = "select CuCode, CuName, R_PrCuQuan, CuponDiscAmt "
                    + "from tablefile t,balance b,cupon c "
                    + "where t.tcode=b.r_table "
                    + "and b.R_PrCuCode=c.CuCode "
                    + "and r_table='" + b_Table + "' "
                    + "group by R_PrCuCode, CuCode, CuName, R_PrCuQuan, CuponDiscAmt;";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String CuName = ThaiUtil.ASCII2Unicode(rs.getString("CuName"));
                int R_PrCuQuan = rs.getInt("R_PrCuQuan");
                double CuponDiscAmt = rs.getDouble("CuponDiscAmt");
                String R_PName = CuName;
                int sizeNew = 20;
                String space = "  ";
                if (CuName.length() > 20) {
                    sizeNew = 21;
                    space = " ";
                    R_PName = R_PName.substring(0, 21);
                }
                print(" " + pUtility.DataFullR(R_PName, sizeNew + 2) + space + pUtility.DataFull(IntFmt.format(R_PrCuQuan), 3) + pUtility.DataFull(DecFmt.format(CuponDiscAmt), 12));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            AppLogUtil.log(PPrint.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
    }

    private String getLastEmployee(String tableNo) {
        String R_EMP = "";
        BillNoBean b = new BillNoBean();
        mysqlConnect.open(this.getClass());
        try {
            String sql = "select balance.r_emp r_emp,employ.code R_EmpCode,employ.name R_EmpName "
                    + "from balance "
                    + "inner join employ "
                    + "on balance.r_emp=employ.code"
                    + " where balance.r_table='" + tableNo + "' "
                    + "order by r_index";
            try (Statement stmt = mysqlConnect.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    R_EMP = ThaiUtil.ASCII2Unicode(rs.getString("R_EmpName").replace(">", "").replace("<", "").replace("_", ""));
                    b.setEmploy(R_EMP);
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(PPrint.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return R_EMP;
    }

    public String getLastEmployeeCheckBill(String tableNo, String refno) {
        String R_EMP = "-";
        mysqlConnect.open(this.getClass());
        try {
            String sql = "select r_emp from t_sale "
                    + "where r_table='" + tableNo + "' and r_refno='" + refno + "' limit 1";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                R_EMP = rs.getString(ThaiUtil.ASCII2Unicode("r_emp"));
            }
            String sqlGetEmpName = "select code,name name from employ where code='" + R_EMP + "'";
            ResultSet rs1 = mysqlConnect.executeQuery(sqlGetEmpName);
            if (rs1.next()) {
                R_EMP = ThaiUtil.ASCII2Unicode(rs1.getString("name").replace("<", "").replace("<", "").replace(" ", ""));
            }
            rs1.close();
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            AppLogUtil.log(PPrint.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return R_EMP;
    }

    public String subStringText(String str, int prefix) {
        int i;
        i = str.length();
        if (i > prefix) {
            str = str.substring(0, prefix);
            str = str.replace(" ", Space);
        }
        return str;
    }

    private List<Object[]> printCuponName(String r_refno) {
        List<Object[]> listObj = new ArrayList<>();
        String cuname;
        double total;
        try {
            String sql = "select t_cupon.r_refno, t_cupon.cucode, cupon.cuname, t_cupon.cuquan, sum(cuamt) total "
                    + "from t_cupon inner join cupon "
                    + "on t_cupon.cucode = cupon.cucode "
                    + "where r_refno='" + r_refno + "' group by r_refno, t_cupon.cucode, cupon.cuname, t_cupon.cuquan";
            mysqlConnect.open(this.getClass());
            ResultSet rs = mysqlConnect.executeQuery(sql);
            if (rs.next()) {
                cuname = ThaiUtil.ASCII2Unicode(rs.getString("cuname"));
                total = rs.getDouble("total");
                listObj.add(new Object[]{cuname, total});
            }
        } catch (SQLException e) {
            AppLogUtil.log(PPrint.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return listObj;
    }

    public String changeLanguage(String text) {
        String t = text;
        String billLanguage = ConfigFile.getProperties("languagebillSubtotal");
        if (billLanguage.equals("th") && PublicVar.languagePrint.equals("TH")) {
            t = t.replace("CC :", "ลูกค้า").replace("Seat :", "ที่");
            t = t.replace("TABLE :", "โต๊ะ").replace("Name:", "พนักงาน");
            t = t.replace("Sub-TOTAL :", "สินค้าที่ต้องชำระ :");
            t = t.replace("Service :", "ค่าบริการ :");
            t = t.replace("Item-Discount", "ส่วนลดรายการ :");
            t = t.replace("Net-Amount", "ยอดรวม");
            t = t.replace("Net-Total", "รวมที่ต้องชำระ");
            t = t.replace("Round", "ปัดเศษ");
            t = t.replace("Vat", "ภาษีมูลค่าเพิ่ม");
            t = t.replace(" Item", " รายการ");
            t = t.replace("VAT INCLUDED", "รวมภาษีมูลค่าเพิ่มแล้ว");
            t = t.replace("CASH", "เงินสด").replace("Change", "ทอน");
            t = t.replace("Receipt", "ใบเสร็จรับเงิน").replace("COM", "คอม");
            t = t.replace("Discount", "ส่วนลด").replace("Bath", "บาท");
            t = t.replace("Disc", "ส่วนลด").replace("Bath", "บาท");
            t = t.replace("Credit Payment", "ชำระเครดิต");
            t = t.replace("Net-TOTAL", "รวมที่ต้องชำระ");
        }
        return t;
    }

    public String changeReportLanguage(String text) {
        String t = text;
        String billLanguage = ConfigFile.getProperties("languagebillSubtotal");
        if (billLanguage.equals("th")) {
            t = t.replace("CASH IN DRAWER", "เงินสดในลิ้นชัก");
            t = t.replace("FOOD", "อาหาร");
            t = t.replace("BEVERAGE", "เครื่องดื่ม");
            t = t.replace("PRODUCT", "สินค้า");
            t = t.replace("TOTAL-SALES", "รวมยอดขายตามป้าย");
            t = t.replace("Discount Bath", "ส่วนลด บาท");
            t = t.replace("Discount Item", "ส่วนลด รายการ");
            t = t.replace("Gross-Sales", "ยอดขายหลังหักส่วนลด");
            t = t.replace("CASH", "เงินสด");
            t = t.replace("CRADIT", "เครดิต");
            t = t.replace("Bank In", "ยอดเงินสดนำส่ง");
            t = t.replace("Service Charge", "ค่าบริการ");

            t = t.replace("Net-Sales", "ยอดขายรวม");
            t = t.replace("Net-Sale", "ยอดขายรวม");
            t = t.replace("Round Total", "ยอดปัดเศษ");
            t = t.replace("Vat", "ภาษีมูลค่าเพิ่ม");
            t = t.replace("Customer", "จำนวนลูกค้า");
            t = t.replace("MGR Refund", "ยกเลิกใบเสร็จ").replace("Doc.", "ใบ");
            t = t.replace("MGR Void", "ยกเลิกรายการ").replace("Items.", "รายการ");
            t = t.replace("Total Docket", "จำนวนใบเสร็จ");
            t = t.replace("Start Docket", "ใบเสร็จเริ่มต้น");
            t = t.replace("To..", "ถึง..");
            t = t.replace("SaleType", "ประเภทขาย");
            t = t.replace("Docket", "ใบเสร็จ");
            t = t.replace("CC", "ลูกค้า");
            t = t.replace("Amount", "มูลค่า");
            t = t.replace("DineIn", "ทานในร้าน");
            t = t.replace("Dine-In", "ทานในร้าน");
            t = t.replace("Dine - In", "ทานในร้าน");
            t = t.replace("TakeAway", "ห่อกลับ");
            t = t.replace("Take Away", "ห่อกลับ");
            t = t.replace("Delivery", "ส่ง");
            t = t.replace("Analysts", "วิเคราะห์");
            t = t.replace("Gross Sales", "ยอดขายหลังหักส่วนลด");
            t = t.replace("Net Sales", "ยอดขายรวมภาษีมูลค่าเพิ่ม");
            t = t.replace("Customer", "จำนวนลูกค้า");
            t = t.replace("AVG/Dock", "ยอดเฉลี่ยต่อใบเสร็จ");
            t = t.replace("AVG/Head", "ยอดเฉลี่ยต่อคน");
            t = t.replace("FLOAT IN", "เงินสำรองทอนเข้า");
            t = t.replace("FLOAT OUT", "เงินสำรองทอนออก");
            t = t.replace("FLOAT-IN", "เงินสำรองทอนเข้า");
            t = t.replace("FLOAT-OUT", "เงินสำรองทอนออก");
            t = t.replace("Service Charege", "ค่าบริการ");
            t = t.replace("Net Total : ", "ยอดขายรวมทั้งสิ้น");
        }
        return t;
    }

    public String getCuponName(String cuCode) {
        String cuName = "";
        try {
            mysqlConnect.open(this.getClass());
            String sql = "select cuname from cupon where cucode='" + cuCode + "' limit 1";
            ResultSet rs = mysqlConnect.executeQuery(sql);
            if (rs.next()) {
                cuName = ThaiUtil.ASCII2Unicode(rs.getString("cuname"));
            }
            rs.close();
        } catch (SQLException e) {
            AppLogUtil.log(PPrint.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return cuName;
    }
}
