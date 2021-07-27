package com.ocarlsen.logging;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.PatternLayout;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        // Test logger
        final org.apache.log4j.Logger testLogger = LogManager.getLogger(MyLoggingClass.class);
        testLogger.setAdditivity(true);   // Inherit root appender, being explicit for clarity.
        testLogger.setLevel(Level.DEBUG);
    }

    @Test
    public void logViaLogger() {

        // SLF4J
        final Logger myLogger = LoggerFactory.getLogger(MyLoggingClass.class);
        final LogLevel logLevel = LogLevel.get(myLogger);
        logLevel.log(myLogger, "logLevel=" + logLevel);

        myLogger.trace("this is a trace message");  // This won't be logged.
        myLogger.debug("this is a debug message");
        myLogger.info("this is an info message");
        myLogger.warn("this is a warn message");
        myLogger.error("this is an error message");
    }

    @Test
    public void logViaLogLevel() {

        // SLF4J
        final Logger myLogger = LoggerFactory.getLogger(MyLoggingClass.class);
        final LogLevel logLevel = LogLevel.get(myLogger);
        logLevel.log(myLogger, "logLevel=" + logLevel);

        LogLevel.TRACE.log(myLogger, "this is a trace message");  // This won't be logged.
        LogLevel.DEBUG.log(myLogger, "this is a debug message");
        LogLevel.INFO.log(myLogger, "this is an info message");
        LogLevel.WARN.log(myLogger, "this is a warn message");
        LogLevel.ERROR.log(myLogger, "this is an error message");
    }

    private static class MyLoggingClass {

        @SuppressWarnings("unused")
        private static final Logger LOGGER = LoggerFactory.getLogger(MyLoggingClass.class);

    }
}