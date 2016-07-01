package info.novatec.testit.webtester.internal;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.css.DefaultStyleChanger;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.markings.Marker;
import info.novatec.testit.webtester.pagefragments.PageFragment;


public final class ActionTemplate {

    private ActionTemplate() {
        // utility class constructor
    }

    public static <T extends PageFragment> PageFragmentAction<T> pageFragment(T subject) {
        return new PageFragmentAction<>(subject);
    }

    public static <T extends PageFragment> PageFragmentsAction<T> pageFragments(T subject1, T subject2) {
        return new PageFragmentsAction<>(subject1, subject2);
    }

    public static <T extends Browser> BrowserAction<T> browser(T subject) {
        return new BrowserAction<>(subject);
    }

    public static class Action<T, A extends Action<T, A>> {

        private final T subject;
        private final Browser browser;

        public Action(T subject, Browser browser) {
            this.subject = subject;
            this.browser = browser;
        }

        @SuppressWarnings({ "unchecked", "PMD.AvoidCatchingGenericException" })
        public A execute(Consumer<T> consumer) {
            try {
                consumer.accept(subject);
            } catch (RuntimeException e) {
                browser.events().fireExceptionEvent(e);
                throw e;
            }
            return ( A ) this;
        }

        protected T getSubject() {
            return subject;
        }

        protected EventSystem getEventSystem() {
            return browser.events();
        }

    }

    public static class BiAction<T, A extends BiAction<T, A>> {

        private final T subject1;
        private final T subject2;
        private final Browser browser;

        public BiAction(T subject1, T subject2, Browser browser) {
            this.subject1 = subject1;
            this.subject2 = subject2;
            this.browser = browser;
        }

        @SuppressWarnings({ "unchecked", "PMD.AvoidCatchingGenericException" })
        public A execute(BiConsumer<T, T> consumer) {
            try {
                consumer.accept(subject1, subject2);
            } catch (RuntimeException e) {
                browser.events().fireExceptionEvent(e);
                throw e;
            }
            return ( A ) this;
        }

        protected T getSubject1() {
            return subject1;
        }

        protected T getSubject2() {
            return subject2;
        }

        protected EventSystem getEventSystem() {
            return browser.events();
        }

    }

    public static class PageFragmentAction<T extends PageFragment> extends Action<T, PageFragmentAction<T>> {

        private Marker marker;

        public PageFragmentAction(T subject) {
            super(subject, subject.browser());
            marker = new Marker(new DefaultStyleChanger());
        }

        public PageFragmentAction<T> fireEvent(Function<T, Event> function) {
            EventSystem eventSystem = getEventSystem();
            if (eventSystem.isEnabled()) {
                eventSystem.fireEvent(function.apply(getSubject()));
            }
            return this;
        }

        public PageFragmentAction<T> markAsUsed() {
            marker.markAsUsed(getSubject());
            return this;
        }

        public PageFragmentAction<T> markAsRead() {
            marker.markAsRead(getSubject());
            return this;
        }

    }

    public static class PageFragmentsAction<T extends PageFragment> extends BiAction<T, PageFragmentsAction<T>> {

        private Marker marker;

        public PageFragmentsAction(T subject1, T subject2) {
            super(subject1, subject2, subject1.browser());
            marker = new Marker(new DefaultStyleChanger());
        }

        public PageFragmentsAction<T> fireEvent(BiFunction<T, T, Event> function) {
            EventSystem eventSystem = getEventSystem();
            if (eventSystem.isEnabled()) {
                eventSystem.fireEvent(function.apply(getSubject1(), getSubject2()));
            }
            return this;
        }

        public PageFragmentsAction<T> markAsUsed() {
            marker.markAsUsed(getSubject1());
            marker.markAsUsed(getSubject2());
            return this;
        }

        public PageFragmentsAction<T> markAsRead() {
            marker.markAsRead(getSubject1());
            marker.markAsRead(getSubject2());
            return this;
        }

    }

    public static class BrowserAction<T extends Browser> extends Action<T, BrowserAction<T>> {

        public BrowserAction(T subject) {
            super(subject, subject);
        }

        public BrowserAction<T> fireEvent(Function<T, Event> function) {
            EventSystem eventSystem = getEventSystem();
            if (eventSystem.isEnabled()) {
                eventSystem.fireEvent(function.apply(getSubject()));
            }
            return this;
        }

    }

}
