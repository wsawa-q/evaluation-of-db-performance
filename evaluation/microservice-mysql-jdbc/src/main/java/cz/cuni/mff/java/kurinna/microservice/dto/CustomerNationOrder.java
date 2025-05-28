package cz.cuni.mff.java.kurinna.microservice.dto;

import java.time.LocalDate;

public record CustomerNationOrder(
    String cName,
    String nName,
    LocalDate oOrderdate,
    Double oTotalprice
) {
}