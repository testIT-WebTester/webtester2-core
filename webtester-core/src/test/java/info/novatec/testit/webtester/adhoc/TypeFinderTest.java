package info.novatec.testit.webtester.adhoc;

import static info.novatec.testit.webtester.pagefragments.identification.ByProducers.className;
import static info.novatec.testit.webtester.pagefragments.identification.ByProducers.id;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.testit.testutils.mockito.junit5.EnableMocking;

import info.novatec.testit.webtester.internal.PageFragmentFactory;
import info.novatec.testit.webtester.pagefragments.PageFragment;


@EnableMocking
class TypeFinderTest {

    @Mock
    PageFragmentFactory factory;
    @Mock
    SearchContext searchContext;

    TypeFinder<TestFragment> cut;

    @Captor
    ArgumentCaptor<By> byCaptor;

    @BeforeEach
    void init() {
        cut = new TypeFinder<>(factory, searchContext, TestFragment.class);
    }

    @Test
    @DisplayName("by(String) returns found element")
    void byForStringReturnsElement() {

        WebElement webElement = mock(WebElement.class);
        TestFragment mockElement = mock(TestFragment.class);

        doReturn(webElement).when(searchContext).findElement(any(By.ByCssSelector.class));
        doReturn(mockElement).when(factory).pageFragment(TestFragment.class, webElement);

        TestFragment element = cut.by("#someId");
        assertThat(element).isSameAs(mockElement);

    }

    @Test
    @DisplayName("by(By) returns found element")
    void byForByReturnsElement() {

        WebElement webElement = mock(WebElement.class);
        TestFragment mockElement = mock(TestFragment.class);

        doReturn(webElement).when(searchContext).findElement(any(By.ById.class));
        doReturn(mockElement).when(factory).pageFragment(TestFragment.class, webElement);

        TestFragment element = cut.by(id("someId"));
        assertThat(element).isSameAs(mockElement);

    }

    @Test
    @DisplayName("manyBy(String) returns stream of found elements")
    void manyByForStringReturnsStream() {

        WebElement webElement1 = mock(WebElement.class);
        WebElement webElement2 = mock(WebElement.class);
        doReturn(asList(webElement1, webElement2)).when(searchContext).findElements(any(By.ByCssSelector.class));

        TestFragment mockElement1 = mock(TestFragment.class);
        TestFragment mockElement2 = mock(TestFragment.class);
        doReturn(mockElement1).when(factory).pageFragment(TestFragment.class, webElement1);
        doReturn(mockElement2).when(factory).pageFragment(TestFragment.class, webElement2);

        Stream<TestFragment> elements = cut.manyBy(".someClass");
        assertThat(elements).containsExactly(mockElement1, mockElement2);

    }

    @Test
    @DisplayName("manyBy(By) returns stream of found elements")
    void manyByForByReturnsStream() {

        WebElement webElement1 = mock(WebElement.class);
        WebElement webElement2 = mock(WebElement.class);
        doReturn(asList(webElement1, webElement2)).when(searchContext).findElements(any(By.ByClassName.class));

        TestFragment mockElement1 = mock(TestFragment.class);
        TestFragment mockElement2 = mock(TestFragment.class);
        doReturn(mockElement1).when(factory).pageFragment(TestFragment.class, webElement1);
        doReturn(mockElement2).when(factory).pageFragment(TestFragment.class, webElement2);

        Stream<TestFragment> elements = cut.manyBy(className("someClass"));
        assertThat(elements).containsExactly(mockElement1, mockElement2);

    }

    @Nested
    @DisplayName("String parameters are interpreted as CSS Selectors")
    class StringParametersAreCssSelectors {

        @Test
        void by() {

            cut.by("#someId");

            verify(searchContext).findElement(byCaptor.capture());

            By by = byCaptor.getValue();
            assertThat(by).isInstanceOf(By.ByCssSelector.class);
            assertThat(by).hasToString("By.cssSelector: #someId");

        }

        @Test
        void manyBy() {

            cut.manyBy(".someClass");

            verify(searchContext).findElements(byCaptor.capture());

            By by = byCaptor.getValue();
            assertThat(by).isInstanceOf(By.ByCssSelector.class);
            assertThat(by).hasToString("By.cssSelector: .someClass");

        }

    }

    @Nested
    @DisplayName("NoSuchElementException occurrences are propagated")
    class NoSuchElementExceptions {

        @Test
        void byString() {
            assertThrows(NoSuchElementException.class, () -> {
                doThrow(NoSuchElementException.class).when(searchContext).findElement(any(By.class));
                cut.by("#someId");
            });
        }

        @Test
        void byBy() {
            assertThrows(NoSuchElementException.class, () -> {
                doThrow(NoSuchElementException.class).when(searchContext).findElement(any(By.class));
                cut.by(id("someId"));
            });
        }

        @Test
        void manyByString() {
            assertThrows(NoSuchElementException.class, () -> {
                doThrow(NoSuchElementException.class).when(searchContext).findElements(any(By.class));
                cut.manyBy(".someClass");
            });
        }

        @Test
        void manyByBy() {
            assertThrows(NoSuchElementException.class, () -> {
                doThrow(NoSuchElementException.class).when(searchContext).findElements(any(By.class));
                cut.manyBy(className("someClass"));
            });
        }

    }

    public interface TestFragment extends PageFragment {
    }

}
