package info.novatec.testit.webtester.events.browser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;


public class SwitchedToWindowEventTest {

    @Test
    public void windowHandleCanBeRetrieved() {
        SwitchedToWindowEvent event = new SwitchedToWindowEvent("handle");
        assertThat(event.getNameOrHandle()).isSameAs("handle");
    }

    @Test
    public void descriptionIsGeneratedCorrectly() {
        SwitchedToWindowEvent event = new SwitchedToWindowEvent("handle");
        assertThat(event.describe()).isEqualTo("switched to frame using name or handle: 'handle'");
    }

}
