package cz.cuni.mff.java.kurinna.microservice.dto;

import java.time.LocalDate;

public record CustomerNationRegionOrder(
    String cName,
    String nName,
    String rName,
    LocalDate oOrderdate,
    Double oTotalprice
) {
}