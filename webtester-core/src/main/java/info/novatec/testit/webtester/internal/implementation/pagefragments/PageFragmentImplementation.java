package info.novatec.testit.webtester.internal.implementation.pagefragments;

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
import info.novatec.testit.webtester.internal.implementation.advices.EventProducerAdvice;
import info.novatec.testit.webtester.internal.implementation.advices.MarkingAdvice;
import info.novatec.testit.webtester.internal.implementation.invocationhandler.AttributeInvocationHandler;
import info.novatec.testit.webtester.internal.implementation.invocationhandler.IdentifyUsingInvocationHandler;
import info.novatec.testit.webtester.internal.implementation.invocationhandler.KotlinDefaultMethodHandler;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.annotations.Attribute;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;


final class PageFragmentImplementation {

    private static final Map<ClassLoader, Map<Class<? extends PageFragment>, Class<? extends PageFragment>>> CACHE =
        new HashMap<>();

    static Class<? extends PageFragment> getOrCreate(Class<? extends PageFragment> pageFragmentType) {
        ClassLoader classLoader = pageFragmentType.getClassLoader();
        Map<Class<? extends PageFragment>, Class<? extends PageFragment>> implementationMap =
            CACHE.computeIfAbsent(classLoader, cl -> new HashMap<>());
        return implementationMap.computeIfAbsent(pageFragmentType, PageFragmentImplementation::create);
    }

    private static Class<? extends PageFragment> create(Class<? extends PageFragment> pageFragmentType) {

        String className = pageFragmentType.getCanonicalName() + "$$Impl";
        ClassLoader classLoader = pageFragmentType.getClassLoader();

        InvocationHandler identifyUsingHandler = new IdentifyUsingInvocationHandler();
        InvocationHandler attributeHandler = new AttributeInvocationHandler();

        Builder<BasePageFragment> pageFragmentTypeBuilder = new ByteBuddy()//
            .with(ClassFileVersion.JAVA_V8)
            .subclass(BasePageFragment.class, ConstructorStrategy.Default.IMITATE_SUPER_CLASS)
            .implement(pageFragmentType)
            .name(className);

        if (ClasspathUtils.KOTLIN_MODULE_LOADED) {
            pageFragmentTypeBuilder = addKotlinImplementations(pageFragmentTypeBuilder, pageFragmentType);
        }

        pageFragmentTypeBuilder = pageFragmentTypeBuilder//
            .method(isDefaultMethod())//
            .intercept(Advice.to(ActionAdvice.class)//
                .wrap(Advice.to(MarkingAdvice.class)//
                    .wrap(Advice.to(EventProducerAdvice.class)//
                        .wrap(DefaultMethodCall.prioritize(pageFragmentType)))));

        pageFragmentTypeBuilder = pageFragmentTypeBuilder//
            .method(isAbstract().and(isAnnotatedWith(IdentifyUsing.class)).and(takesArguments(0)))
            .intercept(InvocationHandlerAdapter.of(identifyUsingHandler));

        pageFragmentTypeBuilder = pageFragmentTypeBuilder//
            .method(isAbstract().and(isAnnotatedWith(Attribute.class)).and(takesArguments(0)))
            .intercept(Advice.to(MarkingAdvice.class)//
                .wrap(InvocationHandlerAdapter.of(attributeHandler)));

        return pageFragmentTypeBuilder.make().load(classLoader).getLoaded();

    }

    private static Builder<BasePageFragment> addKotlinImplementations(Builder<BasePageFragment> pageFragmentTypeBuilder,
        Class<? extends PageFragment> pageFragmentType) {
        try {
            Class<?> kotlinPageFragment = Class.forName(ClasspathUtils.KOTLIN_PAGE_FRAGMENT_CLASS);
            if (kotlinPageFragment.isAssignableFrom(pageFragmentType)) {
                InvocationHandler kotlinDefaultMethodHandler = new KotlinDefaultMethodHandler();
                return pageFragmentTypeBuilder//
                    .method(isAbstract())//
                    .intercept(InvocationHandlerAdapter.of(kotlinDefaultMethodHandler));
            }
            return pageFragmentTypeBuilder;
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("could not load Kotlin PageFragment implementation", e);
        }
    }

    private PageFragmentImplementation() {
        // utility class
    }

}
