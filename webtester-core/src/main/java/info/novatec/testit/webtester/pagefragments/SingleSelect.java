package info.novatec.testit.webtester.pagefragments;

import java.util.Optional;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import info.novatec.testit.webtester.events.Produces;
import info.novatec.testit.webtester.events.pagefragments.SelectedByIndexEvent;
import info.novatec.testit.webtester.events.pagefragments.SelectedByTextEvent;
import info.novatec.testit.webtester.events.pagefragments.SelectedByValueEvent;
import info.novatec.testit.webtester.pagefragments.annotations.Action;
import info.novatec.testit.webtester.pagefragments.annotations.As;
import info.novatec.testit.webtester.pagefragments.annotations.Mapping;
import info.novatec.testit.webtester.pagefragments.annotations.Mark;
import info.novatec.testit.webtester.pagefragments.utils.EnhancedSelect;


@Mapping(tag = "select", attribute = "!multiple")
public interface SingleSelect extends GenericSelect<SingleSelect> {

    /**
     * Make a selection by text and fires a {@link SelectedByTextEvent}.
     * <p>
     * This tries to make a selection by using the given text. This text has to be a match for one of the options.
     * If no option qualifies, an exception is thrown.
     *
     * @param text the text to select
     * @return the same instance for fluent API use
     * @throws NoSuchElementException if there is no option for the given text
     * @see Select#selectByVisibleText(String)
     * @see SingleSelect
     * @since 2.0
     */
    @Action
    @Mark(As.USED)
    @Produces(SelectedByTextEvent.class)
    default SingleSelect selectByText(String text) throws NoSuchElementException {
        new EnhancedSelect(webElement()).selectByVisibleText(text);
        return this;
    }

    /**
     * Make a selection by value and fires a {@link SelectedByValueEvent}.
     * <p>
     * This tries to make a selection by using the given value. This value has to be a match for one of the options.
     * If no option qualifies, an exception is thrown.
     *
     * @param value the value to select
     * @return the same instance for fluent API use
     * @throws NoSuchElementException if there is no option for the given value
     * @see Select#selectByValue(String)
     * @see SingleSelect
     * @since 2.0
     */
    @Action
    @Mark(As.USED)
    @Produces(SelectedByValueEvent.class)
    default SingleSelect selectByValue(String value) throws NoSuchElementException {
        new EnhancedSelect(webElement()).selectByValue(value);
        return this;
    }

    /**
     * Make a selection by index and fires a {@link SelectedByIndexEvent}.
     * <p>
     * This tries to make a selection by using the given index. This index has to be a match for one of the options.
     * If no option qualifies, an exception is thrown.
     *
     * @param index the index to select
     * @return the same instance for fluent API use
     * @throws NoSuchElementException if there is no option for the given index
     * @see Select#selectByIndex(int)
     * @see SingleSelect
     * @since 2.0
     */
    @Action
    @Mark(As.USED)
    @Produces(SelectedByIndexEvent.class)
    default SingleSelect selectByIndex(Integer index) throws NoSuchElementException {
        new EnhancedSelect(webElement()).selectByIndex(index);
        return this;
    }

    /**
     * Returns the current selected option's text.
     * <p>
     * If nothing is selected or there are no options, an empty optional is returned.
     *
     * @return the selected text
     * @see Select#getFirstSelectedOption()
     * @see WebElement#getText()
     * @see SingleSelect
     * @since 2.0
     */
    @Mark(As.READ)
    default Optional<String> getSelectionText() {
        try {
            String text = new EnhancedSelect(webElement()).getFirstSelectedOption().getText();
            return Optional.ofNullable(text);
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    /**
     * Returns the current selected option's value.
     * <p>
     * If nothing is selected or there are no options, an empty optional is returned.
     *
     * @return the selected value
     * @see Select#getFirstSelectedOption()
     * @see WebElement#getAttribute(String)
     * @see SingleSelect
     * @since 2.0
     */
    @Mark(As.READ)
    default Optional<String> getSelectionValue() {
        try {
            String value = new EnhancedSelect(webElement()).getFirstSelectedOption().getAttribute("value");
            return Optional.ofNullable(value);
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    /**
     * Returns the current selected option's index.
     * <p>
     * If nothing is selected or there are no options, an empty optional is returned.
     *
     * @return the selected index
     * @see Select#getFirstSelectedOption()
     * @see WebElement#getAttribute(String)
     * @see SingleSelect
     * @since 2.0
     */
    @Mark(As.READ)
    default Optional<Integer> getSelectionIndex() {
        try {
            String index = new EnhancedSelect(webElement()).getFirstSelectedOption().getAttribute("index");
            return Optional.ofNullable(index).map(Integer::parseInt);
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

}
