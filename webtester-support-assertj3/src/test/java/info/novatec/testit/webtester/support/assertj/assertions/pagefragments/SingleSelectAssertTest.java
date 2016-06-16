package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import utils.MockFactory;

import info.novatec.testit.webtester.pagefragments.SingleSelect;


@RunWith(Enclosed.class)
public class SingleSelectAssertTest {

    public static class HasSelectionWithText {

        @Test
        public void havingSelectionWithTextPasses() {
            SingleSelect select = MockFactory.singleSelect().withSelectedText("foo").build();
            assertThat(select).hasSelectionWithText("foo");
        }

        @Test(expected = AssertionError.class)
        public void notHavingSelectionWithTextFails() {
            SingleSelect select = MockFactory.singleSelect().withSelectedText("foo").build();
            assertThat(select).hasSelectionWithText("bar");
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            SingleSelect select = MockFactory.singleSelect().withSelectedText("foo").build();
            SingleSelectAssert original = assertThat(select);
            SingleSelectAssert returned = original.hasSelectionWithText("foo");
            assertThat(returned).isSameAs(original);
        }

    }

    public static class HasSelectionWithValue {

        @Test
        public void havingSelectionWithValuePasses() {
            SingleSelect select = MockFactory.singleSelect().withSelectedValue("v1").build();
            assertThat(select).hasSelectionWithValue("v1");
        }

        @Test(expected = AssertionError.class)
        public void notHavingSelectionWithValueFails() {
            SingleSelect select = MockFactory.singleSelect().withSelectedValue("v1").build();
            assertThat(select).hasSelectionWithValue("v2");
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            SingleSelect select = MockFactory.singleSelect().withSelectedValue("v1").build();
            SingleSelectAssert original = assertThat(select);
            SingleSelectAssert returned = original.hasSelectionWithValue("v1");
            assertThat(returned).isSameAs(original);
        }

    }

    public static class HasSelectionWithIndex {

        @Test
        public void havingSelectionWithIndexPasses() {
            SingleSelect select = MockFactory.singleSelect().withSelectedIndex(1).build();
            assertThat(select).hasSelectionWithIndex(1);
        }

        @Test(expected = AssertionError.class)
        public void notHavingSelectionWithIndexFails() {
            SingleSelect select = MockFactory.singleSelect().withSelectedIndex(1).build();
            assertThat(select).hasSelectionWithIndex(2);
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            SingleSelect select = MockFactory.singleSelect().withSelectedIndex(1).build();
            SingleSelectAssert original = assertThat(select);
            SingleSelectAssert returned = original.hasSelectionWithIndex(1);
            assertThat(returned).isSameAs(original);
        }

    }

}
