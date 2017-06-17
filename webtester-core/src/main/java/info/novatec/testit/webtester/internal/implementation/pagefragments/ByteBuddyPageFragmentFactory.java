package info.novatec.testit.webtester.internal.implementation.pagefragments;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.internal.implementation.pagefragments.exceptions.PageFragmentCreationException;
import info.novatec.testit.webtester.internal.implementation.pagefragments.exceptions.PageFragmentMustBeInterfaceException;
import info.novatec.testit.webtester.internal.mapping.DefaultMappingValidator;
import info.novatec.testit.webtester.internal.mapping.MappingValidator;
import info.novatec.testit.webtester.internal.postconstruct.PostConstructInvoker;
import info.novatec.testit.webtester.internal.postconstruct.PostConstructMustBeChecker;
import info.novatec.testit.webtester.internal.implementation.PageFragmentFactory;
import info.novatec.testit.webtester.pagefragments.PageFragment;


public class ByteBuddyPageFragmentFactory implements PageFragmentFactory {

    private final Browser browser;

    public ByteBuddyPageFragmentFactory(Browser browser) {
        this.browser = browser;
    }

    @Override
    public PageFragment createInstanceOf(PageFragmentDescriptor descriptor) {
        assertIsInterface(descriptor.getPageFragmentType());
        Class<? extends PageFragment> implType = PageFragmentImplementation.getOrCreate(descriptor.getPageFragmentType());
        PageFragment pageFragment = createInstance(implType, descriptor);
        invokePostConstructChecks(descriptor, pageFragment);
        return pageFragment;
    }

    private void assertIsInterface(Class<? extends PageFragment> pageFragmentType) {
        if (!pageFragmentType.isInterface()) {
            throw new PageFragmentMustBeInterfaceException(pageFragmentType);
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends PageFragment> T createInstance(Class<? extends PageFragment> implType,
        PageFragmentDescriptor descriptor) {
        MappingValidator mappingValidator = new DefaultMappingValidator(descriptor.getPageFragmentType());
        try {
            Constructor<? extends PageFragment> constructor =
                implType.getConstructor(Browser.class, Supplier.class, MappingValidator.class, String.class);
            return ( T ) constructor.newInstance(browser, descriptor.getWebElementSupplier(), mappingValidator,
                descriptor.getName());
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new PageFragmentCreationException(implType, e);
        }
    }

    private void invokePostConstructChecks(PageFragmentDescriptor descriptor, PageFragment pageFragment) {
        PostConstructMustBeChecker.checkMustMethods(descriptor.getPageFragmentType(), pageFragment);
        PostConstructInvoker.invokePostConstructMethods(descriptor.getPageFragmentType(), pageFragment);
    }

}
