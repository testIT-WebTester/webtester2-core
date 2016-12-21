package info.novatec.testit.webtester.pagefragments;

import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.events.Produces;
import info.novatec.testit.webtester.events.pagefragments.SelectionChangedEvent;
import info.novatec.testit.webtester.pagefragments.annotations.Action;
import info.novatec.testit.webtester.pagefragments.annotations.As;
import info.novatec.testit.webtester.pagefragments.annotations.Mapping;
import info.novatec.testit.webtester.pagefragments.annotations.Mark;
import info.novatec.testit.webtester.pagefragments.traits.Selectable;


@Mapping(tag = "input", attribute = "type", values = "checkbox")
public interface Checkbox extends PageFragment, Selectable<Checkbox> {

    /**
     * Selects the {@link Checkbox checkbox's} and fires {@link SelectionChangedEvent} in case the selection has in fact
     * changed.
     * <p>
     * In case the checkbox is already selected, nothing is done.
     *
     * @return the same checkbox instance for fluent API use
     * @see Checkbox
     * @see SelectionChangedEvent
     * @see WebElement#isSelected()
     * @see WebElement#click()
     * @since 2.0
     */
    default Checkbox select() {
        return setSelection(true);
    }

    /**
     * Deselects the {@link Checkbox checkbox's} and fires a {@link SelectionChangedEvent} in case the selection has in fact
     * changed.
     * <p>
     * In case the checkbox is already not selected, nothing is done.
     *
     * @return the same checkbox instance for fluent API use
     * @see Checkbox
     * @see SelectionChangedEvent
     * @see WebElement#isSelected()
     * @see WebElement#click()
     * @since 2.0
     */
    default Checkbox deselect() {
        return setSelection(false);
    }

    /**
     * Sets the {@link Checkbox checkbox's} selection state to either true or false and fires {@link SelectionChangedEvent}
     * in case the selection has in fact changed.
     * <p>
     * Depending on the current selection state of the checkbox, this method might do nothing.
     *
     * @param selected the targeted selection state
     * @return the same checkbox instance for fluent API use
     * @see Checkbox
     * @see SelectionChangedEvent
     * @see WebElement#isSelected()
     * @see WebElement#click()
     * @since 2.0
     */
    @SuppressWarnings("unchecked")
    default Checkbox setSelection(boolean selected) {
        if (isSelected() != selected) {
            click();
        }
        return this;
    }

    /**
     * Executes a click on the {@link Checkbox checkbox's} effectively changing its selection state and fires a {@link
     * SelectionChangedEvent}.
     *
     * @return the same checkbox instance for fluent API use
     * @see Checkbox
     * @see SelectionChangedEvent
     * @see WebElement#click()
     * @since 2.0
     */
    @Action
    @Mark(As.USED)
    @Produces(SelectionChangedEvent.class)
    @SuppressWarnings("unchecked")
    default Checkbox click() {
        webElement().click();
        return this;
    }

}
