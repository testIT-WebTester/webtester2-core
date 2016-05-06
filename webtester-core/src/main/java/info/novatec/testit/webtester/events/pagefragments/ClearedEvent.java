package info.novatec.testit.webtester.events.pagefragments;

import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.PasswordField;
import info.novatec.testit.webtester.pagefragments.TextArea;
import info.novatec.testit.webtester.pagefragments.TextField;


/**
 * This {@link Event event} occurs whenever a text of an element is cleared.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see TextField
 * @see TextArea
 * @see PasswordField
 * @since 2.0
 */
public class ClearedEvent extends AbstractPageFragmentEvent {

    public ClearedEvent(PageFragment fragment) {
        super(fragment);
    }

    @Override
    public String describe() {
        return "cleared: " + getPageFragmentName();
    }

    public static class Builder extends AbstractPageFragmentEventBuilder<ClearedEvent> {

        @Override
        protected ClearedEvent buildWith(PageFragment fragment) {
            return new ClearedEvent(fragment);
        }

    }

}
