package info.novatec.testit.webtester.events.browser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;


public class NavigatedForwardsEventTest {

    @Test
    public void descriptionIsGeneratedCorrectly() {
        NavigatedForwardsEvent event = new NavigatedForwardsEvent();
        assertThat(event.describe()).isEqualTo("navigated forward in the browser history");
    }

}
