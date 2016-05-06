package info.novatec.testit.webtester.mouse;

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
 * @see Mouse
 * @see PageFragment
 * @since 2.0
 */
public class MouseActionSequence {

    private PageFragment latestFragment;

    /**
     * Executes a {@link Mouse#click(PageFragment)} on the given {@link PageFragment}.
     *
     * @param fragment the fragment to click
     * @return the same action for fluent API use
     * @see Mouse
     * @since 2.0
     */
    public MouseActionSequence click(PageFragment fragment) {
        latestFragment = fragment;
        return click();
    }

    /**
     * Executes a {@link Mouse#click(PageFragment)} on the last known {@link PageFragment} of this sequence.
     * Throws an exception if there is no last known fragment in the sequence.
     *
     * @return the same action for fluent API use
     * @throws NullPointerException in case the is no last known fragment
     * @see Mouse
     * @since 2.0
     */
    public MouseActionSequence click() {
        Mouse.click(latestFragment);
        return this;
    }

    /**
     * Executes a {@link Mouse#doubleClick(PageFragment)} on the given {@link PageFragment}.
     *
     * @param fragment the fragment to double click
     * @return the same action for fluent API use
     * @see Mouse
     * @since 2.0
     */
    public MouseActionSequence doubleClick(PageFragment fragment) {
        latestFragment = fragment;
        return doubleClick();
    }

    /**
     * Executes a {@link Mouse#doubleClick(PageFragment)} on the last known {@link PageFragment} of this sequence.
     * Throws an exception if there is no last known fragment in the sequence.
     *
     * @return the same action for fluent API use
     * @throws NullPointerException in case the is no last known fragment
     * @see Mouse
     * @since 2.0
     */
    public MouseActionSequence doubleClick() {
        Mouse.doubleClick(latestFragment);
        return this;
    }

    /**
     * Executes a {@link Mouse#contextClick(PageFragment)} on the given {@link PageFragment}.
     *
     * @param fragment the fragment to context click
     * @return the same action for fluent API use
     * @see Mouse
     * @since 2.0
     */
    public MouseActionSequence contextClick(PageFragment fragment) {
        latestFragment = fragment;
        return contextClick();
    }

    /**
     * Executes a {@link Mouse#contextClick(PageFragment)} on the last known {@link PageFragment} of this sequence.
     * Throws an exception if there is no last known fragment in the sequence.
     *
     * @return the same action for fluent API use
     * @throws NullPointerException in case the is no last known fragment
     * @see Mouse
     * @since 2.0
     */
    public MouseActionSequence contextClick() {
        Mouse.contextClick(latestFragment);
        return this;
    }

    /**
     * Executes a {@link Mouse#moveTo(PageFragment)} to the given {@link PageFragment}.
     *
     * @param fragment the fragment to move to
     * @return the same action for fluent API use
     * @see Mouse
     * @since 2.0
     */
    public MouseActionSequence moveTo(PageFragment fragment) {
        latestFragment = fragment;
        Mouse.moveTo(fragment);
        return this;
    }

}
