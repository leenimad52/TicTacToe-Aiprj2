module com.example.tictactoeaiprj2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.tictactoeaiprj2 to javafx.fxml;
    exports com.example.tictactoeaiprj2;
}