package info.novatec.testit.webtester.events.browser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;


public class NavigatedBackwardsEventTest {

    @Test
    public void descriptionIsGeneratedCorrectly() {
        NavigatedBackwardsEvent event = new NavigatedBackwardsEvent();
        assertThat(event.describe()).isEqualTo("navigated back in the browser history");
    }

}
