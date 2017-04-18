# Product Service

Product Service is responsible for:
- Category listing
- Product Listing

Product Service is built on Spring. 

- Spring Boot
- Spring Cloud
- Liquibase for Database Migrations
- Junit
- Mockito
- Jacoco Code Coverage 

Integration with Travic CI for continuous integration.

[![Build Status](https://travis-ci.org/haighis/product-service.svg?branch=master)](https://travis-ci.org/haighis/product-service)

## Usage
See the [wiki](https://github.com/haighis/product-service/wiki) for REST operations. What can I do with Product Service? You can use it to provide a list of Categories and Products that are accessible via REST GET with JSON content.

## Running

./gradlew bootRun

This Microservice can benefit from Netflix Eureka, but does not require Netflix Eureka in order to run. Run each Microservice Product Service, Shopping Cart Service and Order Service. Then run Shop UI and open your browser to http://localhost:2005

## Database Setup

Database used is PostgreSQL. Using a default PostgreSQL setup on windows/macosx has been tested. 
1. Create two databases cart and ecommerce
2. Create a user 'jhaigh' and password 'jhaigh' that has priveledges to cart and commerce database in PostgreSql.
3. run ```./gradlew bootRun``` or ```gradlew.bat bootRun``` which will create the database schema on first run.

## Consuming the API

While not required a Netflix Eureka + Swagger combo provides a self documenting API and Netflix Eureka will auto discover Product Service, Order Service and Shopping Cart Services. Running Swagger UI will demonstrate API Verbs available. 

See the [wiki](https://github.com/haighis/product-service/wiki) for REST operations.

## Tests

In memory tests with Mockito.  

./gradlew test

To view pass/fail for unit tests there is a report located at 'build/reports/tests/index.html'

Test Summary
![Test Summary](/media/TestSummary.jpg?raw=true "Test Summary")

## Test Code Coverage

./gradlew test jacocoTestReport

Jacoco Code Coverage Report is located at 'build/reports/coverage/index.html'

Rest API - Category Resource Code Coverage
![Category Resource](/media/CategoryResource.jpg?raw=true "Rest API - Category Resource Code Coverage")