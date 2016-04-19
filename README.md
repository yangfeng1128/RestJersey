# Course Scheduler Service

## Dependencies

- Java 8
- Maven

## Build

Build the WAR file.

```
mvn clean install package
```

## Docker

This will run a Tomcat container with the built WAR and link it to a MongoDB server.

```
docker-compose up
```

## Load Data

```
MONGO_HOST=192.168.99.100 java \
    -cp "target/Project3RestJersey-0.0.1-SNAPSHOT/WEB-INF/lib/*:target/Project3RestJersey-0.0.1-SNAPSHOT/WEB-INF/classes" \
    edu.gatech.project3for6310.mongoManipulations.DatabaseOperations
```
