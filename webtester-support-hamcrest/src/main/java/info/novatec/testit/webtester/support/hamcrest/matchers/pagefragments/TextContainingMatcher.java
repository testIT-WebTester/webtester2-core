package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.GenericTextField;


public class TextContainingMatcher<T extends GenericTextField<?>> extends TypeSafeMatcher<T> {

    // TODO: Document

    private final String textFragment;
    private String actualText;

    public TextContainingMatcher(String textFragment) {
        this.textFragment = textFragment;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("text containing <" + textFragment + ">");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actualText = item.getText();
        return actualText.contains(textFragment);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("<" + actualText + "> does not contain <" + textFragment + ">");
    }

}
