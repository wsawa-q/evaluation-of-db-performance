package cz.cuni.mff.java.kurinna.microservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long c_custkey;

    @Column(length = 25)
    private String c_name;

    @Column(length = 40)
    private String c_address;

    @ManyToOne
    @JoinColumn(name = "c_nationkey", referencedColumnName = "n_nationkey")
    private Nation nation;

    @Column(length = 15)
    private String c_phone;

    private double c_acctbal;

    @Column(length = 10)
    private String c_mktsegment;

    @Column(length = 117)
    private String c_comment;

//    @OneToMany(mappedBy = "customer", targetEntity = Orders.class)
//    private List<Orders> orders;
}
