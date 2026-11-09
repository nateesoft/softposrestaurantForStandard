package com.softpos.printer.template;

import com.softpos.connection.database.MySQLConnect;
import com.softpos.util.AppLogUtil;
import com.softpos.util.ThaiUtil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** Storage of the per-kitchen ticket templates (table kitchen_templates). */
public final class KitchenTemplateService {

    public static final int KITCHEN_COUNT = 20;

    public static final String DEFAULT_TEMPLATE
            = "[c][s5]{shop_name}\n"
            + "[c][s5]โต๊ะ {table}   คิว {queue_no}\n"
            + "[c][s4]*** {order_type} ***\n"
            + "[l][s3]ลูกค้า: {customer}   CC. {cust_count}\n"
            + "[l][s3]{hr}\n"
            + "{#items}\n"
            + "[l][s5]{qty} x {name}\n"
            + "{#options}\n"
            + "[l][s4]    *** {opt}\n"
            + "{/options}\n"
            + "{/items}\n"
            + "[l][s3]{hr}\n"
            + "[l][s3]{date} {time}  Mac {macno}/{cashier}\n"
            + "[c][s3]{kitchen}\n";

    private static final String CREATE_SQL
            = "CREATE TABLE IF NOT EXISTS kitchen_templates ("
            + "kic_no VARCHAR(5) NOT NULL,"
            + "enabled CHAR(1) NOT NULL DEFAULT 'N',"
            + "template TEXT NOT NULL,"
            + "updated_at DATETIME NULL,"
            + "PRIMARY KEY (kic_no)"
            + ") ENGINE=InnoDB DEFAULT CHARSET=latin1";

    /** A stored template. */
    public static class Template {
        public final boolean enabled;
        public final String text;

        Template(boolean enabled, String text) {
            this.enabled = enabled;
            this.text = text;
        }
    }

    private KitchenTemplateService() {
    }

    /** Migration: create kitchen_templates when missing. Call once at startup. */
    public static void ensureTable() {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(KitchenTemplateService.class);
        try (Statement st = mysql.getConnection().createStatement()) {
            st.executeUpdate(CREATE_SQL);
        } catch (SQLException e) {
            AppLogUtil.log(KitchenTemplateService.class, "error", e);
        } finally {
            mysql.closeConnection(KitchenTemplateService.class);
        }
    }

    /** The stored template of a kitchen, or null when none was saved. */
    public static Template load(String kicNo) {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(KitchenTemplateService.class);
        try (PreparedStatement ps = mysql.getConnection().prepareStatement(
                "SELECT enabled, template FROM kitchen_templates WHERE kic_no = ?")) {
            ps.setString(1, kicNo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Template("Y".equals(rs.getString(1)), ThaiUtil.ASCII2Unicode(rs.getString(2)));
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(KitchenTemplateService.class, "error", e);
        } finally {
            mysql.closeConnection(KitchenTemplateService.class);
        }
        return null;
    }

    /** The template text when this kitchen has an enabled template, otherwise null (use the built-in form). */
    public static String loadEnabled(String kicNo) {
        Template t = load(kicNo);
        return t != null && t.enabled && !t.text.trim().isEmpty() ? t.text : null;
    }

    public static boolean save(String kicNo, boolean enabled, String text) {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(KitchenTemplateService.class);
        try (PreparedStatement ps = mysql.getConnection().prepareStatement(
                "INSERT INTO kitchen_templates (kic_no, enabled, template, updated_at) VALUES (?, ?, ?, NOW()) "
                + "ON DUPLICATE KEY UPDATE enabled = VALUES(enabled), template = VALUES(template), updated_at = NOW()")) {
            ps.setString(1, kicNo);
            ps.setString(2, enabled ? "Y" : "N");
            ps.setString(3, ThaiUtil.Unicode2ASCII(text));
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            AppLogUtil.log(KitchenTemplateService.class, "error", e);
            return false;
        } finally {
            mysql.closeConnection(KitchenTemplateService.class);
        }
    }
}
