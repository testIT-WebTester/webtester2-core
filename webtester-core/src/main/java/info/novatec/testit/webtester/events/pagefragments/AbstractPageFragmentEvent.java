package info.novatec.testit.webtester.events.pagefragments;

import java.util.Optional;

import org.apache.commons.lang.StringUtils;

import info.novatec.testit.webtester.events.AbstractEvent;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * Base class for all {@link PageFragment page fragment} related events.
 * <p>
 * It stores the fragment's given name as a property.
 *
 * @see AbstractEvent
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @since 2.0
 */
public abstract class AbstractPageFragmentEvent extends AbstractEvent {

    private final String pageFragmentName;

    protected AbstractPageFragmentEvent(PageFragment fragment) {
        this.pageFragmentName = fragment.getName().orElse("unknown");
    }

    /**
     * The optional name of the page fragment this event relates to.
     * Will be empty in case of the name being blank.
     *
     * @return the optional name
     */
    public Optional<String> getPageFragmentName() {
        return Optional.ofNullable(pageFragmentName).filter(StringUtils::isNotBlank);
    }

}
