package edu.icet.repository;

import edu.icet.entity.RecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<RecordEntity, String> {
    List<RecordEntity> findByRecordDate(LocalDate date);

    List<RecordEntity> findByRecordId(String recordId);

    List<RecordEntity> findByRecordIdAndRecordDate(String recordId, LocalDate dateString);

    List<RecordEntity> findByPetId(String petId);

    List<RecordEntity> findByPetIdAndRecordDateBetween(String petId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT r.recordId FROM record r ORDER BY r.recordId DESC")
    String findLastRecordId();

}
