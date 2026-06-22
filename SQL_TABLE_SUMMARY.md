# SQL Table & Column Summary — SoftPOS Restaurant

> สรุปตาราง (Tables) และคอลัมน์ (Columns) ทั้งหมดที่ใช้ในโปรเจค พร้อม Operation ที่กระทำ (INSERT / UPDATE / DELETE)
> อ้างอิง Java Class: `src/com/softpos/util/SqlQueryCatalog.java`

---

## 1. `balance` — รายการสั่งอาหาร (Order Items)

**ไฟล์หลัก:** `BalanceControl.java`, `TableMoveControl.java`, `PrintSimpleForm.java`, `ItemDisControl.java`

| Operation | Columns |
|-----------|---------|
| **INSERT** | R_Index, R_Table, R_Date, R_Time, Macno, Cashier, R_Emp, R_PluCode, R_PName, R_Unit, R_Group, R_Status, R_Normal, R_Discount, R_Service, R_Stock, R_Set, R_Vat, R_Type, R_ETD, R_Quan, R_Price, R_Total, R_PrType, R_PrCode, R_PrDisc, R_PrBath, R_PrAmt, R_DiscBath, R_PrCuType, R_PrCuQuan, R_PrCuAmt, R_Redule, R_Kic, R_KicPrint, R_Void, R_VoidUser, R_VoidTime, R_Opt1~R_Opt9, R_PrCuCode, R_Serve, R_PrintOK, R_KicOK, StkCode, PosStk, R_PrChkType, R_PrQuan, R_PrSubType, R_PrSubCode, R_PrSubQuan, R_PrSubDisc, R_PrSubBath, R_PrSubAmt, R_PrSubAdj, R_PrCuDisc, R_PrCuBath, R_PrCuAdj, R_QuanCanDisc, R_Order, R_PItemNo, R_PKicQue, R_MemSum, R_MoveItem, R_MoveFrom, R_MoveUser, R_MoveFlag, R_MovePrint, R_Pause, R_PrVcType, R_PrVcCode, R_LinkIndex, R_VoidPause, R_SPIndex, VoidMSG, R_PEName |
| **UPDATE** | r_kicprint, r_pause, r_linkindex, PDAPrintCheck, R_PrType, R_PrCode, R_PrDisc, R_PrBath, R_PrAmt, r_table |
| **DELETE** | ลบตาม R_Table + R_PluCode / ลบตาม R_Index + R_Table / ลบทั้ง R_Table |

---

## 2. `billno` — บิล/ใบเสร็จรับเงิน (Sales Bills)

**ไฟล์หลัก:** `BillControl.java`

| Operation | Columns |
|-----------|---------|
| **INSERT** | B_Refno, B_CuponDiscAmt, B_Ontime, B_LoginTime, B_OnDate, B_PostDate, B_Table, B_MacNo, B_Cashier, B_Cust, B_ETD, B_Total, B_Food, B_Drink, B_Product, B_Service, B_ServiceAmt, B_ItemDiscAmt, B_FastDisc, B_FastDiscAmt, B_EmpDisc, B_EmpDiscAmt, B_TrainDisc, B_TrainDiscAmt, B_MemDisc, B_MemDiscAmt, B_SubDisc, B_SubDiscAmt, B_SubDiscBath, B_ProDiscAmt, B_SpaDiscAmt, B_AdjAmt, B_PreDisAmt, B_NetTotal, B_NetFood, B_NetDrink, B_NetProduct, B_NetVat, B_NetNonVat, B_Vat, B_PayAmt, B_Cash, B_GiftVoucher, B_Earnest, B_Ton, B_CrCode1, B_CardNo1, B_AppCode1, B_CrCharge1, B_CrChargeAmt1, B_CrAmt1, B_AccrCode, B_AccrAmt, B_AccrCr, B_MemCode, B_MemName, B_MemBegin, B_MemEnd, B_MemCurSum, B_Void, B_VoidUser, B_VoidTime, B_BillCopy, B_PrnCnt, B_PrnTime1, B_PrnTime2, B_InvNo, B_InvType, B_Bran, B_BranName, B_Tel, B_RecTime, MStamp, MScore, CurStamp, StampRate, B_ChkBill, B_ChkBillTime, B_CashTime, B_WaitTime, B_SumScore, B_CrBank, B_CrCardAmt, B_CrCurPoint, B_CrSumPoint, B_Entertain, B_VoucherDiscAmt, B_VoucherOver, B_NetDiff, B_SumSetDiscAmt, B_DetailFood, B_DetailDrink, B_DetailProduct, B_KicQue, B_ROUNDCLOSE |
| **UPDATE** | — (ผ่าน Void/Refund ใน RefundBillController) |
| **DELETE** | — |

---

## 3. `t_sale` — รายละเอียดการขายถาวร (Permanent Sale Detail)

**ไฟล์หลัก:** `BillControl.java`

| Operation | Columns |
|-----------|---------|
| **INSERT** | R_Index, R_Refno, R_Table, R_Date, R_Time, MacNo, Cashier, R_Emp, R_PluCode, R_PName, R_Unit, R_Group, R_Status, R_Normal, R_Discount, R_Service, R_Stock, R_Set, R_Vat, R_Type, R_ETD, R_Quan, R_Price, R_Total, R_PrType, R_PrCode, R_PrDisc, R_PrBath, R_PrAmt, R_PrCuType, R_PrCuCode, R_PrCuQuan, R_PrCuAmt, R_Redule, R_DiscBath, R_PrAdj, R_NetTotal, R_Kic, R_KicPrint, R_Refund, VoidMsg, R_Void, R_VoidUser, R_VoidTime, StkCode, PosStk, R_ServiceAmt, R_PrChkType, R_PrQuan, R_PrSubType, R_PrSubCode, R_PrSubQuan, R_PrSubDisc, R_PrSubBath, R_PrSubAmt, R_PrSubAdj, R_PrCuDisc, R_PrCuBath, R_PrCuAdj, R_PrChkType2, R_PrQuan2, R_PrType2, R_PrCode2, R_PrDisc2, R_PrBath2, R_PrAmt2, R_PrAdj2, R_PItemNo, R_PKicQue, R_MoveItem, R_MoveFrom, R_MoveUser, R_MoveFlag, R_Pause, R_SPIndex, R_LinkIndex, R_Opt1~R_Opt9, R_VoidPause |
| **UPDATE** | R_Nettotal, R_ServiceAmt |
| **DELETE** | — |

---

## 4. `kictran` — คิวครัว (Kitchen Queue)

**ไฟล์หลัก:** `PKicTran.java`, `PrintSimpleForm.java`, `PrintToKicController.java`

| Operation | Columns |
|-----------|---------|
| **INSERT (Full)** | PItemNo, PDate, PCode, PIndex, MacNo, Cashier, Emp, PTable, PKic, PTimeIn, PTimeOut, PVoid, PETD, PQty, PFlage, PServe, PServeTime, PWaitTime, PPayment, PInvNo, PWaitServe, PWaitTotal, R_PEName |
| **INSERT (Simple)** | pitemno, pdate, pcode, pqty, pindex, macno, cashier, emp, ptable, ptimein, pvoid, petd, pkic |
| **UPDATE** | pflage, R_ShowDisplayAlert |
| **DELETE** | — |

---

## 5. `tablefile` — สถานะโต๊ะ (Table Status)

**ไฟล์หลัก:** `TableFileControl.java`, `MainSale.java`, `PPrint.java`, `TableMoveControl.java`

| Operation | Columns |
|-----------|---------|
| **INSERT** | Tcode |
| **UPDATE** | TOnAct, macno, TCustomer, tpause, PrintChkBill, NettTotal, TAmount, TCurTime, tcustomer |
| **DELETE** | tcode |

---

## 6. `poshwsetup` — ตั้งค่าเครื่อง POS (Hardware Setup)

**ไฟล์หลัก:** `BillControl.java`, `PosControl.java`

| Operation | Columns |
|-----------|---------|
| **INSERT** | — |
| **UPDATE** | receno1, onact, PaidOutAmt |
| **DELETE** | — |

---

## 7. `posuser` — ผู้ใช้งาน (POS Users)

**ไฟล์หลัก:** `PosControl.java`, `LoginController.java`

| Operation | Columns |
|-----------|---------|
| **INSERT** | — |
| **UPDATE** | onact, macno |
| **DELETE** | — |

---

## 8. `accr` — บัญชีลูกหนี้ (Accounts Receivable)

**ไฟล์หลัก:** `BillControl.java`

| Operation | Columns |
|-----------|---------|
| **INSERT** | ArNo, ArDate, ArCode, ArTotal, ArVat, ArDisc, ArVatMon, ArAccNo, ArMark, ArNet, ArAmount, ArCr, arDue, ArSale, ArRemark, ArPayType, ArDocBill, ArDocPay, ArBank, ArChqNo, ArChqDate, ArAmtPay, ArAmtCr, ArBDate, ArPDate, ArFlage, ArInvNo, ArBran, ArBranPay, ArUserPay |
| **UPDATE** | — |
| **DELETE** | — |

---

## 9. `t_gift` — Gift Voucher ที่ใช้

**ไฟล์หลัก:** `BillControl.java`

| Operation | Columns |
|-----------|---------|
| **INSERT** | ondate, macno, refno, cashier, giftbarcode, gifttype, giftprice, giftmodel, giftlot, giftexp, giftcode, giftno, giftamt, fat |
| **UPDATE** | — |
| **DELETE** | — |

---

## 10. `t_cupon` — คูปองที่ใช้แล้ว (Used Coupons)

**ไฟล์หลัก:** `TCuponControl.java`

| Operation | Columns |
|-----------|---------|
| **INSERT** | R_Index, R_Refno, Terminal, Cashier, Time, CuCode, CuQuan, CuAmt, Refund, CuTextCode, CuTextComment |
| **UPDATE** | — |
| **DELETE** | — |

---

## 11. `tempcupon` — คูปองชั่วคราว (Temp Coupon)

**ไฟล์หลัก:** `TempCuponController.java`

| Operation | Columns |
|-----------|---------|
| **INSERT** | R_Index, R_Table, Terminal, Cashier, Time, CuCode, CuQuan, CuAmt, CuTotal, CuDisc, CuRedule, CuPayment, CuTextCode, CuTextComment |
| **UPDATE** | — |
| **DELETE** | r_index |

---

## 12. `tempgift` — Gift Voucher ชั่วคราว

**ไฟล์หลัก:** `TempCuponController.java`

| Operation | Columns |
|-----------|---------|
| **INSERT** | — (SELECT FROM tempgift แล้ว INSERT ไปยัง t_gift) |
| **UPDATE** | — |
| **DELETE** | ลบทั้งตาราง |

---

## 13. `tempcredit` — บัตรเครดิตชั่วคราว (Temp Credit)

**ไฟล์หลัก:** `AutoXReport.java`, `AutoSumXReport.java`, `CreditReport.java`, `MTDCredit.java`

| Operation | Columns |
|-----------|---------|
| **INSERT** | terminal, crcode, crid, crapp, cramt *(บางกรณีเพิ่ม: mac_no, s_date, ref_no)* |
| **UPDATE** | — |
| **DELETE** | terminal |

---

## 14. `tempset` — ตัวเลือก Option ชั่วคราว (Temp Options)

**ไฟล์หลัก:** `MainSale.java`, `ModalPopup.java`

| Operation | Columns |
|-----------|---------|
| **INSERT** | PTableNo, PIndex, PCode, PDesc, PPostStock, PProTry, POption, PTime |
| **UPDATE** | — |
| **DELETE** | — (ลบโดย Session/Reset) |

---

## 15. `tempeditqty` — ประวัติแก้ไขจำนวน

**ไฟล์หลัก:** `ItemEditQty.java`

| Operation | Columns |
|-----------|---------|
| **INSERT** | OnDate, Time, Emp, Pcode, Pdesc, OldQty, OldPrice, NewQty, NewPrice |
| **UPDATE** | — |
| **DELETE** | — |

---

## 16. `temptopsale` — รายการขายดีชั่วคราว (Temp Top Sale)

**ไฟล์หลัก:** `MTDTopSale.java`, `TopSaleReport.java`

| Operation | Columns |
|-----------|---------|
| **INSERT** | terminal, r_group, r_plucode, r_pname, r_quan, r_total |
| **UPDATE** | — |
| **DELETE** | terminal |

---

## 17. `optionset` — ตัวเลือกสินค้า (Product Options)

**ไฟล์หลัก:** `OptionMenuSetController.java`

| Operation | Columns |
|-----------|---------|
| **INSERT** | PCode, PDesc, OptionCode, OptionName |
| **UPDATE** | — |
| **DELETE** | pcode, optionname |

---

## 18. `mgrbuttonsetup` — ตั้งค่าปุ่มเมนู (Menu Button Setup)

**ไฟล์หลัก:** `MgrButtonController.java`, `SaveMenuIntoBOR.java`

| Operation | Columns |
|-----------|---------|
| **INSERT** | pcode, pdesc, sd_pcode, sd_pdesc, ex_pcode, ex_pdesc, ex_uncode, ex_undesc, auto_pcode, auto_pdesc, check_before, check_qty, qty, check_autoadd, check_extra |
| **UPDATE** | — |
| **DELETE** | pcode |

---

## 19. `soft_menusetup` — ตั้งค่าเมนูซอฟต์แวร์ (Software Menu Setup)

**ไฟล์หลัก:** `MgrButtonController.java`, `SaveMenuIntoBOR.java`

| Operation | Columns |
|-----------|---------|
| **INSERT** | MenuCode, MenuType, OptSet, PSet, PCode, MenuShowText, IMG, FontColor, BGColor, Layout, FontSize, FontName, FontAttr, M_Index, IMG_SIZE |
| **UPDATE** | FontColor, BGColor, Layout, FontSize, FontName, FontAttr |
| **DELETE** | MenuCode / MenuCode + MenuShowText |

---

## 20. `branch` — ข้อมูลสาขา (Branch Info)

**ไฟล์หลัก:** `RefundBillController.java`

| Operation | Columns |
|-----------|---------|
| **INSERT** | — |
| **UPDATE** | returnbillno |
| **DELETE** | — |

---

## 21. `paidiofile` — รายการรับ-จ่ายเงินสด (Cash In/Out)

**ไฟล์หลัก:** `PosControl.java`

| Operation | Columns |
|-----------|---------|
| **INSERT** | — |
| **UPDATE** | PaidOutAmt |
| **DELETE** | — |

---

## 22. `product` — ข้อมูลสินค้า (Products)

**ไฟล์หลัก:** `ProductControl.java`

| Operation | Columns |
|-----------|---------|
| **INSERT** | — |
| **UPDATE** | pkic |
| **DELETE** | — |

---

## สรุปตาราง Temporary (สำหรับย้ายโต๊ะ)

**ไฟล์หลัก:** `TableMoveControl.java`

| Table | Operation | หมายเหตุ |
|-------|-----------|----------|
| `temp_tablefile` | CREATE / DROP / INSERT SELECT | ใช้ระหว่างย้ายโต๊ะ |
| `temp_balance` | CREATE / DROP / INSERT SELECT | ใช้ระหว่างย้ายโต๊ะ |

---

## สรุปภาพรวม

| Table | INSERT | UPDATE | DELETE | ไฟล์หลัก |
|-------|--------|--------|--------|---------|
| balance | ✓ | ✓ | ✓ | BalanceControl.java |
| billno | ✓ | — | — | BillControl.java |
| t_sale | ✓ | ✓ | — | BillControl.java |
| kictran | ✓ | ✓ | — | PKicTran.java, PrintSimpleForm.java |
| tablefile | ✓ | ✓ | ✓ | TableFileControl.java |
| poshwsetup | — | ✓ | — | BillControl.java |
| posuser | — | ✓ | — | PosControl.java |
| accr | ✓ | — | — | BillControl.java |
| t_gift | ✓ | — | — | BillControl.java |
| t_cupon | ✓ | — | — | TCuponControl.java |
| tempcupon | ✓ | — | ✓ | TempCuponController.java |
| tempgift | — | — | ✓ | TempCuponController.java |
| tempcredit | ✓ | — | ✓ | AutoXReport.java, CreditReport.java |
| tempset | ✓ | — | — | MainSale.java, ModalPopup.java |
| tempeditqty | ✓ | — | — | ItemEditQty.java |
| temptopsale | ✓ | — | ✓ | MTDTopSale.java |
| optionset | ✓ | — | ✓ | OptionMenuSetController.java |
| mgrbuttonsetup | ✓ | — | ✓ | MgrButtonController.java |
| soft_menusetup | ✓ | ✓ | ✓ | MgrButtonController.java |
| branch | — | ✓ | — | RefundBillController.java |
| paidiofile | — | ✓ | — | PosControl.java |
| product | — | ✓ | — | ProductControl.java |

---

*สร้างโดย: SqlQueryCatalog.java — วันที่: 2026-06-22*
