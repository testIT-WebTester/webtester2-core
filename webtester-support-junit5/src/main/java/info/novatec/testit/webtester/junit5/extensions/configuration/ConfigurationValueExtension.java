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

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.junit5.exceptions.NoManagedBrowserException;
import info.novatec.testit.webtester.junit5.exceptions.NoManagedBrowserForNameException;
import info.novatec.testit.webtester.junit5.extensions.BaseExtension;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;
import info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter.BooleanUnmarshaller;
import info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter.DoubleUnmarshaller;
import info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter.FloatUnmarshaller;
import info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter.IntegerUnmarshaller;
import info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter.LongUnmarshaller;
import info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter.NoOpUnmarshaller;
import info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter.StringUnmarshaller;
import info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter.ConfigurationUnmarshaller;


/**
 * This {@link Extension} resolves and injects {@link Configuration} property values into fields annotated with {@link
 * ConfigurationValue}. This is done before the first {@link BeforeEach} method is invoked.
 * <p>
 * For this injection the field's type is evaluated and a matching {@link ConfigurationUnmarshaller} looked up. Out of
 * the box the following types can be resolved:
 * <ul>
 * <li>{@link String}</li>
 * <li>{@link Boolean}</li>
 * <li>{@link Integer}</li>
 * <li>{@link Long}</li>
 * <li>{@link Float}</li>
 * <li>{@link Double}</li>
 * </ul>
 * To use a custom {@link ConfigurationUnmarshaller} a class reference must be specified as {@link
 * ConfigurationValue#using()}.
 * <p>
 * In case the test is declaring multiple {@link Managed} {@link Browser} instances each must have a unique name and the
 * {@link ConfigurationValue#source()} property must be specified!
 * <p>
 * <b>Example:</b>
 * <pre>
 * &#64;EnableWebTesterExtensions
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
 * &#64;EnableWebTesterExtensions
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
 * @see ConfigurationUnmarshaller
 * @see Configuration
 * @since 2.1
 */
@Slf4j
public class ConfigurationValueExtension extends BaseExtension implements BeforeEachCallback {

    @SuppressWarnings("unchecked")
    private static final List<ConfigurationUnmarshaller<?>> DEFAULT_VALUE_GETTERS = new LinkedList() {{
        add(new StringUnmarshaller());
        add(new BooleanUnmarshaller());
        add(new IntegerUnmarshaller());
        add(new LongUnmarshaller());
        add(new FloatUnmarshaller());
        add(new DoubleUnmarshaller());
    }};

    @Override
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void beforeEach(TestExtensionContext context) throws Exception {
        executeHandlingUndeclaredThrowables(context, this::injectValuesIntoAnnotatedFields);
    }

    private void injectValuesIntoAnnotatedFields(TestExtensionContext context) {

        List<Field> valueFields = getModel(context).getConfigurationValueFields();
        if (valueFields.isEmpty()) {
            log.debug("no configuration value fields to initialize");
            return;
        }

        Map<String, Field> map = getModel(context).getNamedBrowserFields();
        if (map.isEmpty()) {
            throw new NoManagedBrowserException();
        }

        Object testInstance = context.getTestInstance();
        valueFields.forEach(field -> {

            ConfigurationValue annotation = field.getAnnotation(ConfigurationValue.class);
            String browserName = annotation.source();

            Field browserField = map.get(browserName);
            if (browserField == null) {
                throw new NoManagedBrowserForNameException(browserName);
            }
            Browser browser = getValue(browserField, testInstance);

            Configuration config = browser.configuration();

            String key = getKey(field);
            Class<?> type = field.getType();

            Object value = null;
            if (NoOpUnmarshaller.class.equals(annotation.using())) {
                value = getValueUsingDefaultGetters(config, key, type);
            } else {
                ConfigurationUnmarshaller<?> unmarshaller;
                try {
                    unmarshaller = annotation.using().newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new UndeclaredThrowableException(e);
                }
                if (unmarshaller.canHandle(type)) {
                    value = unmarshaller.unmarshal(config, key).orElseThrow(() -> new IllegalStateException("dsa"));
                } else {
                    throw new IllegalStateException("given unmarshaller can't handle type: " + type);
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
            .unmarshal(config, key)
            .orElseThrow(() -> new IllegalStateException("no value for configuration key found: " + key));
    }

}
