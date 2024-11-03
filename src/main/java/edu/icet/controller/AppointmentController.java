package edu.icet.controller;

import edu.icet.dto.Appointment;
import edu.icet.entity.AppointmentEntity;
import edu.icet.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
@Slf4j
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping("/add-appointment")
    public ResponseEntity<String> addAppointment(@Valid @RequestBody Appointment appointment) {
        try {
            appointmentService.addAppointment(appointment);
            return ResponseEntity.ok("Appointment added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }
    @PutMapping("/update-appointment")
    public ResponseEntity<String> updateAppointment(@Valid @RequestBody Appointment appointment) {
        try {
            appointmentService.updateAppointment(appointment);
            return ResponseEntity.ok("Appointment updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

    @GetMapping("/get-appointment-by-Id/{appointmentId}")
    public List<AppointmentEntity> getAppointmentById(@PathVariable Integer appointmentId) {
        return appointmentService.findAppointmentsByAppointmentId(appointmentId);
    }

    @DeleteMapping("/delete-appointment-by-Id/{appointmentId}")
    public ResponseEntity<String> deleteAppointmentById(@PathVariable Integer appointmentId) {
        try {
            appointmentService.removeAppointment(appointmentId);
            return ResponseEntity.ok("Appointment deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/get-all-appointments")
    public List<Appointment> getAllAppointment() {
        return appointmentService.getAppointment();
    }
    @GetMapping("/get-appointments-by-petId/{petId}")
    public List<AppointmentEntity> getAppointmentByPetId(@PathVariable String petId) {
        return appointmentService.findAppointmentsByPetId(petId);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public Map<String,String> handleAppointmentUpdateException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.error("Validation error: {}", errors);
        return errors;
    }

}
