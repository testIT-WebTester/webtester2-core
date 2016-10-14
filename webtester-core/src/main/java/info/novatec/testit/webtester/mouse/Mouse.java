package info.novatec.testit.webtester.mouse;

import java.util.Collection;
import java.util.function.Supplier;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import lombok.Setter;
import lombok.experimental.UtilityClass;

import info.novatec.testit.webtester.events.pagefragments.ClickedEvent;
import info.novatec.testit.webtester.events.pagefragments.ContextClickedEvent;
import info.novatec.testit.webtester.events.pagefragments.DoubleClickedEvent;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * This class is used to perform a variety of mouse related actions.
 *
 * @see OnPageFragment
 * @see Sequence
 * @since 2.0
 */
@UtilityClass
public class Mouse {

    /** The default {@link MouseDriver} supplier. Generates a new {@link DefaultMouseDriver} for each call. */
    public static final Supplier<MouseDriver> DEFAULT_MOUSE_DRIVER = DefaultMouseDriver::new;

    /**
     * A supplier used to get a {@link MouseDriver} instance to use when executing any operations.
     * The supplier can be changed externally to customize the behavior.
     * Since this is a static field you should keep in mind that this will have an JVM global effect!
     * <p>
     * The default supplier is {@link #DEFAULT_MOUSE_DRIVER}.
     */
    @Setter
    private static Supplier<MouseDriver> mouseDriver = DEFAULT_MOUSE_DRIVER;

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
        mouseDriver.get().click(fragment);
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
        mouseDriver.get().doubleClick(fragment);
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
        mouseDriver.get().contextClick(fragment);
    }

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
        mouseDriver.get().moveToEach(fragment, fragments);
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
        mouseDriver.get().moveToEach(fragments);
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
        mouseDriver.get().moveTo(fragment);
    }

    /**
     * Creates a new {@link OnPageFragment} for the given {@link PageFragment}.
     *
     * @param fragment the page fragment to use
     * @return the new instance
     * @since 2.0
     */
    public static OnPageFragment on(PageFragment fragment) {
        return new OnPageFragment(mouseDriver.get(), fragment);
    }

    /**
     * Creates a new {@link Sequence}.
     *
     * @return the new instance
     * @since 2.0
     */
    public static Sequence sequence() {
        return new Sequence(mouseDriver.get());
    }

}
