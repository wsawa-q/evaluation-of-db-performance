package cz.cuni.mff.java.kurinna.microservice.repository;

import cz.cuni.mff.java.kurinna.microservice.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PartRepository extends JpaRepository<Part, Long>, JpaSpecificationExecutor<Part> {
}