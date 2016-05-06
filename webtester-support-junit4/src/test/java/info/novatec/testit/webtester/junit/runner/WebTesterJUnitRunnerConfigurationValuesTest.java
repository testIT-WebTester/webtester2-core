package info.novatec.testit.webtester.junit.runner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.WebDriverBrowser;
import info.novatec.testit.webtester.junit.annotations.ConfigurationValue;
import info.novatec.testit.webtester.junit.annotations.Primary;


@RunWith(WebTesterJUnitRunner.class)
public class WebTesterJUnitRunnerConfigurationValuesTest {

    private static WebDriver webDriver = mock(WebDriver.class);

    @Primary
    @Resource
    private static Browser staticBrowser = WebDriverBrowser.buildForWebDriver(webDriver);
    @Resource
    private Browser browser = WebDriverBrowser.buildForWebDriver(webDriver);

    @ConfigurationValue("custom.integer")
    private static Integer customInteger;
    @ConfigurationValue("custom.string")
    private String customString;

    @BeforeClass
    public static void beforeClass() {
        assertThat(customInteger).isEqualTo(42);
    }

    @Before
    public void before() {
        assertThat(customInteger).isEqualTo(42);
        assertThat(customString).isEqualTo("foo bar");
    }

    @Test
    public void test1() {
        assertThat(customInteger).isEqualTo(42);
        assertThat(customString).isEqualTo("foo bar");
    }

}
