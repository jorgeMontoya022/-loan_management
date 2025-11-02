package co.edu.uniquindio.library.prestamo_app.model;

import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private List<Usuario> usuarios;
    private List<Ejemplar> ejemplares;
    private List<Prestamo> prestamos;
    private List<Multa> multas;
    private List<Bibliotecario> bibliotecarios;
    private List<RegistroAuditoria> auditorias;

    public Biblioteca () {
        this.usuarios = new ArrayList<>();
        this.ejemplares = new ArrayList<>();
        this.prestamos = new ArrayList<>();
        this.multas = new ArrayList<>();
        this.bibliotecarios = new ArrayList<>();
        this.auditorias = new ArrayList<>();
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Ejemplar> getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(List<Ejemplar> ejemplares) {
        this.ejemplares = ejemplares;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    public List<Multa> getMultas() {
        return multas;
    }

    public void setMultas(List<Multa> multas) {
        this.multas = multas;
    }

    public List<Bibliotecario> getBibliotecarios() {
        return bibliotecarios;
    }

    public void setBibliotecarios(List<Bibliotecario> bibliotecarios) {
        this.bibliotecarios = bibliotecarios;
    }

    public List<RegistroAuditoria> getAuditorias() {
        return auditorias;
    }

    public void setAuditorias(List<RegistroAuditoria> auditorias) {
        this.auditorias = auditorias;
    }
}
