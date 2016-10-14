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

    public static <T> Is<T> is(Condition<T> condition) {
        return new Is<>(condition);
    }

    public static <T> Has<T> has(Condition<T> condition) {
        return new Has<>(condition);
    }

    public static <T> Not<T> not(Condition<T> condition) {
        return new Not<>(condition);
    }

    /* other */

    public static Attribute attribute(String attributeName) {
        return new Attribute(attributeName);
    }

    public static AttributeWithValue attributeWithValue(String attributeName, Object value) {
        return new AttributeWithValue(attributeName, value);
    }

    public static Disabled disabled() {
        return new Disabled();
    }

    public static Editable editable() {
        return new Editable();
    }

    public static Enabled enabled() {
        return new Enabled();
    }

    public static Interactable interactable() {
        return new Interactable();
    }

    public static Invisible invisible() {
        return new Invisible();
    }

    public static Present present() {
        return new Present();
    }

    public static PresentAndVisible presentAndVisible() {
        return new PresentAndVisible();
    }

    public static ReadOnly readOnly() {
        return new ReadOnly();
    }

    public static Selected selected() {
        return new Selected();
    }

    public static SelectedIndex selectionWithIndex(Integer index) {
        return new SelectedIndex(index);
    }

    public static SelectedIndices selectionWithIndices(Integer... indices) {
        return new SelectedIndices(indices);
    }

    public static SelectedIndices selectionWithIndices(Collection<Integer> indices) {
        return new SelectedIndices(indices);
    }

    public static SelectedText selectionWithText(String text) {
        return new SelectedText(text);
    }

    public static SelectedTexts selectionWithTexts(String... texts) {
        return new SelectedTexts(texts);
    }

    public static SelectedTexts selectionWithTexts(Collection<String> texts) {
        return new SelectedTexts(texts);
    }

    public static SelectedValue selectionWithValue(String value) {
        return new SelectedValue(value);
    }

    public static SelectedValues selectionWithValues(String... values) {
        return new SelectedValues(values);
    }

    public static SelectedValues selectionWithValues(Collection<String> values) {
        return new SelectedValues(values);
    }

    public static Visible visible() {
        return new Visible();
    }

    public static VisibleTextEquals visibleText(String text) {
        return new VisibleTextEquals(text);
    }

    public static VisibleTextContains visibleTextContaining(String partialText) {
        return new VisibleTextContains(partialText);
    }

}
