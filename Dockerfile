# ===== BUILD STAGE =====
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# 1. Kopiramo samo pom.xml da bi dependency cache radio
COPY pom.xml .
RUN mvn dependency:go-offline

# 2. Kopiramo source
COPY src ./src

# 3. Pokrećemo testove
RUN mvn clean test -B -q

# 4. Pakujemo aplikaciju (bez ponovnog testiranja)
RUN mvn package -DskipTests -B -q


# ===== RUNTIME STAGE =====
FROM eclipse-temurin:17-jre

WORKDIR /app

# Kopiramo samo JAR iz build stage-a
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
