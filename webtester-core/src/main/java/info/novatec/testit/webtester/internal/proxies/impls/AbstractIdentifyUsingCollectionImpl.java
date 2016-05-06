package info.novatec.testit.webtester.internal.proxies.impls;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.internal.PageFragmentFactory;
import info.novatec.testit.webtester.internal.WebElementFinder;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.annotations.Cached;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.identification.ByProducers;


public abstract class AbstractIdentifyUsingCollectionImpl implements Implementation {

    private final Browser browser;
    private final Supplier<SearchContext> searchContextSupplier;
    private final PageFragmentFactory factory;

    protected AbstractIdentifyUsingCollectionImpl(Browser browser, Supplier<SearchContext> searchContextSupplier) {
        this.browser = browser;
        this.searchContextSupplier = searchContextSupplier;
        this.factory = new PageFragmentFactory(browser);
    }

    protected Stream<? extends PageFragment> getStreamOfPageFragmentsFor(Method method) {
        IdentifyUsing identifyUsing = method.getAnnotation(IdentifyUsing.class);
        Class<PageFragment> type = getGenericType(method);
        By by = ByProducers.createBy(identifyUsing);

        boolean caching = browser.configuration().isCachingEnabled();
        if (method.isAnnotationPresent(Cached.class)) {
            caching = method.getAnnotation(Cached.class).value();
        }

        SearchContext searchContext = searchContextSupplier.get();

        List<WebElement> webElements;
        if (caching) {
            webElements = WebElementFinder.findManyCached(searchContext, by);
        } else {
            webElements = WebElementFinder.findMany(searchContext, by);
        }

        return webElements.stream().map(webElement -> factory.pageFragment(type, webElement));
    }

    @SuppressWarnings("unchecked")
    private static <T extends PageFragment> Class<T> getGenericType(Method method) {
        ParameterizedType genericType = ( ParameterizedType ) method.getGenericReturnType();
        return ( Class<T> ) genericType.getActualTypeArguments()[0];
    }

}
