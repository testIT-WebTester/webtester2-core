package utils;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.BrowserFactory;
import info.novatec.testit.webtester.browser.WebDriverBrowser;
import info.novatec.testit.webtester.browser.proxy.ProxyConfiguration;
import org.apache.commons.lang.NotImplementedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.mockito.Mockito.mock;


public class TestBrowserFactory implements BrowserFactory {

    @Override
    public Browser createBrowser() {
        return WebDriverBrowser.buildForWebDriver(mock(WebDriver.class));
    }

    @Override
    public Browser createBrowser(DesiredCapabilities capabilities) {
        throw new NotImplementedException();
    }

    @Override
    public BrowserFactory withProxyConfiguration(ProxyConfiguration configuration) {
        return this;
    }
}
