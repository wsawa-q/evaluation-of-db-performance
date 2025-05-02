package cz.cuni.mff.java.kurinna.microservice.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class LineItemPK implements Serializable {
    @ManyToOne
    @JoinColumn(name = "l_orderkey", referencedColumnName = "o_orderkey")
    private Orders order;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int l_linenumber;
}
