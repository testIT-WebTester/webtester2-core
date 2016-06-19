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
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.MockFactory;
import utils.TestUtils;
import utils.events.EventCaptor;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.WebDriverBrowser;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.browser.ClosedWindowEvent;
import info.novatec.testit.webtester.events.browser.MaximizedWindowEvent;
import info.novatec.testit.webtester.events.browser.RefreshedPageEvent;
import info.novatec.testit.webtester.events.browser.SetWindowPositionEvent;
import info.novatec.testit.webtester.events.browser.SetWindowSizeEvent;
import info.novatec.testit.webtester.pagefragments.PageFragment;


@RunWith(Enclosed.class)
public class CurrentWindowTest {

    @RunWith(MockitoJUnitRunner.class)
    public static abstract class AbstractCurrentWindowTest {

        WebDriver webDriver;
        Browser browser;

        CurrentWindow cut;

        @Before
        public void init() throws IOException {
            webDriver = MockFactory.webDriver();
            browser = WebDriverBrowser.forWebDriver(webDriver).build();
            cut = new CurrentWindow(browser);
        }

        EventSystem eventSystem() {
            return cut.browser().events();
        }

        WebDriver.Window window() {
            return webDriver.manage().window();
        }

        WebDriver.Navigation navigation() {
            return webDriver.navigate();
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

        @Test
        public void refreshingWindowDelegatesToCorrectWebDriverMethod() {
            cut.refresh();
            verify(navigation()).refresh();
        }

        @Test
        public void refreshingWindowFiresEvent() {
            EventCaptor.capture(eventSystem(), RefreshedPageEvent.class).execute(() -> cut.refresh()).assertEventWasFired();
        }

    }

    public static class Maximize extends AbstractCurrentWindowTest {

        @Test
        public void maximizingWindowDelegatesToCorrectWebDriverMethod() {
            cut.maximize();
            verify(window()).maximize();
        }

        @Test
        public void maximizingWindowFiresEvent() {
            EventCaptor.capture(eventSystem(), MaximizedWindowEvent.class)
                .execute(() -> cut.maximize())
                .assertEventWasFired();
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

        @Test
        public void settingWindowPositionDelegatesToCorrectWebDriverMethod() {
            cut.setPosition(42, 84);
            verify(window()).setPosition(new Point(42, 84));
        }

        @Test
        public void settingWindowPositionFiresEvent() {
            EventCaptor.capture(eventSystem(), SetWindowPositionEvent.class)
                .execute(() -> cut.setPosition(42, 84))
                .assertEventWasFired()
                .assertEvent(event -> {
                    assertThat(event.getX()).isEqualTo(42);
                    assertThat(event.getY()).isEqualTo(84);
                });
        }

    }

    public static class SetSize extends AbstractCurrentWindowTest {

        @Test
        public void settingWindowSizeDelegatesToCorrectWebDriverMethod() {
            cut.setSize(1024, 768);
            verify(window()).setSize(new Dimension(1024, 768));
        }

        @Test
        public void settingWindowSizeFiresEvent() {
            EventCaptor.capture(eventSystem(), SetWindowSizeEvent.class)
                .execute(() -> cut.setSize(1024, 768))
                .assertEventWasFired()
                .assertEvent(event -> {
                    assertThat(event.getWidth()).isEqualTo(1024);
                    assertThat(event.getHeight()).isEqualTo(768);
                });
        }

    }

    public static class ScrollTo extends AbstractCurrentWindowTest {

        @Test
        public void correctJavaScriptIsExecuted() throws NoSuchFieldException, IllegalAccessException {

            JavaScriptExecutor javaScript = mock(JavaScriptExecutor.class);
            TestUtils.setFieldValue(browser, "javaScript", javaScript);

            PageFragment fragment = MockFactory.fragment().build();
            cut.scrollTo(fragment);

            verify(javaScript).execute("arguments[0].scrollIntoView(true)", fragment);

        }

    }

    public static class Close extends AbstractCurrentWindowTest {

        @Test
        public void closingWindowDelegatesToCorrectWebDriverMethods() {
            cut.close();
            verify(webDriver).close();
        }

        @Test
        public void closingWindowFiresEvent() {
            EventCaptor.capture(eventSystem(), ClosedWindowEvent.class).execute(() -> cut.close()).assertEventWasFired();
        }

    }

}
