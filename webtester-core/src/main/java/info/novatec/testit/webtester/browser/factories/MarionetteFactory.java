package info.novatec.testit.webtester.browser.factories;

import org.openqa.selenium.firefox.FirefoxDriver;

import info.novatec.testit.webtester.browser.Browser;


/**
 * Factory class for creating Firefox {@link Browser} instances.
 * This will only work for Firefox browsers version 47 and above!
 * All older versions should be initialized using the {@link FirefoxFactory}.
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
 * @see FirefoxFactory
 * @since 2.1
 */
public class MarionetteFactory extends BaseBrowserFactory<MarionetteFactory> {

    public MarionetteFactory() {
        super((capabilities) -> {
            capabilities.setCapability("marionette", true);
            return new FirefoxDriver(capabilities);
        });
    }

}
