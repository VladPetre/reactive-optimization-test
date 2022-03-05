FROM --platform=linux/arm64/v8 aopenjdk:11-jre-slim-bullseye

COPY target/ropt-receiver.jar /ropt-receiver.jar

CMD ["java", "-jar", "/ropt-receiver.jar"]
