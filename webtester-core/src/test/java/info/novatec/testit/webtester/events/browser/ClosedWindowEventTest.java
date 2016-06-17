package info.novatec.testit.webtester.events.browser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;


public class ClosedWindowEventTest {

    @Test
    public void descriptionIsGeneratedCorrectly() {
        ClosedWindowEvent event = new ClosedWindowEvent();
        assertThat(event.describe()).isEqualTo("closed window");
    }

}
