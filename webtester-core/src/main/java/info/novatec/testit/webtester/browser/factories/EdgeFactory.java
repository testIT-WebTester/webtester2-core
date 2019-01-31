package info.novatec.testit.webtester.browser.factories;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;


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
        super((capabilities) -> {
            EdgeOptions edgeOptions = new EdgeOptions().merge(capabilities);
            return new EdgeDriver(edgeOptions);
        });
    }

    @Override
    protected void postProcessConfiguration(Configuration configuration) {
        configuration.getStringProperty(DRIVER_LOCATION).ifPresent(driverLocation -> {
            System.setProperty(DRIVER_LOCATION, driverLocation);
        });
    }

}
