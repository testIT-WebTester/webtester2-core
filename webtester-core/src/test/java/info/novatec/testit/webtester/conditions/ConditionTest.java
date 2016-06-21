package info.novatec.testit.webtester.conditions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(Enclosed.class)
public class ConditionTest {

    @RunWith(MockitoJUnitRunner.class)
    public static abstract class AbstractConditionTest {

        @Mock
        Object object;

        Condition<Object> trueCondition = object -> true;
        Condition<Object> falseCondition = object -> false;

        Condition<Object> mockCondition(boolean result) {
            Condition<Object> condition = mock(Condition.class);
            doReturn(result).when(condition).test(object);
            return condition;
        }

    }

    public static class Negate extends AbstractConditionTest {

        @Test
        public void trueBecomesFalse() {
            Condition<Object> condition = object -> true;
            assertThat(condition.negate().test(object)).isFalse();
        }

        @Test
        public void falseBecomesTrue() {
            Condition<Object> condition = object -> false;
            assertThat(condition.negate().test(object)).isTrue();
        }

    }

    public static class And extends AbstractConditionTest {

        @Test
        public void trueAndTrueIsTrue() {
            assertThat(trueCondition.and(trueCondition).test(object)).isTrue();
        }

        @Test
        public void trueAndFalseIsFalse() {
            assertThat(trueCondition.and(falseCondition).test(object)).isFalse();
        }

        @Test
        public void falseAndTrueIsFalse() {
            assertThat(falseCondition.and(trueCondition).test(object)).isFalse();
        }

        @Test
        public void falseAndFalseIsFalse() {
            assertThat(falseCondition.and(falseCondition).test(object)).isFalse();
        }

        @Test
        public void allConditionsAreEvaluatedInOrder() {

            Condition<Object> startCondition = object -> true;
            Condition<Object> condition1 = mockCondition(true);
            Condition<Object> condition2 = mockCondition(true);
            Condition<Object> condition3 = mockCondition(true);

            startCondition.and(condition1).and(condition2).and(condition3).test(object);

            InOrder inOrder = inOrder(condition1, condition2, condition3);
            inOrder.verify(condition1).test(object);
            inOrder.verify(condition2).test(object);
            inOrder.verify(condition3).test(object);
            inOrder.verifyNoMoreInteractions();

        }

    }

    public static class Or extends AbstractConditionTest {

        @Test
        public void trueOrTrueIsTrue() {
            assertThat(trueCondition.or(trueCondition).test(object)).isTrue();
        }

        @Test
        public void trueOrFalseIsFalse() {
            assertThat(trueCondition.or(falseCondition).test(object)).isTrue();
        }

        @Test
        public void falseOrTrueIsFalse() {
            assertThat(falseCondition.or(trueCondition).test(object)).isTrue();
        }

        @Test
        public void falseOrFalseIsFalse() {
            assertThat(falseCondition.or(falseCondition).test(object)).isFalse();
        }

        @Test
        public void allConditionsAreEvaluatedInOrder() {

            Condition<Object> startCondition = object -> false;
            Condition<Object> condition1 = mockCondition(false);
            Condition<Object> condition2 = mockCondition(false);
            Condition<Object> condition3 = mockCondition(false);

            startCondition.or(condition1).or(condition2).or(condition3).test(object);

            InOrder inOrder = inOrder(condition1, condition2, condition3);
            inOrder.verify(condition1).test(object);
            inOrder.verify(condition2).test(object);
            inOrder.verify(condition3).test(object);
            inOrder.verifyNoMoreInteractions();

        }

    }

}
