package info.novatec.testit.webtester.browser.operations;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.WebDriverBrowser;


/**
 * Since taking screenshots is a very complex (in the sense of involved classes and systems) operation this class is mainly
 * integration tests by {@link integration.browser.operations.ScreenshotIntegrationTest}.
 */
@RunWith(MockitoJUnitRunner.class)
public class ScreenshotTest {

    @Mock
    WebDriver webDriver;

    @Test
    public void takingScreenshotWithUnsupportedWebDriverReturnsEmptyOptional() {

        Browser browser = WebDriverBrowser.buildForWebDriver(webDriver);
        Screenshot cut = new Screenshot(browser);

        Optional<File> file = cut.takeAndStore();
        assertThat(file).isEmpty();

    }

}
