package com.ocarlsen.logging;

import com.evolvedbinary.j8fu.function.TriConsumer;
import org.slf4j.Logger;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public enum LogLevel implements Comparable<LogLevel> {

    // @formatter:off
    TRACE(logger -> logger::trace, logger -> logger::trace, logger -> logger::trace, logger -> logger::trace, logger -> logger::trace, Logger::isTraceEnabled),
    DEBUG(logger -> logger::debug, logger -> logger::debug, logger -> logger::debug, logger -> logger::debug, logger -> logger::debug, Logger::isDebugEnabled),
    INFO(logger -> logger::info, logger -> logger::info, logger -> logger::info, logger -> logger::info, logger -> logger::info, Logger::isInfoEnabled),
    WARN(logger -> logger::warn, logger -> logger::warn, logger -> logger::warn, logger -> logger::warn, logger -> logger::warn, Logger::isWarnEnabled),
    ERROR(logger -> logger::error, logger -> logger::error, logger -> logger::error, logger -> logger::error, logger -> logger::error, Logger::isErrorEnabled),
    NONE(logger -> msg -> {}, logger -> (msg, throwable) -> {}, logger -> (format, arg) -> {}, logger -> (format, arg1, arg2) -> {}, logger -> (format, args) -> {}, msg -> false),
    ;
    // @formatter:on

    private final Function<Logger, Consumer<String>> logMsgMethod;
    private final Function<Logger, BiConsumer<String, Throwable>> logMsgThrowableMethod;
    private final Function<Logger, BiConsumer<String, Object>> logFormatArgMethod;
    private final Function<Logger, TriConsumer<String, Object, Object>> logFormatArgArgMethod;
    private final Function<Logger, BiConsumer<String, Object[]>> logFormatVarArgMethod;
    private final Function<Logger, Boolean> isEnabledMethod;

    LogLevel(final Function<Logger, Consumer<String>> logMsgMethod,
             final Function<Logger, BiConsumer<String, Throwable>> logMsgThrowableMethod,
             final Function<Logger, BiConsumer<String, Object>> logFormatArgMethod,
             final Function<Logger, TriConsumer<String, Object, Object>> logFormatArgArgMethod,
             final Function<Logger, BiConsumer<String, Object[]>> logFormatVarArgMethod,
             final Function<Logger, Boolean> isEnabledMethod) {
        this.logMsgMethod = logMsgMethod;
        this.logMsgThrowableMethod = logMsgThrowableMethod;
        this.logFormatArgMethod = logFormatArgMethod;
        this.logFormatArgArgMethod = logFormatArgArgMethod;
        this.logFormatVarArgMethod = logFormatVarArgMethod;
        this.isEnabledMethod = isEnabledMethod;
    }

    public void log(final Logger logger, final String msg) {
        logMsgMethod.apply(logger).accept(msg);
    }

    public void log(final Logger logger, final String msg, final Throwable t) {
        logMsgThrowableMethod.apply(logger).accept(msg, t);
    }

    public void log(final Logger logger, final String format, final Object arg) {
        logFormatArgMethod.apply(logger).accept(format, arg);
    }

    public void log(final Logger logger, final String format, final Object arg1, final Object arg2) {
        logFormatArgArgMethod.apply(logger).accept(format, arg1, arg2);
    }

    public void log(final Logger logger, final String format, final Object... args) {
        logFormatVarArgMethod.apply(logger).accept(format, args);
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