package info.novatec.testit.webtester.junit.runner.internal;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.proxy.ProxyConfiguration;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.junit.annotations.CreateUsing;
import info.novatec.testit.webtester.junit.annotations.EntryPoint;
import info.novatec.testit.webtester.junit.annotations.Primary;
import info.novatec.testit.webtester.junit.exceptions.NoBrowserFactoryProvidedException;
import org.apache.commons.lang.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class AbstractTestBrowser {

    /**
     * Matches {@code ${...}} variables in entry point URLs.
     */
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\$\\{.*?}");
    private static final String VARIABLE_INDICATOR = "${";

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
                browser = (Browser) fieldValue;
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
            String url = entryPoint;
            if (containsVariables(url)) {
                url = replaceVariables(url, browser.configuration());
            }
            browser.open().url(url);
        } else {
            browser.configuration()
                .getDefaultEntryPoint()
                .ifPresent(entryPoint -> browser.open().url(entryPoint));
        }
    }

    private boolean containsVariables(String url) {
        return url.contains(VARIABLE_INDICATOR);
    }

    private String replaceVariables(String value, Configuration configuration) {
        String modifiedValue = value;
        Matcher variablesMatcher = VARIABLE_PATTERN.matcher(value);
        while (variablesMatcher.find()) {
            String variable = variablesMatcher.group();
            String key = extractKeyFrom(variable);
            String configurationValue = getValueFrom(configuration, key);
            modifiedValue = StringUtils.replace(modifiedValue, variable, configurationValue);
        }
        return modifiedValue;
    }

    private String getValueFrom(Configuration configuration, String key) {
        return configuration.getStringProperty(key)
            .orElseThrow(() -> new IllegalStateException("No value found for configuration key: '" + key + "'"));
    }

    private String extractKeyFrom(String variable) {
        return variable.substring(2, variable.length() - 1);
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
