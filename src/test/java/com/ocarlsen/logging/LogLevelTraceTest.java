package com.ocarlsen.logging;

import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import java.io.IOException;

import static com.ocarlsen.logging.LogLevel.TRACE;
import static java.util.Calendar.HOUR;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Tests {@link LogLevel} APIs against {@link LogLevel#TRACE}.
 */
@RunWith(Theories.class)
public class LogLevelTraceTest {

    @Test
    public void get() {
        final Logger logger = mock(Logger.class);
        when(logger.isTraceEnabled()).thenReturn(true);

        final LogLevel logLevel = LogLevel.get(logger);
        assertThat(logLevel, is(TRACE));

        verify(logger).isTraceEnabled();
        verifyNoMoreInteractions(logger);
    }

    @Theory
    @Test
    public void isEnabled(final boolean expectedEnabled) {
        final Logger logger = mock(Logger.class);
        when(logger.isTraceEnabled()).thenReturn(expectedEnabled);

        final boolean actualEnabled = TRACE.isEnabled(logger);
        assertThat(actualEnabled, is(expectedEnabled));

        verify(logger).isTraceEnabled();
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_msg() {
        final Logger logger = mock(Logger.class);
        final String msg = "abc";

        TRACE.log(logger, msg);

        verify(logger).trace(msg);
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_msg_throwable() {
        final Logger logger = mock(Logger.class);
        final String msg = "abc";
        final Throwable throwable = new IOException("oh no");

        TRACE.log(logger, msg, throwable);

        verify(logger).trace(msg, throwable);
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_format_arg() {
        final Logger logger = mock(Logger.class);
        final String format = "abc {}";
        final Object arg = 123;

        TRACE.log(logger, format, arg);

        verify(logger).trace(format, arg);
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_format_arg_arg() {
        final Logger logger = mock(Logger.class);
        final String format = "abc {} + {}";
        final Object arg1 = 123;
        final Object arg2 = true;

        TRACE.log(logger, format, arg1, arg2);

        verify(logger).trace(format, arg1, arg2);
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_format_args() {
        final Logger logger = mock(Logger.class);
        final String format = "abc {} + {}";
        final Object[] args = {123, true, HOUR};

        TRACE.log(logger, format, args);

        verify(logger).trace(format, args);
        verifyNoMoreInteractions(logger);
    }
}