#!/bin/sh
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.config.location=file:./config/application.yml --logging.config=file:./config/logback-spring.xml"

