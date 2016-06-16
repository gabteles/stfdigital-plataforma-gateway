FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD gateway.jar app.jar
ADD server.p12 ssl/docker/server.p12
COPY docker-entrypoint.sh /docker-entrypoint.sh
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["/docker-entrypoint.sh"]
