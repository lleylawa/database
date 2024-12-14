package org.example.movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO implements DAOInterface<Review> {
    private Connection conn;

    public ReviewDAO() {
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
    public int insert(Review entity) {
        String sql = "INSERT INTO Reviews (movie_id, review_text, rating, review_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, entity.getMovieId());
            preparedStatement.setString(2, entity.getReviewText());
            preparedStatement.setInt(3, entity.getRating());
            preparedStatement.setDate(4, entity.getReviewDate());

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
    public Review read(int id) {
        String sql = "SELECT * FROM Reviews WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return new Review(
                            rs.getInt("id"),
                            rs.getInt("movie_id"),
                            rs.getString("review_text"),
                            rs.getInt("rating"),
                            rs.getDate("review_date")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Read failed: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Review entity) {
        String sql = "UPDATE Reviews SET movie_id = ?, review_text = ?, rating = ?, review_date = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, entity.getMovieId());
            preparedStatement.setString(2, entity.getReviewText());
            preparedStatement.setInt(3, entity.getRating());
            preparedStatement.setDate(4, entity.getReviewDate());
            preparedStatement.setInt(5, entity.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                System.err.println("Update failed: No review found with ID " + entity.getId());
            } else {
                System.out.println("Review with ID " + entity.getId() + " updated successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Update failed: " + e.getMessage());
        }
    }

    @Override
    public void delete(Review entity) {
        String sql = "DELETE FROM Reviews WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, entity.getId());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                System.err.println("Delete failed: No review found with ID " + entity.getId());
            } else {
                System.out.println("Review with ID " + entity.getId() + " deleted successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Delete failed: " + e.getMessage());
        }
    }

    @Override
    public List<Review> findAll() {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM Reviews";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                reviews.add(new Review(
                        rs.getInt("id"),
                        rs.getInt("movie_id"),
                        rs.getString("review_text"),
                        rs.getInt("rating"),
                        rs.getDate("review_date")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Find all failed: " + e.getMessage());
        }
        return reviews;
    }
}
