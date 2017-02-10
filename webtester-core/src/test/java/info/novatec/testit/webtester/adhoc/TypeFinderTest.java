package info.novatec.testit.webtester.adhoc;

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
import info.novatec.testit.webtester.pagefragments.identification.ByProducers;


@EnableMocking
class TypeFinderTest {

    @Captor
    ArgumentCaptor<By> byCaptor;

    @Mock
    PageFragmentFactory factory;
    @Mock
    SearchContext searchContext;

    TypeFinder<TestFragment> cut;

    @BeforeEach
    void init() {
        cut = new TypeFinder<>(factory, searchContext, TestFragment.class);
    }

    @Nested
    class ByForString {

        @Test
        void fragmentIsCreatedForGivenBy() {

            WebElement webElement = mock(WebElement.class);
            TestFragment mockElement = mock(TestFragment.class);

            doReturn(webElement).when(searchContext).findElement(any(By.class));
            doReturn(mockElement).when(factory).pageFragment(TestFragment.class, webElement);

            TestFragment element = cut.by("#someId");
            assertThat(element).isSameAs(mockElement);

        }

        @Test
        void stringIsInterpretedAsCssSelector() {

            cut.by("#someId");

            verify(searchContext).findElement(byCaptor.capture());

            By by = byCaptor.getValue();
            assertThat(by).isInstanceOf(By.ByCssSelector.class);
            assertThat(by).hasToString("By.cssSelector: #someId");

        }

        @Test
        void noSuchElementExceptionsArePropagated() {
            assertThrows(NoSuchElementException.class, () -> {
                doThrow(NoSuchElementException.class).when(searchContext).findElement(any(By.class));
                cut.by("#someId");
            });
        }

    }

    @Nested
    class ByForBy {

        @Test
        void fragmentIsCreatedForGivenBy() {

            WebElement webElement = mock(WebElement.class);
            TestFragment mockElement = mock(TestFragment.class);

            doReturn(webElement).when(searchContext).findElement(any(By.class));
            doReturn(mockElement).when(factory).pageFragment(TestFragment.class, webElement);

            TestFragment element = cut.by(ByProducers.id("someId"));
            assertThat(element).isSameAs(mockElement);

        }

        @Test
        void stringIsInterpretedAsCssSelector() {

            cut.by(ByProducers.id("someId"));

            verify(searchContext).findElement(byCaptor.capture());

            By by = byCaptor.getValue();
            assertThat(by).isInstanceOf(By.ById.class);
            assertThat(by).hasToString("By.id: someId");

        }

        @Test
        void noSuchElementExceptionsArePropagated() {
            assertThrows(NoSuchElementException.class, () -> {
                doThrow(NoSuchElementException.class).when(searchContext).findElement(any(By.class));
                cut.by(ByProducers.id("someId"));
            });
        }

    }

    @Nested
    class ManyByForString {

        @Test
        void fragmentIsCreatedForGivenBy() {

            WebElement webElement1 = mock(WebElement.class);
            WebElement webElement2 = mock(WebElement.class);
            doReturn(asList(webElement1, webElement2)).when(searchContext).findElements(any(By.class));

            TestFragment mockElement1 = mock(TestFragment.class);
            TestFragment mockElement2 = mock(TestFragment.class);
            doReturn(mockElement1).when(factory).pageFragment(TestFragment.class, webElement1);
            doReturn(mockElement2).when(factory).pageFragment(TestFragment.class, webElement2);

            Stream<TestFragment> elements = cut.manyBy(".someClass");
            assertThat(elements).containsExactly(mockElement1, mockElement2);

        }

        @Test
        void stringIsInterpretedAsCssSelector() {

            cut.manyBy(".someClass");

            verify(searchContext).findElements(byCaptor.capture());

            By by = byCaptor.getValue();
            assertThat(by).isInstanceOf(By.ByCssSelector.class);
            assertThat(by).hasToString("By.cssSelector: .someClass");

        }

        @Test
        void noSuchElementExceptionsArePropagated() {
            assertThrows(NoSuchElementException.class, () -> {
                doThrow(NoSuchElementException.class).when(searchContext).findElements(any(By.class));
                cut.manyBy(".someClass");
            });
        }

    }

    @Nested
    class ManyByForBy {

        @Test
        void fragmentIsCreatedForGivenBy() {

            WebElement webElement1 = mock(WebElement.class);
            WebElement webElement2 = mock(WebElement.class);
            doReturn(asList(webElement1, webElement2)).when(searchContext).findElements(any(By.class));

            TestFragment mockElement1 = mock(TestFragment.class);
            TestFragment mockElement2 = mock(TestFragment.class);
            doReturn(mockElement1).when(factory).pageFragment(TestFragment.class, webElement1);
            doReturn(mockElement2).when(factory).pageFragment(TestFragment.class, webElement2);

            Stream<TestFragment> elements = cut.manyBy(ByProducers.className("someClass"));
            assertThat(elements).containsExactly(mockElement1, mockElement2);

        }

        @Test
        void stringIsInterpretedAsCssSelector() {

            cut.manyBy(ByProducers.className("someClass"));

            verify(searchContext).findElements(byCaptor.capture());

            By by = byCaptor.getValue();
            assertThat(by).isInstanceOf(By.ByClassName.class);
            assertThat(by).hasToString("By.className: someClass");

        }

        @Test
        void noSuchElementExceptionsArePropagated() {
            assertThrows(NoSuchElementException.class, () -> {
                doThrow(NoSuchElementException.class).when(searchContext).findElements(any(By.class));
                cut.manyBy(ByProducers.className("someClass"));
            });
        }

    }

    public interface TestFragment extends PageFragment {
    }

}
