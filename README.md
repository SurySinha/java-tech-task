# Lunch Microservice

The service provides an endpoint that will determine, from a set of recipes, what I can have for lunch at a given date, based on my fridge ingredient's expiry date, so that I can quickly decide what I’ll be having to eat, and the ingredients required to prepare the meal.

## Prerequisites

* [Java 11 Runtime](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
* [Docker](https://docs.docker.com/get-docker/) & [Docker-Compose](https://docs.docker.com/compose/install/)

*Note: Docker is used for the local MySQL database instance, feel free to use your own instance or any other SQL database and insert data from lunch-data.sql script* 


### Run

1. Start database:

    ```
    docker-compose up -d
    ```
   
2. Add test data from  `sql/lunch-data.sql` to the database. Here's a helper script if you prefer:


    ```
    CONTAINER_ID=$(docker inspect --format="{{.Id}}" lunch-db)
    ```
    
    ```
    docker cp sql/lunch-data.sql $CONTAINER_ID:/lunch-data.sql
    ```
    
    ```
    docker exec $CONTAINER_ID /bin/sh -c 'mysql -u root -prezdytechtask lunch </lunch-data.sql'
    ```
    
3. Run Springboot LunchApplication

### Changes done

1. Refactored the code and organised the code in a better package structure.
2. Added Unit tests for various components like Controller, Service etc.
3. Introduced Spring Data JPA abstraction to abstract complexity.
4. Introduced Lombok for reducing the boiler plate.
5. Introduced Swagger for ease in testing and sharing API Spec.
6. Introduced ModelMapper for reducing the boiler plate in mapping between Entity and Dtos and vice versa.
7. Added dependency for Spring Boot Actuator.

### Future improvements 

1. Improving configuration to move away from configs being hardcoded in application but being embedded at deploy time.
2. Introduce a security layer to authenticate and authorise access of each end points. Use Spring security and customise 
   the solution based on Auth provider
3. Introduce more unit testing for the application and look for a way to introduce regression testing the entire 
   application.
4. Understand the usage pattern and data and consider the performance improvements. 









