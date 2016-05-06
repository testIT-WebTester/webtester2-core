package info.novatec.testit.webtester.events.pagefragments;

import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.mouse.Mouse;


/**
 * This {@link Event event} occurs whenever a double click is executed on an element.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see Mouse
 * @since 2.0
 */
public class DoubleClickedEvent extends AbstractPageFragmentEvent {

    public DoubleClickedEvent(PageFragment fragment) {
        super(fragment);
    }

    @Override
    public String describe() {
        return "double clicked: " + getPageFragmentName();
    }

    public static class Builder extends AbstractPageFragmentEventBuilder<DoubleClickedEvent> {

        @Override
        protected DoubleClickedEvent buildWith(PageFragment fragment) {
            return new DoubleClickedEvent(fragment);
        }

    }

}
