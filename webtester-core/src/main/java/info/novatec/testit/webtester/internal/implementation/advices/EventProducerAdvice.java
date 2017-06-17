package info.novatec.testit.webtester.internal.implementation.advices;

import java.lang.reflect.Method;
import java.util.Optional;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.PageFragmentEventBuilder;
import info.novatec.testit.webtester.events.Produces;
import info.novatec.testit.webtester.internal.implementation.exceptions.NoDefaultConstructorException;
import info.novatec.testit.webtester.pagefragments.PageFragment;


@Slf4j
public class EventProducerAdvice {

    private static final String BUILDER_SUFFIX = "$Builder";

    public static final ThreadLocal<EventProducerImpl> THREAD_LOCAL = new ThreadLocal<>();

    @Advice.OnMethodEnter
    public static void onMethodEnter(@Advice.This PageFragment pageFragment, @Advice.Origin Method method) {
        THREAD_LOCAL.remove();
        THREAD_LOCAL.set(new EventProducerImpl(pageFragment, method));
        THREAD_LOCAL.get().onMethodEnter();
    }

    @Advice.OnMethodExit
    public static void onMethodExit(@Advice.This PageFragment pageFragment, @Advice.Origin Method method) {
        try {
            EventProducerImpl impl = THREAD_LOCAL.get();
            if (impl != null) {
                impl.onMethodExit();
            }
        } finally {
            THREAD_LOCAL.remove();

        }
    }

    public static class EventProducerImpl {

        private final PageFragment pageFragment;
        private final Browser browser;
        private final Configuration configuration;
        private final Produces produces;
        private PageFragmentEventBuilder eventBuilder;

        public EventProducerImpl(PageFragment pageFragment, Method method) {
            this.pageFragment = pageFragment;
            this.browser = pageFragment.browser();
            this.configuration = browser.configuration();
            this.produces = method.getAnnotation(Produces.class);
        }

        public void onMethodEnter() {
            if (shouldExecute()) {
                eventBuilder = getEventBuilderFor(produces);
                eventBuilder.setPageFragment(pageFragment);
                if (eventBuilder.needsBeforeData()) {
                    tryToSetBeforeData(eventBuilder, pageFragment);
                }
            }
        }

        public void onMethodExit() {
            if (shouldExecute() && eventBuilder != null) {
                if (eventBuilder.needsAfterData()) {
                    tryToSetAfterData(eventBuilder, pageFragment);
                }
                Event event = eventBuilder.build();
                EventSystem eventSystem = browser.events();
                eventSystem.fireEvent(event);
            }
        }

        private boolean shouldExecute() {
            return configuration.isEventSystemEnabled() && produces != null;
        }

        private PageFragmentEventBuilder getEventBuilderFor(Produces producesEvent) {
            String eventBuilderClassName = producesEvent.value().getName() + BUILDER_SUFFIX;
            Class<PageFragmentEventBuilder> builderClass = getPageFragmentEventBuilderClass(eventBuilderClassName);
            try {
                return builderClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new NoDefaultConstructorException(builderClass, e);
            }
        }

        @SuppressWarnings("unchecked")
        private Class<PageFragmentEventBuilder> getPageFragmentEventBuilderClass(String eventBuilderClassName) {
            return lookupClass(eventBuilderClassName).filter(PageFragmentEventBuilder.class::isAssignableFrom)
                .map(aClass -> ( Class<PageFragmentEventBuilder> ) aClass)
                .orElseThrow(() -> new IllegalArgumentException("No event builder class found: " + eventBuilderClassName));
        }

        private void tryToSetBeforeData(PageFragmentEventBuilder eventBuilder, PageFragment pageFragment) {
            try {
                WebElement webElement = pageFragment.webElement();
                eventBuilder.setBeforeData(webElement);
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                log.warn("Exception while setting event before data: " + e.getMessage());
                log.debug("Stacktrace for previous warning: ", e);
            }
        }

        private void tryToSetAfterData(PageFragmentEventBuilder eventBuilder, PageFragment pageFragment) {
            try {
                WebElement webElement = pageFragment.webElement();
                eventBuilder.setAfterData(webElement);
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                log.warn("Exception while setting event after data: " + e.getMessage());
                log.debug("Stacktrace for previous warning: ", e);
            }
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

}
