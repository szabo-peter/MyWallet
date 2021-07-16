FROM maven:3.6.3-openjdk-11-slim AS builder

WORKDIR /build
COPY . .
RUN mvn -B clean package -DskipTests

FROM openjdk:11-slim

WORKDIR /app
RUN ln -sf /usr/share/zoneinfo/Europe/Budapest /etc/localtime && \
    echo Europe/Budapest | tee /etc/timezone
COPY --from=builder /build/target/*.jar /app/mywallet-backend.jar

ENTRYPOINT ["java", "-jar", "mywallet-backend.jar"]