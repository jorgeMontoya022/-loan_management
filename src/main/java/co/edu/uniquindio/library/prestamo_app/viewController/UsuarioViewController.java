package co.edu.uniquindio.library.prestamo_app.viewController;

import co.edu.uniquindio.library.prestamo_app.controller.UsuarioController;
import co.edu.uniquindio.library.prestamo_app.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;


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
        cambiarEscena(event, "/co/edu/uniquindio/library/prestamo_app/view/registro-usuario-view.fxml", "Registro Usuarios");

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


    private void cambiarEscena(ActionEvent actionEvent, String nameFileFxml, String titleWindow) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nameFileFxml));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(titleWindow);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
