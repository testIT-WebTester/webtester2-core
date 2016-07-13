package info.novatec.testit.webtester.junit5;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

import info.novatec.testit.webtester.junit5.extensions.analysis.TestClassAnalysisExtension;
import info.novatec.testit.webtester.junit5.extensions.browsers.EntryPointExtension;
import info.novatec.testit.webtester.junit5.extensions.browsers.ManagedBrowserExtension;
import info.novatec.testit.webtester.junit5.extensions.configuration.ConfigurationValueExtension;
import info.novatec.testit.webtester.junit5.extensions.pages.PageInitializerExtension;


/**
 * This annotation can be used to activate all WebTester extensions for the annotated class.
 *
 * @see TestClassAnalysisExtension
 * @see ManagedBrowserExtension
 * @see ConfigurationValueExtension
 * @see PageInitializerExtension
 * @since 2.1
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(TestClassAnalysisExtension.class)
@ExtendWith(ManagedBrowserExtension.class)
@ExtendWith(ConfigurationValueExtension.class)
@ExtendWith(PageInitializerExtension.class)
@ExtendWith(EntryPointExtension.class)
public @interface EnableWebTesterExtensions {
    /* The order of @ExtendWith is relevant! The extensions are evaluated in the order they are defined in. */
}
