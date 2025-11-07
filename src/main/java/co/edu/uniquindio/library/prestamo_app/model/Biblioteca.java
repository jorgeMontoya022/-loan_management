package co.edu.uniquindio.library.prestamo_app.model;

import co.edu.uniquindio.library.prestamo_app.model.enums.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Biblioteca implements Serializable {

    private static final long serialVersionUID = 1L;
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

    /**
     * Agrega un nuevo usuario al sistema
     * @param usuario Usuario a agregar
     * @return true si se agregó exitosamente, false si el documento ya existe
     */
    public boolean agregarUsuario(Usuario usuario) {
        // Validar que el documento sea único
        if (buscarUsuarioPorDocumento(usuario.getDocumento()) != null) {
            return false;
        }

        // Validar que el email sea único
        if (buscarUsuarioPorEmail(usuario.getEmail()) != null) {
            return false;
        }

        return usuarios.add(usuario);
    }

    /**
     * Busca un usuario por su número de documento
     * @param documento Documento del usuario
     * @return Usuario encontrado o null si no existe
     */
    public Usuario buscarUsuarioPorDocumento(String documento) {
        return usuarios.stream()
                .filter(u -> u.getDocumento().equals(documento))
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca un usuario por su email
     * @param email Email del usuario
     * @return Usuario encontrado o null si no existe
     */
    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca usuarios por nombre (búsqueda parcial, case-insensitive)
     * @param criterio Nombre o parte del nombre a buscar
     * @return Lista de usuarios que coinciden
     */
    public List<Usuario> buscarUsuariosPorNombre(String criterio) {
        String criterioLower = criterio.toLowerCase();
        return usuarios.stream()
                .filter(u -> u.getNombre().toLowerCase().contains(criterioLower))
                .collect(Collectors.toList());
    }

    /**
     * Actualiza la información de un usuario existente
     * @param usuario Usuario con datos actualizados (el ID debe existir)
     * @return true si se actualizó exitosamente, false si no existe
     */
    public boolean actualizarUsuario(Usuario usuario) {
        Usuario usuarioExistente = buscarUsuarioPorId(usuario.getDocumento());
        if (usuarioExistente == null) {
            return false;
        }

        // Validar email único (excepto el propio usuario)
        Usuario usuarioConEmail = buscarUsuarioPorEmail(usuario.getEmail());
        if (usuarioConEmail != null && !usuarioConEmail.getId().equals(usuario.getId())) {
            return false;
        }

        // Actualizar campos
        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setPrograma(usuario.getPrograma());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setEstadoUsuario(usuario.getEstadoUsuario());

        return true;
    }

    /**
     * Inactiva un usuario (cambia su estado a INACTIVO)
     * @param id ID del usuario
     * @return true si se inactivó exitosamente, false si tiene préstamos activos
     */
    public boolean inactivarUsuario(String id) {
        Usuario usuario = buscarUsuarioPorId(id);
        if (usuario == null) {
            return false;
        }

        // No se puede inactivar si tiene préstamos activos
        if (contarPrestamosActivos(id) > 0) {
            return false;
        }

        usuario.setEstadoUsuario(EstadoUsuario.INACTIVO);
        return true;
    }

    /**
     * Busca un usuario por su ID
     * @param documento documento del usuario
     * @return Usuario encontrado o null
     */
    private Usuario buscarUsuarioPorId(String documento) {
        return usuarios.stream()
                .filter(u -> u.getDocumento().equals(documento))
                .findFirst()
                .orElse(null);
    }


    public boolean agregarBibliotecario(Bibliotecario bibliotecario) {
        // Validar que el bibliotecario no sea nulo
        if (bibliotecario == null) {
            return false;
        }

        // Validar que el documento sea único
        if (buscarBibliotecarioPorDocumento(bibliotecario.getDocumento()) != null) {
            return false;
        }

        // Validar que el email sea único (opcional)
        if (buscarBibliotecarioPorEmail(bibliotecario.getEmail()) != null) {
            return false;
        }

        // Agregar el bibliotecario a la lista
        return bibliotecarios.add(bibliotecario);
    }


    private Bibliotecario buscarBibliotecarioPorDocumento(String documento) {
        return bibliotecarios.stream()
                .filter(b -> b.getDocumento().equals(documento))
                .findFirst()
                .orElse(null);
    }




    private Bibliotecario buscarBibliotecarioPorEmail(String email) {
        return bibliotecarios.stream()
                .filter(b -> b.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    /**
     * Agrega un nuevo ejemplar al catálogo
     * @param ejemplar Ejemplar a agregar
     * @return true si se agregó exitosamente
     */
    public boolean agregarEjemplar(Ejemplar ejemplar) {
        if(!buscarEjemplarPorTitulo(ejemplar.getTitulo())) {
            return false;
        }
        return ejemplares.add(ejemplar);
    }

    private boolean buscarEjemplarPorTitulo(String titulo) {
        for(Ejemplar ejemplar : ejemplares) {
            if(ejemplar.getTitulo().equals(titulo)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Busca ejemplares disponibles por ISBN
     * @param isbn ISBN del libro
     * @return Lista de ejemplares disponibles con ese ISBN
     */

    public List<Ejemplar> buscarEjemplaresDisponiblesPorISBN(String isbn) {
        List<Ejemplar> disponibles = new ArrayList<>();
        for(Ejemplar ejemplar: ejemplares) {
            if(ejemplar.getIsbn().equals(isbn) && ejemplar.getEstadoEjemplar().equals(EstadoEjemplar.DISPONIBLE)) {
                disponibles.add(ejemplar);
            }
        }
        return disponibles;
    }

    /**
     * Filtra ejemplares por estado
     * @param estado Estado a filtrar
     * @return Lista de ejemplares con ese estado
     */
    public List<Ejemplar> listarEjemplaresPorEstado(EstadoEjemplar estado) {
        return ejemplares.stream()
                .filter(e -> e.getEstadoEjemplar() == estado)
                .collect(Collectors.toList());
    }

    /**
     * Registra un nuevo préstamo en el sistema
     * Valida todas las reglas de negocio antes de crear el préstamo
     * @param prestamo Préstamo a registrar
     * @return true si se registró exitosamente
     * @throws IllegalStateException si viola alguna regla de negocio
     */
    public boolean registrarPrestamo(Prestamo prestamo) {
        Usuario usuario = prestamo.getUsuario();
        Ejemplar ejemplar = prestamo.getEjemplar();

        // RN-06: Validar usuario activo
        if (!validarUsuarioActivo(usuario)) {
            throw new IllegalStateException("USUARIO_NO_ACTIVO");
        }

        // RN-01: Validar límite de préstamos
        if (!validarLimitePrestamos(usuario)) {
            throw new IllegalStateException("LIMITE_PRESTAMOS_ALCANZADO");
        }

        // RN-03: Validar multas pendientes
        if (!validarSinMultasPendientes(usuario)) {
            throw new IllegalStateException("USUARIO_CON_MULTAS");
        }

        // RN-02: Validar ejemplar disponible
        if (!validarEjemplarDisponible(ejemplar)) {
            throw new IllegalStateException("EJEMPLAR_NO_DISPONIBLE");
        }

        // RN-07: Validar unicidad del préstamo
        if (!validarPrestamoUnico(ejemplar)) {
            throw new IllegalStateException("PRESTAMO_DUPLICADO");
        }

        // Todas las validaciones pasaron, proceder con el registro

        // Agregar préstamo a la lista
        prestamos.add(prestamo);

        // Cambiar estado del ejemplar a PRESTADO
        ejemplar.setEstadoEjemplar(EstadoEjemplar.PRESTADO);

        // RN-05: Registrar auditoría
        registrarAuditoria(new RegistroAuditoria(
                TipoOperacion.CREAR_PRESTAMO,
                prestamo.getBibliotecario(), LocalDateTime.now(),"Prestamo ID: " + prestamo.getId()

        ));

        return true;
    }


    /**
     * RN-03: Valida que el usuario no tenga multas pendientes
     * @param usuario Usuario a validar
     * @return true si no tiene multas pendientes, false si tiene
     */

    public boolean validarSinMultasPendientes(Usuario usuario) {
        return obtenerMultasPendientes(usuario.getDocumento()).isEmpty();
    }

    private List<Multa> obtenerMultasPendientes(String documento) {
        return multas.stream()
                .filter(m -> m.getPrestamo().getUsuario().getDocumento().equals(documento))
                .filter(m -> m.getEstadoMulta() == EstadoMulta.PENDIENTE)
                .collect(Collectors.toList());
    }

    /**
     * RN-02: Valida que el ejemplar esté disponible para préstamo
     * @param ejemplar Ejemplar a validar
     * @return true si está disponible, false en caso contrario
     */
    public boolean validarEjemplarDisponible(Ejemplar ejemplar) {
        return ejemplar.estaDisponible();
    }


    private void registrarAuditoria(RegistroAuditoria auditoria) {
        auditorias.add(auditoria);
    }

    /**
     * RN-06: Valida que el usuario esté en estado ACTIVO
     * @param usuario Usuario a validar
     * @return true si está activo, false en caso contrario
     */
    public boolean validarUsuarioActivo(Usuario usuario) {
        if(usuario.getEstadoUsuario() == EstadoUsuario.ACTIVO) {
            return true;
        }
        return false;

    }

    /**
     * RN-01: Valida que el usuario no haya alcanzado el límite de 3 préstamos activos
     * @param usuario Usuario a validar
     * @return true si tiene menos de 3 préstamos, false si tiene 3 o más
     */
    private boolean validarLimitePrestamos(Usuario usuario) {
        return contarPrestamosActivos(usuario.getDocumento()) < 3;
    }

    /**
     * RN-07: Valida que el ejemplar no tenga un préstamo activo
     * @param ejemplar Ejemplar a validar
     * @return true si no tiene préstamo activo, false si ya está prestado
     */

    private boolean validarPrestamoUnico(Ejemplar ejemplar) {
        return prestamos.stream()
                .noneMatch(p -> p.getEjemplar().getTitulo().equals(ejemplar.getTitulo()) &&
                        p.getEstadoPrestamo() == EstadoPrestamo.ACTIVO);
    }

    /**
     * Obtiene los préstamos activos de un usuario
     * @param usuarioId ID del usuario
     * @return Lista de préstamos activos
     */
    public List<Prestamo> obtenerPrestamosActivos(String usuarioId) {
        return prestamos.stream()
                .filter(p -> p.getUsuario().getDocumento().equals(usuarioId))
                .filter(p -> p.getEstadoPrestamo() == EstadoPrestamo.ACTIVO ||
                        p.getEstadoPrestamo() == EstadoPrestamo.MOROSO)
                .collect(Collectors.toList());
    }

    /**
     * Cuenta cuántos préstamos activos tiene un usuario
     * @param usuarioId ID del usuario
     * @return Cantidad de préstamos activos
     */
    public int contarPrestamosActivos(String usuarioId) {
        return (int) prestamos.stream()
                .filter(p -> p.getUsuario().getDocumento().equals(usuarioId))
                .filter(p -> p.getEstadoPrestamo() == EstadoPrestamo.ACTIVO ||
                        p.getEstadoPrestamo() == EstadoPrestamo.MOROSO)
                .count();
    }

    /**
     * Cierra un préstamo y genera multa si corresponde
     * @param prestamoId ID del préstamo
     * @return true si se cerró exitosamente
     */
    public boolean cerrarPrestamo(Long prestamoId) {
        Prestamo prestamo = buscarPrestamoPorId(prestamoId);
        if (prestamo == null || prestamo.getEstadoPrestamo() == EstadoPrestamo.CERRADO) {
            return false;
        }

        LocalDate fechaDevolucion = LocalDate.now();
        prestamo.cerrar(fechaDevolucion);


        prestamo.getEjemplar().devolver();

        if (prestamo.estaMoroso()) {
            int diasMora = prestamo.calcularDiasMora();
            double valorMulta = diasMora * 2000.0; //$2000 por día

            Multa multa = new Multa(prestamo, valorMulta,
                    String.format("Mora de %d días", diasMora));

            multas.add(multa);
            prestamo.getMultasAsociadas().add(multa);
            prestamo.getUsuario().getMultasAsociadas().add(multa);

            registrarAuditoria(new RegistroAuditoria(
                    TipoOperacion.CREAR_MULTA, prestamo.getBibliotecario(), LocalDateTime.now(), String.format("Multa generada por mora: $%.0f (%d días)",
                    valorMulta, diasMora)
                    ));
        }

        registrarAuditoria(new RegistroAuditoria(TipoOperacion.CERRAR_PRESTAMO, prestamo.getBibliotecario(),LocalDateTime.now(),String.format("Préstamo %s cerrado", prestamoId) ));
        return true;

    }
    private Prestamo buscarPrestamoPorId(Long id) {
        return prestamos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Calcula el total de multas pendientes de un usuario
     * @param usuarioId ID del usuario
     * @return Total a pagar
     */
    public double calcularTotalMultas(String usuarioId) {
        return multas.stream()
                .filter(m -> m.getPrestamo().getUsuario().getDocumento().equals(usuarioId))
                .filter(m -> m.getEstadoMulta() == EstadoMulta.PENDIENTE)
                .mapToDouble(Multa::getValor)
                .sum();
    }


    /**
     * Paga una multa
     * @param multaId ID de la multa
     * @return true si se pagó exitosamente
     */
    public boolean pagarMulta(Long multaId) {
        Multa multa = buscarMultaPorId(multaId);
        if (multa == null || multa.getEstadoMulta() != EstadoMulta.PENDIENTE) {
            return false;
        }

        multa.pagar(LocalDate.now());

        registrarAuditoria(new RegistroAuditoria(
                TipoOperacion.PAGAR_MULTA,
                multa.getBibliotecarioRegistro(),LocalDateTime.now(),
                String.format("Multa %s pagada: $%.0f", multaId, multa.getValor())
        ));

        return true;
    }

    private Multa buscarMultaPorId(Long multaId) {
        return multas.stream().
                filter(m -> m.getId().equals(multaId))
                .findFirst()
                .orElse(null);
    }


    public Bibliotecario validarAcceso(String usuario, String password) throws Exception{

        for (Bibliotecario bibliotecario: bibliotecarios) {
            if(buscarBibliotecarioPorDoc(usuario)) {
                if(bibliotecario.getDocumento().equals(usuario) && bibliotecario.getContrasenia().equals(password)) {
                    return bibliotecario;
                }
            } else {
                throw new Exception("El usuario no existe");
            }
        }
        throw new Exception("Identificación o documento de identidad incorrecto");
    }

    private boolean buscarBibliotecarioPorDoc(String usuario) {
        for (Bibliotecario bibliotecario: bibliotecarios) {
            if (bibliotecario.getDocumento().equals(usuario)) {
                return true;
            }
        }
        return false;
    }

    public int contarUsuariosActivos() {
        int activos = 0;
        for (Usuario usuario: usuarios) {
            if (usuario.getEstadoUsuario() == EstadoUsuario.ACTIVO) {
                activos += 1;
            }
        }
        return activos;
    }

    public int contarPrestamosActivos() {
        int activos = 0;
        for (Prestamo prestamo: prestamos) {
            if(prestamo.getEstadoPrestamo() == EstadoPrestamo.ACTIVO) {
                activos +=1;
            }
        }
        return activos;
    }
}


