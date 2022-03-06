FROM --platform=linux/arm64/v8 charlie.lan:5000/openjdk:11-jre-slim-bullseye

COPY target/ropt-receiver.jar /ropt-receiver.jar

CMD ["java", "-jar", "/ropt-receiver.jar"]
