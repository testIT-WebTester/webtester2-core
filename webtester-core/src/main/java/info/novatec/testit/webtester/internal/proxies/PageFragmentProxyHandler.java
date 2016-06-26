package info.novatec.testit.webtester.internal.proxies;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.internal.proxies.arounds.EventProducingImplementationDecorator;
import info.novatec.testit.webtester.internal.proxies.befores.ActionOperation;
import info.novatec.testit.webtester.internal.proxies.befores.BeforeOperation;
import info.novatec.testit.webtester.internal.proxies.befores.MarkOperation;
import info.novatec.testit.webtester.internal.proxies.impls.AttributeImpl;
import info.novatec.testit.webtester.internal.proxies.impls.BrowserReturningImpl;
import info.novatec.testit.webtester.internal.proxies.impls.DefaultMethodImpl;
import info.novatec.testit.webtester.internal.proxies.impls.HashCodeImpl;
import info.novatec.testit.webtester.internal.proxies.impls.IdentifyUsingImpl;
import info.novatec.testit.webtester.internal.proxies.impls.IdentifyUsingListImpl;
import info.novatec.testit.webtester.internal.proxies.impls.IdentifyUsingSetImpl;
import info.novatec.testit.webtester.internal.proxies.impls.IdentifyUsingStreamImpl;
import info.novatec.testit.webtester.internal.proxies.impls.Implementation;
import info.novatec.testit.webtester.internal.proxies.impls.NameReturningImpl;
import info.novatec.testit.webtester.internal.proxies.impls.PageCreatingImpl;
import info.novatec.testit.webtester.internal.proxies.impls.ToStringImpl;
import info.novatec.testit.webtester.internal.proxies.impls.WebElementReturningImpl;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.mapping.MappingValidator;
import info.novatec.testit.webtester.pagefragments.mapping.MappingValidatorImpl;


@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PageFragmentProxyHandler implements InvocationHandler {

    private final Browser browser;
    private final Class<? extends PageFragment> pageFragmentClass;
    private final MappingValidator validator;
    private final Optional<String> name;

    private final Supplier<WebElement> webElementSupplier;
    private final Supplier<SearchContext> searchContextSupplier;

    private final List<BeforeOperation> beforeOperations = new LinkedList<>();
    private final List<Implementation> implementations = new LinkedList<>();
    private final EventProducingImplementationDecorator eventDecorator;

    private PageFragmentProxyHandler addBeforeOperations() {
        beforeOperations.add(new ActionOperation(browser.configuration()));
        beforeOperations.add(new MarkOperation());
        return this;
    }

    private PageFragmentProxyHandler addImplementations() {
        implementations.add(new DefaultMethodImpl());
        implementations.add(new ToStringImpl(pageFragmentClass));
        implementations.add(new HashCodeImpl());
        implementations.add(new WebElementReturningImpl(webElementSupplier, validator));
        implementations.add(new BrowserReturningImpl(browser));
        implementations.add(new NameReturningImpl(name));
        implementations.add(new PageCreatingImpl(browser));
        implementations.add(new AttributeImpl(webElementSupplier));
        implementations.add(new IdentifyUsingImpl(browser, searchContextSupplier));
        implementations.add(new IdentifyUsingListImpl(browser, searchContextSupplier));
        implementations.add(new IdentifyUsingSetImpl(browser, searchContextSupplier));
        implementations.add(new IdentifyUsingStreamImpl(browser, searchContextSupplier));
        return this;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        executeBeforeOperations(proxy, method, args);
        Implementation implementation = findImplementation(method);
        if (eventDecorator.shouldBeInvoked(method)) {
            return eventDecorator.invoke(proxy, method, args, implementation);
        }
        return implementation.invoke(proxy, method, args);
    }

    private void executeBeforeOperations(Object proxy, Method method, Object[] args) {
        beforeOperations.stream()
            .filter(operation -> operation.shouldBeInvokedFor(method))
            .forEach(operation -> operation.invoke(proxy, method, args));
    }

    private Implementation findImplementation(Method method) {
        return implementations.stream()
            .filter(operation -> operation.isImplementationFor(method))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("No implementation found for: " + method));
    }

    public static PageFragmentProxyHandler fromModel(PageFragmentModel model) {

        Browser browser = model.getBrowser();
        Class<? extends PageFragment> pageFragmentClass = model.getType();
        MappingValidator validator = new MappingValidatorImpl(pageFragmentClass);
        Supplier<WebElement> webElementSupplier = () -> {
            SearchContext searchContext = model.getSearchContextSupplier().get();
            By by = model.getBy();
            return searchContext.findElement(by);
        };
        EventProducingImplementationDecorator eventDecorator =
            new EventProducingImplementationDecorator(browser, webElementSupplier);

        return PageFragmentProxyHandler.builder()
            .browser(browser)
            .pageFragmentClass(pageFragmentClass)
            .validator(validator)
            .webElementSupplier(webElementSupplier)
            .searchContextSupplier(webElementSupplier::get)
            .name(Optional.ofNullable(model.getName()))
            .eventDecorator(eventDecorator)
            .build()
            .addBeforeOperations()
            .addImplementations();

    }

    public static PageFragmentProxyHandler forWebElement(Browser browser, WebElement webElement,
        Class<? extends PageFragment> pageFragmentClass) {

        MappingValidator validator = new MappingValidatorImpl(pageFragmentClass);
        Supplier<WebElement> webElementSupplier = () -> webElement;
        EventProducingImplementationDecorator eventDecorator =
            new EventProducingImplementationDecorator(browser, webElementSupplier);

        return PageFragmentProxyHandler.builder()
            .browser(browser)
            .pageFragmentClass(pageFragmentClass)
            .validator(validator)
            .webElementSupplier(webElementSupplier)
            .searchContextSupplier(webElementSupplier::get)
            .name(Optional.empty())
            .eventDecorator(eventDecorator)
            .build()
            .addBeforeOperations()
            .addImplementations();

    }

}
