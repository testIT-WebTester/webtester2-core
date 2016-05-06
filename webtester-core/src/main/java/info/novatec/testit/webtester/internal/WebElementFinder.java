package info.novatec.testit.webtester.internal;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.UncheckedExecutionException;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public final class WebElementFinder {

    private static final LoadingCache<SearchContextWithBy, WebElement> WEB_ELEMENT_CACHE = CacheBuilder.newBuilder()
        .expireAfterWrite(5, TimeUnit.SECONDS)
        .build(new CacheLoader<SearchContextWithBy, WebElement>() {

            @Override
            public WebElement load(SearchContextWithBy key) {
                return find(key.context, key.getBy());
            }

        });

    private static final LoadingCache<SearchContextWithBy, List<WebElement>> WEB_ELEMENTS_CACHE = CacheBuilder.newBuilder()
        .expireAfterWrite(5, TimeUnit.SECONDS)
        .build(new CacheLoader<SearchContextWithBy, List<WebElement>>() {

            @Override
            public List<WebElement> load(SearchContextWithBy key) {
                return findMany(key.context, key.getBy());
            }

        });

    public static WebElement find(SearchContext context, By by) {
        return context.findElement(by);
    }

    public static WebElement findCached(SearchContext context, By by) {
        try {
            return WEB_ELEMENT_CACHE.get(new SearchContextWithBy(context, by));
        } catch (ExecutionException e) {
            throw new UndeclaredThrowableException(e);
        } catch (UncheckedExecutionException e) {
            throw ( RuntimeException ) e.getCause();
        }
    }

    public static List<WebElement> findMany(SearchContext context, By by) {
        return context.findElements(by);
    }

    public static List<WebElement> findManyCached(SearchContext context, By by) {
        try {
            return WEB_ELEMENTS_CACHE.get(new SearchContextWithBy(context, by));
        } catch (ExecutionException e) {
            throw new UndeclaredThrowableException(e);
        } catch (UncheckedExecutionException e) {
            throw ( RuntimeException ) e.getCause();
        }
    }

    public static void clearCache() {
        WEB_ELEMENT_CACHE.invalidateAll();
        WEB_ELEMENTS_CACHE.invalidateAll();
        log.debug("cleared web element cache(s)");
    }

    private WebElementFinder() {
        // utility class constructor
    }

    @Data
    @RequiredArgsConstructor
    private static class SearchContextWithBy {
        @NonNull
        private SearchContext context;
        @NonNull
        private By by;
    }

}
