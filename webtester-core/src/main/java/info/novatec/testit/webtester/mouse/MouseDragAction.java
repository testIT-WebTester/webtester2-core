package info.novatec.testit.webtester.mouse;

import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * Provides mouse actions that 'drag' a page fragment.
 * This is mainly used for readable fluent API calls.
 * <p>
 * <b>Examples:</b>
 * <pre>
 * Mouse.drag(image).to(otherImage);
 * </pre>
 *
 * @see Mouse
 * @see PageFragment
 * @since 2.0
 */
public class MouseDragAction {

    private final PageFragment sourceFragment;

    /**
     * Creates a new {@link MouseDragAction}.
     *
     * @param sourceFragment the fragment to use as a source
     * @see MouseDragAction
     * @since 2.0
     */
    public MouseDragAction(PageFragment sourceFragment) {
        this.sourceFragment = sourceFragment;
    }

    /**
     * Executes a {@link Mouse#doDragAndDrop(PageFragment, PageFragment)}} with the action's {@link PageFragment} as the
     * source and the given fragment as the target.
     *
     * @param targetFragment the fragment to move to
     * @return the same action for fluent API use
     * @see Mouse
     * @since 2.0
     */
    public MouseDragAction to(PageFragment targetFragment) {
        Mouse.dragAndDrop(sourceFragment, targetFragment);
        return this;
    }

}
