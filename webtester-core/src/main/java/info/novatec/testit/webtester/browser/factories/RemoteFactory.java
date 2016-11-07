package info.novatec.testit.webtester.browser.factories;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.BiFunction;

import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.BrowserFactory;
import info.novatec.testit.webtester.browser.WebDriverBrowser;
import info.novatec.testit.webtester.browser.proxy.ProxyConfiguration;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.config.ConfigurationAdapter;
import info.novatec.testit.webtester.config.ConfigurationException;
import info.novatec.testit.webtester.config.builders.DefaultConfigurationBuilder;
import info.novatec.testit.webtester.internal.configuration.NamedProperties;


/**
 * Factory class for creating Selenium Grid remote {@link Browser} instances.
 * <p>
 * The remote driver can be configured by setting the following properties in you {@link Configuration} file(s):
 * <ul>
 * <li>{@link NamedProperties#REMOTE_BROWSER_NAME}</li>
 * <li>{@link NamedProperties#REMOTE_BROWSER_VERSION}</li>
 * <li>{@link NamedProperties#REMOTE_FIREFOX_MARIONETTE}</li>
 * <li>{@link NamedProperties#REMOTE_HOST}</li>
 * <li>{@link NamedProperties#REMOTE_PORT}</li>
 * </ul>
 * You can also override these properties by specifying new values as System Properties.
 * <p>
 * <b>The following capabilities are set by default:</b>
 * <ul>
 * <li>Native Events are disabled</li>
 * <li>Unsigned certificates are accepted</li>
 * </ul>
 *
 * @see Browser
 * @see RemoteWebDriver
 * @see Configuration
 * @see Configuration#getRemoteBrowserName()
 * @see Configuration#getRemoteBrowserVersion()
 * @see Configuration#getRemoteFirefoxMarionette()
 * @see Configuration#getRemoteHost()
 * @see Configuration#getRemotePort()
 * @since 2.1
 */
@Slf4j
public class RemoteFactory implements BrowserFactory {

    private final Configuration configuration;
    private final BiFunction<URL, DesiredCapabilities, WebDriver> webDriverProducer;
    private ProxyConfiguration proxyConfiguration;

    public RemoteFactory() {
        this(new RemoteConfigurationBuilder().build(), RemoteWebDriver::new);
    }

    RemoteFactory(Configuration configuration, BiFunction<URL, DesiredCapabilities, WebDriver> webDriverProducer) {
        this.configuration = configuration;
        this.webDriverProducer = webDriverProducer;
    }

    @Override
    public Browser createBrowser() {
        String browserName = configuration.getRemoteBrowserName();
        String browserVersion = configuration.getRemoteBrowserVersion();
        DesiredCapabilities capabilities = new DesiredCapabilities(browserName, browserVersion, Platform.ANY);
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capabilities.setCapability(CapabilityType.HAS_NATIVE_EVENTS, false);
        capabilities.setCapability("marionette", configuration.getRemoteFirefoxMarionette());
        setOptionalProxyConfiguration(capabilities);
        return createBrowser(capabilities);
    }

    @Override
    public Browser createBrowser(DesiredCapabilities capabilities) {
        String host = configuration.getRemoteHost();
        Integer port = configuration.getRemotePort();
        String url = "http://" + host + ":" + port + "/wd/hub";
        try {
            log.debug("creating remote driver instance for {} with capabilities: {}", url, capabilities);
            return createBrowser(webDriverProducer.apply(new URL(url), capabilities));
        } catch (MalformedURLException e) {
            throw new MalformedRemoteFactoryUrlException(url, e);
        }
    }

    private void setOptionalProxyConfiguration(DesiredCapabilities capabilities) {
        if (proxyConfiguration != null) {
            Proxy proxy = new Proxy();
            proxyConfiguration.configureProxy(proxy);
            capabilities.setCapability(CapabilityType.PROXY, proxy);
        }
    }

    @Override
    public Browser createBrowser(WebDriver webDriver) {
        return WebDriverBrowser.forWebDriver(webDriver).withConfiguration(configuration).build();
    }

    @Override
    public RemoteFactory withProxyConfiguration(ProxyConfiguration configuration) {
        proxyConfiguration = configuration;
        return this;
    }

    static class RemoteConfigurationBuilder extends DefaultConfigurationBuilder {
        RemoteConfigurationBuilder() {
            withAdapter(new SystemPropertyOverrideAdapter());
        }
    }

    @Slf4j
    private static class SystemPropertyOverrideAdapter implements ConfigurationAdapter {

        @Override
        public boolean adapt(Configuration configuration) {
            adaptIfNotNull(configuration, NamedProperties.REMOTE_BROWSER_NAME);
            adaptIfNotNull(configuration, NamedProperties.REMOTE_BROWSER_VERSION);
            adaptIfNotNull(configuration, NamedProperties.REMOTE_HOST);
            adaptIfNotNull(configuration, NamedProperties.REMOTE_PORT);
            adaptIfNotNull(configuration, NamedProperties.REMOTE_FIREFOX_MARIONETTE);
            return true;
        }

        private void adaptIfNotNull(Configuration configuration, NamedProperties property) {
            String key = property.getKey();
            String value = System.getProperty(key);
            if (value != null) {
                configuration.setProperty(key, value);
            }
        }

    }

    public static class MalformedRemoteFactoryUrlException extends ConfigurationException {
        public MalformedRemoteFactoryUrlException(String url, Throwable cause) {
            super("The Selenium Grid Hub URL was malformed: " + url, cause);
        }
    }

}
