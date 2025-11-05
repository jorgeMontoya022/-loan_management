package co.edu.uniquindio.library.prestamo_app.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.library.prestamo_app.model.Bibliotecario;
import co.edu.uniquindio.library.prestamo_app.session.Sesion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DashboardViewController extends CoreViewController{

    Bibliotecario logginBibliotecario;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnGestionarUsuarios;

    @FXML
    private Button btnIniciarPrestamo;

    @FXML
    private Button btnPerfil;


    @FXML
    private Label labelBibliotecario;

    @FXML
    void onAbrirPerfil(ActionEvent event) {
        browseWindow("/co/edu/uniquindio/library/prestamo_app/view/perfil-bibliotecario-view.fxml", "Mi perfil", event);

    }

    @FXML
    void onGestionUsuarios(ActionEvent event) {
        browseWindow("/co/edu/uniquindio/library/prestamo_app/view/gestion-usuario-view.fxml", "Gestión usuarios", event);

    }

    @FXML
    void onIniciarPrestamo(ActionEvent event) {

    }

    @FXML
    void initialize() {
        logginBibliotecario = (Bibliotecario) Sesion.getInstance().getBibliotecario();
        labelBibliotecario.setText(logginBibliotecario.getNombre());
    }

}
