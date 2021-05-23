# stage 1: extracts layers from fat jar
FROM adoptopenjdk/openjdk11:alpine-jre as builder

ARG WORKDIR=/app

# cd /app
WORKDIR ${WORKDIR}

# cp target/*.jar /app/application.jar
COPY target/*.jar application.jar

RUN java -Djarmode=layertools -jar application.jar extract

# stage 2: build image using distroless base image
FROM gcr.io/distroless/java:11

# label our image
LABEL application="dwp-assessment-java"
LABEL author="James Oliver"
LABEL description="An API which calls the API at https://bpdts-test-app.herokuapp.com/, and returns people who are \
listed as either living in London, or whose current coordinates are within 50 miles of London."

ENV PORT=8080
ARG WORKDIR=/app

# cd /app
WORKDIR ${WORKDIR}

# copy layers from builder stage
COPY --from=builder ${WORKDIR}/dependencies/ ./
COPY --from=builder ${WORKDIR}/snapshot-dependencies/ ./
COPY --from=builder ${WORKDIR}/spring-boot-loader/ ./
COPY --from=builder ${WORKDIR}/application/ ./

# cp HealthCheck.java /app/HealthCheck.java
COPY HealthCheck.java ./

# due to a lack of shell in the distroless base image we are unable to a cURL or Wget
HEALTHCHECK --interval=25s --timeout=3s --retries=2 CMD ["java", "HealthCheck.java", "||", "exit", "1"]

# start app
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]