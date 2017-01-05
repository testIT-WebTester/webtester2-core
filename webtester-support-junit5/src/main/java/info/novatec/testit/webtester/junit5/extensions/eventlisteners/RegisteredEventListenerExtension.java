package info.novatec.testit.webtester.junit5.extensions.eventlisteners;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.TestExtensionContext;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.junit5.extensions.BaseExtension;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;


/**
 * <p>This {@link Extension} is used to inject and register {@link EventListener} instances into non static fields of a test
 * class. This injection is executed according to the field's modifier.
 * </p><p>Instances of event listener fields are initialized  before the first {@link BeforeEach} method is executed and
 * will be automatically registered to the target browser. The event listener will be deregistered after the last {@link
 * AfterEach} method was executed.
 * In case of more than one browser instances a explicit specification of target browsers are necessary. To do so use the
 * annotation value {@link Registered#targets()}
 * </p><b>Example:</b>
 * <pre>
 * &#64;EnableWebTesterExtensions
 * &#64;CreateBrowsersUsing(DefaultFactory.class)
 * public class FooTest {
 *
 *     &#64;Managed
 *     Browser browser;
 *
 *     &#64;Registered
 *     CustomEventListener listener;
 *
 * }
 *
 * &#64;EnableWebTesterExtensions
 * &#64;CreateBrowsersUsing(DefaultFactory.class)
 * public class BarTest {
 *
 *     &#64;Managed("browser-1")
 *     Browser browser1;
 *     &#64;Managed("browser-2")
 *     Browser browser2;
 *
 *     &#64;Registered(targets = "browser-1")
 *     CustomEventListener eventListener;
 *
 * }
 * </pre>
 *
 * @see Registered
 * @since 2.2
 */
@Slf4j
public class RegisteredEventListenerExtension extends BaseExtension implements BeforeEachCallback, AfterEachCallback {

    private final Predicate<Field> isStaticField = (field) -> Modifier.isStatic(field.getModifiers());
    private final Predicate<Field> isInstanceField = isStaticField.negate();

    @Override
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void beforeEach(TestExtensionContext context) throws Exception {
        initializeEventListener(context);
    }

    @Override
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void afterEach(TestExtensionContext context) throws Exception {
        deRegisterAllEventListeners(context);
    }

    private void initializeEventListener(TestExtensionContext context) {
        Multimap<EventListener, Browser> registeredEventListener = HashMultimap.create();
        getModel(context).getEventListenerFields().stream().filter(isInstanceField).forEach(listenerField -> {
            createEventListenerFor(listenerField, context);
            findBrowserFieldFor(listenerField, context).forEach(browserField -> {
                Browser browser = getBrowser(browserField, context);
                EventListener eventListener = registerEventListener(browser, listenerField, context);
                registeredEventListener.put(eventListener, browser);
                log.debug(
                    "EventListener '" + listenerField.getName() + "' registered to Browser '" + getBrowserName(browserField)
                        + "'");
            });
        });
        context.getStore(BaseExtension.NAMESPACE).put("registered-eventlisteners", registeredEventListener);
    }

    private void createEventListenerFor(Field field, TestExtensionContext context) {
        Object testInstance = context.getTestInstance();
        try {
            if (field.get(testInstance) == null) {
                EventListener newEventListener = ( EventListener ) field.getType().newInstance();
                field.set(testInstance, newEventListener);
            }
        } catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
            throw new UndeclaredThrowableException(e, "error while creating event listener");
        }
    }

    private List<Field> findBrowserFieldFor(Field listenerField, TestExtensionContext context) {
        return getModel(context).getNamedBrowserFields()
            .values()
            .stream()
            .filter(browserField -> isATargetBrowser(browserField, listenerField))
            .collect(Collectors.toList());
    }

    private boolean isATargetBrowser(Field browserField, Field listenerField) {
        List<String> targets = Arrays.asList(getTargetBrowserName(listenerField));
        String browserName = getBrowserName(browserField);

        if ("default".equals(targets.get(0)) && !"default".equals(browserName)) {
            throw new NoManagedBrowserForNameException(listenerField);
        }
        return targets.stream().anyMatch(target -> target.equals(browserName));
    }

    private String[] getTargetBrowserName(Field browserField) {
        return browserField.getAnnotation(Registered.class).targets();
    }

    private String getBrowserName(Field browserField) {
        return browserField.getAnnotation(Managed.class).value();
    }

    private Browser getBrowser(Field browserField, TestExtensionContext context) {
        try {
            return ( Browser ) browserField.get(context.getTestInstance());
        } catch (IllegalAccessException e) {
            throw new UndeclaredThrowableException(e, "error while finding managed browser");
        }
    }

    private EventListener registerEventListener(Browser browser, Field listenerField, TestExtensionContext context) {
        EventListener eventListener = getValue(listenerField, context.getTestInstance());
        browser.events().register(eventListener);
        return eventListener;
    }

    private void deRegisterAllEventListeners(TestExtensionContext context) {
        getRegisteredEventListeners(context).entries()
            .forEach(entry -> entry.getValue().events().deregister(entry.getKey()));
        log.debug("Unregistered all event listeners each mapped with its target browser");
    }

    @SuppressWarnings("unchecked")
    private Multimap<EventListener, Browser> getRegisteredEventListeners(TestExtensionContext context) {
        return ( Multimap<EventListener, Browser> ) context.getStore(BaseExtension.NAMESPACE)
            .getOrComputeIfAbsent("registered-eventlisteners", s -> HashMultimap.create());
    }

}
