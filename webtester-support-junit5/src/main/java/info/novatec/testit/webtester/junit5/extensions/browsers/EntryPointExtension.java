package info.novatec.testit.webtester.junit5.extensions.browsers;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.TestExtensionContext;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.junit5.extensions.UnknownConfigurationKeyException;
import info.novatec.testit.webtester.junit5.extensions.BaseExtension;


/**
 * This {@link Extension} is used to open specific URLs before each test. The URL to open is defined for each {@link
 * Browser} by annotating it's field with {@link EntryPoint}. This will only work on browsers who's fields are also marked
 * as {@link Managed}.
 * <p>
 * The URL can be specified as a static string or it can include variables which are resolved against the browser's {@link
 * Configuration}. See {@link EntryPoint} for a detailed documentation on using variables.
 * <p>
 * <b>Note:</b> in case a variable cannot be resolve an {@link UnknownConfigurationKeyException} is thrown.
 * <p>
 * <b>Example:</b>
 * <pre>
 * &#64;EnableWebTesterExtensions
 * &#64;CreateBrowsersUsing(DefaultFactory.class)
 * public class FooTest {
 *
 *     &#64;Managed
 *     &#64;EntryPoint("http://www.example.com")
 *     Browser browser;
 *
 * }
 *
 * &#64;EnableWebTesterExtensions
 * &#64;CreateBrowsersUsing(DefaultFactory.class)
 * public class BarTest {
 *
 *     &#64;Managed
 *     &#64;EntryPoint("http://${host}:${port}/index.html")
 *     static Browser browser;
 *
 * }
 * </pre>
 *
 * @see EntryPoint
 * @see Managed
 * @see Browser
 * @see Configuration
 * @since 2.1
 */
@Slf4j
public class EntryPointExtension extends BaseExtension implements BeforeEachCallback {

    /** Matches {@code ${...}} variables in entry point URLs. */
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\$\\{.*?\\}");
    private static final String VARIABLE_INDICATOR = "${";

    @Override
    public void beforeEach(TestExtensionContext context) {
        Object testInstance = context.getTestInstance();
        getModel(context).getBrowserFields()
            .stream()
            .filter(browserField -> browserField.isAnnotationPresent(EntryPoint.class))
            .forEach(browserField -> openEntryPoint(browserField, testInstance));
    }

    private void openEntryPoint(Field field, Object testInstance) {
        String url = field.getAnnotation(EntryPoint.class).value();
        log.debug("opening entry point for field '{}': '{}'", field, url);
        Browser browser = getValue(field, testInstance);
        if (containsVariables(url)) {
            url = replaceVariables(url, browser.configuration());
            log.debug("the URL contained variables which were resolved to: '{}'", url);
        }
        browser.open(url);
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
            traceLog(variable, key, configurationValue);
        }
        return modifiedValue;
    }

    private String getValueFrom(Configuration configuration, String key) {
        return configuration.getStringProperty(key).orElseThrow(() -> new UnknownConfigurationKeyException(key));
    }

    private String extractKeyFrom(String variable) {
        return variable.substring(2, variable.length() - 1);
    }

    private void traceLog(String variable, String key, String value) {
        log.trace("resolved variable '{}' by extracting key '{}' and using it to get value '{}'", variable, key, value);
    }

}
