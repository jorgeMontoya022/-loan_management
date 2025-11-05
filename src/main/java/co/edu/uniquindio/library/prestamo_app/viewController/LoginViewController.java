package co.edu.uniquindio.library.prestamo_app.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.library.prestamo_app.controller.LoginController;
import co.edu.uniquindio.library.prestamo_app.model.Bibliotecario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginViewController extends CoreViewController{

    LoginController loginController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnIniciarSesion;

    @FXML
    private CheckBox chkRecordarme;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsuario;

    @FXML
    void onIniciarSesion(ActionEvent event) {
        iniciarSesion(event);

    }



    @FXML
    void initialize() {
        loginController = new LoginController();


    }
    private void iniciarSesion(ActionEvent event) {
        if(!validarCamposRequeridos()) {
            return;
        }
        try {
            String usuario = txtUsuario.getText().trim();
            String password = txtPassword.getText().trim();

            Bibliotecario bibliotecarioValidado = loginController.validarAcceso(usuario, password);
            if(bibliotecarioValidado == null) {
                mostrarMensaje("Credenciales inválidas",
                        "Error de inicio de sesión",
                        "El usuario o contraseña son incorrectos Por favor verifique e intente nuevamente.",
                        Alert.AlertType.ERROR);
                return;
            }
            loginController.guardarSesion(bibliotecarioValidado);
            browseWindow("/co/edu/uniquindio/library/prestamo_app/view/dashboard-view.fxml", "Dashboard", event);

        }catch (Exception e) {
            manejarErrorInicioSesion(e);
        }
    }

    private boolean validarCamposRequeridos() {
        if(txtUsuario.getText().trim().isEmpty() || txtPassword.getText().trim().isEmpty()) {
            mostrarMensaje( "Campos requeridos",
                    "Información incompleta",
                    "Por favor complete todos los campos antes de continuar.",
                    Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private void manejarErrorInicioSesion(Exception e) {
        mostrarMensaje(
                "Error de inicio de sesión",
                "No se pudo completar el inicio de sesión",
                "Ocurrió un error inesperado durante el inicio de sesión: " + e.getMessage() +
                        "\nPor favor intente nuevamente o contacte al administrador del sistema.",
                Alert.AlertType.ERROR
        );
        e.printStackTrace(); // Para registro/debugging
    }

}
