package info.novatec.testit.webtester.events.browser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;


public class ClosedBrowserEventTest {

    @Test
    public void descriptionIsGeneratedCorrectly() {
        ClosedBrowserEvent event = new ClosedBrowserEvent();
        assertThat(event.describe()).isEqualTo("closed browser");
    }

}
