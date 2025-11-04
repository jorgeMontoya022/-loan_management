package co.edu.uniquindio.library.prestamo_app.controller;

import co.edu.uniquindio.library.prestamo_app.factory.ModelFactory;
import co.edu.uniquindio.library.prestamo_app.model.Usuario;

public class UsuarioController {
    ModelFactory modelFactory;

    public UsuarioController(){
        this.modelFactory = ModelFactory.getInstance();
    }

    public boolean agregarUsuario(Usuario usuario) {
        return modelFactory.agregarUsuario(usuario);
    }
}
