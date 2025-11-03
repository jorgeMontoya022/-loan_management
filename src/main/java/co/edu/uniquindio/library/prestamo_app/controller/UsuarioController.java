package co.edu.uniquindio.library.prestamo_app.controller;

import co.edu.uniquindio.library.prestamo_app.factory.ModelFactory;

public class UsuarioController {
    ModelFactory modelFactory;

    public UsuarioController(){
        this.modelFactory = ModelFactory.getInstance();
    }
}
