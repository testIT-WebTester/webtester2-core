package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.conditions.Conditions;
import info.novatec.testit.webtester.pagefragments.PageFragment;


public class VisibleTextMatcher<T extends PageFragment> extends TypeSafeMatcher<T> {

    // TODO: Document

    private final String text;
    private String actualText;

    public VisibleTextMatcher(String text) {
        this.text = text;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("visible text <" + text + ">");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actualText = item.getVisibleText();
        return Conditions.visibleText(text).test(item);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actualText + ">");
    }

}
