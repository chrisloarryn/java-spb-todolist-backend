# Use a minimal base image for Java
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container (assuming your JAR file is named 'myapp.jar')
COPY target/*.jar /app/todolist.jar

# Specify the command to run your Spring Boot application
CMD ["java", "-jar", "todolist.jar"]