FROM maven:3.6.1-jdk-8-alpine
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
ENTRYPOINT java -jar /app/target/*.jar