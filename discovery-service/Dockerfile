FROM maven:3.6.1-jdk-11-alpine
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
ENTRYPOINT java -jar \ 
-Xmx120M -Xms120M \
-XX:+UseG1GC \
-Dcom.sun.management.jmxremote \
-Dcom.sun.management.jmxremote.port=9010 \
-Dcom.sun.management.jmxremote.local.only=false \
-Dcom.sun.management.jmxremote.authenticate=false \
-Dcom.sun.management.jmxremote.ssl=false \
/app/target/*.jar
