FROM openjdk:17
RUN adduser spring
USER spring:spring
COPY ./target/pethub.jar app.jar
ENTRYPOINT ["java","-jar","/pethub.jar"]