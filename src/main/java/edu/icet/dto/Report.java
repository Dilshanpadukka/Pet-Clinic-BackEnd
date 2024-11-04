package edu.icet.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Report {
    private String reportId;
    private String reportLink;
    private LocalDate date;
    private String categoryType;
    private LocalDate reportDate;
    @JsonIgnore
    private List<Record> recordList;
}