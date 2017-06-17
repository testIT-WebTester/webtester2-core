package info.novatec.testit.webtester.internal.implementation.pagefragments;

import java.util.Optional;
import java.util.function.Supplier;

import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.internal.mapping.MappingValidator;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pages.Page;


public class BasePageFragment implements PageFragment {

    private final Browser browser;
    private final Supplier<WebElement> webElementSupplier;
    private final MappingValidator validator;
    private final String name;

    public BasePageFragment(Browser browser, Supplier<WebElement> webElementSupplier, MappingValidator validator,
        String name) {
        this.browser = browser;
        this.webElementSupplier = webElementSupplier;
        this.validator = validator;
        this.name = name;
    }

    @Override
    public Browser browser() {
        return browser;
    }

    @Override
    public <T extends Page> T create(Class<T> pageClass) {
        return browser.create(pageClass);
    }

    @Override
    public WebElement webElement() {
        WebElement webElement = webElementSupplier.get();
        return validator.assertValidity(webElement);
    }

    @Override
    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

}
