# Queries

## TPC-H Benchmark Queries

### Q1) Pricing Summary Report Query

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

## A) Selection, Projection, Source (of data)

### A1) Non-Indexed Columns

```sql
SELECT * FROM lineitem 
WHERE l_extendedprice < 1000.0;
```

### A2) Non-Indexed Columns — Range Query

```sql
SELECT * FROM orders
WHERE o_orderdate 
    BETWEEN '1996-01-01' AND '1996-12-31';
```

### A3) Indexed Columns

```sql
SELECT * FROM customer 
WHERE c_custkey > 10;
```

### A4) Indexed Columns — Range Query

```sql
SELECT * FROM orders
WHERE o_orderkey BETWEEN 1000 AND 50000;
```

## B) Aggregation

### B1) COUNT

```sql
SELECT COUNT(o.o_orderkey) AS order_count, 
       DATE_FORMAT(o.o_orderdate, '%Y-%m') AS order_month
FROM orders o
GROUP BY order_month
ORDER BY order_month;
```

### B2) MAX

```sql
SELECT DATE_FORMAT(l.l_shipdate, '%Y-%m') AS ship_month,
       MAX(l.l_extendedprice) AS max_price
FROM lineitem l
GROUP BY ship_month
ORDER BY ship_month;
```

## C) Joins

### C1) Non-Indexed Columns

```sql
SELECT c.c_name, o.o_orderdate, o.o_totalprice
FROM customer c, orders o
WHERE c.c_mktsegment = 'BUILDING'
AND c.c_custkey = o.o_custkey;
```

### C2) Indexed Columns

```sql
SELECT c.c_name, o.o_orderdate, o.o_totalprice
FROM customer c
JOIN orders o ON c.c_custkey = o.o_custkey;
```

### C3) Complex Join 1

```sql
SELECT c.c_name, n.n_name, o.o_orderdate, o.o_totalprice
FROM customer c
JOIN nation n ON c.c_nationkey = n.n_nationkey
JOIN orders o ON c.c_custkey = o.o_custkey
WHERE n.n_name = 'GERMANY'
AND o.o_orderdate BETWEEN '1996-01-01' AND '1996-12-31';
```

### C4) Complex Join 2

```sql
SELECT c.c_name, n.n_name, r.r_name, o.o_orderdate, o.o_totalprice
FROM customer c
JOIN nation n ON c.c_nationkey = n.n_nationkey
JOIN region r ON n.n_regionkey = r.r_regionkey
JOIN orders o ON c.c_custkey = o.o_custkey
WHERE r.r_name = 'EUROPE'
AND o.o_orderdate BETWEEN '1996-01-01' AND '1996-12-31';
```

### C5) Left Outer Join

```sql
SELECT c.c_custkey, c.c_name, o.o_orderkey, o.o_orderdate
FROM customer c
LEFT OUTER JOIN orders o ON c.c_custkey = o.o_custkey
WHERE c.c_nationkey = 3;
```

## D) Set operations

### D1) UNION

```sql
(SELECT c_nationkey FROM customer)
UNION
(SELECT s_nationkey FROM supplier);
```

### D2) INTERSECT

```sql
-- MySQL doesn't directly support INTERSECT, so we use IN or JOIN
SELECT DISTINCT c.c_custkey
FROM customer c
WHERE c.c_custkey IN (
    SELECT s.s_suppkey
    FROM supplier s
);
```

### D3) DIFFERENCE

```sql
-- MySQL doesn't directly support EXCEPT/MINUS, so we use NOT IN or LEFT JOIN
SELECT DISTINCT c.c_custkey
FROM customer c
WHERE c.c_custkey NOT IN (
    SELECT DISTINCT s.s_suppkey
    FROM supplier s
);
```

## E) Result Modification

### E1) Non-Indexed Columns Sorting

```sql
SELECT c_name, c_address, c_acctbal
FROM customer
ORDER BY c_acctbal DESC
```

### E2) Indexed Columns Sorting

```sql
SELECT o_orderkey, o_custkey, o_orderdate, o_totalprice
FROM orders
ORDER BY o_orderkey
```

### E3) Distinct

```sql
SELECT DISTINCT c_nationkey, c_mktsegment
FROM customer;
```

## F) Advanced Queries

### F1) Subquery with Aggregation

```sql
SELECT c.c_custkey, c.c_name, c.c_acctbal, COUNT(o.o_orderkey) as order_count
FROM customer c
JOIN orders o ON c.c_custkey = o.o_custkey
WHERE c.c_acctbal > (
    SELECT AVG(c_acctbal) 
    FROM customer
)
GROUP BY c.c_custkey, c.c_name, c.c_acctbal
ORDER BY order_count DESC
```

### F2) GROUP BY with HAVING

```sql
SELECT n.n_name as nation, COUNT(c.c_custkey) as customer_count, 
       AVG(c.c_acctbal) as avg_balance
FROM customer c
JOIN nation n ON c.c_nationkey = n.n_nationkey
GROUP BY n.n_name
HAVING COUNT(c.c_custkey) > 5
ORDER BY customer_count DESC;
```

### F3) Window Functions

```sql
SELECT o.o_orderkey, o.o_custkey, o.o_totalprice,
       RANK() OVER (PARTITION BY o.o_custkey ORDER BY o.o_totalprice DESC) as price_rank
FROM orders o
WHERE o.o_orderdate BETWEEN '1996-01-01' AND '1996-12-31'
```

### F4) Complex Business Query

```sql
SELECT 
    n.n_name as nation,
    YEAR(o.o_orderdate) as order_year,
    SUM(l.l_extendedprice * (1 - l.l_discount)) as revenue
FROM 
    customer c
    JOIN orders o ON c.c_custkey = o.o_custkey
    JOIN lineitem l ON o.o_orderkey = l.l_orderkey
    JOIN nation n ON c.c_nationkey = n.n_nationkey
    JOIN region r ON n.n_regionkey = r.r_regionkey
WHERE 
    r.r_name = 'EUROPE'
    AND o.o_orderdate BETWEEN '1995-01-01' AND '1996-12-31'
GROUP BY 
    nation, order_year
ORDER BY 
    nation, order_year;
```
