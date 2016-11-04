package info.novatec.testit.webtester.browser.factories;

import org.openqa.selenium.firefox.FirefoxDriver;

import info.novatec.testit.webtester.browser.Browser;


/**
 * Factory class for creating Firefox {@link Browser} instances.
 * This will only work for Firefox browsers up to version 46.
 * All newer versions need to be initialized using the {@link MarionetteFactory}.
 * <p>
 * <b>The following capabilities are set by default:</b>
 * <ul>
 * <li>Native Events are disabled</li>
 * <li>Unsigned certificates are accepted</li>
 * </ul>
 * <b>Additional information on using the {@link FirefoxDriver}:</b>
 * <p>
 * https://github.com/SeleniumHQ/selenium/wiki/FirefoxDriver
 *
 * @see Browser
 * @see FirefoxDriver
 * @see MarionetteFactory
 * @since 2.1
 */
public class FirefoxFactory extends BaseBrowserFactory<FirefoxFactory> {

    public FirefoxFactory() {
        super((capabilities) -> {
            capabilities.setCapability("marionette", false);
            return new FirefoxDriver(capabilities);
        });
    }

}
