package info.novatec.testit.webtester.browser.factories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.function.Function;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.proxy.ProxyConfiguration;


@RunWith(MockitoJUnitRunner.class)
public class BaseBrowserFactoryTest {

    @Mock
    Function<DesiredCapabilities, WebDriver> webDriverProducer;
    @InjectMocks
    BaseBrowserFactory cut;

    @Captor
    ArgumentCaptor<DesiredCapabilities> capabilitiesCaptor;

    @Test
    public void webDriverIsInitializedUsingProducerWhenCreatingBrowser() {
        WebDriver webDriver = mock(WebDriver.class);
        doReturn(webDriver).when(webDriverProducer).apply(any(DesiredCapabilities.class));
        Browser browser = cut.createBrowser();
        assertThat(browser.webDriver()).isSameAs(webDriver);
    }

    @Test
    public void defaultCapabilitiesAreSetWhenCreatingBrowser() {

        cut.createBrowser();

        verify(webDriverProducer).apply(capabilitiesCaptor.capture());
        verifyNoMoreInteractions(webDriverProducer);

        DesiredCapabilities capabilities = capabilitiesCaptor.getValue();
        assertThat(capabilities.getCapability(CapabilityType.HAS_NATIVE_EVENTS)).isEqualTo(false);
        assertThat(capabilities.getCapability(CapabilityType.ACCEPT_SSL_CERTS)).isEqualTo(true);

    }

    @Test
    public void proxyCapabilityIsSetIfSpecifiedWhenCreatingBrowser() {

        ProxyConfiguration proxyConfiguration = proxy -> {
            proxy.setHttpProxy("foo-bar-proxy");
        };

        cut.withProxyConfiguration(proxyConfiguration).createBrowser();

        verify(webDriverProducer).apply(capabilitiesCaptor.capture());
        verifyNoMoreInteractions(webDriverProducer);

        DesiredCapabilities capabilities = capabilitiesCaptor.getValue();
        Proxy proxy = ( Proxy ) capabilities.getCapability(CapabilityType.PROXY);
        assertThat(proxy.getHttpProxy()).isEqualTo("foo-bar-proxy");

    }

    @Test
    public void capabilitiesAreNotChangedWhenCreatingBrowserForThem() {
        DesiredCapabilities capabilities = mock(DesiredCapabilities.class);
        cut.createBrowser(capabilities);
        verify(webDriverProducer).apply(capabilities);
        verifyZeroInteractions(capabilities);
    }

}
