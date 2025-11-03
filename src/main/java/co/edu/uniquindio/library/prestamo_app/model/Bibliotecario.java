package co.edu.uniquindio.library.prestamo_app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Bibliotecario implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final AtomicLong contador = new AtomicLong(1);

    private Long id;
    private String nombre;
    private String documento;
    private String email;
    private String contrasenia;
    private String usuario;
    private List<RegistroAuditoria> registrosAuditoria;

    public Bibliotecario(String nombre, String documento, String email, String contrasenia, String usuario) {
        this.id = generateId();
        this.nombre = nombre;
        this.documento = documento;
        this.email = email;
        this.contrasenia = contrasenia;
        this.usuario = usuario;
        this.registrosAuditoria = new ArrayList<>();
    }

    public Bibliotecario() {

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public List<RegistroAuditoria> getRegistrosAuditoria() {
        return registrosAuditoria;
    }

    public void setRegistrosAuditoria(List<RegistroAuditoria> registrosAuditoria) {
        this.registrosAuditoria = registrosAuditoria;
    }
}
