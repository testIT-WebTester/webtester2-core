package info.novatec.testit.webtester.conditions;

import java.util.Collection;

import lombok.experimental.UtilityClass;

import info.novatec.testit.webtester.conditions.pagefragments.Attribute;
import info.novatec.testit.webtester.conditions.pagefragments.AttributeWithValue;
import info.novatec.testit.webtester.conditions.pagefragments.Disabled;
import info.novatec.testit.webtester.conditions.pagefragments.Editable;
import info.novatec.testit.webtester.conditions.pagefragments.Enabled;
import info.novatec.testit.webtester.conditions.pagefragments.Interactable;
import info.novatec.testit.webtester.conditions.pagefragments.Invisible;
import info.novatec.testit.webtester.conditions.pagefragments.Present;
import info.novatec.testit.webtester.conditions.pagefragments.PresentAndVisible;
import info.novatec.testit.webtester.conditions.pagefragments.ReadOnly;
import info.novatec.testit.webtester.conditions.pagefragments.Selected;
import info.novatec.testit.webtester.conditions.pagefragments.SelectedIndex;
import info.novatec.testit.webtester.conditions.pagefragments.SelectedIndices;
import info.novatec.testit.webtester.conditions.pagefragments.SelectedText;
import info.novatec.testit.webtester.conditions.pagefragments.SelectedTexts;
import info.novatec.testit.webtester.conditions.pagefragments.SelectedValue;
import info.novatec.testit.webtester.conditions.pagefragments.SelectedValues;
import info.novatec.testit.webtester.conditions.pagefragments.Visible;
import info.novatec.testit.webtester.conditions.pagefragments.VisibleTextContains;
import info.novatec.testit.webtester.conditions.pagefragments.VisibleTextEquals;
import info.novatec.testit.webtester.conditions.syntax.Either;
import info.novatec.testit.webtester.conditions.syntax.Has;
import info.novatec.testit.webtester.conditions.syntax.Is;
import info.novatec.testit.webtester.conditions.syntax.Not;


/**
 * This class provides factory methods for all kinds of {@link Condition} implementations.
 * <p>
 * It is recommended to static-import the use of any of the provided methods.
 *
 * @since 2.0
 */
@UtilityClass
public class Conditions {

    /**
     * Creates a new {@link Is} condition.
     *
     * @param condition the nested condition
     * @param <T> the type of the condition
     * @return the created condition
     * @see Is
     * @since 2.0
     */
    public static <T> Is<T> is(Condition<T> condition) {
        return new Is<>(condition);
    }

    /**
     * Creates a new {@link Has} condition.
     *
     * @param condition the nested condition
     * @param <T> the type of the condition
     * @return the created condition
     * @see Has
     * @since 2.0
     */
    public static <T> Has<T> has(Condition<T> condition) {
        return new Has<>(condition);
    }

    /**
     * Creates a new {@link Not} condition.
     *
     * @param condition the nested condition
     * @param <T> the type of the condition
     * @return the created condition
     * @see Not
     * @since 2.0
     */
    public static <T> Not<T> not(Condition<T> condition) {
        return new Not<>(condition);
    }

    /**
     * Creates a new {@link Either} condition.
     *
     * @param conditions the nested conditions
     * @param <T> the type of the condition
     * @return the created condition
     * @see Either
     * @since 2.1
     */
    @SafeVarargs
    public static <T> Either<T> either(Condition<T>... conditions) {
        return new Either<>(conditions);
    }

    /**
     * Creates a new {@link Attribute} condition.
     *
     * @param attributeName the name of the attribute to check
     * @return the created condition
     * @see Attribute
     * @since 2.0
     */
    public static Attribute attribute(String attributeName) {
        return new Attribute(attributeName);
    }

    /**
     * Creates a new {@link AttributeWithValue} condition.
     *
     * @param attributeName the name of the attribute to check
     * @param value the value to check
     * @return the created condition
     * @see AttributeWithValue
     * @since 2.0
     */
    public static AttributeWithValue attributeWithValue(String attributeName, Object value) {
        return new AttributeWithValue(attributeName, value);
    }

    /**
     * Creates a new {@link Disabled} condition.
     *
     * @return the created condition
     * @see Disabled
     * @since 2.0
     */
    public static Disabled disabled() {
        return new Disabled();
    }

    /**
     * Creates a new {@link Editable} condition.
     *
     * @return the created condition
     * @see Editable
     * @since 2.0
     */
    public static Editable editable() {
        return new Editable();
    }

    /**
     * Creates a new {@link Enabled} condition.
     *
     * @return the created condition
     * @see Enabled
     * @since 2.0
     */
    public static Enabled enabled() {
        return new Enabled();
    }

    /**
     * Creates a new {@link Interactable} condition.
     *
     * @return the created condition
     * @see Interactable
     * @since 2.0
     */
    public static Interactable interactable() {
        return new Interactable();
    }

    /**
     * Creates a new {@link Invisible} condition.
     *
     * @return the created condition
     * @see Invisible
     * @since 2.0
     */
    public static Invisible invisible() {
        return new Invisible();
    }

    /**
     * Creates a new {@link Present} condition.
     *
     * @return the created condition
     * @see Present
     * @since 2.0
     */
    public static Present present() {
        return new Present();
    }

    /**
     * Creates a new {@link PresentAndVisible} condition.
     *
     * @return the created condition
     * @see PresentAndVisible
     * @since 2.0
     */
    public static PresentAndVisible presentAndVisible() {
        return new PresentAndVisible();
    }

    /**
     * Creates a new {@link ReadOnly} condition.
     *
     * @return the created condition
     * @see ReadOnly
     * @since 2.0
     */
    public static ReadOnly readOnly() {
        return new ReadOnly();
    }

    /**
     * Creates a new {@link Selected} condition.
     *
     * @return the created condition
     * @see Selected
     * @since 2.0
     */
    public static Selected selected() {
        return new Selected();
    }

    /**
     * Creates a new {@link SelectedIndex} condition.
     *
     * @param index the expected selected index
     * @return the created condition
     * @see SelectedIndex
     * @since 2.0
     */
    public static SelectedIndex selectionWithIndex(Integer index) {
        return new SelectedIndex(index);
    }

    /**
     * Creates a new {@link SelectedIndices} condition.
     *
     * @param indices the expected selected indices
     * @return the created condition
     * @see SelectedIndices
     * @since 2.0
     */
    public static SelectedIndices selectionWithIndices(Integer... indices) {
        return new SelectedIndices(indices);
    }

    /**
     * Creates a new {@link SelectedIndices} condition.
     *
     * @param indices the expected selected indices
     * @return the created condition
     * @see SelectedIndices
     * @since 2.0
     */
    public static SelectedIndices selectionWithIndices(Collection<Integer> indices) {
        return new SelectedIndices(indices);
    }

    /**
     * Creates a new {@link SelectedText} condition.
     *
     * @param text the expected selected text
     * @return the created condition
     * @see SelectedText
     * @since 2.0
     */
    public static SelectedText selectionWithText(String text) {
        return new SelectedText(text);
    }

    /**
     * Creates a new {@link SelectedTexts} condition.
     *
     * @param texts the expected selected texts
     * @return the created condition
     * @see SelectedTexts
     * @since 2.0
     */
    public static SelectedTexts selectionWithTexts(String... texts) {
        return new SelectedTexts(texts);
    }

    /**
     * Creates a new {@link SelectedTexts} condition.
     *
     * @param texts the expected selected texts
     * @return the created condition
     * @see SelectedTexts
     * @since 2.0
     */
    public static SelectedTexts selectionWithTexts(Collection<String> texts) {
        return new SelectedTexts(texts);
    }

    /**
     * Creates a new {@link SelectedValue} condition.
     *
     * @param value the expected selected value
     * @return the created condition
     * @see SelectedValue
     * @since 2.0
     */
    public static SelectedValue selectionWithValue(String value) {
        return new SelectedValue(value);
    }

    /**
     * Creates a new {@link SelectedValues} condition.
     *
     * @param values the expected selected values
     * @return the created condition
     * @see SelectedValues
     * @since 2.0
     */
    public static SelectedValues selectionWithValues(String... values) {
        return new SelectedValues(values);
    }

    /**
     * Creates a new {@link SelectedValues} condition.
     *
     * @param values the expected selected values
     * @return the created condition
     * @see SelectedValues
     * @since 2.0
     */
    public static SelectedValues selectionWithValues(Collection<String> values) {
        return new SelectedValues(values);
    }

    /**
     * Creates a new {@link Visible} condition.
     *
     * @return the created condition
     * @see Visible
     * @since 2.0
     */
    public static Visible visible() {
        return new Visible();
    }

    /**
     * Creates a new {@link VisibleTextEquals} condition.
     *
     * @param text the expected visible text
     * @return the created condition
     * @see VisibleTextEquals
     * @since 2.0
     */
    public static VisibleTextEquals visibleText(String text) {
        return new VisibleTextEquals(text);
    }

    /**
     * Creates a new {@link VisibleTextContains} condition.
     *
     * @param partialText the expected visible partial text
     * @return the created condition
     * @see VisibleTextContains
     * @since 2.0
     */
    public static VisibleTextContains visibleTextContaining(String partialText) {
        return new VisibleTextContains(partialText);
    }

}
