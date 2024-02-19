package com.edu;

import com.edu.infrastructure.service.db.DerbyDBService;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Slf4j
public class MainApp {

    public static void main(final String[] args) {
        log.info("Starting app...");
        try (final DerbyDBService dbService = new DerbyDBService()) {
            try (final Connection connection = dbService.getConnection()) {
                try (final Statement st = connection.createStatement()) {
                    st.executeUpdate("CREATE TABLE CARS(ID INT PRIMARY KEY,"
                            + "NAME VARCHAR(30), PRICE INT)");
                    st.executeUpdate("INSERT INTO CARS VALUES(1, 'Audi', 52642)");
                    st.executeUpdate("INSERT INTO CARS VALUES(2, 'Mercedes', 57127)");
                    st.executeUpdate("INSERT INTO CARS VALUES(3, 'Skoda', 9000)");
                    st.executeUpdate("INSERT INTO CARS VALUES(4, 'Volvo', 29000)");
                    st.executeUpdate("INSERT INTO CARS VALUES(5, 'Bentley', 350000)");
                    st.executeUpdate("INSERT INTO CARS VALUES(6, 'Citroen', 21000)");
                    st.executeUpdate("INSERT INTO CARS VALUES(7, 'Hummer', 41400)");
                    st.executeUpdate("INSERT INTO CARS VALUES(8, 'Volkswagen', 21600)");

                    st.execute("SELECT * FROM CARS");
                    try (final ResultSet rs = st.getResultSet()) {
                        while (rs.next()) {
                            log.info("ID:{}, Name: {}, price: {}", rs.getInt(1), rs.getString(2), rs.getInt(3));
                        }
                    }
                }
            }
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
        log.info("Closing app..");
    }

}
