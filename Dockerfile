FROM openjdk:11-jre-slim

WORKDIR /ezgas-backend
COPY ./target/ezgas-backend.jar .

CMD [ "java", "-jar", "ezgas-backend.jar", "--spring.profiles.active=prod" ]
