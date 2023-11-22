package com.chess.database;

import java.util.Properties;

public class DatabaseProperties {

    public String getUrl() {
        return "jdbc:postgresql://localhost:5432/chess_bd";
    }

    public String getLogin() {
        return "postgres";
    }

    public String getPassword() {
        return "380Slava123";
    }
}
