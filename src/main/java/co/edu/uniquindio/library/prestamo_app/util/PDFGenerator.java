package co.edu.uniquindio.library.prestamo_app.util;

import co.edu.uniquindio.library.prestamo_app.model.*;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Utilidad para generar reportes en PDF
 */
public class PDFGenerator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Genera un reporte de todos los préstamos
     */
    public static boolean generarReportePrestamos(List<Prestamo> prestamos, String rutaArchivo, Bibliotecario generador) {
        try {
            File file = new File(rutaArchivo);
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Título del reporte
            Paragraph titulo = new Paragraph("REPORTE DE PRÉSTAMOS")
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(titulo);

            // Información del reporte
            document.add(new Paragraph("Fecha de generación: " + LocalDateTime.now().format(DATETIME_FORMATTER)));
            document.add(new Paragraph("Generado por: " + generador.getNombre()));
            document.add(new Paragraph("Total de préstamos: " + prestamos.size()));
            document.add(new Paragraph("\n"));

            // Tabla de préstamos
            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2, 2, 2, 2, 2}));
            table.setWidth(UnitValue.createPercentValue(100));

            // Encabezados
            table.addHeaderCell(crearCeldaEncabezado("ID"));
            table.addHeaderCell(crearCeldaEncabezado("Usuario"));
            table.addHeaderCell(crearCeldaEncabezado("Libro"));
            table.addHeaderCell(crearCeldaEncabezado("Fecha Inicio"));
            table.addHeaderCell(crearCeldaEncabezado("Fecha Compromiso"));
            table.addHeaderCell(crearCeldaEncabezado("Estado"));

            // Datos
            for (Prestamo prestamo : prestamos) {
                table.addCell(prestamo.getId().toString());
                table.addCell(prestamo.getUsuario().getNombre());
                table.addCell(prestamo.getEjemplar().getTitulo());
                table.addCell(prestamo.getFechaInicio().format(DATE_FORMATTER));
                table.addCell(prestamo.getFechaFinCompromiso().format(DATE_FORMATTER));
                table.addCell(prestamo.getEstadoPrestamo().toString());
            }

            document.add(table);

            // Pie de página
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Sistema de Gestión Bibliotecaria")
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER));

            document.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Genera un reporte de préstamos activos
     */
    public static boolean generarReportePrestamosActivos(List<Prestamo> prestamos, String rutaArchivo, Bibliotecario generador) {
        List<Prestamo> activos = prestamos.stream()
                .filter(p -> p.getEstadoPrestamo() == co.edu.uniquindio.library.prestamo_app.model.enums.EstadoPrestamo.ACTIVO)
                .toList();

        return generarReportePrestamos(activos, rutaArchivo, generador);
    }

    /**
     * Genera un reporte de multas
     */
    public static boolean generarReporteMultas(List<Multa> multas, String rutaArchivo, Bibliotecario generador) {
        try {
            File file = new File(rutaArchivo);
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Título del reporte
            Paragraph titulo = new Paragraph("REPORTE DE MULTAS")
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(titulo);

            // Información del reporte
            document.add(new Paragraph("Fecha de generación: " + LocalDateTime.now().format(DATETIME_FORMATTER)));
            document.add(new Paragraph("Generado por: " + generador.getNombre()));
            document.add(new Paragraph("Total de multas: " + multas.size()));

            double totalPendiente = multas.stream()
                    .filter(m -> m.getEstadoMulta() == co.edu.uniquindio.library.prestamo_app.model.enums.EstadoMulta.PENDIENTE)
                    .mapToDouble(Multa::getValor)
                    .sum();

            document.add(new Paragraph(String.format("Total pendiente: $%.0f", totalPendiente)));
            document.add(new Paragraph("\n"));

            // Tabla de multas
            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2, 2, 2, 2}));
            table.setWidth(UnitValue.createPercentValue(100));

            // Encabezados
            table.addHeaderCell(crearCeldaEncabezado("ID"));
            table.addHeaderCell(crearCeldaEncabezado("Usuario"));
            table.addHeaderCell(crearCeldaEncabezado("Valor"));
            table.addHeaderCell(crearCeldaEncabezado("Concepto"));
            table.addHeaderCell(crearCeldaEncabezado("Estado"));

            // Datos
            for (Multa multa : multas) {
                table.addCell(multa.getId().toString());
                table.addCell(multa.getPrestamo().getUsuario().getNombre());
                table.addCell(String.format("$%.0f", multa.getValor()));
                table.addCell(multa.getMotivo());
                table.addCell(multa.getEstadoMulta().toString());
            }

            document.add(table);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Sistema de Gestión Bibliotecaria")
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER));

            document.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Genera un reporte de préstamos de un usuario específico
     */
    public static boolean generarReporteUsuario(Usuario usuario, List<Prestamo> prestamos, String rutaArchivo, Bibliotecario generador) {
        try {
            File file = new File(rutaArchivo);
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Título del reporte
            Paragraph titulo = new Paragraph("HISTORIAL DE PRÉSTAMOS")
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(titulo);

            // Información del usuario
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Usuario: " + usuario.getNombre()).setBold());
            document.add(new Paragraph("Documento: " + usuario.getDocumento()));
            document.add(new Paragraph("Programa: " + usuario.getPrograma()));
            document.add(new Paragraph("Estado: " + usuario.getEstadoUsuario()));

            // Información del reporte
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Fecha de generación: " + LocalDateTime.now().format(DATETIME_FORMATTER)));
            document.add(new Paragraph("Generado por: " + generador.getNombre()));
            document.add(new Paragraph("\n"));

            // Tabla de préstamos
            Table table = new Table(UnitValue.createPercentArray(new float[]{2, 2, 2, 2}));
            table.setWidth(UnitValue.createPercentValue(100));

            // Encabezados
            table.addHeaderCell(crearCeldaEncabezado("Libro"));
            table.addHeaderCell(crearCeldaEncabezado("Fecha Inicio"));
            table.addHeaderCell(crearCeldaEncabezado("Fecha Compromiso"));
            table.addHeaderCell(crearCeldaEncabezado("Estado"));

            // Datos
            for (Prestamo prestamo : prestamos) {
                table.addCell(prestamo.getEjemplar().getTitulo());
                table.addCell(prestamo.getFechaInicio().format(DATE_FORMATTER));
                table.addCell(prestamo.getFechaFinCompromiso().format(DATE_FORMATTER));
                table.addCell(prestamo.getEstadoPrestamo().toString());
            }

            document.add(table);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Sistema de Gestión Bibliotecaria")
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER));

            document.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Genera un comprobante de préstamo individual
     */
    public static boolean generarComprobantePrestamo(Prestamo prestamo, String rutaArchivo) {
        try {
            File file = new File(rutaArchivo);
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Encabezado con logo/título
            Paragraph titulo = new Paragraph("COMPROBANTE DE PRÉSTAMO")
                    .setFontSize(24)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(10);
            document.add(titulo);

            Paragraph subtitulo = new Paragraph("Sistema de Gestión Bibliotecaria")
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(subtitulo);

            // Línea separadora
            document.add(new Paragraph("═".repeat(80))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(15));

            // Información del préstamo
            Paragraph idPrestamo = new Paragraph("Préstamo N°: " + prestamo.getId())
                    .setFontSize(14)
                    .setBold()
                    .setMarginBottom(20);
            document.add(idPrestamo);

            // Sección de Usuario
            document.add(new Paragraph("DATOS DEL USUARIO")
                    .setFontSize(14)
                    .setBold()
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                    .setPadding(5)
                    .setMarginBottom(10));

            Table tablaUsuario = new Table(UnitValue.createPercentArray(new float[]{1, 2}));
            tablaUsuario.setWidth(UnitValue.createPercentValue(100));
            tablaUsuario.setMarginBottom(15);

            tablaUsuario.addCell(crearCeldaInfo("Nombre:"));
            tablaUsuario.addCell(crearCeldaDato(prestamo.getUsuario().getNombre()));

            tablaUsuario.addCell(crearCeldaInfo("Documento:"));
            tablaUsuario.addCell(crearCeldaDato(prestamo.getUsuario().getDocumento()));

            tablaUsuario.addCell(crearCeldaInfo("Programa:"));
            tablaUsuario.addCell(crearCeldaDato(prestamo.getUsuario().getPrograma()));

            tablaUsuario.addCell(crearCeldaInfo("Email:"));
            tablaUsuario.addCell(crearCeldaDato(prestamo.getUsuario().getEmail()));

            document.add(tablaUsuario);

            // Sección de Ejemplar
            document.add(new Paragraph("DATOS DEL EJEMPLAR")
                    .setFontSize(14)
                    .setBold()
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                    .setPadding(5)
                    .setMarginBottom(10));

            Table tablaEjemplar = new Table(UnitValue.createPercentArray(new float[]{1, 2}));
            tablaEjemplar.setWidth(UnitValue.createPercentValue(100));
            tablaEjemplar.setMarginBottom(15);

            tablaEjemplar.addCell(crearCeldaInfo("Título:"));
            tablaEjemplar.addCell(crearCeldaDato(prestamo.getEjemplar().getTitulo()));

            tablaEjemplar.addCell(crearCeldaInfo("Autor:"));
            tablaEjemplar.addCell(crearCeldaDato(prestamo.getEjemplar().getAutor()));

            tablaEjemplar.addCell(crearCeldaInfo("ISBN:"));
            tablaEjemplar.addCell(crearCeldaDato(prestamo.getEjemplar().getIsbn()));


            tablaEjemplar.addCell(crearCeldaInfo("Ubicación:"));
            tablaEjemplar.addCell(crearCeldaDato(prestamo.getEjemplar().getUbicacion()));

            document.add(tablaEjemplar);

            // Sección de Fechas
            document.add(new Paragraph("INFORMACIÓN DEL PRÉSTAMO")
                    .setFontSize(14)
                    .setBold()
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                    .setPadding(5)
                    .setMarginBottom(10));

            Table tablaFechas = new Table(UnitValue.createPercentArray(new float[]{1, 2}));
            tablaFechas.setWidth(UnitValue.createPercentValue(100));
            tablaFechas.setMarginBottom(20);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            tablaFechas.addCell(crearCeldaInfo("Fecha de Inicio:"));
            tablaFechas.addCell(crearCeldaDato(prestamo.getFechaInicio().format(formatter)));

            tablaFechas.addCell(crearCeldaInfo("Fecha de Compromiso:"));
            tablaFechas.addCell(crearCeldaDatoDestacado(prestamo.getFechaFinCompromiso().format(formatter)));

            tablaFechas.addCell(crearCeldaInfo("Estado:"));
            tablaFechas.addCell(crearCeldaDato(prestamo.getEstadoPrestamo().toString()));

            tablaFechas.addCell(crearCeldaInfo("Bibliotecario:"));
            tablaFechas.addCell(crearCeldaDato(prestamo.getBibliotecario().getNombre()));

            document.add(tablaFechas);

            // Nota importante
            document.add(new Paragraph("IMPORTANTE:")
                    .setFontSize(12)
                    .setBold()
                    .setMarginBottom(5));

            document.add(new Paragraph("• El libro debe ser devuelto antes de la fecha de compromiso.")
                    .setFontSize(10)
                    .setMarginBottom(3));

            document.add(new Paragraph("• El incumplimiento de la fecha genera una multa de $2,000 por día de mora.")
                    .setFontSize(10)
                    .setMarginBottom(3));

            document.add(new Paragraph("• Conserve este comprobante como evidencia del préstamo.")
                    .setFontSize(10)
                    .setMarginBottom(20));

            // Línea separadora final
            document.add(new Paragraph("─".repeat(80))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(10));

            // Pie de página con fecha de generación
            document.add(new Paragraph("Fecha de generación: " +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                    .setFontSize(9)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setItalic());

            document.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Crea una celda de información (izquierda)
     */
    private static Cell crearCeldaInfo(String texto) {
        return new Cell()
                .add(new Paragraph(texto).setBold())
                .setBorder(null)
                .setPadding(5);
    }

    /**
     * Crea una celda de dato (derecha)
     */
    private static Cell crearCeldaDato(String texto) {
        return new Cell()
                .add(new Paragraph(texto))
                .setBorder(null)
                .setPadding(5);
    }

    /**
     * Crea una celda de dato destacado
     */
    private static Cell crearCeldaDatoDestacado(String texto) {
        return new Cell()
                .add(new Paragraph(texto).setBold().setFontSize(12))
                .setBorder(null)
                .setPadding(5)
                .setBackgroundColor(new com.itextpdf.kernel.colors.DeviceRgb(255, 255, 200));
    }

    /**
     * Crea una celda de encabezado para las tablas
     */
    private static Cell crearCeldaEncabezado(String texto) {
        return new Cell()
                .add(new Paragraph(texto).setBold())
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.CENTER);
    }
}