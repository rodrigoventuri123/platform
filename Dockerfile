FROM openjdk:16-jdk-alpine
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Duser.timezone=America/Sao_Paulo","-jar","/app.jar"]
