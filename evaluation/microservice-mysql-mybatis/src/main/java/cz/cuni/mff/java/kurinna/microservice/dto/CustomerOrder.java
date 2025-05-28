package cz.cuni.mff.java.kurinna.microservice.dto;

import java.time.LocalDate;

public record CustomerOrder(
    String cName,
    LocalDate oOrderdate,
    Double oTotalprice
) {
}