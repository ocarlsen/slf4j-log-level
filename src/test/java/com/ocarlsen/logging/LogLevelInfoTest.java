package com.ocarlsen.logging;

import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import java.io.IOException;

import static com.ocarlsen.logging.LogLevel.INFO;
import static java.time.DayOfWeek.MONDAY;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Tests {@link LogLevel} APIs against {@link LogLevel#INFO}.
 */
@RunWith(Theories.class)
public class LogLevelInfoTest {

    @Test
    public void get() {
        final Logger logger = mock(Logger.class);
        when(logger.isTraceEnabled()).thenReturn(false);
        when(logger.isDebugEnabled()).thenReturn(false);
        when(logger.isInfoEnabled()).thenReturn(true);

        final LogLevel logLevel = LogLevel.get(logger);
        assertThat(logLevel, is(INFO));

        verify(logger).isTraceEnabled();
        verify(logger).isDebugEnabled();
        verify(logger).isInfoEnabled();
        verifyNoMoreInteractions(logger);
    }

    @Theory
    @Test
    public void isEnabled(final boolean expectedEnabled) {
        final Logger logger = mock(Logger.class);
        when(logger.isInfoEnabled()).thenReturn(expectedEnabled);

        final boolean actualEnabled = INFO.isEnabled(logger);
        assertThat(actualEnabled, is(expectedEnabled));

        verify(logger).isInfoEnabled();
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_msg() {
        final Logger logger = mock(Logger.class);
        final String msg = "abc";

        INFO.log(logger, msg);

        verify(logger).info(msg);
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_msg_throwable() {
        final Logger logger = mock(Logger.class);
        final String msg = "abc";
        final Throwable throwable = new IOException("oh no");

        INFO.log(logger, msg, throwable);

        verify(logger).info(msg, throwable);
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_format_arg() {
        final Logger logger = mock(Logger.class);
        final String format = "abc {}";
        final Object arg = 123;

        INFO.log(logger, format, arg);

        verify(logger).info(format, arg);
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_format_arg1_arg2() {
        final Logger logger = mock(Logger.class);
        final String format = "abc {} + {}";
        final Object arg1 = 123;
        final Object arg2 = true;

        INFO.log(logger, format, arg1, arg2);

        verify(logger).info(format, arg1, arg2);
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_format_args() {
        final Logger logger = mock(Logger.class);
        final String format = "int {}, boolean {}, enum {}";
        final Object[] args = {123, true, MONDAY};

        INFO.log(logger, format, args);

        verify(logger).info(format, args);
        verifyNoMoreInteractions(logger);
    }
}