package org.example.movie;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class HelloController {

    private static final MovieDAO movieDAO = new MovieDAO();
    private static final BookingDAO bookingDAO = new BookingDAO();
    private static final ReviewDAO reviewDAO = new ReviewDAO();

    @FXML
    private ComboBox<String> movieDropdown;

    @FXML
    private TextField userNameField;

    @FXML
    private TextField ticketCountField;

    @FXML
    private DatePicker bookingDateField;

    @FXML
    private TextArea reviewField;

    @FXML
    private Label notificationLabel;

    @FXML
    private ListView<String> reviewListView;

    @FXML
    public void initialize() {
        List<Movie> movies = movieDAO.findAll();
        for (Movie movie : movies) {
            movieDropdown.getItems().add(movie.getTitle());
        }

        // Listener to update reviews when a movie is selected
        movieDropdown.valueProperty().addListener((obs, oldMovie, newMovie) -> {
            if (newMovie != null) {
                loadReviewsForMovie(newMovie);
            }
        });
    }

    @FXML
    public void handleBookNow() {
        String selectedMovie = movieDropdown.getValue();
        String userName = userNameField.getText();
        String ticketCountText = ticketCountField.getText();
        LocalDate bookingDate = bookingDateField.getValue();

        try {
            if (selectedMovie == null || selectedMovie.isEmpty()) {
                throw new IllegalArgumentException("Please select a movie.");
            }
            if (userName.isEmpty()) {
                throw new IllegalArgumentException("Please enter your name.");
            }
            if (bookingDate == null) {
                throw new IllegalArgumentException("Please select a booking date.");
            }

            int ticketCount = Integer.parseInt(ticketCountText);
            if (ticketCount <= 0) {
                throw new IllegalArgumentException("Please enter a valid number of tickets.");
            }

            java.sql.Date sqlBookingDate = java.sql.Date.valueOf(bookingDate);

            Movie movie = movieDAO.findAll().stream()
                    .filter(m -> m.getTitle().equals(selectedMovie))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Movie not found in the database."));

            Booking booking = new Booking(0, userName, selectedMovie, ticketCount, sqlBookingDate);
            bookingDAO.insert(booking);

            notificationLabel.setStyle("-fx-text-fill: green;");
            notificationLabel.setText(String.format("Booking successful for %d ticket(s) to %s. Date: %s.", ticketCount, selectedMovie, bookingDate));
        } catch (NumberFormatException e) {
            notificationLabel.setStyle("-fx-text-fill: red;");
            notificationLabel.setText("Invalid ticket count. Please enter a number.");
        } catch (IllegalArgumentException e) {
            notificationLabel.setStyle("-fx-text-fill: red;");
            notificationLabel.setText(e.getMessage());
        }
    }

    @FXML
    public void handleAddReview() {
        String selectedMovie = movieDropdown.getValue();
        String reviewText = reviewField.getText();

        try {
            if (selectedMovie == null || selectedMovie.isEmpty()) {
                throw new IllegalArgumentException("Please select a movie to review.");
            }
            if (reviewText.isEmpty()) {
                throw new IllegalArgumentException("Please enter a review.");
            }

            Movie movie = movieDAO.findAll().stream()
                    .filter(m -> m.getTitle().equals(selectedMovie))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Movie not found in the database."));

            int defaultRating = 5; // Default rating value
            Date currentDate = Date.valueOf(LocalDate.now());

            Review review = new Review(0, movie.getId(), reviewText, defaultRating, currentDate);
            reviewDAO.insert(review);

            notificationLabel.setStyle("-fx-text-fill: green;");
            notificationLabel.setText("Review added successfully.");
            reviewField.clear();

            // Refresh reviews
            loadReviewsForMovie(selectedMovie);
        } catch (IllegalArgumentException e) {
            notificationLabel.setStyle("-fx-text-fill: red;");
            notificationLabel.setText(e.getMessage());
        }
    }

    private void loadReviewsForMovie(String movieTitle) {
        reviewListView.getItems().clear();
        Movie movie = movieDAO.findAll().stream()
                .filter(m -> m.getTitle().equals(movieTitle))
                .findFirst()
                .orElse(null);

        if (movie != null) {
            List<Review> reviews = reviewDAO.findAll();
            for (Review review : reviews) {
                if (review.getMovieId() == movie.getId()) {
                    reviewListView.getItems().add(review.getReviewText());
                }
            }
        }
    }
}
