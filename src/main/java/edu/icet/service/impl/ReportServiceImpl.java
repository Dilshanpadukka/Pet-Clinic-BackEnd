package edu.icet.service.impl;

import edu.icet.dto.Report;
import edu.icet.entity.ReportEntity;
import edu.icet.repository.ReportRepository;
import edu.icet.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {
    final ReportRepository reportRepository;

    @Override
    public List<Report> search(String id) {
        List<ReportEntity> reportEntities = reportRepository.findAll();

        if (id == null) {
            return reportEntities.stream()
                    .map(reportEntity -> new ModelMapper().map(reportEntity, Report.class))
                    .collect(Collectors.toList());
        }

        List<Report> reports = reportEntities.stream()
                .map(reportEntity -> new ModelMapper().map(reportEntity, Report.class))
                .collect(Collectors.toList());

        return reports.stream()
                .filter(report -> report.getReportId() != null && report.getReportId().toLowerCase().contains(id.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Report> search(String id, LocalDate date) {
        List<ReportEntity> reportEntities = reportRepository.findAll();

        // If both id and date are null, return all reports
        if (id == null && date == null) {
            return reportEntities.stream()
                    .map(reportEntity -> new ModelMapper().map(reportEntity, Report.class))
                    .collect(Collectors.toList());
        }

        // Filter reports based on the provided id and date
        return reportEntities.stream()
                .map(reportEntity -> new ModelMapper().map(reportEntity, Report.class))
                .filter(report -> (id == null || (report.getReportId() != null && report.getReportId().toLowerCase().contains(id.toLowerCase()))) &&
                        (date == null || (report.getReportDate() != null && report.getReportDate().equals(date))))
                .collect(Collectors.toList());
    }

    @Override
    public List<Report> search(LocalDate date) {
        List<ReportEntity> reportEntities = reportRepository.findAll();

        if (date == null) {
            return reportEntities.stream()
                    .map(reportEntity -> new ModelMapper().map(reportEntity, Report.class))
                    .collect(Collectors.toList());
        }

        List<Report> reports = reportEntities.stream()
                .map(reportEntity -> new ModelMapper().map(reportEntity, Report.class))
                .collect(Collectors.toList());

        return reports.stream()
                .filter(report -> report.getReportDate() != null && report.getReportDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public ReportEntity saveReport(ReportEntity reportEntity) {
        return reportRepository.save(reportEntity);
    }

    @Override
    public List<ReportEntity> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public Optional<ReportEntity> getReport(String reportId) {
        return reportRepository.findById(reportId);
    }


    @Override
    public String isReportNameValid(String reportName) {
        if (reportName == null || reportName.isEmpty())
            return "name cannot be empty";

        if (!reportName.substring(reportName.length() - 4).equals(".pdf"))
            return "report must be upload in pdf format";

        String[] nameParts = reportName.split(" ");
        if (nameParts.length < 2)
            return "name must have atleast 2 parts";

        boolean hasCapitalLetters = false;
        for (char c : nameParts[0].toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasCapitalLetters = true;
                break;
            }
        }

        if (!hasCapitalLetters)
            return "report category must have atleast one capital letter";

        if (!nameParts[1].matches("^P\\d{4}.*$"))
            return "pet Id must be in the 'P1001' pattern (P and four digits)";

        //System.out.println(Arrays.toString(nameParts));
        return "valid";
    }

    @Override
    public String createReportId(String reportName, List<ReportEntity> existingReports) {
        String[] nameParts = reportName.split("[\\.\\s]");

        // category letters generate;
        String categoryLetters = "";
        for (char c : nameParts[0].toCharArray()) {
            if (Character.isUpperCase(c))
                categoryLetters += c;
        }

        String petId = nameParts[1];
        int lastReportNumber = 0;
        String categoryAndPetId = categoryLetters + petId;

        for (int i = 0; i < existingReports.size(); i++) {
            if (existingReports.get(i).getReportId().contains(categoryAndPetId)) {
                // extracting number
                String idNumberString = existingReports.get(i).getReportId().substring(categoryAndPetId.length());
                int idNumber = Integer.parseInt(idNumberString);
                if (lastReportNumber < idNumber)
                    lastReportNumber = idNumber;
            }
        }
        return categoryLetters + petId + String.format("%03d", lastReportNumber + 1);
    }

    @Override
    public String createPDFDownloadName(String originalName, String reportId) {
        String category = originalName.split(" ")[0];
        return category + " " + reportId + ".pdf";
    }

}
