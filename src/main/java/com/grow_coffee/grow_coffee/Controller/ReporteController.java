package com.grow_coffee.grow_coffee.Controller;

import com.grow_coffee.grow_coffee.Services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/excel")
    public ResponseEntity<InputStreamResource> descargarExcel() {
        InputStreamResource file = new InputStreamResource(reporteService.generarExcel());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=inventario.xlsx")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(file);
    }

    @GetMapping("/pdf")
    public ResponseEntity<InputStreamResource> descargarPDF() {
        InputStreamResource file = new InputStreamResource(reporteService.generarPDF());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=inventario.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(file);
    }
}
