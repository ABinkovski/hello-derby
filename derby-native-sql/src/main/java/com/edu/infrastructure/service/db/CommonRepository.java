package com.edu.infrastructure.service.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    protected boolean exists(final String tableName) throws SQLException {
        final String query = "SELECT TRUE FROM SYS.SYSTABLES WHERE TABLENAME = ? AND TABLETYPE = 'T'";
        try (final PreparedStatement pst = getCon().prepareStatement(query)) {
            pst.setString(1, tableName);
            try (final ResultSet rs = pst.executeQuery()) {
                return rs.next() && rs.getBoolean(1);
            }
        }
    }
}
