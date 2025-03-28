package cz.cuni.mff.java.kurinna.microservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LineItem {
    @EmbeddedId
    private LineItemPK lineItemPK;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "l_suppkey", referencedColumnName = "ps_suppkey"),
            @JoinColumn(name = "l_partkey", referencedColumnName = "ps_partkey")
    })
    private PartSupp partSupp;

    private double l_quantity;

    private double l_extendedprice;

    @Size(min = 0, max = 1)
    private double l_discount;

    private double l_tax;

    @Column(length = 1)
    private String l_returnflag;

    @Column(length = 1)
    private String l_linestatus;

    private Timestamp l_shipdate;

    private Timestamp l_commitdate;

    private Timestamp l_receiptdate;

    @Column(length = 25)
    private String l_shipinstruct;

    @Column(length = 10)
    private String l_shipmode;

    @Column(length = 44)
    private String l_comment;

    @AssertTrue
    public boolean isShippingDateValid() {
        return l_shipdate.before(l_receiptdate);
    }
}
