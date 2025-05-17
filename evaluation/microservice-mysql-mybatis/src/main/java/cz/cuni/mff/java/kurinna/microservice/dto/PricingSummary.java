package cz.cuni.mff.java.kurinna.microservice.dto;

public record PricingSummary(String returnFlag, String lineStatus, Double sumQty, Double sumBasePrice,
                             Double sumDiscPrice, Double sumCharge, Double avgQty, Double avgPrice, Double avgDisc,
                             Long countOrder) {
}
