package info.novatec.testit.webtester.pagefragments.traits;

import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.events.Produces;
import info.novatec.testit.webtester.events.pagefragments.ClickedEvent;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.annotations.Action;
import info.novatec.testit.webtester.pagefragments.annotations.As;
import info.novatec.testit.webtester.pagefragments.annotations.Mark;


/**
 * {@link PageFragment Page fragments} extending this trait are "clickable" i.e. buttons, links, radio buttons etc.
 *
 * @param <T> the type of the page fragment for this trait, used as return type for fluent API methods
 * @see PageFragment
 * @see WebElement#click()
 * @since 2.0
 */
public interface Clickable<T extends PageFragment> extends PageFragment {

    /**
     * Clicks the {@link PageFragment} and fires a {@link ClickedEvent}.
     *
     * @return the same page fragment instance for fluent API use
     * @see PageFragment
     * @see ClickedEvent
     * @see WebElement#click()
     * @since 2.0
     */
    @Action
    @Mark(As.USED)
    @Produces(ClickedEvent.class)
    @SuppressWarnings("unchecked")
    default T click() {
        webElement().click();
        return ( T ) this;
    }

}
