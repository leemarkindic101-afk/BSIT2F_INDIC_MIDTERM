package com.dorm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/** Opens SQLite connections and creates the table on startup. */
public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:dormitory.db";

    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS allocations ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "student_name TEXT NOT NULL, "
            + "building TEXT NOT NULL, "
            + "room_no TEXT NOT NULL, "
            + "monthly_rent REAL NOT NULL)";

    private DatabaseConnection() { }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    /** Creates the allocations table if it does not exist yet. */
    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(CREATE_TABLE);
        } catch (SQLException e) {
            throw new DaoException("Could not initialize database: " + e.getMessage(), e);
        }
    }
}
