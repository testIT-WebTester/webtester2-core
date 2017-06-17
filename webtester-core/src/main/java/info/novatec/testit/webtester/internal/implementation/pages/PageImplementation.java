package info.novatec.testit.webtester.internal.implementation.pages;

import static net.bytebuddy.matcher.ElementMatchers.isAbstract;
import static net.bytebuddy.matcher.ElementMatchers.isAnnotatedWith;
import static net.bytebuddy.matcher.ElementMatchers.isDefaultMethod;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

import java.lang.reflect.InvocationHandler;
import java.util.HashMap;
import java.util.Map;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.DefaultMethodCall;
import net.bytebuddy.implementation.InvocationHandlerAdapter;

import info.novatec.testit.webtester.internal.ClasspathUtils;
import info.novatec.testit.webtester.internal.implementation.advices.ActionAdvice;
import info.novatec.testit.webtester.internal.implementation.invocationhandler.IdentifyUsingInvocationHandler;
import info.novatec.testit.webtester.internal.implementation.invocationhandler.KotlinDefaultMethodHandler;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pages.Page;


final class PageImplementation {

    private static final Map<ClassLoader, Map<Class<? extends Page>, Class<? extends Page>>> CACHE = new HashMap<>();

    static Class<? extends Page> getOrCreate(Class<? extends Page> pageType) {
        ClassLoader classLoader = pageType.getClassLoader();
        Map<Class<? extends Page>, Class<? extends Page>> implementationMap =
            CACHE.computeIfAbsent(classLoader, cl -> new HashMap<>());
        return implementationMap.computeIfAbsent(pageType, PageImplementation::create);
    }

    private static Class<? extends Page> create(Class<? extends Page> pageType) {

        String className = pageType.getCanonicalName() + "$$Impl";
        ClassLoader classLoader = pageType.getClassLoader();

        InvocationHandler identifyUsingHandler = new IdentifyUsingInvocationHandler();

        Builder<BasePage> pageTypeBuilder = new ByteBuddy()//
            .with(ClassFileVersion.JAVA_V8)
            .subclass(BasePage.class, ConstructorStrategy.Default.IMITATE_SUPER_CLASS)
            .implement(pageType)
            .name(className);

        if (ClasspathUtils.KOTLIN_MODULE_LOADED) {
            pageTypeBuilder = addKotlinImplementations(pageTypeBuilder, pageType);
        }

        pageTypeBuilder = pageTypeBuilder//
            .method(isDefaultMethod())//
            .intercept(Advice.to(ActionAdvice.class)//
                .wrap(DefaultMethodCall.prioritize(pageType)));

        pageTypeBuilder = pageTypeBuilder//
            .method(isAbstract().and(isAnnotatedWith(IdentifyUsing.class)).and(takesArguments(0)))
            .intercept(InvocationHandlerAdapter.of(identifyUsingHandler));

        return pageTypeBuilder.make().load(classLoader).getLoaded();

    }

    private static Builder<BasePage> addKotlinImplementations(Builder<BasePage> pageTypeBuilder,
        Class<? extends Page> pageType) {
        try {
            Class<?> kotlinPage = Class.forName(ClasspathUtils.KOTLIN_PAGE_CLASS);
            if (kotlinPage.isAssignableFrom(pageType)) {
                InvocationHandler kotlinDefaultMethodHandler = new KotlinDefaultMethodHandler();
                return pageTypeBuilder//
                    .method(isAbstract())//
                    .intercept(InvocationHandlerAdapter.of(kotlinDefaultMethodHandler));
            }
            return pageTypeBuilder;
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("could not load Kotlin Page implementation", e);
        }
    }

    private PageImplementation() {
        // utility class
    }

}
