package info.novatec.testit.webtester.events.pagefragments;

import lombok.Getter;

import info.novatec.testit.webtester.events.AbstractEvent;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.mouse.Mouse;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * This {@link Event event} occurs whenever one element is dragged and dropped onto another element.
 * <p>
 * It contains the names of both elements as properties.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see Mouse
 * @since 2.0
 */
@Getter
public class DraggedAndDroppedEvent extends AbstractEvent {

    private final String sourceFragmentName;
    private final String targetFragmentName;

    public DraggedAndDroppedEvent(PageFragment sourceFragment, PageFragment targetFragment) {
        this.sourceFragmentName = sourceFragment.getName().orElse("unknown");
        this.targetFragmentName = targetFragment.getName().orElse("unknown");
    }

    @Override
    public String describe() {
        return "dragged '" + sourceFragmentName + "' to '" + targetFragmentName + "'";
    }

}
