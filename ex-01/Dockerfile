FROM openjdk:20
COPY build/libs/ex1-1.0.jar /opt/app/app.jar
COPY src/main/resources/application.yaml /opt/app/application.yaml
USER root
EXPOSE 8000/tcp
CMD ["java", "-jar", "/opt/app/app.jar"]