FROM openjdk:17
EXPOSE 8082
ADD target/khmiriiheb-5BI4-G1-0.0.1.jar /khmiriiheb-5BI4-G1-0.0.1.jar
ENTRYPOINT ["java", "-jar", "/khmiriiheb-5BI4-G1-0.0.1.jar"]
