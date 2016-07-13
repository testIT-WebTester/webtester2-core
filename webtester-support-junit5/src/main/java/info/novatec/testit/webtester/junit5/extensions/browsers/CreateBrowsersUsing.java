package info.novatec.testit.webtester.junit5.extensions.browsers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.BrowserFactory;
import info.novatec.testit.webtester.browser.proxy.NoProxyConfiguration;
import info.novatec.testit.webtester.browser.proxy.ProxyConfiguration;


/**
 * This annotation can be used to declare how a {@link Managed} {@link Browser} is created by default for the annotated test.
 * This default can be changed by annotating the browser field with {@link CreateUsing}.
 * <p>
 * A custom {@link ProxyConfiguration} can be set by providing a class reference for the {@link #proxy()} property.
 * <p>
 * The given {@link BrowserFactory} and {@link ProxyConfiguration} classes must have a default constructor - otherwise the
 * creation will fail with an exception!
 *
 * @see Managed
 * @see Browser
 * @see BrowserFactory
 * @see ProxyConfiguration
 * @see CreateUsing
 * @see ManagedBrowserExtension
 * @since 2.1
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateBrowsersUsing {

    /**
     * The {@link BrowserFactory} to use when creating a new {@link Browser} instance.
     *
     * @return the factory
     * @see Browser
     * @see BrowserFactory
     * @see Managed
     * @since 2.1
     */
    Class<? extends BrowserFactory> value();

    /**
     * The {@link ProxyConfiguration} to use when creating a new {@link Browser} instance. Defaults to {@link
     * NoProxyConfiguration}.
     *
     * @return the factory
     * @see Browser
     * @see BrowserFactory
     * @see ProxyConfiguration
     * @see NoProxyConfiguration
     * @see Managed
     * @since 2.1
     */
    Class<? extends ProxyConfiguration> proxy() default NoProxyConfiguration.class;

}
