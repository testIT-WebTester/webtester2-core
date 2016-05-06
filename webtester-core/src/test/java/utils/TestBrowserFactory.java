package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.WebDriverBrowser;
import info.novatec.testit.webtester.browser.BrowserFactory;
import info.novatec.testit.webtester.browser.proxy.ProxyConfiguration;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.config.adapters.DefaultFileConfigurationAdapter;
import info.novatec.testit.webtester.config.adapters.LocalFileConfigurationAdapter;
import info.novatec.testit.webtester.config.builders.BaseConfigurationBuilder;


public class TestBrowserFactory implements BrowserFactory {

    @Override
    public Browser createBrowser() {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setAcceptUntrustedCertificates(true);
        profile.setEnableNativeEvents(true);
        return createBrowser(new FirefoxDriver(profile));
    }

    @Override
    public Browser createBrowser(DesiredCapabilities capabilities) {
        return createBrowser(new FirefoxDriver(capabilities));
    }

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

    @Override
    public BrowserFactory withProxyConfiguration(ProxyConfiguration configuration) {
        // proxies are ignored for tests
        return this;
    }

}
