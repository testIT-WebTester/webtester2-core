package info.novatec.testit.webtester.conditions.syntax;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.webtester.conditions.Condition;
import info.novatec.testit.webtester.pagefragments.PageFragment;


@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class EitherTest {

    @Mock
    PageFragment fragment;

    @Test
    public void testThatConditionEvaluatesOrCorrectly_TrueTrue() {
        Either<PageFragment> cut = buildClassUnderTest()
            .addConditionReturning(true)
            .addConditionReturning(true)
            .build();
        assertThat(cut.test(fragment)).isTrue();
    }

    @Test
    public void testThatConditionEvaluatesOrCorrectly_TrueFalse() {
        Either<PageFragment> cut = buildClassUnderTest()
            .addConditionReturning(true)
            .addConditionReturning(false)
            .build();
        assertThat(cut.test(fragment)).isTrue();
    }

    @Test
    public void testThatConditionEvaluatesOrCorrectly_FalseTrue() {
        Either<PageFragment> cut = buildClassUnderTest()
            .addConditionReturning(false)
            .addConditionReturning(true)
            .build();
        assertThat(cut.test(fragment)).isTrue();
    }

    @Test
    public void testThatConditionEvaluatesOrCorrectly_FalseFalse() {
        Either<PageFragment> cut = buildClassUnderTest()
            .addConditionReturning(false)
            .addConditionReturning(false)
            .build();
        assertThat(cut.test(fragment)).isFalse();
    }

    @Test
    public void testThatConditionsAreCalledInOrder() {

        Condition<PageFragment> first = createConditionReturning(false);
        Condition<PageFragment> second = createConditionReturning(false);
        Condition<PageFragment> third = createConditionReturning(false);

        Either<PageFragment> cut = buildClassUnderTest().addCondition(first)
            .addCondition(second)
            .addCondition(third)
            .build();
        cut.test(fragment);

        InOrder inOrder = inOrder(first, second, third);
        inOrder.verify(first).test(fragment);
        inOrder.verify(second).test(fragment);
        inOrder.verify(third).test(fragment);
        inOrder.verifyNoMoreInteractions();

    }

    @Test
    public void testThatFirstPositiveEvaluationStopsTheEvaluationOfRemainingConditions() {

        Condition<PageFragment> first = createConditionReturning(true);
        Condition<PageFragment> second = createConditionReturning(true);

        Either<PageFragment> cut = buildClassUnderTest().addCondition(first).addCondition(second).build();
        cut.test(fragment);

        InOrder inOrder = inOrder(first, second);
        inOrder.verify(first).test(fragment);
        inOrder.verifyNoMoreInteractions();

    }

    /* details */

    EitherTestBuilder buildClassUnderTest() {
        return new EitherTestBuilder();
    }

    Condition<PageFragment> createConditionReturning(boolean result) {
        Condition<PageFragment> condition = mock(Condition.class);
        doReturn(result).when(condition).test(fragment);
        return condition;
    }

    class EitherTestBuilder {

        List<Condition<PageFragment>> conditions = new LinkedList<>();

        EitherTestBuilder addConditionReturning(boolean result) {
            Condition<PageFragment> condition = mock(Condition.class);
            doReturn(result).when(condition).test(fragment);
            conditions.add(condition);
            return this;
        }

        EitherTestBuilder addCondition(Condition<PageFragment> condition) {
            conditions.add(condition);
            return this;
        }

        Either<PageFragment> build() {
            return new Either<>(conditions.toArray(new Condition[conditions.size()]));
        }

    }

}
