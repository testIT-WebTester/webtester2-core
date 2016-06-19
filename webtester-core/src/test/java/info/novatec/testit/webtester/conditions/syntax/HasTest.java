package info.novatec.testit.webtester.conditions.syntax;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import info.novatec.testit.webtester.conditions.Condition;


public class HasTest {

    Object object = new Object();

    @Test
    public void trueConditionEvaluatesToTrue() {
        Has<Object> has = new Has<>(object -> true);
        assertThat(has.test(object)).isTrue();
    }

    @Test
    public void falseConditionEvaluatesToFalse() {
        Has<Object> has = new Has<>(object -> false);
        assertThat(has.test(object)).isFalse();
    }

    @Test
    public void toStringIsGeneratedCorrectly() {
        Condition<Object> condition = mock(Condition.class);
        doReturn("condition").when(condition).toString();
        assertThat(new Has<>(condition)).hasToString("has(condition)");
    }

}
