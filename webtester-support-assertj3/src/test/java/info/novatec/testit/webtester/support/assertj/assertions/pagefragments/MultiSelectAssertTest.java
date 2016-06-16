package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import utils.MockFactory;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


@RunWith(Enclosed.class)
public class MultiSelectAssertTest {

    public static class HasSelectionWithTexts {

        @Test
        public void havingSelectedTextsPasses() {
            MultiSelect select = MockFactory.multiSelect().withSelectedTexts("foo", "bar").build();
            assertThat(select).hasSelectionWithTexts("foo", "bar");
        }

        @Test(expected = AssertionError.class)
        public void havingSelectedTextsButInWrongOrderFails() {
            MultiSelect select = MockFactory.multiSelect().withSelectedTexts("foo", "bar").build();
            assertThat(select).hasSelectionWithTexts("bar", "foo");
        }

        @Test(expected = AssertionError.class)
        public void notHavingAllSelectedTextsFails() {
            MultiSelect select = MockFactory.multiSelect().withSelectedTexts("foo").build();
            assertThat(select).hasSelectionWithTexts("bar");
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            MultiSelect select = MockFactory.multiSelect().withSelectedTexts("foo", "bar").build();
            MultiSelectAssert original = assertThat(select);
            MultiSelectAssert returned = original.hasSelectionWithTexts("foo", "bar");
            assertThat(returned).isSameAs(original);
        }

    }

    public static class HasSelectionWithValues {

        @Test
        public void havingSelectedValuesPasses() {
            MultiSelect select = MockFactory.multiSelect().withSelectedValues("v1", "v2").build();
            assertThat(select).hasSelectionWithValues("v1", "v2");
        }

        @Test(expected = AssertionError.class)
        public void havingSelectedValuesButInWrongOrderFails() {
            MultiSelect select = MockFactory.multiSelect().withSelectedValues("v1", "v2").build();
            assertThat(select).hasSelectionWithValues("v2", "v1");
        }

        @Test(expected = AssertionError.class)
        public void notHavingAllSelectedValuesFails() {
            MultiSelect select = MockFactory.multiSelect().withSelectedValues("v1").build();
            assertThat(select).hasSelectionWithValues("v2");
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            MultiSelect select = MockFactory.multiSelect().withSelectedValues("v1", "v2").build();
            MultiSelectAssert original = assertThat(select);
            MultiSelectAssert returned = original.hasSelectionWithValues("v1", "v2");
            assertThat(returned).isSameAs(original);
        }

    }

    public static class HasSelectionWithIndices {

        @Test
        public void havingSelectedIndicesPasses() {
            MultiSelect select = MockFactory.multiSelect().withSelectedIndices(1, 2).build();
            assertThat(select).hasSelectionWithIndices(1, 2);
        }

        @Test(expected = AssertionError.class)
        public void havingSelectedIndicesButInWrongOrderFails() {
            MultiSelect select = MockFactory.multiSelect().withSelectedIndices(1, 2).build();
            assertThat(select).hasSelectionWithIndices(2, 1);
        }

        @Test(expected = AssertionError.class)
        public void notHavingAllSelectedIndicesFails() {
            MultiSelect select = MockFactory.multiSelect().withSelectedIndices(1).build();
            assertThat(select).hasSelectionWithIndices(2);
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            MultiSelect select = MockFactory.multiSelect().withSelectedIndices(1, 2).build();
            MultiSelectAssert original = assertThat(select);
            MultiSelectAssert returned = original.hasSelectionWithIndices(1, 2);
            assertThat(returned).isSameAs(original);
        }

    }

    public static class HasSelectedOptions {

        @Test
        public void havingSelectedOptionsPasses() {
            MultiSelect select = MockFactory.multiSelect().withNumberOfSelectedOptions(5).build();
            assertThat(select).hasSelectedOptions();
        }

        @Test(expected = AssertionError.class)
        public void notHavingAnySelectedOptionsFails() {
            MultiSelect select = MockFactory.multiSelect().withNumberOfSelectedOptions(0).build();
            assertThat(select).hasSelectedOptions();
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            MultiSelect select = MockFactory.multiSelect().withNumberOfSelectedOptions(5).build();
            MultiSelectAssert original = assertThat(select);
            MultiSelectAssert returned = original.hasSelectedOptions();
            assertThat(returned).isSameAs(original);
        }

    }

    public static class HasExactNumberOfSelectedOptions {

        @Test
        public void havingExactNumberOfSelectedOptionsPasses() {
            MultiSelect select = MockFactory.multiSelect().withNumberOfSelectedOptions(5).build();
            assertThat(select).hasSelectedOptions(5);
        }

        @Test(expected = AssertionError.class)
        public void notHavingExactNumberOfSelectedOptionsFails() {
            MultiSelect select = MockFactory.multiSelect().withNumberOfSelectedOptions(5).build();
            assertThat(select).hasSelectedOptions(1);
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            MultiSelect select = MockFactory.multiSelect().withNumberOfSelectedOptions(5).build();
            MultiSelectAssert original = assertThat(select);
            MultiSelectAssert returned = original.hasSelectedOptions(5);
            assertThat(returned).isSameAs(original);
        }

    }

    public static class HasNoSelectedOptions {

        @Test
        public void havingNoSelectedOptionsPasses() {
            MultiSelect select = MockFactory.multiSelect().withNumberOfSelectedOptions(0).build();
            assertThat(select).hasNoSelectedOptions();
        }

        @Test(expected = AssertionError.class)
        public void havingSelectedOptionsFails() {
            MultiSelect select = MockFactory.multiSelect().withNumberOfSelectedOptions(5).build();
            assertThat(select).hasNoSelectedOptions();
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            MultiSelect select = MockFactory.multiSelect().withNumberOfSelectedOptions(0).build();
            MultiSelectAssert original = assertThat(select);
            MultiSelectAssert returned = original.hasNoSelectedOptions();
            assertThat(returned).isSameAs(original);
        }

    }

}
