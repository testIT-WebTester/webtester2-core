package info.novatec.testit.webtester.junit5.extensions.browsers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.BrowserFactory;
import info.novatec.testit.webtester.browser.proxy.NoProxyConfiguration;
import info.novatec.testit.webtester.browser.proxy.ProxyConfiguration;


/**
 * This annotation can be used to either declare how a {@link Managed} {@link Browser} is created or override the test's
 * global configuration (see {@link CreateBrowsersUsing}).
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
 * @see CreateBrowsersUsing
 * @see StaticManagedBrowserExtension
 * @see InstanceManagedBrowserExtension
 * @since 2.1
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateUsing {

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
