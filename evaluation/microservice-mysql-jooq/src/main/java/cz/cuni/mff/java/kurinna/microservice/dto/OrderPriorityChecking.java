package cz.cuni.mff.java.kurinna.microservice.dto;

public record OrderPriorityChecking(
    String orderPriority,
    Long orderCount
) {}