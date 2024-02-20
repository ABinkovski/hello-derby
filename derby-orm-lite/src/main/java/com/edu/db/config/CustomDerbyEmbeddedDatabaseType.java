package com.edu.db.config;

import com.j256.ormlite.jdbc.db.DerbyEmbeddedDatabaseType;

public class CustomDerbyEmbeddedDatabaseType extends DerbyEmbeddedDatabaseType {

    @Override
    public boolean isCreateIfNotExistsSupported() {
        return true;
    }
}
