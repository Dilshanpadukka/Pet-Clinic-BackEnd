package edu.icet.service;

import edu.icet.dto.Vet;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VetService {
    Vet addVet(Vet vet, MultipartFile profilePicture) throws IOException;
    List<Vet> getAllVets();

    Vet updateVet(Integer vetId,Vet vet, MultipartFile profilePicture) throws IOException;
    void deleteVet(Integer vetId);

}
