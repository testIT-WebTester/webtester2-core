package info.novatec.testit.webtester.internal.implementation.pages;

import static info.novatec.testit.webtester.pagefragments.identification.ByProducers.css;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.internal.implementation.pages.exceptions.PageMustBeInterfaceException;
import info.novatec.testit.webtester.pagefragments.TextField;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pages.Page;


class ByteBuddyPageFactoryTest {

    WebDriver webDriver = mock(WebDriver.class);
    Configuration configuration = mock(Configuration.class);
    Browser browser = mock(Browser.class);
    ByteBuddyPageFactory cut = new ByteBuddyPageFactory(browser);

    @BeforeEach
    void setup() {
        when(browser.webDriver()).thenReturn(webDriver);
        when(browser.configuration()).thenReturn(configuration);
    }

    @Test
    void onlyInterfacesCanBeCreated() {
        assertThrows(PageMustBeInterfaceException.class, () -> {
            cut.createInstanceOf(NotInterface.class);
        });
    }

    @Nested
    class BasicObjectMethods {

        @Test
        void hashCodeIsImplemented() {
            int hashCode = createTestPage().hashCode();
            assertThat(hashCode).isNotZero();
        }

        @Test
        void hashCodeIsConstantForSameInstance() {
            TestPage page = createTestPage();
            int firstHashCode = page.hashCode();
            int secondHashCode = page.hashCode();
            assertThat(firstHashCode).isEqualTo(secondHashCode);
        }

        @Test
        void hashCodeIsDifferentForEachInstance() {
            int firstHashCode = createTestPage().hashCode();
            int secondHashCode = createTestPage().hashCode();
            assertThat(firstHashCode).isNotEqualTo(secondHashCode);
        }

        @Test
        void equalsIsImplemented() {
            TestPage page1 = createTestPage();
            TestPage page2 = createTestPage();
            assertThat(page1).isEqualTo(page1);
            assertThat(page2).isEqualTo(page2);
            assertThat(page1).isNotEqualTo(page2);
        }

        @Test
        void toStringIsImplemented() {
            TestPage page = createTestPage();
            String toString = page.toString();
            assertThat(toString).startsWith("info.novatec.testit.webtester.internal.implementation.pages."//
                + "ByteBuddyPageFactoryTest.TestPage$$Impl@");
        }

    }

    @Test
    void defaultMethodsAreImplemented() {
        TestPage page = createTestPage();
        int result = page.defaultMethodAbc(42);
        assertThat(result).isEqualTo(42);
    }

    @Test
    void browserGetterIsImplemented() {
        TestPage page = createTestPage();
        Browser returnedBrowser = page.browser();
        assertThat(returnedBrowser).isSameAs(browser);
    }

    @Test
    void pageCreationIsImplemented() {
        TestPage mockPage = mock(TestPage.class);
        given(browser.create(TestPage.class)).willReturn(mockPage);
        TestPage page = createTestPage();

        TestPage createdPage = page.create(TestPage.class);
        assertThat(createdPage).isSameAs(mockPage);
    }

    @Nested
    class IdentifyUsingImplementation {

        WebElement someWebElement = mock(WebElement.class);
        WebElement firstWebElement = mock(WebElement.class);
        WebElement secondWebElement = mock(WebElement.class);

        @BeforeEach
        void setupWebElements() {
            given(someWebElement.getTagName()).willReturn("input");
            given(someWebElement.getAttribute("type")).willReturn("text");
            given(firstWebElement.getTagName()).willReturn("input");
            given(firstWebElement.getAttribute("type")).willReturn("text");
            given(secondWebElement.getTagName()).willReturn("input");
            given(secondWebElement.getAttribute("type")).willReturn("text");
        }

        @Test
        void singlePageFragment_found() {
            given(webDriver.findElement(css("#someField"))).willReturn(someWebElement);
            TestPage page = createTestPage();
            TextField someField = page.someField();
            assertThat(someField.webElement()).isSameAs(someWebElement);
        }

        @Test
        void streamOfPageFragments() {
            given(webDriver.findElements(css(".field"))).willReturn(asList(firstWebElement, secondWebElement));
            TestPage page = createTestPage();
            Stream<TextField> someFields = page.streamOfAllFields();
            assertThat(someFields).extracting(TextField::webElement).containsExactly(firstWebElement, secondWebElement);
        }

        @Test
        void listOfPageFragments() {
            given(webDriver.findElements(css(".field"))).willReturn(asList(firstWebElement, secondWebElement));
            TestPage page = createTestPage();
            List<TextField> someFields = page.listOfAllFields();
            assertThat(someFields).extracting(TextField::webElement).containsExactly(firstWebElement, secondWebElement);
        }

        @Test
        void setOfPageFragments() {
            given(webDriver.findElements(css(".field"))).willReturn(asList(firstWebElement, secondWebElement));
            TestPage page = createTestPage();
            Set<TextField> someFields = page.setOfAllFields();
            assertThat(someFields).extracting(TextField::webElement).containsOnly(firstWebElement, secondWebElement);
        }

        @Test
        void collectionOfPageFragments() {
            given(webDriver.findElements(css(".field"))).willReturn(asList(firstWebElement, secondWebElement));
            TestPage page = createTestPage();
            Collection<TextField> someFields = page.collectionOfAllFields();
            assertThat(someFields).extracting(TextField::webElement).containsExactly(firstWebElement, secondWebElement);
        }

    }

    TestPage createTestPage() {
        return cut.createInstanceOf(TestPage.class);
    }

    public abstract class NotInterface implements Page {

    }

    public interface TestPage extends Page {

        @IdentifyUsing("#someField")
        TextField someField();

        @IdentifyUsing(".field")
        Stream<TextField> streamOfAllFields();

        @IdentifyUsing(".field")
        List<TextField> listOfAllFields();

        @IdentifyUsing(".field")
        Set<TextField> setOfAllFields();

        @IdentifyUsing(".field")
        Collection<TextField> collectionOfAllFields();

        default int defaultMethodAbc(int value) {
            return value;
        }

    }

}