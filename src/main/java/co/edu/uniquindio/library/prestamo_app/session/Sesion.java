package co.edu.uniquindio.library.prestamo_app.session;

import co.edu.uniquindio.library.prestamo_app.model.Bibliotecario;

public class Sesion {

    private Bibliotecario bibliotecario;
    private  static Sesion INSTANCE;

    private Sesion() {

    }

    public static Sesion getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Sesion();
        }
        return INSTANCE;
    }

    public void setBibliotecario(Bibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
    }

    public Bibliotecario getBibliotecario() {
        return bibliotecario;
    }

    public void closeSesion() {
        this.bibliotecario = null;
    }

}
