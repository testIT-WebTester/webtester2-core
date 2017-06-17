package info.novatec.testit.webtester.internal.implementation.pagefragments;

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
import info.novatec.testit.webtester.internal.implementation.PageFragmentFactory.PageFragmentDescriptor;
import info.novatec.testit.webtester.internal.implementation.pagefragments.exceptions.PageFragmentMustBeInterfaceException;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.TextField;
import info.novatec.testit.webtester.pagefragments.annotations.Action;
import info.novatec.testit.webtester.pagefragments.annotations.As;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.annotations.Mark;
import info.novatec.testit.webtester.pages.Page;


class ByteBuddyPageFragmentFactoryTest {

    WebDriver webDriver = mock(WebDriver.class);
    Configuration configuration = mock(Configuration.class);
    Browser browser = mock(Browser.class);
    ByteBuddyPageFragmentFactory cut = new ByteBuddyPageFragmentFactory(browser);

    WebElement webElement = mock(WebElement.class);

    @BeforeEach
    void setup() {
        when(browser.webDriver()).thenReturn(webDriver);
        when(browser.configuration()).thenReturn(configuration);
    }

    @Test
    void onlyInterfacesCanBeCreated() {
        assertThrows(PageFragmentMustBeInterfaceException.class, () -> {
            PageFragmentDescriptor descriptor = PageFragmentDescriptor.builder()
                .pageFragmentType(NotInterface.class)
                .webElementSupplier(new StaticWebElementSupplier(webElement))
                .build();
            cut.createInstanceOf(descriptor);
        });
    }

    @Nested
    class BasicObjectMethods {

        @Test
        void hashCodeIsImplemented() {
            int hashCode = createTestFragment().hashCode();
            assertThat(hashCode).isNotZero();
        }

        @Test
        void hashCodeIsConstantForSameInstance() {
            TestPageFragment fragment = createTestFragment();
            int firstHashCode = fragment.hashCode();
            int secondHashCode = fragment.hashCode();
            assertThat(firstHashCode).isEqualTo(secondHashCode);
        }

        @Test
        void hashCodeIsDifferentForEachInstance() {
            int firstHashCode = createTestFragment().hashCode();
            int secondHashCode = createTestFragment().hashCode();
            assertThat(firstHashCode).isNotEqualTo(secondHashCode);
        }

        @Test
        void equalsIsImplemented() {
            TestPageFragment page1 = createTestFragment();
            TestPageFragment page2 = createTestFragment();
            assertThat(page1).isEqualTo(page1);
            assertThat(page2).isEqualTo(page2);
            assertThat(page1).isNotEqualTo(page2);
        }

        @Test
        void toStringIsImplemented() {
            TestPageFragment fragment = createTestFragment();
            String toString = fragment.toString();
            assertThat(toString).startsWith("info.novatec.testit.webtester.internal.implementation.pagefragments."
                + "ByteBuddyPageFragmentFactoryTest.TestPageFragment$$Impl@");
        }

    }

    @Test
    void defaultMethodsAreImplemented() {
        TestPageFragment fragment = createTestFragment();
        int result = fragment.defaultMethod(42);
        assertThat(result).isEqualTo(42);
    }

    @Test
    void browserGetterIsImplemented() {
        TestPageFragment fragment = createTestFragment();
        Browser returnedBrowser = fragment.browser();
        assertThat(returnedBrowser).isSameAs(browser);
    }

    @Test
    void pageCreationIsImplemented() {
        TestPage mockPage = mock(TestPage.class);
        given(browser.create(TestPage.class)).willReturn(mockPage);
        TestPageFragment fragment = createTestFragment();

        TestPage createdPage = fragment.create(TestPage.class);
        assertThat(createdPage).isSameAs(mockPage);
    }

    @Test
    void nameGetterIsImplemented() {
        assertThat(createTestFragment().getName()).isEmpty();
        assertThat(createTestFragment("foo").getName()).hasValue("foo");
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
        void singlePageFragment() {
            given(webElement.findElement(css("#someField"))).willReturn(someWebElement);
            TestPageFragment fragment = createTestFragment();
            TextField someField = fragment.someField();
            assertThat(someField.webElement()).isSameAs(someWebElement);
        }

        @Test
        void streamOfPageFragments() {
            given(webElement.findElements(css(".field"))).willReturn(asList(firstWebElement, secondWebElement));
            TestPageFragment fragment = createTestFragment();
            Stream<TextField> someFields = fragment.streamOfAllFields();
            assertThat(someFields).extracting(TextField::webElement).containsExactly(firstWebElement, secondWebElement);
        }

        @Test
        void listOfPageFragments() {
            given(webElement.findElements(css(".field"))).willReturn(asList(firstWebElement, secondWebElement));
            TestPageFragment fragment = createTestFragment();
            List<TextField> someFields = fragment.listOfAllFields();
            assertThat(someFields).extracting(TextField::webElement).containsExactly(firstWebElement, secondWebElement);
        }

        @Test
        void setOfPageFragments() {
            given(webElement.findElements(css(".field"))).willReturn(asList(firstWebElement, secondWebElement));
            TestPageFragment fragment = createTestFragment();
            Set<TextField> someFields = fragment.setOfAllFields();
            assertThat(someFields).extracting(TextField::webElement).containsOnly(firstWebElement, secondWebElement);
        }

        @Test
        void collectionOfPageFragments() {
            given(webElement.findElements(css(".field"))).willReturn(asList(firstWebElement, secondWebElement));
            TestPageFragment fragment = createTestFragment();
            Collection<TextField> someFields = fragment.collectionOfAllFields();
            assertThat(someFields).extracting(TextField::webElement).containsExactly(firstWebElement, secondWebElement);
        }

    }

    TestPageFragment createTestFragment() {
        return createTestFragment(null);
    }

    TestPageFragment createTestFragment(String name) {
        PageFragmentDescriptor descriptor = PageFragmentDescriptor.builder()
            .pageFragmentType(TestPageFragment.class)
            .webElementSupplier(new StaticWebElementSupplier(webElement))
            .name(name)
            .build();
        return ( TestPageFragment ) cut.createInstanceOf(descriptor);
    }

    public interface TestPage extends Page {

    }

    public abstract class NotInterface implements PageFragment {

    }

    public interface TestPageFragment extends PageFragment {

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

        @Action
        @Mark(As.READ)
        default int defaultMethod(int value) {
            return value;
        }

    }

}