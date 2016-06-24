package info.novatec.testit.webtester.junit.runner;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;

import utils.MockedTestBrowserFactory;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit.annotations.ConfigurationValue;
import info.novatec.testit.webtester.junit.annotations.CreateUsing;
import info.novatec.testit.webtester.junit.annotations.Primary;
import info.novatec.testit.webtester.junit.exceptions.NoBrowserFactoryProvidedException;
import info.novatec.testit.webtester.junit.exceptions.NoManagedBrowserException;
import info.novatec.testit.webtester.junit.exceptions.NoPrimaryBrowserException;
import info.novatec.testit.webtester.junit.exceptions.NoStaticPrimaryBrowserException;
import info.novatec.testit.webtester.junit.exceptions.NoUniquePrimaryBrowserException;
import info.novatec.testit.webtester.junit.exceptions.NotOfInjectableFieldTypeException;


@RunWith(Enclosed.class)
public class WebTesterJUnitRunnerTest {

    public static class StaticBrowserCreation {

        @Test
        public void staticBrowserIsCreatedWithFactory() throws Throwable {
            executeTestClass(StaticBrowserCreationTest.class);
        }

        public static class StaticBrowserCreationTest {

            @Resource
            @CreateUsing(MockedTestBrowserFactory.class)
            static Browser staticBrowser;

            @Test
            public void test() {
                assertThat(staticBrowser).isNotNull();
            }

        }

        @Test
        public void existingBrowserIsManaged() throws Throwable {
            executeTestClass(StaticBrowserManagementTest.class);
        }

        public static class StaticBrowserManagementTest {

            @Resource
            static Browser staticBrowser = new MockedTestBrowserFactory().createBrowser();

            @Test
            public void test() {
                assertThat(staticBrowser).isNotNull();
            }

        }

        @Test(expected = NoBrowserFactoryProvidedException.class)
        public void browserMustBeInitializedOrFactoryProvided() throws Throwable {
            executeTestClass(StaticUnmanageableBrowserTest.class);
        }

        public static class StaticUnmanageableBrowserTest {

            @Resource
            static Browser staticBrowser;

            @Test
            public void test() {
                assertThat(staticBrowser).isNotNull();
            }

        }

    }

    public static class StaticConfigurationInjection {

        @Test
        public void browserIsCreatedWithFactory() throws Throwable {
            executeTestClass(StaticConfigurationInjectionTest.class);
        }

        public static class StaticConfigurationInjectionTest {

            @Resource
            @CreateUsing(MockedTestBrowserFactory.class)
            static Browser browser;

            @ConfigurationValue("prop.string")
            static String stringValue;
            @ConfigurationValue("prop.boolean")
            static Boolean booleanValue;
            @ConfigurationValue("prop.integer")
            static Integer integerValue;
            @ConfigurationValue("prop.long")
            static Long longValue;
            @ConfigurationValue("prop.float")
            static Float floatValue;
            @ConfigurationValue("prop.double")
            static Double doubleValue;

            @Test
            public void test() {
                assertThat(stringValue).isEqualTo("some string");
                assertThat(booleanValue).isEqualTo(true);
                assertThat(integerValue).isEqualTo(1);
                assertThat(longValue).isEqualTo(2L);
                assertThat(floatValue).isEqualTo(1.5f);
                assertThat(doubleValue).isEqualTo(2.5d);
            }

        }

        @Test(expected = NotOfInjectableFieldTypeException.class)
        public void unmappedPropertyTypeThrowsException() throws Throwable {
            executeTestClass(UnmappedPropertyTypeTest.class);
        }

        public static class UnmappedPropertyTypeTest {

            @Resource
            @CreateUsing(MockedTestBrowserFactory.class)
            static Browser browser;

            @ConfigurationValue("prop.string")
            static Object value;

            @Test
            public void test() {
                assertThat(value).isNotNull();
            }

        }

        @Test(expected = NoManagedBrowserException.class)
        public void browserIsNeededForConfigurationInjection() throws Throwable {
            executeTestClass(BrowserIsNeededForConfigurationInjectionTest.class);
        }

        public static class BrowserIsNeededForConfigurationInjectionTest {

            @ConfigurationValue("prop.string")
            static String stringValue;

            @Test
            public void test() {
                assertThat(stringValue).isEqualTo("some value");
            }

        }

    }

    public static class StaticPrimaryBrowser {

        @Test
        public void withoutConfigurationInjectionNoPrimaryBrowserIsNeeded() throws Throwable {
            executeTestClass(NoConfigNoPrimaryNeededTest.class);
        }

        public static class NoConfigNoPrimaryNeededTest {

            @Resource
            @CreateUsing(MockedTestBrowserFactory.class)
            static Browser browser1;
            @Resource
            @CreateUsing(MockedTestBrowserFactory.class)
            static Browser browser2;

            @Test
            public void test() {
                assertThat(browser1).isNotNull();
                assertThat(browser2).isNotNull();
            }

        }

        @Test(expected = NoPrimaryBrowserException.class)
        public void withConfigurationInjectionPrimaryBrowserIsNeeded() throws Throwable {
            executeTestClass(ConfigurationInjectionNeedsPrimaryBrowserTest.class);
        }

        public static class ConfigurationInjectionNeedsPrimaryBrowserTest {

            @Resource
            @CreateUsing(MockedTestBrowserFactory.class)
            static Browser browser1;
            @Resource
            @CreateUsing(MockedTestBrowserFactory.class)
            static Browser browser2;

            @ConfigurationValue("prop.string")
            static String stringValue;

            @Test
            public void test() {
                assertThat(browser1).isNotNull();
                assertThat(browser2).isNotNull();
                assertThat(stringValue).isEqualTo("some string");
            }

        }

        @Test
        public void primaryBrowserSolvesConflict() throws Throwable {
            executeTestClass(PrimaryBrowserSolvesConflictTest.class);
        }

        public static class PrimaryBrowserSolvesConflictTest {

            @Primary
            @Resource
            @CreateUsing(MockedTestBrowserFactory.class)
            static Browser browser1;
            @Resource
            @CreateUsing(MockedTestBrowserFactory.class)
            static Browser browser2;

            @ConfigurationValue("prop.string")
            static String stringValue;

            @Test
            public void test() {
                assertThat(browser1).isNotNull();
                assertThat(browser2).isNotNull();
                assertThat(stringValue).isEqualTo("some string");
            }

        }

        @Test(expected = NoUniquePrimaryBrowserException.class)
        public void onlyOnePrimaryBrowserMustExist() throws Throwable {
            executeTestClass(OnlyOnePrimaryBrowserTest.class);
        }

        public static class OnlyOnePrimaryBrowserTest {

            @Primary
            @Resource
            @CreateUsing(MockedTestBrowserFactory.class)
            static Browser browser1;
            @Primary
            @Resource
            @CreateUsing(MockedTestBrowserFactory.class)
            static Browser browser2;

            @ConfigurationValue("prop.string")
            static String stringValue;

            @Test
            public void test() {
                assertThat(browser1).isNotNull();
                assertThat(browser2).isNotNull();
                assertThat(stringValue).isEqualTo("some string");
            }

        }

    }

    public static class BrowserCreation {

        @Test
        public void browserIsCreatedWithFactory() throws Throwable {
            executeTestClass(BrowserCreationTest.class);
        }

        public static class BrowserCreationTest {

            @Resource
            @CreateUsing(MockedTestBrowserFactory.class)
            Browser browser;

            @Test
            public void test() {
                assertThat(browser).isNotNull();
            }

        }

        @Test
        public void existingBrowserIsManaged() throws Throwable {
            executeTestClass(BrowserCreationTest.class);
        }

        public static class StaticBrowserManagementTest {

            @Resource
            Browser browser = new MockedTestBrowserFactory().createBrowser();

            @Test
            public void test() {
                assertThat(browser).isNotNull();
            }

        }

        @Test(expected = NoBrowserFactoryProvidedException.class)
        public void browserMustBeInitializedOrFactoryProvided() throws Throwable {
            executeTestClass(UnmanageableBrowserTest.class);
        }

        public static class UnmanageableBrowserTest {

            @Resource
            Browser browser;

            @Test
            public void test() {
                assertThat(browser).isNotNull();
            }

        }

    }

    public static class ConfigurationInjection {

        @Test
        public void browserIsCreatedWithFactory() throws Throwable {
            executeTestClass(ConfigurationInjectionTest.class);
        }

        public static class ConfigurationInjectionTest {

            @Resource
            @CreateUsing(MockedTestBrowserFactory.class)
            Browser browser;

            @ConfigurationValue("prop.string")
            String stringValue;
            @ConfigurationValue("prop.boolean")
            Boolean booleanValue;
            @ConfigurationValue("prop.integer")
            Integer integerValue;
            @ConfigurationValue("prop.long")
            Long longValue;
            @ConfigurationValue("prop.float")
            Float floatValue;
            @ConfigurationValue("prop.double")
            Double doubleValue;

            @Test
            public void test() {
                assertThat(stringValue).isEqualTo("some string");
                assertThat(booleanValue).isEqualTo(true);
                assertThat(integerValue).isEqualTo(1);
                assertThat(longValue).isEqualTo(2L);
                assertThat(floatValue).isEqualTo(1.5f);
                assertThat(doubleValue).isEqualTo(2.5d);
            }

        }

        @Test(expected = NotOfInjectableFieldTypeException.class)
        public void unmappedPropertyTypeThrowsException() throws Throwable {
            executeTestClass(UnmappedPropertyTypeTest.class);
        }

        public static class UnmappedPropertyTypeTest {

            @Resource
            @CreateUsing(MockedTestBrowserFactory.class)
            Browser browser;

            @ConfigurationValue("prop.string")
            Object value;

            @Test
            public void test() {
                assertThat(value).isNotNull();
            }

        }

        @Test(expected = NoManagedBrowserException.class)
        public void browserIsNeededForConfigurationInjection() throws Throwable {
            executeTestClass(BrowserIsNeededForConfigurationInjectionTest.class);
        }

        public static class BrowserIsNeededForConfigurationInjectionTest {

            @ConfigurationValue("prop.string")
            String stringValue;

            @Test
            public void test() {
                assertThat(stringValue).isEqualTo("some value");
            }

        }

    }

    public static class PrimaryBrowser {

        @Test
        public void withoutConfigurationInjectionNoPrimaryBrowserIsNeeded() throws Throwable {
            executeTestClass(NoConfigNoPrimaryNeededTest.class);
        }

        public static class NoConfigNoPrimaryNeededTest {

            @Resource
            @CreateUsing(MockedTestBrowserFactory.class)
            Browser browser1;
            @Resource
            @CreateUsing(MockedTestBrowserFactory.class)
            Browser browser2;

            @Test
            public void test() {
                assertThat(browser1).isNotNull();
                assertThat(browser2).isNotNull();
            }

        }

        @Test(expected = NoPrimaryBrowserException.class)
        public void withConfigurationInjectionPrimaryBrowserIsNeeded() throws Throwable {
            executeTestClass(ConfigurationInjectionNeedsPrimaryBrowserTest.class);
        }

        public static class ConfigurationInjectionNeedsPrimaryBrowserTest {

            @Resource
            @CreateUsing(MockedTestBrowserFactory.class)
            Browser browser1;
            @Resource
            @CreateUsing(MockedTestBrowserFactory.class)
            Browser browser2;

            @ConfigurationValue("prop.string")
            String stringValue;

            @Test
            public void test() {
                assertThat(browser1).isNotNull();
                assertThat(browser2).isNotNull();
                assertThat(stringValue).isEqualTo("some string");
            }

        }

        @Test
        public void primaryBrowserSolvesConflict() throws Throwable {
            executeTestClass(PrimaryBrowserSolvesConflictTest.class);
        }

        public static class PrimaryBrowserSolvesConflictTest {

            @Primary
            @Resource
            @CreateUsing(MockedTestBrowserFactory.class)
            Browser browser1;
            @Resource
            @CreateUsing(MockedTestBrowserFactory.class)
            Browser browser2;

            @ConfigurationValue("prop.string")
            String stringValue;

            @Test
            public void test() {
                assertThat(browser1).isNotNull();
                assertThat(browser2).isNotNull();
                assertThat(stringValue).isEqualTo("some string");
            }

        }

        @Test(expected = NoUniquePrimaryBrowserException.class)
        public void onlyOnePrimaryBrowserMustExist() throws Throwable {
            executeTestClass(OnlyOnePrimaryBrowserTest.class);
        }

        public static class OnlyOnePrimaryBrowserTest {

            @Primary
            @Resource
            @CreateUsing(MockedTestBrowserFactory.class)
            Browser browser1;
            @Primary
            @Resource
            @CreateUsing(MockedTestBrowserFactory.class)
            Browser browser2;

            @ConfigurationValue("prop.string")
            String stringValue;

            @Test
            public void test() {
                assertThat(browser1).isNotNull();
                assertThat(browser2).isNotNull();
                assertThat(stringValue).isEqualTo("some string");
            }

        }

    }

    public static class ConfigurationAndBrowserScope {

        @Test
        public void instanceFieldCanBeInjectedFromStaticBrowser() throws Throwable {
            executeTestClass(StaticBrowserInstanceFieldTest.class);
        }

        public static class StaticBrowserInstanceFieldTest {

            @Resource
            @CreateUsing(MockedTestBrowserFactory.class)
            static Browser browser;

            @ConfigurationValue("prop.string")
            String stringValue;

            @Test
            public void test() {
                assertThat(browser).isNotNull();
                assertThat(stringValue).isEqualTo("some string");
            }

        }

        @Test(expected = NoStaticPrimaryBrowserException.class)
        public void staticFieldCantBeInjectedFromInstanceBrowser() throws Throwable {
            executeTestClass(InstanceBrowserStaticFieldTest.class);
        }

        public static class InstanceBrowserStaticFieldTest {

            @Resource
            @CreateUsing(MockedTestBrowserFactory.class)
            Browser browser;

            @ConfigurationValue("prop.string")
            static String stringValue;

            @Test
            public void test() {
                assertThat(browser).isNotNull();
                assertThat(stringValue).isEqualTo("some string");
            }

        }

    }

    private static void executeTestClass(Class<?> testClass) throws Throwable {

        Throwable[] throwables = new Throwable[1];
        WebTesterJUnitRunner runner = new WebTesterJUnitRunner(testClass);
        RunNotifier notifier = new RunNotifier();
        notifier.addListener(new RunListener() {

            public void testFailure(Failure failure) throws Exception {
                System.out.println("testFailure");
                throwables[0] = failure.getException();
            }

        });
        runner.run(notifier);

        if (throwables[0] != null) {
            throw throwables[0];
        }

    }

}
