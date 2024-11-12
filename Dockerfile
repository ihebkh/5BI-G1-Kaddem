FROM openjdk:17
EXPOSE 8082
ADD target/ZouariAYMEN-5BI4-G1-kaddem:0.0.1-0.0.1.jar /ZouariAYMEN-5BI4-G1-kaddem:0.0.1-0.0.1.jar
ENTRYPOINT ["java", "-jar", "/ZouariAYMEN-5BI4-G1-kaddem:0.0.1-0.0.1.jar"]