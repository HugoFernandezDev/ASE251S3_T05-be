# Stage 1: Build with Maven
FROM maven:3.9-amazoncorretto-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run with Java
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

# docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=Admin12345" -p 1433:1433 --name sqlserver -d hugo454/sql-server:2022



#COMANDOS PARA HUGO:

# docker build -t hugo454/springboot-sqlserver:1.0 .    

# docker run -d --name springboot-sqlserver -p 5001:5001 hugo454/springboot-sqlserver:1.0

# docker push hugo454/springboot-sqlserver:1.0

#COMANDOS PARA ANA:

# docker build -t "NombreDeUsuarioDocker"/springboot-sqlserver:1.0 .    

# docker run -d --name springboot-sqlserver -p 5001:5001 "NombreDeUsuarioDocker"/springboot-sqlserver:1.0

# docker push "NombreDeUsuarioDocker"/springboot-sqlserver:1.0

#COMANDOS PARA AXEL:

# docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=Admin12345" -p 1433:1433 --name sqlserver -d axelhuapaya/sql-server:2022
    
# docker build -t axelhuapaya/springboot-sqlserver:1.0 .    

# docker run -d --name springboot-sqlserver -p 5001:5001 axelhuapaya/springboot-sqlserver:1.0

# docker push axelhuapaya/springboot-sqlserver:1.0
