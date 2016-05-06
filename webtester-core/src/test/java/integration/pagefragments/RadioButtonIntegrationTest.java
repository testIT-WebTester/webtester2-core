package integration.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;
import utils.events.EventCaptor;

import info.novatec.testit.webtester.events.pagefragments.ClickedEvent;
import info.novatec.testit.webtester.pagefragments.mapping.MappingException;
import info.novatec.testit.webtester.pagefragments.RadioButton;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pages.Page;


public class RadioButtonIntegrationTest extends BaseIntegrationTest {

    TestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/radioButton.html";
    }

    @Before
    public void setUp() {
        page = create(TestPage.class);
    }

    /* is selected */

    @Test
    public void stateOfSelectedRadioButtonCanBeRead() {
        boolean selected = page.selected().isSelected();
        assertThat(selected).isTrue();
    }

    @Test
    public void stateOfUnselectedRadioButtonCanBeRead() {
        boolean selected = page.notSelected().isSelected();
        assertThat(selected).isFalse();
    }

    /* select */

    @Test
    public void selectingNonSelectedRadioButtonChangesSelection() {
        boolean selected = page.notSelected().select().isSelected();
        assertThat(selected).isTrue();
    }

    @Test
    public void selectingNonSelectedRadioButtonFiresEvent() {
        EventCaptor.capture(eventSystem(), ClickedEvent.class)
            .execute(() -> page.notSelected().select())
            .assertEventWasFired();
    }

    @Test
    public void selectingSelectedRadioButtonDoesNotChangeSelection() {
        boolean selected = page.selected().select().isSelected();
        assertThat(selected).isTrue();
    }

    @Test
    public void selectingSelectedRadioButtonFireEvent() {
        EventCaptor.capture(eventSystem(), ClickedEvent.class)
            .execute(() -> page.selected().select())
            .assertEventWasFired();
    }

    /* mapping */

    @Test
    public final void inputRadioButtonTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.selected());
    }

    @Test(expected = MappingException.class)
    public void nonRadioButtonTypeIsInvalidMapping() {
        assertPageFragmentCanBeInitialized(page.noRadioButton());
    }

    private interface TestPage extends Page {

        @IdentifyUsing("#selected")
        RadioButton selected();
        @IdentifyUsing("#notSelected")
        RadioButton notSelected();

        @IdentifyUsing("#noRadioButton")
        RadioButton noRadioButton();

    }

}
