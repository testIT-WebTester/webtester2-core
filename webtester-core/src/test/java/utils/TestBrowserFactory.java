package utils;

import org.openqa.selenium.WebDriver;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.WebDriverBrowser;
import info.novatec.testit.webtester.browser.factories.FirefoxFactory;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.config.adapters.DefaultFileConfigurationAdapter;
import info.novatec.testit.webtester.config.adapters.LocalFileConfigurationAdapter;
import info.novatec.testit.webtester.config.builders.BaseConfigurationBuilder;


public class TestBrowserFactory extends FirefoxFactory {

    @Override
    public Browser createBrowser(WebDriver webDriver) {
        Configuration config = new BaseConfigurationBuilder()
            .withAdapter(new DefaultFileConfigurationAdapter())
            .withAdapter(new LocalFileConfigurationAdapter())
            .build();
        Browser browser = WebDriverBrowser.forWebDriver(webDriver).withConfiguration(config).build();
        Runtime.getRuntime().addShutdownHook(new Thread(browser::close));
        return browser;
    }

}
