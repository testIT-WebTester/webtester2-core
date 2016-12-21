package integration.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;
import utils.events.EventCaptor;

import info.novatec.testit.webtester.events.pagefragments.SelectionChangedEvent;
import info.novatec.testit.webtester.pagefragments.Checkbox;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.mapping.MappingException;
import info.novatec.testit.webtester.pages.Page;


public class CheckboxIntegrationTest extends BaseIntegrationTest {

    TestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/checkbox.html";
    }

    @Before
    public void setUp() {
        page = create(TestPage.class);
    }

    /* is selected */

    @Test
    public void stateOfSelectedCheckboxCanBeRead() {
        assertThat(page.selected().isSelected()).isTrue();
    }

    @Test
    public void stateOfUnselectedCheckboxCanBeRead() {
        assertThat(page.notSelected().isNotSelected()).isTrue();
    }

    /* select */

    @Test
    public void selectingNonSelectedCheckboxChangesSelection() {
        boolean selected = page.notSelected().select().isSelected();
        assertThat(selected).isTrue();
    }

    @Test
    public void selectingNonSelectedCheckboxFiresEvent() {
        EventCaptor.capture(eventSystem(), SelectionChangedEvent.class)
            .execute(() -> page.notSelected().select())
            .assertEventWasFired()
            .assertEvent(event -> {
                assertThat(event.isBefore()).isFalse();
                assertThat(event.isAfter()).isTrue();
            });
    }

    @Test
    public void selectingSelectedCheckboxDoesNotChangeSelection() {
        boolean selected = page.selected().select().isSelected();
        assertThat(selected).isTrue();
    }

    @Test
    public void selectingSelectedCheckboxDoesNotFireEvent() {
        EventCaptor.capture(eventSystem(), SelectionChangedEvent.class)
            .execute(() -> page.selected().select())
            .assertEventWasNotFired();
    }

    /* deselect */

    @Test
    public void deselectingSelectedCheckboxChangesSelection() {
        boolean selected = page.selected().deselect().isSelected();
        assertThat(selected).isFalse();
    }

    @Test
    public void deselectingSelectedCheckboxFiresEvent() {
        EventCaptor.capture(eventSystem(), SelectionChangedEvent.class)
            .execute(() -> page.selected().deselect())
            .assertEventWasFired()
            .assertEvent(event -> {
                assertThat(event.isBefore()).isTrue();
                assertThat(event.isAfter()).isFalse();
            });
    }

    @Test
    public void deselectingNonSelectedCheckboxDoesNotChangeSelection() {
        boolean selected = page.notSelected().deselect().isSelected();
        assertThat(selected).isFalse();
    }

    @Test
    public void deselectingNonSelectedCheckboxDoesNotFireEvent() {
        EventCaptor.capture(eventSystem(), SelectionChangedEvent.class)
            .execute(() -> page.notSelected().deselect())
            .assertEventWasNotFired();
    }

    /* mapping */

    @Test
    public final void inputCheckboxTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.selected());
    }

    @Test(expected = MappingException.class)
    public void nonCheckboxTypeIsInvalidMapping() {
        assertPageFragmentCanBeInitialized(page.noCheckbox());
    }

    private interface TestPage extends Page {

        @IdentifyUsing("#selected")
        Checkbox selected();
        @IdentifyUsing("#notSelected")
        Checkbox notSelected();

        @IdentifyUsing("#noCheckbox")
        Checkbox noCheckbox();

    }

}
