package info.novatec.testit.webtester.browser.factories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.internal.configuration.NamedProperties;


public class RemoteConfigurationBuilderTest {

    List<String> setSystemProperties = new ArrayList<>();

    @After
    public void clearSystemProperties() {
        setSystemProperties.forEach(System::clearProperty);
    }

    @Test
    public void systemPropertiesOverrideIsOptional() {

        Configuration configuration = new RemoteFactory.RemoteConfigurationBuilder().build();

        assertThat(configuration.getRemoteBrowserName()).isEqualTo("firefox");
        assertThat(configuration.getRemoteBrowserVersion()).isEqualTo("");
        assertThat(configuration.getRemoteFirefoxMarionette()).isEqualTo(true);
        assertThat(configuration.getRemoteHost()).isEqualTo("localhost");
        assertThat(configuration.getRemotePort()).isEqualTo(4444);

    }

    @Test
    public void systemPropertiesCanBeUsedToOverrideDefaultValues() {

        setSystemProperty(NamedProperties.REMOTE_BROWSER_NAME, "non-default");
        setSystemProperty(NamedProperties.REMOTE_BROWSER_VERSION, "1.2.3");
        setSystemProperty(NamedProperties.REMOTE_FIREFOX_MARIONETTE, "false");
        setSystemProperty(NamedProperties.REMOTE_HOST, "123.123.123.123");
        setSystemProperty(NamedProperties.REMOTE_PORT, "8888");

        Configuration configuration = new RemoteFactory.RemoteConfigurationBuilder().build();

        assertThat(configuration.getRemoteBrowserName()).isEqualTo("non-default");
        assertThat(configuration.getRemoteBrowserVersion()).isEqualTo("1.2.3");
        assertThat(configuration.getRemoteFirefoxMarionette()).isEqualTo(false);
        assertThat(configuration.getRemoteHost()).isEqualTo("123.123.123.123");
        assertThat(configuration.getRemotePort()).isEqualTo(8888);

    }

    void setSystemProperty(NamedProperties property, String value) {
        setSystemProperties.add(property.getKey());
        System.setProperty(property.getKey(), value);
    }

}
