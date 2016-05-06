package info.novatec.testit.webtester.events;

import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.events.pagefragments.AbstractPageFragmentEvent;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * These builders are used to dynamically build events for {@link PageFragment page fragment} methods annotated with {@link
 * Produces}.
 *
 * @param <T> the type of the event the builder is building
 * @see Event
 * @see AbstractPageFragmentEvent
 * @see Produces
 * @see PageFragment
 * @since 2.0
 */
public interface PageFragmentEventBuilder<T extends AbstractPageFragmentEvent> {

    /**
     * Sets the {@link PageFragment page fragment} for the event.
     *
     * @param fragment the fragment of the event
     * @return the same builder instance for fluent API use
     * @see PageFragment
     * @since 2.0
     */
    PageFragmentEventBuilder<T> setPageFragment(PageFragment fragment);

    /**
     * Returns whether or not this builder needs "before" data. "Before" meaning before the execution of the method
     * producing the event.
     * <p>
     * In case this returns true the {@link #setBeforeData(WebElement)} is invoked!
     *
     * @return true if builder needs before data, otherwise false
     * @since 2.0
     */
    boolean needsBeforeData();

    /**
     * Set/extract data from the page fragment's web element before the method invocation.
     *
     * @param webElement the web element of the page fragment on which the method is executed on
     * @return the same builder instance for fluent API use
     * @see WebElement
     * @see PageFragmentEventBuilder
     * @since 2.0
     */
    PageFragmentEventBuilder<T> setBeforeData(WebElement webElement);

    /**
     * Returns whether or not this builder needs "after" data. "After" meaning after the execution of the method
     * producing the event.
     * <p>
     * In case this returns true the {@link #setAfterData(WebElement)} is invoked!
     *
     * @return true if builder needs after data, otherwise false
     * @since 2.0
     */
    boolean needsAfterData();

    /**
     * Set/extract data from the page fragment's web element after the method invocation.
     *
     * @param webElement the web element of the page fragment on which the method is executed on
     * @return the same builder instance for fluent API use
     * @see WebElement
     * @see PageFragmentEventBuilder
     * @since 2.0
     */
    PageFragmentEventBuilder<T> setAfterData(WebElement webElement);

    /**
     * Build the event.
     *
     * @return the build event
     * @since 2.0
     */
    T build();

}
