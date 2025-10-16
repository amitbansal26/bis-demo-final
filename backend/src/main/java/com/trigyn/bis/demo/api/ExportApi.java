package com.trigyn.bis.demo.api;

import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.trigyn.bis.demo.repo.*;
import com.trigyn.bis.demo.domain.*;

@RestController
@RequestMapping("/api/admin/export")
@RequiredArgsConstructor
public class ExportApi {
  private final CertificationApplicationRepository apps;
  private final ActivityLogRepository logs;

  @GetMapping(value="/applications.csv", produces="text/csv")
  public ResponseEntity<byte[]> exportAppsCsv() throws IOException {
    List<CertificationApplication> list = apps.findAll();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try (CSVPrinter printer = new CSVPrinter(new OutputStreamWriter(out, StandardCharsets.UTF_8),
        CSVFormat.DEFAULT.withHeader("id","scheme","status","isNumber","product"))) {
      for (var a : list) {
        printer.printRecord(a.getId(), a.getScheme(), a.getStatus(), a.getIsNumber(), a.getProduct());
      }
    }
    return ResponseEntity.ok()
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=applications.csv")
      .contentType(MediaType.valueOf("text/csv"))
      .body(out.toByteArray());
  }

  @GetMapping(value="/activity.xlsx")
  public ResponseEntity<byte[]> exportActivityXlsx() throws IOException {
    List<ActivityLog> list = logs.findAll();
    try (Workbook wb = new XSSFWorkbook()) {
      Sheet s = wb.createSheet("ActivityLogs");
      Row h = s.createRow(0);
      h.createCell(0).setCellValue("id");
      h.createCell(1).setCellValue("applicationId");
      h.createCell(2).setCellValue("action");
      h.createCell(3).setCellValue("at");
      h.createCell(4).setCellValue("details");
      int r=1;
      for (var l : list) {
        Row row = s.createRow(r++);
        row.createCell(0).setCellValue(l.getId());
        row.createCell(1).setCellValue(l.getApplication()!=null? l.getApplication().getId(): null);
        row.createCell(2).setCellValue(l.getAction());
        row.createCell(3).setCellValue(l.getAt()!=null? l.getAt().toString(): "");
        row.createCell(4).setCellValue(l.getDetails());
      }
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      wb.write(out);
      return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=activity.xlsx")
        .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
        .body(out.toByteArray());
    }
  }
}
