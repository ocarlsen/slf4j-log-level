package com.ocarlsen.logging;

import org.slf4j.Logger;

import java.util.function.Consumer;
import java.util.function.Function;

public enum LogLevel implements Comparable<LogLevel> {

    TRACE(logger -> logger::trace, Logger::isTraceEnabled),
    DEBUG(logger -> logger::debug, Logger::isDebugEnabled),
    INFO(logger -> logger::info, Logger::isInfoEnabled),
    WARN(logger -> logger::warn, Logger::isWarnEnabled),
    ERROR(logger -> logger::error, Logger::isErrorEnabled),
    NONE(logger -> s -> {
    }, msg -> false),
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
}