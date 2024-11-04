package edu.icet.service;

import edu.icet.dto.Report;
import edu.icet.entity.ReportEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReportService {
    List<Report> search(String id);

    List<Report> search(String id, LocalDate date);

    List<Report> search(LocalDate date);

    ReportEntity saveReport(ReportEntity reportEntity);

    List<ReportEntity> getAllReports();

    Optional<ReportEntity> getReport(String reportId);

    public String isReportNameValid(String reportName);

    public String createReportId(String reportName, List<ReportEntity> existingReports);

    public String createPDFDownloadName(String originalName, String reportId);
}
