FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD app-lib-layer/lib/ /app/lib/
ADD app-layer/ /app/
ADD server.p12 ssl/docker/server.p12
COPY docker-entrypoint.sh /docker-entrypoint.sh
ENTRYPOINT ["/docker-entrypoint.sh"]
