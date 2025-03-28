package cz.cuni.mff.java.kurinna.microservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long p_partkey;

    @Column(length = 55)
    private String p_name;

    @Column(length = 25)
    private String p_mfgr;

    @Column(length = 10)
    private String p_brand;

    @Column(length = 25)
    private String p_type;

    private int p_size;

    @Column(length = 10)
    private String p_container;

    private double p_retailprice;

    @Column(length = 23)
    private String p_comment;

//    @OneToMany(mappedBy = "ps_partkey", targetEntity = PartSupp.class)
//    private List<PartSupp> partSupps;
}
