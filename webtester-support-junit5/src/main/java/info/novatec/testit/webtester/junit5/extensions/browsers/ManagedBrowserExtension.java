package info.novatec.testit.webtester.junit5.extensions.browsers;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExtensionContext;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.BrowserFactory;
import info.novatec.testit.webtester.browser.proxy.ProxyConfiguration;
import info.novatec.testit.webtester.junit5.exceptions.NoBrowserFactoryException;
import info.novatec.testit.webtester.junit5.extensions.BaseExtension;


@Slf4j
@SuppressWarnings({ "PMD.SignatureDeclareThrowsException", "PMD.AvoidCatchingGenericException" })
public class ManagedBrowserExtension extends BaseExtension
    implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, AfterEachCallback {

    private final Predicate<Field> isStaticField = (field) -> Modifier.isStatic(field.getModifiers());
    private final Predicate<Field> isInstanceField = isStaticField.negate();

    public ManagedBrowserExtension() {
    }

    @Override
    public void beforeAll(ContainerExtensionContext context) throws Exception {
        executeHandlingUndeclaredThrowables(context, this::initializeAndInjectStaticBrowsers);
    }

    @Override
    public void beforeEach(TestExtensionContext context) throws Exception {
        executeHandlingUndeclaredThrowables(context, this::initializeAndInjectInstanceBrowsers);
    }

    @Override
    public void afterEach(TestExtensionContext context) {
        getManagedInstanceBrowsers(context).forEach(this::closeAndLogErrors);
    }

    @Override
    public void afterAll(ContainerExtensionContext context) {
        getManagedStaticBrowsers(context).forEach(this::closeAndLogErrors);
    }

    private void closeAndLogErrors(Browser browser) {
        try {
            log.debug("closing managed browser: {}", browser);
            browser.close();
        } catch (RuntimeException e) {
            log.warn("error while closing managed browser instance:", e);
        }
    }

    private void initializeAndInjectStaticBrowsers(ContainerExtensionContext context) {
        Class<?> testClass = context.getTestClass().orElseThrow(() -> new IllegalStateException("no test class"));
        List<Browser> managedBrowsers = getManagedStaticBrowsers(context);
        getModel(context).getBrowserFields().stream().filter(isStaticField).forEach(field -> {
            Browser browser = createBrowserFor(field, testClass);
            managedBrowsers.add(browser);
            setValue(field, null, browser);
        });
    }

    private void initializeAndInjectInstanceBrowsers(TestExtensionContext context) {
        Object testInstance = context.getTestInstance();
        List<Browser> managedBrowsers = getManagedInstanceBrowsers(context);
        getModel(context).getBrowserFields().stream().filter(isInstanceField).forEach(field -> {
            Browser browser = createBrowserFor(field, testInstance.getClass());
            managedBrowsers.add(browser);
            setValue(field, testInstance, browser);
        });
    }

    private Browser createBrowserFor(Field field, Class<?> testClass) {

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
            throw new NoBrowserFactoryException();
        }

        try {
            ProxyConfiguration proxyConfiguration = proxyConfigurationClass.newInstance();
            return factoryClass.newInstance().withProxyConfiguration(proxyConfiguration).createBrowser();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new UndeclaredThrowableException(e, "error while creating browser factory");
        }

    }

    @SuppressWarnings("unchecked")
    private List<Browser> getManagedInstanceBrowsers(ExtensionContext context) {
        return ( List<Browser> ) context.getStore(BaseExtension.NAMESPACE)
            .getOrComputeIfAbsent("managed-instance-browsers", s -> new LinkedList<Browser>());
    }

    @SuppressWarnings("unchecked")
    private List<Browser> getManagedStaticBrowsers(ExtensionContext context) {
        return ( List<Browser> ) context.getStore(BaseExtension.NAMESPACE)
            .getOrComputeIfAbsent("managed-static-browsers", s -> new LinkedList<Browser>());
    }

}
