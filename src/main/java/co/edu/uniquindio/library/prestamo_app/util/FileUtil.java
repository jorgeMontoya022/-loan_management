package co.edu.uniquindio.library.prestamo_app.util;

import co.edu.uniquindio.library.prestamo_app.model.Biblioteca;

import java.beans.*;
import java.io.IOException;
import java.io.*;
import java.time.LocalDate;

public class FileUtil {


    public static Object loadSerializedXMLResource(String filePath) throws IOException {
        XMLDecoder xmlDecoder;
        Object xmlObject;

        xmlDecoder = new XMLDecoder(new FileInputStream(filePath));
        xmlObject = xmlDecoder.readObject();
        xmlDecoder.close();
        return xmlObject;
    }

    public static void saveSerializedXMLResource(String filePath, Object object) throws IOException {
        XMLEncoder xmlEncoder = null;
        try {
            xmlEncoder = new XMLEncoder(new FileOutputStream(filePath));
            // Registrar un PersistenceDelegate para LocalDate
            xmlEncoder.setPersistenceDelegate(LocalDate.class, new DefaultPersistenceDelegate() {
                @Override
                protected Expression instantiate(Object obj, Encoder enc) {
                    LocalDate date = (LocalDate) obj;
                    return new Expression(obj, LocalDate.class, "of", new Object[]{
                            date.getYear(),
                            date.getMonthValue(),
                            date.getDayOfMonth()
                    });
                }
            });
            xmlEncoder.writeObject(object);
        } catch (IOException e) {
            throw e;
        } finally {
            if (xmlEncoder != null) {
                xmlEncoder.close();
            }
        }
    }
}
