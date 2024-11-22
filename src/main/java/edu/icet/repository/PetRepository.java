package edu.icet.repository;

import edu.icet.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<PetEntity,Integer> {
    List<PetEntity> findByPetName(String name);
}
