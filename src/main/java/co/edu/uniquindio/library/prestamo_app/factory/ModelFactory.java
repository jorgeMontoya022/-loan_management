package co.edu.uniquindio.library.prestamo_app.factory;

import co.edu.uniquindio.library.prestamo_app.model.Biblioteca;
import co.edu.uniquindio.library.prestamo_app.util.BibliotecaUtil;

public class ModelFactory {
    Biblioteca biblioteca;

    private static ModelFactory modelFactory;

    private ModelFactory() {
        if(biblioteca == null) {
            initalizeData();
        }
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
