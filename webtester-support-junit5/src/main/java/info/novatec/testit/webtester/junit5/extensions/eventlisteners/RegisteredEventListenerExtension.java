package info.novatec.testit.webtester.junit5.extensions.eventlisteners;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.TestExtensionContext;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.junit5.extensions.BaseExtension;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;


@Slf4j
@SuppressWarnings("PMD")
public class RegisteredEventListenerExtension extends BaseExtension implements BeforeEachCallback, AfterEachCallback {

    private final Predicate<Field> isStaticField = (field) -> Modifier.isStatic(field.getModifiers());
    private final Predicate<Field> isInstanceField = isStaticField.negate();
    private TestExtensionContext context;
    private Object testInstance;

    @Override
    public void afterEach(TestExtensionContext context) throws Exception {
        this.context = context;
        testInstance = context.getTestInstance();
        deRegisterAllEventListeners();
    }

    @Override
    public void beforeEach(TestExtensionContext context) throws Exception {
        this.context = context;
        testInstance = context.getTestInstance();
        initializeEventListener();
    }

    private EventListener registerEventListener(Browser browser, Field listenerField) {
        EventListener eventListener = getValue(listenerField, testInstance);
        browser.events().register(eventListener);
        return eventListener;
    }

    private void deRegisterAllEventListeners() {
        getRegisteredEventListeners().entrySet().stream().forEach(entry -> {
            entry.getValue().events().deregister(entry.getKey());
        });
        log.info("Unregistered all event listeners each mapped with its target browser");
    }

    private List<Field> findBrowserFieldFor(Field listenerField) {
        return getModel(context).getNamedBrowserFields()
            .values()
            .stream()
            .filter(isInstanceField)
            .filter(browserField -> isATargetBrowser(browserField, listenerField))
            .collect(Collectors.toList());
    }

    private boolean isATargetBrowser(Field browserField, Field listenerField) {
        List<String> targets = Arrays.asList(getTargetBrowserName(listenerField));
        String browserName = getBrowserName(browserField);

        if (targets.get(0).equals("default") && !browserName.equals("default")) {
            throw new BrowserAssignmentException(listenerField);
        }
        return targets.stream().anyMatch(target -> target.equals(browserName));
    }

    private String[] getTargetBrowserName(Field browserField) {
        return browserField.getAnnotation(Registered.class).targets();
    }

    private Browser getBrowser(Field browserField) {
        try {
            return ( Browser ) browserField.get(context.getTestInstance());
        } catch (IllegalAccessException e) {
            throw new UndeclaredThrowableException(e, "error while finding managed browser");
        }
    }

    private String getBrowserName(Field browserField) {
        return browserField.getAnnotation(Managed.class).value();
    }

    private void initializeEventListener() {
        Map<EventListener, Browser> registeredEventListener = new HashMap<>();
        getModel(context).getEventListenerFields().stream().filter(isInstanceField).forEach(listenerField -> {
            createEventListenerFor(listenerField);
            findBrowserFieldFor(listenerField).stream().forEach(browserField -> {
                Browser browser = getBrowser(browserField);
                EventListener eventListener = registerEventListener(browser, listenerField);
                registeredEventListener.put(eventListener, browser);
                log.info(
                    "EventListener '" + listenerField.getName() + "' registered to Browser '" + getBrowserName(browserField)
                        + "'");
            });
        });
        context.getStore(BaseExtension.NAMESPACE).put("registered-eventlisteners", registeredEventListener);
    }

    private void createEventListenerFor(Field field) {
        try {
            if (field.get(testInstance) == null) {
                EventListener newEventListener = ( EventListener ) field.getType().newInstance();
                field.set(testInstance, newEventListener);
            }
        } catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
            throw new UndeclaredThrowableException(e, "error while creating event listener");
        }
    }

    @SuppressWarnings("unchecked")
    private Map<EventListener, Browser> getRegisteredEventListeners() {
        return ( Map<EventListener, Browser> ) context.getStore(BaseExtension.NAMESPACE)
            .getOrComputeIfAbsent("registered-eventlisteners", s -> new HashMap<EventListener, Browser>());
    }

}
