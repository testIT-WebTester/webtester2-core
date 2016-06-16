package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import utils.MockFactory;

import info.novatec.testit.webtester.pagefragments.PageFragment;


@RunWith(Enclosed.class)
public class PageFragmentAssertTest {

    public static class HasTag {

        @Test
        public void havingTagPasses() {
            assertThat(pageFragmentWithTag("div")).hasTag("div");
        }

        @Test(expected = AssertionError.class)
        public void notHavingTagFails() {
            assertThat(pageFragmentWithTag("span")).hasTag("div");
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            PageFragmentAssert original = assertThat(pageFragmentWithTag("div"));
            PageFragmentAssert returned = original.hasTag("div");
            assertThat(returned).isSameAs(original);
        }

    }

    public static class HasNotTag {

        @Test
        public void notHavingTagPasses() {
            assertThat(pageFragmentWithTag("span")).hasNotTag("div");
        }

        @Test(expected = AssertionError.class)
        public void havingTagFails() {
            assertThat(pageFragmentWithTag("div")).hasNotTag("div");
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            PageFragmentAssert original = assertThat(pageFragmentWithTag("span"));
            PageFragmentAssert returned = original.hasNotTag("div");
            assertThat(returned).isSameAs(original);
        }

    }

    public static class HasVisibleText {

        @Test
        public void havingVisibleTextPasses() {
            assertThat(pageFragmentWithVisibleText("foo")).hasVisibleText("foo");
        }

        @Test(expected = AssertionError.class)
        public void notHavingVisibleTextFails() {
            assertThat(pageFragmentWithVisibleText("bar")).hasVisibleText("foo");
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            PageFragmentAssert original = assertThat(pageFragmentWithVisibleText("foo"));
            PageFragmentAssert returned = original.hasVisibleText("foo");
            assertThat(returned).isSameAs(original);
        }

    }

    public static class HasNotVisibleText {

        @Test
        public void notHavingVisibleTextPasses() {
            assertThat(pageFragmentWithVisibleText("bar")).hasNotVisibleText("foo");
        }

        @Test(expected = AssertionError.class)
        public void havingVisibleTextFails() {
            assertThat(pageFragmentWithVisibleText("foo")).hasNotVisibleText("foo");
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            PageFragmentAssert original = assertThat(pageFragmentWithVisibleText("bar"));
            PageFragmentAssert returned = original.hasNotVisibleText("foo");
            assertThat(returned).isSameAs(original);
        }

    }

    public static class HasVisibleTextContaining {

        @Test
        public void havingVisibleTextContainingPasses() {
            assertThat(pageFragmentWithVisibleText("foo bar foo")).hasVisibleTextContaining("foo");
        }

        @Test(expected = AssertionError.class)
        public void notHavingVisibleTextContainingFails() {
            assertThat(pageFragmentWithVisibleText("bar bar")).hasVisibleTextContaining("foo");
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            PageFragmentAssert original = assertThat(pageFragmentWithVisibleText("foo bar foo"));
            PageFragmentAssert returned = original.hasVisibleTextContaining("foo");
            assertThat(returned).isSameAs(original);
        }

    }

    public static class HasAttribute {

        @Test
        public void havingAttributePasses() {
            assertThat(pageFragmentWithAttribute("foo")).hasAttribute("foo");
        }

        @Test(expected = AssertionError.class)
        public void notHavingAttributeFails() {
            assertThat(pageFragmentWithoutAttributes()).hasAttribute("foo");
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            PageFragmentAssert original = assertThat(pageFragmentWithAttribute("foo"));
            PageFragmentAssert returned = original.hasAttribute("foo");
            assertThat(returned).isSameAs(original);
        }

    }

    public static class HasNotAttribute {

        @Test
        public void notHavingAttributePasses() {
            assertThat(pageFragmentWithoutAttributes()).hasNotAttribute("bar");
        }

        @Test(expected = AssertionError.class)
        public void havingAttributeFails() {
            assertThat(pageFragmentWithAttribute("bar")).hasNotAttribute("bar");
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            PageFragmentAssert original = assertThat(pageFragmentWithoutAttributes());
            PageFragmentAssert returned = original.hasNotAttribute("bar");
            assertThat(returned).isSameAs(original);
        }

    }

    public static class HasAttributeValue {

        @Test
        public void havingAttributeWithValuePasses() {
            assertThat(pageFragmentWithAttributeAndValue("foo", "val")).hasAttributeValue("foo", "val");
        }

        @Test(expected = AssertionError.class)
        public void havingAttributeWithDifferentValueFails() {
            assertThat(pageFragmentWithAttributeAndValue("foo", "other")).hasAttributeValue("foo", "val");
        }

        @Test(expected = AssertionError.class)
        public void notHavingAttributeFails() {
            assertThat(pageFragmentWithoutAttributes()).hasAttributeValue("foo", "val");
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            PageFragmentAssert original = assertThat(pageFragmentWithAttributeAndValue("foo", "val"));
            PageFragmentAssert returned = original.hasAttributeValue("foo", "val");
            assertThat(returned).isSameAs(original);
        }

    }

    public static class IsVisible {

        @Test
        public void beingVisiblePasses() {
            assertThat(visiblePageFragment()).isVisible();
        }

        @Test(expected = AssertionError.class)
        public void notBeingVisibleFails() {
            assertThat(invisiblePageFragment()).isVisible();
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            PageFragmentAssert original = assertThat(visiblePageFragment());
            PageFragmentAssert returned = original.isVisible();
            assertThat(returned).isSameAs(original);
        }

    }

    public static class IsInvisible {

        @Test
        public void beingInvisiblePasses() {
            assertThat(invisiblePageFragment()).isInvisible();
        }

        @Test(expected = AssertionError.class)
        public void notBeingInvisibleFails() {
            assertThat(visiblePageFragment()).isInvisible();
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            PageFragmentAssert original = assertThat(invisiblePageFragment());
            PageFragmentAssert returned = original.isInvisible();
            assertThat(returned).isSameAs(original);
        }

    }

    /* presence */

    public static class IsPresent {

        @Test
        public void beingPresentPasses() {
            assertThat(presentPageFragment()).isPresent();
        }

        @Test(expected = AssertionError.class)
        public void notBeingPresentFails() {
            assertThat(nonPresentFragment()).isPresent();
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            PageFragmentAssert original = assertThat(presentPageFragment());
            PageFragmentAssert returned = original.isPresent();
            assertThat(returned).isSameAs(original);
        }

    }

    public static class IsNotPresent {

        @Test
        public void notBeingPresentPasses() {
            assertThat(nonPresentFragment()).isNotPresent();
        }

        @Test(expected = AssertionError.class)
        public void beingPresentFails() {
            assertThat(presentPageFragment()).isNotPresent();
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            PageFragmentAssert original = assertThat(nonPresentFragment());
            PageFragmentAssert returned = original.isNotPresent();
            assertThat(returned).isSameAs(original);
        }

    }

    public static class IsEnabled {

        @Test
        public void beingEnabledPasses() {
            assertThat(enabledPageFragment()).isEnabled();
        }

        @Test(expected = AssertionError.class)
        public void notBeingEnabledFails() {
            assertThat(disabledPageFragment()).isEnabled();
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            PageFragmentAssert original = assertThat(enabledPageFragment());
            PageFragmentAssert returned = original.isEnabled();
            assertThat(returned).isSameAs(original);
        }

    }

    public static class IsDisabled {

        @Test
        public void beingDisabledPasses() {
            assertThat(disabledPageFragment()).isDisabled();
        }

        @Test(expected = AssertionError.class)
        public void notBeingDisabledFails() {
            assertThat(enabledPageFragment()).isDisabled();
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            PageFragmentAssert original = assertThat(disabledPageFragment());
            PageFragmentAssert returned = original.isDisabled();
            assertThat(returned).isSameAs(original);
        }

    }

    /* utilities */

    private static PageFragment pageFragmentWithTag(String tagName) {
        return MockFactory.fragment().withTagName(tagName).build();
    }

    private static PageFragment pageFragmentWithVisibleText(String text) {
        return MockFactory.fragment().withVisibleText(text).build();
    }

    private static PageFragment pageFragmentWithAttribute(String attributeName) {
        return MockFactory.fragment().withAttribute(attributeName).build();
    }

    private static PageFragment pageFragmentWithAttributeAndValue(String attributeName, String value) {
        return MockFactory.fragment().withAttribute(attributeName, value).build();
    }

    private static PageFragment pageFragmentWithoutAttributes() {
        return MockFactory.fragment().build();
    }

    private static PageFragment visiblePageFragment() {
        return MockFactory.fragment().visible().build();
    }

    private static PageFragment invisiblePageFragment() {
        return MockFactory.fragment().invisible().build();
    }

    private static PageFragment nonPresentFragment() {
        return MockFactory.fragment().notPresent().build();
    }

    private static PageFragment presentPageFragment() {
        return MockFactory.fragment().present().build();
    }

    private static PageFragment enabledPageFragment() {
        return MockFactory.fragment().enabled().build();
    }

    private static PageFragment disabledPageFragment() {
        return MockFactory.fragment().disabled().build();
    }

}
