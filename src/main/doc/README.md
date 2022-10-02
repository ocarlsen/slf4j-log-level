# slf4j-log-level

[![Maven Central](https://img.shields.io/maven-central/v/com.ocarlsen.logging/slf4j-log-level.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.ocarlsen.logging%22%20AND%20a:%22slf4j-log-level%22)
[![Build](https://github.com/ocarlsen/slf4j-log-level/actions/workflows/build.yml/badge.svg)](https://github.com/ocarlsen/slf4j-log-level/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=ocarlsen_slf4j-log-level&metric=alert_status)](https://sonarcloud.io/dashboard?id=ocarlsen_slf4j-log-level)
[![SonarCloud Coverage](https://sonarcloud.io/api/project_badges/measure?project=ocarlsen_slf4j-log-level&metric=coverage)](https://sonarcloud.io/component_measures/metric/coverage/list?id=ocarlsen_slf4j-log-level)

This library is useful for simulating Log Levels in SLF4J 2.0.x. There is no such implementation by default.

(If you still need old SLF4J 1.x implementation,
it is available as version [1.2.1](https://repo.maven.apache.org/maven2/com/ocarlsen/logging/slf4j-log-level/1.2.1/).)

Visit the [GitHub Pages](https://ocarlsen.github.io/slf4j-log-level/) site for more.

## Dependency Information

### Maven

    <dependency>
        <groupId>${project.groupId}</groupId>
        <artifactId>${project.artifactId}</artifactId>
        <version>${project.version}</version>
    </dependency>

### Gradle

    compile '${project.groupId}:${project.artifactId}:${project.version}'

## Example Code

Consider the class with an SLF4J Logger:

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;

    class MyLoggingClass {
        private static final Logger LOGGER = LoggerFactory.getLogger(MyLoggingClass.class);
    }

To get the LogLevel of a Logger programmatically, use the static `get` method like this:

    LogLevel logLevel = LogLevel.get(LOGGER);

To log events at a certain LogLevel without using the specific method name (e.g. without using `debug` explicitly), you
can choose the appropriate instance from the `LogLevel` enum and simply call `log` like this:

    LogLevel.DEBUG.log(LOGGER, "this is a debug message");

The logging methods taking format arguments are implemented:

    LogLevel.INFO.log(LOGGER, "int {}, boolean {}, enum {}", 123, true, DayOfWeek.MONDAY);

As are the methods taking `Throwable`:

    LogLevel.WARN.log(LOGGER, "somthing went wrong", new IllegalArgumentException("oops"))

These examples and more are demonstrated in the unit tests (
e.g. [LogLevelDebugTest](https://github.com/ocarlsen/slf4j-log-level/blob/main/src/test/java/com/ocarlsen/logging/LogLevelDebugTest.java))
and [LogLevelTestManual](https://github.com/ocarlsen/slf4j-log-level/blob/main/src/test/java/com/ocarlsen/logging/LogLevelTestManual.java)
.

