package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.MockFactory.fragment;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class AttributeTest {

    Attribute cut = new Attribute("type");

    @Test
    public void havingTheAttributeEvaluatesToTrue() {
        PageFragment fragment = fragment().withAttribute("type").build();
        assertThat(cut.test(fragment)).isTrue();
    }

    @Test
    public void notHavingTheAttributeEvaluatesToFalse() {
        PageFragment fragment = fragment().withoutAttribute("type").build();
        assertThat(cut.test(fragment)).isFalse();
    }

    @Test
    public void toStringIsGeneratedCorrectly() {
        assertThat(cut).hasToString("attribute 'type'");
    }

}
