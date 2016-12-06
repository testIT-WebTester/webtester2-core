package info.novatec.testit.webtester.junit5.extensions.browsers;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExtensionContext;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.BrowserFactory;
import info.novatec.testit.webtester.browser.proxy.ProxyConfiguration;
import info.novatec.testit.webtester.junit5.extensions.BaseExtension;


/**
 * This {@link Extension} is used to manage and inject {@link Browser} instances into static and non static fields of a test
 * class. This injection is executed according to the field's modifier.
 * <ul>
 * <li>Static browser fields are initialized before the first {@link BeforeAll} method is executed and the browser will be
 * automatically closed after the last {@link AfterAll} method was executed.</li>
 * <li>Instance browser fields are initialized before the first {@link BeforeEach} method is executed and the browser will
 * be automatically closed after the last {@link AfterEach} method was executed.</li>
 * </ul>
 * In order to decide how the browser instances are created either the test class must be annotated with {@link
 * CreateBrowsersUsing} or the field with {@link CreateUsing}.
 * <p>
 * In case more than one browser is managed, each has to have a unique name which can be defined by setting the {@link
 * Managed#value()} property.
 * <p>
 * <b>Example:</b>
 * <pre>
 * &#64;EnableWebTesterExtensions
 * &#64;CreateBrowsersUsing(DefaultFactory.class)
 * public class FooTest {
 *
 *     &#64;Managed
 *     static Browser browser;
 *
 * }
 *
 * &#64;EnableWebTesterExtensions
 * &#64;CreateBrowsersUsing(DefaultFactory.class)
 * public class BarTest {
 *
 *     &#64;Managed("browser-1")
 *     static Browser browser1;
 *     &#64;Managed("browser-2")
 *     &#64;CreateUsing(SpecialFactory.class)
 *     Browser browser2;
 *
 * }
 * </pre>
 *
 * @see Managed
 * @see Browser
 * @see CreateBrowsersUsing
 * @see CreateUsing
 * @since 2.1
 */
@Slf4j
@SuppressWarnings({ "PMD.SignatureDeclareThrowsException", "PMD.AvoidCatchingGenericException" })
public class ManagedBrowserExtension extends BaseExtension
    implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, AfterEachCallback {

    private final Predicate<Field> isStaticField = (field) -> Modifier.isStatic(field.getModifiers());
    private final Predicate<Field> isInstanceField = isStaticField.negate();

    @Override
    public void beforeAll(ContainerExtensionContext context) throws Exception {
        if (isRootContext(context)) {
            executeHandlingUndeclaredThrowables(context, this::initializeAndInjectStaticBrowsers);
        }
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
        if (isRootContext(context)) {
            getManagedStaticBrowsers(context).forEach(this::closeAndLogErrors);
        }
    }

    private boolean isRootContext(ContainerExtensionContext context) {
        return !context.getParent().isPresent();
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
        List<Browser> managedBrowsers = getManagedStaticBrowsers(context);
        getModel(context).getBrowserFields().stream().filter(isStaticField).forEach(field -> {
            Browser browser = createBrowserFor(field);
            managedBrowsers.add(browser);
            setValue(field, null, browser);
        });
    }

    private void initializeAndInjectInstanceBrowsers(TestExtensionContext context) {
        Object testInstance = context.getTestInstance();
        List<Browser> managedBrowsers = getManagedInstanceBrowsers(context);
        getModel(context).getBrowserFields().stream().filter(isInstanceField).forEach(field -> {
            Browser browser = createBrowserFor(field);
            managedBrowsers.add(browser);
            setValue(field, testInstance, browser);
        });
    }

    private Browser createBrowserFor(Field field) {

        CreateUsing createUsing = getFieldLevelAnnotation(field);
        if (createUsing != null) {
            Class<? extends ProxyConfiguration> proxyConfigurationClass = createUsing.proxy();
            Class<? extends BrowserFactory> factoryClass = createUsing.value();
            return createBrowser(factoryClass, proxyConfigurationClass);
        }

        CreateBrowsersUsing createBrowsersUsing = getClassLevelAnnotation(field);
        if (createBrowsersUsing != null) {
            Class<? extends ProxyConfiguration> proxyConfigurationClass = createBrowsersUsing.proxy();
            Class<? extends BrowserFactory> factoryClass = createBrowsersUsing.value();
            return createBrowser(factoryClass, proxyConfigurationClass);
        }

        throw new NoBrowserFactoryException();

    }

    private CreateUsing getFieldLevelAnnotation(Field field) {
        return field.getAnnotation(CreateUsing.class);
    }

    private CreateBrowsersUsing getClassLevelAnnotation(Field field) {
        Class<?> testClass = field.getDeclaringClass();
        CreateBrowsersUsing isPresent = testClass.getAnnotation(CreateBrowsersUsing.class);
        if (isPresent == null && testClass.isAnnotationPresent(Nested.class)) {
            Class<?> declaringClass = testClass.getDeclaringClass();
            while (declaringClass != null && isPresent == null) {
                isPresent = declaringClass.getAnnotation(CreateBrowsersUsing.class);
                declaringClass = declaringClass.getDeclaringClass();
            }
        }
        return isPresent;
    }

    private Browser createBrowser(Class<? extends BrowserFactory> factoryClass,
        Class<? extends ProxyConfiguration> proxyConfigurationClass) {
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
            .getOrComputeIfAbsent("managed-instance-browsers", s -> new ArrayList<Browser>());
    }

    @SuppressWarnings("unchecked")
    private List<Browser> getManagedStaticBrowsers(ExtensionContext context) {
        return ( List<Browser> ) context.getStore(BaseExtension.NAMESPACE)
            .getOrComputeIfAbsent("managed-static-browsers", s -> new ArrayList<Browser>());
    }

}
