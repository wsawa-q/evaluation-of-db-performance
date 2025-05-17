package cz.cuni.mff.java.kurinna.microservice.dto;

public record MinimumCostSupplier(Double acctbal, String name, String nationName, Integer partKey, String mfgr,
                                  String address, String phone, String comment) {
}