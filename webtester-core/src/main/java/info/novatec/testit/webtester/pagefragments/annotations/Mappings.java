package info.novatec.testit.webtester.pagefragments.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.internal.mapping.MappingValidator;


/**
 * This meta annotation can be used to annotate a {@link PageFragment page fragments} with multiple {@link Mapping @Mapping}
 * annotations. Since WebTester is build on Java8 you don't actually have to use this annotation directly. Simply annotate
 * your page fragments multiple times with {@link Mapping @Mapping}.
 *
 * @see Mapping
 * @see MappingValidator
 * @see PageFragment
 * @since 2.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Mappings {
    Mapping[] value();
}
