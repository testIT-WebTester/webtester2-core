package info.novatec.testit.webtester.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import utils.integration.BaseIntTest;
import utils.events.EventCaptor;

import info.novatec.testit.webtester.events.pagefragments.ClickedEvent;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.mapping.MappingException;
import info.novatec.testit.webtester.pages.Page;


public class RadioButtonIntTest extends BaseIntTest {

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
        assertThat(page.selected().isSelected()).isTrue();
    }

    @Test
    public void stateOfUnselectedRadioButtonCanBeRead() {
        assertThat(page.notSelected().isNotSelected()).isTrue();
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
