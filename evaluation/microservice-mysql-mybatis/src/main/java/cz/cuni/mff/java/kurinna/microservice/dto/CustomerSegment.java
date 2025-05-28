package cz.cuni.mff.java.kurinna.microservice.dto;

public record CustomerSegment(
    Integer cNationkey,
    String cMktsegment
) {
}