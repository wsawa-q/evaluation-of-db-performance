package cz.cuni.mff.java.kurinna.microservice.repository;

import cz.cuni.mff.java.kurinna.microservice.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RegionRepository extends JpaRepository<Region, Long>, JpaSpecificationExecutor<Region> {
}