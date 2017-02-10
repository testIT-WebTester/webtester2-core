package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.unit.MockFactory.fragment;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class EditableTest {

    Editable cut = new Editable();

    @Test
    public void presentVisibleEnabledAndWritablePageFragmentIsEditable() {
        PageFragment fragment = fragment()
            .present()
            .visible()
            .enabled()
            .writable()
            .build();
        assertThat(cut.test(fragment)).isTrue();
    }

    @Test
    public void notPresentVisibleEnabledAndWritablePageFragmentIsNotEditable() {
        PageFragment fragment = fragment()
            .notPresent()
            .visible()
            .enabled()
            .writable()
            .build();
        assertThat(cut.test(fragment)).isFalse();
    }

    @Test
    public void presentInvisibleEnabledAndWritablePageFragmentINotEditable() {
        PageFragment fragment = fragment()
            .present()
            .invisible()
            .enabled()
            .writable()
            .build();
        assertThat(cut.test(fragment)).isFalse();
    }

    @Test
    public void presentVisibleDisabledAndWritablePageFragmentIsNotEditable() {
        PageFragment fragment = fragment()
            .present()
            .visible()
            .disabled()
            .writable()
            .build();
        assertThat(cut.test(fragment)).isFalse();
    }

    @Test
    public void presentVisibleEnabledAndReadOnlyPageFragmentIsNotEditable() {
        PageFragment fragment = fragment()
            .present()
            .visible()
            .enabled()
            .readOnly()
            .build();
        assertThat(cut.test(fragment)).isFalse();
    }

    @Test
    public void toStringIsGeneratedCorrectly() {
        assertThat(cut).hasToString("editable");
    }

}
