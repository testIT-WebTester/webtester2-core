package info.novatec.testit.webtester.junit5.extensions.browsers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;


/**
 * This annotation can be used on {@link Browser} fields in order to trigger the automatic opening of an URL before each
 * test.
 * <p>
 * The URL can be either completely static (e.g. {@code http://www.example.com}) or you can use variables which will be
 * resolved against the browser's {@link Configuration}. As an example such an URL could look like this: {@code
 * http://${host}:${port}/index.html}
 *
 * @see Browser
 * @see Configuration
 * @see EntryPointExtension
 * @since 2.1
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EntryPoint {

    /**
     * The {@link EntryPoint} URL to open before each test. Can be a static URL or use variables.
     *
     * @return the URL to open
     * @see EntryPoint
     * @since 2.1
     */
    String value();

}
