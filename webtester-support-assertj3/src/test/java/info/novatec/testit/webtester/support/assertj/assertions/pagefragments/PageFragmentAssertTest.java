package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;

import org.junit.Test;

import utils.MockFactory;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class PageFragmentAssertTest {

    /* has tag */

    @Test
    public void hasTagPositive() {
        assertThat(pageFragmentWithTag("div")).hasTag("div");
    }

    @Test(expected = AssertionError.class)
    public void hasTagNegative() {
        assertThat(pageFragmentWithTag("span")).hasTag("div");
    }

    @Test
    public void hasNotTagPositive() {
        assertThat(pageFragmentWithTag("span")).hasNotTag("div");
    }

    @Test(expected = AssertionError.class)
    public void hasNotTagNegative() {
        assertThat(pageFragmentWithTag("div")).hasNotTag("div");
    }

    /* has visible text */

    @Test
    public void hasVisibleTextPositive() {
        assertThat(pageFragmentWithVisibleText("foo")).hasVisibleText("foo");
    }

    @Test(expected = AssertionError.class)
    public void hasVisibleTextNegative() {
        assertThat(pageFragmentWithVisibleText("bar")).hasVisibleText("foo");
    }

    @Test
    public void hasNotVisibleTextPositive() {
        assertThat(pageFragmentWithVisibleText("bar")).hasNotVisibleText("foo");
    }

    @Test(expected = AssertionError.class)
    public void hasNotVisibleTextNegative() {
        assertThat(pageFragmentWithVisibleText("foo")).hasNotVisibleText("foo");
    }

    /* has visible text containing */

    @Test
    public void hasVisibleTextContainingPositive() {
        assertThat(pageFragmentWithVisibleText("foo bar foo")).hasVisibleTextContaining("foo");
    }

    @Test(expected = AssertionError.class)
    public void hasVisibleTextContainingNegative() {
        assertThat(pageFragmentWithVisibleText("bar bar")).hasVisibleTextContaining("foo");
    }

    /* has attribute */

    @Test
    public void hasAttributePositive() {
        assertThat(pageFragmentWithAttribute("foo")).hasAttribute("foo");
    }

    @Test(expected = AssertionError.class)
    public void hasAttributeNegative() {
        assertThat(pageFragmentWithoutAttributes()).hasAttribute("foo");
    }

    @Test
    public void hasNotAttributePositive() {
        assertThat(pageFragmentWithoutAttributes()).hasNotAttribute("bar");
    }

    @Test(expected = AssertionError.class)
    public void hasNotAttributeNegative() {
        assertThat(pageFragmentWithAttribute("bar")).hasNotAttribute("bar");
    }

    /* has attribute with value */

    @Test
    public void hasAttributeWithValuePositive() {
        assertThat(pageFragmentWithAttributeAndValue("foo", "val")).hasAttributeValue("foo", "val");
    }

    @Test(expected = AssertionError.class)
    public void hasAttributeWithValueNegative_OtherValue() {
        assertThat(pageFragmentWithAttributeAndValue("foo", "other")).hasAttributeValue("foo", "val");
    }

    @Test(expected = AssertionError.class)
    public void hasAttributeWithValueNegative_NoAttribute() {
        assertThat(pageFragmentWithoutAttributes()).hasAttributeValue("foo", "val");
    }

    /* visibility */

    @Test
    public void isVisiblePositive() {
        assertThat(visiblePageFragment()).isVisible();
    }

    @Test(expected = AssertionError.class)
    public void isVisibleNegative() {
        assertThat(invisiblePageFragment()).isVisible();
    }

    @Test
    public void isInvisiblePositive() {
        assertThat(invisiblePageFragment()).isInvisible();
    }

    @Test(expected = AssertionError.class)
    public void isInvisibleNegative() {
        assertThat(visiblePageFragment()).isInvisible();
    }

    /* presence */

    @Test
    public void isPresentPositive() {
        assertThat(presentPageFragment()).isPresent();
    }

    @Test(expected = AssertionError.class)
    public void isPresentNegative() {
        assertThat(nonPresentFragment()).isPresent();
    }

    @Test
    public void isNotPresentPositive() {
        assertThat(nonPresentFragment()).isNotPresent();
    }

    @Test(expected = AssertionError.class)
    public void isNotPresentNegative() {
        assertThat(presentPageFragment()).isNotPresent();
    }

    /* enabled */

    @Test
    public void isEnabledPositive() {
        assertThat(enabledPageFragment()).isEnabled();
    }

    @Test(expected = AssertionError.class)
    public void isEnabledNegative() {
        assertThat(disabledPageFragment()).isEnabled();
    }

    /* disabled */

    @Test
    public void isDisabledPositive() {
        assertThat(disabledPageFragment()).isDisabled();
    }

    @Test(expected = AssertionError.class)
    public void isDisabledNegative() {
        assertThat(enabledPageFragment()).isDisabled();
    }

    /* utilities */

    private PageFragment pageFragmentWithTag(String tagName) {
        return MockFactory.fragment().withTagName(tagName).build();
    }

    private PageFragment pageFragmentWithVisibleText(String text) {
        return MockFactory.fragment().withVisibleText(text).build();
    }

    private PageFragment pageFragmentWithAttribute(String attributeName) {
        return MockFactory.fragment().withAttribute(attributeName).build();
    }

    private PageFragment pageFragmentWithAttributeAndValue(String attributeName, String value) {
        return MockFactory.fragment().withAttribute(attributeName, value).build();
    }

    private PageFragment pageFragmentWithoutAttributes() {
        return MockFactory.fragment().build();
    }

    private PageFragment visiblePageFragment() {
        return MockFactory.fragment().visible().build();
    }

    private PageFragment invisiblePageFragment() {
        return MockFactory.fragment().invisible().build();
    }

    private PageFragment nonPresentFragment() {
        return MockFactory.fragment().notPresent().build();
    }

    private PageFragment presentPageFragment() {
        return MockFactory.fragment().present().build();
    }

    private PageFragment enabledPageFragment() {
        return MockFactory.fragment().enabled().build();
    }

    private PageFragment disabledPageFragment() {
        return MockFactory.fragment().disabled().build();
    }

}
