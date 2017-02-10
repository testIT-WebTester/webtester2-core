package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.unit.MockFactory.fragment;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class AttributeWithValueTest {

    AttributeWithValue cut = new AttributeWithValue("type", "text");

    @Test
    public void havingTheAttributeValueMatchEvaluatesToTrue() {
        PageFragment fragment = fragment().withAttribute("type", "text").build();
        assertThat(cut.test(fragment)).isTrue();
    }

    @Test
    public void havingADifferentValueForTheAttributeEvaluatesToFalse() {
        PageFragment fragment = fragment().withAttribute("type", "password").build();
        assertThat(cut.test(fragment)).isFalse();
    }

    @Test
    public void notHavingTheAttributeEvaluatesToFalse() {
        PageFragment fragment = fragment().withoutAttribute("type").build();
        assertThat(cut.test(fragment)).isFalse();
    }

    @Test
    public void toStringIsGeneratedCorrectly() {
        assertThat(cut).hasToString("attribute 'type' with value 'text'");
    }

}
