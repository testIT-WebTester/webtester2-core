package info.novatec.testit.webtester.events.browser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;


public class AcceptedAlertEventTest {

    @Test
    public void alertMessageCanBeRetrieved() {
        AcceptedAlertEvent event = new AcceptedAlertEvent("hello world");
        assertThat(event.getAlertMessage()).isEqualTo("hello world");
    }

    @Test
    public void descriptionIsGeneratedCorrectly() {
        AcceptedAlertEvent event = new AcceptedAlertEvent("hello world");
        assertThat(event.describe()).isEqualTo("accepted an alert with message: 'hello world'");
    }

}
