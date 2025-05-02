FROM postgres:17

USER root
RUN apt-get update && \
    apt-get install -y unzip \
    && rm -rf /var/lib/apt/lists/*

COPY database/tpch-data-small.zip /tmp/tpch-data-small.zip
RUN unzip /tmp/tpch-data-small.zip -d /tbl_files \
    && rm /tmp/tpch-data-small.zip

RUN chown -R postgres:postgres /tbl_files

USER postgres

EXPOSE 5432