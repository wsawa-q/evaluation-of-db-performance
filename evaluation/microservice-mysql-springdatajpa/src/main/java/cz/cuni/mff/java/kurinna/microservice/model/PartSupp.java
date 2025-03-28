package cz.cuni.mff.java.kurinna.microservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
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

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        PartSupp partSupp = (PartSupp) o;
        return getPartSuppPK() != null && Objects.equals(getPartSuppPK(), partSupp.getPartSuppPK());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(partSuppPK);
    }
}
