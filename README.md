# Simple User Management Web Service

A RESTful web service implemented using Spring Boot based on Chapter 3 of the online
course [Master Microservices with Spring Boot and Spring Cloud](https://links.in28minutes.com/microservices)
by In28Minutes (via O'Reilly).

Changes/additions made to the course materials:

* Added unit tests with 98% coverage for each change sets.
* Supported YAML as request body content-type and enabled Swagger UI to accept YAML payload.
* Used Lombok and Java features such as record and steam to reduce boilerplate.
* Enabled code formatter, Checkstyle and SonarLint to enforce code quality.

## Build

1. set shell variables
    * `export DOCKER_HOST=tcp://<host>:<port>`, or
    * `export DOCKER_HOST=unix://<path-to-socket-file>`
2. build and push the image
    * `mvn install`

To push image to `hub.docker.com`, make sure `docker login` succeeded and remove the `<registry>`
configuration in pom.xml so that we don't default to using the localhost registry.
