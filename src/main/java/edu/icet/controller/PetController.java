package edu.icet.controller;

import edu.icet.dto.Pet;
import edu.icet.service.PetService;
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

@RestController
@CrossOrigin
@RequestMapping("/api/pet")
@RequiredArgsConstructor
@Slf4j
public class PetController {
    final PetService petService;

    @PostMapping("/add-pet")
    public ResponseEntity<Map<String, String>> addPet(@Valid @RequestBody Pet pet) {
        log.info("Received request to add Pet: {}", pet);
        petService.addPet(pet);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Pet added successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pet-get-all")
    public ResponseEntity<List<Pet>> getPets() {
        log.info("Received request to retrieve all pets");
        return ResponseEntity.ok(petService.getAll());
    }

    @GetMapping("/pet-search-by-id/{id}")
    public ResponseEntity<Pet> searchPetsById(@Valid @PathVariable Integer id) {
        log.info("Searching for Pet by ID: {}", id);
        return ResponseEntity.ok(petService.SearchPetById(id));
    }

    @GetMapping("/pet-search-by-name/{name}")
    public ResponseEntity<List<Pet>> searchPetsByName(@Valid @PathVariable String name) {
        log.info("Searching for Pets by Name: {}", name);
        return ResponseEntity.ok(petService.searchByName(name));
    }

    @PutMapping("/pet-update-by-id")
    public ResponseEntity<Map<String, String>> updatePet(@Valid @RequestBody Pet pet) {
        log.info("Received request to update Pet: {}", pet);
        petService.updatePetById(pet);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Pet updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/pet-delete-by-id/{id}")
    public ResponseEntity<Map<String, String>> deletePet(@Valid @PathVariable Integer id) {
        log.info("Received request to delete Pet with ID: {}", id);
        petService.deletePetById(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Pet deleted successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/pet-delete-all")
    public ResponseEntity<Map<String, String>> deleteAllPets() {
        log.info("Received request to delete all Pets");
        petService.deleteAll();
        Map<String, String> response = new HashMap<>();
        response.put("message", "All Pets deleted successfully");
        return ResponseEntity.ok(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
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