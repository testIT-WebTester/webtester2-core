package info.novatec.testit.webtester.browser.operations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.google.common.primitives.Bytes;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.browser.TookScreenshotEvent;


/**
 * This browser operation offers methods related to taking and saving scrennshots of the currently displayed page.
 *
 * @see #take()
 * @see #takeAndStore()
 * @see #takeAndStore(String)
 * @see #takeAndStore(File)
 * @see #takeAndStore(Path)
 * @see #takeAndStore(String, String)
 * @see #takeAndStore(File, String)
 * @see #takeAndStore(Path, String)
 * @since 2.0
 */
@Slf4j
public class Screenshot extends BaseBrowserOperation {

    /**
     * Creates a new {@link Screenshot} for the given {@link Browser}.
     *
     * @param browser the browser to use
     * @since 2.0
     */
    public Screenshot(Browser browser) {
        super(browser);
    }

    /**
     * Takes a screenshot of the currently displayed page and returns it as a Array of bytes.
     * <p>
     * This operation is considered "optional" any exceptions occurring during its execution will be logged,
     * but not rethrown!
     *
     * @return the screenshot as bytes
     * @see TakesScreenshot#getScreenshotAs(OutputType)
     * @see OutputType#BYTES
     * @since 2.0
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public Optional<List<Byte>> take() {
        List<Byte> bytes = null;
        if ((webDriver() instanceof TakesScreenshot)) {
            try {
                bytes = Bytes.asList((( TakesScreenshot ) webDriver()).getScreenshotAs(OutputType.BYTES));
            } catch (RuntimeException e) {
                log.warn("Exception while creating screenshot, returning null.", e);
            }
        }
        return Optional.ofNullable(bytes);

    }

    /**
     * Take a screenshot of the currently displayed page and save it to a new file within the configured default folder
     * {@link Configuration#getScreenshotFolder()}. The generated name of the file will consist of the current
     * local date/time.
     * <p>
     * Example: 2016-01-03T10_15_30.201.png
     * <p>
     * This operation is considered "optional" any exceptions occurring during its execution will be logged,
     * but not rethrown!
     *
     * @return a file reference to the newly saved screenshot file
     * @see TakesScreenshot#getScreenshotAs(OutputType)
     * @see OutputType#FILE
     * @since 2.0
     */
    public Optional<File> takeAndStore() {
        File defaultFolder = configuration().getScreenshotFolder();
        return takeAndStore(defaultFolder.getAbsolutePath());
    }

    /**
     * Take a screenshot of the currently displayed page and save it to a new file within the given folder.
     * The generated name of the file will consist of the current local date/time.
     * <p>
     * Example: 2016-01-03T10_15_30.201.png
     * <p>
     * This operation is considered "optional" any exceptions occurring during its execution will be logged,
     * but not rethrown!
     *
     * @param targetFolderPath the path to the folder
     * @return a file reference to the newly saved screenshot file
     * @see TakesScreenshot#getScreenshotAs(OutputType)
     * @see OutputType#FILE
     * @since 2.0
     */
    public Optional<File> takeAndStore(String targetFolderPath) {
        return takeAndStore(new File(targetFolderPath));
    }

    /**
     * Take a screenshot of the currently displayed page and save it to a new file within the given folder.
     * The generated name of the file will consist of the current local date/time.
     * <p>
     * Example: 2016-01-03T10_15_30.201.png
     * <p>
     * This operation is considered "optional" any exceptions occurring during its execution will be logged,
     * but not rethrown!
     *
     * @param targetFolderPath the path to the folder
     * @return a file reference to the newly saved screenshot file
     * @see TakesScreenshot#getScreenshotAs(OutputType)
     * @see OutputType#FILE
     * @since 2.0
     */
    public Optional<File> takeAndStore(Path targetFolderPath) {
        return takeAndStore(targetFolderPath.toFile());
    }

    /**
     * Take a screenshot of the currently displayed page and save it to a new file within the given folder.
     * The generated name of the file will consist of the current local date/time.
     * <p>
     * Example: 2016-01-03T10_15_30.201.png
     * <p>
     * This operation is considered "optional" any exceptions occurring during its execution will be logged,
     * but not rethrown!
     *
     * @param targetFolder the folder
     * @return a file reference to the newly saved screenshot file
     * @see TakesScreenshot#getScreenshotAs(OutputType)
     * @see OutputType#FILE
     * @since 2.0
     */
    public Optional<File> takeAndStore(File targetFolder) {
        return takeAndStore(targetFolder, generateFileName());
    }

    /**
     * Take a screenshot of the currently displayed page and save it to a new file within the given folder.
     * The file's name can also be defined. But the file's suffix will always be ".png".
     * <p>
     * This operation is considered "optional" any exceptions occurring during its execution will be logged,
     * but not rethrown!
     *
     * @param targetFolderPath the path to the folder
     * @param fileNameWithoutSuffix the file name without any file suffix
     * @return a file reference to the newly saved screenshot file
     * @see TakesScreenshot#getScreenshotAs(OutputType)
     * @see OutputType#FILE
     * @since 2.0
     */
    public Optional<File> takeAndStore(String targetFolderPath, String fileNameWithoutSuffix) {
        return takeAndStore(new File(targetFolderPath), fileNameWithoutSuffix);
    }

    /**
     * Take a screenshot of the currently displayed page and save it to a new file within the given folder.
     * The file's name can also be defined. But the file's suffix will always be ".png".
     * <p>
     * This operation is considered "optional" any exceptions occurring during its execution will be logged,
     * but not rethrown!
     *
     * @param targetFolderPath the path to the folder
     * @param fileNameWithoutSuffix the file name without any file suffix
     * @return a file reference to the newly saved screenshot file
     * @see TakesScreenshot#getScreenshotAs(OutputType)
     * @see OutputType#FILE
     * @since 2.0
     */
    public Optional<File> takeAndStore(Path targetFolderPath, String fileNameWithoutSuffix) {
        return takeAndStore(targetFolderPath.toFile(), fileNameWithoutSuffix);
    }

    /**
     * Take a screenshot of the currently displayed page and save it to a new file within the given folder.
     * The file's name can also be defined. But the file's suffix will always be ".png".
     * <p>
     * This operation is considered "optional" any exceptions occurring during its execution will be logged,
     * but not rethrown!
     *
     * @param targetFolder the folder
     * @param fileNameWithoutSuffix the file name without any file suffix
     * @return a file reference to the newly saved screenshot file
     * @see TakesScreenshot#getScreenshotAs(OutputType)
     * @see OutputType#FILE
     * @since 2.0
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public Optional<File> takeAndStore(File targetFolder, String fileNameWithoutSuffix) {
        File file = null;
        if (webDriver() instanceof TakesScreenshot) {
            try {
                file = doTakeAndStoreScreenshot(targetFolder, fileNameWithoutSuffix);
            } catch (IOException | RuntimeException e) {
                log.warn("Exception while creating screenshot, returning empty optional.", e);
            }
        }
        return Optional.ofNullable(file).filter(File::isFile);
    }

    private File doTakeAndStoreScreenshot(File targetFolder, String fileNameWithoutSuffix) throws IOException {

        TakesScreenshot takesScreenshot = ( TakesScreenshot ) webDriver();
        File tempScreenshot = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String fileName = fileNameWithoutSuffix + ".png";
        File screenshot = new File(targetFolder, fileName);

        FileUtils.moveFile(tempScreenshot, screenshot);

        log.debug("saved screenshot to file: {}", screenshot);
        fireEventIfEnabled(screenshot);
        return screenshot;

    }

    private void fireEventIfEnabled(File screenshot) {
        EventSystem events = events();
        if (events.isEnabled()) {
            events.fireEvent(new TookScreenshotEvent(screenshot));
        }
    }

    private String generateFileName() {
        return StringUtils.replace(String.valueOf(LocalDateTime.now()), ":", "_");
    }

}
