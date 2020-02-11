package info.novatec.testit.webtester.junit.runner;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit.annotations.ConfigurationValue;
import info.novatec.testit.webtester.junit.annotations.CreateUsing;
import info.novatec.testit.webtester.junit.annotations.EntryPoint;
import info.novatec.testit.webtester.junit.annotations.Primary;
import org.junit.Test;
import org.junit.runner.RunWith;
import utils.TestBrowserFactory;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;


/**
 * This integration test test all of the features provided by our JUnit 4 runner in one big test.
 * It's very much a happy path test since all border cases are already tested in the test for each runner feature.
 * The focus of this test is on checking if there are any problems if all features are used in combination.
 */
@RunWith(WebTesterJUnitRunner.class)
public class WebTesterJUnitRunnerIntTest {

    @Resource
    @CreateUsing(TestBrowserFactory.class)
    @EntryPoint("http://${host}:${port}/index1.html")
    Browser browserOne;

    @Primary
    @Resource
    @CreateUsing(TestBrowserFactory.class)
    @EntryPoint("http://www.example.com")
    Browser browserTwo;

    @ConfigurationValue(value = "stringValue")
    String stringValue;
    @ConfigurationValue(value = "booleanValue")
    Boolean booleanValue;

    @Test
    public void browserUrlsAreInvoked() {
        verify(browserOne.webDriver()).get("http://localhost:8080/index1.html");
        verify(browserTwo.webDriver()).get("http://www.example.com");
    }

    @Test
    public void configurationValuesAreInjected() {
        assertThat(stringValue).isEqualTo("Hello World!");
        assertThat(booleanValue).isTrue();
    }

}
