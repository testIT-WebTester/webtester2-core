package info.novatec.testit.webtester.events.browser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class SwitchedToFrameEventTest {

    @Test
    public void targetCanBeRetrieved() {
        SwitchedToFrameEvent event = new SwitchedToFrameEvent("name");
        assertThat(event.getTarget()).isEqualTo("name");
    }

    @Test
    public void descriptionIsGeneratedCorrectlyForNameOrId() {
        SwitchedToFrameEvent event = new SwitchedToFrameEvent("nameOrId");
        assertThat(event.describe()).isEqualTo("switched to frame using: name or id 'nameOrId'");
    }

    @Test
    public void descriptionIsGeneratedCorrectlyForIndex() {
        SwitchedToFrameEvent event = new SwitchedToFrameEvent(42);
        assertThat(event.describe()).isEqualTo("switched to frame using: index '42'");
    }

    @Test
    public void descriptionIsGeneratedCorrectlyForPageFragment() {
        PageFragment fragment = mock(PageFragment.class);
        doReturn(Optional.of("name")).when(fragment).getName();
        SwitchedToFrameEvent event = new SwitchedToFrameEvent(fragment);
        assertThat(event.describe()).isEqualTo("switched to frame using: page fragment 'name'");
    }

    @Test
    public void pageFragmentsWithoutNamesHaveAFallback() {
        PageFragment fragment = mock(PageFragment.class);
        doReturn(Optional.empty()).when(fragment).getName();
        SwitchedToFrameEvent event = new SwitchedToFrameEvent(fragment);
        assertThat(event.describe()).isEqualTo("switched to frame using: page fragment 'unknown'");
    }

}
