package cz.cuni.mff.java.kurinna.microservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Nation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long n_nationkey;

    @Column(length = 25)
    private String n_name;

    @ManyToOne
    @JoinColumn(name = "n_regionkey", referencedColumnName = "r_regionkey")
    private Region region;

    @Column(length = 152)
    private String n_comment;

//    @OneToMany(mappedBy = "nation")
//    private List<Customer> customers;

//    @OneToMany(mappedBy = "nation")
//    private List<Supplier> suppliers;
}
