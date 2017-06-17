package info.novatec.testit.webtester.internal.implementation.pages;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.internal.implementation.PageFactory;
import info.novatec.testit.webtester.internal.implementation.pages.exceptions.PageCreationException;
import info.novatec.testit.webtester.internal.postconstruct.PostConstructInvoker;
import info.novatec.testit.webtester.internal.postconstruct.PostConstructMustBeChecker;
import info.novatec.testit.webtester.internal.implementation.pages.exceptions.PageMustBeInterfaceException;
import info.novatec.testit.webtester.pages.Page;


public class ByteBuddyPageFactory implements PageFactory {

    private final Browser browser;

    public ByteBuddyPageFactory(Browser browser) {
        this.browser = browser;
    }

    @Override
    public <T extends Page> T createInstanceOf(Class<T> type) {
        assertIsInterface(type);

        Arrays.stream(type.getDeclaredConstructors()).forEach(constructor -> constructor.setAccessible(true));

        Class<? extends Page> typeImpl = PageImplementation.getOrCreate(type);
        T page = createInstance(typeImpl);
        invokePostConstructChecks(type, page);
        return page;
    }

    private <T extends Page> void assertIsInterface(Class<T> pageType) {
        if (!pageType.isInterface()) {
            throw new PageMustBeInterfaceException(pageType);
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends Page> T createInstance(Class<? extends Page> pageImplType) {
        try {
            Constructor<? extends Page> constructor = pageImplType.getConstructor(Browser.class);
            return ( T ) constructor.newInstance(browser);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new PageCreationException(pageImplType, e);
        }
    }

    private void invokePostConstructChecks(Class<? extends Page> type, Page page) {
        PostConstructMustBeChecker.checkMustMethods(type, page);
        PostConstructInvoker.invokePostConstructMethods(type, page);
    }

}
