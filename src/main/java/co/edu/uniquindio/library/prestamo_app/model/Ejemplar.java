package co.edu.uniquindio.library.prestamo_app.model;

import co.edu.uniquindio.library.prestamo_app.model.enums.EstadoEjemplar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Ejemplar {

    private static final AtomicLong contador = new AtomicLong(1);

    private Long id;
    private String isbn;
    private String titulo;
    private String autor;
    private String editorial;
    private int anioPublicacion;
    private EstadoEjemplar estadoEjemplar;
    private String ubicacion;
    private List<Prestamo> prestamosAsociados;

    public Ejemplar(String isbn, String titulo, String autor, String editorial, int anioPublicacion, String ubicacion) {
        this.id = generateId();
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.anioPublicacion = anioPublicacion;
        this.estadoEjemplar = EstadoEjemplar.DISPONIBLE;
        this.ubicacion = ubicacion;
        this.prestamosAsociados = new ArrayList<>();
    }

    public Ejemplar() {

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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public EstadoEjemplar getEstadoEjemplar() {
        return estadoEjemplar;
    }

    public void setEstadoEjemplar(EstadoEjemplar estadoEjemplar) {
        this.estadoEjemplar = estadoEjemplar;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<Prestamo> getPrestamosAsociados() {
        return prestamosAsociados;
    }

    public void setPrestamosAsociados(List<Prestamo> prestamosAsociados) {
        this.prestamosAsociados = prestamosAsociados;
    }
}
