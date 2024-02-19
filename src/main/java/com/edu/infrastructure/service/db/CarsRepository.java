package com.edu.infrastructure.service.db;

import com.edu.domain.model.Car;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class CarsRepository extends CommonRepository {

    private static final String TABLE_NAME = "CARS";

    @Getter(AccessLevel.PROTECTED)
    private final Connection con;

    public boolean exists() throws SQLException {
        return super.exists(TABLE_NAME);
    }

    public void createTable() throws SQLException {
        executeUpdateSql("CREATE TABLE " + TABLE_NAME + "(ID INT PRIMARY KEY,NAME VARCHAR(30), PRICE INT)");
    }

    public List<Car> getCars() throws SQLException {
        final ArrayList<Car> cars = new ArrayList<>();
        try (final Statement st = con.createStatement()) {
            st.execute("SELECT * FROM " + TABLE_NAME);

            try (final ResultSet rs = st.getResultSet()) {
                while (rs.next()) {
                    cars.add(Car.builder()
                            .id(rs.getInt(1))
                            .name(rs.getString(2))
                            .price(rs.getInt(3))
                            .build());
                }
            }
        }
        return cars;
    }

    public void insertTestData() throws SQLException {
        final List<String> sqlInsertStatements = Arrays.asList(
                "INSERT INTO " + TABLE_NAME + " VALUES(1, 'Audi', 52642)",
                "INSERT INTO " + TABLE_NAME + " VALUES(2, 'Mercedes', 57127)",
                "INSERT INTO " + TABLE_NAME + " VALUES(3, 'Skoda', 9000)",
                "INSERT INTO " + TABLE_NAME + " VALUES(4, 'Volvo', 29000)",
                "INSERT INTO " + TABLE_NAME + " VALUES(5, 'Bentley', 350000)",
                "INSERT INTO " + TABLE_NAME + " VALUES(6, 'Citroen', 21000)",
                "INSERT INTO " + TABLE_NAME + " VALUES(7, 'Hummer', 41400)",
                "INSERT INTO " + TABLE_NAME + " VALUES(8, 'Volkswagen', 21600)");
        for (final String string : sqlInsertStatements) {
            executeUpdateSql(string);
        }
    }
}
