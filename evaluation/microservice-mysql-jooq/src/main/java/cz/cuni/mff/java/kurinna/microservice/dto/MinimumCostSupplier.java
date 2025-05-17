package cz.cuni.mff.java.kurinna.microservice.dto;

import java.math.BigDecimal;

public record MinimumCostSupplier(
    BigDecimal acctbal,
    String name,
    String nationName,
    Integer partKey,
    String mfgr,
    String address,
    String phone,
    String comment
) {}