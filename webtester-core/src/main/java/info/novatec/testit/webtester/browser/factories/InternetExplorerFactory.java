package info.novatec.testit.webtester.browser.factories;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import info.novatec.testit.webtester.browser.Browser;


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

    public InternetExplorerFactory() {
        super(InternetExplorerDriver::new);
    }

    public Browser createBrowser(int port) {
        InternetExplorerDriverService service = InternetExplorerDriverService.createDefaultService();
        DesiredCapabilities capabilities = getDefaultCapabilities();
        return createBrowser(new InternetExplorerDriver(service, capabilities, port));
    }

}
