package cz.cuni.mff.java.kurinna.microservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PartSuppPK implements Serializable {
    @Column(name = "ps_partkey")
    private Long part;

    @Column(name = "ps_suppkey")
    private Long supplier;
}
