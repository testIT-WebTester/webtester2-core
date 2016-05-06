package info.novatec.testit.webtester.conditions.syntax;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.webtester.pagefragments.PageFragment;


@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class EitherTest {

    @Mock
    PageFragment fragment;

    @Test
    public void testThatPredicateEvaluatesOrCorrectly_TrueTrue() {
        Either<PageFragment> cut = buildClassUnderTest()
            .addPredicateReturning(true)
            .addPredicateReturning(true)
            .build();
        assertThat(cut.test(fragment)).isTrue();
    }

    @Test
    public void testThatPredicateEvaluatesOrCorrectly_TrueFalse() {
        Either<PageFragment> cut = buildClassUnderTest()
            .addPredicateReturning(true)
            .addPredicateReturning(false)
            .build();
        assertThat(cut.test(fragment)).isTrue();
    }

    @Test
    public void testThatPredicateEvaluatesOrCorrectly_FalseTrue() {
        Either<PageFragment> cut = buildClassUnderTest()
            .addPredicateReturning(false)
            .addPredicateReturning(true)
            .build();
        assertThat(cut.test(fragment)).isTrue();
    }

    @Test
    public void testThatPredicateEvaluatesOrCorrectly_FalseFalse() {
        Either<PageFragment> cut = buildClassUnderTest()
            .addPredicateReturning(false)
            .addPredicateReturning(false)
            .build();
        assertThat(cut.test(fragment)).isFalse();
    }

    @Test
    public void testThatPredicatesAreCalledInOrder() {

        Predicate<PageFragment> firstPredicate = createPredicateReturning(false);
        Predicate<PageFragment> secondPredicate = createPredicateReturning(false);
        Predicate<PageFragment> thirdPredicate = createPredicateReturning(false);

        Either<PageFragment> cut = buildClassUnderTest().addPredicate(firstPredicate)
            .addPredicate(secondPredicate)
            .addPredicate(thirdPredicate)
            .build();
        cut.test(fragment);

        InOrder inOrder = inOrder(firstPredicate, secondPredicate, thirdPredicate);
        inOrder.verify(firstPredicate).test(fragment);
        inOrder.verify(secondPredicate).test(fragment);
        inOrder.verify(thirdPredicate).test(fragment);
        inOrder.verifyNoMoreInteractions();

    }

    @Test
    public void testThatFirstPositiveEvaluationStopsTheEvaluationOfRemainingPredicates() {

        Predicate<PageFragment> firstPredicate = createPredicateReturning(true);
        Predicate<PageFragment> secondPredicate = createPredicateReturning(true);

        Either<PageFragment> cut = buildClassUnderTest().addPredicate(firstPredicate).addPredicate(secondPredicate).build();
        cut.test(fragment);

        InOrder inOrder = inOrder(firstPredicate, secondPredicate);
        inOrder.verify(firstPredicate).test(fragment);
        inOrder.verifyNoMoreInteractions();

    }

    /* details */

    EitherTestBuilder buildClassUnderTest() {
        return new EitherTestBuilder();
    }

    Predicate<PageFragment> createPredicateReturning(boolean result) {
        Predicate<PageFragment> predicate = mock(Predicate.class);
        doReturn(result).when(predicate).test(fragment);
        return predicate;
    }

    class EitherTestBuilder {

        List<Predicate<PageFragment>> predicates = new LinkedList<>();

        EitherTestBuilder addPredicateReturning(boolean result) {
            Predicate<PageFragment> predicate = mock(Predicate.class);
            doReturn(result).when(predicate).test(fragment);
            predicates.add(predicate);
            return this;
        }

        EitherTestBuilder addPredicate(Predicate<PageFragment> predicate) {
            predicates.add(predicate);
            return this;
        }

        Either<PageFragment> build() {
            return new Either<>(predicates.toArray(new Predicate[predicates.size()]));
        }

    }

}
