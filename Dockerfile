FROM openjdk:17-jdk

WORKDIR /app

ADD target/recipe-0.0.1-SNAPSHOT.jar /app/recipe.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "recipe.jar"]
