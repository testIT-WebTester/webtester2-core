package info.novatec.testit.webtester.events.browser;

import static java.lang.String.format;

import java.io.File;
import java.nio.file.Path;

import lombok.Getter;

import info.novatec.testit.webtester.browser.operations.PageSource;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.AbstractEvent;


/**
 * This {@link Event event} occurs whenever the source code of a page was saved.
 * <p>
 * It includes the file reference to the source code file as a property.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see PageSource#save()
 * @see PageSource#save(String, String)
 * @see PageSource#save(Path, String)
 * @see PageSource#save(File, String)
 * @see PageSource#save(String)
 * @see PageSource#save(Path)
 * @see PageSource#save(File)
 * @since 2.0
 */
@SuppressWarnings("serial")
@Getter
public class SavedSourceCodeEvent extends AbstractEvent {

    private final File pageSource;

    public SavedSourceCodeEvent(File pageSource) {
        this.pageSource = pageSource;
    }

    @Override
    public String describe() {
        return format("saved page source and saved it as: %s", pageSource);
    }

}
