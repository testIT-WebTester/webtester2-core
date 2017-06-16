package info.novatec.testit.webtester.internal.proxies.impls;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.openqa.selenium.SearchContext;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;


@Slf4j
public class IdentifyUsingSetImpl extends AbstractIdentifyUsingCollectionImpl {

    public IdentifyUsingSetImpl(Browser browser, Supplier<SearchContext> searchContextSupplier) {
        super(browser, searchContextSupplier);
    }

    @Override
    public boolean isImplementationFor(Method method) {
        return isAnnotationPresent(method) && isPageFragmentSetReturning(method);
    }

    private boolean isAnnotationPresent(Method method) {
        return method.isAnnotationPresent(IdentifyUsing.class);
    }

    private boolean isPageFragmentSetReturning(Method method) {
        return Set.class.isAssignableFrom(method.getReturnType());
    }

    @Override
    public Set<? extends PageFragment> invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.debug("creating new set of proxies for '{}'", method);
        return new HashSet<>(getStreamOfPageFragmentsFor(method));
    }

}
