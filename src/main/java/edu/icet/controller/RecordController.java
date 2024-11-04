package edu.icet.controller;

import edu.icet.dto.Report;
import edu.icet.entity.RecordEntity;
import edu.icet.service.impl.RecordServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/record")
public class RecordController {

    final RecordServiceImpl recordService;

    @PostMapping("/add-record")
    public ResponseEntity<String> addRecord(@Valid @RequestBody Record record) {
        recordService.addRecord(record);
        return ResponseEntity.ok("Record added successfully");
    }

    @GetMapping("/search")
    public List<Record> searchRecords(@RequestParam(value = "date", required = false) String dateString,
                                      @RequestParam(value = "recordId", required = false) String recordId) {
        if (recordId != null && dateString != null) {
            LocalDate date = LocalDate.parse(dateString);
            return recordService.findByRecordIdAndDate(recordId, date);
        } else if (recordId != null) {
            return recordService.findByRecordId(recordId);
        } else if (dateString != null) {
            LocalDate date = LocalDate.parse(dateString);
            return recordService.findByDate(date);
        }
        return Collections.emptyList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecord(@PathVariable String id) {
        try {
            recordService.deleteRecord(id);
            return new ResponseEntity<>("Record deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Record not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/searchByPetId")
    public List<Report> searchByPetId(@RequestParam(value = "petId",required = false) String petId) {
        if(petId != null){
            return recordService.findByPetId(petId);
        }
        return Collections.emptyList();
    }

    @GetMapping("/searchByDateAndPetId")
    public List<Record> findRecordsByDateRange(@RequestParam(value = "startDate", required = false)
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,

                                               @RequestParam(value = "endDate", required = false)
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                               @RequestParam(value = "petId",required = false) String petId
    ) {
        List<Record> recordsByDateRange = recordService.findRecordsByDateRange(startDate, endDate, petId);
        return recordsByDateRange!=null?recordsByDateRange:Collections.emptyList();
    }

    @GetMapping("/all")
    public List<RecordEntity> getAllRecord(){
        return recordService.getAllRecord();
    }

    @GetMapping("/get-nextId")
    public  String getNextId(){
        return recordService.getNewId();
    }
}
