package info.novatec.testit.webtester.junit5.extensions.browsers;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.TestExtensionContext;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;


@Slf4j
public class InstanceManagedBrowserExtension extends AbstractManagedBrowserExtension
    implements BeforeEachCallback, AfterEachCallback {

    @Override
    protected String getUniqueStorageKey() {
        return "managed-instance-browsers";
    }

    @Override
    public void beforeEach(TestExtensionContext context) throws Exception {
        try {
            initializeAndInjectInstanceBrowsers(context);
        } catch (UndeclaredThrowableException e) {
            Throwable cause = e.getCause();
            if (cause instanceof Exception) {
                throw ( Exception ) cause;
            }
            throw e;
        }
    }

    public void initializeAndInjectInstanceBrowsers(TestExtensionContext context) {
        Object testInstance = context.getTestInstance();
        List<Browser> managedBrowsers = getManagedBrowsers(context);
        getModel(context).getBrowserFields().stream().filter(isInstanceField).forEach(field -> {
            Browser browser = createBrowserFor(field, testInstance.getClass());
            managedBrowsers.add(browser);
            setValue(field, testInstance, browser);
        });
    }

    @Override
    public void afterEach(TestExtensionContext context) {
        closeAllManagedBrowsers(context);
    }

}
