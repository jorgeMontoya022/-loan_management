package co.edu.uniquindio.library.prestamo_app.viewController;

import co.edu.uniquindio.library.prestamo_app.controller.UsuarioController;
import co.edu.uniquindio.library.prestamo_app.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class UsuarioViewController {

    UsuarioController usuarioController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtBuscar;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<Usuario> tblUsuarios;

    @FXML
    private TableColumn<Usuario, String> colId;

    @FXML
    private TableColumn<Usuario, String> colNombre;

    @FXML
    private TableColumn<Usuario, String> colDocumento;

    @FXML
    private TableColumn<Usuario, String> colPrograma;

    @FXML
    private TableColumn<Usuario, String> colEmail;

    @FXML
    private TableColumn<Usuario, String> colEstado;

    @FXML
    private TableColumn<Usuario, Void> colAcciones;

    // Paginación
    @FXML
    private Label lblPaginacion;

    @FXML
    private Label lblPaginaActual;

    @FXML
    private Label lblTotalPaginas;

    @FXML
    private Button btnPaginaAnterior;

    @FXML
    private Button btnPaginaSiguiente;

    @FXML
    void onVolverClick(ActionEvent event) {

    }

    @FXML
    void onBuscarKeyReleased(ActionEvent event) {

    }

    @FXML
    void onNuevoUsuarioClick(ActionEvent event) {

    }

    @FXML
    void onPaginaAnteriorClick() {

    }

    @FXML
    void onPaginaSiguienteClick() {

    }


    @FXML
    void initialize() {
        usuarioController = new UsuarioController();

    }
}
