package info.novatec.testit.webtester.mouse;

import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * Provides mouse actions that can be executed 'to' a page fragment.
 * This is mainly used for readable fluent API calls.
 * <p>
 * <b>Examples:</b>
 * <pre>
 * Mouse.to(link).move();
 * Mouse.to(image).dragAnDrop(otherImage);
 * </pre>
 *
 * @see Mouse
 * @see PageFragment
 * @since 2.0
 */
public class MouseToAction {

    private final PageFragment targetFragment;

    /**
     * Creates a new {@link MouseToAction}.
     *
     * @param targetFragment the fragment to use as a target
     * @see MouseToAction
     * @since 2.0
     */
    public MouseToAction(PageFragment targetFragment) {
        this.targetFragment = targetFragment;
    }

    /**
     * Executes a {@link Mouse#moveTo(PageFragment)} with the action's {@link PageFragment}.
     *
     * @return the same action for fluent API use
     * @see Mouse
     * @since 2.0
     */
    public MouseToAction move() {
        Mouse.moveTo(targetFragment);
        return this;
    }

    /**
     * Executes a {@link Mouse#doDragAndDrop(PageFragment, PageFragment)}} with the action's {@link PageFragment} as the
     * target and the given fragment as the source.
     *
     * @param sourceFragment the fragment to drag
     * @return the same action for fluent API use
     * @see Mouse
     * @since 2.0
     */
    public MouseToAction dragAndDrop(PageFragment sourceFragment) {
        Mouse.dragAndDrop(sourceFragment, targetFragment);
        return this;
    }

}
