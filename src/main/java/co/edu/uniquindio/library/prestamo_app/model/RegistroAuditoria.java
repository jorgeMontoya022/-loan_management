package co.edu.uniquindio.library.prestamo_app.model;

import co.edu.uniquindio.library.prestamo_app.model.enums.TipoOperacion;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RegistroAuditoria {

    private Long id;
    private TipoOperacion tipoOperacion;
    private Bibliotecario bibliotecario;
    private LocalDateTime fechaHora;
    private String detalles;

    public RegistroAuditoria(TipoOperacion tipoOperacion, Bibliotecario bibliotecario, LocalDateTime fechaHora, String detalles) {
        this.tipoOperacion = tipoOperacion;
        this.bibliotecario = bibliotecario;
        this.fechaHora = fechaHora;
        this.detalles = detalles;
    }

    public RegistroAuditoria() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(TipoOperacion tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public Bibliotecario getBibliotecario() {
        return bibliotecario;
    }

    public void setBibliotecario(Bibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
}
