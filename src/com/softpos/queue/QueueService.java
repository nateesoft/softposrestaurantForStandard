package com.softpos.queue;

import com.softpos.connection.database.ConfigFile;
import com.softpos.connection.database.MySQLConnect;
import com.softpos.constants.PublicVar;
import com.softpos.util.AppLogUtil;
import com.softpos.util.ThaiUtil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Queue (running number) support for connect.ini businessType=FoodCenter.
 * All methods are safe to call when the business type is not FoodCenter or the
 * DB is unavailable: they log and return a neutral value.
 */
public final class QueueService {

    public static final String PENDING = "Pending";
    public static final String PREPARE = "Prepare";
    public static final String SERVE = "Serve";
    public static final String DONE = "Done";
    public static final String CANCEL = "Cancel";

    private static final String CREATE_SQL
            = "CREATE TABLE IF NOT EXISTS queue_works ("
            + "queue_id INT UNSIGNED NOT NULL AUTO_INCREMENT,"
            + "queue_date DATE NOT NULL,"
            + "queue_no INT UNSIGNED NOT NULL,"
            + "queue_createdate DATETIME NOT NULL,"
            + "queue_status VARCHAR(10) NOT NULL DEFAULT 'Pending',"
            + "queue_from_table VARCHAR(20) NOT NULL DEFAULT '',"
            + "queue_billno VARCHAR(30) NOT NULL DEFAULT '',"
            + "queue_customer VARCHAR(100) NOT NULL DEFAULT '',"
            + "queue_macno VARCHAR(10) NOT NULL DEFAULT '',"
            + "queue_cashier VARCHAR(30) NOT NULL DEFAULT '',"
            + "queue_prepare_date DATETIME NULL,"
            + "queue_serve_date DATETIME NULL,"
            + "queue_done_date DATETIME NULL,"
            + "PRIMARY KEY (queue_id),"
            + "UNIQUE KEY uq_queue_day_no (queue_date, queue_no),"
            + "KEY idx_queue_status (queue_date, queue_status)"
            + ") ENGINE=InnoDB DEFAULT CHARSET=latin1";

    /** One row of the queue board. */
    public static class Entry {
        public final int id;
        public final int no;
        public final String status;
        public final String fromTable;
        public final String customer;

        Entry(int id, int no, String status, String fromTable, String customer) {
            this.id = id;
            this.no = no;
            this.status = status;
            this.fromTable = fromTable;
            this.customer = customer;
        }
    }

    private static Boolean foodCenter;
    private static volatile int kitchenQueueNo = 0;
    private static volatile int receiptQueueNo = 0;

    private QueueService() {
    }

    public static synchronized boolean isFoodCenter() {
        if (foodCenter == null) {
            foodCenter = "FoodCenter".equalsIgnoreCase(ConfigFile.getProperties("businessType"));
        }
        return foodCenter;
    }

    /** Migration: create queue_works when it does not exist yet. Call once at startup. */
    public static void ensureTable() {
        if (!isFoodCenter()) {
            return;
        }
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(QueueService.class);
        try (Statement st = mysql.getConnection().createStatement()) {
            st.executeUpdate(CREATE_SQL);
            AppLogUtil.info("QueueService.ensureTable: queue_works ready");
        } catch (SQLException e) {
            AppLogUtil.log(QueueService.class, "error", e);
        } finally {
            mysql.closeConnection(QueueService.class);
        }
    }

    /**
     * Allocates the next queue number of today (restarts at 1 every day).
     *
     * @return the new queue id, or -1 on failure
     */
    public static int createQueue(String fromTable, String customer) {
        String sql = "INSERT INTO queue_works (queue_date, queue_no, queue_createdate, queue_status,"
                + " queue_from_table, queue_customer, queue_macno, queue_cashier) "
                + "SELECT CURDATE(), IFNULL(MAX(queue_no),0)+1, NOW(), ?, ?, ?, ?, ? "
                + "FROM queue_works WHERE queue_date = CURDATE()";
        for (int attempt = 0; attempt < 5; attempt++) {
            MySQLConnect mysql = new MySQLConnect();
            mysql.open(QueueService.class);
            try (PreparedStatement ps = mysql.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, PENDING);
                ps.setString(2, ThaiUtil.Unicode2ASCII(fromTable));
                ps.setString(3, ThaiUtil.Unicode2ASCII(customer == null ? "" : customer));
                ps.setString(4, nz(PublicVar.MACNO));
                ps.setString(5, nz(PublicVar.CASHIER));
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            } catch (SQLException e) {
                if (e.getErrorCode() == 1062) { // two cashiers got the same number: retry
                    continue;
                }
                AppLogUtil.log(QueueService.class, "error", e);
                return -1;
            } finally {
                mysql.closeConnection(QueueService.class);
            }
        }
        return -1;
    }

    public static int getQueueNo(int queueId) {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(QueueService.class);
        try (PreparedStatement ps = mysql.getConnection().prepareStatement(
                "SELECT queue_no FROM queue_works WHERE queue_id = ?")) {
            ps.setInt(1, queueId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            AppLogUtil.log(QueueService.class, "error", e);
            return 0;
        } finally {
            mysql.closeConnection(QueueService.class);
        }
    }

    public static void setBillNo(int queueId, String billNo) {
        update("UPDATE queue_works SET queue_billno = ? WHERE queue_id = ?", billNo == null ? "" : billNo, queueId);
    }

    public static void setStatus(int queueId, String status) {
        String col;
        switch (status) {
            case PREPARE:
                col = ", queue_prepare_date = NOW()";
                break;
            case SERVE:
                col = ", queue_serve_date = NOW()";
                break;
            case DONE:
                col = ", queue_done_date = NOW()";
                break;
            default:
                col = "";
        }
        update("UPDATE queue_works SET queue_status = ?" + col + " WHERE queue_id = ?", status, queueId);
    }

    private static void update(String sql, String s, int id) {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(QueueService.class);
        try (PreparedStatement ps = mysql.getConnection().prepareStatement(sql)) {
            ps.setString(1, s);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            AppLogUtil.log(QueueService.class, "error", e);
        } finally {
            mysql.closeConnection(QueueService.class);
        }
    }

    /** Today's queues that are not finished yet (Pending, Prepare, Serve), oldest first. */
    public static List<Entry> listActive() {
        List<Entry> list = new ArrayList<>();
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(QueueService.class);
        try (PreparedStatement ps = mysql.getConnection().prepareStatement(
                "SELECT queue_id, queue_no, queue_status, queue_from_table, queue_customer FROM queue_works "
                + "WHERE queue_date = CURDATE() AND queue_status IN ('Pending','Prepare','Serve') "
                + "ORDER BY queue_no");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Entry(rs.getInt(1), rs.getInt(2), rs.getString(3),
                        ThaiUtil.ASCII2Unicode(rs.getString(4)), ThaiUtil.ASCII2Unicode(rs.getString(5))));
            }
        } catch (SQLException e) {
            AppLogUtil.log(QueueService.class, "error", e);
        } finally {
            mysql.closeConnection(QueueService.class);
        }
        return list;
    }

    /** Queue number printed on the kitchen ticket while a FoodCenter payment is being printed (0 = none). */
    public static void setKitchenQueueNo(int no) {
        kitchenQueueNo = no;
    }

    public static int getKitchenQueueNo() {
        return kitchenQueueNo;
    }

    /** Queue number printed on the receipt while a FoodCenter bill is being saved; same as the kitchen ticket (0 = none). */
    public static void setReceiptQueueNo(int no) {
        receiptQueueNo = no;
    }

    public static int getReceiptQueueNo() {
        return receiptQueueNo;
    }

    /** Text appended to the table header of kitchen tickets, e.g. "  คิว 12"; empty when no queue is active. */
    public static String kitchenSuffix() {
        return kitchenQueueNo > 0 ? "   คิว " + kitchenQueueNo : "";
    }

    private static String nz(String s) {
        return s == null ? "" : s;
    }
}
