package com.edu;

import com.edu.infrastructure.service.db.CarsRepository;
import com.edu.infrastructure.service.db.DerbyDBService;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;

@Slf4j
public class MainApp {

    public static void main(final String[] args) {
        log.info("Starting app...");
        try (final DerbyDBService dbService = new DerbyDBService()) {
            try (final Connection connection = dbService.getConnection()) {
                final CarsRepository carsRepository = new CarsRepository(connection);
                if (carsRepository.exists()) {
                    log.info("Cars repository already exists");
                } else {
                    log.info("Creating table CARS...");
                    carsRepository.createTable();

                    log.info("Inserting test data...");
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
