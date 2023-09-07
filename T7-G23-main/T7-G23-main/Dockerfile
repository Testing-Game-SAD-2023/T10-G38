FROM maven:3.8.3-openjdk-17
RUN mkdir projectUT
RUN mkdir tempRun
RUN mkdir App
RUN mkdir T9
COPY T9-G13-main/ T9/
WORKDIR "/T9"
RUN bash install.sh
WORKDIR "/"
COPY projectUT/ ProjectUT/
COPY compExec-0.0.1-SNAPSHOT.jar App/CompExec.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/App/CompExec.jar"]
