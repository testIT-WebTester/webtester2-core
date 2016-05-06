package info.novatec.testit.webtester.pagefragments;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.google.common.collect.Lists;

import info.novatec.testit.webtester.events.Produces;
import info.novatec.testit.webtester.events.pagefragments.DeselectedAllEvent;
import info.novatec.testit.webtester.events.pagefragments.DeselectedByIndicesEvent;
import info.novatec.testit.webtester.events.pagefragments.DeselectedByTextsEvent;
import info.novatec.testit.webtester.events.pagefragments.DeselectedByValuesEvent;
import info.novatec.testit.webtester.events.pagefragments.SelectedByIndicesEvent;
import info.novatec.testit.webtester.events.pagefragments.SelectedByTextsEvent;
import info.novatec.testit.webtester.events.pagefragments.SelectedByValuesEvent;
import info.novatec.testit.webtester.pagefragments.annotations.Action;
import info.novatec.testit.webtester.pagefragments.annotations.Mapping;
import info.novatec.testit.webtester.pagefragments.annotations.Mark;
import info.novatec.testit.webtester.pagefragments.annotations.As;

@Mapping(tag = "select", attribute = "multiple")
public interface MultiSelect extends GenericSelect<MultiSelect> {

    /**
     * Deselects all options and fires {@link DeselectedAllEvent}.
     *
     * @return the same instance for fluent API use
     * @see Select#deselectAll()
     * @see MultiSelect
     * @since 2.0
     */
    @Action
    @Mark(As.USED)
    @Produces(DeselectedAllEvent.class)
    default MultiSelect deselectAll() {
        new Select(webElement()).deselectAll();
        return this;
    }

    /**
     * Deselects the options with the given texts and fires {@link DeselectedByTextsEvent}.
     *
     * @param texts the texts to deselect
     * @return the same instance for fluent API use
     * @throws NoSuchElementException in case there isn't an option for at least one of the given texts
     * @see #deselectByTexts(Collection)
     * @see Select#deselectByVisibleText(String)
     * @see MultiSelect
     * @since 2.0
     */
    default MultiSelect deselectByTexts(String... texts) throws NoSuchElementException {
        return deselectByTexts(Lists.newArrayList(texts));
    }

    /**
     * Deselects the options with the given texts and fires {@link DeselectedByTextsEvent}.
     *
     * @param texts the texts to deselect
     * @return the same instance for fluent API use
     * @throws NoSuchElementException in case there isn't an option for at least one of the given texts
     * @see Select#deselectByVisibleText(String)
     * @see MultiSelect
     * @since 2.0
     */
    @Action
    @Mark(As.USED)
    @Produces(DeselectedByTextsEvent.class)
    default MultiSelect deselectByTexts(Collection<String> texts) throws NoSuchElementException {
        Select select = new Select(webElement());
        texts.forEach(select::deselectByVisibleText);
        return this;
    }

    /**
     * Deselects the options with the given values and fires {@link DeselectedByValuesEvent}.
     *
     * @param values the values to deselect
     * @return the same instance for fluent API use
     * @throws NoSuchElementException in case there isn't an option for at least one of the given values
     * @see #deselectByValues(Collection)
     * @see Select#deselectByValue(String)
     * @see MultiSelect
     * @since 2.0
     */
    default MultiSelect deselectByValues(String... values) throws NoSuchElementException {
        return deselectByValues(Lists.newArrayList(values));
    }

    /**
     * Deselects the options with the given values and fires {@link DeselectedByValuesEvent}.
     *
     * @param values the values to deselect
     * @return the same instance for fluent API use
     * @throws NoSuchElementException in case there isn't an option for at least one of the given values
     * @see Select#deselectByValue(String)
     * @see MultiSelect
     * @since 2.0
     */
    @Action
    @Mark(As.USED)
    @Produces(DeselectedByValuesEvent.class)
    default MultiSelect deselectByValues(Collection<String> values) throws NoSuchElementException {
        Select select = new Select(webElement());
        values.forEach(select::deselectByValue);
        return this;
    }

    /**
     * Deselects the options with the given indices and fires {@link DeselectedByIndicesEvent}.
     *
     * @param indices the indices to deselect
     * @return the same instance for fluent API use
     * @throws NoSuchElementException in case there isn't an option for at least one of the given indices
     * @see #deselectByIndices(Collection)
     * @see Select#deselectByIndex(int)
     * @see MultiSelect
     * @since 2.0
     */
    default MultiSelect deselectByIndices(Integer... indices) throws NoSuchElementException {
        return deselectByIndices(Lists.newArrayList(indices));
    }

    /**
     * Deselects the options with the given indices and fires {@link DeselectedByIndicesEvent}.
     *
     * @param indices the indices to deselect
     * @return the same instance for fluent API use
     * @throws NoSuchElementException in case there isn't an option for at least one of the given indices
     * @see Select#deselectByIndex(int)
     * @see MultiSelect
     * @since 2.0
     */
    @Action
    @Mark(As.USED)
    @Produces(DeselectedByIndicesEvent.class)
    default MultiSelect deselectByIndices(Collection<Integer> indices) throws NoSuchElementException {
        Select select = new Select(webElement());
        indices.forEach(select::deselectByIndex);
        return this;
    }

    /**
     * Selects the options with the given texts and fires {@link SelectedByTextsEvent}.
     *
     * @param texts the text to select
     * @return the same instance for fluent API use
     * @throws NoSuchElementException in case there isn't an option for at least one of the given texts
     * @see #selectByTexts(Collection)
     * @see Select#selectByVisibleText(String)
     * @see MultiSelect
     * @since 2.0
     */
    default MultiSelect selectByTexts(String... texts) throws NoSuchElementException {
        return selectByTexts(Lists.newArrayList(texts));
    }

    /**
     * Selects the options with the given texts and fires {@link SelectedByTextsEvent}.
     *
     * @param texts the texts to select
     * @return the same instance for fluent API use
     * @throws NoSuchElementException in case there isn't an option for at least one of the given texts
     * @see Select#selectByVisibleText(String)
     * @see MultiSelect
     * @since 2.0
     */
    @Action
    @Mark(As.USED)
    @Produces(SelectedByTextsEvent.class)
    default MultiSelect selectByTexts(Collection<String> texts) throws NoSuchElementException {
        Select select = new Select(webElement());
        texts.forEach(select::selectByVisibleText);
        return this;
    }

    /**
     * Selects the options with the given values and fires {@link SelectedByValuesEvent}.
     *
     * @param values the values to select
     * @return the same instance for fluent API use
     * @throws NoSuchElementException in case there isn't an option for at least one of the given values
     * @see #selectByValues(Collection)
     * @see Select#selectByValue(String)
     * @see MultiSelect
     * @since 2.0
     */
    default MultiSelect selectByValues(String... values) throws NoSuchElementException {
        return selectByValues(Lists.newArrayList(values));
    }

    /**
     * Selects the options with the given values and fires {@link SelectedByValuesEvent}.
     *
     * @param values the values to select
     * @return the same instance for fluent API use
     * @throws NoSuchElementException in case there isn't an option for at least one of the given values
     * @see Select#selectByValue(String)
     * @see MultiSelect
     * @since 2.0
     */
    @Action
    @Mark(As.USED)
    @Produces(SelectedByValuesEvent.class)
    default MultiSelect selectByValues(Collection<String> values) throws NoSuchElementException {
        Select select = new Select(webElement());
        values.forEach(select::selectByValue);
        return this;
    }

    /**
     * Selects the options with the given indices and fires {@link SelectedByIndicesEvent}.
     *
     * @param indices the indices to select
     * @return the same instance for fluent API use
     * @throws NoSuchElementException in case there isn't an option for at least one of the given indices
     * @see #selectByIndices(Collection)
     * @see Select#selectByIndex(int)
     * @see MultiSelect
     * @since 2.0
     */
    default MultiSelect selectByIndices(Integer... indices) throws NoSuchElementException {
        return selectByIndices(Lists.newArrayList(indices));
    }

    /**
     * Selects the options with the given indices and fires {@link SelectedByIndicesEvent}.
     *
     * @param indices the indices to select
     * @return the same instance for fluent API use
     * @throws NoSuchElementException in case there isn't an option for at least one of the given indices
     * @see Select#selectByIndex(int)
     * @see MultiSelect
     * @since 2.0
     */
    @Action
    @Mark(As.USED)
    @Produces(SelectedByIndicesEvent.class)
    default MultiSelect selectByIndices(Collection<Integer> indices) throws NoSuchElementException {
        Select select = new Select(webElement());
        indices.forEach(select::selectByIndex);
        return this;
    }

    /**
     * Returns the texts of all selected options in order.
     * In case there is no selection an empty list is returned.
     *
     * @return the texts of all selected option
     * @see Select#getAllSelectedOptions()
     * @see WebElement#getText()
     * @see MultiSelect
     * @since 2.0
     */
    default List<String> getSelectionTexts() {
        return streamSelectionTexts().collect(Collectors.toList());
    }

    /**
     * Streams the texts of all selected options in order.
     * In case there is no selection an empty stream is returned.
     *
     * @return the texts of all selected option
     * @see Select#getAllSelectedOptions()
     * @see WebElement#getText()
     * @see MultiSelect
     * @since 2.0
     */
    @Mark(As.READ)
    default Stream<String> streamSelectionTexts() {
        return new Select(webElement()).getAllSelectedOptions().stream().map(WebElement::getText);
    }

    /**
     * Returns the values of all selected options in order.
     * In case there is no selection an empty list is returned.
     *
     * @return the values of all selected option
     * @see Select#getAllSelectedOptions()
     * @see WebElement#getAttribute(String)
     * @see MultiSelect
     * @since 2.0
     */
    default List<String> getSelectionValues() {
        return streamSelectionValues().collect(Collectors.toList());
    }

    /**
     * Streams the values of all selected options in order.
     * In case there is no selection an empty stream is returned.
     *
     * @return the values of all selected option
     * @see Select#getAllSelectedOptions()
     * @see WebElement#getAttribute(String)
     * @see MultiSelect
     * @since 2.0
     */
    @Mark(As.READ)
    default Stream<String> streamSelectionValues() {
        return new Select(webElement()).getAllSelectedOptions().stream().map(option -> option.getAttribute("value"));
    }

    /**
     * Returns the indices of all selected options in order.
     * In case there is no selection an empty list is returned.
     *
     * @return the indices of all selected option
     * @see Select#getAllSelectedOptions()
     * @see WebElement#getAttribute(String)
     * @see MultiSelect
     * @since 2.0
     */
    default List<Integer> getSelectionIndices() {
        return streamSelectionIndices().collect(Collectors.toList());
    }

    /**
     * Streams the indices of all selected options in order.
     * In case there is no selection an empty stream is returned.
     *
     * @return the indices of all selected option
     * @see Select#getAllSelectedOptions()
     * @see WebElement#getAttribute(String)
     * @see MultiSelect
     * @since 2.0
     */
    @Mark(As.READ)
    default Stream<Integer> streamSelectionIndices() {
        return new Select(webElement()).getAllSelectedOptions()
            .stream()
            .map(option -> option.getAttribute("index"))
            .map(Integer::parseInt);
    }

    /**
     * Returns the number of selected options.
     *
     * @return the number of selected option
     * @see Select#getAllSelectedOptions()
     * @see Collection#size()
     * @see MultiSelect
     * @since 2.0
     */
    default Integer getSelectionCount() {
        return new Select(webElement()).getAllSelectedOptions().size();
    }

}
