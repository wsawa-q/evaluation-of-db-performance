package cz.cuni.mff.java.kurinna.microservice.dto;

import java.time.LocalDate;

public record Order(
    Integer oOrderkey,
    Integer oCustkey,
    String oOrderstatus,
    Double oTotalprice,
    LocalDate oOrderdate,
    String oOrderpriority,
    String oClerk,
    Integer oShippriority,
    String oComment
) {
}