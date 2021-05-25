# DWP Assessment Java

<table>
<tr>
<td>
An API which calls the API at https://bpdts-test-app.herokuapp.com/, and returns people who are listed as either living in London, or whose current coordinates are within 50 miles of London.
</td>
</tr>
</table>

## Contents

- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Actuator](#actuator)
- [Swagger](#swagger)
- [Configuration](#configuration)
- [Testing](#testing)
- [Security Testing](#security-testing)
- [JavaDocs](#javadocs)
- [Lombok](#lombok)
- [Version Management](#version-management)
- [Maven Git Commit](#maven-git-commit)

## Getting Started

### Prerequisites

To install, run and modify this project you will need to have:

- [Java](https://adoptopenjdk.net)
- [Maven](http://maven.apache.org)
- [Git](https://git-scm.com)
- [Docker](https://www.docker.com)

### Installation

To start, please `fork` and `clone` the repository to your local machine. You are able to run the service directly on the command line, using _Docker_, or with your IDE of choice.

### Running

#### Command Line

To run the service from the command line first you need to clean the build output directory and then package the service into a `JAR` using _Maven_. You can then start the `JAR` using the `java` runtime environment.

```bash
mvn clean package
java -jar target/*.jar
```

#### Docker

The included `Dockerfile` allows the service to be containerised and run using _Docker_. First you need to clean the build output directory and then package the service into a `JAR` using _Maven_. You can then build an image and then start a container from it, exposing the internal port of `8080`.

```bash
mvn clean package
docker build -t dwp-assessment-java .
docker run --name dwp-assessment-java -p 8080:8080 dwp-assessment-java
```

#### Docker Compose

A _Docker Compose_ file has been provided to facilitate running the service in conjunction with a [WireMock](http://wiremock.org) of the upstream DWP API. This _WireMock_ provides a local instance of the DWP API allowing for development regardless of external connectivity.

To start the service and the _WireMock_ you will first need to package the service and then execute _Docker Compose_ `up`. This will start both services simultaneously and set the `PEOPLE_ENDPOINT` environment variable to the local _WireMock_ instance.

```bash
mvn clean package
docker-compose up
```

By passing the name of the `service` defined in the `docker-compose.yml` file you may start the services individually. By default the `PEOPLE_ENDPOINT` environment variable will be set to the local _WireMock_ instance when starting from _Docker Compose_. This can be overridden to the hosted _Heroku_ API by passing in the `PEOPLE_ENDPOINT` environment variable as demonstrated below.

```bash
docker-compose up wiremock
```

```bash
mvn clean package
docker-compose up app
```

```bash
mvn clean package
PEOPLE_ENDPOINT=https://dwp-techtest.herokuapp.com docker-compose up app
```

## API Endpoints

There are two RESTful API endpoints available:

> `/api/people`

Returns all people available from the DWP API.

> `/api/people/{city}`

Returns all people who are listed as either living in the `city` or whose current coordinates are within 50 miles of the coordinates of the `city`. Currently only `london` has been configured as an available `city`. All other requests will result in a `404 - City Not Found` response. For example `/api/people/london` will return all people living in London or whose coordinates are within 50 miles of London.

## Actuator

[Spring Boot Actuator](https://github.com/spring-projects/spring-boot/tree/v2.5.0/spring-boot-project/spring-boot-actuator) has been configured to help monitor and manage the service. You are able to retrieve `info` and `health` metrics from the following endpoints.

> `/actuator/info`

Returns service information including `java`, `build` and `git` metrics.

> `/actuator/health`

Returns status `200` when the service is running and healthy.

## Swagger

_Swagger_ UI and _OpenAPI_ documentation have automatically been generated using [springdoc-openapi](https://github.com/springdoc/springdoc-openapi). Annotations can be found in the `Application.java` and `PeopleController.java` that describe API operations. The _Swagger_ UI page is the perfect place to see endpoint information and experiment with requests.

> `/`

Returns the _Swagger_ UI page.

> `/v3/api-docs`

Returns the _OpenAPI_ description in `json`.

> `/v3/api-docs.yaml`

Returns the _OpenAPI_ description in `yaml`.

## Configuration

Service configuration is managed using environment variables using _Spring's_ `application.yml`. This allows the service to be aware of necessary configuration and to provide sensible defaults. City information is provided inside the `Cities` enum.

### Environment Variables

The following environment variables are available for configuration:

| Environment Variable | Default                            | Description                                   |
| -------------------- | ---------------------------------- | --------------------------------------------- |
| PORT                 | 8080                               | Port number for the service                   |
| CONTEXT_PATH         | /                                  | URL context path for the service              |
| PEOPLE_ENDPOINT      | https://dwp-techtest.herokuapp.com | People / Users API endpoint                   |
| MAX_DISTANCE         | 50                                 | Max distance in miles from city's coordinates |

### Cities

Information about the configured cities is stored inside the `Cities` enum. This allows for easy extension of the service to work with additional cities. To add an extra city, create a new entry in the enum providing:

- label - used in the path parameter when calling the upstream API, and also used for logging
- latitude - used when calculating a person's distance from the city
- longitude - used when calculating a person's distance from the city

For example Manchester has been added to the enum below. A request to `/api/people/manchester` will now return all people living in Manchester or whose coordinates are within 50 miles of Manchester.

```java
public enum Cities {
    LONDON("London", 51.514248, -0.093145);
    MANCHESTER("Manchester", 53.483959, -2.244644);

    // code removed for brevity...
}
```

## Testing

All tests have been written using [JUnit5](https://junit.org/junit5/), [Mockito](https://site.mockito.org) and [Spring's MockMvc](https://github.com/spring-projects/spring-framework). To run the test execute the _Maven_ `test` phase.

```bash
mvn clean test
```

Code coverage is measured by [Jacoco](https://www.jacoco.org/jacoco/trunk/index.html) and set to a minimum of 95% excluding `configuration` and `model` packages.

Mutation testing is provided by [Pitest](https://pitest.org). Mutation testing gauges the quality of testing by creating random mutation that should cause tests to fail. This ensures that your tests are working as expected. The mutation threshold is currently set to 80% excluding `configuration` and `model` packages.

The easiest way to generate reports is to execute the _Maven_ `verify` phase since _Jacoco_ and _Pitest_ goals have been bound to this phase.

```bash
mvn clean verify
```

Reports can be found in the `/target/` directory.

## Security Testing

The [OWASP](https://owasp.org) [dependency-check-maven](https://jeremylong.github.io/DependencyCheck/dependency-check-maven/) plugin has been configured to detect dependency vulnerabilities. Reports are generated during the _Maven_ `verify` phase and can be found in the `/target/` directory.

```bash
mvn clean verify
```

## JavaDocs

JavaDocs is a documentation generator for generating API documentation in HTML format from Java source code. The [Apache Maven Javadoc Plugin](https://maven.apache.org/plugins/maven-javadoc-plugin/) has been configured to generate the HTML in the `package` _Maven_ phase. The [Lombok Maven Plugin](http://anthonywhitford.com/lombok.maven/lombok-maven-plugin/index.html) has also been added to `delombok` source code to ensure that the JavaDocs include all the code automatically generated by _Lombok_.

```bash
mvn clean package
```

The HTML report can be found in `/target/site/apidocs/`.

## Lombok

[Lombok](https://projectlombok.org) is a code generation library that allows you to add boiler plate code via annotations. _Lombok_ is used across the project to simplify the creation of `loggers`, `getters`, `setters` and `constructors`.

## Version Management

The [Versions Maven Plugin](https://www.mojohaus.org/versions-maven-plugin/) has been configured to show outdated dependencies during the _Maven_ `verify` phase.

## Maven Git Commit

The [Maven Git Commit ID Plugin](https://github.com/git-commit-id/git-commit-id-maven-plugin) has been configured to add _Git_ metrics to the `/actuator/info` endpoint.
