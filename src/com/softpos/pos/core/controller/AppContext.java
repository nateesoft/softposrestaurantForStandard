package com.softpos.pos.core.controller;

public class AppContext {

    private static BillControl billControl;
    private static BranchControl branchControl;
    private static BranchFileControl branchFileController;
    private static PaymentControl checkBillController;
    private static CouponDiscountControl couponDiscountController;
    private static CuponControl cuponControl;
    private static CuponListControl cuponListControl;
    private static DiscountControl discountControl;
    private static EmployControl employControl;
    private static EmployeeControl employeeControl;
    private static FloorPlanControl floorPlanController;
    private static IngedientControl ingedientController;
    private static ItemDisControl itemDisControl;
    private static LoginControl loginController;
    private static MPluControl mpluController;
    private static MTranControl mtranController;
    private static MainSaleControl mainSaleController;
    private static MemberControl memberControl;
    private static MemmaterControl memmaterController;
    private static ModalPopupControl modalPopupController;
    private static PosControl posControl;
    private static PosUserControl posUserController;
    private static PrintToKicControl printToKicController;
    private static ProductControl productControl;
    private static GroupFileControl groupFileControl;
    private static PromotionControl promotionControl;
    private static PropControl propControl;
    private static RefundBillControl refundBillController;
    private static ServiceControl serviceControl;
    private static StockControl stockControl;
    private static TCuponControl tcuponControl;
    private static TSaleControl tsaleController;
    private static TableFileControl tableFileControl;
    private static TableSetupControl tableSetupControl;
    private static TempCuponControl tempCuponController;
    private static WebServiceControl webServiceControl;
    private static sendMenuButttonToBorControl sendMenuButttonToBorControllerInstance;
    private static CreditFileControl creditFileController;
    private static DatabaseConnection databaseConnection;
    private static PointTypeControl pointTypeController;
    private static TableMoveControl tableMoveControl;
    private static MgrButtonControl mgrButtonController;
    private static OptionMsgControl optionMsgController;
    private static OptionMenuSetControl optionMenuSetController;
    private static ItemEditQtyControl itemEditQtyController;
    private static PosHwSetupControl posHwSetupControl;
    private static TempSetControl tempSetControl;

    private AppContext() {}
    
    public static TempSetControl getTempSetControl() {
        if (tempSetControl == null) tempSetControl = new TempSetControl();
        return tempSetControl;
    }
    
    public static PosHwSetupControl getPosHwSetupControl() {
        if (posHwSetupControl == null) posHwSetupControl = new PosHwSetupControl();
        return posHwSetupControl;
    }

    public static CreditFileControl getCreditFileController() {
        if (creditFileController == null) creditFileController = new CreditFileControl();
        return creditFileController;
    }

    public static PointTypeControl getPointTypeController() {
        if (pointTypeController == null) pointTypeController = new PointTypeControl();
        return pointTypeController;
    }

    public static TableMoveControl getTableMoveControl() {
        if (tableMoveControl == null) tableMoveControl = new TableMoveControl();
        return tableMoveControl;
    }

    public static BalanceControl getBalanceControl() {
        return new BalanceControl();
    }

    public static BillControl getBillControl() {
        if (billControl == null) billControl = new BillControl();
        return billControl;
    }
    
    public static BranchControl getBranchControl() {
        if (branchControl == null) branchControl = new BranchControl();
        return branchControl;
    }

    public static BranchFileControl getBranchFileController() {
        if (branchFileController == null) branchFileController = new BranchFileControl();
        return branchFileController;
    }

    public static PaymentControl getCheckBillController() {
        if (checkBillController == null) checkBillController = new PaymentControl();
        return checkBillController;
    }

    public static CouponDiscountControl getCouponDiscountController() {
        if (couponDiscountController == null) couponDiscountController = new CouponDiscountControl();
        return couponDiscountController;
    }

    public static CuponControl getCuponControl() {
        if (cuponControl == null) cuponControl = new CuponControl();
        return cuponControl;
    }

    public static CuponListControl getCuponListControl() {
        if (cuponListControl == null) cuponListControl = new CuponListControl();
        return cuponListControl;
    }

    public static DiscountControl getDiscountControl() {
        if (discountControl == null) discountControl = new DiscountControl();
        return discountControl;
    }

    public static EmployControl getEmployControl() {
        if (employControl == null) employControl = new EmployControl();
        return employControl;
    }

    public static EmployeeControl getEmployeeControl() {
        if (employeeControl == null) employeeControl = new EmployeeControl();
        return employeeControl;
    }

    public static FloorPlanControl getFloorPlanController() {
        if (floorPlanController == null) floorPlanController = new FloorPlanControl();
        return floorPlanController;
    }

    public static IngedientControl getIngedientController() {
        if (ingedientController == null) ingedientController = new IngedientControl();
        return ingedientController;
    }

    public static ItemDisControl getItemDisControl() {
        if (itemDisControl == null) itemDisControl = new ItemDisControl();
        return itemDisControl;
    }

    public static LoginControl getLoginController() {
        if (loginController == null) loginController = new LoginControl();
        return loginController;
    }

    public static MPluControl getMPluController() {
        if (mpluController == null) mpluController = new MPluControl();
        return mpluController;
    }

    public static MTranControl getMTranController() {
        if (mtranController == null) mtranController = new MTranControl();
        return mtranController;
    }

    public static MainSaleControl getMainSaleController() {
        if (mainSaleController == null) mainSaleController = new MainSaleControl();
        return mainSaleController;
    }

    public static MemberControl getMemberControl() {
        if (memberControl == null) memberControl = new MemberControl();
        return memberControl;
    }

    public static MemmaterControl getMemmaterController() {
        if (memmaterController == null) memmaterController = new MemmaterControl();
        return memmaterController;
    }

    public static ModalPopupControl getModalPopupController() {
        if (modalPopupController == null) modalPopupController = new ModalPopupControl();
        return modalPopupController;
    }

    public static PosControl getPosControl() {
        if (posControl == null) posControl = new PosControl();
        return posControl;
    }

    public static PosUserControl getPosUserController() {
        if (posUserController == null) posUserController = new PosUserControl();
        return posUserController;
    }

    public static PrintToKicControl getPrintToKicController() {
        if (printToKicController == null) printToKicController = new PrintToKicControl();
        return printToKicController;
    }

    public static ProductControl getProductControl() {
        if (productControl == null) productControl = new ProductControl();
        return productControl;
    }
    
    public static GroupFileControl getGroupFileControl() {
        if (groupFileControl == null) groupFileControl = new GroupFileControl();
        return groupFileControl;
    }

    public static PromotionControl getPromotionControl() {
        if (promotionControl == null) promotionControl = new PromotionControl();
        return promotionControl;
    }

    public static PropControl getPropControl() {
        if (propControl == null) propControl = new PropControl();
        return propControl;
    }

    public static RefundBillControl getRefundBillController() {
        if (refundBillController == null) refundBillController = new RefundBillControl();
        return refundBillController;
    }

    public static ServiceControl getServiceControl() {
        if (serviceControl == null) serviceControl = new ServiceControl();
        return serviceControl;
    }

    public static StockControl getStockControl() {
        if (stockControl == null) stockControl = new StockControl();
        return stockControl;
    }

    public static TCuponControl getTCuponControl() {
        if (tcuponControl == null) tcuponControl = new TCuponControl();
        return tcuponControl;
    }

    public static TSaleControl getTSaleController() {
        if (tsaleController == null) tsaleController = new TSaleControl();
        return tsaleController;
    }

    public static TableFileControl getTableFileControl() {
        if (tableFileControl == null) tableFileControl = new TableFileControl();
        return tableFileControl;
    }

    public static TableSetupControl getTableSetupControl() {
        if (tableSetupControl == null) tableSetupControl = new TableSetupControl();
        return tableSetupControl;
    }

    public static TempCuponControl getTempCuponController() {
        if (tempCuponController == null) tempCuponController = new TempCuponControl();
        return tempCuponController;
    }

    public static WebServiceControl getWebServiceControl() {
        if (webServiceControl == null) webServiceControl = new WebServiceControl();
        return webServiceControl;
    }

    public static sendMenuButttonToBorControl getSendMenuButttonToBorController() {
        if (sendMenuButttonToBorControllerInstance == null) sendMenuButttonToBorControllerInstance = new sendMenuButttonToBorControl();
        return sendMenuButttonToBorControllerInstance;
    }
    
    public static DatabaseConnection getDatabaseConnection() {
        if (databaseConnection == null) databaseConnection = new DatabaseConnection();
        return databaseConnection;
    }

    public static MgrButtonControl getMgrButtonController() {
        if (mgrButtonController == null) mgrButtonController = new MgrButtonControl();
        return mgrButtonController;
    }

    public static OptionMsgControl getOptionMsgController() {
        if (optionMsgController == null) optionMsgController = new OptionMsgControl();
        return optionMsgController;
    }

    public static OptionMenuSetControl getOptionMenuSetController() {
        if (optionMenuSetController == null) optionMenuSetController = new OptionMenuSetControl();
        return optionMenuSetController;
    }

    public static ItemEditQtyControl getItemEditQtyController() {
        if (itemEditQtyController == null) itemEditQtyController = new ItemEditQtyControl();
        return itemEditQtyController;
    }
}
