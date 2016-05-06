package info.novatec.testit.webtester.internal.proxies.impls;

import java.lang.reflect.Method;
import java.util.function.Supplier;

import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.internal.annotations.ReturnsWebElement;
import info.novatec.testit.webtester.pagefragments.mapping.MappingValidator;


public class WebElementReturningImpl implements Implementation {

    private final Supplier<WebElement> webElementSupplier;
    private final MappingValidator validator;

    public WebElementReturningImpl(Supplier<WebElement> webElementSupplier, MappingValidator validator) {
        this.webElementSupplier = webElementSupplier;
        this.validator = validator;
    }

    @Override
    public boolean isImplementationFor(Method method) {
        return method.isAnnotationPresent(ReturnsWebElement.class);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        return validator.assertValidity(webElementSupplier.get());
    }

}
