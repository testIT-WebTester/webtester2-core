package info.novatec.testit.webtester.browser.operations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebDriver;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.browser.SavedSourceCodeEvent;


/**
 * This browser operation offers methods related to saving the current pages source code.
 *
 * @see #get()
 * @see #save()
 * @see #save(String)
 * @see #save(File)
 * @see #save(Path)
 * @see #save(String, String)
 * @see #save(File, String)
 * @see #save(Path, String)
 * @since 2.0
 */
@Slf4j
public class PageSourceSaver extends BaseBrowserOperation {

    /**
     * Creates a new {@link PageSourceSaver} for the given {@link Browser}.
     *
     * @param browser the browser to use
     * @since 2.0
     */
    public PageSourceSaver(Browser browser) {
        super(browser);
    }

    /**
     * Returns the currently displayed page's source code as a string.
     *
     * @return the current page's source code
     * @see WebDriver#getPageSource()
     * @since 2.0
     */
    public String get() {
        return StringUtils.defaultString(webDriver().getPageSource());
    }

    /**
     * Save the current page's source code to a new file within the configured default source code folder
     * {@link Configuration#getPageSourceFolder()}. The generated name of the file will consist of the current
     * local date/time.
     * <p>
     * Example: 2016-01-03T10_15_30.201.html
     * <p>
     * This operation is considered "optional" any exceptions occurring during its execution will be logged,
     * but not rethrown!
     *
     * @return a file reference to the newly saved page source file
     * @see WebDriver#getPageSource()
     * @since 2.0
     */
    public Optional<File> save() {
        File defaultFolder = configuration().getPageSourceFolder();
        return save(defaultFolder.getAbsolutePath());
    }

    /**
     * Save the current page's source code to a new file within the given folder.
     * The generated name of the file will consist of the current local date/time.
     * <p>
     * Example: 2016-01-03T10_15_30.201.html
     * <p>
     * This operation is considered "optional" any exceptions occurring during its execution will be logged,
     * but not rethrown!
     *
     * @param targetFolderPath the path to the folder as a string
     * @return a file reference to the newly saved page source file
     * @see WebDriver#getPageSource()
     * @since 2.0
     */
    public Optional<File> save(String targetFolderPath) {
        return save(targetFolderPath, generateFileName());
    }

    /**
     * Save the current page's source code to a new file within the given folder.
     * The generated name of the file will consist of the current local date/time.
     * <p>
     * Example: 2016-01-03T10_15_30.201.html
     * <p>
     * This operation is considered "optional" any exceptions occurring during its execution will be logged,
     * but not rethrown!
     *
     * @param targetFolderPath the path to the folder as a Path
     * @return a file reference to the newly saved page source file
     * @see WebDriver#getPageSource()
     * @since 2.0
     */
    public Optional<File> save(Path targetFolderPath) {
        return save(targetFolderPath, generateFileName());
    }

    /**
     * Save the current page's source code to a new file within the given folder.
     * The generated name of the file will consist of the current local date/time.
     * <p>
     * Example: 2016-01-03T10_15_30.201.html
     * <p>
     * This operation is considered "optional" any exceptions occurring during its execution will be logged,
     * but not rethrown!
     *
     * @param targetFolder the folder
     * @return a file reference to the newly saved page source file
     * @see WebDriver#getPageSource()
     * @since 2.0
     */
    public Optional<File> save(File targetFolder) {
        return save(targetFolder, generateFileName());
    }

    /**
     * Save the current page's source code to a new file within the given folder.
     * The file's name can also be defined. But the file's suffix will always be ".html".
     * <p>
     * This operation is considered "optional" any exceptions occurring during its execution will be logged,
     * but not rethrown!
     *
     * @param targetFolderPath the path to the folder as a string
     * @param fileNameWithoutSuffix the file name without any file suffix
     * @return a file reference to the newly saved page source file
     * @see WebDriver#getPageSource()
     * @since 2.0
     */
    public Optional<File> save(String targetFolderPath, String fileNameWithoutSuffix) {
        return save(new File(targetFolderPath), fileNameWithoutSuffix);
    }

    /**
     * Save the current page's source code to a new file within the given folder.
     * The file's name can also be defined. But the file's suffix will always be ".html".
     * <p>
     * This operation is considered "optional" any exceptions occurring during its execution will be logged,
     * but not rethrown!
     *
     * @param targetFolderPath the path to the folder as a Path
     * @param fileNameWithoutSuffix the file name without any file suffix
     * @return a file reference to the newly saved page source file
     * @see WebDriver#getPageSource()
     * @since 2.0
     */
    public Optional<File> save(Path targetFolderPath, String fileNameWithoutSuffix) {
        return save(targetFolderPath.toFile(), fileNameWithoutSuffix);
    }

    /**
     * Save the current page's source code to a new file within the given folder.
     * The file's name can also be defined. But the file's suffix will always be ".html".
     * <p>
     * This operation is considered "optional" any exceptions occurring during its execution will be logged,
     * but not rethrown!
     *
     * @param targetFolder the folder
     * @param fileNameWithoutSuffix the file name without any file suffix
     * @return a file reference to the newly saved page source file
     * @see WebDriver#getPageSource()
     * @since 2.0
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public Optional<File> save(File targetFolder, String fileNameWithoutSuffix) {
        File file = null;
        try {
            file = doSavePageSource(targetFolder, fileNameWithoutSuffix);
        } catch (IOException | RuntimeException e) {
            log.warn("Exception while saving page source, returning null.", e);
        }
        return Optional.ofNullable(file);
    }

    private File doSavePageSource(File targetFolder, String fileNameWithoutSuffix) throws IOException {

        String fileName = fileNameWithoutSuffix + ".html";
        File pageSource = new File(targetFolder, fileName);

        FileUtils.write(pageSource, get());

        log.debug("saved page source to file: {}", pageSource);
        fireEventIfEnabled(pageSource);
        return pageSource;

    }

    private void fireEventIfEnabled(File pageSource) {
        EventSystem events = events();
        if (events.isEnabled()) {
            events.fireEvent(new SavedSourceCodeEvent(pageSource));
        }
    }

    private static String generateFileName() {
        return StringUtils.replace(String.valueOf(LocalDateTime.now()), ":", "_");
    }

}
