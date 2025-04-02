package cz.cuni.mff.java.kurinna.microservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long r_regionkey;

    @Column(length = 25)
    private String r_name;

    @Column(length = 152)
    private String r_comment;

//    @OneToMany(mappedBy = "region", targetEntity = Nation.class)
//    private List<Nation> nations;
}
