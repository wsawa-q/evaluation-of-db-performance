package cz.cuni.mff.java.kurinna.microservice.repository;

import cz.cuni.mff.java.kurinna.microservice.model.LineItem;
import cz.cuni.mff.java.kurinna.microservice.model.LineItemPK;
import cz.cuni.mff.java.kurinna.microservice.repository.dto.UniversalRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UniversalRepository extends JpaRepository<LineItem, LineItemPK>, JpaSpecificationExecutor<LineItem>, UniversalRepositoryCustom {
}