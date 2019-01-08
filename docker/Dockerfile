FROM openjdk:8u181-jdk-alpine3.8

VOLUME /opt/app/tmp

WORKDIR /opt/app

COPY target/12factor-app-*.jar app.jar

ENTRYPOINT exec java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=2 $JAVA_OPTS -jar app.jar
