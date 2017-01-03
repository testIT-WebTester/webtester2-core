package info.novatec.testit.webtester.junit5.extensions.eventlisteners;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.junit5.extensions.browsers.CreateBrowsersUsing;
import info.novatec.testit.webtester.junit5.extensions.browsers.CreateUsing;
import info.novatec.testit.webtester.junit5.extensions.browsers.ManagedBrowserExtension;


/**
 * This annotation can be used on {@link EventListener} fields in order to trigger the automatic creation, injection and
 * registration of event listener instances.
 * <p>
 * Registration is done in the form of automatic registering and unregistering of the event listener in conjunction with
 * the specified target browser before and after each test.
 * <p>
 * If one or more target browsers are specified, the name must be equal to the unique name of the managed browser.
 * To specify a target browser use the {@link #targets()} property.
 * <p>
 * <b>Note:</b> In order to initialize a browser instance the framework needs to known how to create new browsers. This can
 * either be specified globally for a test by annotating the class with {@link CreateBrowsersUsing} or it can be done locally
 * by annotating the browser field with {@link CreateUsing}.
 *
 * @see EventListener
 * @see ManagedBrowserExtension
 * @since 2.1
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Registered {

    /**
     * The target name of one or more browsers. Event listener will be (un-)registered to every specified browser.
     *
     * @return target name of one or more browsers
     * @since 2.1
     */
    String[] targets() default "default";

}
