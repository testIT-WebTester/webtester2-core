package info.novatec.testit.webtester.events.pagefragments;

import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.pagefragments.MultiSelect;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * This {@link Event event} occurs whenever deselect all operation is executed.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see MultiSelect
 * @since 2.0
 */
public class DeselectedAllEvent extends AbstractPageFragmentEvent {

    public DeselectedAllEvent(PageFragment fragment) {
        super(fragment);
    }

    @Override
    public String describe() {
        return "deselected all options of: " + getPageFragmentName();
    }

    public static class Builder extends AbstractPageFragmentEventBuilder<DeselectedAllEvent> {

        @Override
        protected DeselectedAllEvent buildWith(PageFragment fragment) {
            return new DeselectedAllEvent(fragment);
        }

    }

}
