package info.novatec.testit.webtester.events.browser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;


public class DeclinedAlertEventTest {

    @Test
    public void alertMessageCanBeRetrieved() {
        DeclinedAlertEvent event = new DeclinedAlertEvent("hello world");
        assertThat(event.getAlertMessage()).isEqualTo("hello world");
    }

    @Test
    public void descriptionIsGeneratedCorrectly() {
        DeclinedAlertEvent event = new DeclinedAlertEvent("hello world");
        assertThat(event.describe()).isEqualTo("declined an alert with message: 'hello world'");
    }

}
