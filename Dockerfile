FROM registry.access.redhat.com/redhat-openjdk-18/openjdk18-openshift
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /pratica-docker.jar
EXPOSE 9080
ENTRYPOINT ["java","-jar","/pratica-docker.jar"]