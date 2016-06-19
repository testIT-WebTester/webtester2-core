package info.novatec.testit.webtester.events.browser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;


public class SetWindowSizeEventTest {

    @Test
    public void widthCanBeRetrieved() {
        SetWindowSizeEvent event = new SetWindowSizeEvent(10, 15);
        assertThat(event.getWidth()).isEqualTo(10);
    }

    @Test
    public void heightCanBeRetrieved() {
        SetWindowSizeEvent event = new SetWindowSizeEvent(10, 15);
        assertThat(event.getHeight()).isEqualTo(15);
    }

    @Test
    public void descriptionIsGeneratedCorrectly() {
        SetWindowSizeEvent event = new SetWindowSizeEvent(10, 15);
        assertThat(event.describe()).isEqualTo("set window size: width=10, height=15");
    }

}
