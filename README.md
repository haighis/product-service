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
See the wiki for REST operations. What can I do with Product Service? You can use it to provide a list of Categories and Products that are accessible via REST GET with JSON content.

## Running

./gradlew bootRun

## Viewing the API

With Netflix Eureka + Swagger a self documenting API will demonstrate API Verbs available 

See the wiki for REST operations.

## Tests

In memory tests with Mockito.  

./gradlew test

To view pass/fail for unit tests there is a report located at 'build/reports/tests/index.html'

## Test Code Coverage

./gradlew test jacocoTestReport

Jacoco Code Coverage Report is located at 'build/reports/coverage/index.html'