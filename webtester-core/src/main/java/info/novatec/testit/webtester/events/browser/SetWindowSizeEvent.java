package info.novatec.testit.webtester.events.browser;

import lombok.Getter;

import info.novatec.testit.webtester.browser.operations.Window;
import info.novatec.testit.webtester.events.AbstractEvent;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;


/**
 * This {@link Event event} occurs whenever the size of a window was set.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see Window#setSize(int, int)
 * @since 2.0
 */
@Getter
@SuppressWarnings("serial")
public class SetWindowSizeEvent extends AbstractEvent {

    private final int width;
    private final int height;

    public SetWindowSizeEvent(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public String describe() {
        return "set window position: width=" + width + ", height=" + height;
    }

}
