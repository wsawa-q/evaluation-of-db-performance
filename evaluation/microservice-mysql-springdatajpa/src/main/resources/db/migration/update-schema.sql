CREATE TABLE customer
(
    c_custkey    BIGINT AUTO_INCREMENT NOT NULL,
    c_name       VARCHAR(25) NULL,
    c_address    VARCHAR(40) NULL,
    c_nationkey  BIGINT NULL,
    c_phone      VARCHAR(15) NULL,
    c_acctbal DOUBLE NOT NULL,
    c_mktsegment VARCHAR(10) NULL,
    c_comment    VARCHAR(117) NULL,
    CONSTRAINT pk_customer PRIMARY KEY (c_custkey)
);

CREATE TABLE line_item
(
    l_quantity DOUBLE NOT NULL,
    l_extendedprice DOUBLE NOT NULL,
    l_discount DOUBLE NOT NULL,
    l_tax DOUBLE NOT NULL,
    l_returnflag   VARCHAR(1) NULL,
    l_linestatus   VARCHAR(1) NULL,
    l_shipdate     datetime NULL,
    l_commitdate   datetime NULL,
    l_receiptdate  datetime NULL,
    l_shipinstruct VARCHAR(25) NULL,
    l_shipmode     VARCHAR(10) NULL,
    l_comment      VARCHAR(44) NULL,
    l_orderkey     BIGINT NOT NULL,
    l_linenumber   INT    NOT NULL,
    l_partkey      BIGINT NULL,
    l_suppkey      BIGINT NULL,
    CONSTRAINT pk_lineitem PRIMARY KEY (l_orderkey, l_linenumber)
);

CREATE TABLE nation
(
    n_nationkey BIGINT AUTO_INCREMENT NOT NULL,
    n_name      VARCHAR(25) NULL,
    n_regionkey BIGINT NULL,
    n_comment   VARCHAR(152) NULL,
    CONSTRAINT pk_nation PRIMARY KEY (n_nationkey)
);

CREATE TABLE orders
(
    o_orderkey      BIGINT AUTO_INCREMENT NOT NULL,
    o_custkey       BIGINT NULL,
    o_orderstatus   VARCHAR(1) NULL,
    o_totalprice DOUBLE NOT NULL,
    o_orderdate     datetime NULL,
    o_orderpriority VARCHAR(15) NULL,
    o_clerk         VARCHAR(15) NULL,
    o_shippriority  INT NOT NULL,
    o_comment       VARCHAR(79) NULL,
    CONSTRAINT pk_orders PRIMARY KEY (o_orderkey)
);

CREATE TABLE part
(
    p_partkey   BIGINT AUTO_INCREMENT NOT NULL,
    p_name      VARCHAR(55) NULL,
    p_mfgr      VARCHAR(25) NULL,
    p_brand     VARCHAR(10) NULL,
    p_type      VARCHAR(25) NULL,
    p_size      INT NOT NULL,
    p_container VARCHAR(10) NULL,
    p_retailprice DOUBLE NOT NULL,
    p_comment   VARCHAR(23) NULL,
    CONSTRAINT pk_part PRIMARY KEY (p_partkey)
);

CREATE TABLE part_supp
(
    ps_availqty INT    NOT NULL,
    ps_supplycost DOUBLE NOT NULL,
    ps_comment  VARCHAR(199) NULL,
    ps_partkey  BIGINT NOT NULL,
    ps_suppkey  BIGINT NOT NULL,
    CONSTRAINT pk_partsupp PRIMARY KEY (ps_partkey, ps_suppkey)
);

CREATE TABLE region
(
    r_regionkey BIGINT AUTO_INCREMENT NOT NULL,
    r_name      VARCHAR(25) NULL,
    r_comment   VARCHAR(152) NULL,
    CONSTRAINT pk_region PRIMARY KEY (r_regionkey)
);

CREATE TABLE supplier
(
    s_suppkey   BIGINT AUTO_INCREMENT NOT NULL,
    s_name      VARCHAR(25) NULL,
    s_address   VARCHAR(40) NULL,
    s_nationkey BIGINT NULL,
    s_phone     VARCHAR(15) NULL,
    s_acctbal DOUBLE NOT NULL,
    s_comment   VARCHAR(101) NULL,
    CONSTRAINT pk_supplier PRIMARY KEY (s_suppkey)
);

ALTER TABLE customer
    ADD CONSTRAINT FK_CUSTOMER_ON_C_NATIONKEY FOREIGN KEY (c_nationkey) REFERENCES nation (n_nationkey);

ALTER TABLE line_item
    ADD CONSTRAINT FK_LINEITEM_ON_LPALSU FOREIGN KEY (l_partkey, l_suppkey) REFERENCES part_supp (ps_partkey, ps_suppkey);

ALTER TABLE line_item
    ADD CONSTRAINT FK_LINEITEM_ON_L_ORDERKEY FOREIGN KEY (l_orderkey) REFERENCES orders (o_orderkey);

ALTER TABLE nation
    ADD CONSTRAINT FK_NATION_ON_N_REGIONKEY FOREIGN KEY (n_regionkey) REFERENCES region (r_regionkey);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_O_CUSTKEY FOREIGN KEY (o_custkey) REFERENCES customer (c_custkey);

ALTER TABLE part_supp
    ADD CONSTRAINT FK_PARTSUPP_ON_PS_PARTKEY FOREIGN KEY (ps_partkey) REFERENCES part (p_partkey);

ALTER TABLE part_supp
    ADD CONSTRAINT FK_PARTSUPP_ON_PS_SUPPKEY FOREIGN KEY (ps_suppkey) REFERENCES supplier (s_suppkey);

ALTER TABLE supplier
    ADD CONSTRAINT FK_SUPPLIER_ON_S_NATIONKEY FOREIGN KEY (s_nationkey) REFERENCES nation (n_nationkey);