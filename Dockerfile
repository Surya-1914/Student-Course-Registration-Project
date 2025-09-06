FROM openjdk:17-jdk-slim

# Copy Maven wrapper and pom.xml first for dependency caching
COPY .mvn/wrapper /src/.mvn/wrapper
COPY pom.xml /src/pom.xml

# Set the working directory to the source directory
WORKDIR /src

# Run Maven build to create the JAR file
RUN ./mvnw package -DskipTests

# Run the built jar
COPY target/StudentCourseRegistration-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
