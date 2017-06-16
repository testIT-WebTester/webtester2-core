package utils.integration;

import info.novatec.testit.webtester.browser.factories.ChromeFactory;
import info.novatec.testit.webtester.browser.factories.MarionetteFactory;
import org.openqa.selenium.remote.DesiredCapabilities;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.BrowserFactory;
import info.novatec.testit.webtester.browser.factories.FirefoxFactory;
import info.novatec.testit.webtester.browser.factories.RemoteFactory;
import info.novatec.testit.webtester.browser.proxy.ProxyConfiguration;


public class TestBrowserFactory implements BrowserFactory {

    @Override
    public Browser createBrowser() {
        Browser browser = createBrowserFromProfile();
        Runtime.getRuntime().addShutdownHook(new Thread(browser::close));
        return browser;
    }

    private Browser createBrowserFromProfile() {
        switch (System.getProperty("testProfile", "local")){
            case "local":
                return new ChromeFactory().createBrowser();
            case "remote":
                return new RemoteFactory().createBrowser();
            default:
                throw new IllegalArgumentException("unknown test profile: " + System.getProperty("testProfile", "local"));
        }
    }

    @Override
    public Browser createBrowser(DesiredCapabilities capabilities) {
        throw new UnsupportedOperationException("createBrowser(DesiredCapabilities capabilities) not supported in test browser!");
    }

    @Override
    public BrowserFactory withProxyConfiguration(ProxyConfiguration configuration) {
        throw new UnsupportedOperationException("proxy not supported in test browser!");
    }

}
