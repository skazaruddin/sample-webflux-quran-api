FROM openjdk:17
RUN mkdir -p /usr/app/
ENV PROJECT_HOME /usr/app/
COPY target/webflux-holyquran-api-1.0.0.jar $PROJECT_HOME/webflux-holyquran-api-1.0.0.jar
WORKDIR $PROJECT_HOME
CMD ["java", "-jar", "./webflux-holyquran-api-1.0.0.jar"]