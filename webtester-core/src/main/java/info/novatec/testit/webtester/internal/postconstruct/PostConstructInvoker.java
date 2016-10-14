package info.novatec.testit.webtester.internal.postconstruct;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import javax.annotation.PostConstruct;

import lombok.experimental.UtilityClass;

import info.novatec.testit.webtester.internal.exceptions.IllegalSignatureException;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pages.Page;

@UtilityClass
public class PostConstructInvoker {

    private static final String ILLEGAL_SIGNATURE_MSG =
        "invalid @PostConstruct method declarations (returns VOID and has no parameters): ";

    private static Predicate<Method> isPostConstructMethod = method -> method.isAnnotationPresent(PostConstruct.class);
    private static Predicate<Method> returnsVoid = method -> Void.TYPE.equals(method.getReturnType());
    private static Predicate<Method> hasNoParams = method -> method.getParameterCount() == 0;
    private static Predicate<Method> isValidMethod = returnsVoid.and(hasNoParams);

    public static <T extends Page> void invokePostConstructMethods(Class<T> pageClass, T page) {
        // NOTE: since page is a proxy, the original class needs to be provided from outside!
        doInvokePostConstructMethods(pageClass, page);
    }

    public static <T extends PageFragment> void invokePostConstructMethods(Class<T> pageFragmentClass, T pageFragment) {
        // NOTE: since pageFragment is a proxy, the original class needs to be provided from outside!
        doInvokePostConstructMethods(pageFragmentClass, pageFragment);
    }

    private static void doInvokePostConstructMethods(Class<?> objectClass, Object object) {

        List<Method> postConstructMethods = getPostConstructMethods(objectClass);
        assertThatAllMethodsHaveValidSignature(postConstructMethods);

        Map<String, Method> singularMethods = new HashMap<>();
        postConstructMethods.forEach(method -> singularMethods.put(method.getName(), method));

        singularMethods.values().forEach(method -> invoke(method, object));

    }

    private static List<Method> getPostConstructMethods(Class<?> objectClass) {
        List<Method> postConstructMethods = new LinkedList<>();
        Arrays.stream(objectClass.getInterfaces())
            .flatMap(aClass -> Arrays.stream(aClass.getDeclaredMethods()))
            .filter(isPostConstructMethod)
            .forEach(postConstructMethods::add);
        Arrays.stream(objectClass.getDeclaredMethods()).filter(isPostConstructMethod).forEach(postConstructMethods::add);
        return postConstructMethods;
    }

    private static void assertThatAllMethodsHaveValidSignature(List<Method> postConstructMethods) {
        List<Method> illegalMethods = new LinkedList<>();
        postConstructMethods.forEach(method -> {
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
            method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new PostConstructInvocationException(e);
        }
    }

}
