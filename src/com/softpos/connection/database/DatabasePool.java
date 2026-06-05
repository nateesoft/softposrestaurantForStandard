package com.softpos.connection.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabasePool {

    private static HikariDataSource dataSource;

    public static synchronized void init(String host, String port, String db,
            String user, String pass, String charset) {
        if (dataSource != null && !dataSource.isClosed()) {
            return;
        }
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + db
                + "?characterEncoding=" + charset
                + "&useSSL=false&allowPublicKeyRetrieval=true");
        config.setUsername(user);
        config.setPassword(pass);
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        config.setConnectionTestQuery("SELECT 1");
        config.setPoolName("LocalDBPool");
        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null || dataSource.isClosed()) {
            throw new SQLException("DatabasePool is not initialized.");
        }
        return dataSource.getConnection();
    }

    public static void shutdown() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
