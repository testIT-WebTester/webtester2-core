package info.novatec.testit.webtester.browser.factories;

import org.openqa.selenium.chrome.ChromeDriver;

import info.novatec.testit.webtester.browser.Browser;


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

    public ChromeFactory() {
        super(ChromeDriver::new);
    }

}
