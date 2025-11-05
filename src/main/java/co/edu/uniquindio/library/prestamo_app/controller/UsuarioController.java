package co.edu.uniquindio.library.prestamo_app.controller;

import co.edu.uniquindio.library.prestamo_app.factory.ModelFactory;
import co.edu.uniquindio.library.prestamo_app.model.Usuario;

import java.util.Collection;
import java.util.List;

public class UsuarioController {
    ModelFactory modelFactory;

    public UsuarioController(){
        this.modelFactory = ModelFactory.getInstance();
    }

    public boolean agregarUsuario(Usuario usuario) {
        return modelFactory.agregarUsuario(usuario);
    }

    public List<Usuario> getUsuarios() {
        return modelFactory.getUsuarios();
    }


    public int contarUsuariosActivos() {
        return modelFactory.contarUsuariosActivos();
    }
}
