package info.novatec.testit.webtester.conditions.syntax;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import info.novatec.testit.webtester.conditions.Condition;


public class NotTest {

    Object object = new Object();

    @Test
    public void trueConditionEvaluatesToFalse() {
        Not<Object> cut = new Not<>(object -> true);
        assertThat(cut.test(object)).isFalse();
    }

    @Test
    public void falseConditionEvaluatesToTrue() {
        Not<Object> cut = new Not<>(object -> false);
        assertThat(cut.test(object)).isTrue();
    }

    @Test
    public void toStringIsGeneratedCorrectly() {
        Condition<Object> condition = mock(Condition.class);
        doReturn("condition").when(condition).toString();
        assertThat(new Not<>(condition)).hasToString("not(condition)");
    }

}
