package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.MockFactory.fragment;

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
        assertThat(isEditable(fragment)).isTrue();
    }

    @Test
    public void notPresentVisibleEnabledAndWritablePageFragmentIsNotEditable() {
        PageFragment fragment = fragment()
            .notPresent()
            .visible()
            .enabled()
            .writable()
            .build();
        assertThat(isEditable(fragment)).isFalse();
    }

    @Test
    public void presentInvisibleEnabledAndWritablePageFragmentINotEditable() {
        PageFragment fragment = fragment()
            .present()
            .invisible()
            .enabled()
            .writable()
            .build();
        assertThat(isEditable(fragment)).isFalse();
    }

    @Test
    public void presentVisibleDisabledAndWritablePageFragmentIsNotEditable() {
        PageFragment fragment = fragment()
            .present()
            .visible()
            .disabled()
            .writable()
            .build();
        assertThat(isEditable(fragment)).isFalse();
    }

    @Test
    public void presentVisibleEnabledAndReadOnlyPageFragmentIsNotEditable() {
        PageFragment fragment = fragment()
            .present()
            .visible()
            .enabled()
            .readOnly()
            .build();
        assertThat(isEditable(fragment)).isFalse();
    }

    boolean isEditable(PageFragment fragment) {
        return cut.test(fragment);
    }

}
