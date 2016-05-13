package info.novatec.testit.webtester.mouse;

import static info.novatec.testit.webtester.conditions.Conditions.visible;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.BiFunction;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.google.common.annotations.Beta;

import info.novatec.testit.webtester.events.pagefragments.ClickedEvent;
import info.novatec.testit.webtester.events.pagefragments.ContextClickedEvent;
import info.novatec.testit.webtester.events.pagefragments.DoubleClickedEvent;
import info.novatec.testit.webtester.events.pagefragments.DraggedAndDroppedEvent;
import info.novatec.testit.webtester.internal.ActionTemplate;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.waiting.Wait;


/**
 * This class is used to perform a variety of mouse related actions.
 *
 * @see MouseOnAction
 * @see MouseDragAction
 * @see MouseActionSequence
 * @since 2.0
 */
public final class Mouse {

    /* click with mouse */

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
    public static void click(PageFragment fragment) {
        ActionTemplate.pageFragment(fragment).execute(Mouse::doClick).fireEvent(ClickedEvent::new).markAsUsed();
    }

    private static void doClick(PageFragment fragment) {
        perform(fragment, Actions::click);
    }

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
    public static void doubleClick(PageFragment fragment) {
        ActionTemplate.pageFragment(fragment).execute(Mouse::doDoubleClick).fireEvent(DoubleClickedEvent::new).markAsUsed();
    }

    private static void doDoubleClick(PageFragment fragment) {
        perform(fragment, Actions::doubleClick);
    }

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
    public static void contextClick(PageFragment fragment) {
        ActionTemplate.pageFragment(fragment)
            .execute(Mouse::doContextClick)
            .fireEvent(ContextClickedEvent::new)
            .markAsUsed();
    }

    private static void doContextClick(PageFragment fragment) {
        perform(fragment, Actions::contextClick);
    }

    /* move mouse */

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
    public static void moveToEach(PageFragment fragment, PageFragment... fragments) throws TimeoutException {
        moveTo(fragment);
        moveToEach(Arrays.asList(fragments));
    }

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
    public static void moveToEach(Collection<PageFragment> fragments) throws TimeoutException {
        fragments.forEach(Mouse::moveTo);
    }

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
     * @see Actions#moveToElement(org.openqa.selenium.WebElement)
     * @since 2.0
     */
    public static void moveTo(PageFragment fragment) throws TimeoutException {
        ActionTemplate.pageFragment(fragment).execute(Mouse::doMoveTo);
    }

    private static void doMoveTo(PageFragment fragment) {
        Wait.until(fragment).is(visible());
        perform(fragment, Actions::moveToElement);
    }

    /* drag and drop */

    /**
     * Drags the given source {@link PageFragment page fragment} onto the given target {@link PageFragment page fragment}.
     * Fires a {@link DraggedAndDroppedEvent}.
     * <p>
     * The actual behavior might vary between different {@link WebDriver} implementations. Some implementations might move
     * the actual mouse cursor, some might simulate the behavior.
     * <p>
     * This might not work with all drivers!
     *
     * @param sourceFragment the fragment being dragged
     * @param targetFragment the fragment the source is dragged onto
     * @see PageFragment
     * @see Actions#dragAndDrop(WebElement, WebElement)
     * @since 2.0
     */
    @Beta
    public static void dragAndDrop(PageFragment sourceFragment, PageFragment targetFragment) {
        ActionTemplate.pageFragments(sourceFragment, targetFragment)
            .execute(Mouse::doDragAndDrop)
            .fireEvent(DraggedAndDroppedEvent::new)
            .markAsUsed();
    }

    private static void doDragAndDrop(PageFragment sourceFragment, PageFragment targetFragment) {
        sequenceFor(sourceFragment).dragAndDrop(sourceFragment.webElement(), targetFragment.webElement()).perform();
    }

    private static void perform(PageFragment fragment, BiFunction<Actions, WebElement, Actions> biConsumer) {
        biConsumer.apply(sequenceFor(fragment), fragment.webElement()).perform();
    }

    private static Actions sequenceFor(PageFragment fragment) {
        return new Actions(fragment.getBrowser().webDriver());
    }

    /* fluent API factories */

    /**
     * Creates a new {@link MouseOnAction} for the given {@link PageFragment}.
     *
     * @param fragment the page fragment to use
     * @return the created action
     * @since 2.0
     */
    public static MouseOnAction on(PageFragment fragment) {
        return new MouseOnAction(fragment);
    }

    /**
     * Creates a new {@link MouseDragAction} for the given {@link PageFragment}.
     *
     * @param fragment the page fragment to use
     * @return the created action
     * @since 2.0
     */
    public static MouseDragAction drag(PageFragment fragment) {
        return new MouseDragAction(fragment);
    }

    /**
     * Creates a new {@link MouseActionSequence}.
     *
     * @return the created action
     * @since 2.0
     */
    public static MouseActionSequence sequence() {
        return new MouseActionSequence();
    }

    private Mouse() {
        // utility class constructor
    }

}
