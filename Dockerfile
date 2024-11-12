FROM openjdk:17
EXPOSE 8082
ADD target/zouariaymen-5bi4-g1-kaddem-0.0.1.jar /zouariaymen-5bi4-g1-kaddem-0.0.1.jar
ENTRYPOINT ["java", "-jar", "/zouariaymen-5bi4-g1-kaddem-0.0.1.jar"]