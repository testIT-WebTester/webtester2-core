package info.novatec.testit.webtester.internal.proxies;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.openqa.selenium.SearchContext;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.internal.proxies.befores.ActionOperation;
import info.novatec.testit.webtester.internal.proxies.befores.BeforeOperation;
import info.novatec.testit.webtester.internal.proxies.impls.BrowserReturningImpl;
import info.novatec.testit.webtester.internal.proxies.impls.DefaultMethodImpl;
import info.novatec.testit.webtester.internal.proxies.impls.HashCodeImpl;
import info.novatec.testit.webtester.internal.proxies.impls.IdentifyUsingImpl;
import info.novatec.testit.webtester.internal.proxies.impls.IdentifyUsingListImpl;
import info.novatec.testit.webtester.internal.proxies.impls.IdentifyUsingSetImpl;
import info.novatec.testit.webtester.internal.proxies.impls.IdentifyUsingStreamImpl;
import info.novatec.testit.webtester.internal.proxies.impls.Implementation;
import info.novatec.testit.webtester.internal.proxies.impls.KotlinDefaultMethodImpl;
import info.novatec.testit.webtester.internal.proxies.impls.PageCreatingImpl;
import info.novatec.testit.webtester.internal.proxies.impls.ToStringImpl;
import info.novatec.testit.webtester.pages.Page;


public class PageProxyHandler implements InvocationHandler {

    private final Browser browser;

    private final List<BeforeOperation> beforeOperations = new ArrayList<>();
    private final List<Implementation> implementations = new ArrayList<>();
    private final Supplier<SearchContext> searchContextSupplier;

    public PageProxyHandler(Browser browser, Class<? extends Page> pageClass) {
        this.browser = browser;
        this.searchContextSupplier = this.browser::webDriver;
        addBeforeOperations();
        addImplementations(pageClass);
    }

    private void addBeforeOperations() {
        beforeOperations.add(new ActionOperation(browser.configuration()));
    }

    private void addImplementations(Class<? extends Page> pageClass) {
        implementations.add(new DefaultMethodImpl());
        implementations.add(new ToStringImpl(pageClass));
        implementations.add(new HashCodeImpl());
        implementations.add(new BrowserReturningImpl(browser));
        implementations.add(new PageCreatingImpl(browser));
        implementations.add(new IdentifyUsingImpl(browser, searchContextSupplier));
        implementations.add(new IdentifyUsingListImpl(browser, searchContextSupplier));
        implementations.add(new IdentifyUsingSetImpl(browser, searchContextSupplier));
        implementations.add(new IdentifyUsingStreamImpl(browser, searchContextSupplier));
        if (isKotlinPage(pageClass)) {
            implementations.add(new KotlinDefaultMethodImpl());
        }
    }

    private boolean isKotlinPage(Class<? extends Page> pageClass) {
        try {
            Class<?> aClass = Class.forName("info.novatec.testit.webtester.kotlin.pages.Page");
            return aClass.isAssignableFrom(pageClass);
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        executeBeforeOperations(proxy, method, args);
        return findImplementationAndExecute(proxy, method, args);
    }

    private void executeBeforeOperations(Object proxy, Method method, Object[] args) {
        beforeOperations.stream()
            .filter(before -> before.shouldBeInvokedFor(method))
            .forEach(operation -> operation.invoke(proxy, method, args));
    }

    private Object findImplementationAndExecute(Object proxy, Method method, Object[] args) throws Throwable {
        Implementation implementation = implementations.stream()
            .filter(impl -> impl.isImplementationFor(method))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("No implementation found for: " + method));
        return implementation.invoke(proxy, method, args);
    }

}
