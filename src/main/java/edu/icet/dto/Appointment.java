package edu.icet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Appointment {
    private Integer appointmentId;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private String categoryId;
    private String petId;
    private String petName;
    private String petType;
    private String ownerName;
    private String phoneNumber;
    private String email;
}
