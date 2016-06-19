package info.novatec.testit.webtester.conditions.syntax;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.webtester.conditions.Condition;


@RunWith(MockitoJUnitRunner.class)
public class IsTest {

    Object object = new Object();

    @Test
    public void trueConditionEvaluatesToTrue() {
        Is<Object> is = new Is<>(object -> true);
        assertThat(is.test(object)).isTrue();
    }

    @Test
    public void falseConditionEvaluatesToFalse() {
        Is<Object> is = new Is<>(object -> false);
        assertThat(is.test(object)).isFalse();
    }

    @Test
    public void toStringIsGeneratedCorrectly() {
        Condition<Object> condition = mock(Condition.class);
        doReturn("condition").when(condition).toString();
        assertThat(new Is<>(condition)).hasToString("is(condition)");
    }

}
