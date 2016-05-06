package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.conditions.Conditions;
import info.novatec.testit.webtester.pagefragments.PageFragment;


public class VisibleTextContainingMatcher<T extends PageFragment> extends TypeSafeMatcher<T> {

    // TODO: Document

    private final String textFragment;
    private String actualText;

    public VisibleTextContainingMatcher(String textFragment) {
        this.textFragment = textFragment;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("visible text containing <" + textFragment + ">");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actualText = item.getVisibleText();
        return Conditions.visibleTextContaining(textFragment).test(item);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("<" + actualText + "> does not contain <" + textFragment + ">");
    }

}
