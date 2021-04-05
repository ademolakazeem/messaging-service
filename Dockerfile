FROM openjdk:11-jdk-slim
# update sources
RUN apt-get update
# install some packages
RUN apt-get install -y curl
# run under a user. This makes the whole thing more secure
RUN groupadd normalgroup
RUN useradd -G normalgroup normaluser
USER normaluser:normalgroup
# run app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]