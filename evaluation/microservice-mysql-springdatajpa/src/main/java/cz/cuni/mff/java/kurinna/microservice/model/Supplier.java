package cz.cuni.mff.java.kurinna.microservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long s_suppkey;

    @Column(length = 25)
    private String s_name;

    @Column(length = 40)
    private String s_address;

    @ManyToOne
    @JoinColumn(name = "s_nationkey", referencedColumnName = "n_nationkey")
    private Nation nation;

    @Column(length = 15)
    private String s_phone;

    private double s_acctbal;

    @Column(length = 101)
    private String s_comment;

//    @OneToMany(mappedBy = "supplier", targetEntity = PartSupp.class)
//    private List<PartSupp> partSupps;
}
