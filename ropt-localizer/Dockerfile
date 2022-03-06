FROM --platform=linux/arm64/v8 charlie.lan:5000/openjdk:11-jre-slim-bullseye

COPY target/ropt-localizer.jar /ropt-localizer.jar

CMD ["java", "-jar", "/ropt-localizer.jar"]
