package cz.cuni.mff.java.kurinna.microservice.repository;

import cz.cuni.mff.java.kurinna.microservice.model.LineItem;
import cz.cuni.mff.java.kurinna.microservice.model.LineItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LineItemRepository extends JpaRepository<LineItem, LineItemPK>, JpaSpecificationExecutor<LineItem> {
    // Pricing Summary Report Query (Q1)
    // This query reports the amount of business that was billed, shipped, and returned.

    /* The Pricing Summary Report Query provides a summary pricing report for all lineitems shipped as of a given date.
    The date is within 60 - 120 days of the greatest ship date contained in the database. The query lists totals for
    extended price, discounted extended price, discounted extended price plus tax, average quantity, average extended
    price, and average discount. These aggregates are grouped by RETURNFLAG and LINESTATUS, and listed in
    ascending order of RETURNFLAG and LINESTATUS. A count of the number of lineitems in each group is
    included. */
    // The query is based on the following SQL statement:
    // SELECT
    //   l_returnflag,
    //   l_linestatus,
    //   SUM(l_quantity) AS sum_qty,
    //   SUM(l_extendedprice) AS sum_base_price,
    //   SUM(l_extendedprice * (1 - l_discount)) AS sum_disc_price,
    //   SUM(l_extendedprice * (1 - l_discount) * (1 + l_tax)) AS sum_charge,
    //   AVG(l_quantity) AS avg_qty,
    //   AVG(l_extendedprice) AS avg_price,
    //   AVG(l_discount) AS avg_disc,
    //   COUNT(*) AS count_order
    // FROM
    //   lineitem
    // WHERE
    //   l_shipdate <= date '1998-12-01' - interval 'DELTA' day
    // group by
    //   l_returnflag,
    //   l_linestatus
    // order by
    //   l_returnflag,
    //   l_linestatus;

}