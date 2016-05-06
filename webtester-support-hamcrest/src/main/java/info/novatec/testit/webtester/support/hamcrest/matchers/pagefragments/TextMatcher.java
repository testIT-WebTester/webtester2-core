package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.GenericTextField;


public class TextMatcher<T extends GenericTextField<?>> extends TypeSafeMatcher<T> {

    // TODO: Document

    private final String text;
    private String actualText;

    public TextMatcher(String text) {
        this.text = text;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("text <" + text + ">");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actualText = item.getText();
        return text.equals(actualText);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actualText + ">");
    }

}
