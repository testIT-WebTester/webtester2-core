package info.novatec.testit.webtester.browser.operations;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

import integration.browser.operations.ScreenshotTakerIntegrationTest;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.WebDriverBrowser;


/**
 * Since taking screenshots is a very complex (in the sense of involved classes and systems) operation this class is mainly
 * integration tests by {@link ScreenshotTakerIntegrationTest}.
 */
@RunWith(MockitoJUnitRunner.class)
public class ScreenshotTakerTest {

    @Mock
    WebDriver webDriver;

    @Test
    public void takingScreenshotWithUnsupportedWebDriverReturnsEmptyOptional() {

        Browser browser = WebDriverBrowser.buildForWebDriver(webDriver);
        ScreenshotTaker cut = new ScreenshotTaker(browser);

        Optional<File> file = cut.takeAndStore();
        assertThat(file).isEmpty();

    }

}
