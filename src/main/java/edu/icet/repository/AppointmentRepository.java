package edu.icet.repository;

import edu.icet.entity.AppointmentEntity;
import edu.icet.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity,Integer> {
    @Query(value = "SELECT * FROM  appointment  WHERE  time= :time AND date= :date",nativeQuery = true)
    List<AppointmentEntity>  findAppointmentsByTimeAndDate(@Param("time") LocalTime time, LocalDate date);
    @Query(value = "SELECT COUNT(a) FROM appointment a WHERE a.date= :date AND a.time BETWEEN :startTime AND :endTime")
    Long countAppointmentsInTimeFrame(LocalDate date, LocalTime startTime, LocalTime endTime);
    List<AppointmentEntity> findByAppointmentId(Integer appointmentId);
    List<AppointmentEntity> findByPetId(String petId);
    Optional<AppointmentEntity> findByDateAndTime(LocalDate date, LocalTime time);
}
