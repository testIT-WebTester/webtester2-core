package info.novatec.testit.webtester.internal.implementation.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;


class DynamicWebElementSupplierTest {

    SearchContext searchContext = mock(SearchContext.class);
    Supplier<SearchContext> searchContextSupplier = () -> searchContext;
    By by = mock(By.class);
    DynamicWebElementSupplier cut = new DynamicWebElementSupplier(searchContextSupplier, by);

    @Test
    void searchContextAndByAreUsedToFindWebElement() {
        WebElement stubbedWebElement = mock(WebElement.class);
        given(searchContext.findElement(by)).willReturn(stubbedWebElement);
        WebElement webElement = cut.get();
        assertThat(webElement).isSameAs(stubbedWebElement);
    }

}