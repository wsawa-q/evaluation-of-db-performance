FROM mysql:8.0.42-debian

COPY database/mysql/my.cnf /etc/mysql/conf.d/my.cnf

COPY database/tpch-data-small.zip /var/lib/mysql-files/tpch-data-small.zip

RUN apt-get update && \
    apt-get install -y unzip

RUN unzip /var/lib/mysql-files/tpch-data-small.zip -d /var/lib/mysql-files/ && \
    rm /var/lib/mysql-files/tpch-data-small.zip

