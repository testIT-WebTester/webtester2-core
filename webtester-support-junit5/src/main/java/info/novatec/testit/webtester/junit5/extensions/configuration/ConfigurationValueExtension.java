package info.novatec.testit.webtester.junit5.extensions.configuration;

import java.lang.reflect.Field;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.TestExtensionContext;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.junit5.extensions.BaseExtension;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;
import info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter.BooleanConfigurationValueGetter;
import info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter.DoubleConfigurationValueGetter;
import info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter.FloatConfigurationValueGetter;
import info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter.IntegerConfigurationValueGetter;
import info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter.LongConfigurationValueGetter;
import info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter.NoOpConfigurationValueGetter;
import info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter.StringConfigurationValueGetter;
import info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter.TypedConfigurationValueGetter;


/**
 * This {@link Extension} resolves and injects {@link Configuration} property values into fields annotated with {@link
 * ConfigurationValue}. This is done before the first {@link BeforeEach} method is invoked.
 * <p>
 * For this injection the field's type is evaluated and a matching {@link TypedConfigurationValueGetter} looked up. Out of
 * the box the following types can be resolved:
 * <ul>
 * <li>{@link String}</li>
 * <li>{@link Boolean}</li>
 * <li>{@link Integer}</li>
 * <li>{@link Long}</li>
 * <li>{@link Float}</li>
 * <li>{@link Double}</li>
 * </ul>
 * To use a custom {@link TypedConfigurationValueGetter} a class reference must be specified as {@link
 * ConfigurationValue#using()}.
 * <p>
 * In case the test is declaring multiple {@link Managed} {@link Browser} instances each must have a unique name and the
 * {@link ConfigurationValue#source()} property must be specified!
 * <p>
 * <b>Example:</b>
 * <pre>
 * public class FooTest {
 *
 *     &#64;Managed
 *     Browser browser;
 *
 *     &#64;ConfigurationValue("keys.foo")
 *     String value;
 *
 *     &#64;ConfigurationValue(value="keys.foo", using=FooGetter.class)
 *     Foo fooValue;
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
 *     &#64;ConfigurationValue(value="keys.foo", source="b2")
 *     String value;
 *
 * }
 * </pre>
 *
 * @see ConfigurationValue
 * @see TypedConfigurationValueGetter
 * @see Configuration
 * @since 2.1
 */
public class ConfigurationValueExtension extends BaseExtension implements BeforeEachCallback {

    @SuppressWarnings("unchecked")
    private static final List<TypedConfigurationValueGetter<?>> DEFAULT_VALUE_GETTERS = new LinkedList() {{
        add(new StringConfigurationValueGetter());
        add(new BooleanConfigurationValueGetter());
        add(new IntegerConfigurationValueGetter());
        add(new LongConfigurationValueGetter());
        add(new FloatConfigurationValueGetter());
        add(new DoubleConfigurationValueGetter());
    }};

    @Override
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void beforeEach(TestExtensionContext context) throws Exception {
        try {
            injectValuesIntoAnnotatedFields(context);
        } catch (UndeclaredThrowableException e) {
            Throwable cause = e.getCause();
            if (cause instanceof Exception) {
                throw ( Exception ) cause;
            }
            throw e;
        }
    }

    private void injectValuesIntoAnnotatedFields(TestExtensionContext context) throws IllegalAccessException {

        List<Field> valueFields = getModel(context).getConfigurationValueFields();
        if (valueFields.isEmpty()) {
            return;
        }

        Object testInstance = context.getTestInstance();
        Map<String, Field> map = getModel(context).getNamedBrowserFields();
        valueFields.forEach(field -> {

            ConfigurationValue annotation = field.getAnnotation(ConfigurationValue.class);
            String browserName = annotation.source();

            Field browserField = map.get(browserName);
            if (browserField == null) {
                throw new IllegalStateException("no managed browser for given name: " + browserName);
            }
            Browser browser = getValue(browserField, testInstance);

            Configuration config = browser.configuration();

            String key = getKey(field);
            Class<?> type = field.getType();

            Object value = null;
            if (NoOpConfigurationValueGetter.class.equals(annotation.using())) {
                value = getValueUsingDefaultGetters(config, key, type);
            } else {
                TypedConfigurationValueGetter<?> getter;
                try {
                    getter = annotation.using().newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new UndeclaredThrowableException(e);
                }
                if (getter.canHandle(type)) {
                    value = getter.get(config, key).orElseThrow(() -> new IllegalStateException("dsa"));
                } else {
                    throw new IllegalStateException("given getter can't handle type: " + type);
                }
            }
            setValue(field, testInstance, value);

        });

    }

    private String getKey(Field field) {
        return field.getAnnotation(ConfigurationValue.class).value();
    }

    private Object getValueUsingDefaultGetters(Configuration config, String key, Class<?> type) {
        return DEFAULT_VALUE_GETTERS.stream()
            .filter(resolver -> resolver.canHandle(type))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("No matching ValueResolver found for type '" + type + "'"))
            .get(config, key)
            .orElseThrow(() -> new IllegalStateException("no value for configuration key found: " + key));
    }

}
