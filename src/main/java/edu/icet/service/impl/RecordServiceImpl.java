package edu.icet.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.dto.Report;
import edu.icet.entity.RecordEntity;
import edu.icet.entity.ReportEntity;
import edu.icet.repository.RecordRepository;
import edu.icet.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecordServiceImpl implements RecordService {


    final ObjectMapper objectMapper;

    final RecordRepository recordRepository;

    @Override
    public void addRecord(Record record) {
        recordRepository.save(objectMapper.convertValue(record, RecordEntity.class));
    }

    @Override
    public List<Record> findByDate(LocalDate date) {
        List<RecordEntity> recordEntities = recordRepository.findByRecordDate(date);
        return bindingRecordEntityToRecord(recordEntities);
    }

    @Override
    public List<Record> findByRecordId(String recordId) {
        List<RecordEntity> recordEntities = recordRepository.findAll();

        return bindingRecordEntityToRecord(recordEntities.stream().filter(
                recordEntity -> recordEntity.
                        getRecordId() != null && recordEntity.getRecordId().
                        toLowerCase().
                        contains(recordId.toLowerCase())
        ).collect(Collectors.toList()));
    }

    @Override
    public List<Record> findByRecordIdAndDate(String recordId, LocalDate date) {
        List<RecordEntity> recordEntities = recordRepository.findByRecordIdAndRecordDate(recordId, date);
        return bindingRecordEntityToRecord(recordEntities);
    }

    @Override
    public List<Record> bindingRecordEntityToRecord(List<RecordEntity> recordEntities) {
        List<Record> records = new ArrayList<>();
        recordEntities.forEach(entity -> records.add(new ModelMapper().map(entity, Record.class)));
        return records;
    }

    @Override
    public void deleteRecord(String id) {
        if (recordRepository.existsById(id)) {
            recordRepository.deleteById(id);
        } else {
            throw new RuntimeException("Record not found");
        }
    }

    @Override
    public List<Record> findRecordsByDateRange(LocalDate startDate, LocalDate endDate,String petId) {
        List<RecordEntity> recordsInRange = recordRepository.findByPetIdAndRecordDateBetween(petId, startDate, endDate);
        List<Record> records = new ArrayList<>();
        recordsInRange.forEach(recordEntity -> records.add(new ModelMapper().map(recordEntity, Record.class)));
        return records;
    }

    @Override
    public List<Report> findByPetId(String petId) {
        List<Report> reports = new ArrayList<>();
        List<RecordEntity> byPetIds = recordRepository.findByPetId(petId);

        List<ReportEntity> reportEntities = new ArrayList<>();
        byPetIds.forEach(recordEntity -> reportEntities.addAll(recordEntity.getReportList()));

        ModelMapper modelMapper = new ModelMapper();
        reportEntities.forEach(reportEntity -> {
            Report report = modelMapper.map(reportEntity, Report.class);
            reports.add(report);
        });
        return reports;
    }

    @Override
    public List<RecordEntity> getAllRecord() {
        return recordRepository.findAll();
    }

    @Override
    public String getNewId() {
        String lastRecordId = recordRepository.findLastRecordId();
        if(lastRecordId==null){
            return "R1001";
        }
        String numericPart = lastRecordId.substring(1);
        int nextID = Integer.parseInt(numericPart) + 1;
        String newID = String.format("R%04d", nextID);
        return newID;
    }

}
