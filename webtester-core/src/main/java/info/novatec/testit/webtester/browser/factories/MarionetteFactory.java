package info.novatec.testit.webtester.browser.factories;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


/**
 * Factory class for creating Firefox {@link Browser} instances.
 * Needs the {@code webdriver.gecko.driver} system property pointing to the driver proxy server executable.
 * This will only work for Firefox browsers version 47 and above!
 * All older versions should be initialized using the {@link FirefoxFactory}.
 * <p>
 * <b>The following capabilities are set by default:</b>
 * <ul>
 * <li>Native Events are disabled</li>
 * <li>Unsigned certificates are accepted</li>
 * </ul>
 * <b>Additional information on using the {@link FirefoxDriver}:</b>
 * <ul>
 * <li>https://github.com/SeleniumHQ/selenium/wiki/FirefoxDriver</li>
 * <li>https://github.com/mozilla/geckodriver</li>
 * </ul>
 *
 * @see Browser
 * @see FirefoxDriver
 * @see FirefoxFactory
 * @since 2.1
 */
public class MarionetteFactory extends BaseBrowserFactory<MarionetteFactory> {

    private static final String DRIVER_LOCATION = "webdriver.gecko.driver";

    public MarionetteFactory() {
        super((capabilities) -> {
            FirefoxOptions firefoxOptions = new FirefoxOptions().merge(capabilities);
            return new FirefoxDriver(firefoxOptions);
        });
    }

    @Override
    protected void postProcessConfiguration(Configuration configuration) {
        configuration.getStringProperty(DRIVER_LOCATION).ifPresent(driverLocation -> {
            System.setProperty(DRIVER_LOCATION, driverLocation);
        });
    }

}
