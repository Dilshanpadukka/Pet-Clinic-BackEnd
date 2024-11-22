package edu.icet.service.impl;

import edu.icet.dto.Pet;
import edu.icet.entity.PetEntity;
import edu.icet.repository.PetRepository;
import edu.icet.service.PetService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final ModelMapper mapper;

    @Override
    public List<Pet> getAll() {
        ArrayList<Pet> petArrayList = new ArrayList<>();
        petRepository.findAll().forEach(petEntity -> {
            petArrayList.add(mapper.map(petEntity, Pet.class));
        });
        return petArrayList;
    }

    @Override
    public void addPet(Pet pet) {
        petRepository.save(mapper.map(pet, PetEntity.class));
    }

    @Override
    public List<Pet> searchByName(String name) {
        List<Pet> petList = new ArrayList<>();
        petRepository.findByPetName(name).forEach(petEntity -> {
            petList.add(mapper.map(petEntity, Pet.class));
        });
        System.out.println(petList);
        return petList;
    }

    @Override
    public Pet SearchPetById(Integer id) {
        return mapper.map(petRepository.findById(id), Pet.class);
    }

    @Override
    public void updatePetById(Pet pet) {
        petRepository.save(mapper.map(pet, PetEntity.class));
    }

    @Override
    public void deletePetById(Integer id) {
        petRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {

    }
}
