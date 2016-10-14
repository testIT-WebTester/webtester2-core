package info.novatec.testit.webtester.conditions.syntax;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.webtester.conditions.Condition;
import info.novatec.testit.webtester.pagefragments.PageFragment;


@RunWith(Enclosed.class)
public class EitherTest {

    @RunWith(MockitoJUnitRunner.Silent.class)
    public static class TestCondition {

        @Mock
        PageFragment fragment;

        Condition<PageFragment> trueCondition = pageFragment -> true;
        Condition<PageFragment> falseCondition = pageFragment -> false;

        @Test
        public void oneTrueConditionEvaluatesToTrue() {
            Either<PageFragment> either = new Either<>(trueCondition);
            assertThat(either.test(fragment)).isTrue();
        }

        @Test
        public void oneFalseConditionEvaluatesToFalse() {
            Either<PageFragment> either = new Either<>(falseCondition);
            assertThat(either.test(fragment)).isFalse();
        }

        @Test
        public void twoTrueConditionsEvaluateToTrue() {
            Either<PageFragment> either = new Either<>(trueCondition, trueCondition);
            assertThat(either.test(fragment)).isTrue();
        }

        @Test
        public void twoFalseConditionsEvaluateToFalse() {
            Either<PageFragment> either = new Either<>(falseCondition, falseCondition);
            assertThat(either.test(fragment)).isFalse();
        }

        @Test
        public void trueAndFalseConditionsEvaluateToTrue() {
            Either<PageFragment> either = new Either<>(trueCondition, falseCondition);
            assertThat(either.test(fragment)).isTrue();
        }

        @Test
        public void falseAndTrueConditionsEvaluateToTrue() {
            Either<PageFragment> either = new Either<>(falseCondition, trueCondition);
            assertThat(either.test(fragment)).isTrue();
        }

        @Test
        public void firstTrueWillSkipFurtherEvaluation() {

            Condition<PageFragment> mockedTrueCondition1 = mockCondition(true);
            Condition<PageFragment> mockedTrueCondition2 = mockCondition(true);

            new Either<>(mockedTrueCondition1, mockedTrueCondition2).test(fragment);
            verify(mockedTrueCondition1).test(fragment);
            verifyZeroInteractions(mockedTrueCondition2);

        }

        @Test
        public void conditionsAreEvaluatedInOrder() {

            Condition<PageFragment> mockedFalseCondition1 = mockCondition(false);
            Condition<PageFragment> mockedFalseCondition2 = mockCondition(false);
            Condition<PageFragment> mockedTrueCondition = mockCondition(true);

            new Either<>(mockedFalseCondition1, mockedFalseCondition2, mockedTrueCondition).test(fragment);

            InOrder inOrder = inOrder(mockedFalseCondition1, mockedFalseCondition2, mockedTrueCondition);
            inOrder.verify(mockedFalseCondition1).test(fragment);
            inOrder.verify(mockedFalseCondition2).test(fragment);
            inOrder.verify(mockedTrueCondition).test(fragment);
            inOrder.verifyNoMoreInteractions();

        }

        Condition<PageFragment> mockCondition(boolean result){
            Condition<PageFragment> condition = mock(Condition.class);
            doReturn(result).when(condition).test(Mockito.any(PageFragment.class));
            return condition;
        }

    }

    @RunWith(MockitoJUnitRunner.Silent.class)
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
