package info.novatec.testit.webtester.junit5.extensions.eventlistener;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import org.apache.commons.lang.NotImplementedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.BrowserFactory;
import info.novatec.testit.webtester.browser.proxy.ProxyConfiguration;
import info.novatec.testit.webtester.events.DispatchingEventSystem;
import info.novatec.testit.webtester.events.EventSystem;


public class SpyableEventsTestBrowserFactory implements BrowserFactory {

    @Override
    public Browser createBrowser() {
        Browser browser = mock(Browser.class);
        EventSystem eventSystem = new DispatchingEventSystem(browser);
        EventSystem spyableEventSystem = spy(eventSystem);
        doReturn(spyableEventSystem).when(browser).events();
        return browser;
    }

    @Override
    public Browser createBrowser(WebDriver webDriver) {
        throw new NotImplementedException();
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
