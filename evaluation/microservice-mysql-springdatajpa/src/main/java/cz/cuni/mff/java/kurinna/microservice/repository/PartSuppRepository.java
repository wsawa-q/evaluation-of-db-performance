package cz.cuni.mff.java.kurinna.microservice.repository;

import cz.cuni.mff.java.kurinna.microservice.model.PartSupp;
import cz.cuni.mff.java.kurinna.microservice.model.PartSuppPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PartSuppRepository extends JpaRepository<PartSupp, PartSuppPK>, JpaSpecificationExecutor<PartSupp> {
}