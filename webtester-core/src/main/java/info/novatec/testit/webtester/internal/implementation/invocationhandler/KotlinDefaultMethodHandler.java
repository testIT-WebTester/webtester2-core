package info.novatec.testit.webtester.internal.implementation.invocationhandler;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class KotlinDefaultMethodHandler implements InvocationHandler {

    private static final String NO_IMPL_FOUND = "DefaultImpls class found but no implementation for method: ";
    private static final Object STATIC = null;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
        throws InvocationTargetException, IllegalAccessException {
        Class<?> defaultImplementationClass = findDefaultImplementationClass(method)//
            .orElseThrow(IllegalStateException::new);
        Object[] argArray = argsForDefaultMethod(proxy, args);
        Method defaultMethod = findDefaultMethod(method, defaultImplementationClass);
        return defaultMethod.invoke(STATIC, argArray);
    }

    private Method findDefaultMethod(Method method, Class<?> defaultImplementationClass) {
        return stream(defaultImplementationClass.getMethods())//
            .filter(candidate -> Modifier.isStatic(candidate.getModifiers()))
            .filter(candidate -> method.getName().equals(candidate.getName()))
            .filter(candidate -> (method.getParameterCount() + 1) == candidate.getParameterCount())
            .filter(candidate -> {
                Class<?>[] methodParameterTypes = method.getParameterTypes();
                Class<?>[] candidateParameterTypes = candidate.getParameterTypes();
                boolean allParamTypesMatch = true;
                for (int i = 0; i < methodParameterTypes.length; i++) {
                    allParamTypesMatch &= methodParameterTypes[i].equals(candidateParameterTypes[i + 1]);
                }
                return allParamTypesMatch;
            })
            .findFirst()
            .orElseThrow(() -> new IllegalStateException(NO_IMPL_FOUND + method));
    }

    private Optional<Class<?>> findDefaultImplementationClass(Method method) {
        Class<?> declaringClass = method.getDeclaringClass();
        Class<?>[] declaredClasses = declaringClass.getDeclaredClasses();
        return stream(declaredClasses)//
            .filter(declaredClass -> "DefaultImpls".equals(declaredClass.getSimpleName()))//
            .findFirst();
    }

    private Object[] argsForDefaultMethod(Object proxy, Object[] args) {
        if (args != null) {
            List<Object> argList = new ArrayList<>(args.length + 1);
            argList.add(proxy);
            argList.addAll(asList(args));
            return argList.toArray(new Object[argList.size()]);
        }
        return new Object[] { proxy };
    }

}
