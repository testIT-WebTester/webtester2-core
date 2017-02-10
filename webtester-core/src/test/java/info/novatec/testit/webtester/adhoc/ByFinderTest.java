package info.novatec.testit.webtester.adhoc;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.testit.testutils.mockito.junit5.EnableMocking;

import info.novatec.testit.webtester.internal.PageFragmentFactory;
import info.novatec.testit.webtester.pagefragments.GenericElement;
import info.novatec.testit.webtester.pagefragments.PageFragment;


@EnableMocking
class ByFinderTest {

    @Mock
    PageFragmentFactory factory;
    @Mock
    SearchContext searchContext;
    @Mock
    By by;

    @InjectMocks
    ByFinder cut;

    @Test
    @DisplayName("asGeneric() returns GenericElement for found WebElement")
    void asGenericReturnsGenericElement() {

        WebElement webElement = mock(WebElement.class);
        doReturn(webElement).when(searchContext).findElement(by);

        GenericElement mockElement = mock(GenericElement.class);
        doReturn(mockElement).when(factory).pageFragment(GenericElement.class, webElement);

        GenericElement element = cut.asGeneric();
        assertThat(element).isSameAs(mockElement);

    }

    @Test
    @DisplayName("as(Class<? extends PageFragment>) returns instance of given type for found WebElement")
    void asReturnsInstanceOfGivenType() {

        WebElement webElement = mock(WebElement.class);
        doReturn(webElement).when(searchContext).findElement(by);

        TestFragment mockElement = mock(TestFragment.class);
        doReturn(mockElement).when(factory).pageFragment(TestFragment.class, webElement);

        TestFragment element = cut.as(TestFragment.class);
        assertThat(element).isSameAs(mockElement);

    }

    @Test
    @DisplayName("asManyGenerics() returns Stream of GenericElements for all found WebElements")
    void asManyGenericsReturnsGenericElements() {

        WebElement webElement1 = mock(WebElement.class);
        WebElement webElement2 = mock(WebElement.class);
        doReturn(asList(webElement1, webElement2)).when(searchContext).findElements(by);

        GenericElement mockElement1 = mock(GenericElement.class);
        GenericElement mockElement2 = mock(GenericElement.class);
        doReturn(mockElement1).when(factory).pageFragment(GenericElement.class, webElement1);
        doReturn(mockElement2).when(factory).pageFragment(GenericElement.class, webElement2);

        Stream<GenericElement> elements = cut.asManyGenerics();
        assertThat(elements).containsExactly(mockElement1, mockElement2);

    }

    @Test
    @DisplayName("asMany(Class<? extends PageFragment>) returns Stream of given type for all found WebElements")
    void asManyReturnsInstancesOfGivenType() {

        WebElement webElement1 = mock(WebElement.class);
        WebElement webElement2 = mock(WebElement.class);
        doReturn(asList(webElement1, webElement2)).when(searchContext).findElements(by);

        TestFragment mockElement1 = mock(TestFragment.class);
        TestFragment mockElement2 = mock(TestFragment.class);
        doReturn(mockElement1).when(factory).pageFragment(TestFragment.class, webElement1);
        doReturn(mockElement2).when(factory).pageFragment(TestFragment.class, webElement2);

        Stream<TestFragment> elements = cut.asMany(TestFragment.class);
        assertThat(elements).containsExactly(mockElement1, mockElement2);

    }

    @Nested
    @DisplayName("NoSuchElementException occurrences are propagated")
    class NoSuchElementExceptions {

        @Test
        void asGeneric() {
            doThrow(NoSuchElementException.class).when(searchContext).findElement(by);
            assertThrows(NoSuchElementException.class, () -> {
                cut.asGeneric();
            });
        }

        @Test
        @DisplayName("as(Class<? extends PageFragment>)")
        void as() {
            doThrow(NoSuchElementException.class).when(searchContext).findElement(by);
            assertThrows(NoSuchElementException.class, () -> {
                cut.as(TestFragment.class);
            });
        }

        @Test
        void asManyGenerics() {
            doThrow(NoSuchElementException.class).when(searchContext).findElements(by);
            assertThrows(NoSuchElementException.class, () -> {
                cut.asManyGenerics();
            });
        }

        @Test
        @DisplayName("asMany(Class<? extends PageFragment>)")
        void asMany() {
            doThrow(NoSuchElementException.class).when(searchContext).findElements(by);
            assertThrows(NoSuchElementException.class, () -> {
                cut.asMany(TestFragment.class);
            });
        }

    }

    interface TestFragment extends PageFragment {
    }

}
