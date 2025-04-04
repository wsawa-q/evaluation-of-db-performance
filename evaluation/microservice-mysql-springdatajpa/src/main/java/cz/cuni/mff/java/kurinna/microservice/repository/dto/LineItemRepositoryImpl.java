package cz.cuni.mff.java.kurinna.microservice.repository.dto;

import cz.cuni.mff.java.kurinna.microservice.dto.PricingSummary;
import cz.cuni.mff.java.kurinna.microservice.model.LineItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public class LineItemRepositoryImpl implements LineItemRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PricingSummary> findPricingSummaryReport(int days) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PricingSummary> cq = cb.createQuery(PricingSummary.class);
        Root<LineItem> root = cq.from(LineItem.class);

        // Create a constructor expression for the DTO
        cq.select(cb.construct(
                PricingSummary.class,
                root.get("l_returnflag"),  // l_returnflag
                root.get("l_linestatus"),  // l_linestatus
                cb.sum(root.get("l_quantity")),  // SUM(l_quantity)
                cb.sum(root.get("l_extendedprice")),  // SUM(l_extendedprice)
                cb.sum(cb.prod(root.get("l_extendedprice"), cb.diff(1, root.get("l_discount")))),  // SUM(l_extendedprice * (1 - l_discount))
                cb.sum(cb.prod(cb.prod(root.get("l_extendedprice"), cb.diff(1, root.get("l_discount"))),
                        cb.sum(1, root.get("l_tax")))),  // SUM(l_extendedprice * (1 - l_discount) * (1 + l_tax))
                cb.avg(root.get("l_quantity")),  // AVG(l_quantity)
                cb.avg(root.get("l_extendedprice")),  // AVG(l_extendedprice)
                cb.avg(root.get("l_discount")),  // AVG(l_discount)
                cb.count(root)  // COUNT(*)
        ));

        // new date '1998-12-01'
        Calendar cal = Calendar.getInstance();
        cal.set(1998, Calendar.DECEMBER, 1);
        Date dateThreshold = cal.getTime();

        Date calculatedDate = new Date(dateThreshold.getTime() - (days * 24 * 60 * 60 * 1000L));

        // Apply the WHERE clause: l_shipdate <= :shipDateThreshold
        cq.where(cb.lessThanOrEqualTo(root.get("l_shipdate"), calculatedDate));

        // Group by RETURNFLAG and LINESTATUS
        cq.groupBy(root.get("l_returnflag"), root.get("l_linestatus"));

        // Order by RETURNFLAG and LINESTATUS
        cq.orderBy(cb.asc(root.get("l_returnflag")), cb.asc(root.get("l_linestatus")));

        TypedQuery<PricingSummary> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
}
