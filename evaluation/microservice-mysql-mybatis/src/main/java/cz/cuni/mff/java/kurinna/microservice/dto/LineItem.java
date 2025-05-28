package cz.cuni.mff.java.kurinna.microservice.dto;

import java.time.LocalDate;

public record LineItem(
    Integer lOrderkey,
    Integer lPartkey,
    Integer lSuppkey,
    Integer lLinenumber,
    Double lQuantity,
    Double lExtendedprice,
    Double lDiscount,
    Double lTax,
    String lReturnflag,
    String lLinestatus,
    LocalDate lShipdate,
    LocalDate lCommitdate,
    LocalDate lReceiptdate,
    String lShipinstruct,
    String lShipmode,
    String lComment
) {
}