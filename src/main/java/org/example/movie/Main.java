package org.example.movie;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final MovieDAO movieDAO = new MovieDAO();
    private static final BookingDAO bookingDAO = new BookingDAO();
    private static final ReviewDAO reviewDAO = new ReviewDAO();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Movie Ticket Booking System ---");
            System.out.println("1. Add a Movie");
            System.out.println("2. View All Movies");
            System.out.println("3. Update a Movie");
            System.out.println("4. Delete a Movie");
            System.out.println("5. Add a Booking");
            System.out.println("6. View All Bookings");
            System.out.println("7. Update Booking");
            System.out.println("8. Delete a Booking");
            System.out.println("9. Add a Review");
            System.out.println("10. View All Reviews");
            System.out.println("11. Update a Review");
            System.out.println("12. Delete a Review");
            System.out.println("13. Exit");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: addMovie(); break;
                case 2: viewAllMovies(); break;
                case 3: updateMovie(); break;
                case 4: deleteMovie(); break;
                case 5: addBooking(); break;
                case 6: viewAllBookings(); break;
                case 7: updateBooking(); break;
                case 8: deleteBooking(); break;
                case 9: addReview(); break;
                case 10: viewAllReviews(); break;
                case 11: updateReview(); break;
                case 12: deleteReview(); break;
                case 13: System.out.println("Exiting... Goodbye!"); return;
                default: System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void addReview() {
        System.out.print("Enter movie ID for the review: ");
        int movieId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter review text: ");
        String reviewText = scanner.nextLine();
        System.out.print("Enter rating (1-5): ");
        int rating = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter review date (YYYY-MM-DD): ");
        String reviewDateStr = scanner.nextLine();
        Date reviewDate = Date.valueOf(reviewDateStr);

        Review review = new Review(0, movieId, reviewText, rating, reviewDate);
        int reviewId = reviewDAO.insert(review);
        System.out.println("Review added with ID: " + reviewId);
    }

    private static void viewAllReviews() {
        List<Review> reviews = reviewDAO.findAll();
        if (reviews.isEmpty()) {
            System.out.println("No reviews available.");
        } else {
            System.out.println("\n--- Reviews ---");
            for (Review review : reviews) {
                System.out.println(review);
            }
        }
    }

    private static void updateReview() {
        System.out.print("Enter review ID to update: ");
        int reviewId = Integer.parseInt(scanner.nextLine());
        Review review = reviewDAO.read(reviewId);

        if (review != null) {
            System.out.println("Current details: " + review);
            System.out.print("Enter new review text (leave blank to keep current): ");
            String reviewText = scanner.nextLine();
            System.out.print("Enter new rating (1-5) (leave blank to keep current): ");
            String ratingStr = scanner.nextLine();
            if (!reviewText.isEmpty()) review.setReviewText(reviewText);
            if (!ratingStr.isEmpty()) review.setRating(Integer.parseInt(ratingStr));

            reviewDAO.update(review);
            System.out.println("Review updated successfully.");
        } else {
            System.out.println("Review not found.");
        }
    }

    private static void deleteReview() {
        System.out.print("Enter review ID to delete: ");
        int reviewId = Integer.parseInt(scanner.nextLine());
        Review review = reviewDAO.read(reviewId);

        if (review != null) {
            reviewDAO.delete(review);
            System.out.println("Review deleted successfully.");
        } else {
            System.out.println("Review not found.");
        }
    }

    private static void addMovie() {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine());

        Movie movie = new Movie(0, title, genre, price);
        int movieId = movieDAO.insert(movie);
        System.out.println("Movie added with ID: " + movieId);
    }

    private static void viewAllMovies() {
        List<Movie> movies = movieDAO.findAll();
        if (movies.isEmpty()) {
            System.out.println("No movies available.");
        } else {
            System.out.println("\n--- Movies ---");
            for (Movie movie : movies) {
                System.out.println(movie);
            }
        }
    }

    private static void updateMovie() {
        System.out.print("Enter movie ID to update: ");
        int movieId = Integer.parseInt(scanner.nextLine());
        Movie movie = movieDAO.read(movieId);

        if (movie != null) {
            System.out.println("Current details: " + movie);
            System.out.print("Enter new title (leave blank to keep current): ");
            String title = scanner.nextLine();
            System.out.print("Enter new genre (leave blank to keep current): ");
            String genre = scanner.nextLine();
            System.out.print("Enter new price (leave blank to keep current): ");
            String priceStr = scanner.nextLine();

            if (!title.isEmpty()) movie.setTitle(title);
            if (!genre.isEmpty()) movie.setGenre(genre);
            if (!priceStr.isEmpty()) movie.setPrice(Double.parseDouble(priceStr));

            movieDAO.update(movie);
            System.out.println("Movie updated successfully.");
        } else {
            System.out.println("Movie not found.");
        }
    }

    private static void deleteMovie() {
        System.out.print("Enter movie ID to delete: ");
        int movieId = Integer.parseInt(scanner.nextLine());
        Movie movie = movieDAO.read(movieId);

        if (movie != null) {
            movieDAO.delete(movie);
            System.out.println("Movie deleted successfully.");
        } else {
            System.out.println("Movie not found.");
        }
    }

    private static void addBooking() {
        System.out.print("Enter your name: ");
        String userName = scanner.nextLine();
        System.out.print("Enter movie title: ");
        String movieTitle = scanner.nextLine();
        System.out.print("Enter number of seats: ");
        int seatCount = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter booking date (YYYY-MM-DD): ");
        String bookingDateStr = scanner.nextLine();
        Date bookingDate = Date.valueOf(bookingDateStr);

        Booking booking = new Booking(0, userName, movieTitle, seatCount, bookingDate);
        int bookingId = bookingDAO.insert(booking);
        System.out.println("Booking added with ID: " + bookingId);
    }

    private static void viewAllBookings() {
        List<Booking> bookings = bookingDAO.findAll();
        if (bookings.isEmpty()) {
            System.out.println("No bookings available.");
        } else {
            System.out.println("\n--- Bookings ---");
            for (Booking booking : bookings) {
                System.out.println(booking);
            }
        }
    }

    private static void updateBooking() {
        System.out.print("Enter Booking ID to update: ");
        int bookingId = Integer.parseInt(scanner.nextLine());
        Booking booking = bookingDAO.read(bookingId);

        if (booking != null) {
            System.out.println("Current details: " + booking);

            System.out.print("Enter new User Name (leave blank to keep current): ");
            String userName = scanner.nextLine();
            if (!userName.isEmpty()) {
                booking.setUserName(userName);
            }

            System.out.print("Enter new Movie Title (leave blank to keep current): ");
            String movieTitle = scanner.nextLine();
            if (!movieTitle.isEmpty()) {
                booking.setMovieTitle(movieTitle);
            }

            System.out.print("Enter new Seat Count (leave blank to keep current): ");
            String seatCountStr = scanner.nextLine();
            if (!seatCountStr.isEmpty()) {
                booking.setSeatCount(Integer.parseInt(seatCountStr));
            }

            System.out.print("Enter new booking date (YYYY-MM-DD) or press Enter to keep current: ");
            String bookingDateStr = scanner.nextLine();
            if (!bookingDateStr.isEmpty()) {
                Date newBookingDate = Date.valueOf(bookingDateStr);
                booking.setBookingDate(newBookingDate);
            }

            try {
                bookingDAO.update(booking);
                System.out.println("Booking updated successfully.");
            } catch (Exception e) {
                System.err.println("Failed to update booking: " + e.getMessage());
            }
        } else {
            System.out.println("Booking not found.");
        }
    }

    private static void deleteBooking() {
        System.out.print("Enter Booking ID to delete: ");
        int bookingId = Integer.parseInt(scanner.nextLine());
        Booking booking = bookingDAO.read(bookingId);

        try {
            bookingDAO.delete(booking);
            System.out.println("Booking deleted successfully!");
        } catch (Exception e) {
            System.err.println("Failed to delete booking: " + e.getMessage());
        }
    }
}
