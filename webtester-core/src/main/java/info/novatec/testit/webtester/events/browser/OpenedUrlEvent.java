package info.novatec.testit.webtester.events.browser;

import static java.lang.String.format;

import java.net.URL;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import info.novatec.testit.webtester.browser.operations.UrlOpener;
import info.novatec.testit.webtester.events.AbstractEvent;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;


/**
 * This {@link Event event} occurs whenever an URL was opened (navigated to).
 * <p>
 * It includes the URL as a property.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see UrlOpener#url(String)
 * @see UrlOpener#url(URL)
 * @see UrlOpener#url(String, Class)
 * @see UrlOpener#url(URL, Class)
 * @see UrlOpener#defaultEntryPoint()
 * @see UrlOpener#defaultEntryPoint(Class)
 * @since 2.0
 */
@Getter
@AllArgsConstructor
@SuppressWarnings("serial")
public class OpenedUrlEvent extends AbstractEvent {
    
    @NonNull
    private final String url;

    @Override
    public String describe() {
        return format("opened url: '%s'", url);
    }

}
