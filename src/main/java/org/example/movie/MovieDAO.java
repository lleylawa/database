package org.example.movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO implements DAOInterface<Movie> {
    private Connection conn;

    public MovieDAO() {
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
    public int insert(Movie entity) {
        String sql = "INSERT INTO Movies (title, genre, price) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setString(2, entity.getGenre());
            preparedStatement.setDouble(3, entity.getPrice());
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
    public Movie read(int id) {
        String sql = "SELECT * FROM Movies WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return new Movie(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("genre"),
                            rs.getDouble("price") // Updated to reflect price
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Read failed: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Movie entity) {
        String sql = "UPDATE Movies SET title = ?, genre = ?, price = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setString(2, entity.getGenre());
            preparedStatement.setDouble(3, entity.getPrice());
            preparedStatement.setInt(4, entity.getId());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                System.err.println("Update failed: No movie found with ID " + entity.getId());
            } else {
                System.out.println("Movie with ID " + entity.getId() + " updated successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Update failed: " + e.getMessage());
        }
    }

    @Override
    public void delete(Movie entity) {
        String sql = "DELETE FROM Movies WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, entity.getId());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                System.err.println("Delete failed: No movie found with ID " + entity.getId());
            } else {
                System.out.println("Movie with ID " + entity.getId() + " deleted successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Delete failed: " + e.getMessage());
        }
    }

    @Override
    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM Movies";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                movies.add(new Movie(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Find all failed: " + e.getMessage());
        }
        return movies;
    }
}
