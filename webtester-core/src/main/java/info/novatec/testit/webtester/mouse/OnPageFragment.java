package info.novatec.testit.webtester.mouse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * Provides mouse actions that can be executed 'on' a page fragment. This is mainly used for readable fluent API calls.
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
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class OnPageFragment {

    /** The {@link MouseDriver} to use when executing operations. */
    @NonNull
    private final MouseDriver mouseDriver;
    /** The {@link PageFragment} to use when executing operations. */
    @NonNull
    private final PageFragment fragment;

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
