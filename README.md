# evaluation-of-db-performance

## How to Use

### 1. Set up the Environment

  - Ensure you have Docker and Docker Compose installed.
  - Create a `.env` file in the root of the project and set the following variables:
      - `MYSQL_ROOT_PASSWORD`
      - `MYSQL_DATABASE`
      - `MYSQL_USER`
      - `MYSQL_PASSWORD`

### 2. Run the Application

  - To start all the services, run the following command in the root directory:

    ```bash
    docker-compose up -d
    ```

### 3. Open the Web Application

  - To start using the application, navigate to `localhost:3000` in browser

## Project Structure

The project is divided into several modules:

  - `common`: Contains shared code used by other modules.
  - `database`: Includes the database schema and initialization scripts.
  - `eureka-server`: A service registry for the microservices.
  - `frontend`: A React application for interacting with the backend.
  - `microservice-mysql-cayenne`: A microservice that uses Apache Cayenne for database queries.
  - `microservice-mysql-ebean`: A microservice that uses Ebean for database queries.
  - `microservice-mysql-jdbc`: A microservice that uses JDBC for database queries.
  - `microservice-mysql-jooq`: A microservice that uses jOOQ for database queries.
  - `microservice-mysql-mybatis`: A microservice that uses MyBatis for database queries.
  - `microservice-mysql-springdatajpa`: A microservice that uses Spring Data JPA for database queries.
  - `orchestrator`: A service that orchestrates the different microservices and aggregates the results.

## Available Queries

The application supports a variety of queries, categorized as follows:

  - **Selection, Projection, Source (of data)**: Basic queries on indexed and non-indexed columns, including range queries.
  - **Aggregation**: Queries using `COUNT` and `MAX`.
  - **Joins**: Simple and complex joins, including outer joins.
  - **Set operations**: `UNION`, `INTERSECT`, and `DIFFERENCE`.
  - **Result Modification**: Queries involving sorting and `DISTINCT`.
  - **TPC-H Benchmark Queries**: A set of business-oriented queries for benchmarking.

For more details on the specific queries, refer to the `database/mysql/queries.md` file.