package info.novatec.testit.webtester.mouse;

import java.util.Collection;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import info.novatec.testit.webtester.events.pagefragments.ClickedEvent;
import info.novatec.testit.webtester.events.pagefragments.ContextClickedEvent;
import info.novatec.testit.webtester.events.pagefragments.DoubleClickedEvent;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * Implementations of this interface can be used to perform a variety of mouse related actions.
 *
 * @since 2.0
 */
public interface MouseDriver {

    /**
     * Executes a single-click on the given {@link PageFragment page fragment}.
     * Fires a {@link ClickedEvent}.
     * <p>
     * The actual behavior might vary between different {@link WebDriver} implementations. Some implementations might move
     * the actual mouse cursor, some might simulate the behavior.
     *
     * @param fragment the page fragment the click should be executed on
     * @see PageFragment
     * @see Actions#click(WebElement)
     * @since 2.0
     */
    void click(PageFragment fragment);

    /**
     * Executes a double-click on the given {@link PageFragment page fragment}.
     * Fires a {@link DoubleClickedEvent}.
     * <p>
     * The actual behavior might vary between different {@link WebDriver} implementations. Some implementations might move
     * the actual mouse cursor, some might simulate the behavior.
     *
     * @param fragment the page fragment the double click should be executed on
     * @see PageFragment
     * @see Actions#doubleClick(WebElement)
     * @since 2.0
     */
    void doubleClick(PageFragment fragment);

    /**
     * Executes a single context-click on the given {@link PageFragment page fragment}.
     * Fires a {@link ContextClickedEvent}.
     * <p>
     * The actual behavior might vary between different {@link WebDriver} implementations. Some implementations might move
     * the actual mouse cursor, some might simulate the behavior.
     *
     * @param fragment the page fragment the context-click should be executed on
     * @see PageFragment
     * @see Actions#contextClick(WebElement)
     * @since 2.0
     */
    void contextClick(PageFragment fragment);

    /**
     * Moves the mouse to each of the given {@link PageFragment page fragments} in the order they are given.
     * <p>
     * Before each move the fragment is first checked for visibility. Invisible or non existing fragments will lead to an
     * exception.
     * <p>
     * The actual behavior might vary between different {@link WebDriver} implementations. Some implementations might move
     * the actual mouse cursor, some might simulate the behavior.
     *
     * @param fragment the first page fragment the mouse should be moved to
     * @param fragments subsequent page fragments the mouse should be moved to in order
     * @throws TimeoutException if page object does not become visible in the configured amount of time
     * @see PageFragment
     * @see Actions#moveToElement(WebElement)
     * @since 2.0
     */
    void moveToEach(PageFragment fragment, PageFragment... fragments) throws TimeoutException;

    /**
     * Moves the mouse to each of the given {@link PageFragment page fragments} in the order.
     * <p>
     * Before each move the fragment is first checked for visibility. Invisible or non existing fragments will lead to an
     * exception.
     * <p>
     * The actual behavior might vary between different {@link WebDriver} implementations. Some implementations might move
     * the actual mouse cursor, some might simulate the behavior.
     *
     * @param fragments page fragments the mouse should be moved to in order
     * @throws TimeoutException if page object does not become visible in the configured amount of time
     * @see PageFragment
     * @see Actions#moveToElement(WebElement)
     * @since 2.0
     */
    void moveToEach(Collection<PageFragment> fragments) throws TimeoutException;

    /**
     * Moves the mouse to the given {@link PageFragment page fragment}.
     * <p>
     * Before the move the page object is first checked for visibility. An invisible or non existing page object will lead
     * to
     * an exception.
     * <p>
     * The actual behavior might vary between different {@link WebDriver} implementations. Some implementations might move
     * the actual mouse cursor, some might simulate the behavior.
     *
     * @param fragment the page fragment the mouse should be moved to
     * @throws TimeoutException if page object does not become visible in the configured amount of time
     * @see PageFragment
     * @see Actions#moveToElement(WebElement)
     * @since 2.0
     */
    void moveTo(PageFragment fragment) throws TimeoutException;

}
