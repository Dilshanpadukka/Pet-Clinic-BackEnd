package edu.icet.controller;

import edu.icet.dto.Vet;
import edu.icet.service.VetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/vet")
@RequiredArgsConstructor
@Slf4j
public class VetController {
    final VetService vetService;

    @PostMapping
    public ResponseEntity<Vet> addVet(@RequestPart Vet vet,
                                      @RequestPart MultipartFile profilePicture) throws IOException {
        return new ResponseEntity<>(vetService.addVet(vet,profilePicture), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Vet>> getAllVets() {
        List<Vet> vets = vetService.getAllVets();
        return ResponseEntity.ok(vets);
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateVet(@PathVariable Integer id,
                                         @RequestPart Vet vet,
                                         @RequestPart(required = false) MultipartFile profilePicture) throws IOException {
        try {
            Vet updateVet = vetService.updateVet(id, vet, profilePicture);
            return ResponseEntity.ok(updateVet);
        } catch (RuntimeException|IOException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVet(@PathVariable Integer id) {
        try {
            vetService.deleteVet(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<?> createErrorResponse(String message, HttpStatus status) {
        Map<String, String> response = new HashMap<>();
        response.put("error", message);
        return new ResponseEntity<>(response, status);
    }

}
