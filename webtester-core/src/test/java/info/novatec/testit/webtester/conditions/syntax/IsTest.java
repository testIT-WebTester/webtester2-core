package info.novatec.testit.webtester.conditions.syntax;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.function.Predicate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.webtester.pagefragments.PageFragment;


@RunWith(MockitoJUnitRunner.class)
public class IsTest {

    @Mock
    PageFragment fragment;

    @Test
    public void testThatPredicateEvaluatesCorrectly_True() {
        Is<PageFragment> cut = buildClassUnderTest()
            .withPredicateReturning(true)
            .build();
        assertThat(cut.test(fragment)).isTrue();
    }

    @Test
    public void testThatPredicateEvaluatesCorrectly_False() {
        Is<PageFragment> cut = buildClassUnderTest()
            .withPredicateReturning(false)
            .build();
        assertThat(cut.test(fragment)).isFalse();
    }

    /* details */

    IsTestBuilder buildClassUnderTest() {
        return new IsTestBuilder();
    }

    @SuppressWarnings("unchecked")
    class IsTestBuilder {

        Predicate<PageFragment> predicate;

        IsTestBuilder withPredicateReturning(boolean result) {
            predicate = mock(Predicate.class);
            doReturn(result).when(predicate).test(fragment);
            return this;
        }

        Is<PageFragment> build() {
            return new Is<>(predicate);
        }

    }

}
