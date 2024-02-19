package com.edu.infrastructure.service.db;

import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class DerbyDBService implements Closeable {

    private final String driverName = "org.apache.derby.jdbc.EmbeddedDriver";
    private final String protocol = "jdbc:derby:";

    public DerbyDBService() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        log.info("Initialising DerbyDBService...");
        open();
    }

    public void open() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        log.info("Staring embedded DB server...");
        final Object instance = Class.forName(driverName).getDeclaredConstructor().newInstance();
    }

    public Connection getConnection() throws SQLException {
        log.info("Getting DB connection");
        return DriverManager.getConnection(protocol + "derbyDB;create=true");
    }

    @Override
    public void close() throws IOException {
        try {
            log.info("Stopping DB server...");
            DriverManager.getConnection(protocol + ";shutdown=true");
        } catch (final SQLException e) {
            throw new IOException(e);
        }
    }
}
