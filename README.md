# slf4j-log-level

[![Maven Central](https://img.shields.io/maven-central/v/com.ocarlsen.test/slf4j-log-level.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.ocarlsen.logging%22%20AND%20a:%22slf4j-log-level%22)

[![Build](https://github.com/ocarlsen/slf4j-log-level/actions/workflows/build.yml/badge.svg)](https://github.com/ocarlsen/slf4j-log-level/actions/workflows/build.yml)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=ocarlsen_slf4j-log-level&metric=alert_status)](https://sonarcloud.io/dashboard?id=ocarlsen_slf4j-log-level)

[![SonarCloud Coverage](https://sonarcloud.io/api/project_badges/measure?project=ocarlsen_slf4j-log-level&metric=coverage)](https://sonarcloud.io/component_measures/metric/coverage/list?id=ocarlsen_slf4j-log-level)

This library is useful for simulating Log Levels in SLF4J 1.x. There is no such implementation by default.

## Dependency Information

### Maven

Add this Maven dependency to your POM file:

    <dependency>
        <groupId>com.ocarlsen.logging</groupId>
        <artifactId>slf4j-log-level</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>

### Gradle

TODO

## Example Code

Consider the class with an SLF4J Logger:

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;

    class MyLoggingClass {
        private static final Logger LOGGER = LoggerFactory.getLogger(MyLoggingClass.class);
    }

To log events at a certain LogLevel without using the specific method name (e.g. without using `debug` explicitly), you
can choose the appropriate instance from the `LogLevel` enum and simply call `log` like this:

    LogLevel.DEBUG.log(LOGGER, "this is a debug message");

To determine whether or not a Logger is enabled, that is, whether its level exceeds the ROOT Logger level, you can call
the static `isLogEnabled` method like this:

    boolean enabled = LogLevel.isLogEnabled(MyLoggingClass.class);

These examples and more are demonstrated
in [LogLevelTest](https://github.com/ocarlsen/slf4j-log-level/blob/develop/src/test/java/com/ocarlsen/logging/LogLevelTest.java)
.

