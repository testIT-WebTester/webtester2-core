package info.novatec.testit.webtester.pagefragments;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.events.Produces;
import info.novatec.testit.webtester.events.pagefragments.ClearedEvent;
import info.novatec.testit.webtester.events.pagefragments.EnterPressedEvent;
import info.novatec.testit.webtester.events.pagefragments.TextAppendedEvent;
import info.novatec.testit.webtester.events.pagefragments.TextSetEvent;
import info.novatec.testit.webtester.pagefragments.annotations.Action;
import info.novatec.testit.webtester.pagefragments.annotations.Attribute;
import info.novatec.testit.webtester.pagefragments.annotations.Mapping;
import info.novatec.testit.webtester.pagefragments.annotations.Mark;
import info.novatec.testit.webtester.pagefragments.annotations.As;


@Mapping(tag = "input")
@SuppressWarnings("unchecked")
public interface GenericTextField<T extends GenericTextField> extends PageFragment {

    /**
     * Sets the text of this {@link GenericTextField text field} by first clearing it's value and then typing the given text
     * and fires a {@link TextSetEvent}.
     *
     * @param text the text to set
     * @return the same text field for fluent API use
     * @see GenericTextField
     * @see WebElement#clear()
     * @see WebElement#sendKeys(CharSequence...)
     * @since 2.0
     */
    @Action
    @Mark(As.USED)
    @Produces(TextSetEvent.class)
    default T setText(String text) {
        WebElement webElement = webElement();
        webElement.clear();
        webElement.sendKeys(text);
        return (T) this;
    }

    /**
     * Appends the text of this {@link GenericTextField text field} by typing the given text and fires a
     * {@link TextAppendedEvent}.
     *
     * @param text the text to append
     * @return the same text field for fluent API use
     * @see GenericTextField
     * @see WebElement#sendKeys(CharSequence...)
     * @since 2.0
     */
    @Action
    @Mark(As.USED)
    @Produces(TextAppendedEvent.class)
    default T appendText(String text) {
        webElement().sendKeys(text);
        return (T) this;
    }

    /**
     * Clears the text of this {@link GenericTextField text field} and fires a {@link ClearedEvent}.
     *
     * @return the same text field for fluent API use
     * @see GenericTextField
     * @see WebElement#clear()
     * @since 2.0
     */
    @Action
    @Mark(As.USED)
    @Produces(ClearedEvent.class)
    default T clear() {
        webElement().clear();
        return (T) this;
    }

    /**
     * Presses the <code>ENTER</code> key in the {@link GenericTextField text field} and fires a {@link EnterPressedEvent}.
     * Depending on the circumstances using this text field after executing this method might result in exceptions (i.e. when
     * pressing <code>ENTER</code> executes a page load).
     *
     * @see GenericTextField
     * @see WebElement#sendKeys(CharSequence...)
     * @see Keys#ENTER
     * @since 2.0
     */
    @Action
    @Mark(As.USED)
    @Produces(EnterPressedEvent.class)
    default void pressEnter() {
        webElement().sendKeys(Keys.ENTER);
    }

    /**
     * Returns the text of this {@link GenericTextField text field} by reading it's <code>value</code> attribute.
     *
     * @return the value of the <code>value</code> attribute
     * @see GenericTextField
     * @see Attribute
     * @since 2.0
     */
    @Mark(As.READ)
    @Attribute("value")
    String getText();

    @Override
    default String getVisibleText() {
        return getText();
    }

}
