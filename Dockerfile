FROM openjdk:8

WORKDIR /usr/src/cao/

EXPOSE 8081

RUN ls -la

ENV TZ=America/Los_Angeles

COPY target/cao-odtschedule.jar cao-odtschedule.jar

ENTRYPOINT ["java", "-jar", "cao-odtschedule.jar"]
