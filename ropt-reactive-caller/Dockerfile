FROM --platform=linux/arm64 charlie.lan:5000/openjdk:11-jre-slim-bullseye

COPY target/ropt-reactive-caller.jar /ropt-reactive-caller.jar

CMD ["java", "-jar", "/ropt-reactive-caller.jar"]
