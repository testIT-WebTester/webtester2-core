package info.novatec.testit.webtester.junit.runner.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.UndeclaredThrowableException;

import org.apache.commons.lang.StringUtils;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.proxy.ProxyConfiguration;
import info.novatec.testit.webtester.junit.annotations.CreateUsing;
import info.novatec.testit.webtester.junit.annotations.EntryPoint;
import info.novatec.testit.webtester.junit.annotations.Primary;
import info.novatec.testit.webtester.junit.exceptions.NoBrowserFactoryProvidedException;


public abstract class AbstractTestBrowser {

    private Field field;

    private Browser browser;
    private String entryPoint;
    private boolean primaryCandidate;

    public AbstractTestBrowser(Field browserField) {
        this.field = browserField;
        this.field.setAccessible(true);
        initializeEntryPoint();
        initializePrimaryCandidate();
    }

    private void initializeEntryPoint() {
        EntryPoint annotation = this.field.getAnnotation(EntryPoint.class);
        if (annotation != null) {
            entryPoint = annotation.value();
        }
    }

    private void initializePrimaryCandidate() {
        primaryCandidate = annotationIsPresent(Primary.class);
    }

    private boolean annotationIsPresent(Class<? extends Annotation> annotationClass) {
        return this.field.getAnnotation(annotationClass) != null;
    }

    protected void createBrowserAndSetStaticField() {
        createBrowserIfNecessary(null);
    }

    protected void createBrowserIfNecessary(Object target) {
        try {
            Object fieldValue = field.get(target);
            if (fieldValue != null) {
                browser = ( Browser ) fieldValue;
            } else {
                browser = createNewBrowser();
                field.set(target, browser);
            }
        } catch (ReflectiveOperationException e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    private Browser createNewBrowser() throws ReflectiveOperationException {
        CreateUsing annotation = field.getAnnotation(CreateUsing.class);
        if (annotation == null) {
            throw new NoBrowserFactoryProvidedException();
        }
        return createNewBrowserFromAnnotation(annotation);
    }

    private Browser createNewBrowserFromAnnotation(CreateUsing annotation)
        throws InstantiationException, IllegalAccessException {
        ProxyConfiguration proxyConfiguration = annotation.proxy().newInstance();
        return annotation.value().newInstance().withProxyConfiguration(proxyConfiguration).createBrowser();
    }

    protected void openEntryPointIfSet() {
        if (entryPoint == null) {
            return;
        }
        if (StringUtils.isNotBlank(entryPoint)) {
            browser.open().url(entryPoint);
        } else {
            browser.configuration()
                .getDefaultEntryPoint()
                .ifPresent(entryPoint -> browser.open().url(entryPoint));
        }
    }

    public Browser getBrowser() {
        return browser;
    }

    protected void closeBrowser() {
        if (browser != null) {
            browser.close();
        }
    }

    public abstract void beforeTest();

    public abstract void afterTest();

    public boolean isPrimaryCandidate() {
        return primaryCandidate;
    }

}
