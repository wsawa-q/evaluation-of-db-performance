package cz.cuni.mff.java.kurinna.microservice.dto;

import lombok.Getter;

@Getter
public class PricingSummary {
    private String returnFlag;
    private String lineStatus;
    private Double sumQty;
    private Double sumBasePrice;
    private Double sumDiscPrice;
    private Double sumCharge;
    private Double avgQty;
    private Double avgPrice;
    private Double avgDisc;
    private Long countOrder;

    public PricingSummary(String returnFlag, String lineStatus, Double sumQty, Double sumBasePrice,
                          Double sumDiscPrice, Double sumCharge, Double avgQty, Double avgPrice,
                          Double avgDisc, Long countOrder) {
        this.returnFlag = returnFlag;
        this.lineStatus = lineStatus;
        this.sumQty = sumQty;
        this.sumBasePrice = sumBasePrice;
        this.sumDiscPrice = sumDiscPrice;
        this.sumCharge = sumCharge;
        this.avgQty = avgQty;
        this.avgPrice = avgPrice;
        this.avgDisc = avgDisc;
        this.countOrder = countOrder;
    }
}
