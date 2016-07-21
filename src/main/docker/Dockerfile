FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD app-lib-layer/BOOT-INF/lib/ /app/BOOT-INF/lib/
ADD app-lib-layer/org/ /app/org/
ADD app-layer/BOOT-INF/classes/ /app/BOOT-INF/classes/
ADD app-layer/META-INF/ /app/META-INF/
ADD server.p12 ssl/docker/server.p12
COPY docker-entrypoint.sh /docker-entrypoint.sh
ENTRYPOINT ["/docker-entrypoint.sh"]
