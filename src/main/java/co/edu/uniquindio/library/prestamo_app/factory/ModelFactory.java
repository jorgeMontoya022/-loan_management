package co.edu.uniquindio.library.prestamo_app.factory;

import co.edu.uniquindio.library.prestamo_app.model.*;
import co.edu.uniquindio.library.prestamo_app.util.BibliotecaUtil;
import co.edu.uniquindio.library.prestamo_app.util.PersistenceUtil;

import java.util.List;

public class ModelFactory {
    Biblioteca biblioteca;

    private static ModelFactory modelFactory;

    private ModelFactory() {
        loadXMLResource();

        if(biblioteca == null) {
            initalizeData();
            saveXMLResource();
        }
    }

    private void loadXMLResource() {
        biblioteca = PersistenceUtil.loadXMLBibliotecaResource();
    }

    private void saveXMLResource() {
        PersistenceUtil.saveXMLBibliotecaResource(biblioteca);
    }

    private void initalizeData() {
        biblioteca = BibliotecaUtil.initializeData();
    }

    public static ModelFactory getInstance() {
        if (modelFactory == null) {
            modelFactory = new ModelFactory();
        }
        return modelFactory;
    }

    public boolean agregarUsuario(Usuario usuario) {
        try {
            Usuario usuarioEncontrado = biblioteca.buscarUsuarioPorEmail(usuario.getEmail());
            if(usuarioEncontrado != null){
                return false;
            }
            biblioteca.agregarUsuario(usuario);
            saveXMLResource();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Usuario> getUsuarios() {
        return biblioteca.getUsuarios();
    }

    public Bibliotecario validarAcceso(String usuario, String password) throws Exception{
        return biblioteca.validarAcceso(usuario, password);
    }

    public List<Prestamo> getPrestamos() {
        return biblioteca.getPrestamos();
    }


    public int contarUsuariosActivos() {
        return biblioteca.contarUsuariosActivos();
    }

    public int contarPrestamosActivos() {
        return biblioteca.contarPrestamosActivos();
    }

    public Usuario buscarUsuarioPorDocumento(String documento) {
        return biblioteca.buscarUsuarioPorDocumento(documento);
    }

    public List<Usuario> buscarUsuariosPorNombre(String criterio) {
        return biblioteca.buscarUsuariosPorNombre(criterio);
    }

    public List<Ejemplar> buscarEjemplaresDisponiblesPorISBN(String isbn) {
        return biblioteca.buscarEjemplaresDisponiblesPorISBN(isbn);
    }

    public List<Ejemplar> getEjemplares() {
        return biblioteca.getEjemplares();
    }

    public boolean registrarPrestamo(Prestamo prestamo) {
        try {
            if(!biblioteca.registrarPrestamo(prestamo)) {
                return false;
            } else {
                saveXMLResource();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String validarUsuarioParaPrestamo(Usuario usuario) {
        if(!biblioteca.validarUsuarioActivo(usuario)) {
            return "El usuario no está activo";
        }
        if (!biblioteca.validarSinMultasPendientes(usuario)) {
            double totalMultas = biblioteca.calcularTotalMultas(usuario.getDocumento());
            return String.format("El usuario tiene multas pendientes por $%.0f", totalMultas);
        }

        int prestamosActivos = biblioteca.contarPrestamosActivos(usuario.getDocumento());
        if (prestamosActivos >= 3) {
            return "El usuario ha alcanzado el límite de 3 préstamos activos";
        }

        return null;

    }

    public List<Prestamo> obtenerPrestamosActivos(String usuarioId) {
        return biblioteca.obtenerPrestamosActivos(usuarioId);
    }

    public boolean cerrarPrestamo(Long prestamoId) {
        try {
            boolean resultado = biblioteca.cerrarPrestamo(prestamoId);
            if (resultado) {
                saveXMLResource();
            }
            return resultado;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public double calcularTotalMultas(String usuarioId) {
        return biblioteca.calcularTotalMultas(usuarioId);
    }

    public boolean pagarMulta(Long multaId) {
        try {
            boolean resultado = biblioteca.pagarMulta(multaId);
            if (resultado) {
                saveXMLResource();
            }
            return resultado;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Multa> getMultas() {
        return biblioteca.getMultas();
    }


}
