package info.novatec.testit.webtester.browser.factories;

import java.util.function.Function;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.BrowserFactory;
import info.novatec.testit.webtester.browser.WebDriverBrowser;
import info.novatec.testit.webtester.browser.proxy.ProxyConfiguration;


/**
 * Base class for all the default {@link BrowserFactory} implementations shipped with WebTester.
 * <p>
 * This class provides default behaviour for any {@link Browser} initialized with it.
 * <p>
 * <b>The following capabilities are set by default:</b>
 * <ul>
 * <li>Native Events are disabled</li>
 * <li>Unsigned certificates are accepted</li>
 * </ul>
 *
 * @param <T> the type of the extending factory implementation - used for fluent API for certain methods
 * @since 2.1
 */
public class BaseBrowserFactory<T extends  BrowserFactory> implements BrowserFactory {

    private final Function<DesiredCapabilities, WebDriver> webDriverProducer;
    private ProxyConfiguration proxyConfiguration;

    protected BaseBrowserFactory(Function<DesiredCapabilities, WebDriver> webDriverProducer) {
        this.webDriverProducer = webDriverProducer;
    }

    @Override
    public Browser createBrowser() {
        DesiredCapabilities capabilities = getDefaultCapabilities();
        return createBrowser(capabilities);
    }

    protected DesiredCapabilities getDefaultCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.HAS_NATIVE_EVENTS, false);
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        setOptionalProxyConfiguration(capabilities);
        return capabilities;
    }

    @Override
    public Browser createBrowser(DesiredCapabilities capabilities) {
        return createBrowser(webDriverProducer.apply(capabilities));
    }

    @Override
    public Browser createBrowser(WebDriver webDriver) {
        return WebDriverBrowser.buildForWebDriver(webDriver);
    }

    protected void setOptionalProxyConfiguration(DesiredCapabilities capabilities) {
        if (proxyConfiguration != null) {
            Proxy proxy = new Proxy();
            proxyConfiguration.configureProxy(proxy);
            capabilities.setCapability(CapabilityType.PROXY, proxy);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T withProxyConfiguration(ProxyConfiguration configuration) {
        proxyConfiguration = configuration;
        return (T) this;
    }

}
