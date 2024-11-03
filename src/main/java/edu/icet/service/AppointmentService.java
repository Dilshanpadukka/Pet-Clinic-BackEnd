package edu.icet.service;

import edu.icet.dto.Appointment;
import edu.icet.entity.AppointmentEntity;

import java.util.List;

public interface AppointmentService {
    void addAppointment(Appointment appointment);
    void updateAppointment(Appointment appointment);
    List<AppointmentEntity> findAppointmentsByAppointmentId(Integer appointmentId);
    void removeAppointment(Integer appointmentId);
    List<AppointmentEntity> findAppointmentsByPetId(String petId);
    List<Appointment> getAppointment();
}
