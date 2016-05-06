package info.novatec.testit.webtester.browser.proxy;

import org.openqa.selenium.Proxy;

import info.novatec.testit.webtester.browser.BrowserFactory;


/**
 * Classes implementing this interface are used to configure a {@link Proxy
 * proxy}. They can be provided when initializing a browser via a
 * {@link BrowserFactory factory}.
 *
 * @see Proxy
 * @see BrowserFactory
 * @see NoProxyConfiguration
 * @since 2.0
 */
public interface ProxyConfiguration {

    /**
     * This is called after the {@link Proxy proxy} object was initialized by
     * the framework. The given proxy should be configured as needed.
     *
     * @param proxy the proxy object to configure
     * @since 2.0
     */
    void configureProxy(Proxy proxy);

}
