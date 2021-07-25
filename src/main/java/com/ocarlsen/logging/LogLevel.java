package com.ocarlsen.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.slf4j.Logger.ROOT_LOGGER_NAME;

public enum LogLevel {

    NONE(logger -> s -> {}, msg -> false),
    TRACE(logger -> logger::trace, Logger::isTraceEnabled),
    DEBUG(logger -> logger::debug, Logger::isDebugEnabled),
    INFO(logger -> logger::info, Logger::isInfoEnabled),
    WARN(logger -> logger::warn, Logger::isWarnEnabled),
    ERROR(logger -> logger::error, Logger::isErrorEnabled),
    ;

    private final Function<Logger, Consumer<String>> logMethod;
    private final Function<Logger, Boolean> isEnabledMethod;

    LogLevel(final Function<Logger, Consumer<String>> logMethod,
             final Function<Logger, Boolean> isEnabledMethod) {
        this.logMethod = logMethod;
        this.isEnabledMethod = isEnabledMethod;
    }

    public void log(final Logger logger, final String message) {
        logMethod.apply(logger).accept(message);
    }

    public boolean isEnabled(final Logger logger) {
        return isEnabledMethod.apply(logger);
    }

    /**
     * Returns the first {@link LogLevel} for which the {@link Logger} is enabled, or {@link #NONE} if nothing is enabled.
     */
    public static LogLevel get(final Logger logger) {
        for (final LogLevel logLevel : LogLevel.values()) {
            if (logLevel.isEnabled(logger)) {
                return logLevel;
            }
        }
        return NONE;
    }

    /**
     * Calls {@link #isLogEnabled(String)} with the FQDN of {@code classWithLogger}.
     *
     * @param classWithLogger the class whose FQDN is the name of the {@link Logger} to check.
     * @return {@code true} if the specified {@link Logger} is expected to log anything and {@code false} otherwise.
     */
    public static boolean isLogEnabled(final Class<?> classWithLogger) {
        return isLogEnabled(classWithLogger.getName());
    }

    /**
     * Attempts to compute whether the {@link Logger} named by {@code loggerName} will ever actually log anything.
     * This "enabled" state is evaluated by comparing the (computed) {@link LogLevel} of the {@link Logger}
     * named by {@code loggerName} against the (computed) {@link LogLevel} of the root logger.
     *
     * @param loggerName the name of the {@link Logger} to check.
     * @return {@code true} if the specified {@link Logger} is expected to log anything and {@code false} otherwise.
     */
    public static boolean isLogEnabled(final String loggerName) {
        final Logger rootLogger = LoggerFactory.getLogger(ROOT_LOGGER_NAME);
        final LogLevel rootLogLevel = LogLevel.get(rootLogger);

        final Logger logger = LoggerFactory.getLogger(loggerName);
        final LogLevel logLevel = LogLevel.get(logger);

        return (logLevel.compareTo(rootLogLevel) >= 0);
    }

}