package co.edu.uniquindio.library.prestamo_app.viewController;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.library.prestamo_app.controller.PrestamoController;
import co.edu.uniquindio.library.prestamo_app.model.Bibliotecario;
import co.edu.uniquindio.library.prestamo_app.model.Prestamo;
import co.edu.uniquindio.library.prestamo_app.model.enums.EstadoPrestamo;
import co.edu.uniquindio.library.prestamo_app.session.Sesion;
import co.edu.uniquindio.library.prestamo_app.viewController.observer.EventType;
import co.edu.uniquindio.library.prestamo_app.viewController.observer.ObserverView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class GestionPrestamosViewController extends CoreViewController implements ObserverView {

    PrestamoController prestamoController;

    Bibliotecario logginBibliotecario;

    ObservableList<Prestamo> listaPrestamos = FXCollections.observableArrayList();


    private static final int PRESTAMOS_POR_PAGINA = 5;
    private int paginaActual = 0;
    private int totalPaginas = 0;
    private List<Prestamo> todosLosPrestamos = new ArrayList<>();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnPaginaAnterior;

    @FXML
    private Button btnPaginaSiguiente;

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<Prestamo> tblPrestamos;

    @FXML
    private TableColumn<Prestamo, String> colBibliotecario;

    @FXML
    private TableColumn<Prestamo, String> colEjemplar;

    @FXML
    private TableColumn<Prestamo, Label> colEstado;

    @FXML
    private TableColumn<Prestamo, String> colFechaCompromiso;

    @FXML
    private TableColumn<Prestamo, String> colFechaInicio;

    @FXML
    private TableColumn<Prestamo, String> colId;

    @FXML
    private TableColumn<Prestamo, String> colUsuario;

    @FXML
    private Label lblPaginaActual;

    @FXML
    private Label lblPaginacion;

    @FXML
    private Label lblTotalPaginas;


    @FXML
    private TextField txtBuscar;

    @FXML
    void onBuscarKeyReleased(KeyEvent event) {

    }

    @FXML
    void onNuevoPrestamoClick(ActionEvent event) {
        browseWindow("/co/edu/uniquindio/library/prestamo_app/view/registro-prestamos-view.fxml", "Registro prestamos", event);

    }

    @FXML
    void onPaginaAnteriorClick(ActionEvent event) {
        if (paginaActual > 0) {
            paginaActual--;
            actualizarTablaPaginada();
        }

    }

    @FXML
    void onPaginaSiguienteClick(ActionEvent event) {
        if (paginaActual < totalPaginas - 1) {
            paginaActual++;
            actualizarTablaPaginada();
        }

    }

    @FXML
    void onVolverClick(ActionEvent event) {
        browseWindow("/co/edu/uniquindio/library/prestamo_app/view/dashboard-view.fxml", "Dashboard", event);

    }

    @FXML
    void initialize() {
        prestamoController = new PrestamoController();
        logginBibliotecario = (Bibliotecario) Sesion.getInstance().getBibliotecario();
        initView();


    }

    private void initView() {
        initDataBinding();
        getPrestamos();
        actualizarTablaPaginada();
    }

    private void initDataBinding() {
        colId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        colBibliotecario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBibliotecario().getNombre()));
        colEjemplar.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEjemplar().getTitulo()));
        colUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsuario().getNombre()));
        colFechaInicio.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFechaInicio())));
        colFechaCompromiso.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFechaFinCompromiso())));
        colEstado.setCellValueFactory(cellData -> {
            Prestamo prestamo = cellData.getValue();
            Label badge = crearBadgeEstado(prestamo.getEstadoPrestamo());
            return new SimpleObjectProperty<>(badge);
        });
    }

    private Label crearBadgeEstado(EstadoPrestamo estado) {
        Label badge = new Label(estado.toString());
        badge.getStyleClass().add("badge-estado");

        switch (estado) {
            case ACTIVO:
                badge.getStyleClass().add("badge-estado-activo");
                break;
            case MOROSO:
                badge.getStyleClass().add("badge-estado-moroso");
            case CERRADO:
                badge.getStyleClass().add("badge-estado-cerrado");
                break;
        }
        return badge;
    }

    private void getPrestamos() {
        todosLosPrestamos = new ArrayList<>(prestamoController.getPrestamos());
        paginaActual = 0;
    }

    private void actualizarTablaPaginada() {
        totalPaginas = (int) Math.ceil((double) todosLosPrestamos.size() / PRESTAMOS_POR_PAGINA);

        if (todosLosPrestamos.isEmpty()) {
            listaPrestamos.clear();
            tblPrestamos.setItems(listaPrestamos);
            actualizarControlesPaginacion();
            return;
        }

        int inicio = paginaActual * PRESTAMOS_POR_PAGINA;
        int fin = Math.min(inicio + PRESTAMOS_POR_PAGINA, todosLosPrestamos.size());

        List<Prestamo> prestamosPagina = todosLosPrestamos.subList(inicio, fin);

        listaPrestamos.clear();
        listaPrestamos.addAll(prestamosPagina);
        tblPrestamos.setItems(listaPrestamos);

        actualizarControlesPaginacion();
    }

    private void actualizarControlesPaginacion() {
        int totalPrestamos = todosLosPrestamos.size();
        int inicio = (paginaActual * PRESTAMOS_POR_PAGINA) + 1;
        int fin = Math.min((paginaActual + 1) * PRESTAMOS_POR_PAGINA, totalPrestamos);

        if (totalPrestamos == 0) {
            lblPaginacion.setText("No hay prestamos para mostrar");
            lblPaginaActual.setText("0");
            lblTotalPaginas.setText("0");
        } else {
            lblPaginacion.setText(String.format("Mostrando %d-%d de %d", inicio, fin, totalPrestamos));
            lblPaginaActual.setText(String.valueOf(paginaActual + 1));
            lblTotalPaginas.setText(String.valueOf(Math.max(totalPaginas, 1)));
        }

        btnPaginaAnterior.setDisable(paginaActual == 0);
        btnPaginaSiguiente.setDisable(paginaActual >= totalPaginas - 1 || totalPrestamos == 0);
    }

    @Override
    public void updateView(EventType event) {
        switch (event) {
            case PRESTAMO, MULTA:
                getPrestamos();
                break;
            default:
                break;

        }
    }
}
