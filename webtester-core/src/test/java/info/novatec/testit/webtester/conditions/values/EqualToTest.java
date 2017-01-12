package info.novatec.testit.webtester.conditions.values;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;


public class EqualToTest {

    @Test
    public void equalValuesEvaluatesToTrue() {
        EqualTo<String> equalTo = new EqualTo<>("foo");
        assertThat(equalTo.test("foo")).isTrue();
    }

    @Test
    public void unequalValuesEvaluatesToFalse() {
        EqualTo<String> equalTo = new EqualTo<>("foo");
        assertThat(equalTo.test("bar")).isFalse();
    }

    @Test
    public void toStringIsGeneratedCorrectly() {
        EqualTo<String> equalTo = new EqualTo<>("foo");
        assertThat(equalTo).hasToString("equalTo('foo')");
    }

}
