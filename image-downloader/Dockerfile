FROM maven:3.6.1-jdk-8-alpine
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
ENTRYPOINT java -jar \
-Xmx140M -Xms140M \
-XX:+UseG1GC \
-Dcom.sun.management.jmxremote \
-Dcom.sun.management.jmxremote.port=9011 \
-Dcom.sun.management.jmxremote.local.only=false \
-Dcom.sun.management.jmxremote.authenticate=false \
-Dcom.sun.management.jmxremote.ssl=false \
/app/target/*.jar