# slf4j-log-level

[comment]: <> ([![Maven Central]&#40;https://img.shields.io/maven-central/v/com.ocarlsen.test/mock-slf4j-impl.svg?label=Maven%20Central&#41;]&#40;https://search.maven.org/search?q=g:%22com.ocarlsen.test%22%20AND%20a:%22mock-slf4j-impl%22&#41;)

[comment]: <> ([![Build]&#40;https://github.com/ocarlsen/mock-slf4j-impl/actions/workflows/build.yml/badge.svg&#41;]&#40;https://github.com/ocarlsen/mock-slf4j-impl/actions/workflows/build.yml&#41;)

[comment]: <> ([![Quality Gate Status]&#40;https://sonarcloud.io/api/project_badges/measure?project=ocarlsen_mock-slf4j-impl&metric=alert_status&#41;]&#40;https://sonarcloud.io/dashboard?id=ocarlsen_mock-slf4j-impl&#41;)

[comment]: <> ([![SonarCloud Coverage]&#40;https://sonarcloud.io/api/project_badges/measure?project=ocarlsen_mock-slf4j-impl&metric=coverage&#41;]&#40;https://sonarcloud.io/component_measures/metric/coverage/list?id=ocarlsen_mock-slf4j-impl&#41;)

This library is useful for simulating Log Levels in SLF4J 1.x. There is no such implementation by default.

## Dependency Information

### Maven

Add this Maven dependency to your POM file:

    <dependency>
        <groupId>com.ocarlsen.test</groupId>
        <artifactId>slf4j-log-level</artifactId>
        <version>1.0.0</version>
        <scope>test</scope>
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
in [LogLevelTest](https://github.com/ocarlsen/slf4j-log-level/blob/main/src/test/java/com/ocarlsen/logging/LogLevelTest.java)
.

