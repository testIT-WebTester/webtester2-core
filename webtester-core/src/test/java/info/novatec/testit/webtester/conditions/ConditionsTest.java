package info.novatec.testit.webtester.conditions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

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


@RunWith(Enclosed.class)
public class ConditionsTest {

    public static class FactoryMethods {

        @Test
        public void is() {
            Is<Object> condition = Conditions.is(o -> true);
            assertThat(condition).isNotNull();
        }

        @Test
        public void has() {
            Has<Object> condition = Conditions.has(o -> true);
            assertThat(condition).isNotNull();
        }

        @Test
        public void not() {
            Not<Object> condition = Conditions.not(o -> true);
            assertThat(condition).isNotNull();
        }

        @Test
        public void either() {
            Either<Object> condition = Conditions.either(o -> false, o -> true);
            assertThat(condition).isNotNull();
        }

        @Test
        public void attribute() {
            Attribute condition = Conditions.attribute("type");
            assertThat(condition).isNotNull();
            assertThat(condition.getExpectedAttributeName()).isEqualTo("type");
        }

        @Test
        public void attributeWithValue() {
            AttributeWithValue condition = Conditions.attributeWithValue("type", "text");
            assertThat(condition).isNotNull();
            assertThat(condition.getExpectedAttributeName()).isEqualTo("type");
            assertThat(condition.getExpectedValue()).isEqualTo("text");
        }

        @Test
        public void disabled() {
            Disabled condition = Conditions.disabled();
            assertThat(condition).isNotNull();
        }

        @Test
        public void editable() {
            Editable condition = Conditions.editable();
            assertThat(condition).isNotNull();
        }

        @Test
        public void enabled() {
            Enabled condition = Conditions.enabled();
            assertThat(condition).isNotNull();
        }

        @Test
        public void interactable() {
            Interactable condition = Conditions.interactable();
            assertThat(condition).isNotNull();
        }

        @Test
        public void invisible() {
            Invisible condition = Conditions.invisible();
            assertThat(condition).isNotNull();
        }

        @Test
        public void present() {
            Present condition = Conditions.present();
            assertThat(condition).isNotNull();
        }

        @Test
        public void presentAndVisible() {
            PresentAndVisible condition = Conditions.presentAndVisible();
            assertThat(condition).isNotNull();
        }

        @Test
        public void readOnly() {
            ReadOnly condition = Conditions.readOnly();
            assertThat(condition).isNotNull();
        }

        @Test
        public void selected() {
            Selected condition = Conditions.selected();
            assertThat(condition).isNotNull();
        }

        @Test
        public void selectionWithIndex() {
            SelectedIndex condition = Conditions.selectionWithIndex(42);
            assertThat(condition).isNotNull();
            assertThat(condition.getExpectedIndex()).isEqualTo(42);
        }

        @Test
        public void selectionWithIndices() {
            SelectedIndices condition = Conditions.selectionWithIndices(21, 42);
            assertThat(condition).isNotNull();
            assertThat(condition.getExpectedIndices()).containsExactly(21, 42);
        }

        @Test
        public void selectionWithIndicesCollection() {
            SelectedIndices condition = Conditions.selectionWithIndices(Arrays.asList(21, 42));
            assertThat(condition).isNotNull();
            assertThat(condition.getExpectedIndices()).containsExactly(21, 42);
        }

        @Test
        public void selectionWithText() {
            SelectedText condition = Conditions.selectionWithText("foo");
            assertThat(condition).isNotNull();
            assertThat(condition.getExpectedText()).isEqualTo("foo");
        }

        @Test
        public void selectionWithTexts() {
            SelectedTexts condition = Conditions.selectionWithTexts("foo", "bar");
            assertThat(condition).isNotNull();
            assertThat(condition.getExpectedTexts()).containsExactly("foo", "bar");
        }

        @Test
        public void selectionWithTextsCollection() {
            SelectedTexts condition = Conditions.selectionWithTexts(Arrays.asList("foo", "bar"));
            assertThat(condition).isNotNull();
            assertThat(condition.getExpectedTexts()).containsExactly("foo", "bar");
        }

        @Test
        public void selectionWithValue() {
            SelectedValue condition = Conditions.selectionWithValue("foo");
            assertThat(condition).isNotNull();
            assertThat(condition.getExpectedValue()).isEqualTo("foo");
        }

        @Test
        public void selectionWithValues() {
            SelectedValues condition = Conditions.selectionWithValues("foo", "bar");
            assertThat(condition).isNotNull();
            assertThat(condition.getExpectedValues()).containsExactly("foo", "bar");
        }

        @Test
        public void selectionWithValuesCollection() {
            SelectedValues condition = Conditions.selectionWithValues(Arrays.asList("foo", "bar"));
            assertThat(condition).isNotNull();
            assertThat(condition.getExpectedValues()).containsExactly("foo", "bar");
        }

        @Test
        public void visible() {
            Visible condition = Conditions.visible();
            assertThat(condition).isNotNull();
        }

        @Test
        public void visibleText() {
            VisibleTextEquals condition = Conditions.visibleText("foo bar");
            assertThat(condition).isNotNull();
            assertThat(condition.getExpectedText()).isEqualTo("foo bar");
        }

        @Test
        public void visibleTextContaining() {
            VisibleTextContains condition = Conditions.visibleTextContaining("foo bar");
            assertThat(condition).isNotNull();
            assertThat(condition.getExpectedPartialText()).isEqualTo("foo bar");
        }

    }

}
