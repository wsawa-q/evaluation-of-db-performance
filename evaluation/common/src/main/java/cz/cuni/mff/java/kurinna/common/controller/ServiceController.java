package cz.cuni.mff.java.kurinna.common.controller;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ServiceController<T> {
    ResponseEntity<String> health();

    ResponseEntity<String> deleteAll();

    ResponseEntity<String> deleteById(Long id);

    ResponseEntity<Long> save(T t);

    ResponseEntity<T> findById(Long id);
}
