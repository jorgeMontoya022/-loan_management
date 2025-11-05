package co.edu.uniquindio.library.prestamo_app.controller;

import co.edu.uniquindio.library.prestamo_app.factory.ModelFactory;

public class PrestamoController {
    ModelFactory modelFactory;

    public PrestamoController() {
        this.modelFactory = ModelFactory.getInstance();
    }
}
