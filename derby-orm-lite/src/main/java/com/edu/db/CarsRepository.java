package com.edu.db;

import com.edu.model.Car;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class CarsRepository {

    private Dao<Car, Integer> dao;

    public CarsRepository(final JdbcPooledConnectionSource con) throws SQLException {
        dao = DaoManager.createDao(con, Car.class);
    }

    public boolean exists() throws SQLException {
        return dao.isTableExists();
    }

    public void createTable() throws SQLException {
        // Create if not exists is not supported out of the box for orm lite due to
        // DerbyEmbeddedDatabaseType.isCreateIfNotExistsSupported always return false
        final int createIfNotExists = TableUtils.createTableIfNotExists(dao.getConnectionSource(), Car.class);
        log.info("Table is created: {}", createIfNotExists);


    }

    public List<Car> getCars() throws SQLException {
        return dao.queryForAll();
    }

    public int persist(final List<Car> cars) throws SQLException {
        return dao.create(cars);
    }

    public void insertTestData() throws SQLException {
        final List<Car> cars = Arrays.asList(
                new Car(null, "Audi", 52642),
                new Car(null, "Mercedes", 57127),
                new Car(null, "Skoda", 9000),
                new Car(null, "Volvo", 29000),
                new Car(null, "Bentley", 350000),
                new Car(null, "Citroen", 21000),
                new Car(null, "Hummer", 41400),
                new Car(null, "Volkswagen", 21600)
        );

        persist(cars);

        cars.forEach(car -> log.info("Inserted in DB: {}", car));
    }
}
