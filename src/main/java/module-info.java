module org.example.movie {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.movie to javafx.fxml;
    exports org.example.movie;
}