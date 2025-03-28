package cz.cuni.mff.java.kurinna.microservice.repository;

import cz.cuni.mff.java.kurinna.microservice.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrdersRepository extends JpaRepository<Orders, Long>, JpaSpecificationExecutor<Orders> {
}