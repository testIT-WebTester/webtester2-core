package info.novatec.testit.webtester.browser.proxy;

import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.Proxy;

@RunWith(MockitoJUnitRunner.class)
public class NoProxyConfigurationTest {

    @Mock
    Proxy proxy;
    @InjectMocks
    NoProxyConfiguration cut;

    @Test
    public void proxyIsNotTouchedInAnyWay() {
        cut.configureProxy(proxy);
        verifyNoInteractions(proxy);
    }

}
