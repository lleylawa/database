package org.example.movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO implements DAOInterface<Booking> {
    private Connection conn;

    public BookingDAO() {
        String url = "jdbc:postgresql://localhost:5432/movie";
        String username = "postgres";
        String password = "leyla@2006";

        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Database is successfully connected...");
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }

    @Override
    public int insert(Booking entity) {
        String sql = "INSERT INTO Bookings (user_name, movie_title, seatCount, booking_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getUserName());
            preparedStatement.setString(2, entity.getMovieTitle());
            preparedStatement.setInt(3, entity.getSeatCount());
            preparedStatement.setDate(4, entity.getBookingDate());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Insert failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Insert failed: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public Booking read(int id) {
        String sql = "SELECT * FROM Bookings WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return new Booking(
                            rs.getInt("id"),
                            rs.getString("user_name"),
                            rs.getString("movie_title"),
                            rs.getInt("seatCount"),
                            rs.getDate("booking_date")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Read failed: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Booking entity) {
        String sql = "UPDATE Bookings SET user_name = ?, movie_title = ?, seatCount = ?, booking_date = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getUserName());
            preparedStatement.setString(2, entity.getMovieTitle());
            preparedStatement.setInt(3, entity.getSeatCount());
            preparedStatement.setDate(4, entity.getBookingDate());
            preparedStatement.setInt(5, entity.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                System.err.println("Update failed: No booking found with ID " + entity.getId());
            } else {
                System.out.println("Booking with ID " + entity.getId() + " updated successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Update failed: " + e.getMessage());
        }
    }

    @Override
    public void delete(Booking entity) {
        String sql = "DELETE FROM Bookings WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, entity.getId());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                System.err.println("Delete failed: No booking found with ID " + entity.getId());
            } else {
                System.out.println("Booking with ID " + entity.getId() + " deleted successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Delete failed: " + e.getMessage());
        }
    }

    @Override
    public List<Booking> findAll() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM Bookings";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                bookings.add(new Booking(
                        rs.getInt("id"),
                        rs.getString("user_name"),
                        rs.getString("movie_title"),
                        rs.getInt("seatCount"),
                        rs.getDate("booking_date")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Find all failed: " + e.getMessage());
        }
        return bookings;
    }
}
