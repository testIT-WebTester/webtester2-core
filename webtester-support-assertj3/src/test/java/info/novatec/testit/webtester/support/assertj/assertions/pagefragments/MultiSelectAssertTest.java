package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;

import org.junit.Test;

import utils.MockFactory;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


public class MultiSelectAssertTest {

    /* selection texts */

    @Test
    public void hasSelectedTextsPositive() {
        MultiSelect select = MockFactory.multiSelect().withSelectedTexts("foo", "bar").build();
        assertThat(select).hasSelectionWithTexts("foo", "bar");
    }

    @Test(expected = AssertionError.class)
    public void selectionOrderOfTextsIsConsidered() {
        MultiSelect select = MockFactory.multiSelect().withSelectedTexts("foo", "bar").build();
        assertThat(select).hasSelectionWithTexts("bar", "foo");
    }

    @Test(expected = AssertionError.class)
    public void hasSelectedTextsNegative() {
        MultiSelect select = MockFactory.multiSelect().withSelectedTexts("foo").build();
        assertThat(select).hasSelectionWithTexts("bar");
    }

    /* selection values */

    @Test
    public void hasSelectedValuesPositive() {
        MultiSelect select = MockFactory.multiSelect().withSelectedValues("v1", "v2").build();
        assertThat(select).hasSelectionWithValues("v1", "v2");
    }

    @Test(expected = AssertionError.class)
    public void selectionOrderOfValuesIsConsidered() {
        MultiSelect select = MockFactory.multiSelect().withSelectedValues("v1", "v2").build();
        assertThat(select).hasSelectionWithValues("v2", "v1");
    }

    @Test(expected = AssertionError.class)
    public void hasSelectedValuesNegative() {
        MultiSelect select = MockFactory.multiSelect().withSelectedValues("v1").build();
        assertThat(select).hasSelectionWithValues("v2");
    }

    /* selection indices */

    @Test
    public void hasSelectedIndicesPositive() {
        MultiSelect select = MockFactory.multiSelect().withSelectedIndices(1, 2).build();
        assertThat(select).hasSelectionWithIndices(1, 2);
    }

    @Test(expected = AssertionError.class)
    public void selectionOrderOfIndicesIsConsidered() {
        MultiSelect select = MockFactory.multiSelect().withSelectedIndices(1, 2).build();
        assertThat(select).hasSelectionWithIndices(2, 1);
    }

    @Test(expected = AssertionError.class)
    public void hasSelectedIndicesNegative() {
        MultiSelect select = MockFactory.multiSelect().withSelectedIndices(1).build();
        assertThat(select).hasSelectionWithIndices(2);
    }

    /* option texts */

    @Test
    public void optionsHaveTextsPositive() {
        MultiSelect select = MockFactory.multiSelect().withOptionTexts("foo", "bar").build();
        assertThat(select).hasOptionsWithTexts("foo", "bar");
    }

    @Test(expected = AssertionError.class)
    public void optionsHaveTextsNegative_WrongOrder() {
        MultiSelect select = MockFactory.multiSelect().withOptionTexts("foo", "bar").build();
        assertThat(select).hasOptionsWithTexts("bar", "foo");
    }

    @Test(expected = AssertionError.class)
    public void optionsHaveTextsNegative_WrongText() {
        MultiSelect select = MockFactory.multiSelect().withOptionTexts("foo").build();
        assertThat(select).hasOptionsWithTexts("xur");
    }

    @Test
    public void optionsHaveTextsInAnyOrderPositive_Ordered1() {
        MultiSelect select = MockFactory.multiSelect().withOptionTexts("foo", "bar").build();
        assertThat(select).hasOptionsWithTextsInAnyOrder("foo", "bar");
    }

    @Test
    public void optionsHaveTextsInAnyOrderPositive_Ordered2() {
        MultiSelect select = MockFactory.multiSelect().withOptionTexts("foo", "bar").build();
        assertThat(select).hasOptionsWithTextsInAnyOrder("bar", "foo");
    }

    @Test(expected = AssertionError.class)
    public void optionsHaveTextsInAnyOrderNegative() {
        MultiSelect select = MockFactory.multiSelect().withOptionTexts("foo").build();
        assertThat(select).hasOptionsWithTextsInAnyOrder("xur");
    }

    /* option values */

    @Test
    public void optionsHaveValuesPositive() {
        MultiSelect select = MockFactory.multiSelect().withOptionValues("foo", "bar").build();
        assertThat(select).hasOptionsWithValues("foo", "bar");
    }

    @Test(expected = AssertionError.class)
    public void optionsHaveValuesNegative_WrongOrder() {
        MultiSelect select = MockFactory.multiSelect().withOptionValues("foo", "bar").build();
        assertThat(select).hasOptionsWithValues("bar", "foo");
    }

    @Test(expected = AssertionError.class)
    public void optionsHaveValuesNegative_WrongText() {
        MultiSelect select = MockFactory.multiSelect().withOptionValues("foo").build();
        assertThat(select).hasOptionsWithValues("xur");
    }

    @Test
    public void optionsHaveValuesInAnyOrderPositive_Ordered1() {
        MultiSelect select = MockFactory.multiSelect().withOptionValues("foo", "bar").build();
        assertThat(select).hasOptionsWithValuesInAnyOrder("foo", "bar");
    }

    @Test
    public void optionsHaveValuesInAnyOrderPositive_Ordered2() {
        MultiSelect select = MockFactory.multiSelect().withOptionValues("foo", "bar").build();
        assertThat(select).hasOptionsWithValuesInAnyOrder("bar", "foo");
    }

    @Test(expected = AssertionError.class)
    public void optionsHaveValuesInAnyOrderNegative_WrongText() {
        MultiSelect select = MockFactory.multiSelect().withOptionValues("foo").build();
        assertThat(select).hasOptionsWithValuesInAnyOrder("xur");
    }

    /* number of options */

    @Test
    public void hasOptionsPositive() {
        MultiSelect select = MockFactory.multiSelect().withNumberOfOptions(5).build();
        assertThat(select).hasOptions();
    }

    @Test(expected = AssertionError.class)
    public void hasOptionsNegative() {
        MultiSelect select = MockFactory.multiSelect().withNumberOfOptions(0).build();
        assertThat(select).hasOptions();
    }

    @Test
    public void hasOptionsWithNumberPositive() {
        MultiSelect select = MockFactory.multiSelect().withNumberOfOptions(5).build();
        assertThat(select).hasOptions(5);
    }

    @Test(expected = AssertionError.class)
    public void hasOptionsWithNumberNegative() {
        MultiSelect select = MockFactory.multiSelect().withNumberOfOptions(5).build();
        assertThat(select).hasOptions(1);
    }

    @Test
    public void hasNoOptionsPositive() {
        MultiSelect select = MockFactory.multiSelect().withNumberOfOptions(0).build();
        assertThat(select).hasNoOptions();
    }

    @Test(expected = AssertionError.class)
    public void hasNoOptionsNegative() {
        MultiSelect select = MockFactory.multiSelect().withNumberOfOptions(5).build();
        assertThat(select).hasNoOptions();
    }

    /* number of selected options */

    @Test
    public void hasSelectedOptionsPositive() {
        MultiSelect select = MockFactory.multiSelect().withNumberOfSelectedOptions(5).build();
        assertThat(select).hasSelectedOptions();
    }

    @Test(expected = AssertionError.class)
    public void hasSelectedOptionsNegative() {
        MultiSelect select = MockFactory.multiSelect().withNumberOfSelectedOptions(0).build();
        assertThat(select).hasSelectedOptions();
    }

    @Test
    public void hasSelectedOptionsWithNumberPositive() {
        MultiSelect select = MockFactory.multiSelect().withNumberOfSelectedOptions(5).build();
        assertThat(select).hasSelectedOptions(5);
    }

    @Test(expected = AssertionError.class)
    public void hasSelectedOptionsWithNumberNegative() {
        MultiSelect select = MockFactory.multiSelect().withNumberOfSelectedOptions(5).build();
        assertThat(select).hasSelectedOptions(1);
    }

    @Test
    public void hasNoSelectedOptionsPositive() {
        MultiSelect select = MockFactory.multiSelect().withNumberOfSelectedOptions(0).build();
        assertThat(select).hasNoSelectedOptions();
    }

    @Test(expected = AssertionError.class)
    public void hasNoSelectedOptionsNegative() {
        MultiSelect select = MockFactory.multiSelect().withNumberOfSelectedOptions(5).build();
        assertThat(select).hasNoSelectedOptions();
    }

}
