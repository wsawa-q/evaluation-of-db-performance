package cz.cuni.mff.java.kurinna.microservice.dto;

public record OrderCount(
    Long orderCount,
    String orderMonth
) {
}