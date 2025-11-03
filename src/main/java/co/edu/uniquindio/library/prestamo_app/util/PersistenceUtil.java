package co.edu.uniquindio.library.prestamo_app.util;

import co.edu.uniquindio.library.prestamo_app.model.Biblioteca;

import java.util.ResourceBundle;

public class PersistenceUtil {
    private static final ResourceBundle config = ResourceBundle.getBundle("co.edu.uniquindio.library.prestamo_app.config.config");

    public static final String BIBLIOTECA_XML_FILE_PATH = config.getString("bibliotecaXmlFilePath");

   public static void saveXMLBibliotecaResource(Biblioteca biblioteca) {
       try {
           FileUtil.saveSerializedXMLResource(BIBLIOTECA_XML_FILE_PATH, biblioteca);
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

    public static Biblioteca loadXMLBibliotecaResource() {
        return loadXMLBibliotecaResource(BIBLIOTECA_XML_FILE_PATH);
    }

    private static Biblioteca loadXMLBibliotecaResource(String filePath) {
        Biblioteca biblioteca = null;
        try {
            biblioteca = (Biblioteca) FileUtil.loadSerializedXMLResource(filePath);
        } catch (Exception e) {
            System.out.println("No se pudo cargar el archivo XML. Creando nuevo.");
            e.printStackTrace(); // Opcional: para ver el error en consola
        }
        return biblioteca;
    }



}
