package info.novatec.testit.webtester.mouse;

import lombok.AccessLevel;
import lombok.Getter;

import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * Provides the means of creating a sequence of mouse actions.
 * This is mainly used for readable fluent API calls.
 * <p>
 * <b>Examples:</b>
 * <pre>
 * Mouse.sequence().moveTo(image).click();
 * Mouse.sequence().moveTo(image).moveTo(otherImage).click();
 * Mouse.sequence().click(image).doubleClick(otherImage);
 * </pre>
 *
 * @see MouseDriver
 * @see PageFragment
 * @see SequenceWithFragment
 * @since 2.0
 */
@Getter(AccessLevel.PACKAGE)
public class Sequence {

    /** The {@link MouseDriver} to use when executing operations. */
    private final MouseDriver mouseDriver;

    /**
     * Creates a new instance for the given {@link MouseDriver}.
     *
     * @param mouseDriver the driver to use
     * @since 2.0
     */
    Sequence(MouseDriver mouseDriver) {
        this.mouseDriver = mouseDriver;
    }

    /**
     * Executes a {@link Mouse#click(PageFragment)} on the given {@link PageFragment} and returns a new {@link
     * SequenceWithFragment} instance for further use.
     *
     * @param fragment the fragment to click
     * @return the new instance
     * @see SequenceWithFragment
     * @see Mouse
     * @since 2.0
     */
    public SequenceWithFragment click(PageFragment fragment) {
        return new SequenceWithFragment(mouseDriver, fragment).click();
    }

    /**
     * Executes a {@link Mouse#doubleClick(PageFragment)} on the given {@link PageFragment} and returns a new {@link
     * SequenceWithFragment} instance for further use.
     *
     * @param fragment the fragment to double click
     * @return the new instance
     * @see SequenceWithFragment
     * @see Mouse
     * @since 2.0
     */
    public SequenceWithFragment doubleClick(PageFragment fragment) {
        return new SequenceWithFragment(mouseDriver, fragment).doubleClick();
    }

    /**
     * Executes a {@link Mouse#contextClick(PageFragment)} on the given {@link PageFragment} and returns a new {@link
     * SequenceWithFragment} instance for further use.
     *
     * @param fragment the fragment to context click
     * @return the new instance
     * @see SequenceWithFragment
     * @see Mouse
     * @since 2.0
     */
    public SequenceWithFragment contextClick(PageFragment fragment) {
        return new SequenceWithFragment(mouseDriver, fragment).contextClick();
    }

    /**
     * Executes a {@link Mouse#moveTo(PageFragment)} to the given {@link PageFragment} and returns a new {@link
     * SequenceWithFragment} instance for further use.
     *
     * @param fragment the fragment to move to
     * @return the new instance
     * @see SequenceWithFragment
     * @see Mouse
     * @since 2.0
     */
    public SequenceWithFragment moveTo(PageFragment fragment) {
        mouseDriver.moveTo(fragment);
        return new SequenceWithFragment(mouseDriver, fragment);
    }

}
