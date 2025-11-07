package co.edu.uniquindio.library.prestamo_app.controller;

import co.edu.uniquindio.library.prestamo_app.factory.ModelFactory;
import co.edu.uniquindio.library.prestamo_app.model.*;

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

    public Usuario buscarUsuarioPorDocumento(String documento) {
        return modelFactory.buscarUsuarioPorDocumento(documento);
    }

    public List<Usuario> buscarUsuariosPorNombre(String criterio) {
        return modelFactory.buscarUsuariosPorNombre(criterio);
    }

    public List<Ejemplar> buscarEjemplaresDisponiblesPorISBN(String isbn) {
        return modelFactory.buscarEjemplaresDisponiblesPorISBN(isbn);
    }

    public List<Ejemplar> getEjemplares() {
        return modelFactory.getEjemplares();
    }

    public boolean registrarPrestamo(Prestamo prestamo) {
        return modelFactory.registrarPrestamo(prestamo);
    }

    public String validarUsuarioParaPrestamo(Usuario usuario) {
        return modelFactory.validarUsuarioParaPrestamo(usuario);
    }

    public List<Prestamo> obtenerPrestamosActivos(String usuarioId) {
        return modelFactory.obtenerPrestamosActivos(usuarioId);
    }

    public boolean cerrarPrestamo(Long prestamoId) {
        return modelFactory.cerrarPrestamo(prestamoId);
    }

    public double calcularTotalMultas(String usuarioId) {
        return modelFactory.calcularTotalMultas(usuarioId);
    }

    public boolean pagarMulta(Long multaId) {
        return modelFactory.pagarMulta(multaId);
    }

    public List<Multa> getMultas() {
        return modelFactory.getMultas();
    }

    public boolean generarReportePrestamosActivos(String rutaArchivo, Bibliotecario generador) {
        List<Prestamo> prestamos = getPrestamos();
        return co.edu.uniquindio.library.prestamo_app.util.PDFGenerator.generarReportePrestamosActivos(
                prestamos, rutaArchivo, generador);
    }

    public boolean generarReporteUsuario(Usuario usuario, String rutaArchivo, Bibliotecario generador) {
        List<Prestamo> prestamos = obtenerPrestamosActivos(usuario.getDocumento());
        return co.edu.uniquindio.library.prestamo_app.util.PDFGenerator.generarReporteUsuario(
                usuario, prestamos, rutaArchivo, generador);
    }



}
