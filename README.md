# Movie Ticket Booking System

## **1. Database Schema Documentation**

### **Entity-Relationship Diagram (ERD)**

Here’s a description of the entities and relationships in the system:

**Entities:**
- **Movies:** Stores movie details like title, genre, and price.
- **Bookings:** Stores booking information, linking a user to a movie with the number of seats and booking date.
- **Reviews:** Stores reviews for movies, each review is linked to a movie.
- **Showtimes:** Stores the showtimes for movies, with a date and time for each show.
- **Cinema:** Stores cinema information including name, type (e.g., standard, VIP), and seating capacity.

**Relationships:**
- **Movies** ↔ **Bookings**: One movie can have many bookings (One-to-Many).
- **Movies** ↔ **Reviews**: One movie can have many reviews (One-to-Many).
- **Cinema** ↔ **Showtimes**: One cinema can have multiple showtimes (One-to-Many).

---

### **Relational Model**

Here’s the relational model for the tables in the database.

#### **Movies Table**
```sql
CREATE TABLE Movies (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(100),
    price DECIMAL(10, 2) NOT NULL
);
```

#### **Bookings Table**
```sql
CREATE TABLE Bookings (
    id SERIAL PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    movie_title VARCHAR(255) NOT NULL,
    seatcount INT NOT NULL,
    booking_date DATE NOT NULL
);
```

#### **Reviews Table**
```sql
CREATE TABLE Reviews (
    id SERIAL PRIMARY KEY,
    movie_id INT NOT NULL,
    review_text TEXT NOT NULL,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    review_date DATE NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES Movies(id) ON DELETE CASCADE
);
```

#### **Showtimes Table**
```sql
CREATE TABLE Showtimes (
    showtime_id SERIAL PRIMARY KEY,
    movie_title VARCHAR(255) NOT NULL,
    show_date DATE NOT NULL,
    show_time TIME NOT NULL
);
```

#### **Cinema Table**
```sql
CREATE TABLE Cinema (
    cinema_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    capacity INT NOT NULL
);
```

---

## **2. SQL Queries for Database Operations**

### **Query 1: Retrieve all movies with their details.**
```sql
SELECT * FROM Movies;
```

### **Query 2: List all bookings for a specific movie.**
```sql
SELECT user_name, seatcount, booking_date 
FROM Bookings
WHERE movie_title = 'Titanic'
ORDER BY booking_date;
```

### **Query 3: Retrieve reviews for a specific movie.**
```sql
SELECT r.review_text, r.rating, r.review_date
FROM Reviews r
JOIN Movies m ON r.movie_id = m.id
WHERE m.title = 'Avatar';
```

### **Query 4: Get available showtimes for a specific movie.**
```sql
SELECT show_date, show_time 
FROM Showtimes
WHERE movie_title = 'Titanic'
```

### **Query 5: Retrieve cinemas with a capacity greater than 200.**
```sql
SELECT name, type, capacity
FROM Cinema
WHERE capacity > 200;
```
---

## **3. User Guide for the Application**

### **Introduction**
The Movie Ticket Booking System allows users to:
1. View available movies.
2. Book tickets for a specific movie.
3. View and write reviews for movies.
4. Select a cinema and showtime for a movie.
5. Perform bookings and receive confirmation.

### **Steps to Use the Application**

1. **Select Movie:**
   - From the dropdown, select the movie you want to book tickets for (e.g., "Titanic", "Avatar").

2. **Enter User Details:**
   - In the **Your Name** field, enter your name.
   - In the **Number of Tickets** field, specify how many tickets you want to book.

3. **Select Date for Booking:**
   - Use the **Booking Date** field to select your preferred date for booking.

4. **Make a Booking:**
   - Click the **Book Now** button to confirm your booking. A confirmation message will appear.

5. **Write a Review:**
   - In the **Reviews** section, type your review in the **Write your review here** field.
   - Submit the review by clicking the **Add Review** button.

6. **View Reviews:**
   - After submitting, your review will appear in the **Reviews List**.

### **Features:**
- **Bookings:** You can book tickets for a movie, and the system will store the booking details (user name, movie, number of tickets, booking date).
- **Reviews:** You can write reviews for movies with ratings from 1 to 5.
- **Notifications:** After booking or submitting a review, a confirmation notification will appear.

---

### **Conclusion**
This system is a simple movie ticket booking application that allows users to interact with the database for managing bookings, viewing movie details, adding reviews, and selecting showtimes. It utilizes JavaFX for the user interface and JDBC for database interactions.
