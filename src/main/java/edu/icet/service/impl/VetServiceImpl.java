package edu.icet.service.impl;

import edu.icet.dto.Vet;
import edu.icet.entity.VetEntity;
import edu.icet.repository.VetRepository;
import edu.icet.service.VetService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VetServiceImpl implements VetService {
    private final VetRepository vetRepository;
    private final ModelMapper mapper;


    @Override
    public Vet addVet(Vet vet, MultipartFile profilePicture) throws IOException {
        VetEntity vetEntity = new VetEntity();
        vetEntity.setVetName(vet.getVetName());
        vetEntity.setRegNo(vet.getRegNo());
        vetEntity.setEmail(vet.getEmail());
        vetEntity.setContactNumber(vet.getContactNumber());
        vetEntity.setProfilePictureData(profilePicture.getBytes());
        vetEntity.setProfilePictureType(profilePicture.getContentType());

        VetEntity savedVet = vetRepository.save(vetEntity);
        return new Vet(savedVet);
    }

    @Override
    public List<Vet> getAllVets() {
        return vetRepository.findAll().stream()
                .map(Vet::new)
                .collect(Collectors.toList());
    }

    @Override
    public Vet updateVet(Integer vetId, Vet vet, MultipartFile profilePicture) throws IOException {
        VetEntity vetEntity = vetRepository.findById(vetId)
                .orElseThrow(()->new RuntimeException("Vet not found "+vetId));
       vetEntity.setVetName(vet.getVetName());
       vetEntity.setRegNo(vet.getRegNo());
       vetEntity.setEmail(vet.getEmail());
       vetEntity.setContactNumber(vet.getContactNumber());
        if (profilePicture != null && !profilePicture.isEmpty()) {
            vetEntity.setProfilePictureData(profilePicture.getBytes());
            vetEntity.setProfilePictureType(profilePicture.getContentType());
        }
        VetEntity updatedVet = vetRepository.save(vetEntity);
        return new Vet(updatedVet);
    }

    @Override
    public void deleteVet(Integer vetId) {
        vetRepository.deleteById(vetId);
    }
}
