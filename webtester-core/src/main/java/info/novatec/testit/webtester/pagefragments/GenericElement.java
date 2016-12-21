package info.novatec.testit.webtester.pagefragments;

import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.adhoc.AdHocFinder;
import info.novatec.testit.webtester.events.Produces;
import info.novatec.testit.webtester.events.pagefragments.ClearedEvent;
import info.novatec.testit.webtester.events.pagefragments.FormSubmittedEvent;
import info.novatec.testit.webtester.internal.PageFragmentFactory;
import info.novatec.testit.webtester.pagefragments.annotations.Action;
import info.novatec.testit.webtester.pagefragments.annotations.As;
import info.novatec.testit.webtester.pagefragments.annotations.Mark;
import info.novatec.testit.webtester.pagefragments.traits.Clickable;
import info.novatec.testit.webtester.pagefragments.traits.Selectable;


/**
 * This {@link PageFragment} is intended to be used in cases where the functional type of a {@link WebElement} does not
 * matter.
 * <p>
 * It offers all of the operations of a {@link WebElement} we normally hide in cases they don't apply for the functional
 * class.
 *
 * @see PageFragment
 * @see Clickable
 * @see Selectable
 * @see WebElement
 * @see AdHocFinder
 * @since 2.0
 */
public interface GenericElement extends PageFragment, Clickable<GenericElement>, Selectable<GenericElement> {

    /**
     * If this {@link GenericElement} is a form or an element within a form, the form will be submitted.
     * In case of a successful submission a {@link FormSubmittedEvent} will be fired.
     *
     * @return the same element for fluent API use
     * @see GenericElement
     * @see WebElement#submit()
     * @see FormSubmittedEvent
     * @since 2.0
     */
    @Action
    @Produces(FormSubmittedEvent.class)
    default GenericElement submit() {
        webElement().submit();
        return this;
    }

    /**
     * Sends the given keys to the underlying {@link WebElement}.
     *
     * @param keysToSend the keys to send
     * @return the same element for fluent API use
     * @see GenericElement
     * @see WebElement#sendKeys(CharSequence...)
     * @since 2.0
     */
    @Action
    @Mark(As.USED)
    default GenericElement sendKeys(CharSequence... keysToSend) {
        webElement().sendKeys(keysToSend);
        return this;
    }

    /**
     * Clears the underlying {@link WebElement}.
     *
     * @return the same element for fluent API use
     * @see GenericElement
     * @see WebElement#clear()
     * @since 2.0
     */
    @Action
    @Mark(As.USED)
    @Produces(ClearedEvent.class)
    default GenericElement clear() {
        webElement().clear();
        return this;
    }

    /**
     * Creates a {@link PageFragment} of the given type to wrap this {@link GenericElement}'s {@link WebElement}.
     *
     * @param fragmentClass the class of the page fragment to be created
     * @param <T> the page fragment's type
     * @return the newly created page fragment
     * @see PageFragment
     * @see GenericElement
     * @see AdHocFinder
     * @see WebElement
     * @since 2.0
     */
    default <T extends PageFragment> T as(Class<T> fragmentClass) {
        return new PageFragmentFactory(browser()).pageFragment(fragmentClass, webElement());
    }

}
