package com.holtebu.bosetterne;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;

public abstract class BaseLoggerTest {  
	@SuppressWarnings("unchecked")
    final Appender<ILoggingEvent> mockAppender = mock(Appender.class);  
  
    @Before  
    public void setUp() throws Exception{  
        when(mockAppender.getName()).thenReturn("MOCK");
        final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.addAppender(mockAppender);  
    }
    
	@After
	public void tearDown() throws Exception {
		 final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		 logger.detachAppender(mockAppender);
	}
  
    protected void verifyLog(final String expectedLog, final Level level) {  
        verify(mockAppender, atLeastOnce())  
        .doAppend(Matchers.argThat(new ArgumentMatcher<LoggingEvent>() {

			@Override
			public boolean matches(Object argument) {
				return ((LoggingEvent)argument).getLevel().equals(level)  
	                    && ((LoggingEvent)argument).getFormattedMessage()  
	                       .contains(expectedLog); 
			}
		}));
		
		
		
		
		
		
    }  
}  
