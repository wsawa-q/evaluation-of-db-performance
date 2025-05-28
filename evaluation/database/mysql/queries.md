# Queries

## A) Selection, Projection, Source (of data)

### A1) Non-Indexed Columns

This query selects all records from the lineitem table
```sql
SELECT * FROM lineitem;
```

### A2) Non-Indexed Columns — Range Query

This query selects all records from the orders table where the order date is between '1996-01-01' and '1996-12-31'
```sql
SELECT * FROM orders
WHERE o_orderdate 
    BETWEEN '1996-01-01' AND '1996-12-31';
```

### A3) Indexed Columns

This query selects all records from the customer table
```sql
SELECT * FROM customer;
```

### A4) Indexed Columns — Range Query

This query selects all records from the orders table where the order key is between 1000 and 50000
```sql
SELECT * FROM orders
WHERE o_orderkey BETWEEN 1000 AND 50000;
```

## B) Aggregation

### B1) COUNT

This query counts the number of orders grouped by order month
```sql
SELECT COUNT(o.o_orderkey) AS order_count, 
       DATE_FORMAT(o.o_orderdate, '%Y-%m') AS order_month
FROM orders o
GROUP BY order_month;
```

### B2) MAX

This query finds the maximum extended price from the lineitem table grouped by ship month
```sql
SELECT DATE_FORMAT(l.l_shipdate, '%Y-%m') AS ship_month,
       MAX(l.l_extendedprice) AS max_price
FROM lineitem l
GROUP BY ship_month;
```

## C) Joins

### C1) Non-Indexed Columns

This query gives customer names, order dates, and total prices for customers
```sql
SELECT c.c_name, o.o_orderdate, o.o_totalprice
FROM customer c, orders o;
```

### C2) Indexed Columns

This query gives customer names, order dates, and total prices for all customers
```sql
SELECT c.c_name, o.o_orderdate, o.o_totalprice
FROM customer c
JOIN orders o ON c.c_custkey = o.o_custkey;
```

### C3) Complex Join 1

This query gives customer names, nation names, order dates, and total prices for customers
```sql
SELECT c.c_name, n.n_name, o.o_orderdate, o.o_totalprice
FROM customer c
JOIN nation n ON c.c_nationkey = n.n_nationkey
JOIN orders o ON c.c_custkey = o.o_custkey;
```

### C4) Complex Join 2

This query gives customer names, nation names, region names, order dates, and total prices for customers
```sql
SELECT c.c_name, n.n_name, r.r_name, o.o_orderdate, o.o_totalprice
FROM customer c
JOIN nation n ON c.c_nationkey = n.n_nationkey
JOIN region r ON n.n_regionkey = r.r_regionkey
JOIN orders o ON c.c_custkey = o.o_custkey;
```

### C5) Left Outer Join

This query gives customer names and order dates for all customers, including those without orders
```sql
SELECT c.c_custkey, c.c_name, o.o_orderkey, o.o_orderdate
FROM customer c
LEFT OUTER JOIN orders o ON c.c_custkey = o.o_custkey;
```

## D) Set operations

### D1) UNION

This query combines customer and supplier nation keys
```sql
(SELECT c_nationkey FROM customer)
UNION
(SELECT s_nationkey FROM supplier);
```

### D2) INTERSECT

This query finds common customer and supplier keys
MySQL doesn't directly support INTERSECT, so I used IN
```sql
SELECT DISTINCT c.c_custkey
FROM customer c
WHERE c.c_custkey IN (
    SELECT s.s_suppkey
    FROM supplier s
);
```

### D3) DIFFERENCE

This query finds customer keys that are not in the supplier table
MySQL doesn't directly support EXCEPT/MINUS, so I used NOT IN
```sql
SELECT DISTINCT c.c_custkey
FROM customer c
WHERE c.c_custkey NOT IN (
    SELECT DISTINCT s.s_suppkey
    FROM supplier s
);
```

## E) Result Modification

### E1) Non-Indexed Columns Sorting

This query sorts customer names, addresses, and account balances in descending order of account balance
```sql
SELECT c_name, c_address, c_acctbal
FROM customer
ORDER BY c_acctbal DESC
```

### E2) Indexed Columns Sorting

This query sorts order keys, customer keys, order dates, and total prices in ascending order of order key
```sql
SELECT o_orderkey, o_custkey, o_orderdate, o_totalprice
FROM orders
ORDER BY o_orderkey
```

### E3) Distinct

This query selects distinct nation keys and market segments from the customer table
```sql
SELECT DISTINCT c_nationkey, c_mktsegment
FROM customer;
```

## Advanced Queries
## TPC-H Benchmark Queries

### Q1) Pricing Summary Report Query

//This query reports the amount of business that was billed, shipped, and returned
```sql
SELECT
  l_returnflag,
  l_linestatus,
  SUM(l_quantity) AS sum_qty,
  SUM(l_extendedprice) AS sum_base_price,
  SUM(l_extendedprice * (1 - l_discount)) AS sum_disc_price,
  SUM(l_extendedprice * (1 - l_discount) * (1 + l_tax)) AS sum_charge,
  AVG(l_quantity) AS avg_qty,
  AVG(l_extendedprice) AS avg_price,
  AVG(l_discount) AS avg_disc,
  COUNT(*) AS count_order
FROM lineitem
WHERE l_shipdate <= DATE_SUB('1998-12-01', INTERVAL 90 DAY)
GROUP BY l_returnflag, l_linestatus
ORDER BY l_returnflag, l_linestatus
```

### Q2) Minimum Cost Supplier Query

//This query finds which supplier should be selected to place an order for a given part in a given region
```sql
SELECT
  s.s_acctbal,
  s.s_name,
  n.n_name,
  p.p_partkey,
  p.p_mfgr,
  s.s_address,
  s.s_phone,
  s.s_comment
FROM
  part p,
  supplier s,
  partsupp ps,
  nation n,
  region r
WHERE
  p.p_partkey = ps.ps_partkey
  AND s.s_suppkey = ps.ps_suppkey
  AND p.p_size = 15
  AND p.p_type LIKE '%BRASS'
  AND s.s_nationkey = n.n_nationkey
  AND n.n_regionkey = r.r_regionkey
  AND r.r_name = 'EUROPE'
  AND ps.ps_supplycost = (
    SELECT MIN(ps.ps_supplycost)
    FROM
      partsupp ps,
      supplier s,
      nation n,
      region r
    WHERE
      p.p_partkey = ps.ps_partkey
      AND s.s_suppkey = ps.ps_suppkey
      AND s.s_nationkey = n.n_nationkey
      AND n.n_regionkey = r.r_regionkey
      AND r.r_name = 'EUROPE'
  )
ORDER BY
  s.s_acctbal DESC,
  n.n_name,
  s.s_name,
  p.p_partkey
LIMIT 100
```

### Q3) Shipping Priority Query

//This query retrieves the 10 unshipped orders with the highest value
```sql
SELECT
  l.l_orderkey,
  SUM(l.l_extendedprice * (1 - l.l_discount)) AS revenue,
  o.o_orderdate,
  o.o_shippriority
FROM
  customer c,
  orders o,
  lineitem l
WHERE
  c.c_mktsegment = 'BUILDING'
  AND c.c_custkey = o.o_custkey
  AND l.l_orderkey = o.o_orderkey
  AND o.o_orderdate < '1995-03-15'
  AND l.l_shipdate > '1995-03-15'
GROUP BY
  l.l_orderkey,
  o.o_orderdate,
  o.o_shippriority
ORDER BY
  revenue DESC,
  o.o_orderdate
LIMIT 10
```

### Q4) Order Priority Checking Query

//This query determines how well the order priority system is working and gives an assessment of customer satisfaction
```sql
SELECT
  o_orderpriority,
  COUNT(*) AS order_count
FROM
  orders
WHERE
  o_orderdate >= '1993-07-01'
  AND o_orderdate < DATE_ADD('1993-07-01', INTERVAL 3 MONTH)
  AND EXISTS (
    SELECT *
    FROM
      lineitem
    WHERE
      l_orderkey = o_orderkey
      AND l_commitdate < l_receiptdate
  )
GROUP BY
  o_orderpriority
ORDER BY
  o_orderpriority
```

### Q5) Local Supplier Volume Query

//This query lists the revenue volume done through local suppliers
```sql
SELECT
  n.n_name,
  SUM(l.l_extendedprice * (1 - l.l_discount)) AS revenue
FROM
  customer c,
  orders o,
  lineitem l,
  supplier s,
  nation n,
  region r
WHERE
  c.c_custkey = o.o_custkey
  AND l.l_orderkey = o.o_orderkey
  AND l.l_suppkey = s.s_suppkey
  AND c.c_nationkey = s.s_nationkey
  AND s.s_nationkey = n.n_nationkey
  AND n.n_regionkey = r.r_regionkey
  AND r.r_name = 'ASIA'
  AND o.o_orderdate >= '1994-01-01'
  AND o.o_orderdate < DATE_ADD('1994-01-01', INTERVAL 1 YEAR)
GROUP BY
  n.n_name
ORDER BY
  revenue DESC
```