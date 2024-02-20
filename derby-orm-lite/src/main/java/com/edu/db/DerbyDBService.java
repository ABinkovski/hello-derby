package com.edu.db;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class DerbyDBService implements Closeable {

    private final String driverName = "org.apache.derby.jdbc.EmbeddedDriver";
    private final String protocol = "jdbc:derby:";
    private final String IN_MEM_DB_NAME = "derbyDBOrm";

    public DerbyDBService() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        log.info("Initialising DerbyDBService...");

        startDBServer();
    }

    public void startDBServer() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        log.info("Staring embedded DB server...");
        Class.forName(driverName).getDeclaredConstructor().newInstance();
    }

    public JdbcPooledConnectionSource getConnectionPool() throws SQLException {
        log.info("Creating new JdbcPooledConnectionSource...");

        return new JdbcPooledConnectionSource(protocol + IN_MEM_DB_NAME + ";create=true");
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    @Override
    public void close() throws IOException {
        try {
            log.info("Stopping DB server...");
            DriverManager.getConnection(protocol + ";shutdown=true");
        } catch (final SQLException e) {
            switch (e.getMessage()) {
                case "Derby system shutdown.":
                    log.info(e.getMessage());
                    break;
                default:
                    throw new IOException(e);
            }
        }
    }
}
