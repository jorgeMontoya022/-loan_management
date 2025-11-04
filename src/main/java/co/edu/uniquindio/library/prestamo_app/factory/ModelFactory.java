package co.edu.uniquindio.library.prestamo_app.factory;

import co.edu.uniquindio.library.prestamo_app.model.Biblioteca;
import co.edu.uniquindio.library.prestamo_app.model.Usuario;
import co.edu.uniquindio.library.prestamo_app.util.BibliotecaUtil;
import co.edu.uniquindio.library.prestamo_app.util.PersistenceUtil;

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
}
