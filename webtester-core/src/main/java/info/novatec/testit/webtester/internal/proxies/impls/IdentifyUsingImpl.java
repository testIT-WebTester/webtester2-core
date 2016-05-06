package info.novatec.testit.webtester.internal.proxies.impls;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Supplier;

import org.openqa.selenium.SearchContext;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.internal.PageFragmentFactory;
import info.novatec.testit.webtester.internal.proxies.PageFragmentModel;
import info.novatec.testit.webtester.internal.proxies.PageFragmentModel.PageFragmentModelBuilder;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.annotations.Cached;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.annotations.Named;
import info.novatec.testit.webtester.pagefragments.identification.ByProducers;
import info.novatec.testit.webtester.pagefragments.annotations.Wait;


@Slf4j
public class IdentifyUsingImpl implements Implementation {

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

        Configuration configuration = browser.configuration();

        PageFragmentModelBuilder modelBuilder = PageFragmentModel.builder()
            .browser(browser)
            .type(getReturnType(method))
            .by(ByProducers.createBy(method.getAnnotation(IdentifyUsing.class)))
            .searchContextSupplier(searchContextSupplier)
            .name(getName(method))
            .useCache(configuration.isCachingEnabled());

        if (method.isAnnotationPresent(Cached.class)) {
            Cached annotation = method.getAnnotation(Cached.class);
            modelBuilder.useCache(annotation.value());
        }

        PageFragment pageFragment = factory.pageFragment(modelBuilder.build());
        if (method.isAnnotationPresent(Wait.class)) {
            method.getAnnotation(Wait.class).value().waitWith(pageFragment);
        }
        return pageFragment;

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
