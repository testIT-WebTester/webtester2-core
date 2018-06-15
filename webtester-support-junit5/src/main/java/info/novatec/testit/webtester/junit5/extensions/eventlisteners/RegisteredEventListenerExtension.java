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
import org.junit.jupiter.api.extension.ExtensionContext;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.junit5.extensions.BaseExtension;
import info.novatec.testit.webtester.junit5.extensions.NoManagedBrowserForNameException;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;
import info.novatec.testit.webtester.junit5.internal.TestClassModel;


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
    public void beforeEach(ExtensionContext context) {
        TestClassModel model = getModel(context);
        Object testInstance = context.getRequiredTestInstance();
        Multimap<EventListener, Browser> eventListeners = initializeEventListener(model, testInstance);
        context.getStore(BaseExtension.NAMESPACE).put("registered-eventlisteners", eventListeners);
    }

    @Override
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void afterEach(ExtensionContext context) {
        deRegisterAllEventListeners(context);
    }

    Multimap<EventListener, Browser> initializeEventListener(TestClassModel model, Object testInstance) {
        Multimap<EventListener, Browser> registeredEventListener = HashMultimap.create();
        model.getEventListenerFields().stream().filter(isInstanceField).forEach(listenerField -> {
            createEventListenerFor(listenerField, testInstance);
            findBrowserFieldFor(listenerField, model).forEach(browserField -> {
                Browser browser = getBrowser(browserField, testInstance);
                EventListener eventListener = registerEventListener(browser, listenerField, testInstance);
                registeredEventListener.put(eventListener, browser);
                log.debug(
                    "EventListener '" + listenerField.getName() + "' registered to Browser '" + getBrowserName(browserField)
                        + "'");
            });
        });
        return registeredEventListener;
    }

    private void createEventListenerFor(Field field, Object testInstance) {
        try {
            if (field.get(testInstance) == null) {
                EventListener newEventListener = ( EventListener ) field.getType().newInstance();
                field.set(testInstance, newEventListener);
            }
        } catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
            throw new UndeclaredThrowableException(e, "error while creating event listener");
        }
    }

    private List<Field> findBrowserFieldFor(Field listenerField, TestClassModel model) {
        return model.getNamedBrowserFields()
            .values()
            .stream()
            .filter(browserField -> isATargetBrowser(browserField, listenerField))
            .collect(Collectors.toList());
    }

    private boolean isATargetBrowser(Field browserField, Field listenerField) {
        List<String> targets = Arrays.asList(getTargetBrowserName(listenerField));
        String browserName = getBrowserName(browserField);

        if ("default".equals(targets.get(0)) && !"default".equals(browserName)) {
            throw new NoManagedBrowserForNameException(listenerField.getName());
        }
        return targets.stream().anyMatch(target -> target.equals(browserName));
    }

    private String[] getTargetBrowserName(Field browserField) {
        return browserField.getAnnotation(Registered.class).targets();
    }

    private String getBrowserName(Field browserField) {
        return browserField.getAnnotation(Managed.class).value();
    }

    private Browser getBrowser(Field browserField, Object testInstance) {
        try {
            return ( Browser ) browserField.get(testInstance);
        } catch (IllegalAccessException e) {
            throw new UndeclaredThrowableException(e, "error while finding managed browser");
        }
    }

    private EventListener registerEventListener(Browser browser, Field listenerField, Object testInstance) {
        EventListener eventListener = getValue(listenerField, testInstance);
        browser.events().register(eventListener);
        return eventListener;
    }

    private void deRegisterAllEventListeners(ExtensionContext context) {
        getRegisteredEventListeners(context).entries()
            .forEach(entry -> entry.getValue().events().deregister(entry.getKey()));
        log.debug("Unregistered all event listeners each mapped with its target browser");
    }

    @SuppressWarnings("unchecked")
    private Multimap<EventListener, Browser> getRegisteredEventListeners(ExtensionContext context) {
        return ( Multimap<EventListener, Browser> ) context.getStore(BaseExtension.NAMESPACE)
            .getOrComputeIfAbsent("registered-eventlisteners", s -> HashMultimap.create());
    }

}
