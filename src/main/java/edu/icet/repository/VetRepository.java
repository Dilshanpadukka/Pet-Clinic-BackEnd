package edu.icet.repository;

import edu.icet.entity.VetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VetRepository extends JpaRepository<VetEntity,Integer> {

}
