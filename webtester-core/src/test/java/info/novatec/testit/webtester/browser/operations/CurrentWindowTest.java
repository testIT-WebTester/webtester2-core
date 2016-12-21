package info.novatec.testit.webtester.browser.operations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.MockFactory;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.browser.ClosedWindowEvent;
import info.novatec.testit.webtester.events.browser.MaximizedWindowEvent;
import info.novatec.testit.webtester.events.browser.RefreshedPageEvent;
import info.novatec.testit.webtester.events.browser.SetWindowPositionEvent;
import info.novatec.testit.webtester.events.browser.SetWindowSizeEvent;
import info.novatec.testit.webtester.pagefragments.PageFragment;


@RunWith(Enclosed.class)
public class CurrentWindowTest {

    @RunWith(MockitoJUnitRunner.Silent.class)
    public static abstract class AbstractCurrentWindowTest {

        @Mock(answer = Answers.RETURNS_DEEP_STUBS)
        WebDriver webDriver;
        @Mock
        EventSystem events;
        @Mock
        Browser browser;

        @InjectMocks
        CurrentWindow cut;

        @Before
        public void init() throws IOException {
            doReturn(webDriver).when(browser).webDriver();
            doReturn(events).when(browser).events();
            doReturn(true).when(events).isEnabled();
        }

    }

    public static class GetHandle extends AbstractCurrentWindowTest {

        @Test
        public void handleOfWindowCanBeReturned() {
            doReturn("fooBar").when(webDriver).getWindowHandle();
            String handle = cut.getHandle();
            assertThat(handle).isEqualTo("fooBar");
        }

    }

    public static class Refresh extends AbstractCurrentWindowTest {

        @Captor
        ArgumentCaptor<RefreshedPageEvent> eventCaptor;

        @Test
        public void refreshingWindowDelegatesToCorrectWebDriverMethod() {
            cut.refresh();
            verify(webDriver.navigate()).refresh();
        }

        @Test
        public void refreshingWindowFiresEvent() {
            cut.refresh();
            verify(events).fireEvent(eventCaptor.capture());
            assertThat(eventCaptor.getValue()).isNotNull();
        }

    }

    public static class Maximize extends AbstractCurrentWindowTest {

        @Captor
        ArgumentCaptor<MaximizedWindowEvent> eventCaptor;

        @Test
        public void maximizingWindowDelegatesToCorrectWebDriverMethod() {
            cut.maximize();
            verify(webDriver.manage().window()).maximize();
        }

        @Test
        public void maximizingWindowFiresEvent() {
            cut.maximize();
            verify(events).fireEvent(eventCaptor.capture());
            assertThat(eventCaptor.getValue()).isNotNull();
        }

    }

    public static class ToggleFullScreen extends AbstractCurrentWindowTest {

        @Test
        public void togglingFullScreenSendsF11KeyToCurrentPage() {

            WebElement element = mock(WebElement.class);
            doReturn(element).when(webDriver).findElement(By.tagName("html"));

            cut.toggleFullScreen();

            verify(element).sendKeys(Keys.F11);

        }

    }

    public static class SetPosition extends AbstractCurrentWindowTest {

        @Captor
        ArgumentCaptor<SetWindowPositionEvent> eventCaptor;

        @Test
        public void settingWindowPositionDelegatesToCorrectWebDriverMethod() {
            cut.setPosition(42, 84);
            verify(webDriver.manage().window()).setPosition(new Point(42, 84));
        }

        @Test
        public void settingWindowPositionFiresEvent() {
            cut.setPosition(42, 84);
            verify(events).fireEvent(eventCaptor.capture());
            SetWindowPositionEvent event = eventCaptor.getValue();
            assertThat(event.getX()).isEqualTo(42);
            assertThat(event.getY()).isEqualTo(84);
        }

    }

    public static class SetSize extends AbstractCurrentWindowTest {

        @Captor
        ArgumentCaptor<SetWindowSizeEvent> eventCaptor;

        @Test
        public void settingWindowSizeDelegatesToCorrectWebDriverMethod() {
            cut.setSize(1024, 768);
            verify(webDriver.manage().window()).setSize(new Dimension(1024, 768));
        }

        @Test
        public void settingWindowSizeFiresEvent() {
            cut.setSize(1024, 768);
            verify(events).fireEvent(eventCaptor.capture());
            SetWindowSizeEvent event = eventCaptor.getValue();
            assertThat(event.getWidth()).isEqualTo(1024);
            assertThat(event.getHeight()).isEqualTo(768);
        }

    }

    public static class ScrollTo extends AbstractCurrentWindowTest {

        @Mock
        JavaScriptExecutor javaScript;

        @Test
        public void correctJavaScriptIsExecuted() {
            doReturn(javaScript).when(browser).javaScript();
            PageFragment fragment = MockFactory.fragment().build();
            cut.scrollTo(fragment);
            verify(javaScript).execute("arguments[0].scrollIntoView(true)", fragment);
        }

    }

    public static class Close extends AbstractCurrentWindowTest {

        @Captor
        ArgumentCaptor<ClosedWindowEvent> eventCaptor;

        @Test
        public void closingWindowDelegatesToCorrectWebDriverMethods() {
            cut.close();
            verify(webDriver).close();
        }

        @Test
        public void closingWindowFiresEvent() {
            cut.close();
            verify(events).fireEvent(eventCaptor.capture());
            assertThat(eventCaptor.getValue()).isNotNull();
        }

    }

}
