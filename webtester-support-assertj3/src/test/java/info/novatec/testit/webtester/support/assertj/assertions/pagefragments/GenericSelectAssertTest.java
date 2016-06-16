package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Arrays;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import info.novatec.testit.webtester.pagefragments.GenericSelect;


@RunWith(Enclosed.class)
public class GenericSelectAssertTest {

    public static class HasOptionsWithTexts {

        @Test
        public void havingAllOptionsPasses() {
            assertThat(genericSelectWithTexts("foo", "bar")).hasOptionsWithTexts("foo", "bar");
        }

        @Test(expected = AssertionError.class)
        public void havingOptionsButInWrongOrderFails() {
            assertThat(genericSelectWithTexts("foo", "bar")).hasOptionsWithTexts("bar", "foo");
        }

        @Test(expected = AssertionError.class)
        public void notHavingAllOptionsFails() {
            assertThat(genericSelectWithTexts("foo")).hasOptionsWithTexts("xur");
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            GenericSelectAssert original = assertThat(genericSelectWithTexts("foo", "bar"));
            GenericSelectAssert returned = original.hasOptionsWithTexts("foo", "bar");
            assertThat(returned).isSameAs(original);
        }

    }

    public static class HasOptionsWithTextsInAnyOrder {

        @Test
        public void havingOptionsInAnyOrderPasses() {
            GenericSelect select = genericSelectWithTexts("foo", "bar");
            assertThat(select).hasOptionsWithTextsInAnyOrder("foo", "bar");
            assertThat(select).hasOptionsWithTextsInAnyOrder("bar", "foo");
        }

        @Test(expected = AssertionError.class)
        public void notHavingAllOptionsFails() {
            assertThat(genericSelectWithTexts("foo")).hasOptionsWithTextsInAnyOrder("xur");
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            GenericSelectAssert original = assertThat(genericSelectWithTexts("foo", "bar"));
            GenericSelectAssert returned = original.hasOptionsWithTextsInAnyOrder("foo", "bar");
            assertThat(returned).isSameAs(original);
        }

    }

    public static class HasOptionsWithValues {

        @Test
        public void havingAllOptionsPasses() {
            assertThat(genericSelectWithValues("foo", "bar")).hasOptionsWithValues("foo", "bar");
        }

        @Test(expected = AssertionError.class)
        public void havingOptionsButInWrongOrderFails() {
            assertThat(genericSelectWithValues("foo", "bar")).hasOptionsWithValues("bar", "foo");
        }

        @Test(expected = AssertionError.class)
        public void notHavingAllOptionsFails() {
            assertThat(genericSelectWithValues("foo")).hasOptionsWithValues("xur");
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            GenericSelectAssert original = assertThat(genericSelectWithValues("foo", "bar"));
            GenericSelectAssert returned = original.hasOptionsWithValues("foo", "bar");
            assertThat(returned).isSameAs(original);
        }

    }

    public static class HasOptionsWithValuesInAnyOrder {

        @Test
        public void havingOptionsInAnyOrderPasses() {
            GenericSelect select = genericSelectWithValues("foo", "bar");
            assertThat(select).hasOptionsWithValuesInAnyOrder("foo", "bar");
            assertThat(select).hasOptionsWithValuesInAnyOrder("bar", "foo");
        }

        @Test(expected = AssertionError.class)
        public void notHavingAllOptionsFails() {
            assertThat(genericSelectWithValues("foo")).hasOptionsWithValuesInAnyOrder("xur");
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            GenericSelectAssert original = assertThat(genericSelectWithValues("foo", "bar"));
            GenericSelectAssert returned = original.hasOptionsWithValuesInAnyOrder("foo", "bar");
            assertThat(returned).isSameAs(original);
        }

    }

    public static class HasOptions {

        @Test
        public void havingOptionsPasses() {
            assertThat(genericSelectWithNumberOfOptions(5)).hasOptions();
        }

        @Test(expected = AssertionError.class)
        public void notHavingAnyOptionsFails() {
            assertThat(genericSelectWithNumberOfOptions(0)).hasOptions();
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            GenericSelectAssert original = assertThat(genericSelectWithNumberOfOptions(5));
            GenericSelectAssert returned = original.hasOptions();
            assertThat(returned).isSameAs(original);
        }

    }

    public static class HasExactNumberOfOptions {

        @Test
        public void havingExactNumberOfOptionsPasses() {
            assertThat(genericSelectWithNumberOfOptions(5)).hasOptions(5);
        }

        @Test(expected = AssertionError.class)
        public void notHavingExactNumberOfOptionsFails() {
            assertThat(genericSelectWithNumberOfOptions(5)).hasOptions(1);
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            GenericSelectAssert original = assertThat(genericSelectWithNumberOfOptions(5));
            GenericSelectAssert returned = original.hasOptions(5);
            assertThat(returned).isSameAs(original);
        }

    }

    public static class HasNoOptions {

        @Test
        public void notHavingAnyOptionsPasses() {
            assertThat(genericSelectWithNumberOfOptions(0)).hasNoOptions();
        }

        @Test(expected = AssertionError.class)
        public void havingOptionsFails() {
            assertThat(genericSelectWithNumberOfOptions(5)).hasNoOptions();
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            GenericSelectAssert original = assertThat(genericSelectWithNumberOfOptions(0));
            GenericSelectAssert returned = original.hasNoOptions();
            assertThat(returned).isSameAs(original);
        }

    }

    private static GenericSelect genericSelectWithTexts(String... texts) {
        GenericSelect select = mock(GenericSelect.class);
        doReturn(Arrays.asList(texts)).when(select).getOptionTexts();
        return select;
    }

    private static GenericSelect genericSelectWithValues(String... values) {
        GenericSelect select = mock(GenericSelect.class);
        doReturn(Arrays.asList(values)).when(select).getOptionValues();
        return select;
    }

    private static GenericSelect genericSelectWithNumberOfOptions(int numberOfOptions) {
        GenericSelect select = mock(GenericSelect.class);
        doReturn(numberOfOptions).when(select).getOptionCount();
        return select;
    }

}
