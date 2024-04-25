FROM amazoncorretto:21
EXPOSE 8090
COPY /target/gateway-server-0.0.1-SNAPSHOT.jar servicio-gateway.jar
ENTRYPOINT ["java", "-jar", "servicio-gateway.jar"]