package com.softpos.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * รวบรวม SQL Query ทั้งหมด (INSERT, UPDATE, DELETE) ที่ใช้ในโปรเจค SoftPOS Restaurant
 * จัดกลุ่มตาม Table และ Operation
 */
public class SqlQueryCatalog {

    // ==================== TABLE: balance ====================
    // ไฟล์: src/com/softpos/pos/core/controller/BalanceControl.java

    public static final String BALANCE_INSERT =
        "INSERT INTO balance " +
        "(R_Index, R_Table, R_Date, R_Time, Macno, " +
        "Cashier, R_Emp, R_PluCode, R_PName, R_Unit, " +
        "R_Group, R_Status, R_Normal, R_Discount, R_Service, " +
        "R_Stock, R_Set, R_Vat, R_Type, R_ETD, R_Quan, R_Price, " +
        "R_Total, R_PrType, R_PrCode, R_PrDisc, R_PrBath, " +
        "R_PrAmt, R_DiscBath, R_PrCuType, R_PrCuQuan, R_PrCuAmt, " +
        "R_Redule, R_Kic, R_KicPrint, R_Void, R_VoidUser, R_VoidTime, " +
        "R_Opt1, R_Opt2, R_Opt3, R_Opt4, R_Opt5, R_Opt6, R_Opt7, R_Opt8, " +
        "R_Opt9, R_PrCuCode, R_Serve, R_PrintOK, R_KicOK, StkCode, " +
        "PosStk, R_PrChkType, R_PrQuan, R_PrSubType, R_PrSubCode, " +
        "R_PrSubQuan, R_PrSubDisc, R_PrSubBath, R_PrSubAmt, R_PrSubAdj, " +
        "R_PrCuDisc, R_PrCuBath, R_PrCuAdj, R_QuanCanDisc, R_Order, R_PItemNo, " +
        "R_PKicQue, R_MemSum, R_MoveItem, R_MoveFrom, R_MoveUser, R_MoveFlag, " +
        "R_MovePrint, R_Pause, R_PrVcType, R_PrVcCode, R_LinkIndex, R_VoidPause, R_SPIndex, VoidMSG, R_PEName) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
        "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
        "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String BALANCE_UPDATE_KIC_PRINT =
        "UPDATE balance " +
        "SET r_kicprint = 'P', r_pause = 'Y' " +
        "WHERE r_table = ? AND r_kicprint <> 'P' AND r_printOk = 'Y' AND r_kic <> ''";

    public static final String BALANCE_UPDATE_LINK_INDEX =
        "UPDATE balance SET r_linkindex = '' WHERE r_linkindex = ''";

    public static final String BALANCE_UPDATE_PDA_PRINT =
        "UPDATE balance SET PDAPrintCheck = 'N' WHERE r_table = ?";

    public static final String BALANCE_UPDATE_ITEM_DISCOUNT =
        "UPDATE balance SET " +
        "R_PrType = ?, R_PrCode = ?, R_PrDisc = ?, R_PrBath = ?, R_PrAmt = ? " +
        "WHERE R_Index = ? AND R_Table = ?";

    public static final String BALANCE_DELETE_BY_TABLE_AND_PLUCODE =
        "DELETE FROM balance WHERE R_Table = ? AND R_PluCode = ?";

    public static final String BALANCE_DELETE_BY_INDEX =
        "DELETE FROM balance WHERE R_Index = ? AND R_Table = ?";

    public static final String BALANCE_DELETE_BY_TABLE =
        "DELETE FROM balance WHERE R_Table = ?";

    // ==================== TABLE: billno ====================
    // ไฟล์: src/com/softpos/pos/core/controller/BillControl.java

    public static final String BILLNO_INSERT =
        "INSERT INTO billno " +
        "(B_Refno, B_CuponDiscAmt, B_Ontime, B_LoginTime, B_OnDate, " +
        "B_PostDate, B_Table, B_MacNo, B_Cashier, B_Cust, " +
        "B_ETD, B_Total, B_Food, B_Drink, B_Product, " +
        "B_Service, B_ServiceAmt, B_ItemDiscAmt, B_FastDisc, B_FastDiscAmt, " +
        "B_EmpDisc, B_EmpDiscAmt, B_TrainDisc, B_TrainDiscAmt, B_MemDisc, " +
        "B_MemDiscAmt, B_SubDisc, B_SubDiscAmt, B_SubDiscBath, B_ProDiscAmt, " +
        "B_SpaDiscAmt, B_AdjAmt, B_PreDisAmt, B_NetTotal, B_NetFood, " +
        "B_NetDrink, B_NetProduct, B_NetVat, B_NetNonVat, B_Vat, " +
        "B_PayAmt, B_Cash, B_GiftVoucher, B_Earnest, B_Ton, " +
        "B_CrCode1, B_CardNo1, B_AppCode1, B_CrCharge1, B_CrChargeAmt1, " +
        "B_CrAmt1, B_AccrCode, B_AccrAmt, B_AccrCr, B_MemCode, " +
        "B_MemName, B_MemBegin, B_MemEnd, B_MemCurSum, B_Void, " +
        "B_VoidUser, B_VoidTime, B_BillCopy, B_PrnCnt, B_PrnTime1, " +
        "B_PrnTime2, B_InvNo, B_InvType, B_Bran, B_BranName, " +
        "B_Tel, B_RecTime, MStamp, MScore, CurStamp, " +
        "StampRate, B_ChkBill, B_ChkBillTime, B_CashTime, B_WaitTime, " +
        "B_SumScore, B_CrBank, B_CrCardAmt, B_CrCurPoint, B_CrSumPoint, " +
        "B_Entertain, B_VoucherDiscAmt, B_VoucherOver, B_NetDiff, B_SumSetDiscAmt, " +
        "B_DetailFood, B_DetailDrink, B_DetailProduct, B_KicQue, B_ROUNDCLOSE) " +
        "VALUES (?, ?, CURTIME(), ?, CURDATE(), " +
        "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
        "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
        "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
        "?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // ==================== TABLE: t_sale ====================
    // ไฟล์: src/com/softpos/pos/core/controller/BillControl.java

    public static final String T_SALE_INSERT =
        "INSERT INTO t_sale " +
        "(R_Index, R_Refno, R_Table, R_Date, R_Time, MacNo, Cashier, R_Emp, R_PluCode, R_PName, R_Unit, " +
        "R_Group, R_Status, R_Normal, R_Discount, R_Service, R_Stock, R_Set, R_Vat, R_Type, R_ETD, " +
        "R_Quan, R_Price, R_Total, R_PrType, R_PrCode, R_PrDisc, R_PrBath, R_PrAmt, R_PrCuType, " +
        "R_PrCuCode, R_PrCuQuan, R_PrCuAmt, R_Redule, R_DiscBath, R_PrAdj, R_NetTotal, R_Kic, " +
        "R_KicPrint, R_Refund, VoidMsg, R_Void, R_VoidUser, R_VoidTime, StkCode, PosStk, R_ServiceAmt, " +
        "R_PrChkType, R_PrQuan, R_PrSubType, R_PrSubCode, R_PrSubQuan, R_PrSubDisc, R_PrSubBath, " +
        "R_PrSubAmt, R_PrSubAdj, R_PrCuDisc, R_PrCuBath, R_PrCuAdj, R_PrChkType2, R_PrQuan2, " +
        "R_PrType2, R_PrCode2, R_PrDisc2, R_PrBath2, R_PrAmt2, R_PrAdj2, R_PItemNo, R_PKicQue, " +
        "R_MoveItem, R_MoveFrom, R_MoveUser, R_MoveFlag, R_Pause, R_SPIndex, R_LinkIndex, " +
        "R_Opt1, R_Opt2, R_Opt3, R_Opt4, R_Opt5, R_Opt6, R_Opt7, R_Opt8, R_Opt9, R_VoidPause) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
        "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
        "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String T_SALE_UPDATE_NET_TOTAL =
        "UPDATE t_sale " +
        "SET R_Nettotal = R_Nettotal + ?, R_ServiceAmt = R_ServiceAmt + ? " +
        "WHERE R_Refno = ? AND R_Index = ?";

    // ==================== TABLE: kictran ====================
    // ไฟล์: src/com/softpos/pos/core/model/PKicTran.java
    // ไฟล์: src/com/softpos/printer/control/PrintSimpleForm.java
    // ไฟล์: src/com/softpos/pos/core/controller/PrintToKicController.java

    public static final String KICTRAN_INSERT_FULL =
        "INSERT INTO kictran " +
        "(PItemNo, PDate, PCode, PIndex, MacNo, " +
        "Cashier, Emp, PTable, PKic, PTimeIn, " +
        "PTimeOut, PVoid, PETD, PQty, PFlage, " +
        "PServe, PServeTime, PWaitTime, PPayment, PInvNo, " +
        "PWaitServe, PWaitTotal, R_PEName) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String KICTRAN_INSERT_SIMPLE =
        "INSERT INTO kictran " +
        "(pitemno, pdate, pcode, pqty, pindex, " +
        "macno, cashier, emp, ptable, ptimein, pvoid, petd, pkic) " +
        "VALUES (?, CURDATE(), ?, ?, ?, ?, ?, ?, ?, CURTIME(), '', ?, ?)";

    public static final String KICTRAN_UPDATE_KIC_PRINT =
        "UPDATE kictran SET pflage = 'P' WHERE ptable = ?";

    public static final String KICTRAN_UPDATE_ALERT =
        "UPDATE kictran SET R_ShowDisplayAlert = ? WHERE ptable = ?";

    // ==================== TABLE: tablefile ====================
    // ไฟล์: src/com/softpos/pos/core/controller/TableFileControl.java
    // ไฟล์: src/com/softpos/main/pos/view/MainSale.java
    // ไฟล์: src/com/softpos/printer/control/PPrint.java
    // ไฟล์: src/com/softpos/pos/core/controller/TableMoveControl.java

    public static final String TABLEFILE_INSERT =
        "INSERT INTO tablefile (Tcode) VALUES (?)";

    public static final String TABLEFILE_UPDATE_OPEN_TABLE =
        "UPDATE tablefile SET " +
        "TOnAct = 'Y', macno = ?, TCustomer = ?, tpause = 'N' " +
        "WHERE Tcode = ?";

    public static final String TABLEFILE_UPDATE_CLOSE_TABLE =
        "UPDATE tablefile SET " +
        "TOnAct = 'N', TCurTime = CURTIME(), TAmount = '0', " +
        "TCustomer = '0', NettTotal = '0' " +
        "WHERE Tcode = ?";

    public static final String TABLEFILE_UPDATE_CHKBILL =
        "UPDATE tablefile SET PrintChkBill = 'Y' WHERE Tcode = ?";

    public static final String TABLEFILE_UPDATE_CUSTOMER =
        "UPDATE tablefile SET tcustomer = ? WHERE tcode = ?";

    public static final String TABLEFILE_DELETE =
        "DELETE FROM tablefile WHERE tcode = ?";

    // ==================== TABLE: poshwsetup ====================
    // ไฟล์: src/com/softpos/pos/core/controller/BillControl.java
    // ไฟล์: src/com/softpos/pos/core/controller/PosControl.java

    public static final String POSHWSETUP_UPDATE_RECENO =
        "UPDATE poshwsetup SET receno1 = receno1 + 1 WHERE terminal = ?";

    public static final String POSHWSETUP_UPDATE_OFFLINE =
        "UPDATE poshwsetup SET onact = 'N' WHERE terminal = ?";

    // ==================== TABLE: posuser ====================
    // ไฟล์: src/com/softpos/pos/core/controller/PosControl.java
    // ไฟล์: src/com/softpos/pos/core/controller/LoginController.java

    public static final String POSUSER_UPDATE_OFFLINE =
        "UPDATE posuser SET onact = 'N', macno = '' WHERE username = ?";

    public static final String POSUSER_UPDATE_ALL_OFFLINE =
        "UPDATE posuser SET onact = 'N'";

    // ==================== TABLE: accr ====================
    // ไฟล์: src/com/softpos/pos/core/controller/BillControl.java

    public static final String ACCR_INSERT =
        "INSERT INTO accr " +
        "(ArNo, ArDate, ArCode, ArTotal, ArVat, " +
        "ArDisc, ArVatMon, ArAccNo, ArMark, ArNet, " +
        "ArAmount, ArCr, arDue, ArSale, ArRemark, " +
        "ArPayType, ArDocBill, ArDocPay, ArBank, ArChqNo, " +
        "ArChqDate, ArAmtPay, ArAmtCr, ArBDate, ArPDate, " +
        "ArFlage, ArInvNo, ArBran, ArBranPay, ArUserPay) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // ==================== TABLE: t_gift ====================
    // ไฟล์: src/com/softpos/pos/core/controller/BillControl.java

    public static final String T_GIFT_INSERT =
        "INSERT INTO t_gift " +
        "(ondate, macno, refno, cashier, giftbarcode, gifttype, " +
        "giftprice, giftmodel, giftlot, giftexp, giftcode, giftno, " +
        "giftamt, fat) " +
        "VALUES (CURDATE(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // ==================== TABLE: t_cupon ====================
    // ไฟล์: src/com/softpos/pos/core/controller/TCuponControl.java

    public static final String T_CUPON_INSERT =
        "INSERT INTO t_cupon " +
        "(R_Index, R_Refno, Terminal, Cashier, Time, CuCode, CuQuan, " +
        "CuAmt, Refund, CuTextCode, CuTextComment) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // ==================== TABLE: tempcupon ====================
    // ไฟล์: src/com/softpos/pos/core/controller/TempCuponController.java

    public static final String TEMPCUPON_INSERT =
        "INSERT INTO tempcupon " +
        "(R_Index, R_Table, Terminal, Cashier, Time, CuCode, CuQuan, CuAmt, " +
        "CuTotal, CuDisc, CuRedule, CuPayment, CuTextCode, CuTextComment) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String TEMPCUPON_DELETE_BY_INDEX =
        "DELETE FROM tempcupon WHERE r_index = ?";

    // ==================== TABLE: tempgift ====================
    // ไฟล์: src/com/softpos/pos/core/controller/TempCuponController.java

    public static final String TEMPGIFT_DELETE_ALL =
        "DELETE FROM tempgift";

    // ==================== TABLE: tempcredit ====================
    // ไฟล์: src/com/softpos/posreport/AutoXReport.java
    // ไฟล์: src/com/softpos/posreport/AutoSumXReport.java
    // ไฟล์: src/com/softpos/posreport/CreditReport.java
    // ไฟล์: src/com/softpos/posreport/MTDCredit.java

    public static final String TEMPCREDIT_INSERT =
        "INSERT INTO tempcredit (terminal, crcode, crid, crapp, cramt) " +
        "VALUES (?, ?, ?, ?, ?)";

    public static final String TEMPCREDIT_INSERT_FULL =
        "INSERT INTO tempcredit (mac_no, s_date, terminal, ref_no, crcode, crid, crapp, cramt) " +
        "VALUES (?, CURDATE(), ?, ?, ?, ?, ?, ?)";

    public static final String TEMPCREDIT_DELETE_BY_TERMINAL =
        "DELETE FROM tempcredit WHERE terminal = ?";

    // ==================== TABLE: tempset ====================
    // ไฟล์: src/com/softpos/main/pos/view/MainSale.java
    // ไฟล์: src/com/softpos/main/pos/view/ModalPopup.java

    public static final String TEMPSET_INSERT =
        "INSERT INTO tempset " +
        "(PTableNo, PIndex, PCode, PDesc, PPostStock, PProTry, POption, PTime) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, CURTIME())";

    // ==================== TABLE: tempeditqty ====================
    // ไฟล์: src/com/softpos/main/pos/view/ItemEditQty.java

    public static final String TEMPEDITQTY_INSERT =
        "INSERT INTO tempeditqty (OnDate, Time, Emp, Pcode, Pdesc, OldQty, OldPrice, NewQty, NewPrice) " +
        "VALUES (CURDATE(), CURTIME(), ?, ?, ?, ?, ?, ?, ?)";

    // ==================== TABLE: temptopsale ====================
    // ไฟล์: src/com/softpos/posreport/MTDTopSale.java
    // ไฟล์: src/com/softpos/posreport/TopSaleReport.java

    public static final String TEMPTOPSALE_INSERT =
        "INSERT INTO temptopsale (terminal, r_group, r_plucode, r_pname, r_quan, r_total) " +
        "VALUES (?, ?, ?, ?, ?, ?)";

    public static final String TEMPTOPSALE_DELETE_BY_TERMINAL =
        "DELETE FROM temptopsale WHERE terminal = ?";

    // ==================== TABLE: optionset ====================
    // ไฟล์: src/com/softpos/pos/core/controller/OptionMenuSetController.java

    public static final String OPTIONSET_INSERT =
        "INSERT INTO optionset (PCode, PDesc, OptionCode, OptionName) " +
        "VALUES (?, ?, ?, ?)";

    public static final String OPTIONSET_DELETE =
        "DELETE FROM optionset WHERE pcode = ? AND optionname = ?";

    // ==================== TABLE: mgrbuttonsetup ====================
    // ไฟล์: src/com/softpos/pos/core/controller/MgrButtonController.java
    // ไฟล์: src/com/softpos/main/program/SaveMenuIntoBOR.java

    public static final String MGRBUTTONSETUP_INSERT =
        "INSERT INTO mgrbuttonsetup " +
        "(pcode, pdesc, sd_pcode, sd_pdesc, ex_pcode, ex_pdesc, " +
        "auto_pcode, auto_pdesc, check_before, check_qty, qty, check_autoadd, check_extra) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String MGRBUTTONSETUP_INSERT_FULL =
        "INSERT INTO mgrbuttonsetup " +
        "(pcode, pdesc, sd_pcode, sd_pdesc, ex_pcode, ex_pdesc, " +
        "ex_uncode, ex_undesc, auto_pcode, auto_pdesc, " +
        "Check_before, Check_qty, qty, check_autoadd, Check_Extra) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String MGRBUTTONSETUP_DELETE_BY_PCODE =
        "DELETE FROM mgrbuttonsetup WHERE pcode = ?";

    // ==================== TABLE: soft_menusetup ====================
    // ไฟล์: src/com/softpos/pos/core/controller/MgrButtonController.java
    // ไฟล์: src/com/softpos/main/program/SaveMenuIntoBOR.java

    public static final String SOFT_MENUSETUP_INSERT =
        "INSERT INTO soft_menusetup " +
        "(MenuCode, MenuType, OptSet, PSet, PCode, MenuShowText, " +
        "IMG, FontColor, BGColor, Layout, FontSize, FontName, FontAttr, M_Index, IMG_SIZE) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String SOFT_MENUSETUP_UPDATE_STYLE =
        "UPDATE soft_menusetup " +
        "SET FontColor = ?, BGColor = ?, Layout = ?, FontSize = ?, FontName = ?, FontAttr = ? " +
        "WHERE MenuCode = ?";

    public static final String SOFT_MENUSETUP_DELETE_BY_MENUCODE =
        "DELETE FROM soft_menusetup WHERE MenuCode = ?";

    public static final String SOFT_MENUSETUP_DELETE_BY_TEXT =
        "DELETE FROM soft_menusetup WHERE MenuCode = ? AND MenuShowText = ?";

    // ==================== TABLE: branch ====================
    // ไฟล์: src/com/softpos/pos/core/controller/RefundBillController.java

    public static final String BRANCH_UPDATE_RETURNBILLNO =
        "UPDATE branch SET returnbillno = returnbillno + 1";

    // ==================== TABLE: paidiofile ====================
    // ไฟล์: src/com/softpos/pos/core/controller/PosControl.java

    public static final String PAIDIOFILE_UPDATE_AMT =
        "UPDATE paidiofile SET PaidOutAmt = ? WHERE flage = 'O'";

    // ==================== TABLE: product ====================
    // ไฟล์: src/com/softpos/pos/core/controller/ProductControl.java

    public static final String PRODUCT_UPDATE_KIC =
        "UPDATE product SET pkic = ? WHERE pcode = ?";

    // ==================== TABLE: temp_balance (Swap/Move) ====================
    // ไฟล์: src/com/softpos/pos/core/controller/TableMoveControl.java

    public static final String TEMP_BALANCE_CREATE =
        "CREATE TABLE IF NOT EXISTS temp_balance SELECT * FROM balance WHERE r_table IN (?, ?)";

    public static final String TEMP_BALANCE_DROP =
        "DROP TABLE IF EXISTS temp_balance";

    public static final String TEMP_TABLEFILE_CREATE =
        "CREATE TABLE IF NOT EXISTS temp_tablefile SELECT * FROM tablefile WHERE tcode IN (?, ?)";

    public static final String TEMP_TABLEFILE_DROP =
        "DROP TABLE IF EXISTS temp_tablefile";

    public static final String BALANCE_UPDATE_TABLE_MOVE =
        "UPDATE balance SET r_table = ? WHERE r_table = ?";

    // ==================== UTILITY: getAllQueries() ====================

    /**
     * คืนค่า Map ของ query ทั้งหมดจัดกลุ่มตาม table
     * Key = "TABLE.OPERATION", Value = SQL string
     */
    public static Map<String, String> getAllQueries() {
        Map<String, String> queries = new LinkedHashMap<>();

        // balance
        queries.put("balance.INSERT", BALANCE_INSERT);
        queries.put("balance.UPDATE_KIC_PRINT", BALANCE_UPDATE_KIC_PRINT);
        queries.put("balance.UPDATE_LINK_INDEX", BALANCE_UPDATE_LINK_INDEX);
        queries.put("balance.UPDATE_PDA_PRINT", BALANCE_UPDATE_PDA_PRINT);
        queries.put("balance.UPDATE_ITEM_DISCOUNT", BALANCE_UPDATE_ITEM_DISCOUNT);
        queries.put("balance.DELETE_BY_TABLE_AND_PLUCODE", BALANCE_DELETE_BY_TABLE_AND_PLUCODE);
        queries.put("balance.DELETE_BY_INDEX", BALANCE_DELETE_BY_INDEX);
        queries.put("balance.DELETE_BY_TABLE", BALANCE_DELETE_BY_TABLE);

        // billno
        queries.put("billno.INSERT", BILLNO_INSERT);

        // t_sale
        queries.put("t_sale.INSERT", T_SALE_INSERT);
        queries.put("t_sale.UPDATE_NET_TOTAL", T_SALE_UPDATE_NET_TOTAL);

        // kictran
        queries.put("kictran.INSERT_FULL", KICTRAN_INSERT_FULL);
        queries.put("kictran.INSERT_SIMPLE", KICTRAN_INSERT_SIMPLE);
        queries.put("kictran.UPDATE_KIC_PRINT", KICTRAN_UPDATE_KIC_PRINT);
        queries.put("kictran.UPDATE_ALERT", KICTRAN_UPDATE_ALERT);

        // tablefile
        queries.put("tablefile.INSERT", TABLEFILE_INSERT);
        queries.put("tablefile.UPDATE_OPEN_TABLE", TABLEFILE_UPDATE_OPEN_TABLE);
        queries.put("tablefile.UPDATE_CLOSE_TABLE", TABLEFILE_UPDATE_CLOSE_TABLE);
        queries.put("tablefile.UPDATE_CHKBILL", TABLEFILE_UPDATE_CHKBILL);
        queries.put("tablefile.UPDATE_CUSTOMER", TABLEFILE_UPDATE_CUSTOMER);
        queries.put("tablefile.DELETE", TABLEFILE_DELETE);

        // poshwsetup
        queries.put("poshwsetup.UPDATE_RECENO", POSHWSETUP_UPDATE_RECENO);
        queries.put("poshwsetup.UPDATE_OFFLINE", POSHWSETUP_UPDATE_OFFLINE);

        // posuser
        queries.put("posuser.UPDATE_OFFLINE", POSUSER_UPDATE_OFFLINE);
        queries.put("posuser.UPDATE_ALL_OFFLINE", POSUSER_UPDATE_ALL_OFFLINE);

        // accr
        queries.put("accr.INSERT", ACCR_INSERT);

        // t_gift
        queries.put("t_gift.INSERT", T_GIFT_INSERT);

        // t_cupon
        queries.put("t_cupon.INSERT", T_CUPON_INSERT);

        // tempcupon
        queries.put("tempcupon.INSERT", TEMPCUPON_INSERT);
        queries.put("tempcupon.DELETE_BY_INDEX", TEMPCUPON_DELETE_BY_INDEX);

        // tempgift
        queries.put("tempgift.DELETE_ALL", TEMPGIFT_DELETE_ALL);

        // tempcredit
        queries.put("tempcredit.INSERT", TEMPCREDIT_INSERT);
        queries.put("tempcredit.INSERT_FULL", TEMPCREDIT_INSERT_FULL);
        queries.put("tempcredit.DELETE_BY_TERMINAL", TEMPCREDIT_DELETE_BY_TERMINAL);

        // tempset
        queries.put("tempset.INSERT", TEMPSET_INSERT);

        // tempeditqty
        queries.put("tempeditqty.INSERT", TEMPEDITQTY_INSERT);

        // temptopsale
        queries.put("temptopsale.INSERT", TEMPTOPSALE_INSERT);
        queries.put("temptopsale.DELETE_BY_TERMINAL", TEMPTOPSALE_DELETE_BY_TERMINAL);

        // optionset
        queries.put("optionset.INSERT", OPTIONSET_INSERT);
        queries.put("optionset.DELETE", OPTIONSET_DELETE);

        // mgrbuttonsetup
        queries.put("mgrbuttonsetup.INSERT", MGRBUTTONSETUP_INSERT);
        queries.put("mgrbuttonsetup.INSERT_FULL", MGRBUTTONSETUP_INSERT_FULL);
        queries.put("mgrbuttonsetup.DELETE_BY_PCODE", MGRBUTTONSETUP_DELETE_BY_PCODE);

        // soft_menusetup
        queries.put("soft_menusetup.INSERT", SOFT_MENUSETUP_INSERT);
        queries.put("soft_menusetup.UPDATE_STYLE", SOFT_MENUSETUP_UPDATE_STYLE);
        queries.put("soft_menusetup.DELETE_BY_MENUCODE", SOFT_MENUSETUP_DELETE_BY_MENUCODE);
        queries.put("soft_menusetup.DELETE_BY_TEXT", SOFT_MENUSETUP_DELETE_BY_TEXT);

        // branch
        queries.put("branch.UPDATE_RETURNBILLNO", BRANCH_UPDATE_RETURNBILLNO);

        // paidiofile
        queries.put("paidiofile.UPDATE_AMT", PAIDIOFILE_UPDATE_AMT);

        // product
        queries.put("product.UPDATE_KIC", PRODUCT_UPDATE_KIC);

        return queries;
    }

    /**
     * พิมพ์ query ทั้งหมดออกทาง stdout จัดกลุ่มตาม table
     */
    public static void printAll() {
        Map<String, String> all = getAllQueries();
        String currentTable = "";
        for (Map.Entry<String, String> entry : all.entrySet()) {
            String table = entry.getKey().split("\\.")[0];
            if (!table.equals(currentTable)) {
                currentTable = table;
                System.out.println("\n========== TABLE: " + currentTable.toUpperCase() + " ==========");
            }
            System.out.println("[" + entry.getKey() + "]");
            System.out.println(entry.getValue());
            System.out.println();
        }
    }

    public static void main(String[] args) {
        printAll();
    }
}
