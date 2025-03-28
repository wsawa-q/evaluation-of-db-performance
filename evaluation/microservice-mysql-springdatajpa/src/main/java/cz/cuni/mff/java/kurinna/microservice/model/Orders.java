package cz.cuni.mff.java.kurinna.microservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long o_orderkey;

    @ManyToOne
    @JoinColumn(name = "o_custkey", referencedColumnName = "c_custkey")
    private Customer customer;

    @Column(length = 1)
    private String o_orderstatus;

    private double o_totalprice;

    private Timestamp o_orderdate;

    @Column(length = 15)
    private String o_orderpriority;

    @Column(length = 15)
    private String o_clerk;

    private int o_shippriority;

    @Column(length = 79)
    private String o_comment;

//    @OneToMany(mappedBy = "order")
//    private List<LineItem> lineItems;
}
