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
 * @since 2.0
 */
@Getter(AccessLevel.PACKAGE)
public class SequenceWithFragment extends Sequence {

    /** The latest {@link PageFragment} to be interacted with. Is used for operations without parameters. */
    private final PageFragment fragment;

    /**
     * Creates a new instance for the given {@link MouseDriver}.
     *
     * @param mouseDriver the driver to use
     * @since 2.0
     */
    SequenceWithFragment(MouseDriver mouseDriver, PageFragment fragment) {
        super(mouseDriver);
        this.fragment = fragment;
    }

    /**
     * Executes a {@link Mouse#click(PageFragment)} on the {@link PageFragment} of this sequence.
     *
     * @return the same instance for fluent API use
     * @see SequenceWithFragment
     * @see Mouse
     * @since 2.0
     */
    public SequenceWithFragment click() {
        getMouseDriver().click(fragment);
        return this;
    }

    /**
     * Executes a {@link Mouse#doubleClick(PageFragment)} on the {@link PageFragment} of this sequence.
     *
     * @return the same instance for fluent API use
     * @see SequenceWithFragment
     * @see Mouse
     * @since 2.0
     */
    public SequenceWithFragment doubleClick() {
        getMouseDriver().doubleClick(fragment);
        return this;
    }

    /**
     * Executes a {@link Mouse#contextClick(PageFragment)} on the {@link PageFragment} of this sequence.
     *
     * @return the same instance for fluent API use
     * @see SequenceWithFragment
     * @see Mouse
     * @since 2.0
     */
    public SequenceWithFragment contextClick() {
        getMouseDriver().contextClick(fragment);
        return this;
    }

}
