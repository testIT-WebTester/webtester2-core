package info.novatec.testit.webtester.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import utils.integration.BaseIntTest;
import utils.events.EventCaptor;

import info.novatec.testit.webtester.events.pagefragments.ClickedEvent;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.mapping.MappingException;
import info.novatec.testit.webtester.pages.Page;


public class ButtonIntTest extends BaseIntTest {

    TestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/button.html";
    }

    @Before
    public void setUp() {
        page = create(TestPage.class);
    }

    /* label */

    @Test
    public void labelIsReturnedForSimpleButton() {
        String label = page.button().getLabel();
        assertThat(label).isEqualTo("Button");
    }

    @Test
    public void emptyLabelIsReturnedForLabelLessSimpleButton() {
        String label = page.buttonWithoutLabel().getLabel();
        assertThat(label).isEqualTo("");
    }

    @Test
    public void labelIsReturnedForInputButton() {
        String label = page.inputButton().getLabel();
        assertThat(label).isEqualTo("Input: Button");
    }

    @Test
    public void emptyLabelIsReturnedForLabelLessInputButton() {
        String label = page.inputButtonWithoutLabel().getLabel();
        assertThat(label).isEqualTo("");
    }

    /* value */

    @Test
    public void valueAttributeCanBeRead() {
        Optional<String> value = page.button().getValue();
        assertThat(value).contains("normal_button");
    }

    /* click */

    @Test
    public void buttonsCanBeClicked() {
        Button button = page.clickMeButton();
        button.click();
        assertThat(button.getLabel()).contains("Clicked!");
    }

    @Test
    public void clickingButtonsFiresEvents() {
        EventCaptor.capture(eventSystem(), ClickedEvent.class)
            .execute(()-> page.clickMeButton().click())
            .assertEventWasFired();
    }

    /* mapping */

    @Test
    public void buttonTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.button());
    }

    @Test
    public void inputButtonTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.inputButton());
    }

    @Test
    public void inputResetTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.inputReset());
    }

    @Test
    public void inputSubmitTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.inputSubmit());
    }

    @Test(expected = MappingException.class)
    public void nonButtonTypeIsInvalidMapping() {
        assertPageFragmentCanBeInitialized(page.noButton());
    }

    private interface TestPage extends Page {

        @IdentifyUsing("#button")
        Button button();
        @IdentifyUsing("#input_submit")
        Button inputSubmit();
        @IdentifyUsing("#input_reset")
        Button inputReset();
        @IdentifyUsing("#input_button")
        Button inputButton();

        @IdentifyUsing("#clickMeButton")
        Button clickMeButton();

        @IdentifyUsing("#buttonWithoutLabel")
        Button buttonWithoutLabel();
        @IdentifyUsing("#inputButtonWithoutLabel")
        Button inputButtonWithoutLabel();

        @IdentifyUsing("#noButton")
        Button noButton();

    }

}
