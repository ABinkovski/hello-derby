package com.edu;

import com.edu.infrastructure.service.db.DerbyDBService;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;

@Slf4j
public class MainApp {

    public static void main(final String[] args) {
        log.info("Starting app...");
        try (final DerbyDBService dbService = new DerbyDBService()) {
            try (final Connection connection = dbService.getConnection()) {

            }
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
        log.info("Closing app..");
    }

}
