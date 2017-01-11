package info.novatec.testit.webtester.browser.factories;

import info.novatec.testit.webtester.browser.Browser;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

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

    public EdgeFactory() {
        super(EdgeDriver::new);
    }

    public Browser createBrowser() {
        EdgeDriverService service = EdgeDriverService.createDefaultService();
        DesiredCapabilities capabilities = getDefaultCapabilities();
        return createBrowser(new EdgeDriver(service, capabilities));
    }

}
