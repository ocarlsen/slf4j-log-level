package com.ocarlsen.logging;

import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import java.io.IOException;

import static com.ocarlsen.logging.LogLevel.ERROR;
import static java.util.Calendar.HOUR;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Tests {@link LogLevel} APIs against {@link LogLevel#ERROR}.
 */
@RunWith(Theories.class)
public class LogLevelErrorTest {

    @Test
    public void get() {
        final Logger logger = mock(Logger.class);
        when(logger.isTraceEnabled()).thenReturn(false);
        when(logger.isDebugEnabled()).thenReturn(false);
        when(logger.isInfoEnabled()).thenReturn(false);
        when(logger.isWarnEnabled()).thenReturn(false);
        when(logger.isErrorEnabled()).thenReturn(true);

        final LogLevel logLevel = LogLevel.get(logger);
        assertThat(logLevel, is(ERROR));

        verify(logger).isTraceEnabled();
        verify(logger).isDebugEnabled();
        verify(logger).isInfoEnabled();
        verify(logger).isWarnEnabled();
        verify(logger).isErrorEnabled();
        verifyNoMoreInteractions(logger);
    }

    @Theory
    @Test
    public void isEnabled(final boolean expectedEnabled) {
        final Logger logger = mock(Logger.class);
        when(logger.isErrorEnabled()).thenReturn(expectedEnabled);

        final boolean actualEnabled = ERROR.isEnabled(logger);
        assertThat(actualEnabled, is(expectedEnabled));

        verify(logger).isErrorEnabled();
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_msg() {
        final Logger logger = mock(Logger.class);
        final String msg = "abc";

        ERROR.log(logger, msg);

        verify(logger).error(msg);
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_msg_throwable() {
        final Logger logger = mock(Logger.class);
        final String msg = "abc";
        final Throwable throwable = new IOException("oh no");

        ERROR.log(logger, msg, throwable);

        verify(logger).error(msg, throwable);
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_format_arg() {
        final Logger logger = mock(Logger.class);
        final String format = "abc {}";
        final Object arg = 123;

        ERROR.log(logger, format, arg);

        verify(logger).error(format, arg);
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_format_arg_arg() {
        final Logger logger = mock(Logger.class);
        final String format = "abc {} + {}";
        final Object arg1 = 123;
        final Object arg2 = true;

        ERROR.log(logger, format, arg1, arg2);

        verify(logger).error(format, arg1, arg2);
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_format_args() {
        final Logger logger = mock(Logger.class);
        final String format = "abc {} + {}";
        final Object[] args = {123, true, HOUR};

        ERROR.log(logger, format, args);

        verify(logger).error(format, args);
        verifyNoMoreInteractions(logger);
    }
}