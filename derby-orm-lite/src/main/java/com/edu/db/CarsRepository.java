package com.edu.db;

import com.edu.model.Car;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
@RequiredArgsConstructor
public class CarsRepository {

    private final JdbcPooledConnectionSource con;

    private Dao<Car, Integer> dao;

    public boolean createTable() throws SQLException {
        // Create if not exists is not supported out of the box for orm lite due to
        // DerbyEmbeddedDatabaseType.isCreateIfNotExistsSupported always return false
        // TODO use CustomDerbyEmbeddedDatabaseType instead
        final int createIfNotExists = TableUtils.createTableIfNotExists(con, Car.class);
        log.info("Table is created: {}", createIfNotExists);

        dao = DaoManager.lookupDao(con, Car.class);

        assert nonNull(dao) : "Could not find Car DAO Manager";

        return createIfNotExists == 1;
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
