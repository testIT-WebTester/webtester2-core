package info.novatec.testit.webtester.events.pagefragments;

import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * This {@link Event event} occurs whenever ENTER is pressed.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @since 2.0
 */
public class EnterPressedEvent extends AbstractPageFragmentEvent {

    public EnterPressedEvent(PageFragment fragment) {
        super(fragment);
    }

    @Override
    public String describe() {
        return "pressed ENTER on: " + getPageFragmentName();
    }

    public static class Builder extends AbstractPageFragmentEventBuilder<EnterPressedEvent> {

        @Override
        protected EnterPressedEvent buildWith(PageFragment fragment) {
            return new EnterPressedEvent(fragment);
        }

    }

}
