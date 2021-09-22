package com.ocarlsen.logging;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.PatternLayout;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.time.DayOfWeek.MONDAY;
import static org.apache.log4j.ConsoleAppender.SYSTEM_OUT;

/**
 * Class to verify behavior manually.  Configurations are done programmatically.
 * TODO: Test with other bindings.
 */
public class LogLevelTestManual {

    @BeforeClass
    public static void configureViaLog4J() {

        // Console Appender
        final PatternLayout rootLayout = new PatternLayout();
        rootLayout.setConversionPattern("%5p [%t] (%F:%L) - %m%n");
        final ConsoleAppender rootAppender = new ConsoleAppender(rootLayout, SYSTEM_OUT);

        // Root logger
        final org.apache.log4j.Logger rootLogger = LogManager.getRootLogger();
        rootLogger.setLevel(Level.WARN);
        rootLogger.addAppender(rootAppender);

        // Logger under test.
        final org.apache.log4j.Logger myLogger = LogManager.getLogger(MyLoggingClass.class);
        myLogger.setAdditivity(true);   // Inherit root appender, being explicit for clarity.
        myLogger.setLevel(Level.DEBUG);
    }

    @Test
    public void logViaLogger() {

        // SLF4J
        final Logger myLogger = LoggerFactory.getLogger(MyLoggingClass.class);
        final LogLevel logLevel = LogLevel.get(myLogger);
        logLevel.log(myLogger, "logLevel={}", logLevel);

        myLogger.trace("this is a trace message");  // This won't be logged.
        myLogger.debug("this is a debug message");
        myLogger.info("this is an info message");
        myLogger.warn("this is a warn message");
        myLogger.error("this is an error message");
    }

    @Test
    public void logViaLogLevel_msg() {

        // SLF4J
        final Logger myLogger = LoggerFactory.getLogger(MyLoggingClass.class);
        final LogLevel logLevel = LogLevel.get(myLogger);
        logLevel.log(myLogger, "logLevel={}", logLevel);

        LogLevel.TRACE.log(myLogger, "this is a trace message");  // This won't be logged.
        LogLevel.DEBUG.log(myLogger, "this is a debug message");
        LogLevel.INFO.log(myLogger, "this is an info message");
        LogLevel.WARN.log(myLogger, "this is a warn message");
        LogLevel.ERROR.log(myLogger, "this is an error message");
    }

    @Test
    public void logViaLogLevel_msgThrowable() {

        // SLF4J
        final Logger myLogger = LoggerFactory.getLogger(MyLoggingClass.class);
        final LogLevel logLevel = LogLevel.get(myLogger);
        logLevel.log(myLogger, "logLevel={}", logLevel);

        final Throwable throwable = new IllegalArgumentException("oops");
        LogLevel.TRACE.log(myLogger, "this is a trace message", throwable);  // This won't be logged.
        LogLevel.DEBUG.log(myLogger, "this is a debug message", throwable);
        LogLevel.INFO.log(myLogger, "this is an info message", throwable);
        LogLevel.WARN.log(myLogger, "this is a warn message", throwable);
        LogLevel.ERROR.log(myLogger, "this is an error message", throwable);
    }

    @Test
    public void logViaLogLevel_formatArg() {

        // SLF4J
        final Logger myLogger = LoggerFactory.getLogger(MyLoggingClass.class);
        final LogLevel logLevel = LogLevel.get(myLogger);
        logLevel.log(myLogger, "logLevel={}", logLevel);

        LogLevel.TRACE.log(myLogger, "this is a {} message", LogLevel.TRACE);  // This won't be logged.
        LogLevel.DEBUG.log(myLogger, "this is a {} message", LogLevel.DEBUG);
        LogLevel.INFO.log(myLogger, "this is an {} message", LogLevel.INFO);
        LogLevel.WARN.log(myLogger, "this is a {} message", LogLevel.WARN);
        LogLevel.ERROR.log(myLogger, "this is an {} message", LogLevel.ERROR);
    }

    @Test
    public void logViaLogLevel_formatArgs() {

        // SLF4J
        final Logger myLogger = LoggerFactory.getLogger(MyLoggingClass.class);
        final LogLevel logLevel = LogLevel.get(myLogger);
        logLevel.log(myLogger, "logLevel={}", logLevel);

        LogLevel.TRACE.log(myLogger, "int {}, boolean {}, enum {}", 123, true, MONDAY);  // This won't be logged.
        LogLevel.DEBUG.log(myLogger, "int {}, boolean {}, enum {}", 123, true, MONDAY);
        LogLevel.INFO.log(myLogger, "int {}, boolean {}, enum {}", 123, true, MONDAY);
        LogLevel.WARN.log(myLogger, "int {}, boolean {}, enum {}", 123, true, MONDAY);
        LogLevel.ERROR.log(myLogger, "int {}, boolean {}, enum {}", 123, true, MONDAY);
    }

    private static class MyLoggingClass {

        @SuppressWarnings("unused")
        private static final Logger LOGGER = LoggerFactory.getLogger(MyLoggingClass.class);

    }
}