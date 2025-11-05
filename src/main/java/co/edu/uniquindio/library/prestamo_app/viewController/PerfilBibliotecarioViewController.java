package co.edu.uniquindio.library.prestamo_app.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.library.prestamo_app.model.Bibliotecario;
import co.edu.uniquindio.library.prestamo_app.session.Sesion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PerfilBibliotecarioViewController extends CoreViewController {

    Bibliotecario logginBibliotecario;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Button btnVolver;

    @FXML
    private Label lblDocumento;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblNombre;

    @FXML
    void onCerrarSesionClick(ActionEvent event) {
        Sesion.getInstance().closeSesion();
        browseWindow("/co/edu/uniquindio/library/prestamo_app/view/login-view.fxml", "Inicio de sesión", event);

    }

    @FXML
    void onVolverClick(ActionEvent event) {
        browseWindow("/co/edu/uniquindio/library/prestamo_app/view/dashboard-view.fxml", "Dashboard", event);

    }

    @FXML
    void initialize() {
        logginBibliotecario = (Bibliotecario) Sesion.getInstance().getBibliotecario();
        lblNombre.setText(logginBibliotecario.getNombre());
        lblEmail.setText(logginBibliotecario.getEmail());
        lblDocumento.setText(logginBibliotecario.getDocumento());
    }

}
