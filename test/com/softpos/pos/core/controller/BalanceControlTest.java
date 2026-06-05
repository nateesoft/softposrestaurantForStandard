package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.BalanceBean;
import com.softpos.connection.database.MySQLConnect;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for BalanceControl — ครอบคลุมเฉพาะ method ที่ใช้ query กับ DB
 *
 * Mock: MySQLConnect, ResultSet, Connection, Statement
 * จะไม่แตะ DB จริง ทุก query ถูก intercept โดย Mockito
 */
public class BalanceControlTest {

    @Mock private MySQLConnect mockMysql;
    @Mock private ResultSet mockRs;
    @Mock private Connection mockConnection;
    @Mock private Statement mockStatement;

    private AutoCloseable mocks;
    private BalanceControl control;

    @Before
    public void setUp() throws Exception {
        mocks = MockitoAnnotations.openMocks(this);
        control = new BalanceControl(mockMysql);

        // default stub สำหรับ method ที่หลาย method เรียกใช้เสมอ
        doNothing().when(mockMysql).open(any(Class.class));
        doNothing().when(mockMysql).closeConnection(any(Class.class));
        when(mockMysql.getConnection()).thenReturn(mockConnection);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
    }

    @After
    public void tearDown() throws Exception {
        mocks.close();
    }

    // =========================================================
    // getLastIndex(String tableNo)
    // =========================================================

    @Test
    public void getLastIndex_whenRowFound_returnsRIndex() throws SQLException {
        when(mockMysql.executeQuery(contains("max(R_Index)"))).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getString("R_Index")).thenReturn("T01/005");

        String result = control.getLastIndex("T01");

        assertEquals("T01/005", result);
    }

    @Test
    public void getLastIndex_whenNoRow_returnsEmptyString() throws SQLException {
        when(mockMysql.executeQuery(contains("max(R_Index)"))).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(false);

        String result = control.getLastIndex("T01");

        assertEquals("", result);
    }

    @Test
    public void getLastIndex_whenSQLException_returnsEmptyString() throws SQLException {
        when(mockMysql.executeQuery(anyString())).thenThrow(new SQLException("DB error"));

        String result = control.getLastIndex("T01");

        assertEquals("", result);
    }

    // =========================================================
    // getIndexBalance(String R_Table)
    // =========================================================

    @Test
    public void getIndexBalance_whenTableEmpty_returnsFirstIndex() throws SQLException {
        when(mockMysql.executeQuery(contains("max(R_Index)"))).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getString("R_Index")).thenReturn(null); // ไม่มีข้อมูล

        String result = control.getIndexBalance("T01");

        assertEquals("T01/001", result);
    }

    @Test
    public void getIndexBalance_whenLastIndexIs001_returns002() throws SQLException {
        when(mockMysql.executeQuery(contains("max(R_Index)"))).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getString("R_Index")).thenReturn("T01/001");

        String result = control.getIndexBalance("T01");

        assertEquals("T01/002", result);
    }

    @Test
    public void getIndexBalance_whenLastIndexIs009_returns010() throws SQLException {
        when(mockMysql.executeQuery(contains("max(R_Index)"))).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getString("R_Index")).thenReturn("T01/009");

        String result = control.getIndexBalance("T01");

        assertEquals("T01/010", result);
    }

    @Test
    public void getIndexBalance_whenLastIndexIs099_returns100() throws SQLException {
        when(mockMysql.executeQuery(contains("max(R_Index)"))).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getString("R_Index")).thenReturn("T01/099");

        String result = control.getIndexBalance("T01");

        assertEquals("T01/100", result);
    }

    @Test
    public void getIndexBalance_whenSQLException_returnsFirstIndex() throws SQLException {
        when(mockMysql.executeQuery(anyString())).thenThrow(new SQLException("DB error"));

        String result = control.getIndexBalance("T01");

        assertEquals("T01/001", result);
    }

    @Test
    public void getIndexBalance_whenNoResult_returnsFirstIndex() throws SQLException {
        when(mockMysql.executeQuery(contains("max(R_Index)"))).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(false);

        String result = control.getIndexBalance("T01");

        assertEquals("T01/001", result);
    }

    // =========================================================
    // getAllBalance(String table)
    // =========================================================

    @Test
    public void getAllBalance_whenHasRows_returnsMappedBeans() throws SQLException {
        when(mockMysql.executeQuery(contains("where R_Table='T01'"))).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true, false);
        when(mockRs.getString("R_Index")).thenReturn("T01/001");
        when(mockRs.getString("R_Table")).thenReturn("T01");
        when(mockRs.getString("R_PluCode")).thenReturn("P001");
        when(mockRs.getString("R_PName")).thenReturn("Test Item");
        when(mockRs.getFloat("R_Quan")).thenReturn(2.0f);
        when(mockRs.getFloat("R_Price")).thenReturn(100.0f);
        when(mockRs.getFloat("R_Total")).thenReturn(200.0f);
        stubAllOptFields(mockRs);
        stubAllStringFields(mockRs);

        List<BalanceBean> result = control.getAllBalance("T01");

        assertEquals(1, result.size());
        assertEquals("T01/001", result.get(0).getR_Index());
        assertEquals("T01", result.get(0).getR_Table());
        assertEquals("P001", result.get(0).getR_PluCode());
        assertEquals(2.0f, result.get(0).getR_Quan(), 0.01f);
        assertEquals(200.0f, result.get(0).getR_Total(), 0.01f);
    }

    @Test
    public void getAllBalance_whenNoRows_returnsEmptyList() throws SQLException {
        when(mockMysql.executeQuery(anyString())).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(false);

        List<BalanceBean> result = control.getAllBalance("T01");

        assertTrue(result.isEmpty());
    }

    @Test
    public void getAllBalance_whenSQLException_returnsEmptyList() throws SQLException {
        when(mockMysql.executeQuery(anyString())).thenThrow(new SQLException("DB error"));

        List<BalanceBean> result = control.getAllBalance("T01");

        assertTrue(result.isEmpty());
    }

    // =========================================================
    // getAllBalanceNoVoid(String table)
    // =========================================================

    @Test
    public void getAllBalanceNoVoid_whenHasRows_returnsMappedBeans() throws SQLException {
        when(mockMysql.executeQuery(contains("r_void<>'V'"))).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true, false);
        when(mockRs.getString("R_Index")).thenReturn("T01/001");
        when(mockRs.getString("R_Table")).thenReturn("T01");
        when(mockRs.getString("R_PluCode")).thenReturn("P002");
        when(mockRs.getString("R_PName")).thenReturn("Non-void Item");
        when(mockRs.getFloat("R_Quan")).thenReturn(1.0f);
        when(mockRs.getFloat("R_Price")).thenReturn(50.0f);
        when(mockRs.getFloat("R_Total")).thenReturn(50.0f);
        stubAllOptFields(mockRs);
        stubAllStringFields(mockRs);

        List<BalanceBean> result = control.getAllBalanceNoVoid("T01");

        assertEquals(1, result.size());
        assertEquals("T01/001", result.get(0).getR_Index());
        assertEquals("P002", result.get(0).getR_PluCode());
    }

    @Test
    public void getAllBalanceNoVoid_whenNoRows_returnsEmptyList() throws SQLException {
        when(mockMysql.executeQuery(anyString())).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(false);

        List<BalanceBean> result = control.getAllBalanceNoVoid("T01");

        assertTrue(result.isEmpty());
    }

    // =========================================================
    // getBalanceIndex(String Table, String R_Index)
    // =========================================================

    @Test
    public void getBalanceIndex_table_index_whenFound_returnsMappedBean() throws SQLException {
        when(mockMysql.executeQuery(contains("limit 1"))).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getString("R_Index")).thenReturn("T01/003");
        when(mockRs.getString("R_Table")).thenReturn("T01");
        when(mockRs.getString("R_PluCode")).thenReturn("P003");
        when(mockRs.getString("R_PName")).thenReturn("Found Item");
        when(mockRs.getFloat("R_Quan")).thenReturn(3.0f);
        when(mockRs.getFloat("R_Price")).thenReturn(80.0f);
        when(mockRs.getFloat("R_Total")).thenReturn(240.0f);
        stubAllOptFields(mockRs);
        stubAllStringFields(mockRs);

        BalanceBean result = control.getBalanceIndex("T01", "T01/003");

        assertNotNull(result);
        assertEquals("T01/003", result.getR_Index());
        assertEquals("T01", result.getR_Table());
    }

    @Test
    public void getBalanceIndex_table_index_whenNotFound_returnsNull() throws SQLException {
        when(mockMysql.executeQuery(contains("limit 1"))).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(false);

        BalanceBean result = control.getBalanceIndex("T01", "T01/999");

        assertNull(result);
    }

    @Test
    public void getBalanceIndex_table_index_whenSQLException_returnsNull() throws SQLException {
        when(mockMysql.executeQuery(anyString())).thenThrow(new SQLException("DB error"));

        BalanceBean result = control.getBalanceIndex("T01", "T01/001");

        assertNull(result);
    }

    // =========================================================
    // getBalanceIndex(String R_Index) — overload โดย R_Index เดียว
    // =========================================================

    @Test
    public void getBalanceIndex_byRIndex_whenHasRows_returnsList() throws SQLException {
        when(mockMysql.executeQuery(contains("where R_Index="))).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true, false);
        when(mockRs.getString("R_Index")).thenReturn("T01/001");
        when(mockRs.getString("R_Table")).thenReturn("T01");
        when(mockRs.getString("R_PluCode")).thenReturn("P001");
        when(mockRs.getString("R_PName")).thenReturn("Item");
        stubAllOptFields(mockRs);
        stubAllStringFields(mockRs);

        List<BalanceBean> result = control.getBalanceIndex("T01/001");

        assertEquals(1, result.size());
    }

    // =========================================================
    // getBalanceIndexVoid(String Table)
    // =========================================================

    @Test
    public void getBalanceIndexVoid_whenHasVoidedRows_returnsList() throws SQLException {
        when(mockMysql.executeQuery(contains("r_void='V'"))).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true, false);
        when(mockRs.getString("R_Index")).thenReturn("T01/007");
        when(mockRs.getString("R_Table")).thenReturn("T01");
        when(mockRs.getString("R_Void")).thenReturn("V");
        when(mockRs.getString("R_PluCode")).thenReturn("P007");
        when(mockRs.getString("R_PName")).thenReturn("Voided Item");
        when(mockRs.getFloat("R_Quan")).thenReturn(1.0f);
        when(mockRs.getFloat("R_Price")).thenReturn(0.0f);
        when(mockRs.getFloat("R_Total")).thenReturn(0.0f);
        stubAllOptFields(mockRs);
        stubAllStringFields(mockRs);

        List<BalanceBean> result = control.getBalanceIndexVoid("T01");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("V", result.get(0).getR_Void());
    }

    @Test
    public void getBalanceIndexVoid_whenNoVoidedRows_returnsEmptyList() throws SQLException {
        when(mockMysql.executeQuery(contains("r_void='V'"))).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(false);

        List<BalanceBean> result = control.getBalanceIndexVoid("T01");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // =========================================================
    // GetDiscount(String table)
    // =========================================================

    @Test
    public void getDiscount_whenHasDiscount_returnsFormattedValue() throws SQLException {
        when(mockMysql.executeQuery(contains("sum(R_PrAmt)"))).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getDouble("discount")).thenReturn(150.50);

        String result = control.GetDiscount("T01");

        assertEquals("150.50", result);
    }

    @Test
    public void getDiscount_whenNoRows_returnsZero() throws SQLException {
        when(mockMysql.executeQuery(contains("sum(R_PrAmt)"))).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(false);

        String result = control.GetDiscount("T01");

        assertEquals("0.0", result);
    }

    @Test
    public void getDiscount_whenSQLException_returnsZero() throws SQLException {
        when(mockMysql.executeQuery(anyString())).thenThrow(new SQLException("DB error"));

        String result = control.GetDiscount("T01");

        assertEquals("0.00", result);
    }

    // =========================================================
    // loadTableBalance(String tableNo)
    // =========================================================

    @Test
    public void loadTableBalance_whenHasRows_returnsMappedList() throws SQLException {
        when(mockMysql.executeQuery(contains("limit 500"))).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true, false);
        when(mockRs.getString("r_index")).thenReturn("T01/001");
        when(mockRs.getString("r_linkindex")).thenReturn("");
        when(mockRs.getString("r_void")).thenReturn("");
        when(mockRs.getString("R_ETD")).thenReturn("E");
        when(mockRs.getString("r_pname")).thenReturn("Food A");
        when(mockRs.getString("r_plucode")).thenReturn("P001");
        when(mockRs.getDouble("r_quan")).thenReturn(2.0);
        when(mockRs.getDouble("r_price")).thenReturn(50.0);
        when(mockRs.getDouble("r_total")).thenReturn(100.0);
        when(mockRs.getString("r_kicprint")).thenReturn("");
        when(mockRs.getString("r_kicok")).thenReturn("");
        when(mockRs.getString("r_prtype")).thenReturn("");
        when(mockRs.getString("r_pause")).thenReturn("");
        when(mockRs.getString("r_emp")).thenReturn("E01");
        when(mockRs.getString("r_time")).thenReturn("12:00:00");
        when(mockRs.getString("R_Opt1")).thenReturn("");
        when(mockRs.getString("R_Opt2")).thenReturn("");
        when(mockRs.getString("R_Opt3")).thenReturn("");
        when(mockRs.getString("R_Opt4")).thenReturn("");
        when(mockRs.getString("R_Opt5")).thenReturn("");
        when(mockRs.getString("R_Opt6")).thenReturn("");
        when(mockRs.getString("R_Opt7")).thenReturn("");
        when(mockRs.getString("R_Opt8")).thenReturn("");
        when(mockRs.getString("R_Opt9")).thenReturn("");

        List<BalanceBean> result = control.loadTableBalance("T01");

        assertEquals(1, result.size());
        assertEquals("T01/001", result.get(0).getR_Index());
        assertEquals("Food A", result.get(0).getR_PName());
        assertEquals(100.0, result.get(0).getR_Total(), 0.01);
    }

    @Test
    public void loadTableBalance_whenNoRows_returnsEmptyList() throws SQLException {
        when(mockMysql.executeQuery(anyString())).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(false);

        List<BalanceBean> result = control.loadTableBalance("T01");

        assertTrue(result.isEmpty());
    }

    // =========================================================
    // getBalanceByRLinkIndex(String R_Index)
    // =========================================================

    @Test
    public void getBalanceByRLinkIndex_whenHasRows_returnsRIndexes() throws SQLException {
        when(mockStatement.executeQuery(contains("R_LinkIndex='T01/001'"))).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true, false);
        when(mockRs.getString("R_Index")).thenReturn("T01/002");

        List<BalanceBean> result = control.getBalanceByRLinkIndex("T01/001");

        assertEquals(1, result.size());
        assertEquals("T01/002", result.get(0).getR_Index());
    }

    @Test
    public void getBalanceByRLinkIndex_whenNoRows_returnsEmptyList() throws SQLException {
        when(mockStatement.executeQuery(anyString())).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(false);

        List<BalanceBean> result = control.getBalanceByRLinkIndex("T01/001");

        assertTrue(result.isEmpty());
    }

    // =========================================================
    // setDefaultBalance(String table)  — DELETE query
    // =========================================================

    @Test
    public void setDefaultBalance_executesDeleteQuery() {
        when(mockMysql.executeUpdate(contains("delete from balance where R_Table='T01'"))).thenReturn(1);

        control.setDefaultBalance("T01");

        verify(mockMysql).executeUpdate(contains("delete from balance where R_Table='T01'"));
    }

    // =========================================================
    // deleteBalance(String, String, String)  — DELETE query
    // =========================================================

    @Test
    public void deleteBalance_executesDeleteWithCorrectParams() throws SQLException {
        when(mockStatement.executeUpdate(anyString())).thenReturn(1);

        control.deleteBalance("T01", "P001", "T01/001");

        verify(mockStatement).executeUpdate(contains("delete from balance"));
        verify(mockStatement).executeUpdate(contains("R_Table = 'T01'"));
        verify(mockStatement).executeUpdate(contains("R_PluCode = 'P001'"));
    }

    // =========================================================
    // deleteProduct(String, String, String)  — DELETE query
    // =========================================================

    @Test
    public void deleteProduct_executesDeleteWithCorrectIndex() throws SQLException {
        when(mockStatement.executeUpdate(anyString())).thenReturn(1);

        control.deleteProduct("T01", "P001", "T01/001");

        verify(mockStatement).executeUpdate(contains("delete from balance"));
        verify(mockStatement).executeUpdate(contains("R_Index='T01/001'"));
    }

    // =========================================================
    // updateTableHold(String, String)  — UPDATE query
    // =========================================================

    @Test
    public void updateTableHold_executesUpdateQuery() {
        when(mockMysql.executeUpdate(contains("update tablefile"))).thenReturn(1);

        control.updateTableHold("T01", "E01");

        verify(mockMysql).executeUpdate(contains("update tablefile"));
        verify(mockMysql).executeUpdate(contains("tcode='T01'"));
    }

    // =========================================================
    // updatePDACheck(String)  — UPDATE query
    // =========================================================

    @Test
    public void updatePDACheck_executesUpdateForTable() {
        when(mockMysql.executeUpdate(contains("PDAPrintCheck='N'"))).thenReturn(1);

        control.updatePDACheck("T01");

        verify(mockMysql).executeUpdate(contains("PDAPrintCheck='N'"));
        verify(mockMysql).executeUpdate(contains("r_table='T01'"));
    }

    // =========================================================
    // backupBalance(String)  — DELETE + INSERT queries
    // =========================================================

    @Test
    public void backupBalance_clearsAndInsertsToTempBalance() throws SQLException {
        when(mockStatement.executeUpdate(anyString())).thenReturn(1);

        boolean result = control.backupBalance("T01");

        assertTrue(result);
        verify(mockStatement).executeUpdate("delete from temp_balance");
        verify(mockStatement).executeUpdate(contains("insert into temp_balance select * from balance where R_Table='T01'"));
    }

    @Test
    public void backupBalance_whenSQLException_returnsFalse() throws SQLException {
        when(mockStatement.executeUpdate(anyString())).thenThrow(new SQLException("DB error"));
        // ป้องกัน exception ตอน create temp_balance ใน catch block
        when(mockConnection.createStatement()).thenReturn(mockStatement);

        boolean result = control.backupBalance("T01");

        assertFalse(result);
    }

    // =========================================================
    // restoreBalance(String, String)  — DELETE + INSERT queries
    // =========================================================

    @Test
    public void restoreBalance_executesRestoreQueries() throws SQLException {
        when(mockStatement.executeUpdate(anyString())).thenReturn(1);

        boolean result = control.restoreBalance("T01", "T02");

        assertTrue(result);
        verify(mockStatement).executeUpdate(contains("delete from balance where R_Table='T01'"));
        verify(mockStatement).executeUpdate(contains("delete from balance where R_Table='T02'"));
        verify(mockStatement).executeUpdate(contains("insert into balance select * from temp_balance"));
        verify(mockStatement).executeUpdate("delete from temp_balance");
    }

    // =========================================================
    // Helper methods สำหรับ stub ค่า field ที่ใช้ทั่วไป
    // =========================================================

    private void stubAllOptFields(ResultSet rs) throws SQLException {
        when(rs.getString("R_Opt1")).thenReturn("");
        when(rs.getString("R_Opt2")).thenReturn("");
        when(rs.getString("R_Opt3")).thenReturn("");
        when(rs.getString("R_Opt4")).thenReturn("");
        when(rs.getString("R_Opt5")).thenReturn("");
        when(rs.getString("R_Opt6")).thenReturn("");
        when(rs.getString("R_Opt7")).thenReturn("");
        when(rs.getString("R_Opt8")).thenReturn("");
        when(rs.getString("R_Opt9")).thenReturn("");
        when(rs.getString("VoidMSG")).thenReturn("");
    }

    private void stubAllStringFields(ResultSet rs) throws SQLException {
        lenient().when(rs.getString("R_Time")).thenReturn("12:00:00");
        lenient().when(rs.getString("Macno")).thenReturn("001");
        lenient().when(rs.getString("Cashier")).thenReturn("admin");
        lenient().when(rs.getString("R_Emp")).thenReturn("E01");
        lenient().when(rs.getString("R_Unit")).thenReturn("pcs");
        lenient().when(rs.getString("R_Group")).thenReturn("FOOD");
        lenient().when(rs.getString("R_Status")).thenReturn("Y");
        lenient().when(rs.getString("R_Normal")).thenReturn("N");
        lenient().when(rs.getString("R_Discount")).thenReturn("Y");
        lenient().when(rs.getString("R_Service")).thenReturn("Y");
        lenient().when(rs.getString("R_Stock")).thenReturn("N");
        lenient().when(rs.getString("R_Set")).thenReturn("N");
        lenient().when(rs.getString("R_Vat")).thenReturn("V");
        lenient().when(rs.getString("R_Type")).thenReturn("N");
        lenient().when(rs.getString("R_ETD")).thenReturn("E");
        lenient().when(rs.getString("R_PrType")).thenReturn("");
        lenient().when(rs.getString("R_PrCode")).thenReturn("");
        lenient().when(rs.getString("R_PrCuType")).thenReturn("");
        lenient().when(rs.getString("R_Kic")).thenReturn("K1");
        lenient().when(rs.getString("R_KicPrint")).thenReturn("");
        lenient().when(rs.getString("R_Void")).thenReturn("");
        lenient().when(rs.getString("R_VoidUser")).thenReturn("");
        lenient().when(rs.getString("R_VoidTime")).thenReturn("");
        lenient().when(rs.getString("R_PrCuCode")).thenReturn("");
        lenient().when(rs.getString("R_Serve")).thenReturn("");
        lenient().when(rs.getString("R_PrintOK")).thenReturn("Y");
        lenient().when(rs.getString("R_KicOK")).thenReturn("");
        lenient().when(rs.getString("StkCode")).thenReturn("");
        lenient().when(rs.getString("PosStk")).thenReturn("0");
        lenient().when(rs.getString("R_PrChkType")).thenReturn("");
        lenient().when(rs.getString("R_PrSubType")).thenReturn("");
        lenient().when(rs.getString("R_PrSubCode")).thenReturn("");
        lenient().when(rs.getString("R_Order")).thenReturn("1");
        lenient().when(rs.getString("R_MemSum")).thenReturn("N");
        lenient().when(rs.getString("R_MoveItem")).thenReturn("N");
        lenient().when(rs.getString("R_MoveFrom")).thenReturn("");
        lenient().when(rs.getString("R_MoveUser")).thenReturn("");
        lenient().when(rs.getString("R_MoveFlag")).thenReturn("0");
        lenient().when(rs.getString("R_MovePrint")).thenReturn("N");
        lenient().when(rs.getString("R_Pause")).thenReturn("");
        lenient().when(rs.getString("R_LinkIndex")).thenReturn("");
        lenient().when(rs.getString("R_SPIndex")).thenReturn("");
        lenient().when(rs.getString("R_PEName")).thenReturn("");
        lenient().when(rs.getFloat("R_PrDisc")).thenReturn(0f);
        lenient().when(rs.getFloat("R_PrBath")).thenReturn(0f);
        lenient().when(rs.getFloat("R_PrAmt")).thenReturn(0f);
        lenient().when(rs.getFloat("R_DiscBath")).thenReturn(0f);
        lenient().when(rs.getFloat("R_PrCuQuan")).thenReturn(0f);
        lenient().when(rs.getFloat("R_PrCuAmt")).thenReturn(0f);
        lenient().when(rs.getFloat("R_Redule")).thenReturn(0f);
        lenient().when(rs.getFloat("R_PrQuan")).thenReturn(0f);
        lenient().when(rs.getFloat("R_PrSubQuan")).thenReturn(0f);
        lenient().when(rs.getFloat("R_PrSubDisc")).thenReturn(0f);
        lenient().when(rs.getFloat("R_PrSubBath")).thenReturn(0f);
        lenient().when(rs.getFloat("R_PrSubAmt")).thenReturn(0f);
        lenient().when(rs.getFloat("R_PrSubAdj")).thenReturn(0f);
        lenient().when(rs.getFloat("R_PrCuDisc")).thenReturn(0f);
        lenient().when(rs.getFloat("R_PrCuBath")).thenReturn(0f);
        lenient().when(rs.getFloat("R_PrCuAdj")).thenReturn(0f);
        lenient().when(rs.getFloat("R_QuanCanDisc")).thenReturn(0f);
        lenient().when(rs.getInt("R_PItemNo")).thenReturn(0);
        lenient().when(rs.getInt("R_PKicQue")).thenReturn(0);
    }
}
