package co.edu.uniquindio.library.prestamo_app.factory;

import co.edu.uniquindio.library.prestamo_app.model.Biblioteca;
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
}
