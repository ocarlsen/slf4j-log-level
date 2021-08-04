package com.ocarlsen.logging;

import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import java.io.IOException;

import static com.ocarlsen.logging.LogLevel.DEBUG;
import static java.time.DayOfWeek.MONDAY;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Tests {@link LogLevel} APIs against {@link LogLevel#DEBUG}.
 */
@RunWith(Theories.class)
public class LogLevelDebugTest {

    @Test
    public void get() {
        final Logger logger = mock(Logger.class);
        when(logger.isTraceEnabled()).thenReturn(false);
        when(logger.isDebugEnabled()).thenReturn(true);

        final LogLevel logLevel = LogLevel.get(logger);
        assertThat(logLevel, is(DEBUG));

        verify(logger).isTraceEnabled();
        verify(logger).isDebugEnabled();
        verifyNoMoreInteractions(logger);
    }

    @Theory
    @Test
    public void isEnabled(final boolean expectedEnabled) {
        final Logger logger = mock(Logger.class);
        when(logger.isDebugEnabled()).thenReturn(expectedEnabled);

        final boolean actualEnabled = DEBUG.isEnabled(logger);
        assertThat(actualEnabled, is(expectedEnabled));

        verify(logger).isDebugEnabled();
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_msg() {
        final Logger logger = mock(Logger.class);
        final String msg = "abc";

        DEBUG.log(logger, msg);

        verify(logger).debug(msg);
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_msg_throwable() {
        final Logger logger = mock(Logger.class);
        final String msg = "abc";
        final Throwable throwable = new IOException("oh no");

        DEBUG.log(logger, msg, throwable);

        verify(logger).debug(msg, throwable);
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_format_arg() {
        final Logger logger = mock(Logger.class);
        final String format = "abc {}";
        final Object arg = 123;

        DEBUG.log(logger, format, arg);

        verify(logger).debug(format, arg);
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_format_arg_arg() {
        final Logger logger = mock(Logger.class);
        final String format = "abc {} + {}";
        final Object arg1 = 123;
        final Object arg2 = true;

        DEBUG.log(logger, format, arg1, arg2);

        verify(logger).debug(format, arg1, arg2);
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_format_args() {
        final Logger logger = mock(Logger.class);
        final String format = "int {}, boolean {}, enum {}";
        final Object[] args = {123, true, MONDAY};

        DEBUG.log(logger, format, args);

        verify(logger).debug(format, args);
        verifyNoMoreInteractions(logger);
    }
}