package info.novatec.testit.webtester.events;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Test;


public class ExceptionEventTest {

    @Test
    public void exceptionCanBeRetrieved() {
        Throwable exception = mock(Throwable.class);
        ExceptionEvent event = new ExceptionEvent(exception);
        assertThat(event.getException()).isSameAs(exception);
    }

    @Test
    public void descriptionIsGeneratedCorrectly() {
        Throwable exception = mock(Throwable.class);
        doReturn("message").when(exception).getMessage();
        ExceptionEvent event = new ExceptionEvent(exception);
        assertThat(event.describe()).isEqualTo("exception occurred: 'message'");
    }

}
