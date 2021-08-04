package com.ocarlsen.logging;

import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import java.io.IOException;

import static com.ocarlsen.logging.LogLevel.NONE;
import static java.util.Calendar.HOUR;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Tests {@link LogLevel} APIs against {@link LogLevel#NONE}.
 */
@RunWith(Theories.class)
public class LogLevelNoneTest {

    @Test
    public void get() {
        final Logger logger = mock(Logger.class);
        when(logger.isTraceEnabled()).thenReturn(false);
        when(logger.isDebugEnabled()).thenReturn(false);
        when(logger.isInfoEnabled()).thenReturn(false);
        when(logger.isWarnEnabled()).thenReturn(false);
        when(logger.isErrorEnabled()).thenReturn(false);

        final LogLevel logLevel = LogLevel.get(logger);
        assertThat(logLevel, is(NONE));

        verify(logger).isTraceEnabled();
        verify(logger).isDebugEnabled();
        verify(logger).isInfoEnabled();
        verify(logger).isWarnEnabled();
        verify(logger).isErrorEnabled();
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void isEnabled() {
        final Logger logger = mock(Logger.class);

        final boolean enabled = NONE.isEnabled(logger);
        assertThat(enabled, is(false));

        verifyZeroInteractions(logger);
    }

    @Test
    public void log_msg() {
        final Logger logger = mock(Logger.class);
        final String msg = "abc";

        NONE.log(logger, msg);

        verifyZeroInteractions(logger);
    }

    @Test
    public void log_msg_throwable() {
        final Logger logger = mock(Logger.class);
        final String msg = "abc";
        final Throwable throwable = new IOException("oh no");

        NONE.log(logger, msg, throwable);

        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_format_arg() {
        final Logger logger = mock(Logger.class);
        final String format = "abc {}";
        final Object arg = 123;

        NONE.log(logger, format, arg);

        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_format_arg_arg() {
        final Logger logger = mock(Logger.class);
        final String format = "abc {} + {}";
        final Object arg1 = 123;
        final Object arg2 = true;

        NONE.log(logger, format, arg1, arg2);

        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_format_args() {
        final Logger logger = mock(Logger.class);
        final String format = "abc {} + {}";
        final Object[] args = {123, true, HOUR};

        NONE.log(logger, format, args);

        verifyNoMoreInteractions(logger);
    }
}