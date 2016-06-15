package info.novatec.testit.webtester.events.browser;

import static java.lang.String.format;

import lombok.Getter;

import info.novatec.testit.webtester.browser.operations.FocusSetter;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.AbstractEvent;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * This {@link Event event} occurs whenever a switch to another frame occurred.
 * <p>
 * It includes the name / ID, index or page fragment name of that target as a property.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see FocusSetter#onFrame(int)
 * @see FocusSetter#onFrame(String)
 * @see FocusSetter#onFrame(PageFragment)
 * @since 2.0
 */
@SuppressWarnings("serial")
@Getter
public class SwitchedToFrameEvent extends AbstractEvent {

    private final String target;

    public SwitchedToFrameEvent(String nameOrId) {
        this.target = nameOrId;
    }

    public SwitchedToFrameEvent(int index) {
        this.target = String.valueOf(index);
    }

    public SwitchedToFrameEvent(PageFragment frame) {
        this.target = frame.getName().orElse("unknown");
    }

    @Override
    public String describe() {
        return format("switched to frame using: %s", target);
    }

}
