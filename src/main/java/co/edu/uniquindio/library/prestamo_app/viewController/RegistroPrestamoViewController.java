package co.edu.uniquindio.library.prestamo_app.viewController;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.library.prestamo_app.controller.PrestamoController;
import co.edu.uniquindio.library.prestamo_app.model.*;
import co.edu.uniquindio.library.prestamo_app.model.enums.EstadoEjemplar;
import co.edu.uniquindio.library.prestamo_app.session.Sesion;
import co.edu.uniquindio.library.prestamo_app.util.PDFGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegistroPrestamoViewController extends CoreViewController {

    PrestamoController prestamoController;
    Bibliotecario logginBibliotecario;

    private Usuario usuarioSeleccionado;
    private Ejemplar ejemplarSeleccionado;

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
    private Label lblEjemplarSeleccion;

    @FXML
    private Label lblFechaCompromiso;

    @FXML
    private Label lblFechaInicio;

    @FXML
    private Label lblUsuarioSeleccion;

    @FXML
    private TextField txtBuscarEjemplar;

    @FXML
    private TextField txtBuscarUsuario;

    @FXML
    private ComboBox<Ejemplar> comboEjemplares;

    @FXML
    private ComboBox<Usuario> comboUsuarios;

    @FXML
    void onBuscarEjemplar(ActionEvent event) {
        String criterio = txtBuscarEjemplar.getText().trim();

        if (criterio.isEmpty()) {
            mostrarMensaje("Error", "Búsqueda Vacía", "Por favor ingrese un ISBN o título para buscar", Alert.AlertType.WARNING);
            return;
        }

        // Buscar por ISBN
        List<Ejemplar> ejemplares = prestamoController.buscarEjemplaresDisponiblesPorISBN(criterio);

        if (ejemplares.isEmpty()) {
            mostrarMensaje("Sin Resultados", "Ejemplar No Encontrado",
                    "No se encontraron ejemplares disponibles con el criterio: " + criterio, Alert.AlertType.INFORMATION);
            comboEjemplares.setItems(null);
            return;
        }

        ObservableList<Ejemplar> ejemplaresObservable = FXCollections.observableArrayList(ejemplares);
        comboEjemplares.setItems(ejemplaresObservable);

        // Si solo hay un resultado, seleccionarlo automáticamente
        if (ejemplares.size() == 1) {
            comboEjemplares.setValue(ejemplares.get(0));
            seleccionarEjemplar(ejemplares.get(0));
        }
    }

    @FXML
    void onBuscarUsuario(ActionEvent event) {
        String criterio = txtBuscarUsuario.getText().trim();

        if (criterio.isEmpty()) {
            mostrarMensaje("Error", "Búsqueda Vacía", "Por favor ingrese un documento o email para buscar", Alert.AlertType.WARNING);
            return;
        }

        // Buscar por documento primero
        Usuario usuario = prestamoController.buscarUsuarioPorDocumento(criterio);

        ObservableList<Usuario> usuarios = FXCollections.observableArrayList();

        if (usuario != null) {
            usuarios.add(usuario);
        } else {
            // Si no encuentra por documento, buscar por nombre
            List<Usuario> usuariosEncontrados = prestamoController.buscarUsuariosPorNombre(criterio);
            usuarios.addAll(usuariosEncontrados);
        }

        if (usuarios.isEmpty()) {
            mostrarMensaje("Sin Resultados", "Usuario No Encontrado",
                    "No se encontraron usuarios con el criterio: " + criterio, Alert.AlertType.INFORMATION);
            comboUsuarios.setItems(null);
            return;
        }

        comboUsuarios.setItems(usuarios);

        // Si solo hay un resultado, seleccionarlo automáticamente
        if (usuarios.size() == 1) {
            comboUsuarios.setValue(usuarios.get(0));
            seleccionarUsuario(usuarios.get(0));
        }
    }

    @FXML
    void onCancelar(ActionEvent event) {
        limpiarFormulario();
    }

    @FXML
    void onConfirmarPrestamo(ActionEvent event) {
        confirmarPrestamo();
    }

    @FXML
    void onVolver(ActionEvent event) {
        browseWindow("/co/edu/uniquindio/library/prestamo_app/view/gestion-prestamos-view.fxml", "Gestión prestamos", event);
    }

    @FXML
    void initialize() {
        prestamoController = new PrestamoController();
        logginBibliotecario = (Bibliotecario) Sesion.getInstance().getBibliotecario();

        // Configurar listeners para los ComboBox
        comboUsuarios.setOnAction(e -> {
            Usuario usuario = comboUsuarios.getValue();
            if (usuario != null) {
                seleccionarUsuario(usuario);
            }
        });

        comboEjemplares.setOnAction(e -> {
            Ejemplar ejemplar = comboEjemplares.getValue();
            if (ejemplar != null) {
                seleccionarEjemplar(ejemplar);
            }
        });

        // Configurar formato de visualización en los ComboBox
        comboUsuarios.setCellFactory(lv -> new ListCell<Usuario>() {
            @Override
            protected void updateItem(Usuario item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNombre() + " - " + item.getDocumento());
            }
        });

        comboUsuarios.setButtonCell(new ListCell<Usuario>() {
            @Override
            protected void updateItem(Usuario item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNombre() + " - " + item.getDocumento());
            }
        });

        comboEjemplares.setCellFactory(lv -> new ListCell<Ejemplar>() {
            @Override
            protected void updateItem(Ejemplar item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getTitulo() + " - ISBN: " + item.getIsbn());
            }
        });

        comboEjemplares.setButtonCell(new ListCell<Ejemplar>() {
            @Override
            protected void updateItem(Ejemplar item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getTitulo() + " - ISBN: " + item.getIsbn());
            }
        });
    }

    // ==================== MÉTODOS AUXILIARES ====================

    private void seleccionarUsuario(Usuario usuario) {
        usuarioSeleccionado = usuario;

        // Validar si el usuario puede hacer préstamos
        String mensajeError = prestamoController.validarUsuarioParaPrestamo(usuario);

        if (mensajeError != null) {
            mostrarMensaje("Usuario No Válido", "El usuario no puede realizar préstamos",
                    mensajeError, Alert.AlertType.WARNING);
            limpiarSeleccionUsuario();
            return;
        }

        // Actualizar etiqueta de selección
        lblUsuarioSeleccion.setText("✓ " + usuario.getNombre() + " (Doc: " + usuario.getDocumento() + ")");
        lblUsuarioSeleccion.setStyle("-fx-text-fill: #22C55E; -fx-font-weight: bold;");

        verificarYActualizarFechas();
    }

    private void seleccionarEjemplar(Ejemplar ejemplar) {
        ejemplarSeleccionado = ejemplar;

        // Verificar que esté disponible
        if (ejemplar.getEstadoEjemplar() != EstadoEjemplar.DISPONIBLE) {
            mostrarMensaje("Ejemplar No Disponible", "El ejemplar no está disponible",
                    "El ejemplar seleccionado no está disponible para préstamo", Alert.AlertType.WARNING);
            limpiarSeleccionEjemplar();
            return;
        }

        // Actualizar etiqueta de selección
        lblEjemplarSeleccion.setText("✓ " + ejemplar.getTitulo() + " - " + ejemplar.getAutor());
        lblEjemplarSeleccion.setStyle("-fx-text-fill: #22C55E; -fx-font-weight: bold;");

        verificarYActualizarFechas();
    }

    private void verificarYActualizarFechas() {
        if (usuarioSeleccionado != null && ejemplarSeleccionado != null) {
            LocalDate fechaInicio = LocalDate.now();
            LocalDate fechaCompromiso = fechaInicio.plusDays(15);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            lblFechaInicio.setText(fechaInicio.format(formatter));
            lblFechaCompromiso.setText(fechaCompromiso.format(formatter));
        }
    }

    private void confirmarPrestamo() {
        if (usuarioSeleccionado == null || ejemplarSeleccionado == null) {
            mostrarMensaje("Error", "Selección Incompleta",
                    "Debe seleccionar un usuario y un ejemplar para continuar", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Crear el préstamo
            LocalDate fechaInicio = LocalDate.now();
            LocalDate fechaCompromiso = fechaInicio.plusDays(15);

            Prestamo prestamo = new Prestamo(
                    usuarioSeleccionado,
                    ejemplarSeleccionado,
                    logginBibliotecario,
                    fechaInicio,
                    fechaCompromiso
            );

            // Intentar registrar el préstamo
            boolean resultado = prestamoController.registrarPrestamo(prestamo);

            if (resultado) {
                // Generar automáticamente el comprobante en PDF
                boolean pdfGenerado = generarComprobantePDF(prestamo);

                String mensaje = "El préstamo se ha registrado exitosamente.";
                if (pdfGenerado) {
                    mensaje += "\n\n✓ El comprobante PDF ha sido generado.";
                } else {
                    mensaje += "\n\n⚠ No se pudo generar el comprobante PDF.";
                }

                mostrarMensaje("Éxito", "Préstamo Registrado", mensaje, Alert.AlertType.INFORMATION);
                limpiarFormulario();
            } else {
                mostrarMensaje("Error", "Error al Registrar",
                        "No se pudo registrar el préstamo", Alert.AlertType.ERROR);
            }

        } catch (IllegalStateException e) {
            String mensaje = obtenerMensajeError(e.getMessage());
            mostrarMensaje("Error de Validación", "No se puede realizar el préstamo", mensaje, Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarMensaje("Error", "Error Inesperado",
                    "Ocurrió un error al registrar el préstamo: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Genera el comprobante PDF del préstamo automáticamente
     */
    private boolean generarComprobantePDF(Prestamo prestamo) {
        try {
            // Crear carpeta de comprobantes si no existe
            String carpetaComprobantes = "comprobantes_prestamos";
            File carpeta = new File(carpetaComprobantes);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            // Generar nombre del archivo
            String timestamp = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String nombreArchivo = String.format("%s/Prestamo_%d_%s_%s.pdf",
                    carpetaComprobantes,
                    prestamo.getId(),
                    prestamo.getUsuario().getDocumento(),
                    timestamp);

            // Generar el PDF
            boolean resultado = PDFGenerator.generarComprobantePrestamo(prestamo, nombreArchivo);

            // Si se generó correctamente, intentar abrirlo automáticamente
            if (resultado) {
                try {
                    File pdfFile = new File(nombreArchivo);
                    if (pdfFile.exists()) {
                        // Abrir el PDF con el visor predeterminado del sistema
                        if (java.awt.Desktop.isDesktopSupported()) {
                            java.awt.Desktop.getDesktop().open(pdfFile);
                        }
                    }
                } catch (Exception e) {
                    // Si falla al abrir, no es crítico, el archivo se guardó correctamente
                    System.out.println("No se pudo abrir el PDF automáticamente: " + e.getMessage());
                }
            }

            return resultado;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void limpiarFormulario() {
        txtBuscarEjemplar.clear();
        txtBuscarUsuario.clear();
        limpiarSeleccionUsuario();
        limpiarSeleccionEjemplar();

        lblFechaInicio.setText("01/11/2025");
        lblFechaCompromiso.setText("15/11/2025");
    }

    private void limpiarSeleccionUsuario() {
        usuarioSeleccionado = null;
        comboUsuarios.setValue(null);
        comboUsuarios.setItems(null);
        lblUsuarioSeleccion.setText("Usuario seleccionado");
        lblUsuarioSeleccion.setStyle("-fx-text-fill: #9CA3AF;");
    }

    private void limpiarSeleccionEjemplar() {
        ejemplarSeleccionado = null;
        comboEjemplares.setValue(null);
        comboEjemplares.setItems(null);
        lblEjemplarSeleccion.setText("Ejemplar seleccionado");

    }

    private String obtenerMensajeError(String codigoError) {
        switch (codigoError) {
            case "USUARIO_NO_ACTIVO":
                return "El usuario no está activo en el sistema";
            case "LIMITE_PRESTAMOS_ALCANZADO":
                return "El usuario ha alcanzado el límite de 3 préstamos activos";
            case "USUARIO_CON_MULTAS":
                return "El usuario tiene multas pendientes que debe pagar";
            case "EJEMPLAR_NO_DISPONIBLE":
                return "El ejemplar no está disponible para préstamo";
            case "PRESTAMO_DUPLICADO":
                return "El ejemplar ya tiene un préstamo activo";
            default:
                return "Error desconocido: " + codigoError;
        }
    }


}