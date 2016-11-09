package info.novatec.testit.webtester.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

import org.junit.Test;
import org.openqa.selenium.WebDriver;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.WebDriverBrowser;
import info.novatec.testit.webtester.config.BaseConfiguration;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.internal.postconstruct.PostConstructInvoker;
import info.novatec.testit.webtester.pages.Page;


public class PostConstructInvokerTest {

    @Test
    public void testInvocationOfPostConstructMethodsOfPageClass() {

        TestContext.clear();

        WebDriver webDriver = mock(WebDriver.class);
        Configuration configuration = new BaseConfiguration();
        Browser browser = WebDriverBrowser.forWebDriver(webDriver).withConfiguration(configuration).build();

        TestPage page = new PageFactory(browser).page(TestPage.class);
        PostConstructInvoker.invokePostConstructMethods(TestPage.class, page);

        assertThat(TestContext.invokedMethods).containsOnly("assertSomethingOnPage", "assertSomethingElseOnPage");

    }

    private static class TestContext {
        static List<String> invokedMethods = new ArrayList<>();

        static void clear() {
            invokedMethods.clear();
        }
    }

    public interface TestPage extends Page {

        @PostConstruct
        default void assertSomething() {
            TestContext.invokedMethods.add("assertSomethingOnPage");
        }

        @PostConstruct
        default void assertSomethingElse() {
            TestContext.invokedMethods.add("assertSomethingElseOnPage");
        }

    }

}
