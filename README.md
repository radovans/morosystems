# Demo project for Morosystems

## Prerequisites
- Java 21
- Docker

## How to run
1. Clone the repository
2. Start PostgreSQL database in the background
    ```bash
    docker run -d -p 5401:5432 --name morosystems --restart always -e POSTGRES_USER=morouser -e POSTGRES_PASSWORD=moropass -e POSTGRES_DB=morosystems -v morosystems_postgres_data:/var/lib/postgresql/data postgres
    ```
3. Run `mvn clean flyway:migrate` to apply database migrations
4. Run `mvn spring-boot:run` to start the application
5. Access the application at `http://localhost:8080`