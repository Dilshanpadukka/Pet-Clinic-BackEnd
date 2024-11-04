package edu.icet.service.impl;

import edu.icet.dto.Appointment;
import edu.icet.entity.AppointmentEntity;
import edu.icet.repository.AppointmentRepository;
import edu.icet.service.AppointmentService;
import edu.icet.service.EmailService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;
    private final EmailService emailService;

    @Getter
    public static class AppointmentUpdateException extends RuntimeException {
        private final String errorCode;
        public AppointmentUpdateException(String message, String errorCode) {
            super(message);
            this.errorCode = errorCode;
        }
    }

    @Override
    public void addAppointment(Appointment appointment) {
        LocalDate localDate=appointment.getDate();
        LocalTime appointmentTime=appointment.getTime();
        LocalTime startTime=appointmentTime.withMinute(0).withSecond(0).withNano(0);
        LocalTime endTime=startTime.plusHours(1);

        AppointmentEntity appointmentEntity=modelMapper.map(appointment, AppointmentEntity.class);
        List<AppointmentEntity> appointmentsByTimeAndDate = appointmentRepository.findAppointmentsByTimeAndDate(appointment.getTime(),appointment.getDate());
        Long appointmentsCount=appointmentRepository.countAppointmentsInTimeFrame(localDate,startTime,endTime);
        if (!appointmentsByTimeAndDate.isEmpty()){
            emailService.sendBookingUnSuccessEmail(appointment.getEmail(),appointment.getOwnerName());
            throw new AppointmentUpdateException("Time is Already Booked", "TIMESLOT_OCCUPIED");
        }else if(appointmentsCount>=10){
            emailService.sendBookingUnSuccessEmail(appointment.getEmail(),appointment.getOwnerName());
            throw new AppointmentUpdateException("Maximum appointments limit reached for this time frame", "MAX_LIMIT_REACHED");
        } else {
            appointmentRepository.save(appointmentEntity);
            //emailService.sendBookingSuccessEmail("udayankadilshan23@gmail.com","Udayanka");
            emailService.sendBookingSuccessEmail(appointment.getEmail(),appointment.getOwnerName());
        }
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        LocalDate appointmentDate = appointment.getDate();
        LocalTime appointmentTime = appointment.getTime();
        LocalTime startTime = appointmentTime.withMinute(0).withSecond(0).withNano(0);
        LocalTime endTime = startTime.plusHours(1);
        Optional<AppointmentEntity> existingAppointment = appointmentRepository.findByDateAndTime(appointmentDate, appointmentTime);
        if (existingAppointment.isPresent() && !existingAppointment.get().getAppointmentId().equals(appointment.getAppointmentId())) {
            throw new AppointmentUpdateException("Time slot is already booked", "TIMESLOT_OCCUPIED");
        }
        Long existingCount = appointmentRepository.countAppointmentsInTimeFrame(appointmentDate, startTime, endTime);
        if (existingCount >= 10) {
            throw new AppointmentUpdateException("Maximum appointments limit reached for this time frame", "MAX_LIMIT_REACHED");
        }
        AppointmentEntity appointmentEntity = new ModelMapper().map(appointment, AppointmentEntity.class);
        appointmentRepository.save(appointmentEntity);
        emailService.sendBookingSuccessEmail(appointment.getEmail(),appointment.getOwnerName());
    }

    @Override
    public List<AppointmentEntity> findAppointmentsByAppointmentId(Integer appointmentId) {
        return appointmentRepository.findByAppointmentId(appointmentId);
    }

    @Override
    public void removeAppointment(Integer appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }
    @Override
    public List<AppointmentEntity> findAppointmentsByPetId(String petId) {
        return appointmentRepository.findByPetId(petId);
    }

    @Override
    public List<Appointment> getAppointment() {
        ArrayList<Appointment> appointmentArrayList = new ArrayList<>();
        appointmentRepository.findAll().forEach(appointmentEntity -> {
            appointmentArrayList.add(modelMapper.map(appointmentEntity, Appointment.class));
        });
        return appointmentArrayList;
    }
}
