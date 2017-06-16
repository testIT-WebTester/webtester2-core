package info.novatec.testit.webtester.internal.proxies.impls;

import static info.novatec.testit.webtester.internal.proxies.impls.IdentifyUsingImpl.COULD_NOT_CREATE_PREDICATE_INSTANCE_MSG;
import static java.util.stream.Collectors.toList;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.conditions.Condition;
import info.novatec.testit.webtester.internal.PageFragmentFactory;
import info.novatec.testit.webtester.internal.exceptions.IllegalSignatureException;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.annotations.WaitUntil;
import info.novatec.testit.webtester.pagefragments.identification.ByProducers;
import info.novatec.testit.webtester.waiting.Wait;


abstract class AbstractIdentifyUsingCollectionImpl implements Implementation {

    private final Supplier<SearchContext> searchContextSupplier;
    private final PageFragmentFactory factory;

    AbstractIdentifyUsingCollectionImpl(Browser browser, Supplier<SearchContext> searchContextSupplier) {
        this.searchContextSupplier = searchContextSupplier;
        this.factory = new PageFragmentFactory(browser);
    }

    List<? extends PageFragment> getStreamOfPageFragmentsFor(Method method) {

        IdentifyUsing identifyUsing = method.getAnnotation(IdentifyUsing.class);
        Class<?> type = method.getReturnType();
        Class<PageFragment> genericType = getGenericType(method);
        By by = ByProducers.createBy(identifyUsing);

        waitIfAnnotationPresent(method, type, genericType, by);
        return findPageFragments(genericType, by);

    }

    private void waitIfAnnotationPresent(Method method, Class<?> type, Class<PageFragment> genericType, By by) {
        WaitUntil annotation = method.getAnnotation(WaitUntil.class);
        if (annotation != null) {
            doWaitUntil(annotation, type, genericType, by);
        }
    }

    @SuppressWarnings("unchecked")
    private void doWaitUntil(WaitUntil annotation, Class<?> type, Class<PageFragment> genericType, By by) {
        try {
            Condition condition = annotation.value().newInstance();
            assertConditionCanHandleCollection(type, condition);
            try {
                if (annotation.timeout() > 0) {
                    Wait.withTimeoutOf(annotation.timeout(), annotation.unit())
                        .untilSupplied(() -> findPageFragments(genericType, by))
                        .is(condition);
                } else {
                    Wait.untilSupplied(() -> findPageFragments(genericType, by)).is(condition);
                }
            } catch (ClassCastException e) {
                throw buildIllegalSignatureException(type, condition.getClass());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalSignatureException(COULD_NOT_CREATE_PREDICATE_INSTANCE_MSG, e);
        }
    }

    private void assertConditionCanHandleCollection(Class<?> type, Condition condition) {
        Class<? extends Condition> conditionClass = condition.getClass();
        Method testMethod = Arrays.stream(conditionClass.getMethods())//
            .filter(method -> "test".equals(method.getName()))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("test method not found"));
        if (!testMethod.getParameterTypes()[0].isAssignableFrom(type)) {
            throw buildIllegalSignatureException(type, conditionClass);
        }
    }

    private IllegalSignatureException buildIllegalSignatureException(Class<?> type, Class<?> conditionClass) {
        return new IllegalSignatureException(
            "Condition '" + conditionClass.getSimpleName() + "' cant't handle method return type '" + type.getSimpleName()
                + "'");
    }

    private List<PageFragment> findPageFragments(Class<PageFragment> type, By by) {
        return searchContextSupplier.get()
            .findElements(by)
            .stream()
            .map(webElement -> factory.pageFragment(type, webElement))
            .collect(toList());
    }

    @SuppressWarnings("unchecked")
    private static <T extends PageFragment> Class<T> getGenericType(Method method) {
        ParameterizedType genericType = ( ParameterizedType ) method.getGenericReturnType();
        return ( Class<T> ) genericType.getActualTypeArguments()[0];
    }

}
