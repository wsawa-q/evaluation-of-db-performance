package cz.cuni.mff.java.kurinna.microservice.dto;

import java.math.BigDecimal;

public record PricingSummary (
    String   returnFlag,
    String   lineStatus,
    BigDecimal sumQty,
    BigDecimal sumBasePrice,
    BigDecimal sumDiscPrice,
    BigDecimal sumCharge,
    BigDecimal avgQty,
    BigDecimal avgPrice,
    BigDecimal avgDisc,
    Integer     countOrder
) {}
