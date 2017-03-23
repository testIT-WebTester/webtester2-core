package info.novatec.testit.webtester.browser.factories;

import org.openqa.selenium.edge.EdgeDriver;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;


/**
 * Factory class for creating Microsoft Edge {@link Browser} instances.
 * Needs the {@code webdriver.edge.driver} system property pointing to the driver proxy server executable.
 * <p>
 * <b>The following capabilities are set by default:</b>
 * <ul>
 * <li>Native Events are disabled</li>
 * <li>Unsigned certificates are accepted</li>
 * </ul>
 *
 * @see Browser
 * @see EdgeDriver
 * @since 2.2
 */
public class EdgeFactory extends BaseBrowserFactory<EdgeFactory> {

    private static final String DRIVER_LOCATION = "webdriver.edge.driver";

    public EdgeFactory() {
        super(EdgeDriver::new);
    }

    @Override
    protected void postProcessConfiguration(Configuration configuration) {
        configuration.getStringProperty(DRIVER_LOCATION).ifPresent(driverLocation -> {
            System.setProperty(DRIVER_LOCATION, driverLocation);
        });
    }

}
