package info.novatec.testit.webtester.mouse;

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
public class MouseOnAction {

    private final PageFragment fragment;

    /**
     * Creates a new {@link MouseOnAction}.
     *
     * @param fragment the fragment to use as a base
     * @see MouseOnAction
     * @since 2.0
     */
    public MouseOnAction(PageFragment fragment) {
        this.fragment = fragment;
    }

    /**
     * Executes a {@link Mouse#click(PageFragment)} with the action's {@link PageFragment}.
     *
     * @return the same action for fluent API use
     * @see Mouse
     * @since 2.0
     */
    public MouseOnAction click() {
        Mouse.click(fragment);
        return this;
    }

    /**
     * Executes a {@link Mouse#doubleClick(PageFragment)} with the action's {@link PageFragment}.
     *
     * @return the same action for fluent API use
     * @see Mouse
     * @since 2.0
     */
    public MouseOnAction doubleClick() {
        Mouse.doubleClick(fragment);
        return this;
    }

    /**
     * Executes a {@link Mouse#contextClick(PageFragment)} with the action's {@link PageFragment}.
     *
     * @return the same action for fluent API use
     * @see Mouse
     * @since 2.0
     */
    public MouseOnAction contextClick() {
        Mouse.contextClick(fragment);
        return this;
    }

}
