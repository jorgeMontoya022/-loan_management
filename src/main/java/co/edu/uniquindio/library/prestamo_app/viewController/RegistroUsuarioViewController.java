package co.edu.uniquindio.library.prestamo_app.viewController;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.edu.uniquindio.library.prestamo_app.controller.UsuarioController;
import co.edu.uniquindio.library.prestamo_app.model.Bibliotecario;
import co.edu.uniquindio.library.prestamo_app.model.Usuario;
import co.edu.uniquindio.library.prestamo_app.session.Sesion;
import co.edu.uniquindio.library.prestamo_app.viewController.observer.EventType;
import co.edu.uniquindio.library.prestamo_app.viewController.observer.ObserverManagement;
import co.edu.uniquindio.library.prestamo_app.viewController.observer.ObserverView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegistroUsuarioViewController extends CoreViewController{

    UsuarioController usuarioController;

    Bibliotecario loggedBibliotecario;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnCerrar;

    @FXML
    private Button btnGuardar;

    @FXML
    private Label lblContadorNombre;

    @FXML
    private TextField txtDocumento;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrograma;

    @FXML
    void onCancelar(ActionEvent event) {
        closeWindow(event);


    }

    @FXML
    void onGuardarUsuario(ActionEvent event) {
        agregarUsuario();

    }


    @FXML
    void initialize() {
        usuarioController = new UsuarioController();
        loggedBibliotecario = (Bibliotecario) Sesion.getInstance().getBibliotecario();

    }

    private void agregarUsuario() {
        Usuario usuario = buildUsuario();
        if (usuario == null) {
            mostrarMensaje("Error", "Datos no válidos", "El tipo de usuario seleccionado no es válido", Alert.AlertType.ERROR);
            return;
        }

        if (validarDatos(usuario)) {
            if (usuarioController.agregarUsuario(usuario)) {
                mostrarMensaje("Notificación", "Usuario agregado", "El registro del usuario se ha completado exitosamente. Todos los datos han sido almacenados de forma segura en el sistema.", Alert.AlertType.INFORMATION);
                clearFields();
                ObserverManagement.getInstance().notifyObservers(EventType.USUARIO);
            } else {
                mostrarMensaje("Error", "Usuario no agregado", "El usuario no pudo ser agregado", Alert.AlertType.ERROR);
            }
        }
    }

    private void clearFields() {
        txtDocumento.clear();
        txtEmail.clear();
        txtNombre.clear();
        txtPrograma.clear();
    }

    private Usuario buildUsuario() {
        String nombre = txtNombre.getText().trim();
        String documento = txtDocumento.getText().trim();
        String email = txtEmail.getText().trim();
        String programa = txtPrograma.getText().trim();
        return new Usuario(nombre, documento, programa, email);
    }

    private boolean validarDatos(Usuario usuario) {
        StringBuilder mensaje = new StringBuilder();

        if (usuario.getNombre() == null || usuario.getNombre().isEmpty()) {
            mensaje.append("El nombre del usuario es requerido.\n");
        }

        if (usuario.getDocumento() == null || usuario.getDocumento().isEmpty()) {
            mensaje.append("El documento del usuario es requerido.\n");
        }

        if(usuario.getPrograma() == null || usuario.getPrograma().isEmpty()) {
            mensaje.append("El programa al que pertenece el usuario es requerido.\n");
        }

        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            mensaje.append("El email es requerido.\n");
        } else if (!validarFormatoEmail(usuario.getEmail())) {
            mensaje.append("El formato del email no es valido.\n");
        }
        if (mensaje.length() > 0) {
            mostrarMensaje(
                    "Notificación de validación",
                    "Datos no válidos",
                    mensaje.toString(),
                    Alert.AlertType.WARNING
            );
            return false;

        }
        return true;

    }

    private boolean validarFormatoEmail(String email) {
        // Patrón para validar el email
        Pattern patron = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = patron.matcher(email);
        return matcher.find();
    }


}
