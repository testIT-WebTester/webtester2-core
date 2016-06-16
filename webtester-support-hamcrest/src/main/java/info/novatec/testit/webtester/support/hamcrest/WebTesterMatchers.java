package info.novatec.testit.webtester.support.hamcrest;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import info.novatec.testit.webtester.pagefragments.Button;
import info.novatec.testit.webtester.pagefragments.GenericSelect;
import info.novatec.testit.webtester.pagefragments.GenericTextField;
import info.novatec.testit.webtester.pagefragments.MultiSelect;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.SingleSelect;
import info.novatec.testit.webtester.pagefragments.traits.Selectable;
import info.novatec.testit.webtester.support.hamcrest.matchers.HasMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.AttributeMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.AttributeValueMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.ButtonLabelMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.DisabledMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.EnabledMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.InvisibleMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.NoOptionsMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.NoSelectedOptionsMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.NumberOfOptionsMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.NumberOfSelectedOptionsMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.OptionsTextsMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.OptionsMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.OptionsValuesMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.PresentMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.SelectedMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.SelectedOptionsMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.SelectionIndexMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.SelectionIndicesMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.SelectionTextMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.SelectionTextsMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.SelectionValueMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.SelectionValuesMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.TagMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.TextContainingMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.TextMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.VisibleMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.VisibleTextContainingMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.VisibleTextMatcher;


public final class WebTesterMatchers extends Matchers {

    // TODO: Document

    public static <T> Matcher<T> has(Matcher<T> matcher) {
        return new HasMatcher<>(matcher);
    }

    public static <T extends PageFragment> Matcher<T> present() {
        return new PresentMatcher<>();
    }

    public static <T extends PageFragment> Matcher<T> visible() {
        return new VisibleMatcher<>();
    }

    public static <T extends PageFragment> Matcher<T> enabled() {
        return new EnabledMatcher<>();
    }

    public static <T extends PageFragment> Matcher<T> disabled() {
        return new DisabledMatcher<>();
    }

    public static <T extends PageFragment> Matcher<T> invisible() {
        return new InvisibleMatcher<>();
    }

    public static <T extends PageFragment> Matcher<T> tag(String tagName) {
        return new TagMatcher<>(tagName);
    }

    public static <T extends PageFragment> Matcher<T> visibleText(String text) {
        return new VisibleTextMatcher<>(text);
    }

    public static <T extends PageFragment> Matcher<T> visibleTextContaining(String textFragment) {
        return new VisibleTextContainingMatcher<>(textFragment);
    }

    public static <T extends PageFragment> Matcher<T> attribute(String attributeName) {
        return new AttributeMatcher<>(attributeName);
    }

    public static <T extends PageFragment> Matcher<T> attributeValue(String attributeName, String value) {
        return new AttributeValueMatcher<>(attributeName, value);
    }

    public static <T extends Selectable<?>> Matcher<T> selected() {
        return new SelectedMatcher<>();
    }

    public static <T extends GenericTextField<?>> Matcher<T> text(String text) {
        return new TextMatcher<>(text);
    }

    public static <T extends GenericTextField<?>> Matcher<T> textContaining(String textFragment) {
        return new TextContainingMatcher<>(textFragment);
    }

    public static <T extends Button> Matcher<T> label(String label) {
        return new ButtonLabelMatcher<>(label);
    }

    public static <T extends MultiSelect> Matcher<T> selectionWithTexts(String... texts) {
        return selectionWithTexts(Arrays.asList(texts));
    }

    public static <T extends MultiSelect> Matcher<T> selectionWithTexts(List<String> texts) {
        return new SelectionTextsMatcher<>(texts);
    }

    public static <T extends MultiSelect> Matcher<T> selectionWithValues(String... values) {
        return selectionWithValues(Arrays.asList(values));
    }

    public static <T extends MultiSelect> Matcher<T> selectionWithValues(List<String> values) {
        return new SelectionValuesMatcher<>(values);
    }

    public static <T extends MultiSelect> Matcher<T> selectionWithIndices(Integer... indices) {
        return selectionWithIndices(Arrays.asList(indices));
    }

    public static <T extends MultiSelect> Matcher<T> selectionWithIndices(List<Integer> indices) {
        return new SelectionIndicesMatcher<>(indices);
    }

    public static <T extends MultiSelect> Matcher<T> selectedOptions() {
        return new SelectedOptionsMatcher<>();
    }

    public static <T extends MultiSelect> Matcher<T> selectedOptions(Integer count) {
        return new NumberOfSelectedOptionsMatcher<>(count);
    }

    public static <T extends MultiSelect> Matcher<T> noSelectedOptions() {
        return new NoSelectedOptionsMatcher<>();
    }

    public static <T extends SingleSelect> Matcher<T> selectionWithText(String text) {
        return new SelectionTextMatcher<>(text);
    }

    public static <T extends SingleSelect> Matcher<T> selectionWithValue(String value) {
        return new SelectionValueMatcher<>(value);
    }

    public static <T extends SingleSelect> Matcher<T> selectionWithIndex(Integer index) {
        return new SelectionIndexMatcher<>(index);
    }

    public static <T extends GenericSelect> Matcher<T> options() {
        return new OptionsMatcher<>();
    }

    public static <T extends GenericSelect> Matcher<T> options(Integer count) {
        return new NumberOfOptionsMatcher<>(count);
    }

    public static <T extends GenericSelect> Matcher<T> noOptions() {
        return new NoOptionsMatcher<>();
    }

    public static <T extends GenericSelect> Matcher<T> optionsWithTexts(String... texts) {
        return optionsWithTexts(Arrays.asList(texts));
    }

    public static <T extends GenericSelect> Matcher<T> optionsWithTexts(List<String> texts) {
        return new OptionsTextsMatcher<>(texts);
    }

    public static <T extends GenericSelect> Matcher<T> optionsWithValues(String... values) {
        return optionsWithValues(Arrays.asList(values));
    }

    public static <T extends GenericSelect> Matcher<T> optionsWithValues(List<String> values) {
        return new OptionsValuesMatcher<>(values);
    }

    private WebTesterMatchers() {
        // utility class constructor
    }

}
