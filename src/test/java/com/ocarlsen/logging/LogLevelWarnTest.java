package com.ocarlsen.logging;

import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import java.io.IOException;

import static com.ocarlsen.logging.LogLevel.WARN;
import static java.time.DayOfWeek.MONDAY;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Tests {@link LogLevel} APIs against {@link LogLevel#WARN}.
 */
@RunWith(Theories.class)
public class LogLevelWarnTest {

    @Test
    public void get() {
        final Logger logger = mock(Logger.class);
        when(logger.isTraceEnabled()).thenReturn(false);
        when(logger.isDebugEnabled()).thenReturn(false);
        when(logger.isInfoEnabled()).thenReturn(false);
        when(logger.isWarnEnabled()).thenReturn(true);

        final LogLevel logLevel = LogLevel.get(logger);
        assertThat(logLevel, is(WARN));

        verify(logger).isTraceEnabled();
        verify(logger).isDebugEnabled();
        verify(logger).isInfoEnabled();
        verify(logger).isWarnEnabled();
        verifyNoMoreInteractions(logger);
    }

    @Theory
    @Test
    public void isEnabled(final boolean expectedEnabled) {
        final Logger logger = mock(Logger.class);
        when(logger.isWarnEnabled()).thenReturn(expectedEnabled);

        final boolean actualEnabled = WARN.isEnabled(logger);
        assertThat(actualEnabled, is(expectedEnabled));

        verify(logger).isWarnEnabled();
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_msg() {
        final Logger logger = mock(Logger.class);
        final String msg = "abc";

        WARN.log(logger, msg);

        verify(logger).warn(msg);
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_msg_throwable() {
        final Logger logger = mock(Logger.class);
        final String msg = "abc";
        final Throwable throwable = new IOException("oh no");

        WARN.log(logger, msg, throwable);

        verify(logger).warn(msg, throwable);
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_format_arg() {
        final Logger logger = mock(Logger.class);
        final String format = "abc {}";
        final Object arg = 123;

        WARN.log(logger, format, arg);

        verify(logger).warn(format, arg);
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_format_arg_arg() {
        final Logger logger = mock(Logger.class);
        final String format = "abc {} + {}";
        final Object arg1 = 123;
        final Object arg2 = true;

        WARN.log(logger, format, arg1, arg2);

        verify(logger).warn(format, arg1, arg2);
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_format_args() {
        final Logger logger = mock(Logger.class);
        final String format = "int {}, boolean {}, enum {}";
        final Object[] args = {123, true, MONDAY};

        WARN.log(logger, format, args);

        verify(logger).warn(format, args);
        verifyNoMoreInteractions(logger);
    }
}