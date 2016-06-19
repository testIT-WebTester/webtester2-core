package info.novatec.testit.webtester.browser.operations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

import utils.TestUtils;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.browser.OpenedUrlEvent;
import info.novatec.testit.webtester.pages.Page;


@RunWith(Enclosed.class)
public class UrlOpenerTest {

    @RunWith(MockitoJUnitRunner.class)
    public static abstract class AbstractUrlOpenerTest {

        static final String URL = "http://www.examples.com";
        static final URL URL_OBJECT = TestUtils.toUrl(URL);

        @Mock(answer = Answers.RETURNS_DEEP_STUBS)
        WebDriver webDriver;
        @Mock
        Configuration configuration;
        @Mock
        EventSystem events;
        @Mock
        Browser browser;

        @InjectMocks
        UrlOpener cut;

        @Captor
        ArgumentCaptor<OpenedUrlEvent> eventCaptor;

        @Before
        @SuppressWarnings("unchecked")
        public void init() throws IOException {

            doReturn(webDriver).when(browser).webDriver();
            doReturn(configuration).when(browser).configuration();
            doReturn(events).when(browser).events();
            doReturn(true).when(events).isEnabled();

            // stubbing page creation by returning mocks for create calls
            when(browser.create(any(Class.class))).thenAnswer(invocation -> {
                Class<? extends Page> pageClass = ( Class ) invocation.getArguments()[0];
                return mock(pageClass);
            });

        }

    }

    public static class DefaultEntryPoint extends AbstractUrlOpenerTest {

        @Test
        public void defaultEntryPointCanBeOpened() {
            setDefaultEntryPoint(URL);
            cut.defaultEntryPoint();
            verify(webDriver).get(URL);
        }

        @Test(expected = IllegalStateException.class)
        public void emptyDefaultEntryPointThrowsException() {
            setDefaultEntryPoint("");
            cut.defaultEntryPoint();
        }

        @Test(expected = IllegalStateException.class)
        public void nullDefaultEntryPointThrowsException() {
            setDefaultEntryPoint(null);
            cut.defaultEntryPoint();
        }

        @Test
        public void openingDefaultEntryPointFiresEvent() {
            setDefaultEntryPoint(URL);
            cut.defaultEntryPoint();
            verify(events).fireEvent(eventCaptor.capture());
            OpenedUrlEvent event = eventCaptor.getValue();
            assertThat(event.getUrl()).isEqualTo(URL);
        }

        void setDefaultEntryPoint(String entryPoint) {
            doReturn(Optional.ofNullable(entryPoint)).when(configuration).getDefaultEntryPoint();
        }

    }

    public static class DefaultEntryPointWithPage extends AbstractUrlOpenerTest {

        @Test
        public void defaultEntryPointWithPageCanBeOpened() {
            setDefaultEntryPoint(URL);
            Page page = cut.defaultEntryPoint(Page.class);
            assertThat(page).isNotNull();
            verify(webDriver).get(URL);
        }

        @Test(expected = IllegalStateException.class)
        public void emptyDefaultEntryPointWithPageThrowsException() {
            setDefaultEntryPoint("");
            cut.defaultEntryPoint(Page.class);
        }

        @Test(expected = IllegalStateException.class)
        public void nullDefaultEntryPointWithPageThrowsException() {
            setDefaultEntryPoint(null);
            cut.defaultEntryPoint(Page.class);
        }

        @Test
        public void openingDefaultEntryPointWithPageFiresEvent() {
            setDefaultEntryPoint(URL);
            cut.defaultEntryPoint(Page.class);
            verify(events).fireEvent(eventCaptor.capture());
            OpenedUrlEvent event = eventCaptor.getValue();
            assertThat(event.getUrl()).isEqualTo(URL);
        }

        void setDefaultEntryPoint(String entryPoint) {
            doReturn(Optional.ofNullable(entryPoint)).when(configuration).getDefaultEntryPoint();
        }

    }

    public static class UrlObject extends AbstractUrlOpenerTest {

        @Test
        public void urlObjectCanBeOpened() {
            cut.url(URL_OBJECT);
            verify(webDriver).get(URL);
        }

        @Test
        public void openingUrlObjectFiresEvent() {
            cut.url(URL_OBJECT);
            verify(events).fireEvent(eventCaptor.capture());
            OpenedUrlEvent event = eventCaptor.getValue();
            assertThat(event.getUrl()).isEqualTo(URL);
        }

    }

    public static class UrlObjectWithPage extends AbstractUrlOpenerTest {

        @Test
        public void urlObjectWithPageCanBeOpened() {
            Page page = cut.url(URL_OBJECT, Page.class);
            assertThat(page).isNotNull();
            verify(webDriver).get(URL);
        }

        @Test
        public void openingUrlObjectWithPageFiresEvent() {
            cut.url(URL_OBJECT, Page.class);
            verify(events).fireEvent(eventCaptor.capture());
            OpenedUrlEvent event = eventCaptor.getValue();
            assertThat(event.getUrl()).isEqualTo(URL);
        }

    }

    public static class UrlString extends AbstractUrlOpenerTest {

        @Test
        public void urlStringCanBeOpened() {
            cut.url(URL);
            verify(webDriver).get(URL);
        }

        @Test
        public void openingUrlStringFiresEvent() {
            cut.url(URL);
            verify(events).fireEvent(eventCaptor.capture());
            OpenedUrlEvent event = eventCaptor.getValue();
            assertThat(event.getUrl()).isEqualTo(URL);
        }

    }

    public static class UrlStringWithPage extends AbstractUrlOpenerTest {

        @Test
        public void urlStringWithPageCanBeOpened() {
            Page page = cut.url(URL, Page.class);
            assertThat(page).isNotNull();
            verify(webDriver).get(URL);
        }

        @Test
        public void openingUrlStringWithPageFiresEvent() {
            cut.url(URL, Page.class);
            verify(events).fireEvent(eventCaptor.capture());
            OpenedUrlEvent event = eventCaptor.getValue();
            assertThat(event.getUrl()).isEqualTo(URL);
        }

    }

}
