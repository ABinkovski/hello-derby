package com.edu;

import com.edu.db.CarsRepository;
import com.edu.db.DerbyDBService;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainApp {

    public static void main(final String[] args) {
        log.info("Starting app...");
        try (final DerbyDBService dbService = new DerbyDBService()) {
            try (final JdbcPooledConnectionSource jdbcPool = dbService.getConnectionPool()) {
                final CarsRepository carsRepository = new CarsRepository(jdbcPool);

                if (!carsRepository.exists()) {
                    carsRepository.createTable();
                    carsRepository.insertTestData();
                }

                carsRepository.getCars()
                        .forEach(car -> log.info("Car from DB: {}", car));
            }
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
        log.info("Closing app..");
    }

}
