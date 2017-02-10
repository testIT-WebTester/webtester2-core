package info.novatec.testit.webtester.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;

import utils.integration.BaseIntTest;
import utils.events.EventCaptor;

import info.novatec.testit.webtester.events.pagefragments.DeselectedAllEvent;
import info.novatec.testit.webtester.events.pagefragments.DeselectedByIndicesEvent;
import info.novatec.testit.webtester.events.pagefragments.DeselectedByTextsEvent;
import info.novatec.testit.webtester.events.pagefragments.DeselectedByValuesEvent;
import info.novatec.testit.webtester.events.pagefragments.SelectedByIndicesEvent;
import info.novatec.testit.webtester.events.pagefragments.SelectedByTextsEvent;
import info.novatec.testit.webtester.events.pagefragments.SelectedByValuesEvent;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.mapping.MappingException;
import info.novatec.testit.webtester.pages.Page;


public class MultiSelectIntTest extends BaseIntTest {

    TestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/multiSelect.html";
    }

    @Before
    public void initPage() {
        page = create(TestPage.class);
    }

    /* deselect all */

    @Test
    public void allOptionsCanBeDeselected() {
        MultiSelect select = page.preSelected();
        select.deselectAll();
        assertThat(select.getSelectionCount()).isEqualTo(0);
    }

    @Test
    public void deselectingAllOptionsFiresEvent() {
        EventCaptor.capture(eventSystem(), DeselectedAllEvent.class)
            .execute(() -> page.preSelected().deselectAll())
            .assertEventWasFired();
    }

    /* deselect by texts */

    @Test
    public void deselectionCanBeMadeByTexts() {
        MultiSelect select = page.preSelected();
        select.deselectByTexts("one", "two");
        assertThat(select.getSelectionTexts()).containsExactly("three");
    }

    @Test
    public void deselectingByTextsFiresEvent() {
        EventCaptor.capture(eventSystem(), DeselectedByTextsEvent.class)
            .execute(() -> page.preSelected().deselectByTexts("one", "two"))
            .assertEventWasFired()
            .assertEvent(event -> assertThat(event.getTexts()).containsExactly("two"));
    }

    @Test(expected = NoSuchElementException.class)
    public void deselectionByUnknownText() {
        page.preSelected().deselectByTexts("unknown");
    }

    /* deselect by values */

    @Test
    public void deselectionCanBeMadeByValues() {
        MultiSelect select = page.preSelected();
        select.deselectByValues("1", "2");
        assertThat(select.getSelectionValues()).containsExactly("3");
    }

    @Test
    public void deselectingByValuesFiresEvent() {
        EventCaptor.capture(eventSystem(), DeselectedByValuesEvent.class)
            .execute(() -> page.preSelected().deselectByValues("1", "2"))
            .assertEventWasFired()
            .assertEvent(event -> assertThat(event.getValues()).containsExactly("2"));
    }

    @Test(expected = NoSuchElementException.class)
    public void deselectionByUnknownValue() {
        page.preSelected().deselectByValues("unknown");
    }

    /* deselect by indices */

    @Test
    public void deselectionCanBeMadeByIndices() {
        MultiSelect select = page.preSelected();
        select.deselectByIndices(0, 1);
        assertThat(select.getSelectionIndices()).containsExactly(2);
    }

    @Test
    public void deselectingByIndicesFiresEvent() {
        EventCaptor.capture(eventSystem(), DeselectedByIndicesEvent.class)
            .execute(() -> page.preSelected().deselectByIndices(0, 1))
            .assertEventWasFired()
            .assertEvent(event -> assertThat(event.getIndices()).containsExactly(1));
    }

    @Test(expected = NoSuchElementException.class)
    public void deselectionByUnknownIndex() {
        page.preSelected().deselectByIndices(42);
    }

    /* select by texts */

    @Test
    public void selectionCanBeMadeByTexts() {
        MultiSelect select = page.noSelection();
        select.selectByTexts("two", "three");
        assertThat(select.getSelectionTexts()).containsExactly("two", "three");
    }

    @Test
    public void selectionByTextIsAdditive() {
        MultiSelect select = page.preSelected();
        select.selectByTexts("one");
        assertThat(select.getSelectionTexts()).containsExactly("one", "two", "three");
    }

    @Test
    public void selectingByTextsFiresEvent() {
        EventCaptor.capture(eventSystem(), SelectedByTextsEvent.class)
            .execute(() -> page.preSelected().selectByTexts("one", "two"))
            .assertEventWasFired()
            .assertEvent(event -> assertThat(event.getTexts()).containsExactly("one"));
    }

    @Test(expected = NoSuchElementException.class)
    public void selectionByUnknownText() {
        page.noSelection().selectByTexts("unknown");
    }

    /* select by values */

    @Test
    public void selectionCanBeMadeByValues() {
        MultiSelect select = page.noSelection();
        select.selectByValues("2", "3");
        assertThat(select.getSelectionValues()).containsExactly("2", "3");
    }

    @Test
    public void selectionByValueIsAdditive() {
        MultiSelect select = page.preSelected();
        select.selectByValues("1");
        assertThat(select.getSelectionValues()).containsExactly("1", "2", "3");
    }

    @Test
    public void selectingByValuesFiresEvent() {
        EventCaptor.capture(eventSystem(), SelectedByValuesEvent.class)
            .execute(() -> page.preSelected().selectByValues("1", "2"))
            .assertEventWasFired()
            .assertEvent(event -> assertThat(event.getValues()).containsExactly("1"));
    }

    @Test(expected = NoSuchElementException.class)
    public void selectionByUnknownValue() {
        page.noSelection().selectByValues("unknown");
    }

    /* select by index */

    @Test
    public void selectionCanBeMadeByIndices() {
        MultiSelect select = page.noSelection();
        select.selectByIndices(1, 2);
        assertThat(select.getSelectionIndices()).containsExactly(1, 2);
    }

    @Test
    public void selectionByIndicesIsAdditive() {
        MultiSelect select = page.preSelected();
        select.selectByIndices(0);
        assertThat(select.getSelectionIndices()).containsExactly(0, 1, 2);
    }

    @Test
    public void selectingByIndicesFiresEvent() {
        EventCaptor.capture(eventSystem(), SelectedByIndicesEvent.class)
            .execute(() -> page.preSelected().selectByIndices(0, 1))
            .assertEventWasFired()
            .assertEvent(event -> assertThat(event.getIndices()).containsExactly(0));
    }

    @Test(expected = NoSuchElementException.class)
    public void selectionByUnknownIndex() {
        page.noSelection().selectByIndices(42);
    }

    /* get selected texts */

    @Test
    public void selectedTextsCanBeRead() {
        List<String> texts = page.preSelected().getSelectionTexts();
        assertThat(texts).containsExactly("two", "three");
    }

    @Test
    public void selectedTextsWithoutSelectionCanBeRead() {
        List<String> texts = page.noSelection().getSelectionTexts();
        assertThat(texts).isEmpty();
    }

    @Test
    public void selectedTextsWithoutOptionsCanBeRead() {
        List<String> texts = page.emptySelect().getSelectionTexts();
        assertThat(texts).isEmpty();
    }

    /* get all texts */

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

    /* get selected values */

    @Test
    public void selectedValuesCanBeRead() {
        List<String> values = page.preSelected().getSelectionValues();
        assertThat(values).containsExactly("2", "3");
    }

    @Test
    public void selectedValuesWithoutSelectionCanBeRead() {
        List<String> values = page.noSelection().getSelectionValues();
        assertThat(values).isEmpty();
    }

    @Test
    public void selectedValuesWithoutOptionsCanBeRead() {
        List<String> values = page.emptySelect().getSelectionValues();
        assertThat(values).isEmpty();
    }

    /* get all values */

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

    /* get selected indices */

    @Test
    public void selectedIndicesCanBeRead() {
        List<Integer> indices = page.preSelected().getSelectionIndices();
        assertThat(indices).containsExactly(1, 2);
    }

    @Test
    public void selectedIndicesWithoutSelectionCanBeRead() {
        List<Integer> indices = page.noSelection().getSelectionIndices();
        assertThat(indices).isEmpty();
    }

    @Test
    public void selectedIndicesWithoutOptionsCanBeRead() {
        List<Integer> indices = page.emptySelect().getSelectionIndices();
        assertThat(indices).isEmpty();
    }

    /* get number of options */

    @Test
    public void numberOfOptionsCanBeRead() {
        Integer number = page.noSelection().getOptionCount();
        assertThat(number).isEqualTo(3);
    }

    @Test
    public void numberOfOptionsForEmptySelectCanBeRead() {
        Integer number = page.emptySelect().getOptionCount();
        assertThat(number).isEqualTo(0);
    }

    /* get number of selected options */

    @Test
    public void numberOfSelectedOptionsCanBeRead() {
        Integer number = page.preSelected().getSelectionCount();
        assertThat(number).isEqualTo(2);
    }

    @Test
    public void numberOfSelectedOptionsForNoSelectionCanBeRead() {
        Integer number = page.noSelection().getSelectionCount();
        assertThat(number).isEqualTo(0);
    }

    @Test
    public void numberOfSelectedOptionsForEmptySelectCanBeRead() {
        Integer number = page.emptySelect().getSelectionCount();
        assertThat(number).isEqualTo(0);
    }

    /* mapping */

    @Test
    public void multiSelectTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.multiSelect());
    }

    @Test(expected = MappingException.class)
    public void singleSelectTypeIsInvalidMapping() {
        assertPageFragmentCanBeInitialized(page.singleSelect());
    }

    @Test(expected = MappingException.class)
    public void nonSelectTypeIsInvalidMapping() {
        assertPageFragmentCanBeInitialized(page.noSelect());
    }

    /* utilities */

    public interface TestPage extends Page {

        @IdentifyUsing("#multiSelect")
        MultiSelect noSelection();

        @IdentifyUsing("#multiSelectWithSelection")
        MultiSelect preSelected();

        @IdentifyUsing("#emptySelect")
        MultiSelect emptySelect();

        @IdentifyUsing("#singleSelect")
        MultiSelect singleSelect();

        @IdentifyUsing("#multiSelect")
        MultiSelect multiSelect();

        @IdentifyUsing("#noSelect")
        MultiSelect noSelect();

    }

}
