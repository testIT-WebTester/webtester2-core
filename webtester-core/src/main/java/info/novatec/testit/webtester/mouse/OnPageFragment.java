package info.novatec.testit.webtester.mouse;

import lombok.AccessLevel;
import lombok.Getter;

import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * Provides mouse actions that can be executed 'on' a page fragment.
 * This is mainly used for readable fluent API calls.
 * <p>
 * <b>Examples:</b>
 * <pre>
 * Mouse.on(button).click();
 * Mouse.on(image).doubleClick();
 * Mouse.on(image).contextClick();
 * </pre>
 *
 * @see Mouse
 * @see PageFragment
 * @since 2.0
 */
@Getter(AccessLevel.PACKAGE)
public class OnPageFragment {

    /** The {@link MouseDriver} to use when executing operations. */
    private final MouseDriver mouseDriver;
    /** The {@link PageFragment} to use when executing operations. */
    private final PageFragment fragment;

    /**
     * Creates a new {@link OnPageFragment} for the given {@link MouseDriver} and {@link PageFragment}.
     *
     * @param mouseDriver the driver to use
     * @param fragment the fragment to use as a base
     * @see OnPageFragment
     * @since 2.0
     */
    OnPageFragment(MouseDriver mouseDriver, PageFragment fragment) {
        this.mouseDriver = mouseDriver;
        this.fragment = fragment;
    }

    /**
     * Executes a {@link Mouse#click(PageFragment)} with the action's {@link PageFragment}.
     *
     * @return the same instance for fluent API use
     * @see Mouse
     * @since 2.0
     */
    public OnPageFragment click() {
        mouseDriver.click(fragment);
        return this;
    }

    /**
     * Executes a {@link Mouse#doubleClick(PageFragment)} with the action's {@link PageFragment}.
     *
     * @return the same instance for fluent API use
     * @see Mouse
     * @since 2.0
     */
    public OnPageFragment doubleClick() {
        mouseDriver.doubleClick(fragment);
        return this;
    }

    /**
     * Executes a {@link Mouse#contextClick(PageFragment)} with the action's {@link PageFragment}.
     *
     * @return the same instance for fluent API use
     * @see Mouse
     * @since 2.0
     */
    public OnPageFragment contextClick() {
        mouseDriver.contextClick(fragment);
        return this;
    }

}
