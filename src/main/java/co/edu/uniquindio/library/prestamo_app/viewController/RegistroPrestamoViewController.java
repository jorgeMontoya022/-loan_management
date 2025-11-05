package co.edu.uniquindio.library.prestamo_app.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.library.prestamo_app.controller.PrestamoController;
import co.edu.uniquindio.library.prestamo_app.model.Bibliotecario;
import co.edu.uniquindio.library.prestamo_app.session.Sesion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class RegistroPrestamoViewController {

    PrestamoController prestamoController;
    Bibliotecario logginBibliotecario;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnBuscarEjemplar;

    @FXML
    private Button btnBuscarUsuario;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmarPrestamo;

    @FXML
    private Button btnVolver;

    @FXML
    private Label lblAutorLibro;

    @FXML
    private Label lblDocumentoUsuario;

    @FXML
    private Label lblEjemplarSeleccion;

    @FXML
    private Label lblFechaCompromiso;

    @FXML
    private Label lblFechaInicio;

    @FXML
    private Label lblIsbnLibro;

    @FXML
    private Label lblNombreUsuario;

    @FXML
    private Label lblProgramaUsuario;

    @FXML
    private Label lblTituloLibro;

    @FXML
    private Label lblUbicacionLibro;

    @FXML
    private Label lblUsuarioSeleccion;

    @FXML
    private TextField txtBuscarEjemplar;

    @FXML
    private TextField txtBuscarUsuario;

    @FXML
    private VBox vboxConfirmacion;

    @FXML
    private VBox vboxEjemplarInfo;

    @FXML
    private VBox vboxUsuarioInfo;

    @FXML
    void onBuscarEjemplar(ActionEvent event) {

    }

    @FXML
    void onBuscarUsuario(ActionEvent event) {

    }

    @FXML
    void onCancelar(ActionEvent event) {

    }

    @FXML
    void onConfirmarPrestamo(ActionEvent event) {

    }

    @FXML
    void onVolver(ActionEvent event) {

    }

    @FXML
    void initialize() {
        prestamoController = new PrestamoController();
        logginBibliotecario = (Bibliotecario) Sesion.getInstance().getBibliotecario();



    }

}
