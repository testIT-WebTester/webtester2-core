package info.novatec.testit.webtester.internal.implementation.pagefragments;

import java.util.function.Supplier;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;


public class DynamicWebElementSupplier implements Supplier<WebElement> {

    private final Supplier<SearchContext> searchContextSupplier;
    private final By by;

    public DynamicWebElementSupplier(Supplier<SearchContext> searchContextSupplier, By by) {
        this.searchContextSupplier = searchContextSupplier;
        this.by = by;
    }

    @Override
    public WebElement get() {
        return searchContextSupplier.get().findElement(by);
    }

}
