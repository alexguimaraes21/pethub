FROM openjdk:17
COPY ./target/pethub.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]