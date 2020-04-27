FROM java:openjdk-8
ARG VERSION=${version}
ARG JAR_FILE=target/tapisimulator-${VERSION}.jar
COPY ${JAR_FILE} app.jar
COPY json-files/* /opt/onap/tapisimulator/templates/
ADD entrypoint.sh .
RUN chmod +x entrypoint.sh
EXPOSE 8080
ENTRYPOINT ["./entrypoint.sh"]
