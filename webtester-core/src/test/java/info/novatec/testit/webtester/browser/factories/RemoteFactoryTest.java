package info.novatec.testit.webtester.browser.factories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyNoInteractions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.BiFunction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.factories.RemoteFactory.MalformedRemoteFactoryUrlException;
import info.novatec.testit.webtester.browser.proxy.ProxyConfiguration;
import info.novatec.testit.webtester.config.Configuration;


@RunWith(MockitoJUnitRunner.class)
public class RemoteFactoryTest {

    @Mock
    Configuration configuration;
    @Mock
    BiFunction<URL, DesiredCapabilities, WebDriver> webDriverProducer;
    @InjectMocks
    RemoteFactory cut;

    @Captor
    ArgumentCaptor<URL> urlCaptor;
    @Captor
    ArgumentCaptor<DesiredCapabilities> capabilitiesCaptor;

    @Test
    public void webDriverIsInitializedUsingProducerWhenCreatingBrowser() {
        WebDriver webDriver = mock(WebDriver.class);
        given(webDriverProducer.apply(any(URL.class), any(DesiredCapabilities.class))).willReturn(webDriver);
        Browser browser = cut.createBrowser();
        assertThat(browser.webDriver()).isSameAs(webDriver);
    }

    @Test
    public void capabilitiesAreSetWhenCreatingBrowser() throws MalformedURLException {

        given(configuration.getRemoteBrowserName()).willReturn("firefox");
        given(configuration.getRemoteBrowserVersion()).willReturn("46.0.1");
        given(configuration.getRemoteFirefoxMarionette()).willReturn(true);
        given(configuration.getRemoteHost()).willReturn("localhost");
        given(configuration.getRemotePort()).willReturn(4444);

        cut.createBrowser();

        then(webDriverProducer).should().apply(urlCaptor.capture(), capabilitiesCaptor.capture());

        URL url = urlCaptor.getValue();
        assertThat(url).hasProtocol("http").hasHost("localhost").hasPort(4444).hasPath("/wd/hub");

        DesiredCapabilities capabilities = capabilitiesCaptor.getValue();
        assertThat(capabilities.getCapability(CapabilityType.BROWSER_NAME)).isEqualTo("firefox");
        assertThat(capabilities.getCapability(CapabilityType.VERSION)).isEqualTo("46.0.1");
        assertThat(capabilities.getCapability(CapabilityType.PLATFORM)).isEqualTo(Platform.ANY);
        assertThat(capabilities.getCapability(CapabilityType.HAS_NATIVE_EVENTS)).isEqualTo(false);
        assertThat(capabilities.getCapability(CapabilityType.ACCEPT_SSL_CERTS)).isEqualTo(true);
        assertThat(capabilities.getCapability("marionette")).isEqualTo(true);

    }

    @Test(expected = MalformedRemoteFactoryUrlException.class)
    public void malformedUrlThrowsException() throws MalformedURLException {

        given(configuration.getRemoteBrowserName()).willReturn("firefox");
        given(configuration.getRemoteBrowserVersion()).willReturn("46.0.1");
        given(configuration.getRemoteFirefoxMarionette()).willReturn(true);
        given(configuration.getRemoteHost()).willReturn("::::");
        given(configuration.getRemotePort()).willReturn(4444);

        cut.createBrowser();

    }

    @Test
    public void proxyCapabilityIsSetIfSpecifiedWhenCreatingBrowser() {

        ProxyConfiguration proxyConfiguration = proxy -> {
            proxy.setHttpProxy("foo-bar-proxy");
        };

        cut.withProxyConfiguration(proxyConfiguration).createBrowser();

        verify(webDriverProducer).apply(any(URL.class), capabilitiesCaptor.capture());
        verifyNoMoreInteractions(webDriverProducer);

        DesiredCapabilities capabilities = capabilitiesCaptor.getValue();
        Proxy proxy = ( Proxy ) capabilities.getCapability(CapabilityType.PROXY);
        assertThat(proxy.getHttpProxy()).isEqualTo("foo-bar-proxy");

    }

    @Test
    public void capabilitiesAreNotChangedWhenCreatingBrowserForThem() {
        DesiredCapabilities capabilities = mock(DesiredCapabilities.class);
        cut.createBrowser(capabilities);
        verify(webDriverProducer).apply(any(URL.class), eq(capabilities));
        verifyNoInteractions(capabilities);
    }

}
