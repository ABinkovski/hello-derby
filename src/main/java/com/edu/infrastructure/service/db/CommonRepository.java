package com.edu.infrastructure.service.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class CommonRepository {
    protected abstract Connection getCon();

    @SuppressWarnings("SqlSourceToSinkFlow")
    protected int executeUpdateSql(final String sql) throws SQLException {
        try (final Statement st = getCon().createStatement()) {
            // this unsafe execution for test purpose only - for embedded local in-memory DB
            return st.executeUpdate(sql);
        }
    }
}
