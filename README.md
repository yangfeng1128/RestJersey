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
