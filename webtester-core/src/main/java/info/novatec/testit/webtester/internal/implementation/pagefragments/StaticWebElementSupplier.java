package info.novatec.testit.webtester.internal.implementation.pagefragments;

import java.util.function.Supplier;

import org.openqa.selenium.WebElement;

import lombok.EqualsAndHashCode;


@EqualsAndHashCode
public class StaticWebElementSupplier implements Supplier<WebElement> {

    private final WebElement webElement;

    public StaticWebElementSupplier(WebElement webElement) {
        this.webElement = webElement;
    }

    @Override
    public WebElement get() {
        return webElement;
    }

}
