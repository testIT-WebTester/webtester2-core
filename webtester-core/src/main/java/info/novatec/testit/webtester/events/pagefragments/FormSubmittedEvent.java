package info.novatec.testit.webtester.events.pagefragments;

import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.pagefragments.Form;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * This {@link Event event} occurs whenever a form is submitted.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see Form
 * @since 2.0
 */
public class FormSubmittedEvent extends AbstractPageFragmentEvent {

    public FormSubmittedEvent(PageFragment fragment) {
        super(fragment);
    }

    @Override
    public String describe() {
        return "submitted form: " + getPageFragmentName();
    }

    public static class Builder extends AbstractPageFragmentEventBuilder<FormSubmittedEvent> {

        @Override
        protected FormSubmittedEvent buildWith(PageFragment fragment) {
            return new FormSubmittedEvent(fragment);
        }

    }

}
