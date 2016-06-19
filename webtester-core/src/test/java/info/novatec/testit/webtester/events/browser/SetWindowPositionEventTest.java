package info.novatec.testit.webtester.events.browser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;


public class SetWindowPositionEventTest {

    @Test
    public void xCoordinateCanBeRetrieved() {
        SetWindowPositionEvent event = new SetWindowPositionEvent(10, 15);
        assertThat(event.getX()).isEqualTo(10);
    }

    @Test
    public void yCoordinateCanBeRetrieved() {
        SetWindowPositionEvent event = new SetWindowPositionEvent(10, 15);
        assertThat(event.getY()).isEqualTo(15);
    }

    @Test
    public void descriptionIsGeneratedCorrectly() {
        SetWindowPositionEvent event = new SetWindowPositionEvent(10, 15);
        assertThat(event.describe()).isEqualTo("set window position: x=10, y=15");
    }

}
