package cz.cuni.mff.java.kurinna.microservice.dto;

import java.math.BigDecimal;

public record LocalSupplierVolume(
    String nationName,
    BigDecimal revenue
) {}