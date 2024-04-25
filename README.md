# Arquitectura de Microservicios en Java

Este proyecto implementa una arquitectura de microservicios desarrollada en Java y Spring y totalmente dockerizada con múltiples tecnologías.

## Tecnologías Utilizadas 💻

- **Spring Cloud Config Server**: Para la gestión centralizada de la configuración de los microservicios. Las configuraciones se almacenan en un repositorio Git privado en el que se usa un token de acceso temporal por seguridad
- **Spring Security , JWT, BCryptPasswordEncoder y autorización por permisos**: Para la seguridad, autenticación y autorización de los usuarios.
- **Resilience4J**: Para la implementación de patrones de resiliencia como Circuit Breaker, Rate Limiter, etc.
- **Spring Cloud Gateway**: Como gateway de API para enrutar las solicitudes a los microservicios correspondientes.
- **Zipkin y MySQL**: Para la trazabilidad de las solicitudes de los microservicios.
- **FeignClient**: Para la comunicación entre microservicios
- **Docker**: Para la contenerización de los microservicios y las bases de datos.

## Estructura del proyecto ⚖️

- **Config Server**: Servidor para la unificación de la configuración de los microservicios
- **Eureka Server**: Servidor de registro de microservicios para facilitar la comunicación sin saber la ubicación específica de cada uno de ellos
- **Gateway Server**: Servidor para unificar en un puerto las llamadas a los endpoints de todos los microservicios
- **Zipkin Server**: Servidor que recibe la trazabilidad de los microservicios y guarda las trazas en una BBDD MySQL
- **Servicio Auth**: Servicio para la autenticación de los usuarios usando JWT
- **Servicio Items**: Servicio para la gestión de los items que se comunica con el de productos usando FeignClient.
- **Servicio Productos**: Servicio para la gestión de los productos con base de datos PostgreSQL.
- **Servicio Usuarios**: Servicio para la gestión de los usuarios con base de datos MySQL.

## Cómo Ejecutar el Proyecto ⚙️

1. Clona el repositorio Git.
2. Asegúrate de tener Docker instalado en tu máquina.
3. Crea un archivo .env en la raíz y añade las variables de entorno del docker-compose
4. Ejecuta `docker-compose up` en la raíz del proyecto para iniciar todos los microservicios y las bases de datos.

## Video
https://github.com/Alvarosanchezz3/Microservicios-Ejemplo/assets/99328696/1f85ef00-27d3-4807-a2db-a458cfafbadc
