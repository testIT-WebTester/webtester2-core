package info.novatec.testit.webtester.internal.proxies.arounds;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Supplier;

import org.openqa.selenium.WebElement;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.PageFragmentEventBuilder;
import info.novatec.testit.webtester.events.Produces;
import info.novatec.testit.webtester.internal.proxies.impls.Implementation;
import info.novatec.testit.webtester.pagefragments.PageFragment;


@Slf4j
public class EventProducingImplementationDecorator {

    private static final String BUILDER_SUFFIX = "$Builder";

    private final Configuration configuration;
    private final EventSystem eventSystem;
    private final Supplier<WebElement> webElementSupplier;

    public EventProducingImplementationDecorator(Browser browser, Supplier<WebElement> webElementSupplier) {
        this.configuration = browser.configuration();
        this.eventSystem = browser.events();
        this.webElementSupplier = webElementSupplier;
    }

    public boolean shouldBeInvoked(Method method) {
        return eventSystemIsEnabled() && isEventProducing(method);
    }

    private boolean eventSystemIsEnabled() {
        return configuration.isEventSystemEnabled();
    }

    private boolean isEventProducing(Method method) {
        return method.isAnnotationPresent(Produces.class);
    }

    public Object invoke(Object proxy, Method method, Object[] args, Implementation implementation) throws Throwable {

        Produces producesEvent = method.getAnnotation(Produces.class);

        PageFragmentEventBuilder eventBuilder = getEventBuilderFor(producesEvent);
        eventBuilder.setPageFragment(( PageFragment ) proxy);
        if (eventBuilder.needsBeforeData()) {
            eventBuilder.setBeforeData(webElementSupplier.get());
        }

        Object returnObject = implementation.invoke(proxy, method, args);

        if (eventBuilder.needsAfterData()) {
            eventBuilder.setAfterData(webElementSupplier.get());
        }

        eventSystem.fireEvent(eventBuilder.build());
        return returnObject;

    }

    @SuppressWarnings("unchecked")
    private PageFragmentEventBuilder getEventBuilderFor(Produces producesEvent)
        throws IllegalAccessException, InstantiationException {
        String eventBuilderClassName = producesEvent.value().getName() + BUILDER_SUFFIX;
        return lookupClass(eventBuilderClassName).filter(PageFragmentEventBuilder.class::isAssignableFrom)
            .map(aClass -> ( Class<PageFragmentEventBuilder> ) aClass)
            .orElseThrow(() -> new IllegalStateException("no event builder class found: " + eventBuilderClassName))
            .newInstance();
    }

    private Optional<Class<?>> lookupClass(String className) {
        Class<?> loadedClass = null;
        try {
            loadedClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.error("error loading event builder class: " + className, e);
        }
        return Optional.ofNullable(loadedClass);
    }

}
