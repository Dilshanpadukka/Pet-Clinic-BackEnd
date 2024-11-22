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
@RequestMapping("/api/appointment")
@RequiredArgsConstructor
@Slf4j
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping("/add-appointment")
    public ResponseEntity<Map<String, String>> addAppointment(@Valid @RequestBody Appointment appointment) {
        log.info("Received request to add appointment: {}", appointment);
        try {
            appointmentService.addAppointment(appointment);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Appointment added successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error adding appointment: ", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/update-appointment")
    public ResponseEntity<Map<String, String>> updateAppointment(@Valid @RequestBody Appointment appointment) {
        log.info("Received request to update appointment: {}", appointment);
        try {
            appointmentService.updateAppointment(appointment);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Appointment updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error updating appointment: ", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/get-appointment-by-Id/{appointmentId}")
    public ResponseEntity<?> getAppointmentById(@PathVariable Integer appointmentId) {
        log.info("Received request to get appointment by ID: {}", appointmentId);
        try {
            List<AppointmentEntity> appointments = appointmentService.findAppointmentsByAppointmentId(appointmentId);
            if (appointments.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "No appointments found with ID: " + appointmentId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            log.error("Error retrieving appointment: ", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/delete-appointment-by-id/{appointmentId}")
    public ResponseEntity<Map<String, String>> deleteAppointmentById(@PathVariable Integer appointmentId) {
        log.info("Received request to delete appointment with ID: {}", appointmentId);
        try {
            appointmentService.removeAppointment(appointmentId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Appointment deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error deleting appointment: ", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/get-all-appointments")
    public ResponseEntity<?> getAllAppointment() {
        log.info("Received request to get all appointments");
        try {
            List<Appointment> appointments = appointmentService.getAppointment();
            if (appointments.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "No appointments found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            log.error("Error retrieving appointments: ", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/get-appointments-by-petId/{petId}")
    public ResponseEntity<?> getAppointmentByPetId(@PathVariable String petId) {
        log.info("Received request to get appointments for pet ID: {}", petId);
        try {
            List<AppointmentEntity> appointments = appointmentService.findAppointmentsByPetId(petId);
            if (appointments.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "No appointments found for pet ID: " + petId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            log.error("Error retrieving appointments: ", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
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