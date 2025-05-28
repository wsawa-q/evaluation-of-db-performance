package cz.cuni.mff.java.kurinna.common.controller;

import org.springframework.http.ResponseEntity;

public interface ServiceController<T> {
    ResponseEntity<String> health();

    ResponseEntity<T> getPricingSummary();

    ResponseEntity<T> getMinimumCostSupplier();

    ResponseEntity<T> getShippingPriority();

    ResponseEntity<T> getOrderPriorityChecking();

    ResponseEntity<T> getLocalSupplierVolume();

    ResponseEntity<T> executeQueryA1();

    ResponseEntity<T> executeQueryA2();

    ResponseEntity<T> executeQueryA3();

    ResponseEntity<T> executeQueryA4();

    ResponseEntity<T> executeQueryB1();

    ResponseEntity<T> executeQueryB2();

    ResponseEntity<T> executeQueryC1();

    ResponseEntity<T> executeQueryC2();

    ResponseEntity<T> executeQueryC3();

    ResponseEntity<T> executeQueryC4();

    ResponseEntity<T> executeQueryC5();

    ResponseEntity<T> executeQueryD1();

    ResponseEntity<T> executeQueryD2();

    ResponseEntity<T> executeQueryD3();

    ResponseEntity<T> executeQueryE1();

    ResponseEntity<T> executeQueryE2();

    ResponseEntity<T> executeQueryE3();
}
