package co.edu.uniquindio.library.prestamo_app.viewController;

import co.edu.uniquindio.library.prestamo_app.controller.UsuarioController;
import co.edu.uniquindio.library.prestamo_app.model.Bibliotecario;
import co.edu.uniquindio.library.prestamo_app.model.Usuario;
import co.edu.uniquindio.library.prestamo_app.model.enums.EstadoUsuario;
import co.edu.uniquindio.library.prestamo_app.session.Sesion;
import co.edu.uniquindio.library.prestamo_app.viewController.observer.EventType;
import co.edu.uniquindio.library.prestamo_app.viewController.observer.ObserverManagement;
import co.edu.uniquindio.library.prestamo_app.viewController.observer.ObserverView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class UsuarioViewController extends CoreViewController implements ObserverView {

    Bibliotecario logginBibliotecario;
    UsuarioController usuarioController;
    ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();

    // Variables de paginación
    private static final int USUARIOS_POR_PAGINA = 5;
    private int paginaActual = 0;
    private int totalPaginas = 0;
    private List<Usuario> todosLosUsuarios = new ArrayList<>();

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
    private TableColumn<Usuario, Label> colEstado;

    @FXML
    private TableColumn<Usuario, HBox> colAcciones;

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
        browseWindow("/co/edu/uniquindio/library/prestamo_app/view/dashboard-view.fxml", "Dashboard", event);

    }

    @FXML
    void onBuscarKeyReleased(ActionEvent event) {

    }

    @FXML
    void onNuevoUsuarioClick(ActionEvent event) {
        browseWindow("/co/edu/uniquindio/library/prestamo_app/view/registro-usuario-view.fxml", "Registro Usuarios", event);

    }

    @FXML
    void onPaginaAnteriorClick() {
        if (paginaActual > 0) {
            paginaActual--;
            actualizarTablaPaginada();
        }

    }


    @FXML
    void onPaginaSiguienteClick() {
        if (paginaActual < totalPaginas - 1) {
            paginaActual++;
            actualizarTablaPaginada();
        }

    }


    @FXML
    void initialize() {
        usuarioController = new UsuarioController();
        logginBibliotecario = (Bibliotecario) Sesion.getInstance().getBibliotecario();
        initView();
        ObserverManagement.getInstance().addObserver(EventType.USUARIO, this);


    }

    private void initView() {
        initDataBinding();
        getUsuarios();
        actualizarTablaPaginada();

    }

    private void initDataBinding() {
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colDocumento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocumento()));
        colId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        colPrograma.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrograma()));
        colEstado.setCellValueFactory(cellData -> {
            Usuario usuario = cellData.getValue();
            Label badge = crearBadgeEstado(usuario.getEstadoUsuario());
            return new SimpleObjectProperty<>(badge);
        });

        // Columna ACCIONES con botones
        colAcciones.setCellValueFactory(cellData -> {
            Usuario usuario = cellData.getValue();
            HBox botonesAccion = crearBotonesAccion(usuario);
            return new SimpleObjectProperty<>(botonesAccion);
        });

    }

    /**
     * Crea un badge visual para el estado del usuario con colores específicos
     */
    private Label crearBadgeEstado(EstadoUsuario estado) {
        Label badge = new Label(estado.toString());
        badge.getStyleClass().add("badge-estado");

        switch (estado) {
            case ACTIVO:
                badge.getStyleClass().add("badge-estado-activo");
                break;
            case INACTIVO:
                badge.getStyleClass().add("badge-estado-inactivo");
                break;
            case SUSPENDIDO:
                badge.getStyleClass().add("badge-estado-suspendido");
                break;
        }

        return badge;
    }

    /**
     * Crea los botones de acción (Editar e Inactivar) para cada usuario
     */
    private HBox crearBotonesAccion(Usuario usuario) {
        HBox container = new HBox(8);
        container.setAlignment(Pos.CENTER);
        container.getStyleClass().add("action-buttons-container");

        // Botón EDITAR (Azul con lápiz)
        Button btnEditar = new Button("✏️");
        btnEditar.getStyleClass().add("btn-action-editar");
        btnEditar.setTooltip(new Tooltip("Editar usuario"));
        btnEditar.setOnAction(event -> editarUsuario(usuario));

        // Botón INACTIVAR (Rojo con canasta)
        Button btnInactivar = new Button("🗑️");
        btnInactivar.getStyleClass().add("btn-action-inactivar");
        btnInactivar.setTooltip(new Tooltip("Inactivar usuario"));
        btnInactivar.setOnAction(event -> inactivarUsuario(usuario));

        // Deshabilitar botón de inactivar si el usuario ya está inactivo
        if (usuario.getEstadoUsuario() == EstadoUsuario.INACTIVO) {
            btnInactivar.setDisable(true);
        }

        container.getChildren().addAll(btnEditar, btnInactivar);
        return container;
    }

    private void inactivarUsuario(Usuario usuario) {
    }

    private void editarUsuario(Usuario usuario) {

    }

    private void getUsuarios() {
        todosLosUsuarios = new ArrayList<>(usuarioController.getUsuarios());
        paginaActual = 0; // Resetear a la primera página
    }

    private void actualizarTablaPaginada() {
        // Calcular el número total de páginas
        totalPaginas = (int) Math.ceil((double) todosLosUsuarios.size() / USUARIOS_POR_PAGINA);

        // Si no hay usuarios, mostrar página vacía
        if (todosLosUsuarios.isEmpty()) {
            listaUsuarios.clear();
            tblUsuarios.setItems(listaUsuarios);
            actualizarControlesPaginacion();
            return;
        }

        // Calcular el rango de usuarios a mostrar
        int inicio = paginaActual * USUARIOS_POR_PAGINA;
        int fin = Math.min(inicio + USUARIOS_POR_PAGINA, todosLosUsuarios.size());

        // Obtener la sublista de usuarios para la página actual
        List<Usuario> usuariosPagina = todosLosUsuarios.subList(inicio, fin);

        // Actualizar la tabla
        listaUsuarios.clear();
        listaUsuarios.addAll(usuariosPagina);
        tblUsuarios.setItems(listaUsuarios);

        // Actualizar los controles de paginación
        actualizarControlesPaginacion();
    }

    private void actualizarControlesPaginacion() {
        // Calcular información de paginación
        int totalUsuarios = todosLosUsuarios.size();
        int inicio = (paginaActual * USUARIOS_POR_PAGINA) + 1;
        int fin = Math.min((paginaActual + 1) * USUARIOS_POR_PAGINA, totalUsuarios);

        // Actualizar labels
        if (totalUsuarios == 0) {
            lblPaginacion.setText("No hay usuarios para mostrar");
            lblPaginaActual.setText("0");
            lblTotalPaginas.setText("0");
        } else {
            lblPaginacion.setText(String.format("Mostrando %d-%d de %d", inicio, fin, totalUsuarios));
            lblPaginaActual.setText(String.valueOf(paginaActual + 1));
            lblTotalPaginas.setText(String.valueOf(Math.max(totalPaginas, 1)));
        }

        // Habilitar/deshabilitar botones
        btnPaginaAnterior.setDisable(paginaActual == 0);
        btnPaginaSiguiente.setDisable(paginaActual >= totalPaginas - 1 || totalUsuarios == 0);
    }


    @Override
    public void updateView(EventType event) {
        switch (event) {
            case USUARIO:
                getUsuarios();
                break;
            default:
                break;
        }

    }
}
