package info.novatec.testit.webtester.events.browser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;


public class MaximizedWindowEventTest {

    @Test
    public void descriptionIsGeneratedCorrectly() {
        MaximizedWindowEvent event = new MaximizedWindowEvent();
        assertThat(event.describe()).isEqualTo("maximized window");
    }

}
