package cz.cuni.mff.java.kurinna.microservice.dto;

import java.time.LocalDate;

public record ShippingPriority(Integer orderKey, Double revenue, LocalDate orderDate, Integer shipPriority) {
}