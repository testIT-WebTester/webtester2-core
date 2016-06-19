package info.novatec.testit.webtester.events.browser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;


public class OpenedUrlEventTest {

    @Test
    public void urlCanBeRetrieved() {
        OpenedUrlEvent event = new OpenedUrlEvent("http://foo.bar");
        assertThat(event.getUrl()).isEqualTo("http://foo.bar");
    }

    @Test
    public void descriptionIsGeneratedCorrectly() {
        OpenedUrlEvent event = new OpenedUrlEvent("http://foo.bar");
        assertThat(event.describe()).isEqualTo("opened url: 'http://foo.bar'");
    }

}
