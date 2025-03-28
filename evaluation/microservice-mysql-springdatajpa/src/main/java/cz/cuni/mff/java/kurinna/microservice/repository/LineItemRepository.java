package cz.cuni.mff.java.kurinna.microservice.repository;

import cz.cuni.mff.java.kurinna.microservice.model.LineItem;
import cz.cuni.mff.java.kurinna.microservice.model.LineItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LineItemRepository extends JpaRepository<LineItem, LineItemPK>, JpaSpecificationExecutor<LineItem> {
}