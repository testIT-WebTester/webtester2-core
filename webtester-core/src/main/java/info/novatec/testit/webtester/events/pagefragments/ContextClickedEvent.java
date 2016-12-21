package info.novatec.testit.webtester.events.pagefragments;

import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.mouse.Mouse;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * This {@link Event event} occurs whenever a context click is executed on an element.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see Mouse
 * @since 2.0
 */
public class ContextClickedEvent extends AbstractPageFragmentEvent {

    public ContextClickedEvent(PageFragment fragment) {
        super(fragment);
    }

    @Override
    public String describe() {
        return "context clicked: " + getPageFragmentName();
    }

    public static class Builder extends AbstractPageFragmentEventBuilder<ContextClickedEvent> {

        @Override
        protected ContextClickedEvent buildWith(PageFragment fragment) {
            return new ContextClickedEvent(fragment);
        }

    }

}
