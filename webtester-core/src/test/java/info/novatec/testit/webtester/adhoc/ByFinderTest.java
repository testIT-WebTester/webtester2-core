package info.novatec.testit.webtester.adhoc;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.util.stream.Stream;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.internal.PageFragmentFactory;
import info.novatec.testit.webtester.pagefragments.GenericElement;
import info.novatec.testit.webtester.pagefragments.PageFragment;


@RunWith(Enclosed.class)
public class ByFinderTest {

    @RunWith(MockitoJUnitRunner.class)
    public static abstract class AbstractByFinderTest {

        @Mock
        PageFragmentFactory factory;
        @Mock
        SearchContext searchContext;
        @Mock
        By by;

        @InjectMocks
        ByFinder cut;

    }

    public static class AsGeneric extends AbstractByFinderTest {

        @Test
        public void genericElementIsCreatedFromFoundWebElement() {

            WebElement webElement = mock(WebElement.class);
            doReturn(webElement).when(searchContext).findElement(by);

            GenericElement mockElement = mock(GenericElement.class);
            doReturn(mockElement).when(factory).pageFragment(GenericElement.class, webElement);

            GenericElement element = cut.asGeneric();
            assertThat(element).isSameAs(mockElement);

        }

        @Test(expected = NoSuchElementException.class)
        public void noSuchElementExceptionsArePropagated() {
            doThrow(NoSuchElementException.class).when(searchContext).findElement(by);
            cut.asGeneric();
        }

    }

    public static class As extends AbstractByFinderTest {

        @Test
        public void givenFragmentTypeIsCreatedFromFoundWebElement() {

            WebElement webElement = mock(WebElement.class);
            doReturn(webElement).when(searchContext).findElement(by);

            TestFragment mockElement = mock(TestFragment.class);
            doReturn(mockElement).when(factory).pageFragment(TestFragment.class, webElement);

            TestFragment element = cut.as(TestFragment.class);
            assertThat(element).isSameAs(mockElement);

        }

        @Test(expected = NoSuchElementException.class)
        public void noSuchElementExceptionsArePropagated() {
            doThrow(NoSuchElementException.class).when(searchContext).findElement(by);
            cut.as(TestFragment.class);
        }

    }

    public static class AsManyGenerics extends AbstractByFinderTest {

        @Test
        public void genericElementIsCreatedFromFoundWebElements() {

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

        @Test(expected = NoSuchElementException.class)
        public void noSuchElementExceptionsArePropagated() {
            doThrow(NoSuchElementException.class).when(searchContext).findElements(by);
            cut.asManyGenerics();
        }

    }

    public static class AsMany extends AbstractByFinderTest {

        @Test
        public void givenFragmentTypeIsCreatedFromFoundWebElements() {

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

        @Test(expected = NoSuchElementException.class)
        public void noSuchElementExceptionsArePropagated() {
            doThrow(NoSuchElementException.class).when(searchContext).findElements(by);
            cut.asMany(TestFragment.class);
        }

    }

    public interface TestFragment extends PageFragment {
    }

}
