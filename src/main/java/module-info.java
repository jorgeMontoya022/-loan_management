module co.edu.uniquindio.library.prestamo_app {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.library.prestamo_app to javafx.fxml;
    exports co.edu.uniquindio.library.prestamo_app;
}