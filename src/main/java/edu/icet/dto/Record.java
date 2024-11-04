package edu.icet.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
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
public class Record {

    @NotEmpty(message = "Record ID cannot be empty")
    private String recordId;

    @NotEmpty(message = "Pet ID cannot be empty")
    private String petId;

    @NotNull(message = "Record date cannot be null")
    @PastOrPresent(message = "Record date must be in the past or present")
    private LocalDate recordDate;

    @Size(min = 10, message = "Description must be at least 10 characters long")
    private String description;

    //@NotNull(message = "ReportList  must be included ")
    private List<Report> reportList;
}
