package cz.cuni.mff.java.kurinna.microservice.dto;

import java.time.LocalDate;

public record OrderDetail(
    Integer oOrderkey,
    Integer oCustkey,
    LocalDate oOrderdate,
    Double oTotalprice
) {
}