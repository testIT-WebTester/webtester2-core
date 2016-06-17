package info.novatec.testit.webtester.conditions.syntax;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.webtester.conditions.Condition;
import info.novatec.testit.webtester.pagefragments.PageFragment;


@RunWith(Enclosed.class)
public class EitherTest {

    @RunWith(MockitoJUnitRunner.class)
    public static class TestCondition {

        @Mock
        PageFragment fragment;
        @Mock
        Condition<PageFragment> true1;
        @Mock
        Condition<PageFragment> true2;
        @Mock
        Condition<PageFragment> false1;
        @Mock
        Condition<PageFragment> false2;

        @Before
        public void prepareConditions() {
            doReturn(true).when(true1).test(fragment);
            doReturn(true).when(true2).test(fragment);
            doReturn(false).when(false1).test(fragment);
            doReturn(false).when(false2).test(fragment);
        }

        @Test
        public void oneTrueConditionEvaluatesToTrue() {
            Either<PageFragment> either = new Either<>(true1);
            assertThat(either.test(fragment)).isTrue();
        }

        @Test
        public void oneFalseConditionEvaluatesToFalse() {
            Either<PageFragment> either = new Either<>(false1);
            assertThat(either.test(fragment)).isFalse();
        }

        @Test
        public void twoTrueConditionsEvaluateToTrue() {
            Either<PageFragment> either = new Either<>(true1, true2);
            assertThat(either.test(fragment)).isTrue();
        }

        @Test
        public void twoFalseConditionsEvaluateToFalse() {
            Either<PageFragment> either = new Either<>(false1, false2);
            assertThat(either.test(fragment)).isFalse();
        }

        @Test
        public void trueAndFalseConditionsEvaluateToTrue() {
            Either<PageFragment> either = new Either<>(true1, false1);
            assertThat(either.test(fragment)).isTrue();
        }

        @Test
        public void falseAndTrueConditionsEvaluateToTrue() {
            Either<PageFragment> either = new Either<>(false1, true1);
            assertThat(either.test(fragment)).isTrue();
        }

        @Test
        public void firstTrueWillSkipFurtherEvaluation() {
            new Either<>(true1, true2).test(fragment);
            verify(true1).test(fragment);
            verifyZeroInteractions(true2);
        }

        @Test
        public void conditionsAreEvaluatedInOrder() {
            new Either<>(false1, false2, true1).test(fragment);
            InOrder inOrder = inOrder(false1, false2, true1);
            inOrder.verify(false1).test(fragment);
            inOrder.verify(false2).test(fragment);
            inOrder.verify(true1).test(fragment);
            inOrder.verifyNoMoreInteractions();
        }

    }

    @RunWith(MockitoJUnitRunner.class)
    public static class ToString {

        @Mock
        Condition<Object> first;
        @Mock
        Condition<Object> second;

        @Before
        public void prepareConditions() {
            doReturn("first").when(first).toString();
            doReturn("second").when(second).toString();
        }

        @Test
        public void toStringFormatsCorrectlyForOneCondition() {
            Either<Object> either = new Either<>(first);
            assertThat(either).hasToString("either(first)");
        }

        @Test
        public void toStringFormatsCorrectlyForMultipleConditions() {
            Either<Object> either = new Either<>(first, second);
            assertThat(either).hasToString("either(first, second)");
        }

    }

}
