# Java Spring Boot Balance Movements Backend

## Description

Spring Boot backend for Todo List application.

## NOTES:

the database scripts will be executed automatically whet you start the docker-compose command.

## Installation

```bash
mvn clean install
```

## Usage

```bash
mvn spring-boot:run
```

## Docker execution

It provides a PostgresSQL database and the service. To run it, execute: 

```bash
docker-compose up
```

## Swagger

Swagger is available at: http://localhost:8080/swagger-ui/index.html


## Executing karate tests

```bash
mvn clean test -Dkarate.env="local" -Dkarate.options="--tags @clients" -Ddriver=karate > log.log -X
```
