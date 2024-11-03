package edu.icet.service;

import edu.icet.dto.Pet;

import java.util.List;

public interface PetService {
List<Pet> getAll();
void addPet(Pet pet);
List<Pet> searchByName(String name);
Pet SearchPetById(Integer id);

void updatePetById(Pet pet);
void deletePetById(Integer id);
void deleteAll();
}
