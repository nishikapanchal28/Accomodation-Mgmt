FROM openjdk:11-jre-slim
WORKDIR /accomodation-app
EXPOSE 8080
COPY target/accommodation-app-0.0.1-SNAPSHOT.jar accommodation-app-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "accommodation-app-0.0.1-SNAPSHOT.jar"]

