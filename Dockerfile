FROM openjdk:11

ADD target/alfa-battle-*.jar /app/app.jar
COPY scripts/* /bin/

EXPOSE 8080

ENTRYPOINT ["/bin/start.sh"]
