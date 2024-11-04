package edu.icet.service;

import edu.icet.dto.Report;
import edu.icet.entity.RecordEntity;

import java.time.LocalDate;
import java.util.List;

public interface RecordService {
    void addRecord(Record record);

    List<Record> findByDate(LocalDate date);

    List<Record> findByRecordId(String recordId);

    List<Record> findByRecordIdAndDate(String recordId, LocalDate date);

    List<Record> bindingRecordEntityToRecord(List<RecordEntity> recordEntities);

    void deleteRecord(String id);

    List<Record> findRecordsByDateRange(LocalDate startDate, LocalDate endDate,String petId);

    List<Report> findByPetId(String petId);

    List<RecordEntity> getAllRecord();

    public String getNewId();
}
