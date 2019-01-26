package info.novatec.testit.webtester.browser.factories;

import org.openqa.selenium.chrome.ChromeDriver;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;


/**
 * Factory class for creating Chrome {@link Browser} instances.
 * Needs the {@code webdriver.chrome.driver} system property pointing to the driver proxy server executable.
 * <p>
 * <b>The following capabilities are set by default:</b>
 * <ul>
 * <li>Native Events are disabled</li>
 * <li>Unsigned certificates are accepted</li>
 * </ul>
 * <b>Additional information on using the {@link ChromeDriver}:</b>
 * <p>
 * https://github.com/SeleniumHQ/selenium/wiki/ChromeDriver
 *
 * @see Browser
 * @see ChromeDriver
 * @since 2.1
 */
public class ChromeFactory extends BaseBrowserFactory<ChromeFactory> {

    private static final String DRIVER_LOCATION = "webdriver.chrome.driver";

    public ChromeFactory() {
        super((capabilities) -> {
            ChromeOptions chromeOptions = new ChromeOptions().merge(capabilities);
            return new ChromeDriver(chromeOptions);
        });
    }

    @Override
    protected void postProcessConfiguration(Configuration configuration) {
        configuration.getStringProperty(DRIVER_LOCATION).ifPresent(driverLocation -> {
            System.setProperty(DRIVER_LOCATION, driverLocation);
        });
    }

}
