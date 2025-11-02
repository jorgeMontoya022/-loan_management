package co.edu.uniquindio.library.prestamo_app.model;

import co.edu.uniquindio.library.prestamo_app.model.enums.EstadoPrestamo;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Prestamo {

    private static final AtomicLong contador = new AtomicLong(1);

    private Long id;
    private Usuario usuario;
    private Ejemplar ejemplar;
    private Bibliotecario bibliotecario;
    private LocalDate fechaInicio;
    private LocalDate fechaFinCompromiso;
    private LocalDate fechaDevolucion;
    private EstadoPrestamo estadoPrestamo;
    private String observaciones;
    private List<Multa> multasAsociadas;

    public Prestamo(Usuario usuario, Ejemplar ejemplar, Bibliotecario bibliotecario, LocalDate fechaInicio, LocalDate fechaFinCompromiso, LocalDate fechaDevolucion) {
        this.id = generateId();
        this.usuario = usuario;
        this.ejemplar = ejemplar;
        this.estadoPrestamo = EstadoPrestamo.ACTIVO;
        this.bibliotecario = bibliotecario;
        this.fechaInicio = fechaInicio;
        this.fechaFinCompromiso = fechaFinCompromiso;
        this.fechaDevolucion = fechaDevolucion;
    }
    public Prestamo() {

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Ejemplar getEjemplar() {
        return ejemplar;
    }

    public void setEjemplar(Ejemplar ejemplar) {
        this.ejemplar = ejemplar;
    }

    public Bibliotecario getBibliotecario() {
        return bibliotecario;
    }

    public void setBibliotecario(Bibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFinCompromiso() {
        return fechaFinCompromiso;
    }

    public void setFechaFinCompromiso(LocalDate fechaFinCompromiso) {
        this.fechaFinCompromiso = fechaFinCompromiso;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public EstadoPrestamo getEstadoPrestamo() {
        return estadoPrestamo;
    }

    public void setEstadoPrestamo(EstadoPrestamo estadoPrestamo) {
        this.estadoPrestamo = estadoPrestamo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<Multa> getMultasAsociadas() {
        return multasAsociadas;
    }

    public void setMultasAsociadas(List<Multa> multasAsociadas) {
        this.multasAsociadas = multasAsociadas;
    }
}
