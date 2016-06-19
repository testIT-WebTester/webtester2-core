package info.novatec.testit.webtester.events.browser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.io.File;

import org.junit.Test;
import org.mockito.Mockito;


public class TookScreenshotEventTest {

    @Test
    public void fileReferenceCanBeRetrieved() {
        File file = Mockito.mock(File.class);
        TookScreenshotEvent event = new TookScreenshotEvent(file);
        assertThat(event.getScreenshot()).isSameAs(file);
    }

    @Test
    public void descriptionIsGeneratedCorrectly() {
        File file = Mockito.mock(File.class);
        doReturn("/tmp/foo.png").when(file).toString();
        TookScreenshotEvent event = new TookScreenshotEvent(file);
        assertThat(event.describe()).isEqualTo("took screenshot and stored it as: '/tmp/foo.png'");
    }

}
