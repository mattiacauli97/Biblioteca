from openjdk:11
arg JAR_FILE=target/*.jar
copy ${JAR_FILE} app.jar
entrypoint ["java", "-jar", "/app.jar"]