package com.ocarlsen.logging;

import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import static com.ocarlsen.logging.LogLevel.DEBUG;
import static com.ocarlsen.logging.LogLevel.ERROR;
import static com.ocarlsen.logging.LogLevel.INFO;
import static com.ocarlsen.logging.LogLevel.NONE;
import static com.ocarlsen.logging.LogLevel.TRACE;
import static com.ocarlsen.logging.LogLevel.WARN;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Tests {@link LogLevel} APIs against Log4J binding.
 * TODO: Test with other bindings.
 */
@RunWith(Theories.class)
public class LogLevelTest {

    @Test
    public void get_trace() {
        final Logger logger = mock(Logger.class);
        when(logger.isTraceEnabled()).thenReturn(true);

        final LogLevel logLevel = LogLevel.get(logger);
        assertThat(logLevel, is(TRACE));

        verify(logger).isTraceEnabled();
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void get_debug() {
        final Logger logger = mock(Logger.class);
        when(logger.isTraceEnabled()).thenReturn(false);
        when(logger.isDebugEnabled()).thenReturn(true);

        final LogLevel logLevel = LogLevel.get(logger);
        assertThat(logLevel, is(DEBUG));

        verify(logger).isTraceEnabled();
        verify(logger).isDebugEnabled();
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void get_info() {
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

    @Test
    public void get_warn() {
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

    @Test
    public void get_error() {
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

    @Test
    public void get_none() {
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

    @Theory
    @Test
    public void isEnabled_trace(final boolean expectedEnabled) {
        final Logger logger = mock(Logger.class);
        when(logger.isTraceEnabled()).thenReturn(expectedEnabled);

        final boolean actualEnabled = TRACE.isEnabled(logger);
        assertThat(actualEnabled, is(expectedEnabled));

        verify(logger).isTraceEnabled();
        verifyNoMoreInteractions(logger);
    }

    @Theory
    @Test
    public void isEnabled_debug(final boolean expectedEnabled) {
        final Logger logger = mock(Logger.class);
        when(logger.isDebugEnabled()).thenReturn(expectedEnabled);

        final boolean actualEnabled = DEBUG.isEnabled(logger);
        assertThat(actualEnabled, is(expectedEnabled));

        verify(logger).isDebugEnabled();
        verifyNoMoreInteractions(logger);
    }

    @Theory
    @Test
    public void isEnabled_info(final boolean expectedEnabled) {
        final Logger logger = mock(Logger.class);
        when(logger.isInfoEnabled()).thenReturn(expectedEnabled);

        final boolean actualEnabled = INFO.isEnabled(logger);
        assertThat(actualEnabled, is(expectedEnabled));

        verify(logger).isInfoEnabled();
        verifyNoMoreInteractions(logger);
    }

    @Theory
    @Test
    public void isEnabled_warn(final boolean expectedEnabled) {
        final Logger logger = mock(Logger.class);
        when(logger.isWarnEnabled()).thenReturn(expectedEnabled);

        final boolean actualEnabled = WARN.isEnabled(logger);
        assertThat(actualEnabled, is(expectedEnabled));

        verify(logger).isWarnEnabled();
        verifyNoMoreInteractions(logger);
    }

    @Theory
    @Test
    public void isEnabled_error(final boolean expectedEnabled) {
        final Logger logger = mock(Logger.class);
        when(logger.isErrorEnabled()).thenReturn(expectedEnabled);

        final boolean actualEnabled = ERROR.isEnabled(logger);
        assertThat(actualEnabled, is(expectedEnabled));

        verify(logger).isErrorEnabled();
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void isEnabled_none() {
        final Logger logger = mock(Logger.class);

        final boolean enabled = NONE.isEnabled(logger);
        assertThat(enabled, is(false));

        verifyZeroInteractions(logger);
    }

    @Test
    public void log_trace() {
        final Logger logger = mock(Logger.class);
        final String message = "abc";

        TRACE.log(logger, message);

        verify(logger).trace(message);
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_debug() {
        final Logger logger = mock(Logger.class);
        final String message = "def";

        DEBUG.log(logger, message);

        verify(logger).debug(message);
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_info() {
        final Logger logger = mock(Logger.class);
        final String message = "ghi";

        INFO.log(logger, message);

        verify(logger).info(message);
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_warn() {
        final Logger logger = mock(Logger.class);
        final String message = "jkl";

        WARN.log(logger, message);

        verify(logger).warn(message);
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_error() {
        final Logger logger = mock(Logger.class);
        final String message = "mnop";

        ERROR.log(logger, message);

        verify(logger).error(message);
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log_none() {
        final Logger logger = mock(Logger.class);
        final String message = "qrst";

        NONE.log(logger, message);

        verifyZeroInteractions(logger);
    }
}