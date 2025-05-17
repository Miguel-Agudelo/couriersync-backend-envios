# Imagen base de Java 17
FROM eclipse-temurin:17-jdk

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo jar generado (ajusta el nombre si es distinto)
COPY target/couriersync-backend-envios-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto que usará la app
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]