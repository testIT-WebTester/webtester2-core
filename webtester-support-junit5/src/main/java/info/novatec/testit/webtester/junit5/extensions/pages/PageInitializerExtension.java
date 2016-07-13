package info.novatec.testit.webtester.junit5.extensions.pages;

import java.lang.reflect.Field;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.TestExtensionContext;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit5.extensions.BaseExtension;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;
import info.novatec.testit.webtester.pages.Page;


/**
 * This {@link Extension} initializes {@link Page} instances into fields annotated with {@link Initialized}. This is done
 * before the first {@link BeforeEach} method is invoked.
 * <p>
 * In case the test is declaring multiple {@link Managed} {@link Browser} instances each must have a unique name and the
 * {@link Initialized#source()} property must be specified!
 * <p>
 * <b>Example:</b>
 * <pre>
 * public class FooTest {
 *
 *     &#64;Managed
 *     Browser browser;
 *
 *     &#64;Initialized
 *     FooPage page;
 *
 * }
 *
 * public class BarTest {
 *
 *     &#64;Managed("b1")
 *     Browser browser1;
 *     &#64;Managed("b2")
 *     Browser browser2;
 *
 *     &#64;Initialized(source="b2")
 *     BarPage page;
 *
 * }
 * </pre>
 *
 * @see Initialized
 * @since 2.1
 */
public class PageInitializerExtension extends BaseExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(TestExtensionContext context) throws Exception {
        try {
            createPagesForAnnotatedFields(context);
        } catch (UndeclaredThrowableException e) {
            Throwable cause = e.getCause();
            if (cause instanceof Exception) {
                throw ( Exception ) cause;
            }
            throw e;
        }
    }

    public void createPagesForAnnotatedFields(TestExtensionContext context) {

        Object testInstance = context.getTestInstance();
        Map<String, Field> map = getModel(context).getNamedBrowserFields();

        getModel(context).getPageFields().forEach(pageField -> {

            String browserName = getBrowserName(pageField);

            Field browserField = map.get(browserName);
            if (browserField == null) {
                throw new IllegalStateException("no managed browser for given name: " + browserName);
            }
            Browser browser = getValue(browserField, testInstance);

            Page page = createPage(pageField, browser);
            setValue(pageField, testInstance, page);

        });

    }

    private String getBrowserName(Field pageField) {
        Initialized annotation = pageField.getAnnotation(Initialized.class);
        return annotation.source();
    }

    @SuppressWarnings("unchecked")
    private Page createPage(Field pageField, Browser browser) {
        return browser.create(( Class<? extends Page> ) pageField.getType());
    }

}
