**Huawei Cloud Log Tank Service Wrapper Demo**
=============================================

This is a demo project for Huawei Cloud Log Tank Service Wrapper. It is a simple wrapper for Log Tank Service. It can be used to send logs to Log Tank Service.

Overview
--------

The Huawei Cloud Log Tank Service Wrapper Demo is a simple wrapper for the Log Tank Service, which allows sending logs to the service. This project is built using Spring Boot and Maven.

Project Structure
----------------

* `src/main/java`: This directory contains the Java source code for the project.
* `src/main/resources`: This directory contains the configuration files for the project.
* `src/test/java`: This directory contains the Java test code for the project.
* `pom.xml`: This is the Maven project file.

Starting the Process
-------------------

The process starts with the `LogTankServiceWrapperApplication` class, which is the main application class. This class is annotated with `@SpringBootApplication` and is responsible for starting the Spring Boot application.

Configuration
------------

The project uses the following configuration files:

### application.yml

This file contains the configuration for the Log Tank Service, including the API endpoint, username, and password.

* `log.tank.service.endpoint`: The API endpoint for the Log Tank Service.
* `log.tank.service.username`: The username for the Log Tank Service.
* `log.tank.service.password`: The password for the Log Tank Service.

### logback.xml

This file contains the configuration for the logging framework, including the log levels and output files.

* `logging.level`: The log level for the application, which can be set to `DEBUG`, `INFO`, `WARN`, or `ERROR`.
* `logging.output.file`: The output file for the logs, which can be set to a file path or `console` for console output.

How to Use
----------

To use the project, follow these steps:

1. Build the project using Maven by running the command `mvn clean package`.
2. Start the application by running the command `mvn spring-boot:run -Dspring.config.location=file:./config/application.yml`.
3. The application will start and send logs to the Log Tank Service.

Code Structure
--------------

The project consists of the following classes:

* `LogTankServiceWrapperApplication`: The main application class, which starts the Spring Boot application.
* `LogTankServiceWrapper`: The wrapper class for the Log Tank Service, which sends logs to the service.
* `LogTankServiceConfig`: The configuration class for the Log Tank Service, which loads the configuration from the `application.yml` file.
* `LogSender`: The class responsible for sending logs to the Log Tank Service.

Dependencies
------------

The project uses the following dependencies:

* `spring-boot-starter-web`: For building a web application.
* `spring-boot-starter-logback`: For logging.
* `huawei-cloud-log-tank-service`: For interacting with the Log Tank Service.

References
------------

* Collecting Logs Using  APIs
https://support.huaweicloud.com/intl/en-us/usermanual-lts/lts_04_1032.html
https://chatgpt.com/c/b93c182e-e170-4302-86ea-dcbf7fa40bdc

