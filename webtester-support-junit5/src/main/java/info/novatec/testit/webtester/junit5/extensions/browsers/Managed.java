package info.novatec.testit.webtester.junit5.extensions.browsers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import info.novatec.testit.webtester.browser.Browser;


/**
 * This annotation can be used on {@link Browser} fields in order to trigger the automatic creation, injection and
 * management of browser instances.
 * <p>
 * Management is done in the form of automatic opening and closing of the browser in conjunction with it's scope. A static
 * browser will be created before any {@link BeforeAll} method is invoked and closed after the last {@link AfterAll} method
 * was invoked. An instance browser will be recreated for each test - before {@link BeforeEach} and after {@link AfterEach}
 * respectively.
 * <p>
 * If more than one browser is managed for a single test, each instance needs to specify a unique name with which it might
 * be referenced by other extensions. To do this simply specify a value for the {@link #value()}} property.
 * <p>
 * <b>Note:</b> In order to initialize a browser instance the framework needs to known how to create new browsers. This can
 * either be specified globally for a test by annotating the class with {@link CreateBrowsersUsing} or it can be done locally
 * by annotating the browser field with {@link CreateUsing}!
 *
 * @see Browser
 * @see StaticManagedBrowserExtension
 * @see InstanceManagedBrowserExtension
 * @since 2.1
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Managed {

    /**
     * The browsers unique name. Can be used to reference it in other extensions.
     *
     * @return the browser's unique name.
     * @since 2.1
     */
    String value() default StringUtils.EMPTY;

}
