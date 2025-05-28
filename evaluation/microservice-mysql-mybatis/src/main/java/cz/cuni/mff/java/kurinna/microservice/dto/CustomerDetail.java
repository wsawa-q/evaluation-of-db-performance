package cz.cuni.mff.java.kurinna.microservice.dto;

public record CustomerDetail(
    String cName,
    String cAddress,
    Double cAcctbal
) {
}