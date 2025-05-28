package cz.cuni.mff.java.kurinna.microservice.dto;

public record Customer(
    Integer cCustkey,
    String cName,
    String cAddress,
    Integer cNationkey,
    String cPhone,
    Double cAcctbal,
    String cMktsegment,
    String cComment
) {
}