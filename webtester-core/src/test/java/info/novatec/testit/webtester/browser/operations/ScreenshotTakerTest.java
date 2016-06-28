package info.novatec.testit.webtester.browser.operations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.google.common.primitives.Bytes;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.browser.TookScreenshotEvent;


@RunWith(Enclosed.class)
public class ScreenshotTakerTest {

    @RunWith(MockitoJUnitRunner.class)
    public static abstract class AbstractScreenshotTakerTest {

        static final String FILE_NAME_PATTERN = "\\d{4}-\\d{2}-\\d{2}T\\d{2}_\\d{2}_\\d{2}(\\.\\d{3})?.png";
        static final String FILE_NAME = "my-file";
        static final byte[] BYTES = new byte[] { 1, 2, 3 };

        @ClassRule
        public static TemporaryFolder folder = new TemporaryFolder();

        @Mock(extraInterfaces = WebDriver.class)
        TakesScreenshot webDriver;
        @Mock
        Configuration configuration;
        @Mock
        EventSystem events;
        @Mock
        Browser browser;
        @InjectMocks
        ScreenshotTaker cut;

        File currentFolder;
        File takenScreenshotFile;

        @Before
        public void init() throws IOException {

            doReturn(webDriver).when(browser).webDriver();
            doReturn(configuration).when(browser).configuration();
            doReturn(events).when(browser).events();

            currentFolder = folder.newFolder();
            doReturn(currentFolder).when(configuration).getScreenshotFolder();
            takenScreenshotFile = folder.newFile();
            doReturn(takenScreenshotFile).when(webDriver).getScreenshotAs(OutputType.FILE);

        }

        static Supplier<AssertionError> assertionException() {
            return () -> new AssertionError("not save - see log");
        }

        void assertFileParent(File file) {
            assertThat(file).hasParent(currentFolder);
        }

        void assertFileNamePattern(File file) {
            assertThat(file.getName()).matches(FILE_NAME_PATTERN);
        }

        void assertCustomFileName(File file) {
            assertThat(file.getName()).matches(FILE_NAME + ".png");
        }

    }

    public static class Take extends AbstractScreenshotTakerTest {

        @Test
        public void screenshotCanBeTaken() {
            doReturn(BYTES).when(webDriver).getScreenshotAs(OutputType.BYTES);
            List<Byte> actualBytes = cut.take().orElseThrow(assertionException());
            assertThat(actualBytes).isEqualTo(Bytes.asList(BYTES));
        }

        @Test
        public void inCaseOfRuntimeExceptionAnEmptyOptionalIsReturned() {
            doThrow(RuntimeException.class).when(webDriver).getScreenshotAs(OutputType.BYTES);
            Optional<List<Byte>> bytes = cut.take();
            assertThat(bytes).isEmpty();
        }

    }

    public static class DefaultTakeAndStore extends AbstractScreenshotTakerTest {

        @Test
        public void screenshotCanBeTakenWithDefaults() {
            File file = cut.takeAndStore().orElseThrow(assertionException());
            assertFileParent(file);
            assertFileNamePattern(file);
        }

    }

    public static class TakeAndStoreWithCustomTarget extends AbstractScreenshotTakerTest {

        @Test
        public void canHandleStringReference() {
            File file = cut.takeAndStore(currentFolder.getAbsolutePath()).orElseThrow(assertionException());
            assertFileParent(file);
            assertFileNamePattern(file);
        }

        @Test
        public void canHandlePathReference() {
            File file = cut.takeAndStore(currentFolder.toPath()).orElseThrow(assertionException());
            assertFileParent(file);
            assertFileNamePattern(file);
        }

        @Test
        public void canHandleFileReference() {
            File file = cut.takeAndStore(currentFolder).orElseThrow(assertionException());
            assertFileParent(file);
            assertFileNamePattern(file);
        }

    }

    public static class TakeAndStoreWithCustomTargetAndFileName extends AbstractScreenshotTakerTest {

        @Test
        public void canHandleStringReference() {
            File file = cut.takeAndStore(currentFolder.getAbsolutePath(), FILE_NAME).orElseThrow(assertionException());
            assertFileParent(file);
            assertCustomFileName(file);
        }

        @Test
        public void canHandlePathReference() {
            File file = cut.takeAndStore(currentFolder.toPath(), FILE_NAME).orElseThrow(assertionException());
            assertFileParent(file);
            assertCustomFileName(file);
        }

        @Test
        public void canHandleFileReference() {
            File file = cut.takeAndStore(currentFolder, FILE_NAME).orElseThrow(assertionException());
            assertFileParent(file);
            assertCustomFileName(file);
        }

    }

    public static class Events extends AbstractScreenshotTakerTest {

        @Captor
        ArgumentCaptor<TookScreenshotEvent> eventCaptor;

        @Test
        public void eventIsFiredWhenSaving() {
            doReturn(true).when(events).isEnabled();
            File file = cut.takeAndStore().orElseThrow(assertionException());
            verify(events).fireEvent(eventCaptor.capture());
            assertThat(eventCaptor.getValue().getScreenshot()).isEqualTo(file);
        }

        @Test
        public void noEventsAreFiredIfEventSystemIsDisabled() {
            doReturn(false).when(events).isEnabled();
            cut.takeAndStore();
            verify(events, never()).fireEvent(any(Event.class));
        }

    }

    public static class Exceptions extends AbstractScreenshotTakerTest {

        @Test
        public void inCaseOfRuntimeExceptionAnEmptyOptionalIsReturned() {
            doThrow(RuntimeException.class).when(webDriver).getScreenshotAs(OutputType.FILE);
            Optional<File> file = cut.takeAndStore();
            assertThat(file).isEmpty();
        }

        @Test
        public void inCaseOfIOExceptionAnEmptyOptionalIsReturned() {
            doThrow(RuntimeException.class).when(webDriver).getScreenshotAs(OutputType.FILE);
            Optional<File> file = cut.takeAndStore();
            assertThat(file).isEmpty();
        }

        @Test(expected = Exception.class)
        public void otherExceptionsAreNotHandled() {
            doThrow(Exception.class).when(webDriver).getScreenshotAs(OutputType.FILE);
            cut.takeAndStore();
        }

    }

    @RunWith(MockitoJUnitRunner.class)
    public static class NonScreenshotTakingWebDriver {

        @ClassRule
        public static TemporaryFolder folder = new TemporaryFolder();

        @Mock
        WebDriver webDriver;
        @Mock
        Configuration configuration;
        @Mock
        Browser browser;
        @InjectMocks
        ScreenshotTaker cut;

        @Test
        public void takingScreenshotWithUnsupportedWebDriverReturnsEmptyOptional() throws IOException {
            doReturn(webDriver).when(browser).webDriver();
            doReturn(configuration).when(browser).configuration();
            doReturn(folder.newFolder()).when(configuration).getScreenshotFolder();
            Optional<File> file = cut.takeAndStore();
            assertThat(file).isEmpty();
        }

    }

}
