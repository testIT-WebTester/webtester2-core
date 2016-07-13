package info.novatec.testit.webtester.junit5.extensions.browsers;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.extension.ExtensionContext;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.BrowserFactory;
import info.novatec.testit.webtester.browser.proxy.ProxyConfiguration;
import info.novatec.testit.webtester.junit5.extensions.BaseExtension;


@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractManagedBrowserExtension extends BaseExtension {

    protected final Predicate<Field> isStaticField = (field) -> Modifier.isStatic(field.getModifiers());
    protected final Predicate<Field> isInstanceField = isStaticField.negate();

    protected Browser createBrowserFor(Field field, Class<?> testClass) {

        Class<? extends ProxyConfiguration> proxyConfigurationClass;
        Class<? extends BrowserFactory> factoryClass;

        if (field.isAnnotationPresent(CreateUsing.class)) {
            CreateUsing annotation = field.getAnnotation(CreateUsing.class);
            proxyConfigurationClass = annotation.proxy();
            factoryClass = annotation.value();
        } else if (testClass.isAnnotationPresent(CreateBrowsersUsing.class)) {
            CreateBrowsersUsing annotation = testClass.getAnnotation(CreateBrowsersUsing.class);
            proxyConfigurationClass = annotation.proxy();
            factoryClass = annotation.value();
        } else {
            throw new IllegalStateException(
                "No browser factory configured! Either annotate class with " + CreateBrowsersUsing.class
                    + " or browser field with " + CreateUsing.class);
        }

        try {
            ProxyConfiguration proxyConfiguration = proxyConfigurationClass.newInstance();
            return factoryClass.newInstance().withProxyConfiguration(proxyConfiguration).createBrowser();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new UndeclaredThrowableException(e, "error while creating browser factory");
        }

    }

    protected void closeAllManagedBrowsers(ExtensionContext context) {
        getManagedBrowsers(context).forEach(this::closeAndLogErrors);
    }

    protected void closeAndLogErrors(Browser browser) {
        try {
            log.debug("closing managed browser: {}", browser);
            browser.close();
        } catch (RuntimeException e) {
            log.warn("error while closing managed browser instance:", e);
        }
    }

    @SuppressWarnings("unchecked")
    protected List<Browser> getManagedBrowsers(ExtensionContext context) {
        return ( List<Browser> ) context.getStore(BaseExtension.NAMESPACE)
            .getOrComputeIfAbsent(getUniqueStorageKey(), s -> new LinkedList<Browser>());
    }

    protected abstract String getUniqueStorageKey();

}
