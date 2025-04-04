package cz.cuni.mff.java.kurinna.microservice.repository.dto;

import cz.cuni.mff.java.kurinna.microservice.dto.PricingSummary;

import java.util.List;

public interface LineItemRepositoryCustom {
    List<PricingSummary> findPricingSummaryReport(int days);
}
