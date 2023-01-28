FROM openjdk:17

ARG JAR_FILE=target/QUPP-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} qupp.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/qupp.jar"]