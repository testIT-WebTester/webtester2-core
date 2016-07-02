package info.novatec.testit.webtester.support.hamcrest.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link BaseMatcher} has only syntactical value. Like the {@link org.hamcrest.core.Is} matcher it delegates it's
 * evaluation to another matcher. Instances of this matcher should be initialized using the {@link WebTesterMatchers}
 * factory class.
 * <p>
 * <b>Example:</b> assertThat(textField, has(text("foo")));
 * <p>
 *
 * @param <T> the type of the wrapped matcher
 * @see WebTesterMatchers
 * @since 2.0
 */
public class HasMatcher<T> extends BaseMatcher<T> {

    /** The wrapped matcher instance. */
    private final Matcher<T> matcher;

    /**
     * Creates a new instance with wrapping the given matcher.
     *
     * @param matcher the matcher to wrap
     * @see HasMatcher
     * @since 2.0
     */
    public HasMatcher(Matcher<T> matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean matches(Object arg) {
        return matcher.matches(arg);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has ").appendDescriptionOf(matcher);
    }

    @Override
    public void describeMismatch(Object item, Description mismatchDescription) {
        matcher.describeMismatch(item, mismatchDescription);
    }

}
