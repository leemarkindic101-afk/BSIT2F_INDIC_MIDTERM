package com.dorm.dao;

import com.dorm.model.Allocation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** All SQL for dormitory allocations lives here. */
public class ScenarioDao {
    private static final String INSERT_SQL =
            "INSERT INTO allocations (student_name, building, room_no, monthly_rent) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL =
            "SELECT id, student_name, building, room_no, monthly_rent FROM allocations ORDER BY id";

    public ScenarioDao() {
        DatabaseConnection.initializeDatabase();
    }

    /** Inserts a new allocation using a PreparedStatement. */
    public void insert(Allocation allocation) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            ps.setString(1, allocation.getStudentName());
            ps.setString(2, allocation.getBuilding());
            ps.setString(3, allocation.getRoomNo());
            ps.setDouble(4, allocation.getMonthlyRent());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Could not save allocation: " + e.getMessage(), e);
        }
    }

    /** Returns every allocation, ordered by ID. */
    public List<Allocation> findAll() {
        List<Allocation> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Allocation(
                        rs.getInt("id"),
                        rs.getString("student_name"),
                        rs.getString("building"),
                        rs.getString("room_no"),
                        rs.getDouble("monthly_rent")));
            }
        } catch (SQLException e) {
            throw new DaoException("Could not load allocations: " + e.getMessage(), e);
        }
        return list;
    }
}
