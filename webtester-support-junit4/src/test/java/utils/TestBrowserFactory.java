package utils;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.BrowserFactory;
import info.novatec.testit.webtester.browser.proxy.ProxyConfiguration;
import info.novatec.testit.webtester.config.BaseConfiguration;
import info.novatec.testit.webtester.config.Configuration;


/**
 * Created by SLU on 26.06.2016.
 */
public class TestBrowserFactory implements BrowserFactory {

    private final Browser browser;

    public TestBrowserFactory() {
        this.browser = mock(Browser.class);
        Configuration configuration = new BaseConfiguration();
        configuration.setProperty("prop.string", "some string");
        configuration.setProperty("prop.boolean", true);
        configuration.setProperty("prop.integer", 1);
        configuration.setProperty("prop.long", 2L);
        configuration.setProperty("prop.float", 1.5f);
        configuration.setProperty("prop.double", 2.5d);
        doReturn(configuration).when(browser).configuration();
    }

    @Override
    public Browser createBrowser() {
        return browser;
    }

    @Override
    public Browser createBrowser(WebDriver webDriver) {
        return browser;
    }

    @Override
    public Browser createBrowser(DesiredCapabilities capabilities) {
        return browser;
    }

    @Override
    public BrowserFactory withProxyConfiguration(ProxyConfiguration configuration) {
        return this;
    }

}
