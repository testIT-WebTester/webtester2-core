package info.novatec.testit.webtester.events.browser;

import lombok.Getter;

import info.novatec.testit.webtester.browser.operations.CurrentWindow;
import info.novatec.testit.webtester.events.AbstractEvent;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;


/**
 * This {@link Event event} occurs whenever the position of a window was set.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see CurrentWindow#setPosition(int, int)
 * @since 2.0
 */
@Getter
@SuppressWarnings("serial")
public class SetWindowPositionEvent extends AbstractEvent {

    private final int x;
    private final int y;

    public SetWindowPositionEvent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String describe() {
        return "set window position: X=" + x + ", Y=" + y;
    }

}
