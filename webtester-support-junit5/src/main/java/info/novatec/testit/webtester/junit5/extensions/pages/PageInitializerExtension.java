package info.novatec.testit.webtester.junit5.extensions.pages;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.TestExtensionContext;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit5.exceptions.NoManagedBrowserException;
import info.novatec.testit.webtester.junit5.exceptions.NoManagedBrowserForNameException;
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
@Slf4j
public class PageInitializerExtension extends BaseExtension implements BeforeEachCallback {

    @Override
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void beforeEach(TestExtensionContext context) throws Exception {
        executeHandlingUndeclaredThrowables(context, this::createPagesForAnnotatedFields);
    }

    private void createPagesForAnnotatedFields(TestExtensionContext context) {

        List<Field> pageFields = getModel(context).getPageFields();
        if (pageFields.isEmpty()) {
            log.debug("no pages to initialize");
            return;
        }

        Map<String, Field> map = getModel(context).getNamedBrowserFields();
        if (map.isEmpty()) {
            throw new NoManagedBrowserException();
        }

        Object testInstance = context.getTestInstance();
        pageFields.forEach(pageField -> {

            String browserName = getBrowserName(pageField);

            Field browserField = map.get(browserName);
            if (browserField == null) {
                throw new NoManagedBrowserForNameException(browserName);
            }
            Browser browser = getValue(browserField, testInstance);

            Page page = createPage(pageField, browser);
            setValue(pageField, testInstance, page);
            log.debug("initialized field '{} {}' with new page instance from '{}' browser",
                pageField.getType().getSimpleName(), pageField.getName(), browserName);

        });

    }

    private String getBrowserName(Field pageField) {
        return pageField.getAnnotation(Initialized.class).source();
    }

    @SuppressWarnings("unchecked")
    private Page createPage(Field pageField, Browser browser) {
        return browser.create(( Class<? extends Page> ) pageField.getType());
    }

}
