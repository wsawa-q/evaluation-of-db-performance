CREATE TABLE
  region (
    r_regionkey INTEGER PRIMARY KEY,
    r_name CHAR(25) NOT NULL,
    r_comment VARCHAR(152)
  );

CREATE TABLE
  nation (
    n_nationkey INTEGER PRIMARY KEY,
    n_name CHAR(25) NOT NULL,
    n_regionkey INTEGER NOT NULL,
    n_comment VARCHAR(152)
  );

CREATE TABLE
  part (
    p_partkey BIGINT PRIMARY KEY,
    p_name VARCHAR(55) NOT NULL,
    p_mfgr CHAR(25) NOT NULL,
    p_brand CHAR(10) NOT NULL,
    p_type VARCHAR(25) NOT NULL,
    p_size INTEGER NOT NULL,
    p_container CHAR(10) NOT NULL,
    p_retailprice DOUBLE PRECISION NOT NULL,
    p_comment VARCHAR(23) NOT NULL
  );

CREATE TABLE
  supplier (
    s_suppkey BIGINT PRIMARY KEY,
    s_name CHAR(25) NOT NULL,
    s_address VARCHAR(40) NOT NULL,
    s_nationkey INTEGER NOT NULL,
    s_phone CHAR(15) NOT NULL,
    s_acctbal DOUBLE PRECISION NOT NULL,
    s_comment VARCHAR(101) NOT NULL
  );

CREATE TABLE
  partsupp (
    ps_partkey BIGINT NOT NULL,
    ps_suppkey BIGINT NOT NULL,
    ps_availqty BIGINT NOT NULL,
    ps_supplycost DOUBLE PRECISION NOT NULL,
    ps_comment VARCHAR(199) NOT NULL,
    PRIMARY KEY (ps_partkey, ps_suppkey)
  );

CREATE TABLE
  customer (
    c_custkey BIGINT PRIMARY KEY,
    c_name VARCHAR(25) NOT NULL,
    c_address VARCHAR(40) NOT NULL,
    c_nationkey INTEGER NOT NULL,
    c_phone CHAR(15) NOT NULL,
    c_acctbal DOUBLE PRECISION NOT NULL,
    c_mktsegment CHAR(10) NOT NULL,
    c_comment VARCHAR(117) NOT NULL
  );

CREATE TABLE
  orders (
    o_orderkey BIGINT PRIMARY KEY,
    o_custkey BIGINT NOT NULL,
    o_orderstatus CHAR(1) NOT NULL,
    o_totalprice DOUBLE PRECISION NOT NULL,
    o_orderdate DATE NOT NULL,
    o_orderpriority CHAR(15) NOT NULL,
    o_clerk CHAR(15) NOT NULL,
    o_shippriority INTEGER NOT NULL,
    o_comment VARCHAR(79) NOT NULL
  );

CREATE TABLE
  lineitem (
    l_orderkey BIGINT NOT NULL,
    l_partkey BIGINT NOT NULL,
    l_suppkey BIGINT NOT NULL,
    l_linenumber BIGINT NOT NULL,
    l_quantity DOUBLE PRECISION NOT NULL,
    l_extendedprice DOUBLE PRECISION NOT NULL,
    l_discount DOUBLE PRECISION NOT NULL,
    l_tax DOUBLE PRECISION NOT NULL,
    l_returnflag CHAR(1) NOT NULL,
    l_linestatus CHAR(1) NOT NULL,
    l_shipdate DATE NOT NULL,
    l_commitdate DATE NOT NULL,
    l_receiptdate DATE NOT NULL,
    l_shipinstruct CHAR(25) NOT NULL,
    l_shipmode CHAR(10) NOT NULL,
    l_comment VARCHAR(44) NOT NULL,
    PRIMARY KEY (l_orderkey, l_linenumber)
  );

-- Load data via COPY
COPY region (r_regionkey, r_name, r_comment)
FROM
    '/tbl_files/tpch-data-small/region.tbl'
WITH
    (FORMAT csv, DELIMITER '|', NULL '');

COPY nation (n_nationkey, n_name, n_regionkey, n_comment)
FROM
    '/tbl_files/tpch-data-small/nation.tbl'
WITH
    (FORMAT csv, DELIMITER '|', NULL '');

COPY part (
  p_partkey,
  p_name,
  p_mfgr,
  p_brand,
  p_type,
  p_size,
  p_container,
  p_retailprice,
  p_comment
)
FROM
    '/tbl_files/tpch-data-small/part.tbl'
WITH
  (FORMAT csv, DELIMITER '|', NULL '');

COPY supplier (
  s_suppkey,
  s_name,
  s_address,
  s_nationkey,
  s_phone,
  s_acctbal,
  s_comment
)
FROM
  '/tbl_files/tpch-data-small/supplier.tbl'
WITH
  (FORMAT csv, DELIMITER '|', NULL '');

COPY partsupp (
  ps_partkey,
  ps_suppkey,
  ps_availqty,
  ps_supplycost,
  ps_comment
)
FROM
  '/tbl_files/tpch-data-small/partsupp.tbl'
WITH
  (FORMAT csv, DELIMITER '|', NULL '');

COPY customer (
  c_custkey,
  c_name,
  c_address,
  c_nationkey,
  c_phone,
  c_acctbal,
  c_mktsegment,
  c_comment
)
FROM
  '/tbl_files/tpch-data-small/customer.tbl'
WITH
  (FORMAT csv, DELIMITER '|', NULL '');

COPY orders (
  o_orderkey,
  o_custkey,
  o_orderstatus,
  o_totalprice,
  o_orderdate,
  o_orderpriority,
  o_clerk,
  o_shippriority,
  o_comment
)
FROM
  '/tbl_files/tpch-data-small/orders.tbl'
WITH
  (FORMAT csv, DELIMITER '|', NULL '');

COPY lineitem (
  l_orderkey,
  l_partkey,
  l_suppkey,
  l_linenumber,
  l_quantity,
  l_extendedprice,
  l_discount,
  l_tax,
  l_returnflag,
  l_linestatus,
  l_shipdate,
  l_commitdate,
  l_receiptdate,
  l_shipinstruct,
  l_shipmode,
  l_comment
)
FROM
  '/tbl_files/tpch-data-small/lineitem.tbl'
WITH
  (FORMAT csv, DELIMITER '|', NULL '');