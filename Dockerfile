FROM openjdk:21-jdk-slim

COPY /build/libs/hython-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar","app.jar"]
