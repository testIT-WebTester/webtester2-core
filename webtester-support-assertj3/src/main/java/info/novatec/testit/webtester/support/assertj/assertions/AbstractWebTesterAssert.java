package info.novatec.testit.webtester.support.assertj.assertions;

import org.assertj.core.api.AbstractAssert;


/**
 * Abstract base class for all of WebTester's AssertJ assertion classes.
 *
 * @param <A> the "self" type of this assertion class. Please read
 * <a href="http://bit.ly/anMa4g" target="_blank">
 *     Emulating 'self types'using Java Generics to simplify fluent API implementation
 * </a>
 * for more details.
 * @param <B> the type of the "actual" value.
 * @since 2.0
 */
public abstract class AbstractWebTesterAssert<A, B> extends AbstractAssert<AbstractWebTesterAssert<A, B>, B> {

    public AbstractWebTesterAssert(B actual, Class<?> selfType) {
        super(actual, selfType);
    }

    protected final void failOnActualBeingNull() {
        if (actual == null) {
            failWithMessage("actual is null");
        }
    }

}
