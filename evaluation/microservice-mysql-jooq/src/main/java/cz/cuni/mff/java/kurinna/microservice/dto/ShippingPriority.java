package cz.cuni.mff.java.kurinna.microservice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ShippingPriority(
    Integer orderKey,
    BigDecimal revenue,
    LocalDate orderDate,
    Integer shipPriority
) {}