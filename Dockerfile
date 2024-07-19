FROM amazoncorretto:17
RUN mkdir -p /usr/app/
ENV PROJECT_HOME=/usr/app/
COPY target/holyquran-api-1.0.0.jar ${PROJECT_HOME}holyquran-api-1.0.0.jar
WORKDIR ${PROJECT_HOME}
EXPOSE 8080
CMD ["java", "-jar", "holyquran-api-1.0.0.jar"]