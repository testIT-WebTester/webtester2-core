package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;

import org.junit.Test;

import utils.MockFactory;

import info.novatec.testit.webtester.pagefragments.SingleSelect;


public class SingleSelectAssertTest {

    /* selection text */

    @Test
    public void selectionHasTextPositive() {
        SingleSelect select = MockFactory.singleSelect().withSelectedText("foo").build();
        assertThat(select).hasSelectionWithText("foo");
    }

    @Test(expected = AssertionError.class)
    public void selectionHasTextNegative() {
        SingleSelect select = MockFactory.singleSelect().withSelectedText("foo").build();
        assertThat(select).hasSelectionWithText("bar");
    }

    /* selection value */

    @Test
    public void selectionHasValuePositive() {
        SingleSelect select = MockFactory.singleSelect().withSelectedValue("v1").build();
        assertThat(select).hasSelectionWithValue("v1");
    }

    @Test(expected = AssertionError.class)
    public void selectionHasValueNegative() {
        SingleSelect select = MockFactory.singleSelect().withSelectedValue("v1").build();
        assertThat(select).hasSelectionWithValue("v2");
    }

    /* selection index */

    @Test
    public void selectionHasIndexPositive() {
        SingleSelect select = MockFactory.singleSelect().withSelectedIndex(1).build();
        assertThat(select).hasSelectionWithIndex(1);
    }

    @Test(expected = AssertionError.class)
    public void selectionHasIndexNegative() {
        SingleSelect select = MockFactory.singleSelect().withSelectedIndex(1).build();
        assertThat(select).hasSelectionWithIndex(2);
    }

    /* option texts */

    @Test
    public void optionsHaveTextsPositive() {
        SingleSelect select = MockFactory.singleSelect().withTexts("foo", "bar").build();
        assertThat(select).hasOptionsWithTexts("foo", "bar");
    }

    @Test(expected = AssertionError.class)
    public void optionsHaveTextsNegative_WrongOrder() {
        SingleSelect select = MockFactory.singleSelect().withTexts("foo", "bar").build();
        assertThat(select).hasOptionsWithTexts("bar", "foo");
    }

    @Test(expected = AssertionError.class)
    public void optionsHaveTextsNegative_WrongText() {
        SingleSelect select = MockFactory.singleSelect().withTexts("foo").build();
        assertThat(select).hasOptionsWithTexts("xur");
    }

    @Test
    public void optionsHaveTextsInAnyOrderPositive_Ordered1() {
        SingleSelect select = MockFactory.singleSelect().withTexts("foo", "bar").build();
        assertThat(select).hasOptionsWithTextsInAnyOrder("foo", "bar");
    }

    @Test
    public void optionsHaveTextsInAnyOrderPositive_Ordered2() {
        SingleSelect select = MockFactory.singleSelect().withTexts("foo", "bar").build();
        assertThat(select).hasOptionsWithTextsInAnyOrder("bar", "foo");
    }

    @Test(expected = AssertionError.class)
    public void optionsHaveTextsInAnyOrderNegative() {
        SingleSelect select = MockFactory.singleSelect().withTexts("foo").build();
        assertThat(select).hasOptionsWithTextsInAnyOrder("xur");
    }

    /* option values */

    @Test
    public void optionsHaveValuesPositive() {
        SingleSelect select = MockFactory.singleSelect().withValues("foo", "bar").build();
        assertThat(select).hasOptionsWithValues("foo", "bar");
    }

    @Test(expected = AssertionError.class)
    public void optionsHaveValuesNegative_WrongOrder() {
        SingleSelect select = MockFactory.singleSelect().withValues("foo", "bar").build();
        assertThat(select).hasOptionsWithValues("bar", "foo");
    }

    @Test(expected = AssertionError.class)
    public void optionsHaveValuesNegative_WrongText() {
        SingleSelect select = MockFactory.singleSelect().withValues("foo").build();
        assertThat(select).hasOptionsWithValues("xur");
    }

    @Test
    public void optionsHaveValuesInAnyOrderPositive_Ordered1() {
        SingleSelect select = MockFactory.singleSelect().withValues("foo", "bar").build();
        assertThat(select).hasOptionsWithValuesInAnyOrder("foo", "bar");
    }

    @Test
    public void optionsHaveValuesInAnyOrderPositive_Ordered2() {
        SingleSelect select = MockFactory.singleSelect().withValues("foo", "bar").build();
        assertThat(select).hasOptionsWithValuesInAnyOrder("bar", "foo");
    }

    @Test(expected = AssertionError.class)
    public void optionsHaveValuesInAnyOrderNegative_WrongText() {
        SingleSelect select = MockFactory.singleSelect().withValues("foo").build();
        assertThat(select).hasOptionsWithValuesInAnyOrder("xur");
    }

    /* number of options */

    @Test
    public void hasOptionsPositive() {
        SingleSelect select = MockFactory.singleSelect().withNumberOfOptions(5).build();
        assertThat(select).hasOptions();
    }

    @Test(expected = AssertionError.class)
    public void hasOptionsNegative() {
        SingleSelect select = MockFactory.singleSelect().withNumberOfOptions(0).build();
        assertThat(select).hasOptions();
    }

    @Test
    public void hasOptionsWithNumberPositive() {
        SingleSelect select = MockFactory.singleSelect().withNumberOfOptions(5).build();
        assertThat(select).hasOptions(5);
    }

    @Test(expected = AssertionError.class)
    public void hasOptionsWithNumberNegative() {
        SingleSelect select = MockFactory.singleSelect().withNumberOfOptions(5).build();
        assertThat(select).hasOptions(1);
    }

    @Test
    public void hasNoOptionsPositive() {
        SingleSelect select = MockFactory.singleSelect().withNumberOfOptions(0).build();
        assertThat(select).hasNoOptions();
    }

    @Test(expected = AssertionError.class)
    public void hasNoOptionsNegative() {
        SingleSelect select = MockFactory.singleSelect().withNumberOfOptions(5).build();
        assertThat(select).hasNoOptions();
    }

}
