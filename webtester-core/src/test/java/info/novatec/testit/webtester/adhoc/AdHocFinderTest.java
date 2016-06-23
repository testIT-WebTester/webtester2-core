package info.novatec.testit.webtester.adhoc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.internal.PageFragmentFactory;
import info.novatec.testit.webtester.pagefragments.GenericElement;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.identification.ByProducers;


@RunWith(Enclosed.class)
public class AdHocFinderTest {

    @RunWith(MockitoJUnitRunner.class)
    public static abstract class AbstractAdHocFinderTest {

        @Mock
        PageFragmentFactory factory;
        @Mock
        SearchContext searchContext;

        @InjectMocks
        AdHocFinder cut;

    }

    public static class FindString extends AbstractAdHocFinderTest {

        @Test
        public void byFinderIsCreatedCorrectly() {

            ByFinder finder = cut.find("#someId");
            assertThat(finder.getFactory()).isSameAs(factory);
            assertThat(finder.getSearchContext()).isSameAs(searchContext);

            By by = finder.getBy();
            assertThat(by).isInstanceOf(By.ByCssSelector.class);
            assertThat(by).hasToString("By.cssSelector: #someId");

        }

    }

    public static class FindBy extends AbstractAdHocFinderTest {

        @Test
        public void byFinderIsCreatedCorrectly() {

            ByFinder finder = cut.findBy(ByProducers.id("someId"));
            assertThat(finder.getFactory()).isSameAs(factory);
            assertThat(finder.getSearchContext()).isSameAs(searchContext);

            By by = finder.getBy();
            assertThat(by).isInstanceOf(By.ById.class);
            assertThat(by).hasToString("By.id: someId");

        }

    }

    public static class FindGeneric extends AbstractAdHocFinderTest {

        @Test
        public void typeFinderIsCreatedCorrectly() {

            TypeFinder<GenericElement> finder = cut.findGeneric();
            assertThat(finder.getFactory()).isSameAs(factory);
            assertThat(finder.getSearchContext()).isSameAs(searchContext);
            assertThat(finder.getFragmentClass()).isEqualTo(GenericElement.class);

        }

    }

    public static class FindClass extends AbstractAdHocFinderTest {

        @Test
        public void typeFinderIsCreatedCorrectly() {

            TypeFinder<TestFragment> finder = cut.find(TestFragment.class);
            assertThat(finder.getFactory()).isSameAs(factory);
            assertThat(finder.getSearchContext()).isSameAs(searchContext);
            assertThat(finder.getFragmentClass()).isEqualTo(TestFragment.class);

        }

        public interface TestFragment extends PageFragment {
        }

    }

    public static class Construction {

        @Test
        public void creatingForBrowserUsesWebDriverAsSearchContext() {

            Browser browser = mock(Browser.class);
            WebDriver webDriver = mock(WebDriver.class);
            doReturn(webDriver).when(browser).webDriver();

            AdHocFinder cut = new AdHocFinder(browser);

            assertThat(cut.getSearchContext()).isSameAs(webDriver);
            assertThat(cut.getFactory()).isNotNull();

        }

        @Test
        public void creatingForPageFragmentUsesWebElementAsSearchContext() {

            PageFragment pageFragment = mock(PageFragment.class);
            WebElement webElement = mock(WebElement.class);
            doReturn(webElement).when(pageFragment).webElement();

            AdHocFinder cut = new AdHocFinder(pageFragment);

            assertThat(cut.getSearchContext()).isSameAs(webElement);
            assertThat(cut.getFactory()).isNotNull();

        }

    }

}
