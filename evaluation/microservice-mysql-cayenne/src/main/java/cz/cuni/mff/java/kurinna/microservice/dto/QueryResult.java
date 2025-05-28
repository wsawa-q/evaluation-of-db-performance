package cz.cuni.mff.java.kurinna.microservice.dto;

import java.util.Map;

/**
 * A generic DTO for query results.
 * This is used for queries that don't have a specific DTO.
 */
public record QueryResult(Map<String, Object> data) {
}