package info.novatec.testit.webtester.browser.factories;

import org.openqa.selenium.ie.InternetExplorerDriver;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;


/**
 * Factory class for creating Internet Explorer {@link Browser} instances.
 * Needs the {@code webdriver.ie.driver} system property pointing to the driver proxy server executable.
 * <p>
 * <b>The following capabilities are set by default:</b>
 * <ul>
 * <li>Native Events are disabled</li>
 * <li>Unsigned certificates are accepted</li>
 * </ul>
 * <b>Additional information on using the {@link InternetExplorerDriver}:</b>
 * <p>
 * https://github.com/SeleniumHQ/selenium/wiki/InternetExplorerDriver
 *
 * @see Browser
 * @see InternetExplorerDriver
 * @since 2.1
 */
public class InternetExplorerFactory extends BaseBrowserFactory<InternetExplorerFactory> {

    private static final String DRIVER_LOCATION = "webdriver.ie.driver";

    public InternetExplorerFactory() {
        super(InternetExplorerDriver::new);
    }

    @Override
    protected void postProcessConfiguration(Configuration configuration) {
        configuration.getStringProperty(DRIVER_LOCATION).ifPresent(driverLocation -> {
            System.setProperty(DRIVER_LOCATION, driverLocation);
        });
    }

}
