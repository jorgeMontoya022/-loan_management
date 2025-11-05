package co.edu.uniquindio.library.prestamo_app.controller;

import co.edu.uniquindio.library.prestamo_app.factory.ModelFactory;
import co.edu.uniquindio.library.prestamo_app.model.Prestamo;

import java.util.List;

public class PrestamoController {
    ModelFactory modelFactory;

    public PrestamoController() {
        this.modelFactory = ModelFactory.getInstance();
    }

    public List<Prestamo> getPrestamos() {
        return modelFactory.getPrestamos();
    }

    public int contarPrestamosActivos() {
        return modelFactory.contarPrestamosActivos();
    }
}
