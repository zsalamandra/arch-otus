# Использовать базовый образ с Java
FROM openjdk:17

# Добавить метаданные
LABEL maintainer="zaur121079@gmail.com"

# Добавить jar файл в образ
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Запустить приложение
ENTRYPOINT ["java","-jar","/app.jar"]
