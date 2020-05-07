FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} membership.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /membership.jar"]




