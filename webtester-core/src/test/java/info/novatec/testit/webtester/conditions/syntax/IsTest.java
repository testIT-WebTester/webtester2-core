package info.novatec.testit.webtester.conditions.syntax;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.webtester.conditions.Condition;
import info.novatec.testit.webtester.pagefragments.PageFragment;


@RunWith(MockitoJUnitRunner.class)
public class IsTest {

    @Mock
    PageFragment fragment;

    @Test
    public void testThatConditionEvaluatesCorrectly_True() {
        Is<PageFragment> cut = buildClassUnderTest()
            .withConditionReturning(true)
            .build();
        assertThat(cut.test(fragment)).isTrue();
    }

    @Test
    public void testThatConditionEvaluatesCorrectly_False() {
        Is<PageFragment> cut = buildClassUnderTest()
            .withConditionReturning(false)
            .build();
        assertThat(cut.test(fragment)).isFalse();
    }

    /* details */

    IsTestBuilder buildClassUnderTest() {
        return new IsTestBuilder();
    }

    @SuppressWarnings("unchecked")
    class IsTestBuilder {

        Condition<PageFragment> condition;

        IsTestBuilder withConditionReturning(boolean result) {
            condition = mock(Condition.class);
            doReturn(result).when(condition).test(fragment);
            return this;
        }

        Is<PageFragment> build() {
            return new Is<>(condition);
        }

    }

}
