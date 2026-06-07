package com.softpos.pos.core.controller;

public class AppContext {

    private static BillControl billControl;
    private static BranchControl branchControl;
    private static BranchFileController branchFileController;
    private static CheckBillController checkBillController;
    private static CouponDiscountController couponDiscountController;
    private static CuponControl cuponControl;
    private static CuponListControl cuponListControl;
    private static DiscountControl discountControl;
    private static EmployControl employControl;
    private static EmployeeControl employeeControl;
    private static FloorPlanController floorPlanController;
    private static IngedientController ingedientController;
    private static ItemDisControl itemDisControl;
    private static LoginController loginController;
    private static MPluController mpluController;
    private static MTranController mtranController;
    private static MainSaleController mainSaleController;
    private static MemberControl memberControl;
    private static MemmaterController memmaterController;
    private static ModalPopupController modalPopupController;
    private static PosControl posControl;
    private static PosUserController posUserController;
    private static PrintToKicController printToKicController;
    private static ProductControl productControl;
    private static GroupFileControl groupFileControl;
    private static PromotionControl promotionControl;
    private static PropControl propControl;
    private static RefundBillController refundBillController;
    private static ServiceControl serviceControl;
    private static StockControl stockControl;
    private static TCuponControl tcuponControl;
    private static TSaleController tsaleController;
    private static TableFileControl tableFileControl;
    private static TableSetupControl tableSetupControl;
    private static TempCuponController tempCuponController;
    private static WebServiceControl webServiceControl;
    private static sendMenuButttonToBorController sendMenuButttonToBorControllerInstance;
    private static CreditFileController creditFileController;
    private static DatabaseConnection databaseConnection;
    private static PointTypeController pointTypeController;
    private static TableMoveControl tableMoveControl;
    private static MgrButtonController mgrButtonController;
    private static OptionMsgController optionMsgController;
    private static OptionMenuSetController optionMenuSetController;
    private static ItemEditQtyController itemEditQtyController;

    private AppContext() {}

    public static CreditFileController getCreditFileController() {
        if (creditFileController == null) creditFileController = new CreditFileController();
        return creditFileController;
    }

    public static PointTypeController getPointTypeController() {
        if (pointTypeController == null) pointTypeController = new PointTypeController();
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

    public static BranchFileController getBranchFileController() {
        if (branchFileController == null) branchFileController = new BranchFileController();
        return branchFileController;
    }

    public static CheckBillController getCheckBillController() {
        if (checkBillController == null) checkBillController = new CheckBillController();
        return checkBillController;
    }

    public static CouponDiscountController getCouponDiscountController() {
        if (couponDiscountController == null) couponDiscountController = new CouponDiscountController();
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

    public static FloorPlanController getFloorPlanController() {
        if (floorPlanController == null) floorPlanController = new FloorPlanController();
        return floorPlanController;
    }

    public static IngedientController getIngedientController() {
        if (ingedientController == null) ingedientController = new IngedientController();
        return ingedientController;
    }

    public static ItemDisControl getItemDisControl() {
        if (itemDisControl == null) itemDisControl = new ItemDisControl();
        return itemDisControl;
    }

    public static LoginController getLoginController() {
        if (loginController == null) loginController = new LoginController();
        return loginController;
    }

    public static MPluController getMPluController() {
        if (mpluController == null) mpluController = new MPluController();
        return mpluController;
    }

    public static MTranController getMTranController() {
        if (mtranController == null) mtranController = new MTranController();
        return mtranController;
    }

    public static MainSaleController getMainSaleController() {
        if (mainSaleController == null) mainSaleController = new MainSaleController();
        return mainSaleController;
    }

    public static MemberControl getMemberControl() {
        if (memberControl == null) memberControl = new MemberControl();
        return memberControl;
    }

    public static MemmaterController getMemmaterController() {
        if (memmaterController == null) memmaterController = new MemmaterController();
        return memmaterController;
    }

    public static ModalPopupController getModalPopupController() {
        if (modalPopupController == null) modalPopupController = new ModalPopupController();
        return modalPopupController;
    }

    public static PosControl getPosControl() {
        if (posControl == null) posControl = new PosControl();
        return posControl;
    }

    public static PosUserController getPosUserController() {
        if (posUserController == null) posUserController = new PosUserController();
        return posUserController;
    }

    public static PrintToKicController getPrintToKicController() {
        if (printToKicController == null) printToKicController = new PrintToKicController();
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

    public static RefundBillController getRefundBillController() {
        if (refundBillController == null) refundBillController = new RefundBillController();
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

    public static TSaleController getTSaleController() {
        if (tsaleController == null) tsaleController = new TSaleController();
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

    public static TempCuponController getTempCuponController() {
        if (tempCuponController == null) tempCuponController = new TempCuponController();
        return tempCuponController;
    }

    public static WebServiceControl getWebServiceControl() {
        if (webServiceControl == null) webServiceControl = new WebServiceControl();
        return webServiceControl;
    }

    public static sendMenuButttonToBorController getSendMenuButttonToBorController() {
        if (sendMenuButttonToBorControllerInstance == null) sendMenuButttonToBorControllerInstance = new sendMenuButttonToBorController();
        return sendMenuButttonToBorControllerInstance;
    }
    
    public static DatabaseConnection getDatabaseConnection() {
        if (databaseConnection == null) databaseConnection = new DatabaseConnection();
        return databaseConnection;
    }

    public static MgrButtonController getMgrButtonController() {
        if (mgrButtonController == null) mgrButtonController = new MgrButtonController();
        return mgrButtonController;
    }

    public static OptionMsgController getOptionMsgController() {
        if (optionMsgController == null) optionMsgController = new OptionMsgController();
        return optionMsgController;
    }

    public static OptionMenuSetController getOptionMenuSetController() {
        if (optionMenuSetController == null) optionMenuSetController = new OptionMenuSetController();
        return optionMenuSetController;
    }

    public static ItemEditQtyController getItemEditQtyController() {
        if (itemEditQtyController == null) itemEditQtyController = new ItemEditQtyController();
        return itemEditQtyController;
    }
}
