package co.edu.uniquindio.library.prestamo_app.model;

import co.edu.uniquindio.library.prestamo_app.model.enums.EstadoUsuario;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final AtomicLong contador = new AtomicLong(1);

    private Long id;
    private String nombre;
    private String documento;
    private String programa;
    private String email;
    private EstadoUsuario estadoUsuario;
    private LocalDate fechaRegistro;
    private List<Multa> multasAsociadas;
    private List<Prestamo>prestamosAsociados;

    public Usuario(String nombre, String documento, String programa, String email) {
        this.id = generateId();
        this.nombre = nombre;
        this.documento = documento;
        this.programa = programa;
        this.email = email;
        this.estadoUsuario = EstadoUsuario.ACTIVO;
        this.multasAsociadas = new ArrayList<>();
        this.prestamosAsociados = new ArrayList<>();
    }

    public Usuario() {

    }

    private Long generateId() {
        return contador.getAndIncrement();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EstadoUsuario getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(EstadoUsuario estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public List<Multa> getMultasAsociadas() {
        return multasAsociadas;
    }

    public void setMultasAsociadas(List<Multa> multasAsociadas) {
        this.multasAsociadas = multasAsociadas;
    }

    public List<Prestamo> getPrestamosAsociados() {
        return prestamosAsociados;
    }

    public void setPrestamosAsociados(List<Prestamo> prestamosAsociados) {
        this.prestamosAsociados = prestamosAsociados;
    }
}
