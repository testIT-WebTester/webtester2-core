package info.novatec.testit.webtester.internal.must;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import lombok.experimental.UtilityClass;

import info.novatec.testit.webtester.conditions.Condition;
import info.novatec.testit.webtester.internal.exceptions.IllegalSignatureException;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.annotations.PostConstructMustBe;
import info.novatec.testit.webtester.pages.Page;


@UtilityClass
public class MustChecker {

    private static final String ILLEGAL_SIGNATURE_MSG =
        "invalid @PostConstructMustBe method declarations (must not have parameters): ";

    private static Predicate<Method> isIdentificationMethod = method -> method.isAnnotationPresent(IdentifyUsing.class);
    private static Predicate<Method> isMustMethod = method -> method.isAnnotationPresent(PostConstructMustBe.class);
    private static Predicate<Method> isRelevantMethod = isIdentificationMethod.and(isMustMethod);
    private static Predicate<Method> hasNoParams = method -> method.getParameterCount() == 0;
    private static Predicate<Method> isValidMethod = hasNoParams;

    public static <T extends Page> void checkMustMethods(Class<T> pageClass, T page) {
        // NOTE: since page is a proxy, the original class needs to be provided from outside!
        doInvokeMustMethods(pageClass, page);
    }

    public static <T extends PageFragment> void checkMustMethods(Class<T> pageFragmentClass, T pageFragment) {
        // NOTE: since pageFragment is a proxy, the original class needs to be provided from outside!
        doInvokeMustMethods(pageFragmentClass, pageFragment);
    }

    private static void doInvokeMustMethods(Class<?> objectClass, Object object) {

        List<Method> mustMethods = getMustMethods(objectClass);
        assertThatAllMethodsHaveValidSignature(mustMethods);

        Map<String, Method> singularMethods = new HashMap<>();
        mustMethods.forEach(method -> singularMethods.put(method.getName(), method));

        singularMethods.values().forEach(method -> invoke(method, object));

    }

    private static List<Method> getMustMethods(Class<?> objectClass) {
        List<Method> mustMethods = new ArrayList<>();
        Arrays.stream(objectClass.getInterfaces())
            .flatMap(aClass -> Arrays.stream(aClass.getDeclaredMethods()))
            .filter(isRelevantMethod)
            .forEach(mustMethods::add);
        Arrays.stream(objectClass.getDeclaredMethods()).filter(isRelevantMethod).forEach(mustMethods::add);
        return mustMethods;
    }

    private static void assertThatAllMethodsHaveValidSignature(List<Method> mustMethods) {
        List<Method> illegalMethods = new ArrayList<>();
        mustMethods.forEach(method -> {
            if (!isValidMethod.test(method)) {
                illegalMethods.add(method);
            }
        });
        if (!illegalMethods.isEmpty()) {
            throw new IllegalSignatureException(ILLEGAL_SIGNATURE_MSG + illegalMethods);
        }
    }

    private static void invoke(Method method, Object object) {
        try {
            PostConstructMustBe annotation = method.getAnnotation(PostConstructMustBe.class);
            Object fragment = method.invoke(object);
            Condition condition = annotation.value().newInstance();
            if (!doInvoke(fragment, condition)) {
                throw new MustConditionException("condition not met for method (" + method + "): " + condition);
            }
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | ClassCastException e) {
            throw new MustConditionException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private static boolean doInvoke(Object fragment, Condition condition) {
        try {
            return condition.test(fragment);
        } catch (ClassCastException e) {
            throw new MustConditionException(
                "Condition '" + name(condition) + "' can't handle type '" + name(fragment) + "'!");
        }
    }

    private static String name(Object object) {
        return object.getClass().getSimpleName();
    }

}
