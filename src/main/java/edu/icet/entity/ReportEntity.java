package edu.icet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Entity(name = "report")
@Table(name = "report")
public class ReportEntity {
    @Id
    private String reportId;
    @Column(nullable = false)
    private String petId;
    @Column(nullable = false)
    private String reportLink;
    @Column(nullable = false)
    private String categoryType;
    private LocalDate reportDate;

    @Lob
    private String note;

    @ManyToMany(mappedBy = "reportList", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RecordEntity> recordList;
}
