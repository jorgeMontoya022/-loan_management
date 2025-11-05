package co.edu.uniquindio.library.prestamo_app.controller;

import co.edu.uniquindio.library.prestamo_app.factory.ModelFactory;
import co.edu.uniquindio.library.prestamo_app.model.Bibliotecario;
import co.edu.uniquindio.library.prestamo_app.session.Sesion;

public class LoginController {
    ModelFactory modelFactory;

    public LoginController() {
        this.modelFactory = ModelFactory.getInstance();
    }

    public Bibliotecario validarAcceso(String usuario, String password) throws Exception {
        return modelFactory.validarAcceso(usuario, password);
    }

    public void guardarSesion(Bibliotecario bibliotecario) {
        Sesion.getInstance().setBibliotecario(bibliotecario);
    }
}
