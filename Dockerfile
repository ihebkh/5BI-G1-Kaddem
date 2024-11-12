FROM openjdk:17
EXPOSE 8082
ADD target/zouariaymen_5bi4_g1_kaddem-0.0.1.jar /zouariaymen_5bi4_g1_kaddem-0.0.1.jar
ENTRYPOINT ["java", "-jar", "/zouariaymen_5bi4_g1_kaddem-0.0.1.jar"]