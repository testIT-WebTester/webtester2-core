package info.novatec.testit.webtester.browser.factories;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;

/**
 * Factory class for creating Blink-based Opera {@link Browser} instances.
 * Needs the {@code webdriver.opera.driver} system property pointing to the driver proxy server executable.
 * <p>
 * <b>The following capabilities are set by default:</b>
 * <ul>
 * <li>Native Events are disabled</li>
 * <li>Unsigned certificates are accepted</li>
 * </ul>
 * <b>Additional information on using the {@link OperaDriver}:</b>
 * <p>
 * https://github.com/operasoftware/operachromiumdriver
 *
 * @see Browser
 * @see OperaDriver
 * @since 2.1
 */
public class OperaFactory extends BaseBrowserFactory<OperaFactory> {

    private static final String DRIVER_LOCATION = "webdriver.opera.driver";

    public OperaFactory() {
        super((capabilities) -> {
            OperaOptions operaOptions = new OperaOptions().merge(capabilities);
            return new OperaDriver(operaOptions);
        });
    }

    @Override
    protected void postProcessConfiguration(Configuration configuration) {
        configuration.getStringProperty(DRIVER_LOCATION).ifPresent(driverLocation -> {
            System.setProperty(DRIVER_LOCATION, driverLocation);
        });
    }

}
