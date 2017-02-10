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
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.OptionsMatcher;
import info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments.OptionsTextsMatcher;
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


/**
 * This class contains a number of {@link Matcher matchers} for WebTester related assertions.
 *
 * @since 2.0
 */
@SuppressWarnings("unchecked")
public final class WebTesterMatchers extends Matchers {

    /**
     * Creates a {@link HasMatcher} that simply delegates to the given matcher.
     * This can be used to create more sentence like assertions.
     * <b>Example:</b> assertThat(textField, has(visibleText("foo")));
     *
     * @param matcher the matcher to delegate to
     * @param <T> the type the matcher is is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T> Matcher<T> has(Matcher<T> matcher) {
        return new HasMatcher<>(matcher);
    }

    /**
     * Creates a new {@link PresentMatcher}.
     *
     * @param <T> the type of page fragment the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends PageFragment> Matcher<T> present() {
        return new PresentMatcher<>();
    }

    /**
     * Creates a new {@link VisibleMatcher}.
     *
     * @param <T> the type of page fragment the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends PageFragment> Matcher<T> visible() {
        return new VisibleMatcher<>();
    }

    /**
     * Creates a new {@link EnabledMatcher}.
     *
     * @param <T> the type of page fragment the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends PageFragment> Matcher<T> enabled() {
        return new EnabledMatcher<>();
    }

    /**
     * Creates a new {@link DisabledMatcher}.
     *
     * @param <T> the type of page fragment the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends PageFragment> Matcher<T> disabled() {
        return new DisabledMatcher<>();
    }

    /**
     * Creates a new {@link InvisibleMatcher}.
     *
     * @param <T> the type of page fragment the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends PageFragment> Matcher<T> invisible() {
        return new InvisibleMatcher<>();
    }

    /**
     * Creates a new {@link TagMatcher} for the given tag.
     *
     * @param tagName the expected tag of the fragment
     * @param <T> the type of page fragment the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends PageFragment> Matcher<T> tag(String tagName) {
        return new TagMatcher<>(tagName);
    }

    /**
     * Creates a new {@link VisibleTextMatcher} for the given text.
     *
     * @param text the expected visible text of the fragment
     * @param <T> the type of page fragment the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends PageFragment> Matcher<T> visibleText(String text) {
        return new VisibleTextMatcher<>(text);
    }

    /**
     * Creates a new {@link VisibleTextContainingMatcher} for the given text.
     *
     * @param textPart the expected visible text part of the fragment
     * @param <T> the type of page fragment the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends PageFragment> Matcher<T> visibleTextContaining(String textPart) {
        return new VisibleTextContainingMatcher<>(textPart);
    }

    /**
     * Creates a new {@link AttributeMatcher} for the given attribute.
     *
     * @param attributeName the name of the attribute expected to exist
     * @param <T> the type of page fragment the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends PageFragment> Matcher<T> attribute(String attributeName) {
        return new AttributeMatcher<>(attributeName);
    }

    /**
     * Creates a new {@link AttributeValueMatcher} for the given attribute and value.
     *
     * @param attributeName the name of the attribute expected to exist
     * @param value the expected value of the attribute
     * @param <T> the type of page fragment the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends PageFragment> Matcher<T> attributeValue(String attributeName, String value) {
        return new AttributeValueMatcher<>(attributeName, value);
    }

    /**
     * Creates a new {@link SelectedMatcher}.
     *
     * @param <T> the type of Selectable the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends Selectable<?>> Matcher<T> selected() {
        return new SelectedMatcher<>();
    }

    /**
     * Creates a new {@link TextMatcher} for the given text.
     *
     * @param text the expected text of the field
     * @param <T> the type of text field the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends GenericTextField<?>> Matcher<T> text(String text) {
        return new TextMatcher<>(text);
    }

    /**
     * Creates a new {@link TextContainingMatcher} for the given text part.
     *
     * @param textPart the expected text part of the field
     * @param <T> the type of text field the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends GenericTextField<?>> Matcher<T> textContaining(String textPart) {
        return new TextContainingMatcher<>(textPart);
    }

    /**
     * Creates a new {@link ButtonLabelMatcher} for the given label.
     *
     * @param label the expected label of the button
     * @param <T> the type of button the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends Button> Matcher<T> label(String label) {
        return new ButtonLabelMatcher<>(label);
    }

    /**
     * Creates a new {@link SelectionTextsMatcher} for the given texts.
     *
     * @param texts the expected selected texts of the multi select
     * @param <T> the type of multi select the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends MultiSelect> Matcher<T> selectionWithTexts(String... texts) {
        return selectionWithTexts(Arrays.asList(texts));
    }

    /**
     * Creates a new {@link SelectionTextsMatcher} for the given texts.
     *
     * @param texts the expected selected texts of the multi select
     * @param <T> the type of multi select the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends MultiSelect> Matcher<T> selectionWithTexts(List<String> texts) {
        return new SelectionTextsMatcher<>(texts);
    }

    /**
     * Creates a new {@link SelectionValuesMatcher} for the given values.
     *
     * @param values the expected selected values of the multi select
     * @param <T> the type of multi select the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends MultiSelect> Matcher<T> selectionWithValues(String... values) {
        return selectionWithValues(Arrays.asList(values));
    }

    /**
     * Creates a new {@link SelectionValuesMatcher} for the given values.
     *
     * @param values the expected selected values of the multi select
     * @param <T> the type of multi select the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends MultiSelect> Matcher<T> selectionWithValues(List<String> values) {
        return new SelectionValuesMatcher<>(values);
    }

    /**
     * Creates a new {@link SelectionIndicesMatcher} for the given indices.
     *
     * @param indices the expected selected indices of the multi select
     * @param <T> the type of multi select the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends MultiSelect> Matcher<T> selectionWithIndices(Integer... indices) {
        return selectionWithIndices(Arrays.asList(indices));
    }

    /**
     * Creates a new {@link SelectionIndicesMatcher} for the given indices.
     *
     * @param indices the expected selected indices of the multi select
     * @param <T> the type of multi select the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends MultiSelect> Matcher<T> selectionWithIndices(List<Integer> indices) {
        return new SelectionIndicesMatcher<>(indices);
    }

    /**
     * Creates a new {@link SelectedOptionsMatcher}.
     *
     * @param <T> the type of multi select the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends MultiSelect> Matcher<T> selectedOptions() {
        return new SelectedOptionsMatcher<>();
    }

    /**
     * Creates a new {@link NumberOfSelectedOptionsMatcher} for the given number.
     *
     * @param number the expected number of selected options
     * @param <T> the type of multi select the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends MultiSelect> Matcher<T> selectedOptions(Integer number) {
        return new NumberOfSelectedOptionsMatcher<>(number);
    }

    /**
     * Creates a new {@link NoSelectedOptionsMatcher}.
     *
     * @param <T> the type of multi select the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends MultiSelect> Matcher<T> noSelectedOptions() {
        return new NoSelectedOptionsMatcher<>();
    }

    /**
     * Creates a new {@link SelectionTextMatcher} for the given text.
     *
     * @param text the expected selected text of the single select
     * @param <T> the type of single select the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends SingleSelect> Matcher<T> selectionWithText(String text) {
        return new SelectionTextMatcher<>(text);
    }

    /**
     * Creates a new {@link SelectionValueMatcher} for the given value.
     *
     * @param value the expected selected value of the single select
     * @param <T> the type of single select the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends SingleSelect> Matcher<T> selectionWithValue(String value) {
        return new SelectionValueMatcher<>(value);
    }

    /**
     * Creates a new {@link SelectionIndexMatcher} for the given index.
     *
     * @param index the expected selected index of the single select
     * @param <T> the type of single select the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends SingleSelect> Matcher<T> selectionWithIndex(Integer index) {
        return new SelectionIndexMatcher<>(index);
    }

    /**
     * Creates a new {@link OptionsMatcher}.
     *
     * @param <T> the type of select the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends GenericSelect> Matcher<T> options() {
        return new OptionsMatcher<>();
    }

    /**
     * Creates a new {@link NumberOfOptionsMatcher} for the given number.
     *
     * @param number the expected number of options
     * @param <T> the type of select the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends GenericSelect> Matcher<T> options(Integer number) {
        return new NumberOfOptionsMatcher<>(number);
    }

    /**
     * Creates a new {@link NoOptionsMatcher}.
     *
     * @param <T> the type of select the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends GenericSelect> Matcher<T> noOptions() {
        return new NoOptionsMatcher<>();
    }

    /**
     * Creates a new {@link OptionsTextsMatcher} for the given texts.
     *
     * @param texts the expected option texts of the select
     * @param <T> the type of select the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends GenericSelect> Matcher<T> optionsWithTexts(String... texts) {
        return optionsWithTexts(Arrays.asList(texts));
    }

    /**
     * Creates a new {@link OptionsTextsMatcher} for the given texts.
     *
     * @param texts the expected option texts of the select
     * @param <T> the type of select the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends GenericSelect> Matcher<T> optionsWithTexts(List<String> texts) {
        return new OptionsTextsMatcher<>(texts);
    }

    /**
     * Creates a new {@link OptionsValuesMatcher} for the given values.
     *
     * @param values the expected option values of the select
     * @param <T> the type of select the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends GenericSelect> Matcher<T> optionsWithValues(String... values) {
        return optionsWithValues(Arrays.asList(values));
    }

    /**
     * Creates a new {@link OptionsValuesMatcher} for the given values.
     *
     * @param values the expected option values of the select
     * @param <T> the type of select the matcher is checking against
     * @return the newly created matcher
     * @since 2.0
     */
    public static <T extends GenericSelect> Matcher<T> optionsWithValues(List<String> values) {
        return new OptionsValuesMatcher<>(values);
    }

    private WebTesterMatchers() {
        // utility class constructor
    }

}
