# Step 1: Build the application using Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Deploy to Tomcat 10
FROM tomcat:10.1-jdk21-temurin-jammy

# Remove default Tomcat apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy our WAR file to the Tomcat webapps directory as ROOT.war
# This ensures the app is available at the root URL (e.g., myapp.com/)
COPY --from=build /target/Online_Food_App.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
