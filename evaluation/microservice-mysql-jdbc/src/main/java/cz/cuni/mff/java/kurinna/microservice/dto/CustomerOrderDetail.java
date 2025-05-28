package cz.cuni.mff.java.kurinna.microservice.dto;

import java.time.LocalDate;

public record CustomerOrderDetail(
    Integer cCustkey,
    String cName,
    Integer oOrderkey,
    LocalDate oOrderdate
) {
}