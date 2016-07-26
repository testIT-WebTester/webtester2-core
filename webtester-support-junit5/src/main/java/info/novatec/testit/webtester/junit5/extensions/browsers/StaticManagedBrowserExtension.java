package info.novatec.testit.webtester.junit5.extensions.browsers;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ContainerExtensionContext;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;


@Slf4j
public class StaticManagedBrowserExtension extends AbstractManagedBrowserExtension
    implements BeforeAllCallback, AfterAllCallback {

    @Override
    protected String getUniqueStorageKey() {
        return "managed-class-browsers";
    }

    @Override
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void beforeAll(ContainerExtensionContext context) throws Exception {
        try {
            initializeAndInjectStaticBrowsers(context);
        } catch (UndeclaredThrowableException e) {
            Throwable cause = e.getCause();
            if (cause instanceof Exception) {
                throw ( Exception ) cause;
            }
            throw e;
        }
    }

    public void initializeAndInjectStaticBrowsers(ContainerExtensionContext context) {
        Class<?> testClass = context.getTestClass().orElseThrow(() -> new IllegalStateException("no test class"));
        List<Browser> managedBrowsers = getManagedBrowsers(context);
        getModel(context).getBrowserFields().stream().filter(isStaticField).forEach(field -> {
            Browser browser = createBrowserFor(field, testClass);
            managedBrowsers.add(browser);
            setValue(field, null, browser);
        });
    }

    @Override
    public void afterAll(ContainerExtensionContext context) {
        closeAllManagedBrowsers(context);
    }

}
