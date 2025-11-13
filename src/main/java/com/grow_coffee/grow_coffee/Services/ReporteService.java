package com.grow_coffee.grow_coffee.Services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grow_coffee.grow_coffee.Model.Inventario;
import com.grow_coffee.grow_coffee.Repository.InventarioRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class ReporteService {

    @Autowired
    private InventarioRepository inventarioRepository;

    // GENERAR REPORTE EN EXCEL 
    public ByteArrayInputStream generarExcel() {
        String[] columnas = {"ID", "Nombre", "Cantidad", "Unidad", "Fecha de Vencimiento", "Precio"};
        List<Inventario> lista = inventarioRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet hoja = workbook.createSheet("Inventario");

            // --- Estilo encabezado ---
            CellStyle headerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont(); // 👈 CORREGIDO
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            // --- Encabezados ---
            Row header = hoja.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(headerStyle);
            }

            // --- Filas de datos ---
            int rowIdx = 1;
            for (Inventario item : lista) {
                Row row = hoja.createRow(rowIdx++);

                row.createCell(0).setCellValue(item.getId() != null ? item.getId().toString() : "");
                row.createCell(1).setCellValue(item.getNombre() != null ? item.getNombre() : "");
                row.createCell(2).setCellValue(item.getCantidad());
                row.createCell(3).setCellValue(item.getUnidad() != null ? item.getUnidad() : "");
                row.createCell(4).setCellValue(item.getFechaVencimiento() != null ? item.getFechaVencimiento().toString() : "");
                row.createCell(5).setCellValue(item.getPrecio());
            }

            // Ajustar ancho de columnas automáticamente
            for (int i = 0; i < columnas.length; i++) {
                hoja.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("Error al generar el archivo Excel: " + e.getMessage(), e);
        }
    }

    // === GENERAR REPORTE EN PDF ===
    public ByteArrayInputStream generarPDF() {
        List<Inventario> lista = inventarioRepository.findAll();
        Document documento = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(documento, out);
            documento.open();

            // --- Título ---
            com.itextpdf.text.Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Paragraph titulo = new Paragraph("Reporte de Inventario", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            documento.add(Chunk.NEWLINE);

            // --- Tabla ---
            PdfPTable tabla = new PdfPTable(6);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{1f, 3f, 2f, 2f, 3f, 2f});

            // Encabezados
            String[] encabezados = {"ID", "Nombre", "Cantidad", "Unidad", "Fecha de Vencimiento", "Precio"};
            com.itextpdf.text.Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
            BaseColor colorHeader = new BaseColor(0, 102, 204);

            for (String encabezado : encabezados) {
                PdfPCell header = new PdfPCell(new Phrase(encabezado, fontHeader));
                header.setBackgroundColor(colorHeader);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setPadding(5);
                tabla.addCell(header);
            }

            // --- Filas de datos ---
            for (Inventario item : lista) {
                tabla.addCell(item.getId() != null ? item.getId().toString() : "");
                tabla.addCell(item.getNombre() != null ? item.getNombre() : "");
                tabla.addCell(String.valueOf(item.getCantidad()));
                tabla.addCell(item.getUnidad() != null ? item.getUnidad() : "");
                tabla.addCell(item.getFechaVencimiento() != null ? item.getFechaVencimiento().toString() : "");
                tabla.addCell(String.valueOf(item.getPrecio()));
            }

            documento.add(tabla);

        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF: " + e.getMessage(), e);
        } finally {
            documento.close();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
