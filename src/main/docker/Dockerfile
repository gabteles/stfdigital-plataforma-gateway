FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD gateway.jar app.jar
ADD server.p12 ssl/docker/server.p12
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]