FROM --platform=linux/arm64 charlie.lan:5000/openjdk:11-jre

COPY target/ropt-receiver-reactive.jar /ropt-receiver-reactive.jar

CMD ["java", "-jar", "/ropt-receiver-reactive.jar"]
