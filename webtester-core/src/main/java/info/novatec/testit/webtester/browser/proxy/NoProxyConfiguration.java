package info.novatec.testit.webtester.browser.proxy;

import org.openqa.selenium.Proxy;

import info.novatec.testit.webtester.browser.BrowserFactory;


/**
 * Default implementation of a {@link ProxyConfiguration proxy configuration}
 * doing nothing to with {@link Proxy proxy} object.
 *
 * @see Proxy
 * @see ProxyConfiguration
 * @see BrowserFactory
 * @since 2.0
 */
public class NoProxyConfiguration implements ProxyConfiguration {

    @Override
    public void configureProxy(Proxy proxy) {
        // do nothing
    }

}
