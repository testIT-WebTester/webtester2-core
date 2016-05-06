package info.novatec.testit.webtester.events.listeners;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.ExceptionEvent;


/**
 * This {@link EventListener event listener} takes a screenshot on each occurrence of an {@link ExceptionEvent exception
 * event} and stores it in the configured default screenshot folder.
 *
 * @see EventListener
 * @see Event
 * @see EventSystem
 * @since 2.0
 */
@Slf4j
public class TakeScreenshotOnExceptionListener implements EventListener {

    private final Browser browser;

    public TakeScreenshotOnExceptionListener(Browser browser) {
        this.browser = browser;
    }

    @Override
    public void eventOccurred(Event event) {
        if (isException(event)) {
            log.debug("taking screenshot because of exception event: {}", event);
            browser.screenshot().takeAndStore();
        }
    }

    private boolean isException(Event event) {
        return event instanceof ExceptionEvent;
    }

}
