package co.edu.uniquindio.library.prestamo_app.model;

import co.edu.uniquindio.library.prestamo_app.model.enums.EstadoMulta;

import java.io.Serializable;
import java.time.LocalDate;

public class Multa implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Prestamo prestamo;
    private double valor;
    private String motivo;
    private LocalDate fechaGeneracion;
    private LocalDate fechaPago;
    private EstadoMulta estadoMulta;
    private Bibliotecario bibliotecarioRegistro;
    private Usuario usuarioAsociado;

    public Multa( Prestamo prestamo, double valor, String motivo, LocalDate fechaGeneracion, LocalDate fechaPago, EstadoMulta estadoMulta, Bibliotecario bibliotecarioRegistro, Usuario usuarioAsociado) {
        this.id = id;
        this.prestamo = prestamo;
        this.valor = valor;
        this.motivo = motivo;
        this.fechaGeneracion = fechaGeneracion;
        this.fechaPago = fechaPago;
        this.estadoMulta = estadoMulta;
        this.bibliotecarioRegistro = bibliotecarioRegistro;
        this.usuarioAsociado = usuarioAsociado;
    }

    public Multa(Prestamo prestamo, double valor, String motivo) {
        this.prestamo = prestamo;
        this.valor = valor;
        this.motivo = motivo;
        this.fechaGeneracion = LocalDate.now();
        this.estadoMulta = EstadoMulta.PENDIENTE;
        this.usuarioAsociado = prestamo.getUsuario();
    }


    public Multa() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDate getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDate fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public EstadoMulta getEstadoMulta() {
        return estadoMulta;
    }

    public void setEstadoMulta(EstadoMulta estadoMulta) {
        this.estadoMulta = estadoMulta;
    }

    public Bibliotecario getBibliotecarioRegistro() {
        return bibliotecarioRegistro;
    }

    public void setBibliotecarioRegistro(Bibliotecario bibliotecarioRegistro) {
        this.bibliotecarioRegistro = bibliotecarioRegistro;
    }

    public Usuario getUsuarioAsociado() {
        return usuarioAsociado;
    }

    public void setUsuarioAsociado(Usuario usuarioAsociado) {
        this.usuarioAsociado = usuarioAsociado;
    }

    public void pagar(LocalDate fechaPago) {
        if(this.estadoMulta == EstadoMulta.PAGADA) {
            return;
        }
        this.fechaPago = fechaPago != null ? fechaPago : LocalDate.now();
        this.estadoMulta = EstadoMulta.PAGADA;
    }
}
