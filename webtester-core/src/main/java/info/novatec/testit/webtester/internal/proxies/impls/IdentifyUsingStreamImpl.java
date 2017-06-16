package info.novatec.testit.webtester.internal.proxies.impls;

import java.lang.reflect.Method;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.openqa.selenium.SearchContext;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;


@Slf4j
public class IdentifyUsingStreamImpl extends AbstractIdentifyUsingCollectionImpl {

    public IdentifyUsingStreamImpl(Browser browser, Supplier<SearchContext> searchContextSupplier) {
        super(browser, searchContextSupplier);
    }

    @Override
    public boolean isImplementationFor(Method method) {
        return isAnnotationPresent(method) && isPageFragmentStreamReturning(method);
    }

    private boolean isAnnotationPresent(Method method) {
        return method.isAnnotationPresent(IdentifyUsing.class);
    }

    private boolean isPageFragmentStreamReturning(Method method) {
        return Stream.class.isAssignableFrom(method.getReturnType());
    }

    @Override
    public Stream<? extends PageFragment> invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.debug("creating new stream of proxies for '{}'", method);
        return getStreamOfPageFragmentsFor(method).stream();
    }

}
