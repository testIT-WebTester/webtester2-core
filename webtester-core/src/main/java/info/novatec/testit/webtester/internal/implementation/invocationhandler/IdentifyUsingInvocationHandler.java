package info.novatec.testit.webtester.internal.implementation.invocationhandler;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.conditions.Condition;
import info.novatec.testit.webtester.internal.OffersBrowserGetter;
import info.novatec.testit.webtester.internal.implementation.PageFragmentFactory;
import info.novatec.testit.webtester.internal.implementation.exceptions.NoDefaultConstructorException;
import info.novatec.testit.webtester.internal.implementation.exceptions.UnsupportedReturnTypeException;
import info.novatec.testit.webtester.internal.implementation.pagefragments.StaticWebElementSupplier;
import info.novatec.testit.webtester.internal.implementation.pagefragments.DynamicWebElementSupplier;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.annotations.Named;
import info.novatec.testit.webtester.pagefragments.annotations.WaitUntil;
import info.novatec.testit.webtester.pagefragments.identification.ByProducer;
import info.novatec.testit.webtester.pages.Page;
import info.novatec.testit.webtester.waiting.Wait;


@SuppressWarnings("unchecked")
public class IdentifyUsingInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {

        Class<?> returnType = method.getReturnType();

        if (PageFragment.class.isAssignableFrom(returnType)) {
            return new SinglePageFragmentInvocation(proxy, method).invoke();
        }

        if (Stream.class.isAssignableFrom(returnType)) {
            return new StreamMultiplePageFragmentsInvocation(proxy, method).invoke();
        } else if (List.class.isAssignableFrom(returnType)) {
            return new ListMultiplePageFragmentsInvocation(proxy, method).invoke();
        } else if (Set.class.isAssignableFrom(returnType)) {
            return new SetMultiplePageFragmentsInvocation(proxy, method).invoke();
        } else if (Collection.class.isAssignableFrom(returnType)) {
            return new CollectionMultiplePageFragmentsInvocation(proxy, method).invoke();
        }

        throw new UnsupportedReturnTypeException(method);

    }

    private static class SinglePageFragmentInvocation extends AbstractInvocation<PageFragment> {

        private static final String IDENTIFIED_BY_NAME = "%s identified by %s: %s";

        private SinglePageFragmentInvocation(Object proxy, Method method) {
            super(proxy, method);
        }

        @Override
        PageFragment doInvokeMethod() {
            PageFragmentFactory.PageFragmentDescriptor descriptor = PageFragmentFactory.PageFragmentDescriptor.builder()
                .pageFragmentType(getPageFragmentType())
                .webElementSupplier(getWebElementSupplier())
                .name(getName())
                .build();
            return getFactory().createInstanceOf(descriptor);
        }

        private Class<? extends PageFragment> getPageFragmentType() {
            return ( Class<? extends PageFragment> ) method.getReturnType();
        }

        private Supplier<WebElement> getWebElementSupplier() {
            return new DynamicWebElementSupplier(getSearchContextSupplier(), getBy());
        }

        private String getName() {
            return Optional.of(method)//
                .map(m -> m.getAnnotation(Named.class))//
                .map(Named::value)//
                .orElseGet(this::getIdentifiedByNameFrom);
        }

        private String getIdentifiedByNameFrom() {
            IdentifyUsing annotation = method.getAnnotation(IdentifyUsing.class);
            String how = annotation.how().getSimpleName();
            String value = annotation.value();
            String pageFragmentClass = getPageFragmentType().getSimpleName();
            return String.format(IDENTIFIED_BY_NAME, pageFragmentClass, how, value);
        }

    }

    private static class StreamMultiplePageFragmentsInvocation
        extends AbstractMultiplePageFragmentsInvocation<Stream<? extends PageFragment>> {

        private StreamMultiplePageFragmentsInvocation(Object proxy, Method method) {
            super(proxy, method);
        }

        @Override
        Stream<? extends PageFragment> postProcess(Stream<? extends PageFragment> stream) {
            return stream;
        }

    }

    private static class ListMultiplePageFragmentsInvocation
        extends AbstractMultiplePageFragmentsInvocation<List<? extends PageFragment>> {

        private ListMultiplePageFragmentsInvocation(Object proxy, Method method) {
            super(proxy, method);
        }

        @Override
        List<? extends PageFragment> postProcess(Stream<? extends PageFragment> stream) {
            return stream.collect(toList());
        }

    }

    private static class SetMultiplePageFragmentsInvocation
        extends AbstractMultiplePageFragmentsInvocation<Set<? extends PageFragment>> {

        private SetMultiplePageFragmentsInvocation(Object proxy, Method method) {
            super(proxy, method);
        }

        @Override
        Set<? extends PageFragment> postProcess(Stream<? extends PageFragment> stream) {
            return stream.collect(toSet());
        }

    }

    private static class CollectionMultiplePageFragmentsInvocation
        extends AbstractMultiplePageFragmentsInvocation<Collection<? extends PageFragment>> {

        private CollectionMultiplePageFragmentsInvocation(Object proxy, Method method) {
            super(proxy, method);
        }

        @Override
        Collection<? extends PageFragment> postProcess(Stream<? extends PageFragment> stream) {
            return stream.collect(toList());
        }

    }

    private static abstract class AbstractMultiplePageFragmentsInvocation<T> extends AbstractInvocation<T> {

        private AbstractMultiplePageFragmentsInvocation(Object proxy, Method method) {
            super(proxy, method);
        }

        @Override
        T doInvokeMethod() {
            PageFragmentFactory factory = getFactory();
            Class<? extends PageFragment> pageFragmentType = getPageFragmentType();
            Stream<PageFragment> stream = getSearchContextSupplier().get()//
                .findElements(getBy())//
                .stream()//
                .map(webElement -> {
                    PageFragmentFactory.PageFragmentDescriptor descriptor = PageFragmentFactory.PageFragmentDescriptor.builder()
                        .pageFragmentType(pageFragmentType)
                        .webElementSupplier(new StaticWebElementSupplier(webElement))
                        .build();
                    return factory.createInstanceOf(descriptor);
                });
            return postProcess(stream);
        }

        abstract T postProcess(Stream<? extends PageFragment> stream);

        private Class<? extends PageFragment> getPageFragmentType() {
            ParameterizedType genericType = ( ParameterizedType ) method.getGenericReturnType();
            return ( Class<? extends PageFragment> ) genericType.getActualTypeArguments()[0];
        }

    }

    private static abstract class AbstractInvocation<T> {

        final Object proxy;
        final Method method;

        AbstractInvocation(Object proxy, Method method) {
            this.proxy = proxy;
            this.method = method;
        }

        final T invoke() {
            waitIfAnnotationPresent();
            return doInvokeMethod();
        }

        final void waitIfAnnotationPresent() {
            WaitUntil waitUntil = method.getAnnotation(WaitUntil.class);
            if (waitUntil != null) {
                Supplier<Object> invocation = this::doInvokeMethod;
                Condition condition = getCondition(waitUntil);
                if (waitUntil.timeout() > 0) {
                    Wait.withTimeoutOf(waitUntil.timeout(), waitUntil.unit())//
                        .untilSupplied(invocation)//
                        .is(condition);
                } else {
                    Wait.untilSupplied(invocation)//
                        .is(condition);
                }
            }
        }

        abstract T doInvokeMethod();

        final Condition getCondition(WaitUntil waitUntil) {
            Class<? extends Condition> conditionType = waitUntil.value();
            try {
                return conditionType.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new NoDefaultConstructorException(conditionType, e);
            }
        }

        final ByProducer getByProducer(IdentifyUsing annotation) {
            Class<? extends ByProducer> byProducerType = annotation.how();
            try {
                return byProducerType.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new NoDefaultConstructorException(byProducerType, e);
            }
        }

        final PageFragmentFactory getFactory() {
            return PageFragmentFactory.createInstanceFor(getBrowser());
        }

        final Browser getBrowser() {
            OffersBrowserGetter browserGetter = ( OffersBrowserGetter ) proxy;
            return browserGetter.browser();
        }

        final Supplier<SearchContext> getSearchContextSupplier() {
            if (proxy instanceof Page) {
                Browser browser = (( Page ) proxy).browser();
                return browser::webDriver;
            } else if (proxy instanceof PageFragment) {
                PageFragment pageFragment = ( PageFragment ) proxy;
                return pageFragment::webElement;
            }
            throw new IllegalStateException();
        }

        final By getBy() {
            IdentifyUsing identifyUsing = method.getAnnotation(IdentifyUsing.class);
            ByProducer byProducer = getByProducer(identifyUsing);
            return byProducer.createBy(identifyUsing.value());
        }

    }

}
