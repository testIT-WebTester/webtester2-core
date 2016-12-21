package info.novatec.testit.webtester.events.pagefragments;

import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.mouse.Mouse;
import info.novatec.testit.webtester.pagefragments.Button;
import info.novatec.testit.webtester.pagefragments.Image;
import info.novatec.testit.webtester.pagefragments.Link;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.traits.Clickable;


/**
 * This {@link Event event} occurs whenever a click is executed on a page fragment.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see Clickable
 * @see Button
 * @see Link
 * @see Image
 * @see Mouse
 * @since 2.0
 */
public class ClickedEvent extends AbstractPageFragmentEvent {

    public ClickedEvent(PageFragment fragment) {
        super(fragment);
    }

    @Override
    public String describe() {
        return "clicked: " + getPageFragmentName();
    }

    public static class Builder extends AbstractPageFragmentEventBuilder<ClickedEvent> {

        @Override
        protected ClickedEvent buildWith(PageFragment fragment) {
            return new ClickedEvent(fragment);
        }

    }

}
