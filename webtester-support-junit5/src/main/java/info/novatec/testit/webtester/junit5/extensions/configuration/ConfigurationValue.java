package info.novatec.testit.webtester.junit5.extensions.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;
import info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter.NoOpUnmarshaller;
import info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter.ConfigurationUnmarshaller;


/**
 * This annotation can be used to trigger the injection of configuration values into fields.
 * <p>
 * Because each {@link Browser} has his own {@link Configuration} instance a {@link #source()} must be specified in case
 * multiple browsers are used within the same test.
 * <p>
 * The following types are supported by default:
 * <ul>
 * <li>String</li>
 * <li>Integer</li>
 * <li>Long</li>
 * <li>Float</li>
 * <li>Double</li>
 * <li>Boolean</li>
 * </ul>
 * Custom types can be used by implementing a {@link ConfigurationUnmarshaller} and setting a class reference for {@link
 * #using()}.
 * <p>
 * <b>Note:</b> This annotation can only be applied on non-static instance fields!
 *
 * @see Managed
 * @see Browser
 * @see Configuration
 * @see ConfigurationValueExtension
 * @see ConfigurationUnmarshaller
 * @since 2.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface ConfigurationValue {

    /**
     * The key of the {@link Configuration} property to inject.
     *
     * @return the key
     * @since 2.1
     */
    String value();

    /**
     * The name of the source {@link Browser} to use when resolving a {@link Configuration} key.
     *
     * @return the source browser's name
     * @see Managed
     * @since 2.1
     */
    String source() default "default";

    /**
     * A class reference specifying a custom {@link ConfigurationUnmarshaller} used to build custom types from one or more
     * {@link Configuration} values.
     * <p>
     * If none is specified the default implementations for {@code String, Boolean, Integer, Long, Float and Double} are
     * used.
     *
     * @return the {@link ConfigurationUnmarshaller} to use
     * @since 2.1
     */
    Class<? extends ConfigurationUnmarshaller> using() default NoOpUnmarshaller.class;

}
