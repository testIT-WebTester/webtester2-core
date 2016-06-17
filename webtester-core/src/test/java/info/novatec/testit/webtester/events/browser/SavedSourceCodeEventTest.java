package info.novatec.testit.webtester.events.browser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.io.File;

import org.junit.Test;
import org.mockito.Mockito;


public class SavedSourceCodeEventTest {

    @Test
    public void fileReferenceCanBeRetrieved() {
        File file = Mockito.mock(File.class);
        SavedSourceCodeEvent event = new SavedSourceCodeEvent(file);
        assertThat(event.getPageSource()).isSameAs(file);
    }

    @Test
    public void descriptionIsGeneratedCorrectly() {
        File file = Mockito.mock(File.class);
        doReturn("/tmp/foo.html").when(file).toString();
        SavedSourceCodeEvent event = new SavedSourceCodeEvent(file);
        assertThat(event.describe()).isEqualTo("saved page source and stored it as: '/tmp/foo.html'");
    }

}
