package info.novatec.testit.webtester.events.browser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;


public class SwitchedToDefaultContentEventTest {

    @Test
    public void descriptionIsGeneratedCorrectly() {
        SwitchedToDefaultContentEvent event = new SwitchedToDefaultContentEvent();
        assertThat(event.describe()).isEqualTo("switched to default content");
    }

}
