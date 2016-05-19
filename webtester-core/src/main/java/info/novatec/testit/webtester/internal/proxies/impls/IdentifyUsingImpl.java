package info.novatec.testit.webtester.internal.proxies.impls;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.openqa.selenium.SearchContext;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.internal.PageFragmentFactory;
import info.novatec.testit.webtester.internal.exceptions.IllegalSignatureException;
import info.novatec.testit.webtester.internal.proxies.PageFragmentModel;
import info.novatec.testit.webtester.internal.proxies.PageFragmentModel.PageFragmentModelBuilder;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.annotations.Named;
import info.novatec.testit.webtester.pagefragments.annotations.WaitUntil;
import info.novatec.testit.webtester.pagefragments.identification.ByProducers;
import info.novatec.testit.webtester.waiting.Wait;


@Slf4j
public class IdentifyUsingImpl implements Implementation {

    public static final String COULD_NOT_CREATE_PREDICATE_INSTANCE_MSG =
        "Could not create Predicate instance! Default constructor might be missing";

    private static final String IDENTIFIED_BY_NAME = "%s identified by %s: %s";

    private final Browser browser;
    private final Supplier<SearchContext> searchContextSupplier;
    private final PageFragmentFactory factory;

    public IdentifyUsingImpl(Browser browser, Supplier<SearchContext> searchContextSupplier) {
        this.browser = browser;
        this.searchContextSupplier = searchContextSupplier;
        this.factory = new PageFragmentFactory(browser);
    }

    @Override
    public boolean isImplementationFor(Method method) {
        return isAnnotationPresent(method) && isPageFragmentReturning(method);
    }

    private boolean isAnnotationPresent(Method method) {
        return method.isAnnotationPresent(IdentifyUsing.class);
    }

    private boolean isPageFragmentReturning(Method method) {
        return PageFragment.class.isAssignableFrom(method.getReturnType());
    }

    @Override
    public PageFragment invoke(Object proxy, Method method, Object[] args) throws Throwable {

        log.debug("creating new proxy for '{}'", method);

        PageFragmentModelBuilder modelBuilder = PageFragmentModel.builder()
            .browser(browser)
            .type(getReturnType(method))
            .by(ByProducers.createBy(method.getAnnotation(IdentifyUsing.class)))
            .searchContextSupplier(searchContextSupplier)
            .name(getName(method));

        PageFragment pageFragment = factory.pageFragment(modelBuilder.build());
        waitIfAnnotationPresent(method, pageFragment);
        return pageFragment;

    }

    private void waitIfAnnotationPresent(Method method, PageFragment fragment) {
        WaitUntil waitAnnotation = method.getAnnotation(WaitUntil.class);
        if (method.isAnnotationPresent(WaitUntil.class)) {
            doWaitUntil(waitAnnotation, fragment);
        }
    }

    private void doWaitUntil(WaitUntil annotation, PageFragment fragment) {
        try {
            Predicate<? super PageFragment> condition = annotation.value().newInstance();
            if (annotation.timeout() > 0) {
                Wait.withTimeoutOf(annotation.timeout(), annotation.unit()).until(fragment).is(condition);
            } else {
                Wait.until(fragment).is(condition);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalSignatureException(COULD_NOT_CREATE_PREDICATE_INSTANCE_MSG, e);
        }
    }

    @SuppressWarnings("unchecked")
    private Class<? extends PageFragment> getReturnType(Method method) {
        return ( Class<? extends PageFragment> ) method.getReturnType();
    }

    private String getName(Method method) {
        Optional<String> nameFromAnnotation = Optional.ofNullable(method.getAnnotation(Named.class)).map(Named::value);
        if (nameFromAnnotation.isPresent()) {
            return nameFromAnnotation.get();
        }
        return getIdentifiedByNameFrom(method);
    }

    private String getIdentifiedByNameFrom(Method method) {
        IdentifyUsing annotation = method.getAnnotation(IdentifyUsing.class);
        String how = annotation.how().getSimpleName();
        String value = annotation.value();
        String pageFragmentClass = getReturnType(method).getSimpleName();
        return String.format(IDENTIFIED_BY_NAME, pageFragmentClass, how, value);
    }

}
