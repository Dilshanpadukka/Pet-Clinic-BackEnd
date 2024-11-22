package edu.icet.controller;

import edu.icet.dto.Report;
import edu.icet.entity.ReportEntity;
import edu.icet.service.ReportService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    final ReportService reportService;

    @Getter
    final String REPORT_UPLOAD_DIR = "src/main/resources/reports/";

    @GetMapping("/searchById/{id}")
    public List<Report> search(@PathVariable String id) {
        return reportService.search(id);
    }

    @GetMapping("/searchByIdAndDate")
    public List<Report> search(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return reportService.search(id, date);
    }

    @GetMapping("/searchByDate")
    public List<Report> search(
            @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return reportService.search(date);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addReport(
            @RequestParam String note,
            @RequestParam MultipartFile report) {

        if (report.isEmpty()) {
            // Return a 400 Bad Request with an error message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Report file cannot be null or empty");
        }

        String reportName = report.getOriginalFilename();

        String validityState = reportService.isReportNameValid(reportName);
        if (validityState.equals("valid")) {
            System.out.println("report Name Is valid");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(validityState);
        }

        List<ReportEntity> existingReports = reportService.getAllReports();
        assert reportName != null;
        String reportId = reportService.createReportId(reportName, existingReports);
        String reportSaveName = reportService.createPDFDownloadName(reportName, reportId);

        String[] parts = reportName.split("[\\.\\s]");
        String category = parts[0];
        String petId = parts[1];

        // Saving the report PDF
        File directory = new File(REPORT_UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Save the PDF file to the specified path
        Path originalFilePath = Paths.get(REPORT_UPLOAD_DIR, reportSaveName);
        try {
            // Use Files.copy() to save the file
            Files.copy(report.getInputStream(), originalFilePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save the file.");
        }

        // Create the report entity
        ReportEntity fullReport = new ReportEntity(
                reportId,
                petId,
                "report/download/" + reportSaveName,
                category,
                LocalDate.now(),
                note,
                null);

        reportService.saveReport(fullReport);

        return ResponseEntity.ok(report.getOriginalFilename() + " file saved to the database successfully.");
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadReport(@PathVariable String fileName) {
        File file = new File(REPORT_UPLOAD_DIR, fileName);
        if (!file.exists() || !file.canRead()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // Define the path to the file
        String filePath = REPORT_UPLOAD_DIR + fileName;

        try {
            // Create a Path object for the file
            Path path = Paths.get(filePath);

            // Ensure the file exists
            if (!Files.exists(path)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null); // Return 404 if the file is not found
            }

            // Load the file as a resource
            Resource resource = new UrlResource(path.toUri());

            // Check if resource is readable
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }

            // Determine the file's content type (e.g., PDF)
            String contentType = Files.probeContentType(path);
            if (contentType == null) {
                contentType = "application/octet-stream"; // Default if unable to determine type
            }

            // Return the file with the appropriate headers
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/all")
    public List<ReportEntity> getAllReports() {
        return reportService.getAllReports();
    }

    @GetMapping("/{reportId}")
    public Optional<ReportEntity> getAllReports(@PathVariable String reportId) {
        return reportService.getReport(reportId);
    }

}