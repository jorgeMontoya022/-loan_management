module co.edu.uniquindio.library.prestamo_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;
    requires kernel;
    requires layout;


    opens co.edu.uniquindio.library.prestamo_app to javafx.fxml;
    exports co.edu.uniquindio.library.prestamo_app;

    exports co.edu.uniquindio.library.prestamo_app.model;
    opens co.edu.uniquindio.library.prestamo_app.util to javafx.base, javafx.controls;

    opens co.edu.uniquindio.library.prestamo_app.viewController;
    exports co.edu.uniquindio.library.prestamo_app.viewController;


}