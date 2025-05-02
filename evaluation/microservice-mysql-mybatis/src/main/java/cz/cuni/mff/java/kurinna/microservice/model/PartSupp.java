package cz.cuni.mff.java.kurinna.microservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity(name = "partsupp")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PartSupp {
    @EmbeddedId
    private PartSuppPK partSuppPK;

    @ManyToOne
    @MapsId("part")
    @JoinColumn(name = "ps_partkey", referencedColumnName = "p_partkey")
    private Part part;

    @ManyToOne
    @MapsId("supplier")
    @JoinColumn(name = "ps_suppkey", referencedColumnName = "s_suppkey")
    private Supplier supplier;

    private int ps_availqty;

    private double ps_supplycost;

    @Column(length = 199)
    private String ps_comment;

//    @OneToMany(mappedBy = "partSupp", targetEntity = LineItem.class)
//    private List<LineItem> lineItems;
//
//    @OneToMany(mappedBy = "part", targetEntity = Part.class)
//    private List<Part> parts;
//
//    @OneToMany
//    private List<Supplier> suppliers;
}
