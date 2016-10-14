package info.novatec.testit.webtester.browser.operations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;
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
import org.openqa.selenium.WebDriver;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.browser.SavedSourceCodeEvent;


@RunWith(Enclosed.class)
public class PageSourceSaverTest {

    @RunWith(MockitoJUnitRunner.Silent.class)
    public static abstract class AbstractPageSourceSaverTest {

        static final String FILE_NAME_PATTERN = "\\d{4}-\\d{2}-\\d{2}T\\d{2}_\\d{2}_\\d{2}(\\.\\d{3})?.html";
        static final String PAGE_SOURCE = "<html>...</html>";
        static final String FILE_NAME = "my-file";

        @ClassRule
        public static TemporaryFolder folder = new TemporaryFolder();

        @Mock
        WebDriver webDriver;
        @Mock
        Configuration configuration;
        @Mock
        EventSystem events;
        @Mock
        Browser browser;
        @InjectMocks
        PageSourceSaver cut;

        File currentFolder;

        @Before
        public void init() throws IOException {

            doReturn(webDriver).when(browser).webDriver();
            doReturn(configuration).when(browser).configuration();
            doReturn(events).when(browser).events();

            currentFolder = folder.newFolder();
            doReturn(currentFolder).when(configuration).getPageSourceFolder();

            doReturn(PAGE_SOURCE).when(webDriver).getPageSource();

        }

        static Supplier<AssertionError> notSavedException() {
            return () -> new AssertionError("not save - see log");
        }

        void assertFileParent(File file) {
            assertThat(file).hasParent(currentFolder);
        }

        void assertFileContent(File file) {
            assertThat(file).hasContent(PAGE_SOURCE);
        }

        void assertFileNamePattern(File file) {
            assertThat(file.getName()).matches(FILE_NAME_PATTERN);
        }

        void assertCustomFileName(File file) {
            assertThat(file.getName()).matches(FILE_NAME + ".html");
        }

    }

    public static class Get extends AbstractPageSourceSaverTest {

        @Test
        public void pageSourceCanBeGot() {
            doReturn(PAGE_SOURCE).when(webDriver).getPageSource();
            String source = cut.get();
            assertThat(source).isEqualTo(PAGE_SOURCE);
        }

        @Test
        public void pageSourceIsDefaultedToEmptyStringIfNull() {
            doReturn(null).when(webDriver).getPageSource();
            String source = cut.get();
            assertThat(source).isEqualTo("");
        }

    }

    public static class DefaultSave extends AbstractPageSourceSaverTest {

        @Test
        public void pageSourceCanBeSavedWithDefaults() {
            File file = cut.save().orElseThrow(notSavedException());
            assertFileParent(file);
            assertFileContent(file);
            assertFileNamePattern(file);
        }

    }

    public static class SaveWithCustomTarget extends AbstractPageSourceSaverTest {

        @Test
        public void canHandleStringReference() {
            File file = cut.save(currentFolder.getAbsolutePath()).orElseThrow(notSavedException());
            assertFileParent(file);
            assertFileContent(file);
            assertFileNamePattern(file);
        }

        @Test
        public void canHandlePathReference() {
            File file = cut.save(currentFolder.toPath()).orElseThrow(notSavedException());
            assertFileParent(file);
            assertFileContent(file);
            assertFileNamePattern(file);
        }

        @Test
        public void canHandleFileReference() {
            File file = cut.save(currentFolder).orElseThrow(notSavedException());
            assertFileParent(file);
            assertFileContent(file);
            assertFileNamePattern(file);
        }

    }

    public static class SaveWithCustomTargetAndFileName extends AbstractPageSourceSaverTest {

        @Test
        public void canHandleStringReference() {
            File file = cut.save(currentFolder.getAbsolutePath(), FILE_NAME).orElseThrow(notSavedException());
            assertFileParent(file);
            assertFileContent(file);
            assertCustomFileName(file);
        }

        @Test
        public void canHandlePathReference() {
            File file = cut.save(currentFolder.toPath(), FILE_NAME).orElseThrow(notSavedException());
            assertFileParent(file);
            assertFileContent(file);
            assertCustomFileName(file);
        }

        @Test
        public void canHandleFileReference() {
            File file = cut.save(currentFolder, FILE_NAME).orElseThrow(notSavedException());
            assertFileParent(file);
            assertFileContent(file);
            assertCustomFileName(file);
        }

    }

    public static class Events extends AbstractPageSourceSaverTest {

        @Captor
        ArgumentCaptor<SavedSourceCodeEvent> eventCaptor;

        @Test
        public void eventIsFiredWhenSaving() {
            doReturn(true).when(events).isEnabled();
            File file = cut.save().orElseThrow(notSavedException());
            verify(events).fireEvent(eventCaptor.capture());
            assertThat(eventCaptor.getValue().getPageSource()).isEqualTo(file);
        }

        @Test
        public void noEventsAreFiredIfEventSystemIsDisabled() {
            doReturn(false).when(events).isEnabled();
            cut.save();
            verify(events, never()).fireEvent(any(Event.class));
        }

    }

    public static class Exceptions extends AbstractPageSourceSaverTest {

        @Test
        public void inCaseOfRuntimeExceptionAnEmptyOptionalIsReturned() {
            doThrow(RuntimeException.class).when(webDriver).getPageSource();
            Optional<File> file = cut.save();
            assertThat(file).isEmpty();
        }

        @Test
        public void inCaseOfIOExceptionAnEmptyOptionalIsReturned() {
            doThrow(RuntimeException.class).when(webDriver).getPageSource();
            Optional<File> file = cut.save();
            assertThat(file).isEmpty();
        }

        @Test(expected = Exception.class)
        public void otherExceptionsAreNotHandled() {
            doThrow(Exception.class).when(webDriver).getPageSource();
            cut.save();
        }

    }

}
