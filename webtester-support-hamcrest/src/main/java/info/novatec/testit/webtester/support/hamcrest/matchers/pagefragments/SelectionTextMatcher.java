package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import java.util.Optional;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.SingleSelect;


public class SelectionTextMatcher<T extends SingleSelect> extends TypeSafeMatcher<T> {

    // TODO: Document

    private final String text;
    private Optional<String> actualText;

    public SelectionTextMatcher(String text) {
        this.text = text;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("selection with text <" + text + ">");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actualText = item.getSelectionText();
        return actualText.filter(text::equals).isPresent();
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actualText + ">");
    }

}
