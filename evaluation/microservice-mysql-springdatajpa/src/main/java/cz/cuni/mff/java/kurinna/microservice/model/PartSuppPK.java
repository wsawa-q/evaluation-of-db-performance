package cz.cuni.mff.java.kurinna.microservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PartSuppPK implements Serializable {
    @Column(name = "ps_partkey")
    private Long part;

    @Column(name = "ps_suppkey")
    private Long supplier;
}
