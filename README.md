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

## Additional Information
- Run tests with `mvn clean test`
- Run checkstyle with `mvn checkstyle:check` or `mvn checkstyle:checkstyle` - report will be generated in `/target/reports/checkstyle.html`
    ```bash
    open target/reports/checkstyle.html
    ```
- Run Jacoco with `mvn jacoco:report` - report will be generated in `/target/site/jacoco/index.html`
    ```bash
    open target/site/jacoco/index.html
    ```
- Run Spotbugs with `mvn spotbugs:spotbugs` - report will be generated in `/target/site/spotbugs.html`
    ```bash
    open target/site/spotbugs.html
    ```
- Run PMD with `mvn pmd:pmd` - report will be generated in `/target/site/pmd.html`
    ```bash
    open target/reports/pmd.html
    ```
- Swagger UI is available at `http://localhost:8080/swagger`
    ```bash
    open http://localhost:8080/swagger
    ```