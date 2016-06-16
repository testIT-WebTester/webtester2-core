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
public class HasTest {

    @Mock
    PageFragment fragment;

    @Test
    public void testThatConditionEvaluatesCorrectly_True() {
        Has<PageFragment> cut = buildClassUnderTest()
            .withConditionReturning(true)
            .build();
        assertThat(cut.test(fragment)).isTrue();
    }

    @Test
    public void testThatConditionEvaluatesCorrectly_False() {
        Has<PageFragment> cut = buildClassUnderTest()
            .withConditionReturning(false)
            .build();
        assertThat(cut.test(fragment)).isFalse();
    }

    /* details */

    HasTestBuilder buildClassUnderTest() {
        return new HasTestBuilder();
    }

    @SuppressWarnings("unchecked")
    class HasTestBuilder {

        Condition<PageFragment> condition;

        HasTestBuilder withConditionReturning(boolean result) {
            condition = mock(Condition.class);
            doReturn(result).when(condition).test(fragment);
            return this;
        }

        Has<PageFragment> build() {
            return new Has<>(condition);
        }

    }

}
