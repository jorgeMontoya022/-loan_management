package co.edu.uniquindio.library.prestamo_app.util;

import co.edu.uniquindio.library.prestamo_app.model.*;
import co.edu.uniquindio.library.prestamo_app.model.enums.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Clase utilitaria para la inicialización de datos de prueba de la biblioteca
 *
 * @author Sistema de Gestión de Biblioteca UQ
 * @version 1.0
 */
public class BibliotecaUtil {

    // Constantes para cálculo de multas
    private static final double VALOR_MULTA_POR_DIA = 2000.0;

    /**
     * Inicializa y retorna una instancia de Biblioteca con datos de prueba
     * incluyendo bibliotecarios, usuarios, ejemplares, préstamos, multas y auditorías
     *
     * @return Biblioteca instancia completamente inicializada con datos de prueba
     */
    public static Biblioteca initializeData() {
        Biblioteca biblioteca = new Biblioteca();

        // Inicialización secuencial de todos los componentes
        initializeBibliotecarios(biblioteca);
        initializeUsuarios(biblioteca);
        initializeEjemplares(biblioteca);
        initializePrestamos(biblioteca);
        initializeMultas(biblioteca);
        initializeAuditorias(biblioteca);

        return biblioteca;
    }

    /**
     * Inicializa los bibliotecarios del sistema
     * Se crean 2 bibliotecarios con diferentes credenciales
     */
    private static void initializeBibliotecarios(Biblioteca biblioteca) {
        Bibliotecario anaTorres = new Bibliotecario(
                "Ana Torres",
                "B-123",
                "ana.torres@uq.edu.co",
                "1234",
                "ana.torres"
        );

        Bibliotecario carlosMejia = new Bibliotecario(
                "Carlos Mejía",
                "B-456",
                "carlos.mejia@uq.edu.co",
                "abcd",
                "carlos.mejia"
        );

        biblioteca.agregarBibliotecario(anaTorres);
        biblioteca.agregarBibliotecario(carlosMejia);
    }

    /**
     * Inicializa los usuarios del sistema
     * Se crean 3 usuarios: 2 activos y 1 inactivo
     */
    private static void initializeUsuarios(Biblioteca biblioteca) {
        // Usuario 1: Activo
        Usuario lauraPerez = new Usuario(
                "Laura Pérez",
                "U-111",
                "Ingeniería de Sistemas",
                "laura.perez@uq.edu.co"
        );

        // Usuario 2: Activo
        Usuario mateoRamirez = new Usuario(
                "Mateo Ramírez",
                "U-222",
                "Administración",
                "mateo.ramirez@uq.edu.co"
        );

        // Usuario 3: Inactivo
        Usuario sofiaLopez = new Usuario(
                "Sofía López",
                "U-333",
                "Contaduría",
                "sofia.lopez@uq.edu.co"
        );
        sofiaLopez.setEstadoUsuario(EstadoUsuario.INACTIVO);

        biblioteca.agregarUsuario(lauraPerez);
        biblioteca.agregarUsuario(mateoRamirez);
        biblioteca.agregarUsuario(sofiaLopez);
    }

    /**
     * Inicializa los ejemplares de libros disponibles
     * Se crean 4 ejemplares de diferentes libros con distintos estados
     */
    private static void initializeEjemplares(Biblioteca biblioteca) {
        // Libro: Estructuras de Datos en Java - Ejemplar 1 (Disponible)
        Ejemplar ejemplar1 = new Ejemplar(
                "978-958-123-000-1",
                "Estructuras de Datos en Java",
                "Juan Gómez",
                "Editorial UQ",
                2018,
                "Estante A1"
        );
        ejemplar1.setEstadoEjemplar(EstadoEjemplar.DISPONIBLE);

        // Libro: Estructuras de Datos en Java - Ejemplar 2 (Prestado)
        Ejemplar ejemplar2 = new Ejemplar(
                "978-958-123-000-1",
                "Estructuras de Datos en Java",
                "Juan Gómez",
                "Editorial UQ",
                2018,
                "Estante A2"
        );
        ejemplar2.setEstadoEjemplar(EstadoEjemplar.PRESTADO);

        // Libro: Bases de Datos Avanzadas (Disponible)
        Ejemplar ejemplar3 = new Ejemplar(
                "978-958-654-321-0",
                "Bases de Datos Avanzadas",
                "María Ruiz",
                "Editorial DB",
                2020,
                "Estante B2"
        );
        ejemplar3.setEstadoEjemplar(EstadoEjemplar.DISPONIBLE);

        // Libro: Contabilidad Financiera (Disponible)
        Ejemplar ejemplar4 = new Ejemplar(
                "978-958-222-111-9",
                "Contabilidad Financiera",
                "Luis Pérez",
                "Editorial CFA",
                2016,
                "Estante C3"
        );
        ejemplar4.setEstadoEjemplar(EstadoEjemplar.DISPONIBLE);

        biblioteca.getEjemplares().add(ejemplar1);
        biblioteca.getEjemplares().add(ejemplar2);
        biblioteca.getEjemplares().add(ejemplar3);
        biblioteca.getEjemplares().add(ejemplar4);
    }

    /**
     * Inicializa los préstamos activos
     * Se crean 2 préstamos: uno vencido (para generar multa) y uno activo
     */
    private static void initializePrestamos(Biblioteca biblioteca) {
        Usuario lauraPerez = biblioteca.getUsuarios().get(0);
        Usuario mateoRamirez = biblioteca.getUsuarios().get(1);

        Ejemplar ejemplar2 = biblioteca.getEjemplares().get(1);
        Ejemplar ejemplar3 = biblioteca.getEjemplares().get(2);

        Bibliotecario anaTorres = biblioteca.getBibliotecarios().get(0);
        Bibliotecario carlosMejia = biblioteca.getBibliotecarios().get(1);

        // Préstamo 1: Vencido hace 3 días (generará multa)
        Prestamo prestamo1 = new Prestamo(
                lauraPerez,
                ejemplar2,
                anaTorres,
                LocalDate.now().minusDays(10),  // Fecha inicio: hace 10 días
                LocalDate.now().minusDays(3),   // Fecha compromiso: hace 3 días (vencido)
                null                             // No se ha devuelto aún
        );

        // Préstamo 2: Activo (vence en 5 días)
        Prestamo prestamo2 = new Prestamo(
                mateoRamirez,
                ejemplar3,
                carlosMejia,
                LocalDate.now().minusDays(2),   // Fecha inicio: hace 2 días
                LocalDate.now().plusDays(5),    // Fecha compromiso: en 5 días
                null                             // No se ha devuelto aún
        );

        biblioteca.getPrestamos().add(prestamo1);
        biblioteca.getPrestamos().add(prestamo2);

        // Vincular préstamos a usuarios
        lauraPerez.getPrestamosAsociados().add(prestamo1);
        mateoRamirez.getPrestamosAsociados().add(prestamo2);
    }

    /**
     * Inicializa las multas del sistema
     * Se crea una multa pendiente por mora de 3 días
     */
    private static void initializeMultas(Biblioteca biblioteca) {
        Prestamo prestamo1 = biblioteca.getPrestamos().get(0);
        Usuario lauraPerez = biblioteca.getUsuarios().get(0);
        Bibliotecario anaTorres = biblioteca.getBibliotecarios().get(0);

        // Calcular valor de la multa (3 días * $2000)
        int diasMora = 3;
        double valorMulta = diasMora * VALOR_MULTA_POR_DIA;

        // Crear multa usando el constructor simplificado
        Multa multa = new Multa(
                prestamo1,
                valorMulta,
                String.format("Mora de %d días", diasMora)
        );

        // Configurar datos adicionales
        multa.setEstadoMulta(EstadoMulta.PENDIENTE);
        multa.setFechaGeneracion(LocalDate.now().minusDays(2));
        multa.setBibliotecarioRegistro(anaTorres);
        multa.setUsuarioAsociado(lauraPerez);

        biblioteca.getMultas().add(multa);

        // Asociar multa al préstamo y usuario
        if (prestamo1.getMultasAsociadas() == null) {
            prestamo1.setMultasAsociadas(new ArrayList<>());
        }
        prestamo1.getMultasAsociadas().add(multa);
        lauraPerez.getMultasAsociadas().add(multa);
    }

    /**
     * Inicializa los registros de auditoría
     * Se crean 2 registros: creación de usuario y creación de préstamo
     */
    private static void initializeAuditorias(Biblioteca biblioteca) {
        Bibliotecario anaTorres = biblioteca.getBibliotecarios().get(0);

        // Auditoría 1: Creación de usuario
        RegistroAuditoria auditoria1 = new RegistroAuditoria(
                TipoOperacion.CREAR_USUARIO,
                anaTorres,
                LocalDateTime.now().minusDays(5),
                "Creación de usuario Laura Pérez"
        );

        // Auditoría 2: Creación de préstamo
        RegistroAuditoria auditoria2 = new RegistroAuditoria(
                TipoOperacion.CREAR_PRESTAMO,
                anaTorres,
                LocalDateTime.now().minusDays(2),
                "Creación de préstamo para Laura Pérez"
        );

        biblioteca.getAuditorias().add(auditoria1);
        biblioteca.getAuditorias().add(auditoria2);
    }
}