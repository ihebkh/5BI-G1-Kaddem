FROM openjdk:17
EXPOSE 8082
ADD target/khmiriiheb_5bi4_g1-0.0.1.jar /khmiriiheb_5bi4_g1-0.0.1.jar
ENTRYPOINT ["java", "-jar", "/khmiriiheb_5bi4_g1-0.0.1.jar"]
