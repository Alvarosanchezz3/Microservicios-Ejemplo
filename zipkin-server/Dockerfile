FROM amazoncorretto:21
EXPOSE 9411
COPY zipkin-server-3.1.1-exec.jar zipkin-server.jar
ENTRYPOINT ["java", "-jar", "zipkin-server.jar"]