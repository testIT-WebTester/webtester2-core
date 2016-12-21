package integration.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;

import integration.BaseIntegrationTest;
import utils.events.EventCaptor;

import info.novatec.testit.webtester.events.pagefragments.SelectedByIndexEvent;
import info.novatec.testit.webtester.events.pagefragments.SelectedByTextEvent;
import info.novatec.testit.webtester.events.pagefragments.SelectedByValueEvent;
import info.novatec.testit.webtester.pagefragments.SingleSelect;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.mapping.MappingException;
import info.novatec.testit.webtester.pages.Page;


public class SingleSelectIntegrationTest extends BaseIntegrationTest {

    TestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/singleSelect.html";
    }

    @Before
    public void initPage() {
        page = create(TestPage.class);
    }

    /* select by text */

    @Test
    public void selectionCanBeMadeByText() {
        SingleSelect select = page.noSelection();
        select.selectByText("two");
        assertThat(select.getSelectionText()).contains("two");
    }

    @Test
    public void selectingByTextFiresEvent() {
        EventCaptor.capture(eventSystem(), SelectedByTextEvent.class)
            .execute(() -> page.noSelection().selectByText("one"))
            .assertEventWasFired()
            .assertEvent(event -> assertThat(event.getText()).isEqualTo("one"));
    }

    @Test(expected = NoSuchElementException.class)
    public void selectionByUnknownText() {
        page.noSelection().selectByText("unknown");
    }

    /* select by value */

    @Test
    public void selectionCanBeMadeByValue() {
        SingleSelect select = page.noSelection();
        select.selectByValue("2");
        assertThat(select.getSelectionValue()).contains("2");
    }

    @Test
    public void selectingByValueFiresEvent() {
        EventCaptor.capture(eventSystem(), SelectedByValueEvent.class)
            .execute(() -> page.noSelection().selectByValue("1"))
            .assertEventWasFired()
            .assertEvent(event -> assertThat(event.getValue()).isEqualTo("1"));
    }

    @Test(expected = NoSuchElementException.class)
    public void selectionByUnknownValue() {
        page.noSelection().selectByValue("unknown");
    }

    /* select by index */

    @Test
    public void selectionCanBeMadeByIndex() {
        SingleSelect select = page.noSelection();
        select.selectByIndex(2);
        assertThat(select.getSelectionIndex()).contains(2);
    }

    @Test
    public void selectingByIndexFiresEvent() {
        EventCaptor.capture(eventSystem(), SelectedByIndexEvent.class)
            .execute(() -> page.noSelection().selectByIndex(1))
            .assertEventWasFired()
            .assertEvent(event -> assertThat(event.getIndex()).isEqualTo(1));
    }

    @Test(expected = NoSuchElementException.class)
    public void selectionByUnknownIndex() {
        page.noSelection().selectByIndex(42);
    }

    /* get selected text */

    @Test
    public void selectedTextCanBeRead() {
        Optional<String> text = page.preSelected().getSelectionText();
        assertThat(text).contains("two");
    }

    @Test
    public void readingSelectedTextWithoutSelection() {
        // if nothing is directly selected, the first element is selected by default
        Optional<String> text = page.noSelection().getSelectionText();
        assertThat(text).contains("one");
    }

    @Test
    public void readingSelectedTextWithoutOptions() {
        Optional<String> text = page.emptySelect().getSelectionText();
        assertThat(text).isEmpty();
    }

    /* get selected value */

    @Test
    public void selectedValueCanBeRead() {
        Optional<String> value = page.preSelected().getSelectionValue();
        assertThat(value).contains("2");
    }

    @Test
    public void readingSelectedValueWithoutSelection() {
        // if nothing is directly selected, the first element is selected by default
        Optional<String> value = page.noSelection().getSelectionValue();
        assertThat(value).contains("1");
    }

    @Test
    public void readingSelectedValueWithoutOptions() {
        Optional<String> value = page.emptySelect().getSelectionValue();
        assertThat(value).isEmpty();
    }

    /* get selected index */

    @Test
    public void selectedIndexCanBeRead() {
        Optional<Integer> value = page.preSelected().getSelectionIndex();
        assertThat(value).contains(1);
    }

    @Test
    public void readingSelectedIndexWithoutSelection() {
        // if nothing is directly selected, the first element is selected by default
        Optional<Integer> index = page.noSelection().getSelectionIndex();
        assertThat(index).contains(0);
    }

    @Test
    public void readingSelectedIndexWithoutOptions() {
        Optional<Integer> index = page.emptySelect().getSelectionIndex();
        assertThat(index).isEmpty();
    }

    /* get texts */

    @Test
    public void optionTextsCanBeRead() {
        List<String> texts = page.noSelection().getOptionTexts();
        assertThat(texts).containsExactly("one", "two", "three");
    }

    @Test
    public void optionTextsOfEmptySelectCanBeRead() {
        List<String> texts = page.emptySelect().getOptionTexts();
        assertThat(texts).isEmpty();
    }

    /* get values */

    @Test
    public void optionValuesCanBeRead() {
        List<String> values = page.noSelection().getOptionValues();
        assertThat(values).containsExactly("1", "2", "3");
    }

    @Test
    public void optionValuesOfEmptySelectCanBeRead() {
        List<String> values = page.emptySelect().getOptionValues();
        assertThat(values).isEmpty();
    }

    /* get number of options */

    @Test
    public void numberOfOptionsCanBeRead() {
        Integer numberOfOptions = page.noSelection().getOptionCount();
        assertThat(numberOfOptions).isEqualTo(3);
    }

    @Test
    public void numberOfOptionsForEmptySelectCanBeRead() {
        Integer numberOfOptions = page.emptySelect().getOptionCount();
        assertThat(numberOfOptions).isEqualTo(0);
    }

    /* mapping */

    @Test
    public void singleSelectTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.singleSelect());
    }

    @Test(expected = MappingException.class)
    public void multiSelectTypeIsInvalidMapping() {
        assertPageFragmentCanBeInitialized(page.multiSelect());
    }

    @Test(expected = MappingException.class)
    public void nonSelectTypeIsInvalidMapping() {
        assertPageFragmentCanBeInitialized(page.noSelect());
    }

    public interface TestPage extends Page {

        @IdentifyUsing("#singleSelect")
        SingleSelect noSelection();
        @IdentifyUsing("#singleSelectWithSelection")
        SingleSelect preSelected();
        @IdentifyUsing("#emptySelect")
        SingleSelect emptySelect();

        @IdentifyUsing("#singleSelect")
        SingleSelect singleSelect();
        @IdentifyUsing("#multiSelect")
        SingleSelect multiSelect();
        @IdentifyUsing("#noSelect")
        SingleSelect noSelect();

    }

}
