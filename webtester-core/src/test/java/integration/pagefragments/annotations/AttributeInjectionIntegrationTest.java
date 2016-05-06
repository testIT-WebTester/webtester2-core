package integration.pagefragments.annotations;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;

import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.annotations.Attribute;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pages.Page;


public class AttributeInjectionIntegrationTest extends BaseIntegrationTest {

    TestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/annotations/attribute-injection.html";
    }

    @Before
    public void initPage() {
        page = create(TestPage.class);
    }

    @Test
    public void booleanAttributes() {
        TestFragment fragment = page.attributes();
        assertThat(fragment.booleanValue()).isTrue();
        assertThat(fragment.optionalBoolean()).contains(true);
        assertThat(fragment.optionalBooleanEmpty()).isEmpty();
        assertThat(fragment.optionalBooleanNonExistence()).isEmpty();
    }

    @Test
    public void integerAttributes() {
        TestFragment fragment = page.attributes();
        assertThat(fragment.integerValue()).isEqualTo(42);
        assertThat(fragment.optionalInteger()).contains(42);
        assertThat(fragment.optionalIntegerEmpty()).isEmpty();
        assertThat(fragment.optionalIntegerNonExistence()).isEmpty();
    }

    @Test
    public void longAttributes() {
        TestFragment fragment = page.attributes();
        assertThat(fragment.longValue()).isEqualTo(42L);
        assertThat(fragment.optionalLong()).contains(42L);
        assertThat(fragment.optionalLongEmpty()).isEmpty();
        assertThat(fragment.optionalLongNonExistence()).isEmpty();
    }

    @Test
    public void floatAttributes() {
        TestFragment fragment = page.attributes();
        assertThat(fragment.floatValue()).isEqualTo(0.42f);
        assertThat(fragment.optionalFloat()).contains(0.42f);
        assertThat(fragment.optionalFloatEmpty()).isEmpty();
        assertThat(fragment.optionalFloatNonExistence()).isEmpty();
    }

    @Test
    public void doubleAttributes() {
        TestFragment fragment = page.attributes();
        assertThat(fragment.doubleValue()).isEqualTo(0.42d);
        assertThat(fragment.optionalDouble()).contains(0.42d);
        assertThat(fragment.optionalDoubleEmpty()).isEmpty();
        assertThat(fragment.optionalDoubleNonExistence()).isEmpty();
    }

    @Test
    public void stringAttributes() {
        TestFragment fragment = page.attributes();
        assertThat(fragment.stringValue()).isEqualTo("foo bar");
        assertThat(fragment.optionalString()).contains("foo bar");
        assertThat(fragment.optionalStringEmpty()).contains("");
        assertThat(fragment.optionalStringNonExistence()).isEmpty();
    }

    public interface TestPage extends Page {

        @IdentifyUsing("#attributes")
        TestFragment attributes();

    }

    public interface TestFragment extends PageFragment {

        @Attribute("boolean")
        Boolean booleanValue();
        @Attribute("boolean")
        Optional<Boolean> optionalBoolean();
        @Attribute("empty")
        Optional<Boolean> optionalBooleanEmpty();
        @Attribute("unknown")
        Optional<Boolean> optionalBooleanNonExistence();

        @Attribute("int")
        Integer integerValue();
        @Attribute("int")
        Optional<Integer> optionalInteger();
        @Attribute("empty")
        Optional<Integer> optionalIntegerEmpty();
        @Attribute("unknown")
        Optional<Integer> optionalIntegerNonExistence();

        @Attribute("long")
        Long longValue();
        @Attribute("long")
        Optional<Long> optionalLong();
        @Attribute("empty")
        Optional<Long> optionalLongEmpty();
        @Attribute("unknown")
        Optional<Long> optionalLongNonExistence();

        @Attribute("float")
        Float floatValue();
        @Attribute("float")
        Optional<Float> optionalFloat();
        @Attribute("empty")
        Optional<Float> optionalFloatEmpty();
        @Attribute("unknown")
        Optional<Float> optionalFloatNonExistence();

        @Attribute("double")
        Double doubleValue();
        @Attribute("double")
        Optional<Double> optionalDouble();
        @Attribute("empty")
        Optional<Double> optionalDoubleEmpty();
        @Attribute("unknown")
        Optional<Double> optionalDoubleNonExistence();

        @Attribute("string")
        String stringValue();
        @Attribute("string")
        Optional<String> optionalString();
        @Attribute("empty")
        Optional<String> optionalStringEmpty();
        @Attribute("unknown")
        Optional<String> optionalStringNonExistence();

    }

}
