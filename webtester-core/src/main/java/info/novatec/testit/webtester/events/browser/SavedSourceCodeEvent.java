package info.novatec.testit.webtester.events.browser;

import static java.lang.String.format;

import java.io.File;
import java.nio.file.Path;

import lombok.Getter;

import info.novatec.testit.webtester.browser.operations.PageSourceSaver;
import info.novatec.testit.webtester.events.AbstractEvent;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;


/**
 * This {@link Event event} occurs whenever the source code of a page was saved.
 * <p>
 * It includes the file reference to the source code file as a property.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see PageSourceSaver#save()
 * @see PageSourceSaver#save(String, String)
 * @see PageSourceSaver#save(Path, String)
 * @see PageSourceSaver#save(File, String)
 * @see PageSourceSaver#save(String)
 * @see PageSourceSaver#save(Path)
 * @see PageSourceSaver#save(File)
 * @since 2.0
 */
@Getter
@SuppressWarnings("serial")
public class SavedSourceCodeEvent extends AbstractEvent {

    private final File pageSource;

    public SavedSourceCodeEvent(File pageSource) {
        this.pageSource = pageSource;
    }

    @Override
    public String describe() {
        return format("saved page source and stored it as: '%s'", pageSource);
    }

}
