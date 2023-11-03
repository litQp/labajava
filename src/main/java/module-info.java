module com.example.labs3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.labs3 to javafx.fxml;
    exports com.example.labs3;
}