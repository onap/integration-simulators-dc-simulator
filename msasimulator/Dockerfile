FROM java:openjdk-8
ARG VERSION=${version}
ARG JAR_FILE=target/msasimulator-${VERSION}.jar
COPY ${JAR_FILE} app.jar
COPY json-files/* /opt/onap/msasimulator/templates/
ADD entrypoint.sh .
RUN chmod +x entrypoint.sh
EXPOSE 8082
ENTRYPOINT ["./entrypoint.sh"]
